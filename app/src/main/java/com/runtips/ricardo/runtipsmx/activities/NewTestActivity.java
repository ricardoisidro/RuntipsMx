package com.runtips.ricardo.runtipsmx.activities;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.runtips.ricardo.runtipsmx.R;
import com.runtips.ricardo.runtipsmx.models.Test;

import java.util.Arrays;
import java.util.List;

import io.realm.Realm;

public class NewTestActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txtWeight;
    private EditText txtPulse;
    private EditText txtTime;

    private Button btnSave;

    private TimePickerDialog.OnTimeSetListener timeSetListener;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_test);

        realm = Realm.getDefaultInstance();

        txtWeight = findViewById(R.id.txtHeartRateWeight);
        txtPulse = findViewById(R.id.txtHeartRatePulse);
        txtTime = findViewById(R.id.txtHeartRateTime);
        btnSave = findViewById(R.id.btnHeartRateSave);

        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        String userPulse = txtPulse.getText().toString();
        int pulse = Integer.parseInt(userPulse);
        String userWeight = txtWeight.getText().toString();
        float weight = Float.parseFloat(userWeight);
        String userTime = txtTime.getText().toString();
        List<String> time = Arrays.asList(userTime.split("\\s*:\\s*"));

        int minutes = Integer.parseInt(time.get(0));
        int seconds = Integer.parseInt(time.get(1));

        int totalNumberOfSecs = minutes*60*1000 + seconds*1000;

        if (userPulse.length() > 0) {
            if(userWeight.length() > 0) {
                if (totalNumberOfSecs > 0) {

                    createNewTest(totalNumberOfSecs, pulse, weight);

                    Intent intent = new Intent(NewTestActivity.this, StartActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(NewTestActivity.this, getResources().getText(R.string.txtHeartRateIncorrectTime) + ".", Toast.LENGTH_LONG).show();
                }
            }
        }
        else {
            Toast.makeText(NewTestActivity.this, getResources().getText(R.string.txtHeartRateIncorrectMeasure), Toast.LENGTH_LONG).show();
        }

    }

    /** CRUD Actions **/
    private void createNewTest(int time, int pulse, float weight) {

        realm.executeTransaction(realm -> {
            Test test2 = new Test(time, pulse, weight);
            realm.copyToRealm(test2);
        });

    }
}
