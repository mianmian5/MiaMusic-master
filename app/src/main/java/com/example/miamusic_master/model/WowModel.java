package com.example.miamusic_master.model;

import android.database.Observable;

import com.example.miamusic_master.api.ApiEngine;
import com.example.miamusic_master.bean.BannerBean;
import com.example.miamusic_master.bean.DailyRecommendBean;
import com.example.miamusic_master.bean.HighQualityPlayListBean;
import com.example.miamusic_master.bean.MainRecommendPlayListBean;
import com.example.miamusic_master.bean.PlaylistDetailBean;
import com.example.miamusic_master.bean.RecommendPlayListBean;
import com.example.miamusic_master.bean.TopListBean;
import com.example.miamusic_master.contract.WowContract;
//import com.example.miamusic_master.manager.bean.MusicCanPlayBean;

import io.reactivex.*;

public class WowModel implements WowContract.Model {
    @Override
    public io.reactivex.Observable<BannerBean> getBanner() {
        return ApiEngine.getInstance().getApiService().getBanner();
    }

    @Override
    public io.reactivex.Observable<MainRecommendPlayListBean> getRecommendPlayList() {
        return ApiEngine.getInstance().getApiService().getRecommendPlayList();
    }

    @Override
    public io.reactivex.Observable<DailyRecommendBean> getDailyRecommend() {
        return ApiEngine.getInstance().getApiService().getDailyRecommend();
    }

    @Override
    public io.reactivex.Observable<TopListBean> getTopList() {
        return ApiEngine.getInstance().getApiService().getTopList();
    }

    @Override
    public io.reactivex.Observable<RecommendPlayListBean> getPlayList(String type, int limit) {
        return ApiEngine.getInstance().getApiService().getPlayList(type, limit);
    }

    @Override
    public Observable<PlaylistDetailBean> getPlaylistDetail(long id) {
        return null;
//        return ApiEngine.getInstance().getApiService().getPlaylistDetail(id);
    }

    @Override
    public Observable<HighQualityPlayListBean> getHighQuality(int limit, long before) {
        return null;
    }

//    @Override
//    public Observable<MusicCanPlayBean> getMusicCanPlay(long id) {
//        return ApiEngine.getInstance().getApiService().getMusicCanPlay(id);
//    }

//    @Override
//    public Observable<HighQualityPlayListBean> getHighQuality(int limit, long before) {
//        return ApiEngine.getInstance().getApiService().getHighquality(limit, before);
//    }
}
