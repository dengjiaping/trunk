package com.histudent.jwsoft.histudent.model.bean.homework;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Created by lichaojie on 2017/10/24.
 * desc:
 * HomeworkAlreadyBean:作业列表的每个item(时间 + 数据)
 * HomeworkAlreadySubBean:每个条目下的子数据(具体的作业)
 */

public class HomeworkAlreadyBean extends SectionEntity<HomeworkAlreadySubBean> {
    /**
     * 头标题内容
     */
    private String mTitleHeadContent;
    private boolean isShowTitle;

    public boolean isShowTitle() {
        return isShowTitle;
    }

    public void setShowTitle(boolean showTitle) {
        isShowTitle = showTitle;
    }

    public HomeworkAlreadyBean(boolean isHeader, String header) {
        super(isHeader, header);
        this.mTitleHeadContent = header == null ? "" : header;
    }

    public HomeworkAlreadyBean(HomeworkAlreadySubBean homeworkAlreadySubBean) {
        super(homeworkAlreadySubBean);
    }

    public String getTitleHeadContent() {
        return mTitleHeadContent;
    }

    public void setTitleHeadContent(String titleHeadContent) {
        mTitleHeadContent = titleHeadContent;
    }

}
