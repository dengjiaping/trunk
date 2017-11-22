package com.histudent.jwsoft.histudent.view.widget;

import android.content.Context;
import android.graphics.ColorMatrixColorFilter;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by huyg on 2017/10/9.
 * 点击颜色变深imageView
 */

public class AutoImageView extends android.support.v7.widget.AppCompatImageView implements View.OnTouchListener {

    //变暗(三个-50，值越大则效果越深)
    public final float[] BT_SELECTED_DARK = new float[]{
            1, 0, 0, 0, -50, 0, 1,
            0, 0, -50, 0, 0, 1, 0, -50, 0, 0, 0, 1, 0 };

    public AutoImageView(Context context, @Nullable AttributeSet attrs) {
        super(context,attrs);
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        ImageView iv = (ImageView) view;
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                iv.setColorFilter(new ColorMatrixColorFilter(BT_SELECTED_DARK));
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                iv.clearColorFilter();
                break;
        }
        return false;
    }
}
