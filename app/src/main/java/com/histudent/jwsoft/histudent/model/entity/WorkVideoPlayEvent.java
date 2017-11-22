package com.histudent.jwsoft.histudent.model.entity;

/**
 * Created by huyg on 2017/10/26.
 */

public class WorkVideoPlayEvent {
    public VideoInfoEntity videoInfoEntity;
    public int position;

    public WorkVideoPlayEvent(VideoInfoEntity videoInfoEntity) {
        this.videoInfoEntity = videoInfoEntity;
    }
}
