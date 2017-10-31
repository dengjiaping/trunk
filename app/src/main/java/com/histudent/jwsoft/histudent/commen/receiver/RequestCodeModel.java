package com.histudent.jwsoft.histudent.commen.receiver;

/**
 * Created by liuguiyu-pc on 2016/9/29.
 * 全部请求码定义类
 */
public class RequestCodeModel {

    public int PUBLIC = 99;
    public int TIME = 100;//时间轴
    public int ESSAY = 101;//随记
    public int LOG_P = 102;//个人日志
    public int LOG_C = 103;//班级日志
    public int GOODFRIENDA_ACTION = 104;//好友动态
    public int CLASS_ACTION_C = 105;//班级中班级动态
    public int CLASS_ACTION_H = 106;//Hi圈中班级动态
    public int ABOUTMINE = 107;//与我相关
    public int CLASS = 108;//班级
    public int PERSION = 109;//个人
    public int PHOTO = 110;//照片

    private static RequestCodeModel codeModel;

    private RequestCodeModel() {
    }

    public static RequestCodeModel getInstence() {

        if (codeModel == null) {
            codeModel = new RequestCodeModel();
        }
        return codeModel;
    }
}
