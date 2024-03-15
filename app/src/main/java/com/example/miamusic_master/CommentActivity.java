package com.example.miamusic_master;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.miamusic_master.adapter.CommentAdapter;
import com.example.miamusic_master.adapter.MyPagerAdapter;
import com.example.miamusic_master.adapter.PlayListAdapter;
import com.example.miamusic_master.adapter.PlaylistCommentAdapter;
import com.example.miamusic_master.bean.BannerBean;
import com.example.miamusic_master.bean.MainRecomListBean;
import com.example.miamusic_master.bean.MusicCommentBean;
import com.example.miamusic_master.bean.PlayListCommentBean;
import com.example.miamusic_master.bean.SongDetailBean;
import com.example.miamusic_master.widget.RikkaRoundRectView;


import org.greenrobot.greendao.annotation.Id;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 歌曲、歌单评论界面 复用
 */
public class CommentActivity extends AppCompatActivity {
    private static final String TAG = "CommentActivity";

    public static final String COVER = "cover";
    public static final String NAME = "name";
    public static final String ARTIST = "artist";
    public static final String ID = "id";
    public static final String FROM = "from";
    public static final int SONG_COMMENT = 1;
    public static final int PLAYLIST_COMMENT = 2;
    public RikkaRoundRectView ivCover1;

    @BindView(R.id.iv_cover1)
    RikkaRoundRectView ivCover;
    @BindView(R.id.tv_music_name)
    TextView tvSongName;
    @BindView(R.id.tv_artist)
    TextView tvArtist;
    @BindView(R.id.rv_hot_comment)
    RecyclerView rvHotComment;
    @BindView(R.id.rv_new_comment)
    RecyclerView rvNewComment;
    private int from;
    private CommentAdapter  newAdapter;
    private PlaylistCommentAdapter newAdapter2;
    private long id;
    private List<MusicCommentBean.CommentsBean> newCommentList = new ArrayList<>();
    private List<PlayListCommentBean.CommentsBean> playlistCommentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);
        ivCover1=findViewById(R.id.iv_cover1);
        initData();

    }

    protected void initData() {

        Intent intent = getIntent();
        id = intent.getLongExtra(ID, 0);
        System.out.println("cover"+intent.getStringExtra(COVER));
        Glide.with(this).load(intent.getStringExtra(COVER)).into(ivCover);
        tvSongName.setText(intent.getStringExtra(NAME));
        tvArtist.setText(intent.getStringExtra(ARTIST));
        from = intent.getIntExtra(FROM, 1);

        rvHotComment.setLayoutManager(new LinearLayoutManager(this));
        rvNewComment.setLayoutManager(new LinearLayoutManager(this));

        newAdapter = new CommentAdapter(this);
        newAdapter2 = new PlaylistCommentAdapter(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://service-m99y4afi-1323400135.gz.tencentapigw.com/release/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        // 创建API接口实例

//        需要获取评论的格式 歌单评论、歌曲评论等（因为这个activity是复用的
        switch (from) {
            case SONG_COMMENT://歌曲评论
                rvNewComment.setAdapter(newAdapter);
                System.out.println("获取歌曲评论~"+ id);
                MusicCommentService musicCommentService = retrofit.create(MusicCommentService.class);
                Call<MusicCommentBean> call = musicCommentService.getMusicComment(id);
                call.enqueue(new Callback<MusicCommentBean>() {
                    @Override
                    public void onResponse(Call<MusicCommentBean> call, Response<MusicCommentBean> response) {
                        Toast.makeText(CommentActivity.this, "歌曲评论请求成功！", Toast.LENGTH_SHORT).show();
//                        setLeftTitleText(getString(R.string.comment) + "(" + bean.getTotal() + ")", getString(R.string.colorWhite));
                        notifyList(response.body().getComments());
                        System.out.println("歌曲id"+id);
                        System.out.println("歌曲评论"+response.body());
                        System.out.println("歌曲评论:热评"+response.body().getHotComments());
                        System.out.println("歌曲评论:评论"+response.body().getComments());


                    }


                    @Override
                    public void onFailure(Call<MusicCommentBean> call, Throwable t) {

                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }


                });
//                mPresenter.getMusicComment(id);
                break;
            case PLAYLIST_COMMENT://歌单评论
                rvNewComment.setAdapter(newAdapter2);
                System.out.println("获取歌单评论~"+id);
                PlaylistCommentService playlistCommentService = retrofit.create(PlaylistCommentService.class);
                Call<PlayListCommentBean> call2 = playlistCommentService.getPlaylistComment(id);
                call2.enqueue(new Callback<PlayListCommentBean>() {
                    @Override
                    public void onResponse(Call<PlayListCommentBean> call, Response<PlayListCommentBean> response) {
                        Toast.makeText(CommentActivity.this, "歌单评论请求成功！", Toast.LENGTH_SHORT).show();
//                        setLeftTitleText(getString(R.string.comment) + "(" + bean.getTotal() + ")", getString(R.string.colorWhite));
                        notifyList2(response.body().getComments());
                        System.out.println("歌曲id"+id);
                        System.out.println("歌曲评论"+response.body());
                        System.out.println("歌曲评论:评论"+response.body().getComments());


                    }


                    @Override
                    public void onFailure(Call<PlayListCommentBean> call, Throwable t) {

                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }


                });
                break;
        }

    }
    private void notifyList( List<MusicCommentBean.CommentsBean> comments) {
        newCommentList.clear();
        if (comments != null) {
            newCommentList = comments;
        }
        newAdapter.notifyDataSetChanged(newCommentList);
    }
    private void notifyList2( List<PlayListCommentBean.CommentsBean> comments) {
        playlistCommentList.clear();
        if (comments != null) {
            playlistCommentList = comments;
        }
        newAdapter2.notifyDataSetChanged(playlistCommentList);
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
    }










}
