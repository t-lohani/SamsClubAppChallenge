package com.samsclub.app.samsclubapp.utils;


import android.net.Uri;

/**
 * Created by TARUN on 10/4/2017.
 */

public class ItemSummary {
    private String prodName, prodPrice, prodRating, reviewCount;
    private Uri imgUrl;

    public ItemSummary(Uri imgUrl, String prodName, String prodPrice,
                       String prodRating, String reviewCount) {
        this.imgUrl = imgUrl;
        this.prodName = prodName;
        this.prodPrice = prodPrice;
        this.prodRating = prodRating;
        this.reviewCount = reviewCount;
    }

    public Uri getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(Uri imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(String prodPrice) {
        this.prodPrice = prodPrice;
    }

    public String getProdRating() {
        return prodRating;
    }

    public void setProdRating(String prodRating) {
        this.prodRating = prodRating;
    }

    public String getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(String reviewCount) {
        this.reviewCount = reviewCount;
    }
}
