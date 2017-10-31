package com.histudent.jwsoft.histudent.commen.bean;

import java.io.Serializable;
import java.util.List;

/**
 *
 * 申请加入社群或者班级请求实体类
 * Created by ZhangYT on 2016/8/26.
 */
public class GroupNewsModel implements Serializable {

        private List<ApplyListBean> applyList;
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

        public static class ApplyListBean implements Serializable {
            /**
             * applyUserId : string
             * applyUserAvatar : string
             * applyUserRealName : string
             * groupId : string
             * groupName : string
             * applyTime : string
             * applyId : string
             * applyReason : string
             */

            private String applyUserId;
            private String applyUserAvatar;
            private String applyUserRealName;
            private String groupId;
            private String groupName;
            private String applyTime;
            private String applyId;
            private String applyReason;

            public String getApplyUserId() {
                return applyUserId;
            }

            public void setApplyUserId(String applyUserId) {
                this.applyUserId = applyUserId;
            }

            public String getApplyUserAvatar() {
                return applyUserAvatar;
            }

            public void setApplyUserAvatar(String applyUserAvatar) {
                this.applyUserAvatar = applyUserAvatar;
            }

            public String getApplyUserRealName() {
                return applyUserRealName;
            }

            public void setApplyUserRealName(String applyUserRealName) {
                this.applyUserRealName = applyUserRealName;
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

            public String getApplyTime() {
                return applyTime;
            }

            public void setApplyTime(String applyTime) {
                this.applyTime = applyTime;
            }

            public String getApplyId() {
                return applyId;
            }

            public void setApplyId(String applyId) {
                this.applyId = applyId;
            }

            public String getApplyReason() {
                return applyReason;
            }

            public void setApplyReason(String applyReason) {
                this.applyReason = applyReason;
            }
        }

        public static class InvitationListBean implements Serializable {
            /**
             * id : string
             * groupId : string
             * groupName : string
             * invitationUserId : string
             * invitationUserName : string
             * invitationUserAvatar : string
             * beInvitedUserId : string
             * personalOpinions : 0
             * invitationTime : 2017-05-17T06:55:54.190Z
             */

            private String id;
            private String invitationId;
            private String groupId;
            private String groupName;
            private String invitationUserId;
            private String invitationUserName;
            private String invitationUserAvatar;
            private String beInvitedUserId;
            private int personalOpinions;
            private String invitationTime;

            public String getInvitationId() {
                return invitationId;
            }

            public void setInvitationId(String invitationId) {
                this.invitationId = invitationId;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public String getBeInvitedUserId() {
                return beInvitedUserId;
            }

            public void setBeInvitedUserId(String beInvitedUserId) {
                this.beInvitedUserId = beInvitedUserId;
            }

            public int getPersonalOpinions() {
                return personalOpinions;
            }

            public void setPersonalOpinions(int personalOpinions) {
                this.personalOpinions = personalOpinions;
            }

            public String getInvitationTime() {
                return invitationTime;
            }

            public void setInvitationTime(String invitationTime) {
                this.invitationTime = invitationTime;
            }
        }
}
