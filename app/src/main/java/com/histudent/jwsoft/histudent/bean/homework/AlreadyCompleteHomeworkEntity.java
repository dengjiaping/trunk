package com.histudent.jwsoft.histudent.bean.homework;

import java.util.List;

/**
 * Created by lichaojie on 2017/11/1.
 * desc:
 */

public class AlreadyCompleteHomeworkEntity {

    private int itemCount;
    private List<ItemsEntity> items;

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public List<ItemsEntity> getItems() {
        return items;
    }

    public void setItems(List<ItemsEntity> items) {
        this.items = items;
    }

    public class ItemsEntity {
        private String timeStr;
        private String weekStr;
        private List<SubItemEntity> workList;

        public String getTimeStr() {
            return timeStr;
        }

        public void setTimeStr(String timeStr) {
            this.timeStr = timeStr;
        }

        public String getWeekStr() {
            return weekStr;
        }

        public void setWeekStr(String weekStr) {
            this.weekStr = weekStr;
        }

        public List<SubItemEntity> getWorkList() {
            return workList;
        }

        public void setWorkList(List<SubItemEntity> workList) {
            this.workList = workList;
        }

        public class SubItemEntity {
            private String classId;
            private String className;
            private String contents;
            private String createTime;
            private String gradeName;
            private String id;
            private boolean isComplete;
            private String logo;
            private String ownerId;
            private String ownerName;
            private String subjectId;
            private String subjectName;
            private String teamName;
            private boolean onlyOnline;

            public boolean isOnlyOnline() {
                return onlyOnline;
            }

            public void setOnlyOnline(boolean onlyOnline) {
                this.onlyOnline = onlyOnline;
            }

            public String getTeamName() {
                return teamName;
            }

            public void setTeamName(String teamName) {
                this.teamName = teamName;
            }

            public String getClassId() {
                return classId;
            }

            public void setClassId(String classId) {
                this.classId = classId;
            }

            public String getClassName() {
                return className;
            }

            public void setClassName(String className) {
                this.className = className;
            }

            public String getContents() {
                return contents;
            }

            public void setContents(String contents) {
                this.contents = contents;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getGradeName() {
                return gradeName;
            }

            public void setGradeName(String gradeName) {
                this.gradeName = gradeName;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public boolean isComplete() {
                return isComplete;
            }

            public void setComplete(boolean complete) {
                isComplete = complete;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getOwnerId() {
                return ownerId;
            }

            public void setOwnerId(String ownerId) {
                this.ownerId = ownerId;
            }

            public String getOwnerName() {
                return ownerName;
            }

            public void setOwnerName(String ownerName) {
                this.ownerName = ownerName;
            }

            public String getSubjectId() {
                return subjectId;
            }

            public void setSubjectId(String subjectId) {
                this.subjectId = subjectId;
            }

            public String getSubjectName() {
                return subjectName;
            }

            public void setSubjectName(String subjectName) {
                this.subjectName = subjectName;
            }
        }

    }
}
