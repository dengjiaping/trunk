package com.histudent.jwsoft.histudent.widget.scrollview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by lichaojie on 2017/8/16.
 * desc:
 * 引用PhotoView后需重写以下 否则会在图片无限缩小后  出现崩溃现象
 *
 */

public class PhotoViewViewPager extends ViewPager {
    public PhotoViewViewPager(Context context) {
        super(context);
    }

    public PhotoViewViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (Exception ex) {
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (Exception e) {
        }
        return false;
    }
}
