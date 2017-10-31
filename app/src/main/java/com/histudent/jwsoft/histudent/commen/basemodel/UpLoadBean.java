package com.histudent.jwsoft.histudent.commen.basemodel;

/**
 * Created by liuguiyu-pc on 2017/4/13.
 * 上传视频的key信息类
 */

public class UpLoadBean {
    private String securityToken;
    private String accessKeySecret;
    private String accessKeyId;
    private String expiration;
    private String extare;

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getExtare() {
        return extare;
    }

    public void setExtare(String extare) {
        this.extare = extare;
    }
}
