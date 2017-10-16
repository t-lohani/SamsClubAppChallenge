package com.samsclub.app.samsclubapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.samsclub.app.samsclubapp.R;
import com.samsclub.app.samsclubapp.listeners.EndlessRecyclerViewScrollListener;
import com.samsclub.app.samsclubapp.listeners.RecyclerTouchListener;
import com.samsclub.app.samsclubapp.utils.DataMapper;
import com.samsclub.app.samsclubapp.utils.ItemSummary;
import com.samsclub.app.samsclubapp.utils.ItemSummaryAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends Activity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private EndlessRecyclerViewScrollListener scrollListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_items);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
            public void onClick(View view, int position) {
                ItemSummary movie = DataMapper.datamap.get(position);
                Toast.makeText(getApplicationContext(), movie.getProdName() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            public void onLongClick(View view, int position) {

            }
        }));

        scrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                Log.d("Tarun", "onLoadMore");
                if (!DataMapper.endOfData)
                    new HomeScreen.FetchIncrementalData().execute();
            }
        };

        mRecyclerView.addOnScrollListener(scrollListener);

        mAdapter = new ItemSummaryAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        mRecyclerView.setAdapter(null);
        super.onDestroy();
    }

    private class FetchIncrementalData extends AsyncTask<Void, Void, Void> {

        final String TAG = "FetchIncrementalData.java";
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
            String urlStr = "https://walmartlabs-test.appspot.com/_ah/api/walmart/v1/"
                    + "walmartproducts/ba8c4ec4-b5b9-4ece-8db2-9695361737b9/"
                    + DataMapper.pageCount+"/30";
//            Log.d("Tarun", urlStr);
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

                JSONObject mainObject = null;
                try {
                    mainObject = new JSONObject(json);
                    JSONArray products = mainObject.getJSONArray("products");
//                  Log.d("Tarun", products.toString());
                    for (int i=0; i<products.length(); i++) {
                        JSONObject product = products.getJSONObject(i);
                        ItemSummary summary = new ItemSummary(product.getString("productImage"),
                                product.getString("productName"), product.getString("price"),
                                product.getString("reviewRating"), product.getString("reviewCount"));
                        DataMapper.datamap.put(DataMapper.itemCount++, summary);
                    }
                    DataMapper.pageCount++;
                } catch (JSONException e) {
                    DataMapper.endOfData = true;
                    e.printStackTrace();
                }

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
            mAdapter.notifyDataSetChanged();
//            Log.d("Tarun", DataMapper.datamap.size()+"");
        }

    }
}
