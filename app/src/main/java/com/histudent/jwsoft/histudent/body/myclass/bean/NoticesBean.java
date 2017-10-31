package com.histudent.jwsoft.histudent.body.myclass.bean;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2017/5/3.
 */

public class NoticesBean {
    /**
     * itemCount : 0
     * pageCount : 0
     * items : [{"notifyId":"string","title":"string","content":"string","hasImage":true,"hasVoice":true,"voice":{"voiceId":"string","voiceUrl":"string","voiceDuration":0},"imageList":[{"imageUrl":"string"}],"createUserName":"string","createTime":"string","readNum":0,"unReadNum":0,"readUserList":[{"userId":"string","realName":"string","avatar":"string","readTime":"2017-05-08T03:37:51.535Z"}],"unReadUserList":[{"userId":"string","realName":"string","avatar":"string","readTime":"2017-05-08T03:37:51.535Z"}]}]
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
         * notifyId : string
         * title : string
         * content : string
         * hasImage : true
         * hasVoice : true
         * voice : {"voiceId":"string","voiceUrl":"string","voiceDuration":0}
         * imageList : [{"imageUrl":"string"}]
         * createUserName : string
         * createTime : string
         * readNum : 0
         * unReadNum : 0
         * isSend
         * noticeUserNum
         * readUserList : [{"userId":"string","realName":"string","avatar":"string","readTime":"2017-05-08T03:37:51.535Z"}]
         * unReadUserList : [{"userId":"string","realName":"string","avatar":"string","readTime":"2017-05-08T03:37:51.535Z"}]
         */

        private String notifyId;
        private String title;
        private String content;
        private boolean hasImage;
        private boolean hasVoice;
        private VoiceBean voice;
        private String createUserName;
        private String createTime;
        private int readNum;
        private int unReadNum;
        private List<ImageListBean> imageList;
        private List<ReadUserListBean> readUserList;
        private List<UnReadUserListBean> unReadUserList;
        private boolean isSend;
        private String noticeTime;
        private boolean isView;

        public boolean isView() {
            return isView;
        }

        public void setIsView(boolean view) {
            isView = view;
        }

        public void setSend(boolean send) {
            isSend = send;
        }

        public String getNoticeTime() {
            return noticeTime;
        }

        public void setNoticeTime(String noticeTime) {
            this.noticeTime = noticeTime;
        }

        public int getNoticeUserNum() {
            return noticeUserNum;
        }

        public void setNoticeUserNum(int noticeUserNum) {
            this.noticeUserNum = noticeUserNum;
        }

        private int noticeUserNum;

        public boolean isSend() {
            return isSend;
        }

        public void setIsSend(boolean send) {
            isSend = send;
        }

        public String getNotifyId() {
            return notifyId;
        }

        public void setNotifyId(String notifyId) {
            this.notifyId = notifyId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public boolean isHasImage() {
            return hasImage;
        }

        public void setHasImage(boolean hasImage) {
            this.hasImage = hasImage;
        }

        public boolean isHasVoice() {
            return hasVoice;
        }

        public void setHasVoice(boolean hasVoice) {
            this.hasVoice = hasVoice;
        }

        public VoiceBean getVoice() {
            return voice;
        }

        public void setVoice(VoiceBean voice) {
            this.voice = voice;
        }

        public String getCreateUserName() {
            return createUserName;
        }

        public void setCreateUserName(String createUserName) {
            this.createUserName = createUserName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getReadNum() {
            return readNum;
        }

        public void setReadNum(int readNum) {
            this.readNum = readNum;
        }

        public int getUnReadNum() {
            return unReadNum;
        }

        public void setUnReadNum(int unReadNum) {
            this.unReadNum = unReadNum;
        }

        public List<ImageListBean> getImageList() {
            return imageList;
        }

        public void setImageList(List<ImageListBean> imageList) {
            this.imageList = imageList;
        }

        public List<ReadUserListBean> getReadUserList() {
            return readUserList;
        }

        public void setReadUserList(List<ReadUserListBean> readUserList) {
            this.readUserList = readUserList;
        }

        public List<UnReadUserListBean> getUnReadUserList() {
            return unReadUserList;
        }

        public void setUnReadUserList(List<UnReadUserListBean> unReadUserList) {
            this.unReadUserList = unReadUserList;
        }

        public static class VoiceBean {
            /**
             * voiceId : string
             * voiceUrl : string
             * voiceDuration : 0
             */

            private String voiceId;
            private String voiceUrl;
            private int voiceDuration;

            public String getVoiceId() {
                return voiceId;
            }

            public void setVoiceId(String voiceId) {
                this.voiceId = voiceId;
            }

            public String getVoiceUrl() {
                return voiceUrl;
            }

            public void setVoiceUrl(String voiceUrl) {
                this.voiceUrl = voiceUrl;
            }

            public int getVoiceDuration() {
                return voiceDuration;
            }

            public void setVoiceDuration(int voiceDuration) {
                this.voiceDuration = voiceDuration;
            }
        }

        public static class ImageListBean {
            /**
             * imageUrl : string
             */

            private String imageUrl;

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }
        }

        public static class ReadUserListBean {
            /**
             * userId : string
             * realName : string
             * avatar : string
             * readTime : 2017-05-08T03:37:51.535Z
             */

            private String userId;
            private String realName;
            private String avatar;
            private String readTime;

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getReadTime() {
                return readTime;
            }

            public void setReadTime(String readTime) {
                this.readTime = readTime;
            }
        }

        public static class UnReadUserListBean {
            /**
             * userId : string
             * realName : string
             * avatar : string
             * readTime : 2017-05-08T03:37:51.535Z
             */

            private String userId;
            private String realName;
            private String avatar;
            private String readTime;

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getReadTime() {
                return readTime;
            }

            public void setReadTime(String readTime) {
                this.readTime = readTime;
            }
        }
    }
}
