package com.histudent.jwsoft.histudent.body.message.uikit.rts.doodle.action;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * 橡皮擦（与画布背景色相同的Path）
 * <p/>
 * Created by Administrator on 2015/6/24.
 */
public class MyEraser extends Action {
    private Path path;
    private Paint mPaint;

    public MyEraser(Float x, Float y, Integer color, Integer size) {
        super(x, y, color, size);
        path = new Path();
        path.moveTo(x, y);
        path.lineTo(x, y);

        initPaint();
    }

    private void initPaint(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(color);
        mPaint.setStrokeWidth(size);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    public boolean isSequentialAction() {
        return true;
    }

    public void onDraw(Canvas canvas) {
        canvas.drawPath(path, mPaint);
    }

    public void onMove(float mx, float my) {
        path.lineTo(mx, my);
    }
}
