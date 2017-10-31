package com.histudent.jwsoft.histudent.entity;

/**
 * Created by huyg on 2017/10/26.
 */

public class VideoInfoEntity {
    private String fileName;
    private long duration;
    private String cover;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
