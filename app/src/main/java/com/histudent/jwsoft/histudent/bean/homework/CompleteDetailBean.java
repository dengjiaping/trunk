package com.histudent.jwsoft.histudent.bean.homework;

import android.widget.ScrollView;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huyg on 2017/11/2.
 */

public class CompleteDetailBean implements Serializable{
    /**
     * completeVoice : {"id":"6c24806e-98c6-4e79-abed-46c4b329d4f0","filePath":"http://files.hitongx.com/UploadFiles/HomeWorkFile/20171102/575a0b18-d30b-4f39-adec-dd0db6fc26fe.aac","mediaType":0,"fileLength":0,"createDate":"0001-01-01 00:00:00","batchNumber":"00000000-0000-0000-0000-000000000000"}
     * completeImages : [{"id":"abd4122e-e82a-4954-b214-721fb3a5071b","associateObjectId":"6cd60bc6-9cbf-4476-a07e-498319f64f4e","associateObjectType":2,"filePath":"http://img.hitongx.com/UploadFiles/HomeWorkFile/20171102/94f51085-d635-4e8a-9de9-d8d088d39d14.jpg","thumbnailFilePath":"http://img.hitongx.com/UploadFiles/HomeWorkFile/20171102/94f51085-d635-4e8a-9de9-d8d088d39d14.jpg@310w_240h_1e_1c_2o.jpg","friendlyFileName":"1509609736164931.jpg","mediaType":1,"contentType":"image/jpeg","fileLength":208560,"height":1500,"width":2000,"createDate":"2017-11-02 16:02:38","downloadNum":0,"ext":"","batchNumber":"00000000-0000-0000-0000-000000000000"},{"id":"ccd9da8d-0f40-4bef-bef8-c2b294bc31b9","associateObjectId":"6cd60bc6-9cbf-4476-a07e-498319f64f4e","associateObjectType":2,"filePath":"http://img.hitongx.com/UploadFiles/HomeWorkFile/20171102/3db36a2b-57ff-4087-8d0b-1f236d194e40.jpg","thumbnailFilePath":"http://img.hitongx.com/UploadFiles/HomeWorkFile/20171102/3db36a2b-57ff-4087-8d0b-1f236d194e40.jpg@310w_240h_1e_1c_2o.jpg","friendlyFileName":"1509609735944775.jpg","mediaType":1,"contentType":"image/jpeg","fileLength":101754,"height":1920,"width":1080,"createDate":"2017-11-02 16:02:38","downloadNum":0,"ext":"","batchNumber":"00000000-0000-0000-0000-000000000000"},{"id":"d8081541-28c6-4a63-b8ee-d0f2b8abfb01","associateObjectId":"6cd60bc6-9cbf-4476-a07e-498319f64f4e","associateObjectType":2,"filePath":"http://img.hitongx.com/UploadFiles/HomeWorkFile/20171102/82b91ce0-c6af-425e-aef9-8c84534d0684.jpg","thumbnailFilePath":"http://img.hitongx.com/UploadFiles/HomeWorkFile/20171102/82b91ce0-c6af-425e-aef9-8c84534d0684.jpg@310w_240h_1e_1c_2o.jpg","friendlyFileName":"1509609735791788.jpg","mediaType":1,"contentType":"image/jpeg","fileLength":66423,"height":1178,"width":884,"createDate":"2017-11-02 16:02:38","downloadNum":0,"ext":"","batchNumber":"00000000-0000-0000-0000-000000000000"}]
     * isCommented : false
     * id : 6cd60bc6-9cbf-4476-a07e-498319f64f4e
     * hasImage : true
     * hasVoice : true
     * hasVideo : false
     * completeTime : 2017-11-02 16:02:38
     * homeworkId : 87b74ed8-c2e9-45ba-a8be-f8fee4f65387
     * userId : b87664a1-94ad-4ac5-9325-cd2123c90010
     * userRealName : huyg
     * userAvatar : http://img.hitongx.com/UploadFiles/UploadAvatar/UserSystemAvatar/cn_zodiac_08.png@400w_400h_1e_1c_2o_200-1ci.png
     */

    private CompleteVoiceBean completeVoice;
    private List<VideoDetailBean> completeVideos;
    private List<CompleteImagesBean> completeImages;
    private CompleteVoiceBean commentedVoice;



    private boolean isCommented;
    private String id;
    private boolean hasImage;
    private boolean hasVoice;
    private boolean hasVideo;
    private String completeTime;
    private String homeworkId;
    private String userId;
    private String userRealName;
    private String userAvatar;
    private String contents;
    private String comment;
    private boolean commentImg;
    private boolean commentVoice;
    private String commentTime;
    private String commentUser;
    public CompleteVoiceBean getCommentedVoice() {
        return commentedVoice;
    }

    public void setCommentedVoice(CompleteVoiceBean commentedVoice) {
        this.commentedVoice = commentedVoice;
    }
    public boolean isCommented() {
        return isCommented;
    }

    public void setCommented(boolean commented) {
        isCommented = commented;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isCommentImg() {
        return commentImg;
    }

    public void setCommentImg(boolean commentImg) {
        this.commentImg = commentImg;
    }

    public boolean isCommentVoice() {
        return commentVoice;
    }

    public void setCommentVoice(boolean commentVoice) {
        this.commentVoice = commentVoice;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(String commentUser) {
        this.commentUser = commentUser;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }



    public CompleteVoiceBean getCompleteVoice() {
        return completeVoice;
    }

    public void setCompleteVoice(CompleteVoiceBean completeVoice) {
        this.completeVoice = completeVoice;
    }

    public List<VideoDetailBean> getCompleteVideos() {
        return completeVideos;
    }

    public void setCompleteVideos(List<VideoDetailBean> completeVideos) {
        this.completeVideos = completeVideos;
    }

    public boolean isIsCommented() {
        return isCommented;
    }

    public void setIsCommented(boolean isCommented) {
        this.isCommented = isCommented;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    public boolean isHasVoice() {
        return hasVoice;
    }

    public void setHasVoice(boolean hasVoice) {
        this.hasVoice = hasVoice;
    }

    public boolean isHasVideo() {
        return hasVideo;
    }

    public void setHasVideo(boolean hasVideo) {
        this.hasVideo = hasVideo;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public String getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(String homeworkId) {
        this.homeworkId = homeworkId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public List<CompleteImagesBean> getCompleteImages() {
        return completeImages;
    }

    public void setCompleteImages(List<CompleteImagesBean> completeImages) {
        this.completeImages = completeImages;
    }

    public static class CompleteVoiceBean implements Serializable{
        /**
         * id : 6c24806e-98c6-4e79-abed-46c4b329d4f0
         * filePath : http://files.hitongx.com/UploadFiles/HomeWorkFile/20171102/575a0b18-d30b-4f39-adec-dd0db6fc26fe.aac
         * mediaType : 0
         * fileLength : 0
         * createDate : 0001-01-01 00:00:00
         * batchNumber : 00000000-0000-0000-0000-000000000000
         */

        private String id;
        private String filePath;
        private int mediaType;
        private int fileLength;
        private String createDate;
        private String batchNumber;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public int getMediaType() {
            return mediaType;
        }

        public void setMediaType(int mediaType) {
            this.mediaType = mediaType;
        }

        public int getFileLength() {
            return fileLength;
        }

        public void setFileLength(int fileLength) {
            this.fileLength = fileLength;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getBatchNumber() {
            return batchNumber;
        }

        public void setBatchNumber(String batchNumber) {
            this.batchNumber = batchNumber;
        }
    }

    public static class CompleteImagesBean implements Serializable{
        /**
         * id : abd4122e-e82a-4954-b214-721fb3a5071b
         * associateObjectId : 6cd60bc6-9cbf-4476-a07e-498319f64f4e
         * associateObjectType : 2
         * filePath : http://img.hitongx.com/UploadFiles/HomeWorkFile/20171102/94f51085-d635-4e8a-9de9-d8d088d39d14.jpg
         * thumbnailFilePath : http://img.hitongx.com/UploadFiles/HomeWorkFile/20171102/94f51085-d635-4e8a-9de9-d8d088d39d14.jpg@310w_240h_1e_1c_2o.jpg
         * friendlyFileName : 1509609736164931.jpg
         * mediaType : 1
         * contentType : image/jpeg
         * fileLength : 208560
         * height : 1500
         * width : 2000
         * createDate : 2017-11-02 16:02:38
         * downloadNum : 0
         * ext :
         * batchNumber : 00000000-0000-0000-0000-000000000000
         */

        private String id;
        private String associateObjectId;
        private int associateObjectType;
        private String filePath;
        private String thumbnailFilePath;
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

        public String getThumbnailFilePath() {
            return thumbnailFilePath;
        }

        public void setThumbnailFilePath(String thumbnailFilePath) {
            this.thumbnailFilePath = thumbnailFilePath;
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


}
