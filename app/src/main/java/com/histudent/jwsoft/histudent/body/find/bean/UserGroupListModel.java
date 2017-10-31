package com.histudent.jwsoft.histudent.body.find.bean;

import java.util.List;

/**
 * Created by ZhangYT on 2017/5/17.
 */

public class UserGroupListModel {


    /**
     * myCreateGroupNum : 3
     * myCreateGroups : [{"groupId":"ef3163bc-e005-426f-a8ac-3bfbbf0e9e5e","groupName":"嗨同学","groupLogo":"http://img.hitongx.com/UploadFiles/groupLogo/20161027/4d21980a-d7de-48a6-97f5-594900418b47.jpg@60w_60h_1e_1c_2o_30-1ci.jpg","groupMemberNum":1,"groupDescription":"羽毛球","groupUserId":"df3a0abd-415f-4e26-ac6c-00f8ea013a9d","isMember":true,"joinDate":"0001-01-01 00:00:00","createTime":"0001-01-01 00:00:00","isPublic":true,"isManager":true,"isGroupMaker":true,"isApplying":false,"tagId":"00000000-0000-0000-0000-000000000000","childTagId":"e59e1fae-fbe0-4622-a92e-0b6d941d5726","childTagName":"其他","isAttestation":false,"toDayVisitNum":0},{"groupId":"1681ec86-945e-4ef2-92fb-afa563686a42","groupName":"动漫社群","groupLogo":"http://img.hitongx.com/UploadFiles/groupLogo/20161216/191d147f-6655-460e-a591-ab40f8b9fb1c.jpg@60w_60h_1e_1c_2o_30-1ci.jpg","groupMemberNum":3,"groupDescription":"欢迎广大动漫爱好者参与","groupUserId":"df3a0abd-415f-4e26-ac6c-00f8ea013a9d","isMember":true,"joinDate":"0001-01-01 00:00:00","createTime":"0001-01-01 00:00:00","isPublic":true,"isManager":true,"isGroupMaker":true,"isApplying":false,"tagId":"00000000-0000-0000-0000-000000000000","childTagId":"e59e1fae-fbe0-4622-a92e-0b6d941d5726","childTagName":"其他","isAttestation":false,"toDayVisitNum":0},{"groupId":"9318e030-0f35-4bde-95e7-1e799ce994c1","groupName":"航模社群","groupLogo":"http://img.hitongx.com/UploadFiles/groupLogo/20160726/a3b449d5-b947-4acd-8257-afa35d57b03a.png@60w_60h_1e_1c_2o_30-1ci.png","groupMemberNum":6,"groupDescription":"<p>而特产是<\/p><p><span style=\"text-decoration: underline;\"><em><strong>发生vs<\/strong><\/em><\/span><\/p>","groupUserId":"df3a0abd-415f-4e26-ac6c-00f8ea013a9d","isMember":true,"joinDate":"0001-01-01 00:00:00","createTime":"0001-01-01 00:00:00","isPublic":false,"isManager":true,"isGroupMaker":true,"isApplying":false,"tagId":"00000000-0000-0000-0000-000000000000","childTagId":"ee557e8b-aa31-4f10-9c7d-e2ea307ec690","childTagName":"航模","isAttestation":false,"toDayVisitNum":0}]
     * myJoinGroupNum : 11
     * myJoinGroups : [{"groupId":"af80b32c-83cc-4b67-a404-8d3f4f210100","groupName":"他咯咯","groupLogo":"http://img.hitongx.com/UploadFiles/groupLogo/20161222/8c8e4f4d-90fa-4373-ba96-b1df8cd33920.jpg@60w_60h_1e_1c_2o_30-1ci.jpg","groupMemberNum":2,"groupDescription":"他咯咯","groupUserId":"9d47904f-567c-4019-acde-25713e1b3e5b","isMember":true,"joinDate":"0001-01-01 00:00:00","createTime":"0001-01-01 00:00:00","isPublic":false,"isManager":false,"isGroupMaker":false,"isApplying":false,"tagId":"00000000-0000-0000-0000-000000000000","childTagId":"b287102d-88dc-4ee3-b3fb-a48b71974faa","childTagName":"户外","isAttestation":false,"toDayVisitNum":0},{"groupId":"8c135a27-2b46-4aa9-b18b-710621c5d1b0","groupName":"啦啦啦模型我有用默默","groupLogo":"http://img.hitongx.com/UploadFiles/groupLogo/20161221/6196cdf6-b1fc-4e4d-8010-83f3ab9b1aa1.jpg@60w_60h_1e_1c_2o_30-1ci.jpg","groupMemberNum":4,"groupDescription":"<p>啦啦啦啦<\/p>","groupUserId":"24f8ecef-d045-4bd1-bcf4-8eb34ac59d7a","isMember":true,"joinDate":"0001-01-01 00:00:00","createTime":"0001-01-01 00:00:00","isPublic":true,"isManager":false,"isGroupMaker":false,"isApplying":false,"tagId":"00000000-0000-0000-0000-000000000000","childTagId":"7978aef3-1f01-45b2-ac93-26eccde1908f","childTagName":"舞蹈","isAttestation":false,"toDayVisitNum":0}]
     */

    private int myCreateGroupNum;
    private int myJoinGroupNum;
    private List<MyCreateGroupsBean> myCreateGroups;
    private List<MyJoinGroupsBean> myJoinGroups;

    public int getMyCreateGroupNum() {
        return myCreateGroupNum;
    }

    public void setMyCreateGroupNum(int myCreateGroupNum) {
        this.myCreateGroupNum = myCreateGroupNum;
    }

    public int getMyJoinGroupNum() {
        return myJoinGroupNum;
    }

    public void setMyJoinGroupNum(int myJoinGroupNum) {
        this.myJoinGroupNum = myJoinGroupNum;
    }

    public List<MyCreateGroupsBean> getMyCreateGroups() {
        return myCreateGroups;
    }

    public void setMyCreateGroups(List<MyCreateGroupsBean> myCreateGroups) {
        this.myCreateGroups = myCreateGroups;
    }

    public List<MyJoinGroupsBean> getMyJoinGroups() {
        return myJoinGroups;
    }

    public void setMyJoinGroups(List<MyJoinGroupsBean> myJoinGroups) {
        this.myJoinGroups = myJoinGroups;
    }

    public static class MyCreateGroupsBean {
        /**
         * groupId : ef3163bc-e005-426f-a8ac-3bfbbf0e9e5e
         * groupName : 嗨同学
         * groupLogo : http://img.hitongx.com/UploadFiles/groupLogo/20161027/4d21980a-d7de-48a6-97f5-594900418b47.jpg@60w_60h_1e_1c_2o_30-1ci.jpg
         * groupMemberNum : 1
         * groupDescription : 羽毛球
         * groupUserId : df3a0abd-415f-4e26-ac6c-00f8ea013a9d
         * isMember : true
         * joinDate : 0001-01-01 00:00:00
         * createTime : 0001-01-01 00:00:00
         * isPublic : true
         * isManager : true
         * isGroupMaker : true
         * isApplying : false
         * tagId : 00000000-0000-0000-0000-000000000000
         * childTagId : e59e1fae-fbe0-4622-a92e-0b6d941d5726
         * childTagName : 其他
         * isAttestation : false
         * toDayVisitNum : 0
         */

        private String groupId;
        private String groupName;
        private String groupLogo;
        private int groupMemberNum;
        private String groupDescription;
        private String groupUserId;
        private boolean isMember;
        private String joinDate;
        private String createTime;
        private boolean isPublic;
        private boolean isManager;
        private boolean isGroupMaker;
        private boolean isApplying;
        private String tagId;
        private String childTagId;
        private String childTagName;
        private boolean isAttestation;
        private int toDayVisitNum;

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

        public String getGroupLogo() {
            return groupLogo;
        }

        public void setGroupLogo(String groupLogo) {
            this.groupLogo = groupLogo;
        }

        public int getGroupMemberNum() {
            return groupMemberNum;
        }

        public void setGroupMemberNum(int groupMemberNum) {
            this.groupMemberNum = groupMemberNum;
        }

        public String getGroupDescription() {
            return groupDescription;
        }

        public void setGroupDescription(String groupDescription) {
            this.groupDescription = groupDescription;
        }

        public String getGroupUserId() {
            return groupUserId;
        }

        public void setGroupUserId(String groupUserId) {
            this.groupUserId = groupUserId;
        }

        public boolean isIsMember() {
            return isMember;
        }

        public void setIsMember(boolean isMember) {
            this.isMember = isMember;
        }

        public String getJoinDate() {
            return joinDate;
        }

        public void setJoinDate(String joinDate) {
            this.joinDate = joinDate;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public boolean isIsPublic() {
            return isPublic;
        }

        public void setIsPublic(boolean isPublic) {
            this.isPublic = isPublic;
        }

        public boolean isIsManager() {
            return isManager;
        }

        public void setIsManager(boolean isManager) {
            this.isManager = isManager;
        }

        public boolean isIsGroupMaker() {
            return isGroupMaker;
        }

        public void setIsGroupMaker(boolean isGroupMaker) {
            this.isGroupMaker = isGroupMaker;
        }

        public boolean isIsApplying() {
            return isApplying;
        }

        public void setIsApplying(boolean isApplying) {
            this.isApplying = isApplying;
        }

        public String getTagId() {
            return tagId;
        }

        public void setTagId(String tagId) {
            this.tagId = tagId;
        }

        public String getChildTagId() {
            return childTagId;
        }

        public void setChildTagId(String childTagId) {
            this.childTagId = childTagId;
        }

        public String getChildTagName() {
            return childTagName;
        }

        public void setChildTagName(String childTagName) {
            this.childTagName = childTagName;
        }

        public boolean isIsAttestation() {
            return isAttestation;
        }

        public void setIsAttestation(boolean isAttestation) {
            this.isAttestation = isAttestation;
        }

        public int getToDayVisitNum() {
            return toDayVisitNum;
        }

        public void setToDayVisitNum(int toDayVisitNum) {
            this.toDayVisitNum = toDayVisitNum;
        }
    }

    public static class MyJoinGroupsBean {
        /**
         * groupId : af80b32c-83cc-4b67-a404-8d3f4f210100
         * groupName : 他咯咯
         * groupLogo : http://img.hitongx.com/UploadFiles/groupLogo/20161222/8c8e4f4d-90fa-4373-ba96-b1df8cd33920.jpg@60w_60h_1e_1c_2o_30-1ci.jpg
         * groupMemberNum : 2
         * groupDescription : 他咯咯
         * groupUserId : 9d47904f-567c-4019-acde-25713e1b3e5b
         * isMember : true
         * joinDate : 0001-01-01 00:00:00
         * createTime : 0001-01-01 00:00:00
         * isPublic : false
         * isManager : false
         * isGroupMaker : false
         * isApplying : false
         * tagId : 00000000-0000-0000-0000-000000000000
         * childTagId : b287102d-88dc-4ee3-b3fb-a48b71974faa
         * childTagName : 户外
         * isAttestation : false
         * toDayVisitNum : 0
         */

        private String groupId;
        private String groupName;
        private String groupLogo;
        private int groupMemberNum;
        private String groupDescription;
        private String groupUserId;
        private boolean isMember;
        private String joinDate;
        private String createTime;
        private boolean isPublic;
        private boolean isManager;
        private boolean isGroupMaker;
        private boolean isApplying;
        private String tagId;
        private String childTagId;
        private String childTagName;
        private boolean isAttestation;
        private int toDayVisitNum;

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

        public String getGroupLogo() {
            return groupLogo;
        }

        public void setGroupLogo(String groupLogo) {
            this.groupLogo = groupLogo;
        }

        public int getGroupMemberNum() {
            return groupMemberNum;
        }

        public void setGroupMemberNum(int groupMemberNum) {
            this.groupMemberNum = groupMemberNum;
        }

        public String getGroupDescription() {
            return groupDescription;
        }

        public void setGroupDescription(String groupDescription) {
            this.groupDescription = groupDescription;
        }

        public String getGroupUserId() {
            return groupUserId;
        }

        public void setGroupUserId(String groupUserId) {
            this.groupUserId = groupUserId;
        }

        public boolean isIsMember() {
            return isMember;
        }

        public void setIsMember(boolean isMember) {
            this.isMember = isMember;
        }

        public String getJoinDate() {
            return joinDate;
        }

        public void setJoinDate(String joinDate) {
            this.joinDate = joinDate;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public boolean isIsPublic() {
            return isPublic;
        }

        public void setIsPublic(boolean isPublic) {
            this.isPublic = isPublic;
        }

        public boolean isIsManager() {
            return isManager;
        }

        public void setIsManager(boolean isManager) {
            this.isManager = isManager;
        }

        public boolean isIsGroupMaker() {
            return isGroupMaker;
        }

        public void setIsGroupMaker(boolean isGroupMaker) {
            this.isGroupMaker = isGroupMaker;
        }

        public boolean isIsApplying() {
            return isApplying;
        }

        public void setIsApplying(boolean isApplying) {
            this.isApplying = isApplying;
        }

        public String getTagId() {
            return tagId;
        }

        public void setTagId(String tagId) {
            this.tagId = tagId;
        }

        public String getChildTagId() {
            return childTagId;
        }

        public void setChildTagId(String childTagId) {
            this.childTagId = childTagId;
        }

        public String getChildTagName() {
            return childTagName;
        }

        public void setChildTagName(String childTagName) {
            this.childTagName = childTagName;
        }

        public boolean isIsAttestation() {
            return isAttestation;
        }

        public void setIsAttestation(boolean isAttestation) {
            this.isAttestation = isAttestation;
        }

        public int getToDayVisitNum() {
            return toDayVisitNum;
        }

        public void setToDayVisitNum(int toDayVisitNum) {
            this.toDayVisitNum = toDayVisitNum;
        }
    }
}
