package com.histudent.jwsoft.histudent.bean.homework;

import java.util.List;

/**
 * Created by lichaojie on 2017/11/1.
 * desc:
 */

public class SelectReceiverPersonEntity {

    private List<ReceiverClass> teams;

    public List<ReceiverClass> getTeams() {
        return teams;
    }

    public void setTeams(List<ReceiverClass> teams) {
        this.teams = teams;
    }

    public class ReceiverClass {
        private List<ReceiverClassGroupPerson> teamList;
        private String classCoverImg;
        private String classFullName;
        private String classId;
        private String teamId;
        private String className;

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public List<ReceiverClassGroupPerson> getTeamList() {
            return teamList;
        }

        public void setTeamList(List<ReceiverClassGroupPerson> teamList) {
            this.teamList = teamList;
        }

        public String getClassCoverImg() {
            return classCoverImg;
        }

        public void setClassCoverImg(String classCoverImg) {
            this.classCoverImg = classCoverImg;
        }

        public String getClassFullName() {
            return classFullName;
        }

        public void setClassFullName(String classFullName) {
            this.classFullName = classFullName;
        }

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public String getTeamId() {
            return teamId;
        }

        public void setTeamId(String teamId) {
            this.teamId = teamId;
        }

        public class ReceiverClassGroupPerson {
            private String name;
            private int studentNum;
            private String teamId;


            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getStudentNum() {
                return studentNum;
            }

            public void setStudentNum(int studentNum) {
                this.studentNum = studentNum;
            }

            public String getTeamId() {
                return teamId;
            }

            public void setTeamId(String teamId) {
                this.teamId = teamId;
            }
        }
    }
}
