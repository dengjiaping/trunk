package com.histudent.jwsoft.histudent.entity;

import android.view.View;

import com.histudent.jwsoft.histudent.body.mine.model.PhotosModel;

import java.util.List;

/**
 * Created by huyg on 2017/9/28.
 */

public class UploadImgClickEvent {

    public List<PhotosModel> models;
    public int position;
    public View view;

    public UploadImgClickEvent(View view, List<PhotosModel> models, int position) {
        this.view = view;
        this.models = models;
        this.position = position;
    }
}
