package com.example.miamusic_master;

import static com.example.miamusic_master.SearchActivity.KEYWORDS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.miamusic_master.bean.SongSearchBean;
import com.example.miamusic_master.util.ClickUtil;
import com.google.android.material.tabs.TabLayout;

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

public class SearchResultActivity extends AppCompatActivity {
    private static final String TAG = "SearchResultActivity";
    private ViewPager vp_content; // 声明一个翻页视图对象
    private TabLayout tab_title; // 声明一个标签布局对象
    private String keywords;
    public SongSearchFragment mysongFragment;

    @BindView(R.id.et_search)
EditText etSearch;
    public ViewPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this); // 黄油刀绑定视图 千万别漏掉
        vp_content =findViewById(R.id.vp_content);
        tab_title=findViewById(R.id.tab_title);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        mysongFragment=new SongSearchFragment();
        adapter.addFragment(mysongFragment,"单曲");
        vp_content.setAdapter(adapter);
        tab_title.setupWithViewPager(vp_content);
        initData();


    }
    protected void initData() {
        if (getIntent().getStringExtra(KEYWORDS) != null) {
            keywords = getIntent().getStringExtra(KEYWORDS);
            etSearch.setText(keywords);

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
        private FragmentManager fragmentManager;
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
            fragmentManager = manager;
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
        public void removeFragment(Fragment fragment) {
            int index = fragmentList.indexOf(fragment);
            if (index != -1) {
                fragmentList.remove(index);
                fragmentTitleList.remove(index);
                notifyDataSetChanged();
            }
        }

        public void refreshFragment(Fragment fragment) {
            int index = fragmentList.indexOf(fragment);
            if (index != -1) {
                fragmentManager.beginTransaction()
                        .detach(fragment)
                        .attach(fragment)
                        .commit();
            }
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }
    @OnClick({R.id.btn_search})
    public void onClick(View v) {
        if (ClickUtil.isFastClick(1000, v)) {
            return;
        }
        switch (v.getId()) {
            case R.id.btn_search:
                keywords = etSearch.getText().toString();
                if (!TextUtils.isEmpty(keywords)) {
                    Intent intent = new Intent(this, SearchResultActivity.class);
                    intent.putExtra(KEYWORDS, keywords);
                    startActivity(intent);




                } else {
                    Toast.makeText(this, "请输入关键字", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}