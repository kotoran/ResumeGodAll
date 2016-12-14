package com.ajemian.resumegod;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Kudo on 10/9/16.
 */

public class EmployersFavoriteActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView contentListView;
    private ListView strongHireListView;

    private Button deleteAllButton;


    private ResumeSingleton singleton = ResumeSingleton.getInstance();

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employers_favorite);

        contentListView = (ListView) findViewById(R.id.contentListView);
        strongHireListView = (ListView) findViewById(R.id.strongHireListView);
        deleteAllButton = (Button) findViewById(R.id.deleteAllButton);

        deleteAllButton.setOnClickListener(this);

        strongHireListView.setOnItemClickListener(this);
        loadFavorites();


    }

    public void loadFavorites(){
        ArrayList<JobSeekerDatabaseModel> jobseekers = singleton.getJobseekersFromDatabase();
        ArrayList<StrongHireModel> strongHires = singleton.getStrongHireFromDatabase();

        JobSeekerDatabaseItemAdapter jAdapter = new JobSeekerDatabaseItemAdapter(this, jobseekers);
        StrongHireItemAdapter sAdapter = new StrongHireItemAdapter(this, strongHires);

        contentListView.setAdapter(jAdapter);
        strongHireListView.setAdapter(sAdapter);



    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        StrongHireItemAdapter adapter = (StrongHireItemAdapter) adapterView.getAdapter();
        StrongHireModel model = (StrongHireModel) adapter.getItem(i);
        if(model.getStrong_hire() == 1) {
            model.setStrong_hire(0);
            singleton.updateStrongHireWithEmail(model.getEmail(), 0);
        }else{
            model.setStrong_hire(1);
            singleton.updateStrongHireWithEmail(model.getEmail(), 1);
        }

        loadFavorites();
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.deleteAllButton){
            singleton.clearDatabase();
            loadFavorites();
        }
    }
}
