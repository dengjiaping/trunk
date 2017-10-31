package com.histudent.jwsoft.histudent.activity.homework;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.activity.image.ShowImageActivity;
import com.histudent.jwsoft.histudent.adapter.VideoAdapter;
import com.histudent.jwsoft.histudent.base.BaseActivity;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkDetailBean;
import com.histudent.jwsoft.histudent.bean.homework.WorkImgDetailBean;
import com.histudent.jwsoft.histudent.commen.keyword.utils.DisplayUtils;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.comment2.utils.TimeUtils;
import com.histudent.jwsoft.histudent.entity.AudioInfo;
import com.histudent.jwsoft.histudent.entity.AudioPlayEvent;
import com.histudent.jwsoft.histudent.entity.AudioPlayStatusEvent;
import com.histudent.jwsoft.histudent.entity.ImageAttrEntity;
import com.histudent.jwsoft.histudent.fragment.work.WorkCompleteFragment;
import com.histudent.jwsoft.histudent.fragment.work.WorkNoCompleteFragment;
import com.histudent.jwsoft.histudent.presenter.homework.WorkDetailTeacherPresenter;
import com.histudent.jwsoft.histudent.presenter.homework.contract.WorkDetailTeacherContract;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.Serializable;
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
    @BindView(R.id.title_right_image)
    TextView mRightImg;
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
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;

    @OnClick({R.id.title_left, R.id.title_right_image, R.id.notices_voice_control,R.id.work_detail_photo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.title_right_image:
                break;
            case R.id.notices_voice_control://语音播放控制
                if (mPresenter.getAudioState()) {
                    mVoiceControl.setText(getResources().getString(R.string.icon_bofang));
                    mPresenter.pauseAudio();
                } else {
                    if (mAudioInfo != null) {
                        mVoiceControl.setText(getResources().getString(R.string.icon_zanting));
                        mPresenter.playAudio(mAudioInfo.getFile().getAbsolutePath());
                    }

                }
                break;
            case R.id.work_detail_photo:

                break;
        }
    }


    private WorkCompleteFragment mCompleteFragment;
    private WorkNoCompleteFragment mNoCompleteFragment;
    private List<Fragment> mFragments = new ArrayList<>();
    private ViewPagerAdapter mAdapter;
    private static final String[] titles = {"已完成(0)", "未完成(0)"};
    private String homeworkId;
    private VideoAdapter mVideoAdapter;
    private LinearLayoutManager mVideoLayoutManager;
    private GridLayoutManager mImgLayoutManager;
    private String thumb;
    private AudioInfo mAudioInfo = new AudioInfo();

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
    }

    private void initIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            homeworkId = intent.getStringExtra("homeworkId");
            thumb = intent.getStringExtra("thumb");
        }
    }

    private void initView() {
        initTitle();
        initRecycler();
        initFragment();
        initViewPager();
        initTabLayout();
        initRefresh();
    }

    private void initRefresh() {
        mRefresh.setEnableLoadmore(false);
        mRefresh.autoRefresh();
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mCompleteFragment.initData();
                mNoCompleteFragment.initData();
            }
        });
    }

    private void initRecycler() {
        mVideoAdapter = new VideoAdapter(this);
        mVideoLayoutManager = new LinearLayoutManager(this);
        mRecyclerVideo.setAdapter(mVideoAdapter);
        mRecyclerVideo.setLayoutManager(mVideoLayoutManager);
    }

    private void initTitle() {
        mTitle.setText("作业详情");
        mRightImg.setText(R.string.icon_more3);
    }

    private void initFragment() {
        mCompleteFragment = WorkCompleteFragment.newInstance(homeworkId);
        mNoCompleteFragment = WorkNoCompleteFragment.newInstance(homeworkId);
        mFragments.add(mCompleteFragment);
        mFragments.add(mNoCompleteFragment);
    }

    private void initViewPager() {
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
    }

    private void initTabLayout() {
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void showHomeworkDetail(HomeworkDetailBean homeworkDetail) {
        initHeader(homeworkDetail);
        initContent(homeworkDetail);
    }

    private void initContent(HomeworkDetailBean homeworkDetail) {
        if (!TextUtils.isEmpty(homeworkDetail.getContents())) {
            mWorkContent.setText(homeworkDetail.getContents());
        }
        if (homeworkDetail.isHasImage()) {
            List<WorkImgDetailBean> workImgDetailBeens = homeworkDetail.getImgList();
            if (workImgDetailBeens != null && workImgDetailBeens.size() > 0) {
                CommonGlideImageLoader.getInstance().displayNetImage(WorkDetailTeacherActivity.this, workImgDetailBeens.get(0).getFilePath(), mDetailPhoto);
                mPhotoNum.setText(String.valueOf(workImgDetailBeens.size())+"张");
            }
        }
        if (homeworkDetail.isHasVideo()) {

        }
        if (homeworkDetail.isHasVoice()) {
            mPresenter.downloadVoice(homeworkDetail.getVoiceId());
            mVoiceLayout.setVisibility(View.VISIBLE);
            mVoiceDel.setVisibility(View.GONE);
            mAudioInfo.setTime(Long.parseLong(homeworkDetail.getVoiceLength()));

        }

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
        CommonGlideImageLoader.getInstance().displayNetImage(this, thumb, mHeadImg);
    }

    @Override
    public void getHomeworkDetailFail() {
        showContent("获取详情失败");
        finish();
    }

    @Override
    public void downloadVoiceSuccess(File file) {
        mAudioInfo.setFile(file);
    }

    public void finishRefresh() {
        mRefresh.finishRefresh();
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {


        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
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

        intent.putExtras(bundle);
        this.startActivity(intent);
    }


    @Override
    public void showContent(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AudioPlayEvent event) {
//        mProgress.setProgress((int) ((event.position / (time * 1000.0)) * 100));
        mVoiceTime.setText(TimeUtils.formatTime2(event.position / 1000));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AudioPlayStatusEvent event) {
        int status = event.status;
        switch (status) {
            case 0:
            case -1:
                mProgress.setProgress(0);
                mVoiceTime.setText("00:00");
                mVoiceControl.setText(getResources().getString(R.string.icon_bofang));
                break;
        }
    }
}
