package com.histudent.jwsoft.histudent.bean.homework;

/**
 * Created by lichaojie on 2017/10/24.
 * desc:
 * 每个条目下的子数据(具体的作业)
 */

public class HomeworkAlreadySubBean {
    private String mThumb;
    private String mHomeWorkName;
    private String mHomeworkContent;
    private String mPublishOwner;
    private String mGroupName;
    private String mOwnerId;
    private boolean mIsComplete;
    private String id;


    public boolean isComplete() {
        return mIsComplete;
    }

    public void setComplete(boolean isComplete) {
        mIsComplete = isComplete;
    }

    public HomeworkAlreadySubBean setThumb(String thumb) {
        mThumb = thumb == null ? "" : thumb;
        return this;
    }

    public HomeworkAlreadySubBean setHomeWorkName(String homeWorkName) {
        mHomeWorkName = homeWorkName == null ? "" : homeWorkName;
        return this;
    }

    public HomeworkAlreadySubBean setHomeworkContent(String homeworkContent) {
        mHomeworkContent = homeworkContent == null ? "" : homeworkContent;
        return this;
    }

    public HomeworkAlreadySubBean setPublishOwner(String publishOwner) {
        mPublishOwner = publishOwner == null ? "" : publishOwner;
        return this;
    }

    public HomeworkAlreadySubBean setGroupName(String groupName) {
        mGroupName = groupName == null ? "" : groupName;
        return this;
    }

    public HomeworkAlreadySubBean setOwnerId(String ownerId) {
        mOwnerId = ownerId == null ? "" : ownerId;
        return this;
    }

    public HomeworkAlreadySubBean setId(String id) {
        this.id = id == null ? "" : id;
        return this;
    }

    public String getThumb() {
        return mThumb;
    }

    public String getHomeWorkName() {
        return mHomeWorkName;
    }

    public String getHomeworkContent() {
        return mHomeworkContent;
    }

    public String getPublishOwner() {
        return mPublishOwner;
    }

    public String getGroupName() {
        return mGroupName;
    }

    public String getOwnerId() {
        return mOwnerId;
    }

    public String getId() {
        return id;
    }

}
