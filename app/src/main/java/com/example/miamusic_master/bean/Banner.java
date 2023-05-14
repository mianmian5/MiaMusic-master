package com.example.miamusic_master.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Banner {
    @SerializedName("id")
    private int id;
    @SerializedName("image")
    private String imageUrl;
    @SerializedName("title")
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

