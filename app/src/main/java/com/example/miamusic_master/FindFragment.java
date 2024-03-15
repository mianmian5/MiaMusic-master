package com.example.miamusic_master;
//import static com.example.miamusic_master.api.ApiService.BASE_URL;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
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
import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

//import com.example.miamusic_master.adapter.PlayListAdapter;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.miamusic_master.adapter.MyPagerAdapter;
import com.example.miamusic_master.adapter.PlayListAdapter;
//import com.example.miamusic_master.api.ApiService;
import com.example.miamusic_master.bean.BannerBean;
import com.example.miamusic_master.bean.DailyRecommendBean;
import com.example.miamusic_master.bean.HighQualityPlayListBean;
import com.example.miamusic_master.bean.MainRecomListBean;
import com.example.miamusic_master.bean.MainRecommendPlayListBean;
import com.example.miamusic_master.bean.PlaylistBean;
import com.example.miamusic_master.bean.PlaylistDetailBean;
import com.example.miamusic_master.bean.RecommendPlayListBean;
import com.example.miamusic_master.bean.TopListBean;
import com.example.miamusic_master.contract.WowContract;
//import com.example.miamusic_master.presenter.FindPresenter;
import com.example.miamusic_master.base.BaseFragment;
import com.example.miamusic_master.presenter.FindPresenter;
import com.example.miamusic_master.util.ClickUtil;

import com.google.androidgamesdk.gametextinput.Listener;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;


import org.json.JSONArray;
import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class FindFragment extends BaseFragment  {
    private static final String TAG = "WowFragment";
    String BASE_URL = "https://service-n9pb0may-1318194552.gz.apigw.tencentcs.com/release/";
    public static final String PLAYLIST_NAME = "playlistName";
    public static final String PLAYLIST_PICURL = "playlistPicUrl";
    public static final String PLAYLIST_ID = "playlistId";
    private RequestQueue mRequestQueue;
    @BindView(R.id.wow_banner)
    Banner banner;
    @BindView(R.id.rv_recommend_playlist)
    RecyclerView rvRecommendPlayList;

    private PlayListAdapter recommendPlayListAdapter;
    //banner的图片集合
    List bannerImageList = new ArrayList<>();
    //banner集合
    List<BannerBean.BannersBean> banners = new ArrayList<>();
    //推荐歌单集合
    List<MainRecomListBean.ResultBean> recommends = new ArrayList<>();
    List<PlaylistBean> list = new ArrayList<>();
    private ViewPager mViewPager;
    public FindFragment() {
        setFragmentTitle("发现");
    }

    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_find, container, false);
        ButterKnife.bind(this, rootView);
        Banner banner = rootView.findViewById(R.id.wow_banner);
        mViewPager = rootView.findViewById(R.id.view_pager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://service-m99y4afi-1323400135.gz.tencentapigw.com/release/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        // 创建API接口实例
        BannerService bannerService = retrofit.create(BannerService.class);
        RecommendService recommendService = retrofit.create(RecommendService.class);

        Call<BannerBean> call = bannerService.getBanner();
        Call<MainRecomListBean> call2 = recommendService.getRecommendPlayList();

        call.enqueue(new Callback<BannerBean>() {
            @Override
            public void onResponse(Call<BannerBean> call, Response<BannerBean> response) {
                Toast.makeText(getContext(), "轮播图请求成功！", Toast.LENGTH_SHORT).show();
//                List<BannerBean.BannersBean> bannersnew = new ArrayList<>();
                System.out.println(response.body().getBanners());
                banners.addAll(response.body().getBanners());
                loadImageToList();//存入图片
                System.out.println(bannerImageList);
                MyPagerAdapter adapter = new MyPagerAdapter(getContext(), bannerImageList);
                mViewPager.setAdapter(adapter);
                mViewPager.setCurrentItem(0); // 设置当前要显示的图片的位置

            }

            @Override
            public void onFailure(Call<BannerBean> call, Throwable t) {

                Log.e(TAG, "onFailure: " + t.getMessage());
            }


        });
        call2.enqueue(new Callback<MainRecomListBean>() {
            @Override
            public void onResponse(Call<MainRecomListBean> call2, Response<MainRecomListBean> response) {
                Toast.makeText(getContext(), "推荐歌单请求成功！", Toast.LENGTH_SHORT).show();
                System.out.println(response.body());
                recommends.addAll(response.body().getResult());
                System.out.println(recommends);
                for (int i = 0; i < recommends.size(); i++) {
                    PlaylistBean beanInfo = new PlaylistBean();
                    beanInfo.setPlaylistName(recommends.get(i).getName());
                    beanInfo.setPlaylistCoverUrl(recommends.get(i).getPicUrl());
                    list.add(beanInfo);
                }
                recommendPlayListAdapter.setListener(listClickListener);
                recommendPlayListAdapter.notifyDataSetChanged(list);

            }

            @Override
            public void onFailure(Call<MainRecomListBean> call2, Throwable t) {

                Log.e(TAG, "onFailure: " + t.getMessage());
            }


        });
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

    }
    private PlayListAdapter.OnPlayListClickListener listClickListener = position -> {
        if (recommends != null && !recommends.isEmpty()) {
            //进入歌单详情页面
            Intent intent = new Intent(getActivity(), PlayListActivity.class);
            MainRecomListBean.ResultBean bean = recommends.get(position);
            String playlistName = bean.getName();
            intent.putExtra(PLAYLIST_NAME, playlistName);
            String playlistPicUrl = bean.getPicUrl();
            intent.putExtra(PLAYLIST_PICURL, playlistPicUrl);
            Long playlistId = bean.getId();
            intent.putExtra(PLAYLIST_ID, playlistId);
            startActivity(intent);
        }
    };

    @Override
    public FindPresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected void initVariables(Bundle bundle) {

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
            case R.id.rl_play_list:
            case R.id.rl_rank:
            case R.id.rl_radio:
            case R.id.rl_live:
            case R.id.tv_playlist_playground:
                Toast.makeText(getContext(), "功能开发中！", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
