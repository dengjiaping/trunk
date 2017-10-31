package com.histudent.jwsoft.histudent.body.hiworld.bean;

/**
 * Created by liuguiyu-pc on 2016/9/11.
 */
public class AboutMineRedFlagBean {

    private String ftimePoint = "0";
    private String ctimePoint = "0";
    private String rtimePoint = "0";
    private static AboutMineRedFlagBean bean;

    private AboutMineRedFlagBean() {
    }

    public static synchronized AboutMineRedFlagBean getIntence() {
        if (bean == null)
            bean = new AboutMineRedFlagBean();
        return bean;
    }

    public String getFtimePoint() {
        return ftimePoint;
    }

    public void setFtimePoint(String ftimePoint) {
        this.ftimePoint = ftimePoint;
    }

    public String getCtimePoint() {
        return ctimePoint;
    }

    public void setCtimePoint(String ctimePoint) {
        this.ctimePoint = ctimePoint;
    }

    public String getRtimePoint() {
        return rtimePoint;
    }

    public void setRtimePoint(String rtimePoint) {
        this.rtimePoint = rtimePoint;
    }
}
