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
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.widget.Toast;

import com.runtips.ricardo.runtipsmx.classes.ImageProcessing;
import com.runtips.ricardo.runtipsmx.R;
import com.runtips.ricardo.runtipsmx.models.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import io.realm.Realm;

import static android.view.View.VISIBLE;

public class CameraHeartRateActivity extends Activity implements RadioGroup.OnCheckedChangeListener {

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

    private static SurfaceHolder surfaceHolder = null;
    private static SurfaceView surfaceView = null;
    private static Camera myCamera = null;
    private static PowerManager powerManager = null;

    private Button btnCheck;
    private Button btnBack;
    private ImageView imgInstructions;
    private TextView txtInstructions;
    private TextView txtAux1;
    private static TextView txtCheck;
    private TextView txtAux2;
    private TextInputLayout txtInputLayoutTime;
    private EditText editTextTime;
    private TextInputLayout txtInputLayoutPulse;
    private EditText editTextPulse;
    private TextInputLayout txtInputLayoutWeight;
    private EditText editTextWeight;
    private RadioGroup radioGroup;
    //private RadioButton radioManual;
    //private RadioButton radioAuto;

    private boolean isManual = false;

    private static WakeLock wakeLock = null;

    private Realm realm;

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

        c = getApplicationContext();

        //realm.init(c);
        realm = Realm.getDefaultInstance();

        radioGroup = findViewById(R.id.radioGroupAutoManual);
        //radioAuto = findViewById(R.id.radioAutomatic);
        //radioManual = findViewById(R.id.radioManual);

        txtInstructions = findViewById(R.id.txtHeartPulseInstructions);
        imgInstructions = findViewById(R.id.imageViewHeartRateInstructions);
        surfaceView = findViewById(R.id.preview);

        txtAux1 = findViewById(R.id.txtAux01);
        txtAux2 = findViewById(R.id.txtAux02);
        txtCheck = findViewById(R.id.txtPhysicalCamHR);

        txtInputLayoutTime = findViewById(R.id.textInputLayoutHeartPulseTime);
        editTextTime = findViewById(R.id.editHeartRateTime);
        txtInputLayoutWeight = findViewById(R.id.textInputLayoutWeight);
        editTextWeight = findViewById(R.id.editHeartRateWeight);
        txtInputLayoutPulse = findViewById(R.id.textInputLayoutPulse);
        editTextPulse = findViewById(R.id.editHeartRatePulse);

        btnCheck = findViewById(R.id.btnHeartRateOK);
        btnBack = findViewById(R.id.btnHeartRateBack);

        editTextPulse.setVisibility(View.INVISIBLE);
        txtInputLayoutPulse.setVisibility(View.INVISIBLE);

        startSurface();

        /*radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

            switch (i) {
            case R.id.radioAutomatic:

                editTextPulse.setVisibility(View.INVISIBLE);
                txtInputLayoutPulse.setVisibility(View.INVISIBLE);

                //surfaceView.setVisibility(VISIBLE);
                txtInstructions.setVisibility(View.VISIBLE);
                imgInstructions.setVisibility(VISIBLE);

                txtAux1.setVisibility(View.VISIBLE);
                txtAux2.setVisibility(View.VISIBLE);
                txtCheck.setVisibility(View.VISIBLE);
                txtCheck.setText("");

                startSurface();
                runSensor();

                break;

            case R.id.radioManual:

                txtAux1.setVisibility(View.INVISIBLE);
                txtAux2.setVisibility(View.INVISIBLE);
                txtCheck.setVisibility(View.INVISIBLE);

                imgInstructions.setVisibility(View.INVISIBLE);
                //surfaceView.setVisibility(View.INVISIBLE);
                txtInstructions.setVisibility(View.INVISIBLE);

                editTextPulse.setVisibility(View.VISIBLE);
                txtInputLayoutPulse.setVisibility(View.VISIBLE);

                stopSensor();

                break;

        }

            }
        });*/
        radioGroup.setOnCheckedChangeListener(CameraHeartRateActivity.this);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CameraHeartRateActivity.this, VideoActivity.class);
                startActivity(intent);
            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isManual){
                    String userPulse = editTextPulse.getText().toString();
                    int pulse = Integer.parseInt(userPulse);
                    String userWeight = editTextWeight.getText().toString();
                    float weight = Float.parseFloat(userWeight);
                    String userTime = editTextTime.getText().toString();
                    List<String> time = Arrays.asList(userTime.split("\\s*:\\s*"));

                    int minutes = Integer.parseInt(time.get(0));
                    int seconds = Integer.parseInt(time.get(1));

                    int totalNumberOfSecs = minutes*60*1000 + seconds*1000;

                    createNewTest(totalNumberOfSecs, pulse, weight);

                    if(editTextPulse.getText().length() > 1) {

                        Intent intent = new Intent(CameraHeartRateActivity.this, StartActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(CameraHeartRateActivity.this, getResources().getText(R.string.txtHeartRateIncorrectMeasure) + ".", Toast.LENGTH_LONG).show();
                    }

                }
                else{
                    if(beatsAvg < 200 && beatsAvg >= 60){
                        if(editTextTime.getText().length() > 1){
                            Intent intent = new Intent(CameraHeartRateActivity.this, StartActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(CameraHeartRateActivity.this, getResources().getText(R.string.txtHeartRateIncorrectTime), Toast.LENGTH_LONG).show();
                    }

                    else {
                        Toast.makeText(CameraHeartRateActivity.this, getResources().getText(R.string.txtHeartRateIncorrectMeasure), Toast.LENGTH_LONG).show();

                    }

                }

            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

        switch (i) {
            case R.id.radioAutomatic:

                isManual = false;

                editTextPulse.setVisibility(View.INVISIBLE);
                txtInputLayoutPulse.setVisibility(View.INVISIBLE);

                surfaceView.setVisibility(VISIBLE);
                txtInstructions.setVisibility(View.VISIBLE);
                imgInstructions.setVisibility(VISIBLE);

                txtAux1.setVisibility(View.VISIBLE);
                txtAux2.setVisibility(View.VISIBLE);
                txtCheck.setVisibility(View.VISIBLE);
                txtCheck.setText("");

                runSensor();

                break;

            case R.id.radioManual:

                isManual = true;

                txtAux1.setVisibility(View.INVISIBLE);
                txtAux2.setVisibility(View.INVISIBLE);
                txtCheck.setVisibility(View.INVISIBLE);

                imgInstructions.setVisibility(View.INVISIBLE);
                surfaceView.setVisibility(View.INVISIBLE);
                txtInstructions.setVisibility(View.INVISIBLE);

                editTextPulse.setVisibility(View.VISIBLE);
                txtInputLayoutPulse.setVisibility(View.VISIBLE);

                //stopSensor();

                break;

        }
    }

    private void startSurface(){
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(surfaceCallback);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "runtipsmx:screenwakelock");

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},0);
        }
    }

    private void runSensor(){
        wakeLock.acquire(10*60*1000L /*5 minutes*/);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        }
        else {
            try {
                myCamera = Camera.open(0);

            }
            catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(c,"runSensorException" + e, Toast.LENGTH_SHORT).show();
            }

        }

        startTime = System.currentTimeMillis();

    }

    private static void stopSensor() {

        wakeLock.release();
        myCamera.setPreviewCallback(null);
        myCamera.stopPreview();
        myCamera.release();
        myCamera = null;
    }

    @SuppressWarnings("deprecation")
    private static PreviewCallback previewCallback = new PreviewCallback() {

        /**
         * FROM: https://github.com/phishman3579/android-heart-rate-monitor
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
            Toast.makeText(c, "surfaceCreated", Toast.LENGTH_LONG).show();
            try {
                myCamera.setPreviewDisplay(surfaceHolder);
                myCamera.setPreviewCallback(previewCallback);
            } catch (Exception ex) {
                Toast.makeText(c, "sCrEx: " + ex, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Toast.makeText(c, "surfaceChanged", Toast.LENGTH_SHORT).show();

            try {
                turnOnCamera(width, height);
            }
            catch (Exception ex) {
                Toast.makeText(c, "sChEx: " + ex, Toast.LENGTH_LONG).show();

            }

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            Toast.makeText(c, "surfaceDestroyed", Toast.LENGTH_LONG).show();
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

    private static void turnOnCamera(int width, int height) {
        Camera.Parameters parameters = myCamera.getParameters();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        Camera.Size size = getSmallestPreviewSize(width, height, parameters);
        if (size != null) {
            parameters.setPreviewSize(size.width, size.height);
        }
        myCamera.setParameters(parameters);
        myCamera.startPreview();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume(){

        super.onResume();
        runSensor();
    }

    @Override
    protected void onPause(){
        super.onPause();
        stopSensor();
    }

    /** CRUD Actions **/
    private void createNewTest(int time, int pulse, float weight) {

        /*realm.beginTransaction();
        Test test = new Test(time, pulse, weight);
        realm.copyToRealm(test);
        realm.commitTransaction();*/

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Test test2 = new Test(time, pulse, weight);
                realm.copyToRealm(test2);
            }
        });


    }
}
