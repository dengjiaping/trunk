package com.histudent.jwsoft.histudent.account.login.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.account.login.model.CurrentUserInfoModel;
import com.histudent.jwsoft.histudent.account.login.parser.CurrentUserinfoParser;
import com.histudent.jwsoft.histudent.account.regist.bean.RegistBean;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.comment2.utils.ActivityCollector;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2017/4/11.
 * 用于账号绑定第三方平台
 */

public class BindAccountActivity extends BaseLoginActivity {

    private EditText bind_account_edit, bind_account_pwd_edit;
    private TextView title, bind_button;
    private RegistBean bean;
    private IconView showPassword;

    public static void start(Activity activity, RegistBean bean) {
        Intent intent = new Intent(activity, BindAccountActivity.class);
        intent.putExtra("RegistBean", bean);
        activity.startActivity(intent);
    }

    @Override
    public int setViewLayout() {
        ActivityCollector.addActivity(this);
        return R.layout.activity_bind_account;
    }

    @Override
    public void initView() {

        bean = (RegistBean) getIntent().getSerializableExtra("RegistBean");
        showPassword = (IconView) findViewById(R.id.login_password_image);
        bind_account_edit = (EditText) findViewById(R.id.bind_account_edit);
        bind_account_pwd_edit = (EditText) findViewById(R.id.bind_account_pwd_edit);
        title = (TextView) findViewById(R.id.title_middle_text);
        bind_button = (TextView) findViewById(R.id.bind_button);
    }

    @Override
    public void doAction() {
        super.doAction();

        bind_account_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ReminderHelper.getIntentce().exchangeButtonBG(bind_button, ReminderHelper.getIntentce().listenerEditIsEmpty(bind_account_edit, bind_account_pwd_edit));
            }
        });

        bind_account_pwd_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ReminderHelper.getIntentce().exchangeButtonBG(bind_button, ReminderHelper.getIntentce().listenerEditIsEmpty(bind_account_edit, bind_account_pwd_edit));
            }
        });

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.title_left://返回

                finish();
                break;
            case R.id.bind_button://绑定账号
                bindLogin(bind_account_edit.getText().toString(), bind_account_pwd_edit.getText().toString());

                break;

            case R.id.login_password_image://显示（隐藏）密码
                ReminderHelper.getIntentce().exchangePswStatue(this, showPassword, bind_account_pwd_edit);
                break;
        }

    }

    /**
     * 绑定账号
     */
    private void bindLogin(String phoneNum, String pwd) {

        if (TextUtils.isEmpty(phoneNum)) {
            Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> map = new TreeMap<>();
        map.put("userName", phoneNum);
        map.put("pwd", pwd);
        map.put("openId", bean.getOpenId());
        map.put("loginInType", bean.getThirdparty());

        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.bindingAccount_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                CurrentUserInfoModel currentUserInfo = CurrentUserinfoParser.userinfoParser(result);
                if (currentUserInfo != null)
                    //开始云信登录
                    loginAction_YX(currentUserInfo, BindAccountActivity.this, "", currentUserInfo.getImToken());
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });

    }

}
