package com.histudent.jwsoft.histudent.body.find.bean;

import java.util.List;

/**
 * Created by android03_pc on 2017/5/19.
 */

public class CommunitySignBean {


    /**
     * groupMembersList : [{"groupId":"00000000-0000-0000-0000-000000000000","lastVisitTimeCustom":"6分钟前","userId":"df3a0abd-415f-4e26-ac6c-00f8ea013a9d","userSex":0,"isGroupMaster":false,"isAdmin":false,"userAvatar":"http://img.hitongx.com/UploadFiles/Album/20170223/f1609bcd-7fab-4950-8988-f86fa4e1850a.jpg@60w_60h_1e_1c_2o_30-1ci.jpg","userRealName":"吖吖","latLoginTime":"2017-05-19 19:48:53","lastVisitTime":"2017-05-19 19:48:53","userType":0}]
     * toDayVisitNum : 1
     * groupMemberNum : 6
     */

    private int toDayVisitNum;
    private int groupMemberNum;
    private List<GroupMembersListBean> groupMembersList;

    public int getToDayVisitNum() {
        return toDayVisitNum;
    }

    public void setToDayVisitNum(int toDayVisitNum) {
        this.toDayVisitNum = toDayVisitNum;
    }

    public int getGroupMemberNum() {
        return groupMemberNum;
    }

    public void setGroupMemberNum(int groupMemberNum) {
        this.groupMemberNum = groupMemberNum;
    }

    public List<GroupMembersListBean> getGroupMembersList() {
        return groupMembersList;
    }

    public void setGroupMembersList(List<GroupMembersListBean> groupMembersList) {
        this.groupMembersList = groupMembersList;
    }

    public static class GroupMembersListBean {
        /**
         * groupId : 00000000-0000-0000-0000-000000000000
         * lastVisitTimeCustom : 6分钟前
         * userId : df3a0abd-415f-4e26-ac6c-00f8ea013a9d
         * userSex : 0
         * isGroupMaster : false
         * isAdmin : false
         * userAvatar : http://img.hitongx.com/UploadFiles/Album/20170223/f1609bcd-7fab-4950-8988-f86fa4e1850a.jpg@60w_60h_1e_1c_2o_30-1ci.jpg
         * userRealName : 吖吖
         * latLoginTime : 2017-05-19 19:48:53
         * lastVisitTime : 2017-05-19 19:48:53
         * userType : 0
         */

        private String groupId;
        private String lastVisitTimeCustom;
        private String userId;
        private int userSex;
        private boolean isGroupMaster;
        private boolean isAdmin;
        private String userAvatar;
        private String userRealName;
        private String latLoginTime;
        private String lastVisitTime;
        private int userType;

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getLastVisitTimeCustom() {
            return lastVisitTimeCustom;
        }

        public void setLastVisitTimeCustom(String lastVisitTimeCustom) {
            this.lastVisitTimeCustom = lastVisitTimeCustom;
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

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }
    }
}
