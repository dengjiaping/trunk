package com.histudent.jwsoft.histudent.model.bean.main;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.histudent.jwsoft.histudent.commen.bean.AtUserModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lichaojie on 2017/11/13.
 * desc:
 * 首页数据实体
 */

public class HomePageEntity implements MultiItemEntity {

    @Override
    public int getItemType() {
        return 0;
    }

    //轮播图
    public List<BannerEntity> slideShows;
    //班级
    public RecommendClassEntity recommendClasses;
    //活跃用户
    public ActiveUserEntity recommendUsers;
    //热门话题
    public HotTopicEntity recommendTags;
    //推荐动态
    public RecommendDynamicEntity recommendActivities;


    public List<BannerEntity> getSlideShows() {
        return slideShows;
    }

    public void setSlideShows(List<BannerEntity> slideShows) {
        this.slideShows = slideShows;
    }

    public RecommendClassEntity getRecommendClasses() {
        return recommendClasses;
    }

    public void setRecommendClasses(RecommendClassEntity recommendClasses) {
        this.recommendClasses = recommendClasses;
    }

    public ActiveUserEntity getRecommendUsers() {
        return recommendUsers;
    }

    public void setRecommendUsers(ActiveUserEntity recommendUsers) {
        this.recommendUsers = recommendUsers;
    }

    public HotTopicEntity getRecommendTags() {
        return recommendTags;
    }

    public void setRecommendTags(HotTopicEntity recommendTags) {
        this.recommendTags = recommendTags;
    }

    public RecommendDynamicEntity getRecommendActivities() {
        return recommendActivities;
    }

    public void setRecommendActivities(RecommendDynamicEntity recommendActivities) {
        this.recommendActivities = recommendActivities;
    }

    public class BannerEntity {
        public String banner;
        public int category;
        public String createTime;
        public String endTime;
        public String id;
        public String intro;
        public boolean isDeleted;
        public boolean isHomeShow;
        public String link;
        public String logo;
        public boolean name;
        public int slideTime;
        public int sort;
        public String startTime;
        public boolean status;

        public String getBanner() {
            return banner;
        }

        public void setBanner(String banner) {
            this.banner = banner;
        }

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public boolean isDeleted() {
            return isDeleted;
        }

        public void setDeleted(boolean deleted) {
            isDeleted = deleted;
        }

        public boolean isHomeShow() {
            return isHomeShow;
        }

        public void setHomeShow(boolean homeShow) {
            isHomeShow = homeShow;
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

        public boolean isName() {
            return name;
        }

        public void setName(boolean name) {
            this.name = name;
        }

        public int getSlideTime() {
            return slideTime;
        }

        public void setSlideTime(int slideTime) {
            this.slideTime = slideTime;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }
    }

    public class RecommendClassEntity {
        public int itemCount;
        public int pageCount;
        public List<SubClassEntity> items;

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

        public List<SubClassEntity> getItems() {
            return items;
        }

        public void setItems(List<SubClassEntity> items) {
            this.items = items;
        }

        public class SubClassEntity {
            public String classesId;
            public String gradeName;
            public String logo;
            public String name;
            public String schoolName;

            public String getClassesId() {
                return classesId;
            }

            public void setClassesId(String classesId) {
                this.classesId = classesId;
            }

            public String getGradeName() {
                return gradeName;
            }

            public void setGradeName(String gradeName) {
                this.gradeName = gradeName;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSchoolName() {
                return schoolName;
            }

            public void setSchoolName(String schoolName) {
                this.schoolName = schoolName;
            }
        }
    }

    public class ActiveUserEntity {
        public int itemCount;
        public int pageCount;
        public List<SubActiveUserEntity> items;

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

        public List<SubActiveUserEntity> getItems() {
            return items;
        }

        public void setItems(List<SubActiveUserEntity> items) {
            this.items = items;
        }

        public class SubActiveUserEntity {
            public String avatar;
            public String name;
            public String userId;
            public int userType;

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public int getUserType() {
                return userType;
            }

            public void setUserType(int userType) {
                this.userType = userType;
            }
        }
    }

    public class HotTopicEntity {
        public int itemCount;
        public int pageCount;
        public List<SubHotTopicEntity> items;

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

        public List<SubHotTopicEntity> getItems() {
            return items;
        }

        public void setItems(List<SubHotTopicEntity> items) {
            this.items = items;
        }

        public class SubHotTopicEntity {
            public String featuredImage;
            public int ownerCount;
            public String tagDescription;
            public String tagId;
            public String tagName;

            public String getFeaturedImage() {
                return featuredImage;
            }

            public void setFeaturedImage(String featuredImage) {
                this.featuredImage = featuredImage;
            }

            public int getOwnerCount() {
                return ownerCount;
            }

            public void setOwnerCount(int ownerCount) {
                this.ownerCount = ownerCount;
            }

            public String getTagDescription() {
                return tagDescription;
            }

            public void setTagDescription(String tagDescription) {
                this.tagDescription = tagDescription;
            }

            public String getTagId() {
                return tagId;
            }

            public void setTagId(String tagId) {
                this.tagId = tagId;
            }

            public String getTagName() {
                return tagName;
            }

            public void setTagName(String tagName) {
                this.tagName = tagName;
            }
        }
    }

    public class RecommendDynamicEntity {
        public List<SubRecommendDynamicEntity> items;

        public List<SubRecommendDynamicEntity> getItems() {
            return items;
        }

        public void setItems(List<SubRecommendDynamicEntity> items) {
            this.items = items;
        }

        public class SubRecommendDynamicEntity {
            public String actId;
            public String cover;
            public String activityItemKey;
            public String htmlUrl;
            public int imageCount;
            public List<ImageEntity> images;
            public String lastUpdateTime;
            public String location;
            public String ownerId;
            public String ownerName;
            public int ownerType;
            public String sourceId;
            public String summary;
            public String title;
            public String userId;
            public String userName;
            public int multiMediaType;
            public List<TopicModel> topicList;
            public List<AtUserModel> atUserList;
            public String videoId;


            public class AtUserModel implements Serializable {

                private String userId;
                private String realName;

                public String getRealName() {
                    return realName;
                }

                public void setRealName(String realName) {
                    this.realName = realName;
                }

                public String getUserId() {
                    return userId;
                }

                public void setUserId(String userId) {
                    this.userId = userId;
                }
            }


            public class TopicModel implements Serializable {
                public String topicId;
                public String topicName;

                public String getTopicId() {
                    return topicId;
                }

                public void setTopicId(String topicId) {
                    this.topicId = topicId;
                }

                public String getTopicName() {
                    return topicName;
                }

                public void setTopicName(String topicName) {
                    this.topicName = topicName;
                }
            }

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

            public int getMultiMediaType() {
                return multiMediaType;
            }

            public void setMultiMediaType(int multiMediaType) {
                this.multiMediaType = multiMediaType;
            }

            public String getActId() {
                return actId;
            }

            public void setActId(String actId) {
                this.actId = actId;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getActivityItemKey() {
                return activityItemKey;
            }

            public void setActivityItemKey(String activityItemKey) {
                this.activityItemKey = activityItemKey;
            }

            public String getHtmlUrl() {
                return htmlUrl;
            }

            public void setHtmlUrl(String htmlUrl) {
                this.htmlUrl = htmlUrl;
            }

            public int getImageCount() {
                return imageCount;
            }

            public void setImageCount(int imageCount) {
                this.imageCount = imageCount;
            }

            public List<ImageEntity> getImages() {
                return images;
            }

            public void setImages(List<ImageEntity> images) {
                this.images = images;
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

            public String getOwnerId() {
                return ownerId;
            }

            public void setOwnerId(String ownerId) {
                this.ownerId = ownerId;
            }

            public String getOwnerName() {
                return ownerName;
            }

            public void setOwnerName(String ownerName) {
                this.ownerName = ownerName;
            }

            public int getOwnerType() {
                return ownerType;
            }

            public void setOwnerType(int ownerType) {
                this.ownerType = ownerType;
            }

            public String getSourceId() {
                return sourceId;
            }

            public void setSourceId(String sourceId) {
                this.sourceId = sourceId;
            }

            public String getSummary() {
                return summary;
            }

            public void setSummary(String summary) {
                this.summary = summary;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
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

            public class ImageEntity {
                public String bigSizeUrl;
                public String imgId;
                public String name;
                public String thumbnailUrl;

                public String getBigSizeUrl() {
                    return bigSizeUrl;
                }

                public void setBigSizeUrl(String bigSizeUrl) {
                    this.bigSizeUrl = bigSizeUrl;
                }

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
            }
        }
    }

}
