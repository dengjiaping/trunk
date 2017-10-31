package com.histudent.jwsoft.histudent.body.message.uikit.rts.doodle.action;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 点
 * <p/>
 * Created by Administrator on 2015/6/24.
 */
public class MyPoint extends Action {
    private Paint mPaint;
    public MyPoint(Float x, Float y, Integer color, Integer size) {
        super(x, y, color, size);
        initPaint();
    }

    @Override
    public void onStart(Canvas canvas) {
        super.onStart(canvas);
        onDraw(canvas);
    }

    public void onDraw(Canvas canvas) {

        canvas.drawPoint(startX, startY, mPaint);
    }

    private void initPaint(){
        mPaint = new Paint();
        mPaint.setColor(color);
        mPaint.setStrokeWidth(size);

    }

    @Override
    public void onMove(float mx, float my) {

    }
}
