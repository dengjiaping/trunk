package com.histudent.jwsoft.histudent.body.message.uikit.session.fragment;

import com.netease.nimlib.sdk.msg.model.RecentContact;

/**
 * Created by liuguiyu-pc on 2016/8/26.
 */
public class RecentContactsModel {

    private long time;//时间
    private String title;//
    private String content;//最近一条消息的内容
    private int sessionType;//会话类型
    private int unreadCount;//未读数量
    private int chatType;
    private String img;
    private String accountId;
    private RecentContact recent;
    private String url;
    private String id;
    private String Logo;
    private int isRead;//(1;未读，-1：已读)
    private String pushId;
    private String officiaId;
    private String batchNumber;
    private String userId;
    private int openmode;
    private String openParam;
    private int immsgtype;

    public int getOpenmode() {
        return openmode;
    }

    public void setOpenmode(int openmode) {
        this.openmode = openmode;
    }

    public String getOpenParam() {
        return openParam;
    }

    public void setOpenParam(String openParam) {
        this.openParam = openParam;
    }

    public int getImmsgtype() {
        return immsgtype;
    }

    public void setImmsgtype(int immsgtype) {
        this.immsgtype = immsgtype;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }



    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getOfficiaId() {
        return officiaId;
    }

    public void setOfficiaId(String officiaId) {
        this.officiaId = officiaId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String logo) {
        Logo = logo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RecentContact getRecent() {
        return recent;
    }

    public void setRecent(RecentContact recent) {
        this.recent = recent;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getChatType() {
        return chatType;
    }

    public void setChatType(int chatType) {
        this.chatType = chatType;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
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

    public int getSessionType() {
        return sessionType;
    }

    public void setSessionType(int sessionType) {
        this.sessionType = sessionType;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }


}
