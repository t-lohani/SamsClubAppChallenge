package com.samsclub.app.samsclubapp.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by TARUN on 10/4/2017.
 */

public class FileCache {

    private File cacheDir;

    public FileCache(Context context){

        //Find the dir at SDCARD to save cached images

        if (Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            //if SDCARD is mounted (SDCARD is present on device and mounted)
            cacheDir = new File(Environment.getExternalStorageDirectory()+File.separator+"SamsClubApp");
        } else {
            Log.d("Tarun", "No SD Card");
            // if checking on simulator the create cache dir in your application context
            cacheDir = context.getCacheDir();
        }

        if(!cacheDir.exists()) {
            // create cache dir in your application context
            Log.d("Tarun", "Making dirs");
            cacheDir.mkdirs();
        }
    }

    public File getFile(String url){
        //Identify images by hashcode or encode by URLEncoder.encode.
        String filename=String.valueOf(url.hashCode());

        File f = new File(cacheDir, filename);
        return f;
    }

    public void clear(){
        // list all files inside cache directory
        File[] files=cacheDir.listFiles();
        if(files==null)
            return;
        //delete all cache directory files
        for(File f:files)
            f.delete();
    }

}