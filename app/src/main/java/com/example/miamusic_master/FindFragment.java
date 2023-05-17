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

public class FindFragment extends BaseFragment<FindPresenter> implements WowContract.View {
    private static final String TAG = "WowFragment";
    String BASE_URL = "https://service-n9pb0may-1318194552.gz.apigw.tencentcs.com/release/";
    public static final String PLAYLIST_NAME = "playlistName";
    public static final String PLAYLIST_PICURL = "playlistPicUrl";
    public static final String PLAYLIST_CREATOR_NICKNAME = "playlistCreatorNickname";
    public static final String PLAYLIST_CREATOR_AVATARURL = "playlistCreatorAvatarUrl";
    public static final String PLAYLIST_ID = "playlistId";
    private RequestQueue mRequestQueue;
    @BindView(R.id.wow_banner)
    Banner banner;
    @BindView(R.id.rv_recommend_playlist)
    RecyclerView rvRecommendPlayList;


    private PlayListAdapter recommendPlayListAdapter;
    //banner的图片集合
    List bannerImageList = new ArrayList<>();
//    private List<BannerItem> bannerItems;
    //banner集合
    List<BannerBean.BannersBean> banners = new ArrayList<>();
    //推荐歌单集合
    List<MainRecomListBean.ResultBean> recommends = new ArrayList<>();
    List<PlaylistBean> list = new ArrayList<>();
//    private static ApiService apiService;
    private ViewPager mViewPager;
    public FindFragment() {
        setFragmentTitle("发现");
    }



    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        LogUtil.d(TAG, "initView  isPrepared：" + isPrepared() + " isFragmentVisible：" + isFragmentVisible());
        View rootView = inflater.inflate(R.layout.fragment_find, container, false);
        ButterKnife.bind(this, rootView);
        Banner banner = rootView.findViewById(R.id.wow_banner);
        mViewPager = rootView.findViewById(R.id.view_pager);


//        // 创建Retrofit实例
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(20, TimeUnit.SECONDS)
//                .readTimeout(20, TimeUnit.SECONDS)
//                .writeTimeout(20, TimeUnit.SECONDS)
//
////                .cache(cache)
//                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://service-n9pb0may-1318194552.gz.apigw.tencentcs.com/release/")
//                .client(client)
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
//                for (int i = 0; i < banners.size(); i++) {
//                    try {
//                        URL url = new URL(banners.get(i).getPic());
//                        bannerImageList.add(url);
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    }
//                }
                loadImageToList();
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
//                List<BannerBean.BannersBean> bannersnew = new ArrayList<>();
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





//    }
//使用Volley从服务端获取轮播图url
//private void fetchImagesWithVolley() {
//    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, BASE_URL + "banner", null, new Response.Listener<JSONArray>() {
//
//        public void onResponse(JSONArray response) {
//            //获取成功，输出轮播图url
//            for (int i = 0; i < response.length(); i++) {
//                try {
//                    Log.d("Volley", response.getJSONObject(i).getString("url"));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    });
//
//    // 设置重试策略
//    jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
//            5000,
//            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//    mRequestQueue.add(jsonArrayRequest);
//}

    //使用Retrofit2从服务端获取轮播图url


    protected void initData() {

        list.clear();
        recommends.clear();
        recommendPlayListAdapter = new PlayListAdapter(getContext());
        recommendPlayListAdapter.setType(1);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 3);
        rvRecommendPlayList.setLayoutManager(manager);
        rvRecommendPlayList.setHasFixedSize(true);
        rvRecommendPlayList.setAdapter(recommendPlayListAdapter);
//        fetchImagesWithRetrofit();


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
//            String playlistCreatorNickname = bean.getCopywriter();
//            intent.putExtra(PLAYLIST_CREATOR_NICKNAME, playlistCreatorNickname);
//            String playlistCreatorAvatarUrl = bean.getCreator().getAvatarUrl();
//            intent.putExtra(PLAYLIST_CREATOR_AVATARURL, playlistCreatorAvatarUrl);
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


    @Override
    public void onGetBannerSuccess(BannerBean bean) {

        banners.addAll(bean.getBanners());
        loadImageToList();
        banner.setDatas(bannerImageList).start();
        Toast.makeText(getContext(), "请求成功", Toast.LENGTH_SHORT).show();
//        loadImageToList();
        System.out.println(bannerImageList);


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
        Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
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
//        recommends.addAll(bean.getRecommend());
//        for (int i = 0; i < recommends.size(); i++) {
//            PlaylistBean beanInfo = new PlaylistBean();
//            beanInfo.setPlaylistName(recommends.get(i).getName());
//            beanInfo.setPlaylistCoverUrl(recommends.get(i).getPicUrl());
//            list.add(beanInfo);
//        }
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
