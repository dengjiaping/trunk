package com.histudent.jwsoft.histudent.body.myclass.bean;

import java.io.Serializable;

/**
 * Created by liuguiyu-pc on 2017/4/22.
 * 班级基础信息实体bean
 */

public class ClassInfoBean implements Serializable {

    public static final int GETLEVE = 1;
    public static final int GETGRADE = 2;
    public static final int GETCLASS = 3;


    private String logoPath;
    private String schoolId;
    private String schoolNmae;
    private String eduSystemName;
    private String eduSystemId;
    private String gradeNmae;
    private String classNmae;

    private String gradeId;

    public ClassInfoBean() {
    }

    public ClassInfoBean(String logoPath, String schoolId, String schoolNmae, String eduSystemName, String eduSystemId, String gradeNmae, String classNmae,String gradeId) {
        this.logoPath = logoPath;
        this.schoolId = schoolId;
        this.schoolNmae = schoolNmae;
        this.eduSystemName = eduSystemName;
        this.eduSystemId = eduSystemId;
        this.gradeNmae = gradeNmae;
        this.classNmae = classNmae;
        this.gradeId = gradeId;
    }

    public static int getGETLEVE() {
        return GETLEVE;
    }

    public static int getGETGRADE() {
        return GETGRADE;
    }

    public static int getGETCLASS() {
        return GETCLASS;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolNmae() {
        return schoolNmae;
    }

    public void setSchoolNmae(String schoolNmae) {
        this.schoolNmae = schoolNmae;
    }

    public String getEduSystemName() {
        return eduSystemName;
    }

    public void setEduSystemName(String eduSystemName) {
        this.eduSystemName = eduSystemName;
    }

    public String getEduSystemId() {
        return eduSystemId;
    }

    public void setEduSystemId(String eduSystemId) {
        this.eduSystemId = eduSystemId;
    }

    public String getGradeNmae() {
        return gradeNmae;
    }

    public void setGradeNmae(String gradeNmae) {
        this.gradeNmae = gradeNmae;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }


    public String getGradeId() {
        return gradeId;
    }


    public String getClassNmae() {
        return classNmae;
    }

    public void setClassNmae(String classNmae) {
        this.classNmae = classNmae;
    }


}
