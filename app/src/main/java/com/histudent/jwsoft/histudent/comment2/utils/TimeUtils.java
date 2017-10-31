package com.histudent.jwsoft.histudent.comment2.utils;

import android.text.TextUtils;
import android.util.Log;

import com.netease.nim.uikit.common.util.string.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 时间工具类
 * Created by ZhangYT on 2016/8/18.
 */
public class TimeUtils {


    //计算活动截止时间和当期活动时间差值
    public static int getTimeDifferent(String data) {

        long time = -1;
        long time_current = System.currentTimeMillis();
        time = getTimeLong(data);
        Log.e("timeCurrent", time_current + "");
        Log.e("time", time + "");

        //该时间大于当前时间
        if (time - time_current > 60 * 1000) {
            return 1;
        } else {
            return -1;
        }

    }


    //计算活动截止时间和当期活动时间差值
    public static int getTimeDifferent2(String data, String data2) {

        long time1, time2 = -1;

        if (data.contains("/")) {
            time1 = getTimeLong(getFormateData(data), "/");
            time2 = getTimeLong(getFormateData(data2), "/");
        } else {
            time1 = getTimeLong(getFormateData(data));
            time2 = getTimeLong(getFormateData(data2));
        }

        Log.e("getTimeDifferent2", time1 + "");
        Log.e("getTimeDifferent2", time1 + "");
        if (time2 - time1 < 6 * 1000) {
            return -1;
        }
        return 1;

    }


    //格式化时间，添加秒
    public static String getFormateData(String str) {

        if (!StringUtil.isEmpty(str)) {

            if (str.contains("/")) {
                str = str.replace("/", "-");
            }
            if (str.length() == "yyyy-MM-dd HH:mm:ss".length() - 3) {

                return str + ":00";
            } else {
                return str;
            }
        }
        return null;
    }


    /**
     * 时间格式转换
     *
     * @param data1
     * @return
     */
    public static long getTimeLong(String data1) {
        long time = -1;
        if (TextUtils.isEmpty(data1)) return -1;


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(data1);
            time = date.getTime();
            return time;
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }


    //根据分割符格式化时间段
    public static long getTimeLong(String data1, String splist) {
        long time = -1;
        if (TextUtils.isEmpty(data1)) return -1;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy" + splist + "MM" + splist + "dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(data1);
            time = date.getTime();
            return time;
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    //获取下一年的的第一天
    public static String getTheFirstDayOfNextYear() {

        String currentYear = getCurrentTime().substring(0, 4);
        int nextYear = Integer.parseInt(currentYear) + 1;

        String nextYearStr = nextYear + "-01-01 00:00:00";

        Log.e("TheFirstDayOfNextYear:", nextYearStr);
        return nextYearStr;

    }


    //获取当前日期的年份
    public static String getTheCurrentYear() {

        String currentYear = getCurrentTime().substring(0, 4);

        return currentYear;

    }

    //获取格式化的当前时间
    public static String getCurrentTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }

    public static String exchangeTime(String time) {

        if (TextUtils.isEmpty(time)) return "";
        long current_time = System.currentTimeMillis();
        long create_time = getTimeLong(time);
        long timeSpan = current_time - create_time;

        if (timeSpan > 3 * 24 * 60 * 60 * 1000) {
            String[] times = time.split(":");
            return times[0] + ":" + times[1];
        } else if (timeSpan > 2 * 24 * 60 * 60 * 1000) {
            return 2 + "天前";
        } else if (timeSpan > 1 * 24 * 60 * 60 * 1000) {
            return 1 + "天前";
        } else if (timeSpan > 60 * 60 * 1000) {
            return (int) Math.floor(timeSpan / (60 * 60 * 1000)) + "小时前";
        } else if (timeSpan > 60 * 1000) {
            return (int) Math.floor(timeSpan / (60 * 1000)) + "分钟前";
        } else {
            return "刚刚";
        }

    }


    //格式化时间，本年份的出去年份和秒
    public static String getFormateTimeWithoutCurrentYearAndSecond(String time) {

        String t = time.replace(getCurrentTime().substring(0, 5), "");
        t = t.substring(0, t.length() - 3);
        t = t.replace("-", "/");

        return t;
    }

    //格式化时间段
    public static String getFormateTimeWithoutSecond(String t) {
        t = t.substring(0, t.length() - 3);
        t = t.replace("-", "/");

        return t;
    }


    //格式化时间段，除去秒
    public static String getFormateStartTimeAndEndTime(String start, String end) {

        String t = start.replace(getCurrentTime().substring(0, 5), "");
        t = t.substring(0, t.length() - 3);
        String e = end.replace(getCurrentTime().substring(0, 5), "");
        e = e.substring(0, e.length() - 3);
        e = e.replace("-", "/");
        t = t.replace("-", "/");

        return t + "一" + e;
    }


    //判断活动时间是否有效
    public static boolean ActivityIsValid(String data) {

        int time = getTimeDifferent(getFormateData(data));
        if (time > 0) {
            return true;
        }

        return false;
    }

    public static String formatTime2(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        String ms = formatter.format(time * 1000);
        return ms;
    }
}
