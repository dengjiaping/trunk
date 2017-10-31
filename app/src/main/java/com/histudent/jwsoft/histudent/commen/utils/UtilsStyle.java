package com.histudent.jwsoft.histudent.commen.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.SparseArray;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

/**
 * 设置状态栏透明工具类
 * Created by baopengjian on 2017/4/7.
 */

public class UtilsStyle {

    /**
     * 布局中必须有一个id为status_bar的view来设置状态栏背景
     * 必须要在 setContentView之后调用
     *
     * @param activity 当前页面
     */
    public static void setTranslateStatusBar(Activity activity) {
        // 4.4以上处理
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // android
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);// 状态栏透明
//            View status_bar = activity.findViewById(R.id.status_bar);// 标题栏id
//            if (status_bar != null) {
//                ViewGroup.LayoutParams params = status_bar.getLayoutParams();
//                params.height = getStatusBarHeight(activity);
//                status_bar.setLayoutParams(params);
//            }
        }
        //5.0 以上处理
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


    private static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height",
                "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * Flag只有在使用了FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
     * 并且没有使用 FLAG_TRANSLUCENT_STATUS的时候才有效，也就是只有在状态栏全透明的时候才有效。
     *
     * @param activity
     * @param bDark
     */
    public static void setStatusBarMode(Activity activity, boolean bDark) {
        //6.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = activity.getWindow().getDecorView();
            if (decorView != null) {
                int vis = decorView.getSystemUiVisibility();
                if (bDark) {
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                decorView.setSystemUiVisibility(vis);
            }

        }
    }


    /**
     * listView的滑动监听
     *
     * @param list
     * @param scrollY
     */
    public static void setOnScrollListener(ListView list, ScrollY scrollY) {


        list.setOnScrollListener(new AbsListView.OnScrollListener() {

            private SparseArray recordSp = new SparseArray(0);
            private int mCurrentfirstVisibleItem = 0;


            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {

                mCurrentfirstVisibleItem = arg1;
                View firstView = arg0.getChildAt(0);
                if (null != firstView) {
                    ItemRecod itemRecord = (ItemRecod) recordSp.get(arg1);
                    if (null == itemRecord) {
                        itemRecord = new ItemRecod();
                    }
                    itemRecord.height = firstView.getHeight();
                    itemRecord.top = firstView.getTop();
                    recordSp.append(arg1, itemRecord);
                    int y = getScrollY();//滚动距离
                    scrollY.scrollY(y);
                }

            }

            private int getScrollY() {
                int height = 0;
                for (int i = 0; i < mCurrentfirstVisibleItem; i++) {
                    if(recordSp != null){
                        ItemRecod itemRecod = (ItemRecod) recordSp.get(i);
                        if (itemRecod != null)
                            height += itemRecod.height;
                    }

                }
                ItemRecod itemRecod = (ItemRecod) recordSp.get(mCurrentfirstVisibleItem);
                if (null == itemRecod) {
                    itemRecod = new ItemRecod();
                }
                return height - itemRecod.top;
            }


            class ItemRecod {
                int height = 0;
                int top = 0;
            }

        });
    }

    public interface ScrollY {
        void scrollY(int y);
    }


}
