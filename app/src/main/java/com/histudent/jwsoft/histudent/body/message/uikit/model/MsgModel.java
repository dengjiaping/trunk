package com.histudent.jwsoft.histudent.body.message.uikit.model;

/**
 * Created by liuguiyu-pc on 2017/6/9.
 */

public class MsgModel {

    /**
     * type : 6
     * data : {"Msg":"80f24273-3c44-4c3c-bc8e-1975c31fbabe","title":"摇一摇","content":"哦哦哦","img":"http://img.hitongx.com/HuodongCover/Class/20170609/c224397d-b5a9-47be-a422-b8cd5606c79b.jpg@108w_1e_1l_2o.jpg.jpg","url":"http://192.168.0.252:8020/app/?token={token}#/classeventdetails?huodongId=398de321-4d7f-49ee-91a3-859b6afdecb5","openmode":2,"openparam":"398de321-4d7f-49ee-91a3-859b6afdecb5","msgtype":5,"immsgtype":0,"createtime":"2017-06-09 19:58:01"}
     */

    private int type;
    private DataBean data;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * Msg : 80f24273-3c44-4c3c-bc8e-1975c31fbabe
         * title : 摇一摇
         * content : 哦哦哦
         * img : http://img.hitongx.com/HuodongCover/Class/20170609/c224397d-b5a9-47be-a422-b8cd5606c79b.jpg@108w_1e_1l_2o.jpg.jpg
         * url : http://192.168.0.252:8020/app/?token={token}#/classeventdetails?huodongId=398de321-4d7f-49ee-91a3-859b6afdecb5
         * openmode : 2
         * openparam : 398de321-4d7f-49ee-91a3-859b6afdecb5
         * msgtype : 5
         * immsgtype : 0
         * createtime : 2017-06-09 19:58:01
         */

        private String Msg;
        private String title;
        private String content;
        private String img;
        private String url;
        private int openmode;
        private String openparam;
        private int msgtype;
        private int immsgtype;
        private String createtime;

        public String getMsg() {
            return Msg;
        }

        public void setMsg(String Msg) {
            this.Msg = Msg;
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

        public int getOpenmode() {
            return openmode;
        }

        public void setOpenmode(int openmode) {
            this.openmode = openmode;
        }

        public String getOpenparam() {
            return openparam;
        }

        public void setOpenparam(String openparam) {
            this.openparam = openparam;
        }

        public int getMsgtype() {
            return msgtype;
        }

        public void setMsgtype(int msgtype) {
            this.msgtype = msgtype;
        }

        public int getImmsgtype() {
            return immsgtype;
        }

        public void setImmsgtype(int immsgtype) {
            this.immsgtype = immsgtype;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }
    }
}
