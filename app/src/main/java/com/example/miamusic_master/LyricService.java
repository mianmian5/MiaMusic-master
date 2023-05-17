package com.example.miamusic_master;

import com.example.miamusic_master.bean.LyricBean;
import com.example.miamusic_master.bean.MainRecomListBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LyricService {

@GET("lyric")
Call<LyricBean> getLyric(@Query("id") long id);

}
