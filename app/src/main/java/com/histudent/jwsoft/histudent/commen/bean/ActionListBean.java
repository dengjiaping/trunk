package com.histudent.jwsoft.histudent.commen.bean;

import com.histudent.jwsoft.histudent.body.mine.model.CommentsModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/8/10.
 * 个人页面时间轴
 */
public class ActionListBean implements Serializable {


    /**
     * actId : 5b3288b4-4e67-4cd3-9d67-6c9c943aba63
     * ownerId : 2468d967-a754-4535-9ed2-1a2e4909dd75
     * ownerType : 1
     * ownerName : 王伟
     * activityItemKey : UploadPhoto
     * userId : 2468d967-a754-4535-9ed2-1a2e4909dd75
     * userName : admin
     * userAvatar : http://img.hitongx.com/UploadFiles/Album/20160816/91f64d6f-d24e-40b8-8b11-de924e2c9a89.jpg@_60w_60h_1e_1c_30-1ci.jpg
     * sourceId : ab83097e-12d1-4815-98e3-0555eb621b34
     * isPrivate : 0
     * actionInfo : 公开发布照片
     * summary : 234234242
     * images : [{"name":"12906030_124355855000_2.png","thumbnailUrl":"http://img.hitongx.com/UploadFiles/Album/20160602/a645b427-e50e-4426-8d71-95d5674f9a4a.png@_310w_240h_1e_1c.png","bigSizeUrl":"http://img.hitongx.com/UploadFiles/Album/20160602/a645b427-e50e-4426-8d71-95d5674f9a4a.png"},{"name":"10.jpg","thumbnailUrl":"http://img.hitongx.com/UploadFiles/Album/20160624/7ba4a0d9-b7ac-43e6-b216-b363468abb71.jpg@_310w_240h_1e_1c.jpg","bigSizeUrl":"http://img.hitongx.com/UploadFiles/Album/20160624/7ba4a0d9-b7ac-43e6-b216-b363468abb71.jpg"},{"name":"146114","thumbnailUrl":"http://img.hitongx.com/UploadFiles/Album/20160624/456e8134-9179-44e0-a5df-6f610ef33103.jpg@_310w_240h_1e_1c.jpg","bigSizeUrl":"http://img.hitongx.com/UploadFiles/Album/20160624/456e8134-9179-44e0-a5df-6f610ef33103.jpg"},{"name":"14735.jpg","thumbnailUrl":"http://img.hitongx.com/UploadFiles/Album/20160624/246f179c-ba96-4d80-8941-3d5bccd11685.jpg@_310w_240h_1e_1c.jpg","bigSizeUrl":"http://img.hitongx.com/UploadFiles/Album/20160624/246f179c-ba96-4d80-8941-3d5bccd11685.jpg"},{"name":"22669692.d736e7.jpg","thumbnailUrl":"http://img.hitongx.com/UploadFiles/Album/20160624/eda62f1b-0ab1-428e-90c4-7f6b3cae0dda.jpg@_310w_240h_1e_1c.jpg","bigSizeUrl":"http://img.hitongx.com/UploadFiles/Album/20160624/eda62f1b-0ab1-428e-90c4-7f6b3cae0dda.jpg"},{"name":"916693.jpg","thumbnailUrl":"http://img.hitongx.com/UploadFiles/Album/20160624/6434c1c0-8462-4855-87dd-e2bfd25fa824.jpg@_310w_240h_1e_1c.jpg","bigSizeUrl":"http://img.hitongx.com/UploadFiles/Album/20160624/6434c1c0-8462-4855-87dd-e2bfd25fa824.jpg"},{"name":"8718367adab44aed3a397e03b51c8701a08bfbe6.jpg","thumbnailUrl":"http://img.hitongx.com/UploadFiles/Album/20160624/82f0552b-a78b-4aa2-9783-bc45c5ee15fb.jpg@_310w_240h_1e_1c.jpg","bigSizeUrl":"http://img.hitongx.com/UploadFiles/Album/20160624/82f0552b-a78b-4aa2-9783-bc45c5ee15fb.jpg"},{"name":"060828381f30e924cb210b514a086e061c95f7cd.jpg","thumbnailUrl":"http://img.hitongx.com/UploadFiles/Album/20160624/c014373e-b041-42b3-bad4-d5f7273a15ba.jpg@_310w_240h_1e_1c.jpg","bigSizeUrl":"http://img.hitongx.com/UploadFiles/Album/20160624/c014373e-b041-42b3-bad4-d5f7273a15ba.jpg"},{"name":"20140504075942_RPxYf.jpeg","thumbnailUrl":"http://img.hitongx.com/UploadFiles/Album/20160624/454100cf-72b9-4efa-9cda-514ff0e4f63f.jpeg@_310w_240h_1e_1c.src","bigSizeUrl":"http://img.hitongx.com/UploadFiles/Album/20160624/454100cf-72b9-4efa-9cda-514ff0e4f63f.jpeg"}]
     * lastUpdateTime : 2016-08-11 16:15:37
     * location :
     * praiseCount : 0
     * praiseUsers : [{"userId":"8f29ac2a-8de3-43d5-a101-3259abc7054c","name":"俞春杭","avatar":"http://img.hitongx.com/UploadFiles/UploadAvatar/20160727/41c79cc6-4bf0-412f-8de9-4750ee4db472.png@_60w_60h_1e_1c_30-1ci.png"},{"userId":"2468d967-a754-4535-9ed2-1a2e4909dd75","name":"王伟","avatar":"http://img.hitongx.com/UploadFiles/Album/20160816/91f64d6f-d24e-40b8-8b11-de924e2c9a89.jpg@_60w_60h_1e_1c_30-1ci.jpg"}]
     * commentCount : 0
     * forwardCount : 0
     * orderByNum : 0
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
    private String cover;
    private int isPrivate;
    private int privacyStatus;
    private String actionInfo;
    private String summary;
    private String lastUpdateTime;
    private String location;
    private String createTime;
    private int praiseCount;
    private int commentCount;
    private int forwardCount;
    private int orderByNum;
    private int visitNum;
    public String ownerCategoryName;
    private String title;
    private boolean isSpecialFollow;
    private boolean isCare;
    private boolean isShield;
    private String htmlUrl;
    private int activityChannel;
    private String videoId;
    private int multiMediaType;
    private boolean isForward;
    private boolean hasPhoto;
    private boolean hasVideo;
    private boolean hasMusic;
    private int imageCount;
    private double longitude;
    private double latitude;

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

    public int getImageCount() {
        return imageCount;
    }

    public void setImageCount(int imageCount) {
        this.imageCount = imageCount;
    }

    public boolean isIsForward() {
        return isForward;
    }

    public void setIsForward(boolean isForward) {
        this.isForward = isForward;
    }

    public boolean isHasPhoto() {
        return hasPhoto;
    }

    public void setHasPhoto(boolean hasPhoto) {
        this.hasPhoto = hasPhoto;
    }

    public boolean isHasVideo() {
        return hasVideo;
    }

    public void setHasVideo(boolean hasVideo) {
        this.hasVideo = hasVideo;
    }

    public boolean isHasMusic() {
        return hasMusic;
    }

    public void setHasMusic(boolean hasMusic) {
        this.hasMusic = hasMusic;
    }

    public int getMultiMediaType() {
        return multiMediaType;
    }

    public void setMultiMediaType(int multiMediaType) {
        this.multiMediaType = multiMediaType;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public int getActivityChannel() {
        return activityChannel;
    }

    public void setActivityChannel(int activityChannel) {
        this.activityChannel = activityChannel;
    }

    private String ownerList;

    public String getOwnerList() {
        return ownerList;
    }

    public void setOwnerList(String ownerList) {
        this.ownerList = ownerList;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public boolean isIsCare() {
        return isCare;
    }

    public void setIsCare(boolean care) {
        isCare = care;
    }

    public boolean isIsShield() {
        return isShield;
    }

    public void setIsShield(boolean shield) {
        isShield = shield;
    }

    public boolean isIsSpecialFollow() {
        return isSpecialFollow;
    }

    public void setIsSpecialFollow(boolean specialFollow) {
        isSpecialFollow = specialFollow;
    }

    private List<CommentsModel> itemsBeen;

    public List<CommentsModel> getItemsBeen() {
        return itemsBeen;
    }

    public void setItemsBeen(List<CommentsModel> itemsBeen) {
        this.itemsBeen = itemsBeen;
    }

    private boolean isPraised;

    public boolean getIsPraised() {
        return isPraised;
    }

    public void setIsPraised(boolean isPraised) {
        this.isPraised = isPraised;
    }

    private boolean isRecommand;

    public boolean getIsRecommand() {
        return isRecommand;
    }

    public void setIsRecommand(boolean isPraised) {
        this.isRecommand = isPraised;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getVisitNum() {
        return visitNum;
    }

    public void setVisitNum(int visitNum) {
        this.visitNum = visitNum;
    }

    public String getOwnerCategoryName() {
        return ownerCategoryName;
    }

    public void setOwnerCategoryName(String ownerCategoryName) {
        this.ownerCategoryName = ownerCategoryName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * name : 12906030_124355855000_2.png
     * thumbnailUrl : http://img.hitongx.com/UploadFiles/Album/20160602/a645b427-e50e-4426-8d71-95d5674f9a4a.png@_310w_240h_1e_1c.png
     * bigSizeUrl : http://img.hitongx.com/UploadFiles/Album/20160602/a645b427-e50e-4426-8d71-95d5674f9a4a.png
     */


    private List<ImagesBean> images;

    public int getPrivacyStatus() {
        return privacyStatus;
    }

    public void setPrivacyStatus(int privacyStatus) {
        this.privacyStatus = privacyStatus;
    }

    /**
     * userId : 8f29ac2a-8de3-43d5-a101-3259abc7054c
     * name : 俞春杭
     * avatar : http://img.hitongx.com/UploadFiles/UploadAvatar/20160727/41c79cc6-4bf0-412f-8de9-4750ee4db472.png@_60w_60h_1e_1c_30-1ci.png
     */


    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    private List<PraiseUsersBean> praiseUsers;

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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public List<ImagesBean> getImages() {
        return images;
    }

    public void setImages(List<ImagesBean> images) {
        this.images = images;
    }

    public List<PraiseUsersBean> getPraiseUsers() {
        return praiseUsers;
    }

    public void setPraiseUsers(List<PraiseUsersBean> praiseUsers) {
        this.praiseUsers = praiseUsers;
    }

    public static class ImagesBean implements Serializable {
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

    public static class PraiseUsersBean implements Serializable {
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

