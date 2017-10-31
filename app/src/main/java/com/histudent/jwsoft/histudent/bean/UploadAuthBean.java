package com.histudent.jwsoft.histudent.bean;

/**
 * Created by huyg on 2017/10/26.
 */
//        "uploadAuth": "string",
//        "uploadAddress": "string",
//        "requestId": "string",
//        "videoId": "string"
public class UploadAuthBean {
    private String uploadAuth;
    private String uploadAddress;
    private String requestId;
    private String videoId;

    public String getUploadAuth() {
        return uploadAuth;
    }

    public void setUploadAuth(String uploadAuth) {
        this.uploadAuth = uploadAuth;
    }

    public String getUploadAddress() {
        return uploadAddress;
    }

    public void setUploadAddress(String uploadAddress) {
        this.uploadAddress = uploadAddress;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
