package com.histudent.jwsoft.histudent.body.message.model;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2017/4/18.
 */

public class NoticeBean {

    /**
     * cursor : 0
     * startTime : 0
     * items : [{"id":"string","userId":"string","conent":"string","infoType":1,"objectId":"string","objectType":0,"url":"string","addTime":"2017-05-26T09:06:31.972Z","viewTime":"2017-05-26T09:06:31.972Z","viewStatus":true,"isDelete":true,"pUserId":"string","pUserName":"string","pAvatar":"string","actId":"string","associatedId":"string","infoStatus":0}]
     * nextTime : 0
     */

    private int cursor;
    private int startTime;
    private int nextTime;
    private List<ItemsBean> items;

    public int getCursor() {
        return cursor;
    }

    public void setCursor(int cursor) {
        this.cursor = cursor;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getNextTime() {
        return nextTime;
    }

    public void setNextTime(int nextTime) {
        this.nextTime = nextTime;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * id : string
         * userId : string
         * conent : string
         * infoType : 1
         * objectId : string
         * objectType : 0
         * url : string
         * addTime : 2017-05-26T09:06:31.972Z
         * viewTime : 2017-05-26T09:06:31.972Z
         * viewStatus : true
         * isDelete : true
         * pUserId : string
         * pUserName : string
         * pAvatar : string
         * actId : string
         * associatedId : string
         * infoStatus : 0
         */

        private String id;
        private String userId;
        private String conent;
        private int infoType;
        private String objectId;
        private int objectType;
        private String url;
        private String addTime;
        private String viewTime;
        private boolean viewStatus;
        private boolean isDelete;
        private String pUserId;
        private String pUserName;
        private String pAvatar;
        private String actId;
        private String associatedId;
        private int infoStatus;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getConent() {
            return conent;
        }

        public void setConent(String conent) {
            this.conent = conent;
        }

        public int getInfoType() {
            return infoType;
        }

        public void setInfoType(int infoType) {
            this.infoType = infoType;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public int getObjectType() {
            return objectType;
        }

        public void setObjectType(int objectType) {
            this.objectType = objectType;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public String getViewTime() {
            return viewTime;
        }

        public void setViewTime(String viewTime) {
            this.viewTime = viewTime;
        }

        public boolean isViewStatus() {
            return viewStatus;
        }

        public void setViewStatus(boolean viewStatus) {
            this.viewStatus = viewStatus;
        }

        public boolean isIsDelete() {
            return isDelete;
        }

        public void setIsDelete(boolean isDelete) {
            this.isDelete = isDelete;
        }

        public String getPUserId() {
            return pUserId;
        }

        public void setPUserId(String pUserId) {
            this.pUserId = pUserId;
        }

        public String getPUserName() {
            return pUserName;
        }

        public void setPUserName(String pUserName) {
            this.pUserName = pUserName;
        }

        public String getPAvatar() {
            return pAvatar;
        }

        public void setPAvatar(String pAvatar) {
            this.pAvatar = pAvatar;
        }

        public String getActId() {
            return actId;
        }

        public void setActId(String actId) {
            this.actId = actId;
        }

        public String getAssociatedId() {
            return associatedId;
        }

        public void setAssociatedId(String associatedId) {
            this.associatedId = associatedId;
        }

        public int getInfoStatus() {
            return infoStatus;
        }

        public void setInfoStatus(int infoStatus) {
            this.infoStatus = infoStatus;
        }
    }
}
