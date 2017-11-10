package com.histudent.jwsoft.histudent.widget;

/**
 * Created by huyg on 2017/11/8.
 */

public class FlowInfo {
    private String content;
    private boolean isCheck;

    public FlowInfo(String content, boolean isCheck) {
        this.content = content;
        this.isCheck = isCheck;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }
}
