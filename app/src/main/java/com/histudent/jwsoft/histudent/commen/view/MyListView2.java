package com.histudent.jwsoft.histudent.commen.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by ZhangYT on 2016/6/14.
 */
public class MyListView2 extends ListView{

    public MyListView2(Context context) {
        super(context);
    }

    public MyListView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       heightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
