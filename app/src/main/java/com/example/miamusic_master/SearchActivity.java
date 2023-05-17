package com.example.miamusic_master;

import static com.example.miamusic_master.App.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//import android.
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.miamusic_master.adapter.HotSearchAdapter;
import com.example.miamusic_master.adapter.MyPagerAdapter;
import com.example.miamusic_master.bean.BannerBean;
import com.example.miamusic_master.bean.HotSearchDetailBean;
import com.example.miamusic_master.bean.MainRecomListBean;
import com.example.miamusic_master.bean.SongSearchBean;
import com.example.miamusic_master.util.ClickUtil;
import com.example.miamusic_master.widget.SearchEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    public static final String KEYWORDS = "keywords";
    private HotSearchAdapter adapter;
    private HotSearchDetailBean searchDetailBean;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.btn_search)
    TextView btnSearch;
    @BindView(R.id.rv_hotsearch)
    RecyclerView rvHotSearch;

//    private TextView etsearch;
//    private TextView btnsearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this); // 黄油刀绑定视图
        initData();
        etSearch.setFocusable(true);
        etSearch.setFocusableInTouchMode(true);
        etSearch.requestFocus();
        //调用系统输入法
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(etSearch, 0);

    }

    protected void initData() {


        adapter = new HotSearchAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvHotSearch.setLayoutManager(manager);
        rvHotSearch.setAdapter(adapter);
        adapter.setListener(searchListener);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://service-n9pb0may-1318194552.gz.apigw.tencentcs.com/release/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        // 创建API接口实例
        HotSearchService hotSearchService = retrofit.create(HotSearchService.class);

        Call<HotSearchDetailBean> call = hotSearchService.getSearchHotDetail();

        call.enqueue(new Callback<HotSearchDetailBean>() {
            @Override
            public void onResponse(Call<HotSearchDetailBean> call, Response<HotSearchDetailBean> response) {
                Toast.makeText(SearchActivity.this, "热搜词请求成功！", Toast.LENGTH_SHORT).show();

                System.out.println(response.body().getData());
                searchDetailBean = response.body();
                List<HotSearchDetailBean> adapterList = new ArrayList<>();
                adapterList.add(searchDetailBean);
                adapter.notifyDataSetChanged(adapterList);


            }


            @Override
            public void onFailure(Call<HotSearchDetailBean> call, Throwable t) {

                Log.e(TAG, "onFailure: " + t.getMessage());
            }


        });

    }
    private HotSearchAdapter.OnHotSearchAdapterClickListener searchListener = position -> {
        if (searchDetailBean != null) {
            String keywords = searchDetailBean.getData().get(position).getSearchWord();
            searchSong(keywords);
        }
    };



    @OnClick({R.id.btn_search})
    public void onClick(View v) {

//        if (ClickUtil.isFastClick(1000, v)) {
//            return;
//        }
        switch (v.getId()) {
            case R.id.btn_search:
//                Toast.makeText(this, "222", Toast.LENGTH_SHORT).show();
                String keywords = etSearch.getText().toString();
//                String keywords = etSearch.
                if (!TextUtils.isEmpty(keywords)) {
                    Toast.makeText(this, "正在查询~", Toast.LENGTH_SHORT).show();
                    searchSong(keywords);
                } else {
                    Toast.makeText(this, "请输入关键字", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }
    //根据关键字去搜索
    private void searchSong(String keywords) {
//        new GetDataTask().execute();
//
        Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
        intent.putExtra(KEYWORDS, keywords);
        startActivity(intent);
    }








}