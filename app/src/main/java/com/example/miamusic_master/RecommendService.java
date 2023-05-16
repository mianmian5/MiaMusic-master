package com.example.miamusic_master;

import com.example.miamusic_master.bean.BannerBean;
import com.example.miamusic_master.bean.MainRecomListBean;
import com.example.miamusic_master.bean.MainRecommendPlayListBean;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RecommendService {


//    @GET("banner")
//    Call<List<BannerData>> getBannerData(@Query("type") String type);
//    @GET("banner")
//    Call<BannerData> getBanners();
@GET("personalized")
Call<MainRecomListBean> getRecommendPlayList();

}
