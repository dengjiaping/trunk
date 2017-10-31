package com.histudent.jwsoft.histudent.comment2.utils;

import android.app.Activity;
import android.content.Context;
import android.text.InputFilter;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;

import java.lang.reflect.Field;

/**
 * Created by ZhangYT on 2017/1/7.
 */

public class ViewUtils {

    public static int getListViewHeight(ListView listView) {
        BaseAdapter adapter = (BaseAdapter) listView.getAdapter();
        if (adapter == null) {
            return -2;
        }
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View mView = adapter.getView(i, null, listView);
            mView.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

            totalHeight += mView.getMeasuredHeight() + 1;

        }
        return totalHeight;
    }


    public static int getGridViewHeight(GridView gridView) {
        // 获取listview的adapter
        BaseAdapter listAdapter = ((BaseAdapter) gridView.getAdapter());
        if (listAdapter == null) {
            return -1;
        }
        // 固定列宽，有多少列
        int col = 3;// listView.getNumColumns();
        int totalHeight = 0;
        // i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
        // listAdapter.getCount()小于等于8时计算两次高度相加
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            // 获取listview的每一个item
            View listItem = listAdapter.getView(i, null, gridView);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }

        // 获取listview的布局参数
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        // 设置高度
        params.height = totalHeight;
        // 设置margin
        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        // 设置参数
        gridView.setLayoutParams(params);
        return totalHeight;
    }


    //获取EidtTextshe设置的最大输入长度

    public static int getMaxLength(EditText editText) {
        int length = 0;
        try {
            InputFilter[] inputFilters = editText.getFilters();
            for (InputFilter filter : inputFilters) {
                Class<?> c = filter.getClass();
                if (c.getName().equals("android.text.InputFilter$LengthFilter")) {
                    Field[] f = c.getDeclaredFields();
                    for (Field field : f) {
                        if (field.getName().equals("mMax")) {
                            field.setAccessible(true);
                            length = (Integer) field.get(filter);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return length;
    }


    public static void hideSoftKeyBoard(Context context) {

        if (context == null) {
            return;
        }
        final View v = ((Activity) context).getWindow().peekDecorView();
        if (v != null && v.getWindowToken() != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }


    /***
     *  改变按钮的颜色和点击状态
     * @param activity
     * @param tv
     * @param isable
     */
    public static void changeClickable(Activity activity, TextView tv, boolean isable) {

        if (isable) {
            tv.setBackground(activity.getResources().getDrawable(R.drawable.btn_click_true));
            tv.setEnabled(true);
        } else {
            tv.setBackground(activity.getResources().getDrawable(R.drawable.btn_click_false));
            tv.setEnabled(false);
        }
    }


    /**
     * 改变右边的标题的颜色和点击状态
     *
     * @param activity
     * @param isable
     */
    public static void changeTitleRightClickable(Activity activity, boolean isable) {

        if (isable) {
            ((TextView) activity.findViewById(R.id.title_right_text)).setTextColor(activity.getResources().getColor(R.color.green_color));
            activity.findViewById(R.id.title_right).setEnabled(true);
        } else {

            ((TextView) activity.findViewById(R.id.title_right_text)).setTextColor(activity.getResources().getColor(R.color.text_black_h1));
            activity.findViewById(R.id.title_right).setEnabled(false);

        }
    }
}
