package com.histudent.jwsoft.histudent.entity;

/**
 * Created by lichaojie on 2017/10/20.
 * desc:
 * 阅读打消息
 */

public class ReadClockMessageEvent {

    private int count;

    public ReadClockMessageEvent(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
