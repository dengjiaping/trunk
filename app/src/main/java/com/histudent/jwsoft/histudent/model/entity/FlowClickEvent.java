package com.histudent.jwsoft.histudent.model.entity;

/**
 * Created by huyg on 2017/11/3.
 */

public class FlowClickEvent {
    public int position;
    public boolean isCheck;

    public FlowClickEvent(int position, boolean isCheck) {
        this.position = position;
        this.isCheck = isCheck;
    }
}
