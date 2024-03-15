package com.example.miamusic_master;

import com.example.miamusic_master.bean.MusicCommentBean;
import com.example.miamusic_master.bean.PlayListCommentBean;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlaylistCommentService {

    @GET("comment/playlist")
    Call<PlayListCommentBean> getPlaylistComment(@Query("id") long id);
}
