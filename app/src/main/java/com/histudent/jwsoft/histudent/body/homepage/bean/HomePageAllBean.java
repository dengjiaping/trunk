package com.histudent.jwsoft.histudent.body.homepage.bean;

import com.histudent.jwsoft.histudent.commen.bean.AtUserModel;
import com.histudent.jwsoft.histudent.commen.bean.TopicModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liuguiyu-pc on 2017/4/21.
 */

public class HomePageAllBean implements Serializable {

    /**
     * data : {"slideShows":[{"id":"string","category":0,"name":"string","link":"string","logo":"string","intro":"string","banner":"string","isHomeShow":true,"startTime":"2017-04-26T00:50:54.823Z","endTime":"2017-04-26T00:50:54.823Z","createUser":"string","createTime":"2017-04-26T00:50:54.823Z","isDeleted":true,"status":true,"sort":0}],"recommendUsers":{"itemCount":0,"pageCount":0,"items":[{"userType":1,"userId":"string","name":"string","avatar":"string"}]},"recommendClasses":{"itemCount":0,"pageCount":0,"items":[{"classesId":"string","classesNumber":"string","schoolName":"string","gradeName":"string","name":"string","logo":"string"}]},"recommendTags":{"itemCount":0,"pageCount":0,"items":[{"tagId":"string","img":"string","tagName":"string","tagDescription":"string","ownerCount":0}]},"recommendActivities":{"cursor":0,"startTime":0,"items":[{"actId":"string","ownerId":"string","ownerType":0,"ownerName":"string","activityItemKey":"string","userId":"string","userName":"string","userAvatar":"string","sourceId":"string","isPrivate":0,"actionInfo":"string","title":"string","summary":"string","cover":"string","images":[{"imgId":"string","name":"string","thumbnailUrl":"string","bigSizeUrl":"string"}],"comments":[{"commentId":"string","parentId":"string","toUserId":"string","toUser":{"userId":"string","name":"string","avatar":"string"},"user":{"userId":"string","name":"string","avatar":"string"},"content":"string","toCommentContent":"string","createTime":"2017-04-26T00:50:54.824Z","isChild":true}],"lastUpdateTime":"2017-04-26T00:50:54.824Z","location":"string","longitude":0,"latitude":0,"praiseCount":0,"praiseUsers":[{"userId":"string","name":"string","avatar":"string"}],"commentCount":0,"forwardCount":0,"orderByNum":0,"isFollowCreator":true,"isPraised":true,"isSpecialFollow":true,"htmlUrl":"string","multiMediaType":0,"className":"string","activityChannel":0}],"nextTime":0}}
     * ret : 0
     * msg : string
     * excuteTime : 0
     */

    private DataBean data;
    private int ret;
    private String msg;
    private int excuteTime;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getExcuteTime() {
        return excuteTime;
    }

    public void setExcuteTime(int excuteTime) {
        this.excuteTime = excuteTime;
    }

    public static class DataBean {
        /**
         * slideShows : [{"id":"string","category":0,"name":"string","link":"string","logo":"string","intro":"string","banner":"string","isHomeShow":true,"startTime":"2017-04-26T00:50:54.823Z","endTime":"2017-04-26T00:50:54.823Z","createUser":"string","createTime":"2017-04-26T00:50:54.823Z","isDeleted":true,"status":true,"sort":0}]
         * recommendUsers : {"itemCount":0,"pageCount":0,"items":[{"userType":1,"userId":"string","name":"string","avatar":"string"}]}
         * recommendClasses : {"itemCount":0,"pageCount":0,"items":[{"classesId":"string","classesNumber":"string","schoolName":"string","gradeName":"string","name":"string","logo":"string"}]}
         * recommendTags : {"itemCount":0,"pageCount":0,"items":[{"tagId":"string","img":"string","tagName":"string","tagDescription":"string","ownerCount":0}]}
         * recommendActivities : {"cursor":0,"startTime":0,"items":[{"actId":"string","ownerId":"string","ownerType":0,"ownerName":"string","activityItemKey":"string","userId":"string","userName":"string","userAvatar":"string","sourceId":"string","isPrivate":0,"actionInfo":"string","title":"string","summary":"string","cover":"string","images":[{"imgId":"string","name":"string","thumbnailUrl":"string","bigSizeUrl":"string"}],"comments":[{"commentId":"string","parentId":"string","toUserId":"string","toUser":{"userId":"string","name":"string","avatar":"string"},"user":{"userId":"string","name":"string","avatar":"string"},"content":"string","toCommentContent":"string","createTime":"2017-04-26T00:50:54.824Z","isChild":true}],"lastUpdateTime":"2017-04-26T00:50:54.824Z","location":"string","longitude":0,"latitude":0,"praiseCount":0,"praiseUsers":[{"userId":"string","name":"string","avatar":"string"}],"commentCount":0,"forwardCount":0,"orderByNum":0,"isFollowCreator":true,"isPraised":true,"isSpecialFollow":true,"htmlUrl":"string","multiMediaType":0,"className":"string","activityChannel":0}],"nextTime":0}
         */

        private RecommendUsersBean recommendUsers;
        private RecommendClassesBean recommendClasses;
        private RecommendTagsBean recommendTags;
        private RecommendActivitiesBean recommendActivities;
        private List<SlideShowsBean> slideShows;

        public RecommendUsersBean getRecommendUsers() {
            return recommendUsers;
        }

        public void setRecommendUsers(RecommendUsersBean recommendUsers) {
            this.recommendUsers = recommendUsers;
        }

        public RecommendClassesBean getRecommendClasses() {
            return recommendClasses;
        }

        public void setRecommendClasses(RecommendClassesBean recommendClasses) {
            this.recommendClasses = recommendClasses;
        }

        public RecommendTagsBean getRecommendTags() {
            return recommendTags;
        }

        public void setRecommendTags(RecommendTagsBean recommendTags) {
            this.recommendTags = recommendTags;
        }

        public RecommendActivitiesBean getRecommendActivities() {
            return recommendActivities;
        }

        public void setRecommendActivities(RecommendActivitiesBean recommendActivities) {
            this.recommendActivities = recommendActivities;
        }

        public List<SlideShowsBean> getSlideShows() {
            return slideShows;
        }

        public void setSlideShows(List<SlideShowsBean> slideShows) {
            this.slideShows = slideShows;
        }

        public static class RecommendUsersBean {
            /**
             * itemCount : 0
             * pageCount : 0
             * items : [{"userType":1,"userId":"string","name":"string","avatar":"string"}]
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
                 * userType : 1
                 * userId : string
                 * name : string
                 * avatar : string
                 */

                private int userType;
                private String userId;
                private String name;
                private String avatar;

                public int getUserType() {
                    return userType;
                }

                public void setUserType(int userType) {
                    this.userType = userType;
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
            }
        }

        public static class RecommendClassesBean {
            /**
             * itemCount : 0
             * pageCount : 0
             * items : [{"classesId":"string","classesNumber":"string","schoolName":"string","gradeName":"string","name":"string","logo":"string"}]
             */

            private int itemCount;
            private int pageCount;
            private List<ItemsBeanX> items;

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

            public List<ItemsBeanX> getItems() {
                return items;
            }

            public void setItems(List<ItemsBeanX> items) {
                this.items = items;
            }

            public static class ItemsBeanX {
                /**
                 * classesId : string
                 * classesNumber : string
                 * schoolName : string
                 * gradeName : string
                 * name : string
                 * logo : string
                 */

                private String classesId;
                private String classesNumber;
                private String schoolName;
                private String gradeName;
                private String name;
                private String logo;

                public String getClassesId() {
                    return classesId;
                }

                public void setClassesId(String classesId) {
                    this.classesId = classesId;
                }

                public String getClassesNumber() {
                    return classesNumber;
                }

                public void setClassesNumber(String classesNumber) {
                    this.classesNumber = classesNumber;
                }

                public String getSchoolName() {
                    return schoolName;
                }

                public void setSchoolName(String schoolName) {
                    this.schoolName = schoolName;
                }

                public String getGradeName() {
                    return gradeName;
                }

                public void setGradeName(String gradeName) {
                    this.gradeName = gradeName;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getLogo() {
                    return logo;
                }

                public void setLogo(String logo) {
                    this.logo = logo;
                }
            }
        }

        public static class RecommendTagsBean {
            /**
             * itemCount : 0
             * pageCount : 0
             * items : [{"tagId":"string","img":"string","tagName":"string","tagDescription":"string","ownerCount":0}]
             */

            private int itemCount;
            private int pageCount;
            private List<ItemsBeanXX> items;

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

            public List<ItemsBeanXX> getItems() {
                return items;
            }

            public void setItems(List<ItemsBeanXX> items) {
                this.items = items;
            }

            public static class ItemsBeanXX implements Serializable {
                /**
                 * tagId : string
                 * img : string
                 * tagName : string
                 * tagDescription : string
                 * ownerCount : 0
                 */

                private String tagId;
                private String featuredImage;
                private String tagName;
                private String tagDescription;
                private int ownerCount;

                public String getTagId() {
                    return tagId;
                }

                public void setTagId(String tagId) {
                    this.tagId = tagId;
                }

                public String getFeaturedImage() {
                    return featuredImage;
                }

                public void setFeaturedImage(String featuredImage) {
                    this.featuredImage = featuredImage;
                }

                public String getTagName() {
                    return tagName;
                }

                public void setTagName(String tagName) {
                    this.tagName = tagName;
                }

                public String getTagDescription() {
                    return tagDescription;
                }

                public void setTagDescription(String tagDescription) {
                    this.tagDescription = tagDescription;
                }

                public int getOwnerCount() {
                    return ownerCount;
                }

                public void setOwnerCount(int ownerCount) {
                    this.ownerCount = ownerCount;
                }
            }
        }

        public static class RecommendActivitiesBean {
            /**
             * cursor : 0
             * startTime : 0
             * items : [{"actId":"string","ownerId":"string","ownerType":0,"ownerName":"string","activityItemKey":"string","userId":"string","userName":"string","userAvatar":"string","sourceId":"string","isPrivate":0,"actionInfo":"string","title":"string","summary":"string","cover":"string","images":[{"imgId":"string","name":"string","thumbnailUrl":"string","bigSizeUrl":"string"}],"comments":[{"commentId":"string","parentId":"string","toUserId":"string","toUser":{"userId":"string","name":"string","avatar":"string"},"user":{"userId":"string","name":"string","avatar":"string"},"content":"string","toCommentContent":"string","createTime":"2017-04-26T00:50:54.824Z","isChild":true}],"lastUpdateTime":"2017-04-26T00:50:54.824Z","location":"string","longitude":0,"latitude":0,"praiseCount":0,"praiseUsers":[{"userId":"string","name":"string","avatar":"string"}],"commentCount":0,"forwardCount":0,"orderByNum":0,"isFollowCreator":true,"isPraised":true,"isSpecialFollow":true,"htmlUrl":"string","multiMediaType":0,"className":"string","activityChannel":0}]
             * nextTime : 0
             */

            private int cursor;
            private int startTime;
            private int nextTime;
            private List<ItemsBeanXXX> items;

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

            public List<ItemsBeanXXX> getItems() {
                return items;
            }

            public void setItems(List<ItemsBeanXXX> items) {
                this.items = items;
            }

            public static class ItemsBeanXXX {
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
                 * images : [{"imgId":"string","name":"string","thumbnailUrl":"string","bigSizeUrl":"string"}]
                 * comments : [{"commentId":"string","parentId":"string","toUserId":"string","toUser":{"userId":"string","name":"string","avatar":"string"},"user":{"userId":"string","name":"string","avatar":"string"},"content":"string","toCommentContent":"string","createTime":"2017-04-26T00:50:54.824Z","isChild":true}]
                 * lastUpdateTime : 2017-04-26T00:50:54.824Z
                 * location : string
                 * longitude : 0
                 * latitude : 0
                 * praiseCount : 0
                 * praiseUsers : [{"userId":"string","name":"string","avatar":"string"}]
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
                private String lastUpdateTime;
                private String location;
                private int longitude;
                private int latitude;
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
                private String videoId;
                private int activityChannel;
                private int imageCount;
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

                public int getImageCount() {
                    return imageCount;
                }

                public void setImageCount(int imageCount) {
                    this.imageCount = imageCount;
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

                public int getLongitude() {
                    return longitude;
                }

                public void setLongitude(int longitude) {
                    this.longitude = longitude;
                }

                public int getLatitude() {
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
                     * toUser : {"userId":"string","name":"string","avatar":"string"}
                     * user : {"userId":"string","name":"string","avatar":"string"}
                     * content : string
                     * toCommentContent : string
                     * createTime : 2017-04-26T00:50:54.824Z
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

                    public static class UserBean {
                        /**
                         * userId : string
                         * name : string
                         * avatar : string
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

                public static class PraiseUsersBean {
                    /**
                     * userId : string
                     * name : string
                     * avatar : string
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
        }

        public static class SlideShowsBean {
            /**
             * id : string
             * category : 0
             * name : string
             * link : string
             * logo : string
             * intro : string
             * banner : string
             * isHomeShow : true
             * startTime : 2017-04-26T00:50:54.823Z
             * endTime : 2017-04-26T00:50:54.823Z
             * createUser : string
             * createTime : 2017-04-26T00:50:54.823Z
             * isDeleted : true
             * status : true
             * sort : 0
             */

            private String id;
            private int category;
            private String name;
            private String link;
            private String logo;
            private String intro;
            private String banner;
            private boolean isHomeShow;
            private String startTime;
            private String endTime;
            private String createUser;
            private String createTime;
            private boolean isDeleted;
            private boolean status;
            private int sort;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getCategory() {
                return category;
            }

            public void setCategory(int category) {
                this.category = category;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public String getBanner() {
                return banner;
            }

            public void setBanner(String banner) {
                this.banner = banner;
            }

            public boolean isIsHomeShow() {
                return isHomeShow;
            }

            public void setIsHomeShow(boolean isHomeShow) {
                this.isHomeShow = isHomeShow;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public String getCreateUser() {
                return createUser;
            }

            public void setCreateUser(String createUser) {
                this.createUser = createUser;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public boolean isIsDeleted() {
                return isDeleted;
            }

            public void setIsDeleted(boolean isDeleted) {
                this.isDeleted = isDeleted;
            }

            public boolean isStatus() {
                return status;
            }

            public void setStatus(boolean status) {
                this.status = status;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }
        }
    }
}
