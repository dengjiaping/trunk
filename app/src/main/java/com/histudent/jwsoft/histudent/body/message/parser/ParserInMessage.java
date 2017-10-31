package com.histudent.jwsoft.histudent.body.message.parser;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.account.login.model.CurrentUserInfoModel;
import com.histudent.jwsoft.histudent.body.message.model.CategoriesModel;
import com.histudent.jwsoft.histudent.body.message.model.FindUserBean;
import com.histudent.jwsoft.histudent.body.message.model.FollowUsersModel;
import com.histudent.jwsoft.histudent.body.message.model.RecommondUserModel;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/8/3.
 * 消息模块中的解析类
 */
public class ParserInMessage {

    public static List<CategoriesModel> getCategoriesInfoParser(String data) {

        return JSON.parseArray(data, CategoriesModel.class);

    }

    public static List<FollowUsersModel> getFollowUsersInfoParser(String data) {

        return JSON.parseArray(data, FollowUsersModel.class);

    }

    public static List<RecommondUserModel> getRecommondUserInfoParser(String data) {

        return JSON.parseArray(data, RecommondUserModel.class);

    }

    public static List<FindUserBean> getFindUserBeanParser(String data) {

        return JSON.parseArray(data, FindUserBean.class);

    }

    public static List<CurrentUserInfoModel> getUserInfoParser(String data) {

        return JSON.parseArray(data, CurrentUserInfoModel.class);

    }

}
