package com.histudent.jwsoft.histudent.commen.utils;

/**
 * Created by ZhangYT on 2016/7/27.
 */
public interface LocationCallBack {

    //获取登录者的经纬度信息
    void getLocationInfor(double lat, double lon, String address, String city, String dist, boolean isSuccess);
}
