package com.ajemian.resumegod;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kudo on 10/9/16.
 */


public class JobSeekerDatabaseItemAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<JobSeekerDatabaseModel> data;
    private LayoutInflater inflater;

    public JobSeekerDatabaseItemAdapter(Context context, ArrayList<JobSeekerDatabaseModel> data){
        this.context = context;
        this.data = data;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if(view == null){
            view = inflater.inflate(R.layout.listview_jobseeker, null);

            JobSeekerDatabaseModel model = (JobSeekerDatabaseModel) getItem(i);

            TextView firstAndLastNameTextView = (TextView) view.findViewById(R.id.firstAndLastTextView);
            TextView skillsTextView = (TextView) view.findViewById(R.id.skillsTextView);

            ImageView imageView = (ImageView) view.findViewById(R.id.jobseekerImageView);

            firstAndLastNameTextView.setText(model.getFirst_name() + " " + model.getLast_name());
            skillsTextView.setText(model.getSkills());

            DownloadAsyncTask.ViewHolder imageHolder = new DownloadAsyncTask.ViewHolder();
            imageHolder.url = model.getProfile_picture();
            imageHolder.imageView = imageView;

            new DownloadAsyncTask().execute(imageHolder);


        }


        return view;
    }
}