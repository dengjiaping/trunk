package com.histudent.jwsoft.histudent.body.find.bean;

/**
 * 活动成员
 * Created by ZhangYT on 2016/8/17.
 */
public class ActivityMembersBean {


    /**
     * id : 0ebda444-80c7-4dcd-9e5b-610aea1f5061
     * huoDong_Id : 111f2775-e957-4a11-8c9a-2ea4f7448cf8
     * signupUserId : 503ded85-8004-4b55-bce7-6fb33cd67d7d
     * signupUserRealName : 刘宜
     * signupUserAvatar : http://img.hitongx.com/UploadFiles/Album/20170306/d3274c71-e0e4-4a01-a9c2-32b3ecc33f5e.jpg@116w_116h_1e_1c_2o_58-1ci.jpg
     * isCreateUser : true
     * createTime : 2017-04-26 10:58:52
     * updateUserId : 503ded85-8004-4b55-bce7-6fb33cd67d7d
     * updateTime : 2017-04-26 10:58:52
     * isDeleted : false
     * signupUserType: 0 : All , 1 : Student , 2 : Genearch , 3 : Teacher
     */

    private String id;
    private String huoDong_Id;
    private String signupUserId;
    private String signupUserRealName;
    private String signupUserAvatar;
    private boolean isCreateUser;
    private String createTime;
    private String updateUserId;
    private String updateTime;
    private boolean isDeleted;
    private int signupUserType;

    public int getSignUpUserType() {
        return signupUserType;
    }

    public void setSignUpUserType(int signUpUserType) {
        this.signupUserType = signUpUserType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHuoDong_Id() {
        return huoDong_Id;
    }

    public void setHuoDong_Id(String huoDong_Id) {
        this.huoDong_Id = huoDong_Id;
    }

    public String getSignupUserId() {
        return signupUserId;
    }

    public void setSignupUserId(String signupUserId) {
        this.signupUserId = signupUserId;
    }

    public String getSignupUserRealName() {
        return signupUserRealName;
    }

    public void setSignupUserRealName(String signupUserRealName) {
        this.signupUserRealName = signupUserRealName;
    }

    public String getSignupUserAvatar() {
        return signupUserAvatar;
    }

    public void setSignupUserAvatar(String signupUserAvatar) {
        this.signupUserAvatar = signupUserAvatar;
    }

    public boolean isIsCreateUser() {
        return isCreateUser;
    }

    public void setIsCreateUser(boolean isCreateUser) {
        this.isCreateUser = isCreateUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
