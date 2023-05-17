package com.example.miamusic_master;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.miamusic_master.util.ClickUtil;
import com.lzx.starrysky.SongInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 歌曲详情界面
 * Created By Rikka on 2019/7/24
 */
public class SongDetailActivity extends AppCompatActivity {

    @BindView(R.id.iv_cover)
    ImageView ivCover;
    @BindView(R.id.tv_songname)
    TextView tvSongName;
    @BindView(R.id.tv_singer)
    TextView tvSinger;
//    @BindView(R.id.md_singer)
//    MusicDrawerItemView mdSinger;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.fl_view)
    FrameLayout flView;

    //SongActivity来的
    private long songId;

    private SongInfo songInfo;
    private String singerName;
    private long singerId;
    private String singerPicUrl;
    private Animation toTranslateIn;
    private Animation toTranslateOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_song_detail);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ButterKnife.bind(this);
//        getWindow().setFlags(
//                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (toTranslateIn == null) {
            toTranslateIn = AnimationUtils.loadAnimation(this, R.anim.view_to_translate_in);
            toTranslateIn.setFillAfter(true);
            toTranslateIn.setStartOffset(200);
        }
        if (toTranslateOut == null) {
            toTranslateOut = AnimationUtils.loadAnimation(this, R.anim.view_to_translate_out);
            toTranslateOut.setFillAfter(true);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        view.startAnimation(toTranslateIn);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick({R.id.view, R.id.md_nextplay, R.id.md_collect, R.id.md_download,
            R.id.md_commend, R.id.md_share, R.id.md_singer, R.id.md_video})
    public void onClick(View v) {
        if (ClickUtil.isFastClick(1000, v)) {
            return;
        }
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.view:
                finish();
                break;
            case R.id.md_nextplay:
//                ToastUtils.show("下一首播放咯");
                break;
            case R.id.md_collect:
//                ToastUtils.show("收藏歌曲");
                break;
            case R.id.md_download:
//                ToastUtils.show("下载");
                break;
            case R.id.md_commend:
//                ToastUtils.show("评论");
                break;
            case R.id.md_share:
//                ToastUtils.show("分享");
                break;
            case R.id.md_singer:
//                intent.setClass(SongDetailActivity.this, SingerActivity.class);
//                intent.putExtra(SingerActivity.SINGER_NAME, singerName);
//                intent.putExtra(SingerActivity.SINGER_ID, singerId);
//                intent.putExtra(SingerActivity.SINGER_PICURL, singerPicUrl);
//                startActivity(intent);
                break;
            case R.id.md_video:
//                ToastUtils.show("查看MV");
        }
    }

    @Override
    public void finish() {
        super.finish();
        view.startAnimation(toTranslateOut);
        overridePendingTransition(R.anim.bottom_silent, R.anim.bottom_out);
    }
//    @Override
//    protected void initData() {
//        Intent intent = getIntent();
//        if (intent != null) {
//            songInfo = intent.getParcelableExtra(SongActivity.SONG_INFO);
//            singerId = Long.parseLong(songInfo.getArtist());
//            singerName = songInfo.getArtist();
////            singerPicUrl = songInfo.getArtistKey();
//
//            Glide.with(this).load(songInfo.getSongCover()).into(ivCover);
//            tvSongName.setText(getString(R.string.songanme) + songInfo.getSongName());
////            mdSinger.setText(getString(R.string.singer) + singerName);
//            tvSinger.setText(singerName);
//            songId = Long.parseLong(songInfo.getSongId());
//        }
//    }


}
