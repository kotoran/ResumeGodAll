package com.ajemian.resumegod;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Kudo on 10/9/16.
 */

public class ViewFormItemAdapter extends BaseAdapter {

    private Context context;
    private Entry[] data;
    private LayoutInflater inflater;

    public ViewFormItemAdapter(Context context, Entry[] data){
        this.context = context;
        this.data = data;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {


        if(data == null) return 0;
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
            view = inflater.inflate(R.layout.listview_entry_view, null);

            TextView entryLabel = (TextView) view.findViewById(R.id.label);
            TextView entryText = (TextView) view.findViewById(R.id.entryEditText);

            entryLabel.setText(data[i].getLabel());

            String text = data[i].getText();
            if (text == null){
                entryText.setText("Error when loading");
            }else{
                entryText.setText(data[i].getText());
            }

        }


        return view;
    }
}
