package com.ajemian.resumegod;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by Kudo on 10/8/16.
 */

public class DesignationActivity extends AppCompatActivity implements View.OnClickListener, OnRequestListener {

    private Button jobSeekerButton;
    private Button employerButton;

    private ResumeSingleton resumeSingleton = ResumeSingleton.getInstance();

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designation);

        jobSeekerButton = (Button) findViewById(R.id.jobSeekerButton);
        employerButton = (Button) findViewById(R.id.employerButton);
        jobSeekerButton.setVisibility(View.INVISIBLE);
        employerButton.setVisibility(View.INVISIBLE);
        jobSeekerButton.setOnClickListener(this);
        employerButton.setOnClickListener(this);

        resumeSingleton.getAPI().auth(this, resumeSingleton.getEmail());
    }

    @Override
    public void onBackPressed(){

    }




    @Override
    public void onClick(View v){
        if (v.getId() == R.id.jobSeekerButton){
            Intent createJobSeekerIntent = new Intent(this, CreateJobSeekerActivity.class);
            startActivity(createJobSeekerIntent);
        }else if (v.getId() == R.id.employerButton){
            Intent employerIntent = new Intent(this, CreateEmployerActivity.class);
            startActivity(employerIntent);
        }
    }

    @Override
    public void onResponseReceived(RequestType type, Response response) {
        Log.d("ResumeGod", Integer.toString(response.getResponseCode()));

        if(type == RequestType.Auth){
            if(response.getResponseCode() == 200){
                BoolMessageTokenDesignationResponse castedResponse = (BoolMessageTokenDesignationResponse) response;
                resumeSingleton.getAPI().setToken(castedResponse.getToken());
                if(castedResponse.getDesignation() == 1){
                    //jobseeker
                    Intent viewProfileIntent = new Intent(this, ViewProfileActivity.class);
                    startActivity(viewProfileIntent);
                }else if (castedResponse.getDesignation() == 2){
                    //employer
                    Intent getLocationIntent = new Intent(this, TakeLocationActivity.class);
                    getLocationIntent.putExtra("login", "true");
                    startActivity(getLocationIntent);
                }
            }else if (response.getResponseCode() == 418){
                DesignationActivity.this.runOnUiThread(new Runnable(){
                    public void run(){
                        jobSeekerButton.setVisibility(View.VISIBLE);
                        employerButton.setVisibility(View.VISIBLE);
                    }
                });
            }else{
                //probably just go home.
            }
        }
    }

    @Override
    public void onResponsesReceived(RequestType type, int resCode, Response[] responses) {

    }
}
