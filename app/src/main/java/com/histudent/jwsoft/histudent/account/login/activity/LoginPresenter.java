package com.histudent.jwsoft.histudent.account.login.activity;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.HiStudentLog;
import com.histudent.jwsoft.histudent.account.login.model.CurrentUserInfoModel;
import com.histudent.jwsoft.histudent.account.login.parser.CurrentUserinfoParser;
import com.histudent.jwsoft.histudent.account.password.activity.FindPassWordActivity;
import com.histudent.jwsoft.histudent.account.regist.activity.RegistActivity;
import com.histudent.jwsoft.histudent.account.regist.bean.RegistBean;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.umeng.socialize.Config;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2016/10/26.
 */
public class LoginPresenter {

    private static LoginPresenter presenter;
    private BaseActivity context;

    public static LoginPresenter getIntentce() {
        if (presenter == null) {
            presenter = new LoginPresenter();
        }
        return presenter;
    }

    private LoginPresenter() {
    }

    /**
     * 找回密码
     */
    public void forgetPsw(Activity activity) {
        activity.startActivity(new Intent(activity, FindPassWordActivity.class));
    }

    /**
     * 用户注册
     */
    public void userRegister(Activity activity) {

        RegistActivity.start(activity, 200);

    }

    /**
     * 微信登录
     */
    public void loginByWweXin(BaseActivity activity) {
        context = activity;
//        UMShareAPI.get(activity).deleteOauth(activity, SHARE_MEDIA.WEIXIN, null);
        UMShareAPI.get(activity).getPlatformInfo(activity, SHARE_MEDIA.WEIXIN, umAuthListener);
        Config.dialogSwitch = false;
    }

    /**
     * QQ登录
     */
    public void loginByQQ(BaseActivity activity) {
        context = activity;
//        UMShareAPI.get(activity).deleteOauth(activity, SHARE_MEDIA.QQ, null);
        UMShareAPI.get(activity).getPlatformInfo(activity, SHARE_MEDIA.QQ, umAuthListener);
        Config.dialogSwitch = false;
    }

    /**
     * 判断第三方是否绑定以作不同处理
     */
    public void theOtherlogin(final SHARE_MEDIA platform, final Map<String, String> data) {
        Map<String, Object> map = new TreeMap<>();
        final String openId = data.get("openid");
        final int loginInType = platform == SHARE_MEDIA.QQ ? 1 : 2;
        map.put("loginInType", loginInType);
        map.put("openId", openId);
        HiStudentHttpUtils.postDataByOKHttp(true, context, map, HistudentUrl.getOpenIdBindInfo_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject object = new JSONObject(result);
                    int code = object.optInt("ret");
                    if (code == 1) {//已经绑定。直接登录
                        HiStudentLog.e(platform == SHARE_MEDIA.QQ ? "-----HI同学登录成功（QQ登录）------>" : "-----HI同学登录成功（微信登录）------>");
                        String data = object.optString("data");
                        CurrentUserInfoModel currentUserInfo = CurrentUserinfoParser.userinfoParser(data);
                        if (currentUserInfo != null) //开始云信登录
                            ((BaseLoginActivity) context).loginAction_YX(currentUserInfo, context, "", currentUserInfo.getImToken());
                    } else if (code == 400) {//没有绑定，进行绑定
                        RegistBean bean = new RegistBean();
                        bean.setOpenId(openId);
                        bean.setThirdparty(loginInType);
                        bean.setNickname(data.get("screen_name"));
                        bean.setAvatar(data.get("profile_image_url"));
                        BindPhoneActivity.start(context, bean);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    /**
     * 第三方登录接口回调
     */
    private UMAuthListener umAuthListener = new UMAuthListener() {

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            theOtherlogin(platform, data);

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(context, "授权失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(context, "取消授权", Toast.LENGTH_SHORT).show();
        }
    };

}
