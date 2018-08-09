package com.runtips.ricardo.runtipsmx.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.runtips.ricardo.runtipsmx.Classes.Session;
import com.runtips.ricardo.runtipsmx.R;

public class MainActivity extends AppCompatActivity{

    private SharedPreferences prefs;

    private EditText editTextUser;
    private EditText editTextPass;
    private CheckBox checkboxRemember;
    private TextView txtRegister;
    private Button btnIngresar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        btnIngresar = findViewById(R.id.btnLogin);
        txtRegister = findViewById(R.id.txtLoginRegistro);
        editTextUser = findViewById(R.id.editLoginUser);
        editTextPass = findViewById(R.id.editLoginPassword);
        checkboxRemember = findViewById(R.id.checkLoginRemember);

        getCredentials();

        txtRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });



        btnIngresar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String mail = editTextUser.getText().toString();
                String pass = editTextPass.getText().toString();

                if(checkboxRemember.isChecked())
                    Session.saveSharedPreferences(prefs, mail, pass);
                    //saveOnPreferences(mail, pass);
                if(validLogin(mail, pass)){
                    openStartActivity();
                }
            }
        });
    }

    /*private void saveOnPreferences(String mail, String password){
        if(checkboxRemember.isChecked()){
            Session.saveSharedPreferences(prefs, mail, password);
            /*SharedPreferences.Editor editor = prefs.edit();
            editor.putString("email", mail);
            editor.putString("pass", password);

            editor.apply();
        }
    }*/

    private boolean isValidMail(String email){
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.equals("test@test.com");

    }

    private boolean isValidPassword(String password){
        return password.length() > 1 && password.equals("test");
    }

    private boolean validLogin(String mail, String password){
        if(!isValidMail(mail)){
            Toast.makeText(this, R.string.msgMailNotValid, Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!isValidPassword(password)){
            Toast.makeText(this,R.string.msgPasswordNotValid, Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }

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

    /*private String getUserMailPrefs(){
        return prefs.getString("email", "");
    }

    private String getUserPassPrefs(){
        return prefs.getString("pass", "");
    }*/
}
