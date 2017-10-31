package com.histudent.jwsoft.histudent.comment2.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by ZhangYT on 2016/12/30.
 */

public class BackGroundUtils {


    //判断应用是否处于后台
    public static boolean isBackground(Context context) {
        if (context == null) return true;
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                    /*
                    BACKGROUND=400 EMPTY=500 FOREGROUND=100
                    GONE=1000 PERCEPTIBLE=130 SERVICE=300 ISIBLE=200
                     */
//                Log.e(context.getPackageName(), "此appimportace ="
//                        + appProcess.importance
//                        + ",context.getClass().getName()="
//                        + context.getClass().getName());
                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
//                    Log.e(context.getPackageName(), "处于后台"
//                            + appProcess.processName);
                    return true;
                } else {
//                    Log.e(context.getPackageName(), "处于前台"
//                            + appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }

}
