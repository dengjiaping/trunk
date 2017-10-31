package com.histudent.jwsoft.histudent.body.hiworld.bean;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/8/12.
 */
public class GoodFriendBean {


    /**
     * cursor : 1470982813
     * items : [{"actId":"88febd97-3e6e-48f9-a0e5-8b9be85daea5","ownerId":"0643c5ca-9268-421a-9ee1-12c721012804","ownerType":1,"ownerName":"木夏","activityItemKey":"CreateBlog","userId":"0643c5ca-9268-421a-9ee1-12c721012804","userName":"13777553473","userAvatar":"http://img.hitongx.com/UploadFiles/UploadAvatar/UserSystemAvatar/member_74.png@_60w_60h_1e_1c_30-1ci.png","sourceId":"9bed2c7b-13f8-41c4-9560-dc77c0b4b2e1","isPrivate":0,"actionInfo":"公开发布日志","title":"测试日志111","summary":"测试日志111测试日志111测试日志111测试日志111","cover":"","images":[],"lastUpdateTime":"2016-08-12 14:20:13","location":"","praiseCount":0,"praiseUsers":[],"commentCount":6,"forwardCount":0,"orderByNum":0}]
     */

    private int cursor;
    /**
     * actId : 88febd97-3e6e-48f9-a0e5-8b9be85daea5
     * ownerId : 0643c5ca-9268-421a-9ee1-12c721012804
     * ownerType : 1
     * ownerName : 木夏
     * activityItemKey : CreateBlog
     * userId : 0643c5ca-9268-421a-9ee1-12c721012804
     * userName : 13777553473
     * userAvatar : http://img.hitongx.com/UploadFiles/UploadAvatar/UserSystemAvatar/member_74.png@_60w_60h_1e_1c_30-1ci.png
     * sourceId : 9bed2c7b-13f8-41c4-9560-dc77c0b4b2e1
     * isPrivate : 0
     * actionInfo : 公开发布日志
     * title : 测试日志111
     * summary : 测试日志111测试日志111测试日志111测试日志111
     * cover :
     * images : []
     * lastUpdateTime : 2016-08-12 14:20:13
     * location :
     * praiseCount : 0
     * praiseUsers : []
     * commentCount : 6
     * forwardCount : 0
     * orderByNum : 0
     */

    private List<ItemsBean> items;

    public int getCursor() {
        return cursor;
    }

    public void setCursor(int cursor) {
        this.cursor = cursor;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        private String actId;
        private String ownerId;
        private int ownerType;
        private String ownerName;
        private String activityItemKey;
        private String userId;
        private String userName;
        private String userAvatar;
        private String sourceId;
        private int isPrivate;
        private String actionInfo;
        private String title;
        private String summary;
        private String cover;
        private String lastUpdateTime;
        private String location;
        private int praiseCount;
        private int commentCount;
        private int forwardCount;
        private int orderByNum;
        private List<?> images;
        private List<?> praiseUsers;

        public String getActId() {
            return actId;
        }

        public void setActId(String actId) {
            this.actId = actId;
        }

        public String getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(String ownerId) {
            this.ownerId = ownerId;
        }

        public int getOwnerType() {
            return ownerType;
        }

        public void setOwnerType(int ownerType) {
            this.ownerType = ownerType;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }

        public String getActivityItemKey() {
            return activityItemKey;
        }

        public void setActivityItemKey(String activityItemKey) {
            this.activityItemKey = activityItemKey;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserAvatar() {
            return userAvatar;
        }

        public void setUserAvatar(String userAvatar) {
            this.userAvatar = userAvatar;
        }

        public String getSourceId() {
            return sourceId;
        }

        public void setSourceId(String sourceId) {
            this.sourceId = sourceId;
        }

        public int getIsPrivate() {
            return isPrivate;
        }

        public void setIsPrivate(int isPrivate) {
            this.isPrivate = isPrivate;
        }

        public String getActionInfo() {
            return actionInfo;
        }

        public void setActionInfo(String actionInfo) {
            this.actionInfo = actionInfo;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getLastUpdateTime() {
            return lastUpdateTime;
        }

        public void setLastUpdateTime(String lastUpdateTime) {
            this.lastUpdateTime = lastUpdateTime;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public int getPraiseCount() {
            return praiseCount;
        }

        public void setPraiseCount(int praiseCount) {
            this.praiseCount = praiseCount;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public int getForwardCount() {
            return forwardCount;
        }

        public void setForwardCount(int forwardCount) {
            this.forwardCount = forwardCount;
        }

        public int getOrderByNum() {
            return orderByNum;
        }

        public void setOrderByNum(int orderByNum) {
            this.orderByNum = orderByNum;
        }

        public List<?> getImages() {
            return images;
        }

        public void setImages(List<?> images) {
            this.images = images;
        }

        public List<?> getPraiseUsers() {
            return praiseUsers;
        }

        public void setPraiseUsers(List<?> praiseUsers) {
            this.praiseUsers = praiseUsers;
        }
    }
}
