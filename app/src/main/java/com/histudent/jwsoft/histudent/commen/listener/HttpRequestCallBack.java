package com.histudent.jwsoft.histudent.commen.listener;

/**
 * Created by liuguiyu-pc on 2016/8/5.
 */
public interface HttpRequestCallBack {

    void onSuccess(String result);

    void onFailure(String errorMsg);

}
