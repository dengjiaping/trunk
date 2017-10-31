package com.histudent.jwsoft.histudent.body.hiworld.bean;

import java.util.List;

/**
 *
 * 相册
 * Created by ZhangYT on 2016/8/19.
 */
public class FileListBean {


    /**
     * itemCount : 46
     * pageCount : 6
     * items : [{"photoCount":23,"createTime":"2016-07-22 10:14:32","isDefault":false,"isSystem":true,"converPhotoUrl":"http://img.hitongx.com/UploadFiles/Album/20160817/c180b7ec-7654-4bfe-a8fa-3c8d0f5d4f4e.jpg@_310w_240h_1e_1c.jpg","albumId":"983dc70d-7029-4390-ac41-d527efc8d034","albumName":"日志相册","albumDescription":"系统默认创建","privacyStatus":2,"userids":[],"ownerCategoryId":"b1f22493-90e8-4388-885d-c3ac59b431fa","ownerId":"2468d967-a754-4535-9ed2-1a2e4909dd75","classId":"2468d967-a754-4535-9ed2-1a2e4909dd75","groupId":"2468d967-a754-4535-9ed2-1a2e4909dd75"},{"photoCount":30,"createTime":"2016-05-20 10:34:02","isDefault":false,"isSystem":false,"converPhotoUrl":"http://img.hitongx.com/UploadFiles/Album/20160624/82f0552b-a78b-4aa2-9783-bc45c5ee15fb.jpg@_310w_240h_1e_1c.jpg","albumId":"ab83097e-12d1-4815-98e3-0555eb621b34","albumName":"234234242","albumDescription":"44444444444444444444444444444","privacyStatus":3,"userids":[],"ownerCategoryId":"113425cc-311a-4189-b827-b10cebb3d254","ownerId":"2468d967-a754-4535-9ed2-1a2e4909dd75","classId":"2468d967-a754-4535-9ed2-1a2e4909dd75","groupId":"2468d967-a754-4535-9ed2-1a2e4909dd75"},{"photoCount":3,"createTime":"2016-07-21 19:53:09","isDefault":false,"isSystem":false,"converPhotoUrl":"http://img.hitongx.com/UploadFiles/Album/20160802/7b2a8646-d397-4d9a-b190-abdd23b5288f.jpg@_310w_240h_1e_1c.jpg","albumId":"9c91b33f-bb7f-476c-a754-51c85b9aede3","albumName":"新的哦","albumDescription":"","privacyStatus":0,"userids":[],"ownerCategoryId":"113425cc-311a-4189-b827-b10cebb3d254","ownerId":"2468d967-a754-4535-9ed2-1a2e4909dd75","classId":"2468d967-a754-4535-9ed2-1a2e4909dd75","groupId":"2468d967-a754-4535-9ed2-1a2e4909dd75"},{"photoCount":15,"createTime":"2016-05-20 10:43:55","isDefault":false,"isSystem":false,"converPhotoUrl":"http://img.hitongx.com/UploadFiles/Album/20160602/f274bd52-4462-4301-8773-09dd699b0909.jpg@_310w_240h_1e_1c.jpg","albumId":"2b992341-4fde-44c1-9be1-ed99a7c0b590","albumName":"我的新相册","albumDescription":"","privacyStatus":0,"userids":[],"ownerCategoryId":"b1f22493-90e8-4388-885d-c3ac59b431fa","ownerId":"2468d967-a754-4535-9ed2-1a2e4909dd75","classId":"2468d967-a754-4535-9ed2-1a2e4909dd75","groupId":"2468d967-a754-4535-9ed2-1a2e4909dd75"},{"photoCount":4,"createTime":"2016-07-11 15:15:34","isDefault":false,"isSystem":false,"converPhotoUrl":"http://img.hitongx.com/UploadFiles/Album/20160730/c366e095-709b-4f20-bd3d-39678d378afe.jpg@_310w_240h_1e_1c.jpg","albumId":"faf805f7-1052-4fb7-a50d-092a6478e2a9","albumName":"1111","albumDescription":"","privacyStatus":0,"userids":[],"ownerCategoryId":"113425cc-311a-4189-b827-b10cebb3d254","ownerId":"2468d967-a754-4535-9ed2-1a2e4909dd75","classId":"2468d967-a754-4535-9ed2-1a2e4909dd75","groupId":"2468d967-a754-4535-9ed2-1a2e4909dd75"},{"photoCount":2,"createTime":"2016-07-08 16:59:15","isDefault":false,"isSystem":false,"converPhotoUrl":"http://img.hitongx.com/UploadFiles/Album/20160728/389e7b94-e7e9-4610-818c-66fccadb31a7.jpg@_310w_240h_1e_1c.jpg","albumId":"1b97db8b-6de1-4f50-94a4-af3c30d789a3","albumName":"田田田田田田田","albumDescription":"田田田田田田田","privacyStatus":3,"userids":[],"ownerCategoryId":"113425cc-311a-4189-b827-b10cebb3d254","ownerId":"2468d967-a754-4535-9ed2-1a2e4909dd75","classId":"2468d967-a754-4535-9ed2-1a2e4909dd75","groupId":"2468d967-a754-4535-9ed2-1a2e4909dd75"},{"photoCount":3,"createTime":"2016-07-08 21:01:10","isDefault":false,"isSystem":false,"converPhotoUrl":"http://img.hitongx.com/UploadFiles/Album/20160708/025860b5-f658-4055-b5b6-c7c7e5b845e0.jpg@_310w_240h_1e_1c.jpg","albumId":"c1892820-8f74-48b8-bd52-f4ab4e7185a0","albumName":"旅行的意义","privacyStatus":0,"userids":[],"ownerCategoryId":"113425cc-311a-4189-b827-b10cebb3d254","ownerId":"2468d967-a754-4535-9ed2-1a2e4909dd75","classId":"2468d967-a754-4535-9ed2-1a2e4909dd75","groupId":"2468d967-a754-4535-9ed2-1a2e4909dd75"}]
     */

        private int itemCount;
        private int pageCount;
        /**
         * photoCount : 23
         * createTime : 2016-07-22 10:14:32
         * isDefault : false
         * isSystem : true
         * converPhotoUrl : http://img.hitongx.com/UploadFiles/Album/20160817/c180b7ec-7654-4bfe-a8fa-3c8d0f5d4f4e.jpg@_310w_240h_1e_1c.jpg
         * albumId : 983dc70d-7029-4390-ac41-d527efc8d034
         * albumName : 日志相册
         * albumDescription : 系统默认创建
         * privacyStatus : 2
         * userids : []
         * ownerCategoryId : b1f22493-90e8-4388-885d-c3ac59b431fa
         * ownerId : 2468d967-a754-4535-9ed2-1a2e4909dd75
         * classId : 2468d967-a754-4535-9ed2-1a2e4909dd75
         * groupId : 2468d967-a754-4535-9ed2-1a2e4909dd75
         */

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
            private int photoCount;
            private String createTime;
            private boolean isDefault;
            private boolean isSystem;
            private String converPhotoUrl;
            private String albumId;
            private String albumName;
            private String albumDescription;
            private int privacyStatus;
            private String ownerCategoryId;
            private String ownerId;
            private String classId;
            private String groupId;
            private List<?> userids;

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

            public boolean isIsDefault() {
                return isDefault;
            }

            public void setIsDefault(boolean isDefault) {
                this.isDefault = isDefault;
            }

            public boolean isIsSystem() {
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

            public String getOwnerCategoryId() {
                return ownerCategoryId;
            }

            public void setOwnerCategoryId(String ownerCategoryId) {
                this.ownerCategoryId = ownerCategoryId;
            }

            public String getOwnerId() {
                return ownerId;
            }

            public void setOwnerId(String ownerId) {
                this.ownerId = ownerId;
            }

            public String getClassId() {
                return classId;
            }

            public void setClassId(String classId) {
                this.classId = classId;
            }

            public String getGroupId() {
                return groupId;
            }

            public void setGroupId(String groupId) {
                this.groupId = groupId;
            }

            public List<?> getUserids() {
                return userids;
            }

            public void setUserids(List<?> userids) {
                this.userids = userids;
            }
        }

}
