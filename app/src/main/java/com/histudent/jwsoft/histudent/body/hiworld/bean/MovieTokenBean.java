package com.histudent.jwsoft.histudent.body.hiworld.bean;

/**
 * Created by liuguiyu-pc on 2017/4/14.
 */

public class MovieTokenBean {
    /**
     * data : {"requestId":"string","uploadAddress":"string","uploadAuth":"string","videoId":"string"}
     * ret : 0
     * msg : string
     * excuteTime : 0
     */

    private DataBean data;
    private int ret;
    private String msg;
    private int excuteTime;


    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getExcuteTime() {
        return excuteTime;
    }

    public void setExcuteTime(int excuteTime) {
        this.excuteTime = excuteTime;
    }

    public static class DataBean {
        /**
         * requestId : string
         * uploadAddress : string
         * uploadAuth : string
         * videoId : string
         */

        private String requestId;
        private String uploadAddress;
        private String uploadAuth;
        private String videoId;

        public String getRequestId() {
            return requestId;
        }

        public void setRequestId(String requestId) {
            this.requestId = requestId;
        }

        public String getUploadAddress() {
            return uploadAddress;
        }

        public void setUploadAddress(String uploadAddress) {
            this.uploadAddress = uploadAddress;
        }

        public String getUploadAuth() {
            return uploadAuth;
        }

        public void setUploadAuth(String uploadAuth) {
            this.uploadAuth = uploadAuth;
        }

        public String getVideoId() {
            return videoId;
        }

        public void setVideoId(String videoId) {
            this.videoId = videoId;
        }
    }
}
