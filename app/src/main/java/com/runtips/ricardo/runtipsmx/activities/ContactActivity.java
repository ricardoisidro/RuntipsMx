package com.runtips.ricardo.runtipsmx.activities;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.runtips.ricardo.runtipsmx.R;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

        Button btnWhatsapp = findViewById(R.id.btnContactWhatsapp);
        Button btnCorreo = findViewById(R.id.btnContactMail);
        Button btnFacebook = findViewById(R.id.btnContactFacebook);
        Button btnInstagram = findViewById(R.id.btnContactInstagram);

        btnWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(invokeWhatsapp()!= null){
                    startActivity(invokeWhatsapp());
                }
            }
        });

        btnCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                invokeMail();
            }
        });

        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(invokeFacebook());
            }
        });

        btnInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(invokeInstagram());
            }
        });
    }

    private void invokeMail(){

        String mail = getResources().getString(R.string.urlMail);

        Intent clienteCorreo = new Intent(Intent.ACTION_SEND, Uri.parse(mail));
        clienteCorreo.setType("plain/text");
        clienteCorreo.putExtra(Intent.EXTRA_SUBJECT, "Solicitud información desde app");
        clienteCorreo.putExtra(Intent.EXTRA_EMAIL, new String [] {mail});
        try{
            startActivity(Intent.createChooser(clienteCorreo, "Abrir con..."));
        }
        catch (ActivityNotFoundException e){
            Toast.makeText(ContactActivity.this, "Cliente correo no encontrado", Toast.LENGTH_SHORT).show();
        }
    }

    private Intent invokeFacebook(){
        String url = getResources().getString(R.string.urlFacebook);
        String fbId = getResources().getString(R.string.idFacebook);
        Uri uri = Uri.parse(url);
        try{
            this.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            //uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            uri = Uri.parse("fb://page/" + fbId);
            return new Intent(Intent.ACTION_VIEW, uri);
        }
        catch(Exception e){
            return new Intent(Intent.ACTION_VIEW, uri);

        }
    }

    public Intent invokeInstagram(){
        String url = getResources().getString(R.string.urlInstagram);
        Uri uri = Uri.parse(url);
        try{
            this.getPackageManager().getPackageInfo("com.instagram.android", 0);
            return new Intent(Intent.ACTION_VIEW, uri);
        }
        catch(Exception ex){
            return new Intent(Intent.ACTION_VIEW, uri);
        }
    }

    public Intent invokeWhatsapp(){
        String phoneNumber = getResources().getString(R.string.numberWhatsapp);
        try{
            Intent whatsappIntent = new Intent();
            whatsappIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
            whatsappIntent.setAction(Intent.ACTION_SEND);
            whatsappIntent.setType("text/plain");
            whatsappIntent.setPackage("com.whatsapp");
            whatsappIntent.putExtra("jid", phoneNumber +"@s.whatsapp.net");
            return whatsappIntent;
        }
        catch (Exception ex){
            Toast.makeText(this, "Se requiere WhatsApp instalado en el dispositivo" + ex.toString(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
