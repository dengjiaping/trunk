package com.histudent.jwsoft.histudent.account.password.model;

/**
 * Created by liuguiyu-pc on 2017/3/17.
 */

public class PhoneModel {
    public int what;
    public String phoneNum;
    public int codeNum;

    public PhoneModel(int what) {
        this.what = what;
    }

    public PhoneModel(int what, String phoneNum) {
        this.what = what;
        this.phoneNum = phoneNum;
    }

    public PhoneModel(int what, String phoneNum, int codeNum) {
        this.what = what;
        this.phoneNum = phoneNum;
        this.codeNum = codeNum;
    }
}
