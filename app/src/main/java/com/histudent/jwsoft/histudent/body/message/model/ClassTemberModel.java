package com.histudent.jwsoft.histudent.body.message.model;

/**
 * Created by liuguiyu-pc on 2016/8/25.
 * 班级成员model
 */
public class ClassTemberModel {


    /**
     * userId : d3f286e2-e036-42a9-8e0b-2cf3565a1b18
     * userType : 1
     * userRealName : 江子安
     * userSex : 0
     * userAvatar : http://img.hitongx.com/images/NormalAvatarLogo.png
     * userMobile : 18758061474
     * classId : 81c98980-d638-48eb-9ed8-bfc8d7f2877f
     * className : (01)班
     * gradeName : 2013级
     * schoolName : 演示学校
     * isAdmin : false
     * imgCount : 25
     * blogNum : 0
     * isClassMaker : false
     */

    private String userId;
    private int userType;//用户类型 (0 : All , 1 : Student , 2 : Genearch , 3 : Teacher )
    private String userRealName;
    private int userSex;
    private String userAvatar;
    private String userMobile;
    private String classId;
    private String className;
    private String gradeName;
    private String schoolName;
    private boolean isAdmin;
    private String imgCount;
    private String blogNum;
    private boolean isClassMaker;
    private boolean tag;//后加字段，用户选择关联同学时选中状态的标记

    public boolean isTag() {
        return tag;
    }

    public void setTag(boolean tag) {
        this.tag = tag;
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

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public int getUserSex() {
        return userSex;
    }

    public void setUserSex(int userSex) {
        this.userSex = userSex;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getImgCount() {
        return imgCount;
    }

    public void setImgCount(String imgCount) {
        this.imgCount = imgCount;
    }

    public String getBlogNum() {
        return blogNum;
    }

    public void setBlogNum(String blogNum) {
        this.blogNum = blogNum;
    }

    public boolean isIsClassMaker() {
        return isClassMaker;
    }

    public void setIsClassMaker(boolean isClassMaker) {
        this.isClassMaker = isClassMaker;
    }
}
