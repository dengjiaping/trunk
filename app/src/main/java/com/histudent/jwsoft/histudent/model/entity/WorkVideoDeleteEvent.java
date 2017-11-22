package com.histudent.jwsoft.histudent.model.entity;

/**
 * Created by huyg on 2017/10/26.
 */

public class WorkVideoDeleteEvent {
    public VideoInfoEntity videoInfoEntity;
    public int position;

    public WorkVideoDeleteEvent(VideoInfoEntity videoInfoEntity, int position) {
        this.videoInfoEntity = videoInfoEntity;
        this.position = position;
    }
}
