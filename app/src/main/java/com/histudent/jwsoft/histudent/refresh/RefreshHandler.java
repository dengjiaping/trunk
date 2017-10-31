package com.histudent.jwsoft.histudent.refresh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.histudent.jwsoft.histudent.activity.homework.HomeworkAlreadyActivity;
import com.histudent.jwsoft.histudent.adapter.homework.convert.HomeworkAlreadyConvert;
import com.histudent.jwsoft.histudent.bean.PagingBean;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkAlreadyBean;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.constant.ParamKeys;
import com.histudent.jwsoft.histudent.manage.ParamsManager;
import com.histudent.jwsoft.histudent.manage.UserManager;
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

    public boolean isLoadFlower() {
        return isLoadFlower;
    }

    public void setLoadFlower(boolean loadFlower) {
        isLoadFlower = loadFlower;
    }

    private boolean isLoadFlower = true;

    private RefreshHandler(Context context, SmartRefreshLayout refresh_layout, RecyclerView recyclerview,
                           BaseSectionQuickAdapter adapter, PagingBean page_bean) {
        this.CONTEXT = context;
        this.REFRESH_LAYOUT = refresh_layout;
        this.RECYCLERVIEW = recyclerview;
        this.ADAPTER = adapter;
        this.PAGE_BEAN = page_bean;
        this.REFRESH_LAYOUT.setOnRefreshLoadmoreListener(this);
        RECYCLERVIEW.setAdapter(ADAPTER);
    }

    public static final RefreshHandler create(Context context, SmartRefreshLayout refresh_layout, RecyclerView recyclerview,
                                              BaseSectionQuickAdapter adapter, PagingBean page_bean) {
        return new RefreshHandler(context, refresh_layout, recyclerview, adapter, page_bean);
    }

    public void requestData() {
        HiStudentHttpUtils.postDataByOKHttp((HomeworkAlreadyActivity) CONTEXT, buildParams(),
                mType == 0 ? HistudentUrl.TEACHER_ALREADY_HOMEWORK_LIST_ALL : HistudentUrl.TEACHER_OR_STUDENT_ALREADY_HOMEWORK_LIST, new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        REFRESH_LAYOUT.finishLoadmore();
                        REFRESH_LAYOUT.finishRefresh();
                        isLoadFlower = false;
                        final ArrayList<HomeworkAlreadyBean> dataList = HomeworkAlreadyConvert.create(result).convert();
                        if (PAGE_BEAN.getCurrentIndex() == 0) {
                            mAlreadyBeanArrayList.clear();
                            REFRESH_LAYOUT.setEnableLoadmore(true);
                            final int footerLayoutCount = ADAPTER.getFooterLayoutCount();
                            if (footerLayoutCount > 0) {
                                ADAPTER.removeFooterView(((HomeworkAlreadyActivity) CONTEXT).getFootView());
                            }
                        }
                        mAlreadyBeanArrayList.addAll(dataList);
                        ADAPTER.setNewData(mAlreadyBeanArrayList);
                        if (mAlreadyBeanArrayList.size() == 0) {
                            ADAPTER.setEmptyView(((HomeworkAlreadyActivity) CONTEXT).getEmptyView());
                            return;
                        }
                        if (dataList.size() < 8) {
                            REFRESH_LAYOUT.setEnableLoadmore(false);
                            final int footerLayoutCount = ADAPTER.getFooterLayoutCount();
                            if (footerLayoutCount == 0)
                                ADAPTER.addFooterView(((HomeworkAlreadyActivity) CONTEXT).getFootView());
                        }
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        REFRESH_LAYOUT.finishLoadmore();
                        REFRESH_LAYOUT.finishRefresh();
                    }
                }, isLoadFlower ? LoadingType.FLOWER : LoadingType.NONE);
    }

    public Map<String, Object> buildParams() {
        mType = ((HomeworkAlreadyActivity) CONTEXT).getType();
        return ParamsManager.getInstance()
                .setParams(ParamKeys.USER_TYPE, mType)
                .setParams(ParamKeys.PAGE_INDEX, PAGE_BEAN.getCurrentIndex())
                .setParams(ParamKeys.CLASS_ID, UserManager.getInstance().getCurrentClassId())
                .getParamsMap();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        PAGE_BEAN.addCurrentIndex();
        isLoadFlower = false;
        requestData();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        isLoadFlower = false;
        PAGE_BEAN.resetCurrentIndex();
        requestData();
    }

    public void clearData() {
        mAlreadyBeanArrayList.clear();
    }

    public List<HomeworkAlreadyBean> getList(){
        return mAlreadyBeanArrayList;
    }
}
