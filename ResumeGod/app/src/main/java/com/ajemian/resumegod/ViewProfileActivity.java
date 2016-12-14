package com.ajemian.resumegod;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;

import java.util.ArrayList;

/**
 * Created by Kudo on 10/8/16.
 */

public class ViewProfileActivity extends AppCompatActivity implements OnRequestListener, View.OnClickListener{

    private ListView contentListView;
    private TextView subTextView;

    private ImageView profileImageView;

    private ResumeSingleton resumeSingleton = ResumeSingleton.getInstance();

    private Button addToFavoritesButton;

    private BarChart mChart;

    private JobseekerResponse profile;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        profile = null;

        contentListView = (ListView) findViewById(R.id.contentListView);
        subTextView = (TextView) findViewById(R.id.subView);
        profileImageView = (ImageView) findViewById(R.id.profileImageView);
        mChart = (BarChart) findViewById(R.id.spiderChart);






        addToFavoritesButton = (Button) findViewById(R.id.addToFavorites);
        addToFavoritesButton.setOnClickListener(this);

        Intent myIntent = getIntent();
        if(myIntent.hasExtra("email")){
            String email = myIntent.getStringExtra("email");
            resumeSingleton.getAPI().getJobseekerProfile(this, email);
        }else{
            //load yourself.
            resumeSingleton.getAPI().getJobseekerProfile(this, resumeSingleton.getEmail());
            addToFavoritesButton.setText("Logout");
        }
    }

    public void showMessage(String message){
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Resume God");
        alertDialog.setMessage(message);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.hide();
            }
        });

        alertDialog.show();

    }
    @Override
    public void onBackPressed(){
        if(getIntent().hasExtra("email"))
            super.onBackPressed();
    }


    public void loadProfile(JobseekerResponse profile){
        this.profile = profile;
        subTextView.setText(profile.getFirst_name() + " " + profile.getLast_name());
        Entry[] data = { new Entry("Phone Number", profile.getPhone_number()),
                new Entry("Skill #1", profile.getSkill_1()),
                new Entry("Skill #2", profile.getSkill_2()),
                new Entry("Skill #3", profile.getSkill_3())};
        ViewFormItemAdapter adapter = new ViewFormItemAdapter(this, data);
       contentListView.setAdapter(adapter);

        DownloadAsyncTask.ViewHolder viewHolder = new DownloadAsyncTask.ViewHolder();
        viewHolder.imageView = profileImageView;
        viewHolder.url = profile.getProfile_picture();
        Log.d("ResumeGod", "Loading image " + profile.getProfile_picture());

        new DownloadAsyncTask().execute(viewHolder);

        ArrayList<BarEntry> entrySkillOne = new ArrayList<BarEntry>();
        entrySkillOne.add(new BarEntry(profile.getSkill_1_rating(), 0));

        ArrayList<BarEntry> entrySkillTwo = new ArrayList<BarEntry>();
        entrySkillTwo.add(new BarEntry(profile.getSkill_2_rating(), 0));

        ArrayList<BarEntry> entrySkillThree = new ArrayList<BarEntry>();
        entrySkillThree.add(new BarEntry(profile.getSkill_3_rating(), 0));

        BarDataSet dataSetOne = new BarDataSet(entrySkillOne, profile.getSkill_1());
        BarDataSet dataSetTwo = new BarDataSet(entrySkillTwo, profile.getSkill_2());
        BarDataSet dataSetThree = new BarDataSet(entrySkillThree, profile.getSkill_3());
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();

        dataSets.add(dataSetOne);
        dataSets.add(dataSetTwo);
        dataSets.add(dataSetThree);


        BarData dataSetsCollection = new BarData(dataSets);

        mChart.setData(dataSetsCollection);



    }

    public void loadChart(JobseekerResponse profile){

    }
    @Override
    public void onResponseReceived(RequestType type, Response response) {
        if(type == RequestType.GetJobSeekerProfile){
            if(response.getResponseCode() == 200){
                JobseekerResponse profile = (JobseekerResponse) response;
                loadProfile(profile);
            }
        }
    }

    @Override
    public void onResponsesReceived(RequestType type, int resCode, Response[] responses) {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.addToFavorites){
            if(getIntent().hasExtra("email")){
                if(profile != null){
                    showMessage("Added " + profile.getFirst_name() + " to favorites!");
                    resumeSingleton.insertIntoDatabaseJobSeeker(profile);

                }

            }else{
                Intent logoutIntent = new Intent(this, LoginActivity.class);
                resumeSingleton.setEmail("");
                startActivity(logoutIntent);
            }
        }
    }
}
