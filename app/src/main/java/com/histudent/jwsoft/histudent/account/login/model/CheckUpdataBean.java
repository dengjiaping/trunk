package com.histudent.jwsoft.histudent.account.login.model;

import java.util.List;

/**
 *
 * 检查版本更新
 * Created by ZhangYT on 2016/9/8.
 */
public class CheckUpdataBean {

    /**
     * status : 0
     * updatedesc : 您的版本是最新的
     * updateurl : http://www.hitongx.com
     * qrcodeurl :
     * version : 1.1.0
     * pageTime : 0
     * launchpage : [{"width":960,"height":1280,"imgUrl":"http://hitongxue.oss-cn-hangzhou.aliyuncs.com/Logo/startpage.jpg"}]
     */

    private int status;
    private String updatedesc;
    private String updateurl;
    private String qrcodeurl;
    private String version;
    private int pageTime;
    private List<LaunchpageBean> launchpage;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUpdatedesc() {
        return updatedesc;
    }

    public void setUpdatedesc(String updatedesc) {
        this.updatedesc = updatedesc;
    }

    public String getUpdateurl() {
        return updateurl;
    }

    public void setUpdateurl(String updateurl) {
        this.updateurl = updateurl;
    }

    public String getQrcodeurl() {
        return qrcodeurl;
    }

    public void setQrcodeurl(String qrcodeurl) {
        this.qrcodeurl = qrcodeurl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getPageTime() {
        return pageTime;
    }

    public void setPageTime(int pageTime) {
        this.pageTime = pageTime;
    }

    public List<LaunchpageBean> getLaunchpage() {
        return launchpage;
    }

    public void setLaunchpage(List<LaunchpageBean> launchpage) {
        this.launchpage = launchpage;
    }

    public static class LaunchpageBean {
        /**
         * width : 960
         * height : 1280
         * imgUrl : http://hitongxue.oss-cn-hangzhou.aliyuncs.com/Logo/startpage.jpg
         */

        private int width;
        private int height;
        private String imgUrl;

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }
}
