package com.histudent.jwsoft.histudent.entity;

import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.List;

/**
 * Created by huyg on 2017/8/3.
 */

public class IMMessageEvent {
    public IMMessage message;

    public IMMessageEvent(IMMessage message) {
        this.message = message;
    }
}
