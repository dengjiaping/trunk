package com.histudent.jwsoft.histudent.body.mine.viewholder;

import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.commen.view.RichTextView;

/**
 * Created by liuguiyu-pc on 2017/4/21.
 */

public class ActionEssayViewHolder extends ActionViewHolder {

    public TextView item_location, item_group_name;
    public RichTextView content;
    public GridView action_essay_gridView;
    public LinearLayout item_location_layout;
    public IconView video_play, item_commen_location_image;
    public ImageView essay_movie_cover;
    public RelativeLayout action_essay_movie;

    public ActionEssayViewHolder(View view) {
        setCommen(view);
        action_essay_gridView = (GridView) view.findViewById(R.id.action_essay_gridView);
        content = (RichTextView) view.findViewById(R.id.action_essay_content);
        item_group_name = (TextView) view.findViewById(R.id.item_group_name);
        item_location = (TextView) view.findViewById(R.id.item_location);
        item_commen_location_image = (IconView) view.findViewById(R.id.item_commen_location_image);
        video_play = (IconView) view.findViewById(R.id.video_play);
        action_essay_movie = (RelativeLayout) view.findViewById(R.id.action_essay_movie);
        essay_movie_cover = (ImageView) view.findViewById(R.id.essay_movie_cover);
        item_location_layout = (LinearLayout) view.findViewById(R.id.item_location_layout);
    }

}
