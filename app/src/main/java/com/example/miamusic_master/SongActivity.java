package com.example.miamusic_master;

//import static com.rikkathewrold.rikkamusic.main.mvp.view.PlayListActivity.COMPLETED;

import static com.example.miamusic_master.App.getContext;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.miamusic_master.adapter.MyPagerAdapter;
import com.example.miamusic_master.bean.BannerBean;
import com.example.miamusic_master.bean.LyricBean;
import com.example.miamusic_master.bean.MainRecomListBean;
import com.example.miamusic_master.bean.PlaylistDetailBean;
import com.example.miamusic_master.bean.SongDetailBean;
import com.example.miamusic_master.bean.SongUrlBean;
import com.google.common.eventbus.EventBus;
import com.lzx.starrysky.SongInfo;
//import com.lzx.starrysky.StarrySky;
import com.lzx.starrysky.StarrySky;
import com.lzx.starrysky.utils.TimerTaskManager;
//import com.tobery.musicplay.MusicPlay;
//import com.tobery.musicplay.util.PermissionChecks;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.zhengken.lyricview.LyricView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
//import jp.wasabeef.glide.transformations.BlurTransformation;


public class SongActivity extends AppCompatActivity {
    private static final String TAG = "SongActivity";
    private LyricBean lyricBean;
    public static final String SONG_INFO = "songInfo";

    @BindView(R.id.iv_record)
    CircleImageView ivRecord;
    @BindView(R.id.iv_like)
    ImageView ivLike;
    @BindView(R.id.tv_past_time)
    TextView tvPastTime;
    @BindView(R.id.total_time)
    TextView tvTotalTime;
//    @BindView(R.id.seek_bar)
//    AppCompatSeekBar seekBar;
    @BindView(R.id.iv_play)
    ImageView ivPlay;
    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.iv_play_mode)
    ImageView ivPlayMode;
    @BindView(R.id.ll_info)
    LinearLayout llInfo;
    @BindView(R.id.lrc)
    LyricView lrc;

    private SongInfo currentSongInfo;
    private long ids;
//    private SongDetailBean songDetail;
    private TimerTaskManager mTimerTask;
    private boolean isLike = false;
    private int playMode;
    private ObjectAnimator rotateAnimator;
    private ObjectAnimator alphaAnimator;
    public MediaPlayer mediaPlayer;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        initData();
//        StarrySky.init(getApplication()).apply();//初始化音乐框架
//        StarrySky.init(this);
        ButterKnife.bind(this); // 黄油刀绑定视图 千万别漏掉
//        checks = new PermissionChecks(this);
        LyricView lrc = (LyricView)findViewById(R.id.lrc);
        String coverUrl = currentSongInfo.getSongCover();
        Glide.with(this)
                .load(coverUrl)
                .placeholder(R.drawable.shape_record)
                .into(ivRecord);
        Glide.with(this)
                .load(coverUrl)
                .transition(new DrawableTransitionOptions().crossFade(1500))
                .into(ivBg);
//        String a =currentSongInfo.getSongUrl();
//        StarrySky.with().playMusicByUrl(a);
         mediaPlayer = new MediaPlayer();


    }



//    @Override
    protected void initData() {
//        LogUtil.d(TAG, "initData");
        getIntentData();
//        setBackBtn(getString(R.string.colorWhite));
//        playMode = SongPlayManager.getInstance().getMode();
//        mTimerTask = new TimerTaskManager();
//        initTimerTaskWork();

        checkMusicState();
//        setSongDetailBean(songDetail);
    }



    private void getIntentData() {
        Intent intent = getIntent();
        currentSongInfo = intent.getParcelableExtra(SONG_INFO);
        System.out.println("尝试播放："+currentSongInfo.getSongUrl());

//        MusicPlay.playMusicByUrl(currentSongInfo.getSongUrl());
//        StarrySky.init(getApplication()).apply();//初始化音乐框架
//        StarrySky.with().playMusicById(currentSongInfo.getSongId());
//        StarrySky.with().playMusicByInfo(currentSongInfo);

    }

    private void checkMusicState() {
//        StarrySky.with().playMusicByInfo(currentSongInfo);
//        LogUtil.d(TAG, "currentSongInfo : " + currentSongInfo);
        setSongInfo(currentSongInfo.getSongName(), currentSongInfo.getArtist());
//        if (judgeContainsStr(currentSongInfo.getSongId())) {
//            llInfo.setVisibility(View.GONE);
//        } else {
//            获取歌词
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://service-m99y4afi-1323400135.gz.tencentapigw.com/release/")
//                .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            // 创建API接口实例
            LyricService LyricService = retrofit.create(LyricService.class);
            SongUrlService songUrlService = retrofit.create(SongUrlService.class);


            Call<LyricBean> call = LyricService.getLyric(Long.parseLong(currentSongInfo.getSongId()));
            Call<SongUrlBean> call2 =songUrlService.getSongUrl(Long.parseLong(currentSongInfo.getSongId()));

            call.enqueue(new Callback<LyricBean>() {
                @Override
                public void onResponse(Call<LyricBean> call, Response<LyricBean> response) {
//                    Toast.makeText(SongActivity.class, "歌词请求成功！", Toast.LENGTH_SHORT).show();
                    System.out.println(response.body().getTlyric());

                    if (response.body().getLrc().getLyric() != null) {
                        String lyrics = response.body().getLrc().getLyric(); // 从服务器获取到的歌词
                        //将歌词写入文件
                        File file = new File(getExternalFilesDir(null).getAbsolutePath() + File.separator + "lyrics.lrc");
                        LyricView lrc = findViewById(R.id.lrc);

                        try {
                            FileOutputStream fos = new FileOutputStream(file);
                            fos.write(lyrics.getBytes());
                            fos.close();
                            lrc.setLyricFile(file.getAbsoluteFile());
                            Toast.makeText(SongActivity.this, "歌词请求成功！", Toast.LENGTH_SHORT).show();
                            lrc.setOnPlayerClickListener(new LyricView.OnPlayerClickListener() {
                                @Override
                                public void onPlayerClicked(long progress, String content) {
                                    Toast.makeText(SongActivity.this, "你点击了歌词", Toast.LENGTH_SHORT).show();


                                }
                            });
//                                lrc.play(); // 显示歌词
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {
//                            lrc.loadLrc(bean.getLrc().getLyric(), "");
                        }



                }


                @Override
                public void onFailure(Call<LyricBean> call, Throwable t) {

                    Log.e(TAG, "onFailure: " + t.getMessage());
                }


            });
        call2.enqueue(new Callback<SongUrlBean>() {
            @Override
            public void onResponse(Call<SongUrlBean> call, Response<SongUrlBean> response) {
                Toast.makeText(getApplication(), "歌曲url获取成功", Toast.LENGTH_SHORT).show();
                System.out.println("歌曲url："+response.body().getData().get(0).getUrl());
                currentSongInfo.setSongUrl(response.body().getData().get(0).getUrl());

                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mediaPlayer.setDataSource(response.body().getData().get(0).getUrl());
                    mediaPlayer.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                    }
                });



            }


            @Override
            public void onFailure(Call<SongUrlBean> call, Throwable t) {

                Log.e(TAG, "onFailure: " + t.getMessage());
            }


        });



        long duration = currentSongInfo.getDuration();


    }
    public void setSongInfo(String songName, String singerName) {
        RelativeLayout rlSong = findViewById(R.id.rl_song_info);
        TextView tvSongName = findViewById(R.id.tv_songname);
        TextView tvSingerName = findViewById(R.id.tv_singername);
        tvSongName.setText(songName);
        tvSingerName.setText(singerName);
    }





    @OnClick({R.id.iv_play, R.id.iv_like, R.id.iv_download, R.id.iv_comment, R.id.iv_info,
            R.id.iv_play_mode, R.id.iv_pre, R.id.iv_next, R.id.iv_list, R.id.rl_center, R.id.lrc})
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.rl_center:
                showLyrics(true);
                break;
            case R.id.iv_play:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
                else{
                    mediaPlayer.start();
                }

                break;
            case R.id.iv_like:
                if (isLike) {
                    Toast.makeText(SongActivity.this, "暂时无法取消喜欢", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SongActivity.this, "未登录无法喜欢该音乐", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_download:
                Toast.makeText(SongActivity.this, "暂时无法下载", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_comment:

                intent.setClass(SongActivity.this, CommentActivity.class);
                intent.putExtra(CommentActivity.ID, Long.parseLong(currentSongInfo.getSongId()));
                intent.putExtra(CommentActivity.NAME, currentSongInfo.getSongName());
                intent.putExtra(CommentActivity.ARTIST, currentSongInfo.getArtist());
                intent.putExtra(CommentActivity.COVER, currentSongInfo.getSongCover());
                intent.putExtra(CommentActivity.FROM, CommentActivity.SONG_COMMENT);
                startActivity(intent);
                break;
            case R.id.iv_info:
                intent.setClass(SongActivity.this, SongDetailActivity.class);
                intent.putExtra(SONG_INFO, currentSongInfo);
                startActivity(intent);
                overridePendingTransition(R.anim.bottom_in, R.anim.bottom_silent);
                break;
            case R.id.iv_play_mode:
                break;
            case R.id.iv_pre:

                break;
            case R.id.iv_next:

                break;
            case R.id.iv_list:

                break;
        }
    }

    //根据isShowLyrics来判断是否展示歌词
    private void showLyrics(boolean isShowLyrics) {
        ivRecord.setVisibility(isShowLyrics ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }




}
