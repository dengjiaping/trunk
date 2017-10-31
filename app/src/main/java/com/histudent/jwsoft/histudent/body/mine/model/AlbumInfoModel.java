package com.histudent.jwsoft.histudent.body.mine.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/8/4.
 * 相册列表
 */
public class AlbumInfoModel implements Serializable{

    /**
     * praiseNum : 0
     * praiseUser : [{"userId":"24f8ecef-d045-4bd1-bcf4-8eb34ac59d7a","realname":"卢益","avatar":"http://img.hitongx.com/UploadFiles/Album/20170330/527b6e6d-f4ab-4b7d-8414-7beeadb95c03@60w_60h_1e_1c_2o_30-1ci.src"}]
     * photos : [{"photoDate":"2017-03-31","photoCount":1,"photoList":[{"photoId":"be3b1990-f379-47c4-8d23-9232a9cefbfd","photoUrl":"http://img.hitongx.com/UploadFiles/twitter/20170331/4cc67fa1-314f-4642-a467-6612e057ac78.jpg@310w_240h_1e_1c_2o.jpg","filePath":"http://img.hitongx.com/UploadFiles/twitter/20170331/4cc67fa1-314f-4642-a467-6612e057ac78.jpg?wh=1560_2104","photoName":"P70331-123604_compress.jpg","praiseNum":0,"commentNum":0,"createTime":"0001-01-01 00:00:00","updateTime":"0001-01-01 00:00:00"}]},{"photoDate":"2016-10-20","photoCount":1,"photoList":[{"photoId":"4cf97a20-2ec8-41ea-9b5e-0e902d4519cc","photoUrl":"http://img.hitongx.com/UploadFiles/Album/20161020/5a2fa10c-8335-405a-8c13-bde8a77362d5.jpeg@310w_240h_1e_1c_2o.jpeg","filePath":"http://img.hitongx.com/UploadFiles/Album/20161020/5a2fa10c-8335-405a-8c13-bde8a77362d5.jpeg?wh=1080_954","photoName":"bec09face656f51dcb843fc33a23c243.jpeg","praiseNum":0,"commentNum":0,"createTime":"0001-01-01 00:00:00","updateTime":"0001-01-01 00:00:00"}]},{"photoDate":"2016-09-14","photoCount":1,"photoList":[{"photoId":"6c83cc2e-910e-4744-9622-01ca77001ed5","photoUrl":"http://img.hitongx.com/UploadFiles/twitter/20160914/b4924c31-b867-4334-a45a-e2f29d594782.png@310w_240h_1e_1c_2o.png","filePath":"http://img.hitongx.com/UploadFiles/twitter/20160914/b4924c31-b867-4334-a45a-e2f29d594782.png?wh=480_800","photoName":"Screenshot_2016-07-09-09-52-33_compress.png","praiseNum":0,"commentNum":0,"createTime":"0001-01-01 00:00:00","updateTime":"0001-01-01 00:00:00"}]}]
     * photoCount : 8
     * createTime : 0001-01-01 00:00:00
     * isDefault : false
     * isSystem : false
     * converPhotoUrl : http://img.hitongx.com/images/NormalAlbumLogo.png@310w_240h_1e_1c_2o.png
     * albumId : 219fdd4d-202c-49b6-a78b-035974b997de
     * albumName : 1212211121
     * albumDescription :
     * privacyStatus : 0
     * ownerId : df3a0abd-415f-4e26-ac6c-00f8ea013a9d
     * ownerType : 0
     * ownerList : ["df3a0abd-415f-4e26-ac6c-00f8ea013a9d"]
     * isUploadAuth : true
     * isEditAuth : true
     * visitNum : 0
     */

    private int praiseNum;
    private int photoCount;
    private String createTime;
    private boolean isDefault;
    private boolean isSystem;
    private String converPhotoUrl;
    private String albumId;
    private String albumName;
    private String albumDescription;
    private int privacyStatus;
    private String ownerId;
    private int ownerType;
    private boolean isUploadAuth;
    private boolean isEditAuth;
    private int visitNum;
    private List<PraiseUserBean> praiseUser;
    private List<PhotosBean> photos;
    private List<String> ownerList;


    public int getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(int praiseNum) {
        this.praiseNum = praiseNum;
    }

    public int getPhotoCount() {
        return photoCount;
    }

    public void setPhotoCount(int photoCount) {
        this.photoCount = photoCount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public boolean getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(boolean isSystem) {
        this.isSystem = isSystem;
    }

    public String getConverPhotoUrl() {
        return converPhotoUrl;
    }

    public void setConverPhotoUrl(String converPhotoUrl) {
        this.converPhotoUrl = converPhotoUrl;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumDescription() {
        return albumDescription;
    }

    public void setAlbumDescription(String albumDescription) {
        this.albumDescription = albumDescription;
    }

    public int getPrivacyStatus() {
        return privacyStatus;
    }

    public void setPrivacyStatus(int privacyStatus) {
        this.privacyStatus = privacyStatus;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public int getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(int ownerType) {
        this.ownerType = ownerType;
    }

    public boolean isIsUploadAuth() {
        return isUploadAuth;
    }

    public void setIsUploadAuth(boolean isUploadAuth) {
        this.isUploadAuth = isUploadAuth;
    }

    public boolean isIsEditAuth() {
        return isEditAuth;
    }

    public void setIsEditAuth(boolean isEditAuth) {
        this.isEditAuth = isEditAuth;
    }

    public int getVisitNum() {
        return visitNum;
    }

    public void setVisitNum(int visitNum) {
        this.visitNum = visitNum;
    }

    public List<PraiseUserBean> getPraiseUser() {
        return praiseUser;
    }

    public void setPraiseUser(List<PraiseUserBean> praiseUser) {
        this.praiseUser = praiseUser;
    }

    public List<PhotosBean> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotosBean> photos) {
        this.photos = photos;
    }

    public List<String> getOwnerList() {
        return ownerList;
    }

    public void setOwnerList(List<String> ownerList) {
        this.ownerList = ownerList;
    }

    public static class PraiseUserBean {
        /**
         * userId : 24f8ecef-d045-4bd1-bcf4-8eb34ac59d7a
         * realname : 卢益
         * avatar : http://img.hitongx.com/UploadFiles/Album/20170330/527b6e6d-f4ab-4b7d-8414-7beeadb95c03@60w_60h_1e_1c_2o_30-1ci.src
         */

        private String userId;
        private String realname;
        private String avatar;
        private int userType;

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }

    public static class PhotosBean {
        /**
         * photoDate : 2017-03-31
         * photoCount : 1
         * photoList : [{"photoId":"be3b1990-f379-47c4-8d23-9232a9cefbfd","photoUrl":"http://img.hitongx.com/UploadFiles/twitter/20170331/4cc67fa1-314f-4642-a467-6612e057ac78.jpg@310w_240h_1e_1c_2o.jpg","filePath":"http://img.hitongx.com/UploadFiles/twitter/20170331/4cc67fa1-314f-4642-a467-6612e057ac78.jpg?wh=1560_2104","photoName":"P70331-123604_compress.jpg","praiseNum":0,"commentNum":0,"createTime":"0001-01-01 00:00:00","updateTime":"0001-01-01 00:00:00"}]
         */

        private String photoDate;
        private int photoCount;
        private List<PhotoListBean> photoList;

        public String getPhotoDate() {
            return photoDate;
        }

        public void setPhotoDate(String photoDate) {
            this.photoDate = photoDate;
        }

        public int getPhotoCount() {
            return photoCount;
        }

        public void setPhotoCount(int photoCount) {
            this.photoCount = photoCount;
        }

        public List<PhotoListBean> getPhotoList() {
            return photoList;
        }

        public void setPhotoList(List<PhotoListBean> photoList) {
            this.photoList = photoList;
        }

        public static class PhotoListBean {
            /**
             * photoId : be3b1990-f379-47c4-8d23-9232a9cefbfd
             * photoUrl : http://img.hitongx.com/UploadFiles/twitter/20170331/4cc67fa1-314f-4642-a467-6612e057ac78.jpg@310w_240h_1e_1c_2o.jpg
             * filePath : http://img.hitongx.com/UploadFiles/twitter/20170331/4cc67fa1-314f-4642-a467-6612e057ac78.jpg?wh=1560_2104
             * photoName : P70331-123604_compress.jpg
             * praiseNum : 0
             * commentNum : 0
             * createTime : 0001-01-01 00:00:00
             * updateTime : 0001-01-01 00:00:00
             */

            private String photoId;
            private String photoUrl;
            private String filePath;
            private String photoName;
            private int praiseNum;
            private int commentNum;
            private String createTime;
            private String updateTime;

            public String getPhotoId() {
                return photoId;
            }

            public void setPhotoId(String photoId) {
                this.photoId = photoId;
            }

            public String getPhotoUrl() {
                return photoUrl;
            }

            public void setPhotoUrl(String photoUrl) {
                this.photoUrl = photoUrl;
            }

            public String getFilePath() {
                return filePath;
            }

            public void setFilePath(String filePath) {
                this.filePath = filePath;
            }

            public String getPhotoName() {
                return photoName;
            }

            public void setPhotoName(String photoName) {
                this.photoName = photoName;
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

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }
        }
    }
}
