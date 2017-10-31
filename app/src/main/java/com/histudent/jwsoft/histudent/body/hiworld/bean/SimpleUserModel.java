package com.histudent.jwsoft.histudent.body.hiworld.bean;

import com.histudent.jwsoft.histudent.body.find.bean.HuoDongDetailsModel;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.io.Serializable;

/**
 * Created by ZhangYT on 2016/8/29.
 */
public class SimpleUserModel implements Serializable {


    /**
     * avatar : http://img.hitongx.com/images/NormalAvatarLogo.png
     * name : 吖吖的家长
     * userId : c3f1272e-57a9-4b2a-a5ae-216faba9413f
     */

    private String avatar;
    private String name;
    private String userId;
    private boolean tag;
    private String userMobile;
    private boolean isAdmin;
    private int type;



    private HuoDongDetailsModel.SinupedUsersBean mSinupedUsersBean;

    public SimpleUserModel(String avatar, String name, String userId, HuoDongDetailsModel.SinupedUsersBean b) {
        this.avatar = avatar;
        this.name = name;
        this.userId = userId;
        this.mSinupedUsersBean = b;
    }

    public HuoDongDetailsModel.SinupedUsersBean getSinupedUsersBean() {
        return mSinupedUsersBean;
    }

    public void setSinupedUsersBean(HuoDongDetailsModel.SinupedUsersBean sinupedUsersBean) {
        mSinupedUsersBean = sinupedUsersBean;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isMasker() {
        return isMasker;
    }

    public void setMasker(boolean masker) {
        isMasker = masker;
    }

    private boolean isMasker;

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public boolean isTag() {//后加字段，表示该用户是否被选择
        return tag;
    }

    public void setTag(boolean tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "SimpleUserModel{" +
                "avatar='" + avatar + '\'' +
                ", name='" + name + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }

    public SimpleUserModel() {
    }

    public SimpleUserModel(String avatar, String name, String userId, boolean tag) {
        this.avatar = avatar;
        this.name = name;
        this.userId = userId;
        this.tag = tag;
    }

    public SimpleUserModel(String avatar, String name, String userId) {
        this.avatar = avatar;
        this.name = name;
        this.userId = userId;
    }

    public SimpleUserModel(String avatar, String name, String userId, String userMobile) {
        this.avatar = avatar;
        this.name = name;
        this.userId = userId;
        this.userMobile = userMobile;
    }

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    @Override
    public boolean equals(Object obj) {

        if (obj != null && obj instanceof SimpleUserModel) {

            SimpleUserModel model = ((SimpleUserModel) obj);
            if (!StringUtil.isEmpty(model.getUserId())) {
                return model.getUserId().equals(userId);
            }
        }
        return false;
    }
}
