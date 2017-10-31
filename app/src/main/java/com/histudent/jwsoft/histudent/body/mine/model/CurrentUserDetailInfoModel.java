package com.histudent.jwsoft.histudent.body.mine.model;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/7/27.
 * 用户详细信息的model
 */
public class CurrentUserDetailInfoModel {

    /**
     * profile : {"userId":"string","sex":0,"englishName":"string","birthDay":"2016-12-07T03:21:45.501Z","homeAreaCode":"string","nowAreaCode":"string","address":"string","blood":0,"constellation":0,"qq":"string","weiXin":"string","idCard":"string","favoriteMusic":"string","favoriteMovies":"string","favoriteCartoons":"string","favoriteGames":"string","favoriteSports":"string","favoriteBooks":"string","favoriteFood":"string","appreciateMen":"string","presentation":"string","integirty":0,"honorsList":[{"honorId":"string","userId":"string","name":"string","getTime":"2016-12-07T03:21:45.501Z","picture":"string"}]}
     * countInfo : {"blogCount":0,"microBlogCount":0,"photoCount":0}
     * userInfo : {"itemCount":0,"pageCount":0,"items":[{"userId":"string","name":"string","avatar":"string"}]}
     * classInfo : {"itemCount":0,"pageCount":0,"items":[{"classesId":"string","classesNumber":"string","name":"string","logo":"string"}]}
     * groupInfo : {"itemCount":0,"pageCount":0,"items":[{"groupId":"string","name":"string","logo":"string"}]}
     * huoDongInfo : {"itemCount":0,"pageCount":0,"items":[{"huodongId":"string","name":"string","poster":"string"}]}
     * pointLower : 0
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
     * registerTime : 2016-12-07T03:21:45.502Z
     * lastLoginTime : 2016-12-07T03:21:45.502Z
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
     * tokenExpirationTime : 2016-12-07T03:21:45.503Z
     * isFollowed : true
     * deviceType : 0
     * deviceSerial : string
     * distance : 0
     * praiseTime : 2016-12-07T03:21:45.503Z
     * classId : string
     */

    private ProfileBean profile;
    private CountInfoBean countInfo;
    private UserInfoBean userInfo;
    private ClassInfoBean classInfo;
    private GroupInfoBean groupInfo;
    private HuoDongInfoBean huoDongInfo;
    private int pointLower;
    private boolean isOpenInvitationRewardSwitch;
    private String userId;
    private boolean isBindingWeChat;
    private boolean isBindingQQ;
    private int bannerPraiseNum;
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
    private boolean bannerIsPraise;

    public boolean isIsBindingWeChat() {
        return isBindingWeChat;
    }

    public void setIsBindingWeChat(boolean bindingWeChat) {
        isBindingWeChat = bindingWeChat;
    }

    public boolean isIsBindingQQ() {
        return isBindingQQ;
    }

    public void setIsBindingQQ(boolean bindingQQ) {
        isBindingQQ = bindingQQ;
    }

    public boolean isBannerIsPraise() {
        return bannerIsPraise;
    }

    public void setBannerIsPraise(boolean bannerIsPraise) {
        this.bannerIsPraise = bannerIsPraise;
    }

    public boolean isIsOpenInvitationRewardSwitch() {
        return isOpenInvitationRewardSwitch;
    }

    public void setIsOpenInvitationRewardSwitch(boolean openInvitationRewardSwitch) {
        isOpenInvitationRewardSwitch = openInvitationRewardSwitch;
    }

    public int getBannerPraiseNum() {
        return bannerPraiseNum;
    }

    public void setBannerPraiseNum(int bannerPraiseNum) {
        this.bannerPraiseNum = bannerPraiseNum;
    }

    public ProfileBean getProfile() {
        return profile;
    }

    public void setProfile(ProfileBean profile) {
        this.profile = profile;
    }

    public CountInfoBean getCountInfo() {
        return countInfo;
    }

    public void setCountInfo(CountInfoBean countInfo) {
        this.countInfo = countInfo;
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public ClassInfoBean getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(ClassInfoBean classInfo) {
        this.classInfo = classInfo;
    }

    public GroupInfoBean getGroupInfo() {
        return groupInfo;
    }

    public void setGroupInfo(GroupInfoBean groupInfo) {
        this.groupInfo = groupInfo;
    }

    public HuoDongInfoBean getHuoDongInfo() {
        return huoDongInfo;
    }

    public void setHuoDongInfo(HuoDongInfoBean huoDongInfo) {
        this.huoDongInfo = huoDongInfo;
    }

    public int getPointLower() {
        return pointLower;
    }

    public void setPointLower(int pointLower) {
        this.pointLower = pointLower;
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

    public static class ProfileBean {
        /**
         * userId : string
         * sex : 0
         * englishName : string
         * birthDay : 2016-12-07T03:21:45.501Z
         * homeAreaCode : string
         * nowAreaCode : string
         * address : string
         * blood : 0
         * constellation : 0
         * qq : string
         * weiXin : string
         * idCard : string
         * favoriteMusic : string
         * favoriteMovies : string
         * favoriteCartoons : string
         * favoriteGames : string
         * favoriteSports : string
         * favoriteBooks : string
         * favoriteFood : string
         * appreciateMen : string
         * presentation : string
         * integirty : 0
         * honorsList : [{"honorId":"string","userId":"string","name":"string","getTime":"2016-12-07T03:21:45.501Z","picture":"string"}]
         */

        private String userId;
        private int sex;
        private String englishName;
        private String birthDay;
        private String homeAreaCode;
        private String nowAreaCode;
        private String address;
        private int blood;
        private int constellation;
        private String qq;
        private String weiXin;
        private String idCard;
        private String favoriteMusic;
        private String favoriteMovies;
        private String favoriteCartoons;
        private String favoriteGames;
        private String favoriteSports;
        private String favoriteBooks;
        private String favoriteFood;
        private String appreciateMen;
        private String presentation;
        private int integirty;
        private List<HonorsListBean> honorsList;
        private String age;

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getEnglishName() {
            return englishName;
        }

        public void setEnglishName(String englishName) {
            this.englishName = englishName;
        }

        public String getBirthDay() {
            return birthDay;
        }

        public void setBirthDay(String birthDay) {
            this.birthDay = birthDay;
        }

        public String getHomeAreaCode() {
            return homeAreaCode;
        }

        public void setHomeAreaCode(String homeAreaCode) {
            this.homeAreaCode = homeAreaCode;
        }

        public String getNowAreaCode() {
            return nowAreaCode;
        }

        public void setNowAreaCode(String nowAreaCode) {
            this.nowAreaCode = nowAreaCode;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getBlood() {
            return blood;
        }

        public void setBlood(int blood) {
            this.blood = blood;
        }

        public int getConstellation() {
            return constellation;
        }

        public void setConstellation(int constellation) {
            this.constellation = constellation;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getWeiXin() {
            return weiXin;
        }

        public void setWeiXin(String weiXin) {
            this.weiXin = weiXin;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getFavoriteMusic() {
            return favoriteMusic;
        }

        public void setFavoriteMusic(String favoriteMusic) {
            this.favoriteMusic = favoriteMusic;
        }

        public String getFavoriteMovies() {
            return favoriteMovies;
        }

        public void setFavoriteMovies(String favoriteMovies) {
            this.favoriteMovies = favoriteMovies;
        }

        public String getFavoriteCartoons() {
            return favoriteCartoons;
        }

        public void setFavoriteCartoons(String favoriteCartoons) {
            this.favoriteCartoons = favoriteCartoons;
        }

        public String getFavoriteGames() {
            return favoriteGames;
        }

        public void setFavoriteGames(String favoriteGames) {
            this.favoriteGames = favoriteGames;
        }

        public String getFavoriteSports() {
            return favoriteSports;
        }

        public void setFavoriteSports(String favoriteSports) {
            this.favoriteSports = favoriteSports;
        }

        public String getFavoriteBooks() {
            return favoriteBooks;
        }

        public void setFavoriteBooks(String favoriteBooks) {
            this.favoriteBooks = favoriteBooks;
        }

        public String getFavoriteFood() {
            return favoriteFood;
        }

        public void setFavoriteFood(String favoriteFood) {
            this.favoriteFood = favoriteFood;
        }

        public String getAppreciateMen() {
            return appreciateMen;
        }

        public void setAppreciateMen(String appreciateMen) {
            this.appreciateMen = appreciateMen;
        }

        public String getPresentation() {
            return presentation;
        }

        public void setPresentation(String presentation) {
            this.presentation = presentation;
        }

        public int getIntegirty() {
            return integirty;
        }

        public void setIntegirty(int integirty) {
            this.integirty = integirty;
        }

        public List<HonorsListBean> getHonorsList() {
            return honorsList;
        }

        public void setHonorsList(List<HonorsListBean> honorsList) {
            this.honorsList = honorsList;
        }

        public static class HonorsListBean {
            /**
             * honorId : string
             * userId : string
             * name : string
             * getTime : 2016-12-07T03:21:45.501Z
             * picture : string
             */

            private String honorId;
            private String userId;
            private String name;
            private String getTime;
            private String picture;

            public String getHonorId() {
                return honorId;
            }

            public void setHonorId(String honorId) {
                this.honorId = honorId;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getGetTime() {
                return getTime;
            }

            public void setGetTime(String getTime) {
                this.getTime = getTime;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }
        }
    }

    public static class CountInfoBean {
        /**
         * blogCount : 0
         * microBlogCount : 0
         * photoCount : 0
         */

        private int blogCount;
        private int microBlogCount;
        private int photoCount;

        public int getBlogCount() {
            return blogCount;
        }

        public void setBlogCount(int blogCount) {
            this.blogCount = blogCount;
        }

        public int getMicroBlogCount() {
            return microBlogCount;
        }

        public void setMicroBlogCount(int microBlogCount) {
            this.microBlogCount = microBlogCount;
        }

        public int getPhotoCount() {
            return photoCount;
        }

        public void setPhotoCount(int photoCount) {
            this.photoCount = photoCount;
        }
    }

    public static class UserInfoBean {
        /**
         * itemCount : 0
         * pageCount : 0
         * items : [{"userId":"string","name":"string","avatar":"string"}]
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
             * name : string
             * avatar : string
             */

            private String userId;
            private String name;
            private String avatar;

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }
        }
    }

    public static class ClassInfoBean {
        /**
         * itemCount : 0
         * pageCount : 0
         * items : [{"classesId":"string","classesNumber":"string","name":"string","logo":"string"}]
         */

        private int itemCount;
        private int pageCount;
        private List<ItemsBeanX> items;

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

        public List<ItemsBeanX> getItems() {
            return items;
        }

        public void setItems(List<ItemsBeanX> items) {
            this.items = items;
        }

        public static class ItemsBeanX {
            /**
             * classesId : string
             * classesNumber : string
             * name : string
             * logo : string
             */

            private String classesId;
            private String classesNumber;
            private String name;
            private String logo;

            public String getClassesId() {
                return classesId;
            }

            public void setClassesId(String classesId) {
                this.classesId = classesId;
            }

            public String getClassesNumber() {
                return classesNumber;
            }

            public void setClassesNumber(String classesNumber) {
                this.classesNumber = classesNumber;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }
        }
    }

    public static class GroupInfoBean {
        /**
         * itemCount : 0
         * pageCount : 0
         * items : [{"groupId":"string","name":"string","logo":"string"}]
         */

        private int itemCount;
        private int pageCount;
        private List<ItemsBeanXX> items;

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

        public List<ItemsBeanXX> getItems() {
            return items;
        }

        public void setItems(List<ItemsBeanXX> items) {
            this.items = items;
        }

        public static class ItemsBeanXX {
            /**
             * groupId : string
             * name : string
             * logo : string
             */

            private String groupId;
            private String name;
            private String logo;
            private int memberNum;

            public int getMemberNum() {
                return memberNum;
            }

            public void setMemberNum(int memberNum) {
                this.memberNum = memberNum;
            }

            public String getGroupId() {
                return groupId;
            }

            public void setGroupId(String groupId) {
                this.groupId = groupId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }
        }
    }

    public static class HuoDongInfoBean {
        /**
         * itemCount : 0
         * pageCount : 0
         * items : [{"huodongId":"string","name":"string","poster":"string"}]
         */

        private int itemCount;
        private int pageCount;
        private List<ItemsBeanXXX> items;

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

        public List<ItemsBeanXXX> getItems() {
            return items;
        }

        public void setItems(List<ItemsBeanXXX> items) {
            this.items = items;
        }

        public static class ItemsBeanXXX {
            /**
             * huodongId : string
             * name : string
             * poster : string
             */

            private String huodongId;
            private String name;
            private String poster;

            public String getHuodongId() {
                return huodongId;
            }

            public void setHuodongId(String huodongId) {
                this.huodongId = huodongId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPoster() {
                return poster;
            }

            public void setPoster(String poster) {
                this.poster = poster;
            }
        }
    }
}
