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


public class StrongHireItemAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<StrongHireModel> data;
    private LayoutInflater inflater;

    public StrongHireItemAdapter(Context context, ArrayList<StrongHireModel> data){
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
            view = inflater.inflate(R.layout.listview_strong_hire, null);

            StrongHireModel model = (StrongHireModel) getItem(i);

            TextView strongHireTextView = (TextView) view.findViewById(R.id.isStrongHireTextView);

            if(model.getStrong_hire() == 1){
                strongHireTextView.setText("Y");
            }else{
                strongHireTextView.setText("N");
            }


        }


        return view;
    }
}