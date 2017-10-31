package com.histudent.jwsoft.histudent.account.regist.bean;

import java.io.Serializable;

/**
 * Created by liuguiyu-pc on 2017/4/12.
 */

public class RegistBean implements Serializable {

    private String name;
    private String mobile;
    private String resetToken;
    private String pwd;
    private int registerType;
    private String openId;
    private int thirdparty;
    private String nickname;
    private String avatar;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getRegisterType() {
        return registerType;
    }

    public void setRegisterType(int registerType) {
        this.registerType = registerType;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public int getThirdparty() {
        return thirdparty;
    }

    public void setThirdparty(int thirdparty) {
        this.thirdparty = thirdparty;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
