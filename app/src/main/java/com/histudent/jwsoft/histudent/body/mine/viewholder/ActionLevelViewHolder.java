package com.histudent.jwsoft.histudent.body.mine.viewholder;

import android.view.View;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;

/**
 * Created by liuguiyu-pc on 2017/4/21.
 */

public class ActionLevelViewHolder extends ActionViewHolder {

    public TextView level_num, level_num_;

    public ActionLevelViewHolder(View view) {
        level_num = (TextView) view.findViewById(R.id.level_num);
        level_num_ = (TextView) view.findViewById(R.id.level_num_);
        setCommen(view);
    }
}
