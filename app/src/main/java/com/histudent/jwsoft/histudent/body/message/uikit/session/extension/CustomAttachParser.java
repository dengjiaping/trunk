package com.histudent.jwsoft.histudent.body.message.uikit.session.extension;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachmentParser;

/**
 * Created by zhoujianghua on 2015/4/9.
 */
public class CustomAttachParser implements MsgAttachmentParser {

    private static final String KEY_TYPE = "type";
    private static final String KEY_DATA = "data";

    @Override
    public MsgAttachment parse(String json) {
        CustomAttachment attachment = null;
        try {
            JSONObject object = JSON.parseObject(json);
            int type = object.getInteger(KEY_TYPE);
            JSONObject data = object.getJSONObject(KEY_DATA);

            switch (type) {
                case CustomAttachmentType.HomeWork://家庭作业
                case CustomAttachmentType.HUODONG_G://班级活动
                case CustomAttachmentType.HUODONG_C://社群活动
                    return new HomeAttachment(json);

                case CustomAttachmentType.NOTICE://通知
                    return new NoticeAttachment(data);

                case CustomAttachmentType.Guess:
                    GuessAttachment guessAttachment = new GuessAttachment();
                    guessAttachment.fromJson(data);
                    return guessAttachment;

                case CustomAttachmentType.SnapChat:
                    return new SnapChatAttachment(data);

                case CustomAttachmentType.Sticker:
                    return new StickerAttachment();

                case CustomAttachmentType.RTS:
                    return new RTSAttachment();

                default:
                    return new DefaultCustomAttachment();
            }

//            if (attachment != null) {
//                attachment.fromJson(data);
//            }

        } catch (Exception e) {

        }

        return attachment;
    }

    public static String packData(int type, JSONObject data) {
        JSONObject object = new JSONObject();
        object.put(KEY_TYPE, type);
        if (data != null) {
            object.put(KEY_DATA, data);
        }

        return object.toJSONString();
    }
}
