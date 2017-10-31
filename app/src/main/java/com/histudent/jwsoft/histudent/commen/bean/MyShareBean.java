package com.histudent.jwsoft.histudent.commen.bean;

import java.io.Serializable;

/**
 * Created by liuguiyu-pc on 2016/9/10.
 */
public class MyShareBean implements Serializable {

    /**
     * shareTitle :
     * shareText : 测试
     * shareUrl : http://localhost:44967/LinkRoute/Index?name=MicroBlog&parameter=microId%3d590ed603-1e3b-40fa-9ab3-162bed246119
     * sharePic : http://img.hitongx.com/UploadFiles/twitter/20160910/15f77ef6-1188-4b4d-a7a5-6abef041d429.jpg@_200w_200h_1e_1c.jpg
     */

    private String shareTitle;
    private String shareText;
    private String shareUrl;
    private String sharePic;
    private String shareObjectId;

    private int shareObjectType;
    private int social;

    public int getShareObjectType() {
        return shareObjectType;
    }

    public void setShareObjectType(int shareObjectType) {
        this.shareObjectType = shareObjectType;
    }

    public int getSocial() {
        return social;
    }

    public void setSocial(int social) {
        this.social = social;
    }

    public MyShareBean() {
    }

    public MyShareBean(String shareObjectId, int shareObjectType, String shareTitle, String shareText, String shareUrl, String sharePic) {
        this.shareObjectId = shareObjectId;
        this.shareObjectType = shareObjectType;
        this.shareTitle = shareTitle;
        this.shareText = shareText;
        this.shareUrl = shareUrl;
        this.sharePic = sharePic;
    }

    public String getShareObjectId() {
        return shareObjectId;
    }

    public void setShareObjectId(String objectId) {
        this.shareObjectId = objectId;
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
