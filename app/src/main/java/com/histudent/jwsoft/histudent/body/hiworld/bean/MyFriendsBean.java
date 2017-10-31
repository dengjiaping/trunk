package com.histudent.jwsoft.histudent.body.hiworld.bean;

/**
 *
 * 我的好友实体类
 * Created by ZhangYT on 2016/8/25.
 */
public class MyFriendsBean {


    /**
     * followId : 36ed76fc-3cbf-45c1-8a5d-d709fd882640
     * realName : 吖吖的家长
     * isBindIM : true
     * avatar : http://img.hitongx.com/images/NormalAvatarLogo.png
     * userType : 2
     * mobile :
     * userId : c3f1272e-57a9-4b2a-a5ae-216faba9413f
     */

    private String followId;
    private String realName;
    private boolean isBindIM;
    private String avatar;
    private int userType;
    private String mobile;
    private String userId;
    private boolean isEnable=true;//自定义，用于判断该好友是否是可选状态，默认为可选

    private boolean isSelected;//自定义，用户判断该用户是否是已经被选择的状态
    public boolean isIsEnable() {
        return isEnable;
    }


    public boolean isIsSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public String getFollowId() {
        return followId;
    }

    public void setFollowId(String followId) {
        this.followId = followId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public boolean isIsBindIM() {
        return isBindIM;
    }

    public void setIsBindIM(boolean isBindIM) {
        this.isBindIM = isBindIM;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
