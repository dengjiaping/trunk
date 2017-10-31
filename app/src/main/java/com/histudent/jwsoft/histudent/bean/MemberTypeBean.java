package com.histudent.jwsoft.histudent.bean;

/**
 * Created by lichaojie on 2017/8/14.
 * desc:
 */

public class MemberTypeBean  {

    /**
     * avatar : http://img.hitongx.com/UploadFiles/UploadAvatar/20170801/5f72a8ba-7e04-4756-82c9-be06e120fe93.jpg@100w_100h_1e_1c_2o_50-1ci.jpg
     * name : 李超杰
     * userId : 244c33c8-893a-49be-bac5-69b3f69dd016
     * userType : 1
     */

    private String avatar;
    private String name;
    private String userId;
    private int userType;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
