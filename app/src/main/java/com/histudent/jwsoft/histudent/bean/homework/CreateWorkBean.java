package com.histudent.jwsoft.histudent.bean.homework;

import java.util.List;

/**
 * Created by huyg on 2017/11/3.
 */

public class CreateWorkBean {
    /**
     * status : 1
     * classHomeWorkList : [{"classId":"6160560d-4939-4a05-a987-3a5eced05cff","homeWorkId":"0fa433ea-b19b-46f1-9d6c-c5226ce8c844"}]
     */

    private int status;
    private List<ClassHomeWorkListBean> classHomeWorkList;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ClassHomeWorkListBean> getClassHomeWorkList() {
        return classHomeWorkList;
    }

    public void setClassHomeWorkList(List<ClassHomeWorkListBean> classHomeWorkList) {
        this.classHomeWorkList = classHomeWorkList;
    }

    public static class ClassHomeWorkListBean {
        /**
         * classId : 6160560d-4939-4a05-a987-3a5eced05cff
         * homeWorkId : 0fa433ea-b19b-46f1-9d6c-c5226ce8c844
         */

        private String classId;
        private String homeWorkId;

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public String getHomeWorkId() {
            return homeWorkId;
        }

        public void setHomeWorkId(String homeWorkId) {
            this.homeWorkId = homeWorkId;
        }
    }
}
