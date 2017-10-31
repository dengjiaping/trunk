package com.histudent.jwsoft.histudent.account.login.fragment;

import com.histudent.jwsoft.histudent.commen.fragment.BaseFragment;

/**
 * Created by liuguiyu-pc on 2017/4/10.
 */

public abstract class LoginFragment extends BaseFragment {
    /**
     * 更改显示的账号
     *
     * @param account
     */
    public abstract void exchangeAccount(String account);

}
