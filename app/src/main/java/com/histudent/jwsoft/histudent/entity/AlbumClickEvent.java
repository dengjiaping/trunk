package com.histudent.jwsoft.histudent.entity;

import com.histudent.jwsoft.histudent.body.mine.model.AlbumInfoModel;

/**
 * Created by huyg on 2017/9/28.
 */

public class AlbumClickEvent {
    public AlbumInfoModel model;

    public AlbumClickEvent(AlbumInfoModel model) {
        this.model = model;
    }
}
