package com.histudent.jwsoft.histudent.body.mine.model;

import java.io.Serializable;

/**
 * Created by liuguiyu-pc on 2016/8/13.
 */
public class CommentsModel implements Serializable {

    /**
     * commentId : 55daca3f-3542-4fb5-858f-aa98aa9f7078
     * parentId : f38de8b3-6ec7-4cc4-b1c6-a33d24354f38
     * toUserId : 503ded85-8004-4b55-bce7-6fb33cd67d7d
     * toUser : {"userId":"503ded85-8004-4b55-bce7-6fb33cd67d7d","name":"刘宜","avatar":"http://img.hitongx.com/UploadFiles/UploadAvatar/20170509/a583b58e-7d8f-4c7e-9ea3-ee2be8a425db.jpg@60w_60h_1e_1c_2o_30-1ci.jpg","content":"是是是"}
     * user : {"userId":"503ded85-8004-4b55-bce7-6fb33cd67d7d","name":"刘宜","avatar":"http://img.hitongx.com/UploadFiles/UploadAvatar/20170509/a583b58e-7d8f-4c7e-9ea3-ee2be8a425db.jpg@60w_60h_1e_1c_2o_30-1ci.jpg"}
     * content : 嗯嗯
     * createTime : 2017-05-15 20:31:17
     * isChild : true
     */

    private String commentId;
    private String parentId;
    private String toUserId;
    private ToUserBean toUser;
    private UserBean user;
    private String content;
    private String createTime;
    private boolean isChild;
    private String toCommentContent;

    public String getToCommentContent() {
        return toCommentContent;
    }

    public void setToCommentContent(String toCommentContent) {
        this.toCommentContent = toCommentContent;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public ToUserBean getToUser() {
        return toUser;
    }

    public void setToUser(ToUserBean toUser) {
        this.toUser = toUser;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public boolean isIsChild() {
        return isChild;
    }

    public void setIsChild(boolean isChild) {
        this.isChild = isChild;
    }

    public static class ToUserBean implements Serializable {
        /**
         * userId : 503ded85-8004-4b55-bce7-6fb33cd67d7d
         * name : 刘宜
         * avatar : http://img.hitongx.com/UploadFiles/UploadAvatar/20170509/a583b58e-7d8f-4c7e-9ea3-ee2be8a425db.jpg@60w_60h_1e_1c_2o_30-1ci.jpg
         * content : 是是是
         */

        private String userId;
        private String name;
        private String avatar;
        private String content;

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class UserBean implements Serializable {
        /**
         * userId : 503ded85-8004-4b55-bce7-6fb33cd67d7d
         * name : 刘宜
         * avatar : http://img.hitongx.com/UploadFiles/UploadAvatar/20170509/a583b58e-7d8f-4c7e-9ea3-ee2be8a425db.jpg@60w_60h_1e_1c_2o_30-1ci.jpg
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
}
