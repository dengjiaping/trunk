package com.histudent.jwsoft.histudent.model.entity;

import com.histudent.jwsoft.histudent.body.message.model.NoticeBean;

/**
 * Created by huyg on 2017/8/11.
 */

public class NotificationClickEvent {
    public NoticeBean.ItemsBean mNotice;
    public int position;

    public NotificationClickEvent(int position,NoticeBean.ItemsBean itemsBean) {
        this.mNotice = itemsBean;
        this.position = position;
    }
}
