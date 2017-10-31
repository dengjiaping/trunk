package com.histudent.jwsoft.histudent.body.myclass.bean;

import java.io.Serializable;

/**
 * Created by liuguiyu-pc on 2017/4/27.
 */

public class CreatClassSuccedBean implements Serializable{

    /**
     * status : 1
     * cId : 92417783-4dc3-4002-856f-765f2a774ae2
     * classLogo : http://img.hitongx.com/images/NormalClassLogo.png@116w_116h_1e_1c_2o_58-1ci.png
     * classFuName : 紫微幼儿园2015级4班
     * classNum : 39728947
     * shareUrl :
     */

    private int status;
    private String cId;
    private String classLogo;
    private String classFuName;
    private String classNum;
    private String shareUrl;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCId() {
        return cId;
    }

    public void setCId(String cId) {
        this.cId = cId;
    }

    public String getClassLogo() {
        return classLogo;
    }

    public void setClassLogo(String classLogo) {
        this.classLogo = classLogo;
    }

    public String getClassFuName() {
        return classFuName;
    }

    public void setClassFuName(String classFuName) {
        this.classFuName = classFuName;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }
}
