package com.histudent.jwsoft.histudent.commen.view;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.HTApplication;

/**
 * Created by liuguiyu-pc on 2017/4/25.
 */

public class IconView extends TextView{
    public IconView(Context context) {
        super(context);
        init();
    }

    public IconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IconView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //设置字体图标
        this.setTypeface(HTApplication.getInstance().getIconTypeFace());
    }
}
