package com.runtips.ricardo.runtipsmx;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class StartActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences prefs;
    private TextView txtUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        FloatingActionButton roundedButton = (FloatingActionButton) findViewById(R.id.fab);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Button buttonDia1 = (Button) findViewById(R.id.buttonDia1);



        roundedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, ContactActivity.class);
                startActivity(intent);
            }
        });


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        //View headerView = navigationView.getHeaderView(0);
        //TextView navText = (TextView) findViewById(R.id.txtMenuUserName);
        //navText.setText("Your text here");

        buttonDia1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(StartActivity.this, "Dia 1", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.navToday) {
            Toast.makeText(StartActivity.this, R.string.btnStartToday, Toast.LENGTH_SHORT).show();
        } else if (id == R.id.navPaquetes) {
            Toast.makeText(StartActivity.this, R.string.menuPaquetesEntrenamiento, Toast.LENGTH_SHORT).show();
        } else if (id == R.id.navDatosUsuario) {
            Toast.makeText(StartActivity.this, R.string.menuDatos, Toast.LENGTH_SHORT).show();
        } else if (id == R.id.navContacto) {
            Toast.makeText(StartActivity.this, R.string.menuContacto, Toast.LENGTH_SHORT).show();
            logOut();
        } else if (id == R.id.navCerrarSesion) {
            Toast.makeText(StartActivity.this, R.string.menuCerrarSesion, Toast.LENGTH_SHORT).show();
            clearPrefs();
            logOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logOut(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void clearPrefs(){
        prefs.edit().clear().apply();
    }

    /*private void getUsername(){
        String user = getUserUserPrefs();
        if(!TextUtils.isEmpty(user))
            txtUsuario.setText("HolaHola");
    }*/

    /*private String getUserUserPrefs(){
        return prefs.getString("email", "");
    }*/

}
