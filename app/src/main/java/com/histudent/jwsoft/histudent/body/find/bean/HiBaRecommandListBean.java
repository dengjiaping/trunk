package com.histudent.jwsoft.histudent.body.find.bean;

/**
 * hiba精选
 * Created by ZhangYT on 2016/8/11.
 */
public class HiBaRecommandListBean {


    /**
     * collectCount : 0
     * replyCount : 16
     * hitsCount : 54
     * createdTime : 2016-08-08 14:59:19
     * blockName : 校园内外
     * repliedTime : 2016-10-21 09:10:39
     * ownAvatar : http://img.hitongx.com/UploadFiles/UploadAvatar/UserSystemAvatar/member_74.png@_60w_60h_1e_1c_30-1ci.png
     * isAnonymous : false
     * isSticky : true
     * ownId : 0643c5ca-9268-421a-9ee1-12c721012804
     * isRecommand : true
     * htmlUrl : http://192.168.0.252:8080//ShareTopic/HiBa/9597c1b2-54f8-4cce-868d-41d70ecfe7f3
     * title : 测试测试测试测试测试测试测试测试测试测试测试测试测试测
     * isExquisite : true
     * blockId : 5f9b4707-ff82-483e-800e-ecc08198b4d2
     * summaryInfo : {"imgCount":0,"textInfo":"你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你......"}
     * topicId : 9597c1b2-54f8-4cce-868d-41d70ecfe7f3
     * ownName : 杜尔维
     */

    private String imgUrl;
    private int collectCount;
    private int replyCount;
    private int hitsCount;
    private String createdTime;
    private String blockName;
    private String repliedTime;
    private String ownAvatar;
    private boolean isAnonymous;
    private boolean isSticky;
    private String ownId;
    private boolean isRecommand;
    private String htmlUrl;
    private String title;
    private boolean isExquisite;
    private String blockId;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     * imgCount : 0
     * textInfo : 你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你......
     */

    private SummaryInfoBean summaryInfo;
    private String topicId;
    private String ownName;

    public int getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(int collectCount) {
        this.collectCount = collectCount;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public int getHitsCount() {
        return hitsCount;
    }

    public void setHitsCount(int hitsCount) {
        this.hitsCount = hitsCount;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getRepliedTime() {
        return repliedTime;
    }

    public void setRepliedTime(String repliedTime) {
        this.repliedTime = repliedTime;
    }

    public String getOwnAvatar() {
        return ownAvatar;
    }

    public void setOwnAvatar(String ownAvatar) {
        this.ownAvatar = ownAvatar;
    }

    public boolean isIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(boolean isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public boolean isIsSticky() {
        return isSticky;
    }

    public void setIsSticky(boolean isSticky) {
        this.isSticky = isSticky;
    }

    public String getOwnId() {
        return ownId;
    }

    public void setOwnId(String ownId) {
        this.ownId = ownId;
    }

    public boolean isIsRecommand() {
        return isRecommand;
    }

    public void setIsRecommand(boolean isRecommand) {
        this.isRecommand = isRecommand;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isIsExquisite() {
        return isExquisite;
    }

    public void setIsExquisite(boolean isExquisite) {
        this.isExquisite = isExquisite;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public SummaryInfoBean getSummaryInfo() {
        return summaryInfo;
    }

    public void setSummaryInfo(SummaryInfoBean summaryInfo) {
        this.summaryInfo = summaryInfo;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getOwnName() {
        return ownName;
    }

    public void setOwnName(String ownName) {
        this.ownName = ownName;
    }

    public static class SummaryInfoBean {
        private int imgCount;
        private String textInfo;

        public int getImgCount() {
            return imgCount;
        }

        public void setImgCount(int imgCount) {
            this.imgCount = imgCount;
        }

        public String getTextInfo() {
            return textInfo;
        }

        public void setTextInfo(String textInfo) {
            this.textInfo = textInfo;
        }
    }
}
