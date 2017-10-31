package com.histudent.jwsoft.histudent.body.mine.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.histudent.jwsoft.histudent.body.mine.activity.AccountSafeActivity;
import com.histudent.jwsoft.histudent.body.mine.activity.AccountSafeItemActivity;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.fragment.BaseFragment;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.TreeMap;

import static com.histudent.jwsoft.histudent.R.id.accountSafe_phoneSet_fragment_01_button;

/**
 * 账号安全-密保手机第二步
 * Created by liuguiyu-pc on 2016/11/3.
 */
public class PhoneSetFragment_01 extends BaseFragment implements View.OnClickListener {

    private View view;
    private TextView button, edit_phone;
    private Button button_sendCode;
    private EditText edit_code;
    private String post_info;
    private int post_code;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case -1:
                    int num = msg.arg1;
                    if (num > 0) {
                        exchangeButtonBG(button_sendCode, false);
                        button_sendCode.setText(num + "秒后重发");
                    } else {
                        exchangeButtonBG(button_sendCode, true);
                        button_sendCode.setText("重新获取");
                    }
                    break;
                case 100:

                    SystemUtil.countDown(handler, 60);

                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_phone_set_01, container, false);
        return view;
    }

    @Override
    public void initView() {
        super.initView();
        edit_phone = (TextView) view.findViewById(R.id.accountSafe_phoneSet_fragment_01_edit01);
        edit_code = (EditText) view.findViewById(R.id.accountSafe_phoneSet_fragment_01_edit02);
        button = (TextView) view.findViewById(R.id.accountSafe_phoneSet_fragment_01_next);
        button_sendCode = (Button) view.findViewById(accountSafe_phoneSet_fragment_01_button);

        switch (AccountSafeItemActivity.type) {
            case AccountSafeType.PHONE:
                post_info = AccountSafeActivity.model.getMobile();
                String bb = post_info.substring(3, 7);
                String cc = post_info.replace(bb, "****");
                edit_phone.setText(cc);
                post_code = 9;
                break;
            case AccountSafeType.EMAIL:
                if (AccountSafeActivity.model.isIsMobileVerified()) {
                    post_info = AccountSafeActivity.model.getMobile();
                    String bb_ = post_info.substring(3, 7);
                    String cc_ = post_info.replace(bb_, "****");
                    edit_phone.setText(cc_);
                    post_code = 8;
                } else {
                    post_info = AccountSafeActivity.model.getEmail();
                    edit_phone.setText(post_info);
                    post_code = 12;
                }
                break;
        }

        button_sendCode.setOnClickListener(this);
        button.setOnClickListener(this);

        edit_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                boolean flag = TextUtils.isEmpty(editable);
                button.setClickable(!flag);
                button.setBackgroundResource(flag ? R.drawable.gray_button_bg : R.drawable.green_button_bg);

            }
        });

    }

    @Override
    public void onClick(View view) {

        String code = edit_code.getText().toString();
        switch (view.getId()) {

            case R.id.accountSafe_phoneSet_fragment_01_next:

                if (TextUtils.isEmpty(code)) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.inputCheckCode), Toast.LENGTH_SHORT).show();
                } else {
                    equelVerfiyCode(post_info, post_code, code);
                }

                break;

            case accountSafe_phoneSet_fragment_01_button:
                postVerfiyCode(post_info, post_code);
                break;

        }
    }

    /**
     * 请求验证码
     */
    private void postVerfiyCode(String phone, int code) {

        Map<String, Object> map = new TreeMap<>();
        map.put("codeTypeEnum", code);
        map.put("phone", phone);

        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) getActivity(), map, HistudentUrl.verfiyCode_url, new HttpRequestCallBack() {
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
     * 验证验证码
     */
    private void equelVerfiyCode(String phone, int type_, String code) {
        Map<String, Object> map = new TreeMap<>();
        map.put("phone", phone);
        map.put("codeTypeEnum", type_);
        map.put("code", code);
        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) getActivity(), map, HistudentUrl.isValidVerfiyCode_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject object = new JSONObject(result);

                    AccountSafeItemActivity.resetToken = object.optString("resetToken");

                    switch (AccountSafeItemActivity.type) {
                        case AccountSafeType.PHONE:
                            if (gonext != null)
                                gonext.doaction_phone();
                            break;
                        case AccountSafeType.EMAIL:
                            if (gonext != null)
                                gonext.doaction_email();
                            break;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    PhoneSetFragment_01.Gonext gonext;

    public void setOnlistens(PhoneSetFragment_01.Gonext gonext) {
        this.gonext = gonext;
    }

    public interface Gonext {
        void doaction_phone();

        void doaction_email();
    }

}
