package com.example.miamusic_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;


public class SplashActivity extends AppCompatActivity {
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

    }
    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 2000); // 在Activity恢复时重新开始计时
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable); // 在Activity停止时取消postDelayed操作
    }
}