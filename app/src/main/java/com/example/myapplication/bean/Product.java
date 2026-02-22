package com.example.myapplication.bean;

import android.net.Uri;

import java.io.Serializable;

public class Product implements Serializable {
    private String title;
    private String description;
    private String price;
    private Uri image;

    public Product(String title, String description, String price, Uri image) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.image = image;
    }
    public Product(String title, String description, String price, int imageId) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.image = Uri.parse("android.resource://" + getPackageName() + "/" + imageId);
    }

    public String getPackageName() {
        return "com.example.myapplication";
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public Uri getImage() {
        return image;
    }

    public boolean matchesSearch(String keyword){
    if(keyword==null||keyword.trim().isEmpty()){
        return true;//搜索框空的时候也展示
    }
    return title.contains(keyword)||description.contains(keyword);//标题和描述有关键词也
}
}