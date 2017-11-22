package com.histudent.jwsoft.histudent.model.bean;

import com.histudent.jwsoft.histudent.body.mine.model.PhotosModel;

import java.util.List;

/**
 * Created by huyg on 2017/9/28.
 */

public class UploadImgBean {
    private List<PhotosModel> items;

    public List<PhotosModel> getItems() {
        return items;
    }

    public void setItems(List<PhotosModel> items) {
        this.items = items;
    }
}
