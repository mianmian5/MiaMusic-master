package com.example.miamusic_master;

import com.example.miamusic_master.bean.BannerBean;
import com.example.miamusic_master.bean.HotSearchDetailBean;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface HotSearchService {


//    @GET("banner")
//    Call<List<BannerData>> getBannerData(@Query("type") String type);
//    @GET("banner")
//    Call<BannerData> getBanners();
    @GET("search/hot/detail")
    Call<HotSearchDetailBean> getSearchHotDetail();
}
