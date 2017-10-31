package com.histudent.jwsoft.histudent.comment2.utils;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;

/**图片下载异步线程
 * Created by ZhangYT on 2016/8/8.
 */
public class ImageLoadTask extends AsyncTask<String, Void, Bitmap> {

    private String url;
    @Override
    protected Bitmap doInBackground(String... params) {

        url=params[0];


        Bitmap bitmap= HiWorldCache.downLoadImage(url);

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if(bitmap!=null){
            LruCacheUtils.addBitmapToMemoryCache(String.valueOf(url), bitmap);
            FileUtils.saveImageBitmapIntoLocal(url,bitmap,false);
        }
    }
}
