package com.samsclub.app.samsclubapp.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.samsclub.app.samsclubapp.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh_screen);

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getActionBar();
        actionBar.hide();

        new FetchData().execute();
    }

    private class FetchData extends AsyncTask<Void, Void, Void> {

        final String TAG = "FetchData.java";
        String json;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
        /*
         * Will make http call here This call will download required data
         * before launching the app
         */
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String urlStr = "https://walmartlabs-test.appspot.com/_ah/api/walmart/v1/walmartproducts/ba8c4ec4-b5b9-4ece-8db2-9695361737b9/1/30";

            try {
                URL url = new URL(urlStr);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n"); //here we will get whole response
//                    Log.d("Response: ", "> " + json);
                }

                json = buffer.toString();
//                Log.d("Tarun", json);
                return null;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // After completing http call
            // will close this activity and lauch home screen
            Intent intent = new Intent(SplashScreen.this, HomeScreen.class);
            intent.putExtra("json", json);
            startActivity(intent);

            // close this activity
            finish();
        }

    }
}