package com.histudent.jwsoft.histudent.account.login.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.HTApplication;
import com.histudent.jwsoft.histudent.HiStudentLog;
import com.histudent.jwsoft.histudent.account.login.model.CurrentUserInfoModel;
import com.histudent.jwsoft.histudent.account.login.parser.CurrentUserinfoParser;
import com.histudent.jwsoft.histudent.account.regist.activity.NewPersonActivity;
import com.histudent.jwsoft.histudent.body.HTMainActivity;
import com.histudent.jwsoft.histudent.body.message.uikit.DemoCache;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.keyword.utils.SharedPreferencedUtils;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.LocationUtils;
import com.histudent.jwsoft.histudent.comment2.utils.ActivityCollector;
import com.histudent.jwsoft.histudent.constant.ParamKeys;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

import java.util.Map;
import java.util.TreeMap;

import static com.histudent.jwsoft.histudent.body.myclass.fragment.ClassFragment.HOMEWORK_SIGN;
import static com.histudent.jwsoft.histudent.body.myclass.fragment.ClassFragment.READ_CLOCK_NEW_SIGN;

/**
 * Created by liuguiyu-pc on 2016/12/9.
 */

public abstract class BaseLoginActivity extends BaseActivity {

    public static int NOMARL = 1;
    public static int REGIST = 2;
    private int flag = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 用于账号登录的函数（hi同学）
     *
     * @param userName
     * @param passWord
     */
    public void loginAction(final BaseActivity activity, final String userName, final String passWord, int flag) {

        final Map<String, Object> map = new TreeMap<>();
        map.put("userName", userName);
        map.put("pwd", passWord);

        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.login_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                changeShowAppStatus(userName);
                HiStudentLog.e("-----HI同学登录成功（账号密码）------>");
                CurrentUserInfoModel currentUserInfo = CurrentUserinfoParser.userinfoParser(result);
                if (currentUserInfo != null)
                    loginAction_YX(currentUserInfo, activity, userName, passWord);

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    /**
     * 用于手机验证码登录的函数（hi同学）
     *
     * @param phoneNumber
     * @param phoneCode
     */
    public void loginActionByPhone(final BaseActivity activity, final String phoneNumber, String phoneCode) {

        final Map<String, Object> map = new TreeMap<>();
        map.put("mobile", phoneNumber);
        map.put("code", phoneCode);

        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.loginByMobile_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                changeShowAppStatus(phoneNumber);
                HiStudentLog.e("-----HI同学登录成功（手机验证码）------>");
                CurrentUserInfoModel currentUserInfo = CurrentUserinfoParser.userinfoParser(result);

                if (currentUserInfo != null)
                    loginAction_YX(currentUserInfo, activity, phoneNumber, currentUserInfo.getImToken());
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    /**
     * 用于登录的函数（云信）
     */
    public void loginAction_YX(final CurrentUserInfoModel currentUserInfo, final BaseActivity activity, final String userName, final String passWord) {

        showLoadingImage(activity, LoadingType.FLOWER);

        LoginInfo info = new LoginInfo(currentUserInfo.getUserId().replace("-", ""), currentUserInfo.getImToken());
        NIMClient.getService(AuthService.class).login(info).setCallback(new RequestCallbackWrapper() {
            @Override
            public void onResult(int i, Object o, Throwable throwable) {

                closeLoadingImage();

                HiStudentLog.e("-----云信登录返回------>" + i);

                if (i == ResponseCode.RES_SUCCESS) {

                    if (!LocationUtils.isSucess)
                        LocationUtils.getLocationUtils().getLocationInfor(activity, null);

                    HiCache.getInstance().exchangeLoginStatue(1);
                    HiCache.getInstance().saveLoginUserInfo(currentUserInfo);
                    String token = currentUserInfo.getToken();
                    SharedPreferencedUtils.setString(HTApplication.getInstance(), ParamKeys.TOKEN, token);
                    HiCache.getInstance().init(userName, passWord, currentUserInfo);
                    DemoCache.setAccount(currentUserInfo.getUserId().replace("-", ""));
                    if (flag == NOMARL) {
                        HTMainActivity.start(activity);
                    } else if (flag == REGIST) {
                        NewPersonActivity.start(activity);
                    }

                    activity.finish();
                    ActivityCollector.finishAll();
                } else {
                    Toast.makeText(activity, "登录失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /**
     * 登录成功后 根据用户信息去判断  当前用户是否切换新的账号
     * 便于更新班级页面 app icon 类型 右上角是否显示new状态
     */
    private void changeShowAppStatus(String account) {
        final CurrentUserInfoModel loginUserInfo = HiCache.getInstance().getLoginUserInfo();
        if(loginUserInfo != null){
            final String mobile = loginUserInfo.getMobile();
            if (mobile != null && account != null) {
                if (!mobile.equals(account)) {
                    HTApplication
                            .getInstance()
                            .getSharedPreferences(READ_CLOCK_NEW_SIGN, Context.MODE_PRIVATE)
                            .edit().putBoolean(READ_CLOCK_NEW_SIGN, true).apply();
                    HTApplication
                            .getInstance()
                            .getSharedPreferences(HOMEWORK_SIGN, Context.MODE_PRIVATE)
                            .edit().putBoolean(HOMEWORK_SIGN, true).apply();
                }
            }
        }
    }

}
