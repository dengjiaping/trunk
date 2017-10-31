package com.histudent.jwsoft.histudent.body.find.bean;

import com.histudent.jwsoft.histudent.comment2.bean.NetImageModel;

import java.io.Serializable;
import java.util.List;

/**
 * 创建活动提交表单时的工具类
 * Created by ZhangYT on 2016/8/16.
 */
public class CreateHuoDongBean implements Serializable {
    private String areaStr;
    private String startTime;//活动的开始时间
    private String endTime;//活动的结束时间
    private String imageUrl; //活动的封面，本地图片需要上传
    private String instruction;//文字介绍部分
    private String palce;
    private int limitCount = -1; //人数限制
    private int price = -1; //费用
    private List<String> deleteImageId; //已经删除的网络图片的id 集合，需要上传
    private List<String> images;//用户选择的本地需要上传的图片的路径，用于上传
    private String city;
    private Double lat;//活动的位置信息
    private Double lon;
    private String huoDongId; //活动的id,用于区别是编辑还是创建 ，创建时提交表单不需要提交id ，编辑时需要提交id
    private String huoDongName;
    private int alarmType = -1;//活动提醒时间的枚举，用于社群活动
    private List<NetImageModel> instrFileList; //保存网络图片的集合，编辑情况的下的显示
    private String id;//班级或者社群的id
    private int type;//用来区别活动的类型，班级活动、社群活动或者其他活动


    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getDeleteImageId() {
        return deleteImageId;
    }

    public void setDeleteImageId(List<String> deleteImageId) {
        this.deleteImageId = deleteImageId;
    }

    public int getLimitCount() {
        return limitCount;
    }

    public void setLimitCount(int limitCount) {
        this.limitCount = limitCount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPalce() {
        return palce;
    }

    public void setPalce(String palce) {
        this.palce = palce;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getHuoDongId() {
        return huoDongId;
    }

    public void setHuoDongId(String huoDongId) {
        this.huoDongId = huoDongId;
    }

    public List<NetImageModel> getInstrFileList() {
        return instrFileList;
    }

    public void setInstrFileList(List<NetImageModel> instrFileList) {
        this.instrFileList = instrFileList;
    }

    public int getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(int alarmType) {
        this.alarmType = alarmType;
    }

    public String getHuoDongName() {
        return huoDongName;
    }

    public void setHuoDongName(String huoDongName) {
        this.huoDongName = huoDongName;
    }


    public String getAreaStr() {
        return areaStr;
    }

    public void setAreaStr(String areaStr) {
        this.areaStr = areaStr;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
}
