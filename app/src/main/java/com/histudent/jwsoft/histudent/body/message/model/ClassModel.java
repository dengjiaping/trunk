package com.histudent.jwsoft.histudent.body.message.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/8/19.
 */
public class ClassModel implements Serializable {

    /**
     * classId : string
     * classNum : string
     * cName : string
     * gradeName : string
     * schoolName : string
     * alias : string
     * classLogo : string
     * classMemberNum : 0
     * classCoverImg : string
     * classUserId : string
     * classUserRealName : string
     * classUserAvatar : string
     * photoNum : 0
     * blogNum : 0
     * activitiesNum : 0
     * classApplyAuditStatus : 0
     * isClassMaker : true
     * isAdmin : true
     * isMember : true
     * isDefaultClass : true
     * classDescribe : string
     * orgId : string
     * eductionSystemId : 0
     * eductionSystemName : string
     * areaName : string
     * areaShiName : string
     * areaCode : string
     * areaShiCode : string
     * allowJoin : true
     * bannerList : [{"photoId":"string","albumId":"string","syncPhotoId":"string","ownerId":"string","userId":"string","author":"string","schoolId":"string","schoolName":"string","filePath":"string","fileLength":0,"width":0,"height":0,"name":"string","description":"string","uploadIP":"string","createTime":"2017-06-01T08:06:20.707Z","updateUserId":"string","updateTime":"2017-06-01T08:06:20.707Z","longitude":0,"latitude":0,"location":"string","praiseNum":0,"commentNum":0,"visitNum":0,"isDeleted":true,"albumName":"string","ownerName":"string","ownerType":0,"auditStatus":0,"sensitiveLevel":0,"sensitiveKeyWords":"string","batchNumber":"string","syncOwnerType":0,"syncOwnerIds":["string"],"atUser":[{"userId":"string","realName":"string"}],"alias":"string","activityId":"string"}]
     * classActiviteUserAvatar : string
     * classApps : [{"appKey":"string","appType":0,"appName":"string","appUrl":"string","logo":"string","author":"string","appDescription":"string","registeTime":"2017-06-01T08:06:20.708Z","auditTime":"2017-06-01T08:06:20.708Z","appSecret":"string","status":0,"tempApiAccessToken":"string","tokenExpireTime":"2017-06-01T08:06:20.708Z","manageOrgId":"string","includeChildOrgs":true,"sort":0,"useObjectType":"string","isShow":true,"appHotIndex":0,"appOpenNum":0,"appKind":"string","fAppOwnerType":0,"fEnName":"string"}]
     * classBadges : [{"badgeId":"string","badgeUrl":"string","badgeName":"string"}]
     * classRank : 0
     * classGrothValue : 0
     * chatGroupKey : string
     * schoolRanking : 0
     * platformRanking : 0
     * noticeNum : 0
     * homeworkNoCompleteNum : 0
     */

    private String classId;
    private String classNum;
    private String cName;
    private String gradeName;
    private String schoolName;
    private String alias;
    private String classLogo;
    private int classMemberNum;
    private String classCoverImg;
    private String classUserId;
    private String classUserRealName;
    private String classUserAvatar;
    private int photoNum;
    private int blogNum;
    private int activitiesNum;
    private int classApplyAuditStatus;
    private boolean isClassMaker;
    private boolean isAdmin;
    private boolean isMember;
    private boolean isDefaultClass;
    private String classDescribe;
    private String orgId;
    private int eductionSystemId;
    private String eductionSystemName;
    private String areaName;
    private String areaShiName;
    private String areaCode;
    private String areaShiCode;
    private boolean allowJoin;
    private String classActiviteUserAvatar;
    private int classRank;
    private int classGrothValue;
    private String chatGroupKey;
    private int schoolRanking;
    private int platformRanking;
    private int noticeNum;
    private int homeworkNoCompleteNum;
    private int imNum;
    private List<BannerListBean> bannerList;
    private List<ClassAppsBean> classApps;
    private List<ClassBadgesBean> classBadges;

    public int getImNum() {
        return imNum;
    }

    public void setImNum(int imNum) {
        this.imNum = imNum;
    }

    public String getClassId() {
        return classId == null ? "" : classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public String getCName() {
        return cName;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getClassLogo() {
        return classLogo;
    }

    public void setClassLogo(String classLogo) {
        this.classLogo = classLogo;
    }

    public int getClassMemberNum() {
        return classMemberNum;
    }

    public void setClassMemberNum(int classMemberNum) {
        this.classMemberNum = classMemberNum;
    }

    public String getClassCoverImg() {
        return classCoverImg;
    }

    public void setClassCoverImg(String classCoverImg) {
        this.classCoverImg = classCoverImg;
    }

    public String getClassUserId() {
        return classUserId;
    }

    public void setClassUserId(String classUserId) {
        this.classUserId = classUserId;
    }

    public String getClassUserRealName() {
        return classUserRealName;
    }

    public void setClassUserRealName(String classUserRealName) {
        this.classUserRealName = classUserRealName;
    }

    public String getClassUserAvatar() {
        return classUserAvatar;
    }

    public void setClassUserAvatar(String classUserAvatar) {
        this.classUserAvatar = classUserAvatar;
    }

    public int getPhotoNum() {
        return photoNum;
    }

    public void setPhotoNum(int photoNum) {
        this.photoNum = photoNum;
    }

    public int getBlogNum() {
        return blogNum;
    }

    public void setBlogNum(int blogNum) {
        this.blogNum = blogNum;
    }

    public int getActivitiesNum() {
        return activitiesNum;
    }

    public void setActivitiesNum(int activitiesNum) {
        this.activitiesNum = activitiesNum;
    }

    public int getClassApplyAuditStatus() {
        return classApplyAuditStatus;
    }

    public void setClassApplyAuditStatus(int classApplyAuditStatus) {
        this.classApplyAuditStatus = classApplyAuditStatus;
    }

    public boolean isIsClassMaker() {
        return isClassMaker;
    }

    public void setIsClassMaker(boolean isClassMaker) {
        this.isClassMaker = isClassMaker;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isIsMember() {
        return isMember;
    }

    public void setIsMember(boolean isMember) {
        this.isMember = isMember;
    }

    public boolean isIsDefaultClass() {
        return isDefaultClass;
    }

    public void setIsDefaultClass(boolean isDefaultClass) {
        this.isDefaultClass = isDefaultClass;
    }

    public String getClassDescribe() {
        return classDescribe;
    }

    public void setClassDescribe(String classDescribe) {
        this.classDescribe = classDescribe;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public int getEductionSystemId() {
        return eductionSystemId;
    }

    public void setEductionSystemId(int eductionSystemId) {
        this.eductionSystemId = eductionSystemId;
    }

    public String getEductionSystemName() {
        return eductionSystemName;
    }

    public void setEductionSystemName(String eductionSystemName) {
        this.eductionSystemName = eductionSystemName;
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

    public boolean isAllowJoin() {
        return allowJoin;
    }

    public void setAllowJoin(boolean allowJoin) {
        this.allowJoin = allowJoin;
    }

    public String getClassActiviteUserAvatar() {
        return classActiviteUserAvatar;
    }

    public void setClassActiviteUserAvatar(String classActiviteUserAvatar) {
        this.classActiviteUserAvatar = classActiviteUserAvatar;
    }

    public int getClassRank() {
        return classRank;
    }

    public void setClassRank(int classRank) {
        this.classRank = classRank;
    }

    public int getClassGrothValue() {
        return classGrothValue;
    }

    public void setClassGrothValue(int classGrothValue) {
        this.classGrothValue = classGrothValue;
    }

    public String getChatGroupKey() {
        return chatGroupKey;
    }

    public void setChatGroupKey(String chatGroupKey) {
        this.chatGroupKey = chatGroupKey;
    }

    public int getSchoolRanking() {
        return schoolRanking;
    }

    public void setSchoolRanking(int schoolRanking) {
        this.schoolRanking = schoolRanking;
    }

    public int getPlatformRanking() {
        return platformRanking;
    }

    public void setPlatformRanking(int platformRanking) {
        this.platformRanking = platformRanking;
    }

    public int getNoticeNum() {
        return noticeNum;
    }

    public void setNoticeNum(int noticeNum) {
        this.noticeNum = noticeNum;
    }

    public int getHomeworkNoCompleteNum() {
        return homeworkNoCompleteNum;
    }

    public void setHomeworkNoCompleteNum(int homeworkNoCompleteNum) {
        this.homeworkNoCompleteNum = homeworkNoCompleteNum;
    }

    public List<BannerListBean> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<BannerListBean> bannerList) {
        this.bannerList = bannerList;
    }

    public List<ClassAppsBean> getClassApps() {
        return classApps;
    }

    public void setClassApps(List<ClassAppsBean> classApps) {
        this.classApps = classApps;
    }

    public List<ClassBadgesBean> getClassBadges() {
        return classBadges;
    }

    public void setClassBadges(List<ClassBadgesBean> classBadges) {
        this.classBadges = classBadges;
    }

    public static class BannerListBean {
        /**
         * photoId : string
         * albumId : string
         * syncPhotoId : string
         * ownerId : string
         * userId : string
         * author : string
         * schoolId : string
         * schoolName : string
         * filePath : string
         * fileLength : 0
         * width : 0
         * height : 0
         * name : string
         * description : string
         * uploadIP : string
         * createTime : 2017-06-01T08:06:20.707Z
         * updateUserId : string
         * updateTime : 2017-06-01T08:06:20.707Z
         * longitude : 0
         * latitude : 0
         * location : string
         * praiseNum : 0
         * commentNum : 0
         * visitNum : 0
         * isDeleted : true
         * albumName : string
         * ownerName : string
         * ownerType : 0
         * auditStatus : 0
         * sensitiveLevel : 0
         * sensitiveKeyWords : string
         * batchNumber : string
         * syncOwnerType : 0
         * syncOwnerIds : ["string"]
         * atUser : [{"userId":"string","realName":"string"}]
         * alias : string
         * activityId : string
         */

        private String photoId;
        private String albumId;
        private String syncPhotoId;
        private String ownerId;
        private String userId;
        private String author;
        private String schoolId;
        private String schoolName;
        private String filePath;
        private int fileLength;
        private int width;
        private int height;
        private String name;
        private String description;
        private String uploadIP;
        private String createTime;
        private String updateUserId;
        private String updateTime;
        private int longitude;
        private int latitude;
        private String location;
        private int praiseNum;
        private int commentNum;
        private int visitNum;
        private boolean isDeleted;
        private String albumName;
        private String ownerName;
        private int ownerType;
        private int auditStatus;
        private int sensitiveLevel;
        private String sensitiveKeyWords;
        private String batchNumber;
        private int syncOwnerType;
        private String alias;
        private String activityId;
        private List<String> syncOwnerIds;
        private List<AtUserBean> atUser;

        public String getPhotoId() {
            return photoId;
        }

        public void setPhotoId(String photoId) {
            this.photoId = photoId;
        }

        public String getAlbumId() {
            return albumId;
        }

        public void setAlbumId(String albumId) {
            this.albumId = albumId;
        }

        public String getSyncPhotoId() {
            return syncPhotoId;
        }

        public void setSyncPhotoId(String syncPhotoId) {
            this.syncPhotoId = syncPhotoId;
        }

        public String getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(String ownerId) {
            this.ownerId = ownerId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(String schoolId) {
            this.schoolId = schoolId;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public int getFileLength() {
            return fileLength;
        }

        public void setFileLength(int fileLength) {
            this.fileLength = fileLength;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUploadIP() {
            return uploadIP;
        }

        public void setUploadIP(String uploadIP) {
            this.uploadIP = uploadIP;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateUserId() {
            return updateUserId;
        }

        public void setUpdateUserId(String updateUserId) {
            this.updateUserId = updateUserId;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getLongitude() {
            return longitude;
        }

        public void setLongitude(int longitude) {
            this.longitude = longitude;
        }

        public int getLatitude() {
            return latitude;
        }

        public void setLatitude(int latitude) {
            this.latitude = latitude;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public int getPraiseNum() {
            return praiseNum;
        }

        public void setPraiseNum(int praiseNum) {
            this.praiseNum = praiseNum;
        }

        public int getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(int commentNum) {
            this.commentNum = commentNum;
        }

        public int getVisitNum() {
            return visitNum;
        }

        public void setVisitNum(int visitNum) {
            this.visitNum = visitNum;
        }

        public boolean isIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(boolean isDeleted) {
            this.isDeleted = isDeleted;
        }

        public String getAlbumName() {
            return albumName;
        }

        public void setAlbumName(String albumName) {
            this.albumName = albumName;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }

        public int getOwnerType() {
            return ownerType;
        }

        public void setOwnerType(int ownerType) {
            this.ownerType = ownerType;
        }

        public int getAuditStatus() {
            return auditStatus;
        }

        public void setAuditStatus(int auditStatus) {
            this.auditStatus = auditStatus;
        }

        public int getSensitiveLevel() {
            return sensitiveLevel;
        }

        public void setSensitiveLevel(int sensitiveLevel) {
            this.sensitiveLevel = sensitiveLevel;
        }

        public String getSensitiveKeyWords() {
            return sensitiveKeyWords;
        }

        public void setSensitiveKeyWords(String sensitiveKeyWords) {
            this.sensitiveKeyWords = sensitiveKeyWords;
        }

        public String getBatchNumber() {
            return batchNumber;
        }

        public void setBatchNumber(String batchNumber) {
            this.batchNumber = batchNumber;
        }

        public int getSyncOwnerType() {
            return syncOwnerType;
        }

        public void setSyncOwnerType(int syncOwnerType) {
            this.syncOwnerType = syncOwnerType;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public List<String> getSyncOwnerIds() {
            return syncOwnerIds;
        }

        public void setSyncOwnerIds(List<String> syncOwnerIds) {
            this.syncOwnerIds = syncOwnerIds;
        }

        public List<AtUserBean> getAtUser() {
            return atUser;
        }

        public void setAtUser(List<AtUserBean> atUser) {
            this.atUser = atUser;
        }

        public static class AtUserBean {
            /**
             * userId : string
             * realName : string
             */

            private String userId;
            private String realName;

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
        }
    }

    public static class ClassAppsBean {
        /**
         * appKey : string
         * appType : 0
         * appName : string
         * appUrl : string
         * logo : string
         * isNew:boolean
         * alertTypePriority:String
         * msgNum:
         * author : string
         * appDescription : string
         * registeTime : 2017-06-01T08:06:20.708Z
         * auditTime : 2017-06-01T08:06:20.708Z
         * appSecret : string
         * status : 0
         * tempApiAccessToken : string
         * tokenExpireTime : 2017-06-01T08:06:20.708Z
         * manageOrgId : string
         * includeChildOrgs : true
         * sort : 0
         * useObjectType : string
         * isShow : true
         * appHotIndex : 0
         * appOpenNum : 0
         * appKind : string
         * fAppOwnerType : 0
         * fEnName : string
         */

        private String appKey;
        private int appType;
        private String appName;
        private String appUrl;
        private String logo;
        private boolean isNew;
        private int msgNum;
        private String alertTypePriority;
        private String author;
        private String appDescription;
        private String registeTime;
        private String auditTime;
        private String appSecret;
        private int status;
        private String tempApiAccessToken;
        private String tokenExpireTime;
        private String manageOrgId;
        private boolean includeChildOrgs;
        private int sort;
        private String useObjectType;
        private boolean isShow;
        private int appHotIndex;
        private int appOpenNum;
        private String appKind;
        private int fAppOwnerType;
        private String fEnName;

        public String getAppKey() {
            return appKey;
        }

        public void setAppKey(String appKey) {
            this.appKey = appKey;
        }

        public int getAppType() {
            return appType;
        }

        public void setAppType(int appType) {
            this.appType = appType;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getAppUrl() {
            return appUrl;
        }

        public void setAppUrl(String appUrl) {
            this.appUrl = appUrl;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public boolean isIsNew() {
            return isNew;
        }

        public void setIsNew(boolean isNew) {
            this.isNew = isNew;
        }

        public String getAlertTypePriority() {
            return alertTypePriority;
        }

        public void setAlertTypePriority(String alertTypePriority) {
            this.alertTypePriority = alertTypePriority;
        }

        public int getMsgNum() {
            return msgNum;
        }

        public void setMsgNum(int msgNum) {
            this.msgNum = msgNum;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getAppDescription() {
            return appDescription;
        }

        public void setAppDescription(String appDescription) {
            this.appDescription = appDescription;
        }

        public String getRegisteTime() {
            return registeTime;
        }

        public void setRegisteTime(String registeTime) {
            this.registeTime = registeTime;
        }

        public String getAuditTime() {
            return auditTime;
        }

        public void setAuditTime(String auditTime) {
            this.auditTime = auditTime;
        }

        public String getAppSecret() {
            return appSecret;
        }

        public void setAppSecret(String appSecret) {
            this.appSecret = appSecret;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTempApiAccessToken() {
            return tempApiAccessToken;
        }

        public void setTempApiAccessToken(String tempApiAccessToken) {
            this.tempApiAccessToken = tempApiAccessToken;
        }

        public String getTokenExpireTime() {
            return tokenExpireTime;
        }

        public void setTokenExpireTime(String tokenExpireTime) {
            this.tokenExpireTime = tokenExpireTime;
        }

        public String getManageOrgId() {
            return manageOrgId;
        }

        public void setManageOrgId(String manageOrgId) {
            this.manageOrgId = manageOrgId;
        }

        public boolean isIncludeChildOrgs() {
            return includeChildOrgs;
        }

        public void setIncludeChildOrgs(boolean includeChildOrgs) {
            this.includeChildOrgs = includeChildOrgs;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getUseObjectType() {
            return useObjectType;
        }

        public void setUseObjectType(String useObjectType) {
            this.useObjectType = useObjectType;
        }

        public boolean isIsShow() {
            return isShow;
        }

        public void setIsShow(boolean isShow) {
            this.isShow = isShow;
        }

        public int getAppHotIndex() {
            return appHotIndex;
        }

        public void setAppHotIndex(int appHotIndex) {
            this.appHotIndex = appHotIndex;
        }

        public int getAppOpenNum() {
            return appOpenNum;
        }

        public void setAppOpenNum(int appOpenNum) {
            this.appOpenNum = appOpenNum;
        }

        public String getAppKind() {
            return appKind;
        }

        public void setAppKind(String appKind) {
            this.appKind = appKind;
        }

        public int getFAppOwnerType() {
            return fAppOwnerType;
        }

        public void setFAppOwnerType(int fAppOwnerType) {
            this.fAppOwnerType = fAppOwnerType;
        }

        public String getFEnName() {
            return fEnName;
        }

        public void setFEnName(String fEnName) {
            this.fEnName = fEnName;
        }
    }

    public static class ClassBadgesBean {
        /**
         * badgeId : string
         * badgeUrl : string
         * badgeName : string
         */

        private String badgeId;
        private String badgeUrl;
        private String badgeName;

        public String getBadgeId() {
            return badgeId;
        }

        public void setBadgeId(String badgeId) {
            this.badgeId = badgeId;
        }

        public String getBadgeUrl() {
            return badgeUrl;
        }

        public void setBadgeUrl(String badgeUrl) {
            this.badgeUrl = badgeUrl;
        }

        public String getBadgeName() {
            return badgeName;
        }

        public void setBadgeName(String badgeName) {
            this.badgeName = badgeName;
        }
    }
}
