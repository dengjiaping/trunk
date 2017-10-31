package com.histudent.jwsoft.histudent.body.find.bean;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2017/5/16.
 */

public class UserRankBean {

    /**
     * itemCount : 0
     * pageCount : 0
     * items : [{"id":"string","orgId":"string","userId":"string","realName":"string","avatar":"string","blogCount":0,"photoCount":0,"microBlogCount":0,"lastLoginTime":"string","userType":0,"hotPercent":0,"addTime":"string","activitiesCount":0,"rankNum":0,"level":0}]
     */

    private int itemCount;
    private int pageCount;
    private List<ItemsBean> items;

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
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
         * orgId : string
         * userId : string
         * realName : string
         * avatar : string
         * blogCount : 0
         * photoCount : 0
         * microBlogCount : 0
         * lastLoginTime : string
         * userType : 0
         * hotPercent : 0
         * addTime : string
         * activitiesCount : 0
         * rankNum : 0
         * level : 0
         */

        private String id;
        private String orgId;
        private String userId;
        private String realName;
        private String avatar;
        private int blogCount;
        private int photoCount;
        private int microBlogCount;
        private String lastLoginTime;
        private int userType;
        private int hotPercent;
        private String addTime;
        private int activitiesCount;
        private int rankNum;
        private int level;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getBlogCount() {
            return blogCount;
        }

        public void setBlogCount(int blogCount) {
            this.blogCount = blogCount;
        }

        public int getPhotoCount() {
            return photoCount;
        }

        public void setPhotoCount(int photoCount) {
            this.photoCount = photoCount;
        }

        public int getMicroBlogCount() {
            return microBlogCount;
        }

        public void setMicroBlogCount(int microBlogCount) {
            this.microBlogCount = microBlogCount;
        }

        public String getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(String lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public int getHotPercent() {
            return hotPercent;
        }

        public void setHotPercent(int hotPercent) {
            this.hotPercent = hotPercent;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public int getActivitiesCount() {
            return activitiesCount;
        }

        public void setActivitiesCount(int activitiesCount) {
            this.activitiesCount = activitiesCount;
        }

        public int getRankNum() {
            return rankNum;
        }

        public void setRankNum(int rankNum) {
            this.rankNum = rankNum;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }
    }
}
