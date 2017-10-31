package com.histudent.jwsoft.histudent.body.myclass.bean;

/**
 * Created by liuguiyu-pc on 2017/5/25.
 */

public class ClassJoinBean {

    /**
     * classId : string
     * fullName : string
     * cName : string
     * classLogo : string
     * classMemberNum : 0
     * isDefaultClass : true
     * isAdmin : true
     * classNumber : string
     */

    private String classId;
    private String fullName;
    private String cName;
    private String classLogo;
    private int classMemberNum;
    private boolean isDefaultClass;
    private boolean isAdmin;
    private String classNumber;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCName() {
        return cName;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }

    public String getClassLogo() {
        return classLogo;
    }

    public void setClassLogo(String classLogo) {
        this.classLogo = classLogo;
    }

    public int getClassMemberNum() {
        return classMemberNum;
    }

    public void setClassMemberNum(int classMemberNum) {
        this.classMemberNum = classMemberNum;
    }

    public boolean isIsDefaultClass() {
        return isDefaultClass;
    }

    public void setIsDefaultClass(boolean isDefaultClass) {
        this.isDefaultClass = isDefaultClass;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }
}
