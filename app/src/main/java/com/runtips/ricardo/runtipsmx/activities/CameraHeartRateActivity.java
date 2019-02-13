package com.runtips.ricardo.runtipsmx.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.widget.Toast;

import com.runtips.ricardo.runtipsmx.activities.Presentacion01Activity;
import com.runtips.ricardo.runtipsmx.activities.StartActivity;
import com.runtips.ricardo.runtipsmx.classes.ImageProcessing;
import com.runtips.ricardo.runtipsmx.R;

import java.util.concurrent.atomic.AtomicBoolean;

public class CameraHeartRateActivity extends Activity{

    SurfaceHolder SurfaceHolder;

    private static int averageIndex = 0;
    private static final int averageArraySize = 4;
    private static final int[] averageArray = new int[averageArraySize];
    public static Context c;


    private static final AtomicBoolean processing = new AtomicBoolean(false);

    private static int beatsAvg = 0;
    private static double beats = 0;
    private static long startTime = 0;
    private static int beatsIndex = 0;
    private static final int beatsArraySize = 3;
    private static final int[] beatsArray = new int[beatsArraySize];

    //private static final String TAG = "HeartRateMonitor";

    private SurfaceView preview = null;
    private static SurfaceHolder previewHolder = null;
    private static Camera camera = null;

    private Button btnCheck;
    private Button btnBack;
    private static TextView txtCheck;
    private static EditText editTextTime;

    private static WakeLock wakeLock = null;

    public static enum TYPE {
        GREEN, RED
    };

    private static TYPE currentType = TYPE.GREEN;

    //private static int beatsIndex = 0;
    //private static final int beatsArraySize = 3;
    //private static final int[] beatsArray = new int[beatsArraySize];
    //private static double beats = 0;
    //private static long startTime = 0;

    /**
     * "For anyone interested, I figured out how to detect whether the user has his finger
     * placed on the camera or not. In the imageProcessing.java, in the decodeYUV420SPtoRedAvg()
     * method, we calculate the average value of the pixels of the red color. DOing a small
     * experiment, I found out that when the user has his finger placed on the camera lens,
     * the average has a value of >200 . In other case, the average value is <200.
     * thus, in the HeartRateActivity you can add an if statement after the call of the
     * decodeYUV420SPtoRedAvg(), to find wether the value returned is >200 or < 199. You can
     * use this to display an alert box or something similar to guide the user to place his
     * finger on the camera lens."
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_heart_rate);

        preview = findViewById(R.id.preview);
        previewHolder = preview.getHolder();
        previewHolder.addCallback(surfaceCallback);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
            previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        txtCheck = findViewById(R.id.txtPhysicalCamHR);
        editTextTime = findViewById(R.id.editHeartRateTime);

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "runtipsmx:screenwakelock");

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},0);
        }

        btnCheck = findViewById(R.id.btnHeartRateOK);
        btnBack = findViewById(R.id.btnHeartRateBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CameraHeartRateActivity.this, Presentacion01Activity.class);
                startActivity(intent);
            }
        });


        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(beatsAvg < 200 && beatsAvg >= 60){
                    if(editTextTime.getText().length() > 2){
                        Intent intent = new Intent(CameraHeartRateActivity.this, StartActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(CameraHeartRateActivity.this, getResources().getText(R.string.txtHeartRateIncorrectTime), Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(CameraHeartRateActivity.this, getResources().getText(R.string.txtHeartRateIncorrectMeasure), Toast.LENGTH_LONG).show();

            }
        });
    }

    /*private void measure() {
        preview = (SurfaceView) findViewById(R.id.preview);
        previewHolder = preview.getHolder();
        previewHolder.addCallback(surfaceCallback);
        //previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "DoNotDimScreen");


    }*/

    @SuppressWarnings("deprecation")
    private static PreviewCallback previewCallback = new PreviewCallback() {

        /**
         * {@inheritDoc}
         */
        @Override
        public void onPreviewFrame(byte[] data, Camera cam) {
            if (data == null) throw new NullPointerException();
            Camera.Size size = cam.getParameters().getPreviewSize();
            if (size == null) throw new NullPointerException();

            if (!processing.compareAndSet(false, true)) return;

            int width = size.width;
            int height = size.height;
            String message = "";

            int imgAvg = ImageProcessing.decodeYUV420SPtoRedAvg(data.clone(), height, width);
            Log.i("*** CameraimgAvg *** = ", "imgAvg="+imgAvg);
            if (imgAvg == 0 || imgAvg == 255) {
                processing.set(false);
                return;
            }

            int averageArrayAvg = 0;
            int averageArrayCnt = 0;
            for (int i = 0; i < averageArray.length; i++) {
                if (averageArray[i] > 0) {
                    averageArrayAvg += averageArray[i];
                    averageArrayCnt++;
                }
            }

            int rollingAverage = (averageArrayCnt > 0) ? (averageArrayAvg / averageArrayCnt) : 0;
            TYPE newType = currentType;
            if (imgAvg < rollingAverage) {
                newType = TYPE.RED;
                if (newType != currentType) {
                    beats++;
                    // Log.d(TAG, "BEAT!! beats="+beats);
                }
            } else if (imgAvg > rollingAverage) {
                newType = TYPE.GREEN;
            }

            if (averageIndex == averageArraySize) averageIndex = 0;
            averageArray[averageIndex] = imgAvg;
            averageIndex++;

            // Transitioned from one state to another to the same
            if (newType != currentType) {
                currentType = newType;
                //image.postInvalidate();
            }

            long endTime = System.currentTimeMillis();
            double totalTimeInSecs = (endTime - startTime) / 1000d;
            if (totalTimeInSecs >= 10) {
                double bps = (beats / totalTimeInSecs);
                int dpm = (int) (bps * 60d);
                if (dpm < 30 || dpm > 180) {
                    startTime = System.currentTimeMillis();
                    beats = 0;
                    processing.set(false);
                    return;
                }

                // Log.d(TAG,
                // "totalTimeInSecs="+totalTimeInSecs+" beats="+beats);

                if (beatsIndex == beatsArraySize) beatsIndex = 0;
                beatsArray[beatsIndex] = dpm;
                beatsIndex++;

                int beatsArrayAvg = 0;
                int beatsArrayCnt = 0;
                for (int i = 0; i < beatsArray.length; i++) {
                    if (beatsArray[i] > 0) {
                        beatsArrayAvg += beatsArray[i];
                        beatsArrayCnt++;
                    }
                }
                beatsAvg = (beatsArrayAvg / beatsArrayCnt);
                txtCheck.setText(String.valueOf(beatsAvg));
                startTime = System.currentTimeMillis();
                beats = 0;
            }
            processing.set(false);
        }
    };

    @SuppressWarnings("deprecation")
    private static SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                camera.setPreviewDisplay(previewHolder);
                camera.setPreviewCallback(previewCallback);
            } catch (Throwable t) {
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Camera.Parameters parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            Camera.Size size = getSmallestPreviewSize(width, height, parameters);
            if (size != null) {
                parameters.setPreviewSize(size.width, size.height);
            }
            camera.setParameters(parameters);
            camera.startPreview();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        }
    };

    @SuppressWarnings("deprecation")
    private static Camera.Size getSmallestPreviewSize(int width, int height, Camera.Parameters parameters) {
        Camera.Size result = null;

        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result = size;
                } else {
                    int resultArea = result.width * result.height;
                    int newArea = size.width * size.height;

                    if (newArea < resultArea) result = size;
                }
            }
        }

        return result;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume(){

        super.onResume();
        wakeLock.acquire(10*60*1000L /*5 minutes*/);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        else
            camera = Camera.open();

        startTime = System.currentTimeMillis();
    }

    @Override
    protected void onPause(){
        super.onPause();
        wakeLock.release();

        camera.setPreviewCallback(null);
        camera.stopPreview();
        camera.release();
        camera = null;
    }
}
