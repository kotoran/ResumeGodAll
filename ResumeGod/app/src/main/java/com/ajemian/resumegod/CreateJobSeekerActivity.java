package com.ajemian.resumegod;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

/**
 * Created by Kudo on 10/8/16.
 */

public class CreateJobSeekerActivity extends AppCompatActivity implements View.OnClickListener{

    private ListView contentListView;

    private Button submitButton;

    private ResumeSingleton resumeSingleton = ResumeSingleton.getInstance();
    FormItemAdapter adapter;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job_seeker);

        contentListView = (ListView) findViewById(R.id.contentListView);
        submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(this);

        Entry[] data = { new Entry("First Name"),
                new Entry("Last Name"),
                new Entry("Phone Number"),
                new Entry("Skill #1"),
                new Entry("Rate First (1-100)"),
                new Entry("Skill #2"),
                new Entry("Rate Second (1-100)"),
                new Entry("Skill #3"),
                new Entry("Rate Third (1-100)")};


        adapter = new FormItemAdapter(this, data);
        contentListView.setAdapter(adapter);

    }
    public boolean validateFirstName(String text){
        return true;
    }

    public boolean validateLastName(String text){
        return true;
    }

    public boolean validatePhoneNumber(String text){
        return true;
    }
    public boolean validateSkill1(String text){
        return true;
    }

    public boolean validateSkill2(String text){
        return true;
    }
    public boolean validateSkill3(String text){
        return true;
    }

    public boolean validateSkill1Rating(String text){
        return true;
    }

    public boolean validateSkill2Rating(String text){
        return true;
    }
    public boolean validateSkill3Rating(String text){
        return true;
    }


    public boolean retrieveFields(){
        boolean valid = true;
        for(int i=0; i<contentListView.getChildCount(); i++){
            View v = contentListView.getChildAt(i);
            EditText text = (EditText) v.findViewById(R.id.entryEditText);
            Entry entry = (Entry) adapter.getItem(i);

            String validateText = text.getText().toString();
            if(entry.getLabel().equals("First Name")) {
                valid = validateFirstName(validateText);
                resumeSingleton.getJobseekerResponse().setFirst_name(validateText);
            }else if (entry.getLabel().equals("Last Name")){
                valid = validateLastName(validateText);
                resumeSingleton.getJobseekerResponse().setLast_name(validateText);

            }else if (entry.getLabel().equals("Phone Number")){
                valid = validatePhoneNumber(validateText);
                resumeSingleton.getJobseekerResponse().setPhone_number(validateText);

            }else if (entry.getLabel().equals("Skill #1")){
                valid = validateSkill1(validateText);
                resumeSingleton.getJobseekerResponse().setSkill_1(validateText);

            }else if (entry.getLabel().equals("Skill #2")){
                valid = validateSkill2(validateText);
                resumeSingleton.getJobseekerResponse().setSkill_2(validateText);

            }else if (entry.getLabel().equals("Skill #3")){
                valid = validateSkill3(validateText);
                resumeSingleton.getJobseekerResponse().setSkill_3(validateText);

            }else if (entry.getLabel().equals("Rate First (1-100)")){
                valid = validateSkill1Rating(validateText);
                resumeSingleton.getJobseekerResponse().setSkill_1_rating(Integer.parseInt(validateText));

            }else if (entry.getLabel().equals("Rate Second (1-100)")){
                valid = validateSkill2Rating(validateText);
                resumeSingleton.getJobseekerResponse().setSkill_2_rating(Integer.parseInt(validateText));
            }else if (entry.getLabel().equals("Rate Third (1-100)")){
                valid = validateSkill3Rating(validateText);
                resumeSingleton.getJobseekerResponse().setSkill_3_rating(Integer.parseInt(validateText));
            }

        }
        return valid;
    }

    public void onClick(View view) {
        if(view.getId() == R.id.submitButton){
            resumeSingleton.setJobseekerResponse(new JobseekerResponse());
            resumeSingleton.setDesignation(1);
            if(retrieveFields()) {
                Intent takePictureIntent = new Intent(this, TakePictureActivity.class);
                startActivity(takePictureIntent);
            }
        }
    }
}
