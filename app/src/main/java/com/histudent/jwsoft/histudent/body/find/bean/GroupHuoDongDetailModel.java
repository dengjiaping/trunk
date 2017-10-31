package com.histudent.jwsoft.histudent.body.find.bean;

import java.util.List;

/**
 * Created by ZhangYT on 2017/5/16.
 */

public class GroupHuoDongDetailModel {

    /**
     * isSinuped : true
     * sinupedUsers : [{"userId":"df3a0abd-415f-4e26-ac6c-00f8ea013a9d","name":"吖吖","avatar":"http://img.hitongx.com/UploadFiles/Album/20170223/f1609bcd-7fab-4950-8988-f86fa4e1850a.jpg@60w_60h_1e_1c_2o_30-1ci.jpg"}]
     * introImgList : [{"imgId":"e3b2a3a5-753a-4bed-a139-4fedf08832ee","savePath":"http://img.hitongx.com/HuodongCover/Class/20170607/793d003c-4750-43cc-8ff9-150dd2cb8ae1.png@700w_400h_1e_1l_2o.png.png?wh=1080_1920","width":1080,"height":1920}]
     * isMember : true
     * id : 9af9ae86-33c8-438f-8b3e-615bfb22b6a6
     * name : 无限
     * coverUrl : http://img.hitongx.com/HuodongCover/Class/20170607/b79c3fce-74d2-4877-8f00-651edac1d98d.jpg@750w_400h_1e_1l_2o.jpg.jpg
     * startTime : 2017-06-08 19:16:00
     * endTime : 2017-06-12 19:16:00
     * place : 北部软件园培训中心
     * longitude : 120.11
     * latitude : 30.33
     * status : 1
     * alarmType : 1
     * maxUserNum : 0
     * signUpNum : 1
     * userCost : 12
     * introduction : 沫沫
     * ownerId : de36ba39-df97-43cb-9c9c-de20219ba80e
     * ownerName : 摩天轮
     * ownerType : 2
     * isDelete : false
     * createUser : df3a0abd-415f-4e26-ac6c-00f8ea013a9d
     * createName : 吖吖
     * createTime : 2017-06-07 19:17:19
     * updateUser : df3a0abd-415f-4e26-ac6c-00f8ea013a9d
     * updateName : 吖吖
     * updateTime : 2017-06-07 19:17:19
     */

    private boolean isSinuped;
    private boolean isMember;
    private String id;
    private String name;
    private String coverUrl;
    private String startTime;
    private String endTime;
    private String place;
    private double longitude;
    private double latitude;
    private int status;
    private int alarmType;
    private int maxUserNum;
    private int signUpNum;
    private int userCost;
    private String introduction;
    private String ownerId;
    private String ownerName;
    private int ownerType;
    private boolean isDelete;
    private String createUser;
    private String createName;
    private String createTime;
    private String updateUser;
    private String updateName;
    private String updateTime;
    private List<SinupedUsersBean> sinupedUsers;
    private List<IntroImgListBean> introImgList;

    public boolean isIsSinuped() {
        return isSinuped;
    }

    public void setIsSinuped(boolean isSinuped) {
        this.isSinuped = isSinuped;
    }

    public boolean isIsMember() {
        return isMember;
    }

    public void setIsMember(boolean isMember) {
        this.isMember = isMember;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(int alarmType) {
        this.alarmType = alarmType;
    }

    public int getMaxUserNum() {
        return maxUserNum;
    }

    public void setMaxUserNum(int maxUserNum) {
        this.maxUserNum = maxUserNum;
    }

    public int getSignUpNum() {
        return signUpNum;
    }

    public void setSignUpNum(int signUpNum) {
        this.signUpNum = signUpNum;
    }

    public int getUserCost() {
        return userCost;
    }

    public void setUserCost(int userCost) {
        this.userCost = userCost;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
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

    public boolean isIsDelete() {
        return isDelete;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public List<SinupedUsersBean> getSinupedUsers() {
        return sinupedUsers;
    }

    public void setSinupedUsers(List<SinupedUsersBean> sinupedUsers) {
        this.sinupedUsers = sinupedUsers;
    }

    public List<IntroImgListBean> getIntroImgList() {
        return introImgList;
    }

    public void setIntroImgList(List<IntroImgListBean> introImgList) {
        this.introImgList = introImgList;
    }

    public static class SinupedUsersBean {
        /**
         * userId : df3a0abd-415f-4e26-ac6c-00f8ea013a9d
         * name : 吖吖
         * avatar : http://img.hitongx.com/UploadFiles/Album/20170223/f1609bcd-7fab-4950-8988-f86fa4e1850a.jpg@60w_60h_1e_1c_2o_30-1ci.jpg
         */

        private String userId;
        private String name;
        private String avatar;

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

    public static class IntroImgListBean {
        /**
         * imgId : e3b2a3a5-753a-4bed-a139-4fedf08832ee
         * savePath : http://img.hitongx.com/HuodongCover/Class/20170607/793d003c-4750-43cc-8ff9-150dd2cb8ae1.png@700w_400h_1e_1l_2o.png.png?wh=1080_1920
         * width : 1080
         * height : 1920
         */

        private String imgId;
        private String savePath;
        private int width;
        private int height;

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
    }
}
