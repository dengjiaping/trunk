package com.histudent.jwsoft.histudent;

import android.util.Log;

/**
 * Created by lichaojie on 2017/7/4.
 * Describe:
 * Log Print Common Tool
 */
public final class HiStudentLog {
    private static final String TAG = "HiStudentLog";

    public static void e(String msg) {
        printMessage(TAG, msg, 0, null);
    }

    /**
     * @param msg
     * @param object 根据此去获取className
     */
    public static void e(String msg, Object object) {
        printMessage(TAG, msg, 0, object);
    }

    public static void e(String tag, String msg) {
        printMessage(tag, msg, 0, null);
    }

    /**
     * @param tag
     * @param msg
     * @param object 根据此去获取className
     */
    public static void e(String tag, String msg, Object object) {
        printMessage(tag, msg, 0, object);
    }


    public static void i(String msg) {
        printMessage(TAG, msg, 1, null);
    }

    /**
     * @param msg
     * @param object 根据此去获取className
     */
    public static void i(String msg, Object object) {
        printMessage(TAG, msg, 0, object);
    }

    public static void i(String tag, String msg) {
        printMessage(tag, msg, 1, null);
    }

    /**
     * @param tag
     * @param msg
     * @param object 根据此去获取className
     */
    public static void i(String tag, String msg, Object object) {
        printMessage(tag, msg, 0, object);
    }

    private static void printMessage(String tag, String msg, int type, Object object) {
        if (BuildConfig.PRINT_LOG) {
            switch (type) {
                case 0:
                    //错误信息
                    printErrorMsg(tag, msg, object);
                    break;
                case 1:
                    printIndicatorMsg(tag, msg, object);
                    break;
                default:
                    break;
            }
        }
    }

    private static void printIndicatorMsg(String tag, String msg, Object object) {
        if (object == null) {
            Log.i(tag, "printMessage: " + msg);
        } else {
            Log.i(tag, "printMessage: " + msg + "---->CurrentClassName:" + object.getClass().getName());
        }
    }

    private static void printErrorMsg(String tag, String msg, Object object) {
        if (object == null) {
            Log.e(tag, "printMessage: " + msg);
        } else {
            Log.e(tag, "printMessage: " + msg + "---->CurrentClassName:" + object.getClass().getName());
        }
    }

}
