package com.histudent.jwsoft.histudent.commen.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/9/14.
 */
public class PhotoCommentBean implements Serializable {

    /**
     * objectId : string
     * isPraise : true
     * praiseCount : 0
     * commentCount : 0
     * praiseList : [{"praiseTime":"2017-06-05T05:46:51.106Z","userId":"string","name":"string","avatar":"string","content":"string"}]
     * commentList : [{"commentId":"string","parentId":"string","toUserId":"string","toUser":{"userId":"string","name":"string","avatar":"string","content":"string"},"user":{"userId":"string","name":"string","avatar":"string","content":"string"},"content":"string","toCommentContent":"string","createTime":"2017-06-05T05:46:51.106Z","isChild":true}]
     */

    private String objectId;
    private boolean isPraise;
    private int praiseCount;
    private int commentCount;
    private List<PraiseListBean> praiseList;
    private List<CommentListBean> commentList;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public boolean isIsPraise() {
        return isPraise;
    }

    public void setIsPraise(boolean isPraise) {
        this.isPraise = isPraise;
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

    public List<PraiseListBean> getPraiseList() {
        return praiseList;
    }

    public void setPraiseList(List<PraiseListBean> praiseList) {
        this.praiseList = praiseList;
    }

    public List<CommentListBean> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentListBean> commentList) {
        this.commentList = commentList;
    }

    public static class PraiseListBean {
        /**
         * praiseTime : 2017-06-05T05:46:51.106Z
         * userId : string
         * name : string
         * avatar : string
         * content : string
         */

        private String praiseTime;
        private String userId;
        private String name;
        private String avatar;
        private String content;

        public String getPraiseTime() {
            return praiseTime;
        }

        public void setPraiseTime(String praiseTime) {
            this.praiseTime = praiseTime;
        }

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

    public static class CommentListBean {
        /**
         * commentId : string
         * parentId : string
         * toUserId : string
         * toUser : {"userId":"string","name":"string","avatar":"string","content":"string"}
         * user : {"userId":"string","name":"string","avatar":"string","content":"string"}
         * content : string
         * toCommentContent : string
         * createTime : 2017-06-05T05:46:51.106Z
         * isChild : true
         */

        private String commentId;
        private String parentId;
        private String toUserId;
        private ToUserBean toUser;
        private UserBean user;
        private String content;
        private String toCommentContent;
        private String createTime;
        private boolean isChild;

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

        public String getToCommentContent() {
            return toCommentContent;
        }

        public void setToCommentContent(String toCommentContent) {
            this.toCommentContent = toCommentContent;
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

        public static class ToUserBean {
            /**
             * userId : string
             * name : string
             * avatar : string
             * content : string
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

        public static class UserBean {
            /**
             * userId : string
             * name : string
             * avatar : string
             * content : string
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
    }
}
