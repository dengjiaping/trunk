package com.histudent.jwsoft.histudent.body.message.model;

/**
 * Created by liuguiyu-pc on 2016/8/31.
 */
public class NoticeModel {


    /**
     * noticeId : 5049f259-0773-4a1e-b121-6074d61eeb89
     * title : 按时打算
     * content :  啊是的 爱迪生啊是的asas
     * displayArea : 3
     * ownerId : 81c98980-d638-48eb-9ed8-bfc8d7f2877f
     * userId : 2468d967-a754-4535-9ed2-1a2e4909dd75
     * createTime : 2016-08-31 10:34:54
     * lastModified : 2016-08-31 10:34:54
     */

    private String noticeId;
    private String title;
    private String content;
    private int displayArea;
    private String ownerId;
    private String userId;
    private String createTime;
    private String lastModified;

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDisplayArea() {
        return displayArea;
    }

    public void setDisplayArea(int displayArea) {
        this.displayArea = displayArea;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }
}
