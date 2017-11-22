package com.histudent.jwsoft.histudent.body.myclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.CodeNum;
import com.histudent.jwsoft.histudent.HiStudentLog;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.activity.PersonalHuoDongActivity;
import com.histudent.jwsoft.histudent.body.hiworld.activity.EssayActivity;
import com.histudent.jwsoft.histudent.body.hiworld.activity.LogActivity;
import com.histudent.jwsoft.histudent.body.hiworld.activity.UploadPhotoActivity;
import com.histudent.jwsoft.histudent.body.message.model.ClassModel;
import com.histudent.jwsoft.histudent.body.message.uikit.session.SessionHelper;
import com.histudent.jwsoft.histudent.body.myclass.adapter.TaskAdapter;
import com.histudent.jwsoft.histudent.body.myclass.bean.GrouthTaskModel;
import com.histudent.jwsoft.histudent.body.myclass.helper.ClassHelper;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.HTWebActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.comment2.utils.ActionTypeEnum;
import com.histudent.jwsoft.histudent.model.constant.Const;
import com.histudent.jwsoft.histudent.model.entity.BadgeClickEvent;
import com.histudent.jwsoft.histudent.model.entity.TaskClickEvent;
import com.histudent.jwsoft.histudent.model.entity.TaskEvent;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.team.TeamService;
import com.netease.nimlib.sdk.team.model.Team;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by liuguiyu-pc on 2017/4/24.
 * 班级等级
 */

public class ClassGradeActivity extends BaseActivity {


    @BindView(R.id.title_middle_text)
    TextView mTitle;
    @BindView(R.id.title_right_text)
    TextView mRightText;

    @BindView(R.id.srl_refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.task_recycler)
    RecyclerView mTaskRecycler;


    @OnClick({R.id.title_left, R.id.title_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left://返回
                finish();
                break;

            case R.id.title_right://兑换奖品
                break;
        }
    }

    private ClassModel mClassModel;
    private PullToRefreshListView taskListView;
    private TaskAdapter mTaskAdapter;
    private RecyclerView.LayoutManager mTaskManager;

    private static final int PAGE_SIZE = 25;
    private String classId;
    private static final int PULL_DOWN = 0;
    private static final int PULL_UP = 1;
    private int loading = 0;
    private int classGrowthValue = 0;


    public static void start(Activity activity, String classId, int request) {
        Intent intent = new Intent(activity, ClassGradeActivity.class);
        intent.putExtra("classId", classId);
        activity.startActivityForResult(intent, request);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_class_grade;
    }

    @Override
    public void initView() {
        initTitle();
        initRefreshView();
        initTaskAdapter();
    }

    private void initRefreshView() {

        mRefreshLayout.setOnRefreshListener((RefreshLayout refreshLayout) -> {
            loading = PULL_DOWN;
            getGrouthTaskList(0,LoadingType.NONE);
            getClassInfo(false);
        });

        mRefreshLayout.setOnLoadmoreListener((RefreshLayout refreshLayout) -> {
            loading = PULL_UP;
            getGrouthTaskList((int) Math.ceil(((float) mTaskAdapter.getSize()) / PAGE_SIZE),LoadingType.NONE);
        });

    }


    private void initTitle() {
        mTitle.setText("班级等级");
    }

    private void initTaskAdapter() {
        mTaskManager = new LinearLayoutManager(this);
        mTaskAdapter = new TaskAdapter(this);
        mTaskRecycler.setLayoutManager(mTaskManager);
        mTaskRecycler.setNestedScrollingEnabled(false);
        mTaskRecycler.setAdapter(mTaskAdapter);
    }

    @Override
    public void doAction() {
        super.doAction();
        classId = getIntent().getStringExtra("classId");
    }

    @Override
    protected void onResume() {
        super.onResume();
        loading = PULL_DOWN;
        getClassInfo(false);
        getGrouthTaskList(0,LoadingType.FLOWER);

    }

    //获取班级详情数据
    private void getClassInfo(boolean isNeedLoading) {

        ClassHelper.getClassInfo(this, classId, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                mRefreshLayout.finishLoadmore();
                mRefreshLayout.finishRefresh();

                mClassModel = JSON.parseObject(result, ClassModel.class);
                if (mClassModel != null) {
                    mTaskAdapter.setClassModel(mClassModel);

                }
            }

            @Override
            public void onFailure(String errorMsg) {
                mRefreshLayout.finishLoadmore();
                mRefreshLayout.finishRefresh();
            }
        },isNeedLoading?LoadingType.FLOWER:LoadingType.NONE);
    }

    private void getGrouthTaskList(int pageIndex,LoadingType loadingType) {
        ClassHelper.getGrouthTaskList(this, 2, pageIndex, PAGE_SIZE, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                mRefreshLayout.finishLoadmore();
                mRefreshLayout.finishRefresh();
                GrouthTaskModel taskModel = JSON.parseObject(result, GrouthTaskModel.class);
                if (taskModel != null && taskModel.getItems() != null) {
                    List<GrouthTaskModel.TaskListBean> taskListBeans = formatList(taskModel.getItems());

                    switch (loading) {
                        case PULL_DOWN:
                            mTaskAdapter.setTasks(taskListBeans);
                            mTaskAdapter.setFooter(false);
                            mRefreshLayout.setEnableLoadmore(true);
                            break;
                        case PULL_UP:
                            if (taskListBeans.size()<PAGE_SIZE) {
                                mTaskAdapter.setFooter(true);
                                mRefreshLayout.setEnableLoadmore(false);
                            }
                            mTaskAdapter.addTasks(taskListBeans);

                            break;
                    }

                }

            }

            @Override
            public void onFailure(String errorMsg) {
                mRefreshLayout.finishLoadmore();
                mRefreshLayout.finishRefresh();
                Toast.makeText(ClassGradeActivity.this, "获取失败", Toast.LENGTH_SHORT).show();
            }
        },loadingType);
    }


    private List<GrouthTaskModel.TaskListBean> formatList(List<GrouthTaskModel.TaskListBean> taskListBeans) {
        List<GrouthTaskModel.TaskListBean> taskListBeans2 = new ArrayList<>();
        for (int i=0;i<taskListBeans.size();i++){
            if (!taskListBeans.get(i).getIsComplete()) {
                taskListBeans2.add(taskListBeans.get(i));
            }
        }
        return taskListBeans2;
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe
    public void onEvent(TaskEvent event) {
        GrouthTaskModel.TaskListBean mTask = event.getTask();
        if (mTask != null) {
            if (mTask.isCanTodo()) {
                if (mTask.isH5()) {
                    HTWebActivity.start(ClassGradeActivity.this, mTask.getUrl());
                } else {
                    gotoActivity(mTask.getTaskId());
                }
            } else {
                Toast.makeText(ClassGradeActivity.this, "没有权限", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Subscribe
    public void onEvent(TaskClickEvent event) {
        ClassGetGroupNumActivity.start(this, event.classGrothValue);
    }

    @Subscribe
    public void onEvent(BadgeClickEvent event) {
        if (event.badgesBeen != null && event.badgesBeen.size() > 0) {
            ClassBadgeAcitvity.start(this);
        } else {
            Toast.makeText(this, "无徽章信息", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 跳转到具体的activity完成任务
     *
     * @param taskId 任务id
     */
    private void gotoActivity(String taskId) {
        switch (taskId) {
            //[新手任务]推荐成员动态
            case Const.CLASS_ACTIVITY_RECOMMEND_FIRST:
                //朋友圈界面
                ClassCircleActivity.start(ClassGradeActivity.this, mClassModel.getClassId());
                break;
            //分享班级圈
            case Const.CLASS_ACTIVITY_SHARE_FRIST:
                //朋友圈界面
                ClassCircleActivity.start(ClassGradeActivity.this, mClassModel.getClassId());
                break;
            //添加班级成员
            case Const.CLASS_MEMBER_ADD:
                ClassMemberActivity.start(this, CodeNum.CLASS_MEMBER_REQUEST);
                break;
            //[新手任务]发布班级作业
            case Const.HOMEWORK_ASSIGN_FRIST:
                HTWebActivity.start(ClassGradeActivity.this, HistudentUrl.homework);
                break;
            //[新手任务]建立家校互通
            case Const.IM_BUILD_FIRST:
                gotoIM();
                break;
            //[新手任务] 同步随记到班级圈
            case Const.CLASS_MICROBLOG_SYNCHRONOUS_FRIST:
                //发布随记界面
                EssayActivity.start(ClassGradeActivity.this, 0, 0);
                break;
            //[新手任务] 同步照片到班级圈
            case Const.CLASS_PHOTO_SYNCHRONOUS_FIRST:
                //上传照片界面
                UploadPhotoActivity.start(ClassGradeActivity.this, ActionTypeEnum.CLASSANDOWNER,
                        HiCache.getInstance().getLoginUserInfo().getUserId(), true, 0);
                break;
            //[新手任务] 同步日志到班级圈
            case Const.CLASS_BLOG_SYNCHRONOUS_FIRST:
                LogActivity.start(ClassGradeActivity.this);
                break;
            //邀请班级成员
            case Const.CLASS_MEMBER_INVITE:
                ClassMemberActivity.start(this, CodeNum.CLASS_MEMBER_REQUEST);
                break;
            //[新手任务] 发布班级通知
            case Const.CLASS_NOTICE_ADD_FIRST:
                NoticePublishActivity.start(this, 100);
                break;
            //[新手任务] 创建班级相册
            case Const.CLASS_ALBUM_CREATE_FIST:
                ClassHelper.gotoAlbumsCenter(ClassGradeActivity.this, mClassModel.getClassId(), ActionTypeEnum.CLASS, 1111, false);
                break;
            //[新手任务] 发起班级活动
            case Const.CLASS_HUODONG_CREATE_FIRST:
                //发起活动
                CreateHuoDongFirstStep.start(this, mClassModel.getClassId());
                break;
            //[新手任务] 分享班级活动
            case Const.CLASS_HUODONG_SHARE_FIRST:
                //班级活动
                ClassOrGroupHuoDongActivity.start(ClassGradeActivity.this, 1, mClassModel.getClassId(), 100, mClassModel.isIsAdmin());
                break;
            //[新手任务] 记录活动内容轨迹
            case Const.CLASS_HUODONG_TRACK_CREATE:
                PersonalHuoDongActivity.start(this, mClassModel.getClassId(), 1);
                break;
            //[日常任务]成员查看班级圈
            case Const.CLASS_ACTIVITIES_VIEW:
                //朋友圈界面
                ClassCircleActivity.start(ClassGradeActivity.this, mClassModel.getClassId());
                break;
            //每日让班级成员同步不少于10条动态到班级圈
            case Const.CLASS_DAY_ACTIVITY_SYNCHRONOUS:
                //发布随记界面
                EssayActivity.start(ClassGradeActivity.this, 0, 0);
                break;
            //建立家校沟通
            case Const.IM_BUILD:
                gotoIM();
                break;
            //[日常任务] 布置作业
            case Const.HOMEWORK_ASSIGN:
                HTWebActivity.start(ClassGradeActivity.this, HistudentUrl.homework);
                break;
            //推荐成员动态
            case Const.CLASS_ACTIVITY_RECOMMEND:
                //朋友圈界面
                ClassCircleActivity.start(ClassGradeActivity.this, mClassModel.getClassId());
                break;
            //每周发布一条班级通知
            case Const.CLASS_NOTICE_ADD:
                NoticePublishActivity.start(this, 100);
                break;
            //[日常任务] 发起班级活动
            case Const.CLASS_HUODONG_CREATE:
                //发起活动
                CreateHuoDongFirstStep.start(this, mClassModel.getClassId());
                break;
            //新手任务-屏蔽班级圈动态[班级]
            case Const.CLASS_ACTIVITY_SHAIELD_FIRST:
                //朋友圈界面
                ClassCircleActivity.start(ClassGradeActivity.this, mClassModel.getClassId());
                break;
        }
    }

    private void gotoIM() {
        NIMClient.getService(TeamService.class).searchTeam(mClassModel.getChatGroupKey()).setCallback(new RequestCallback<Team>() {
            @Override
            public void onSuccess(Team team) {
                if (team.isMyTeam()) {
                    SessionHelper.startTeamSession(ClassGradeActivity.this, mClassModel.getChatGroupKey(), false);
                } else {
                    ReminderHelper.getIntentce().ToastShow(ClassGradeActivity.this, "你已退出该班级群");
                }
            }

            @Override
            public void onFailed(int i) {
                HiStudentLog.e("家校通进入失败====" + i);

            }

            @Override
            public void onException(Throwable throwable) {
                HiStudentLog.e("家校通进入异常====" + throwable);
            }
        });
    }

}
