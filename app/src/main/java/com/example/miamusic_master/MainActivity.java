package com.example.miamusic_master;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.miamusic_master.adapter.MultiFragmentPagerAdapter;
import com.example.miamusic_master.base.BaseFragment;
import com.example.miamusic_master.util.ClickUtil;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.lzx.starrysky.SongInfo;

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
    //viewPager会缓存的Fragment数量
    private static final int VIEWPAGER_OFF_SCREEN_PAGE_LIMIT = 2;
    public static final String LOGIN_BEAN = "loginBean";

    @BindView(R.id.iv_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.tv_username)
    TextView userName;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
//    @BindView(R.id.main_viewpager)
//    ViewPager viewPager;
    @BindView(R.id.tab_title)
    TextView tabTitle;
    @BindView(R.id.main_viewpager)
    ViewPager viewPager;
//    TabLayout tabTitle;
//    @BindView(R.id.bottom_controller)
//    BottomSongPlayBar bottomController;

    private long firstTime;

    private List<BaseFragment> fragments = new ArrayList<>();
    private MultiFragmentPagerAdapter mPagerAdapter;
//    private List<BaseFragment> fragments = new ArrayList<>();
//    private MultiFragmentPagerAdapter mPagerAdapter;
    private List<SongInfo> songInfos;
//    private LoginBean loginBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this); // 黄油刀绑定视图 千万别漏掉
        mPagerAdapter = new MultiFragmentPagerAdapter(getSupportFragmentManager());
        fragments.add(new FindFragment());
        mPagerAdapter.init(fragments);
        initData();
    }


    protected void initData() {
//        String userLoginInfo = SharePreferenceUtil.getInstance(this).getUserInfo("");
//        loginBean = GsonUtil.fromJSON(userLoginInfo, LoginBean.class);

        viewPager.setAdapter(mPagerAdapter);
        viewPager.setOffscreenPageLimit(VIEWPAGER_OFF_SCREEN_PAGE_LIMIT);
        viewPager.setCurrentItem(1);
//        mPagerAdapter.getItem(1).setUserVisibleHint(true);
//        tabTitle.setupWithViewPager(viewPager);
//        tabTitle.setTabTextColors(Color.parseColor("#e78c86"), Color.parseColor("#FFFDFD"));
//        assert loginBean != null;
//        initView(loginBean);


//        setSelectTextBoldAndBig(Objects.requireNonNull(tabTitle.getTabAt(1)));
//        initTabListener();
//        mPresenter.getLikeList(loginBean.getAccount().getId());
    }

//    @Override
    @OnClick({R.id.ic_nav, R.id.rl_logout, R.id.rl_avatar_name, R.id.tab_title,R.id.iv_search})
    public void onClick(View v) {
        if (ClickUtil.isFastClick(1000, v)) {
            return;
        }
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.ic_nav:
                drawer.openDrawer(GravityCompat.START);
                break;


            case R.id.rl_avatar_name://个人信息
                drawer.closeDrawer(GravityCompat.START);
//                intent.setClass(MainActivity.this, PersonalInfoActivity.class);
//                String loginbean = GsonUtil.toJson(loginBean.getProfile());
//                intent.putExtra(LOGIN_BEAN, loginbean);
                startActivity(intent);
                break;
            case R.id.tab_title:
            case R.id.iv_search://搜索
            intent.setClass(MainActivity.this, SearchActivity.class);
            startActivity(intent);
            break;
        }
    }


}