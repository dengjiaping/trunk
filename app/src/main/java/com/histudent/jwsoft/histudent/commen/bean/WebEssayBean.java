package com.histudent.jwsoft.histudent.commen.bean;

import java.io.Serializable;

/**
 * Created by liuguiyu-pc on 2017/6/12.
 */

public class WebEssayBean implements Serializable {
    /**
     * title : 读书轨迹
     * appType : 2
     * hideUIPrivacy : true
     * hideUISyncClass : true
     * defaultTopic : 21天阅读习惯养成
     */

    private String title;
    private int appType;
    private boolean hideUIPrivacy;
    private boolean hideUISyncClass;
    private String defaultTopic;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getDefaultTopic() {
        return defaultTopic;
    }

    public void setDefaultTopic(String defaultTopic) {
        this.defaultTopic = defaultTopic;
    }
}
