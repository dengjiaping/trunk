package com.histudent.jwsoft.histudent.body.hiworld.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于存放图片关联学生的信息
 * Created by ZhangYT on 2017/4/12.
 */

public class RelationPersonModel implements Serializable {

    private String picName;//关联图片的名称
    private ArrayList<SimpleUserModel> users;//关联学生的id

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public List<SimpleUserModel> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<SimpleUserModel> userIds) {
        this.users = userIds;
    }




}
