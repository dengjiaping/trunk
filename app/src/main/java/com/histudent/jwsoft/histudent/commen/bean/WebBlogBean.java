package com.histudent.jwsoft.histudent.commen.bean;

import java.io.Serializable;

/**
 * Created by liuguiyu-pc on 2017/6/12.
 */

public class WebBlogBean implements Serializable {

    /**
     * action : add
     * appType : 2
     * hideUIPrivacy : true
     * hideUISyncClass : false
     * blogId : 8B54463B-72C2-4ECE-8CA6-001EEBDBB274
     * name : 读后感
     */

    private String action;
    private int appType;
    private boolean hideUIPrivacy;
    private boolean hideUISyncClass;
    private String blogId;
    private String name;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getAppType() {
        return appType;
    }

    public void setAppType(int appType) {
        this.appType = appType;
    }

    public boolean isHideUIPrivacy() {
        return hideUIPrivacy;
    }

    public void setHideUIPrivacy(boolean hideUIPrivacy) {
        this.hideUIPrivacy = hideUIPrivacy;
    }

    public boolean isHideUISyncClass() {
        return hideUISyncClass;
    }

    public void setHideUISyncClass(boolean hideUISyncClass) {
        this.hideUISyncClass = hideUISyncClass;
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
