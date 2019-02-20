package com.runtips.ricardo.runtipsmx.activities;

import java.io.IOException;
import java.util.Calendar;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.runtips.ricardo.runtipsmx.activities.MainActivity;
import com.runtips.ricardo.runtipsmx.activities.TermsConditions;
import com.runtips.ricardo.runtipsmx.activities.VideoActivity;
import com.runtips.ricardo.runtipsmx.api.API;
import com.runtips.ricardo.runtipsmx.api.apiservices.RuntipsmxService;
import com.runtips.ricardo.runtipsmx.classes.Session;
import com.runtips.ricardo.runtipsmx.models.PostRegister;
import com.runtips.ricardo.runtipsmx.models.UserRegister;
import com.runtips.ricardo.runtipsmx.models.UserRegisterResponse;
import com.runtips.ricardo.runtipsmx.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextSurname;
    private EditText editTextBirthday;

    private DatePickerDialog.OnDateSetListener dateSetListener;
    private ImageButton btnCalendar;

    private RadioGroup radioGroupGender;
    private EditText editTextPhone;
    private EditText editTextMail;
    private EditText editTextPassword;
    private EditText editTextPassword2;
    private TextView txtConditions;
    private CheckBox checkConditions;
    private Button btnCancel;
    private Button btnOK;

    private Call<UserRegisterResponse> userResponseCall;
    private RuntipsmxService runtipsmxService;

    private SharedPreferences prefs;

    public RegisterActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},0);
        }


        editTextName = findViewById(R.id.txtRegisterName);
        editTextSurname = findViewById(R.id.txtRegisterSurname);

        editTextBirthday = findViewById(R.id.textRegisterBirth);
        btnCalendar = findViewById(R.id.btnRegisterCalendar);

        radioGroupGender = findViewById(R.id.radioGroupRegisterGender);
        editTextPhone = findViewById(R.id.txtRegisterPhone);
        editTextMail = findViewById(R.id.txtRegisterMail);
        editTextPassword = findViewById(R.id.txtRegisterPassword);
        editTextPassword2 = findViewById(R.id.txtRegisterConfirmPassword);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.spinnerRegisterStates));
        MaterialBetterSpinner materialDesignSpinner = findViewById(R.id.spinnerRegisterState);
        materialDesignSpinner.setAdapter(arrayAdapter);

        editTextBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDateDialog();
            }
        });

        btnCalendar.setOnClickListener(view -> {

            openDateDialog();

        });

        dateSetListener = (datePicker, year, month, dayOfMonth) -> {

            int realMonth = month + 1;
            String longMonth, longDay;
            if(String.valueOf(realMonth).length() == 1)
                longMonth = "0"+String.valueOf(realMonth);
            else
                longMonth=String.valueOf(realMonth);
            if(String.valueOf(dayOfMonth).length() == 1)
                longDay = "0"+String.valueOf(dayOfMonth);
            else
                longDay = String.valueOf(dayOfMonth);
            String realDate = year+"-"+longMonth+"-"+longDay;
            editTextBirthday.setText(realDate);
        };

        txtConditions = findViewById(R.id.txtRegisterConditions);
        checkConditions = findViewById(R.id.checkRegisterConditions);

        SpannableString ss = new SpannableString(getResources().getText(R.string.txtRegisterConditions));

        ClickableSpan cs1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                startActivity(new Intent(RegisterActivity.this, TermsConditions.class));
            }
        };
        ss.setSpan(cs1, 7,29, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtConditions.setText(ss);
        txtConditions.setMovementMethod(LinkMovementMethod.getInstance());

        btnOK = findViewById(R.id.btnRegisterOK);

        btnOK.setOnClickListener(new View.OnClickListener() {
            /*@Override
            public void onClick(View view) {
                if (checkConditions.isChecked()) {

                    runtipsmxService = API.getApi().create(RuntipsmxService.class);
                    String sex = "";
                    final String mail = editTextMail.getText().toString();
                    String celphone = editTextPhone.getText().toString();
                    final String name = editTextName.getText().toString();
                    String surname = editTextSurname.getText().toString();
                    String birth = editTextBirthday.getText().toString();
                    final String pass = editTextPassword.getText().toString();
                    final String pass2 = editTextPassword2.getText().toString();
                    int opc = radioGroupGender.getCheckedRadioButtonId();
                    switch (opc) {
                        case R.id.radioRegisterMan:
                            sex = "M";
                            break;
                        case R.id.radioRegisterWoman:
                            sex = "F";
                            break;
                    }

                    UserRegister user = new UserRegister(mail, celphone, name, surname, 70, birth, sex, pass, pass2);
                    PostRegister postRegister = new PostRegister();
                    postRegister.setUser(user);

                    userResponseCall = runtipsmxService.createUser(postRegister);
                    userResponseCall.enqueue(new Callback<UserRegisterResponse>() {
                        @Override
                        public void onResponse(Call<UserRegisterResponse> call, Response<UserRegisterResponse> response) {
                            if (response.isSuccessful()) {
                                switch (response.code()) {
                                    case 200:
                                    case 201:
                                        UserRegisterResponse userRegisterResponse = response.body();
                                        Toast.makeText(RegisterActivity.this, String.valueOf(userRegisterResponse.getDataRegisterResponse().getId()) + ", bienvenid@ " +
                                                userRegisterResponse.getDataRegisterResponse().getName(), Toast.LENGTH_LONG).show();
                                        Session.saveSharedPreferences(prefs, mail, pass, name);
                                        Intent intent = new Intent(RegisterActivity.this, Presentacion01Activity.class);
                                        startActivity(intent);
                                        break;
                                    default:
                                        Toast.makeText(RegisterActivity.this, "error, but response ok", Toast.LENGTH_SHORT).show();
                                        break;
                                }

                            } else {
                                switch (response.code()) {
                                    case 400:
                                        Toast.makeText(RegisterActivity.this, "bad request", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 422:
                                        try {
                                            Toast.makeText(RegisterActivity.this, response.errorBody().string(), Toast.LENGTH_LONG).show();
                                        } catch (IOException ioe) {
                                            Toast.makeText(RegisterActivity.this, getResources().getString(R.string.msgRegisterUnlegibleError), Toast.LENGTH_LONG).show();
                                        }
                                        break;
                                    case 500:
                                        Toast.makeText(RegisterActivity.this, "server broken", Toast.LENGTH_SHORT).show();
                                        break;

                                    default:
                                        Toast.makeText(RegisterActivity.this, "server returned error", Toast.LENGTH_SHORT).show();
                                        break;
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<UserRegisterResponse> call, Throwable t) {
                            Toast.makeText(RegisterActivity.this, "server or internet failure", Toast.LENGTH_LONG).show();

                        }
                    });
                    //postRegister = null;
                    //user = null;
                }

                else {
                Toast.makeText(RegisterActivity.this, getResources().getString(R.string.msgRegisterAcceptTerms),Toast.LENGTH_LONG).show();
            }
            }*/

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, VideoActivity.class);
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

    private void openDateDialog(){

        try{
            String date_value = editTextBirthday.getText().toString();
            String[] data = date_value.split("-");
            DatePickerDialog dialog = new DatePickerDialog(RegisterActivity.this,
                    android.R.style.Theme_Holo_Dialog_MinWidth,
                    dateSetListener,
                    Integer.parseInt(data[0]),
                    Integer.parseInt(data[1])-1,
                    Integer.parseInt(data[2]));
            dialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
        catch (Exception ex){
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(RegisterActivity.this,
                    android.R.style.Theme_Holo_Dialog_MinWidth,
                    dateSetListener,
                    year,
                    month,
                    day);
            dialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
    }
}
