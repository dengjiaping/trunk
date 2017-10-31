package com.histudent.jwsoft.histudent.comment2.bean;

/**
 * Created by ZhangYT on 2016/7/5.
 */
public class TypeBean {


    /**
     * id : a7adf873-9492-4bc1-8dde-5f324532e24b
     * name : 机器人
     * iconUrl : http://files.hitongx.com/UploadFiles/hdcicon/jiqiren.png
     * sortIndex : 7
     * createTime : 2017-04-26 13:44:06
     * updateUserId : 8f29ac2a-8de3-43d5-a101-3259abc7054c
     * categoryType : 2
     * updateTime : 2017-04-26 13:44:06
     * isDeleted : false
     * categoryLevel : 2
     * parentCategoryId : 81f156fd-c6c0-4fa6-b5cc-2a945fffb527
     * isDefault : true
     * itemCount : 5
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
    private String parentCategoryId;
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

    public String getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(String parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
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
