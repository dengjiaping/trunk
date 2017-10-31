package com.histudent.jwsoft.histudent.account.regist.activity;

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
import com.histudent.jwsoft.histudent.account.regist.bean.RegistBean;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
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
 * Created by liuguiyu-pc on 2017/4/12.
 * 手机注册
 */

public class
PhoneRegistActivity extends BaseActivity {

    private EditText regist_phone_edit, regist_phone_code_edit;
    private TextView link_clik, link_clik_withLine, regist_nextbutton;
    private Button regist_phone_code_button;
    private RegistBean bean;

    public static void start(Activity activity, RegistBean bean) {
        Intent intent = new Intent(activity, PhoneRegistActivity.class);
        intent.putExtra("RegistBean", bean);
        activity.startActivity(intent);
    }

    @Override
    public int setViewLayout() {
        ActivityCollector.addActivity(this);
        return R.layout.activity_regist_phone;
    }

    @Override
    public void initView() {

        bean = (RegistBean) getIntent().getSerializableExtra("RegistBean");

        regist_phone_edit = (EditText) findViewById(R.id.regist_phone_edit);
        regist_phone_code_edit = (EditText) findViewById(R.id.regist_phone_code_edit);
        regist_phone_code_button = (Button) findViewById(R.id.regist_phone_code_button);
        link_clik = (TextView) findViewById(R.id.link_clik);
        link_clik_withLine = (TextView) findViewById(R.id.link_clik_withLine);
        regist_nextbutton = (TextView) findViewById(R.id.regist_nextbutton);

        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);

    }

    @Override
    public void doAction() {
        super.doAction();

        link_clik.setText("收不到短信？");
        link_clik_withLine.setText("获取语音验证码");

        regist_phone_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                ReminderHelper.getIntentce().exchangeButtonBG(regist_nextbutton, ReminderHelper.getIntentce().listenerEditIsEmpty(regist_phone_edit, regist_phone_code_edit));
            }
        });

        regist_phone_code_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                ReminderHelper.getIntentce().exchangeButtonBG(regist_nextbutton, ReminderHelper.getIntentce().listenerEditIsEmpty(regist_phone_edit, regist_phone_code_edit));
            }
        });

        ReminderHelper.getIntentce().exchangeButtonBG(regist_nextbutton, ReminderHelper.getIntentce().listenerEditIsEmpty(regist_phone_edit, regist_phone_code_edit));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.title_left://回退
                finish();
                break;

            case R.id.regist_phone_code_button://获取验证码
                GetCodeHelper.getIntentce().sendCode_regist(PhoneRegistActivity.this, regist_phone_edit.getText().toString(), GetCodeHelper.TEXT);
                break;

            case R.id.regist_nextbutton://下一步
                String phoneNum = regist_phone_edit.getText().toString();
                String codeNum = regist_phone_code_edit.getText().toString();
                checkoutCode(this, phoneNum, codeNum);
                break;

            case R.id.link_clik_withLine://获取语音验证码
                GetCodeHelper.getIntentce().sendCode_regist(PhoneRegistActivity.this, regist_phone_edit.getText().toString(), GetCodeHelper.VOICE);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getSubscribe(String num) {
        if ("0".equals(num)) {
            regist_phone_code_button.setClickable(true);
            link_clik_withLine.setClickable(true);
            link_clik_withLine.setText(R.string.verification_getCode_voice);
            regist_phone_code_button.setText(R.string.verification_getCode);
            regist_phone_code_button.setBackgroundResource(R.drawable.green_button_bg_circle);
        } else {
            regist_phone_code_button.setClickable(false);
            link_clik_withLine.setClickable(false);
            link_clik_withLine.setText(num + getResources().getString(R.string.verification_num));
            regist_phone_code_button.setBackgroundResource(R.drawable.gray_button_bg_circle_);
            regist_phone_code_button.setText(num + getResources().getString(R.string.verification_num));
        }
    }

    /**
     * 校验验证码,并进行下一步
     */
    public void checkoutCode(BaseActivity activity, final String phoneNum, String verfiyCode) {

        if (TextUtils.isEmpty(phoneNum)) {
            Toast.makeText(activity, "请先输入手机号码", Toast.LENGTH_SHORT).show();
            return;
        } else if (phoneNum.length() != 11 || !GetCodeHelper.getIntentce().isMobileNO(phoneNum)) {
            Toast.makeText(activity, "请先输入正确的手机号码", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(verfiyCode)) {
            Toast.makeText(activity, "请先输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> map = new TreeMap<>();
        map.put("code", verfiyCode);
        map.put("codeType", "Register");
        map.put("codeTypeEnum", 1);
        map.put("phone", phoneNum);

        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.isValidVerfiyCode_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject object = new JSONObject(result);
                    bean.setMobile(phoneNum);
                    bean.setResetToken(object.getString("resetToken"));
                    SeletRegistRankActivity.start(PhoneRegistActivity.this, bean);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

}
