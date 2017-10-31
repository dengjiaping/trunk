package com.histudent.jwsoft.histudent.body.find.bean;

import java.util.List;

/**
 * Created by ZhangYT on 2017/5/18.
 */

public class SchoolClassesModel {


    /**
     * itemCount : 161
     * pageCount : 17
     * items : [{"classId":"c17da725-bd4b-473f-a5a7-504e6b62212b","name":"20班","logo":"http://img.hitongx.com/images/NormalClassLogo.png@60w_60h_1e_1c_2o_30-1ci.png","gradeId":"e0eb81fd-5fba-4dc8-a13a-9062a1f2476d","gradeName":"2017级","schoolId":"471","schoolName":"演示学校","userId":"1abf303e-6f18-4339-80de-c826864b18bd","teacherName":"金璐","auditSwitch":false,"joinWay":0,"memberNum":8,"order":0,"openState":0,"openTime":"0001-01-01 00:00:00","updater":"00000000-0000-0000-0000-000000000000","updateTime":"0001-01-01 00:00:00","allowJoinClass":false,"classFullName":"演示学校2017级20班","noticeNum":0,"eduSystemId":0}]
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
         * classId : c17da725-bd4b-473f-a5a7-504e6b62212b
         * name : 20班
         * logo : http://img.hitongx.com/images/NormalClassLogo.png@60w_60h_1e_1c_2o_30-1ci.png
         * gradeId : e0eb81fd-5fba-4dc8-a13a-9062a1f2476d
         * gradeName : 2017级
         * schoolId : 471
         * schoolName : 演示学校
         * userId : 1abf303e-6f18-4339-80de-c826864b18bd
         * teacherName : 金璐
         * auditSwitch : false
         * joinWay : 0
         * memberNum : 8
         * order : 0
         * openState : 0
         * openTime : 0001-01-01 00:00:00
         * updater : 00000000-0000-0000-0000-000000000000
         * updateTime : 0001-01-01 00:00:00
         * allowJoinClass : false
         * classFullName : 演示学校2017级20班
         * noticeNum : 0
         * eduSystemId : 0
         */

        private String classId;
        private String name;
        private String logo;
        private String gradeId;
        private String gradeName;
        private String schoolId;
        private String schoolName;
        private String userId;
        private String teacherName;
        private boolean auditSwitch;
        private int joinWay;
        private int memberNum;
        private int order;
        private int openState;
        private String openTime;
        private String updater;
        private String updateTime;
        private boolean allowJoinClass;
        private String classFullName;
        private int noticeNum;
        private int eduSystemId;

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
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

        public String getGradeId() {
            return gradeId;
        }

        public void setGradeId(String gradeId) {
            this.gradeId = gradeId;
        }

        public String getGradeName() {
            return gradeName;
        }

        public void setGradeName(String gradeName) {
            this.gradeName = gradeName;
        }

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

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public boolean isAuditSwitch() {
            return auditSwitch;
        }

        public void setAuditSwitch(boolean auditSwitch) {
            this.auditSwitch = auditSwitch;
        }

        public int getJoinWay() {
            return joinWay;
        }

        public void setJoinWay(int joinWay) {
            this.joinWay = joinWay;
        }

        public int getMemberNum() {
            return memberNum;
        }

        public void setMemberNum(int memberNum) {
            this.memberNum = memberNum;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getOpenState() {
            return openState;
        }

        public void setOpenState(int openState) {
            this.openState = openState;
        }

        public String getOpenTime() {
            return openTime;
        }

        public void setOpenTime(String openTime) {
            this.openTime = openTime;
        }

        public String getUpdater() {
            return updater;
        }

        public void setUpdater(String updater) {
            this.updater = updater;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public boolean isAllowJoinClass() {
            return allowJoinClass;
        }

        public void setAllowJoinClass(boolean allowJoinClass) {
            this.allowJoinClass = allowJoinClass;
        }

        public String getClassFullName() {
            return classFullName;
        }

        public void setClassFullName(String classFullName) {
            this.classFullName = classFullName;
        }

        public int getNoticeNum() {
            return noticeNum;
        }

        public void setNoticeNum(int noticeNum) {
            this.noticeNum = noticeNum;
        }

        public int getEduSystemId() {
            return eduSystemId;
        }

        public void setEduSystemId(int eduSystemId) {
            this.eduSystemId = eduSystemId;
        }
    }
}
