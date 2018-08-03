package com.runtips.ricardo.runtipsmx.Activities;

import java.util.Calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.runtips.ricardo.runtipsmx.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;


public class RegisterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private EditText editTextName;
    private EditText editTextSurname;
    private EditText editTextBirthday;
    private DatePickerDialog dpd;
    private RadioGroup radioGroupGender;
    private MaterialBetterSpinner spinnerState;
    private EditText editTextPhone;
    private EditText editTextMail;
    private EditText editTextPassword;
    private EditText editTextPassword2;
    private TextView txtConditions;
    private CheckBox checkConditions;
    private Button btnCancel;
    private Button btnOK;

    public RegisterActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextName = findViewById(R.id.txtRegisterName);
        editTextSurname = findViewById(R.id.txtRegisterSurname);
        editTextBirthday = findViewById(R.id.txtRegisterBirth);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.spinnerRegisterStates));
        MaterialBetterSpinner materialDesignSpinner = (MaterialBetterSpinner)
                findViewById(R.id.spinnerRegisterState);
        materialDesignSpinner.setAdapter(arrayAdapter);

        editTextBirthday.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String date_value = "";
                if(editTextBirthday.hasFocus()){
                    try{
                        if(editTextBirthday.getText().toString().isEmpty()) {
                            Calendar now = Calendar.getInstance();
                            dpd = DatePickerDialog.newInstance(
                                    RegisterActivity.this,
                                    now.get(Calendar.YEAR),
                                    now.get(Calendar.MONTH),
                                    now.get(Calendar.DAY_OF_MONTH)
                            );
                        }
                        else{
                            date_value = editTextBirthday.getText().toString();
                            String[] data = date_value.split("/");
                            dpd = DatePickerDialog.newInstance(
                                    RegisterActivity.this,
                                    Integer.parseInt(data[2]),
                                    Integer.parseInt(data[1])-1,
                                    Integer.parseInt(data[0])
                            );
                        }
                        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
                        dpd.show(getFragmentManager(), "Datepickerdialog");
                    }
                    catch(Exception ex){

                    }
                }
            }
        });

        checkConditions = findViewById(R.id.checkLoginRemember);
        txtConditions = findViewById(R.id.txtRegisterConditions);

        txtConditions.setText(Html.fromHtml(getResources().getText(R.string.txtRegisterConditions) + " <a href='https://www.google.com.mx'>Tap aquí</a>"));
        txtConditions.setClickable(true);
        txtConditions.setMovementMethod(LinkMovementMethod.getInstance());

        btnOK = findViewById(R.id.btnRegisterOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, CameraHeartRateActivity.class);
                startActivity(intent);

            }
        });

        btnCancel = findViewById(R.id.btnRegisterCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int month, int dayOfMonth){
        int realMonth = month + 1;
        String longMonth, longDay;
        String date = "";
        if(String.valueOf(realMonth).length() == 1)
            longMonth = "0"+String.valueOf(realMonth);
        else
            longMonth=String.valueOf(realMonth);
        if(String.valueOf(dayOfMonth).length() == 1)
            longDay = "0"+String.valueOf(dayOfMonth);
        else
            longDay = String.valueOf(dayOfMonth);
        date = longDay+"/"+longMonth+"/"+year;
        editTextBirthday.setText(date);
    }

    @Override
    public void onResume(){
        super.onResume();
        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag("Datepickerdialog");
    }
}
