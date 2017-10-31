package com.histudent.jwsoft.histudent.body.hiworld.bean;

import java.util.List;

/**
 * Created by ZhangYT on 2017/4/17.
 */

public class LogModel {


    /**
     * itemCount : 24
     * items : [{"summary":"哈哈哈","createTime":"2017-04-17 21:21:34","privacyStatus":0,"ownerCategoryId":"00000000-0000-0000-0000-000000000000","updateTime":"2017-04-17 21:21:34","location":"null","ownerId":"df3a0abd-415f-4e26-ac6c-00f8ea013a9d","blogId":"a574addd-a63e-405c-bcf5-06a464a48e70","praiseUser":[],"ownerCategoryName":"默认分类","userAvatar":"http://img.hitongx.com/UploadFiles/Album/20170223/f1609bcd-7fab-4950-8988-f86fa4e1850a.jpg@60w_60h_1e_1c_2o_30-1ci.jpg","commentNum":0,"praiseNum":0,"author":"吖吖","isDraft":false,"cover":"","htmlUrl":"http://localhost:44967/LinkRoute/Index?name=a574addd-a63e-405c-bcf5-06a464a48e70&parameter=","title":"啦啦啦","transpondNum":0,"visitNum":0,"userId":"df3a0abd-415f-4e26-ac6c-00f8ea013a9d","isPraised":false},{"summary":"@赵武的家长@吖","createTime":"2017-04-16 17:15:20","privacyStatus":0,"ownerCategoryId":"00000000-0000-0000-0000-000000000000","updateTime":"2017-04-16 17:15:20","location":"null","ownerId":"df3a0abd-415f-4e26-ac6c-00f8ea013a9d","blogId":"78908fd1-d763-42bf-88fb-a907549388e3","praiseUser":[],"ownerCategoryName":"默认分类","userAvatar":"http://img.hitongx.com/UploadFiles/Album/20170223/f1609bcd-7fab-4950-8988-f86fa4e1850a.jpg@60w_60h_1e_1c_2o_30-1ci.jpg","commentNum":0,"praiseNum":0,"author":"吖吖","isDraft":false,"cover":"http://img.hitongx.com/UploadFiles/twitter/20170416/330eacce-7b69-4f46-8d5c-dbe8365e0c04.png","htmlUrl":"http://localhost:44967/LinkRoute/Index?name=78908fd1-d763-42bf-88fb-a907549388e3&parameter=","title":"好","transpondNum":0,"visitNum":0,"userId":"df3a0abd-415f-4e26-ac6c-00f8ea013a9d","isPraised":false}]
     */

    private int itemCount;
    private List<ItemsBean> items;

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * summary : 哈哈哈
         * createTime : 2017-04-17 21:21:34
         * privacyStatus : 0
         * ownerCategoryId : 00000000-0000-0000-0000-000000000000
         * updateTime : 2017-04-17 21:21:34
         * location : null
         * ownerId : df3a0abd-415f-4e26-ac6c-00f8ea013a9d
         * blogId : a574addd-a63e-405c-bcf5-06a464a48e70
         * praiseUser : []
         * ownerCategoryName : 默认分类
         * userAvatar : http://img.hitongx.com/UploadFiles/Album/20170223/f1609bcd-7fab-4950-8988-f86fa4e1850a.jpg@60w_60h_1e_1c_2o_30-1ci.jpg
         * commentNum : 0
         * praiseNum : 0
         * author : 吖吖
         * isDraft : false
         * cover :
         * htmlUrl : http://localhost:44967/LinkRoute/Index?name=a574addd-a63e-405c-bcf5-06a464a48e70&parameter=
         * title : 啦啦啦
         * transpondNum : 0
         * visitNum : 0
         * userId : df3a0abd-415f-4e26-ac6c-00f8ea013a9d
         * isPraised : false
         */

        private String summary;
        private String createTime;
        private int privacyStatus;
        private String ownerCategoryId;
        private String updateTime;
        private String location;
        private String ownerId;
        private String blogId;
        private String ownerCategoryName;
        private String userAvatar;
        private int commentNum;
        private int praiseNum;
        private String author;
        private boolean isDraft;
        private String cover;
        private String htmlUrl;
        private String title;
        private int transpondNum;
        private int visitNum;
        private String userId;
        private boolean isPraised;
        private List<?> praiseUser;

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getPrivacyStatus() {
            return privacyStatus;
        }

        public void setPrivacyStatus(int privacyStatus) {
            this.privacyStatus = privacyStatus;
        }

        public String getOwnerCategoryId() {
            return ownerCategoryId;
        }

        public void setOwnerCategoryId(String ownerCategoryId) {
            this.ownerCategoryId = ownerCategoryId;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(String ownerId) {
            this.ownerId = ownerId;
        }

        public String getBlogId() {
            return blogId;
        }

        public void setBlogId(String blogId) {
            this.blogId = blogId;
        }

        public String getOwnerCategoryName() {
            return ownerCategoryName;
        }

        public void setOwnerCategoryName(String ownerCategoryName) {
            this.ownerCategoryName = ownerCategoryName;
        }

        public String getUserAvatar() {
            return userAvatar;
        }

        public void setUserAvatar(String userAvatar) {
            this.userAvatar = userAvatar;
        }

        public int getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(int commentNum) {
            this.commentNum = commentNum;
        }

        public int getPraiseNum() {
            return praiseNum;
        }

        public void setPraiseNum(int praiseNum) {
            this.praiseNum = praiseNum;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public boolean isIsDraft() {
            return isDraft;
        }

        public void setIsDraft(boolean isDraft) {
            this.isDraft = isDraft;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getHtmlUrl() {
            return htmlUrl;
        }

        public void setHtmlUrl(String htmlUrl) {
            this.htmlUrl = htmlUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getTranspondNum() {
            return transpondNum;
        }

        public void setTranspondNum(int transpondNum) {
            this.transpondNum = transpondNum;
        }

        public int getVisitNum() {
            return visitNum;
        }

        public void setVisitNum(int visitNum) {
            this.visitNum = visitNum;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public boolean isIsPraised() {
            return isPraised;
        }

        public void setIsPraised(boolean isPraised) {
            this.isPraised = isPraised;
        }

        public List<?> getPraiseUser() {
            return praiseUser;
        }

        public void setPraiseUser(List<?> praiseUser) {
            this.praiseUser = praiseUser;
        }
    }
}
