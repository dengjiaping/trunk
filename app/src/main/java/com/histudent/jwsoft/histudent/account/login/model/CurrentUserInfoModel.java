package com.histudent.jwsoft.histudent.account.login.model;

/**
 * Created by liuguiyu-pc on 2016/7/27.
 * 用户登录后返回的基本信息model
 */
public class CurrentUserInfoModel {

    private String id;//存入数据库是需要用到
    private String userId;// 用户ID
    private String realName;//真实姓名
    private String userName;// 用户名
    private String nickname;//昵称
    private int userType;//账号类型（1为学生，2为家长，3为教师）
    private String avatar;//用户头像
    private String currentClassNumber;//当前所属班级编号
    private String email;//邮箱
    private boolean isEmailVerified;
    private String mobile;//手机号
    private boolean isMobileVerified;
    private boolean isAdmin;//是否为管理员
    private int loginTimes;//登录次数 ,
    private String registerTime;//注册时间 ,
    private String lastLoginTime;// 最后登录时间 ,
    private String lastLoginIP;//最后登录IP ,
    private boolean isBanned;//是否封禁 ,
    private String banReason;//(string, optional): 封禁的理由 ,
    private int fansNumber;//粉丝数
    private int attNumber;//关注数
    private int level;//用户等级
    private int experiencePoints;//经验数
    private int requtationPoints;// 威望值
    private int point;//用户积分
    private int tradePoints;//金币值 ,
    private String themeAppearance;// (string, optional): 用户选择的皮肤 ,
    private boolean isUseCustomStyle;//是否使用了自定义风格 ,
    private int pwdSafeLevel;// 用户密码等级 ,
    private boolean isSetSafeQuestion;//密保是否认证 ,
    private String homePageBanner;// (string, optional): 个人主页的banner图片 ,
    private boolean isStopped;//是否是黑名单 ,
    private String nowArea;// (string, optional): 地区 ,
    private String schoolName;// (string, optional): 当前所在学校 ,
    private String gradeName;// (string, optional): 当前所在年级 ,
    private String currClassName;// (string, optional): 当前所在班级 ,
    private String sex;// (string, optional): 性别 ,
    private boolean isBindIM;//是否绑定了网易云信accid ,
    private String token;// 获取身份令牌信息 ,
    private String imToken;//网易云信用户token（用于登录） ,
    private String tokenExpirationTime;//token过期时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBanReason() {
        return banReason;
    }

    public void setBanReason(String banReason) {
        this.banReason = banReason;
    }

    public String getThemeAppearance() {
        return themeAppearance;
    }

    public void setThemeAppearance(String themeAppearance) {
        this.themeAppearance = themeAppearance;
    }

    public String getHomePageBanner() {
        return homePageBanner;
    }

    public void setHomePageBanner(String homePageBanner) {
        this.homePageBanner = homePageBanner;
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

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
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

    public boolean getIsEmailVerified() {
        return isEmailVerified;
    }

    public void setIsEmailVerified(boolean emailVerified) {
        isEmailVerified = emailVerified;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean getIsMobileVerified() {
        return isMobileVerified;
    }

    public void setIsMobileVerified(boolean mobileVerified) {
        isMobileVerified = mobileVerified;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean admin) {
        isAdmin = admin;
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

    public String getLastLoginIP() {
        return lastLoginIP;
    }

    public void setLastLoginIP(String lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
    }

    public boolean getIsBanned() {
        return isBanned;
    }

    public void setIsBanned(boolean banned) {
        isBanned = banned;
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

    public boolean getIsUseCustomStyle() {
        return isUseCustomStyle;
    }

    public void setIsUseCustomStyle(boolean useCustomStyle) {
        isUseCustomStyle = useCustomStyle;
    }

    public int getPwdSafeLevel() {
        return pwdSafeLevel;
    }

    public void setPwdSafeLevel(int pwdSafeLevel) {
        this.pwdSafeLevel = pwdSafeLevel;
    }

    public boolean getIsSetSafeQuestion() {
        return isSetSafeQuestion;
    }

    public void setIsSetSafeQuestion(boolean setSafeQuestion) {
        isSetSafeQuestion = setSafeQuestion;
    }

    public boolean getIsStopped() {
        return isStopped;
    }

    public void setIsStopped(boolean stopped) {
        isStopped = stopped;
    }

    public boolean getIsBindIM() {
        return isBindIM;
    }

    public void setIsBindIM(boolean bindIM) {
        isBindIM = bindIM;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getImToken() {
        return imToken;
    }

    public void setImToken(String imToken) {
        this.imToken = imToken;
    }

    public String getTokenExpirationTime() {
        return tokenExpirationTime;
    }

    public void setTokenExpirationTime(String tokenExpirationTime) {
        this.tokenExpirationTime = tokenExpirationTime;
    }
}
