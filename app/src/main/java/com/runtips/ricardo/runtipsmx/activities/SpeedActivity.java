package com.runtips.ricardo.runtipsmx.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.runtips.ricardo.runtipsmx.classes.AlternativeChronometer;
import com.runtips.ricardo.runtipsmx.R;

public class SpeedActivity extends AppCompatActivity {

    /*private RadioGroup radioGroupDistanceTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed);

        radioGroupDistanceTime = findViewById(R.id.radioGroupChooseTimeorDistance);

        //ArrayAdapter<String> arrayAdapterDistance = new ArrayAdapter<String>(this,
          //      R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.spinnerSpeedIntervalDistances));
        final ArrayAdapter<String> arrayAdapterTime = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.spinnerSpeedIntervalTimes));
        final ArrayAdapter<CharSequence> arrayAdapterDistance = ArrayAdapter.createFromResource(this, R.array.spinnerSpeedIntervalDistances,
                R.layout.support_simple_spinner_dropdown_item);
        arrayAdapterDistance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final MaterialBetterSpinner material = findViewById(R.id.spinnerRunDistance);
        material.setAdapter(arrayAdapterTime);

        radioGroupDistanceTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                material.setSelection(0);
                material.setAdapter(null);
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.radioSpeedSelectDistance:
                        material.setSelection(0);
                        material.setAdapter(arrayAdapterDistance);
                        break;
                    case R.id.radioSpeedSelectTime:
                        material.setSelection(0);
                        material.setAdapter(arrayAdapterTime);
                        break;
                }
            }
        });

    }*/

    private Button btnSpeedStart;
    private Button btnSpeedPause;
    private boolean isStart;

    private int[] entrenamiento = new int[]{12, 6, 12, 6, 12, 6, 12, 6};

    public AlternativeChronometer currentChronometer;
    public AlternativeChronometer totalChronometer;

    static boolean status;
    static long startTime, endTime;
    LocationManager locationManager;
    static ProgressDialog progressDialog;
    static int p = 0;
    LocationService myService;

    @Override
    protected void onDestroy() {
        if(status == true)
            unbindService();
        super.onDestroy();
    }

    private void unbindService() {
        if(status == false) {
            return;
        }
        Intent i  = new Intent(getApplicationContext(), LocationService.class);
        unbindService(serviceConnection);
        status=false;
    }

    @Override
    public void onBackPressed() {
        if(status == false)
            super.onBackPressed();
        else
            moveTaskToBack(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1000:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "GRANTED", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(this, "DENIED", Toast.LENGTH_LONG).show();
            }
            return;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);


        }

        //TODO VIDEO 24:46



        btnSpeedStart = findViewById(R.id.btnSpeedStart);
        btnSpeedPause = findViewById(R.id.btnSpeedPause);
        currentChronometer = findViewById(R.id.chronoSpeedCurrent);
        totalChronometer = findViewById(R.id.chronoTotalTime);

        btnSpeedPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentChronometer.stop();
                long value = currentChronometer.getBase();
                currentChronometer.setBase(value);
                currentChronometer.start();
            }
        });

        btnSpeedStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkGPS();
                locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    return;
                }
                if(status == false) {
                    bindService();
                    progressDialog = new ProgressDialog(SpeedActivity.this);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Obteniendo posición...");
                    progressDialog.show();

                }
            }
        });

        totalChronometer.setOnChronometerTickListener(new AlternativeChronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(AlternativeChronometer chronometer) {
                totalChronometer = chronometer;
            }
        });

        currentChronometer.setOnChronometerTickListener(new AlternativeChronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(AlternativeChronometer chronometerChanged) {
                currentChronometer = chronometerChanged;
            }
        });

    }

    private void checkGPS() {
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            showGPSDisabledAlert();
        }

    }

    private void showGPSDisabledAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Enable GPS")
                .setCancelable(false)
                .setPositiveButton("Enable GPS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

    }

    public void startStopChronometer(View view){

        if(isStart){
            currentChronometer.stop();
            totalChronometer.stop();
            isStart=false;
            ((Button)view).setText(getResources().getString(R.string.btnLabelStart));
        }
        else{
            currentChronometer.setBase(SystemClock.elapsedRealtime());
            totalChronometer.setBase(SystemClock.elapsedRealtime());
            currentChronometer.start();
            totalChronometer.start();
            isStart = true;
            ((Button)view).setText(getResources().getText(R.string.btnLabelStop));
        }

    }

    public void bindService() {
        if(status == true) {
            return;
        }
        Intent intent = new Intent(getApplicationContext(), LocationService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        status = true;
        startTime = System.currentTimeMillis();
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LocationService.LocalBinder binder = (LocationService.LocalBinder)iBinder;
            myService = binder.getService();
            status = true;



        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            status = false;

        }
    };


}
