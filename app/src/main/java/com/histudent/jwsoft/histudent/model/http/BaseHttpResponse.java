package com.histudent.jwsoft.histudent.model.http;

/**
 * Created by huyg on 2017/1/17.
 */

public class BaseHttpResponse {
    private int ret;
    private String msg;
    public static int SUCCESS = 1;

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


    public boolean isSuccess() {
        return SUCCESS == ret;
    }


}
