package com.ajemian.resumegod;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by Kudo on 10/9/16.
 */

public class ViewJobSeekersActivity extends AppCompatActivity implements OnRequestListener, AdapterView.OnItemClickListener, View.OnClickListener{

    private ListView contentListView;

    private ResumeSingleton singleton = ResumeSingleton.getInstance();

    private Button seeMyFavoritesButton;
    private Button logoutButton;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_job_seekers);

        contentListView = (ListView) findViewById(R.id.contentListView);
        seeMyFavoritesButton = (Button) findViewById(R.id.seeFavorites);
        logoutButton = (Button) findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(this);

        seeMyFavoritesButton.setOnClickListener(this);

        contentListView.setOnItemClickListener(this);

        singleton.getAPI().getAllJobseekers(this);
    }
    @Override
    public void onBackPressed(){

    }

    @Override
    public void onResponseReceived(RequestType type, Response response) {

    }

    public void loadProfiles(Response[] profiles){
        JobseekerResponse[] pr = (JobseekerResponse[]) profiles;
        Log.d("ResumeGod", Integer.toString(pr.length));

        JobseekerItemAdapter adapter = new JobseekerItemAdapter(this, pr);
        contentListView.setAdapter(adapter);

    }

    @Override
    public void onResponsesReceived(RequestType type, int resCode, final Response[] responses) {
        if(resCode == 200){
            ViewJobSeekersActivity.this.runOnUiThread(new Runnable(){
                public void run(){
                    loadProfiles(responses);
                }
            });
        }else{
            //display nothing
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent seeProfile = new Intent(this, ViewProfileActivity.class);
        JobseekerItemAdapter adapter = (JobseekerItemAdapter) adapterView.getAdapter();
        JobseekerResponse profile = (JobseekerResponse) adapter.getItem(i);
        seeProfile.putExtra("email", profile.getEmail());
        startActivity(seeProfile);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.seeFavorites){
            Intent seeFavoritesIntent = new Intent(this, EmployersFavoriteActivity.class);
            startActivity(seeFavoritesIntent);
        }else if (view.getId() == R.id.logoutButton){
            Intent logoutIntent = new Intent(this, LoginActivity.class);
            singleton.setEmail("");
            startActivity(logoutIntent);
        }

    }
}
