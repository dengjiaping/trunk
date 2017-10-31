package com.histudent.jwsoft.histudent.body.mine.model;

/**
 * Created by liuguiyu-pc on 2016/7/27.
 */
public class UserHonorsModel {

    private String honorId;//主键Id
    private String userId;//用户Id
    private String getTime;//获奖时间
    private String name;//奖项名称
    private String picture;//奖项描述

    public String getHonorId() {
        return honorId;
    }

    public void setHonorId(String honorId) {
        this.honorId = honorId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGetTime() {
        return getTime;
    }

    public void setGetTime(String getTime) {
        this.getTime = getTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
