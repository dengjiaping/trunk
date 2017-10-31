package com.histudent.jwsoft.histudent.comment2.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 回复消息内容的
 * Created by ZhangYT on 2016/7/13.
 */
public class CommentBean implements  Serializable{
    private List<String>list;
    private String name;
    private String replyName;
    private String content;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public CommentBean() {
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReplyName() {
        return replyName;
    }

    public void setReplyName(String replyName) {
        this.replyName = replyName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
