package com.histudent.jwsoft.histudent.body.find.bean;

import java.util.List;

/**
 * Created by ZhangYT on 2017/5/15.
 */

public class FindHomeModel {


    /**
     * schoolId : 1133006337
     * schoolName : 诸暨市直埠镇第五中心村幼儿园
     * hotClassList : [{"classesId":"85141575-097f-4e03-aa0f-df98e9c554c4","name":"1班","logo":"http://img.hitongx.com/UploadFiles/classLogo/20170428/a9abd1a5-3658-4eb5-b535-aef1c0698dbc.jpg@60w_60h_1e_1c_2o_30-1ci.jpg"}]
     * myAppList : [{"appKey":"100096","appName":"测试选修课","appUrl":"http://192.168.0.252:8091/#!/index?token=db4d7000945b44c9bdc12e2286876444","logo":"http://img.hitongx.com/UploadFiles/AuthAppLogo/20161108/85e9df70-7dd5-49e3-8a80-61176d7f0cd1.jpg@300w_300h_1e_1c_2o.jpg","isUsed":true},{"appKey":"100108","appName":"爱阅读活动","appUrl":"http://192.168.0.252:8080/ReadingTrack/Home/Index?jwsoftApp=1&accesstoken=db4d7000945b44c9bdc12e2286876444","logo":"http://img.hitongx.com/UploadFiles/AuthAppLogo/20170109/bec3658d-fcbc-4d3c-8d53-1b90bf38cb5f.png@300w_300h_1e_1c_2o.png","isUsed":true},{"appKey":"100092","appName":"数字化校园","appUrl":"http://www.jwsoft.cc","logo":"http://img.hitongx.com/UploadFiles/AuthAppLogo/20161122/3dc40cc0-017e-4f67-917d-2ee72de49733.png@300w_300h_1e_1c_2o.png","isUsed":true}]
     * groupCategorys : [{"id":"ee557e8b-aa31-4f10-9c7d-e2ea307ec690","name":"航模默分","iconUrl":"/images/huodong/category/air.png","sortIndex":1,"createTime":"2017-04-26 13:44:06","updateUserId":"8f29ac2a-8de3-43d5-a101-3259abc7054c","categoryType":2,"updateTime":"2017-04-26 13:44:06","isDeleted":false,"categoryLevel":2,"parentCategoryId":"f578463e-a557-416f-acb2-786740299e77","isDefault":true,"itemCount":49},{"id":"e59e1fae-fbe0-4622-a92e-0b6d941d5726","name":"其他默分","iconUrl":"/images/huodong/category/other.png","sortIndex":1,"createTime":"2017-04-26 13:44:06","updateUserId":"8f29ac2a-8de3-43d5-a101-3259abc7054c","categoryType":2,"updateTime":"2017-04-26 13:44:06","isDeleted":false,"categoryLevel":2,"parentCategoryId":"5f1b2fb4-511a-49d0-a80d-6fdf94631d3a","isDefault":true,"itemCount":11},{"id":"7f57b38a-a637-4798-8d38-856179f597b8","name":"书法默分","iconUrl":"/images/huodong/category/pen.png","sortIndex":1,"createTime":"2017-04-26 13:44:06","updateUserId":"8f29ac2a-8de3-43d5-a101-3259abc7054c","categoryType":2,"updateTime":"2017-04-26 13:44:06","isDeleted":false,"categoryLevel":2,"parentCategoryId":"896d42c2-496e-4d62-9da3-930b47ece6d1","isDefault":true,"itemCount":10},{"id":"a0033b3a-2e6a-4c2e-b232-3dd558d63231","name":"篮球默分","iconUrl":"/images/huodong/category/basketball.png","sortIndex":1,"createTime":"2017-04-26 13:44:06","updateUserId":"8f29ac2a-8de3-43d5-a101-3259abc7054c","categoryType":2,"updateTime":"2017-04-26 13:44:06","isDeleted":false,"categoryLevel":2,"parentCategoryId":"17c7e1d9-0930-4476-ad25-244fd7f45ae3","isDefault":true,"itemCount":9},{"id":"3cd870c4-ff71-440a-8050-b4fe0968573d","name":"足球默分","iconUrl":"/images/huodong/category/soccer.png","sortIndex":1,"createTime":"2017-04-26 13:44:06","updateUserId":"8f29ac2a-8de3-43d5-a101-3259abc7054c","categoryType":2,"updateTime":"2017-04-26 13:44:06","isDeleted":false,"categoryLevel":2,"parentCategoryId":"02bd5881-3dec-4e39-9258-9aa136e17d34","isDefault":true,"itemCount":9}]
     */

    private String schoolId;
    private String schoolName;
    private List<HotClassListBean> hotClassList;
    private List<MyAppListBean> myAppList;
    private List<GroupCategorysBean> groupCategorys;

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public List<HotClassListBean> getHotClassList() {
        return hotClassList;
    }

    public void setHotClassList(List<HotClassListBean> hotClassList) {
        this.hotClassList = hotClassList;
    }

    public List<MyAppListBean> getMyAppList() {
        return myAppList;
    }

    public void setMyAppList(List<MyAppListBean> myAppList) {
        this.myAppList = myAppList;
    }

    public List<GroupCategorysBean> getGroupCategorys() {
        return groupCategorys;
    }

    public void setGroupCategorys(List<GroupCategorysBean> groupCategorys) {
        this.groupCategorys = groupCategorys;
    }

    public static class HotClassListBean {
        /**
         * classesId : 85141575-097f-4e03-aa0f-df98e9c554c4
         * name : 1班
         * logo : http://img.hitongx.com/UploadFiles/classLogo/20170428/a9abd1a5-3658-4eb5-b535-aef1c0698dbc.jpg@60w_60h_1e_1c_2o_30-1ci.jpg
         */

        private String classesId;
        private String name;
        private String logo;

        public String getClassesId() {
            return classesId;
        }

        public void setClassesId(String classesId) {
            this.classesId = classesId;
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

    public static class MyAppListBean  {
        /**
         * appKey : 100096
         * appName : 测试选修课
         * appUrl : http://192.168.0.252:8091/#!/index?token=db4d7000945b44c9bdc12e2286876444
         * logo : http://img.hitongx.com/UploadFiles/AuthAppLogo/20161108/85e9df70-7dd5-49e3-8a80-61176d7f0cd1.jpg@300w_300h_1e_1c_2o.jpg
         * isUsed : true
         */

        private String appKey;
        private String appName;
        private String appUrl;
        private String logo;
        private boolean isUsed;
        private boolean isEmpty;
        private boolean isAdd;

        public boolean isUsed() {
            return isUsed;
        }

        public void setUsed(boolean used) {
            isUsed = used;
        }

        public boolean isEmpty() {
            return isEmpty;
        }

        public void setEmpty(boolean empty) {
            isEmpty = empty;
        }

        public boolean isAdd() {
            return isAdd;
        }

        public void setAdd(boolean add) {
            isAdd = add;
        }

        public String getAppKey() {
            return appKey;
        }

        public void setAppKey(String appKey) {
            this.appKey = appKey;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getAppUrl() {
            return appUrl;
        }

        public void setAppUrl(String appUrl) {
            this.appUrl = appUrl;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public boolean isIsUsed() {
            return isUsed;
        }

        public void setIsUsed(boolean isUsed) {
            this.isUsed = isUsed;
        }
    }

    public static class GroupCategorysBean {
        /**
         * id : ee557e8b-aa31-4f10-9c7d-e2ea307ec690
         * name : 航模默分
         * iconUrl : /images/huodong/category/air.png
         * sortIndex : 1
         * createTime : 2017-04-26 13:44:06
         * updateUserId : 8f29ac2a-8de3-43d5-a101-3259abc7054c
         * categoryType : 2
         * updateTime : 2017-04-26 13:44:06
         * isDeleted : false
         * categoryLevel : 2
         * parentCategoryId : f578463e-a557-416f-acb2-786740299e77
         * isDefault : true
         * itemCount : 49
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
}
