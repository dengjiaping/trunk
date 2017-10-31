package com.histudent.jwsoft.histudent.commen.utils;

/**
 * Created by liuguiyu-pc on 2016/10/25.
 */
public interface ReqCallBack<T> {
    /**
     * 响应成功
     */
    void onReqSuccess(T result);

    /**
     * 响应失败
     */
    void onReqFailed(String errorMsg);
}
