package com.runtips.ricardo.runtipsmx.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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
import android.widget.TextView;
import android.widget.Toast;

import com.runtips.ricardo.runtipsmx.Classes.PagerAdapter;
import com.runtips.ricardo.runtipsmx.Classes.Session;
import com.runtips.ricardo.runtipsmx.R;

public class StartActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences prefs;
    private TextView txtUsuario;
    private int tabPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        openLastTab();
        //getSupportFragmentManager().beginTransaction()
          //      .add()

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.txtTab01));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.txtTab02));
        //tabLayout.setTabGravity(TabLayout.GR);

        final ViewPager viewPager = findViewById(R.id.pager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //viewPager.setCurrentItem(tabPosition);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        setSupportActionBar(toolbar);
        FloatingActionButton roundedButton = (FloatingActionButton) findViewById(R.id.fab);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        roundedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openContactActivity();
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        txtUsuario = headerView.findViewById(R.id.txtMenuUserName);

        getUsername();

        viewPager.setCurrentItem(tabPosition);

    }

    private void openLastTab() {
        Intent intent = getIntent();
        if(intent == null) {
            tabPosition = 0;
        } else {
            tabPosition = intent.getIntExtra("position", 0);
        }
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

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
          //  return true;
       // }

        return super.onOptionsItemSelected(item);
    }*/

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
            logOut();
        } else if (id == R.id.navContacto) {
            openContactActivity();
        } else if (id == R.id.navCerrarSesion) {
            Toast.makeText(StartActivity.this, R.string.menuCerrarSesion, Toast.LENGTH_SHORT).show();
            Session.removeSharedPreferences(prefs);
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

    private void openContactActivity(){
        Intent intent = new Intent(StartActivity.this, ContactActivity.class);
        startActivity(intent);
    }

    /*private void clearPrefs(){
        prefs.edit().clear().apply();
    }*/

    private void getUsername(){
        String user = Session.getUserMailPrefs(prefs);
        if(!TextUtils.isEmpty(user))
            txtUsuario.setText(user);
    }

    /*private String getUserUserPrefs(){
        return prefs.getString("email", "");
    }*/



}
