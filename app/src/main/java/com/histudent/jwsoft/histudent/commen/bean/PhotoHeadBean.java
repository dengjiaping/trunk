package com.histudent.jwsoft.histudent.commen.bean;

import java.io.Serializable;

/**
 * Created by liuguiyu-pc on 2017/6/5.
 */

public class PhotoHeadBean implements Serializable {

    private String path;
    private String userAvatar;
    private String userId;
    private String userName;
    private String time;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
