package com.runtips.ricardo.runtipsmx.activities;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.runtips.ricardo.runtipsmx.activities.SpeedActivity;

import java.util.concurrent.TimeUnit;

public class LocationService extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener
{

    private static final long INTERVAL = 1000*2;
    private static final long FASTEST_INTERVAL = 1000;

    LocationRequest locationRequest;
    Location currentLocation, locationStart, locationEnd;
    GoogleApiClient googleApiClient;
    static double distance = 0;

    private final IBinder binder = new LocalBinder();

    private FusedLocationProviderClient myFusedLocationProviderClient;
    private LocationCallback mLocationCallback;

//    mLocationCallback = new LocationCallback() {
//        @Override
//        public void onLocationResult(LocationResult locationResult) {
//            super.onLocationResult(locationResult);
//            }
//    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        createLocationRequest();
        googleApiClient = new GoogleApiClient.Builder(this).
                addApi(LocationServices.API).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).build();
        googleApiClient.connect();
        return binder;
    }

    private void createLocationRequest(){
        locationRequest = new LocationRequest();
        locationRequest.setInterval(INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        try{

            myFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            myFusedLocationProviderClient.getLastLocation();


        }
        catch (SecurityException se){

        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //TODO following Youtube tutorial https://www.youtube.com/watch?v=1vHlLWtjJAw 11:25
    }

    @Override
    public void onLocationChanged(Location location) {
        com.runtips.ricardo.runtipsmx.activities.SpeedActivity.progressDialog.dismiss();

        currentLocation = location;
        if(locationStart == null){
            locationStart = locationEnd = currentLocation;
        }
        else {
            locationEnd = currentLocation;
        }
        updateUI();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public boolean onUnbind(Intent intent){
        stopLocationUpdates();
        if(googleApiClient.isConnected()){
            googleApiClient.disconnect();
        }
        locationStart = locationEnd = null;
        distance = 0;
        return super.onUnbind(intent);
    }

    private void stopLocationUpdates() {

        //myFusedLocationProviderClient.removeLocationUpdates(locationCallback);
        distance = 0;

    }

    public class LocalBinder extends Binder {
        public LocationService getService(){
            return LocationService.this;
        }
    }

    private void updateUI() {
        if(com.runtips.ricardo.runtipsmx.activities.SpeedActivity.p == 0) {
            distance = distance + (locationStart.distanceTo(locationEnd)/1000.00);
            com.runtips.ricardo.runtipsmx.activities.SpeedActivity.endTime = System.currentTimeMillis();
            long diff = com.runtips.ricardo.runtipsmx.activities.SpeedActivity.endTime - SpeedActivity.startTime;
            diff = TimeUnit.MILLISECONDS.toMinutes(diff);

            //SpeedActivity.time.setText("Total time: " + diff + " minutes");
            Toast.makeText(this, "Total time: " + diff + " minutes", Toast.LENGTH_LONG).show();

            //SpeedActivity.distance.setText(new DecimalFormat("#.###").format(distance)+" km's");
            Toast.makeText(this, "Total time: " + diff + " minutes", Toast.LENGTH_LONG).show();


            locationStart = locationEnd;
        }

    }


}
