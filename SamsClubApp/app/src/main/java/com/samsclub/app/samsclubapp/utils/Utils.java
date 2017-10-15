package com.samsclub.app.samsclubapp.utils;

import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by TARUN on 10/4/2017.
 */

public class Utils {
    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            while(true) {
                //Read byte from input stream

                int count=is.read(bytes, 0, buffer_size);
                if(count == -1)
                    break;

                //Write byte from output stream
                os.write(bytes, 0, count);
            }
            Log.d("Tarun", "Broken");
        }
        catch(Exception ex){

        }
    }
}