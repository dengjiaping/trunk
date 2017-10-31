package com.histudent.jwsoft.histudent.bean;

import java.io.Serializable;

/**
 * Created by huyg on 2017/10/18.
 */


public class TaskItemBean implements Serializable{


    /**
     * id : adaf76db-9e60-4212-a676-ccf1cefc5842
     * name : 测试
     * startTime : 2017-10-22 00:00:00
     * endTime : 2017-10-31 23:59:59
     * todayStandardNum : 0
     * standardUserNum : 0
     * joinUserNum : 1
     * readbookNum : 0
     * standardDays : 10
     * isCreate : true
     */
    public Item items;

    public static class Item implements Serializable{
        private String id;
        private String name;
        private String startTime;
        private String endTime;
        private int todayStandardNum;
        private int standardUserNum;
        private int joinUserNum;
        private int readbookNum;
        private int standardDays;
        private boolean isCreate;
        private boolean isEveryDay;
        private String memo;

        public boolean isCreate() {
            return isCreate;
        }

        public void setCreate(boolean create) {
            isCreate = create;
        }

        public boolean isEveryDay() {
            return isEveryDay;
        }

        public void setEveryDay(boolean everyDay) {
            isEveryDay = everyDay;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
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

        public int getTodayStandardNum() {
            return todayStandardNum;
        }

        public void setTodayStandardNum(int todayStandardNum) {
            this.todayStandardNum = todayStandardNum;
        }

        public int getStandardUserNum() {
            return standardUserNum;
        }

        public void setStandardUserNum(int standardUserNum) {
            this.standardUserNum = standardUserNum;
        }

        public int getJoinUserNum() {
            return joinUserNum;
        }

        public void setJoinUserNum(int joinUserNum) {
            this.joinUserNum = joinUserNum;
        }

        public int getReadbookNum() {
            return readbookNum;
        }

        public void setReadbookNum(int readbookNum) {
            this.readbookNum = readbookNum;
        }

        public int getStandardDays() {
            return standardDays;
        }

        public void setStandardDays(int standardDays) {
            this.standardDays = standardDays;
        }

        public boolean isIsCreate() {
            return isCreate;
        }

        public void setIsCreate(boolean isCreate) {
            this.isCreate = isCreate;
        }
    }


}
