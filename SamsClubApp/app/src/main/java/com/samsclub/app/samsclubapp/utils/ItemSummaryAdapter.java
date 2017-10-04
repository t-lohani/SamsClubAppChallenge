package com.samsclub.app.samsclubapp.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.samsclub.app.samsclubapp.R;

import java.util.List;

/**
 * Created by TARUN on 10/4/2017.
 */

public class ItemSummaryAdapter extends RecyclerView.Adapter<ItemSummaryAdapter.MyViewHolder> {

    private List<ItemSummary> itemList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView prodImage;
        public TextView prodName, prodPrice, prodDesc, prodRating, reviewCount;

        public MyViewHolder(View view) {
            super(view);
            prodImage = (ImageView) view.findViewById(R.id.prod_image);
            prodName = (TextView) view.findViewById(R.id.prod_name);
            prodPrice = (TextView) view.findViewById(R.id.prod_price);
            prodRating = (TextView) view.findViewById(R.id.prod_rating);
            reviewCount = (TextView) view.findViewById(R.id.prod_reviews);
        }
    }

    public ItemSummaryAdapter(List<ItemSummary> itemList) {
        this.itemList = itemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_summary_layout, parent, false);

        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ItemSummary movie = itemList.get(position);
        holder.prodImage.setImageURI(movie.getImgUrl());
        holder.prodName.setText(movie.getProdName());
        holder.prodPrice.setText(movie.getProdPrice());
        holder.prodRating.setText(String.valueOf(movie.getProdRating()) + "/5.0     Reviews : ");
        holder.reviewCount.setText(String.valueOf(movie.getReviewCount()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
