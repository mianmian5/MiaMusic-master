package com.example.miamusic_master.widget;

import android.content.Context;

import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

//import com.rikkathewrold.rikkamusic.util.DensityUtil;

public class PlayListRecommendViewPager extends ViewPager {
    private static final String TAG = "PlayListRecommendViewPager";

    private Context mContext;
    private float mLastY;

    public PlayListRecommendViewPager(@NonNull Context context) {
        this(context, null);
    }

    public PlayListRecommendViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int currentItem = getCurrentItem();
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if (currentItem == 0) {
                    if (ev.getY() >= 0 ) {
                        mLastY = ev.getY();
                        return false;
                    }
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public void addOnPageChangeListener(@NonNull OnPageChangeListener listener) {
        super.addOnPageChangeListener(listener);
    }
}
