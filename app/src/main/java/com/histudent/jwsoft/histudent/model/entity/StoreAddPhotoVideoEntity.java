package com.histudent.jwsoft.histudent.model.entity;

import com.histudent.jwsoft.histudent.comment2.bean.AddressInforBean;

/**
 * Created by lichaojie on 2017/8/22.
 * desc:
 * 用于添加拍照、拍摄视频存储实体
 */

public class StoreAddPhotoVideoEntity {

    private String mFileMovieUrl;
    private int mFileMovieDuration;
    private String mFileMovieTransfer;
    private boolean mIsVideoMovie;
    private AddressInforBean mAddressInformationBean;
    private double mLatitude;
    private double mLongitude;
    private String mCity;

    public String getFileMovieUrl() {
        return mFileMovieUrl;
    }

    public int getFileMovieDuration() {
        return mFileMovieDuration;
    }

    public String getFileMovieTransfer() {
        return mFileMovieTransfer;
    }

    public boolean isVideoMovie() {
        return mIsVideoMovie;
    }

    public AddressInforBean getAddressInformationBean() {
        return mAddressInformationBean;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public String getCity() {
        return mCity;
    }

    public boolean isAutoLocation() {
        return mIsAutoLocation;
    }

    private boolean mIsAutoLocation;

    public void setFileMovieUrl(String fileMovieUrl) {
        mFileMovieUrl = fileMovieUrl;
    }

    public void setFileMovieDuration(int fileMovieDuration) {
        mFileMovieDuration = fileMovieDuration;
    }

    public void setFileMovieTransfer(String fileMovieTransfer) {
        mFileMovieTransfer = fileMovieTransfer;
    }

    public void setVideoMovie(boolean videoMovie) {
        mIsVideoMovie = videoMovie;
    }

    public void setAddressInformationBean(AddressInforBean addressInformationBean) {
        mAddressInformationBean = addressInformationBean;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public void setAutoLocation(boolean autoLocation) {
        mIsAutoLocation = autoLocation;
    }


}
