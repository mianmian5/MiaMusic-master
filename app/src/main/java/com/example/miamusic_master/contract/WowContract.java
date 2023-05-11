package com.example.miamusic_master.contract;

import android.database.Observable;

import com.example.miamusic_master.base.BaseModel;
import com.example.miamusic_master.base.BasePresenter;
import com.example.miamusic_master.base.BaseView;
import com.example.miamusic_master.bean.BannerBean;
import com.example.miamusic_master.bean.DailyRecommendBean;
import com.example.miamusic_master.bean.HighQualityPlayListBean;
import com.example.miamusic_master.bean.MainRecommendPlayListBean;
import com.example.miamusic_master.bean.PlaylistDetailBean;
import com.example.miamusic_master.bean.RecommendPlayListBean;
import com.example.miamusic_master.bean.TopListBean;
//import com.example.miamusic_master.manager.bean.MusicCanPlayBean;

//import io.reactivex.Observable;


public interface WowContract {
    interface View extends BaseView {
        void onGetBannerSuccess(BannerBean bean);

        void onGetBannerFail(String e);

        void onGetRecommendPlayListSuccess(MainRecommendPlayListBean bean);

        void onGetRecommendPlayListFail(String e);

        void onGetDailyRecommendSuccess(DailyRecommendBean bean);

        void onGetDailyRecommendFail(String e);

        void onGetTopListSuccess(TopListBean bean);

        void onGetTopListFail(String e);

        void onGetPlayListSuccess(RecommendPlayListBean bean);

        void onGetPlayListFail(String e);

        void onGetPlaylistDetailSuccess(PlaylistDetailBean bean);

        void onGetPlaylistDetailFail(String e);

//        void onGetMusicCanPlaySuccess(MusicCanPlayBean bean);

        void onGetMusicCanPlayFail(String e);

        void onGetHighQualitySuccess(HighQualityPlayListBean bean);

        void onGetHighQualityFail(String e);
    }

    interface Model extends BaseModel {
        io.reactivex.Observable<BannerBean> getBanner();

        io.reactivex.Observable<MainRecommendPlayListBean> getRecommendPlayList();

        io.reactivex.Observable<DailyRecommendBean> getDailyRecommend();

        io.reactivex.Observable<TopListBean> getTopList();

        io.reactivex.Observable<RecommendPlayListBean> getPlayList(String type, int limit);

        Observable<PlaylistDetailBean> getPlaylistDetail(long id);

//        Observable<MusicCanPlayBean> getMusicCanPlay(long id);

        Observable<HighQualityPlayListBean> getHighQuality(int limit, long before);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getBanner();

        public abstract void getRecommendPlayList();

        public abstract void getDailyRecommend();

        public abstract void getTopList();

        public abstract void getPlayList(String type, int limit);

        public abstract void getPlaylistDetail(long id);

        public abstract void getMusicCanPlay(long id);

        public abstract void getHighQuality(int limit, long before);
    }
}
