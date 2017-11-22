package com.histudent.jwsoft.histudent.body.homepage.helper;

import android.content.Context;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.histudent.jwsoft.histudent.body.homepage.adapter.GridViewPictureAdapter;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.model.entity.ImageAttrEntity;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2017/4/21.
 */

public class HomePageHelper {

    private static HomePageHelper helper;

    private HomePageHelper() {
    }

    public static synchronized HomePageHelper getIntence() {
        if (helper == null) {
            helper = new HomePageHelper();
        }
        return helper;
    }

    /**
     * 将图片显示到GridView
     *
     * @param view
     */
    public void showPictures(Context context, GridView view, List<ImageAttrEntity> imageUrls) {
        int columns;
        int size = imageUrls.size();
        switch (size) {
            case 1:
                columns = 1;
                break;
            case 2:
                columns = 2;
                break;
            default:
                columns = 3;
                break;
        }
        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        int itemWidth = (screenWidth - SystemUtil.dip2px(context, 4) * (columns - 1)) / columns;
        int itemHeight = (int)(0.75 * itemWidth);
        view.setNumColumns(columns);
        view.setHorizontalSpacing(SystemUtil.dip2px(context, 4));
        view.setVerticalSpacing(SystemUtil.dip2px(context, 4));
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
        layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
        layoutParams.height = itemHeight * (int)(Math.ceil(size / 3.0));
        view.setLayoutParams(layoutParams);
        GridViewPictureAdapter adapter_picture = new GridViewPictureAdapter(context, imageUrls, columns);
        view.setAdapter(adapter_picture);
    }


    public static class ImageBeans {
        public String thumbnailUrl;

        public String getThumbnailUrl() {
            return thumbnailUrl;
        }

        public void setThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
        }
    }

}
