package com.histudent.jwsoft.histudent.commen.helper;

import android.text.TextUtils;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2017/3/28.
 * 用于获取验证码的帮助类
 */

public class GetCodeHelper {

    private static GetCodeHelper helper;

    public static int TEXT = 1;
    public static int VOICE = 2;

    private GetCodeHelper() {
    }

    public static synchronized GetCodeHelper getIntentce() {
        if (helper == null) {
            helper = new GetCodeHelper();
        }
        return helper;
    }

    /**
     * 获取登录验证
     */
    public void sendCode_login(BaseActivity activity, String phoneNumber, int codeMsgType) {

        if (TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(activity, "请先输入手机号码", Toast.LENGTH_SHORT).show();
            return;
        } else if (phoneNumber.length() != 11 || !isMobileNO(phoneNumber)) {
            Toast.makeText(activity, "请先输入正确的手机号码", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> map = new TreeMap<>();
        map.put("codeTypeEnum", 14);
        map.put("codeType", "SafeConfirm");
        map.put("phone", phoneNumber);
        map.put("codeMsgType", codeMsgType);

        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.verfiyCode_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                SystemUtil.countDown(60);
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

    /**
     * 获取创建社群验证码
     */
    public void sendCode_Community(BaseActivity activity, String phoneNumber, int codeMsgType) {

        if (TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(activity, "请先输入手机号码", Toast.LENGTH_SHORT).show();
            return;
        } else if (phoneNumber.length() != 11 || !isMobileNO(phoneNumber)) {
            Toast.makeText(activity, "请先输入正确的手机号码", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> map = new TreeMap<>();
        map.put("codeTypeEnum", 16);
        map.put("phone", phoneNumber);
        map.put("codeMsgType", codeMsgType);
        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.verfiyCode_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                SystemUtil.countDown(60);
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

    /**
     * 获取创建社群语音验证码
     */
    public void sendCodeVoice_Community(BaseActivity activity, String phoneNumber, int codeMsgType) {

        if (TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(activity, "请先输入手机号码", Toast.LENGTH_SHORT).show();
            return;
        } else if (phoneNumber.length() != 11 || !isMobileNO(phoneNumber)) {
            Toast.makeText(activity, "请先输入正确的手机号码", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> map = new TreeMap<>();
        map.put("codeTypeEnum", 16);
        map.put("phone", phoneNumber);
        map.put("codeMsgType", codeMsgType);
        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.verfiyCode_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                SystemUtil.countDown(60);
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }


    /**
     * 获取绑定验证
     */
    public void sendCode_bind(BaseActivity activity, String phoneNumber, int codeMsgType) {

        if (TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(activity, "请先输入手机号码", Toast.LENGTH_SHORT).show();
            return;
        } else if (phoneNumber.length() != 11 || !isMobileNO(phoneNumber)) {
            Toast.makeText(activity, "请先输入正确的手机号码", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> map = new TreeMap<>();
        map.put("codeTypeEnum", 15);
        map.put("codeType", "SafeConfirm");
        map.put("phone", phoneNumber);
        map.put("codeMsgType", codeMsgType);

        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.verfiyCode_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                SystemUtil.countDown(60);
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

    /**
     * 获取注册验证
     */
    public void sendCode_regist(BaseActivity activity, String phoneNumber, int codeMsgType) {

        if (TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(activity, "请先输入手机号码", Toast.LENGTH_SHORT).show();
            return;
        } else if (phoneNumber.length() != 11 || !isMobileNO(phoneNumber)) {
            Toast.makeText(activity, "请先输入正确的手机号码", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> map = new TreeMap<>();
        map.put("codeTypeEnum", 1);
        map.put("phone", phoneNumber);
        map.put("codeType", "Register");
        map.put("codeMsgType", codeMsgType);

        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.verfiyCode_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                SystemUtil.countDown(60);
            }

            @Override
            public void onFailure(String msg) {
            }
        });
    }

    /**
     * 获取重置密码验证
     */
    public void sendCode_resetPwd(BaseActivity activity, String phoneNumber, int codeMsgType, HttpRequestCallBack callBack) {

        if (TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(activity, "请先输入手机号码", Toast.LENGTH_SHORT).show();
            return;
        } else if (phoneNumber.length() != 11 || !isMobileNO(phoneNumber)) {
            Toast.makeText(activity, "请先输入正确的手机号码", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> map = new TreeMap<>();
        map.put("codeTypeEnum", 2);
        map.put("phone", phoneNumber);
        map.put("codeType", "ResetPwd");
        map.put("codeMsgType", codeMsgType);

        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.verfiyCode_url, callBack);
    }

    /**
     * 判断手机号码的正确性
     *
     * @param mobiles
     * @return
     */
    public boolean isMobileNO(String mobiles) {
//        Pattern pattern = Pattern
//                .compile("^(13[0-9]|15[0-9]|153|15[6-9]|180|18[23]|18[5-9])\\d{8}$");
//        Matcher matcher = pattern.matcher(mobiles);

        if (mobiles.length()==11) {
            return true;
        }
        return false;
    }

}
