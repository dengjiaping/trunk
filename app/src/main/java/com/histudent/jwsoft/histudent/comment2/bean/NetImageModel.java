package com.histudent.jwsoft.histudent.comment2.bean;

import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;

import java.io.Serializable;

/**
 * 网络图片的实体类
 * Created by ZhangYT on 2017/5/17.
 */

public class NetImageModel implements Serializable {

    private int width = SystemUtil.getScreenWind(); //图片的宽度
    private int height; //图片的高度
    private String id; //图片的id
    private String url; //图urL片的
    private boolean isLocal; //是否是本地图片

    public boolean getIsLocal() {
        return isLocal;
    }

    public void setIsLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
