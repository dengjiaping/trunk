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
    private List<String> mTransferTeamIdListData;
    private boolean isNeedRefreshCheckStatus;

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
        EventBus.getDefault().postSticky(new WorkReceiverEvent(mListEntity));
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
        mTransferTeamIdListData = new ArrayList<>();
        final List<HomeworkSelectGroupL0Bean> homeworkSelectGroupL0Been = workReceiverEvent.mWorkGroupL0;
        if (homeworkSelectGroupL0Been.size() > 0) {
            for (HomeworkSelectGroupL0Bean groupL0Bean : homeworkSelectGroupL0Been) {
                //添加首标题
                if (groupL0Bean.isCheck()) {
                    final String teamId = groupL0Bean.getTeamId();
                    mTransferTeamIdListData.add(teamId);
                }
                //添加子标题Id
                final List<HomeworkSelectGroupL1Bean> subItems = groupL0Bean.getSubItems();
                if (subItems.size() > 0) {
                    for (HomeworkSelectGroupL1Bean subItem : subItems) {
                        if (subItem.isCheck()) {
                            final String groupDivideId = subItem.getGroupDivideId();
                            mTransferTeamIdListData.add(groupDivideId);
                        }
                    }
                }

            }
        }
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
                mPresenter.getSelectReceiverPersonList(mCurrentClassId);
            }
        }
    }

    @Override
    public void showContent(String message) {

    }

    @Override
    public void updateListData(List<HomeworkSelectGroupL0Bean> list) {
        //调整默认选择的班级状态
        if (mTransferTeamIdListData != null && mTransferTeamIdListData.size() > 0) {
            for (String teamId : mTransferTeamIdListData) {
                if (list != null && list.size() > 0) {
                    for (HomeworkSelectGroupL0Bean homeworkSelectGroupL0Bean : list) {
                        //头标题
                        if (homeworkSelectGroupL0Bean.getTeamId().equals(teamId)) {
                            homeworkSelectGroupL0Bean.setCheck(true);
                        } else {
                            //子条目
                            final List<HomeworkSelectGroupL1Bean> subItems = homeworkSelectGroupL0Bean.getSubItems();
                            if (subItems != null && subItems.size() > 0) {
                                for (HomeworkSelectGroupL1Bean subItem : subItems) {
                                    if (subItem.getGroupDivideId().equals(teamId)) {
                                        subItem.setCheck(true);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            mTransferTeamIdListData = null;
        }
        //把原来旧数据中的选择状态重新更新到新数据容器里(创建分组页面返回后)
        if (isNeedRefreshCheckStatus) {
            isNeedRefreshCheckStatus = false;
            final List<String> oldSelectTeam = new ArrayList<>();
            for (MultiItemEntity multiItemEntity : mListData) {
                if (multiItemEntity instanceof HomeworkSelectGroupL0Bean) {
                    //主标题
                    final HomeworkSelectGroupL0Bean itemEntity = (HomeworkSelectGroupL0Bean) multiItemEntity;
                    if (itemEntity.isCheck()) {
                        oldSelectTeam.add(itemEntity.getTeamId());
                    } else {
                        //子条目
                        final List<HomeworkSelectGroupL1Bean> subItems = itemEntity.getSubItems();
                        if (subItems != null && subItems.size() > 0) {
                            for (HomeworkSelectGroupL1Bean subItem : subItems) {
                                if (subItem.isCheck()) {
                                    oldSelectTeam.add(subItem.getGroupDivideId());
                                }
                            }
                        }
                    }
                }
            }

            //刷新新数据容器中的状态
            if (oldSelectTeam.size() > 0) {
                for (String id : oldSelectTeam) {
                    for (HomeworkSelectGroupL0Bean homeworkSelectGroupL0Bean : list) {
                        if (homeworkSelectGroupL0Bean.getTeamId().equals(id)) {
                            homeworkSelectGroupL0Bean.setCheck(true);
                        } else {
                            final List<HomeworkSelectGroupL1Bean> subItems = homeworkSelectGroupL0Bean.getSubItems();
                            if (subItems != null && subItems.size() > 0) {
                                for (HomeworkSelectGroupL1Bean subItem : subItems) {
                                    if (subItem.getGroupDivideId().equals(id)) {
                                        subItem.setCheck(true);
                                    }
                                }
                            }
                        }
                    }
                }
            }

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
