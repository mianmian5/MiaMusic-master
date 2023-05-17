package com.example.miamusic_master;

import static com.example.miamusic_master.SearchActivity.KEYWORDS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.miamusic_master.bean.SongSearchBean;
import com.example.miamusic_master.widget.SearchEditText;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

//import com.example.miamusic_master.adapter.MultiFragmentPagerAdapter;

public class SearchResultActivity extends AppCompatActivity {
    private static final String TAG = "SearchResultActivity";
    private ViewPager vp_content; // 声明一个翻页视图对象
    private TabLayout tab_title; // 声明一个标签布局对象
    private String keywords;
    private SongSearchFragment mysongFragment;

//    @BindView(R.id.tablayout)
//    SlidingTabLayout tabLayout;
//    @BindView(R.id.vp_container)
//    ViewPager vpFragment;
    @BindView(R.id.et_search)
EditText etSearch;
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
        ButterKnife.bind(this); // 黄油刀绑定视图 千万别漏掉
        vp_content =findViewById(R.id.vp_content);
        tab_title=findViewById(R.id.tab_title);
        SongSearchFragment mysongFragment;

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SongSearchFragment(),"单曲");
        vp_content.setAdapter(adapter);
        tab_title.setupWithViewPager(vp_content);
        initData();


//        getSupportFragmentManager().beginTransaction().add(R.id.vp_content, mysongFragment).commit();

    }
    protected void initData() {
        if (getIntent().getStringExtra(KEYWORDS) != null) {
            keywords = getIntent().getStringExtra(KEYWORDS);
            etSearch.setText(keywords);

//            // 传递数据给Fragment
////            SongSearchFragment mysongFragment;
//            SongSearchFragment mysongFragment = new SongSearchFragment();
//            Bundle bundle = new Bundle();
//            bundle.putString("myData", keywords);
//            mysongFragment.setArguments(bundle);
//
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.vp_content, mysongFragment);
//            fragmentTransaction.commit();
//            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, fragment)commit();
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl("https://service-n9pb0may-1318194552.gz.apigw.tencentcs.com/release/")
////                .client(client)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                    .build();
//            // 创建API接口实例
//            SearchService searchService = retrofit.create(SearchService.class);
//
//            Call<SongSearchBean> call = searchService.getSongSearch(keywords,1);
//
//            call.enqueue(new Callback<SongSearchBean>() {
//                @Override
//                public void onResponse(Call<SongSearchBean> call, retrofit2.Response<SongSearchBean> response) {
//                    Toast.makeText(getApplication(), "歌曲搜索请求成功！", Toast.LENGTH_SHORT).show();
//                    System.out.println("歌曲搜索结果"+response.body().getResult());
////                searchDetailBean = bean;
//
//                }
//
//
//                @Override
//                public void onFailure(Call<SongSearchBean> call, Throwable t) {
//
//                    Log.e(TAG, "onFailure: " + t.getMessage());
//                }
//
//
//            });

        }
    }
    public  String toValue(){
        //利用Fragment的生命周期onAttach()方法，获取activity的keyword
        return  keywords;
    }



//    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
//        super.(savedInstanceState);

        tab_title.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // 当选中tab时触发
                vp_content.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // 当取消选中tab时触发
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // 当重新选中tab时触发
            }
        });
    }
    private static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }
        @Override
        public int getCount() {
            return fragmentList.size();
        }
        public void addFragment(Fragment fragment,String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }

}