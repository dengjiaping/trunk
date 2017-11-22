package com.histudent.jwsoft.histudent.view.widget.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by lichaojie on 2017/8/10.
 * desc:
 * 可以实现监听ScrollView滑动距离
 */

public class DefineScrollView extends ScrollView {
    private ScrollViewListener scrollViewListener = null;

    public DefineScrollView(Context context) {
        super(context);
    }

    public DefineScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public DefineScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldX, int oldY) {
        super.onScrollChanged(x, y, oldX, oldY);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldX, oldY);
        }
    }
}
