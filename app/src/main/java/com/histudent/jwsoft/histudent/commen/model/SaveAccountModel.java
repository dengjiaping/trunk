package com.histudent.jwsoft.histudent.commen.model;

/**
 * Created by liuguiyu-pc on 2016/7/29.
 * 用于保存账号登陆名和密码的model
 */

public class SaveAccountModel {

    private String id;//以用户的userId作为数据库id
    private String account;//用户名
    private String pwd;//密码
    private String avatar;//头像路径

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
