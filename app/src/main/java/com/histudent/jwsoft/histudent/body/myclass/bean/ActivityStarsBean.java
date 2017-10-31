package com.histudent.jwsoft.histudent.body.myclass.bean;

import java.io.Serializable;

/**
 * Created by liuguiyu-pc on 2017/4/28.
 */

public class ActivityStarsBean implements Serializable {

    /**
     * id : 240c0990-7e89-4674-a371-eea07b92fa1a
     * userId : 87b471ed-fa93-4edf-ae9d-b8205f2e92b6
     * userType : 1
     * classId : 08645e80-d842-441e-92e1-32f0e7dc9fc9
     * startFrequency : 36
     * visitDuration : 195704
     * classActivityCount : 0
     * updateTime : 2017-05-31 11:03:02
     * avatar : http://img.hitongx.com/UploadFiles/UploadAvatar/20170522/049f5ae4-4312-4fc5-bdba-b02fb34f8faf.jpg@60w_60h_1e_1c_2o_30-1ci.jpg
     * realName : 刘桂禹
     * rankNum : 1
     * loginTimeLength : 54:21'44"
     */

    private String id;
    private String userId;
    private int userType;
    private String classId;
    private int startFrequency;
    private int visitDuration;
    private int classActivityCount;
    private String updateTime;
    private String avatar;
    private String realName;
    private int rankNum;
    private String loginTimeLength;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public int getStartFrequency() {
        return startFrequency;
    }

    public void setStartFrequency(int startFrequency) {
        this.startFrequency = startFrequency;
    }

    public int getVisitDuration() {
        return visitDuration;
    }

    public void setVisitDuration(int visitDuration) {
        this.visitDuration = visitDuration;
    }

    public int getClassActivityCount() {
        return classActivityCount;
    }

    public void setClassActivityCount(int classActivityCount) {
        this.classActivityCount = classActivityCount;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getRankNum() {
        return rankNum;
    }

    public void setRankNum(int rankNum) {
        this.rankNum = rankNum;
    }

    public String getLoginTimeLength() {
        return loginTimeLength;
    }

    public void setLoginTimeLength(String loginTimeLength) {
        this.loginTimeLength = loginTimeLength;
    }
}
