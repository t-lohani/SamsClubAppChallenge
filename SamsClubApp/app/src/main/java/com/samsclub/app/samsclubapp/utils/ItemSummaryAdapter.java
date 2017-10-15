package com.samsclub.app.samsclubapp.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.samsclub.app.samsclubapp.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by TARUN on 10/4/2017.
 */

public class ItemSummaryAdapter extends RecyclerView.Adapter<ItemSummaryAdapter.MyViewHolder> {

    private ImageLoader imageLoader;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView prodImage;
        public TextView prodName, prodPrice, prodRating, reviewCount;

        public MyViewHolder(View view) {
            super(view);
            prodImage = (ImageView) view.findViewById(R.id.prod_image);
            prodName = (TextView) view.findViewById(R.id.prod_name);
            prodPrice = (TextView) view.findViewById(R.id.prod_price);
            prodRating = (TextView) view.findViewById(R.id.prod_rating);
            reviewCount = (TextView) view.findViewById(R.id.prod_reviews);
        }
    }

    public ItemSummaryAdapter(Activity activity) {
        imageLoader = new ImageLoader(activity.getApplicationContext());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_summary_layout, parent, false);

        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ItemSummary item = DataMapper.datamap.get(position);
//        holder.prodImage.setImageURI(item.getImgUrl());
        holder.prodName.setText(item.getProdName());
        holder.prodPrice.setText(item.getProdPrice());
        holder.prodRating.setText(String.valueOf(item.getProdRating()) + "/5.0     Reviews : ");
        holder.reviewCount.setText(String.valueOf(item.getReviewCount()));

        ImageView image = holder.prodImage;
        new ImageLoaderClass(Uri.parse(item.getImgUrl()), image, 128, 128).execute();
//        imageLoader.DisplayImage(itemList.get(position).getImgUrl(), image);
    }

    @Override
    public int getItemCount() {
        return DataMapper.itemCount;
    }
}

class ImageLoaderClass extends AsyncTask<Object, Object, Bitmap> {
    private Uri imageUri;

    private ImageView imageView;

    private int preferredWidth = 80;
    private int preferredHeight = 80;

    public ImageLoaderClass(Uri uri, ImageView imageView, int scaleWidth, int scaleHeight ) {
        this.imageUri = uri;
        this.imageView = imageView;
        this.preferredWidth = scaleWidth;
        this.preferredHeight = scaleHeight;
    }

    public Bitmap doInBackground(Object... params) {
        if( imageUri == null ) return null;
        String url = imageUri.toString();
        if( url.length() == 0 ) return null;
        HttpGet httpGet = new HttpGet(url);
        DefaultHttpClient client = new DefaultHttpClient();
        HttpResponse response = null;
        try {
            response = client.execute( httpGet );
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream is = null;
        try {
            is = new BufferedInputStream( response.getEntity().getContent() );
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            if( preferredWidth > 0 && preferredHeight > 0 && bitmap.getWidth() > preferredWidth && bitmap.getHeight() > preferredHeight ) {
                return Bitmap.createScaledBitmap(bitmap, preferredWidth, preferredHeight, false);
            } else {
                return bitmap;
            }
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onPostExecute(Bitmap drawable ) {
        imageView.setImageBitmap(drawable);
    }
}