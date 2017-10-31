package com.histudent.jwsoft.histudent.body.mine.model;

import java.util.List;

/**
 *
 * 黑名单
 * Created by ZhangYT on 2016/9/9.
 */
public class BlackListBean {

        private String userId;
        private String followId;
        private String realName;
        private String avatar;
        private String mobile;
        private boolean isBindIM;
        private int userType;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getFollowId() {
            return followId;
        }

        public void setFollowId(String followId) {
            this.followId = followId;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public boolean isIsBindIM() {
            return isBindIM;
        }

        public void setIsBindIM(boolean isBindIM) {
            this.isBindIM = isBindIM;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

}
