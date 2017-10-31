package com.histudent.jwsoft.histudent.body.mine.model;

/**
 * Created by liuguiyu-pc on 2016/8/11.
 */
public class TimeImageModel {

    private String imgId;
    private String name;// (string, optional): 图片名称 ,
    private String thumbnailUrl;// (string, optional): 缩略图Url ,
    private String bigSizeUrl;// (string, optional): 大尺寸图Url

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getBigSizeUrl() {
        return bigSizeUrl;
    }

    public void setBigSizeUrl(String bigSizeUrl) {
        this.bigSizeUrl = bigSizeUrl;
    }
}
