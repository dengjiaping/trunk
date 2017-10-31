package com.histudent.jwsoft.histudent.body.hiworld.bean;

import java.io.Serializable;

import static android.R.attr.description;

/**
 * 话题
 * Created by ZhangYT on 2017/4/13.
 */

public class TopicModel implements Serializable {

    /**
     * tagId : 61962c55-8596-4553-b943-d49d41d6a7b8
     * description : 测试
     * tagName : 21天阅读习惯养成
     */

    private String tagId;
    private String tagName;
    private int ownerCount;
    private String smallPic;
    private String tagDescription;

    public String getTagDescription() {
        return tagDescription;
    }

    public void setTagDescription(String tagDescription) {
        this.tagDescription = tagDescription;
    }

    public String getSmallPic() {
        return smallPic;
    }

    public void setSmallPic(String smallPic) {
        this.smallPic = smallPic;
    }

    public TopicModel() {
    }

    public TopicModel(String tagName) {
        this.tagName = tagName;
    }

    public int getOwnerCount() {
        return ownerCount;
    }

    public void setOwnerCount(int ownerCount) {
        this.ownerCount = ownerCount;
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

    @Override
    public String toString() {
        return "TopicModel{" +
                "tagId='" + tagId + '\'' +
                ", description='" + description + '\'' +
                ", tagName='" + tagName + '\'' +
                '}';
    }
}
