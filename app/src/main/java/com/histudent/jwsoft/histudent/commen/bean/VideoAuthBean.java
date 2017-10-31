package com.histudent.jwsoft.histudent.commen.bean;

/**
 * Created by huyg on 2017/10/17.
 */

public class VideoAuthBean {

    private String playAuth;
    private String requestId;
    private String videoId;
    private long duration;

    public String getPlayAuth() {
        return playAuth;
    }

    public void setPlayAuth(String playAuth) {
        this.playAuth = playAuth;
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

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
