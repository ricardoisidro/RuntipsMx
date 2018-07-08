package com.runtips.ricardo.runtipsmx;

import java.util.Calendar;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

public class RegisterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private EditText editTextName;
    private EditText editTextSurname;
    private EditText editTextBirthday;
    private DatePickerDialog dpd;


    public RegisterActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextName = findViewById(R.id.txtRegisterName);
        editTextSurname = findViewById(R.id.txtRegisterSurname);
        editTextBirthday = findViewById(R.id.txtRegisterBirth);

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
                        dpd.setVersion(DatePickerDialog.Version.VERSION_1);
                        dpd.show(getFragmentManager(), "Datepickerdialog");
                    }
                    catch(Exception ex){

                    }
                }
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
