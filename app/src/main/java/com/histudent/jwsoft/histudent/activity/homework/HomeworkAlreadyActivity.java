package com.histudent.jwsoft.histudent.activity.homework;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.adapter.decoration.Divider;
import com.histudent.jwsoft.histudent.adapter.homework.HomeworkAlreadyAdapter;
import com.histudent.jwsoft.histudent.bean.PagingBean;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkAlreadyBean;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkAlreadySubBean;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.constant.Const;
import com.histudent.jwsoft.histudent.constant.TransferKeys;
import com.histudent.jwsoft.histudent.entity.WorkTypeEvent;
import com.histudent.jwsoft.histudent.refresh.RefreshHandler;
import com.histudent.jwsoft.histudent.tool.CommonAdvanceUtils;
import com.histudent.jwsoft.histudent.widget.popupwindow.PopupWindowPublishHomework;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lichaojie on 2017/10/24.
 * desc:
 * 已发布的作业 其中包括(学生、老师、及老师发布全部作业)
 */

public class HomeworkAlreadyActivity extends BaseActivity {

    @BindView(R.id.rl_teacher_publish_homework_self)
    RelativeLayout mRelativeLayout;

    @BindView(R.id.tv_teacher_publish_homework_self)
    TextView mTvTeacherPublishHomeworkSelf;
    @BindView(R.id.tv_teacher_publish_homework_all)
    TextView mTvTeacherPublishHomeworkAll;

    @BindView(R.id.view_teacher_publish_homework_self_line)
    View mTeacherPublishHomeworkSelfLine;
    @BindView(R.id.view_teacher_publish_homework_all_line)
    View mTeacherPublishHomeworkAllLine;

    @BindView(R.id.title_middle_text)
    TextView mTvTitleMiddleText;
    @BindView(R.id.title_right_text)
    TextView mTvTitleRightText;

    @BindView(R.id.srl_refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.rv_homework)
    RecyclerView mHomeworkRecyclerView;

    @BindView(R.id.ll_teacher_layout)
    LinearLayout mLLTeacherLayout;

    /**
     * 用户类型
     * 0:All  1:Student  2:Genearch   3:Teacher
     */
    public int mType = 1;
    private View mEmptyView;
    private View mFootView;
    private RefreshHandler mRefreshHandler;
    private HomeworkAlreadyAdapter mAdapter;

    @OnClick(R.id.title_middle_text)
    void testDivideGroup() {
        final Intent intent = new Intent(this, HomeworkSelectReceiverPersonActivity.class);
        CommonAdvanceUtils.startActivity(this, intent);
//        final Intent intent = new Intent(this, HomeworkSubjectManageActivity.class);
//        CommonAdvanceUtils.startActivity(this, intent);
    }

    @OnClick({R.id.title_right_text, R.id.title_left, R.id.rl_teacher_publish_homework_self, R.id.rl_teacher_publish_homework_all})
    void onClickView(View view) {
        switch (view.getId()) {
            case R.id.title_right_text:
                final PopupWindowPublishHomework popupWindowPublishHomework = PopupWindowPublishHomework.create(this);
                popupWindowPublishHomework.showMoreWindow(mRelativeLayout);
                break;
            case R.id.title_left:
                this.finish();
                break;
            case R.id.rl_teacher_publish_homework_self:
                if (mType == 3)
                    return;
                mType = 3;
                mRefreshHandler.setLoadFlower(true);
                refreshUi(0);
                mAdapter.setType(mType);
                mRefreshHandler.clearData();
                mRefreshHandler.requestData();
                break;
            case R.id.rl_teacher_publish_homework_all:
                if (mType == 0)
                    return;
                mType = 0;
                mRefreshHandler.setLoadFlower(true);
                refreshUi(1);
                mAdapter.setType(mType);
                mRefreshHandler.clearData();
                mRefreshHandler.requestData();
                break;
            default:
                break;
        }
    }

    private final List<HomeworkAlreadyBean> mHomeworkAlreadyBeanList = new ArrayList<>();


    /**
     * 老师
     * 0:我发布的
     * 1:全部
     *
     * @param i
     */
    private void refreshUi(int i) {
        if (i == 0) {
            mTvTeacherPublishHomeworkSelf.setTextColor(ContextCompat.getColor(this, R.color._333333));
            mTeacherPublishHomeworkSelfLine.setVisibility(View.VISIBLE);

            mTvTeacherPublishHomeworkAll.setTextColor(ContextCompat.getColor(this, R.color._bbbbbb));
            mTeacherPublishHomeworkAllLine.setVisibility(View.GONE);
        } else if (i == 1) {
            mTvTeacherPublishHomeworkAll.setTextColor(ContextCompat.getColor(this, R.color._333333));
            mTeacherPublishHomeworkAllLine.setVisibility(View.VISIBLE);

            mTvTeacherPublishHomeworkSelf.setTextColor(ContextCompat.getColor(this, R.color._bbbbbb));
            mTeacherPublishHomeworkSelfLine.setVisibility(View.GONE);
        }
    }


    @Override
    public int setViewLayout() {
        return R.layout.activity_homework_already_publish;
    }

    @Override
    public void initView() {
        mTvTitleMiddleText.setText(R.string.homework);
        mTvTitleRightText.setText("我要发布");
        mTvTitleRightText.setTextColor(ContextCompat.getColor(this, R.color._28ca7e));

        mFootView = LayoutInflater.from(this).inflate(R.layout.load_more_bottom_no_data, null);
        mEmptyView = LayoutInflater.from(this).inflate(R.layout.item_layout_empty, null);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        final Divider divider = new Divider(ContextCompat.getDrawable(this, R.drawable.divider_line), LinearLayoutManager.VERTICAL);
        divider.setMargin(SystemUtil.dp2px(12), 0, 0, 0);
        mHomeworkRecyclerView.setLayoutManager(linearLayoutManager);
        mHomeworkRecyclerView.addItemDecoration(divider);
        mHomeworkRecyclerView.addOnItemTouchListener(new ItemClickListener());
    }

    @Override
    public void doAction() {
        super.doAction();
        initOther();
        final boolean isAdmin = getIntent().getBooleanExtra(TransferKeys.IS_ADMIN, false);
        if (isAdmin) {
            //老师
            mType = 3;
        } else {
            mType = 1;
        }
        updateUi();
        mAdapter = HomeworkAlreadyAdapter
                .create(R.layout.item_homework_list_sub_content, R.layout.item_homework_list_title_time, mHomeworkAlreadyBeanList)
                .setType(mType);
        mRefreshHandler = RefreshHandler.create(this, mRefreshLayout, mHomeworkRecyclerView, mAdapter, new PagingBean());
        mRefreshHandler.requestData();
    }

    private class ItemClickListener extends SimpleClickListener {

        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            Intent intent = new Intent();
            intent.putExtra("homeworkId", mRefreshHandler.getList().get(position).t.getId());
            intent.putExtra("thumb",mRefreshHandler.getList().get(position).t.getThumb());
            intent.setClass(HomeworkAlreadyActivity.this, WorkDetailTeacherActivity.class);
            CommonAdvanceUtils.startActivity(HomeworkAlreadyActivity.this, intent);
        }

        @Override
        public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

        }

        @Override
        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

        }

        @Override
        public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

        }
    }


    private void updateUi() {
        if (mType == 3) {
            mLLTeacherLayout.setVisibility(View.VISIBLE);
            mTvTitleRightText.setVisibility(View.VISIBLE);
        } else {
            mLLTeacherLayout.setVisibility(View.GONE);
            mTvTitleRightText.setVisibility(View.GONE);
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
        CommonAdvanceUtils.startActivity(this, intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
