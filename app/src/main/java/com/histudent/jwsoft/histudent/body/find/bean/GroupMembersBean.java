package com.histudent.jwsoft.histudent.body.find.bean;

/**
 * 社群成员详情
 * Created by ZhangYT on 2016/8/12.
 */
public class GroupMembersBean {

    /**
     * groupId : ab937316-3909-4761-93a1-052b39422d7e
     * groupName : 444444
     * userId : 555240d9-0eaf-4c73-8742-703796b230cc
     * userSex : 1
     * userMobile : 18609690416
     * isGroupMaster : false
     * isAdmin : false
     * imgCount : 3
     * userAvatar : http://img.hitongx.com/UploadFiles/UploadAvatar/20160812/6f9414cc-6cc7-49d3-945a-ab0f00a14268.png@_60w_60h_1e_1c_30-1ci.png
     * userRealName : 于洋
     * blogNum : 0
     * userType : 3
     */

    private String groupId;
    private String className;
    private String groupName;
    private String userId;
    private int userSex;
    private String userMobile;
    private boolean isGroupMaster;
    private boolean isAdmin;
    private String imgCount;
    private String userAvatar;
    private String userRealName;
    private String blogNum;
    private int userType;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getUserSex() {
        return userSex;
    }

    public void setUserSex(int userSex) {
        this.userSex = userSex;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public boolean isIsGroupMaster() {
        return isGroupMaster;
    }

    public void setIsGroupMaster(boolean isGroupMaster) {
        this.isGroupMaster = isGroupMaster;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getImgCount() {
        return imgCount;
    }

    public void setImgCount(String imgCount) {
        this.imgCount = imgCount;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getBlogNum() {
        return blogNum;
    }

    public void setBlogNum(String blogNum) {
        this.blogNum = blogNum;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
