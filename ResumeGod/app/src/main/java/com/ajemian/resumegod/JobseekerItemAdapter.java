package com.ajemian.resumegod;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Created by Kudo on 10/9/16.
 */

public class JobseekerItemAdapter extends BaseAdapter {

    private Context context;
    private JobseekerResponse[] data;
    private LayoutInflater inflater;
    private DecimalFormat df = new DecimalFormat();
    private Double myLat;
    private Double myLong;

    public JobseekerItemAdapter(Context context, JobseekerResponse[] data){
        this.context = context;
        this.data = data;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        df.setMaximumFractionDigits(2);
        myLong = ResumeSingleton.getInstance().getLastLocation().getLongitude();
        myLat = ResumeSingleton.getInstance().getLastLocation().getLatitude();
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int i) {
        return data[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if(view == null){
            view = inflater.inflate(R.layout.listview_jobseeker_with_distance, null);

            TextView firstAndLastNameTextView = (TextView) view.findViewById(R.id.firstAndLastTextView);
            TextView skillsTextView = (TextView) view.findViewById(R.id.skillsTextView);

            ImageView imageView = (ImageView) view.findViewById(R.id.jobseekerImageView);

            TextView distanceTextView = (TextView) view.findViewById(R.id.distanceTextView);

            Double userLong = data[i].getLocation_long();
            Double userLat = data[i].getLocation_lat();


            Double calculateDistance = CalculateDistance.calculateDistance(myLat, myLong, userLat, userLong);

            distanceTextView.setText(df.format(calculateDistance) + "km");

            firstAndLastNameTextView.setText(data[i].getFirst_name() + " " + data[i].getLast_name());
            skillsTextView.setText(data[i].getSkill_1() + ", " + data[i].getSkill_2() + ", " + data[i].getSkill_3());

            DownloadAsyncTask.ViewHolder imageHolder = new DownloadAsyncTask.ViewHolder();
            imageHolder.url = data[i].getProfile_picture();
            imageHolder.imageView = imageView;

            new DownloadAsyncTask().execute(imageHolder);


        }


        return view;
    }
}
