package com.histudent.jwsoft.histudent.activity.homework;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.adapter.decoration.Divider;
import com.histudent.jwsoft.histudent.adapter.homework.HomeworkAlreadyAdapter;
import com.histudent.jwsoft.histudent.base.BaseActivity;
import com.histudent.jwsoft.histudent.bean.PagingBean;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkAlreadyBean;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkAlreadySubBean;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.constant.Const;
import com.histudent.jwsoft.histudent.constant.ParamKeys;
import com.histudent.jwsoft.histudent.constant.TransferKeys;
import com.histudent.jwsoft.histudent.entity.WorkTypeEvent;
import com.histudent.jwsoft.histudent.presenter.homework.WorkAlreadyCompletePresenter;
import com.histudent.jwsoft.histudent.presenter.homework.contract.WorkAlreadyCompleteContract;
import com.histudent.jwsoft.histudent.refresh.RefreshHandler;
import com.histudent.jwsoft.histudent.tool.CommonAdvanceUtils;
import com.histudent.jwsoft.histudent.tool.ToastTool;
import com.histudent.jwsoft.histudent.widget.popupwindow.PopupWindowPublishHomework;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lichaojie on 2017/11/1.
 * desc:
 * 已发布的作业 其中包括(学生、老师、及老师发布全部作业)
 */

public class WorkAlreadyCompleteActivity extends BaseActivity<WorkAlreadyCompletePresenter> implements
        WorkAlreadyCompleteContract.View {

    @BindView(R.id.title_middle_text)
    TextView mTvTitleMiddleText;
    @BindView(R.id.title_right_text)
    TextView mTvTitleRightText;

    @BindView(R.id.srl_refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.view_teacher_line)
    View mTeacherLine;

    @BindView(R.id.rv_homework)
    RecyclerView mHomeworkRecyclerView;

    @BindView(R.id.tl_tab_layout)
    TabLayout mTabLayout;

    /**
     * 用户类型
     * 0:All  1:Student  2:Genearch   3:Teacher
     */
    public int mType = 1;
    private View mEmptyView;
    private View mFootView;
    private RefreshHandler mRefreshHandler;
    private HomeworkAlreadyAdapter mAdapter;
    private static final int REQ_CODE = 2000;

    @OnClick({R.id.title_right_text, R.id.title_left})
    void onClickView(View view) {
        switch (view.getId()) {
            case R.id.title_right_text:
                final PopupWindowPublishHomework popupWindowPublishHomework = PopupWindowPublishHomework.create(this);
                popupWindowPublishHomework.showMoreWindow(mTabLayout);
                break;
            case R.id.title_left:
                this.finish();
                break;
            default:
                break;
        }
        mAdapter.setType(mType);
    }

    private final List<HomeworkAlreadyBean> mHomeworkAlreadyBeanList = new ArrayList<>();

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_homework_already_publish;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void init() {
        initView();
        initData();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initView() {
        mTvTitleMiddleText.setText(R.string.homework);
        mTvTitleRightText.setText(R.string.i_ask_publish);
        mTvTitleRightText.setTextColor(ContextCompat.getColor(this, R.color._28ca7e));

        mFootView = LayoutInflater.from(this).inflate(R.layout.load_more_bottom_no_data, null);
        mEmptyView = LayoutInflater.from(this).inflate(R.layout.item_layout_empty, null);
        final ImageView imageView = mEmptyView.findViewById(R.id.iv_empty_resource);
        final TextView text = mEmptyView.findViewById(R.id.tv_empty_content);
        imageView.setImageResource(R.drawable.no_hot_tizi);
        text.setText(R.string.no_homework);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        final Divider divider = new Divider(ContextCompat.getDrawable(this, R.drawable.divider_line), LinearLayoutManager.VERTICAL);
        divider.setMargin(SystemUtil.dp2px(12), 0, 0, 0);
        mHomeworkRecyclerView.setLayoutManager(linearLayoutManager);
        mHomeworkRecyclerView.addItemDecoration(divider);
        mHomeworkRecyclerView.addOnItemTouchListener(new ItemClickListener());

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.addTab(mTabLayout.newTab().setText("我发布的"));
        mTabLayout.addTab(mTabLayout.newTab().setText("全部"));
        setTabLine(60, 60);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                final int position = tab.getPosition();
                if (position == 0) {
                    if (mType == 3)
                        return;
                    mType = 3;
                    mRefreshHandler
                            .setType(mType)
                            .setNeedLoadingDialog(false)
                            .setLoadMore(false)
                            .clearData()
                            .requestData();
                } else if (position == 1) {
                    if (mType == 0)
                        return;
                    mType = 0;
                    mRefreshHandler
                            .setType(mType)
                            .setNeedLoadingDialog(false)
                            .setLoadMore(false)
                            .clearData()
                            .requestData();
                }
                mAdapter.setType(mType);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setTabLine(int left, int right) {
        try {
            Class<?> tabLayout = mTabLayout.getClass();
            Field tabStrip = tabLayout.getDeclaredField("mTabStrip");
            tabStrip.setAccessible(true);
            LinearLayout ll_tab = (LinearLayout) tabStrip.get(mTabLayout);
            for (int i = 0; i < ll_tab.getChildCount(); i++) {
                View child = ll_tab.getChildAt(i);
                child.setPadding(0, 0, 0, 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                //修改两个tab的间距
                params.setMarginStart(SystemUtil.dip2px(this, left));
                params.setMarginEnd(SystemUtil.dip2px(this, right));
                child.setLayoutParams(params);
                child.invalidate();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    private void initData() {
        initOther();
        final boolean isAdmin = getIntent().getBooleanExtra(TransferKeys.IS_TEACHER, false);
        if (isAdmin) {
            //老师
            mType = 3;
            mTabLayout.setVisibility(View.VISIBLE);
            mTeacherLine.setVisibility(View.VISIBLE);
            mTvTitleRightText.setVisibility(View.VISIBLE);
        } else {
            mType = 1;
            mTabLayout.setVisibility(View.GONE);
            mTeacherLine.setVisibility(View.GONE);
            mTvTitleRightText.setVisibility(View.GONE);
        }
        mAdapter = HomeworkAlreadyAdapter
                .create(R.layout.item_homework_list_sub_content, R.layout.item_homework_list_title_time, mHomeworkAlreadyBeanList)
                .setType(mType);
        mRefreshHandler = RefreshHandler.create(this, mRefreshLayout, mHomeworkRecyclerView, mAdapter, new PagingBean(), mPresenter);
        mRefreshHandler.setType(mType);
        mRefreshHandler.requestData();
    }


    @Override
    public void showContent(String message) {
        if (!TextUtils.isEmpty(message))
            ToastTool.showCommonToast(message);
        dismissLoadingDialog();
        mRefreshHandler.completeOnRefresh();
    }

    @Override
    public void controlDialogStatus(String message) {
        if (!TextUtils.isEmpty(message))
            ToastTool.showCommonToast(message);
        mRefreshHandler.completeOnRefresh();
    }

    @Override
    public void updateListData(ArrayList<HomeworkAlreadyBean> convertEntity) {
        dismissLoadingDialog();
        mRefreshHandler.updateListData(convertEntity);
    }

    @Override
    public void deleteHomeworkSuccess() {
        //删除作业成功后重新刷新数据
        mRefreshHandler.clearData();
        mRefreshHandler.requestData();
    }

    private class ItemClickListener extends SimpleClickListener {

        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            final HomeworkAlreadyBean homeworkAlreadyBean = mRefreshHandler.getList().get(position);
            final boolean isHeader = homeworkAlreadyBean.isHeader;
            if (isHeader)
                return;

            Intent intent = new Intent();
            switch (mType) {
                case 3:
                case 0:
                    final boolean isBelongToCurrentTeacher = isBelongToCurrentTeacher(position);
                    intent.putExtra("homeworkId", homeworkAlreadyBean.t.getId());
                    intent.putExtra(TransferKeys.IS_BELONG_TO_CURRENT_TEACHER, isBelongToCurrentTeacher);
                    intent.setClass(WorkAlreadyCompleteActivity.this, WorkDetailTeacherActivity.class);
                    break;
                case 1:
                    HomeworkAlreadySubBean homeworkAlreadySubBean = mRefreshHandler.getList().get(position).t;
                    if (homeworkAlreadySubBean.isComplete()) {
                        if (homeworkAlreadySubBean.getOnlyOnline()) {
                            intent.putExtra("homeworkId", homeworkAlreadySubBean.getId());
                            intent.setClass(WorkAlreadyCompleteActivity.this, WorkDetailStudentActivity.class);
                        } else {
                            intent.putExtra("homeworkId", homeworkAlreadySubBean.getId());
                            intent.putExtra("online", homeworkAlreadySubBean.getOnlyOnline());
                            intent.putExtra("thumb", homeworkAlreadySubBean.getThumb());
                            intent.putExtra("isComplete", homeworkAlreadySubBean.isComplete());
                            intent.setClass(WorkAlreadyCompleteActivity.this, WorkUndoneActivity.class);
                        }
                    } else {
                        intent.putExtra("homeworkId", homeworkAlreadySubBean.getId());
                        intent.putExtra("online", homeworkAlreadySubBean.getOnlyOnline());
                        intent.putExtra("thumb", homeworkAlreadySubBean.getThumb());
                        intent.putExtra("isComplete", homeworkAlreadySubBean.isComplete());
                        intent.setClass(WorkAlreadyCompleteActivity.this, WorkUndoneActivity.class);
                    }
                    break;
            }
            CommonAdvanceUtils.startActivityForResult(WorkAlreadyCompleteActivity.this, intent, REQ_CODE);
        }

        @Override
        public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
            final HomeworkAlreadyBean homeworkAlreadyBean = mRefreshHandler.getList().get(position);
            final boolean isHeader = homeworkAlreadyBean.isHeader;
            if (isHeader)
                return;
            if (mType != 1) {
                //1.只有老师可以取消作业 2.当前老师不可以取消别的老师的作业
                final boolean isBelongToCurrentTeacher = isBelongToCurrentTeacher(position);
                if (!isBelongToCurrentTeacher)
                    return;
                ReminderHelper.getIntentce().showDialog(WorkAlreadyCompleteActivity.this, getString(R.string.hint_title), getString(R.string.cancle_homework), getString(R.string.cancel), () -> {
                }, getString(R.string.confirm), () -> {
                    //确认
                    ToastTool.showCommonToast(getString(R.string.homework_already_cancel));
                    final List<HomeworkAlreadyBean> listData = mRefreshHandler.getList();
                    final String id = listData.get(position).t.getId();
                    mPresenter.deleteCompleteSpecifiedHomework(id);
                });
            }

        }

        @Override
        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

        }

        @Override
        public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

        }
    }

    /**
     * 用户类型 (0 : All , 1 : Student , 2 : Genearch , 3 : Teacher )
     *
     * @return
     */
    public int getType() {
        return mType;
    }

    public View getFootView() {
        return mFootView;
    }

    public View getEmptyView() {
        return mEmptyView;
    }

    private void initOther() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Subscribe
    public void onEvent(WorkTypeEvent event) {
        Intent intent = new Intent();
        intent.setClass(this, CreateWorkActivity.class);
        switch (event.type) {
            case Const.WORK_TEXT:
                intent.putExtra("type", Const.WORK_TEXT);
                break;
            case Const.WORK_VEDIO:
                intent.putExtra("type", Const.WORK_VEDIO);
                break;
            case Const.WORK_VOICE:
                intent.putExtra("type", Const.WORK_VOICE);
                break;
            case Const.WORK_PHOTO:
                intent.putExtra("type", Const.WORK_PHOTO);
                break;
        }
        CommonAdvanceUtils.startActivityForResult(this, intent, REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE) {
            //刷新界面
            mRefreshHandler.clearData();
            mRefreshHandler.requestData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public List<HomeworkAlreadyBean> getListData() {
        return mRefreshHandler.getList();
    }

    public RefreshHandler getRefreshHandler() {
        return mRefreshHandler;
    }


    /**
     * 判断当前点击的作业条目是否是当前老师所发布的
     *
     * @param position
     * @return
     */
    private boolean isBelongToCurrentTeacher(int position) {
        final String realName = HiCache.getInstance().getLoginUserInfo().getRealName();
        final String publishOwner = mRefreshHandler.getList().get(position).t.getPublishOwner();
        if (!TextUtils.isEmpty(realName) && !TextUtils.isEmpty(publishOwner)) {
            if (!realName.equals(publishOwner)) {
                return false;
            }
        }
        return true;
    }
}
