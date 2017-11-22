package com.histudent.jwsoft.histudent.model.entity;

import com.netease.nimlib.sdk.msg.model.IMMessage;

/**
 * Created by huyg on 2017/8/3.
 */

public class IMMessageEvent {
    public IMMessage message;

    public IMMessageEvent(IMMessage message) {
        this.message = message;
    }
}
