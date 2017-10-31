package com.histudent.jwsoft.histudent.body.find.bean;

/**hiba专栏
 * Created by ZhangYT on 2016/8/10.
 */
public class HiBaBlockListBean {


    /**
     * imgUrl : http://img.hitongx.com/UploadFiles/HiBaLogo/hibar_03.png@_310w_240h_1e_1c.png
     * enabled : true
     * createdUserId : 00000000-0000-0000-0000-000000000000
     * replyCount : 99
     * createdTime : 2016-07-26 08:58:30
     * blockId : 5f9b4707-ff82-483e-800e-ecc08198b4d2
     * sortOrder : 4
     * topicCount : 26
     * name : 校园内外
     * intro : 最精彩的教育资讯，最值得先知的校园趣闻，你我都是校园的记者， 让我们彼此分享自己身边那些值得喜闻乐道的事。
     */

    private String imgUrl;
    private boolean enabled;
    private String createdUserId;
    private int replyCount;
    private String createdTime;
    private String blockId;
    private int sortOrder;
    private int topicCount;
    private String name;
    private String intro;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(String createdUserId) {
        this.createdUserId = createdUserId;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public int getTopicCount() {
        return topicCount;
    }

    public void setTopicCount(int topicCount) {
        this.topicCount = topicCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
