package com.histudent.jwsoft.histudent.view.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestOptions;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.activity.ImageBrowserActivity;
import com.histudent.jwsoft.histudent.view.widget.DefinePhotoView;

import java.util.List;

/**
 * Created by lichaojie on 2017/7/19.
 * des:
 * 通用预览大图 adapter
 */

public class PreviewPhotoAdapter extends PagerAdapter {

    private Context mContext;
    private List<String> mGlideImageUrl;

    private DefinePhotoView mDefinePhotoView;
    private RequestOptions mRequestOptions;


    public PreviewPhotoAdapter(Context context, List<String> glideImageUrl) {
        this.mGlideImageUrl = glideImageUrl;
        this.mContext = context;
    }

    public void setPreviewPhotoUrlList(List<String> listUrl) {
        this.mGlideImageUrl = listUrl;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        mDefinePhotoView = new DefinePhotoView(mContext);
        String url = mGlideImageUrl.get(position);
        container.addView(mDefinePhotoView);
        if (mRequestOptions == null) {
            mRequestOptions = mDefinePhotoView.requestOptions(R.color.placeholder_color)
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.LOW);
        }

        mDefinePhotoView.load(url, mRequestOptions)
                .listener((int percent, boolean isDone, GlideException exception) ->
                        ((ImageBrowserActivity) mContext).setCircleProgressView(percent, isDone));

        return mDefinePhotoView;
    }

    @Override
    public int getCount() {
        return mGlideImageUrl.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        DefinePhotoView photoView = (DefinePhotoView) object;
        if (photoView == null)
            return;
        Glide.get(mContext).clearMemory();
        container.removeView(photoView);
    }
}
