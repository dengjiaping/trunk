package com.histudent.jwsoft.histudent.body.mine.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.fragment.BaseFragment;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;

import java.util.Map;
import java.util.TreeMap;

/**
 * 账号安全-重新设置密码
 * Created by liuguiyu-pc on 2016/11/3.
 */
public class PswSetFragment extends BaseFragment {

    private View view;
    private EditText edit_account, edit_new_psw, edit_new_psw_;
    private boolean flag_00, flag_01, flag_02;
    private String psw_00, psw_01, psw_02;
    private TextView commit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_psw_set_02, container, false);
        return view;
    }

    @Override
    public void initView() {
        super.initView();
        edit_account = (EditText) view.findViewById(R.id.accountSafe_pswSet_fragment_02_account);
        edit_new_psw = (EditText) view.findViewById(R.id.accountSafe_pswSet_fragment_01_newPsw);
        edit_new_psw_ = (EditText) view.findViewById(R.id.accountSafe_pswSet_fragment_01_psw);
        commit = (TextView) view.findViewById(R.id.commit);
    }

    @Override
    public void initData() {
        super.initData();

        edit_account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                psw_00 = editable.toString();
                flag_00 = !TextUtils.isEmpty(psw_00);
                boolean flag = flag_00 && flag_01 && flag_02;
                commit.setClickable(flag);
                commit.setBackgroundResource(flag ? R.drawable.green_button_bg : R.drawable.gray_button_bg);
            }
        });

        edit_new_psw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                psw_01 = editable.toString();
                flag_01 = !TextUtils.isEmpty(psw_01);
                boolean flag = flag_00 && flag_01 && flag_02;
                commit.setClickable(flag);
                commit.setBackgroundResource(flag ? R.drawable.green_button_bg : R.drawable.gray_button_bg);
            }
        });

        edit_new_psw_.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                psw_02 = editable.toString();
                flag_02 = !TextUtils.isEmpty(psw_02);
                boolean flag = flag_00 && flag_01 && flag_02;
                commit.setClickable(flag);
                commit.setBackgroundResource(flag ? R.drawable.green_button_bg : R.drawable.gray_button_bg);
            }
        });

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reSetPsw();
            }
        });

    }

    public void reSetPsw() {

        if (TextUtils.isEmpty(psw_00)) {
            Toast.makeText(getActivity(), getResources().getString(R.string.inputNewPwd), Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(psw_01)) {
            Toast.makeText(getActivity(), getResources().getString(R.string.inputNewPwdAgain), Toast.LENGTH_SHORT).show();
        } else if (!psw_01.equals(psw_02)) {
            Toast.makeText(getActivity(), getResources().getString(R.string.PwdNoCommon), Toast.LENGTH_SHORT).show();
        } else if (psw_00.equals(psw_01)) {
            Toast.makeText(getActivity(), "新密码不能和原密码一样", Toast.LENGTH_SHORT).show();
        } else {
            Map<String, Object> map = new TreeMap<>();
            map.put("oldPwd", psw_00);
            map.put("pwd", psw_01);
            HiStudentHttpUtils.postDataByOKHttp((BaseActivity) getActivity(), map, HistudentUrl.exchangePwd_url, new HttpRequestCallBack() {
                @Override
                public void onSuccess(String result) {

                    Toast.makeText(getActivity(), getResources().getString(R.string.setPwdSucceed), Toast.LENGTH_SHORT).show();

                    getActivity().setResult(100);
                    getActivity().finish();

                }

                @Override
                public void onFailure(String errorMsg) {

                }
            });
        }
    }

}
