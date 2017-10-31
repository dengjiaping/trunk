package com.histudent.jwsoft.histudent.body.mine.model;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/8/10.
 * 个人页面时间轴
 */
public class TimeModel {

    private String actId;// (string, optional): 动态Id ,
    private String ownerId;// (string, optional): 拥有者Id ,
    private int ownerType;// (integer, optional): 拥有者类型，（用户、班级、社群） ,
    private String ownerName;// (string, optional): 拥有者名称，（用户、班级、社群） ,
    private String activityItemKey;// (string, optional): 动态项目标识 ,
    private String userId;// (string, optional): 动态操作人的用户Id，也就是触发动作的人 ,
    private String userName;//(string, optional): 动态操作人的姓名 ,
    private String userAvatar;// (string, optional): 动态操作人的头像 ,
    private String sourceId;// (string, optional): 动态源内容id（例如：日志动态的日志Id） ,
    private int isPrivate;// (integer, optional): 隐私情况 ,
    private String actionInfo;// (string, optional): 操作描述信息(例如：公开发布日志) ,
    private String title;// (string, optional): 标题 ,
    private String summary;// (string, optional): 文字摘要信息 ,
    private String cover;// (string, optional): 封面图片Url，通常日志、视频、链接会有封面 ,
    private List<TimeImageModel> images;//(Array[ImageInfo], optional)包含的图片列表,
    private List<UserTimeCommentModel> comments;//(Array[CommentModel], optional)评论（初始显示第1页即最新10条评论，剩余的评论需要调用“获取动态评论”接口来获取） ,
    private String lastUpdateTime;// (string, optional): 最后更新时间 ,
    private String location;// (string, optional): 发生该动态的地理位置或班级 ,
    private int praiseCount;// (integer, optional): 点赞数 ,
    private List<TimePraiseUsersModel> praiseUsers;//(Array[UserBaseInfo], optional)点赞的人（最近10人） ,
    private int commentCount;// (integer, optional): 评论数 ,
    private int forwardCount;// (integer, optional): 转发数 ,
    private int orderByNum;// (integer, optional): 特别关注人排序用
    private boolean isPraised;
    private boolean isSpecialFollow;
    private boolean isFollowCreator;
    private String htmlUrl;
    private int activityChannel;

    public int getActivityChannel() {
        return activityChannel;
    }

    public void setActivityChannel(int activityChannel) {
        this.activityChannel = activityChannel;
    }

    public boolean isIsFollowCreator() {
        return isFollowCreator;
    }

    public void setIsFollowCreator(boolean followCreator) {
        isFollowCreator = followCreator;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public boolean isIsSpecialFollow() {
        return isSpecialFollow;
    }

    public void setIsSpecialFollow(boolean specialFollow) {
        isSpecialFollow = specialFollow;
    }

    public boolean getIsPraised() {
        return isPraised;
    }

    public void setIsPraised(boolean isPraised) {
        this.isPraised = isPraised;
    }

    public List<TimePraiseUsersModel> getPraiseUsers() {
        return praiseUsers;
    }

    public void setPraiseUsers(List<TimePraiseUsersModel> praiseUsers) {
        this.praiseUsers = praiseUsers;
    }

    public List<TimeImageModel> getImages() {
        return images;
    }

    public void setImages(List<TimeImageModel> images) {
        this.images = images;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
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

    public List<UserTimeCommentModel> getComments() {
        return comments;
    }

    public void setComments(List<UserTimeCommentModel> comments) {
        this.comments = comments;
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

    public class UserTimeCommentModel {
        private String commentId;//  (string, optional): 评论id(主键) ,
        private String parentId;//  (string, optional): 父id ,
        private String toUserId;//  (string, optional): 回复的用户 ,
        private String toUser;//  (UserBaseInfo, optional): 回复用户信息 ,
        private String user;//  (UserBaseInfo, optional): 评论者 ,
        private String content;//  (string, optional): 评论的内容 ,
        private String createTime;//  (string, optional): 评论时间

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

        public String getToUser() {
            return toUser;
        }

        public void setToUser(String toUser) {
            this.toUser = toUser;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
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
    }

}


