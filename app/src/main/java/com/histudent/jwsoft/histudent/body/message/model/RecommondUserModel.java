package com.histudent.jwsoft.histudent.body.message.model;

/**
 * Created by liuguiyu-pc on 2016/8/8.
 */
public class RecommondUserModel {

    /**
     * userId : ef9d7a2f-1eb4-40f6-be26-3077f3ad809c
     * realname : 赵武的家长
     * userType : 2
     * avatar : http://img.hitongx.com/images/NormalAvatarLogo.png
     * moreType : 0
     * info : 关注3 粉丝1
     * isFocus : false
     * attNumber : 3
     * fansNumber : 1
     * classFullName : 浙师大杭州幼儿师范学院第二附属幼儿园2016级2班
     * classId : 834b4393-ce35-4fa2-b020-8a39cb833ecd
     */

    private String userId;
    private String realname;
    private int userType;
    private String avatar;
    private int moreType;
    private String info;
    private boolean isFocus;
    private int attNumber;
    private int fansNumber;
    private String classFullName;
    private String classId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getMoreType() {
        return moreType;
    }

    public void setMoreType(int moreType) {
        this.moreType = moreType;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isIsFocus() {
        return isFocus;
    }

    public void setIsFocus(boolean isFocus) {
        this.isFocus = isFocus;
    }

    public int getAttNumber() {
        return attNumber;
    }

    public void setAttNumber(int attNumber) {
        this.attNumber = attNumber;
    }

    public int getFansNumber() {
        return fansNumber;
    }

    public void setFansNumber(int fansNumber) {
        this.fansNumber = fansNumber;
    }

    public String getClassFullName() {
        return classFullName;
    }

    public void setClassFullName(String classFullName) {
        this.classFullName = classFullName;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }
}
