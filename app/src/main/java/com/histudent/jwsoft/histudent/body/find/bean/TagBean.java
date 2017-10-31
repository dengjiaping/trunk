package com.histudent.jwsoft.histudent.body.find.bean;

/**
 * Created by android03_pc on 2017/5/16.
 * 社群TAG标签实体类
 */

public class TagBean {


    /**
     * sortIndex : 1
     * id : 9ee69400-f83e-4e96-921a-6291996962a2
     * updateUserId : 8f29ac2a-8de3-43d5-a101-3259abc7054c
     * createTime : 2017-04-26 13:44:06
     * isDefault : true
     * updateTime : 2017-04-26 13:44:06
     * parentCategoryId : 69a5c3d4-dd25-4d45-9355-2df46d61a9cc
     * categoryType : 2
     * iconUrl : http://files.hitongx.com/UploadFiles/hdcicon/yingyujiao.png
     * name : 英语角
     * itemCount : 6
     * categoryLevel : 2
     * isDeleted : false
     */

    private int sortIndex;
    private String id;
    private String updateUserId;
    private String createTime;
    private boolean isDefault;
    private String updateTime;
    private String parentCategoryId;
    private int categoryType;
    private String iconUrl;
    private String name;
    private int itemCount;
    private int categoryLevel;
    private boolean isDeleted;

    public int getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(int sortIndex) {
        this.sortIndex = sortIndex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public boolean isIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(String parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public int getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(int categoryType) {
        this.categoryType = categoryType;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public int getCategoryLevel() {
        return categoryLevel;
    }

    public void setCategoryLevel(int categoryLevel) {
        this.categoryLevel = categoryLevel;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
