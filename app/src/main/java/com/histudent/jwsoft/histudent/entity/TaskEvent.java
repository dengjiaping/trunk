package com.histudent.jwsoft.histudent.entity;

import com.histudent.jwsoft.histudent.body.myclass.bean.GrouthTaskModel;

/**
 * Created by huyg on 2017/7/3.
 */

public class TaskEvent {

    private GrouthTaskModel.TaskListBean mTask;

    public TaskEvent( GrouthTaskModel.TaskListBean task){
        this.mTask = task;
    }

    public GrouthTaskModel.TaskListBean getTask() {
        return mTask;
    }

    public void setmTask(GrouthTaskModel.TaskListBean mTask) {
        this.mTask = mTask;
    }
}
