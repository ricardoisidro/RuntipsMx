package com.runtips.ricardo.runtipsmx;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Button btn = findViewById(R.id.btnLogin);
        TextView txtRegister = findViewById(R.id.txtLoginRegistro);

        txtRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(MainActivity.this, "Registro+", Toast.LENGTH_LONG).show();
            }
        });

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(MainActivity.this, "Iniciar+", Toast.LENGTH_LONG).show();
            }
        });

        }

}
