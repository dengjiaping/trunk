package com.histudent.jwsoft.histudent.body.mine.model;

import java.util.List;

/**
 *
 * 屏蔽动态的人
 * Created by ZhangYT on 2016/9/9.
 */
public class DisabledFriendListBean {

        private String userId;
        private String name;
        private String avatar;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
}
