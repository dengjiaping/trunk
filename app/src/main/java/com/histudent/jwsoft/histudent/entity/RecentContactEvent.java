package com.histudent.jwsoft.histudent.entity;

import com.netease.nimlib.sdk.msg.model.RecentContact;

import java.util.List;

/**
 * Created by huyg on 2017/8/3.
 */

public class RecentContactEvent {
    public List<RecentContact> recents;

    public RecentContactEvent(List<RecentContact> recents) {
        this.recents = recents;
    }
}
