package com.histudent.jwsoft.histudent.body.mine.model;

import java.io.Serializable;

/**
 * Created by liuguiyu-pc on 2016/8/12.
 */
public class UserClassListModel implements Serializable {


    /**
     * classId : 069e7805-21d0-406b-b2a3-6d4c19158b02
     * cName : 3班
     * gradeName : 2016级
     * schoolName : 杭州英才高级中学
     * alias : 测试创建班级
     * classLogo : http://img.hitongx.com/UploadFiles/classLogo/20160911/7b3b9602-f4a5-43f5-8150-99924f748564.jpg@_60w_60h_1e_1c_30-1ci.jpg
     * classMemberNum : 0
     * classCoverImg :
     * classUserId : 7d89158d-6574-46f4-a1cf-e58b24c420bf
     * classUserRealName : yuyg
     * classUserAvatar : http://img.hitongx.com/images/NormalAvatarLogo.png
     * noticeNum : 0
     * photoNum : 0
     * blogNum : 28
     * classApplyAuditStatus : 1
     * isClassMaker : false
     * isAdmin : false
     * eductionSystemId : 0
     * allowJoin : false
     */

    private String classId;
    private String cName;
    private String gradeName;
    private String schoolName;
    private String alias;
    private String classLogo;
    private int classMemberNum;
    private String classCoverImg;
    private String classUserId;
    private String classUserRealName;
    private String classUserAvatar;
    private int noticeNum;
    private String photoNum;
    private String blogNum;
    private int classApplyAuditStatus;
    private boolean isClassMaker;
    private boolean isAdmin;
    private int eductionSystemId;
    private boolean allowJoin;
    private boolean select=true;
    private boolean isDefaultClass;
    private boolean tag;
    private String fullName;
    private String chatGroupKey;
    private int imNum;

    public int getImNum() {
        return imNum;
    }

    public void setImNum(int imNum) {
        this.imNum = imNum;
    }

    public String getChatGroupKey() {
        return chatGroupKey;
    }

    public void setChatGroupKey(String chatGroupKey) {
        this.chatGroupKey = chatGroupKey;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isTag() {
        return tag;
    }

    public void setTag(boolean tag) {
        this.tag = tag;
    }

    public boolean getIsDefaultClass() {
        return isDefaultClass;
    }

    public void setIsDefaultClass(boolean defaultClass) {
        isDefaultClass = defaultClass;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public boolean isClassMaker() {
        return isClassMaker;
    }

    public void setClassMaker(boolean classMaker) {
        isClassMaker = classMaker;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getCName() {
        return cName;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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

    public String getClassCoverImg() {
        return classCoverImg;
    }

    public void setClassCoverImg(String classCoverImg) {
        this.classCoverImg = classCoverImg;
    }

    public String getClassUserId() {
        return classUserId;
    }

    public void setClassUserId(String classUserId) {
        this.classUserId = classUserId;
    }

    public String getClassUserRealName() {
        return classUserRealName;
    }

    public void setClassUserRealName(String classUserRealName) {
        this.classUserRealName = classUserRealName;
    }

    public String getClassUserAvatar() {
        return classUserAvatar;
    }

    public void setClassUserAvatar(String classUserAvatar) {
        this.classUserAvatar = classUserAvatar;
    }

    public int getNoticeNum() {
        return noticeNum;
    }

    public void setNoticeNum(int noticeNum) {
        this.noticeNum = noticeNum;
    }

    public String getPhotoNum() {
        return photoNum;
    }

    public void setPhotoNum(String photoNum) {
        this.photoNum = photoNum;
    }

    public String getBlogNum() {
        return blogNum;
    }

    public void setBlogNum(String blogNum) {
        this.blogNum = blogNum;
    }

    public int getClassApplyAuditStatus() {
        return classApplyAuditStatus;
    }

    public void setClassApplyAuditStatus(int classApplyAuditStatus) {
        this.classApplyAuditStatus = classApplyAuditStatus;
    }

    public boolean isIsClassMaker() {
        return isClassMaker;
    }

    public void setIsClassMaker(boolean isClassMaker) {
        this.isClassMaker = isClassMaker;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public int getEductionSystemId() {
        return eductionSystemId;
    }

    public void setEductionSystemId(int eductionSystemId) {
        this.eductionSystemId = eductionSystemId;
    }

    public boolean isAllowJoin() {
        return allowJoin;
    }

    public void setAllowJoin(boolean allowJoin) {
        this.allowJoin = allowJoin;
    }
}
