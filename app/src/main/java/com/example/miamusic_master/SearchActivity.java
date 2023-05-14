package com.example.miamusic_master;

import static com.example.miamusic_master.App.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//import android.
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
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
//import okhttp3.OkHttpClient;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    public static final String KEYWORDS = "keywords";
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.btn_search)
    TextView btnSearch;

    EditText editTextTextPersonName;
//    private TextView etsearch;
//    private TextView btnsearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toast.makeText(this, "111", Toast.LENGTH_SHORT).show();
        ButterKnife.bind(this); // 黄油刀绑定视图
        editTextTextPersonName=findViewById(R.id.editTextTextPersonName);
//        etsearch=findViewById(R.id.et_search);
//        btnsearch=findViewById(R.id.btn_search);
//        btnsearch.setOnClickListener(this);
    }

//    @Override
//    public void onClick(View v) {
//        if (v.getId() == R.id.btn_search) {
//
//
//        }

    @OnClick({R.id.btn_search})
    public void onClick(View v) {

//        if (ClickUtil.isFastClick(1000, v)) {
//            return;
//        }
        switch (v.getId()) {
            case R.id.btn_search:
                Toast.makeText(this, "222", Toast.LENGTH_SHORT).show();
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
        new GetDataTask().execute();





//        Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
//        intent.putExtra(KEYWORDS, keywords);
//        startActivity(intent);
    }



//zanshishandiao
    // 定义一个AsyncTask类，在子线程中执行请求
    private class GetDataTask extends AsyncTask<Void, Void, String> {

//        @Override
//        protected String doInBackground(Void... voids) {
//            OkHttpClient client = new OkHttpClient();
//            String url = "http://127.0.0.1:3000/search?keywords=海阔天空";
//            Request request = new Request.Builder().url(url).build();
//            // 执行请求并获取响应结果
//            try (Response response = client.newCall(request).execute()) {
//                return response.body().string();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
        @Override
        protected String doInBackground(Void... voids) {
            RequestQueue queue = Volley.newRequestQueue(getApplication());
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "https://service-n9pb0may-1318194552.gz.apigw.tencentcs.com/release/search?keywords=cry1", null,new com.android.volley.Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    try {

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject object = response.getJSONObject(i);
                            System.out.println(object);
                            Toast.makeText(getContext(), "请求成功!", Toast.LENGTH_SHORT).show();
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }


            })
            ;

            queue.add(jsonArrayRequest);

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // 更新UI
            if (result != null) {
                try {
                    // 解析JSON响应并显示到界面上
                    JSONObject jsonObject = new JSONObject(result);
                    String data = jsonObject.getString("data");
                    editTextTextPersonName.setText(data);
                    System.out.println("有返回结果");
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("无返回结果");

                }
            }
        }
    }


}