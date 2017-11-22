package com.histudent.jwsoft.histudent.model.entity;

/**
 * Created by lichaojie on 2017/9/20.
 * desc:
 * 用于阅读打卡及页面扫描页
 */

public class ReadTaskEvent {

    /**
     * 0:本地开启扫描页面
     * 1:h5直接开启扫描页面
     */
    private int isLocalStartScan = -1;
    private String result = null;
    /**
     * 书籍扫描码
     */
    private String isBn = null;

    /**
     * 是否执行发布
     */
    private boolean isPerformPublish = false;

    /**
     * 获取的书籍名称
     */
    private String bookName = null;


    public String getBookName() {
        return bookName;
    }


    public boolean isPerformPublish() {

        return isPerformPublish;
    }

    public int getLocalStartScan() {
        return isLocalStartScan;
    }

    public String getResult() {
        return result;
    }

    public String getIsBn() {
        return isBn;
    }

    public ReadTaskEvent setLocalStartScan(int localStartScan) {
        isLocalStartScan = localStartScan;
        return this;
    }

    public ReadTaskEvent setResult(String result) {
        this.result = result;
        return this;
    }

    public ReadTaskEvent setIsBn(String isBn) {
        this.isBn = isBn;
        return this;
    }

    public ReadTaskEvent setPerformPublish(boolean performPublish) {
        isPerformPublish = performPublish;
        return this;
    }
    public ReadTaskEvent setBookName(String bookName) {
        this.bookName = bookName;
        return this;
    }


}
