package com.histudent.jwsoft.histudent.entity;

import com.histudent.jwsoft.histudent.bean.homework.HomeworkDetailBean;
import com.histudent.jwsoft.histudent.bean.homework.VideoDetailBean;

/**
 * Created by huyg on 2017/11/3.
 */

public class WorkVideoEvent {
    public VideoDetailBean videoDetailBean;

    public WorkVideoEvent(VideoDetailBean videoDetailBean) {
        this.videoDetailBean = videoDetailBean;
    }
}
