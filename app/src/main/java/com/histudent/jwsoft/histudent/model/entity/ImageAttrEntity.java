package com.histudent.jwsoft.histudent.model.entity;

import java.io.Serializable;

/**
 * Created by huyg on 2017/9/13.
 */

public class ImageAttrEntity implements Serializable{
    private String Id;
    private String thumbnailUrl;
    private String bigSizeUrl;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getBigSizeUrl() {
        return bigSizeUrl;
    }

    public void setBigSizeUrl(String bigSizeUrl) {
        this.bigSizeUrl = bigSizeUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
