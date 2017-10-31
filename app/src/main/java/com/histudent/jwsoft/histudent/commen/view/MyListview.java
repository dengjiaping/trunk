package com.histudent.jwsoft.histudent.commen.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.histudent.jwsoft.histudent.commen.model.SortModel;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/6/6.
 */
public class MyListview extends ListView {

    private Button b;
    private Activity activity;
    private List<SortModel> list;
    private View view;

    /* 手指滑动的最短距离 */
    private int mShortestDistance = 100;
    /* 手指放下的坐标 */
    private int mXDown, mYDown, mXUp, mYUp;

    public MyListview(Context context) {
        this(context, null);
    }

    public MyListview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//
//        switch (ev.getAction()) {
//
//            case MotionEvent.ACTION_DOWN:
//
//                //获取出坐标来
//                mXDown = (int) ev.getX();
//                mYDown = (int) ev.getY();
//
//                view = getItemMainLayoutByPosition(mXDown, mYDown);
//                if (view != null) {
//                    b = (Button) view.findViewById(R.id.button);
//                }
//                break;
//
//            case MotionEvent.ACTION_MOVE:
//
//                break;
//
//            case MotionEvent.ACTION_UP:
//                mXUp = (int) ev.getX();
//                mYUp = (int) ev.getY();
//
//                if (mXDown == mXUp && mYDown == mYUp) {
//
//                    final int position = pointToPosition(mXDown, mYDown);
//
//                    if (position < list.size() && position > -1) {
//                        PersonalHomepageActivity.start(activity, list.get(position).getUserId());
//                    }
//                }
//
//                if (Math.abs(mYDown - mYUp) < 50) {
//
//                    if ((mXDown - mXUp) > mShortestDistance) {
//
//                        if (b.getVisibility() == View.GONE) {
//                            b.setVisibility(VISIBLE);
//                        }
//
//                    } else if ((mXUp - mXDown) > mShortestDistance) {
//
//                        if (b.getVisibility() == View.VISIBLE) {
//                            b.setVisibility(GONE);
//                        }
//
//                    }
//                }
//
//                break;
//
//        }
//
//        return super.onTouchEvent(ev);
//    }

    public void setOnlistenerData(Activity activity, List<SortModel> data) {

        this.activity = activity;

        this.list = data;

    }

    /**
     * 通过手指的XY坐标得到ItemMainLayout
     *
     * @param x
     * @param y
     * @return
     */
    private View getItemMainLayoutByPosition(int x, int y) {
        int position = pointToPosition(x, y);
        if (position != AdapterView.INVALID_POSITION) {
            View view = getChildAt(position - getFirstVisiblePosition());

            return view;
        }
        return null;
    }

}
