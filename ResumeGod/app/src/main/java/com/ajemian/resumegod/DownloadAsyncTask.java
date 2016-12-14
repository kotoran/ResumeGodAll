package com.ajemian.resumegod;

/**
 * Created by Kudo on 12/6/16.
 */
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ajemian.resumegod.BitmapCache;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Kudo on 10/8/2016.
 */

public class DownloadAsyncTask extends AsyncTask<DownloadAsyncTask.ViewHolder, Void, DownloadAsyncTask.ViewHolder> {
    public static class ViewHolder{
        public String url;
        public ImageView imageView;
        public Bitmap bitmap;
    }

    protected DownloadAsyncTask.ViewHolder doInBackground(DownloadAsyncTask.ViewHolder... params){
        DownloadAsyncTask.ViewHolder viewHolder = params[0];
        try{
            if (BitmapCache.doesImageExist(viewHolder.url)){
                viewHolder.bitmap = BitmapCache.getImage(viewHolder.url);
                //viewHolder.bitmap = Bitmap.createScaledBitmap(ImageCache.getImage(viewHolder.url), 500, 400, true);
            }else {
                URL imageUrl = new URL(viewHolder.url);
                viewHolder.bitmap = BitmapFactory.decodeStream(imageUrl.openStream());
                //viewHolder.bitmap = Bitmap.createScaledBitmap(viewHolder.bitmap, 500, 400, true);
                BitmapCache.saveImage(viewHolder.url, viewHolder.bitmap);
            }
        }catch(Exception e){

            viewHolder.bitmap = null;
        }
        return viewHolder;
    }
    @Override
    protected void onPostExecute(DownloadAsyncTask.ViewHolder result){
        super.onPostExecute(result);
        if(result.bitmap != null){
            result.imageView.setImageBitmap(result.bitmap);
        }else{

        }
    }



}
