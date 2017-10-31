package com.histudent.jwsoft.histudent.body.mine.model;

import java.util.List;

/**
 * Created by ZhangYT on 2016/12/19.
 */

public class InvationNewSBean {

    /**
     * applyId : 95568541-1b43-4704-b889-0a55dce0b209
     * groupId : adb4f6a9-b50d-4687-bded-63d3c49dd79c
     * groupName : 最近社群1
     * applyUserAvatar : http://img.hitongx.com/UploadFiles/Album/20160828/103317d3-fa60-439d-b092-097e904a6639@_60w_60h_1e_1c_30-1ci.src
     * applyUserId : e14d4f67-564b-44ce-a75d-494fe878250e
     * applyReason : 来啦啦啦啦啦
     * applyUserRealName : slugs
     * applyTime : 2016-11-17 09:58
     */

    private List<ApplyListBean> applyList;
    /**
     * id : c71b6598-1eb0-4f52-851b-d18348ed37e1
     * beInvitedUserId : df3a0abd-415f-4e26-ac6c-00f8ea013a9d
     * groupId : 52e724cc-52f8-4cce-b43d-c318148d6e1a
     * groupName : 英语角
     * invitationUserId : 555240d9-0eaf-4c73-8742-703796b230cc
     * invitationTime : 2016-12-16 13:35:41
     * invitationUserName : 于洋
     * invitationUserAvatar : /UploadFiles/UploadAvatar/20160812/6f9414cc-6cc7-49d3-945a-ab0f00a14268.png
     * personalOpinions : 0
     */

    private List<InvitationListBean> invitationList;

    public List<ApplyListBean> getApplyList() {
        return applyList;
    }

    public void setApplyList(List<ApplyListBean> applyList) {
        this.applyList = applyList;
    }

    public List<InvitationListBean> getInvitationList() {
        return invitationList;
    }

    public void setInvitationList(List<InvitationListBean> invitationList) {
        this.invitationList = invitationList;
    }

    public static class ApplyListBean {
        private String applyId;
        private String groupId;
        private String groupName;
        private String applyUserAvatar;
        private String applyUserId;
        private String applyReason;
        private String applyUserRealName;
        private String applyTime;

        public String getApplyId() {
            return applyId;
        }

        public void setApplyId(String applyId) {
            this.applyId = applyId;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getApplyUserAvatar() {
            return applyUserAvatar;
        }

        public void setApplyUserAvatar(String applyUserAvatar) {
            this.applyUserAvatar = applyUserAvatar;
        }

        public String getApplyUserId() {
            return applyUserId;
        }

        public void setApplyUserId(String applyUserId) {
            this.applyUserId = applyUserId;
        }

        public String getApplyReason() {
            return applyReason;
        }

        public void setApplyReason(String applyReason) {
            this.applyReason = applyReason;
        }

        public String getApplyUserRealName() {
            return applyUserRealName;
        }

        public void setApplyUserRealName(String applyUserRealName) {
            this.applyUserRealName = applyUserRealName;
        }

        public String getApplyTime() {
            return applyTime;
        }

        public void setApplyTime(String applyTime) {
            this.applyTime = applyTime;
        }
    }

    public static class InvitationListBean {
        private String id;
        private String beInvitedUserId;
        private String groupId;
        private String groupName;
        private String invitationUserId;
        private String invitationTime;
        private String invitationUserName;
        private String invitationUserAvatar;
        private int personalOpinions;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBeInvitedUserId() {
            return beInvitedUserId;
        }

        public void setBeInvitedUserId(String beInvitedUserId) {
            this.beInvitedUserId = beInvitedUserId;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getInvitationUserId() {
            return invitationUserId;
        }

        public void setInvitationUserId(String invitationUserId) {
            this.invitationUserId = invitationUserId;
        }

        public String getInvitationTime() {
            return invitationTime;
        }

        public void setInvitationTime(String invitationTime) {
            this.invitationTime = invitationTime;
        }

        public String getInvitationUserName() {
            return invitationUserName;
        }

        public void setInvitationUserName(String invitationUserName) {
            this.invitationUserName = invitationUserName;
        }

        public String getInvitationUserAvatar() {
            return invitationUserAvatar;
        }

        public void setInvitationUserAvatar(String invitationUserAvatar) {
            this.invitationUserAvatar = invitationUserAvatar;
        }

        public int getPersonalOpinions() {
            return personalOpinions;
        }

        public void setPersonalOpinions(int personalOpinions) {
            this.personalOpinions = personalOpinions;
        }
    }
}
