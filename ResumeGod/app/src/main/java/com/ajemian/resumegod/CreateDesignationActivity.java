package com.ajemian.resumegod;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Kudo on 10/9/16.
 */

public class CreateDesignationActivity extends AppCompatActivity implements OnRequestListener {

    private ResumeSingleton resumeSingleton = ResumeSingleton.getInstance();

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d("Resume God", "We're here");
        setContentView(R.layout.activity_create_designation);

        resumeSingleton.getAPI().createDesignation(this, resumeSingleton.getEmail(), resumeSingleton.getDesignation());



    }

    protected void onStart(){
        super.onStart();


    }

    @Override
    public void onBackPressed(){

    }
    @Override
    public void onResponseReceived(RequestType type, Response response) {

        if(type == RequestType.CreateDesignation){
            if(response.getResponseCode() == 200){
                resumeSingleton.getAPI().auth(this, resumeSingleton.getEmail());
            }
        }else if (type == RequestType.Auth){
            if (response.getResponseCode() == 200){
                BoolMessageTokenDesignationResponse r = (BoolMessageTokenDesignationResponse) response;
                resumeSingleton.getAPI().setToken(r.getToken());
                if(resumeSingleton.getDesignation() == 1){
                    resumeSingleton.getJobseekerResponse().setLocation_lat(resumeSingleton.getLastLocation().getLatitude());
                    resumeSingleton.getJobseekerResponse().setLocation_long(resumeSingleton.getLastLocation().getLongitude());
                    resumeSingleton.getAPI().createJobseekerProfile(this, resumeSingleton.getJobseekerResponse());
                }else if (resumeSingleton.getDesignation() == 2){
                    resumeSingleton.getAPI().createEmployerProfile(this, resumeSingleton.getEmployerResponse());
                }
            }
        }

        if(type == RequestType.CreateEmployerProfile){
            if(response.getResponseCode() == 200){
                Intent viewJobSeekersIntent = new Intent(this, ViewJobSeekersActivity.class);
                startActivity(viewJobSeekersIntent);
            }else{
                Log.d("Resume God", "Something went wrong");
            }
        }else if (type == RequestType.CreateJobSeekerProfile){
            if(response.getResponseCode() == 200){
                Intent viewProfileActivity = new Intent(this, ViewProfileActivity.class);
                startActivity(viewProfileActivity);

            }else{
                Log.d("Resume God", "Something went wrong");
            }
        }
    }

    @Override
    public void onResponsesReceived(RequestType type, int resCode, Response[] responses) {

    }
}
