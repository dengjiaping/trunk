package com.histudent.jwsoft.histudent.model.bean;

/**
 * Created by huyg on 2017/9/28.
 */

/**
 * commentCount: 0
    commentList: []
    isPraise: false
    objectId: "8a26c6ee-bf03-4a1f-ba81-067b93bb6720"
    praiseCount: 0
    praiseList: []
 */
public class ImageInfoBean {
    private boolean isPraise;
    private String objectId;
    private int praiseCount;

    public boolean isPraise() {
        return isPraise;
    }

    public void setPraise(boolean praise) {
        isPraise = praise;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public int getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(int praiseCount) {
        this.praiseCount = praiseCount;
    }
}
