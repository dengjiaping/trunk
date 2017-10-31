package com.histudent.jwsoft.histudent.body.mine.model;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/8/11.
 */
public class UserAlbumInfoModel {

    private int itemCount;
    private int pageCount;
    private List<AlbumInfoModel> items;

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

    public List<AlbumInfoModel> getItems() {
        return items;
    }

    public void setItems(List<AlbumInfoModel> items) {
        this.items = items;
    }
}
