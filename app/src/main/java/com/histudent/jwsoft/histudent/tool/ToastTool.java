package com.histudent.jwsoft.histudent.tool;

import android.content.Context;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.HiStudentApplication;

/**
 * Created by lichaojie on 2017/6/20.
 */

public class ToastTool {

    public static void showCommonToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showCommonToast(String text) {
        Toast.makeText(HiStudentApplication.getInstance(), text, Toast.LENGTH_SHORT).show();
    }
}
