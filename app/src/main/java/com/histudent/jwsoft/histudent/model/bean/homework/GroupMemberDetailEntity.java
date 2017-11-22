package com.histudent.jwsoft.histudent.model.bean.homework;

import java.util.List;

/**
 * Created by lichaojie on 2017/10/31.
 * desc:
 * 作业-成员列表
 */

public class GroupMemberDetailEntity {


    private String teamName;
    private List<SubMemberEntity> memberList;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<SubMemberEntity> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<SubMemberEntity> memberList) {
        this.memberList = memberList;
    }

    public class SubMemberEntity {
        private String avatar;
        private String realName;
        private String teamMemberId;
        private String userId;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getTeamMemberId() {
            return teamMemberId;
        }

        public void setTeamMemberId(String teamMemberId) {
            this.teamMemberId = teamMemberId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
