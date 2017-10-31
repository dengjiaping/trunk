package com.histudent.jwsoft.histudent.body.find.bean;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2017/5/18.
 */

public class GroupMemberBean {

    /**
     * adminMembersNum : 0
     * adminMembersList : [{"userId":"string","userRealName":"string","userMobile":"string","userType":0,"userAvatar":"string","userSex":0,"groupId":"string","groupName":"string","isAdmin":true,"imgCount":0,"blogNum":0,"isGroupMaster":true,"className":"string","latLoginTime":"2017-05-18T02:05:12.670Z","lastVisitTime":"2017-05-18T02:05:12.670Z","lastVisitTimeCustom":"string"}]
     * generalMembersNum : 0
     * generalMembersList : [{"userId":"string","userRealName":"string","userMobile":"string","userType":0,"userAvatar":"string","userSex":0,"groupId":"string","groupName":"string","isAdmin":true,"imgCount":0,"blogNum":0,"isGroupMaster":true,"className":"string","latLoginTime":"2017-05-18T02:05:12.670Z","lastVisitTime":"2017-05-18T02:05:12.670Z","lastVisitTimeCustom":"string"}]
     */

    private int adminMembersNum;
    private int generalMembersNum;
    private List<AdminMembersListBean> adminMembersList;
    private List<GeneralMembersListBean> generalMembersList;

    public int getAdminMembersNum() {
        return adminMembersNum;
    }

    public void setAdminMembersNum(int adminMembersNum) {
        this.adminMembersNum = adminMembersNum;
    }

    public int getGeneralMembersNum() {
        return generalMembersNum;
    }

    public void setGeneralMembersNum(int generalMembersNum) {
        this.generalMembersNum = generalMembersNum;
    }

    public List<AdminMembersListBean> getAdminMembersList() {
        return adminMembersList;
    }

    public void setAdminMembersList(List<AdminMembersListBean> adminMembersList) {
        this.adminMembersList = adminMembersList;
    }

    public List<GeneralMembersListBean> getGeneralMembersList() {
        return generalMembersList;
    }

    public void setGeneralMembersList(List<GeneralMembersListBean> generalMembersList) {
        this.generalMembersList = generalMembersList;
    }

    public static class AdminMembersListBean {
        /**
         * userId : string
         * userRealName : string
         * userMobile : string
         * userType : 0
         * userAvatar : string
         * userSex : 0
         * groupId : string
         * groupName : string
         * isAdmin : true
         * imgCount : 0
         * blogNum : 0
         * isGroupMaster : true
         * className : string
         * latLoginTime : 2017-05-18T02:05:12.670Z
         * lastVisitTime : 2017-05-18T02:05:12.670Z
         * lastVisitTimeCustom : string
         */

        private String userId;
        private String userRealName;
        private String userMobile;
        private int userType;
        private String userAvatar;
        private int userSex;
        private String groupId;
        private String groupName;
        private boolean isAdmin;
        private int imgCount;
        private int blogNum;
        private boolean isGroupMaster;
        private String className;
        private String latLoginTime;
        private String lastVisitTime;
        private String lastVisitTimeCustom;
        private String pinyin;

        public String getPinyin() {
            return pinyin;
        }

        public void setPinyin(String pinyin) {
            this.pinyin = pinyin;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserRealName() {
            return userRealName;
        }

        public void setUserRealName(String userRealName) {
            this.userRealName = userRealName;
        }

        public String getUserMobile() {
            return userMobile;
        }

        public void setUserMobile(String userMobile) {
            this.userMobile = userMobile;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public String getUserAvatar() {
            return userAvatar;
        }

        public void setUserAvatar(String userAvatar) {
            this.userAvatar = userAvatar;
        }

        public int getUserSex() {
            return userSex;
        }

        public void setUserSex(int userSex) {
            this.userSex = userSex;
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

        public boolean isIsAdmin() {
            return isAdmin;
        }

        public void setIsAdmin(boolean isAdmin) {
            this.isAdmin = isAdmin;
        }

        public int getImgCount() {
            return imgCount;
        }

        public void setImgCount(int imgCount) {
            this.imgCount = imgCount;
        }

        public int getBlogNum() {
            return blogNum;
        }

        public void setBlogNum(int blogNum) {
            this.blogNum = blogNum;
        }

        public boolean isIsGroupMaster() {
            return isGroupMaster;
        }

        public void setIsGroupMaster(boolean isGroupMaster) {
            this.isGroupMaster = isGroupMaster;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getLatLoginTime() {
            return latLoginTime;
        }

        public void setLatLoginTime(String latLoginTime) {
            this.latLoginTime = latLoginTime;
        }

        public String getLastVisitTime() {
            return lastVisitTime;
        }

        public void setLastVisitTime(String lastVisitTime) {
            this.lastVisitTime = lastVisitTime;
        }

        public String getLastVisitTimeCustom() {
            return lastVisitTimeCustom;
        }

        public void setLastVisitTimeCustom(String lastVisitTimeCustom) {
            this.lastVisitTimeCustom = lastVisitTimeCustom;
        }
    }

    public static class GeneralMembersListBean {
        /**
         * userId : string
         * userRealName : string
         * userMobile : string
         * userType : 0
         * userAvatar : string
         * userSex : 0
         * groupId : string
         * groupName : string
         * isAdmin : true
         * imgCount : 0
         * blogNum : 0
         * isGroupMaster : true
         * className : string
         * latLoginTime : 2017-05-18T02:05:12.670Z
         * lastVisitTime : 2017-05-18T02:05:12.670Z
         * lastVisitTimeCustom : string
         */

        private String userId;
        private String userRealName;
        private String userMobile;
        private int userType;
        private String userAvatar;
        private int userSex;
        private String groupId;
        private String groupName;
        private boolean isAdmin;
        private int imgCount;
        private int blogNum;
        private boolean isGroupMaster;
        private String className;
        private String latLoginTime;
        private String lastVisitTime;
        private String lastVisitTimeCustom;
        private String pinyin;

        public String getPinyin() {
            return pinyin;
        }

        public void setPinyin(String pinyin) {
            this.pinyin = pinyin;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserRealName() {
            return userRealName;
        }

        public void setUserRealName(String userRealName) {
            this.userRealName = userRealName;
        }

        public String getUserMobile() {
            return userMobile;
        }

        public void setUserMobile(String userMobile) {
            this.userMobile = userMobile;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public String getUserAvatar() {
            return userAvatar;
        }

        public void setUserAvatar(String userAvatar) {
            this.userAvatar = userAvatar;
        }

        public int getUserSex() {
            return userSex;
        }

        public void setUserSex(int userSex) {
            this.userSex = userSex;
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

        public boolean isIsAdmin() {
            return isAdmin;
        }

        public void setIsAdmin(boolean isAdmin) {
            this.isAdmin = isAdmin;
        }

        public int getImgCount() {
            return imgCount;
        }

        public void setImgCount(int imgCount) {
            this.imgCount = imgCount;
        }

        public int getBlogNum() {
            return blogNum;
        }

        public void setBlogNum(int blogNum) {
            this.blogNum = blogNum;
        }

        public boolean isIsGroupMaster() {
            return isGroupMaster;
        }

        public void setIsGroupMaster(boolean isGroupMaster) {
            this.isGroupMaster = isGroupMaster;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getLatLoginTime() {
            return latLoginTime;
        }

        public void setLatLoginTime(String latLoginTime) {
            this.latLoginTime = latLoginTime;
        }

        public String getLastVisitTime() {
            return lastVisitTime;
        }

        public void setLastVisitTime(String lastVisitTime) {
            this.lastVisitTime = lastVisitTime;
        }

        public String getLastVisitTimeCustom() {
            return lastVisitTimeCustom;
        }

        public void setLastVisitTimeCustom(String lastVisitTimeCustom) {
            this.lastVisitTimeCustom = lastVisitTimeCustom;
        }
    }
}
