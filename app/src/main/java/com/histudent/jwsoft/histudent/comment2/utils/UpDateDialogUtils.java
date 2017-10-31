package com.histudent.jwsoft.histudent.comment2.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.activity.MyWebActivity;
import com.histudent.jwsoft.histudent.commen.bean.AdvertisementModel;
import com.histudent.jwsoft.histudent.commen.listener.DialogButtonListener;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.commen.view.swipemenulistview.XCRoundRectImageView;
import com.netease.nim.uikit.common.util.string.StringUtil;

/**
 * 更新对话框
 * * Created by ZhangYT on 2016/10/14.
 */

public class UpDateDialogUtils {

    /**
     * @param title             提示框的标题
     * @param content           提示框的内容
     * @param button_left_name  左边一个按钮的名称
     * @param listener_left     左边按钮的监听
     * @param button_right_name
     * @param listener_right
     * @param context
     * @param isForceUpadate    是否是强制更新
     */

    public static void showNoticeDialog(String title, String content, String button_left_name, final DialogButtonListener listener_left,
                                        String button_right_name, final DialogButtonListener listener_right, Context context, boolean isForceUpadate) {

        View view = LayoutInflater.from(context).inflate(R.layout.update_dialog_notice, null);
        final Dialog dialog_select;
        dialog_select = new Dialog(context, R.style.ActionDialog);
        dialog_select.setContentView(view);
        dialog_select.setCancelable(false);

        TextView textView_title = (TextView) view.findViewById(R.id.dialog_title);
        TextView textView_content = (TextView) view.findViewById(R.id.dialog_content);
        final Button button_left = (Button) view.findViewById(R.id.dialog_left);
        Button button_right = (Button) view.findViewById(R.id.dialog_right);

        if (title == null) {
            textView_title.setVisibility(View.GONE);
        }

        if (isForceUpadate) {
            button_right.setVisibility(View.GONE);
        }

        textView_title.setText(title);
        textView_content.setText(content);
        button_left.setText(button_left_name);
        button_right.setText(button_right_name);

        button_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_select.dismiss();
                if (listener_left != null) {
                    listener_left.setOnDialogButtonListener();
                }

            }
        });

        button_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_select.dismiss();

                if (listener_right != null) {
                    listener_right.setOnDialogButtonListener();
                }
            }
        });

        dialog_select.show();

    }


    /**
     * @param title             下载框标题
     * @param button_right_name 右边按钮的文字
     * @param listener_right
     * @param context
     * @param isFoceUpdata      是否为强制更新
     * @return
     */
    public static Dialog showLoadingDialog(String title, String button_right_name, final DialogButtonListener listener_right, Context context, boolean isFoceUpdata) {
        final Dialog dialog_select;
        View view = LayoutInflater.from(context).inflate(R.layout.update_dialog, null);

        dialog_select = new Dialog(context, R.style.TipDialog);
        dialog_select.setContentView(view);
        dialog_select.setCancelable(false);
        TextView textView_title = (TextView) view.findViewById(R.id.dialog_title);
        Button button_left = (Button) view.findViewById(R.id.dialog_left);
        Button button_right = (Button) view.findViewById(R.id.dialog_right);

        if (title == null) {
            textView_title.setVisibility(View.GONE);
        }

        textView_title.setText(title);
        button_right.setText(button_right_name);

        button_left.setVisibility(View.GONE);

        if (isFoceUpdata) {
            view.findViewById(R.id.linearLayout).setVisibility(View.GONE);

        }

        button_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_select.dismiss();
                if (listener_right != null) {
                    listener_right.setOnDialogButtonListener();
                }
            }
        });

        dialog_select.show();
        return dialog_select;

    }


    /**
     * @param update_listener 右边按钮的文字
     * @param updateStr
     * @param context
     * @return
     */
    public static Dialog showUpdateView(final DialogButtonListener update_listener, String updateStr, Context context) {
        final Dialog dialog_select;
        View view = LayoutInflater.from(context).inflate(R.layout.version_upadate_layout, null);

        dialog_select = new Dialog(context, R.style.TipDialog);
        dialog_select.setContentView(view);
        dialog_select.setCancelable(false);
        final Button upldate_btn = (Button) view.findViewById(R.id.upldate_btn);
        ImageView iv_cancel = (ImageView) view.findViewById(R.id.iv_cancel);

        TextView tv = ((TextView) view.findViewById(R.id.tv_update));
        if (!StringUtil.isEmpty(updateStr)) {
            tv.setText(updateStr);
        }


        upldate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_select.dismiss();
                if (upldate_btn != null) {
                    update_listener.setOnDialogButtonListener();
                }
            }
        });

        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_select.dismiss();

            }
        });

        dialog_select.show();
        return dialog_select;

    }


    public static Dialog showAdvertisementView(final Activity context, final AdvertisementModel model) {
        final Dialog dialog_select;
        View view = LayoutInflater.from(context).inflate(R.layout.advertisement_layout, null);

        dialog_select = new Dialog(context, R.style.TipDialog);
        dialog_select.setContentView(view);
        dialog_select.setCancelable(false);
        final IconView close = (IconView) view.findViewById(R.id.close);
        LinearLayout linear = ((LinearLayout) view.findViewById(R.id.linear));
        XCRoundRectImageView log = ((XCRoundRectImageView) view.findViewById(R.id.iv_log));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(SystemUtil.getScreenWind() * 3 / 5,
                SystemUtil.getScreenHeight_() * 3 / 5);
        linear.setLayoutParams(params);


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_select.dismiss();

            }
        });
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_select.dismiss();

                MyWebActivity.start(context, model.getLink());
            }
        });

        dialog_select.show();
        return dialog_select;

    }

}
