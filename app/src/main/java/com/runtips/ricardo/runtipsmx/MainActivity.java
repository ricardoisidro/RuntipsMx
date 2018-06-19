package com.runtips.ricardo.runtipsmx;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;

public class MainActivity extends AppCompatActivity{

    private EditText editTextUser;
    private EditText editTextPass;
    private CheckBox checkboxRemember;
    private TextView txtRegister;
    private Button btnIngresar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        btnIngresar = findViewById(R.id.btnLogin);
        txtRegister = findViewById(R.id.txtLoginRegistro);
        editTextUser = findViewById(R.id.editLoginUser);
        editTextPass = findViewById(R.id.editLoginPassword);

        txtRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, Presentacion01Activity.class);
                startActivity(intent);
                //finish();
            }
        });

        btnIngresar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(validLogin(editTextUser.getText().toString(), editTextPass.getText().toString()))
                    Toast.makeText(MainActivity.this, "Iniciar+", Toast.LENGTH_LONG).show();
                //Intent intent = new Intent
            }
        });
    }

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
}
