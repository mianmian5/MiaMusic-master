package com.example.miamusic_master;

import com.example.miamusic_master.bean.BannerBean;
import com.example.miamusic_master.bean.PlaylistDetailBean;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecomdetailService {


//    @GET("banner")
//    Call<List<BannerData>> getBannerData(@Query("type") String type);
//    @GET("banner")
//    Call<BannerData> getBanners();
    @GET("playlist/detail")
    Call<PlaylistDetailBean> getPlaylistDetail(@Query("id") Long id);
}
