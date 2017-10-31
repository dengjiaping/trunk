package com.histudent.jwsoft.histudent.commen.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ZhangYT on 2016/7/13.
 */
public class TimeUtils {

    public static String getCurrentTime() {
        Date dt = new Date();
        SimpleDateFormat matter1 = new SimpleDateFormat("MM-dd");
        return matter1.format(dt);
    }

    public static String format(String time) {
        Date date = new Date();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat format = new SimpleDateFormat("MM/dd HH:mm");
        return format.format(date);
    }


    public static String formatYYMMDD(String formatDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(formatDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(date);
    }
    public static long parseYYMMDD(String formatDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(formatDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static Calendar parseTime(String formatDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date date = null;
        try {
            date = sdf.parse(formatDate);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }
    public static Calendar parseTimeNext(String formatDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date date = null;
        try {
            date = sdf.parse(formatDate);
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }


}
