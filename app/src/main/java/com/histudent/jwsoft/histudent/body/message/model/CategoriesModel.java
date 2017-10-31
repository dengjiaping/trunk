package com.histudent.jwsoft.histudent.body.message.model;

/**
 * Created by liuguiyu-pc on 2016/8/3.
 * 分组model
 */
public class CategoriesModel {

    private String categoryId;//(string, optional): 分类Id
    private String id;
    private String name;//(string, optional): 分类名称
    private int sort;//(integer, optional): 分类排序
    private int cateUserNum;// (integer, optional): 分类用户数
    private String categoryType;
    private boolean isDefault;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getCateUserNum() {
        return cateUserNum;
    }

    public void setCateUserNum(int cateUserNum) {
        this.cateUserNum = cateUserNum;
    }
}
