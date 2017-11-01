package com.histudent.jwsoft.histudent.body.myclass.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.gyf.barlibrary.ImmersionBar;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.histudent.jwsoft.histudent.CodeNum;
import com.histudent.jwsoft.histudent.HiStudentApplication;
import com.histudent.jwsoft.histudent.HiStudentLog;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.activity.homework.WorkAlreadyCompleteActivity;
import com.histudent.jwsoft.histudent.body.find.adapter.SortGroupAdapter;
import com.histudent.jwsoft.histudent.body.hiworld.bean.SimpleUserModel;
import com.histudent.jwsoft.histudent.body.message.model.ClassModel;
import com.histudent.jwsoft.histudent.body.message.uikit.rts.doodle.DoodleView;
import com.histudent.jwsoft.histudent.body.message.uikit.session.SessionHelper;
import com.histudent.jwsoft.histudent.body.mine.model.UserClassListModel;
import com.histudent.jwsoft.histudent.body.mine.parser.DataParser;
import com.histudent.jwsoft.histudent.body.myclass.activity.ClassAddOrCreatActivity;
import com.histudent.jwsoft.histudent.body.myclass.activity.ClassBadgeAcitvity;
import com.histudent.jwsoft.histudent.body.myclass.activity.ClassCircleActivity;
import com.histudent.jwsoft.histudent.body.myclass.activity.ClassGradeActivity;
import com.histudent.jwsoft.histudent.body.myclass.activity.ClassJoinActivity;
import com.histudent.jwsoft.histudent.body.myclass.activity.ClassOrGroupHuoDongActivity;
import com.histudent.jwsoft.histudent.body.myclass.activity.ClassSetActivity;
import com.histudent.jwsoft.histudent.body.myclass.activity.NoticeListActivity;
import com.histudent.jwsoft.histudent.body.myclass.adapter.ClassAppAdapter;
import com.histudent.jwsoft.histudent.body.myclass.helper.ClassHelper;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.MyWebActivity;
import com.histudent.jwsoft.histudent.commen.activity.SelecteClassmatesActiviy;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.fragment.BaseFragment;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.ComViewUitls;
import com.histudent.jwsoft.histudent.commen.utils.DataUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.setOnClickListener;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.commen.view.MyGridView;
import com.histudent.jwsoft.histudent.commen.view.StarBar;
import com.histudent.jwsoft.histudent.commen.view.swipemenulistview.AutoScrollImageView;
import com.histudent.jwsoft.histudent.comment2.utils.ActionTypeEnum;
import com.histudent.jwsoft.histudent.comment2.utils.ClassAppKey;
import com.histudent.jwsoft.histudent.comment2.utils.SeletClassMateEnum;
import com.histudent.jwsoft.histudent.constant.TransferKeys;
import com.histudent.jwsoft.histudent.entity.IMMessageEvent;
import com.histudent.jwsoft.histudent.entity.MessageUpdateEvent;
import com.histudent.jwsoft.histudent.entity.ReadClockMessageEvent;
import com.histudent.jwsoft.histudent.manage.UserManager;
import com.histudent.jwsoft.histudent.tool.CommonAdvanceUtils;
import com.netease.nim.uikit.common.util.string.StringUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.netease.nimlib.sdk.team.TeamService;
import com.netease.nimlib.sdk.team.model.Team;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 班级界面
 */
public class ClassFragment extends BaseFragment {

    private View view;
    private PopupWindow popupWindow;
    private MyGridView mGridView;
    private ClassAppAdapter appAdapter;
    private List<Object> appList;
    private setOnClickListener listener;
    private TextView mMiddleTitle;
    private SortGroupAdapter classAdapter;
    private List<Object> classList;
    private String title;
    private String classId;
    private IconView leftImage, rightImage, middle_log;
    private ImageView iv_more;
    private LinearLayout mTitleLeftLayout, mTitleRightLayout, mTitleMiddleLayout;
    private UserClassListModel currentClassModel;
    private PullToRefreshScrollView mListView;
    private ClassModel classModel;
    private AutoScrollImageView mScrollImageView;
    private LinearLayout mClassGradeLayout;
    private int imNumber;
    private int mReadClockMessageCount;
    /**
     * 主要用于点击底部app功能后
     * 再次返回此页面 是否需要执行刷新操作
     */
    private boolean isRefresh;
    private StarBar mStarBar;
    private SmartRefreshLayout mRefreshLayout;
    private RelativeLayout mHeadLayout;
    private FrameLayout mHead;
    private List<RecentContact> recentContacts;

    /**
     * 存储阅读打卡是否显示“新”字样状态
     */
    public static final String READ_CLOCK_NEW_SIGN = "sign";


    @BindView(R.id.growth_value)
    TextView mGrowthValue;
    @BindView(R.id.platform_rank)
    TextView mPlatformRank;
    @BindView(R.id.school_rank)
    TextView mSchoolRank;

    @BindView(R.id.view_status_bar)
    View mViewStatus;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_class, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void initView() {
        super.initView();
        appList = new ArrayList<>();
        classList = new ArrayList<>();
        recentContacts = new ArrayList<>();
        mGridView = view.findViewById(R.id.grid_view);
        ((IconView) view.findViewById(R.id.title_left_image)).setText("");
        mListView = view.findViewById(R.id.scrollView);
        mHead = view.findViewById(R.id.head_layout);
        mClassGradeLayout = view.findViewById(R.id.tv_grouth_layout);
        mStarBar = view.findViewById(R.id.rating_bar);
        mRefreshLayout = view.findViewById(R.id.srl_refresh_layout);

        //设置状态高度
        final int statusBarHeight = ImmersionBar.getStatusBarHeight(getActivity());
        final ViewGroup.LayoutParams layoutParams = mViewStatus.getLayoutParams();
        layoutParams.height = statusBarHeight;
        mViewStatus.setLayoutParams(layoutParams);


        classAdapter = new SortGroupAdapter(classList, getActivity());
        mGridView.setFocusable(false);
        mListView.setMode(PullToRefreshBase.Mode.DISABLED);
        mRefreshLayout.setOnRefreshListener((RefreshLayout refreshlayout) -> getClasses(LoadingType.NONE));
        mRefreshLayout.setEnableLoadmore(false);
        listener = (Object position) -> ClassBadgeAcitvity.start(getActivity());
        mGridView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> loadGridViewItemData(parent, view, position, id));
        mHead.setOnClickListener((View view) -> ClassGradeActivity.start(getActivity(), classId, CodeNum.GROUP_TASK));
        String dataStr = HiCache.getInstance().getHttpDataInDB(HistudentUrl.GetUserSyncClassList, HiCache.getInstance().getLoginUserInfo().getUserId());
        if (TextUtils.isEmpty(dataStr)) {
            getClasses(LoadingType.NONE);
        } else {
            updateUi(dataStr, LoadingType.NONE);
        }

        saveDefaultSyncClassToLocal();
    }


    //初始化头部
    private void initHeadViews() {

        mMiddleTitle = view.findViewById(R.id.title_middle_text);
        middle_log = view.findViewById(R.id.title_middle_image);
        leftImage = view.findViewById(R.id.title_left_image);
        rightImage = view.findViewById(R.id.title_right_image);
        mTitleLeftLayout = view.findViewById(R.id.title_left);
        mTitleRightLayout = view.findViewById(R.id.title_right);
        mTitleMiddleLayout = view.findViewById(R.id.title_middle);
        mScrollImageView = view.findViewById(R.id.iv_photo);
        mHeadLayout = view.findViewById(R.id.rl_head_layout);
        middle_log.setPadding(0, SystemUtil.dp2px(5), 0, 0);
        mMiddleTitle.setText(title);

        if (classList.size() > 1) {
            middle_log.setVisibility(View.VISIBLE);
            middle_log.setText(R.string.icon_arrowbottom);
        } else {
            middle_log.setVisibility(View.GONE);
        }

        iv_more = view.findViewById(R.id.iv_left);

        leftImage.setText(R.string.icon_tool);
        leftImage.setTextSize(13);
        rightImage.setText(R.string.icon_add);

        loadListener();
    }

    private void loadListener() {
        mClassGradeLayout.setOnClickListener((View view) -> ClassGradeActivity.start(getActivity(), classId, CodeNum.GROUP_TASK));//班级等级
        mStarBar.setOnClickListener((View view) -> ClassGradeActivity.start(getActivity(), classId, CodeNum.GROUP_TASK));//班级等级
        mTitleLeftLayout.setOnClickListener((View view) -> ClassSetActivity.start(getActivity(), CodeNum.CLASS_SET));
        mTitleRightLayout.setOnClickListener((View view) -> {
                    //班级页面 右上角添加 根据身份去判断
                    if (HiCache.getInstance().getLoginUserInfo().getUserType() == 3) {
                        //老师
                        if (classList.size() > 0) {
                            ClassAddOrCreatActivity.start(getActivity(), 20, false);
                        } else {
                            ClassAddOrCreatActivity.start(getActivity(), 20, true);
                        }
                    } else {
                        //学生
                        ClassJoinActivity.start(getActivity(), 200);
                    }
                }
        );
        mTitleMiddleLayout.setOnClickListener((View view) -> {
            if (classList != null && classList.size() > 1) {
                middle_log.setText(R.string.icon_arrowup);
                initPopWindow();
            }
        });
        view.findViewById(R.id.class_quan).setOnClickListener((View view) -> {
            //点击红点消失
            view.findViewById(R.id.iv_heart).setVisibility(View.GONE);
            view.findViewById(R.id.tv_heart).setVisibility(View.GONE);
            ClassCircleActivity.start(getActivity(), classId);
        });//朋友圈

        mHeadLayout.setOnClickListener((View view) -> ClassHelper.gotoAlbumsCenter(getActivity(), classId, ActionTypeEnum.CLASS, 1111, true));
    }


    private void setMask(int rank) {
        if (rank > 0 && rank <= 1000) {
            mStarBar.setStarMark(5);
        } else if (1001 < rank && rank <= 3000) {
            mStarBar.setStarMark(4);
        } else if (3001 < rank && rank <= 5000) {
            mStarBar.setStarMark(3);
        } else if (5001 < rank && rank <= 10000) {
            mStarBar.setStarMark(2);
        } else if (rank == 0) {
            mStarBar.setStarMark(0);
        } else {
            mStarBar.setStarMark(1);
        }

    }

    private void loadGridViewItemData(AdapterView<?> parent, View view, int position, long id) {
        UserManager.getInstance().setCurrentClassId(classId);
        ClassModel.ClassAppsBean bean = (ClassModel.ClassAppsBean) appList.get(position);
        switch (bean.getAppKey()) {

            //家校通
            case ClassAppKey.HOME_SCHOOL_COMMICATION:

                final String imkey = classModel.getChatGroupKey();
                if (TextUtils.isEmpty(imkey)) {
                    HiStudentLog.e("== 后台返回ChatGroupKey为空");
                    return;
                }
                NIMClient.getService(TeamService.class).searchTeam(imkey).setCallback(new RequestCallback<Team>() {
                    @Override
                    public void onSuccess(Team team) {
                        if (team.isMyTeam()) {
                            SessionHelper.startTeamSession(getActivity(), imkey, false);
                        } else {
                            ReminderHelper.getIntentce().ToastShow(getActivity(), "你已退出该班级群");
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
                break;

            //通讯录
            case ClassAppKey.CONTACT_LIST:
                SelecteClassmatesActiviy.start(getActivity(), new ArrayList<SimpleUserModel>(), 111, SeletClassMateEnum.CLASSMATE);
                break;

            //班级活动
            case ClassAppKey.CLASS_HUODONG:

                ClassOrGroupHuoDongActivity.start(getActivity(), 1, this.classId, 100, classModel.isIsAdmin());
                break;

            //班级相册
            case ClassAppKey.CLASS_ALBUM:
                ClassHelper.gotoAlbumsCenter(getActivity(), this.classId, ActionTypeEnum.CLASS, 1111, false);
                break;


            //班级荣誉
            case "班级荣誉":

                ClassBadgeAcitvity.start(getActivity());
                break;

            //通知
            case ClassAppKey.NOTICE:
                isRefresh = true;
                NoticeListActivity.start(getActivity(), this.classId);
                break;

            //作业
            case ClassAppKey.HOMEWORK:
                isRefresh = true;
//                MyWebActivity.start(getActivity(), bean.getAppUrl());
                final Intent intent = new Intent(getContext(), WorkAlreadyCompleteActivity.class);
                intent.putExtra(TransferKeys.IS_ADMIN,classModel.isIsAdmin());
                CommonAdvanceUtils.startActivity(getContext(), intent);
                break;
            //班级任务
            case ClassAppKey.CLASS_TASK:
                isRefresh = true;
                ClassGradeActivity.start(getActivity(), this.classId, CodeNum.GROUP_TASK);
                break;
            case ClassAppKey.READ_PUNCH_THE_CLOCK:
            case ClassAppKey.READ_PUNCH_THE_CLOCK_TEST:
                //阅读打卡
                isRefresh = true;
                HiStudentApplication
                        .getInstance()
                        .getSharedPreferences(READ_CLOCK_NEW_SIGN, Context.MODE_PRIVATE)
                        .edit().putBoolean(READ_CLOCK_NEW_SIGN, true).apply();
                MyWebActivity.start(getActivity(), bean.getAppUrl());
                break;
            //打卡测试
//            case ClassAppKey.CLASS_CLOCK:
//                Intent intent = new Intent(getActivity(), AddClockActivity.class);
//                startActivity(intent);
//                break;

        }
    }


    //班级切换PopWindow
    private void initPopWindow() {
        View layout = View.inflate(getActivity(), R.layout.same_city_popwindow, null);
        layout.findViewById(R.id.line).setVisibility(View.VISIBLE);
        view.findViewById(R.id.frame).setVisibility(View.VISIBLE);
        final ListView listView = layout.findViewById(R.id.list_view);

        //设置默认显示的班级
        if (classModel != null) {
            classAdapter.initSeletedItem(classModel.getClassId());
        }
        listView.setAdapter(classAdapter);

        popupWindow = new PopupWindow(layout, SystemUtil.getScreenWind(), LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        popupWindow.showAsDropDown(view.findViewById(R.id.title_middle_text), 0, 0);
        popupWindow.setOnDismissListener(() -> {
            middle_log.setText(R.string.icon_arrowbottom);
            view.findViewById(R.id.frame).setVisibility(View.GONE);
        });
        listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            currentClassModel = ((UserClassListModel) classList.get(position));
            title = currentClassModel.getCName();
            mMiddleTitle.setText(title);

            if (!StringUtil.isEmpty(classId) && !classId.equals(currentClassModel.getClassId())) {
                classId = currentClassModel.getClassId();
                getClassInfor(LoadingType.NONE);
            }

            popupWindow.dismiss();
        });
    }

    /**
     * 获取最近未读会话
     */
    RequestCallbackWrapper requestCallbackWrapper = new RequestCallbackWrapper<List<RecentContact>>() {
        @Override
        public void onResult(int code, List<RecentContact> recents, Throwable exception) {

            if (code != ResponseCode.RES_SUCCESS || recents == null) {
                NIMClient.getService(MsgService.class).queryRecentContacts().setCallback(requestCallbackWrapper);
                return;
            }
            if (classModel == null) {
                NIMClient.getService(MsgService.class).queryRecentContacts().setCallback(requestCallbackWrapper);
                return;
            }
            imNumber = 0;
            for (RecentContact recentContact : recents) {
                if (recentContact.getSessionType() == SessionTypeEnum.Team && recentContact.getContactId().equals(classModel.getChatGroupKey())) {
                    imNumber = imNumber + recentContact.getUnreadCount();
                }
                for (Object object : classList) {
                    UserClassListModel userClassListModel = (UserClassListModel) object;
                    if (recentContact.getSessionType() == SessionTypeEnum.Team && recentContact.getContactId().equals(userClassListModel.getChatGroupKey())) {
                        userClassListModel.setImNum(recentContact.getUnreadCount());
                    }
                }

            }
            classModel.setImNum(imNumber);
            classAdapter.notifyDataSetChanged();
            appAdapter.notifyDataSetChanged();
        }
    };

    //数据获取成功后更新界面
    private void UpdateUI() {
        appAdapter = new ClassAppAdapter(getActivity(), appList, classModel);
        mGridView.setAdapter(appAdapter);

        ClassHelper.updateUi(getActivity(), view, classModel);
        if (classModel.getClassApps() != null) {
            appList.clear();
            appList.addAll(classModel.getClassApps());
//            appBean.setAppKey(ClassAppKey.CLASS_CLOCK);
//            appBean.setAppName("打卡测试");
//            appList.add(appBean);
            appAdapter.notifyDataSetChanged();
        }
        leftImage.setVisibility(View.VISIBLE);
        rightImage.setVisibility(View.VISIBLE);
        if (classModel.getClassBadges() != null) {

            ((LinearLayout) view.findViewById(R.id.user)).removeAllViews();
            if (classModel.getClassBadges().size() > 0) {
                view.findViewById(R.id.badge_layout).setVisibility(View.VISIBLE);
                for (ClassModel.ClassBadgesBean bean : classModel.getClassBadges()) {
                    ComViewUitls.addView1(getActivity(), view.findViewById(R.id.user), bean, listener);
                }
            } else {
                view.findViewById(R.id.badge_layout).setVisibility(View.GONE);
            }
        }

        //设置班级图片墙
        ClassHelper.showPlayAnimation(getActivity(), mScrollImageView, classModel.getBannerList());
        setMask(classModel.getPlatformRanking());
        mGrowthValue.setText(String.valueOf(classModel.getClassGrothValue()));
        mSchoolRank.setText(String.valueOf(classModel.getSchoolRanking()));
        mPlatformRank.setText(String.valueOf(classModel.getPlatformRanking()));
        NIMClient.getService(MsgService.class).queryRecentContacts().setCallback(requestCallbackWrapper);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isRefresh) {
            getClasses(LoadingType.NONE);
            isRefresh = false;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    /***
     * 获取用户的创建或者加入的班级
     */
    public void getClasses(final LoadingType loadingType) {
        DataUtils.GetUserSyncClassList(getActivity(), false, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                mRefreshLayout.finishRefresh();
                HiCache.getInstance().saveHttpDataIntoDB(HistudentUrl.GetUserSyncClassList, HiCache.getInstance().getLoginUserInfo().getUserId(), result);
                Log.e("onSuccess", result);
                updateUi(result, loadingType);
            }

            @Override
            public void onFailure(String errorMsg) {
                mRefreshLayout.finishRefresh();
            }
        }, loadingType);
    }

    private void updateUi(String result, LoadingType loadingType) {
        boolean tag = false;
        classList.clear();
        classList.addAll(DataParser.getUserClassList(result));

        if (classList != null && classList.size() > 0) {
            mListView.setVisibility(View.VISIBLE);
            mRefreshLayout.setVisibility(View.VISIBLE);
            view.findViewById(R.id.fragment).setVisibility(View.GONE);
            view.findViewById(R.id.thetopview).setVisibility(View.VISIBLE);
            if (currentClassModel == null) {//第一次进入班级主页
                currentClassModel = ((UserClassListModel) classList.get(0));
            } else {
                for (int i = 0; i < classList.size(); i++) {
                    UserClassListModel item = ((UserClassListModel) classList.get(i));
                    if (item.getClassId().equals(currentClassModel.getClassId())) {
                        currentClassModel = item;
                        tag = true;
                        break;
                    }
                }
            }

            if (!tag) {//原来的班级不存在时，重新定位到第一个班级
                currentClassModel = ((UserClassListModel) classList.get(0));
            }
            title = currentClassModel.getCName();
            Log.e("getGradeName", title);
            classId = currentClassModel.getClassId();
            initHeadViews();
            getClassInfor(loadingType);
        } else {
            //没有班级，显示加入班级或者创建班级的页面
            if (mListView.getVisibility() == View.VISIBLE) {
                mListView.setVisibility(View.GONE);
                mRefreshLayout.setVisibility(View.GONE);
                view.findViewById(R.id.fragment).setVisibility(View.VISIBLE);
                view.findViewById(R.id.thetopview).setVisibility(View.GONE);
            }
        }
    }


    //实时更新本地默认班级数据
    private void saveDefaultSyncClassToLocal() {

        DataUtils.GetUserSyncClassList(getActivity(), true, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                //更新本地默认同步班级信息
                HiWorldCache.saveDataToDB(HiCache.getInstance().getLoginUserInfo().getUserId(), result, HistudentUrl.GetUserSyncClassList);
            }

            @Override
            public void onFailure(String errorMsg) {
            }
        }, LoadingType.NONE);
    }

    //获取班级详情数据
    private void getClassInfor(LoadingType isShow) {
        ClassHelper.getClassInfo(((BaseActivity) getActivity()), classId, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                classModel = JSON.parseObject(result, ClassModel.class);
                HiCache.getInstance().setClassDetailInfo(classModel);
                if (classModel != null) {

                    mRefreshLayout.finishRefresh();
                    UpdateUI();
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                mRefreshLayout.finishRefresh();
            }
        }, isShow);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mListView.getRefreshableView().smoothScrollTo(0, 0);

        /**
         * 返回刷新
         */
        if (resultCode == 200) {//加入班级
            getClasses(LoadingType.NONE);
            saveDefaultSyncClassToLocal();
        } else if (requestCode == CodeNum.CLASS_SET && resultCode == CodeNum.CLASS_DISSOLVE_RESULT) {//解散班级
            getClasses(LoadingType.NONE);
            saveDefaultSyncClassToLocal();
        } else if (requestCode == CodeNum.CLASS_SET && resultCode == CodeNum.CLASS_EXIT_RESULT) {//退出班级
            getClasses(LoadingType.NONE);
            saveDefaultSyncClassToLocal();
        } else if (requestCode == 100 && resultCode == 0) {//创建班级
            getClasses(LoadingType.NONE);
            saveDefaultSyncClassToLocal();
        } else if (requestCode == CodeNum.CLASS_TRANSFER_REQUEST
                && resultCode == CodeNum.CLASS_TRANSFER_RESULT) {//转让班级
            getClasses(LoadingType.NONE);
            saveDefaultSyncClassToLocal();
        } else if (requestCode == CodeNum.CLASS_SET
                && resultCode == 0) {//班级信息改变
            getClasses(LoadingType.NONE);
            saveDefaultSyncClassToLocal();
        } else if (requestCode == CodeNum.GROUP_TASK) {
            getClasses(LoadingType.NONE);
        }

    }

    @Subscribe(sticky = true)
    public void onEvent(MessageUpdateEvent event) {
        NIMClient.getService(MsgService.class).queryRecentContacts().setCallback(requestCallbackWrapper);
    }


    @Subscribe(sticky = true)
    public void onEvent(ReadClockMessageEvent readClockMessageEvent) {
        //当有阅读打卡消息时  调用
        mReadClockMessageCount = readClockMessageEvent.getCount();
        //更新当前班级信息
        for (Object object : classList) {
            final UserClassListModel userClassListMode = (UserClassListModel) object;
            final String classId = userClassListMode.getClassId();
            final String currentClassId = currentClassModel.getClassId();
            if (classId.equals(currentClassId)) {
                userClassListMode.setImNum(imNumber + mReadClockMessageCount);
            }
        }
        classAdapter.notifyDataSetChanged();
    }

    @Subscribe(sticky = true)
    public void onEvent(IMMessageEvent event) {
        IMMessage message = event.message;
        if (classModel == null) {
            return;
        }
        if (message.getSessionType() == SessionTypeEnum.Team && message.getSessionId().equals(classModel.getChatGroupKey())) {
            imNumber++;
        }
        for (Object object : classList) {
            UserClassListModel userClassListModel = (UserClassListModel) object;
            if (message.getSessionType() == SessionTypeEnum.Team && message.getSessionId().equals(userClassListModel.getChatGroupKey())) {
                userClassListModel.setImNum(userClassListModel.getImNum() + 1);
            }
        }

        classModel.setImNum(imNumber);
        classAdapter.notifyDataSetChanged();
        appAdapter.notifyDataSetChanged();
    }

//    @Subscribe(sticky = true)
//    public void onEventIM(RecentContactEvent event) {
//        List<RecentContact> recents = event.recents;
//        if (recents == null) {
//            return;
//        }
//        recentContacts.addAll(recents);
//        imNumber = 0;
//
//        for (RecentContact recentContact : recents) {
//            if (recentContact.getSessionType() == SessionTypeEnum.Team && recentContact.getContactId().equals(classModel.getChatGroupKey())) {
//                imNumber = imNumber + recentContact.getUnreadCount();
//            }
//            for (Object object : classList) {
//                UserClassListModel userClassListModel = (UserClassListModel) object;
//                if (recentContact.getSessionType() == SessionTypeEnum.Team && recentContact.getContactId().equals(userClassListModel.getChatGroupKey())) {
//                    userClassListModel.setImNum(recentContact.getUnreadCount());
//                }
//            }
//        }
//        classModel.setImNum(imNumber);
//        classAdapter.notifyDataSetChanged();
//        appAdapter.notifyDataSetChanged();
//    }

}
