package com.histudent.jwsoft.histudent.account.password.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
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
import com.histudent.jwsoft.histudent.account.AccountFragment;
import com.histudent.jwsoft.histudent.account.password.model.PhoneModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.bean.CodeModel;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.helper.GetCodeHelper;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.view.IconView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2016/5/26.
 * 找回密码第二步的fragment
 */
public class FindPassWordFragmentTheSecond extends AccountFragment implements View.OnClickListener {

    private View view;
    private TextView phoneNumber;
    private EditText editText, setPwd_password_edit;
    private TextView link_clik, link_clik_withLine;
    private Button codeText;
    private TextView button_next;
    private String phoneNum;
    private IconView login_password_image;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.findpassword_thesecond_fragment, container, false);
        view.setOnClickListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    @Override
    public void initView() {
        super.initView();

        button_next = (TextView) view.findViewById(R.id.findpassword_thesecond_fragment_nextbutton);
        codeText = (Button) view.findViewById(R.id.getCode);
        editText = (EditText) view.findViewById(R.id.bind_phone_code_edit);
        setPwd_password_edit = (EditText) view.findViewById(R.id.setPwd_password_edit);
        phoneNumber = (TextView) view.findViewById(R.id.phoneNumber);

        link_clik = (TextView) view.findViewById(R.id.link_clik);
        link_clik_withLine = (TextView) view.findViewById(R.id.link_clik_withLine);

        login_password_image = (IconView) view.findViewById(R.id.login_password_image);

        button_next.setOnClickListener(this);
        codeText.setOnClickListener(this);
        link_clik_withLine.setOnClickListener(this);
        login_password_image.setOnClickListener(this);

    }

    @Override
    public void initData() {
        super.initData();

        Bundle bundle = getArguments();
        phoneNum = bundle.getString("phoneNum");
        phoneNumber.setText("验证码已发送至 " + phoneNum);
        SystemUtil.countDown(bundle.getInt("codeNum"));

        link_clik.setText("收不到短信？");
        link_clik_withLine.setText("获取语音验证码");

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ReminderHelper.getIntentce().exchangeButtonBG(button_next, ReminderHelper.getIntentce().listenerEditIsEmpty(editText, setPwd_password_edit));
            }
        });

        setPwd_password_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ReminderHelper.getIntentce().exchangeButtonBG(button_next, ReminderHelper.getIntentce().listenerEditIsEmpty(editText, setPwd_password_edit) && editable.length() > 5);
            }
        });

        ReminderHelper.getIntentce().exchangeButtonBG(button_next, ReminderHelper.getIntentce().listenerEditIsEmpty(editText, setPwd_password_edit));

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getSubscribe(String num) {
        if ("0".equals(num)) {
            codeText.setOnClickListener(this);
            codeText.setText("获取验证码");
            link_clik_withLine.setOnClickListener(this);
            link_clik_withLine.setText("获取语音验证码");
            codeText.setBackgroundResource(R.drawable.green_button_bg_circle);
        } else {
            codeText.setOnClickListener(null);
            codeText.setText(num + "秒后重发");
            link_clik_withLine.setOnClickListener(null);
            link_clik_withLine.setText(num + "秒后重发");
            codeText.setBackgroundResource(R.drawable.gray_button_bg_circle_);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.findpassword_thesecond_fragment_nextbutton:

                checkoutCode((BaseActivity) getActivity(), phoneNum, editText.getText().toString());

                break;

            case R.id.getCode://获取重置密码验证码

                GetCodeHelper.getIntentce().sendCode_resetPwd((BaseActivity) getActivity(), phoneNum, GetCodeHelper.TEXT, new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        HiCache.getInstance().saveCodeModel(new CodeModel(phoneNum, 2, System.currentTimeMillis()));
                        SystemUtil.countDown(60);
                    }

                    @Override
                    public void onFailure(String errorMsg) {

                    }
                });

                break;

            case R.id.link_clik_withLine://获取语音验证码

                GetCodeHelper.getIntentce().sendCode_resetPwd((BaseActivity) getActivity(), phoneNum, GetCodeHelper.VOICE, new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        HiCache.getInstance().saveCodeModel(new CodeModel(phoneNum, 2, System.currentTimeMillis()));
                        SystemUtil.countDown(60);
                    }

                    @Override
                    public void onFailure(String errorMsg) {

                    }
                });

                break;
            case R.id.login_password_image://显示隐藏密码

                ReminderHelper.getIntentce().exchangePswStatue(getActivity(), login_password_image, setPwd_password_edit);

                break;

        }
    }

    /**
     * 校验验证码
     */
    public void checkoutCode(BaseActivity activity, final String phoneNum, String verfiyCode) {

        final String pwd = setPwd_password_edit.getText().toString();

        if (TextUtils.isEmpty(phoneNum)) {
            Toast.makeText(activity, "请先输入手机号码", Toast.LENGTH_SHORT).show();
            return;
        } else if (phoneNum.length() != 11 || !GetCodeHelper.getIntentce().isMobileNO(phoneNum)) {
            Toast.makeText(activity, "请先输入正确的手机号码", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(verfiyCode)) {
            Toast.makeText(activity, "请先输入验证码", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(pwd) || pwd.length() < 6) {
            Toast.makeText(activity, "请先输入至少六位的新密码", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> map = new TreeMap<>();
        map.put("code", verfiyCode);
        map.put("codeType", "ResetPwd");
        map.put("phone", phoneNum);

        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.isValidVerfiyCode_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject object = new JSONObject(result);
                    resetPwd(object.getString("userId"), object.getString("resetToken"), pwd);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    /**
     * 重置密码
     */
    public void resetPwd(String userId, String resetToken, String passWord) {
        Map<String, Object> map = new TreeMap<>();
        map.put("userId", userId);
        map.put("resetToken", resetToken);
        map.put("password", passWord);

        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) getActivity(), map, HistudentUrl.resetPassword_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                EventBus.getDefault().post(new PhoneModel(2));
            }

            @Override
            public void onFailure(String msg) {

            }
        });

    }

}
