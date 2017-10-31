package com.histudent.jwsoft.histudent.body.mine.model;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/8/8.
 */
public class UserBlogModel {

    private int itemCount;
    private int pageCount;
    private List<UserBlogItemModel> items;

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

    public List<UserBlogItemModel> getItems() {
        return items;
    }

    public void setItems(List<UserBlogItemModel> items) {
        this.items = items;
    }
}
