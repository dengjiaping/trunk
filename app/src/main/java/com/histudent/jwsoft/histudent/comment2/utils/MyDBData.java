package com.histudent.jwsoft.histudent.comment2.utils;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by ZhangYT on 2016/8/6.
 */

@Table
public class MyDBData {


    @Id
    private String id;

    private String data;//缓存的内容
    private int currentPageIndex;//缓存到的当前页的下标

    public String getId() {
        return id;
    }


    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    public void setCurrentPageIndex(int currentPageIndex) {
        this.currentPageIndex = currentPageIndex;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
