package com.histudent.jwsoft.histudent.body.mine.model;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/12/8.
 */

public class ActionRankModel {


    /**
     * className : string
     * curUserActivity : {"userId":"string","userName":"string","avatar":"string","blogCount":0,"photoCount":0,"logTime":"2016-12-08T03:20:16.680Z","activityValue":0,"rank":0}
     * curClassActivityList : [{"userId":"string","userName":"string","avatar":"string","blogCount":0,"photoCount":0,"logTime":"2016-12-08T03:20:16.680Z","activityValue":0,"rank":0}]
     */

    private String className;
    private CurUserActivityBean curUserActivity;
    private List<CurClassActivityListBean> curClassActivityList;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public CurUserActivityBean getCurUserActivity() {
        return curUserActivity;
    }

    public void setCurUserActivity(CurUserActivityBean curUserActivity) {
        this.curUserActivity = curUserActivity;
    }

    public List<CurClassActivityListBean> getCurClassActivityList() {
        return curClassActivityList;
    }

    public void setCurClassActivityList(List<CurClassActivityListBean> curClassActivityList) {
        this.curClassActivityList = curClassActivityList;
    }

    public static class CurUserActivityBean {
        /**
         * userId : string
         * userName : string
         * avatar : string
         * blogCount : 0
         * photoCount : 0
         * logTime : 2016-12-08T03:20:16.680Z
         * activityValue : 0
         * rank : 0
         */

        private String userId;
        private String userName;
        private String avatar;
        private int blogCount;
        private int photoCount;
        private String logTime;
        private int activityValue;
        private int rank;

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

        public String getLogTime() {
            return logTime;
        }

        public void setLogTime(String logTime) {
            this.logTime = logTime;
        }

        public int getActivityValue() {
            return activityValue;
        }

        public void setActivityValue(int activityValue) {
            this.activityValue = activityValue;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }
    }

    public static class CurClassActivityListBean {
        /**
         * userId : string
         * userName : string
         * avatar : string
         * blogCount : 0
         * photoCount : 0
         * logTime : 2016-12-08T03:20:16.680Z
         * activityValue : 0
         * rank : 0
         */

        private String userId;
        private String userName;
        private String avatar;
        private int blogCount;
        private int photoCount;
        private String logTime;
        private int activityValue;
        private int rank;

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

        public String getLogTime() {
            return logTime;
        }

        public void setLogTime(String logTime) {
            this.logTime = logTime;
        }

        public int getActivityValue() {
            return activityValue;
        }

        public void setActivityValue(int activityValue) {
            this.activityValue = activityValue;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }
    }
}
