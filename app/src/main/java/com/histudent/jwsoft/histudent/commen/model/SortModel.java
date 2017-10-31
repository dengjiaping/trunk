package com.histudent.jwsoft.histudent.commen.model;

public class SortModel {

    private String name;   //显示的名字
    private String sortLetters;  //显示名字拼音的首字母
    private boolean flag;//是否被选中
    private String userId;//(string, optional): 关注用户Id
    private String avatar;//(string, optional): 头像
    private String followId;
    private String sort;
    private int userType;
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

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

}
