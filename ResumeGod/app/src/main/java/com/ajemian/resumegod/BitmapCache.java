package com.ajemian.resumegod;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Kudo on 10/8/16.
 */

public class BitmapCache {

    public static String externalStoragePath = Environment.getExternalStorageDirectory().toString();


    public static boolean doesImageExist(String url){
        return new File(externalStoragePath, removeStuff(url)).exists();

    }

    public static String removeStuff(String url){
        return url.replace(":","").replace("/", "");
    }
    public static Bitmap getImage(String url){
        File f = new File(externalStoragePath, url);
        return BitmapFactory.decodeFile(externalStoragePath+"/"+removeStuff(url));
    }
    public static void saveImage(String url, Bitmap bitmap){
        try {
            FileOutputStream fileOut = new FileOutputStream(new File(externalStoragePath, removeStuff(url)));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOut);
            fileOut.flush();
            fileOut.close();
        }catch(Exception e){
            Log.d("BitmapCache", e.getMessage());
        }
    }

}
