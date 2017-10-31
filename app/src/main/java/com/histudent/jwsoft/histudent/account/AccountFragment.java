package com.histudent.jwsoft.histudent.account;

import android.os.Handler;
import android.widget.Button;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.fragment.BaseFragment;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2016/12/26.
 */

public class AccountFragment extends BaseFragment {

    /**
     * 请求发送验证码（新用户注册）
     *
     * @param handler
     * @param what
     */
    public void sendVerfiyCode_1(String phoneNumber, final Handler handler, final int what) {

        Map<String, Object> map = new TreeMap<>();
        map.put("codeTypeEnum", 1);
        map.put("phone", phoneNumber);

        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) getActivity(), map, HistudentUrl.verfiyCode_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                handler.sendEmptyMessage(what);

            }

            @Override
            public void onFailure(String msg) {
                handler.sendEmptyMessage(-1);
            }
        });
    }

    /**
     * 请求发送验证码(重置密码)
     *
     * @param handler
     * @param what
     */
    public void sendVerfiyCode_2(String phoneNumber, final Handler handler, final int what) {

        Map<String, Object> map = new TreeMap<>();
        map.put("codeTypeEnum", 2);
        map.put("phone", phoneNumber);

        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) getActivity(), map, HistudentUrl.verfiyCode_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                handler.sendEmptyMessage(what);

            }

            @Override
            public void onFailure(String msg) {
                handler.sendEmptyMessage(-1);
            }
        });
    }

    public void sendVerfiyCode_2(String phoneNumber, HttpRequestCallBack callBack) {

        Map<String, Object> map = new TreeMap<>();
        map.put("codeTypeEnum", 2);
        map.put("phone", phoneNumber);

        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) getActivity(), map, HistudentUrl.verfiyCode_url, callBack);
    }

    /**
     * 修改Button的背景与可否点击
     *
     * @param codeText
     * @param flag
     */
    public void exchangeButtonBG(Button codeText, boolean flag) {
        if (flag) {
            codeText.setBackgroundResource(R.drawable.blue_button_bg);
        } else {
            codeText.setBackgroundResource(R.drawable.gray_button_bg);
        }
        codeText.setEnabled(flag);
    }

}
