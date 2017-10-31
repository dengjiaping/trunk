package com.histudent.jwsoft.histudent.comment2.bean;

import java.util.List;

/**
 * 社群发帖的提交参数类
 * Created by ZhangYT on 2017/5/18.
 */

public class CreateTopicModel {
    private List<String>images;
    private String content;
    private String association;
    private double lat;
    private double lon;
    private String associtionId;
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAssociation() {
        return association;
    }

    public void setAssociation(String association) {
        this.association = association;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getAssocitionId() {
        return associtionId;
    }

    public void setAssocitionId(String associtionId) {
        this.associtionId = associtionId;
    }
}
