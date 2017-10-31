package com.histudent.jwsoft.histudent.body.message.uikit.session.action;

import com.histudent.jwsoft.histudent.R;
import com.netease.nim.uikit.session.actions.BaseAction;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by hzxuwen on 2015/6/11.
 */
public class WorkAction extends BaseAction {

    public WorkAction() {
        super(R.drawable.message_plus_work_selector, R.string.input_panel_work);
    }

    @Override
    public void onClick() {
        EventBus.getDefault().post(2);
    }
}
