package com.histudent.jwsoft.histudent.body.homepage.holder;

import android.view.View;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;

/**
 * Created by liuguiyu-pc on 2017/4/21.
 */

public class ViewHolder {
    public TextView name, className, time;

    public void setBottom(View view) {
        name = (TextView) view.findViewById(R.id.action_tuijian_name);
        className = (TextView) view.findViewById(R.id.action_tuijian_class);
        time = (TextView) view.findViewById(R.id.action_tuijian_time);
    }
}
