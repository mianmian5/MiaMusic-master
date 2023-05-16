package com.example.miamusic_master;

import static com.example.miamusic_master.FindFragment.PLAYLIST_ID;
import static com.example.miamusic_master.FindFragment.PLAYLIST_NAME;
import static com.example.miamusic_master.FindFragment.PLAYLIST_PICURL;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.miamusic_master.adapter.MyPagerAdapter;
import com.example.miamusic_master.adapter.SongListAdapter;
//import com.example.miamusic_master.base.BaseActivity;
import com.example.miamusic_master.bean.BannerBean;
import com.example.miamusic_master.bean.DailyRecommendBean;
import com.example.miamusic_master.bean.HighQualityPlayListBean;
import com.example.miamusic_master.bean.MainRecomListBean;
import com.example.miamusic_master.bean.MainRecommendPlayListBean;
import com.example.miamusic_master.bean.PlaylistDetailBean;
import com.example.miamusic_master.bean.RecommendPlayListBean;
import com.example.miamusic_master.bean.TopListBean;
import com.example.miamusic_master.contract.WowContract;
import com.example.miamusic_master.presenter.FindPresenter;
import com.example.miamusic_master.widget.RikkaRoundRectView;
import com.google.android.material.appbar.AppBarLayout;
import com.lzx.starrysky.SongInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 歌单列表界面
 * 该界面就是充满歌曲的歌单列表

 */
public class PlayListActivity extends AppCompatActivity {
    private static final String TAG = "PlayListActivity";
    private String SONG_URL="http://music.163.com/song/media/outer/url?id=";
    //计算完成后发送的Handler msg
    public static final int COMPLETED = 0;

    @BindView(R.id.iv_cover)
    RikkaRoundRectView ivCover;
    @BindView(R.id.tv_playlist_name)
    TextView tvListName;
    @BindView(R.id.iv_creator_avatar)
    CircleImageView ivCreatorAvatar;
    @BindView(R.id.tv_creator_name)
    TextView tvCreatorName;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.rv_playlist_song)
    RecyclerView rvPlaylist;
//    @BindView(R.id.bottom_controller)
//    BottomSongPlayBar bottomController;
    @BindView(R.id.background)
    ImageView ivBg;
    @BindView(R.id.appbar)
    AppBarLayout appBar;
    @BindView(R.id.ll_play)
    RelativeLayout llPlay;
    @BindView(R.id.iv_cover_bg)
    ImageView ivCoverBg;

    private SongListAdapter adapter;
    private List<PlaylistDetailBean.PlaylistBean.TracksBean> beanList = new ArrayList<>();

    private List<SongInfo> songInfos = new ArrayList<>();
    private long playlistId;
    private int position = -1;

    private String creatorUrl;
    private ObjectAnimator alphaAnimator;
    private ObjectAnimator coverAlphaAnimator;
    private String playlistName;
    private String playlistPicUrl;
    private String creatorName;

//    public PlayListActivity(SongListAdapter adapter) {
//        this.adapter = adapter;
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);
        ButterKnife.bind(this); // 黄油刀绑定视图 千万别漏掉
        initData();
    }




    protected void initData() {
        beanList.clear();
        adapter = new SongListAdapter(this);
        adapter.setType(2);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvPlaylist.setLayoutManager(manager);
        rvPlaylist.setAdapter(adapter);
        if (getIntent() != null) {
            playlistPicUrl = getIntent().getStringExtra(PLAYLIST_PICURL);
            Glide.with(this).load(playlistPicUrl).into(ivCover);
            playlistName = getIntent().getStringExtra(PLAYLIST_NAME);
            tvListName.setText(playlistName);
            playlistId = getIntent().getLongExtra(PLAYLIST_ID, 0);
            Glide.with(this)
                    .load(playlistPicUrl)
                    .into(ivCoverBg);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://service-n9pb0may-1318194552.gz.apigw.tencentcs.com/release/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            // 创建API接口实例
            RecomdetailService recomdetailService = retrofit.create(RecomdetailService.class);
            Call<PlaylistDetailBean> call = recomdetailService.getPlaylistDetail(playlistId);


            call.enqueue(new Callback<PlaylistDetailBean>() {
                @Override
                public void onResponse(Call<PlaylistDetailBean> call, Response<PlaylistDetailBean> response) {
                    Toast.makeText(getApplication(), "歌单详情获取成功", Toast.LENGTH_SHORT).show();
//                List<BannerBean.BannersBean> bannersnew = new ArrayList<>();
                    System.out.println("歌单详情："+response.body());
                    beanList.addAll(response.body().getPlaylist().getTracks());
                    for (int i = 0; i < beanList.size(); i++) {
                        SongInfo beanInfo = new SongInfo();
                        beanInfo.setArtist(beanList.get(i).getAr().get(0).getName());
                        beanInfo.setSongName(beanList.get(i).getName());
                        beanInfo.setSongCover(beanList.get(i).getAl().getPicUrl());
                        beanInfo.setSongId(String.valueOf(beanList.get(i).getId()));
                        beanInfo.setDuration(beanList.get(i).getDt());
                        beanInfo.setSongUrl(SONG_URL + beanList.get(i).getId() + ".mp3");
                        beanInfo.setArtist(String.valueOf(beanList.get(i).getAr().get(0).getName()));
//                        beanInfo.setArtistKey(beanList.get(i).getAl().getPicUrl());
                        songInfos.add(beanInfo);
                    }
                    System.out.println(songInfos);
                    adapter.notifyDataSetChanged(songInfos);
                    tvShare.setText(response.body().getPlaylist().getShareCount() + "");
                    tvComment.setText(response.body().getPlaylist().getCommentCount() + "");




                }


                @Override
                public void onFailure(Call<PlaylistDetailBean> call, Throwable t) {

                    Log.e(TAG, "onFailure: " + t.getMessage());
                }


            });
        }

    }

//    @SuppressLint("HandlerLeak")
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            if (msg.what == COMPLETED) {
//                ivBg.setBackground((Drawable) msg.obj);
//                getAlphaAnimatorBg().start();
//                getAlphaAnimatorCover().start();
//            }
//        }
//    };
//
//    private ObjectAnimator getAlphaAnimatorCover() {
//        if (coverAlphaAnimator == null) {
//            coverAlphaAnimator = ObjectAnimator.ofFloat(ivCoverBg, "alpha", 0f, 0.5f);
//            coverAlphaAnimator.setDuration(1500);
//            coverAlphaAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
//        }
//        return coverAlphaAnimator;
//    }

//    private ObjectAnimator getAlphaAnimatorBg() {
//        if (alphaAnimator == null) {
//            alphaAnimator = ObjectAnimator.ofFloat(ivBg, "alpha", 0f, 0.5f);
//            alphaAnimator.setDuration(1500);
//            alphaAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
//        }
//        return alphaAnimator;
//    }

    /**
     * 该方法用url申请一个图片bitmap，并将其压缩成原图1/300，计算上半部分和下半部分颜色RGB平均值
     * 两个RGB去作为渐变色的两个点
     * 还要开子线程去计算...
     */
//    public void calculateColors(String url) {
//        new Thread(() -> {
//            try {
//                //渐变色的两个颜色
//                URL fileUrl;
//                Bitmap bitmap;
//                fileUrl = new URL(url);
//                HttpURLConnection conn = (HttpURLConnection) fileUrl.openConnection();
//                conn.setDoInput(true);
//                conn.connect();
//                InputStream is = conn.getInputStream();
//                BitmapFactory.Options opt = new BitmapFactory.Options();
//                opt.inSampleSize = 270;
//                bitmap = BitmapFactory.decodeStream(is, new Rect(), opt);
//
//                Message msg = Message.obtain();
//                msg.what = COMPLETED;
//                msg.obj = new BitmapDrawable(bitmap);
////                handler.sendMessage(msg);
//
//                is.close();
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }).start();
//    }

//    @Override
    @OnClick({R.id.rl_playall, R.id.rl_comment, R.id.rl_download})
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.rl_playall:
//                SongPlayManager.getInstance().clickPlayAll(songInfos, 0);
                break;
            case R.id.rl_download:
//                ToastUtils.show("下载不了哦~");
                break;
            case R.id.rl_comment:
//                intent.setClass(PlayListActivity.this, CommentActivity.class);
//                intent.putExtra(CommentActivity.FROM, CommentActivity.PLAYLIST_COMMENT);
//                intent.putExtra(CommentActivity.ID, playlistId);
//                intent.putExtra(CommentActivity.NAME, playlistName);
//                intent.putExtra(CommentActivity.ARTIST, creatorName);
//                intent.putExtra(CommentActivity.COVER, playlistPicUrl);
//                startActivity(intent);
                break;
        }
    }

//    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (alphaAnimator != null) {
            if (alphaAnimator.isRunning()) {
                alphaAnimator.cancel();
            }
            alphaAnimator = null;
        }
        if (coverAlphaAnimator != null) {
            if (coverAlphaAnimator.isRunning()) {
                coverAlphaAnimator.cancel();
            }
            coverAlphaAnimator = null;
        }
    }

//    @Override
    public void onGetBannerSuccess(BannerBean bean) {

    }

//    @Override
    public void onGetBannerFail() {
        onGetBannerFail(null);
    }

//    @Override
    public void onGetBannerFail(String e) {

    }

//    @Override
    public void onGetRecommendPlayListSuccess(MainRecommendPlayListBean bean) {

    }

//    @Override
    public void onGetRecommendPlayListFail(String e) {

    }

//    @Override
    public void onGetDailyRecommendSuccess(DailyRecommendBean bean) {

    }

//    @Override
//    public void onGetDailyRecommendSuccess(DailyRecommendBean bean) {
//
//    }

//    @Override
    public void onGetDailyRecommendFail(String e) {

    }






//    @Override
    public void onGetPlaylistDetailFail(String e) {

//        LogUtil.e(TAG, "onGetPlaylistDetailFail : " + e);
//        ToastUtils.show(e);
    }

//    @Override
    public void onGetMusicCanPlayFail(String e) {
    }

//    @Override
    public void onGetHighQualitySuccess(HighQualityPlayListBean bean) {

    }

//    @Override
    public void onGetHighQualityFail(String e) {

    }
}
