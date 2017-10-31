package com.histudent.jwsoft.histudent.body.find.bean;

import java.util.List;

/**
 *
 * 活动详情
 * Created by ZhangYT on 2016/8/17.
 */
public class HuoDongDetailsModel {



        /**
         * isSinuped : false
         * sinupedUsers : [{"userId":"16430800-51c8-4017-a67c-9e5f9b93d5b0","name":"刘思","avatar":"http://img.hitongx.com/UploadFiles/UploadAvatar/20170514/2a602e49-bad6-430a-92f1-49aba5211466.jpg@60w_60h_1e_1c_2o_30-1ci.jpg"}]
         * introImgList : [{"imgId":"0a8b16e1-b225-4f6d-ae3c-c04fcc500f0d","savePath":"http://img.hitongx.com/HuodongCover/Class/20170608/4fcd4a19-b173-4e96-b0e2-fc9bd30d5f0b.jpg@700w_400h_1e_1l_2o.jpg.jpg?wh=1280_720","width":1280,"height":720},{"imgId":"85df6c01-9053-47ec-a08e-502253962e50","savePath":"http://img.hitongx.com/HuodongCover/Class/20170608/be9f86f2-92fe-494e-ba2e-db69d3ed61b1.jpg@700w_400h_1e_1l_2o.jpg.jpg?wh=270_480","width":270,"height":480}]
         * shareUrl : http://192.168.0.252:8020/app/?token={token}#/classeventdetails?huodongId=fd2c4ab9-5b01-4159-b9d6-1c10f5e1d3f8
         * isMember : true
         * id : fd2c4ab9-5b01-4159-b9d6-1c10f5e1d3f8
         * name : 下一站
         * coverUrl : http://img.hitongx.com/HuodongCover/Class/20170608/f94809a8-72fa-4b84-9598-b3a1fb3984bd.jpg@750w_400h_1e_1l_2o.jpg.jpg
         * startTime : 2017-06-09 21:46:00
         * endTime : 2017-06-14 21:46:00
         * place : 北部软件园培训中心
         * longitude : 120.11
         * latitude : 30.33
         * status : 0
         * alarmType : 0
         * maxUserNum : 0
         * signUpNum : 1
         * introduction : 摇一摇
         * ownerId : 6d1c2244-af70-4253-832e-fae92b284ae2
         * ownerName : 4班
         * ownerType : 1
         * schoolId : 3633000601
         * schoolName : 浙江省建筑安装中等专业学校
         * gradeId : 5f2d9260-6d95-4982-9948-e8dc05fb4134
         * gradeName : 2016级
         * isDelete : false
         * createUser : 16430800-51c8-4017-a67c-9e5f9b93d5b0
         * createName : 刘思
         * createTime : 2017-06-08 21:46:51
         * updateUser : 16430800-51c8-4017-a67c-9e5f9b93d5b0
         * updateName : 刘思
         * updateTime : 2017-06-08 21:46:51
         */

        private boolean isSinuped;
        private String shareUrl;
        private boolean isMember;
        private String id;
        private String name;
        private String coverUrl;
        private String startTime;
        private String endTime;
        private String place;
        private double longitude;
        private double latitude;
        private int status;
        private int alarmType;
        private int maxUserNum;
        private int signUpNum;
        private String introduction;
        private String ownerId;
        private String ownerName;
        private int ownerType;
        private String schoolId;
        private String schoolName;
        private String gradeId;
        private String gradeName;
        private boolean isDelete;
        private String createUser;
        private String createName;
        private String createTime;
        private String updateUser;
        private String updateName;
        private String updateTime;
        private List<SinupedUsersBean> sinupedUsers;
        private List<IntroImgListBean> introImgList;

        public boolean isIsSinuped() {
            return isSinuped;
        }

        public void setIsSinuped(boolean isSinuped) {
            this.isSinuped = isSinuped;
        }

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }

        public boolean isIsMember() {
            return isMember;
        }

        public void setIsMember(boolean isMember) {
            this.isMember = isMember;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCoverUrl() {
            return coverUrl;
        }

        public void setCoverUrl(String coverUrl) {
            this.coverUrl = coverUrl;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getAlarmType() {
            return alarmType;
        }

        public void setAlarmType(int alarmType) {
            this.alarmType = alarmType;
        }

        public int getMaxUserNum() {
            return maxUserNum;
        }

        public void setMaxUserNum(int maxUserNum) {
            this.maxUserNum = maxUserNum;
        }

        public int getSignUpNum() {
            return signUpNum;
        }

        public void setSignUpNum(int signUpNum) {
            this.signUpNum = signUpNum;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(String ownerId) {
            this.ownerId = ownerId;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }

        public int getOwnerType() {
            return ownerType;
        }

        public void setOwnerType(int ownerType) {
            this.ownerType = ownerType;
        }

        public String getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(String schoolId) {
            this.schoolId = schoolId;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }

        public String getGradeId() {
            return gradeId;
        }

        public void setGradeId(String gradeId) {
            this.gradeId = gradeId;
        }

        public String getGradeName() {
            return gradeName;
        }

        public void setGradeName(String gradeName) {
            this.gradeName = gradeName;
        }

        public boolean isIsDelete() {
            return isDelete;
        }

        public void setIsDelete(boolean isDelete) {
            this.isDelete = isDelete;
        }

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }

        public String getCreateName() {
            return createName;
        }

        public void setCreateName(String createName) {
            this.createName = createName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateUser() {
            return updateUser;
        }

        public void setUpdateUser(String updateUser) {
            this.updateUser = updateUser;
        }

        public String getUpdateName() {
            return updateName;
        }

        public void setUpdateName(String updateName) {
            this.updateName = updateName;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public List<SinupedUsersBean> getSinupedUsers() {
            return sinupedUsers;
        }

        public void setSinupedUsers(List<SinupedUsersBean> sinupedUsers) {
            this.sinupedUsers = sinupedUsers;
        }

        public List<IntroImgListBean> getIntroImgList() {
            return introImgList;
        }

        public void setIntroImgList(List<IntroImgListBean> introImgList) {
            this.introImgList = introImgList;
        }

        public static class SinupedUsersBean {
            /**
             * userId : 16430800-51c8-4017-a67c-9e5f9b93d5b0
             * name : 刘思
             * avatar : http://img.hitongx.com/UploadFiles/UploadAvatar/20170514/2a602e49-bad6-430a-92f1-49aba5211466.jpg@60w_60h_1e_1c_2o_30-1ci.jpg
             */

            private String userId;
            private String name;
            private String avatar;
            private int userType;

            public int getUserType() {
                return userType;
            }

            public void setUserType(int userType) {
                this.userType = userType;
            }

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

        public static class IntroImgListBean {
            /**
             * imgId : 0a8b16e1-b225-4f6d-ae3c-c04fcc500f0d
             * savePath : http://img.hitongx.com/HuodongCover/Class/20170608/4fcd4a19-b173-4e96-b0e2-fc9bd30d5f0b.jpg@700w_400h_1e_1l_2o.jpg.jpg?wh=1280_720
             * width : 1280
             * height : 720
             */

            private String imgId;
            private String savePath;
            private int width;
            private int height;

            public String getImgId() {
                return imgId;
            }

            public void setImgId(String imgId) {
                this.imgId = imgId;
            }

            public String getSavePath() {
                return savePath;
            }

            public void setSavePath(String savePath) {
                this.savePath = savePath;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }
        }

}
