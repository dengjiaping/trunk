package com.histudent.jwsoft.histudent.comment2.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.listener.DialogButtonListener;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ZhangYT on 2016/9/8.
 */
public class popWindowUtils {


    private static Dialog dialog_select;
    public static String tel;
    public static boolean isCanTranfer;


    public static void showTranferGroupDialog(final Activity activity, final DialogButtonListener listener_left,boolean isClass) {

        isCanTranfer = false;
        tel = "";

        View view = LayoutInflater.from(activity).inflate(R.layout.transfer_group, null);

        dialog_select = new Dialog(activity, R.style.TipDialog);
        dialog_select.setContentView(view);
        dialog_select.setCancelable(false);

        Button button_left = (Button) view.findViewById(R.id.dialog_left);
        Button button_right = (Button) view.findViewById(R.id.dialog_right);
        TextView tv_title= ((TextView) view.findViewById(R.id.tv_title));
        final EditText et = ((EditText) view.findViewById(R.id.et_tell));

        if (isClass){
            tv_title.setText("转让班级");
            et.setHint("输入新班主任手机号");
        }else {
            tv_title.setText("转让社群");
            et.setHint("输入新群主手机号");
        }

        button_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                tel = et.getText().toString().trim();
                if (StringUtil.isEmpty(et.getText().toString().trim())) {

                    Toast.makeText(activity, "请输入手机号", Toast.LENGTH_LONG).show();
                } else {

                    Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
                    Matcher m = p.matcher(et.getText().toString().trim());

                    if (!m.matches()) {
                        Toast.makeText(activity, "输入手机号格式不正确！", Toast.LENGTH_LONG).show();
                    } else {
                        isCanTranfer = true;
                        dialog_select.dismiss();
                    }
                }
                listener_left.setOnDialogButtonListener();
            }
        });


        button_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_select.dismiss();
            }
        });

        dialog_select.show();

    }


    public static void showJoinDialog(Activity activity, final DialogButtonListener listener_right) {

        View view = LayoutInflater.from(activity).inflate(R.layout.alertdialog_select, null);

        dialog_select = new Dialog(activity, R.style.TipDialog);
        dialog_select.setContentView(view);
        dialog_select.setCancelable(false);

        TextView textView_title = (TextView) view.findViewById(R.id.dialog_title);
        TextView textView_content = (TextView) view.findViewById(R.id.dialog_content);
        textView_content.setText(R.string.joinPop);
        textView_title.setText("提示");
        Button button_left = (Button) view.findViewById(R.id.dialog_right);
        Button button_right = (Button) view.findViewById(R.id.dialog_left);

        button_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener_right.setOnDialogButtonListener();
                dialog_select.dismiss();
                isCanTranfer = true;
            }
        });

        button_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_select.dismiss();
            }
        });

        dialog_select.show();

    }

}
