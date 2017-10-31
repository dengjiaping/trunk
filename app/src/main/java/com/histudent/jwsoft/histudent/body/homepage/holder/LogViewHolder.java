package com.histudent.jwsoft.histudent.body.homepage.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;

/**
 * Created by liuguiyu-pc on 2017/4/21.
 */

public class LogViewHolder extends ViewHolder {
    public TextView title, action_log_images;
    public TextView content;
    public ImageView cover;
    public RelativeLayout action_log_layout;

    public LogViewHolder(View view) {
        action_log_images = (TextView) view.findViewById(R.id.action_log_images);
        action_log_layout = (RelativeLayout) view.findViewById(R.id.action_log_layout);
        title = (TextView) view.findViewById(R.id.action_log_title);
        content = (TextView) view.findViewById(R.id.action_log_content);
        cover = (ImageView) view.findViewById(R.id.action_log_cover);
        setBottom(view);
    }
}
