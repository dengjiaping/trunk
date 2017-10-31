package com.histudent.jwsoft.histudent.body.mine.model;

/**
 * 用户设置的隐私信息
 * Created by ZhangYT on 2016/9/9.
 */
public class UserQualitySettingInforBean {


    /**
     * id : 2468d967-a754-4535-9ed2-1a2e4909dd75
     * privacyType : 1
     * privacyQuestion : 1+2
     * privacyAnswer : "3"
     * isBlockAccessUser : 0
     */

    private DataBean data;
    /**
     * data : {"id":"2468d967-a754-4535-9ed2-1a2e4909dd75","privacyType":1,"privacyQuestion":"1+2","privacyAnswer":"\"3\"","isBlockAccessUser":0}
     * ret : 1
     * msg :
     */


    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private int privacyType;
        private String privacyQuestion;
        private String privacyAnswer;
        private int isBlockAccessUser;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getPrivacyType() {
            return privacyType;
        }

        public void setPrivacyType(int privacyType) {
            this.privacyType = privacyType;
        }

        public String getPrivacyQuestion() {
            return privacyQuestion;
        }

        public void setPrivacyQuestion(String privacyQuestion) {
            this.privacyQuestion = privacyQuestion;
        }

        public String getPrivacyAnswer() {
            return privacyAnswer;
        }

        public void setPrivacyAnswer(String privacyAnswer) {
            this.privacyAnswer = privacyAnswer;
        }

        public int getIsBlockAccessUser() {
            return isBlockAccessUser;
        }

        public void setIsBlockAccessUser(int isBlockAccessUser) {
            this.isBlockAccessUser = isBlockAccessUser;
        }
    }
}
