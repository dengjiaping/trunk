package com.histudent.jwsoft.histudent.fragment.work;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.activity.homework.WorkDetailTeacherActivity;
import com.histudent.jwsoft.histudent.adapter.work.WorkCompleteAdapter;
import com.histudent.jwsoft.histudent.adapter.work.WorkNoCompleteAdapter;
import com.histudent.jwsoft.histudent.base.BaseFragment;
import com.histudent.jwsoft.histudent.bean.homework.WorkCompleteBean;
import com.histudent.jwsoft.histudent.presenter.homework.WorkNoCompletePresenter;
import com.histudent.jwsoft.histudent.presenter.homework.contract.WorkNoCompleteContract;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.List;

import butterknife.BindView;

/**
 * Created by huyg on 2017/10/25.
 */

public class WorkNoCompleteFragment extends BaseFragment<WorkNoCompletePresenter> implements WorkNoCompleteContract.View{
    @BindView(R.id.work_no_complete_refresh)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;


    private WorkNoCompleteAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private String homeworkId;
    private static final int PAGE_SIZE = 20;
    private static final int PULL_DOWN = 0;
    private static final int PULL_UP = 1;
    private int loading = 0;


    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_work_no_complete;
    }


    public static WorkNoCompleteFragment newInstance(String homeworkId) {
        Bundle args = new Bundle();
        args.putString("homeworkId", homeworkId);
        WorkNoCompleteFragment fragment = new WorkNoCompleteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init() {
        initArguments();
        initView();
    }

    private void initArguments() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            homeworkId = bundle.getString("homeworkId");
        }
    }

    private void initView() {
        initAdapter();
        initRefresh();
    }

    public void initData(){
        loading = PULL_DOWN;
        mPresenter.getCompleteList(homeworkId, false, 0, PAGE_SIZE);
    }


    private void initRefresh() {
        mRefreshLayout.setEnableAutoLoadmore(true);
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                loading = PULL_UP;
                mPresenter.getCompleteList(homeworkId, false, (int) Math.ceil(((float) mAdapter.getItemCount() / PAGE_SIZE)), PAGE_SIZE);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                loading = PULL_DOWN;
                mPresenter.getCompleteList(homeworkId, false, 0, PAGE_SIZE);
            }
        });
    }

    private void initAdapter() {
        mAdapter = new WorkNoCompleteAdapter(getActivity());
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecycler.setAdapter(mAdapter);
        mRecycler.setLayoutManager(mLayoutManager);
    }

    @Override
    public void showContent(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT);
    }

    @Override
    public void showCompleteList(List<WorkCompleteBean.ItemsBean> itemBeans) {
        finishRefreshAndLoadmore();
        if (loading == PULL_DOWN) {
            mAdapter.setList(itemBeans);
        } else {
            mAdapter.addList(itemBeans);
        }
        ((WorkDetailTeacherActivity)getActivity()).setTabTwo(mAdapter.getItemCount());
    }

    @Override
    public void getCompleteListFail() {
        showContent("获取失败");
        finishRefreshAndLoadmore();
    }

    private void finishRefreshAndLoadmore() {
        mRefreshLayout.finishLoadmore();
        mRefreshLayout.finishRefresh();
        ((WorkDetailTeacherActivity)getActivity()).finishRefresh();
    }
}
