package com.histudent.jwsoft.histudent.comment2.bean;

import java.util.List;

/**
 * 学校实体类
 * Created by ZhangYT on 2016/12/8.
 */

public class SchoolBean {

        private int itemCount;
        private int pageCount;
        /**
         * name : 杭州市江城中学
         * value : 3133000153
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
            private String name;
            private String value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
}
