package com.histudent.jwsoft.histudent.commen.model;

import com.netease.nimlib.sdk.msg.model.RecentContact;

/**
 * Created by liuguiyu-pc on 2016/6/13.
 */
public class HiStudentUserInfo {

    private String name;//名称
    private RecentContact recentContact;
    private int unreadCount;
    private String contactId;

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public RecentContact getRecentContact() {
        return recentContact;
    }

    public void setRecentContact(RecentContact recentContact) {
        this.recentContact = recentContact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
