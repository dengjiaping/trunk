package com.histudent.jwsoft.histudent.body.find.bean;

/**
 * 我的活动
 * Created by ZhangYT on 2016/8/17.
 */
public class PersonalHuoDongBean {


            /**
             * id : string
             * name : string
             * coverUrl : string
             * startTime : 2017-04-25T02:42:16.313Z
             * endTime : 2017-04-25T02:42:16.313Z
             * place : string
             * status : 0
             * signUpNum : 0
             * userCost : 0
             * isSinuped : true
             * isCreate : true
             */

            private String id;
            private String name;
            private String coverUrl;
            private String startTime;
            private String endTime;
            private String place;
            private int status;
            private int signUpNum;
            private int userCost;
            private boolean isSinuped;
            private boolean isCreate;

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

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getSignUpNum() {
                return signUpNum;
            }

            public void setSignUpNum(int signUpNum) {
                this.signUpNum = signUpNum;
            }

            public int getUserCost() {
                return userCost;
            }

            public void setUserCost(int userCost) {
                this.userCost = userCost;
            }

            public boolean getIsSinuped() {
                return isSinuped;
            }

            public void setIsSinuped(boolean isSinuped) {
                this.isSinuped = isSinuped;
            }

            public boolean getIsCreate() {
                return isCreate;
            }

            public void setIsCreate(boolean isCreate) {
                this.isCreate = isCreate;
            }


}
