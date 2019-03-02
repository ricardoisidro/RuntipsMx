package com.runtips.ricardo.runtipsmx.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.runtips.ricardo.runtipsmx.activities.RegisterActivity;
import com.runtips.ricardo.runtipsmx.activities.StartActivity;
import com.runtips.ricardo.runtipsmx.api.API;
import com.runtips.ricardo.runtipsmx.api.apiservices.RuntipsmxService;
import com.runtips.ricardo.runtipsmx.classes.Session;
import com.runtips.ricardo.runtipsmx.R;
import com.runtips.ricardo.runtipsmx.models.LoginResponse;
import com.runtips.ricardo.runtipsmx.models.PostLogin;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{

    private SharedPreferences prefs;

    private EditText editTextUser;
    private EditText editTextPass;
    private CheckBox checkboxRemember;
    private TextView txtRegister;
    private Button btnIngresar;
    private RuntipsmxService runtipsmxLoginService;
    private Call<LoginResponse> userResponseCall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.SplashTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        btnIngresar = findViewById(R.id.btnLogin);
        txtRegister = findViewById(R.id.txtLoginRegistro);
        editTextUser = findViewById(R.id.editLoginUser);
        editTextPass = findViewById(R.id.editLoginPassword);
        checkboxRemember = findViewById(R.id.checkLoginRemember);

        getCredentials();

        SpannableString ss = new SpannableString(getResources().getText(R.string.txtLoginRegistro));

        ClickableSpan cs1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        };

        ss.setSpan(cs1, 13, 23, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtRegister.setText(ss);
        txtRegister.setMovementMethod(LinkMovementMethod.getInstance());

        btnIngresar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String mail = editTextUser.getText().toString();
                String pass = editTextPass.getText().toString();



                validLogin(mail, pass);
            }
        });

    }

    private boolean isValidMail(String email){
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();

    }

    private boolean isValidPassword(String password){
        return password.length() > 1;
    }

    private void validLogin(String mail, final String password){
        if(!isValidMail(mail)){
            Toast.makeText(this, R.string.msgMailNotValid, Toast.LENGTH_SHORT).show();
            //return false;
        }
        else if (!isValidPassword(password)){
            Toast.makeText(this,R.string.msgPasswordNotValid, Toast.LENGTH_SHORT).show();
            //return false;
        }
        else{
            //openStartActivity();

            runtipsmxLoginService = API.getApi().create(RuntipsmxService.class);
            final String correo = editTextUser.getText().toString();
            final String pwd = editTextPass.getText().toString();
            PostLogin postLogin = new PostLogin(correo, pwd);

            userResponseCall = runtipsmxLoginService.createLogin(postLogin);
            userResponseCall.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        switch(response.code()) {
                            case 200: case 201:
                                LoginResponse loginResponse = response.body();
                                //Toast.makeText(MainActivity.this, String.valueOf(response.body().getData().getToken()) + String .valueOf(response.body().getData().getUser()), Toast.LENGTH_LONG).show();
                                //Toast.makeText(MainActivity.this, loginResponse.getData().getToken() + loginResponse.getData().getUser().getName() + loginResponse.getData().getUser()., Toast.LENGTH_LONG).show();
                                if(checkboxRemember.isChecked()) {
                                    Session.saveSharedPreferences(prefs, loginResponse.getData().getUser().getEmail(), password, loginResponse.getData().getUser().getName());
                                }
                                else {
                                    Session.saveSharedPreferences(prefs, "", "", loginResponse.getData().getUser().getName());
                                }
                                openStartActivity();

                                break;
                                default:
                                    Toast.makeText(MainActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                        }

                    }
                    else {
                        switch (response.code()) {
                            case 400:
                                Toast.makeText(MainActivity.this, "bad request", Toast.LENGTH_SHORT).show();
                                break;
                            case 401:
                                try{
                                    Toast.makeText(MainActivity.this, response.errorBody().string(), Toast.LENGTH_LONG).show();
                                }
                                catch (IOException ioe) {
                                    Toast.makeText(MainActivity.this, "Login o password erróneo", Toast.LENGTH_LONG).show();
                                }
                                break;
                            case 404:
                                Toast.makeText(MainActivity.this, "not found", Toast.LENGTH_SHORT).show();
                                break;
                            case 500:
                                Toast.makeText(MainActivity.this, "server broken", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(MainActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "onFailure: Problema en login", Toast.LENGTH_LONG).show();

                }
            });
        }
    }

    /**
     * getCredentials()
     * Check if there are stored values (mail and password) in the sharedpreferences, and put
     * these values on their textboxes.
     */
    private void getCredentials(){
        String email = Session.getUserMailPrefs(prefs);
        String password = Session.getUserPassPrefs(prefs);
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            editTextUser.setText(email);
            editTextPass.setText(password);
            checkboxRemember.setChecked(true);
            openStartActivity();
        }

    }

    private void openStartActivity(){
        Intent intent = new Intent(MainActivity.this, StartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
