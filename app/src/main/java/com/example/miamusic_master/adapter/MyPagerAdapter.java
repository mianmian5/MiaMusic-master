package com.example.miamusic_master.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.viewpager.widget.PagerAdapter;
import com.bumptech.glide.Glide;
import com.example.miamusic_master.R;

import java.util.List;
//用于轮播图
public class MyPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List mImageUrls;

    public MyPagerAdapter(Context context, List<String> imageUrls) {
        mContext = context;
        mImageUrls = imageUrls;
    }

    @Override
    public int getCount() {
        return mImageUrls.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.viewpager_item, container, false);

        ImageView imageView = view.findViewById(R.id.iv_image);
        Glide.with(mContext).load(mImageUrls.get(position)).into(imageView);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}