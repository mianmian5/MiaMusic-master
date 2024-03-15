package com.example.miamusic_master;

import com.example.miamusic_master.bean.BannerBean;
import com.example.miamusic_master.bean.MusicCommentBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MusicCommentService {

    @GET("comment/music")
    Call<MusicCommentBean> getMusicComment(@Query("id") long id);
}
