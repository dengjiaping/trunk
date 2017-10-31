package com.histudent.jwsoft.histudent.comment2.bean;

/**
 * 举报原因
 * Created by ZhangYT on 2017/6/2.
 */

public class ReportReasonModel {
    private boolean isSeleted;
    private String reason;

    public ReportReasonModel( String reason,boolean isSeleted) {
        this.isSeleted = isSeleted;
        this.reason = reason;
    }

    public boolean isSeleted() {
        return isSeleted;
    }

    public void setSeleted(boolean seleted) {
        isSeleted = seleted;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
