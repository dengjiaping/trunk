/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.histudent.jwsoft.histudent.commen.view.dateSelect;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;

/**
 * 日期选择器对话框
 */

public class TheDatePickerDialog extends Dialog {

    private QNumberPicker datePicker_year, datePicker_month, datePicker_day;// 年,月，日选择框
    private int[] initday;// 初始化的年，月，日。
    private Button button_left, button_right;// 取消，确定按钮！
    private TextView textView_clean;// 清空
    private Callback callback;// 回传数据的接口
    private String[] day_limit;// 时间选择器选择范围；
    private String[] info = {"", "", ""};// 日期选择器选择内容
    private int flag;// 显示选择器类型标志位

    /**
     * 日期选择器构造函数
     *
     * @param context   上下文对象
     * @param initday   初始化显示的日期，以数组形式表示。
     * @param day_limit 日期选择器的取值范围，以数组形式表示。
     * @param flag      是否显示日期滚动条
     * @param callback  点击确认之后的回调接口
     */
    public TheDatePickerDialog(Context context, int[] initday, String[] day_limit,
                               int flag, Callback callback) {
        super(context);

        this.day_limit = day_limit;
        this.initday = initday;
        this.callback = callback;
        this.flag = flag;

        switch (flag) {
            case 1:
                info[0] = initday[0] + "";
                break;
            case 2:
                info[0] = initday[0] + "";
                info[1] = "-" + exchangeNum(initday[1]);
                break;
            case 3:
                info[0] = initday[0] + "";
                info[1] = "-" + exchangeNum(initday[1]);
                info[2] = "-" + exchangeNum(initday[2]);
                break;
        }

    }

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getView();

        initIU();

        setAction();

        intAction();

    }

    /**
     * 初始化控件
     */
    private void initIU() {

        datePicker_year = (QNumberPicker) findViewById(R.id.numberPicker_year);
        datePicker_month = (QNumberPicker) findViewById(R.id.numberPicker_month);
        datePicker_day = (QNumberPicker) findViewById(R.id.numberPicker_day);

        button_left = (Button) findViewById(R.id.button_left);
        button_right = (Button) findViewById(R.id.button_right);
        textView_clean = (TextView) findViewById(R.id.button_clear);

        if (flag == 2) {
            datePicker_day.setVisibility(View.GONE);
        } else if (flag == 1) {
            datePicker_month.setVisibility(View.GONE);
            datePicker_day.setVisibility(View.GONE);
        }

    }

    /**
     * 获取视图
     */
    private void getView() {

        setContentView(R.layout.date_picker_dialog);
    }

    /**
     * 设置属性
     */
    private void setAction() {

        datePicker_year
                .setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        datePicker_year
                .setMinValue(Integer.parseInt(day_limit[0].split("-")[0]));
        datePicker_year
                .setMaxValue(Integer.parseInt(day_limit[1].split("-")[0]));
        datePicker_year.setValue(initday[0]);
        datePicker_year.setWrapSelectorWheel(false);

        datePicker_month
                .setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        datePicker_month.setMinValue(1);
        datePicker_month.setMaxValue(12);
        datePicker_month.setValue(initday[1]);
        datePicker_month.setWrapSelectorWheel(false);

        datePicker_day
                .setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        datePicker_day.setMinValue(1);
        datePicker_day.setMaxValue(getDayByMonth(initday[0], initday[1]));
        datePicker_day.setValue(initday[2]);
        datePicker_day.setWrapSelectorWheel(false);

    }

    /**
     * 事件监听
     */
    private void intAction() {

        // 年
        datePicker_year.setOnValueChangedListener(new OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker arg0, int arg1, int arg2) {

                if (arg2 > 0) {
                    info[0] = exchangeNum(arg2);
                } else {
                    info[0] = exchangeNum(arg1);
                }

            }
        });

        // 月
        datePicker_month.setOnValueChangedListener(new OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker arg0, int arg1, int arg2) {

                if (arg2 > 0) {
                    info[1] = "-" + exchangeNum(arg2);
                } else {
                    info[1] = "-" + exchangeNum(arg1);
                }

                // 更新每月的天数
                datePicker_day.setMaxValue(getDayByMonth(
                        Integer.parseInt(info[0]), arg2));
                datePicker_day.setWrapSelectorWheel(false);
            }
        });

        // 日
        datePicker_day.setOnValueChangedListener(new OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker arg0, int arg1, int arg2) {

                if (arg2 > 0) {
                    info[2] = "-" + exchangeNum(arg2);
                } else {
                    info[2] = "-" + exchangeNum(arg1);
                }

            }
        });

        // 确定
        button_left.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                callback.showInfo(info[0] + info[1] + info[2]);

                dismiss();
            }
        });

        // 取消
        button_right
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        dismiss();
                    }
                });

        // 清空
        textView_clean
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {

                        callback.showInfo("+");

                        dismiss();
                    }
                });

    }

    /**
     * 传入小于10的数前面加"0"处理
     *
     * @param num
     * @return
     */
    private String exchangeNum(int num) {

        if (num < 10) {

            return "0" + num;

        } else {
            return "" + num;
        }

    }

    /**
     * 回传所选信息
     *
     * @author 天锁
     */
    public interface Callback {

        void showInfo(String string);

    }

    /**
     * 根据月份确定天数
     *
     * @return
     */

    private int getDayByMonth(int year, int month) {

        int day = 0;

        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:

                day = 31;

                break;
            case 4:
            case 6:
            case 9:
            case 11:

                day = 30;

                break;
            case 2:

                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                    day = 29;
                } else {
                    day = 28;
                }

                break;

            default:
                break;
        }

        return day;
    }

}
