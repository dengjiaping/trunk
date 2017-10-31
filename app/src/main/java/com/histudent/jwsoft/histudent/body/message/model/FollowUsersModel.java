package com.histudent.jwsoft.histudent.body.message.model;

/**
 * Created by liuguiyu-pc on 2016/8/3.
 */
public class FollowUsersModel {

    public String userId;//(string, optional): 用户id ,
    public String followId;
    public String realName;// (string, optional): 真是姓名 ,
    public String avatar;// (string, optional): 头像 ,
    public String mobile;//(string, optional): 用户号码
    public int userType;
    private boolean IsMutual;

    public boolean isIsMutual() {
        return IsMutual;
    }

    public void setIsMutual(boolean mutual) {
        IsMutual = mutual;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getFollowId() {
        return followId;
    }

    public void setFollowId(String followId) {
        this.followId = followId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
