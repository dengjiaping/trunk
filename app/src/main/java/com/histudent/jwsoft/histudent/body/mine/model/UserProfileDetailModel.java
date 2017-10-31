package com.histudent.jwsoft.histudent.body.mine.model;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/7/27.
 * 用户详细信息的model
 */
public class UserProfileDetailModel {

    private String userId;//用户ID
    private int sex;//性别=['0','1','2']integer Enum:0,1,2
    private String englishName;//:英文名
    private String birthDay;//:出生年月
    private String age;//年龄
    private String homeAreaCode;//:家乡
    private String nowAreaCode;//:现居地
    private String address;//:详细地址
    private int blood;//:血型=['0','1','2','3','4','5']integer Enum:0,1,2,3,4,5
    private int constellation;//:星座=['0','1','2','3','4','5','6','7','8','9','10','11','12']integer Enum:0,1,2,3,4,5,6,7,8,9,10,11,12
    private String favoriteMusic;//:喜欢的音乐
    private String favoriteMovies;//:喜欢的影视
    private String favoriteCartoons;//:喜欢的动漫
    private String favoriteGames;//:喜欢的游戏
    private String favoriteSports;//:喜欢的运动
    private String favoriteBooks;//:喜欢的书籍
    private String favoriteFood;//:喜欢的美食
    private String appreciateMen;//:喜欢的名人
    private String presentation;//:自我介绍
    private int integirty;//:资料完善度（0-100）
    private List<UserHonorsModel> honorsList;//:获奖情况列表

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

    public List<UserHonorsModel> getHonorsList() {
        return honorsList;
    }

    public void setHonorsList(List<UserHonorsModel> honorsList) {
        this.honorsList = honorsList;
    }
}
