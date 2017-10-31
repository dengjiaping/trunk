package com.histudent.jwsoft.histudent.commen.view.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.ShareUtils;
import com.histudent.jwsoft.histudent.commen.bean.MyShareBean;

/**
 * 分享Window
 */
public class SharePopupWindow extends PopupWindow {


    private LinearLayout btn_01, btn_02, btn_03, btn_04;
    private TextView btn_cancel;
    private View mMenuView;

    public SharePopupWindow(final BaseActivity context, final MyShareBean bean) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.wind_share_layout, null);

        btn_01 = (LinearLayout) mMenuView.findViewById(R.id.weixin);
        btn_02 = (LinearLayout) mMenuView.findViewById(R.id.weixin_);
        btn_03 = (LinearLayout) mMenuView.findViewById(R.id.qq);
        btn_04 = (LinearLayout) mMenuView.findViewById(R.id.qq_);
        btn_cancel = (TextView) mMenuView.findViewById(R.id.btn_cancel);

        /**
         * 微信分享
         */
        btn_01.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                ShareUtils.share(context, bean, ShareUtils.WEIXIN);
            }
        });

        /**
         * 朋友圈分享
         */
        btn_02.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                ShareUtils.share(context, bean, ShareUtils.WEIXIN_CIRCLE);
            }
        });

        /**
         * QQ分享
         */
        btn_03.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                ShareUtils.share(context, bean, ShareUtils.QQ);
            }
        });

        /**
         * QQ空间分享
         */
        btn_04.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                ShareUtils.share(context, bean, ShareUtils.QZONE);
            }
        });

        /**
         * 取消按钮
         */
        btn_cancel.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                dismiss();
            }
        });

        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(context.getResources().getColor(R.color.hiworld_fram_gb));
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                dismiss();
                return true;
            }
        });

    }

}
