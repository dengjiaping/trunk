package com.histudent.jwsoft.histudent.model.bean.homework;

import java.util.List;

/**
 * Created by lichaojie on 2017/11/1.
 * desc:
 */

public class ClassMemberEntity {

    private List<SubMember> stuClassMembers;

    public List<SubMember> getStuClassMembers() {
        return stuClassMembers;
    }

    public void setStuClassMembers(List<SubMember> stuClassMembers) {
        this.stuClassMembers = stuClassMembers;
    }

    public class SubMember {
        private boolean isAdmin;
        private String userAvatar;
        private String userId;
        private String userRealName;
        private String classId;
        private int userType;

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public boolean isAdmin() {
            return isAdmin;
        }

        public void setAdmin(boolean admin) {
            isAdmin = admin;
        }

        public String getUserAvatar() {
            return userAvatar;
        }

        public void setUserAvatar(String userAvatar) {
            this.userAvatar = userAvatar;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserRealName() {
            return userRealName;
        }

        public void setUserRealName(String userRealName) {
            this.userRealName = userRealName;
        }

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }
    }
}
