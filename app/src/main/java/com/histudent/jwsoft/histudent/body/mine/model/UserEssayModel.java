package com.histudent.jwsoft.histudent.body.mine.model;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/8/11.
 */
public class UserEssayModel {

    private int itemCount;// 119,
    private int pageCount;// 15,
    private List<EssayModel> items;//

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<EssayModel> getItems() {
        return items;
    }

    public void setItems(List<EssayModel> items) {
        this.items = items;
    }
}