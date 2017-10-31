package com.histudent.jwsoft.histudent.body.mine.model;

/**
 * Created by liuguiyu-pc on 2016/12/16.
 * 邀请有礼bean
 */

public class InvitationResponseModel {


    /**
     * invitedRewardAmount : 50
     * beInvitedRewardAmount : 60
     * ruleUrl : http://www.hitongx.com/
     * invitedListUrl : http://www.hitongx.com/
     * invitedTitle : 邀请你身边的老师加入并成功激活班级，<br><font align=justify size=15 color='#ff00000'>你</font>将获得<font  align=justify size=15 color='#ff00000'>50</font>元，<font  align=justify size=15 color='#ff00000'>TA</font>也能获得<font  align=justify size=15 color='#ff00000'>60</font>元开通奖励
     * invitedContent : 已成功邀请50人,累计获得奖励60元
     * smsContent : [嗨同学]您的好友“刘桂禹”的班级正在使用嗨同学平台，班级网站的建设有它真方便，送你50元奖励，邀请你也来免费创建自己的班级体验。点击链接领取http://www.hitongx.com/?invitationcode=87b471ed-fa93-4edf-ae9d-b8205f2e92b6
     * shareTitle : 邀请有礼
     * shareText : 邀请有礼活动正式开启啦
     * shareUrl : http://www.hitongx.com/?invitationcode=87b471ed-fa93-4edf-ae9d-b8205f2e92b6
     * sharePic : http://img.hitongx.com/pic/1.jpg
     */

    private int invitedRewardAmount;
    private int beInvitedRewardAmount;
    private String ruleUrl;
    private String invitedListUrl;
    private String invitedTitle;
    private String invitedContent;
    private String smsContent;
    private String shareTitle;
    private String shareText;
    private String shareUrl;
    private String sharePic;

    public int getInvitedRewardAmount() {
        return invitedRewardAmount;
    }

    public void setInvitedRewardAmount(int invitedRewardAmount) {
        this.invitedRewardAmount = invitedRewardAmount;
    }

    public int getBeInvitedRewardAmount() {
        return beInvitedRewardAmount;
    }

    public void setBeInvitedRewardAmount(int beInvitedRewardAmount) {
        this.beInvitedRewardAmount = beInvitedRewardAmount;
    }

    public String getRuleUrl() {
        return ruleUrl;
    }

    public void setRuleUrl(String ruleUrl) {
        this.ruleUrl = ruleUrl;
    }

    public String getInvitedListUrl() {
        return invitedListUrl;
    }

    public void setInvitedListUrl(String invitedListUrl) {
        this.invitedListUrl = invitedListUrl;
    }

    public String getInvitedTitle() {
        return invitedTitle;
    }

    public void setInvitedTitle(String invitedTitle) {
        this.invitedTitle = invitedTitle;
    }

    public String getInvitedContent() {
        return invitedContent;
    }

    public void setInvitedContent(String invitedContent) {
        this.invitedContent = invitedContent;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareText() {
        return shareText;
    }

    public void setShareText(String shareText) {
        this.shareText = shareText;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getSharePic() {
        return sharePic;
    }

    public void setSharePic(String sharePic) {
        this.sharePic = sharePic;
    }
}
