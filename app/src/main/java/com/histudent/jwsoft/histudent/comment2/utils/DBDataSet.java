package com.histudent.jwsoft.histudent.comment2.utils;

import com.lidroid.xutils.db.annotation.Table;

/**
 *
 * 保存用户浏览的最后一页的页码
 * Created by ZhangYT on 2016/8/6.
 */

@Table
public class DBDataSet {
    private String id;
    private int lastPageIndex;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLastPageIndex() {
        return lastPageIndex;
    }

    public void setLastPageIndex(int lastPageIndex) {
        this.lastPageIndex = lastPageIndex;
    }
}
