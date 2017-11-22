package com.histudent.jwsoft.histudent.body.myclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.CodeNum;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.model.ClassModel;
import com.histudent.jwsoft.histudent.body.mine.activity.ActionBaseActivity;
import com.histudent.jwsoft.histudent.body.mine.adapter.ActionAdapter;
import com.histudent.jwsoft.histudent.body.mine.model.UserTimeModel;
import com.histudent.jwsoft.histudent.body.mine.parser.DataParser;
import com.histudent.jwsoft.histudent.body.myclass.bean.ActivityStarsBean;
import com.histudent.jwsoft.histudent.body.myclass.helper.ClassHelper;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.bean.MyShareBean;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.enums.ReportType;
import com.histudent.jwsoft.histudent.commen.keyword.utils.DisplayUtils;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.ItemDataExchangeUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.UtilsStyle;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.commen.view.StarBar;
import com.histudent.jwsoft.histudent.commen.view.popupwindow.SharePopupWindow;
import com.histudent.jwsoft.histudent.commen.view.popupwindow.TopMenuPopupWindow;
import com.histudent.jwsoft.histudent.commen.view.swipemenulistview.AutoScrollImageView;
import com.histudent.jwsoft.histudent.comment2.activity.ReportActivity;
import com.histudent.jwsoft.histudent.info.persioninfo.activity.PersonCenterActivity;
import com.histudent.jwsoft.histudent.model.manage.ParamsManager;
import com.histudent.jwsoft.histudent.view.widget.AutoImageView;
import com.histudent.jwsoft.histudent.view.widget.popupwindow.PopupWindowDynamicCategory;
import com.histudent.jwsoft.histudent.view.widget.refresh.DefinedWeChatHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by liuguiyu-pc on 2017/4/24.
 * 班级圈
 */

public class ClassCircleActivity extends ActionBaseActivity {
    private TextView class_name, class_id, class_master, action_num, photo_num;
    private ActionAdapter mActionAdapter;
    private int timeCursor;
    private UserTimeModel model;
    private HiStudentHeadImageView class_log;
    private LinearLayout emblem_list, mStarLayout;
    private TopMenuPopupWindow menuWindow;
    private StarBar rating_bar;
    private LinearLayout mClassGradeLayout;
    private String classId;
    private IconView tubiao;
    private ArrayList<ActivityStarsBean> beanList = new ArrayList<>();
    private IconView mClassIdIcon;
    private IconView mClassMasterIcon;
    private TextView mTitle;

    private LinearLayout mEmptyLayout;
    private TextView mGrowthValue;
    private TextView mPlatformRank;
    private TextView mSchoolRank;

    private View mFootView;
    private AutoScrollImageView mScrollImageView;
    private String masterId;

    private boolean isLoadMore = false;

    @BindView(R.id.ptl_listView)
    PullToRefreshListView mPullToRefreshListView;
    @BindView(R.id.srl_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.ll_view_head)
    View mDynamicHoverLayout;
    @BindView(R.id.tv_hover_modify_title)
    AppCompatTextView mTvModifyTitle;

    /**
     * 存入当前过滤类型的数据
     */
    private final List<ActionListBean> mListDynamicCurrentData = new ArrayList<>();
    /**
     * 存放所有的数据
     */
    private final List<ActionListBean> mListDynamicAllData = new ArrayList<>();
    /**
     * 用户点击动态选择框
     */
    private PopupWindowDynamicCategory mPopupWindowDynamicCategory;

    /**
     * 动态type
     * 0:全部   1:随记  2:日志 3:照片 4:活动
     **/
    private int mCurrentType = -1;

    /**
     * 此为ListView头部高度 + ListView第一个条目(即动态分类标题高度45)
     * 当ListView 在Y 方向滑动距离大于 此值  即 固定分类动态标题
     */
    private int mShowDynamicHeight;
    /**
     * 无任何动态后  触发此view也可以进行动态过滤
     */
    private View mEmptyInnerDynamicLayout;
    private AppCompatTextView mTvEmptyInnerTitle;


    public static void start(Activity activity, String classId) {
        if (TextUtils.isEmpty(classId)) return;
        Intent intent = new Intent(activity, ClassCircleActivity.class);
        intent.putExtra("classId", classId);
        activity.startActivity(intent);
    }

    @Override
    public int setViewLayout() {
        /**
         * 全屏不重绘
         */
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        return R.layout.activity_class_circle;
    }

    @Override
    public void initView() {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.layout);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) relativeLayout.getLayoutParams();
        params.topMargin = DisplayUtils.getStatusHeight(this);
        relativeLayout.setLayoutParams(params);
        EventBus.getDefault().register(this);
        classId = getIntent().getStringExtra("classId");
        mCurrentType = 0;//默认显示全部状态
        mActionAdapter = ActionAdapter.create(this, mListDynamicAllData, ActionAdapter.CLASS_CIRCLE, true);
        View headView = LayoutInflater.from(this).inflate(R.layout.activity_class_circle_head, null);
        mPullToRefreshListView.getRefreshableView().addHeaderView(headView);
        mFootView = LayoutInflater.from(this).inflate(R.layout.load_more_bottom_no_data, null);
        class_log = headView.findViewById(R.id.class_log);
        mGrowthValue = headView.findViewById(R.id.growth_value);
        mPlatformRank = headView.findViewById(R.id.platform_rank);
        mSchoolRank = headView.findViewById(R.id.school_rank);
        class_name = headView.findViewById(R.id.class_name);
        class_id = headView.findViewById(R.id.class_id);
        class_master = headView.findViewById(R.id.class_master);
        action_num = (TextView) findViewById(R.id.action_num);
        photo_num = (TextView) findViewById(R.id.photo_num);
        emblem_list = headView.findViewById(R.id.emblem_list);
        mStarLayout = headView.findViewById(R.id.star_layout);
        rating_bar = headView.findViewById(R.id.rating_bar);
        mClassGradeLayout = headView.findViewById(R.id.tv_grouth_layout);
        mEmptyLayout = headView.findViewById(R.id.ll_empty_layout);
        mScrollImageView = headView.findViewById(R.id.iv_photo);
//        tubiao = headView.findViewById(R.id.tubiao);
        mClassIdIcon = headView.findViewById(R.id.class_id_icon);
        mClassMasterIcon = headView.findViewById(R.id.class_master_icon);
        mEmptyInnerDynamicLayout = headView.findViewById(R.id.view_empty_dynamic_layout);
        mTvEmptyInnerTitle = headView.findViewById(R.id.tv_hover_modify_title);


        //获取头部高度 进行悬停控件判断
        headView.post(() -> {
            int headViewHeight = headView.getHeight();
            mShowDynamicHeight = headViewHeight;
        });
        mTitle = (TextView) findViewById(R.id.title_middle_text);
        initRefresh();
    }

    /**
     * 用户是否点击的为加载为空数据时  emptyLayout上面的动态类型过滤按钮
     */
    private boolean isClickDynamicLayout = false;


    private void initRefresh() {
        mRefreshLayout.setEnableAutoLoadmore(true);
        DefinedWeChatHeader chatHeader = new DefinedWeChatHeader(this);
        mRefreshLayout.setRefreshHeader(chatHeader);
        mRefreshLayout.setEnableHeaderTranslationContent(false);
        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.autoRefresh();
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);
        mPullToRefreshListView.setAdapter(mActionAdapter);
        mEmptyInnerDynamicLayout.setOnClickListener((View view) -> {
            //如果用户点击的是加载为空时view 上面的动态过滤框 获取点击时的
            isClickDynamicLayout = true;
            scrollAssignLocation();
        });

        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                isLoadMore = true;
                getClassAction();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                performOnRefresh();
            }
        });
    }


    private void performOnRefresh(){
        isLoadMore = false;
        mRefreshLayout.setEnableLoadmore(true);
        removeFootView();
        timeCursor = 0;
        isLoadMore = false;
        getClassInfo();
        getActiveStars();
        getClassAction();
    }


    private void removeFootView() {
        int footerViewsCount = mPullToRefreshListView.getRefreshableView().getFooterViewsCount();
        if (footerViewsCount > 0)
            mPullToRefreshListView.getRefreshableView().removeFooterView(mFootView);
    }

    /**
     * 显示动态过滤窗PopupWindow
     *
     * @param v
     */
    public void showDynamicPopupWindow(View v) {
        int height = 0;
        final boolean enableLoadMore = mRefreshLayout.isEnableLoadmore();
        if (!enableLoadMore) {
            height = calculateViewLocation(v);
        } else {
            scrollAssignLocation();
        }
        mPopupWindowDynamicCategory = PopupWindowDynamicCategory.create(this, mCurrentType, height);
        mPopupWindowDynamicCategory.showPopupWindow(v);
    }

    /**
     * 计算当前view x ,y坐标
     * 当前返回Y 主要计算纵向滑动距离
     *
     * @param view
     * @return
     */
    private int calculateViewLocation(View view) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        int y = location[1];
        return y;
    }


    /**
     * 动态弹窗出来后 滑动至指定的位置 即动态标题处
     */
    public void scrollAssignLocation() {
        if (mDynamicHoverLayout.getVisibility() == View.GONE) {
            mPullToRefreshListView.getRefreshableView().setSelection(2);
        }
    }


    @Override
    public void doAction() {
        super.doAction();
        String actionResult = HiCache.getInstance().getHttpDataInDB(HistudentUrl.classAction_url, classId);
        if (!TextUtils.isEmpty(actionResult)) {
            parserJsonData(actionResult);
        }
        String activeResult = HiCache.getInstance().getHttpDataInDB(HistudentUrl.activeStars_url, classId);
        if (!TextUtils.isEmpty(activeResult)) {
            displayActiveStars(activeResult);
        }
        String classInfoResult = HiCache.getInstance().getHttpDataInDB(HistudentUrl.getClassInfor, classId);
        if (!TextUtils.isEmpty(classInfoResult)) {
            displayClassInfo(classInfoResult);
        }
        loadListener();
    }

    private void displayClassInfo(String result) {
        ClassModel classModel = JSON.parseObject(result, ClassModel.class);
        if (classModel != null) {
            masterId = classModel.getClassUserId();
            action_num.setText(classModel.getActivitiesNum() + "个动态");
            photo_num.setText(classModel.getPhotoNum() + "张照片");
            setMask(classModel.getPlatformRanking());
            mGrowthValue.setText(String.valueOf(classModel.getClassGrothValue()));
            mSchoolRank.setText(String.valueOf(classModel.getSchoolRanking()));
            mPlatformRank.setText(String.valueOf(classModel.getPlatformRanking()));
            class_master.setText(classModel.getClassUserRealName());
            rating_bar.setOnClickListener((View view) -> {
                if (classModel.isIsMember()) {
                    ClassGradeActivity.start(ClassCircleActivity.this, classId, CodeNum.GROUP_TASK);
                } else {
                    OtherClassGradeActivity.start(ClassCircleActivity.this, classId);
                }
            });
            mClassGradeLayout.setOnClickListener((View view) -> {
                if (classModel.isIsMember()) {
                    ClassGradeActivity.start(ClassCircleActivity.this, classId, CodeNum.GROUP_TASK);
                } else {
                    OtherClassGradeActivity.start(ClassCircleActivity.this, classId);
                }
            });

            CommonGlideImageLoader.getInstance().displayNetImage(this, classModel.getClassLogo(), class_log);
            class_name.setText(classModel.getSchoolName() + classModel.getGradeName() + classModel.getCName());
            if (classModel.isIsMember()) {
                class_id.setText(classModel.getClassNum());
                mClassIdIcon.setVisibility(View.VISIBLE);
                class_id.setVisibility(View.VISIBLE);
                rating_bar.setClickable(true);
                mClassGradeLayout.setClickable(true);
                mTitle.setText(R.string.class_circle);
            } else {
                class_id.setVisibility(View.GONE);
                mClassIdIcon.setVisibility(View.GONE);
                rating_bar.setClickable(false);
                mClassGradeLayout.setClickable(false);
                mTitle.setText(R.string.class_main_page);
            }
        }

    }

    private void loadListener() {
        mStarLayout.setOnClickListener((View view) -> ActiveStarsActivity.start(ClassCircleActivity.this, beanList));

        //最外层停留县浮框
        mDynamicHoverLayout.setOnClickListener((View view) -> {
            mPullToRefreshListView.getRefreshableView().setSelection(2);
            mDynamicHoverLayout.setVisibility(View.GONE);
            showDynamicPopupWindow(view);
        });

        UtilsStyle.setOnScrollListener(mPullToRefreshListView.getRefreshableView(), new UtilsStyle.ScrollY() {
            @Override
            public void scrollY(int y) {
                if (y > mShowDynamicHeight) {
                    mDynamicHoverLayout.setVisibility(View.VISIBLE);
                } else {
                    mDynamicHoverLayout.setVisibility(View.GONE);
                }

                //当加载为空时  此时点击空view上的动态过滤按钮 滑动后重新弹出PopupWindow
                if (isClickDynamicLayout) {
                    isClickDynamicLayout = false;
                    if (mEmptyLayout.getVisibility() == View.VISIBLE) {
                        showDynamicPopupWindow(mEmptyLayout);
                        return;
                    }
                }

                //如果用户点击是ListView上的动态过滤按钮
                if (mActionAdapter.getTopDynamicStatus()) {
                    mActionAdapter.setOnClickTopDynamicStatus(false);
                    final View view = mPullToRefreshListView.getRefreshableView().getChildAt(1);
                    showDynamicPopupWindow(view);
                }
            }
        });
    }

    private static final String TAG = "ClassCircleActivity";


    /**
     * 用户在PopupWindow选择过滤类型时 改变此时状态
     * 并刷新数据
     *
     * @param type
     */
    public void changeUserSelectDynamicType(int type) {
        this.mCurrentType = type;
        mActionAdapter.setCurrentType(mCurrentType);
        //改变当前类型并重新获取数据
        isLoadMore = false;
        timeCursor = 0;
        resetRefreshBeforeStatus();
        refreshDynamicTitle(type);
        getClassAction();
    }


    /**
     * 重置刷新前状态
     */
    private void resetRefreshBeforeStatus() {
        mRefreshLayout.setEnableLoadmore(true);
        removeFootView();
    }


    /**
     * 刷新当前用户所选动态类型的数据标题
     */
    public void refreshDynamicTitle(int type) {
        this.mCurrentType = type;
        final String context = getTextBySelectType(type);
        mTvModifyTitle.setText(context);
        mTvEmptyInnerTitle.setText(context);
    }

    /**
     * 根据用户所选择的动态类型去获取相应标题
     *
     * @param type
     * @return
     */
    public String getTextBySelectType(int type) {
        String text = "";
        switch (type) {
            case 0:
                text = getString(R.string.dynamic_all);
                break;
            case 1:
                text = getString(R.string.dynamic_essay);
                break;
            case 2:
                text = getString(R.string.dynamic_log);
                break;
            case 3:
                text = getString(R.string.dynamic_photo);
                break;
            case 4:
                text = getString(R.string.dynamic_activity);
                break;
            default:
                break;
        }
        return text;
    }


    /**
     * 处理动态数据
     *
     * @param type
     */
    public void solveDynamicBusinessLogic(int type) {
        this.mCurrentType = type;
        //滑动指定位置
        scrollAssignLocation();
        controlEmptyStatus(mListDynamicCurrentData);
        setDifferentListData();
        mActionAdapter.notifyDataSetChanged();
    }


    /**
     * 根据当前过滤的数据去更新adapter中数据
     */
    private void setDifferentListData() {
        if (mEmptyLayout.getVisibility() == View.VISIBLE) {
            mActionAdapter.setListData(mListDynamicCurrentData, mCurrentType, false);
        } else {
            mActionAdapter.setListData(mListDynamicCurrentData, mCurrentType, true);
        }
    }

    /**
     * 判断当前空view显示状态
     *
     * @param mListData
     */
    private void controlEmptyStatus(List<ActionListBean> mListData) {
        if (mListData.size() == 0) {
            mEmptyLayout.setVisibility(View.VISIBLE);
        } else {
            mEmptyLayout.setVisibility(View.GONE);
        }
    }


    private void setMask(int rank) {
        if (rank > 0 && rank <= 1000) {
            rating_bar.setStarMark(5);
        } else if (1001 < rank && rank <= 3000) {
            rating_bar.setStarMark(4);
        } else if (3001 < rank && rank <= 5000) {
            rating_bar.setStarMark(3);
        } else if (5001 < rank && rank <= 10000) {
            rating_bar.setStarMark(2);
        } else if (rank == 0) {
            rating_bar.setStarMark(0);
        } else {
            rating_bar.setStarMark(1);
        }

    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.title_left://返回
                finish();
                break;

            case R.id.title_right://菜单
                showClassMenu(this);
                break;


            case R.id.emblem_gomore://活跃之星
                ActiveStarsActivity.start(ClassCircleActivity.this, beanList);
                break;
            case R.id.class_master:
                PersonCenterActivity.start(ClassCircleActivity.this, masterId);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(ActionAdapter.Refresh refresh) {
        timeCursor = 0;
        mListDynamicAllData.clear();
        getClassAction();
    }

    /**
     * 获取班级基础信息
     */
    private void getClassInfo() {
        ClassHelper.getClassInfo(this, classId, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                displayClassInfo(result);
                HiCache.getInstance().saveHttpDataIntoDB(HistudentUrl.getClassInfor, classId, result);
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        }, LoadingType.NONE);
    }

    /**
     * 获取班级动态(未屏蔽)
     */
    private void getClassAction() {
        final Map<String, Object> map = ParamsManager.getInstance()
                .setParams("timeCursor", timeCursor)
                .setParams("pageSize", 8)
                .setParams("classId", classId)
                .getParamsMap();
        if (mCurrentType != 0) {
            //如果当前有选择的类型
            map.put("activityItemKey", mCurrentType);
        }
        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.classAction_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                mRefreshLayout.finishLoadmore();
                mRefreshLayout.finishRefresh();
                parserJsonData(result);
                HiCache.getInstance().saveHttpDataIntoDB(HistudentUrl.classAction_url, classId, result);
            }

            @Override
            public void onFailure(String errorMsg) {
                mRefreshLayout.finishLoadmore();
                mRefreshLayout.finishRefresh();
            }
        }, LoadingType.NONE);
    }


    /**
     * 处理返回过来的数据
     *
     * @param result
     */
    private void parserJsonData(String result) {
        model = DataParser.getUserTimeModel(result);
        if (!isLoadMore) {
            mListDynamicAllData.clear();
            mListDynamicCurrentData.clear();
        }
        if (model != null) {
            List<ActionListBean> listData = ItemDataExchangeUtils.dataExchange(model.getItems());
            if (listData.size() > 0) {
                timeCursor = model.getCursor();
                final int size = listData.size();
                if (size > 0 && size < 8) {
                    mPullToRefreshListView.getRefreshableView().addFooterView(mFootView);
                    mRefreshLayout.setEnableLoadmore(false);
                }
            } else {
                if (timeCursor != 0) {
                    mPullToRefreshListView.getRefreshableView().addFooterView(mFootView);
                    mRefreshLayout.setEnableLoadmore(false);
                } else {
                    if (mEmptyLayout.getVisibility() == View.VISIBLE) {
                        mRefreshLayout.setEnableLoadmore(false);
                        return;
                    }
                    mRefreshLayout.setEnableLoadmore(false);
                    mEmptyLayout.setVisibility(View.VISIBLE);
                }

            }

            mListDynamicAllData.addAll(listData);
            mListDynamicCurrentData.addAll(listData);
            mActionAdapter.setisShield(false);
            solveDynamicBusinessLogic(mCurrentType);
        }
    }


    /**
     * 获取活跃之星数据
     */
    private void getActiveStars() {
        final Map<String, Object> map = ParamsManager.getInstance()
                .setParams("topCount", 20)
                .setParams("classId", classId)
                .getParamsMap();
        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.activeStars_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                mRefreshLayout.finishLoadmore();
                mRefreshLayout.finishRefresh();
                displayActiveStars(result);
                HiCache.getInstance().saveHttpDataIntoDB(HistudentUrl.activeStars_url, classId, result);
            }

            @Override
            public void onFailure(String errorMsg) {
                mRefreshLayout.finishLoadmore();
                mRefreshLayout.finishRefresh();
            }
        }, LoadingType.NONE);
    }

    private void displayActiveStars(String result) {
        beanList.clear();
        beanList = (ArrayList<ActivityStarsBean>) JSON.parseArray(result, ActivityStarsBean.class);

        if (beanList != null && beanList.size() > 0) {
            mStarLayout.setVisibility(View.VISIBLE);
            emblem_list.removeAllViews();
            for (final ActivityStarsBean model : beanList) {
                View layout = LayoutInflater.from(ClassCircleActivity.this).inflate(R.layout.fragment_homepage_fashion_item, null);
                AutoImageView iv = layout.findViewById(R.id.fashion_head);
                TextView className = layout.findViewById(R.id.fashion_name);
                View isTeacherIdentity = layout.findViewById(R.id.iv_teacher_identity);
                int padding = (int) ((SystemUtil.getScreenWind() - 5.5 * SystemUtil.dp2px(60)) / 10);
                layout.setPadding(padding, SystemUtil.dp2px(8), padding, SystemUtil.dp2px(8));
                if (!TextUtils.isEmpty(model.getAvatar()))
                    CommonGlideImageLoader.getInstance().displayNetImageWithCircle(ClassCircleActivity.this, model.getAvatar(), iv);
                if (model.getUserType() == 3) {
                    //老师
                    isTeacherIdentity.setVisibility(View.VISIBLE);
                } else {
                    //学生
                    isTeacherIdentity.setVisibility(View.INVISIBLE);
                }
                className.setTextColor(getResources().getColor(R.color.text_black_h3));
                className.setText(model.getRealName());

                layout.setTag(model);
                layout.setOnClickListener((View view) -> PersonCenterActivity.start(ClassCircleActivity.this, model.getUserId()));
                iv.setOnClickListener((View view) -> PersonCenterActivity.start(ClassCircleActivity.this, model.getUserId()));
                emblem_list.addView(layout);
            }
        } else {
            mStarLayout.setVisibility(View.GONE);
        }
    }

    /**
     * 显示菜单
     *
     * @param activity
     */
    private void showClassMenu(final BaseActivity activity) {

        List<String> list_name = new ArrayList<>();
        List<Integer> list_color = new ArrayList<>();

        list_name.add(getString(R.string.shield));
        list_name.add(getString(R.string.share));
        list_name.add(getString(R.string.inform));
        list_color.add(Color.rgb(51, 51, 51));
        list_color.add(Color.rgb(51, 51, 51));
        list_color.add(Color.rgb(51, 51, 51));

        menuWindow = new TopMenuPopupWindow(activity, (View v) -> {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.btn_01:
                    ClassShieldActivity.start(this, classId);
                    break;
                case R.id.btn_02:
                    getShareData(ClassCircleActivity.this, classId);
                    break;

                case R.id.btn_03:
                    ReportActivity.start(ClassCircleActivity.this, classId, ReportType.OTHER);
                    break;

                default:
                    break;
            }
        }, list_name, list_color, false);
        menuWindow.showAtLocation(activity.findViewById(R.id.layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }

    /**
     * 获取班级圈分享数据并予以分享
     *
     * @param activityId
     */
    private void getShareData(final BaseActivity activity, final String activityId) {
        if (TextUtils.isEmpty(activityId)) return;
        final Map<String, Object> map = ParamsManager.getInstance()
                .setParams("objectId", activityId)
                .setParams("shareObjectType", 9)
                .getParamsMap();
        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.share_url_, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                MyShareBean shareBean = JSONObject.parseObject(result, MyShareBean.class);
                SharePopupWindow popupWindow = new SharePopupWindow(activity, shareBean);
                popupWindow.showAtLocation(activity.findViewById(R.id.layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }

            @Override
            public void onFailure(String msg) {

            }
        });

    }

    public void updateUIStatus() {
        final List<ActionListBean> listData = mActionAdapter.getListData();
        if (listData.size() == 0) {
            if (mEmptyLayout.getVisibility() == View.GONE)
                mEmptyLayout.setVisibility(View.VISIBLE);
        } else {
            mEmptyLayout.setVisibility(View.GONE);
        }
        performOnRefresh();
    }


    @Override
    protected void reFreshActionList(int position, ActionListBean bean) {
        mActionAdapter.setItem(position, bean);
    }

    @Override
    protected void reMoveAction(int position) {
        mActionAdapter.removeItem(position);
    }

}
