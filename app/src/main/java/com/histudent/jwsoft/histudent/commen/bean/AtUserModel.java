package com.histudent.jwsoft.histudent.commen.bean;

import java.io.Serializable;

/**
 * Created by huyg on 2017/6/27.
 */
//"userId": "9d47904f-567c-4019-acde-25713e1b3e5b",
//        "realName": "老鼠"
public class AtUserModel implements Serializable {

    private String userId;
    private String realName;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
