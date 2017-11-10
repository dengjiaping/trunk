package com.histudent.jwsoft.histudent.activity.homework;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.activity.image.ShowImageActivity;
import com.histudent.jwsoft.histudent.adapter.VideoAdapter;
import com.histudent.jwsoft.histudent.adapter.work.VideoDetailAdapter;
import com.histudent.jwsoft.histudent.adapter.work.WorkCompleteAdapter;
import com.histudent.jwsoft.histudent.adapter.work.WorkNoCompleteAdapter;
import com.histudent.jwsoft.histudent.base.BaseActivity;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkDetailBean;
import com.histudent.jwsoft.histudent.bean.homework.VideoDetailBean;
import com.histudent.jwsoft.histudent.bean.homework.WorkCompleteBean;
import com.histudent.jwsoft.histudent.bean.homework.WorkImgDetailBean;
import com.histudent.jwsoft.histudent.body.hiworld.activity.WatchActionVideoActivity;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.keyword.utils.DisplayUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.commen.view.popupwindow.TopMenuPopupWindow;
import com.histudent.jwsoft.histudent.comment2.utils.EmotionUtils;
import com.histudent.jwsoft.histudent.comment2.utils.TimeUtils;
import com.histudent.jwsoft.histudent.constant.TransferKeys;
import com.histudent.jwsoft.histudent.entity.AudioInfo;
import com.histudent.jwsoft.histudent.entity.AudioPlayEvent;
import com.histudent.jwsoft.histudent.entity.AudioPlayStatusEvent;
import com.histudent.jwsoft.histudent.entity.CorrectWorkEvent;
import com.histudent.jwsoft.histudent.entity.ImageAttrEntity;
import com.histudent.jwsoft.histudent.entity.WorkNoticeEvent;
import com.histudent.jwsoft.histudent.entity.WorkVideoEvent;
import com.histudent.jwsoft.histudent.fragment.work.WorkCompleteFragment;
import com.histudent.jwsoft.histudent.fragment.work.WorkNoCompleteFragment;
import com.histudent.jwsoft.histudent.presenter.homework.WorkDetailTeacherPresenter;
import com.histudent.jwsoft.histudent.presenter.homework.contract.WorkDetailTeacherContract;
import com.histudent.jwsoft.histudent.tool.ToastTool;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2017/10/25.
 */

public class WorkDetailTeacherActivity extends BaseActivity<WorkDetailTeacherPresenter> implements WorkDetailTeacherContract.View {

    @BindView(R.id.title_middle_text)
    TextView mTitle;
    @BindView(R.id.work_detail_img)
    ImageView mHeadImg;
    @BindView(R.id.work_detail_subject)
    TextView mHeadSubject;
    @BindView(R.id.work_detail_create_name)
    TextView mCreateName;
    @BindView(R.id.work_detail_create_time)
    TextView mCreateTime;
    @BindView(R.id.work_detail_content)
    TextView mWorkContent;
    @BindView(R.id.work_detail_recycler_video)
    RecyclerView mRecyclerVideo;
    @BindView(R.id.work_detail_photo)
    ImageView mDetailPhoto;
    @BindView(R.id.work_detail_photo_num)
    TextView mPhotoNum;
    @BindView(R.id.notices_voice)
    FrameLayout mVoiceLayout;
    @BindView(R.id.notices_voice_control)
    IconView mVoiceControl;
    @BindView(R.id.notices_voice_time)
    TextView mVoiceTime;
    @BindView(R.id.notices_voice_time_total)
    TextView mVoiceTimeTotal;
    @BindView(R.id.notices_voice_progress)
    SeekBar mProgress;
    @BindView(R.id.delete_voice)
    IconView mVoiceDel;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;
    @BindView(R.id.work_detail_imgs)
    FrameLayout mImgLayout;
    @BindView(R.id.ll_content)
    LinearLayout mContentLayout;
    @BindView(R.id.detail_expansion)
    IconView mExpansion;
    @BindView(R.id.title_right_image)
    IconView mIvTitleRightIcon;
    @BindView(R.id.complete_recycler)
    RecyclerView mCompleteRecycler;
    @BindView(R.id.no_complete_recycler)
    RecyclerView mNoCompleteRecycler;

    private boolean isBelongToCurrentTeacher;
    private TopMenuPopupWindow mMenuPopupWindow;

    @OnClick({R.id.title_left, R.id.notices_voice_control, R.id.work_detail_photo, R.id.detail_expansion, R.id.title_right_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.notices_voice_control://语音播放控制
                if (mPresenter.getAudioState()) {
                    mVoiceControl.setText(getResources().getString(R.string.icon_bofang));
                    mPresenter.pauseAudio();
                } else {
                    if (mAudioInfo.getFile() != null) {
                        mVoiceControl.setText(getResources().getString(R.string.icon_zanting));
                        mPresenter.playAudio(mAudioInfo.getFile().getAbsolutePath());
                    }

                }
                break;
            case R.id.work_detail_photo:
                if (imageAttrs != null && imageAttrs.size() > 0) {
                    showImageDetail(mDetailPhoto, 0, imageAttrs);
                }
                break;
            case R.id.detail_expansion:
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mContentLayout.getLayoutParams();
                params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                mContentLayout.setLayoutParams(params);
                mExpansion.setVisibility(View.GONE);
                break;
            case R.id.title_right_image:
                solveHomeworkOperation();
                break;
            default:
                break;
        }
    }

    private List<String> titles = new ArrayList<>();
    private String homeworkId;
    private VideoDetailAdapter mVideoAdapter;
    private LinearLayoutManager mVideoLayoutManager;
    private GridLayoutManager mImgLayoutManager;
    private AudioInfo mAudioInfo = new AudioInfo();
    private List<ImageAttrEntity> imageAttrs = new ArrayList();
    private String voiceId;
    private long voiceTime;
    private Handler mHandler = new Handler();
    private static final int PAGE_SIZE = 60;
    private WorkCompleteAdapter mCompleteAdapter;
    private LinearLayoutManager mCompleteLayoutManager;
    private WorkNoCompleteAdapter mNoCompleteAdapter;
    private LinearLayoutManager mNoCompleteLayoutManager;
    private static final int REQ_CORRECT = 2000;
    private String noticeUserId;
    private boolean isOnline;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_work_detail_teacher;
    }

    @Override
    protected void init() {
        initIntent();
        initView();
        initData();
    }

    private void initData() {
        mPresenter.getHomeworkDetail(homeworkId);
        mPresenter.getCompleteList(homeworkId, true, 0, PAGE_SIZE);
        mPresenter.getCompleteList(homeworkId, false, 0, PAGE_SIZE);
    }

    private void initIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            homeworkId = intent.getStringExtra("homeworkId");
            isBelongToCurrentTeacher = intent.getBooleanExtra(TransferKeys.IS_BELONG_TO_CURRENT_TEACHER, true);
        }
    }

    private void initView() {
        initTitle();
        initRecycler();
        initTabLayout();
        initRefresh();
        initProgress();
    }

    private void initProgress() {
        mProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mVoiceControl.setText(getResources().getString(R.string.icon_zanting));
                double position = seekBar.getProgress() / 100.0;
                mPresenter.playAudio(mAudioInfo.getFile().getAbsolutePath(), (int) (mAudioInfo.getTime() * 1000 * position));
            }
        });
    }

    private void initRefresh() {
        mRefresh.setEnableLoadmore(false);
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPresenter.getCompleteList(homeworkId, true, 0, PAGE_SIZE);
                mPresenter.getCompleteList(homeworkId, false, 0, PAGE_SIZE);
            }
        });
    }


    private void initRecycler() {
        mVideoAdapter = new VideoDetailAdapter(this);
        mVideoLayoutManager = new LinearLayoutManager(this);
        mRecyclerVideo.setAdapter(mVideoAdapter);
        mRecyclerVideo.setLayoutManager(mVideoLayoutManager);

        mCompleteAdapter = new WorkCompleteAdapter(this);
        mCompleteLayoutManager = new LinearLayoutManager(this);
        mCompleteRecycler.setLayoutManager(mCompleteLayoutManager);
        mCompleteRecycler.setAdapter(mCompleteAdapter);
        mCompleteRecycler.setVisibility(View.VISIBLE);
        mNoCompleteAdapter = new WorkNoCompleteAdapter(this);
        mNoCompleteLayoutManager = new LinearLayoutManager(this);
        mNoCompleteRecycler.setLayoutManager(mNoCompleteLayoutManager);
        mNoCompleteRecycler.setAdapter(mNoCompleteAdapter);
        mNoCompleteRecycler.setVisibility(View.GONE);
    }

    private void initTitle() {
        mTitle.setText("作业详情");
        mIvTitleRightIcon.setText(R.string.icon_more);
        if (isBelongToCurrentTeacher) {
            //当前的作业是属于登录的老师所发布的
            mIvTitleRightIcon.setVisibility(View.VISIBLE);
        } else {
            mIvTitleRightIcon.setVisibility(View.GONE);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initTabLayout() {
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.addTab(mTabLayout.newTab());
        setTabLine(50, 50);
        setTabOne(0);
        setTabTwo(1);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        mCompleteRecycler.setVisibility(View.VISIBLE);
                        mNoCompleteRecycler.setVisibility(View.GONE);
                        break;
                    case 1:
                        mCompleteRecycler.setVisibility(View.GONE);
                        mNoCompleteRecycler.setVisibility(View.VISIBLE);
                        break;
                }
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

    public void setTabOne(int num) {
        mTabLayout.getTabAt(0).setText("已完成(" + num + ")");
    }

    public void setTabTwo(int num) {
        mTabLayout.getTabAt(1).setText("未完成(" + num + ")");
    }

    @Override
    public void showHomeworkDetail(HomeworkDetailBean homeworkDetail) {
        initHeader(homeworkDetail);
        initContent(homeworkDetail);
        changeLayout();
    }

    private void changeLayout() {
        mContentLayout.post(new Runnable() {
            @Override
            public void run() {
                int height = mContentLayout.getMeasuredHeight();
                if (height > DisplayUtils.dp2px(WorkDetailTeacherActivity.this, 120)) {
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mContentLayout.getLayoutParams();
                    params.height = DisplayUtils.dp2px(WorkDetailTeacherActivity.this, 120);
                    mContentLayout.setLayoutParams(params);
                    mExpansion.setVisibility(View.VISIBLE);
                } else {
                    mExpansion.setVisibility(View.GONE);
                }
            }
        });

    }

    private void initContent(HomeworkDetailBean homeworkDetail) {
        if (!TextUtils.isEmpty(homeworkDetail.getContents())) {
            mWorkContent.setText(EmotionUtils.convertNormalStringToSpannableString(homeworkDetail.getContents()));
        }
        if (homeworkDetail.isHasImage()) {
            List<WorkImgDetailBean> workImgDetailBeens = homeworkDetail.getImgList();
            if (workImgDetailBeens != null && workImgDetailBeens.size() > 0) {
                mImgLayout.setVisibility(View.VISIBLE);
                for (WorkImgDetailBean workImgDetailBean : workImgDetailBeens) {
                    ImageAttrEntity imageAttrEntity = new ImageAttrEntity();
                    imageAttrEntity.setId(workImgDetailBean.getId());
                    imageAttrEntity.setBigSizeUrl(workImgDetailBean.getThumbnailFilePath());
                    imageAttrEntity.setThumbnailUrl(workImgDetailBean.getFilePath());
                    imageAttrs.add(imageAttrEntity);
                }
                CommonGlideImageLoader.getInstance().displayNetImage(WorkDetailTeacherActivity.this, workImgDetailBeens.get(0).getThumbnailFilePath(), mDetailPhoto);
                mPhotoNum.setText(String.valueOf(workImgDetailBeens.size()) + "张");
            } else {
                mImgLayout.setVisibility(View.GONE);
            }
        } else {
            mImgLayout.setVisibility(View.GONE);
        }

        if (homeworkDetail.isHasVideo()) {
            mRecyclerVideo.setVisibility(View.VISIBLE);
            List<VideoDetailBean> videoList = homeworkDetail.getVideoList();
            mVideoAdapter.setList(videoList);
        } else {
            mRecyclerVideo.setVisibility(View.GONE);
        }

        if (homeworkDetail.isHasVoice()) {
            mPresenter.downloadVoice(homeworkDetail.getVoiceId());
            mVoiceLayout.setVisibility(View.VISIBLE);
            mVoiceDel.setVisibility(View.GONE);
            voiceTime = homeworkDetail.getVoiceLength();
            mAudioInfo.setTime(voiceTime);
            mVoiceTimeTotal.setText(TimeUtils.formatTime2(voiceTime));
        }
        isOnline = homeworkDetail.isOnlyOnline();
        mCompleteAdapter.setOnline(isOnline);
    }

    private void initHeader(HomeworkDetailBean homeworkDetail) {
        if (!TextUtils.isEmpty(homeworkDetail.getClassName())) {
            mHeadSubject.setText(homeworkDetail.getClassName());
        }
        if (!TextUtils.isEmpty(homeworkDetail.getOwnerName())) {
            mCreateName.setText(homeworkDetail.getOwnerName());
        }
        if (!TextUtils.isEmpty(homeworkDetail.getCreateTime())) {
            mCreateTime.setText(TimeUtils.exchangeTime(homeworkDetail.getCreateTime()));
        }
        CommonGlideImageLoader.getInstance().displayNetImage(this, homeworkDetail.getLogo(), mHeadImg);
    }

    @Override
    public void getHomeworkDetailFail() {
        showContent("获取详情失败");
        finish();
    }

    @Override
    public void showCompleteList(boolean isComplete, List<WorkCompleteBean.ItemsBean> itemsBeen) {
        mRefresh.finishRefresh();
        if (itemsBeen != null && itemsBeen.size() > 0) {
            if (isComplete) {
                mCompleteAdapter.setList(itemsBeen);
                setTabOne(itemsBeen.size());
            } else {
                mNoCompleteAdapter.setList(itemsBeen);
                setTabTwo(itemsBeen.size());
            }
        }
    }

    @Override
    public void downloadVoiceSuccess(File file) {
        mAudioInfo.setFile(file);
    }

    @Override
    public void controlDialogStatus(String message) {
        if (!TextUtils.isEmpty(message)) {
            ToastTool.showCommonToast(message);
        }
        dismissLoadingDialog();
    }

    @Override
    public void deleteHomeworkSuccess() {
        if (mMenuPopupWindow != null) {
            mMenuPopupWindow.dismiss();
        }
        ToastTool.showCommonToast(getString(R.string.homework_already_cancel));
        this.finish();
    }

    @Override
    public void getCompleteListFail() {

    }

    public void finishRefresh() {
        mRefresh.finishRefresh();
    }


    private void showImageDetail(View view, int position, List<ImageAttrEntity> imageAttrs) {
        Intent intent = new Intent(this, ShowImageActivity.class);
        Bundle bundle = new Bundle();
        int[] location = new int[2];
        view.getLocationInWindow(location);
        int x = location[0];
        int y = location[1];
        bundle.putInt("x", x);
        bundle.putInt("y", y);
        bundle.putInt("width", view.getWidth());
        bundle.putInt("height", view.getHeight());
        bundle.putBoolean("isOperate", true);
        bundle.putSerializable("photos", (Serializable) imageAttrs);
        bundle.putInt("position", position);
        bundle.putInt("column_num", 3);
        bundle.putInt("horizontal_space", DisplayUtils.dp2px(this, 4));
        bundle.putInt("vertical_space", DisplayUtils.dp2px(this, 4));
        bundle.putBoolean("isCb", false);
        intent.putExtras(bundle);
        this.startActivity(intent);
    }


    @Override
    public void showContent(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AudioPlayEvent event) {
        mProgress.setProgress((int) ((event.position / (float) (mAudioInfo.getTime() * 1000.0)) * 100));
        mVoiceTime.setText(TimeUtils.formatTime2(event.position / 1000));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AudioPlayStatusEvent event) {
        int status = event.status;
        switch (status) {
            case 0:
            case -1:
                mProgress.setProgress(100);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mProgress.setProgress(0);
                        mVoiceTime.setText("00:00");
                        mVoiceControl.setText(getResources().getString(R.string.icon_bofang));
                    }
                }, 500);
                break;
        }
    }

    @Subscribe
    public void onEvent(WorkVideoEvent workVideoEvent) {
        VideoDetailBean videoListBean = workVideoEvent.videoDetailBean;
        WatchActionVideoActivity.start(this, videoListBean.getAliVideoId());
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
        ReminderHelper.getIntentce().showTopMenuDialog(this, "", new View.OnClickListener() {
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

    @Subscribe
    public void onEvent(CorrectWorkEvent event) {
        WorkCompleteBean.ItemsBean itemsBean = event.itemsBean;
        Intent intent = new Intent();
        if (itemsBean != null) {
            if (isOnline) {
                intent.setClass(this, CorrectWorkActivity.class);
                intent.putExtra("completeInfo", itemsBean);
                startActivityForResult(intent, REQ_CORRECT);
            }
        }
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
        if (mPresenter != null) {
            mPresenter.stopAudio();
        }

    }

    /**
     * 处理右上角 作业情况
     */
    private void solveHomeworkOperation() {
        final List<String> textList = new ArrayList<>();
        final List<Integer> textColor = new ArrayList<>();
        textList.add(getString(R.string.cancel_homework));
        textColor.add(Color.rgb(51, 51, 51));
        textColor.add(Color.rgb(51, 51, 51));
        mMenuPopupWindow = new TopMenuPopupWindow(this, (View view) -> {
            switch (view.getId()) {
                case R.id.btn_01:
                    showLoadingDialog();
                    mPresenter.deleteHomeworkOperation(homeworkId);
                    break;
                default:
                    break;
            }
            mMenuPopupWindow.dismiss();
        }, textList, textColor, true);
        mMenuPopupWindow.showAtLocation(mIvTitleRightIcon, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CORRECT) {
            mPresenter.getCompleteList(homeworkId, true, 0, PAGE_SIZE);
            mPresenter.getCompleteList(homeworkId, false, 0, PAGE_SIZE);
        }
    }

}
