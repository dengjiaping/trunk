package com.histudent.jwsoft.histudent.body.find.bean;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2017/5/19.
 */

public class SchoolHomeBean {
    /**
     * schoolId : string
     * schoolName : string
     * schoolBanner : string
     * classCount : 0
     * memberCount : 0
     * topClasses : [{"classesId":"string","classesNumber":"string","schoolName":"string","gradeName":"string","name":"string","logo":"string"}]
     */

    private String schoolId;
    private String schoolName;
    private String schoolBanner;
    private int classCount;
    private int memberCount;
    private List<TopClassesBean> topClasses;

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

    public String getSchoolBanner() {
        return schoolBanner;
    }

    public void setSchoolBanner(String schoolBanner) {
        this.schoolBanner = schoolBanner;
    }

    public int getClassCount() {
        return classCount;
    }

    public void setClassCount(int classCount) {
        this.classCount = classCount;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public List<TopClassesBean> getTopClasses() {
        return topClasses;
    }

    public void setTopClasses(List<TopClassesBean> topClasses) {
        this.topClasses = topClasses;
    }

    public static class TopClassesBean {
        /**
         * classesId : string
         * classesNumber : string
         * schoolName : string
         * gradeName : string
         * name : string
         * logo : string
         */

        private String classesId;
        private String classesNumber;
        private String schoolName;
        private String gradeName;
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
