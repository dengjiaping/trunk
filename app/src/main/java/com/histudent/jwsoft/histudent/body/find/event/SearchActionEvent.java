package com.histudent.jwsoft.histudent.body.find.event;

import com.histudent.jwsoft.histudent.body.find.bean.SecondCommunityBean;

import java.util.List;

/**
 * Created by android03_pc on 2017/5/22.
 */

public class SearchActionEvent {

    public int message;//消息标志位
    public String content;//搜索内容
    public String groupId;//Tab标签Id

    public List<SecondCommunityBean> list ;


    public SearchActionEvent(int message,String content,String groupId) {
        this.message = message;
        this.content = content;
        this.groupId = groupId;
    }

    public SearchActionEvent(int message,List<SecondCommunityBean> list) {
        this.list = list;
        this.message = message;
    }
}
