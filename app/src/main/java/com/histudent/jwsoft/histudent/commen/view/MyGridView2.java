package com.histudent.jwsoft.histudent.commen.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by ZhangYT on 2016/6/12.
 */
public class MyGridView2 extends GridView {

    public MyGridView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridView2(Context context) {
        super(context);
    }

    public MyGridView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
//        super.onMeasure(widthMeasureSpec, expandSpec);
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
