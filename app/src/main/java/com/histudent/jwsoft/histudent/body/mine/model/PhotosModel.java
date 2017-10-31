package com.histudent.jwsoft.histudent.body.mine.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by liuguiyu-pc on 2016/8/11.
 */
public class PhotosModel {
    private String photoId;// (string, optional): 照片ID ,
    private String albumId;// (string, optional): 所属相册 ,
    private String ownerId;// (string, optional): 拥有者ID ,
    private String userId;//(string, optional): 用户ID ,
    private String author;//(string, optional): 作者 ,
    private String filePath;// (string, optional): 文件路径 ,
    private int fileLength;// (integer, optional): 文件大小 ,
    private int width;//(integer, optional): 照片宽度 ,
    private int height;//(integer, optional): 照片高度 ,
    private String name;//(string, optional): 照片名称 ,
    private String description;//(string, optional): 照片描述 ,
    private int auditStatus;// (integer, optional): 审核状态 ,
    private String uploadIP;// (string, optional): 上传IP ,
    private String createTime;//(string, optional): 创建时间 ,
    private String updateUserId;// (string, optional): 更新者 ,
    private String updateTime;// (string, optional): 更细时间 ,
    private int praiseNum;//(integer, optional, read only): 点赞数 ,
    private int commentNum;//(integer, optional, read only): 评论数 ,
    private int visitNum;//(integer, optional, read only): 浏览数 ,
    private boolean isDeleted;//(boolean, optional): 是否已删除

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getFileLength() {
        return fileLength;
    }

    public void setFileLength(int fileLength) {
        this.fileLength = fileLength;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(int auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getUploadIP() {
        return uploadIP;
    }

    public void setUploadIP(String uploadIP) {
        this.uploadIP = uploadIP;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(int praiseNum) {
        this.praiseNum = praiseNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public int getVisitNum() {
        return visitNum;
    }

    public void setVisitNum(int visitNum) {
        this.visitNum = visitNum;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
