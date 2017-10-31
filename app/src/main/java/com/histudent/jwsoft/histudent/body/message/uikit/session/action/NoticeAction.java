package com.histudent.jwsoft.histudent.body.message.uikit.session.action;

import com.histudent.jwsoft.histudent.R;
import com.netease.nim.uikit.session.actions.BaseAction;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by hzxuwen on 2015/6/11.
 */
public class NoticeAction extends BaseAction {

    public NoticeAction() {
        super(R.drawable.message_plus_notice_selector, R.string.input_panel_notice);
    }

    @Override
    public void onClick() {
        EventBus.getDefault().post(1);
    }
}
