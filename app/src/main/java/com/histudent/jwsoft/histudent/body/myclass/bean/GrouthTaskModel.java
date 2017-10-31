package com.histudent.jwsoft.histudent.body.myclass.bean;

import java.util.List;

/**
 * Created by ZhangYT on 2017/5/5.
 */

public class GrouthTaskModel {

    /**
     * taskList : [{"content":"上传班级照片","img":"","taskId":"UploadClassPhoto","isComplete":true,"experiencePoints":2,"title":"上传班级照片","points":3}]
     * canGetPoint : 3
     * alreadyGetPoint : 3
     */

    private int canGetPoint;
    private int alreadyGetPoint;
    private List<TaskListBean> items;

    public int getCanGetPoint() {
        return canGetPoint;
    }

    public void setCanGetPoint(int canGetPoint) {
        this.canGetPoint = canGetPoint;
    }

    public int getAlreadyGetPoint() {
        return alreadyGetPoint;
    }

    public void setAlreadyGetPoint(int alreadyGetPoint) {
        this.alreadyGetPoint = alreadyGetPoint;
    }

    public List<TaskListBean> getItems() {
        return items;
    }

    public void setItems(List<TaskListBean> items) {
        this.items = items;
    }

    public class TaskListBean {
        /**
         * content : 上传班级照片
         * img :
         * taskId : UploadClassPhoto
         * isComplete : true
         * experiencePoints : 2
         * title : 上传班级照片
         * points : 3
         */

        private String content;
        private String img;
        private String taskId;
        private boolean isComplete;
        private int experiencePoints;//经验
        private String title;
        private int points; //积分
        private boolean isH5;
        private String url;
        private long requiredTimes;
        private long completedTimes;
        private boolean isCanTodo;

        public boolean isComplete() {
            return isComplete;
        }

        public boolean isCanTodo() {
            return isCanTodo;
        }

        public void setIsCanTodo(boolean isCanTodo) {
            this.isCanTodo = isCanTodo;
        }

        public long getRequiredTimes() {
            return requiredTimes;
        }

        public void setRequiredTimes(long requiredTimes) {
            this.requiredTimes = requiredTimes;
        }

        public long getCompletedTimes() {
            return completedTimes;
        }

        public void setCompletedTimes(long completedTimes) {
            this.completedTimes = completedTimes;
        }

        public boolean getIsComplete() {
            return isComplete;
        }

        public void setComplete(boolean complete) {
            isComplete = complete;
        }

        public boolean isH5() {
            return isH5;
        }

        public void setH5(boolean h5) {
            isH5 = h5;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public boolean isIsComplete() {
            return isComplete;
        }

        public void setIsComplete(boolean isComplete) {
            this.isComplete = isComplete;
        }

        public int getExperiencePoints() {
            return experiencePoints;
        }

        public void setExperiencePoints(int experiencePoints) {
            this.experiencePoints = experiencePoints;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getPoints() {
            return points;
        }

        public void setPoints(int points) {
            this.points = points;
        }
    }
}
