package com.example.miamusic_master;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

//import com.example.miamusic_master.adapter.MultiFragmentPagerAdapter;

public class SearchResultActivity extends AppCompatActivity {
    private static final String TAG = "SearchResultActivity";

//    @BindView(R.id.tablayout)
//    SlidingTabLayout tabLayout;
//    @BindView(R.id.vp_container)
//    ViewPager vpFragment;
//    @BindView(R.id.et_search)
//    SearchEditText etSearch;
//
//    private MultiFragmentPagerAdapter pagerAdapter;
//    private List<BaseFragment> fragments = new ArrayList<>();
//
//    private MultiFragmentPagerAdapter pagerAdapter;
//    private String keywords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
//        ImmersionBar.with(this)
//                .transparentBar()
//                .statusBarColor(R.color.colorPrimary)
//                .statusBarDarkFont(false)
//                .init();
//
//        pagerAdapter = new MultiFragmentPagerAdapter(getSupportFragmentManager());
//        fragments.add(new SongSearchFragment("单曲"));
//        fragments.add(new FeedSearchFragment("视频"));
//        fragments.add(new SingerSearchFragment("歌手"));
//        fragments.add(new AlbumSearchFragment("专辑"));
//        fragments.add(new PlayListSearchFragment("歌单"));
//        fragments.add(new RadioSearchFragment("主播电台"));
//        fragments.add(new UserSearchFragment("用户"));
//        pagerAdapter.init(fragments);
    }
}