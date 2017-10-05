package com.samsclub.app.samsclubapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.samsclub.app.samsclubapp.R;
import com.samsclub.app.samsclubapp.listeners.RecyclerTouchListener;
import com.samsclub.app.samsclubapp.utils.ItemSummary;
import com.samsclub.app.samsclubapp.utils.ItemSummaryAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends Activity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ItemSummary> itemList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Intent intent = getIntent();
        String json = intent.getStringExtra("json");

//        Log.d("Tarun", json);

        JSONObject mainObject = null;
        itemList = new ArrayList<>();
        try {
            mainObject = new JSONObject(json);
            JSONArray products = mainObject.getJSONArray("products");
//            Log.d("Tarun", products.toString());
            for (int i=0; i<products.length(); i++) {
                JSONObject product = products.getJSONObject(i);
                ItemSummary is = new ItemSummary(product.getString("productImage"),
                        product.getString("productName"), product.getString("price"),
                        product.getString("reviewRating"), product.getString("reviewCount"));
                itemList.add(is);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_items);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
            public void onClick(View view, int position) {
                ItemSummary movie = itemList.get(position);
                Toast.makeText(getApplicationContext(), movie.getProdName() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            public void onLongClick(View view, int position) {

            }
        }));

        mAdapter = new ItemSummaryAdapter(itemList, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        mRecyclerView.setAdapter(null);
        super.onDestroy();
    }
}
