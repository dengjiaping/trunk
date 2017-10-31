package com.histudent.jwsoft.histudent.account.login.parser;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.account.login.model.CurrentUserInfoModel;

/**
 * Created by liuguiyu-pc on 2016/7/27.
 * 解析器，解析登录成功后返回的数据
 */
public class CurrentUserinfoParser {

    public static CurrentUserInfoModel userinfoParser(String data) {

        return JSON.parseObject(data, CurrentUserInfoModel.class);
    }

}
