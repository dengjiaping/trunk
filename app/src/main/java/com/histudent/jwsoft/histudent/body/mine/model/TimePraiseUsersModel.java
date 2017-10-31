package com.histudent.jwsoft.histudent.body.mine.model;

/**
 * Created by liuguiyu-pc on 2016/8/11.
 */
public class TimePraiseUsersModel {
    private String userId;//  (string, optional): 用户id ,
    private String name;// (string, optional): 名字 ,
    private String avatar;//  (string, optional): 头像

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
