package com.histudent.jwsoft.histudent.fragment.work;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.activity.homework.WorkDetailTeacherActivity;
import com.histudent.jwsoft.histudent.adapter.work.WorkCompleteAdapter;
import com.histudent.jwsoft.histudent.adapter.work.WorkNoCompleteAdapter;
import com.histudent.jwsoft.histudent.base.BaseFragment;
import com.histudent.jwsoft.histudent.bean.homework.WorkCompleteBean;
import com.histudent.jwsoft.histudent.body.myclass.activity.NoticeDetailActivity;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.entity.WorkNoticeEvent;
import com.histudent.jwsoft.histudent.presenter.homework.WorkNoCompletePresenter;
import com.histudent.jwsoft.histudent.presenter.homework.contract.WorkNoCompleteContract;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;

/**
 * Created by huyg on 2017/10/25.
 */

public class WorkNoCompleteFragment extends BaseFragment<WorkNoCompletePresenter> implements WorkNoCompleteContract.View {
    @BindView(R.id.work_no_complete_refresh)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;


    private WorkNoCompleteAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private String homeworkId;
    private static final int PAGE_SIZE = 60;
    private static final int PULL_DOWN = 0;
    private static final int PULL_UP = 1;
    private int loading = 0;
    private String noticeUserId;

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
        initData();
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
        initData();
    }

    public void initData() {
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
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCompleteList(List<WorkCompleteBean.ItemsBean> itemBeans) {
        finishRefreshAndLoadmore();
        if (loading == PULL_DOWN) {
            mAdapter.setList(itemBeans);
        } else {
            mAdapter.addList(itemBeans);
        }
        ((WorkDetailTeacherActivity) getActivity()).setTabTwo(mAdapter.getList() == null ? 0 : mAdapter.getList().size());
    }

    @Override
    public void getCompleteListFail() {
        showContent("获取失败");
        finishRefreshAndLoadmore();
    }

    @Subscribe
    public void onEvent(WorkNoticeEvent event) {
        noticeUserId = event.userId;
        initPopWindow();
    }


    private void initPopWindow() {


        List<String> buttonName = new ArrayList<>();
        List<Integer> listColor = new ArrayList<>();
        listColor.add(Color.rgb(187, 187, 187));
        listColor.add(Color.rgb(51, 51, 51));
        listColor.add(Color.rgb(51, 51, 51));
        buttonName.add("提醒方式\n通知将以你选择的方式通知对方");
        buttonName.add("应用内");
        buttonName.add("短信");
        ReminderHelper.getIntentce().showTopMenuDialog(getActivity(), "", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.btn_02:
                        mPresenter.singleNotice(homeworkId, noticeUserId, 3);
                        break;
                    case R.id.btn_03:
                        mPresenter.singleNotice(homeworkId, noticeUserId, 4);
                        break;
                }
            }
        }, buttonName, listColor, false);

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
