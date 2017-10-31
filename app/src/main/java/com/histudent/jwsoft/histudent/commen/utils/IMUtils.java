package com.histudent.jwsoft.histudent.commen.utils;

import android.os.Handler;
import android.os.Message;

import com.histudent.jwsoft.histudent.HiStudentLog;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.cache.NimUserInfoCache;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.team.model.TeamMember;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/6/1.
 */
public class IMUtils {

    public static NimUserInfo userInfo;//搜索好友对象
    public static List<TeamMember> teamMembers = new ArrayList<>();
    private static NimUserInfo user;
    public static int GETUSERINFO = -100;


    /**
     * 获取当前登录用户资料
     *
     * @return
     */
    public static void getNimUserInfo(final Handler handler) {

        String account = NimUIKit.getAccount();

        final Message message = handler.obtainMessage();

        user = NimUserInfoCache.getInstance().getUserInfo(account);
        if (user == null) {
            NimUserInfoCache.getInstance().getUserInfoFromRemote(account, new RequestCallback<NimUserInfo>() {
                @Override
                public void onSuccess(NimUserInfo param) {

                    user = param;
                    message.what = GETUSERINFO;
                    message.obj = user;
                    handler.sendMessage(message);

                }

                @Override
                public void onFailed(int code) {
                    HiStudentLog.e("getUserInfoFromRemote failed:" + code);
                }

                @Override
                public void onException(Throwable exception) {
                    HiStudentLog.e("getUserInfoFromRemote exception:" + exception);
                }
            });
        } else {
            message.what = GETUSERINFO;
            message.obj = user;
            handler.sendMessage(message);
        }

    }

    /**
     * 获取当前指定用户资料
     *
     * @return
     */
    public static void getNimUserInfo(final Handler handler, String account) {

        final Message message = handler.obtainMessage();

        user = NimUserInfoCache.getInstance().getUserInfo(account);
        if (user == null) {
            NimUserInfoCache.getInstance().getUserInfoFromRemote(account, new RequestCallback<NimUserInfo>() {
                @Override
                public void onSuccess(NimUserInfo param) {

                    user = param;
                    message.what = GETUSERINFO;
                    message.obj = user;
                    handler.sendMessage(message);

                }

                @Override
                public void onFailed(int code) {
                    HiStudentLog.e("getUserInfoFromRemote failed:" + code);
                }

                @Override
                public void onException(Throwable exception) {
                    HiStudentLog.e("getUserInfoFromRemote exception:" + exception);
                }
            });
        } else {
            message.what = GETUSERINFO;
            message.obj = user;
            handler.sendMessage(message);
        }

    }


}
