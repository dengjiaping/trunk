package com.histudent.jwsoft.histudent.tool;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by lichaojie on 2017/9/18.
 * desc:
 * activity common jump
 */

public class CommonAdvanceUtils {

    private static final long SPACING_INTERVAL = 500L;
    private static long LAST_TIME = 0;

    public static final void startActivity(AppCompatActivity context, Intent intent) {
        if (!checkAdvanceStatus())
            return;
        LAST_TIME = System.currentTimeMillis();
        ContextCompat.startActivity(context, intent, null);
    }

    public static final void startActivity(Context context, Intent intent) {
        if (!checkAdvanceStatus())
            return;
        LAST_TIME = System.currentTimeMillis();
        ContextCompat.startActivity(context, intent, null);
    }

    public static final void startActivityForResult(AppCompatActivity context, Intent intent, int requestCode) {
        if (!checkAdvanceStatus())
            return;
        LAST_TIME = System.currentTimeMillis();
        context.startActivityForResult(intent, requestCode);
    }


    /**
     * 检测当前Activity之间跳转情况
     * 屏敝双击跳转两次
     *
     * @return
     */
    public static final boolean checkAdvanceStatus() {
        if (System.currentTimeMillis() - LAST_TIME < SPACING_INTERVAL) {
            return false;
        }
        return true;
    }


}
