package com.example.miamusic_master.presenter;

import android.content.Context;
import android.widget.Toast;

import com.example.miamusic_master.MainActivity;
import com.example.miamusic_master.bean.BannerBean;
import com.example.miamusic_master.bean.DailyRecommendBean;
import com.example.miamusic_master.bean.HighQualityPlayListBean;
import com.example.miamusic_master.bean.MainRecommendPlayListBean;
import com.example.miamusic_master.bean.PlaylistDetailBean;
import com.example.miamusic_master.bean.RecommendPlayListBean;
import com.example.miamusic_master.bean.TopListBean;
import com.example.miamusic_master.contract.WowContract;


import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class FindPresenter extends WowContract.Presenter {
    private static final String TAG = "WowPresenter";

    public FindPresenter(WowContract.View view) {
        this.mView = view;
//        this.mModel = (WowContract.Model) new WowModel();
    }


    @Override
    public void getBanner() {
        mModel.getBanner().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BannerBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
//                        LogUtil.d(TAG, "getBanner onSubscribe");
                        System.out.println("getBanner onSubscribe");

                    }

                    @Override
                    public void onNext(BannerBean bean) {
//                        LogUtil.d(TAG, "BannerBean : " + bean);
                        mView.onGetBannerSuccess(bean);
                    }

                    @Override
                    public void onError(Throwable e) {

                        mView.onGetBannerFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

//                        LogUtil.d(TAG, "getBanner onComplete");
                    }
                });

    }

    @Override
    public void getRecommendPlayList() {
        mModel.getRecommendPlayList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MainRecommendPlayListBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
//                        LogUtil.d(TAG, "getRecommendPlayList onSubscribe");
                    }

                    @Override
                    public void onNext(MainRecommendPlayListBean recommendPlayListBean) {
//                        LogUtil.d(TAG, "onNext" + recommendPlayListBean.toString());
                        mView.onGetRecommendPlayListSuccess(recommendPlayListBean);
                    }

                    @Override
                    public void onError(Throwable e) {
//                        LogUtil.e(TAG, "onError:" + e);
                        mView.onGetRecommendPlayListFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

//                        LogUtil.d(TAG, "getRecommendPlayList onComplete");
                    }
                });

    }

    @Override
    public void getDailyRecommend() {

    }

    @Override
    public void getTopList() {

    }

    @Override
    public void getPlayList(String type, int limit) {

    }

    @Override
    public void getPlaylistDetail(long id) {

    }

    @Override
    public void getMusicCanPlay(long id) {


    }

    @Override
    public void getHighQuality(int limit, long before) {

    }
}
