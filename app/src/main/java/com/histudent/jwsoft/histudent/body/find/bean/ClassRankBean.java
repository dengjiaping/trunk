package com.histudent.jwsoft.histudent.body.find.bean;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2017/5/16.
 */

public class ClassRankBean {

    /**
     * itemCount : 0
     * pageCount : 0
     * items : [{"id":"string","orgId":"string","classesId":"string","number":"string","name":"string","noteName":"string","logo":"string","classMasterId":"string","classMasterAvatar":"string","classMasterName":"string","memberNum":0,"hotPercent":0,"addTime":"2017-05-16T00:54:45.769Z","rankNum":0,"activitiesCount":0}]
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
         * id : string
         * orgId : string
         * classesId : string
         * number : string
         * name : string
         * noteName : string
         * logo : string
         * classMasterId : string
         * classMasterAvatar : string
         * classMasterName : string
         * memberNum : 0
         * hotPercent : 0
         * addTime : 2017-05-16T00:54:45.769Z
         * rankNum : 0
         * activitiesCount : 0
         */

        private String id;
        private String orgId;
        private String classesId;
        private String number;
        private String name;
        private String noteName;
        private String logo;
        private String classMasterId;
        private String classMasterAvatar;
        private String classMasterName;
        private int memberNum;
        private int hotPercent;
        private String addTime;
        private int rankNum;
        private int activitiesCount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public String getClassesId() {
            return classesId;
        }

        public void setClassesId(String classesId) {
            this.classesId = classesId;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNoteName() {
            return noteName;
        }

        public void setNoteName(String noteName) {
            this.noteName = noteName;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getClassMasterId() {
            return classMasterId;
        }

        public void setClassMasterId(String classMasterId) {
            this.classMasterId = classMasterId;
        }

        public String getClassMasterAvatar() {
            return classMasterAvatar;
        }

        public void setClassMasterAvatar(String classMasterAvatar) {
            this.classMasterAvatar = classMasterAvatar;
        }

        public String getClassMasterName() {
            return classMasterName;
        }

        public void setClassMasterName(String classMasterName) {
            this.classMasterName = classMasterName;
        }

        public int getMemberNum() {
            return memberNum;
        }

        public void setMemberNum(int memberNum) {
            this.memberNum = memberNum;
        }

        public int getHotPercent() {
            return hotPercent;
        }

        public void setHotPercent(int hotPercent) {
            this.hotPercent = hotPercent;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public int getRankNum() {
            return rankNum;
        }

        public void setRankNum(int rankNum) {
            this.rankNum = rankNum;
        }

        public int getActivitiesCount() {
            return activitiesCount;
        }

        public void setActivitiesCount(int activitiesCount) {
            this.activitiesCount = activitiesCount;
        }
    }
}
