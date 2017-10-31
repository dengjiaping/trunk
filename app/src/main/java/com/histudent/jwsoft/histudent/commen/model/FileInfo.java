package com.histudent.jwsoft.histudent.commen.model;

/**
 * Created by liuguiyu-pc on 2016/12/30.
 * 保存录音的id 以及时间长度
 */

public class FileInfo {

    private String id;
    private int time;
    private String file;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
