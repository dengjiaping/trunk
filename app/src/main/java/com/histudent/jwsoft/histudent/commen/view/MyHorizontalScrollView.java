package com.histudent.jwsoft.histudent.commen.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * Created by liuguiyu-pc on 2017/5/22.
 */

public class MyHorizontalScrollView extends HorizontalScrollView {

    private int downX = 0;
    private int currentPage = 0;
    private boolean isMove=false;
    private OnSmoothListener mListener;


    public void setOnSmoothListener(OnSmoothListener mListener) {
        this.mListener = mListener;
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyHorizontalScrollView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setHorizontalScrollBarEnabled(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    /**
     * isMove 有时候ACTION_DOWN不触发，但是ACTION_MOVE，ACTION_UP 触发
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isMove){
                    downX = (int) ev.getRawX();
                    isMove = true;
                }

                break;
            case MotionEvent.ACTION_UP:
                isMove = false;
                if (Math.abs((ev.getRawX() - downX)) > (getWidth()) / 8) {
                    if (ev.getRawX() - downX > 0) {
                        smoothScrollToPrePage();
                    } else {
                        smoothScrollToNextPage();
                    }
                } else {
                    smoothScrollToCurrent();
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    private void smoothScrollToCurrent() {
        smoothScrollTo((getWidth()) * currentPage, 0);
        mListener.onSmooth(currentPage);
    }

    private void smoothScrollToNextPage() {
        currentPage++;
        smoothScrollTo((getWidth()) * currentPage, 0);
        mListener.onSmooth(currentPage);
    }

    private void smoothScrollToPrePage() {
        if (currentPage > 0) {
            currentPage--;
            smoothScrollTo((getWidth()) * currentPage, 0);
            mListener.onSmooth(currentPage);
        }
    }

    public void setCurrentPage(int position){
        smoothScrollTo((getWidth()) * position, 0);
    }

    /**
     * 下一页
     */
    public void nextPage() {
        smoothScrollToNextPage();
        mListener.onSmooth(currentPage);
    }

    /**
     * 上一页
     */
    public void prePage() {
        smoothScrollToPrePage();
        mListener.onSmooth(currentPage);
    }


    public interface OnSmoothListener{
        void onSmooth(int currentPage);
    }
}
