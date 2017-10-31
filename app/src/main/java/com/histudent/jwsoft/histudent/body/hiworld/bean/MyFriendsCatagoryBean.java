package com.histudent.jwsoft.histudent.body.hiworld.bean;

/**
 *
 * 好友分类实体类
 * Created by ZhangYT on 2016/8/25.
 */
public class MyFriendsCatagoryBean {


    /**
     * cateUserNum : 1
     * sort : 1
     * categoryType : 1
     * name : 全部关注
     * categoryId : 254b1215-4556-45fe-b370-b00046e37fde
     */

    private int cateUserNum;
    private int sort;
    private int categoryType;
    private String name;
    private String categoryId;

    public int getCateUserNum() {
        return cateUserNum;
    }

    public void setCateUserNum(int cateUserNum) {
        this.cateUserNum = cateUserNum;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(int categoryType) {
        this.categoryType = categoryType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
