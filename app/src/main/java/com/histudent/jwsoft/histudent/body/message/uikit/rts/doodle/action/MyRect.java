package com.histudent.jwsoft.histudent.body.message.uikit.rts.doodle.action;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 方框
 * <p/>
 * Created by Administrator on 2015/6/24.
 */
public class MyRect extends Action {
    private Paint mPaint;
    public MyRect(Float x, Float y, Integer color, Integer size) {
        super(x, y, color, size);
        initPaint();
    }

    public void onDraw(Canvas canvas) {

        canvas.drawRect(startX, startY, stopX, stopY, mPaint);
    }

    private void initPaint(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(color);
        mPaint.setStrokeWidth(size);
    }

    public void onMove(float mx, float my) {
        stopX = mx;
        stopY = my;
    }
}
