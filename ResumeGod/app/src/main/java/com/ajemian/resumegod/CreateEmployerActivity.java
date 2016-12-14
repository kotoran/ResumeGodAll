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

public class CreateEmployerActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView contentListView;

    private Button submitButton;

    private ResumeSingleton resumeSingleton = ResumeSingleton.getInstance();
    private FormItemAdapter adapter;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_employer);

        contentListView = (ListView) findViewById(R.id.contentListView);
        submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(this);


        Entry[] data = { new Entry("Company Name")};


        adapter = new FormItemAdapter(this, data);
        contentListView.setAdapter(adapter);

    }

    public boolean validateCompanyName(String text){
        return true;
    }

    public boolean retrieveFields(){
        boolean valid = true;
        for(int i=0; i<contentListView.getChildCount(); i++) {
            View v = contentListView.getChildAt(i);
            EditText text = (EditText) v.findViewById(R.id.entryEditText);
            Entry entry = (Entry) adapter.getItem(i);

            String validateText = text.getText().toString();
            if(entry.getLabel().equals("Company Name")){
                valid = validateCompanyName(validateText);
                resumeSingleton.getEmployerResponse().setCompany_name(validateText);
            }
        }

        return valid;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.submitButton){
            resumeSingleton.setEmployerResponse(new EmployerResponse());
            resumeSingleton.setDesignation(2);
            if(retrieveFields()) {
                Intent takeLocationIntent = new Intent(this, TakeLocationActivity.class);
                startActivity(takeLocationIntent);
            }
        }
    }
}
