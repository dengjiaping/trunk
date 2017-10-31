package com.histudent.jwsoft.histudent.account.login.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.account.login.model.LoginData;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.fragment.BaseFragment;
import com.histudent.jwsoft.histudent.commen.helper.GetCodeHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by liuguiyu-pc on 2017/4/10.
 * 手机验证码登录
 */

public class PhoneFragmnet extends BaseFragment implements View.OnClickListener {

    private View view;
    private EditText edit_phone, edit_code;
    private Button code_button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return view = inflater.inflate(R.layout.fragment_login_phone, container, false);
    }

    @Override
    public void initView() {
        super.initView();

        edit_phone = (EditText) view.findViewById(R.id.login_phone_edit);
        edit_code = (EditText) view.findViewById(R.id.login_phone_code_edit);
        code_button = (Button) view.findViewById(R.id.login_phone_code_button);

        code_button.setOnClickListener(this);

        LoginData loginData = new LoginData(null, true, null, true);
        EventBus.getDefault().post(loginData);

        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);

    }

    @Override
    public void initData() {
        super.initData();

        edit_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                LoginData loginData = new LoginData(edit_phone.getText().toString(), true, edit_code.getText().toString(), false);
                EventBus.getDefault().post(loginData);

            }
        });

        edit_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                LoginData loginData = new LoginData(edit_phone.getText().toString(), false, edit_code.getText().toString(), false);
                EventBus.getDefault().post(loginData);
            }
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.login_phone_code_button://获取验证码
                GetCodeHelper.getIntentce().sendCode_login((BaseActivity) getActivity(), edit_phone.getText().toString(), GetCodeHelper.TEXT);
                break;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getSubscribe(String num) {
        if ("-1".equals(num)) {
            if (code_button.isClickable())
                GetCodeHelper.getIntentce().sendCode_login((BaseActivity) getActivity(), edit_phone.getText().toString(), GetCodeHelper.VOICE);
        } else if ("0".equals(num)) {
            code_button.setClickable(true);
            code_button.setBackgroundResource(R.drawable.green_button_bg_circle);
            code_button.setText(R.string.verification_getCode);
        } else {
            code_button.setClickable(false);
            code_button.setBackgroundResource(R.drawable.gray_button_bg_circle_);
            code_button.setText(num + getResources().getString(R.string.verification_num));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getVoiceCode(Boolean flag) {
        if (flag) {
            if (code_button.isClickable())
                GetCodeHelper.getIntentce().sendCode_login((BaseActivity) getActivity(), edit_phone.getText().toString(), GetCodeHelper.VOICE);
        }
    }

}
