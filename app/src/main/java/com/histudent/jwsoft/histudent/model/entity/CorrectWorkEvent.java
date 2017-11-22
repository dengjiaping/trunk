package com.histudent.jwsoft.histudent.model.entity;

import com.histudent.jwsoft.histudent.model.bean.homework.WorkCompleteBean;

/**
 * Created by huyg on 2017/11/1.
 */

public class CorrectWorkEvent {
    public WorkCompleteBean.ItemsBean itemsBean;

    public CorrectWorkEvent(WorkCompleteBean.ItemsBean itemsBean) {
        this.itemsBean = itemsBean;
    }
}
