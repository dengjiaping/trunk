package com.histudent.jwsoft.histudent.model.entity;

import com.histudent.jwsoft.histudent.body.message.uikit.session.fragment.RecentContactsModel;

/**
 * Created by huyg on 2017/7/24.
 */

public class RecentContactDelete {

    public RecentContactsModel model;
    public int position;

    public RecentContactDelete(RecentContactsModel model, int position) {
        this.model = model;
        this.position = position;
    }
}
