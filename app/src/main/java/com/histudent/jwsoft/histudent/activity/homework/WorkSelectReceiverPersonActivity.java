package com.histudent.jwsoft.histudent.activity.homework;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.adapter.decoration.Divider;
import com.histudent.jwsoft.histudent.adapter.homework.HomeworkSelectPersonExpandableAdapter;
import com.histudent.jwsoft.histudent.base.BaseActivity;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkSelectGroupL0Bean;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkSelectGroupL1Bean;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.constant.TransferKeys;
import com.histudent.jwsoft.histudent.entity.WorkReceiverEvent;
import com.histudent.jwsoft.histudent.listener.homework.OnItemSelectReceiverPersonListener;
import com.histudent.jwsoft.histudent.manage.UserManager;
import com.histudent.jwsoft.histudent.presenter.homework.WorkSelectReceiverPersonPresenter;
import com.histudent.jwsoft.histudent.presenter.homework.contract.WorkSelectReceiverPersonContract;
import com.histudent.jwsoft.histudent.tool.ToastTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lichaojie on 2017/11/1.
 * desc:
 * 发布作业-选择接收人
 */

public class WorkSelectReceiverPersonActivity extends BaseActivity<WorkSelectReceiverPersonPresenter>
        implements WorkSelectReceiverPersonContract.View {

    @BindView(R.id.title_middle_text)
    TextView mTvTitleMiddleText;
    @BindView(R.id.title_right_text)
    TextView mIvTitleRightText;
    @BindView(R.id.rv_select_group)
    RecyclerView mRvSelectGroup;

    private List<MultiItemEntity> mListData = new ArrayList<>();
    private HomeworkSelectPersonExpandableAdapter mPersonExpandableAdapter;
    private final List<String> mUserSelectTeamIdList = new ArrayList<>();
    private List<HomeworkSelectGroupL0Bean> mListEntity = new ArrayList<>();
    private String mCurrentClassId;
    private final List<String> mTransferTeamIdListData = new ArrayList<>();
    private boolean isNeedRefreshCheckStatus;
    private String mTransferTeamIdFromAddGroup;

    @OnClick(R.id.title_left)
    void finishPage() {
        finish();
    }

    @OnClick(R.id.title_right_text)
    void confirm() {
        mUserSelectTeamIdList.clear();
        for (MultiItemEntity multiItemEntity : mListData) {
            if (multiItemEntity instanceof HomeworkSelectGroupL0Bean) {
                final HomeworkSelectGroupL0Bean entity = (HomeworkSelectGroupL0Bean) multiItemEntity;
                mListEntity.add(entity);
            }
        }
        EventBus.getDefault().post(new WorkReceiverEvent(mListEntity));
        finish();
    }


    private static final String TAG = WorkSelectReceiverPersonActivity.class.getName();


    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_homework_select_receiver_person;
    }

    @Override
    protected void init() {
        initView();
        initData();
    }

    public void initView() {
        mTvTitleMiddleText.setText("接收人");
        mIvTitleRightText.setText(R.string.confirm);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        final Divider divider = new Divider(ContextCompat.getDrawable(this, R.drawable.divider_line), LinearLayoutManager.VERTICAL);
        divider.setMargin(SystemUtil.dp2px(12), 0, 0, 0);
        mRvSelectGroup.setLayoutManager(linearLayoutManager);
        mRvSelectGroup.addItemDecoration(divider);
    }


    public void initData() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        showLoadingDialog();
        mCurrentClassId = UserManager.getInstance().getCurrentClassId();
        mPresenter.getSelectReceiverPersonList(mCurrentClassId);
        mPersonExpandableAdapter = HomeworkSelectPersonExpandableAdapter.create(mListData);
        mRvSelectGroup.setAdapter(mPersonExpandableAdapter);
        mRvSelectGroup.addOnItemTouchListener(OnItemSelectReceiverPersonListener.create(this, mListData));
    }

    @Subscribe(sticky = true)
    public void onEvent(WorkReceiverEvent workReceiverEvent) {
        //从发布页面传递过来的小组信息
        mPresenter.solveGroupInformationFromPublish(mTransferTeamIdListData, workReceiverEvent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //重新刷新数据 分组成员已经发
        if (intent != null) {
            final String classId = intent.getStringExtra(TransferKeys.CLASS_ID);
            final String groupId = intent.getStringExtra(TransferKeys.GROUP_ID);
            if (classId != null || groupId != null) {
                showLoadingDialog();
                isNeedRefreshCheckStatus = true;
                mTransferTeamIdFromAddGroup = intent.getStringExtra(TransferKeys.TEAM_ID);
                mPresenter.getSelectReceiverPersonList(mCurrentClassId);
            }
        }
    }

    @Override
    public void showContent(String message) {

    }

    @Override
    public void updateListData(List<HomeworkSelectGroupL0Bean> list) {
        //1.调整默认选择的班级状态
        mPresenter.adjustDefaultClassStatus(mTransferTeamIdListData, list);

        //2.把原来旧数据中的选择状态重新更新到新数据容器里(创建分组页面返回后)
        if (isNeedRefreshCheckStatus) {
            isNeedRefreshCheckStatus = false;
            mPresenter.updateOldDataSelectStatus(mListData, list);
        }

        //3.如果是从创建分组返回来  根据传递过来的teamId去默认选择该分组
        if (!TextUtils.isEmpty(mTransferTeamIdFromAddGroup)) {
            mPresenter.solveAddGroupMemberInformation(mTransferTeamIdFromAddGroup, list);
            mTransferTeamIdFromAddGroup = null;
        }

        mListData.clear();
        mListData.addAll(list);
        mPersonExpandableAdapter.setNewData(mListData);
        mPersonExpandableAdapter.expandAll();
    }

    @Override
    public void controlDialogStatus(String message) {
        if (!TextUtils.isEmpty(message))
            ToastTool.showCommonToast(message);
        dismissLoadingDialog();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
