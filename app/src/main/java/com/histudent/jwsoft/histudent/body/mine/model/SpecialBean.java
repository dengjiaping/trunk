package com.histudent.jwsoft.histudent.body.mine.model;

/**
 * Created by liuguiyu-pc on 2017/5/10.
 * 等级特权model
 */

public class SpecialBean {


    /**
     * rightKey : string
     * cover : string
     * name : string
     * redirtType : 1
     * redirtUrl : string
     * bgColor : string
     */

    private String rightKey;
    private String cover;
    private String name;
    private int redirtType;
    private String redirtUrl;
    private String bgColor;

    public String getRightKey() {
        return rightKey;
    }

    public void setRightKey(String rightKey) {
        this.rightKey = rightKey;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRedirtType() {
        return redirtType;
    }

    public void setRedirtType(int redirtType) {
        this.redirtType = redirtType;
    }

    public String getRedirtUrl() {
        return redirtUrl;
    }

    public void setRedirtUrl(String redirtUrl) {
        this.redirtUrl = redirtUrl;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }
}
