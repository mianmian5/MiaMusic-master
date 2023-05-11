package com.example.miamusic_master;

import static com.example.miamusic_master.App.getContext;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

//import com.hjq.toast.ToastUtils;
//import com.example.miamusic_master.App;
//import com.example.miamusic_master.base.BaseFragment;
//import com.example.miamusic_master.dj.mvp.view.RadioRecommendActivity;
//import com.example.miamusic_master.main.adapter.PlayListAdapter;
//import com.example.miamusic_master.main.bean.BannerBean;
//import com.example.miamusic_master.main.bean.DailyRecommendBean;
//import com.example.miamusic_master.main.bean.HighQualityPlayListBean;
//import com.example.miamusic_master.main.bean.MainRecommendPlayListBean;
//import com.example.miamusic_master.main.bean.PlaylistBean;
//import com.example.miamusic_master.main.bean.PlaylistDetailBean;
//import com.example.miamusic_master.main.bean.RecommendPlayListBean;
//import com.example.miamusic_master.main.bean.TopListBean;
//import com.example.miamusic_master.main.mvp.contract.WowContract;
//import com.example.miamusic_master.main.mvp.presenter.WowPresenter;
//import com.example.miamusic_master.main.mvp.view.DailyRecommendActivity;
//import com.example.miamusic_master.main.mvp.view.PlayListActivity;
//import com.example.miamusic_master.main.mvp.view.PlayListRecommendActivity;
//import com.example.miamusic_master.main.mvp.view.RankActivity;
//import com.example.miamusic_master.manager.bean.MusicCanPlayBean;
//import com.example.miamusic_master.util.BannerGlideImageLoader;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miamusic_master.App;
import com.example.miamusic_master.R;
//import com.example.miamusic_master.adapter.PlayListAdapter;
import com.example.miamusic_master.adapter.PlayListAdapter;
import com.example.miamusic_master.bean.AlbumSublistBean;
import com.example.miamusic_master.bean.BannerBean;
import com.example.miamusic_master.bean.DailyRecommendBean;
import com.example.miamusic_master.bean.HighQualityPlayListBean;
import com.example.miamusic_master.bean.MainRecommendPlayListBean;
import com.example.miamusic_master.bean.PlaylistBean;
import com.example.miamusic_master.bean.PlaylistDetailBean;
import com.example.miamusic_master.bean.RecommendPlayListBean;
import com.example.miamusic_master.bean.TopListBean;
import com.example.miamusic_master.contract.WowContract;
import com.example.miamusic_master.presenter.FindPresenter;
import com.example.miamusic_master.base.BaseFragment;
import com.example.miamusic_master.util.ClickUtil;

import com.youth.banner.Banner;
import com.youth.banner.*;
import com.youth.banner.config.BannerConfig;
import com.youth.banner.indicator.CircleIndicator;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FindFragment extends BaseFragment<FindPresenter> implements WowContract.View {
    private static final String TAG = "WowFragment";

    public static final String PLAYLIST_NAME = "playlistName";
    public static final String PLAYLIST_PICURL = "playlistPicUrl";
    public static final String PLAYLIST_CREATOR_NICKNAME = "playlistCreatorNickname";
    public static final String PLAYLIST_CREATOR_AVATARURL = "playlistCreatorAvatarUrl";
    public static final String PLAYLIST_ID = "playlistId";

    @BindView(R.id.wow_banner)
    Banner banner;
    @BindView(R.id.rv_recommend_playlist)
    RecyclerView rvRecommendPlayList;

    private PlayListAdapter recommendPlayListAdapter;
    //banner的图片集合
    List<URL> bannerImageList = new ArrayList<>();
    //banner集合
    List<BannerBean.BannersBean> banners = new ArrayList<>();
    //推荐歌单集合
    List<MainRecommendPlayListBean.RecommendBean> recommends = new ArrayList<>();
    List<PlaylistBean> list = new ArrayList<>();

    public FindFragment() {
        setFragmentTitle("发现");
    }


    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        LogUtil.d(TAG, "initView  isPrepared：" + isPrepared() + " isFragmentVisible：" + isFragmentVisible());
        View rootView = inflater.inflate(R.layout.fragment_find, container, false);
        ButterKnife.bind(this, rootView);
//        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
//                .setDelayTime(4000)
//                .setImageLoader(new BannerGlideImageLoader())
//                .isAutoPlay(true);
        return rootView;
    }


    protected void initData() {

        list.clear();
        recommends.clear();
        recommendPlayListAdapter = new PlayListAdapter(getContext());
        recommendPlayListAdapter.setType(1);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 3);
        rvRecommendPlayList.setLayoutManager(manager);
        rvRecommendPlayList.setHasFixedSize(true);
        rvRecommendPlayList.setAdapter(recommendPlayListAdapter);
//        showDialog();
        if (mPresenter != null) {
            mPresenter.getBanner();
            mPresenter.getRecommendPlayList();
        }
//        mPresenter.getBanner();

    }

    @Override
    public FindPresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected void initVariables(Bundle bundle) {

    }


    @Override
    public void onGetBannerSuccess(BannerBean bean) {

        banners.addAll(bean.getBanners());
        loadImageToList();
//        banner.setImages(bannerImageList).start();


    }

    //将图片装到BannerList中
    private void loadImageToList() {
        for (int i = 0; i < banners.size(); i++) {
            try {
                URL url = new URL(banners.get(i).getPic());
                bannerImageList.add(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }


    @OnClick({R.id.rl_day_rec, R.id.rl_play_list, R.id.rl_rank, R.id.rl_radio, R.id.rl_live,
            R.id.tv_playlist_playground})
    public void onClick(View v) {
        if (ClickUtil.isFastClick(1000, v)) {
            return;
        }
        switch (v.getId()) {
            case R.id.rl_day_rec:
//                startActivity(new Intent(activity, DailyRecommendActivity.class));
                break;
            case R.id.rl_play_list:
//                startActivity(new Intent(activity, PlayListRecommendActivity.class));
                break;
            case R.id.rl_rank:
//                startActivity(new Intent(activity, RankActivity.class));
                break;
            case R.id.rl_radio:
//                startActivity(new Intent(activity, RadioRecommendActivity.class));
                break;
            case R.id.rl_live:
//                ToastUtils.show("没有提供直播接口哦，你想看我跳舞也行");
                break;
            case R.id.tv_playlist_playground:
//                startActivity(new Intent(activity, PlayListRecommendActivity.class));
                break;
        }
    }



    @Override
    public void onGetBannerFail(String e) {
        Toast.makeText(banner.getContext(), e, Toast.LENGTH_SHORT).show();
//        LogUtil.e(TAG, "onGetBannerFail : " + e);
    }

//    @Override
//    public void onGetRecommendPlayListSuccess(MainRecommendPlayListBean bean) {
//
//    }

    @Override
    public void onGetRecommendPlayListSuccess(MainRecommendPlayListBean bean) {
//        hideDialog();
//        LogUtil.d(TAG, "onGetRecommendPlayListSuccess" + bean);
        recommends.addAll(bean.getRecommend());
        for (int i = 0; i < recommends.size(); i++) {
            PlaylistBean beanInfo = new PlaylistBean();
            beanInfo.setPlaylistName(recommends.get(i).getName());
            beanInfo.setPlaylistCoverUrl(recommends.get(i).getPicUrl());
            list.add(beanInfo);
        }
//        recommendPlayListAdapter.setListener(listClickListener);
//        recommendPlayListAdapter.notifyDataSetChanged(list);
    }

//    private PlayListAdapter.OnPlayListClickListener listClickListener = position -> {
//        if (recommends != null && !recommends.isEmpty()) {
//            //进入歌单详情页面
////            Intent intent = new Intent(getActivity(), PlayListActivity.class);
//            MainRecommendPlayListBean.RecommendBean bean = recommends.get(position);
//            String playlistName = bean.getName();
//            intent.putExtra(PLAYLIST_NAME, playlistName);
//            String playlistPicUrl = bean.getPicUrl();
//            intent.putExtra(PLAYLIST_PICURL, playlistPicUrl);
//            String playlistCreatorNickname = bean.getCreator().getNickname();
//            intent.putExtra(PLAYLIST_CREATOR_NICKNAME, playlistCreatorNickname);
//            String playlistCreatorAvatarUrl = bean.getCreator().getAvatarUrl();
//            intent.putExtra(PLAYLIST_CREATOR_AVATARURL, playlistCreatorAvatarUrl);
//            long playlistId = bean.getId();
//            intent.putExtra(PLAYLIST_ID, playlistId);
//            startActivity(intent);
//        }
//    };

    @Override
    public void onGetRecommendPlayListFail(String e) {
//        hideDialog();

    }

    @Override
    public void onGetDailyRecommendSuccess(DailyRecommendBean bean) {

    }

    @Override
    public void onGetDailyRecommendFail(String e) {

    }

    @Override
    public void onGetTopListSuccess(TopListBean bean) {

    }

    @Override
    public void onGetTopListFail(String e) {

    }

    @Override
    public void onGetPlayListSuccess(RecommendPlayListBean bean) {

    }

    @Override
    public void onGetPlayListFail(String e) {

    }

    @Override
    public void onGetPlaylistDetailSuccess(PlaylistDetailBean bean) {

    }

    @Override
    public void onGetPlaylistDetailFail(String e) {

    }

    @Override
    public void onGetMusicCanPlayFail(String e) {

    }

    @Override
    public void onGetHighQualitySuccess(HighQualityPlayListBean bean) {

    }

    @Override
    public void onGetHighQualityFail(String e) {

    }


}
