package com.histudent.jwsoft.histudent.commen.utils;

/**
 * Created by ZhangYT on 2016/7/7.
 * 用于计算两个地址之间的距离
 */


public class GetDistance {

    public static double DistanceOfTwoPoints(double lat11, double lng1,
                                    double lat22, double lng2) {
            double lat1 = (Math.PI/180)*lat11;
            double lat2 = (Math.PI/180)*lat22;
            double lon1 = (Math.PI/180)*lng1;
            double lon2 = (Math.PI/180)*lng2;


            //地球半径
            double R = 6371;

            //两点间距离 km，如果想要米的话，结果*1000就可以了
            double d =  Math.acos(Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1))*R;

            return d*1000;
        }
}
