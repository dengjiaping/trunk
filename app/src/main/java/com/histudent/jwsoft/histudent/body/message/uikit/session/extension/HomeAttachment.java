package com.histudent.jwsoft.histudent.body.message.uikit.session.extension;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.histudent.jwsoft.histudent.body.message.uikit.model.MsgModel;
import com.netease.nim.uikit.session.viewholder.CustomAttachment;

/**
 * Created by zhoujianghua on 2015/4/9.
 */
public class HomeAttachment extends CustomAttachment {


    String json;

    public HomeAttachment(String json) {
        super(CustomAttachmentType.HomeWork);
        this.json = json;
    }

    @Override
    protected void parseData(JSONObject data) {
//        this.data = data;
    }

    @Override
    protected JSONObject packData() {
        return null;
    }

    public MsgModel getMsgModel() {
        return TextUtils.isEmpty(json) ? null : JSONObject.parseObject(json, MsgModel.class);
    }

}
