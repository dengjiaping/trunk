package com.histudent.jwsoft.histudent.body.myclass.bean;

/**
 * Created by ZhangYT on 2017/7/7.
 */

public class PointRecordModel {
    public static final int TYPE_CONTENT=0;
    public static final int TYPE_TITLE=1;

    private int type = TYPE_CONTENT;

    private String month;
    private int points;
    private int consumePoints;
    private RecordListBean recordListBean;

    public RecordListBean getRecordListBean() {
        return recordListBean;
    }

    public void setRecordListBean(RecordListBean recordListBean) {
        this.recordListBean = recordListBean;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getConsumePoints() {
        return consumePoints;
    }

    public void setConsumePoints(int consumePoints) {
        this.consumePoints = consumePoints;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
