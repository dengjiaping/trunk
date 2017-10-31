package com.histudent.jwsoft.histudent.body.message.uikit.session.extension;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by huangjun on 2015/7/28.
 */
public class NoticeAttachment extends CustomAttachment {

    private JSONObject data;

    public NoticeAttachment(JSONObject data) {
        super(CustomAttachmentType.NOTICE);
        this.data = data;
    }

    @Override
    protected JSONObject packData() {
        return null;
    }

    @Override
    protected void parseData(JSONObject data) {
        this.data = data;
    }

    public String getTitle() {
        return data.getString("title");
    }

    public String getContent() {
        return data.getString("content");
    }

    public String getMsg() {
        return data.getString("Msg");
    }

}
