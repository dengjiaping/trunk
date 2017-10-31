package com.histudent.jwsoft.histudent.comment2.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ZhangYT on 2016/12/8.
 */

public class TellUtils {


    public static boolean isPhoneNumber(String tell) {

        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(tell);

        if (!m.matches()) {

            return false;
        } else {
            return true;
        }
    }

    public static boolean isQQNumber(String email) {


        String regex = "[1-9][0-9]{4,10}";
        boolean flag = email.matches(regex);
        if (flag) {
            return true;
        } else {
            return false;
        }
    }
}
