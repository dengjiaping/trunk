package com.histudent.jwsoft.histudent.entity;

/**
 * Created by huyg on 2017/10/14.
 *
 */

public class AudioPlayStatusEvent {

    public int status;//0,播放结束，-1失败 ,1 播放准备

    public AudioPlayStatusEvent(int status) {
        this.status = status;
    }
}
