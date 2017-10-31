package com.histudent.jwsoft.histudent.comment2.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.adapter.SortGroupAdapter;

import java.util.List;


/**
 * Created by ZhangYT on 2016/12/1.
 */

public class RightPopWindow extends PopupWindow {


    private View mMenuView;
    private ListView listView;
    private SortGroupAdapter Adapter;

    private OnItemClick onItemClick;


    public RightPopWindow(Activity context, List<Object> list, OnItemClick onItemClick) {
        super(context);
        this.onItemClick = onItemClick;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.nearby_person_pop, null);
        listView = ((ListView) mMenuView.findViewById(R.id.list_view));
        initListView(context, list);


        //设置按钮监听
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(false);

        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(00000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                dismiss();
                return true;
            }
        });

    }

    private void initListView(final Activity context, final List<Object> list) {
        Adapter = new SortGroupAdapter(list, context);
        listView.setAdapter(Adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                ((TextView) view.findViewById(R.id.tv_name)).setPressed(true);
                onItemClick.ItemClick(position);
                RightPopWindow.this.dismiss();
            }
        });
    }

    public interface OnItemClick {
        public void ItemClick(int position);
    }

}
