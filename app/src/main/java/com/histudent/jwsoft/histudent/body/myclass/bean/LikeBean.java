package com.histudent.jwsoft.histudent.body.myclass.bean;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2017/5/3.
 */

public class LikeBean {

    /**
     * itemCount : 1
     * pageCount : 1
     * items : [{"praiseTime":"2017-05-02 17:42:50","userId":"87b471ed-fa93-4edf-ae9d-b8205f2e92b6","name":"刘桂禹","avatar":"http://img.hitongx.com/UploadFiles/Album/20170330/eb354dae-74bb-4933-969e-bd1f4a83e8fe.jpg@60w_60h_1e_1c_2o_30-1ci.jpg"}]
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
         * praiseTime : 2017-05-02 17:42:50
         * userId : 87b471ed-fa93-4edf-ae9d-b8205f2e92b6
         * name : 刘桂禹
         * avatar : http://img.hitongx.com/UploadFiles/Album/20170330/eb354dae-74bb-4933-969e-bd1f4a83e8fe.jpg@60w_60h_1e_1c_2o_30-1ci.jpg
         */

        private String praiseTime;
        private String userId;
        private String name;
        private String avatar;

        public String getPraiseTime() {
            return praiseTime;
        }

        public void setPraiseTime(String praiseTime) {
            this.praiseTime = praiseTime;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}
