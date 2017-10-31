package com.histudent.jwsoft.histudent.body.message.fragment;

import android.util.Log;

import com.histudent.jwsoft.histudent.body.message.uikit.session.activity.TeamMessageActivity;
import com.netease.nim.uikit.session.fragment.TeamMessageFragment;
import com.netease.nimlib.sdk.msg.model.IMMessage;

/**
 * Created by ZhangYT on 2017/7/18.
 */

public class MyTeamMessageFragment extends TeamMessageFragment{


    @Override
    public boolean sendMessage(IMMessage message) {
        if (super.sendMessage(message)){
            ((TeamMessageActivity)getActivity()).recordMessage(message);
            return true;
        }
        return false;
    }
}
