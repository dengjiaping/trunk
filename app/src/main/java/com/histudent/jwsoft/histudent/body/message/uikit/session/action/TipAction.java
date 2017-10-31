package com.histudent.jwsoft.histudent.body.message.uikit.session.action;

import android.content.Context;
import android.text.TextUtils;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.view.Dialog_confirm;
import com.netease.nim.uikit.session.actions.BaseAction;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.model.CustomMessageConfig;
import com.netease.nimlib.sdk.msg.model.IMMessage;

/**
 * Tip类型消息测试
 * Created by hzxuwen on 2016/3/9.
 */
public class TipAction extends BaseAction {

    public TipAction() {
        super(R.drawable.message_plus_tip_selector,R.string.tip_message);
    }

    @Override
    public void onClick() {
        Exit(getActivity());
    }

    public void Exit(final Context context) {
        final Dialog_confirm confirmDialog = new Dialog_confirm(context, "取消", "发送");
        confirmDialog.show();
        confirmDialog.setClicklistener(new Dialog_confirm.ClickListenerInterface() {
            @Override
            public void doConfirm() {
                confirmDialog.dismiss();

            }

            @Override
            public void doCancel(String info) {
                confirmDialog.dismiss();
                sendTip(info);
            }
        });
    }

    private void sendTip(String info) {
        if (TextUtils.isEmpty(info)) return;
        IMMessage msg = MessageBuilder.createTipMessage(getAccount(), getSessionType());
        msg.setContent(info);

        CustomMessageConfig config = new CustomMessageConfig();
        config.enablePush = false; // 不推送
        msg.setConfig(config);

        sendMessage(msg);
    }
}
