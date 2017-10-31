package com.histudent.jwsoft.histudent.commen.utils.imageloader;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.histudent.jwsoft.histudent.HiStudentApplication;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.utils.FileUtil;
import com.lidroid.xutils.BitmapUtils;

import java.io.File;

/**
 * Created by liuguiyu-pc on 2017/3/16.
 * 使用Xutils加载图片
 */

public class XutilsImageLoader implements ImageLoader {

    private int maxMemory, cacheSize;
    private BitmapUtils bitmapUtils;

    public BitmapUtils getIntence() {

        if (bitmapUtils == null) {
            maxMemory = (int) Runtime.getRuntime().maxMemory();
            cacheSize = maxMemory / 6;
            bitmapUtils = new BitmapUtils(HiStudentApplication.getInstance(), FileUtil.getPathFactory(FileUtil.CacheType.XUTILS_IMG, true), cacheSize);
            bitmapUtils.configDefaultBitmapMaxSize(500, 500);
        }
        return bitmapUtils;
    }

    @Override
    public void displayNetImage(Context context, String imageUrl, ImageView imageView, int defaultImage) {
        if (context == null || TextUtils.isEmpty(imageUrl) || imageView == null) return;
        if (defaultImage != 0) {
            getIntence().configDefaultLoadingImage(defaultImage);
            getIntence().configDefaultLoadFailedImage(defaultImage);
        }

        getIntence().display(imageView, imageUrl);
    }

    @Override
    public void displayNetImage(Context context, String imageUrl, ImageView imageView) {
        displayNetImage(context, imageUrl, imageView, 0);
    }

    @Override
    public void displayLocalImage(Context context, File file, ImageView imageView, int defaultImage) {
        if (context == null || file == null || imageView == null) return;
        if (defaultImage != 0) {
            getIntence().configDefaultLoadingImage(defaultImage);
            getIntence().configDefaultLoadFailedImage(defaultImage);
        }
        getIntence().display(imageView, file.getAbsolutePath());
    }

    @Override
    public void displayLocalImage(Context context, File file, ImageView imageView) {
        displayLocalImage(context, file, imageView, 0);
    }

}
