package com.runtips.ricardo.runtipsmx.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.runtips.ricardo.runtipsmx.R;

public class PlansActivity extends AppCompatActivity {

    private int tabPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        savePreviousTab();

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
}
