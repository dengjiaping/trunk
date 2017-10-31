package com.histudent.jwsoft.histudent.comment2.bean;

import java.io.Serializable;

/**
 * Created by liuguiyu-pc on 2016/12/15.
 */

public class ClassSelectModel implements Serializable {

    private static final long serialVersionUID = 1234L;

    private int userType;
    private String logo, schoolId, schoolName, eduSystemId, eduSystemName, gradeName, gradeId, className, classId, cityName, areaName, noteName, classDescription;

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public String getClassDescription() {
        return classDescription;
    }

    public void setClassDescription(String classDescription) {
        this.classDescription = classDescription;
    }

    public static ClassSelectModel getIntent() {
        return new ClassSelectModel();
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
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

    public String getEduSystemId() {
        return eduSystemId;
    }

    public void setEduSystemId(String eduSystemId) {
        this.eduSystemId = eduSystemId;
    }

    public String getEduSystemName() {
        return eduSystemName;
    }

    public void setEduSystemName(String eduSystemName) {
        this.eduSystemName = eduSystemName;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }
}
