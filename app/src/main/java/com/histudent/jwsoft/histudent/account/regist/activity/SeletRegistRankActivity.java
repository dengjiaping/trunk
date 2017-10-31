package com.histudent.jwsoft.histudent.account.regist.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.account.login.activity.BaseLoginActivity;
import com.histudent.jwsoft.histudent.account.login.activity.DealActivity;
import com.histudent.jwsoft.histudent.account.login.model.CurrentUserInfoModel;
import com.histudent.jwsoft.histudent.account.login.parser.CurrentUserinfoParser;
import com.histudent.jwsoft.histudent.account.regist.bean.RegistBean;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.comment2.utils.ActivityCollector;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2017/4/12.
 * 选择注册身份
 */

public class SeletRegistRankActivity extends BaseLoginActivity {

    private EditText regist_name, regist_pwd;
    private TextView regist_nextbutton;
    private RegistBean bean;
    private LinearLayout student_radioButton, teacher_radioButton;
    private int type = 1;
    private TextView link_clik, link_clik_withLine;
    private IconView showPassword;

    public static void start(Activity activity, RegistBean bean) {
        Intent intent = new Intent(activity, SeletRegistRankActivity.class);
        intent.putExtra("RegistBean", bean);
        activity.startActivity(intent);
    }

    @Override
    public int setViewLayout() {
        ActivityCollector.addActivity(this);
        return R.layout.activity_regist_selectrank;
    }

    @Override
    public void initView() {
        bean = (RegistBean) getIntent().getSerializableExtra("RegistBean");
        regist_name = (EditText) findViewById(R.id.regist_name);
        regist_pwd = (EditText) findViewById(R.id.regist_pwd);
        regist_nextbutton = (TextView) findViewById(R.id.regist_nextbutton);
        student_radioButton = (LinearLayout) findViewById(R.id.student_radioButton);
        teacher_radioButton = (LinearLayout) findViewById(R.id.teacher_radioButton);
        link_clik = (TextView) findViewById(R.id.link_clik);
        link_clik_withLine = (TextView) findViewById(R.id.link_clik_withLine);
        showPassword = (IconView) findViewById(R.id.login_password_image);
    }

    @Override
    public void doAction() {
        super.doAction();

        link_clik.setText("注册即表示同意");
        link_clik_withLine.setText("用户服务协议");

        regist_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ReminderHelper.getIntentce().exchangeButtonBG(regist_nextbutton, ReminderHelper.getIntentce().listenerEditIsEmpty(regist_name, regist_pwd));
            }
        });

        regist_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ReminderHelper.getIntentce().exchangeButtonBG(regist_nextbutton, ReminderHelper.getIntentce().listenerEditIsEmpty(regist_name, regist_pwd));
            }
        });

        ReminderHelper.getIntentce().exchangeButtonBG(regist_nextbutton, ReminderHelper.getIntentce().listenerEditIsEmpty(regist_name, regist_pwd));

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.title_left://回退
                finish();
                break;

            case R.id.login_password_image://显示密码
                ReminderHelper.getIntentce().exchangePswStatue(this, showPassword, regist_pwd);
                break;

            case R.id.regist_nextbutton://下一步
                bean.setName(regist_name.getText().toString());
                bean.setPwd(regist_pwd.getText().toString());
                bean.setRegisterType(type);
                registUser(SeletRegistRankActivity.this, bean);
                break;
            case R.id.student_radioButton://学生
                type = 1;
                student_radioButton.setBackgroundResource(R.drawable.green_button_bg_circle);
                teacher_radioButton.setBackgroundResource(R.drawable.gray_button_bg_circle);
                break;
            case R.id.teacher_radioButton://老师
                type = 3;
                student_radioButton.setBackgroundResource(R.drawable.gray_button_bg_circle);
                teacher_radioButton.setBackgroundResource(R.drawable.green_button_bg_circle);
                break;
            case R.id.link_clik_withLine://服务协议
                DealActivity.start(this, HistudentUrl.agreement_url, getResources().getString(R.string.agreement_hi));
                break;
        }
    }

    /**
     * 注册新用户
     */
    private void registUser(final BaseActivity activity, RegistBean bean) {

        if (TextUtils.isEmpty(bean.getName())) {
            Toast.makeText(activity, "请先填写姓名", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(bean.getPwd())) {
            Toast.makeText(activity, "请先填写密码", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> map = new TreeMap<>();
        map.put("name", bean.getName());
        map.put("mobile", bean.getMobile());
        map.put("resetToken", bean.getResetToken());
        map.put("pwd", bean.getPwd());
        map.put("registerType", bean.getRegisterType());
        map.put("openId", bean.getOpenId());
        map.put("thirdparty", bean.getThirdparty());
        map.put("nickname", bean.getNickname());
        map.put("avatar", bean.getAvatar());

        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.register_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                CurrentUserInfoModel currentUserInfo = CurrentUserinfoParser.userinfoParser(result);
                if (currentUserInfo != null) //开始云信登录

                    loginAction_YX(currentUserInfo, SeletRegistRankActivity.this, "", currentUserInfo.getImToken());
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });

    }

}
