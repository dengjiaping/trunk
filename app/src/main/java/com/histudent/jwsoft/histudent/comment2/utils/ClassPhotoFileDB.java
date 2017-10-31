package com.histudent.jwsoft.histudent.comment2.utils;

/**
 * Created by ZhangYT on 2016/8/3.
 */
public class ClassPhotoFileDB {

    private String data;//相册的信息数据
    private String id;//用户的账号
    private  int categoryId;//相册分类

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
