package com.histudent.jwsoft.histudent.body.myclass.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.myclass.adapter.GrowTaskAdapter;
import com.histudent.jwsoft.histudent.body.myclass.bean.GrouthTaskModel;
import com.histudent.jwsoft.histudent.body.myclass.helper.ClassHelper;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.fragment.BaseFragment;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;
import com.histudent.jwsoft.histudent.widget.MostRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by huyg on 2017/7/4.
 * 新手任务
 */

public class NoviceTasksFragment extends BaseFragment{


    @BindView(R.id.novice_task_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.srl_refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    private GrowTaskAdapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private View mEmptyView;
    private View mFootView;
    private static final int PAGE_SIZE = 20;
    private static final int PULL_DOWN = 0;
    private static final int PULL_UP = 1;
    private int loading = 0;
    // 默认,话题文本高亮颜色
    private static final int FOREGROUND_COLOR = Color.parseColor("#000000");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_novice,container,false);
        ButterKnife.bind(this,view);
        return view;
    }


    private void initRefresh() {
        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                loading = PULL_UP;
                getGrouthTaskList((int)Math.ceil(((float)mAdapter.getSize())/PAGE_SIZE),LoadingType.NONE);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                loading = PULL_DOWN;
                getGrouthTaskList(0,LoadingType.NONE);
            }
        });
    }


    public void getGrouthTaskList(int pageIndex, LoadingType loadingType){
        ClassHelper.getGrouthTaskList(getActivity(),2, 2,pageIndex,PAGE_SIZE, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {


                GrouthTaskModel taskModel = JSON.parseObject(result, GrouthTaskModel.class);
                switch (loading){
                    case PULL_DOWN:
                        mRefreshLayout.finishRefresh();
                        mAdapter.setFooter(false);
                        mRefreshLayout.setEnableLoadmore(true);
                        if (taskModel != null) {

                            mAdapter.setTasks(taskModel.getItems());
                        }
                        break;
                    case PULL_UP:
                        mRefreshLayout.finishLoadmore();
                        if (taskModel != null) {
                            if (taskModel.getItems() == null || taskModel.getItems().size() < PAGE_SIZE) {
                                mAdapter.setFooter(true);
                                mRefreshLayout.setEnableLoadmore(false);
                            } else {
                                mAdapter.setFooter(false);
                                mRefreshLayout.setEnableLoadmore(true);
                            }
                            mAdapter.addTasks(taskModel.getItems());
                        }
                        break;
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadmore();
                Toast.makeText(getActivity(), "获取失败", Toast.LENGTH_SHORT).show();
            }
        },loadingType);
    }


    @Override
    public void initView() {
        super.initView();
//        initEmptyView();
//        initFootView();
        initAdapter();
        initRefresh();
    }

//    private void initFootView() {
//
//    }

//    private void initEmptyView() {
//        mEmptyView
//    }

    private void initAdapter() {
        mManager = new LinearLayoutManager(getActivity());
        mAdapter = new GrowTaskAdapter(getActivity());
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(mManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        super.initData();
        loading = PULL_DOWN;
        getGrouthTaskList(0,LoadingType.FLOWER);
    }
}
