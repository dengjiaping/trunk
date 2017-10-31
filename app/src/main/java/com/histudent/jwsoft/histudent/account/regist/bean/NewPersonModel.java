package com.histudent.jwsoft.histudent.account.regist.bean;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/12/16.
 */

public class NewPersonModel {


    /**
     * itemCount : 0
     * pageCount : 0
     * items : [{"userId":"string","realName":"string","userName":"string","secondLoginName":"string","nickname":"string","passwordQuestion":"string","userType":0,"associateId":"string","avatar":"string","currentClassNumber":"string","email":"string","isEmailVerified":true,"mobile":"string","isMobileVerified":true,"isAdmin":true,"loginTimes":0,"registerTime":"2016-12-16T01:08:40.598Z","lastLoginTime":"2016-12-16T01:08:40.598Z","lastLoginIP":"string","isBanned":true,"banReason":"string","fansNumber":0,"attNumber":0,"level":0,"experiencePoints":0,"requtationPoints":0,"point":0,"tradePoints":0,"themeAppearance":"string","isUseCustomStyle":true,"pwdSafeLevel":0,"isSetSafeQuestion":true,"homePageBanner":"string","appPageBanner":"string","isStopped":true,"nowArea":"string","schoolName":"string","gradeName":"string","currClassName":"string","sex":"string","isBindIM":true,"imToken":"string","token":"string","tokenExpirationTime":"2016-12-16T01:08:40.598Z","isFollowed":true,"deviceType":0,"deviceSerial":"string","distance":0,"praiseTime":"2016-12-16T01:08:40.598Z","classId":"string","guide":0}]
     */

    private int itemCount;
    private int pageCount;
    private List<ItemsBean> items;

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * userId : string
         * realName : string
         * userName : string
         * secondLoginName : string
         * nickname : string
         * passwordQuestion : string
         * userType : 0
         * associateId : string
         * avatar : string
         * currentClassNumber : string
         * email : string
         * isEmailVerified : true
         * mobile : string
         * isMobileVerified : true
         * isAdmin : true
         * loginTimes : 0
         * registerTime : 2016-12-16T01:08:40.598Z
         * lastLoginTime : 2016-12-16T01:08:40.598Z
         * lastLoginIP : string
         * isBanned : true
         * banReason : string
         * fansNumber : 0
         * attNumber : 0
         * level : 0
         * experiencePoints : 0
         * requtationPoints : 0
         * point : 0
         * tradePoints : 0
         * themeAppearance : string
         * isUseCustomStyle : true
         * pwdSafeLevel : 0
         * isSetSafeQuestion : true
         * homePageBanner : string
         * appPageBanner : string
         * isStopped : true
         * nowArea : string
         * schoolName : string
         * gradeName : string
         * currClassName : string
         * sex : string
         * isBindIM : true
         * imToken : string
         * token : string
         * tokenExpirationTime : 2016-12-16T01:08:40.598Z
         * isFollowed : true
         * deviceType : 0
         * deviceSerial : string
         * distance : 0
         * praiseTime : 2016-12-16T01:08:40.598Z
         * classId : string
         * guide : 0
         */

        private String userId;
        private String realName;
        private String userName;
        private String secondLoginName;
        private String nickname;
        private String passwordQuestion;
        private int userType;
        private String associateId;
        private String avatar;
        private String currentClassNumber;
        private String email;
        private boolean isEmailVerified;
        private String mobile;
        private boolean isMobileVerified;
        private boolean isAdmin;
        private int loginTimes;
        private String registerTime;
        private String lastLoginTime;
        private String lastLoginIP;
        private boolean isBanned;
        private String banReason;
        private int fansNumber;
        private int attNumber;
        private int level;
        private int experiencePoints;
        private int requtationPoints;
        private int point;
        private int tradePoints;
        private String themeAppearance;
        private boolean isUseCustomStyle;
        private int pwdSafeLevel;
        private boolean isSetSafeQuestion;
        private String homePageBanner;
        private String appPageBanner;
        private boolean isStopped;
        private String nowArea;
        private String schoolName;
        private String gradeName;
        private String currClassName;
        private String sex;
        private boolean isBindIM;
        private String imToken;
        private String token;
        private String tokenExpirationTime;
        private boolean isFollowed;
        private int deviceType;
        private String deviceSerial;
        private int distance;
        private String praiseTime;
        private String classId;
        private int guide;

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

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getSecondLoginName() {
            return secondLoginName;
        }

        public void setSecondLoginName(String secondLoginName) {
            this.secondLoginName = secondLoginName;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPasswordQuestion() {
            return passwordQuestion;
        }

        public void setPasswordQuestion(String passwordQuestion) {
            this.passwordQuestion = passwordQuestion;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public String getAssociateId() {
            return associateId;
        }

        public void setAssociateId(String associateId) {
            this.associateId = associateId;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getCurrentClassNumber() {
            return currentClassNumber;
        }

        public void setCurrentClassNumber(String currentClassNumber) {
            this.currentClassNumber = currentClassNumber;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public boolean isIsEmailVerified() {
            return isEmailVerified;
        }

        public void setIsEmailVerified(boolean isEmailVerified) {
            this.isEmailVerified = isEmailVerified;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public boolean isIsMobileVerified() {
            return isMobileVerified;
        }

        public void setIsMobileVerified(boolean isMobileVerified) {
            this.isMobileVerified = isMobileVerified;
        }

        public boolean isIsAdmin() {
            return isAdmin;
        }

        public void setIsAdmin(boolean isAdmin) {
            this.isAdmin = isAdmin;
        }

        public int getLoginTimes() {
            return loginTimes;
        }

        public void setLoginTimes(int loginTimes) {
            this.loginTimes = loginTimes;
        }

        public String getRegisterTime() {
            return registerTime;
        }

        public void setRegisterTime(String registerTime) {
            this.registerTime = registerTime;
        }

        public String getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(String lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public String getLastLoginIP() {
            return lastLoginIP;
        }

        public void setLastLoginIP(String lastLoginIP) {
            this.lastLoginIP = lastLoginIP;
        }

        public boolean isIsBanned() {
            return isBanned;
        }

        public void setIsBanned(boolean isBanned) {
            this.isBanned = isBanned;
        }

        public String getBanReason() {
            return banReason;
        }

        public void setBanReason(String banReason) {
            this.banReason = banReason;
        }

        public int getFansNumber() {
            return fansNumber;
        }

        public void setFansNumber(int fansNumber) {
            this.fansNumber = fansNumber;
        }

        public int getAttNumber() {
            return attNumber;
        }

        public void setAttNumber(int attNumber) {
            this.attNumber = attNumber;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getExperiencePoints() {
            return experiencePoints;
        }

        public void setExperiencePoints(int experiencePoints) {
            this.experiencePoints = experiencePoints;
        }

        public int getRequtationPoints() {
            return requtationPoints;
        }

        public void setRequtationPoints(int requtationPoints) {
            this.requtationPoints = requtationPoints;
        }

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public int getTradePoints() {
            return tradePoints;
        }

        public void setTradePoints(int tradePoints) {
            this.tradePoints = tradePoints;
        }

        public String getThemeAppearance() {
            return themeAppearance;
        }

        public void setThemeAppearance(String themeAppearance) {
            this.themeAppearance = themeAppearance;
        }

        public boolean isIsUseCustomStyle() {
            return isUseCustomStyle;
        }

        public void setIsUseCustomStyle(boolean isUseCustomStyle) {
            this.isUseCustomStyle = isUseCustomStyle;
        }

        public int getPwdSafeLevel() {
            return pwdSafeLevel;
        }

        public void setPwdSafeLevel(int pwdSafeLevel) {
            this.pwdSafeLevel = pwdSafeLevel;
        }

        public boolean isIsSetSafeQuestion() {
            return isSetSafeQuestion;
        }

        public void setIsSetSafeQuestion(boolean isSetSafeQuestion) {
            this.isSetSafeQuestion = isSetSafeQuestion;
        }

        public String getHomePageBanner() {
            return homePageBanner;
        }

        public void setHomePageBanner(String homePageBanner) {
            this.homePageBanner = homePageBanner;
        }

        public String getAppPageBanner() {
            return appPageBanner;
        }

        public void setAppPageBanner(String appPageBanner) {
            this.appPageBanner = appPageBanner;
        }

        public boolean isIsStopped() {
            return isStopped;
        }

        public void setIsStopped(boolean isStopped) {
            this.isStopped = isStopped;
        }

        public String getNowArea() {
            return nowArea;
        }

        public void setNowArea(String nowArea) {
            this.nowArea = nowArea;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }

        public String getGradeName() {
            return gradeName;
        }

        public void setGradeName(String gradeName) {
            this.gradeName = gradeName;
        }

        public String getCurrClassName() {
            return currClassName;
        }

        public void setCurrClassName(String currClassName) {
            this.currClassName = currClassName;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public boolean isIsBindIM() {
            return isBindIM;
        }

        public void setIsBindIM(boolean isBindIM) {
            this.isBindIM = isBindIM;
        }

        public String getImToken() {
            return imToken;
        }

        public void setImToken(String imToken) {
            this.imToken = imToken;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getTokenExpirationTime() {
            return tokenExpirationTime;
        }

        public void setTokenExpirationTime(String tokenExpirationTime) {
            this.tokenExpirationTime = tokenExpirationTime;
        }

        public boolean isIsFollowed() {
            return isFollowed;
        }

        public void setIsFollowed(boolean isFollowed) {
            this.isFollowed = isFollowed;
        }

        public int getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(int deviceType) {
            this.deviceType = deviceType;
        }

        public String getDeviceSerial() {
            return deviceSerial;
        }

        public void setDeviceSerial(String deviceSerial) {
            this.deviceSerial = deviceSerial;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public String getPraiseTime() {
            return praiseTime;
        }

        public void setPraiseTime(String praiseTime) {
            this.praiseTime = praiseTime;
        }

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public int getGuide() {
            return guide;
        }

        public void setGuide(int guide) {
            this.guide = guide;
        }
    }
}
