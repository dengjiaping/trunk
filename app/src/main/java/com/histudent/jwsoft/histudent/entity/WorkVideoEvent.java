package com.histudent.jwsoft.histudent.entity;

import com.histudent.jwsoft.histudent.bean.homework.HomeworkDetailBean;

/**
 * Created by huyg on 2017/11/3.
 */

public class WorkVideoEvent {
    public HomeworkDetailBean.VideoListBean videoListBean;

    public WorkVideoEvent(HomeworkDetailBean.VideoListBean videoListBean) {
        this.videoListBean = videoListBean;
    }
}
