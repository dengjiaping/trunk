package com.histudent.jwsoft.histudent.account.login.model;

import java.io.Serializable;

/**
 * Created by liuguiyu-pc on 2017/4/11.
 */

public class LoginData implements Serializable {

    private String account;
    private String pwd;
    private boolean isAccountLogin, isAccountExchange;

    public LoginData(String account, boolean isAccountExchange, String pwd, boolean isAccountLogin) {
        this.account = account;
        this.pwd = pwd;
        this.isAccountExchange = isAccountExchange;
        this.isAccountLogin = isAccountLogin;
    }

    public boolean isAccountExchange() {
        return isAccountExchange;
    }

    public void setAccountExchange(boolean accountExchange) {
        isAccountExchange = accountExchange;
    }

    public boolean isAccountLogin() {
        return isAccountLogin;
    }

    public void setAccountLogin(boolean accountLogin) {
        isAccountLogin = accountLogin;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return account + "==" + pwd;
    }

}
