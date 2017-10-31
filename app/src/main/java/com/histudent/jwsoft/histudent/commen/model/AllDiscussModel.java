package com.histudent.jwsoft.histudent.commen.model;

import com.histudent.jwsoft.histudent.commen.enums.ItemType;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/7/26.
 * “我的”中所有 点赞，评论，分享的item的model
 */

class AllDiscussModel {

    private ItemType type;//类型
    private String time;//时间
    private String titile;//标题
    private String content;//内容
    private List<String> list_picture;//图片地址集合
    private String location;//地址
    private String likes;//点赞数量
    private List<String> list_account;//点赞账号集合
    private String comments;//评论数量
//    private List<>

}
