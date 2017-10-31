package com.histudent.jwsoft.histudent.commen.bean;

import java.io.Serializable;

/**
 * Created by ZhangYT on 2017/4/27.
 * 相册权限类型的实体类
 */

public class AlbumAuthorityModel implements Serializable {
    private boolean isMasker; //是否是群主、班主任
    private boolean isAdmin;//是否是管理员
    private boolean isMember;//是否是成员
    private boolean isOwner;//是否是自己
    private String id;//班级，社群，个人id

    public AlbumAuthorityModel(boolean isMasker, boolean isAdmin, boolean isMember, boolean isOwner, String id) {
        this.isMasker = isMasker;
        this.isAdmin = isAdmin;
        this.isMember = isMember;
        this.isOwner = isOwner;
        this.id = id;
    }

    public AlbumAuthorityModel(boolean isOwner, String id) {
        this.isOwner = isOwner;
        this.id = id;
    }


    public AlbumAuthorityModel(boolean isMasker, boolean isAdmin, boolean isMember) {
        this.isMasker = isMasker;
        this.isAdmin = isAdmin;
        this.isMember = isMember;
    }

    public AlbumAuthorityModel() {
    }

    public AlbumAuthorityModel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isMember() {
        return isMember;
    }

    public void setIsMember(boolean member) {
        isMember = member;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setIsOwner(boolean owner) {
        isOwner = owner;
    }

    public boolean isMasker() {

        return isMasker;
    }

    public void setIsMasker(boolean masker) {
        isMasker = masker;
    }
}
