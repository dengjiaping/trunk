package com.histudent.jwsoft.histudent.account.password.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.account.AccountFragment;
import com.histudent.jwsoft.histudent.account.password.model.PhoneModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.bean.CodeModel;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.helper.GetCodeHelper;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by liuguiyu-pc on 2016/5/26.
 * 找回密码第一步的fragment
 */
public class FindPassWordFragmentTheFirst extends AccountFragment implements View.OnClickListener {

    private View view;
    private EditText editText_account;
    private TextView button_next;
    private String phone;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return view = inflater.inflate(R.layout.findpassword_thefirst_fragment, container, false);
    }

    @Override
    public void initView() {
        super.initView();

        editText_account = (EditText) view.findViewById(R.id.findpassword_thefirst_fragment_edittext);
        button_next = (TextView) view.findViewById(R.id.findpassword_thefirst_fragment_nextbutton);

        button_next.setOnClickListener(this);

    }

    @Override
    public void initData() {
        super.initData();

        editText_account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ReminderHelper.getIntentce().exchangeButtonBG(button_next, GetCodeHelper.getIntentce().isMobileNO(editable.toString()));
            }
        });

        ReminderHelper.getIntentce().exchangeButtonBG(button_next, GetCodeHelper.getIntentce().isMobileNO(editText_account.getText().toString()));

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.findpassword_thefirst_fragment_nextbutton:

                phone = editText_account.getText().toString();

                GetCodeHelper.getIntentce().sendCode_resetPwd((BaseActivity) getActivity(), phone, GetCodeHelper.TEXT, new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        HiCache.getInstance().saveCodeModel(new CodeModel(phone, 2, System.currentTimeMillis()));
                        SystemUtil.countDown(60);
                        int codeTime = HiCache.getInstance().isNeedGetCode(phone);
                        final PhoneModel model = new PhoneModel(0, phone, codeTime > 0 ? codeTime : 60);
                        EventBus.getDefault().post(model);
                    }

                    @Override
                    public void onFailure(String errorMsg) {

                    }
                });
                break;

        }
    }

}
