package com.histudent.jwsoft.histudent.body.mine.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.mine.activity.AccountSafeActivity;
import com.histudent.jwsoft.histudent.body.mine.activity.AccountSafeItemActivity;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.fragment.BaseFragment;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;

import java.util.Map;
import java.util.TreeMap;

/**
 * 账号安全-密保手机第二步
 * Created by liuguiyu-pc on 2016/11/3.
 */
public class PhoneSetFragment_02 extends BaseFragment implements View.OnClickListener {

    private View view;
    private final int PHONE = 2;
    private final int PHONE_NULL = 3;
    private final int EMAIL = 4;
    private final int EMAIL_NULL = 5;
    private TextView button_complet;
    private EditText editText, editText_code;
    private Button button_code;
    private int code;
    private boolean flag_phone, flag_code;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case -1:
                    int num = msg.arg1;
                    if (num > 0) {
                        exchangeButtonBG(button_code, false);
                        button_code.setText(num + "s后重发");
                    } else {
                        exchangeButtonBG(button_code, true);
                        button_code.setText("重新获取");
                    }
                    break;

                case 0:
                    button_code.setOnClickListener(flag_phone ? PhoneSetFragment_02.this : null);
                    button_code.setBackgroundResource(flag_phone ? R.drawable.green_button_bg : R.drawable.gray_button_bg);
                    button_complet.setOnClickListener(flag_code ? PhoneSetFragment_02.this : null);
                    button_complet.setBackgroundResource(flag_code ? R.drawable.green_button_bg : R.drawable.gray_button_bg);
                    break;

                case 100:
                    SystemUtil.countDown(handler, code == 5 ? 60 : 120);
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_phone_set_02, container, false);
        return view;
    }

    @Override
    public void initView() {
        super.initView();

        editText = (EditText) view.findViewById(R.id.accountSafe_phoneSet_fragment_02_edit01);
        editText_code = (EditText) view.findViewById(R.id.accountSafe_phoneSet_fragment_02_edit02);
        button_code = (Button) view.findViewById(R.id.accountSafe_phoneSet_fragment_02_button);
        button_complet = (TextView) view.findViewById(R.id.accountSafe_phoneSet_fragment_02_next);

        switch (AccountSafeItemActivity.type) {

            case PHONE:

                code = 5;
                editText.setHint(R.string.newPhoneCode);
                editText.setInputType(InputType.TYPE_CLASS_PHONE);

                break;
            case PHONE_NULL:

                code = 5;
                editText.setHint(R.string.phoneCode);
                editText.setInputType(InputType.TYPE_CLASS_PHONE);

                break;
            case EMAIL:

                code = 6;
                editText.setHint(R.string.newEmailCode);
                editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

                break;
            case EMAIL_NULL:

                code = 6;
                editText.setHint(R.string.emailCode);
                editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

                break;
        }

    }

    @Override
    public void initData() {
        super.initData();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                flag_phone = !TextUtils.isEmpty(editable);
                handler.sendEmptyMessage(0);
            }
        });

        editText_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                flag_code = !TextUtils.isEmpty(editable);
                handler.sendEmptyMessage(0);
            }
        });

    }

    @Override
    public void onClick(View view) {

        String info = editText.getText().toString();
        String info_code = editText_code.getText().toString();

        switch (view.getId()) {

            case R.id.accountSafe_phoneSet_fragment_02_button:

                switch (AccountSafeItemActivity.type) {
                    case PHONE:
                    case PHONE_NULL:
                        if ((TextUtils.isEmpty(info) || info.equals(AccountSafeActivity.model.getMobile())) && info.length() != 11) {
                            Toast.makeText(getActivity(), "请输入新手机号码", Toast.LENGTH_SHORT).show();
                        } else {
                            postVerfiyCode(info, code);
                        }
                        break;

                    case EMAIL:
                    case EMAIL_NULL:
                        if ((TextUtils.isEmpty(info) || info.equals(AccountSafeActivity.model.getEmail())) && info.contains("@")) {
                            Toast.makeText(getActivity(), "请输入新邮箱", Toast.LENGTH_SHORT).show();
                        } else {
                            postVerfiyCode(info, code);
                        }
                        break;
                }

                break;

            case R.id.accountSafe_phoneSet_fragment_02_next:

                switch (AccountSafeItemActivity.type) {

                    case PHONE:

                        if (TextUtils.isEmpty(info) || info.equals(AccountSafeActivity.model.getMobile())) {
                            Toast.makeText(getActivity(), "请输入新手机号码", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(info_code) && info_code.length() != 5) {
                            Toast.makeText(getActivity(), "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                        } else {
                            resetMobile(info, info_code);
                        }
                        break;
                    case PHONE_NULL:
                        if (TextUtils.isEmpty(info)) {
                            Toast.makeText(getActivity(), "请输入手机号码", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(info_code) && info_code.length() != 5) {
                            Toast.makeText(getActivity(), "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                        } else {
                            setMobile(info, info_code);
                        }
                        break;
                    case EMAIL:
                        if (TextUtils.isEmpty(info) || info.equals(AccountSafeActivity.model.getEmail())) {
                            Toast.makeText(getActivity(), "请输入新邮箱", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(info_code) && info_code.length() != 5) {
                            Toast.makeText(getActivity(), "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                        } else {
                            resetEmail(info, info_code);
                        }
                        break;
                    case EMAIL_NULL:
                        if (TextUtils.isEmpty(info)) {
                            Toast.makeText(getActivity(), "请输入邮箱", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(info_code) && info_code.length() != 5) {
                            Toast.makeText(getActivity(), "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                        } else {
                            setEmail(info, info_code);
                        }
                        break;
                }

                break;

        }
    }

    /**
     * 请求发送验证码
     */
    private void postVerfiyCode(String phone, int code) {

        Map<String, Object> map = new TreeMap<>();
        map.put(code == 5 ? "codeTypeEnum" : "codeType", code);
        map.put(code == 5 ? "phone" : "email", phone);

        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) getActivity(), map, code == 5 ? HistudentUrl.verfiyCode_url : HistudentUrl.verfiyEmailCode_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                handler.sendEmptyMessage(100);

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    /**
     * 重设密保手机
     */
    private void resetMobile(String phone, String code) {

        Map<String, Object> map = new TreeMap<>();
        map.put("userId", HiCache.getInstance().getLoginUserInfo().getUserId());
        map.put("resetToken", AccountSafeItemActivity.resetToken);
        map.put("mobile", phone);
        map.put("code", code);

        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) getActivity(), map, HistudentUrl.resetMobile_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                Toast.makeText(getActivity(), getResources().getString(R.string.exchangePhoneSucceed), Toast.LENGTH_SHORT).show();

                getActivity().setResult(200);
                getActivity().finish();

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    /**
     * 重设密保邮箱
     */
    private void resetEmail(String email, String code) {

        Map<String, Object> map = new TreeMap<>();
        map.put("userId", HiCache.getInstance().getLoginUserInfo().getUserId());
        map.put("resetToken", AccountSafeItemActivity.resetToken);
        map.put("email", email);
        map.put("code", code);

        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) getActivity(), map, HistudentUrl.resetEmail_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                Toast.makeText(getActivity(), getResources().getString(R.string.exchangeEmailSucceed), Toast.LENGTH_SHORT).show();

                getActivity().setResult(200);
                getActivity().finish();

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    /**
     * 设置密保手机
     */
    private void setMobile(String phone, String code) {

        Map<String, Object> map = new TreeMap<>();
        map.put("mobile", phone);
        map.put("code", code);

        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) getActivity(), map, HistudentUrl.setMobile_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                Toast.makeText(getActivity(), getResources().getString(R.string.setPhoneSucceed), Toast.LENGTH_SHORT).show();

                getActivity().setResult(200);
                getActivity().finish();

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    /**
     * 设置密保邮箱
     */
    private void setEmail(String email, String code) {

        Map<String, Object> map = new TreeMap<>();
        map.put("email", email);
        map.put("code", code);

        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) getActivity(), map, HistudentUrl.setEmail_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                Toast.makeText(getActivity(), getResources().getString(R.string.setEmailSucceed), Toast.LENGTH_SHORT).show();

                getActivity().setResult(200);
                getActivity().finish();

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SystemUtil.closeDown();
    }
}
