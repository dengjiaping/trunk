package com.histudent.jwsoft.histudent.comment2.bean;

/**
 * Created by ZhangYT on 2017/4/14.
 */

public class UserBean {

    /**
     * userId : 9c52e448-54a9-4e6d-9ede-1daaf35166da
     * realName : yy
     * avatar : http://img.hitongx.com/images/NormalAvatarLogo.png
     */

    private String userId;
    private String realName;
    private String avatar;
    private String userMobile;

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
