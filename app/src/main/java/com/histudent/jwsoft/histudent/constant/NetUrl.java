package com.histudent.jwsoft.histudent.constant;


import com.histudent.jwsoft.histudent.HTApplication;

/**
 * Created by huyg on 2017/8/18.
 */

public class NetUrl {

    public static final String BASE_URL = HTApplication.isOnLine ? "https://api2.hitongx.com/v2/" : "http://192.168.0.252:8290/";
}
