package com.histudent.jwsoft.histudent.commen.utils;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.body.hiworld.bean.SimpleUserModel;
import com.histudent.jwsoft.histudent.comment2.utils.ViewUtils;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.List;

/**
 * Created by ZhangYT on 2017/4/14.
 */

public class ListenerUtils {


    public static void setTextChangeListener(final EditText et, final TextView tv) {

        if (et != null) {
            et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (tv != null) {
                        tv.setText(et.getText().toString().length() + "/" + ViewUtils.getMaxLength(et));
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    public static void setTextChangeListener(final EditText et, final Activity activity, final String toastContent) {

        if (et != null) {
            et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    if (et.getText().toString().length()>=ViewUtils.getMaxLength(et)-1){
                        Toast.makeText(activity,toastContent,Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    public static void setOnDeleteUserListener(final EditText et, final List<SimpleUserModel> list) {
        Log.e("---before", list.size() + "");
        final int[] cursorPostion = new int[1];

        if (et != null) {

            et.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    Log.e("---before", keyCode + "");
                    if (keyCode == KeyEvent.KEYCODE_DEL) {

                        if (et.getText().toString().trim().length() > 0 && list != null && list.size() > 0) {
                            String content = et.getText().toString();
                         cursorPostion[0] = et.getSelectionEnd();
                            if (cursorPostion[0] > 0 && content.contains("@")) {

                                String left = content.substring(0, cursorPostion[0]);//截取光标之前的内容
                                String right = content.substring(cursorPostion[0], content.length());//截取光标之前的内容

                                if (!StringUtil.isEmpty(left) && left.contains("@")) {
                                    String atUser = left.substring(left.lastIndexOf("@"), cursorPostion[0]);//截取光标之前@之后的内容
                                    for (SimpleUserModel model : list) {

                                        if (atUser.equals("@" + model.getName())) {
                                            atUser = "";
                                            et.setSelection(cursorPostion[0] - ("@" + model.getName()).length(), cursorPostion[0]);
//                            et.setText(EmotionUtils.convertNormalStringToSpannableString(left + atUser + right));

                                            break;
                                        }
                                    }
                                }

                            }
                        }

                    }

                    return false;
                }
            });

//            et.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                    et.setSelection(et.getSelectionEnd());
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//
//                }
//            });

        }
    }
}
