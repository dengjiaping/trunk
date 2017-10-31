package com.histudent.jwsoft.histudent.commen.bean;

/**
 * Created by liuguiyu-pc on 2017/3/17.
 * 验证码相关model
 */

public class CodeModel {

    private String phone;
    private int type;
    private long time;

    public CodeModel(String phone, int type, long time) {
        this.phone = phone;
        this.type = type;
        this.time = time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
