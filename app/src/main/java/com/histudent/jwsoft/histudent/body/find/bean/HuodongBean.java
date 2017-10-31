package com.histudent.jwsoft.histudent.body.find.bean;

import java.io.Serializable;

/**
 * Created by ZhangYT on 2016/9/19.
 */
public class HuodongBean implements Serializable {


    /**
     * appKey : 100096
     * appName : 测试选修课
     * appUrl : http://192.168.0.252:8091/#!/index?token=db4d7000945b44c9bdc12e2286876444
     * logo : http://img.hitongx.com/UploadFiles/AuthAppLogo/20161108/85e9df70-7dd5-49e3-8a80-61176d7f0cd1.jpg@300w_300h_1e_1c_2o.jpg
     * isUsed : true
     */

    private String appKey;
    private String appName;
    private String appUrl;
    private String logo;
    private boolean isUsed;
    boolean isEmpty;
    private boolean isAdd;

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public boolean isIsUsed() {
        return isUsed;
    }

    public void setIsUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }
}
