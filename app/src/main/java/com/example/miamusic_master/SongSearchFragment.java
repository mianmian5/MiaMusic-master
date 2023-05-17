package com.example.miamusic_master;

import static com.example.miamusic_master.App.getContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miamusic_master.adapter.SongListAdapter;
import com.example.miamusic_master.bean.SongSearchBean;
import com.example.miamusic_master.util.ClickUtil;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.lzx.starrysky.SongInfo;
import com.lzx.starrysky.StarrySky;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 单曲搜索结果 type = 1
 */
@SuppressLint("ValidFragment")
public class SongSearchFragment extends Fragment {
    private static final String TAG = "SongSearchFragment";
    private String SONG_URL="http://music.163.com/song/media/outer/url?id=";

    @BindView(R.id.rv_song_search)
    RecyclerView rvSong;

    private String type;
    private String keywords;
    private int searchType = 1;
    private SongListAdapter adapter;
    private List<SongSearchBean.ResultBean.SongsBean> resultBeans = new ArrayList<>();
    private boolean needRefresh = false;
    private List<SongInfo> songInfos = new ArrayList<>();
//    private SearchResultActivity.MyListener myListener;

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//
//        if (context instanceof SearchResultActivity.MyListener) {
//            myListener = (SearchResultActivity.MyListener) context;
//        } else {
//            throw new RuntimeException(context.toString() + " must implement MyListener");
//        }
//    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_song, container, false);
        ButterKnife.bind(this, rootView);
        initData();
//        //获取Bundle 然后获取数据
//        Bundle bundle =this.getArguments();//得到从Activity传来的数据
//        if(bundle!=null){
//            keywords = bundle.getString("myData");
//        }
        // 从Bundle中获取数据
//        keywords = getArguments().getString("myData");
//        EventB us.getDefault().register(this);
        return rootView;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        keywords = ((SearchResultActivity)context).toValue();
    }


//    @Override
    protected void initData() {
        resultBeans.clear();

        adapter = new SongListAdapter(getContext());
        adapter.setType(3);
        adapter.setKeywords(keywords);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rvSong.setLayoutManager(manager);
        rvSong.setAdapter(adapter);


        if (keywords != null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://service-n9pb0may-1318194552.gz.apigw.tencentcs.com/release/")
//                .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            // 创建API接口实例
            SearchService searchService = retrofit.create(SearchService.class);

            Call<SongSearchBean> call = searchService.getSongSearch(keywords,1);

            call.enqueue(new Callback<SongSearchBean>() {
                @Override
                public void onResponse(Call<SongSearchBean> call, retrofit2.Response<SongSearchBean> response) {
//                    Toast.makeText(getApplication(), "歌曲搜索请求成功！", Toast.LENGTH_SHORT).show();
                    System.out.println("歌曲搜索结果"+response.body().getResult());
//                searchDetailBean = bean;
                    resultBeans.clear();
                    if (response.body().getResult().getSongs() != null) {
                        resultBeans.addAll(response.body().getResult().getSongs());
                    }
                    songInfos.clear();
                    for (int i = 0; i < resultBeans.size(); i++) {
                        SongInfo songInfo = new SongInfo();
                        songInfo.setSongId(String.valueOf(resultBeans.get(i).getId()));
                        songInfo.setArtist(resultBeans.get(i).getArtists().get(0).getName());
                        songInfo.setSongCover(resultBeans.get(i).getArtists().get(0).getPicUrl() != null ? resultBeans.get(i).getArtists().get(0).getPicUrl() :
                                resultBeans.get(i).getArtists().get(0).getImg1v1Url());
                        songInfo.setSongName(resultBeans.get(i).getName());
                        songInfo.setSongUrl(SONG_URL + resultBeans.get(i).getId() + ".mp3");
                        songInfo.setDuration(resultBeans.get(i).getDuration());
                        songInfo.setArtist(String.valueOf(resultBeans.get(i).getArtists().get(0).getName()));
//                        songInfo.setArtistKey(resultBeans.get(i).getArtists().get(0).getPicUrl());
                        songInfos.add(songInfo);
                    }
                    adapter.notifyDataSetChanged(songInfos);

                }


                @Override
                public void onFailure(Call<SongSearchBean> call, Throwable t) {

                    Log.e(TAG, "onFailure: " + t.getMessage());
                }


            });

        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        LogUtil.d(TAG, "onResume");
    }



    @OnClick({R.id.rl_playall})
    public void onClick(View v) {
        if (ClickUtil.isFastClick(1000, v)) {
            return;
        }
        switch (v.getId()) {
            case R.id.rl_playall:
                StarrySky.with().playMusic(songInfos,0);

//                SongPlayManager.getInstance().clickPlayAll(songInfos, 0);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }



//    @Override
    public void onGetSongSearchSuccess(SongSearchBean bean) {
//        hideDialog();
//        LogUtil.d(TAG, "onGetSongSearchSuccess : " + bean);
        resultBeans.clear();
        if (bean.getResult().getSongs() != null) {
            resultBeans.addAll(bean.getResult().getSongs());
        }
        songInfos.clear();
        for (int i = 0; i < resultBeans.size(); i++) {
            SongInfo songInfo = new SongInfo();
            songInfo.setSongId(String.valueOf(resultBeans.get(i).getId()));
            songInfo.setArtist(resultBeans.get(i).getArtists().get(0).getName());
            songInfo.setSongCover(resultBeans.get(i).getArtists().get(0).getPicUrl() != null ? resultBeans.get(i).getArtists().get(0).getPicUrl() :
                    resultBeans.get(i).getArtists().get(0).getImg1v1Url());
            songInfo.setSongName(resultBeans.get(i).getName());
//            songInfo.setSongUrl(SONG_URL + resultBeans.get(i).getId() + ".mp3");
//            songInfo.setDuration(resultBeans.get(i).getDuration());
//            songInfo.setArtistId(String.valueOf(resultBeans.get(i).getArtists().get(0).getId()));
//            songInfo.setArtistKey(resultBeans.get(i).getArtists().get(0).getPicUrl());
//            songInfos.add(songInfo);
        }
        adapter.notifyDataSetChanged(songInfos);
    }



}
