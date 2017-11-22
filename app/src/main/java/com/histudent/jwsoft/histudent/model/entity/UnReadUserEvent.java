package com.histudent.jwsoft.histudent.model.entity;

import com.histudent.jwsoft.histudent.body.myclass.bean.NoticeDetailBean;

/**
 * Created by huyg on 2017/9/28.
 */

public class UnReadUserEvent {
    public NoticeDetailBean.UnReadUserListBean unReadUserListBean;

    public UnReadUserEvent(NoticeDetailBean.UnReadUserListBean unReadUserListBean) {
        this.unReadUserListBean = unReadUserListBean;
    }
}
