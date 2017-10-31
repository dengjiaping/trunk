package com.histudent.jwsoft.histudent.account.login.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.histudent.jwsoft.histudent.HiStudentApplication;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.account.login.adapter.SpinnerAdapter;
import com.histudent.jwsoft.histudent.account.login.model.LoginData;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.fragment.BaseFragment;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.model.SaveAccountModel;
import com.histudent.jwsoft.histudent.commen.utils.DataUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.view.IconView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuguiyu-pc on 2017/4/10.
 * 账号登录
 */

public class AccountFragmnet extends BaseFragment implements View.OnClickListener {

    private View view;
    private IconView showPassword, showAccount;
    private EditText edit_accont, edit_password;
    private List<SaveAccountModel> account_list;
    private SpinnerAdapter adapter;
    private RelativeLayout layout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return view = inflater.inflate(R.layout.fragment_login_account, container, false);
    }

    @Override
    public void initView() {
        super.initView();

        showPassword = view.findViewById(R.id.login_password_image);
        showAccount = (IconView) view.findViewById(R.id.login_account_image);
        edit_accont = (EditText) view.findViewById(R.id.login_account_edit);
        edit_password = (EditText) view.findViewById(R.id.login_password_edit);
        layout = (RelativeLayout) view.findViewById(R.id.layout);

        showPassword.setOnClickListener(this);

        if (HiStudentApplication.isOnLine) {
            showAccount.setVisibility(View.GONE);
        } else {
            showAccount.setOnClickListener(this);
        }

        SaveAccountModel model_account = HiCache.getInstance().getAccountDataInDB(HiCache.getInstance().getAcount());
        if (model_account != null) {

            if (model_account.getAccount().startsWith("hi")) return;

            edit_accont.setText(model_account.getAccount());

            DataUtils.showSoftInputFromWindow(getActivity(), edit_password);

            LoginData loginData = new LoginData(model_account.getAccount(), true, null, true);
            EventBus.getDefault().post(loginData);
        }

    }

    @Override
    public void initData() {
        super.initData();

        edit_accont.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                LoginData loginData = new LoginData(edit_accont.getText().toString(), true, edit_password.getText().toString(), true);
                EventBus.getDefault().post(loginData);

            }
        });

        edit_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                LoginData loginData = new LoginData(edit_accont.getText().toString(), false, edit_password.getText().toString(), true);
                EventBus.getDefault().post(loginData);

            }
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.login_account_image://显示账号下拉列表
                showAccountList();
                break;

            case R.id.login_password_image://显示（隐藏）密码
                ReminderHelper.getIntentce().exchangePswStatue(getActivity(), showPassword, edit_password);
                break;
        }

    }

    /**
     * 显示下拉账号列表
     */
    public void showAccountList() {
        SystemUtil.hideSoftKeyboard(getActivity());
        showWindow();
    }

    /**
     * 显示弹窗
     */
    private void showWindow() {

        List<SaveAccountModel> list_model = HiCache.getInstance().getAllAccountDataInDB();
        account_list = new ArrayList<>();
        if (list_model != null) {
            account_list.addAll(list_model);
        }

        View view = getActivity().getLayoutInflater().inflate(R.layout.spinner_window, null);
        adapter = new SpinnerAdapter(getActivity(), account_list);

        ListView listView = (ListView) view.findViewById(R.id.spinnerWindow);

        listView.setAdapter(adapter);

        final PopupWindow mPop = new PopupWindow(view, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        mPop.setWidth(layout.getWidth());

        mPop.setTouchable(true);
        // 需要设置一下此参数，点击外边可消失
        mPop.setBackgroundDrawable(new BitmapDrawable(getActivity().getResources(), (Bitmap) null));
        //设置点击窗口外边窗口消失
        mPop.setOutsideTouchable(true);
        mPop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 设置此参数获得焦点，否则无法点击
        mPop.setFocusable(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SaveAccountModel model = account_list.get(position);
                edit_accont.setText(model.getAccount());
                EventBus.getDefault().post(model);
                mPop.dismiss();

            }
        });

        mPop.showAsDropDown(layout);
    }

}
