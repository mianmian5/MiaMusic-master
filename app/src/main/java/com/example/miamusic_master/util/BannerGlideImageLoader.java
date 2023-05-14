package com.example.miamusic_master.util;

import android.content.Context;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;

/**
 * 用于Banner的图片加载器
 */
public class BannerGlideImageLoader extends ImageLoader {
    /**
     * Constructs a new ImageLoader.
     *
     * @param queue      The RequestQueue to use for making image requests.
     * @param imageCache The cache to use as an L1 cache.
     */
    public BannerGlideImageLoader(RequestQueue queue, ImageCache imageCache) {
        super(queue, imageCache);
    }

    //    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).into(imageView);
    }
}

