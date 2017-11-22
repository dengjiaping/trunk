package com.histudent.jwsoft.histudent.view.widget;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import java.lang.reflect.Field;

/**
 * Created by lichaojie on 2017/8/3.
 * desc:
 */

public class DefineTabLayout extends TabLayout{
    private static final int TabViewNumber = 8;
    private static final String SCROLLABLE_TAB_MIN_WIDTH = "mScrollableTabMinWidth";
    public DefineTabLayout(Context context) {
        super(context);
        initTabMinWidth();
    }

    public DefineTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTabMinWidth();
    }

    public DefineTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTabMinWidth();
    }
    private void initTabMinWidth() {
        int screenWidth=getResources().getDisplayMetrics().widthPixels;
        int tabMinWidth = screenWidth / TabViewNumber;

        Field field;
        try {
            field = TabLayout.class.getDeclaredField(SCROLLABLE_TAB_MIN_WIDTH);
            field.setAccessible(true);
            field.set(this, tabMinWidth);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
