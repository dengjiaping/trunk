package com.histudent.jwsoft.histudent.model.bean;

/**
 * Created by lichaojie on 2017/10/26.
 * desc:
 * 用于分页面数据显示
 */

public class PagingBean {

    /**
     * 当前页数索引
     */
    private int mCurrentIndex = 0;

    public int getCurrentIndex() {
        return mCurrentIndex;
    }

    /**
     * 上拉加载时 执行索引++
     *
     * @return
     */
    public PagingBean addCurrentIndex() {
        mCurrentIndex++;
        return this;
    }

    /**
     * 索引重置
     *
     * @return
     */
    public PagingBean resetCurrentIndex() {
        mCurrentIndex = 0;
        return this;
    }

}
