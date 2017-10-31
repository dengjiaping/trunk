package com.histudent.jwsoft.histudent.body.myclass.bean;

/**
 * Created by huyg on 2017/7/7.
 */

public  class RecordListBean{
    /**
     * pointItemName : [日常任务]分享班级圈
     * experiencePoints : 5
     * requtationPoints : 5
     * createTime : 2017-06-08 17:09:05
     * points : 5
     */

    private String pointItemName;
    private int experiencePoints;
    private int requtationPoints;
    private String createTime;
    private int points;

    public String getPointItemName() {
        return pointItemName;
    }

    public void setPointItemName(String pointItemName) {
        this.pointItemName = pointItemName;
    }

    public int getExperiencePoints() {
        return experiencePoints;
    }

    public void setExperiencePoints(int experiencePoints) {
        this.experiencePoints = experiencePoints;
    }

    public int getRequtationPoints() {
        return requtationPoints;
    }

    public void setRequtationPoints(int requtationPoints) {
        this.requtationPoints = requtationPoints;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
