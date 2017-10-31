package com.histudent.jwsoft.histudent.body.mine.model;

/**
 * Created by liuguiyu-pc on 2016/8/2.
 */
public class CountInfoModel {

    private int blogCount;// 日志数

    private int microBlogCount;// 随记数量

    private int photoCount;// 照片数量

    public int getBlogCount() {
        return blogCount;
    }

    public void setBlogCount(int blogCount) {
        this.blogCount = blogCount;
    }

    public int getMicroBlogCount() {
        return microBlogCount;
    }

    public void setMicroBlogCount(int microBlogCount) {
        this.microBlogCount = microBlogCount;
    }

    public int getPhotoCount() {
        return photoCount;
    }

    public void setPhotoCount(int photoCount) {
        this.photoCount = photoCount;
    }
}
