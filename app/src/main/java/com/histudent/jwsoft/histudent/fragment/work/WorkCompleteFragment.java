package com.histudent.jwsoft.histudent.fragment.work;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.activity.homework.CorrectWorkActivity;
import com.histudent.jwsoft.histudent.activity.homework.WorkDetailStudentActivity;
import com.histudent.jwsoft.histudent.activity.homework.WorkDetailTeacherActivity;
import com.histudent.jwsoft.histudent.adapter.work.WorkCompleteAdapter;
import com.histudent.jwsoft.histudent.adapter.work.WorkNoCompleteAdapter;
import com.histudent.jwsoft.histudent.base.BaseFragment;
import com.histudent.jwsoft.histudent.bean.RecordBean;
import com.histudent.jwsoft.histudent.bean.homework.WorkCompleteBean;
import com.histudent.jwsoft.histudent.entity.CorrectWorkEvent;
import com.histudent.jwsoft.histudent.presenter.homework.WorkCompletePresenter;
import com.histudent.jwsoft.histudent.presenter.homework.contract.WorkCompleteContract;
import com.histudent.jwsoft.histudent.presenter.homework.contract.WorkDetailTeacherContract;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;

/**
 * Created by huyg on 2017/10/25.
 */

public class WorkCompleteFragment extends BaseFragment<WorkCompletePresenter> implements WorkCompleteContract.View {

    @BindView(R.id.complete_refresh)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;


    private WorkCompleteAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private String homeworkId;
    private static final int PAGE_SIZE = 20;
    private static final int PULL_DOWN = 0;
    private static final int PULL_UP = 1;
    private int loading = 0;
    private Intent intent = new Intent();

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_work_complete;
    }

    public static WorkCompleteFragment newInstance(String homeworkId) {
        Bundle args = new Bundle();
        args.putString("homeworkId", homeworkId);
        WorkCompleteFragment fragment = new WorkCompleteFragment();
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

    public void initData() {
        loading = PULL_DOWN;
        mPresenter.getCompleteList(homeworkId, true, 0, PAGE_SIZE);
    }

    private void initRefresh() {
        mRefreshLayout.setEnableAutoLoadmore(true);
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setNestedScrollingEnabled(true);
        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                loading = PULL_UP;
                mPresenter.getCompleteList(homeworkId, true, (int) Math.ceil(((float) mAdapter.getItemCount() / PAGE_SIZE)), PAGE_SIZE);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                loading = PULL_DOWN;
                mPresenter.getCompleteList(homeworkId, true, 0, PAGE_SIZE);
            }
        });
    }

    private void initAdapter() {
        mAdapter = new WorkCompleteAdapter(getActivity());
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecycler.setAdapter(mAdapter);
        mRecycler.setLayoutManager(mLayoutManager);
    }


    @Override
    public void showContent(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
    }

    @Override
    public void showCompleteList(List<WorkCompleteBean.ItemsBean> itemBeans) {
        finishRefreshAndLoadmore();
        if (loading == PULL_DOWN) {
            mAdapter.setList(itemBeans);
        } else {
            mAdapter.addList(itemBeans);
        }
        ((WorkDetailTeacherActivity) getActivity()).setTabOne(mAdapter.getList() == null ? 0 : mAdapter.getList().size());
    }

    @Override
    public void getCompleteListFail() {
        showContent("获取失败");
        finishRefreshAndLoadmore();
    }

    @Subscribe
    public void onEvent(CorrectWorkEvent event) {
        WorkCompleteBean.ItemsBean itemsBean = event.itemsBean;
        if (itemsBean != null) {
            intent.setClass(getActivity(), CorrectWorkActivity.class);
            intent.putExtra("completeInfo", itemsBean);
            startActivity(intent);
        }
    }

    private void finishRefreshAndLoadmore() {
        mRefreshLayout.finishLoadmore();
        mRefreshLayout.finishRefresh();
        ((WorkDetailTeacherActivity) getActivity()).finishRefresh();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
