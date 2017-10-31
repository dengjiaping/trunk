package com.histudent.jwsoft.histudent.account.login.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.account.login.model.CurrentUserInfoModel;
import com.histudent.jwsoft.histudent.account.login.parser.CurrentUserinfoParser;
import com.histudent.jwsoft.histudent.account.regist.activity.SeletRegistRankActivity;
import com.histudent.jwsoft.histudent.account.regist.bean.RegistBean;
import com.histudent.jwsoft.histudent.commen.helper.GetCodeHelper;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.comment2.utils.ActivityCollector;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.TreeMap;


/**
 * Created by liuguiyu-pc on 2017/4/11.
 * 用于手机绑定第三方平台
 */

public class BindPhoneActivity extends BaseLoginActivity {

    private EditText bind_phone_edit, bind_phone_code_edit;
    private Button code_button;
    private TextView link_clik, link_clik_withLine, bind_nextbutton;
    private RegistBean bean;

    public static void start(Activity activity, RegistBean bean) {
        Intent intent = new Intent(activity, BindPhoneActivity.class);
        intent.putExtra("RegistBean", bean);
        activity.startActivity(intent);
    }

    @Override
    public int setViewLayout() {
        ActivityCollector.addActivity(this);
        return R.layout.activity_bind_phone;
    }

    @Override
    public void initView() {

        bean = (RegistBean) getIntent().getSerializableExtra("RegistBean");

        bind_phone_edit = (EditText) findViewById(R.id.bind_phone_edit);
        bind_phone_code_edit = (EditText) findViewById(R.id.bind_phone_code_edit);
        code_button = (Button) findViewById(R.id.bind_phone_code_button);
        link_clik = (TextView) findViewById(R.id.link_clik);
        link_clik_withLine = (TextView) findViewById(R.id.link_clik_withLine);
        bind_nextbutton = (TextView) findViewById(R.id.bind_nextbutton);

        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);


    }

    @Override
    public void doAction() {
        super.doAction();

        link_clik.setText("收不到短信？");
        link_clik_withLine.setText("获取语音验证码");

        bind_phone_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ReminderHelper.getIntentce().exchangeButtonBG(bind_nextbutton, ReminderHelper.getIntentce().listenerEditIsEmpty(bind_phone_edit, bind_phone_code_edit));
            }
        });

        bind_phone_code_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ReminderHelper.getIntentce().exchangeButtonBG(bind_nextbutton, ReminderHelper.getIntentce().listenerEditIsEmpty(bind_phone_edit, bind_phone_code_edit));
            }
        });

        ReminderHelper.getIntentce().exchangeButtonBG(bind_nextbutton, ReminderHelper.getIntentce().listenerEditIsEmpty(bind_phone_edit, bind_phone_code_edit));

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.bind_phone_code_button://获取验证码

                GetCodeHelper.getIntentce().sendCode_bind(this, bind_phone_edit.getText().toString(), GetCodeHelper.TEXT);
                break;

            case R.id.bind_nextbutton://下一步

                bindLogin(bind_phone_edit.getText().toString(), bind_phone_code_edit.getText().toString());
                break;

            case R.id.bind_account_have://绑定已有账号

                BindAccountActivity.start(this, bean);
                break;

            case R.id.link_clik_withLine://获取语音验证码

                GetCodeHelper.getIntentce().sendCode_bind(this, bind_phone_edit.getText().toString(), GetCodeHelper.VOICE);
                break;

            case R.id.title_left://返回

                finish();
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getSubscribe(String num) {
        if ("0".equals(num)) {
            code_button.setClickable(true);
            code_button.setBackgroundResource(R.drawable.green_button_bg_circle);
            code_button.setText(R.string.verification_getCode);
            link_clik_withLine.setClickable(true);
            link_clik_withLine.setText(R.string.verification_getCode);
        } else {
            code_button.setClickable(false);
            code_button.setBackgroundResource(R.drawable.gray_button_bg_circle_);
            code_button.setText(num + getResources().getString(R.string.verification_num));
            link_clik_withLine.setClickable(false);
            link_clik_withLine.setText(num + getResources().getString(R.string.verification_num));
        }
    }

    /**
     * 绑定手机账号
     */
    private void bindLogin(String phoneNum, String phoneCode) {

        if (TextUtils.isEmpty(phoneNum)) {
            Toast.makeText(this, "请先输入手机号码", Toast.LENGTH_SHORT).show();
            return;
        } else if (phoneNum.length() != 11 || !GetCodeHelper.getIntentce().isMobileNO(phoneNum)) {
            Toast.makeText(this, "请先输入正确的手机号码", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(phoneCode)) {
            Toast.makeText(this, "请先输入短信验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        bean.setMobile(phoneNum);
        Map<String, Object> map = new TreeMap<>();
        map.put("mobile", phoneNum);
        map.put("code", phoneCode);
        map.put("openId", bean.getOpenId());
        map.put("loginInType", bean.getThirdparty());
        HiStudentHttpUtils.postDataByOKHttp(true, this, map, HistudentUrl.verfiyBindingMobile_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject object = new JSONObject(result);
                    int code = object.optInt("ret");
                    if (code == 1) {//该手机已注册，直接绑定登录
                        String data = object.optString("data");
                        CurrentUserInfoModel currentUserInfo = CurrentUserinfoParser.userinfoParser(data);
                        if (currentUserInfo != null)//开始云信登录
                            loginAction_YX(currentUserInfo, BindPhoneActivity.this, currentUserInfo.getUserName(), currentUserInfo.getImToken());
                    } else if (code == 400) {//该手机没有注册，走注册流程
                        JSONObject object_ = object.getJSONObject("data");
                        bean.setResetToken(object_.getString("resetToken"));
                        SeletRegistRankActivity.start(BindPhoneActivity.this, bean);
                    } else if (code == -1) {
                        String msg = object.optString("msg");
                        ReminderHelper.getIntentce().ToastShow(BindPhoneActivity.this, msg);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });

    }

}
