package com.histudent.jwsoft.histudent.body.find.bean;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2017/5/18.
 */

public class ClassMemberBean {

    /**
     * teaNum : 0
     * stuNum : 0
     * teaClassMembers : [{"userId":"string","userType":0,"userRealName":"string","userSex":0,"userAvatar":"string","userMobile":"string","classId":"string","cName":"string","gradeName":"string","schoolName":"string","isAdmin":true,"imgCount":0,"blogNum":0,"isClassMaker":true}]
     * stuClassMembers : [{"userId":"string","userType":0,"userRealName":"string","userSex":0,"userAvatar":"string","userMobile":"string","classId":"string","cName":"string","gradeName":"string","schoolName":"string","isAdmin":true,"imgCount":0,"blogNum":0,"isClassMaker":true}]
     * shareUrl : string
     */

    private int teaNum;
    private int stuNum;
    private String shareUrl;
    private List<TeaClassMembersBean> teaClassMembers;
    private List<StuClassMembersBean> stuClassMembers;

    public int getTeaNum() {
        return teaNum;
    }

    public void setTeaNum(int teaNum) {
        this.teaNum = teaNum;
    }

    public int getStuNum() {
        return stuNum;
    }

    public void setStuNum(int stuNum) {
        this.stuNum = stuNum;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public List<TeaClassMembersBean> getTeaClassMembers() {
        return teaClassMembers;
    }

    public void setTeaClassMembers(List<TeaClassMembersBean> teaClassMembers) {
        this.teaClassMembers = teaClassMembers;
    }

    public List<StuClassMembersBean> getStuClassMembers() {
        return stuClassMembers;
    }

    public void setStuClassMembers(List<StuClassMembersBean> stuClassMembers) {
        this.stuClassMembers = stuClassMembers;
    }

    public static class TeaClassMembersBean {
        /**
         * userId : string
         * userType : 0
         * userRealName : string
         * userSex : 0
         * userAvatar : string
         * userMobile : string
         * classId : string
         * cName : string
         * gradeName : string
         * schoolName : string
         * isAdmin : true
         * imgCount : 0
         * blogNum : 0
         * isClassMaker : true
         */

        private String userId;
        private int userType;
        private String userRealName;
        private int userSex;
        private String userAvatar;
        private String userMobile;
        private String classId;
        private String cName;
        private String gradeName;
        private String schoolName;
        private boolean isAdmin;
        private int imgCount;
        private int blogNum;
        private boolean isClassMaker;

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

        public String getCName() {
            return cName;
        }

        public void setCName(String cName) {
            this.cName = cName;
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

        public int getImgCount() {
            return imgCount;
        }

        public void setImgCount(int imgCount) {
            this.imgCount = imgCount;
        }

        public int getBlogNum() {
            return blogNum;
        }

        public void setBlogNum(int blogNum) {
            this.blogNum = blogNum;
        }

        public boolean isIsClassMaker() {
            return isClassMaker;
        }

        public void setIsClassMaker(boolean isClassMaker) {
            this.isClassMaker = isClassMaker;
        }
    }

    public static class StuClassMembersBean {
        /**
         * userId : string
         * userType : 0
         * userRealName : string
         * userSex : 0
         * userAvatar : string
         * userMobile : string
         * classId : string
         * cName : string
         * gradeName : string
         * schoolName : string
         * isAdmin : true
         * imgCount : 0
         * blogNum : 0
         * isClassMaker : true
         */

        private String userId;
        private int userType;
        private String userRealName;
        private int userSex;
        private String userAvatar;
        private String userMobile;
        private String classId;
        private String cName;
        private String gradeName;
        private String schoolName;
        private boolean isAdmin;
        private int imgCount;
        private int blogNum;
        private boolean isClassMaker;

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

        public String getCName() {
            return cName;
        }

        public void setCName(String cName) {
            this.cName = cName;
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

        public int getImgCount() {
            return imgCount;
        }

        public void setImgCount(int imgCount) {
            this.imgCount = imgCount;
        }

        public int getBlogNum() {
            return blogNum;
        }

        public void setBlogNum(int blogNum) {
            this.blogNum = blogNum;
        }

        public boolean isIsClassMaker() {
            return isClassMaker;
        }

        public void setIsClassMaker(boolean isClassMaker) {
            this.isClassMaker = isClassMaker;
        }
    }
}
