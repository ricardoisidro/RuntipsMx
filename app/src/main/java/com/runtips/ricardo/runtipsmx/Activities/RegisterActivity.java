package com.runtips.ricardo.runtipsmx.Activities;

import java.util.Calendar;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.runtips.ricardo.runtipsmx.API.API;
import com.runtips.ricardo.runtipsmx.API.APIServices.RuntipsmxService;
import com.runtips.ricardo.runtipsmx.Classes.Session;
import com.runtips.ricardo.runtipsmx.Models.Data;
import com.runtips.ricardo.runtipsmx.Models.Post;
import com.runtips.ricardo.runtipsmx.Models.UserResponse;
import com.runtips.ricardo.runtipsmx.R;
import com.runtips.ricardo.runtipsmx.Models.User;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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

    private Call<UserResponse> userResponseCall;
    private RuntipsmxService runtipsmxService;

    private SharedPreferences prefs;

    public RegisterActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        editTextName = findViewById(R.id.txtRegisterName);
        editTextSurname = findViewById(R.id.txtRegisterSurname);
        editTextBirthday = findViewById(R.id.txtRegisterBirth);
        radioGroupGender = findViewById(R.id.radioGroupRegisterGender);
        editTextPhone = findViewById(R.id.txtRegisterPhone);
        spinnerState = findViewById(R.id.spinnerRegisterState);
        editTextMail = findViewById(R.id.txtRegisterMail);
        editTextPassword = findViewById(R.id.txtRegisterPassword);
        editTextPassword2 = findViewById(R.id.txtRegisterConfirmPassword);

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

                runtipsmxService = API.getApi().create(RuntipsmxService.class);

                String sex = "";
                final String mail = editTextMail.getText().toString();
                String celphone = editTextPhone.getText().toString();
                final String name = editTextName.getText().toString();
                String surname = editTextSurname.getText().toString();
                String birth = editTextBirthday.getText().toString();
                final String pass = editTextPassword.getText().toString();
                int opc = radioGroupGender.getCheckedRadioButtonId();
                switch (opc){
                    case R.id.radioRegisterMan:
                        sex = "M";
                        break;
                    case R.id.radioRegisterWoman:
                        sex = "F";
                        break;
                }

                User user = new User(mail, celphone, name, surname, 70, birth, sex, pass, pass);
                Post post = new Post();
                post.setUser(user);

                userResponseCall = runtipsmxService.createUser(post);
                userResponseCall.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        if(response.isSuccessful()){
                            //TODO cachar correctamente la estructura de datos
                            UserResponse userResponse = response.body();
                            Toast.makeText(RegisterActivity.this, String.valueOf(userResponse.getData().getId()) + ", bienvenid@ " +
                                    userResponse.getData().getName().toString(), Toast.LENGTH_LONG).show();
                            Session.saveSharedPreferences(prefs, mail, pass, name);
                            Intent intent = new Intent(RegisterActivity.this, CameraHeartRateActivity.class);
                            startActivity(intent);
                        }

                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "Hubo un problema", Toast.LENGTH_LONG).show();

                    }
                });

                post = null;
                user = null;

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
        date = year+"-"+longMonth+"-"+longDay;
        editTextBirthday.setText(date);
    }

    @Override
    public void onResume(){
        super.onResume();
        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager()
                .findFragmentByTag("Datepickerdialog");
    }

    public void generarJson(int opcion, String mail, String celphone, String name, String surname,
                            String birth, String pass){

        String json;
        String sex = "";
        switch (opcion){
            case R.id.radioRegisterMan:
                sex = "M";
                break;
            case R.id.radioRegisterWoman:
                sex = "F";
                break;
        }
        json = "{" +

                        "email:" + "\"" + mail + "\", " +
                        "cel_phone:" + "\"" + celphone + "\", " +
                        "name:" + "\"" + name + "\", " +
                        "last_name:" + "\"" + surname + "\", " +
                        "weight:75, " +
                        "birth_date:" + "\"" + birth + "\", " +
                        "sex:" + "\"" + sex + "\", " +
                        "password:" + "\"" + pass + "\", " +
                        "password_confirmation:" + "\"" + pass + "\" " +
                "}";

        Gson gson = new GsonBuilder().create();
        User user = gson.fromJson(json, User.class);
        int a = 0;

    }
}
