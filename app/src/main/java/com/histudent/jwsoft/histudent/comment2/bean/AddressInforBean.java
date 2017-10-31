package com.histudent.jwsoft.histudent.comment2.bean;

import java.io.Serializable;

/**
 * 存放地址的信息，地址的名称，地址的精度和维度
 * Created by ZhangYT on 2016/7/7.
 */

public class AddressInforBean implements Serializable {
    private String name = "";
    private double latitude;
    private double longitude;
    private String city = "";
    private String detailStr = "";
    private boolean isShowAddress=true;

    public boolean isShowAddress() {
        return isShowAddress;
    }

    public void setShowAddress(boolean showAddress) {
        isShowAddress = showAddress;
    }

    public String getDetailStr() {
        return detailStr;
    }

    public void setDetailStr(String detailStr) {
        this.detailStr = detailStr;
    }

    private String areaStr;

    public String getAreaStr() {
        return areaStr;
    }

    public void setAreaStr(String areaStr) {
        this.areaStr = areaStr;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public AddressInforBean() {
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "AddressInforBean{" +
                "name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", city='" + city + '\'' +
                ", detailStr='" + detailStr + '\'' +
                ", areaStr='" + areaStr + '\'' +
                '}';
    }
}
