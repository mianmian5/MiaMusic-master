package com.example.miamusic_master;

import com.example.miamusic_master.bean.BannerBean;
import com.example.miamusic_master.bean.MusicCommentBean;
import com.example.miamusic_master.bean.SongUrlBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SongUrlService {

    @GET("song/url")
    Call<SongUrlBean> getSongUrl(@Query("id") long id);
}
