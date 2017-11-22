package com.histudent.jwsoft.histudent.model.entity;

/**
 * Created by lichaojie on 2017/10/20.
 * desc:
 * 班级应用 提示信息
 */

public class ClassApplicationMessageEvent {
    private int readClockCount;
    private int homeworkCount;
    private int readPunch;

    public int getReadPunch() {
        return readPunch;
    }

    public void setReadPunch(int readPunch) {
        this.readPunch = readPunch;
    }

    public int getReadClockCount() {
        return readClockCount;
    }

    public ClassApplicationMessageEvent setReadClockCount(int readClockCount) {
        this.readClockCount = readClockCount;
        return this;
    }

    public int getHomeworkCount() {
        return homeworkCount;
    }

    public ClassApplicationMessageEvent setHomeworkCount(int homeworkCount) {
        this.homeworkCount = homeworkCount;
        return this;
    }
}
