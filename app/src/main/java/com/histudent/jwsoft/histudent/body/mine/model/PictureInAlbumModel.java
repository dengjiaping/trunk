package com.histudent.jwsoft.histudent.body.mine.model;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/8/19.
 */
public class PictureInAlbumModel {


    /**
     * itemCount : 3
     * items : [{"photoCount":1,"photoList":[{"commentNum":0,"praiseNum":0,"createTime":"2017-04-28 15:21:57","updateTime":"2017-04-28 15:21:57","photoId":"d2f791bc-5189-4807-9678-54b31693ea1a","filePath":"http://img.hitongx.com/UploadFiles/twitter/20170428/c81775a0-d1b2-46e2-b868-eb37a866b627.jpg?wh=640_863","photoName":"8949cc81-544b-43b8-9728-160f8c618f01_compress.jpg","activityItemKey":"UploadPhoto","photoUrl":"http://img.hitongx.com/UploadFiles/twitter/20170428/c81775a0-d1b2-46e2-b868-eb37a866b627.jpg@310w_240h_1e_1c_2o.jpg"},{"commentNum":0,"praiseNum":0,"createTime":"2017-04-28 11:51:28","updateTime":"2017-04-28 11:51:28","photoId":"a1948411-d1b3-4774-84cf-54314013c9ce","filePath":"http://img.hitongx.com/UploadFiles/twitter/20170428/40f355bc-68be-4cd2-8f4e-d508884b5dcc.png?wh=512_512","photoName":"0b707a92-5fd8-4075-b69f-36fd5f5f369e_compress.png","activityItemKey":"UploadPhoto","photoUrl":"http://img.hitongx.com/UploadFiles/twitter/20170428/40f355bc-68be-4cd2-8f4e-d508884b5dcc.png@310w_240h_1e_1c_2o.png"},{"commentNum":0,"praiseNum":0,"createTime":"2017-04-28 11:51:26","updateTime":"2017-04-28 11:51:26","photoId":"a1e42a9c-4a8b-47c5-84c7-0c8340be977a","filePath":"http://img.hitongx.com/UploadFiles/twitter/20170428/9165e95e-e5c0-4505-bd9e-4fe943eac695.jpg?wh=640_863","photoName":"8949cc81-544b-43b8-9728-160f8c618f01_compress.jpg","activityItemKey":"UploadPhoto","photoUrl":"http://img.hitongx.com/UploadFiles/twitter/20170428/9165e95e-e5c0-4505-bd9e-4fe943eac695.jpg@310w_240h_1e_1c_2o.jpg"}],"photoDate":"2017-04-28"}]
     * pageCount : 1
     */

    private int itemCount;
    private int pageCount;
    private List<ItemsBean> items;

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * photoCount : 1
         * photoList : [{"commentNum":0,"praiseNum":0,"createTime":"2017-04-28 15:21:57","updateTime":"2017-04-28 15:21:57","photoId":"d2f791bc-5189-4807-9678-54b31693ea1a","filePath":"http://img.hitongx.com/UploadFiles/twitter/20170428/c81775a0-d1b2-46e2-b868-eb37a866b627.jpg?wh=640_863","photoName":"8949cc81-544b-43b8-9728-160f8c618f01_compress.jpg","activityItemKey":"UploadPhoto","photoUrl":"http://img.hitongx.com/UploadFiles/twitter/20170428/c81775a0-d1b2-46e2-b868-eb37a866b627.jpg@310w_240h_1e_1c_2o.jpg"},{"commentNum":0,"praiseNum":0,"createTime":"2017-04-28 11:51:28","updateTime":"2017-04-28 11:51:28","photoId":"a1948411-d1b3-4774-84cf-54314013c9ce","filePath":"http://img.hitongx.com/UploadFiles/twitter/20170428/40f355bc-68be-4cd2-8f4e-d508884b5dcc.png?wh=512_512","photoName":"0b707a92-5fd8-4075-b69f-36fd5f5f369e_compress.png","activityItemKey":"UploadPhoto","photoUrl":"http://img.hitongx.com/UploadFiles/twitter/20170428/40f355bc-68be-4cd2-8f4e-d508884b5dcc.png@310w_240h_1e_1c_2o.png"},{"commentNum":0,"praiseNum":0,"createTime":"2017-04-28 11:51:26","updateTime":"2017-04-28 11:51:26","photoId":"a1e42a9c-4a8b-47c5-84c7-0c8340be977a","filePath":"http://img.hitongx.com/UploadFiles/twitter/20170428/9165e95e-e5c0-4505-bd9e-4fe943eac695.jpg?wh=640_863","photoName":"8949cc81-544b-43b8-9728-160f8c618f01_compress.jpg","activityItemKey":"UploadPhoto","photoUrl":"http://img.hitongx.com/UploadFiles/twitter/20170428/9165e95e-e5c0-4505-bd9e-4fe943eac695.jpg@310w_240h_1e_1c_2o.jpg"}]
         * photoDate : 2017-04-28
         */

        private int photoCount;
        private String photoDate;
        private List<PhotoListBean> photoList;

        public int getPhotoCount() {
            return photoCount;
        }

        public void setPhotoCount(int photoCount) {
            this.photoCount = photoCount;
        }

        public String getPhotoDate() {
            return photoDate;
        }

        public void setPhotoDate(String photoDate) {
            this.photoDate = photoDate;
        }

        public List<PhotoListBean> getPhotoList() {
            return photoList;
        }

        public void setPhotoList(List<PhotoListBean> photoList) {
            this.photoList = photoList;
        }

        public static class PhotoListBean {


            /**
             * commentNum : 0
             * praiseNum : 0
             * createTime : 2017-04-28 15:21:57
             * updateTime : 2017-04-28 15:21:57
             * photoId : d2f791bc-5189-4807-9678-54b31693ea1a
             * filePath : http://img.hitongx.com/UploadFiles/twitter/20170428/c81775a0-d1b2-46e2-b868-eb37a866b627.jpg?wh=640_863
             * photoName : 8949cc81-544b-43b8-9728-160f8c618f01_compress.jpg
             * activityItemKey : UploadPhoto
             * photoUrl : http://img.hitongx.com/UploadFiles/twitter/20170428/c81775a0-d1b2-46e2-b868-eb37a866b627.jpg@310w_240h_1e_1c_2o.jpg
             */

            private int commentNum;
            private int praiseNum;
            private String createTime;
            private String updateTime;
            private String photoId;
            private String filePath;
            private String photoName;
            private String activityItemKey;
            private String photoUrl;
            private int parentPosition;
            private boolean isSecleted;

            public int getParentPosition() {
                return parentPosition;
            }

            public void setParentPosition(int parentPosition) {
                this.parentPosition = parentPosition;
            }

            public boolean isSecleted() {
                return isSecleted;
            }

            public void setSecleted(boolean secleted) {
                isSecleted = secleted;
            }

            public int getCommentNum() {
                return commentNum;
            }

            public void setCommentNum(int commentNum) {
                this.commentNum = commentNum;
            }

            public int getPraiseNum() {
                return praiseNum;
            }

            public void setPraiseNum(int praiseNum) {
                this.praiseNum = praiseNum;
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

            public String getPhotoId() {
                return photoId;
            }

            public void setPhotoId(String photoId) {
                this.photoId = photoId;
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

            public String getActivityItemKey() {
                return activityItemKey;
            }

            public void setActivityItemKey(String activityItemKey) {
                this.activityItemKey = activityItemKey;
            }

            public String getPhotoUrl() {
                return photoUrl;
            }

            public void setPhotoUrl(String photoUrl) {
                this.photoUrl = photoUrl;
            }
        }
    }
}
