package com.histudent.jwsoft.histudent.body.mine.model;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/8/19.
 */
public class AlbumAdapterModel {

    String time;
    List<PictureInAlbumModel.ItemsBean.PhotoListBean> beans;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<PictureInAlbumModel.ItemsBean.PhotoListBean> getBeans() {
        return beans;
    }

    public void setBeans(List<PictureInAlbumModel.ItemsBean.PhotoListBean> beans) {
        this.beans = beans;
    }
}
