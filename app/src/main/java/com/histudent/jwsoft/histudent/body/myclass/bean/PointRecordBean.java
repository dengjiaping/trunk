package com.histudent.jwsoft.histudent.body.myclass.bean;

import java.util.List;

/**
 * Created by huyg on 2017/7/7.
 */
// {
//         "itemCount": 2,
//         "pageCount": 1,
//         "items": [
//         {
//         "month": 6,
//         "points": 0,
//         "consumePoints": 0,
//         "recordList": [
//         {
//         "pointItemName": "[日常任务]分享班级圈",
//         "experiencePoints": 5,
//         "requtationPoints": 5,
//         "createTime": "2017-06-08 17:09:05",
//         "points": 5
//         },
//         {
//         "pointItemName": "分享班级圈",
//         "experiencePoints": 0,
//         "requtationPoints": 5,
//         "createTime": "2017-06-08 17:09:05",
//         "points": 5
//         }
//         ]
//         }
//         ]
//         },
public class PointRecordBean {


    /**
     * itemCount : 2
     * pageCount : 1
     * items : [{"month":6,"points":0,"consumePoints":0,"recordList":[{"pointItemName":"[日常任务]分享班级圈","experiencePoints":5,"requtationPoints":5,"createTime":"2017-06-08 17:09:05","points":5},{"pointItemName":"分享班级圈","experiencePoints":0,"requtationPoints":5,"createTime":"2017-06-08 17:09:05","points":5}]}]
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
         * month : 6
         * points : 0
         * consumePoints : 0
         * recordList : [{"pointItemName":"[日常任务]分享班级圈","experiencePoints":5,"requtationPoints":5,"createTime":"2017-06-08 17:09:05","points":5},{"pointItemName":"分享班级圈","experiencePoints":0,"requtationPoints":5,"createTime":"2017-06-08 17:09:05","points":5}]
         */

        private String month;
        private int points;
        private int consumePoints;
        private List<RecordListBean> recordList;

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public int getPoints() {
            return points;
        }

        public void setPoints(int points) {
            this.points = points;
        }

        public int getConsumePoints() {
            return consumePoints;
        }

        public void setConsumePoints(int consumePoints) {
            this.consumePoints = consumePoints;
        }

        public List<RecordListBean> getRecordList() {
            return recordList;
        }

        public void setRecordList(List<RecordListBean> recordList) {
            this.recordList = recordList;
        }


    }
}
