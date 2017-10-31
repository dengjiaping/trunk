package com.histudent.jwsoft.histudent.body.mine.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;

/**
 * Created by liuguiyu-pc on 2017/4/21.
 */

public class ActionLogViewHolder extends ActionViewHolder {
    public TextView action_log_title;
    public TextView action_log_content, action_log_images;
    public ImageView action_log_cover;
    public RelativeLayout action_log_cover_layout;

    public ActionLogViewHolder(View view) {
        action_log_title = view.findViewById(R.id.action_log_title);
        action_log_content = view.findViewById(R.id.action_log_content);
        action_log_images = view.findViewById(R.id.action_log_images);
        action_log_cover = view.findViewById(R.id.action_log_cover);
        action_log_cover_layout = view.findViewById(R.id.action_log_cover_layout);
        setCommen(view);
    }
}
