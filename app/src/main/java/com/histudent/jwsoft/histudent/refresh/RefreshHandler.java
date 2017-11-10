package com.histudent.jwsoft.histudent.refresh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.histudent.jwsoft.histudent.activity.homework.WorkAlreadyCompleteActivity;
import com.histudent.jwsoft.histudent.bean.PagingBean;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkAlreadyBean;
import com.histudent.jwsoft.histudent.constant.ParamKeys;
import com.histudent.jwsoft.histudent.manage.ParamsManager;
import com.histudent.jwsoft.histudent.manage.UserManager;
import com.histudent.jwsoft.histudent.presenter.homework.WorkAlreadyCompletePresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lichaojie on 2017/10/26.
 * desc:
 */

public class RefreshHandler implements OnRefreshLoadmoreListener {

    private final Context CONTEXT;
    private final SmartRefreshLayout REFRESH_LAYOUT;
    private final RecyclerView RECYCLERVIEW;
    private final PagingBean PAGE_BEAN;
    private final BaseSectionQuickAdapter ADAPTER;
    private final List<HomeworkAlreadyBean> mAlreadyBeanArrayList = new ArrayList<>();
    private int mType = -1;
    private final WorkAlreadyCompletePresenter PRESENTER;
    private boolean isNeedLoadingDialog;
    private boolean isLoadMore;


    private RefreshHandler(Context context, SmartRefreshLayout refresh_layout, RecyclerView recyclerview,
                           BaseSectionQuickAdapter adapter, PagingBean page_bean, WorkAlreadyCompletePresenter presenter) {
        this.CONTEXT = context;
        this.REFRESH_LAYOUT = refresh_layout;
        this.RECYCLERVIEW = recyclerview;
        this.ADAPTER = adapter;
        this.PAGE_BEAN = page_bean;
        this.PRESENTER = presenter;
        this.REFRESH_LAYOUT.setOnRefreshLoadmoreListener(this);
        RECYCLERVIEW.setAdapter(ADAPTER);
    }

    public static final RefreshHandler create(Context context, SmartRefreshLayout refresh_layout, RecyclerView recyclerview,
                                              BaseSectionQuickAdapter adapter, PagingBean page_bean, WorkAlreadyCompletePresenter presenter) {
        return new RefreshHandler(context, refresh_layout, recyclerview, adapter, page_bean, presenter);
    }

    public RefreshHandler requestData() {
        if (isNeedLoadingDialog) {
            isNeedLoadingDialog = false;
        } else {
            ((WorkAlreadyCompleteActivity) CONTEXT).showLoadingDialog();
        }
        final Map<String, Object> params = ParamsManager.getInstance()
                .setParams(ParamKeys.USER_TYPE, mType)
                .setParams(ParamKeys.PAGE_INDEX, PAGE_BEAN.getCurrentIndex())
                .setParams(ParamKeys.CLASS_ID, UserManager.getInstance().getCurrentClassId())
                .getParamsMap();
        if (mType == 0) {
            PRESENTER.getAlreadyCompleteAllHomeworkList(CONTEXT, params);
        } else {
            PRESENTER.getAlreadyCompleteHomeworkList(CONTEXT, params);
        }
        return this;
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        PAGE_BEAN.addCurrentIndex();
        isNeedLoadingDialog = true;
        isLoadMore = true;
        requestData();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        PAGE_BEAN.resetCurrentIndex();
        isNeedLoadingDialog = true;
        isLoadMore = false;
        requestData();
    }

    public void completeOnRefresh() {
        REFRESH_LAYOUT.finishLoadmore();
        REFRESH_LAYOUT.finishRefresh();
    }

    public void updateListData(ArrayList<HomeworkAlreadyBean> dataList) {
        completeOnRefresh();
        if (PAGE_BEAN.getCurrentIndex() == 0) {
            mAlreadyBeanArrayList.clear();
            REFRESH_LAYOUT.setEnableLoadmore(true);
            final int footerLayoutCount = ADAPTER.getFooterLayoutCount();
            if (footerLayoutCount > 0) {
                ADAPTER.removeFooterView(((WorkAlreadyCompleteActivity) CONTEXT).getFootView());
            }
        }
        mAlreadyBeanArrayList.addAll(dataList);
        ADAPTER.setNewData(mAlreadyBeanArrayList);
        if (mAlreadyBeanArrayList.size() == 0) {
            ADAPTER.setEmptyView(((WorkAlreadyCompleteActivity) CONTEXT).getEmptyView());
            return;
        }
        if (dataList.size() < 8) {
            REFRESH_LAYOUT.setEnableLoadmore(false);
            final int footerLayoutCount = ADAPTER.getFooterLayoutCount();
            if (footerLayoutCount == 0)
                ADAPTER.addFooterView(((WorkAlreadyCompleteActivity) CONTEXT).getFootView());
        }
    }

    public RefreshHandler clearData() {
        PAGE_BEAN.resetCurrentIndex();
        mAlreadyBeanArrayList.clear();
        return this;
    }

    public List<HomeworkAlreadyBean> getList() {
        return mAlreadyBeanArrayList;
    }

    public boolean isLoadMore() {
        return isLoadMore;
    }

    public RefreshHandler setLoadMore(boolean loadMore) {
        isLoadMore = loadMore;
        return this;
    }

    public RefreshHandler setType(int type) {
        this.mType = type;
        return this;
    }

    public boolean isNeedLoadingDialog() {
        return isNeedLoadingDialog;
    }

    public RefreshHandler setNeedLoadingDialog(boolean needLoadingDialog) {
        isNeedLoadingDialog = needLoadingDialog;
        return this;
    }
}
