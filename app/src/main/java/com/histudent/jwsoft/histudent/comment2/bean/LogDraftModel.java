package com.histudent.jwsoft.histudent.comment2.bean;

import com.histudent.jwsoft.histudent.body.hiworld.bean.SimpleUserModel;

import java.io.Serializable;
import java.util.List;

/**
 * 日志保存草稿实体类
 * Created by ZhangYT on 2017/4/15.
 */

public class LogDraftModel implements Serializable {


    private String logTitle;//日志的标题
    private List<SimpleUserModel> atUserList;//日志中@的用户集合
    private List<LogContent> itemList;//日志的内容

    public List<LogContent> getItemList() {
        return itemList;
    }

    public void setItemList(List<LogContent> itemList) {
        this.itemList = itemList;
    }

    //
    public class LogContent {//日志的每一项图文混排的对象
        private String itemTitle;
        private List<String> itemImagePathList;

        public String getItemTitle() {
            return itemTitle;
        }

        public void setItemTitle(String itemTitle) {
            this.itemTitle = itemTitle;
        }

        public List<String> getItemImagePathList() {
            return itemImagePathList;
        }

        public void setItemImagePathList(List<String> itemImagePathList) {
            this.itemImagePathList = itemImagePathList;
        }
    }

    public String getLogTitle() {
        return logTitle;
    }

    public void setLogTitle(String logTitle) {
        this.logTitle = logTitle;
    }

    public List<SimpleUserModel> getAtUserList() {
        return atUserList;
    }

    public void setAtUserList(List<SimpleUserModel> atUserList) {
        this.atUserList = atUserList;
    }

}
