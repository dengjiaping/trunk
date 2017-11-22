package com.histudent.jwsoft.histudent.view.activity.homework;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.base.BaseActivity;
import com.histudent.jwsoft.histudent.body.hiworld.activity.WatchActionVideoActivity;
import com.histudent.jwsoft.histudent.commen.keyword.fragment.EmotionMainFragment;
import com.histudent.jwsoft.histudent.commen.keyword.utils.DisplayUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.comment2.utils.EmotionUtils;
import com.histudent.jwsoft.histudent.comment2.utils.TimeUtils;
import com.histudent.jwsoft.histudent.model.bean.homework.CommentBean;
import com.histudent.jwsoft.histudent.model.bean.homework.CompleteDetailBean;
import com.histudent.jwsoft.histudent.model.bean.homework.VideoDetailBean;
import com.histudent.jwsoft.histudent.model.bean.homework.WorkCompleteBean;
import com.histudent.jwsoft.histudent.model.constant.Const;
import com.histudent.jwsoft.histudent.model.entity.AudioInfo;
import com.histudent.jwsoft.histudent.model.entity.AudioPlayEvent;
import com.histudent.jwsoft.histudent.model.entity.AudioPlayStatusEvent;
import com.histudent.jwsoft.histudent.model.entity.FlowClickEvent;
import com.histudent.jwsoft.histudent.model.entity.ImageAttrEntity;
import com.histudent.jwsoft.histudent.model.entity.RecordInfoEvent;
import com.histudent.jwsoft.histudent.model.entity.RecordStatusEvent;
import com.histudent.jwsoft.histudent.model.entity.WorkVideoEvent;
import com.histudent.jwsoft.histudent.presenter.homework.CorrectPresenter;
import com.histudent.jwsoft.histudent.presenter.homework.contract.CorrectContract;
import com.histudent.jwsoft.histudent.view.activity.image.ShowImageActivity;
import com.histudent.jwsoft.histudent.view.adapter.work.VideoDetailAdapter;
import com.histudent.jwsoft.histudent.view.widget.FlowInfo;
import com.histudent.jwsoft.histudent.view.widget.FlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

import static com.histudent.jwsoft.histudent.view.widget.FlowLayout.STYLE_CHECKBOX;


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

    @BindView(R.id.write_comment)
    LinearLayout mWriteComment;
    @BindView(R.id.comment_content_layout)
    LinearLayout mCommentLayout;
    @BindView(R.id.comment_content)
    TextView mCommentContent;
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
    @BindView(R.id.correct_finish)
    Button mCorrectFinish;
    @BindView(R.id.layout)
    LinearLayout mLayout;
    @BindView(R.id.modify_comment)
    LinearLayout mModifyComment;


    @OnClick({R.id.title_left, R.id.work_detail_photo, R.id.correct_finish, R.id.control_record, R.id.modify_comment})
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
            case R.id.correct_finish:
                mPresenter.stopAudio();
                voiceType = TYPE_VOICE_COMMENT;
                String contentStr = mCorrectContent.getText().toString();
                String proposalStr = initProposal();
                String delVoiceStr = "";
                if (delVoice != null && delVoice.size() > 0) {
                    delVoiceStr = new Gson().toJson(delVoice);
                }
                if (TextUtils.isEmpty(contentStr) && TextUtils.isEmpty(proposalStr)) {
                    showContent("请填写评语");
                    return;
                }

                showLoadingDialog();
                mPresenter.commentHomework(completeId, contentStr, proposalStr, mCommentAudioInfo, delVoiceStr);
                break;
            case R.id.control_record:
                SystemUtil.hideSoftKeyboard(CorrectWorkActivity.this);
                SystemUtil.hideSoftKeyboard(CorrectWorkActivity.this);
                showRecordWindow();
                break;
            case R.id.modify_comment:
                mPresenter.stopAudio();
                voiceType = TYPE_VOICE_COMMENT_DETAIL;
                mWriteComment.setVisibility(View.VISIBLE);
                mCommentLayout.setVisibility(View.GONE);
                initWriteComment();
                break;

        }
    }


    private String initProposal() {
        proposalIds.clear();
        List<FlowInfo> flowInfos = mFlowLayout.getFlowInfos();
        if (flowInfos != null && flowInfos.size() > 0) {
            for (int i = 0; i < flowInfos.size(); i++) {
                if (flowInfos.get(i).getIsCheck()) {
                    proposalIds.add(commens.get(i).getCommentId());
                }

            }
        }
        if (proposalIds != null && proposalIds.size() > 0) {
            return new Gson().toJson(proposalIds);
        }
        return "";

    }

    private WorkCompleteBean.ItemsBean itemsBean;

    private VideoDetailAdapter mVideoAdapter;
    private LinearLayoutManager mVideoLayoutManager;
    private GridLayoutManager mImgLayoutManager;
    private AudioInfo mAudioInfo = new AudioInfo();//作业详情音频
    private AudioInfo mCommentAudioInfo = new AudioInfo();//修改评语音频
    private AudioInfo mCommentDetailAudioInfo = new AudioInfo();//完成评语音频
    private List<ImageAttrEntity> imageAttrs = new ArrayList();
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
    private View commentAudio;//评语修改的audio
    private IconView mCommentVoiceControl;
    private TextView mCommentVoiceTime;
    private TextView mCommentVoiceTimeTotal;
    private SeekBar mCommentProgress;
    private IconView mCommentVoiceDel;

    private View detailAudio; //作业的audio
    private IconView mDetailVoiceControl;
    private TextView mDetailVoiceTime;
    private TextView mDetailVoiceTimeTotal;
    private SeekBar mDetailProgress;
    private IconView mDetailVoiceDel;

    private View commentdetailAudio;
    private IconView mCommentDetailVoiceControl;
    private TextView mCommentDetailVoiceTime;
    private TextView mCommentDetailVoiceTimeTotal;
    private SeekBar mCommentDetailProgress;
    private IconView mCommentDetailVoiceDel;
    private static final int TYPE_VOICE_DETAIL = 1;//作业的audio
    private static final int TYPE_VOICE_COMMENT = 2;
    private static final int TYPE_VOICE_COMMENT_DETAIL = 3;
    private int voiceType;
    private String completeId;
    private long detailVoiceTime;
    private List<String> proposalIds = new ArrayList<>();
    private CompleteDetailBean mCompleteDetailBean;
    private List<String> delVoice = new ArrayList<>();
    private List<FlowInfo> flowInfos;

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
//        mCorrectContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(150)});
        mCorrectContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mContentNum.setVisibility(View.GONE);
        initHeader();
        initTitle();
        initRecycler();
        initInputSoftKeyboard();
        initEmotionMainFragment();
        initAudioPlay();
    }

    private void initInputSoftKeyboard() {
//        获取焦点后 自动弹出键盘
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) mCorrectContent.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(mCorrectContent, 0);
            }

        }, 100);
    }


    private void initAudioPlay() {
        commentAudio = findViewById(R.id.comment_audio);
        mCommentVoiceControl = commentAudio.findViewById(R.id.notices_voice_control);
        mCommentVoiceTime = commentAudio.findViewById(R.id.notices_voice_time);
        mCommentProgress = commentAudio.findViewById(R.id.notices_voice_progress);
        mCommentVoiceTimeTotal = commentAudio.findViewById(R.id.notices_voice_time_total);
        mCommentVoiceDel = commentAudio.findViewById(R.id.delete_voice);
        mCommentVoiceDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentAudio.setVisibility(View.GONE);
                mPresenter.stopAudio();
                delVoice.clear();
                if (mCompleteDetailBean != null) {
                    if (mCompleteDetailBean.getCompleteVoice() != null) {
                        if (!TextUtils.isEmpty(mCompleteDetailBean.getCompleteVoice().getId())) {
                            delVoice.add(mCompleteDetailBean.getCompleteVoice().getId());
                        }
                    }
                }
                mCommentAudioInfo.setFile(null);
            }
        });

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

        detailAudio = findViewById(R.id.detail_audio);
        mDetailVoiceControl = detailAudio.findViewById(R.id.notices_voice_control);
        mDetailVoiceTime = detailAudio.findViewById(R.id.notices_voice_time);
        mDetailProgress = detailAudio.findViewById(R.id.notices_voice_progress);
        mDetailVoiceTimeTotal = detailAudio.findViewById(R.id.notices_voice_time_total);
        mDetailVoiceDel = detailAudio.findViewById(R.id.delete_voice);

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
                    if (voiceType != TYPE_VOICE_DETAIL) {
                        pauseAudio(TYPE_VOICE_DETAIL);
                    }
                    mPresenter.playAudio(mAudioInfo.getFile().getAbsolutePath());
                    mDetailVoiceControl.setText(getResources().getString(R.string.icon_zanting));
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

        commentdetailAudio = findViewById(R.id.comment_detail_audio);
        mCommentDetailVoiceControl = commentdetailAudio.findViewById(R.id.notices_voice_control);
        mCommentDetailVoiceTime = commentdetailAudio.findViewById(R.id.notices_voice_time);
        mCommentDetailProgress = commentdetailAudio.findViewById(R.id.notices_voice_progress);
        mCommentDetailVoiceTimeTotal = commentdetailAudio.findViewById(R.id.notices_voice_time_total);
        mCommentDetailVoiceDel = commentdetailAudio.findViewById(R.id.delete_voice);
        mCommentDetailVoiceDel.setVisibility(View.GONE);


        mCommentDetailVoiceControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPresenter.getAudioState()) {

                    if (voiceType == TYPE_VOICE_COMMENT_DETAIL) {
                        mPresenter.pauseAudio();
                        mCommentDetailVoiceControl.setText(getResources().getString(R.string.icon_bofang));
                    } else {
                        pauseAudio(TYPE_VOICE_COMMENT_DETAIL);
                        mPresenter.playAudio(mCommentDetailAudioInfo.getFile().getAbsolutePath());
                        mCommentDetailVoiceControl.setText(getResources().getString(R.string.icon_zanting));
                    }
                } else {
                    if (voiceType != TYPE_VOICE_COMMENT_DETAIL) {
                        mPresenter.pauseAudio();
                    }
                    mPresenter.playAudio(mCommentDetailAudioInfo.getFile().getAbsolutePath());
                    mCommentDetailVoiceControl.setText(getResources().getString(R.string.icon_zanting));
                }
                voiceType = TYPE_VOICE_COMMENT_DETAIL;
            }
        });

        mCommentDetailProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                double position = seekBar.getProgress() / 100.0;
                voiceType = TYPE_VOICE_COMMENT_DETAIL;
                pauseAudio(TYPE_VOICE_COMMENT_DETAIL);
                mDetailVoiceControl.setText(getResources().getString(R.string.icon_zanting));
                mPresenter.playAudio(mCommentDetailAudioInfo.getFile().getAbsolutePath(), (int) (mCommentDetailAudioInfo.getTime() * 1000 * position));
            }
        });
    }

    private void pauseAudio(int voiceType) {
        switch (voiceType) {
            case TYPE_VOICE_DETAIL:
                mCommentDetailVoiceControl.setText(getResources().getString(R.string.icon_bofang));
                mCommentDetailVoiceTime.setText("00:00");
                mCommentDetailProgress.setProgress(0);
                mCommentVoiceControl.setText(getResources().getString(R.string.icon_bofang));
                mCommentVoiceTime.setText("00:00");
                mCommentProgress.setProgress(0);
                break;
            case TYPE_VOICE_COMMENT:
                mDetailVoiceControl.setText(getResources().getString(R.string.icon_bofang));
                mDetailVoiceTime.setText("00:00");
                mDetailProgress.setProgress(0);
                mCommentDetailVoiceControl.setText(getResources().getString(R.string.icon_bofang));
                mCommentDetailVoiceTime.setText("00:00");
                mCommentDetailProgress.setProgress(0);
                break;
            case TYPE_VOICE_COMMENT_DETAIL:
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

    private void initComment(CompleteDetailBean completeDetailBean) {
        if (completeDetailBean.isCommented()) {
            mCommentLayout.setVisibility(View.VISIBLE);
            mWriteComment.setVisibility(View.GONE);
            mCommentContent.setText(EmotionUtils.convertNormalStringToSpannableString(completeDetailBean.getComment()));
            if (completeDetailBean.isCommentVoice()) {
                CompleteDetailBean.CompleteVoiceBean completeVoiceBean = completeDetailBean.getCommentedVoice();
                mCommentDetailVoiceTimeTotal.setText(TimeUtils.formatTime2(completeVoiceBean.getFileLength()));
                mCommentDetailAudioInfo.setTime(completeVoiceBean.getFileLength());
                commentdetailAudio.setVisibility(View.VISIBLE);
                mPresenter.downloadVoice(completeVoiceBean.getId(), TYPE_VOICE_COMMENT_DETAIL);
            } else {
                commentdetailAudio.setVisibility(View.GONE);
            }
        } else {
            mWriteComment.setVisibility(View.VISIBLE);
            mCommentLayout.setVisibility(View.GONE);
        }
    }

    private void initRecycler() {
        mVideoAdapter = new VideoDetailAdapter(this);
        mVideoLayoutManager = new LinearLayoutManager(this);
        mRecyclerVideo.setAdapter(mVideoAdapter);
        mRecyclerVideo.setLayoutManager(mVideoLayoutManager);
    }

    private void initWriteComment() {
        if (mCompleteDetailBean != null) {
            String flowInfoStr = "";
            StringBuilder flowInfoBuilder = new StringBuilder();
            if (flowInfos != null && flowInfos.size() > 0) {
                for (FlowInfo flowInfo : flowInfos) {
                    if (flowInfo.getIsCheck()) {
                        flowInfoBuilder.append(flowInfo.getContent());
                        flowInfoBuilder.append(",");
                    }

                }
            }
            flowInfoStr = flowInfoBuilder.toString();
            if (flowInfoStr.endsWith(",")) {
                flowInfoStr.substring(0, flowInfoStr.length() - 1);
            }
            String comment = "";
            if (!TextUtils.isEmpty(mCompleteDetailBean.getComment())) {
                if (flowInfoStr.length() < mCompleteDetailBean.getComment().length()) {
                    comment = mCompleteDetailBean.getComment().substring(flowInfoStr.length());
                } else {
                    comment = "";
                }

            }
            mCorrectContent.setText(comment);
            mCorrectContent.setSelection(mCorrectContent.getText().length());
            if (mCompleteDetailBean.isCommentVoice()) {
                CompleteDetailBean.CompleteVoiceBean completeVoiceBean = mCompleteDetailBean.getCommentedVoice();
                mCommentVoiceTimeTotal.setText(TimeUtils.formatTime2(completeVoiceBean.getFileLength()));
                mCommentAudioInfo.setTime(completeVoiceBean.getFileLength());
                mCommentAudioInfo.setFile(mCommentDetailAudioInfo.getFile());
                commentAudio.setVisibility(View.VISIBLE);
                mPresenter.downloadVoice(completeVoiceBean.getId(), TYPE_VOICE_COMMENT);
            } else {
                commentAudio.setVisibility(View.GONE);
            }
        }
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
        showLoadingDialog();
        mPresenter.getCompleteDetail(itemsBean.getHomeworkId(), itemsBean.getUserId());
        mPresenter.getCommentList(itemsBean.getCompleteId());
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


    private void initContent(CompleteDetailBean completeDetail) {
        completeId = completeDetail.getId();
        if (!TextUtils.isEmpty(completeDetail.getContents())) {
            mWorkContent.setText(EmotionUtils.convertNormalStringToSpannableString(completeDetail.getContents()));
        }
        if (completeDetail.isHasImage()) {
            imageAttrs.clear();
            List<CompleteDetailBean.CompleteImagesBean> completeImages = completeDetail.getCompleteImages();
            if (completeImages != null && completeImages.size() > 0) {
                for (CompleteDetailBean.CompleteImagesBean completeImage : completeImages) {
                    ImageAttrEntity imageAttrEntity = new ImageAttrEntity();
                    imageAttrEntity.setId(completeImage.getId());
                    imageAttrEntity.setBigSizeUrl(completeImage.getFilePath());
                    imageAttrEntity.setThumbnailUrl(completeImage.getFilePath());
                    imageAttrs.add(imageAttrEntity);
                }
                mImgLayout.setVisibility(View.VISIBLE);
                CommonGlideImageLoader.getInstance().displayNetImage(CorrectWorkActivity.this, completeImages.get(0).getFilePath(), mDetailPhoto);
                mPhotoNum.setText(String.valueOf(completeImages.size()) + "张");
            } else {
                mImgLayout.setVisibility(View.GONE);
            }
        } else {
            mImgLayout.setVisibility(View.GONE);
        }
        if (completeDetail.isHasVideo()) {
            mRecyclerVideo.setVisibility(View.VISIBLE);
            List<VideoDetailBean> videoDetailBeen = completeDetail.getCompleteVideos();
            mVideoAdapter.setList(videoDetailBeen);
        }
        if (completeDetail.isHasVoice()) {
            mPresenter.downloadVoice(completeDetail.getCompleteVoice().getId(), TYPE_VOICE_DETAIL);
            detailAudio.setVisibility(View.VISIBLE);
            mDetailVoiceDel.setVisibility(View.GONE);
            mAudioInfo.setTime(completeDetail.getCompleteVoice().getFileLength());
            detailVoiceTime = completeDetail.getCompleteVoice().getFileLength();
            mDetailVoiceTimeTotal.setText(TimeUtils.formatTime2(completeDetail.getCompleteVoice().getFileLength()));
        }

    }

    @Override
    public void showCompleteDetail(CompleteDetailBean completeDetailBean) {
        dismissLoadingDialog();
        mCompleteDetailBean = completeDetailBean;
        initContent(completeDetailBean);
        initComment(completeDetailBean);
    }


    @Override
    public void getCompleteDetailFail() {
        dismissLoadingDialog();
    }

    @Override
    public void downloadVoiceSuccess(File file, int type) {
        if (type == TYPE_VOICE_COMMENT_DETAIL) {
            mCommentDetailAudioInfo.setFile(file);
        } else if (type == TYPE_VOICE_DETAIL) {
            mAudioInfo.setFile(file);
        }


    }

    @Override
    public void commentProposalSuccess(List<CommentBean> commentBeens) {
        flowInfos = new ArrayList<>();
        for (CommentBean commentBeen : commentBeens) {
            commens.add(commentBeen);
            commensContent.add(commentBeen.getCommentContent());
            FlowInfo flowInfo = new FlowInfo(commentBeen.getCommentContent(), commentBeen.isSign());
            flowInfos.add(flowInfo);
        }
        mFlowLayout.setData(flowInfos, STYLE_CHECKBOX);
    }

    @Override
    public void commentHomeworkSuccess() {
        dismissLoadingDialog();
        mPresenter.getCompleteDetail(itemsBean.getHomeworkId(), itemsBean.getUserId());
    }


    @Subscribe
    public void onEvent(RecordStatusEvent event) {
        int state = event.status;
        switch (state) {
            case Const.START:
                mHandler.sendEmptyMessageDelayed(MSG_RECORD, 1000);

                break;
            case Const.SUCCESS:
            case Const.CANCEL:
            case Const.MAX:
                mHandler.removeCallbacksAndMessages(null);
                mPopupWindow.dismiss();
                mCommentVoiceTime.setText("00:00");
                mCommentProgress.setProgress(0);
                mCommentVoiceTimeTotal.setText(TimeUtils.formatTime2(time));
                mCommentVoiceControl.setText(getResources().getString(R.string.icon_bofang));
                commentAudio.setVisibility(View.VISIBLE);
                time = 0;
                break;

        }

    }

    @Subscribe
    public void onEvent(RecordInfoEvent event) {
        if (event != null) {
            mCommentAudioInfo.setFile(event.mFile);
            mCommentAudioInfo.setRecordType(event.mRecordType);
            mCommentAudioInfo.setTime(event.time / 1000);
        }

    }

    @Subscribe
    public void onEvent(WorkVideoEvent workVideoEvent) {
        VideoDetailBean videoListBean = workVideoEvent.videoDetailBean;
        WatchActionVideoActivity.start(this, videoListBean.getAliVideoId());
    }

    @Subscribe
    public void onEvent(FlowClickEvent event) {
        int position = event.position;
        boolean isCheck = event.isCheck;
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_RECORD:
                    time++;
                    mRecordTime.setText(TimeUtils.formatTime2(time) + "/");
                    mHandler.sendEmptyMessageDelayed(MSG_RECORD, 1000);
                    break;
                default:
                    break;
            }
        }
    };


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
        if (voiceType == TYPE_VOICE_COMMENT) {
            mCommentProgress.setProgress((int) ((event.position / (mCommentAudioInfo.getTime() * 1000.0)) * 100));
            mCommentVoiceTime.setText(TimeUtils.formatTime2(event.position / 1000));
        } else if (voiceType == TYPE_VOICE_DETAIL) {
            mDetailProgress.setProgress((int) ((event.position / (mAudioInfo.getTime() * 1000.0)) * 100));
            mDetailVoiceTime.setText(TimeUtils.formatTime2(event.position / 1000));
        } else if (voiceType == TYPE_VOICE_COMMENT_DETAIL) {
            mCommentDetailProgress.setProgress((int) ((event.position / (mCommentDetailAudioInfo.getTime() * 1000.0)) * 100));
            mCommentDetailVoiceTime.setText(TimeUtils.formatTime2(event.position / 1000));
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

                } else if (voiceType == TYPE_VOICE_COMMENT_DETAIL) {
                    mCommentDetailProgress.setProgress(100);

                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mCommentDetailProgress.setProgress(0);
                            mCommentDetailVoiceTime.setText("00:00");
                            mCommentDetailVoiceControl.setText(getResources().getString(R.string.icon_bofang));
                        }
                    }, 500);

                }
                break;
        }
    }

    /**
     * 录音
     */
    private void showRecordWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.popup_record_layout, null);
        mRecordTime = view.findViewById(R.id.record);
        mTip = view.findViewById(R.id.tip);
        mRecordLayout = view.findViewById(R.id.record_layout);
        mRecord = view.findViewById(R.id.record_img);

        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setBackgroundAlpha(0.5f);//设置屏幕透明度
        mRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeRecordPop(isRecord);
                isRecord = !isRecord;
            }
        });
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
                if (mPresenter.getRecordState()) {
                    mPresenter.stopRecord();
                }
            }
        });
        mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);
        mPopupWindow.showAtLocation(mLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }


    public void changeRecordPop(boolean record) {
        if (record) {
            mTip.setVisibility(View.VISIBLE);
            mRecordLayout.setVisibility(View.GONE);
            mRecord.setImageDrawable(getResources().getDrawable(R.mipmap.icon_record_start));
            mPresenter.stopRecord();
            mPopupWindow.dismiss();
        } else {
            mRecordTime.setText("00:00/");
            mTip.setVisibility(View.GONE);
            mRecordLayout.setVisibility(View.VISIBLE);
            mRecord.setImageDrawable(getResources().getDrawable(R.mipmap.icon_record_stop));
            mPresenter.startRecord();
        }
    }

    @Override
    public void showContent(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        dismissLoadingDialog();
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 屏幕透明度0.0-1.0 1表示完全不透明
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.stopAudio();
    }
}
