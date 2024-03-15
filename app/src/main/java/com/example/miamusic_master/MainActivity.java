package com.example.miamusic_master;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.miamusic_master.adapter.MultiFragmentPagerAdapter;
import com.example.miamusic_master.base.BaseFragment;
import com.example.miamusic_master.util.ClickUtil;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.lzx.starrysky.SongInfo;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
//ButterKnife可以使用大量不同的注释来注释视图，例如：
//@BindView：绑定视图（View）并支持底层视图的类型强制转换
//@BindViews：绑定多个视图到一个列表中
//@BindDimen、@BindColor 和 @BindString：将资源绑定为值
//@OnClick：绑定单击事件

public class MainActivity extends AppCompatActivity {
    private ViewPager vp_content; // 声明一个翻页视图对象
    private TabLayout tab_title; // 声明一个标签布局对象

    String BASE_URL = "https://service-m99y4afi-1323400135.gz.tencentapigw.com/release/";
    @BindView(R.id.iv_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.tv_username)
    TextView userName;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.tab_title)
    TextView tabTitle;
    @BindView(R.id.main_viewpager)
    ViewPager viewPager;

    private List<BaseFragment> fragments = new ArrayList<>();
    private MultiFragmentPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this); // 黄油刀绑定视图 千万别漏掉

        mPagerAdapter = new MultiFragmentPagerAdapter(getSupportFragmentManager());
        fragments.add(new FindFragment());//添加一个fragment
        mPagerAdapter.init(fragments);

        initData();
    }


    protected void initData() {
        viewPager.setAdapter(mPagerAdapter);
        viewPager.setCurrentItem(1);
    }

    @OnClick({R.id.ic_nav, R.id.rl_logout, R.id.rl_avatar_name, R.id.tab_title,R.id.iv_search})
    public void onClick(View v) {

        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.ic_nav:
                drawer.openDrawer(GravityCompat.START);//侧滑栏
                break;

            case R.id.tab_title:
            case R.id.iv_search://搜索界面
            intent.setClass(MainActivity.this, SearchActivity.class);
            startActivity(intent);
            break;
        }
    }



}