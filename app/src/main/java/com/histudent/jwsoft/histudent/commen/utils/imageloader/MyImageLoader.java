package com.histudent.jwsoft.histudent.commen.utils.imageloader;


import android.content.Context;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by liuguiyu-pc on 2017/3/16.
 * 图片加载代理类
 */

public class MyImageLoader implements ImageLoader {

    private static MyImageLoader loader;
    private ImageLoader imageLoader;

    public static synchronized MyImageLoader getIntent() {
        if (loader == null)
            loader = new MyImageLoader();
        return loader;
    }

    private MyImageLoader() {
        imageLoader = new XutilsImageLoader();
    }

    @Override
    public void displayNetImage(Context context, String imageUrl, ImageView imageView, int defaultImage) {
        try {
            imageLoader.displayNetImage(context, imageUrl, imageView, defaultImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void displayNetImage(Context context, String imageUrl, ImageView imageView) {
        try {
            imageLoader.displayNetImage(context, imageUrl, imageView, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void displayLocalImage(Context context, File file, ImageView imageView, int defaultImage) {
        try {
            imageLoader.displayLocalImage(context, file, imageView, defaultImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void displayLocalImage(Context context, File file, ImageView imageView) {
        try {
            imageLoader.displayLocalImage(context, file, imageView, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
