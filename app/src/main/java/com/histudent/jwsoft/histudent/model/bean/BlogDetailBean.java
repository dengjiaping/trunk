package com.histudent.jwsoft.histudent.model.bean;

import java.util.List;

/**
 * Created by huyg on 2017/9/11.
 */
//{"data":{"actId":"aa6160b9-38de-41e4-add5-369d4f84b75d","ownerId":"b87664a1-94ad-4ac5-9325-cd2123c90010","ownerType":1,"ownerName":"huyg","activityItemKey":"CreateBlog","userId":"b87664a1-94ad-4ac5-9325-cd2123c90010","userName":"huyg","userAvatar":"http://img.hitongx.com/UploadFiles/UploadAvatar/UserSystemAvatar/cn_zodiac_08.png@100w_100h_1e_1c_2o_50-1ci.png","sourceId":"b977a26f-4679-4c63-9e56-d75659a2da8a","isPrivate":0,"actionInfo":"公开发布日志","title":"风风光光","summary":"见客户过<a href=\"javascript:linkTo('UserHome','5c8c0576-708e-4283-9a0e-6650b6695c4e');\" target=\"_blank\" title=\"妥协\">@妥协</a> 看靠近你","content":"见客户过@妥协<img src=\"http://img.hitongx.com/UploadFiles/twitter/20170911/1053f1eb-6156-4f24-b3ea-b384a35d8229.png@1080w_1920h_1e_1l_2o.png.png\" alt=\"image0\"><img src=\"http://img.hitongx.com/UploadFiles/twitter/20170911/e7e935b2-32e6-4c6f-aa6a-80b58a83d3e6.png@1080w_1920h_1e_1l_2o.png.png\" alt=\"image0\">看靠近你","cover":"","imageCount":0,"images":[],"comments":[],"lastUpdateTime":"2017-09-11 14:09:07","location":"杭州市拱墅区","longitude":120.110000,"latitude":30.330000,"praiseCount":0,"praiseUsers":[],"commentCount":0,"forwardCount":0,"orderByNum":0,"isFollowCreator":false,"isPraised":false,"isSpecialFollow":false,"htmlUrl":"http://192.168.0.252:8020/app/?token=995ddc71cd9542e8908beac0e98b3079#/activitydetail?actId=aa6160b9-38de-41e4-add5-369d4f84b75d","multiMediaType":0,"activityChannel":0,"isRecommand":false,"rank":0,"isDelete":false},"ret":1,"msg":"","excuteTime":73.454000000000008}
public class BlogDetailBean {
    /**
     * actId : aa6160b9-38de-41e4-add5-369d4f84b75d
     * ownerId : b87664a1-94ad-4ac5-9325-cd2123c90010
     * ownerType : 1
     * ownerName : huyg
     * activityItemKey : CreateBlog
     * userId : b87664a1-94ad-4ac5-9325-cd2123c90010
     * userName : huyg
     * userAvatar : http://img.hitongx.com/UploadFiles/UploadAvatar/UserSystemAvatar/cn_zodiac_08.png@100w_100h_1e_1c_2o_50-1ci.png
     * sourceId : b977a26f-4679-4c63-9e56-d75659a2da8a
     * isPrivate : 0
     * actionInfo : 公开发布日志
     * title : 风风光光
     * summary : 见客户过<a href="javascript:linkTo('UserHome','5c8c0576-708e-4283-9a0e-6650b6695c4e');" target="_blank" title="妥协">@妥协</a> 看靠近你
     * content : 见客户过@妥协<img src="http://img.hitongx.com/UploadFiles/twitter/20170911/1053f1eb-6156-4f24-b3ea-b384a35d8229.png@1080w_1920h_1e_1l_2o.png.png" alt="image0"><img src="http://img.hitongx.com/UploadFiles/twitter/20170911/e7e935b2-32e6-4c6f-aa6a-80b58a83d3e6.png@1080w_1920h_1e_1l_2o.png.png" alt="image0">看靠近你
     * cover :
     * imageCount : 0
     * images : []
     * comments : []
     * lastUpdateTime : 2017-09-11 14:09:07
     * location : 杭州市拱墅区
     * longitude : 120.11
     * latitude : 30.33
     * praiseCount : 0
     * praiseUsers : []
     * commentCount : 0
     * forwardCount : 0
     * orderByNum : 0
     * isFollowCreator : false
     * isPraised : false
     * isSpecialFollow : false
     * htmlUrl : http://192.168.0.252:8020/app/?token=995ddc71cd9542e8908beac0e98b3079#/activitydetail?actId=aa6160b9-38de-41e4-add5-369d4f84b75d
     * multiMediaType : 0
     * activityChannel : 0
     * isRecommand : false
     * rank : 0
     * isDelete : false
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
    private String content;
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
    private int activityChannel;
    private boolean isRecommand;
    private int rank;
    private boolean isDelete;
    private List<?> images;
    private List<?> comments;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
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

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean isIsDelete() {
        return isDelete;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }

    public List<?> getImages() {
        return images;
    }

    public void setImages(List<?> images) {
        this.images = images;
    }

    public List<?> getComments() {
        return comments;
    }

    public void setComments(List<?> comments) {
        this.comments = comments;
    }

    public List<?> getPraiseUsers() {
        return praiseUsers;
    }

    public void setPraiseUsers(List<?> praiseUsers) {
        this.praiseUsers = praiseUsers;
    }
}
