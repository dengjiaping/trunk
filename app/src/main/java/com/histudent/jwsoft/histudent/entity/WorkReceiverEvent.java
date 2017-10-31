package com.histudent.jwsoft.histudent.entity;

import com.histudent.jwsoft.histudent.bean.homework.HomeworkSelectGroupL0Bean;

import java.util.List;

/**
 * Created by huyg on 2017/10/31.
 */

public class WorkReceiverEvent {
    public List<HomeworkSelectGroupL0Bean> mWorkGroupL0;

    public WorkReceiverEvent(List<HomeworkSelectGroupL0Bean> mWorkGroupL0) {
        this.mWorkGroupL0 = mWorkGroupL0;
    }
}
