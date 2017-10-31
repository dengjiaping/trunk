package com.histudent.jwsoft.histudent.body.find.bean;

/**
 *
 * 分类的活动
 * Created by ZhangYT on 2016/8/13.
 */
public class SortGroupListBean {


        private String tagId;
        private String name;
        private int groupNum;
        private String categoryImg;

        public String getTagId() {
            return tagId;
        }

        public void setTagId(String tagId) {
            this.tagId = tagId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getGroupNum() {
            return groupNum;
        }

        public void setGroupNum(int groupNum) {
            this.groupNum = groupNum;
        }

        public String getCategoryImg() {
            return categoryImg;
        }

        public void setCategoryImg(String categoryImg) {
            this.categoryImg = categoryImg;
        }

}
