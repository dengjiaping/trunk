package com.histudent.jwsoft.histudent.comment2.utils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by ZhangYT on 2016/9/8.
 */
public class LruCacheUtils {

    private static LruCache<String, Bitmap> mMemoryCache;


    private LruCacheUtils() {
        // 获取到可用内存的最大值，使用内存超出这个值会引起OutOfMemory异常。
        // LruCache通过构造函数传入缓存值，以KB为单位。
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        // 使用最大可用内存值的1/8作为缓存的大小。
        int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // 重写此方法来衡量每张图片的大小，默认返回图片数量。
                return bitmap.getByteCount() / 1024;
            }
        };
    }


    private static void LruCacheUtilsInstance() {

        if (mMemoryCache == null) {
            LruCacheUtils lruCacheUtils = new LruCacheUtils();
        }
    }


    public static void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        LruCacheUtilsInstance();

        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public static Bitmap getBitmapFromMemCache(String key) {
        LruCacheUtilsInstance();
        return mMemoryCache.get(key);
    }
}
