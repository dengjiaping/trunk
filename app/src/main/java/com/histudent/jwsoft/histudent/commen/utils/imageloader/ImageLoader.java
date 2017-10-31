package com.histudent.jwsoft.histudent.commen.utils.imageloader;

import android.content.Context;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by liuguiyu-pc on 2017/3/16.
 * 图片加载基本接口定义
 */

public interface ImageLoader {

    //加载网络图片（带默认图）
    void displayNetImage(Context context, String imageUrl, ImageView imageView, int defaultImage);

    //加载网络图片（不带默认图）
    void displayNetImage(Context context, String imageUrl, ImageView imageView);

    //加载本地图片（带默认图）
    void displayLocalImage(Context context, File file, ImageView imageView, int defaultImage);

    //加载本地图片（不带默认图）
    void displayLocalImage(Context context, File file, ImageView imageView);

}
