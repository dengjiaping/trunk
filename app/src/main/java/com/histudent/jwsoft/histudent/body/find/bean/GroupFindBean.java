package com.histudent.jwsoft.histudent.body.find.bean;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2017/5/18.
 */

public class GroupFindBean {

    /**
     * tagId : string
     * name : string
     * groupNum : 0
     * categoryImg : string
     * grorpModels : [{"groupId":"string","groupName":"string","groupLogo":"string","tagName":"string","aearStr":"string","groupCoverImg":"string","groupDescription":"string","groupDescriptionImgsList":[{"imgId":"string","savePath":"string"}],"groupUserId":"string","groupUserRealaName":"string","groupUserAvatar":"string","tlakNum":"string","imgNum":"string","isMember":true,"joinDate":"2017-05-18T08:02:41.934Z","createTime":"2017-05-18T08:02:41.934Z","isPublic":true,"isManager":true,"isGroupMaker":true,"isApplying":true,"tagId":"string","childTagId":"string","childTagName":"string","areaName":"string","areaShiName":"string","areaCode":"string","areaShiCode":"string","groupIndexUrl":"string","isAttestation":true,"groupMemberNum":0,"toDayVisitNum":0,"groupMembersList":[{"userId":"string","userRealName":"string","userMobile":"string","userType":0,"userAvatar":"string","userSex":0,"groupId":"string","groupName":"string","isAdmin":true,"imgCount":0,"blogNum":0,"isGroupMaster":true,"className":"string","latLoginTime":"2017-05-18T08:02:41.934Z","lastVisitTime":"2017-05-18T08:02:41.934Z","lastVisitTimeCustom":"string"}],"groupNumber":"string"}]
     */

    private String tagId;
    private String name;
    private int groupNum;
    private String categoryImg;
    private List<GrorpModelsBean> grorpModels;

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(int groupNum) {
        this.groupNum = groupNum;
    }

    public String getCategoryImg() {
        return categoryImg;
    }

    public void setCategoryImg(String categoryImg) {
        this.categoryImg = categoryImg;
    }

    public List<GrorpModelsBean> getGrorpModels() {
        return grorpModels;
    }

    public void setGrorpModels(List<GrorpModelsBean> grorpModels) {
        this.grorpModels = grorpModels;
    }

    public static class GrorpModelsBean {
        /**
         * groupId : string
         * groupName : string
         * groupLogo : string
         * tagName : string
         * aearStr : string
         * groupCoverImg : string
         * groupDescription : string
         * groupDescriptionImgsList : [{"imgId":"string","savePath":"string"}]
         * groupUserId : string
         * groupUserRealaName : string
         * groupUserAvatar : string
         * tlakNum : string
         * imgNum : string
         * isMember : true
         * joinDate : 2017-05-18T08:02:41.934Z
         * createTime : 2017-05-18T08:02:41.934Z
         * isPublic : true
         * isManager : true
         * isGroupMaker : true
         * isApplying : true
         * tagId : string
         * childTagId : string
         * childTagName : string
         * areaName : string
         * areaShiName : string
         * areaCode : string
         * areaShiCode : string
         * groupIndexUrl : string
         * isAttestation : true
         * groupMemberNum : 0
         * toDayVisitNum : 0
         * groupMembersList : [{"userId":"string","userRealName":"string","userMobile":"string","userType":0,"userAvatar":"string","userSex":0,"groupId":"string","groupName":"string","isAdmin":true,"imgCount":0,"blogNum":0,"isGroupMaster":true,"className":"string","latLoginTime":"2017-05-18T08:02:41.934Z","lastVisitTime":"2017-05-18T08:02:41.934Z","lastVisitTimeCustom":"string"}]
         * groupNumber : string
         */

        private String groupId;
        private String groupName;
        private String groupLogo;
        private String tagName;
        private String aearStr;
        private String groupCoverImg;
        private String groupDescription;
        private String groupUserId;
        private String groupUserRealaName;
        private String groupUserAvatar;
        private String tlakNum;
        private String imgNum;
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
        private String areaName;
        private String areaShiName;
        private String areaCode;
        private String areaShiCode;
        private String groupIndexUrl;
        private boolean isAttestation;
        private int groupMemberNum;
        private int toDayVisitNum;
        private String groupNumber;
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

        public String getAearStr() {
            return aearStr;
        }

        public void setAearStr(String aearStr) {
            this.aearStr = aearStr;
        }

        public String getGroupCoverImg() {
            return groupCoverImg;
        }

        public void setGroupCoverImg(String groupCoverImg) {
            this.groupCoverImg = groupCoverImg;
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

        public String getGroupUserRealaName() {
            return groupUserRealaName;
        }

        public void setGroupUserRealaName(String groupUserRealaName) {
            this.groupUserRealaName = groupUserRealaName;
        }

        public String getGroupUserAvatar() {
            return groupUserAvatar;
        }

        public void setGroupUserAvatar(String groupUserAvatar) {
            this.groupUserAvatar = groupUserAvatar;
        }

        public String getTlakNum() {
            return tlakNum;
        }

        public void setTlakNum(String tlakNum) {
            this.tlakNum = tlakNum;
        }

        public String getImgNum() {
            return imgNum;
        }

        public void setImgNum(String imgNum) {
            this.imgNum = imgNum;
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

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getAreaShiName() {
            return areaShiName;
        }

        public void setAreaShiName(String areaShiName) {
            this.areaShiName = areaShiName;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public String getAreaShiCode() {
            return areaShiCode;
        }

        public void setAreaShiCode(String areaShiCode) {
            this.areaShiCode = areaShiCode;
        }

        public String getGroupIndexUrl() {
            return groupIndexUrl;
        }

        public void setGroupIndexUrl(String groupIndexUrl) {
            this.groupIndexUrl = groupIndexUrl;
        }

        public boolean isIsAttestation() {
            return isAttestation;
        }

        public void setIsAttestation(boolean isAttestation) {
            this.isAttestation = isAttestation;
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

        public static class GroupDescriptionImgsListBean {
            /**
             * imgId : string
             * savePath : string
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

        public static class GroupMembersListBean {
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
             * latLoginTime : 2017-05-18T08:02:41.934Z
             * lastVisitTime : 2017-05-18T08:02:41.934Z
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
}
