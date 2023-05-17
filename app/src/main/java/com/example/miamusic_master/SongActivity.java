package com.example.miamusic_master;

//import static com.rikkathewrold.rikkamusic.main.mvp.view.PlayListActivity.COMPLETED;

import static com.example.miamusic_master.App.getContext;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import com.example.miamusic_master.bean.SongDetailBean;
import com.google.common.eventbus.EventBus;
import com.lzx.starrysky.SongInfo;
//import com.lzx.starrysky.StarrySky;
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
    private boolean isShowLyrics = false;





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
        String a =currentSongInfo.getSongUrl();
//        StarrySky.with().playMusicByUrl(a);


    }
//    checks.requestPermissions(APP_PERMISSIONS, it ->{
//        if (it){
//            //  MusicPlay.initConfig(this,new PlayConfig());
//        }else {
//
//        }
//        return null;
//    });




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
        if (judgeContainsStr(currentSongInfo.getSongId())) {
            llInfo.setVisibility(View.GONE);
        } else {
//            获取歌词
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://service-n9pb0may-1318194552.gz.apigw.tencentcs.com/release/")
//                .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            // 创建API接口实例
            LyricService LyricService = retrofit.create(LyricService.class);

            Call<LyricBean> call = LyricService.getLyric(Long.parseLong(currentSongInfo.getSongId()));

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
//                            lrc.setLyricFile(lyricFile);

//                            lrc.setLyricFile(file);
                    } else {
//                            lrc.loadLrc(bean.getLrc().getLyric(), "");
                        }



                }


                @Override
                public void onFailure(Call<LyricBean> call, Throwable t) {

                    Log.e(TAG, "onFailure: " + t.getMessage());
                }


            });
//            if (lyricBean == null) {
////                mPresenter.getLyric(Long.parseLong(currentSongInfo.getSongId()));
//            }
//            llInfo.setVisibility(View.VISIBLE);
//            ids = Long.parseLong(currentSongInfo.getSongId());
//            String songId = currentSongInfo.getSongId();

//            if (SongPlayManager.getInstance().getSongDetail(ids) == null) {
//                mPresenter.getSongDetail(ids);
//            } else {
//                songDetail = SongPlayManager.getInstance().getSongDetail(ids);
//                setSongDetailBean(songDetail);
//            }
        }

        long duration = currentSongInfo.getDuration();


    }
    public void setSongInfo(String songName, String singerName) {
        RelativeLayout rlSong = findViewById(R.id.rl_song_info);
//        rlSong.setVisibility(View.VISIBLE);
        TextView tvSongName = findViewById(R.id.tv_songname);
        TextView tvSingerName = findViewById(R.id.tv_singername);
        tvSongName.setText(songName);
        tvSingerName.setText(singerName);
    }

    /**
     * 该方法主要使用正则表达式来判断字符串中是否包含字母
     */
    public boolean judgeContainsStr(String cardNum) {
        String regex = ".*[a-zA-Z]+.*";
        Matcher m = Pattern.compile(regex).matcher(cardNum);
        return m.matches();
    }





    @OnClick({R.id.iv_play, R.id.iv_like, R.id.iv_download, R.id.iv_comment, R.id.iv_info,
            R.id.iv_play_mode, R.id.iv_pre, R.id.iv_next, R.id.iv_list, R.id.rl_center, R.id.lrc})
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.rl_center:
                isShowLyrics = true;
                showLyrics(true);
                break;
            case R.id.iv_play:
//                if (SongPlayManager.getInstance().isPlaying()) {
//                    SongPlayManager.getInstance().pauseMusic();
//                } else if (SongPlayManager.getInstance().isPaused()) {
//                    SongPlayManager.getInstance().playMusic();
//                } else if (SongPlayManager.getInstance().isIdle()) {
//                    SongPlayManager.getInstance().clickASong(currentSongInfo);
//                }
                break;
            case R.id.iv_like:
                if (isLike) {
//                    ToastUtils.show("Sorry啊，我没有找到取消喜欢的接口");
                } else {
//                    mPresenter.likeMusic(ids);
                }
                break;
            case R.id.iv_download:
//                ToastUtils.show("Sorry啊，歌都不是我的，不能下载的");
                break;
            case R.id.iv_comment:
//                if (songDetail == null) {
//                    ToastUtils.show("获取不到歌曲信息，稍后再试");
//                    return;
//                }
//                intent.setClass(SongActivity.this, CommentActivity.class);
//                intent.putExtra(CommentActivity.ID, songDetail.getSongs().get(0).getId());
//                intent.putExtra(CommentActivity.NAME, songDetail.getSongs().get(0).getName());
//                intent.putExtra(CommentActivity.ARTIST, songDetail.getSongs().get(0).getAr().get(0).getName());
//                intent.putExtra(CommentActivity.COVER, songDetail.getSongs().get(0).getAl().getPicUrl());
//                intent.putExtra(CommentActivity.FROM, CommentActivity.SONG_COMMENT);
//                startActivity(intent);
                break;
            case R.id.iv_info:
                intent.setClass(SongActivity.this, SongDetailActivity.class);
                intent.putExtra(SONG_INFO, currentSongInfo);
                startActivity(intent);
                overridePendingTransition(R.anim.bottom_in, R.anim.bottom_silent);
                break;
            case R.id.iv_play_mode:
//                if (playMode == SongPlayManager.MODE_LIST_LOOP_PLAY) {
//                    SongPlayManager.getInstance().setMode(SongPlayManager.MODE_SINGLE_LOOP_PLAY);
//                    ivPlayMode.setImageResource(R.drawable.shape_single_cycle);
//                    playMode = SongPlayManager.MODE_SINGLE_LOOP_PLAY;
//                    ToastUtils.show("切换到单曲循环模式");
//                } else if (playMode == SongPlayManager.MODE_SINGLE_LOOP_PLAY) {
//                    SongPlayManager.getInstance().setMode(SongPlayManager.MODE_RANDOM);
//                    ivPlayMode.setImageResource(R.drawable.shape_list_random);
//                    playMode = SongPlayManager.MODE_RANDOM;
//                    ToastUtils.show("切换到随机播放模式");
//                } else if (playMode == SongPlayManager.MODE_RANDOM) {
//                    SongPlayManager.getInstance().setMode(SongPlayManager.MODE_LIST_LOOP_PLAY);
//                    ivPlayMode.setImageResource(R.drawable.shape_list_cycle);
//                    playMode = SongPlayManager.MODE_LIST_LOOP_PLAY;
//                    ToastUtils.show("切换到列表循环模式");
//                }
                break;
            case R.id.iv_pre:
//                SongPlayManager.getInstance().playPreMusic();
                break;
            case R.id.iv_next:
//                SongPlayManager.getInstance().playNextMusic();
                break;
            case R.id.iv_list:
//                intent.setClass(SongActivity.this, SongListActivity.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.bottom_in, R.anim.bottom_silent);
                break;
        }
    }

    //根据isShowLyrics来判断是否展示歌词
    private void showLyrics(boolean isShowLyrics) {
        ivRecord.setVisibility(isShowLyrics ? View.GONE : View.VISIBLE);
//        lrc.setVisibility(isShowLyrics ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (rotateAnimator != null) {
            if (rotateAnimator.isRunning()) {
                rotateAnimator.cancel();
            }
            rotateAnimator = null;
        }
        if (alphaAnimator != null) {
            if (alphaAnimator.isRunning()) {
                alphaAnimator.cancel();
            }
            alphaAnimator = null;
        }
    }




}
