package com.histudent.jwsoft.histudent.body.message.uikit.rts.doodle.action;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 空心圆
 * <p/>
 * Created by Administrator on 2015/6/24.
 */
public class MyCircle extends Action {
    private float radius;
    private Paint mPaint;

    public MyCircle(Float x, Float y, Integer color, Integer size) {
        super(x, y, color, size);
        radius = 0;
        mPaint= new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(color);
        mPaint.setStrokeWidth(size);
    }

    public void onDraw(Canvas canvas) {
        canvas.drawCircle((startX + stopX) / 2, (startY + stopY) / 2, radius,
                mPaint);
    }

    public void onMove(float mx, float my) {
        stopX = mx;
        stopY = my;
        radius = (float) ((Math.sqrt((mx - startX) * (mx - startX)
                + (my - startY) * (my - startY))) / 2);
    }
}
