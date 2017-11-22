package com.histudent.jwsoft.histudent.model.entity;

/**
 * Created by huyg on 2017/9/7.
 */

public class SelectMemberEvent {
    public String mobile;
    public String name;

    public SelectMemberEvent(String mobile, String name) {
        this.mobile = mobile;
        this.name = name;
    }
}
