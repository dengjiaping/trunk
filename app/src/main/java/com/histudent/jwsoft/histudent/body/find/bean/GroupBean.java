package com.histudent.jwsoft.histudent.body.find.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liuguiyu-pc on 2017/5/16.
 */

public class GroupBean implements Serializable {

    /**
     * groupId : 57665a49-c985-43e2-af59-b743f9fb9bae
     * groupName : 感觉健康
     * groupLogo : http://img.hitongx.com/UploadFiles/groupLogo/20170519/ee41e386-c23b-4963-809d-f75d7ef4e579.jpg@116w_116h_1e_1c_2o_58-1ci.jpg
     * tagName : 其他
     * groupDescription : 兔兔
     * groupDescriptionImgsList : [{"imgId":"fff69ba6-1f82-4b71-8bb1-491c272415fb","savePath":"http://img.hitongx.com/UploadFiles/groupFile/20170527/91ed41c4-e063-4545-87c1-70d82ed14d50.jpg@700w_400h_1e_1l_2o.jpg.jpg?wh=3120_4208"},{"imgId":"ed4ec100-8209-4a8f-914d-b016c5e8e7d1","savePath":"http://img.hitongx.com/UploadFiles/groupFile/20170527/9daa71ca-a625-437d-a6ed-448e2a261443.jpg@700w_400h_1e_1l_2o.jpg.jpg?wh=270_480"},{"imgId":"64a842df-c2e7-42f2-aab1-a4b6bf08ce1f","savePath":"http://img.hitongx.com/UploadFiles/groupFile/20170527/e6c649a0-5956-48d5-a166-c5d4d8a515a2.jpg@700w_400h_1e_1l_2o.jpg.jpg?wh=270_480"}]
     * groupUserId : 00000000-0000-0000-0000-000000000000
     * tlakNum : 0
     * isMember : true
     * joinDate : 0001-01-01 00:00:00
     * createTime : 0001-01-01 00:00:00
     * isPublic : false
     * isManager : true
     * isGroupMaker : true
     * isApplying : false
     * tagId : 5f1b2fb4-511a-49d0-a80d-6fdf94631d3a
     * childTagId : e59e1fae-fbe0-4622-a92e-0b6d941d5726
     * childTagName : 其他
     * isAttestation : false
     * groupMemberNum : 1
     * toDayVisitNum : 1
     * groupMembersList : [{"userId":"16430800-51c8-4017-a67c-9e5f9b93d5b0","userRealName":"刘思","userType":0,"userAvatar":"http://img.hitongx.com/UploadFiles/UploadAvatar/20170514/2a602e49-bad6-430a-92f1-49aba5211466.jpg@60w_60h_1e_1c_2o_30-1ci.jpg","userSex":0,"groupId":"00000000-0000-0000-0000-000000000000","isAdmin":false,"isGroupMaster":false,"latLoginTime":"0001-01-01 00:00:00","lastVisitTime":"2017-05-27 15:31:30"}]
     * groupNumber : 62528425
     * imTeamId : 46322850
     * logoThumbnail : http://img.hitongx.com/UploadFiles/groupLogo/20170519/ee41e386-c23b-4963-809d-f75d7ef4e579.jpg@300w_400h_1e_1l_2o.jpg.jpg
     */

    private String groupId;
    private String groupName;
    private String groupLogo;
    private String tagName;
    private String groupDescription;
    private String groupUserId;
    private String tlakNum;
    private boolean isMember;
    private String joinDate;
    private String createTime;
    private boolean isPublic;
    private boolean isManager;
    private boolean isGroupMaker;
    private boolean isApplying;
    private String tagId;
    private String childTagId;
    private String childTagName;
    private boolean isAttestation;
    private boolean isTodayVisit;
    private int groupMemberNum;
    private int toDayVisitNum;
    private String groupNumber;
    private String imTeamId;
    private String logoThumbnail;
    private List<GroupDescriptionImgsListBean> groupDescriptionImgsList;
    private List<GroupMembersListBean> groupMembersList;



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

    public String getGroupLogo() {
        return groupLogo;
    }

    public void setGroupLogo(String groupLogo) {
        this.groupLogo = groupLogo;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public String getGroupUserId() {
        return groupUserId;
    }

    public void setGroupUserId(String groupUserId) {
        this.groupUserId = groupUserId;
    }

    public String getTlakNum() {
        return tlakNum;
    }

    public void setTlakNum(String tlakNum) {
        this.tlakNum = tlakNum;
    }

    public boolean isIsMember() {
        return isMember;
    }

    public void setIsMember(boolean isMember) {
        this.isMember = isMember;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public boolean isIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public boolean isIsManager() {
        return isManager;
    }

    public void setIsManager(boolean isManager) {
        this.isManager = isManager;
    }

    public boolean isIsGroupMaker() {
        return isGroupMaker;
    }

    public void setIsGroupMaker(boolean isGroupMaker) {
        this.isGroupMaker = isGroupMaker;
    }

    public boolean isIsApplying() {
        return isApplying;
    }

    public void setIsApplying(boolean isApplying) {
        this.isApplying = isApplying;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getChildTagId() {
        return childTagId;
    }

    public void setChildTagId(String childTagId) {
        this.childTagId = childTagId;
    }

    public String getChildTagName() {
        return childTagName;
    }

    public void setChildTagName(String childTagName) {
        this.childTagName = childTagName;
    }

    public boolean isIsAttestation() {
        return isAttestation;
    }

    public void setIsAttestation(boolean isAttestation) {
        this.isAttestation = isAttestation;
    }

    public boolean isIsTodayVisit() {
        return isTodayVisit;
    }

    public void setIsTodayVisit(boolean isTodayVisit) {
        this.isTodayVisit = isTodayVisit;
    }

    public int getGroupMemberNum() {
        return groupMemberNum;
    }

    public void setGroupMemberNum(int groupMemberNum) {
        this.groupMemberNum = groupMemberNum;
    }

    public int getToDayVisitNum() {
        return toDayVisitNum;
    }

    public void setToDayVisitNum(int toDayVisitNum) {
        this.toDayVisitNum = toDayVisitNum;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getImTeamId() {
        return imTeamId;
    }

    public void setImTeamId(String imTeamId) {
        this.imTeamId = imTeamId;
    }

    public String getLogoThumbnail() {
        return logoThumbnail;
    }

    public void setLogoThumbnail(String logoThumbnail) {
        this.logoThumbnail = logoThumbnail;
    }

    public List<GroupDescriptionImgsListBean> getGroupDescriptionImgsList() {
        return groupDescriptionImgsList;
    }

    public void setGroupDescriptionImgsList(List<GroupDescriptionImgsListBean> groupDescriptionImgsList) {
        this.groupDescriptionImgsList = groupDescriptionImgsList;
    }

    public List<GroupMembersListBean> getGroupMembersList() {
        return groupMembersList;
    }

    public void setGroupMembersList(List<GroupMembersListBean> groupMembersList) {
        this.groupMembersList = groupMembersList;
    }

    public static class GroupDescriptionImgsListBean implements Serializable {
        /**
         * imgId : fff69ba6-1f82-4b71-8bb1-491c272415fb
         * savePath : http://img.hitongx.com/UploadFiles/groupFile/20170527/91ed41c4-e063-4545-87c1-70d82ed14d50.jpg@700w_400h_1e_1l_2o.jpg.jpg?wh=3120_4208
         */

        private String imgId;
        private String savePath;

        public String getImgId() {
            return imgId;
        }

        public void setImgId(String imgId) {
            this.imgId = imgId;
        }

        public String getSavePath() {
            return savePath;
        }

        public void setSavePath(String savePath) {
            this.savePath = savePath;
        }
    }

    public static class GroupMembersListBean implements Serializable {
        /**
         * userId : 16430800-51c8-4017-a67c-9e5f9b93d5b0
         * userRealName : 刘思
         * userType : 0
         * userAvatar : http://img.hitongx.com/UploadFiles/UploadAvatar/20170514/2a602e49-bad6-430a-92f1-49aba5211466.jpg@60w_60h_1e_1c_2o_30-1ci.jpg
         * userSex : 0
         * groupId : 00000000-0000-0000-0000-000000000000
         * isAdmin : false
         * isGroupMaster : false
         * latLoginTime : 0001-01-01 00:00:00
         * lastVisitTime : 2017-05-27 15:31:30
         */

        private String userId;
        private String userRealName;
        private int userType;
        private String userAvatar;
        private int userSex;
        private String groupId;
        private boolean isAdmin;
        private boolean isGroupMaster;
        private String latLoginTime;
        private String lastVisitTime;

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

        public boolean isIsAdmin() {
            return isAdmin;
        }

        public void setIsAdmin(boolean isAdmin) {
            this.isAdmin = isAdmin;
        }

        public boolean isIsGroupMaster() {
            return isGroupMaster;
        }

        public void setIsGroupMaster(boolean isGroupMaster) {
            this.isGroupMaster = isGroupMaster;
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
    }
}
