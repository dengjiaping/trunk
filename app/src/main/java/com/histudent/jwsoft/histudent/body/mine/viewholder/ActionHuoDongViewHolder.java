package com.histudent.jwsoft.histudent.body.mine.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;

/**
 * Created by liuguiyu-pc on 2017/4/21.
 */

public class ActionHuoDongViewHolder extends ActionViewHolder {

    public TextView huodong_summer, huodong_title;
    public TextView huodong_content;
    public ImageView huodong_cover;

    public ActionHuoDongViewHolder(View view) {
        huodong_summer = (TextView) view.findViewById(R.id.huodong_summer);
        huodong_title = (TextView) view.findViewById(R.id.huodong_title);
        huodong_content = (TextView) view.findViewById(R.id.huodong_content);
        huodong_cover = (ImageView) view.findViewById(R.id.huodong_cover);
        setCommen(view);
    }
}
