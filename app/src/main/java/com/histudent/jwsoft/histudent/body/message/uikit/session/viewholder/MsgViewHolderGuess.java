package com.histudent.jwsoft.histudent.body.message.uikit.session.viewholder;

import com.histudent.jwsoft.histudent.body.message.uikit.session.extension.GuessAttachment;
import com.netease.nim.uikit.session.viewholder.MsgViewHolderText;

/**
 * Created by zhoujianghua on 2015/8/4.
 */
public class MsgViewHolderGuess extends MsgViewHolderText {

    @Override
    protected String getDisplayText() {
        GuessAttachment attachment = (GuessAttachment) message.getAttachment();

        return attachment.getValue().getDesc() + "!";
    }
}
