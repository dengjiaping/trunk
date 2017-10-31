package com.histudent.jwsoft.histudent.body.mine.model;

import com.histudent.jwsoft.histudent.commen.bean.AtUserModel;
import com.histudent.jwsoft.histudent.commen.bean.TopicModel;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/8/11.
 */
public class UserTimeModel {

    /**
     * cursor : 0
     * startTime : 0
     * items : [{"actId":"string","ownerId":"string","ownerType":0,"ownerName":"string","activityItemKey":"string","userId":"string","userName":"string","userAvatar":"string","sourceId":"string","isPrivate":0,"actionInfo":"string","title":"string","summary":"string","cover":"string","imageCount":0,"images":[{"imgId":"string","name":"string","thumbnailUrl":"string","bigSizeUrl":"string"}],"comments":[{"commentId":"string","parentId":"string","toUserId":"string","toUser":{"userId":"string","name":"string","avatar":"string","content":"string"},"user":{"userId":"string","name":"string","avatar":"string","content":"string"},"content":"string","toCommentContent":"string","createTime":"2017-05-04T03:49:56.935Z","isChild":true}],"lastUpdateTime":"2017-05-04T03:49:56.935Z","location":"string","longitude":0,"latitude":0,"praiseCount":0,"praiseUsers":[{"userId":"string","name":"string","avatar":"string","content":"string"}],"commentCount":0,"forwardCount":0,"orderByNum":0,"isFollowCreator":true,"isPraised":true,"isSpecialFollow":true,"htmlUrl":"string","multiMediaType":0,"className":"string","activityChannel":0,"isRecommand":true}]
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
         * actId : string
         * ownerId : string
         * ownerType : 0
         * ownerName : string
         * activityItemKey : string
         * userId : string
         * userName : string
         * userAvatar : string
         * sourceId : string
         * isPrivate : 0
         * actionInfo : string
         * title : string
         * summary : string
         * cover : string
         * imageCount : 0
         * images : [{"imgId":"string","name":"string","thumbnailUrl":"string","bigSizeUrl":"string"}]
         * comments : [{"commentId":"string","parentId":"string","toUserId":"string","toUser":{"userId":"string","name":"string","avatar":"string","content":"string"},"user":{"userId":"string","name":"string","avatar":"string","content":"string"},"content":"string","toCommentContent":"string","createTime":"2017-05-04T03:49:56.935Z","isChild":true}]
         * lastUpdateTime : 2017-05-04T03:49:56.935Z
         * location : string
         * longitude : 0
         * latitude : 0
         * praiseCount : 0
         * praiseUsers : [{"userId":"string","name":"string","avatar":"string","content":"string"}]
         * commentCount : 0
         * forwardCount : 0
         * orderByNum : 0
         * isFollowCreator : true
         * isPraised : true
         * isSpecialFollow : true
         * htmlUrl : string
         * multiMediaType : 0
         * className : string
         * activityChannel : 0
         * isRecommand : true
         */

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
        private int imageCount;
        private String lastUpdateTime;
        private String location;
        private double longitude;
        private double latitude;
        private int praiseCount;
        private int commentCount;
        private int forwardCount;
        private int orderByNum;
        private boolean isFollowCreator;
        private boolean isPraised;
        private boolean isSpecialFollow;
        private String htmlUrl;
        private int multiMediaType;
        private String className;
        private int activityChannel;
        private boolean isRecommand;
        private String videoId;
        private List<ImagesBean> images;
        private List<CommentsBean> comments;
        private List<PraiseUsersBean> praiseUsers;
        private List<TopicModel> topicList;
        private List<AtUserModel>  atUserList;

        public List<TopicModel> getTopicList() {
            return topicList;
        }

        public void setTopicList(List<TopicModel> topicList) {
            this.topicList = topicList;
        }

        public List<AtUserModel> getAtUserList() {
            return atUserList;
        }

        public void setAtUserList(List<AtUserModel> atUserList) {
            this.atUserList = atUserList;
        }

        public String getVideoId() {
            return videoId;
        }

        public void setVideoId(String videoId) {
            this.videoId = videoId;
        }

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

        public int getImageCount() {
            return imageCount;
        }

        public void setImageCount(int imageCount) {
            this.imageCount = imageCount;
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

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(int longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(int latitude) {
            this.latitude = latitude;
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

        public boolean isIsFollowCreator() {
            return isFollowCreator;
        }

        public void setIsFollowCreator(boolean isFollowCreator) {
            this.isFollowCreator = isFollowCreator;
        }

        public boolean isIsPraised() {
            return isPraised;
        }

        public void setIsPraised(boolean isPraised) {
            this.isPraised = isPraised;
        }

        public boolean isIsSpecialFollow() {
            return isSpecialFollow;
        }

        public void setIsSpecialFollow(boolean isSpecialFollow) {
            this.isSpecialFollow = isSpecialFollow;
        }

        public String getHtmlUrl() {
            return htmlUrl;
        }

        public void setHtmlUrl(String htmlUrl) {
            this.htmlUrl = htmlUrl;
        }

        public int getMultiMediaType() {
            return multiMediaType;
        }

        public void setMultiMediaType(int multiMediaType) {
            this.multiMediaType = multiMediaType;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public int getActivityChannel() {
            return activityChannel;
        }

        public void setActivityChannel(int activityChannel) {
            this.activityChannel = activityChannel;
        }

        public boolean isIsRecommand() {
            return isRecommand;
        }

        public void setIsRecommand(boolean isRecommand) {
            this.isRecommand = isRecommand;
        }

        public List<ImagesBean> getImages() {
            return images;
        }

        public void setImages(List<ImagesBean> images) {
            this.images = images;
        }

        public List<CommentsBean> getComments() {
            return comments;
        }

        public void setComments(List<CommentsBean> comments) {
            this.comments = comments;
        }

        public List<PraiseUsersBean> getPraiseUsers() {
            return praiseUsers;
        }

        public void setPraiseUsers(List<PraiseUsersBean> praiseUsers) {
            this.praiseUsers = praiseUsers;
        }

        public static class ImagesBean {
            /**
             * imgId : string
             * name : string
             * thumbnailUrl : string
             * bigSizeUrl : string
             */

            private String imgId;
            private String name;
            private String thumbnailUrl;
            private String bigSizeUrl;

            public String getImgId() {
                return imgId;
            }

            public void setImgId(String imgId) {
                this.imgId = imgId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getThumbnailUrl() {
                return thumbnailUrl;
            }

            public void setThumbnailUrl(String thumbnailUrl) {
                this.thumbnailUrl = thumbnailUrl;
            }

            public String getBigSizeUrl() {
                return bigSizeUrl;
            }

            public void setBigSizeUrl(String bigSizeUrl) {
                this.bigSizeUrl = bigSizeUrl;
            }
        }

        public static class CommentsBean {
            /**
             * commentId : string
             * parentId : string
             * toUserId : string
             * toUser : {"userId":"string","name":"string","avatar":"string","content":"string"}
             * user : {"userId":"string","name":"string","avatar":"string","content":"string"}
             * content : string
             * toCommentContent : string
             * createTime : 2017-05-04T03:49:56.935Z
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

        public static class PraiseUsersBean {
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
