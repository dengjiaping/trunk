package com.histudent.jwsoft.histudent.bean.homework;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huyg on 2017/10/27.
 */

public class WorkCompleteBean implements Serializable{
    /**
     * itemCount : 1
     * pageCount : 1
     * items : [{"id":"af15a0cc-e042-4014-a819-2a1166eed924","homeworkId":"b23fb938-1cbd-4ad4-a0a0-7f75f92b080d","userId":"3be6ae24-1395-4c29-8ef0-019feb5de052","userName":"李昂","userAvatar":"http://img.hitongx.com/UploadFiles/UploadAvatar/20171017/8afaeadd-bbf8-4dc1-929a-80d8bc7d3fe5.jpg@400w_400h_1e_1c_2o_200-1ci.jpg","completeId":"0ef97d5d-e51e-4f33-a61b-1c4e4046356d","completeTime":"2017-10-18 18:41:12","isComment":false,"commentTime":"2017-10-18 16:08:04"}]
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

    public static class ItemsBean implements Serializable{
        /**
         * id : af15a0cc-e042-4014-a819-2a1166eed924
         * homeworkId : b23fb938-1cbd-4ad4-a0a0-7f75f92b080d
         * userId : 3be6ae24-1395-4c29-8ef0-019feb5de052
         * userName : 李昂
         * userAvatar : http://img.hitongx.com/UploadFiles/UploadAvatar/20171017/8afaeadd-bbf8-4dc1-929a-80d8bc7d3fe5.jpg@400w_400h_1e_1c_2o_200-1ci.jpg
         * completeId : 0ef97d5d-e51e-4f33-a61b-1c4e4046356d
         * completeTime : 2017-10-18 18:41:12
         * isComment : false
         * commentTime : 2017-10-18 16:08:04
         */

        private String id;
        private String homeworkId;
        private String userId;
        private String userName;
        private String userAvatar;
        private String completeId;
        private String completeTime;
        private boolean isComment;
        private String commentTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserAvatar() {
            return userAvatar;
        }

        public void setUserAvatar(String userAvatar) {
            this.userAvatar = userAvatar;
        }

        public String getCompleteId() {
            return completeId;
        }

        public void setCompleteId(String completeId) {
            this.completeId = completeId;
        }

        public String getCompleteTime() {
            return completeTime;
        }

        public void setCompleteTime(String completeTime) {
            this.completeTime = completeTime;
        }

        public boolean isIsComment() {
            return isComment;
        }

        public void setIsComment(boolean isComment) {
            this.isComment = isComment;
        }

        public String getCommentTime() {
            return commentTime;
        }

        public void setCommentTime(String commentTime) {
            this.commentTime = commentTime;
        }

    }
}
