package com.histudent.jwsoft.histudent.body.homepage.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.ImageUtil;
import com.histudent.jwsoft.histudent.entity.ImageAttrEntity;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/6/1.
 * 在首页的GridView中加载图片的dadpter
 */
public class GridViewPictureAdapter extends BaseAdapter {

    private Context mContext;
    private List<ImageAttrEntity> imageUrls;
    private int columns;
    private int itemWidth;
    private int itemHeight;


    public GridViewPictureAdapter(Context context, List<ImageAttrEntity> imageUrls, int columns) {
        this.mContext = context;
        this.imageUrls = imageUrls;
        this.columns = columns;
        initItemWidth();
    }

    private void initItemWidth() {
        int screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        itemWidth = (screenWidth - SystemUtil.dip2px(mContext, 4) * (columns-1)) / columns;
        itemHeight = (int)(0.75 * itemWidth);
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public Object getItem(int position) {
        return imageUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatHolder holder;
        if (convertView == null) {
            holder = new ChatHolder();
            //下拉项布局
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gridview_picture_item, null);

            holder.picture = convertView.findViewById(R.id.grid_picture);
            convertView.setTag(holder);
        } else {
            holder = (ChatHolder) convertView.getTag();
        }
        ViewGroup.LayoutParams lp;
        lp = holder.picture.getLayoutParams();
        lp.width = itemWidth;
        lp.height = itemHeight;
        holder.picture.setLayoutParams(lp);

        ImageAttrEntity imageUrl = imageUrls.get(position);
        Glide.with(mContext)
                .load(imageUrl.getBigSizeUrl())
//                .apply(CommonGlideImageLoader.getInstance().buildRequestOptions(ContextCompat.getDrawable(mContext, R.mipmap.default_image)))
                .transition(CommonGlideImageLoader.DRAWABLE_TRANSITION_OPTIONS)

                .into(holder.picture);
        return convertView;
    }

    class ChatHolder {
        ImageView picture;
    }


}
