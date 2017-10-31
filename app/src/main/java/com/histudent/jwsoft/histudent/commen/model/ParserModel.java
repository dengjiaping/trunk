package com.histudent.jwsoft.histudent.commen.model;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/8/30.
 * 点赞详情列表model
 */
public class ParserModel {
    /**
     * itemCount : 2
     * pageCount : 1
     * items : [{"praiseTime":"2017-06-06 11:48:51","userId":"472a5ddd-ae50-40e7-b9f2-0fa103af340d","name":"刘桂禹","avatar":"http://img.hitongx.com/UploadFiles/Album/20170217/998a15f0-9fbd-4667-bbe1-b84c6ef8d7c5.jpg@60w_60h_1e_1c_2o_30-1ci.jpg"},{"praiseTime":"2017-02-17 10:30:58","userId":"bcafb6a6-acf9-46ac-87ef-8b54fef593af","name":"嗯啊","avatar":"http://img.hitongx.com/UploadFiles/Album/20170323/106195f0-7334-466d-8df3-2c517c107f3e@60w_60h_1e_1c_2o_30-1ci.src"}]
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
         * praiseTime : 2017-06-06 11:48:51
         * userId : 472a5ddd-ae50-40e7-b9f2-0fa103af340d
         * name : 刘桂禹
         * avatar : http://img.hitongx.com/UploadFiles/Album/20170217/998a15f0-9fbd-4667-bbe1-b84c6ef8d7c5.jpg@60w_60h_1e_1c_2o_30-1ci.jpg
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
