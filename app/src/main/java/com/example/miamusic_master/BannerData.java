package com.example.miamusic_master;

import com.google.gson.annotations.SerializedName;
import com.youth.banner.Banner;

import java.util.List;

public class BannerData {

    @SerializedName("banners")
    private List<Banner> banners;
    @SerializedName("code")
    private int code;

    public List<Banner> getBanners1() {
        return banners;
    }

    public void setBanners(List<Banner> banners) {
        this.banners = banners;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}