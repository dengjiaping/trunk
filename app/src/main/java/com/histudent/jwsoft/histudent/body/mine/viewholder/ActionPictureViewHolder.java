package com.histudent.jwsoft.histudent.body.mine.viewholder;

import android.view.View;
import android.widget.ImageView;

import com.histudent.jwsoft.histudent.R;

/**
 * Created by liuguiyu-pc on 2017/4/21.
 */

public class ActionPictureViewHolder extends ActionViewHolder {

    public ImageView picture_img;

    public ActionPictureViewHolder(View view) {
        setCommen(view);
        picture_img = (ImageView) view.findViewById(R.id.picture_img);
    }

}
