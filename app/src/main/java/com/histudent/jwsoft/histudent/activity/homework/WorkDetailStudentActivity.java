package com.histudent.jwsoft.histudent.activity.homework;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.activity.image.ShowImageActivity;
import com.histudent.jwsoft.histudent.adapter.VideoAdapter;
import com.histudent.jwsoft.histudent.adapter.work.VideoDetailAdapter;
import com.histudent.jwsoft.histudent.base.BaseActivity;
import com.histudent.jwsoft.histudent.bean.homework.CommentBean;
import com.histudent.jwsoft.histudent.bean.homework.CompleteDetailBean;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkDetailBean;
import com.histudent.jwsoft.histudent.bean.homework.VideoDetailBean;
import com.histudent.jwsoft.histudent.bean.homework.WorkCompleteBean;
import com.histudent.jwsoft.histudent.bean.homework.WorkImgDetailBean;
import com.histudent.jwsoft.histudent.body.hiworld.activity.WatchActionVideoActivity;
import com.histudent.jwsoft.histudent.body.hiworld.activity.WatchEssayVideoActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.keyword.fragment.EmotionMainFragment;
import com.histudent.jwsoft.histudent.commen.keyword.utils.DisplayUtils;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.comment2.utils.EmotionUtils;
import com.histudent.jwsoft.histudent.comment2.utils.TimeUtils;
import com.histudent.jwsoft.histudent.constant.TransferKeys;
import com.histudent.jwsoft.histudent.entity.AudioInfo;
import com.histudent.jwsoft.histudent.entity.AudioPlayEvent;
import com.histudent.jwsoft.histudent.entity.AudioPlayStatusEvent;
import com.histudent.jwsoft.histudent.entity.CorrectWorkEvent;
import com.histudent.jwsoft.histudent.entity.ImageAttrEntity;
import com.histudent.jwsoft.histudent.entity.VideoInfoEntity;
import com.histudent.jwsoft.histudent.entity.WorkNoticeEvent;
import com.histudent.jwsoft.histudent.entity.WorkVideoEvent;
import com.histudent.jwsoft.histudent.entity.WorkVideoPlayEvent;
import com.histudent.jwsoft.histudent.presenter.homework.WorkDetailStudentPresenter;
import com.histudent.jwsoft.histudent.presenter.homework.contract.WorkDetailStudentContract;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2017/10/25.
 */

public class WorkDetailStudentActivity extends BaseActivity<WorkDetailStudentPresenter> implements WorkDetailStudentContract.View {
    @BindView(R.id.work_detail_name)
    TextView mCompleteName;
    @BindView(R.id.work_detail_avatar)
    ImageView mCompleteAvatar;
    @BindView(R.id.work_detail_time)
    TextView mCompleteTime;
    @BindView(R.id.title_middle_text)
    TextView mTitle;
    @BindView(R.id.work_detail_content)
    TextView mWorkContent;
    @BindView(R.id.work_student_detail_content)
    TextView mStudentContent;
    @BindView(R.id.work_detail_recycler_video)
    RecyclerView mRecyclerVideo;
    @BindView(R.id.work_student_detail_recycler_video)
    RecyclerView mStudentRecyclerVideo;
    @BindView(R.id.work_detail_photo)
    ImageView mDetailPhoto;
    @BindView(R.id.work_student_detail_photo)
    ImageView mStudentPhoto;
    @BindView(R.id.work_detail_photo_num)
    TextView mPhotoNum;
    @BindView(R.id.work_student_detail_photo_num)
    TextView mStudentPhotoNum;
    @BindView(R.id.comment_content)
    TextView mCommentContent;
    @BindView(R.id.work_detail_imgs)
    FrameLayout mImgLayout;
    @BindView(R.id.work_student_detail_imgs)
    FrameLayout mStudentImgLayout;
    @BindView(R.id.scroll_view)
    ScrollView mScrollView;
    @BindView(R.id.comment_content_layout)
    LinearLayout mCommentLayout;
    @BindView(R.id.modify_comment)
    LinearLayout mModifyComment;


    @OnClick({R.id.title_left, R.id.work_detail_photo, R.id.work_student_detail_photo, R.id.modify_comment})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.work_detail_photo:
                if (imageAttrs != null && imageAttrs.size() > 0) {
                    showImageDetail(mDetailPhoto, 0, imageAttrs);
                }
                break;
            case R.id.work_student_detail_photo:
                if (imageStudentAttrs != null && imageStudentAttrs.size() > 0) {
                    showImageDetail(mDetailPhoto, 0, imageStudentAttrs);
                }
                break;
            case R.id.modify_comment:
                Intent intent = new Intent(WorkDetailStudentActivity.this, FinishHomeworkActivity.class);
                intent.putExtra("detail", mCompleteDetail);
                intent.putExtra("audio", mAudioInfo);
                intent.putExtra("homeworkId", homeworkId);
                intent.putExtra("isModify", true);
                startActivityForResult(intent, REQ_FINISH);
                break;

        }
    }


    private VideoAdapter mVideoAdapter;
    private LinearLayoutManager mVideoLayoutManager;
    private LinearLayoutManager mStudentVideoLayoutManager;
    private GridLayoutManager mImgLayoutManager;
    private AudioInfo mAudioInfo = new AudioInfo();//学生完成的
    private AudioInfo mCommentAudioInfo = new AudioInfo();//评论
    private AudioInfo mDetailAudioInfo = new AudioInfo();//老师布置的作业
    private List<ImageAttrEntity> imageAttrs = new ArrayList();
    private List<ImageAttrEntity> imageStudentAttrs = new ArrayList();
    private List<String> commensContent = new ArrayList<>();
    private List<CommentBean> commens = new ArrayList<>();
    private EmotionMainFragment emotionMainFragment;
    private TextView mRecordTime;
    private TextView mTip;
    private LinearLayout mRecordLayout;
    private PopupWindow mPopupWindow;
    private ImageView mRecord;
    private boolean isRecord = false;
    private int time;
    private static final int MSG_RECORD = 1;
    private VideoDetailAdapter mDetailVideoAdapter;

    //评论的内容
    private View commentAudio;
    private IconView mCommentVoiceControl;
    private TextView mCommentVoiceTime;
    private TextView mCommentVoiceTimeTotal;
    private SeekBar mCommentProgress;
    private IconView mCommentVoiceDel;

    //学生完成的作业
    private View detailAudio;
    private IconView mDetailVoiceControl;
    private TextView mDetailVoiceTime;
    private TextView mDetailVoiceTimeTotal;
    private SeekBar mDetailProgress;
    private IconView mDetailVoiceDel;

    //老师布置的作业
    private View workAudio;
    private IconView mWorkVoiceControl;
    private TextView mWorkVoiceTime;
    private TextView mWorkVoiceTimeTotal;
    private SeekBar mWorkProgress;
    private IconView mWorkVoiceDel;


    private int voiceType;
    private String completeId;
    private long detailVoiceTime;
    private List<String> proposalIds = new ArrayList<>();
    private Map<Integer, Boolean> proposalState = new HashMap<>();
    private String homeworkId;
    private String userId;
    private static final int TYPE_VOICE_DETAIL = 1;//学生完成的
    private static final int TYPE_VOICE_COMMENT = 2;//老师评论
    private static final int TYPE_VOICE_WORK = 3;//作业详情
    private Handler mHandler = new Handler();
    private CompleteDetailBean mCompleteDetail;
    private static final int REQ_FINISH = 2000;




    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_work_detail_student;
    }

    @Override
    protected void init() {
        initIntent();
        initView();
        initData();
    }


    private void initView() {

        initTitle();
        initRecycler();
        initAudioPlay();
    }


    private void initAudioPlay() {
        commentAudio = findViewById(R.id.comment_audio);
        mCommentVoiceControl = commentAudio.findViewById(R.id.notices_voice_control);
        mCommentVoiceTime = commentAudio.findViewById(R.id.notices_voice_time);
        mCommentProgress = commentAudio.findViewById(R.id.notices_voice_progress);
        mCommentVoiceTimeTotal = commentAudio.findViewById(R.id.notices_voice_time_total);
        mCommentVoiceDel = commentAudio.findViewById(R.id.delete_voice);
        mCommentVoiceDel.setVisibility(View.GONE);
        mCommentVoiceControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPresenter.getAudioState()) {

                    if (voiceType == TYPE_VOICE_COMMENT) {
                        mPresenter.pauseAudio();
                        mCommentVoiceControl.setText(getResources().getString(R.string.icon_bofang));
                    } else {
                        pauseAudio(TYPE_VOICE_COMMENT);
                        mPresenter.playAudio(mCommentAudioInfo.getFile().getAbsolutePath());
                        mCommentVoiceControl.setText(getResources().getString(R.string.icon_zanting));
                    }
                } else {
                    if (voiceType != TYPE_VOICE_COMMENT) {
                        pauseAudio(TYPE_VOICE_COMMENT);
                    }
                    mPresenter.playAudio(mCommentAudioInfo.getFile().getAbsolutePath());
                    mCommentVoiceControl.setText(getResources().getString(R.string.icon_zanting));


                }
                voiceType = TYPE_VOICE_COMMENT;
            }
        });

        mCommentProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                double position = seekBar.getProgress() / 100.0;
                voiceType = TYPE_VOICE_COMMENT;
                pauseAudio(TYPE_VOICE_COMMENT);
                mCommentVoiceControl.setText(getResources().getString(R.string.icon_zanting));
                mPresenter.playAudio(mCommentAudioInfo.getFile().getAbsolutePath(), (int) (mCommentAudioInfo.getTime() * 1000 * position));
            }
        });


        detailAudio = findViewById(R.id.detail_student_audio);
        mDetailVoiceControl = detailAudio.findViewById(R.id.notices_voice_control);
        mDetailVoiceTime = detailAudio.findViewById(R.id.notices_voice_time);
        mDetailProgress = detailAudio.findViewById(R.id.notices_voice_progress);
        mDetailVoiceTimeTotal = detailAudio.findViewById(R.id.notices_voice_time_total);
        mDetailVoiceDel = detailAudio.findViewById(R.id.delete_voice);
        mDetailVoiceDel.setVisibility(View.GONE);

        mDetailVoiceControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPresenter.getAudioState()) {
                    if (voiceType == TYPE_VOICE_DETAIL) {
                        mPresenter.pauseAudio();
                        mDetailVoiceControl.setText(getResources().getString(R.string.icon_bofang));
                    } else {
                        pauseAudio(TYPE_VOICE_DETAIL);
                        mPresenter.playAudio(mAudioInfo.getFile().getAbsolutePath());
                        mDetailVoiceControl.setText(getResources().getString(R.string.icon_zanting));
                    }
                } else {
                    if (mAudioInfo.getFile() != null) {
                        if (voiceType != TYPE_VOICE_DETAIL) {
                            pauseAudio(TYPE_VOICE_DETAIL);
                        }
                        mPresenter.playAudio(mAudioInfo.getFile().getAbsolutePath());
                        mDetailVoiceControl.setText(getResources().getString(R.string.icon_zanting));
                    }

                }
                voiceType = TYPE_VOICE_DETAIL;

            }
        });


        mDetailProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                double position = seekBar.getProgress() / 100.0;
                voiceType = TYPE_VOICE_DETAIL;
                pauseAudio(TYPE_VOICE_DETAIL);
                mDetailVoiceControl.setText(getResources().getString(R.string.icon_zanting));
                mPresenter.playAudio(mAudioInfo.getFile().getAbsolutePath(), (int) (mAudioInfo.getTime() * 1000 * position));
            }
        });

        workAudio = findViewById(R.id.work_detail_audio);
        mWorkVoiceControl = workAudio.findViewById(R.id.notices_voice_control);
        mWorkVoiceTime = workAudio.findViewById(R.id.notices_voice_time);
        mWorkProgress = workAudio.findViewById(R.id.notices_voice_progress);
        mWorkVoiceTimeTotal = workAudio.findViewById(R.id.notices_voice_time_total);
        mWorkVoiceDel = workAudio.findViewById(R.id.delete_voice);
        mWorkVoiceDel.setVisibility(View.GONE);


        mWorkVoiceControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPresenter.getAudioState()) {
                    if (voiceType == TYPE_VOICE_WORK) {
                        mPresenter.pauseAudio();
                        mWorkVoiceControl.setText(getResources().getString(R.string.icon_bofang));
                    } else {
                        pauseAudio(TYPE_VOICE_WORK);
                        mPresenter.playAudio(mDetailAudioInfo.getFile().getAbsolutePath());
                        mWorkVoiceControl.setText(getResources().getString(R.string.icon_zanting));
                    }
                } else {
                    if (voiceType != TYPE_VOICE_WORK) {
                        pauseAudio(TYPE_VOICE_WORK);
                    }
                    mPresenter.playAudio(mDetailAudioInfo.getFile().getAbsolutePath());
                    mWorkVoiceControl.setText(getResources().getString(R.string.icon_zanting));
                }
                voiceType = TYPE_VOICE_WORK;
            }
        });

        mWorkProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                double position = seekBar.getProgress() / 100.0;
                voiceType = TYPE_VOICE_WORK;
                pauseAudio(TYPE_VOICE_WORK);
                mWorkVoiceControl.setText(getResources().getString(R.string.icon_zanting));
                mPresenter.playAudio(mDetailAudioInfo.getFile().getAbsolutePath(), (int) (mDetailAudioInfo.getTime() * 1000 * position));
            }
        });
    }


    private void pauseAudio(int voiceType) {
        switch (voiceType) {
            case TYPE_VOICE_DETAIL:
                mWorkVoiceControl.setText(getResources().getString(R.string.icon_bofang));
                mWorkVoiceTime.setText("00:00");
                mWorkProgress.setProgress(0);
                mCommentVoiceControl.setText(getResources().getString(R.string.icon_bofang));
                mCommentVoiceTime.setText("00:00");
                mCommentProgress.setProgress(0);
                break;
            case TYPE_VOICE_COMMENT:
                mDetailVoiceControl.setText(getResources().getString(R.string.icon_bofang));
                mDetailVoiceTime.setText("00:00");
                mDetailProgress.setProgress(0);
                mWorkVoiceControl.setText(getResources().getString(R.string.icon_bofang));
                mWorkVoiceTime.setText("00:00");
                mWorkProgress.setProgress(0);
                break;
            case TYPE_VOICE_WORK:
                mCommentVoiceControl.setText(getResources().getString(R.string.icon_bofang));
                mCommentVoiceTime.setText("00:00");
                mCommentProgress.setProgress(0);

                mDetailVoiceControl.setText(getResources().getString(R.string.icon_bofang));
                mDetailVoiceTime.setText("00:00");
                mDetailProgress.setProgress(0);
                break;
        }
        mPresenter.stopAudio();
    }


    private void initRecycler() {
        mVideoAdapter = new VideoAdapter(this);
        mDetailVideoAdapter = new VideoDetailAdapter(this);
        mVideoLayoutManager = new LinearLayoutManager(this);
        mStudentVideoLayoutManager = new LinearLayoutManager(this);
        mRecyclerVideo.setAdapter(mVideoAdapter);
        mRecyclerVideo.setLayoutManager(mVideoLayoutManager);
        mStudentRecyclerVideo.setAdapter(mDetailVideoAdapter);
        mStudentRecyclerVideo.setLayoutManager(mStudentVideoLayoutManager);
    }

    private void initTitle() {
        mTitle.setText("作业详情");
    }

    private void initHeader(HomeworkDetailBean homeworkDetail) {
        mCompleteName.setText(homeworkDetail.getOwnerName());
        mCompleteTime.setText(TimeUtils.exchangeTime(homeworkDetail.getCreateTime()));
        CommonGlideImageLoader.getInstance().displayNetImageWithCircle(this, homeworkDetail.getOwnerAvatar(), mCompleteAvatar);
    }

    private void initData() {
        showLoadingDialog();
        mPresenter.getCompleteDetail(homeworkId, HiCache.getInstance().getLoginUserInfo().getUserId());
        mPresenter.getHomeworkDetail(homeworkId);
    }


    private void initIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            homeworkId = intent.getStringExtra("homeworkId");
        }

    }


    private void initContent(CompleteDetailBean completeDetail) {
        completeId = completeDetail.getId();
        imageStudentAttrs.clear();
        if (!TextUtils.isEmpty(completeDetail.getContents())) {
            mStudentContent.setText(EmotionUtils.convertNormalStringToSpannableString(completeDetail.getContents()));
        }
        if (completeDetail.isHasImage()) {
            List<CompleteDetailBean.CompleteImagesBean> completeImages = completeDetail.getCompleteImages();
            if (completeImages != null && completeImages.size() > 0) {
                for (CompleteDetailBean.CompleteImagesBean completeImage : completeImages) {
                    ImageAttrEntity imageAttrEntity = new ImageAttrEntity();
                    imageAttrEntity.setId(completeImage.getId());
                    imageAttrEntity.setBigSizeUrl(completeImage.getFilePath());
                    imageAttrEntity.setThumbnailUrl(completeImage.getFilePath());
                    imageStudentAttrs.add(imageAttrEntity);
                }
                mStudentImgLayout.setVisibility(View.VISIBLE);
                CommonGlideImageLoader.getInstance().displayNetImage(WorkDetailStudentActivity.this, completeImages.get(0).getFilePath(), mStudentPhoto);
                mStudentPhotoNum.setText(String.valueOf(completeImages.size()) + "张");
            } else {
                mStudentImgLayout.setVisibility(View.GONE);
            }
        } else {
            mStudentImgLayout.setVisibility(View.GONE);
        }
        if (completeDetail.isHasVideo()) {
            mStudentRecyclerVideo.setVisibility(View.VISIBLE);
            List<VideoDetailBean> videoDetailBeen = completeDetail.getCompleteVideos();
            mDetailVideoAdapter.setList(videoDetailBeen);
        } else {
            mStudentRecyclerVideo.setVisibility(View.GONE);
        }
        if (completeDetail.isHasVoice()) {
            mPresenter.downloadVoice(completeDetail.getCompleteVoice().getId(), TYPE_VOICE_DETAIL);
            detailAudio.setVisibility(View.VISIBLE);
            mAudioInfo.setTime(completeDetail.getCompleteVoice().getFileLength());
            detailVoiceTime = completeDetail.getCompleteVoice().getFileLength();
            mDetailVoiceTimeTotal.setText(TimeUtils.formatTime2(completeDetail.getCompleteVoice().getFileLength()));
        }else{
            detailAudio.setVisibility(View.GONE);
            mAudioInfo.setFile(null);
        }

    }

    @Override
    public void showCompleteDetail(CompleteDetailBean completeDetailBean) {
        this.mCompleteDetail = completeDetailBean;
        initContent(completeDetailBean);
        initComment(completeDetailBean);
    }

    private void initComment(CompleteDetailBean completeDetailBean) {
        if (completeDetailBean.isCommented()) {
            mCommentLayout.setVisibility(View.VISIBLE);
            mModifyComment.setVisibility(View.GONE);
            mCommentContent.setText(EmotionUtils.convertNormalStringToSpannableString(completeDetailBean.getComment()));
            if (completeDetailBean.isCommentVoice()) {
                CompleteDetailBean.CompleteVoiceBean completeVoiceBean = completeDetailBean.getCommentedVoice();
                mCommentVoiceTimeTotal.setText(TimeUtils.formatTime2(completeVoiceBean.getFileLength()));
                mCommentAudioInfo.setTime(completeVoiceBean.getFileLength());
                commentAudio.setVisibility(View.VISIBLE);
                mPresenter.downloadVoice(completeVoiceBean.getId(), TYPE_VOICE_COMMENT);
            } else {
                commentAudio.setVisibility(View.GONE);
            }
        } else {
            mCommentLayout.setVisibility(View.GONE);
            mModifyComment.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void getCompleteDetailFail() {

    }

    @Override
    public void downloadVoiceSuccess(File file, int type) {
        if (type == TYPE_VOICE_WORK) {
            mDetailAudioInfo.setFile(file);
        } else if (type == TYPE_VOICE_DETAIL) {
            mAudioInfo.setFile(file);
        } else if (type == TYPE_VOICE_COMMENT) {
            mCommentAudioInfo.setFile(file);
        }
    }

    @Override
    public void showHomeworkDetail(HomeworkDetailBean homeworkDetail) {
        dismissLoadingDialog();
        initHeader(homeworkDetail);
        initWorkContent(homeworkDetail);
    }


    private void initWorkContent(HomeworkDetailBean homeworkDetail) {
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
                CommonGlideImageLoader.getInstance().displayNetImage(WorkDetailStudentActivity.this, workImgDetailBeens.get(0).getThumbnailFilePath(), mDetailPhoto);
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
            mDetailVideoAdapter.setList(videoList);
        } else {
            mRecyclerVideo.setVisibility(View.GONE);
        }

        if (homeworkDetail.isHasVoice()) {
            mPresenter.downloadVoice(homeworkDetail.getVoiceId(), TYPE_VOICE_WORK);
            workAudio.setVisibility(View.VISIBLE);
            mWorkVoiceDel.setVisibility(View.GONE);
            long voiceTime = homeworkDetail.getVoiceLength();
            mDetailAudioInfo.setTime(voiceTime);
            mWorkVoiceTimeTotal.setText(TimeUtils.formatTime2(voiceTime));
        }

    }

    @Override
    public void getHomeworkDetailFail() {
        dismissLoadingDialog();
        showContent("获取详情失败");
        finish();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_FINISH) {
            mPresenter.getCompleteDetail(homeworkId, HiCache.getInstance().getLoginUserInfo().getUserId());
        }
    }

    @Subscribe
    public void onEvent(WorkVideoEvent workVideoEvent) {
        VideoDetailBean videoListBean = workVideoEvent.videoDetailBean;
        WatchActionVideoActivity.start(this, videoListBean.getAliVideoId());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AudioPlayEvent event) {
        if (voiceType == TYPE_VOICE_COMMENT) {
            mCommentProgress.setProgress((int) ((event.position / (mCommentAudioInfo.getTime() * 1000.0)) * 100));
            mCommentVoiceTime.setText(TimeUtils.formatTime2(event.position / 1000));
        } else if (voiceType == TYPE_VOICE_DETAIL) {
            mDetailProgress.setProgress((int) ((event.position / (mAudioInfo.getTime() * 1000.0)) * 100));
            mDetailVoiceTime.setText(TimeUtils.formatTime2(event.position / 1000));
        } else if (voiceType == TYPE_VOICE_WORK) {
            mWorkProgress.setProgress((int) ((event.position / (mDetailAudioInfo.getTime() * 1000.0)) * 100));
            mWorkVoiceTime.setText(TimeUtils.formatTime2(event.position / 1000));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AudioPlayStatusEvent event) {
        int status = event.status;
        switch (status) {
            case 0:
            case -1:
                if (voiceType == TYPE_VOICE_COMMENT) {
                    mCommentProgress.setProgress(100);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mCommentProgress.setProgress(0);
                            mCommentVoiceTime.setText("00:00");
                            mCommentVoiceControl.setText(getResources().getString(R.string.icon_bofang));
                        }
                    }, 500);

                } else if (voiceType == TYPE_VOICE_WORK) {
                    mWorkProgress.setProgress(100);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mWorkProgress.setProgress(0);
                            mWorkVoiceTime.setText("00:00");
                            mWorkVoiceControl.setText(getResources().getString(R.string.icon_bofang));
                        }
                    }, 500);

                } else if (voiceType == TYPE_VOICE_DETAIL) {
                    mDetailProgress.setProgress(100);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mDetailProgress.setProgress(0);
                            mDetailVoiceTime.setText("00:00");
                            mDetailVoiceControl.setText(getResources().getString(R.string.icon_bofang));
                        }
                    }, 500);
                }
                break;
        }
    }

    @Override
    public void showContent(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        dismissLoadingDialog();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
