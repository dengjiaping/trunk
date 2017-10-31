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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 顶部菜单栏PopupWindow
 */
public class TopMenuPopupWindow extends PopupWindow {


    private Button btn_cancel, btn_01, btn_02, btn_03, btn_04, btn_05, btn_06, btn_07;
    View line_01, line_02, line_03, line_04, line_05, line_06, line_07;
    List<View> list_line;
    private View mMenuView;
    private OnCloseListener onCloseListener;
    private List<Button> list_button;
    private boolean isRound;
    private LinearLayout layout;
    private boolean isNeedCancel;

    public void setDismissListener(OnCloseListener onCloseListener) {
        this.onCloseListener = onCloseListener;
    }

    /**
     * \
     *
     * @param context
     * @param itemsOnClick 选项框的点击事件
     * @param buttonName   选项框的内容
     * @param buttonColor  字体颜色
     * @param isNeedCancel 是否需要取消按钮
     */

    public TopMenuPopupWindow(Activity context, OnClickListener itemsOnClick, List<String> buttonName, List<Integer> buttonColor, boolean isNeedCancel) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.alert_dialog, null);
        layout =  mMenuView.findViewById(R.id.pop_layout);
        LinearLayout.LayoutParams params = ((LinearLayout.LayoutParams) layout.getLayoutParams());
        params.setMargins(SystemUtil.dp2px(20), 0, SystemUtil.dp2px(20), 0);
        layout.setLayoutParams(params);
        list_button = new ArrayList<>();


        list_line = new ArrayList<>();

        btn_01 = mMenuView.findViewById(R.id.btn_01);
        btn_02 = mMenuView.findViewById(R.id.btn_02);
        btn_03 = mMenuView.findViewById(R.id.btn_03);
        btn_04 = mMenuView.findViewById(R.id.btn_04);
        btn_05 = mMenuView.findViewById(R.id.btn_05);
        btn_06 = mMenuView.findViewById(R.id.btn_06);
        btn_07 = mMenuView.findViewById(R.id.btn_07);
        btn_cancel = mMenuView.findViewById(R.id.btn_cancel);

        list_button.add(btn_01);
        list_button.add(btn_02);
        list_button.add(btn_03);
        list_button.add(btn_04);
        list_button.add(btn_05);
        list_button.add(btn_06);
        list_button.add(btn_07);


        //分割线

        line_01 = mMenuView.findViewById(R.id.line_01);
        line_02 = mMenuView.findViewById(R.id.line_02);
        line_03 = mMenuView.findViewById(R.id.line_03);
        line_04 = mMenuView.findViewById(R.id.line_04);
        line_05 = mMenuView.findViewById(R.id.line_05);
        line_06 = mMenuView.findViewById(R.id.line_06);
        line_07 = mMenuView.findViewById(R.id.line_07);


        list_line.add(line_01);
        list_line.add(line_02);
        list_line.add(line_03);
        list_line.add(line_04);
        list_line.add(line_05);
        list_line.add(line_06);
        list_line.add(line_07);

        //设置button和分割线的显示
        for (int i = 0; i < buttonName.size(); i++) {
            list_line.get(i).setVisibility(View.VISIBLE);
            list_button.get(i).setVisibility(View.VISIBLE);//显示按钮
            list_button.get(i).setText(buttonName.get(i)); //设置按钮内容
            list_button.get(i).setTextColor(buttonColor.get(i)); //设置按钮字体颜色
            list_button.get(i).setOnClickListener(itemsOnClick); //设置按钮监听
        }

        if (!isNeedCancel) {
            btn_cancel.setVisibility(View.GONE);
            list_line.get(buttonColor.size() - 1).setVisibility(View.GONE);

        }

        //取消按钮
        btn_cancel.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                //销毁弹出框
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

    @Override
    public void dismiss() {
        super.dismiss();
        if (onCloseListener != null) {
            onCloseListener.onDismiss();
        }
    }

    public interface OnCloseListener {
        void onDismiss();
    }
}
