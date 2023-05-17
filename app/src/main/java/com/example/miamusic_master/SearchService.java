package com.example.miamusic_master;

import com.example.miamusic_master.bean.BannerBean;
import com.example.miamusic_master.bean.SongSearchBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchService {

    @GET("search")
    Call<SongSearchBean> getSongSearch(@Query("keywords") String keywords, @Query("type") int type);

//    @GET("search")
//    Call<FeedSearchBean> getFeedSearch(@Query("keywords") String keywords, @Query("type") int type);
//
//    @GET("search")
//    Call<SingerSearchBean> getSingerSearch(@Query("keywords") String keywords, @Query("type") int type);
//
//    @GET("search")
//    Call<AlbumSearchBean> getAlbumSearch(@Query("keywords") String keywords, @Query("type") int type);
//
//    @GET("search")
//    Call<PlayListSearchBean> getPlayListSearch(@Query("keywords") String keywords, @Query("type") int type);
//
//    @GET("search")
//    Call<RadioSearchBean> getRadioSearch(@Query("keywords") String keywords, @Query("type") int type);
//
//    @GET("search")
//    Call<UserSearchBean> getUserSearch(@Query("keywords") String keywords, @Query("type") int type);
//
//    @GET("search")
//    Call<SynthesisSearchBean> getSynthesisSearch(@Query("keywords") String keywords, @Query("type") int type);

}
