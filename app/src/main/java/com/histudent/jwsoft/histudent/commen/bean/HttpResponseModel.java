package com.histudent.jwsoft.histudent.commen.bean;

/**
 * Created by liuguiyu-pc on 2016/10/26.
 */
public class HttpResponseModel {
    private String data;
    private int ret;
    private String msg;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
