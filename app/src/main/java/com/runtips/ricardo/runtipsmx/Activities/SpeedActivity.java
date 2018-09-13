package com.runtips.ricardo.runtipsmx.Activities;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.runtips.ricardo.runtipsmx.Classes.AlternativeChronometer;
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
    private Button btnSpeedStop;
    private Button btnSpeedPause;
    private boolean isStart;

    private int[] entrenamiento = new int[]{12, 6, 12, 6, 12, 6, 12, 6};

    private AlternativeChronometer currentChronometer;
    private AlternativeChronometer totalChronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed);

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
}
