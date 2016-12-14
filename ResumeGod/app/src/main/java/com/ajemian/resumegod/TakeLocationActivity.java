package com.ajemian.resumegod;

import android.*;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Kudo on 10/9/16.
 */

public class TakeLocationActivity extends AppCompatActivity implements LocationListener, View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private Button submitButton;

    private GoogleApiClient mGoogleApiClient;

    private ResumeSingleton resumeSingleton = ResumeSingleton.getInstance();

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_location);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }


        submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setVisibility(View.INVISIBLE);

        submitButton.setOnClickListener(this);
        verifyLocationPermissions(this);
    }
    protected void onStart(){
        super.onStart();

    }

    public void verifyLocationPermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION);
        Log.d("Resume God", "Checking Permissions");

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1
            );
        }else{
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Log.d("ResumeGod", "You're set to go!");

                    mGoogleApiClient.connect();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    verifyLocationPermissions(this);
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        submitButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.submitButton){
            try {
                Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(
                        mGoogleApiClient);
                if (lastLocation != null) {
                    Log.d("Resume God", "Last location found");
                    resumeSingleton.setLastLocation(lastLocation);
                    transition();
                }else{
                    Log.d("Resume God", "No last location found.. registering");
                    LocationRequest request = LocationRequest.create();
                    request.setInterval(1000);
                    request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    request.setNumUpdates(1);
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, request, this);
                }
            }catch(SecurityException e){
                Log.d("Resume God", "Security error");
            }
        }
    }
    public void transition(){
        if(getIntent().hasExtra("login")){
            Log.d("ResumeGod", "Already logged in");
            Intent jobseekersIntent = new Intent(this, ViewJobSeekersActivity.class);
            startActivity(jobseekersIntent);
        }else {
            Intent createDesignationIntent = new Intent(this, CreateDesignationActivity.class);
            startActivity(createDesignationIntent);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("Resume God", "Found a location");
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        resumeSingleton.setLastLocation(location);

        transition();


    }
}
