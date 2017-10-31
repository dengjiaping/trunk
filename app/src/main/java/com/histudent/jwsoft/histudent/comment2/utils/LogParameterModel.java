package com.histudent.jwsoft.histudent.comment2.utils;

import com.histudent.jwsoft.histudent.body.hiworld.bean.SimpleUserModel;
import com.histudent.jwsoft.histudent.body.mine.model.UserClassListModel;

import java.io.Serializable;
import java.util.List;

/**
 * 发表日志需要传递的参数
 * Created by ZhangYT on 2017/4/17.
 */

public class LogParameterModel implements Serializable {
    private String title;//标题
    private List<UserClassListModel> classIds;//同步班级信息
    private List<SimpleUserModel> atUserList;//at的好友信息
    private int privacyStatus = -1;
    private double longitude;
    private String location;
    private double latitude;
    private String content;
    private List<String> fileList;//日志的图片信息
    private String blogId;//草稿id

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<UserClassListModel> getClassIds() {
        return classIds;
    }

    public void setClassIds(List<UserClassListModel> classIds) {
        this.classIds = classIds;
    }

    public List<SimpleUserModel> getAtUserList() {
        return atUserList;
    }

    public void setAtUserList(List<SimpleUserModel> atUserList) {
        this.atUserList = atUserList;
    }

    public int getPrivacyStatus() {
        return privacyStatus;
    }

    public void setPrivacyStatus(int privacyStatus) {
        this.privacyStatus = privacyStatus;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getFileList() {
        return fileList;
    }

    public void setFileList(List<String> fileList) {
        this.fileList = fileList;
    }
}
