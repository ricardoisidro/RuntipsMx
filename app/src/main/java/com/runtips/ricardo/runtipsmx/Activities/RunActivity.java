package com.runtips.ricardo.runtipsmx.Activities;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import com.runtips.ricardo.runtipsmx.Classes.AlternativeChronometer;
import com.runtips.ricardo.runtipsmx.R;

public class RunActivity extends AppCompatActivity {

    private Chronometer chronometer;
    private AlternativeChronometer chrono;
    private Button btnStart;
    private Button btnStop;
    private Button btnPause;
    private int tabPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        ActionBar ab = getSupportActionBar();
        if(ab != null){
            ab.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle == null) {
            tabPosition = 0;
        } else {
            tabPosition = bundle.getInt("position");
        }

        chronometer = findViewById(R.id.chronometerRun);
        chrono = findViewById(R.id.altchronometerRun);

        chronometer.setFormat("Time: %s");

        btnStart = findViewById(R.id.btnRunStart);
        btnStop = findViewById(R.id.btnRunStop);
        btnPause = findViewById(R.id.btnRunPause);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chrono.start();
                chronometer.start();
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chrono.setBase(SystemClock.elapsedRealtime());
                chronometer.setBase(SystemClock.elapsedRealtime());
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chrono.stop();
                chronometer.stop();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RunActivity.this, StartActivity.class);
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
                intent.putExtra("position", tabPosition);
                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
