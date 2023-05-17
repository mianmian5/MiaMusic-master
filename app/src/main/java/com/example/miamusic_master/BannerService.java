package com.example.miamusic_master;

import com.example.miamusic_master.bean.BannerBean;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BannerService {

    @GET("banner")
    Call<BannerBean> getBanner();
}
