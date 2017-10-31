package com.histudent.jwsoft.histudent.entity;

/**
 * Created by lichaojie on 2017/8/22.
 * desc:
 * 视频上传实体
 */

public class StoreUploadVideoEntity {
    public String videoId;
    public String videoCover;
    public int videoTimeLength;

    public StoreUploadVideoEntity() {

    }

    public StoreUploadVideoEntity(String videoId, String videoCover, int videoTimeLength) {
        this.videoId = videoId;
        this.videoCover = videoCover;
        this.videoTimeLength = videoTimeLength;
    }
}
