package com.runtips.ricardo.runtipsmx.activities;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.runtips.ricardo.runtipsmx.classes.AlternativeChronometer;
import com.runtips.ricardo.runtipsmx.db.DBHelper;
import com.runtips.ricardo.runtipsmx.R;

import java.util.HashMap;
import java.util.Timer;

public class RunActivity extends AppCompatActivity{

    private AlternativeChronometer chrono;
    private TextView txtSpeed;
    private TextView txtDistance;
    private Button btnStart;
    private Button btnPause;
    private int tabPosition;
    private boolean isStart;

    //private GPSStatus gpsStatus = null;

    private boolean isBound = false;
    //private Tracker tracker;
    //private Workout workout;
    private Timer timer;
    SQLiteDatabase dataBase;

    private enum GPSLevel {POOR, ACCEPTABLE, GOOD};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
        ActionBar ab = getSupportActionBar();
        if(ab != null){
            ab.setDisplayHomeAsUpEnabled(true);
        }

        savePreviousTab();

        dataBase = DBHelper.getWritableDatabase(this);

        //bindGpsTracker();
        //gpsStatus = new GPSStatus(this);


        chrono = findViewById(R.id.altchronometerRun);
        txtSpeed = findViewById(R.id.txtRunSpeedTime);
        txtDistance = findViewById(R.id.txtRunDistanceTime);

        btnStart = findViewById(R.id.btnRunStart);
        btnPause = findViewById(R.id.btnRunPause);

        chrono.setOnChronometerTickListener(new AlternativeChronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(AlternativeChronometer chronometerChanged) {
                chrono = chronometerChanged;
            }
        });

    }

    public void startStopChronometer(View view){

        if(isStart){
            chrono.stop();
            isStart=false;
            ((Button)view).setText(getResources().getString(R.string.btnLabelStart));
        }
        else{
            chrono.setBase(SystemClock.elapsedRealtime());
            chrono.start();
            isStart = true;
            ((Button)view).setText(getResources().getText(R.string.btnLabelStop));
        }

    }

    private void savePreviousTab(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle == null) {
            tabPosition = 0;
        } else {
            tabPosition = bundle.getInt("position");
        }
    }

    // TODO Check difference between FLAGS. Apparently both are similar

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RunActivity.this, StartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("position", tabPosition);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(RunActivity.this, StartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("position", tabPosition);
                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    /*private void bindGpsTracker() {
        // Establish a connection with the service. We use an explicit
        // class name because we want a specific service implementation that
        // we know will be running in our own process (and thus won't be
        // supporting component replacement by other applications).
        getApplicationContext().bindService(new Intent(this, Tracker.class),
                serviceConnection, Context.BIND_AUTO_CREATE);
        isBound = true;
    }*/

    /*private void unbindGpsTracker() {
        if (isBound) {
            // Detach our existing connection.
            getApplicationContext().unbindService(serviceConnection);
            isBound = false;
        }
    }*/

    /*private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {

            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service. Because we have bound to a explicit
            // service that we know is running in our own process, we can
            // cast its IBinder to a concrete class and directly access it.
            if(tracker == null){
                tracker = ((Tracker.LocalBinder) service).getService();
                onGPSTrackerBound();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            // Because it is running in our same process, we should never
            // see this happen.
            isBound = false;
            tracker = null;

        }
    };*/

    /*private void onGPSTrackerBound(){
        //TODO put correct method def
        if(tracker == null){
            return;
        }
        if(tracker.getWorkout() == null){
            return;
        }

        workout = tracker.getWorkout();

        {
            //RunActivity Line 182
            HashMap<String, Object> bindValues = new HashMap<>();
            //bindValues.put(Workout.KEY_COUNTER_VIEW, countdownView);
            //workout.onBind(workout, bindValues);

        }

        //startTimer();
    }

    @Override
    public void onTick() {
        updateGpsView();
    }

    /**
     *
     */
    /*private void updateGpsView(){
        if(!gpsStatus.isEnabled() || !gpsStatus.isLogging()){

        }
        else{
            //gps accuracy
            float accuracy = -1;
            if(tracker != null){
                Location location = tracker.getLastKnownLocation();
                if(location != null){
                    accuracy = location.getAccuracy();
                }
            }
            //String gpsAccuracy = getGpsAccuracyString(accuracy);

            if(!gpsStatus.isFixed()){

            }
            else{
                switch(getGpsLevel(accuracy)){
                    case POOR:
                        break;
                    case ACCEPTABLE:
                        break;
                    case GOOD:
                        break;

                }
            }

        }

    }

    @Override
    public String getGpsAccuracy() {
        if(tracker != null){
            Location location = tracker.getLastKnownLocation();
            if(location != null){
                return getGpsAccuracyString(location.getAccuracy());
            }
        }
        return "";
    }

    @Override
    public int getSatellitesAvailable() {
        return 0;
    }

    @Override
    public int getSatellitesFixed() {
        return 0;
    }

    private GPSLevel getGpsLevel(double gpsAccuracyMeters){
        if(gpsAccuracyMeters <= 7){
            return GPSLevel.GOOD;
        }
        else if(gpsAccuracyMeters <= 15){
            return GPSLevel.ACCEPTABLE;
        }
        else
            return GPSLevel.POOR;
    }

    private String getGpsAccuracyString(float accuracy){
        if(accuracy > 0){

        }
        else{

        }
        return "";
    }

    /*private void startTimer(){
        timer = new Timer();
        //timer.scheduleAtFixedRate();

    }*/


    /*private boolean checkGpsEnabled(){
        boolean enabled  = false;
        try {
            int gpsSignal = Settings.Secure.getInt(this.getContentResolver(), Settings.Secure.LOCATION_MODE);

            if(gpsSignal == 0){
                showGpsAlert();
            }
            enabled = true;
        }
        catch (Settings.SettingNotFoundException snfe){
            snfe.printStackTrace();

        }
        return enabled;
    }

    public void showGpsAlert(){
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.alertGPSTitle))
                .setMessage(getString(R.string.alertGPSMessage))
                .setPositiveButton(getString(R.string.alertGPSOK), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(getString(R.string.alertGPSCancel), null)
                .show();
    }*/
}
