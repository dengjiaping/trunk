package com.histudent.jwsoft.histudent.activity.homework;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.activity.image.ShowImageActivity;
import com.histudent.jwsoft.histudent.adapter.VideoAdapter;
import com.histudent.jwsoft.histudent.base.BaseActivity;
import com.histudent.jwsoft.histudent.bean.homework.CommentBean;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkDetailBean;
import com.histudent.jwsoft.histudent.bean.homework.WorkCompleteBean;
import com.histudent.jwsoft.histudent.bean.homework.WorkImgDetailBean;
import com.histudent.jwsoft.histudent.commen.keyword.fragment.EmotionMainFragment;
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
import com.histudent.jwsoft.histudent.presenter.homework.CorrectPresenter;
import com.histudent.jwsoft.histudent.presenter.homework.contract.CorrectContract;
import com.histudent.jwsoft.histudent.widget.FlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.histudent.jwsoft.histudent.widget.FlowLayout.STYLE_CHECKBOX;

/**
 * Created by huyg on 2017/10/30.
 * 批改作业
 */

public class CorrectWorkActivity extends BaseActivity<CorrectPresenter> implements CorrectContract.View {

    @BindView(R.id.work_complete_name)
    TextView mCompleteName;
    @BindView(R.id.work_complete_avatar)
    ImageView mCompleteAvatar;
    @BindView(R.id.work_complete_time)
    TextView mCompleteTime;
    @BindView(R.id.title_middle_text)
    TextView mTitle;
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
    @BindView(R.id.write_comment)
    LinearLayout mWriteComment;
    @BindView(R.id.comment_content)
    LinearLayout mCommentLayout;
    @BindView(R.id.work_detail_imgs)
    FrameLayout mImgLayout;
    @BindView(R.id.correct_content)
    EditText mCorrectContent;
    @BindView(R.id.correct_content_num)
    TextView mContentNum;
    @BindView(R.id.layout_flow)
    FlowLayout mFlowLayout;
    @BindView(R.id.control_emotion)
    IconView mEmotionBtn;
    @BindView(R.id.scroll_view)
    ScrollView mScrollView;
    @OnClick({R.id.title_left, R.id.notices_voice_control, R.id.work_detail_photo})
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
                    if (mAudioInfo != null) {
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
        }
    }

    private WorkCompleteBean.ItemsBean itemsBean;

    private VideoAdapter mVideoAdapter;
    private LinearLayoutManager mVideoLayoutManager;
    private GridLayoutManager mImgLayoutManager;
    private AudioInfo mAudioInfo = new AudioInfo();
    private List<ImageAttrEntity> imageAttrs = new ArrayList();
    private List<String> commens = new ArrayList<>();
    private EmotionMainFragment emotionMainFragment;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_correct_work;
    }

    @Override
    protected void init() {
        initIntent();
        initView();
        initData();
    }


    private void initView() {
        mCorrectContent.setFilters(new InputFilter[] { new InputFilter.LengthFilter(150) });
        mCorrectContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mContentNum.setText(charSequence.length()+"/150");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        initHeader();
        initTitle();
        initRecycler();
        initComment();
        initEmotionMainFragment();
    }

    private void initComment() {
        if (itemsBean.isIsComment()){
            mCommentLayout.setVisibility(View.VISIBLE);
            mWriteComment.setVisibility(View.GONE);
        }else{
            mWriteComment.setVisibility(View.VISIBLE);
            mCommentLayout.setVisibility(View.GONE);
        }
    }

    private void initRecycler() {
        mVideoAdapter = new VideoAdapter(this);
        mVideoLayoutManager = new LinearLayoutManager(this);
        mRecyclerVideo.setAdapter(mVideoAdapter);
        mRecyclerVideo.setLayoutManager(mVideoLayoutManager);
    }

    private void initTitle() {
        mTitle.setText("作业详情");
    }

    private void initHeader() {
        mCompleteName.setText(itemsBean.getUserName());
        mCompleteTime.setText(TimeUtils.exchangeTime(itemsBean.getCompleteTime()));
        CommonGlideImageLoader.getInstance().displayNetImageWithCircle(this, itemsBean.getUserAvatar(), mCompleteAvatar);
    }

    private void initData() {
        mPresenter.getHomeworkDetail(itemsBean.getHomeworkId());
    }


    private void initIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            itemsBean = (WorkCompleteBean.ItemsBean) intent.getSerializableExtra("completeInfo");
        }

    }

    /**
     * 初始化表情面板
     */
    public void initEmotionMainFragment() {
        //构建传递参数
        Bundle bundle = new Bundle();
        //绑定主内容编辑框
        bundle.putBoolean(EmotionMainFragment.BIND_TO_EDITTEXT, false);
        //隐藏控件
        bundle.putBoolean(EmotionMainFragment.HIDE_BAR_EDITTEXT_AND_BTN, true);
        //替换fragment
        //创建修改实例
        emotionMainFragment = EmotionMainFragment.newInstance(EmotionMainFragment.class, bundle);
        emotionMainFragment.bindToEditText(mCorrectContent);
        emotionMainFragment.bindToContentView(mScrollView);
        emotionMainFragment.setExchangeButton(mEmotionBtn);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in thefragment_container view with this fragment,
        // and add the transaction to the backstack
        transaction.replace(R.id.fl_emotionview_main, emotionMainFragment);
//        transaction.addToBackStack(null);
        //提交修改
        transaction.commit();
    }

    @Override
    public void getHomeworkDetailFail() {
        showContent("获取详情失败");
        finish();
    }

    @Override
    public void showHomeworkDetail(HomeworkDetailBean homeworkDetail) {
        initContent(homeworkDetail);
        mPresenter.getCommentList("");
    }

    private void initContent(HomeworkDetailBean homeworkDetail) {
        if (!TextUtils.isEmpty(homeworkDetail.getContents())) {
            mWorkContent.setText(homeworkDetail.getContents());
        }
        if (homeworkDetail.isHasImage()) {
            List<WorkImgDetailBean> workImgDetailBeens = homeworkDetail.getImgList();
            if (workImgDetailBeens != null && workImgDetailBeens.size() > 0) {
                for (WorkImgDetailBean workImgDetailBean : workImgDetailBeens) {
                    ImageAttrEntity imageAttrEntity = new ImageAttrEntity();
                    imageAttrEntity.setId(workImgDetailBean.getId());
                    imageAttrEntity.setBigSizeUrl(workImgDetailBean.getFilePath());
                    imageAttrEntity.setThumbnailUrl(workImgDetailBean.getFilePath());
                    imageAttrs.add(imageAttrEntity);
                }
                mImgLayout.setVisibility(View.VISIBLE);
                CommonGlideImageLoader.getInstance().displayNetImage(CorrectWorkActivity.this, workImgDetailBeens.get(0).getFilePath(), mDetailPhoto);
                mPhotoNum.setText(String.valueOf(workImgDetailBeens.size()) + "张");
            }else{
                    mImgLayout.setVisibility(View.GONE);
            }
        }else{
            mImgLayout.setVisibility(View.GONE);
        }
        if (homeworkDetail.isHasVideo()) {
            homeworkDetail.getVideoId();
        }
        if (homeworkDetail.isHasVoice()) {
            mPresenter.downloadVoice(homeworkDetail.getVoiceId());
            mVoiceLayout.setVisibility(View.VISIBLE);
            mVoiceDel.setVisibility(View.GONE);
            mAudioInfo.setTime(Long.parseLong(homeworkDetail.getVoiceLength()));
        }

    }

    @Override
    public void downloadVoiceSuccess(File file) {
        mAudioInfo.setFile(file);
    }

    @Override
    public void commentProposalSuccess(List<CommentBean> commentBeens) {
        for (CommentBean commentBeen:commentBeens){
            commens.add(commentBeen.getCommentContent());
        }
        mFlowLayout.setData(commens,STYLE_CHECKBOX);
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

    @Override
    public void showContent(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
}
