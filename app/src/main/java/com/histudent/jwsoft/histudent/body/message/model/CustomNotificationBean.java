package com.histudent.jwsoft.histudent.body.message.model;

/**
 * Created by liuguiyu-pc on 2016/9/14.
 */
public class CustomNotificationBean {
//     "batchNumber": "05fc0a9d-61f6-4f05-a8f2-1c891087e642",
//             "pushId": "a5aedcc7-a129-41d5-8f6c-138af89dc210",
//             "officiaId": "00000000-0000-0000-0000-000000000000",
//             "Logo": "http://img.hitongx.com/Logo/SystemMsgLogo.png@108w_108h_1e_1c_2o.png",
//             "title": "社群活动开始提醒",
//             "content": "你创建的社群活动\"咯默默看看\"将在2017-08-10 17:44进行,请合理安排时间",
//             "img": "http://img.hitongx.com/HuodongCover/Class/20170810/5e942907-fe60-4bd5-baad-b8b627cbd0ba.jpg@108w_1e_1l_2o.jpg.jpg",
//             "url": "http://192.168.0.252:8020/app/?token={token}#/communityeventdetails?huodongId=aa15203d-3aa1-4871-8eec-4944b214ef87",
//             "openmode": 2,
//             "openparam": "aa15203d-3aa1-4871-8eec-4944b214ef87",
//             "msgtype": 3,
//             "immsgtype": 7,
//             "createtime": "2017-08-10 17:15:00"

    private String batchNumber;
    private String pushId;
    private String officiaId;
    private String Logo;
    private String title;
    private String content;
    private String img;
    private String url;
    private int msgtype;
    private int openmode;
    private String openParam;
    private int immsgtype;

    public int getOpenmode() {
        return openmode;
    }

    public void setOpenmode(int openmode) {
        this.openmode = openmode;
    }

    public String getOpenParam() {
        return openParam;
    }

    public void setOpenParam(String openParam) {
        this.openParam = openParam;
    }

    public int getImmsgtype() {
        return immsgtype;
    }

    public void setImmsgtype(int immsgtype) {
        this.immsgtype = immsgtype;
    }

    private String createtime;

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getOfficiaId() {
        return officiaId;
    }

    public void setOfficiaId(String officiaId) {
        this.officiaId = officiaId;
    }

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String Logo) {
        this.Logo = Logo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(int msgtype) {
        this.msgtype = msgtype;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }


}
