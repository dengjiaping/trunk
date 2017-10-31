package com.histudent.jwsoft.histudent.body.mine.parser;

import android.text.TextUtils;
import android.text.format.Time;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.body.mine.model.CurrentUserDetailInfoModel;
import com.histudent.jwsoft.histudent.body.mine.model.EssayModel;
import com.histudent.jwsoft.histudent.body.mine.model.RecentlyPictureModel;
import com.histudent.jwsoft.histudent.body.mine.model.UserAlbumInfoModel;
import com.histudent.jwsoft.histudent.body.mine.model.UserBlogItemModel;
import com.histudent.jwsoft.histudent.body.mine.model.UserClassListModel;
import com.histudent.jwsoft.histudent.body.mine.model.UserTimeModel;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/7/27.
 * "我的"相关数据解析类
 */
public class DataParser {

    /**
     * 解析获取用户详细信息的model
     *
     * @param data
     */
    public static CurrentUserDetailInfoModel getUserinfoParser(String data) {

        CurrentUserDetailInfoModel model = JSON.parseObject(data, CurrentUserDetailInfoModel.class);

        model.getProfile().setAge(getAge(model.getProfile().getBirthDay()));

        return model;

    }

    /**
     * 解析获取相关日志信息的model
     *
     * @param data
     */
    public static UserBlogItemModel getBlogParser(String data) {

        return JSON.parseObject(data, UserBlogItemModel.class);

    }

    /**
     * 解析获取相关相册信息的model
     *
     * @param data
     */
    public static UserAlbumInfoModel getAlbumParser(String data) {

        return JSON.parseObject(data, UserAlbumInfoModel.class);

    }

    /**
     * 解析获取个人主页时间轴model
     *
     * @param data
     */
    public static UserTimeModel getUserTimeModel(String data) {

        return JSON.parseObject(data, UserTimeModel.class);

    }

    /**
     * 解析获取个人主页随记model
     *
     * @param data
     */
    public static EssayModel getUserEssayModel(String data) {

        return JSON.parseObject(data, EssayModel.class);

    }

    /**
     * 解析获取个人班级列表l
     *
     * @param data
     */
    public static List<UserClassListModel> getUserClassList(String data) {

        return JSON.parseArray(data, UserClassListModel.class);

    }

    /**
     * 解析获取个人主页最近照片model
     *
     * @param data
     */
    public static RecentlyPictureModel getRecentlyPictureModel(String data) {

        return JSON.parseObject(data, RecentlyPictureModel.class);

    }

    public static String getAge(String birth) {

        if (TextUtils.isEmpty(birth))
            return "";

        Time time = new Time("GMT+8");
        time.setToNow();
        int year = time.year;

        String year_ = birth.split("-")[0];

        int year_b = Integer.parseInt(year_);

        return (year - year_b) + "";
    }

}
