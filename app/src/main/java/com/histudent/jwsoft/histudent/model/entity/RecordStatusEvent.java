package com.histudent.jwsoft.histudent.model.entity;

/**
 * Created by huyg on 2017/10/24.
 */

public class RecordStatusEvent {


    public int status;//0,录音成功，-1录音失败 ,1 播放准备,2 录音开始,3 录音到达最大长度

    public RecordStatusEvent(int status) {
        this.status = status;
    }
}
