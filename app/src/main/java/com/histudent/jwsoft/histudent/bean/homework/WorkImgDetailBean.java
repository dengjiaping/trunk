package com.histudent.jwsoft.histudent.bean.homework;

/**
 * Created by huyg on 2017/10/31.
 */

public class WorkImgDetailBean {
    /**
     * id : 0b081c82-2bcb-40d2-86cb-4db15562be5c
     * associateObjectId : 92466cc5-5a8a-467a-ad72-85c370ef2649
     * associateObjectType : 1
     * filePath : http://img.hitongx.com/UploadFiles/HomeWorkFile/20171031/ddacd21c-2838-4bc6-86bb-d67164768ba8.jpg@310w_240h_1e_1c_2o.jpg
     * friendlyFileName : 1509432903802659.jpg
     * mediaType : 1
     * contentType : image/jpeg
     * fileLength : 119139
     * height : 1500
     * width : 2000
     * createDate : 2017-10-31 14:55:23
     * downloadNum : 0
     * ext :
     * batchNumber : 00000000-0000-0000-0000-000000000000
     */

    private String id;
    private String associateObjectId;
    private int associateObjectType;
    private String filePath;
    private String friendlyFileName;
    private int mediaType;
    private String contentType;
    private int fileLength;
    private int height;
    private int width;
    private String createDate;
    private int downloadNum;
    private String ext;
    private String batchNumber;

    public String getThumbnailFilePath() {
        return thumbnailFilePath;
    }

    public void setThumbnailFilePath(String thumbnailFilePath) {
        this.thumbnailFilePath = thumbnailFilePath;
    }

    private String thumbnailFilePath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssociateObjectId() {
        return associateObjectId;
    }

    public void setAssociateObjectId(String associateObjectId) {
        this.associateObjectId = associateObjectId;
    }

    public int getAssociateObjectType() {
        return associateObjectType;
    }

    public void setAssociateObjectType(int associateObjectType) {
        this.associateObjectType = associateObjectType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFriendlyFileName() {
        return friendlyFileName;
    }

    public void setFriendlyFileName(String friendlyFileName) {
        this.friendlyFileName = friendlyFileName;
    }

    public int getMediaType() {
        return mediaType;
    }

    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getFileLength() {
        return fileLength;
    }

    public void setFileLength(int fileLength) {
        this.fileLength = fileLength;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getDownloadNum() {
        return downloadNum;
    }

    public void setDownloadNum(int downloadNum) {
        this.downloadNum = downloadNum;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }
}
