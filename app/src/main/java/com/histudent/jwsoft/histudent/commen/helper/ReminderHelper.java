package com.histudent.jwsoft.histudent.commen.helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.account.login.model.CheckUpdateBean;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.listener.DialogButtonListener;
import com.histudent.jwsoft.histudent.commen.utils.DataUtils;
import com.histudent.jwsoft.histudent.commen.utils.MyUpdateManager;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.view.LoadingDialog;
import com.histudent.jwsoft.histudent.commen.view.PointDialog;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuguiyu-pc on 2017/4/20.
 * 按钮提示帮助类
 */

public class ReminderHelper {

    private static ReminderHelper helper;
    private static Handler mHandler;
    private static LoadingDialog dialogFlower;
    private static PointDialog dialogPencil;

    private ReminderHelper() {
    }

    public static synchronized ReminderHelper getIntentce() {
        if (helper == null) {
            helper = new ReminderHelper();
            if (mHandler == null) {
                mHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                    }
                };
            }
        }
        return helper;
    }

    /**
     * 修改View的背景与可否点击
     *
     * @param codeText
     * @param flag
     */
    public void exchangeButtonBG(View codeText, boolean flag) {
        codeText.setEnabled(flag);
        codeText.setBackgroundResource(flag ? R.drawable.green_button_bg_circle : R.drawable.green_button_bg_circle_);
    }

    /**
     * 修改View的背景与可否点击
     *
     * @param codeText
     * @param flag
     */
    public void exchangeButtonBG_(View codeText, boolean flag) {
        codeText.setClickable(flag);
        codeText.setBackgroundResource(flag ? R.drawable.green_button_bg : R.drawable.gray_button_bg);
    }

    /**
     * 监听EditText是否为空
     */
    public boolean listenerEditIsEmpty(EditText... editText) {
        boolean flag = true;
        for (EditText edit : editText) {
            flag = flag && (!TextUtils.isEmpty(edit.getText().toString()));
        }
        return flag;
    }

    /**
     * 显示（不显示）输入的密码
     */
    private boolean flag = true;

    public void exchangePswStatue(Activity activity, IconView checkBox, EditText edit_password) {
        SystemUtil.hideSoftKeyboard(activity);
        if (flag) {
            flag = false;
            checkBox.setText(R.string.icon_eye);
            edit_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            flag = true;
            checkBox.setText(R.string.icon_eye2);
            edit_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        edit_password.setSelection(edit_password.getText().length());
    }

    /**
     * 显示选择对话框(两个按钮)
     */
    public void showDialog(Context context, String title, String content, String button_left_name, final DialogButtonListener listener_left, String button_right_name, final DialogButtonListener listener_right) {

        View view = LayoutInflater.from(context).inflate(R.layout.alertdialog_select, null);

        final Dialog dialog_select = new Dialog(context, R.style.TipDialog);
        dialog_select.setContentView(view);
        dialog_select.setCancelable(false);


        TextView textView_title = (TextView) view.findViewById(R.id.dialog_title);
        TextView textView_content = (TextView) view.findViewById(R.id.dialog_content);
        Button button_left = (Button) view.findViewById(R.id.dialog_left);
        Button button_right = (Button) view.findViewById(R.id.dialog_right);


        if (StringUtil.isEmpty(title)) {
            textView_title.setVisibility(View.GONE);
        }

        if (StringUtil.isEmpty(button_left_name) || StringUtil.isEmpty(button_right_name)) {
            view.findViewById(R.id.line).setVisibility(View.GONE);
        }

        if (StringUtil.isEmpty(button_left_name)) {
            button_left.setVisibility(View.GONE);
        }
        if (StringUtil.isEmpty(button_right_name)) {
            button_right.setVisibility(View.GONE);
        }

        textView_title.setText(title);
        textView_content.setText(content);
        button_left.setText(button_left_name);
        button_right.setText(button_right_name);


        button_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener_left != null) {
                    dialog_select.dismiss();
                    listener_left.setOnDialogButtonListener();
                }
            }
        });

        button_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listener_right != null) {
                    dialog_select.dismiss();
                    listener_right.setOnDialogButtonListener();
                }

            }
        });

        dialog_select.show();

    }

    /**
     * 显示选择对话框(一个按钮)
     */
    public void showDialog(Context context, String content, String button_name, final DialogButtonListener listener_left) {
        showDialog(context, null, content, button_name, listener_left, null, null);
    }

    /**
     * 显示Toast
     *
     * @param context
     * @param tvString
     */

    public void ToastShow(Context context, String tvString) {
        if (context == null || TextUtils.isEmpty(tvString)) return;
        View layout = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(tvString);
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    /**
     * 显示Toast
     *
     * @param context
     * @param tvString
     */

    public void ToastShow_with_image(Context context, String tvString, int img) {
        View layout = LayoutInflater.from(context).inflate(R.layout.toast_layout_with_image, null);
        TextView text = (TextView) layout.findViewById(R.id.text);
        IconView iconView = (IconView) layout.findViewById(R.id.toast_imag);
        iconView.setText(img);
        text.setText(tvString);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }


    /**
     * 免责声明
     */
    public void showStatementDialog(Activity context, final DialogButtonListener buttomListener, final DialogButtonListener readListener) {

        View view = LayoutInflater.from(context).inflate(R.layout.statement_layout, null);

        final Dialog dialog_select = new Dialog(context, R.style.TipDialog);
        dialog_select.setContentView(view);
        dialog_select.setCancelable(false);

        TextView textView_instr = (TextView) view.findViewById(R.id.tv_instr);
        IconView close = ((IconView) view.findViewById(R.id.close));
        Button button_right = (Button) view.findViewById(R.id.dialog_left);

        DataUtils.setColorText(textView_instr.getText().toString(),
                textView_instr.getText().toString().indexOf("《"),
                textView_instr.getText().toString().indexOf("》") + 1,
                textView_instr, context.getResources().getColor(R.color.green_color));


        //关闭按钮
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_select.dismiss();
            }
        });


        //阅读免责声明
        textView_instr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                readListener.setOnDialogButtonListener();
            }
        });

        //加入按钮
        button_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (buttomListener != null) {
                    dialog_select.dismiss();
                    buttomListener.setOnDialogButtonListener();
                }

            }
        });

        if (!context.isFinishing())
            dialog_select.show();
    }


    /**
     * @param context
     * @param title        标题
     * @param itemsOnClick 选项框的点击事件
     * @param buttonName   选项框的名称
     * @param buttonColor  字的颜色
     * @param isNeedCancel 是否需要取消按钮
     */
    public void showTopMenuDialog(Activity context, String title, final View.OnClickListener itemsOnClick,
                                  List<String> buttonName, List<Integer> buttonColor, boolean isNeedCancel) {
        List<Button> list_button;
        List<View> list_line;
        Button btn_01, btn_02, btn_03, btn_04, btn_05, btn_06, btn_07, btn_cancel;
        View line_title, line_01, line_02, line_03, line_04, line_05, line_06, line_07;

        View mMenuView = View.inflate(context, R.layout.action_layout, null);
        final AppCompatDialog dialog_select = new AppCompatDialog(context, R.style.ActionDialog);
        Button tv_title = mMenuView.findViewById(R.id.title);
        dialog_select.setContentView(mMenuView);


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


        line_title = mMenuView.findViewById(R.id.line_title);
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


        if (StringUtil.isEmpty(title)) {
            tv_title.setVisibility(View.GONE);
            line_title.setVisibility(View.GONE);
        } else {
            tv_title.setText(title);
            line_title.setVisibility(View.VISIBLE);
        }

        for (int i = 0; i < buttonName.size(); i++) {
            list_button.get(i).setVisibility(View.VISIBLE);//显示按钮
            list_line.get(i).setVisibility(View.VISIBLE);
            list_button.get(i).setText(buttonName.get(i)); //设置按钮内容
            list_button.get(i).setTextColor(buttonColor.get(i)); //设置按钮字体颜色
            list_button.get(i).setOnClickListener((View v) -> {
                dialog_select.dismiss();
                itemsOnClick.onClick(v);
            });
        }

        if (!isNeedCancel) {
            btn_cancel.setVisibility(View.GONE);
            if (buttonColor.size() > 0)
                list_line.get(buttonColor.size() - 1).setVisibility(View.GONE);
        }

        btn_cancel.setOnClickListener((View v) -> dialog_select.dismiss());

        if (!context.isFinishing())
            dialog_select.show();
    }


    //发随记专用的弹出框
    public void showEssayMenuDialog(final Activity context, final View.OnClickListener itemsOnClick) {

        final Button btn_01, btn_02;

        View mMenuView = View.inflate(context, R.layout.essay_popwindow, null);
        final Dialog dialogselect = new Dialog(context, R.style.ActionDialog);
        btn_01 = ((Button) mMenuView.findViewById(R.id.btn_01));
        btn_02 = ((Button) mMenuView.findViewById(R.id.btn_02));


        btn_01.setTextColor(Color.rgb(51, 51, 51));
        btn_02.setTextColor(Color.rgb(51, 51, 51));

        dialogselect.setContentView(mMenuView);


        btn_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemsOnClick.onClick(v);
                dialogselect.dismiss();
            }
        });

        mMenuView.findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemsOnClick.onClick(btn_01);
                dialogselect.dismiss();
            }
        });


        btn_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogselect.dismiss();
                itemsOnClick.onClick(v);
            }
        }); //设置按钮监听

        if (!context.isFinishing())
            dialogselect.show();
    }

    /**
     * 版本更新提示栏
     *
     * @param context
     * @return
     */
    public static void showUpdateView_login(Context context, CheckUpdateBean checkUpdateBean, DialogButtonListener update_listener) {

        if (checkUpdateBean == null) return;

        if (checkUpdateBean.getStatus() == 0) {//不更新
            if (update_listener != null)
                update_listener.setOnDialogButtonListener();
            return;
        }

        View view = LayoutInflater.from(context).inflate(R.layout.version_upadate_layout_login, null);

        Dialog dialog_select_login = new Dialog(context, R.style.TipDialog);
        dialog_select_login.setContentView(view);
        dialog_select_login.setCancelable(false);

        TextView upldate_btn = (TextView) view.findViewById(R.id.upldate_btn);
        TextView iv_cancel = (TextView) view.findViewById(R.id.iv_cancel);
        TextView line = (TextView) view.findViewById(R.id.line);

        if (checkUpdateBean.getStatus() == 2) {//强制更新
            iv_cancel.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        }

        TextView tv = ((TextView) view.findViewById(R.id.contnet));
        if (!StringUtil.isEmpty(checkUpdateBean.getUpdatedesc())) {
            tv.setText(checkUpdateBean.getUpdatedesc());
        }

        upldate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_select_login.dismiss();
                MyUpdateManager myUpdateManager = new MyUpdateManager(context);
                myUpdateManager.LoadingApk(checkUpdateBean.getStatus(), checkUpdateBean.getUpdateurl());
            }
        });

        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_select_login.dismiss();
                if (update_listener != null)
                    update_listener.setOnDialogButtonListener();
            }
        });

        dialog_select_login.show();
    }

    /**
     * 显示菊花形状的loading
     *
     * @param activity
     */
    public void showshowFlowerLoading(final Activity activity) {

        ((BaseActivity) activity).showLoadingImage(activity, LoadingType.FLOWER);
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (dialogFlower == null)
//                    dialogFlower = new LoadingDialog(BaseActivity.activity);
//                if (activity!= null && !activity.isFinishing()&&dialogFlower!=null) {
//                    dialogFlower.show();
//                    dialogFlower.setCanceledOnTouchOutside(false);
//                }
//            }
//        }, 0);
    }

    /**
     * 显示铅笔形状的loading
     *
     * @param activity
     */
    public void showshowPencilLoading(final Activity activity) {

        ((BaseActivity) activity).showLoadingImage(activity, LoadingType.PENCIL);
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                if (dialogPencil == null)
//                    dialogPencil = new PointDialog(activity);
//                if (activity!=null&&!activity.isFinishing()) {
//                    dialogPencil.show();
//                    dialogPencil.setCanceledOnTouchOutside(false);
//                }
//            }
//        }, 0);
    }

    /**
     * 关闭加载时的等待层
     */
    public void closeLoadingImage(Activity activity) {
        ((BaseActivity) activity).closeLoadingImage();

//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (dialogFlower != null && dialogFlower.isShowing()) {
//                    dialogFlower.dismiss();
//                }
//                if (dialogPencil != null && dialogPencil.isShowing()) {
//                    dialogPencil.dismiss();
//                }
//            }
//        }, 0);
    }
}