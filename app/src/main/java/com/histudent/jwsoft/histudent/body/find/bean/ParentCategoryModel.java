package com.histudent.jwsoft.histudent.body.find.bean;

/**
 * Created by ZhangYT on 2017/5/16.
 */

public class ParentCategoryModel {


    /**
     * id : 69a5c3d4-dd25-4d45-9355-2df46d61a9cc
     * name : 英语角
     * iconUrl : http://files.hitongx.com/images/huodong/category/english.png
     * sortIndex : 11
     * createTime : 2016-07-21 09:04:53
     * updateUserId : 8f29ac2a-8de3-43d5-a101-3259abc7054c
     * categoryType : 2
     * updateTime : 2016-07-21 09:04:53
     * isDeleted : false
     * categoryLevel : 1
     * isDefault : false
     * itemCount : 6
     */

    private String id;
    private String name;
    private String iconUrl;
    private int sortIndex;
    private String createTime;
    private String updateUserId;
    private int categoryType;
    private String updateTime;
    private boolean isDeleted;
    private int categoryLevel;
    private boolean isDefault;
    private int itemCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(int sortIndex) {
        this.sortIndex = sortIndex;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public int getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(int categoryType) {
        this.categoryType = categoryType;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public int getCategoryLevel() {
        return categoryLevel;
    }

    public void setCategoryLevel(int categoryLevel) {
        this.categoryLevel = categoryLevel;
    }

    public boolean isIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }
}
