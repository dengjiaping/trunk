package com.histudent.jwsoft.histudent.body.homepage.bean;

/**
 * Created by liuguiyu-pc on 2017/4/26.
 */

public class TopicInfoBean {


    /**
     * tagId : string
     * tagName : string
     * tagDescription : string
     * featuredImage : string
     * ownerCount : 0
     * htmlUrl : string
     * smallPic : string
     */

    private String tagId;
    private String tagName;
    private String tagDescription;
    private String featuredImage;
    private int ownerCount;
    private String htmlUrl;
    private String smallPic;

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

    public String getTagDescription() {
        return tagDescription;
    }

    public void setTagDescription(String tagDescription) {
        this.tagDescription = tagDescription;
    }

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

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getSmallPic() {
        return smallPic;
    }

    public void setSmallPic(String smallPic) {
        this.smallPic = smallPic;
    }
}
