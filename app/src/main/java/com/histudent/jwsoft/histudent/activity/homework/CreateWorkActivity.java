package com.histudent.jwsoft.histudent.activity.homework;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
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
import com.histudent.jwsoft.histudent.adapter.ImgAdapter;
import com.histudent.jwsoft.histudent.adapter.VideoAdapter;
import com.histudent.jwsoft.histudent.adapter.decoration.UploadItemDecoration;
import com.histudent.jwsoft.histudent.base.BaseActivity;
import com.histudent.jwsoft.histudent.bean.UploadAuthBean;
import com.histudent.jwsoft.histudent.bean.homework.CommonSubjectBean;
import com.histudent.jwsoft.histudent.bean.homework.CreateWorkBean;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkSelectGroupL0Bean;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkSelectGroupL1Bean;
import com.histudent.jwsoft.histudent.body.hiworld.activity.WatchEssayVideoActivity;
import com.histudent.jwsoft.histudent.commen.helper.PictureTailorHelper;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.keyword.fragment.EmotionMainFragment;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.comment2.utils.TimeUtils;
import com.histudent.jwsoft.histudent.constant.Const;
import com.histudent.jwsoft.histudent.constant.ParamKeys;
import com.histudent.jwsoft.histudent.constant.TransferKeys;
import com.histudent.jwsoft.histudent.entity.AudioInfo;
import com.histudent.jwsoft.histudent.entity.AudioPlayEvent;
import com.histudent.jwsoft.histudent.entity.AudioPlayStatusEvent;
import com.histudent.jwsoft.histudent.entity.ImgAddEvent;
import com.histudent.jwsoft.histudent.entity.RecordInfoEvent;
import com.histudent.jwsoft.histudent.entity.RecordStatusEvent;
import com.histudent.jwsoft.histudent.entity.VideoInfoEntity;
import com.histudent.jwsoft.histudent.entity.WorkImgDeleteEvent;
import com.histudent.jwsoft.histudent.entity.WorkReceiverEvent;
import com.histudent.jwsoft.histudent.entity.WorkVideoDeleteEvent;
import com.histudent.jwsoft.histudent.entity.WorkVideoPlayEvent;
import com.histudent.jwsoft.histudent.presenter.homework.CreateWorkPresenter;
import com.histudent.jwsoft.histudent.presenter.homework.contract.CreateWorkContract;
import com.histudent.jwsoft.histudent.tool.CommonTool;
import com.histudent.jwsoft.histudent.tool.ToastTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2017/10/23.
 */

public class CreateWorkActivity extends BaseActivity<CreateWorkPresenter> implements CreateWorkContract.View {

    @BindView(R.id.title_middle_text)
    TextView mTitle;
    @BindView(R.id.title_right_text)
    TextView mRightText;
    @BindView(R.id.recycler_img)
    RecyclerView mImgRecycler;
    @BindView(R.id.recycler_video)
    RecyclerView mVideoRecycler;
    @BindView(R.id.control_photo)
    IconView mPhotoBtn;
    @BindView(R.id.control_record)
    IconView mRecordBtn;
    @BindView(R.id.control_emotion)
    IconView mEmotionBtn;
    @BindView(R.id.layout)
    LinearLayout mLayout;
    @BindView(R.id.scroll_view)
    ScrollView mScrollView;
    @BindView(R.id.work_subject)
    TextView mSubject;
    @BindView(R.id.work_subject_layout)
    LinearLayout mSubjectLayout;
    @BindView(R.id.work_receiver)
    TextView mReceiver;
    @BindView(R.id.work_receiver_layout)
    LinearLayout mReceiverLayout;
    @BindView(R.id.work_online)
    CheckBox mOnline;
    @BindView(R.id.work_content)
    EditText mWorkContent;
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
    private List<HomeworkSelectGroupL0Bean> mWorkGroupL0;
    private int mDeletePosition;

    @OnClick({R.id.title_left_image, R.id.title_right_text, R.id.control_photo, R.id.control_record,
            R.id.control_emotion, R.id.work_subject_layout, R.id.work_receiver_layout,
            R.id.delete_voice, R.id.notices_voice_control})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_image:
                finish();
                break;
            case R.id.title_right_text:
                if (TextUtils.isEmpty(subjectId)) {
                    ToastTool.showCommonToast(getString(R.string.please_select_subject));
                    return;
                }
                if (receivers.size() == 0) {
                    ToastTool.showCommonToast(getString(R.string.please_select_receiver));
                    return;
                }
                if (!TextUtils.isEmpty(mWorkContent.getText()) || (videoIds != null && videoIds.size() > 0) || mAudioInfo.getFile() != null || (imgFiles != null && imgFiles.size() > 0)) {

                    String videoIdsStr = "";
                    if (videoIds != null && videoIds.size() > 0) {
                        videoIdsStr = new Gson().toJson(videoIds);
                    }
                    final String receiverStr = new Gson().toJson(receivers);
                    showLoadingDialog();
                    mPresenter.createHomeWork(subjectId, receiverStr, mWorkContent.getText().toString(), mOnline.isChecked(), videoIdsStr, mAudioInfo, imgFiles);
                } else {
                    ToastTool.showCommonToast(getString(R.string.please_input_homework_content));
                }

                break;
            case R.id.control_photo://图片或视频
                SystemUtil.hideSoftKeyboard(CreateWorkActivity.this);
                switch (type) {
                    case Const.WORK_VOICE://音频
                    case Const.WORK_TEXT://文字
                        if (imgUrls != null && imgUrls.size() > 0) {
                            mClipHelper.selectPictures(this, imgUrls, 9);
                        } else if (videoIds != null && videoIds.size() > 0) {
                            if (videoIds.size() < 4) {
                                PictureTailorHelper.getInstance(false).takePhotoAndAudio(CreateWorkActivity.this, REQ_CODE_VIDEO, 2);
                            } else {
                                showContent("视频最多4个");
                            }
                        } else {
                            showPhotoWindow();
                        }
                        break;
                    case Const.WORK_VEDIO://视频

                        if (videoIds.size() < 4) {
                            PictureTailorHelper.getInstance(false).takePhotoAndAudio(CreateWorkActivity.this, REQ_CODE_VIDEO, 2);
                        } else {
                            showContent("视频最多4个");
                        }
                        break;

                    case Const.WORK_PHOTO://图片
                        mClipHelper.selectPictures(this, imgUrls, 9);
                        break;
                }
                break;
            case R.id.control_record://录音
                SystemUtil.hideSoftKeyboard(CreateWorkActivity.this);
                showRecordWindow();
                break;
            case R.id.control_emotion:

                break;
            case R.id.work_subject_layout://科目
                final Intent intent = new Intent();
                intent.setClass(CreateWorkActivity.this, WorkSubjectManageActivity.class);
                intent.putExtra(ParamKeys.SUBJECT_ID, subjectId);
                startActivityForResult(intent, REQ_SUBJECT);
                break;
            case R.id.work_receiver_layout://接收人
                Intent receiver = new Intent();
                receiver.setClass(CreateWorkActivity.this, WorkSelectReceiverPersonActivity.class);
                EventBus.getDefault().postSticky(new WorkReceiverEvent(mWorkGroupL0));
                startActivityForResult(receiver, REQ_RECEIVER);
                break;
            case R.id.delete_voice://删除语音
                mVoiceLayout.setVisibility(View.GONE);
                mPresenter.stopAudio();
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
        }
    }


    private TextView mRecordTime;
    private TextView mTip;
    private LinearLayout mRecordLayout;
    private PopupWindow mPopupWindow;
    private ImageView mRecord;
    private boolean isRecord = false;
    private static final int MSG_RECORD = 1;
    private PictureTailorHelper mPictureTailorHelper;
    private EmotionMainFragment emotionMainFragment;
    private GridLayoutManager mImgManager;
    private LinearLayoutManager mVideoManager;
    private ImgAdapter mImgAdapter;
    private VideoAdapter mVideoAdapter;
    private int type = Const.WORK_TEXT;
    private final static int REQ_CODE_VIDEO = 1000;
    private List<VideoInfoEntity> mVideos = new ArrayList<>();
    private List<String> videoIds = new ArrayList<>();
    private String currentFileName;
    private PictureTailorHelper mClipHelper;
    private List<String> imgUrls = new ArrayList<>();
    private List<File> imgFiles = new ArrayList<>();
    private int time;
    private AudioInfo mAudioInfo = new AudioInfo();
    private PowerManager powerManager = null;
    private PowerManager.WakeLock wakeLock = null;
    private static final int REQ_SUBJECT = 2001;
    private static final int REQ_RECEIVER = 2002;
    private String subjectId;
    private final List<String> receivers = new ArrayList<>();
    private long voiceTime;


    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_create_homework;
    }

    @Override
    protected void init() {
        initView();
        initIntent();
        initOther();
    }

    private void initOther() {
        mClipHelper = PictureTailorHelper.getInstance();
        initLock();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        switch (type) {
            case Const.WORK_TEXT://文字
                break;
            case Const.WORK_VEDIO://视频
                PictureTailorHelper.getInstance(false).takePhotoAndAudio(CreateWorkActivity.this, REQ_CODE_VIDEO, 2);
                break;
            case Const.WORK_VOICE://音频
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showRecordWindow();
                    }
                }, 1000);

                break;
            case Const.WORK_PHOTO://图片
                mClipHelper.selectPictures(this, imgUrls, 9);
                break;
        }

    }

    private void initIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            type = intent.getIntExtra("type", Const.WORK_TEXT);
        }
    }

    private void initView() {
        initTitle();
        initRecycler();
        initEmotionMainFragment();
        initInputSoftKeyboard();
    }

    private void initInputSoftKeyboard() {
//        获取焦点后 自动弹出键盘
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) mWorkContent.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(mWorkContent, 0);
            }

        }, 100);
    }

    private void initRecycler() {
        mImgManager = new GridLayoutManager(this, 4);
        mVideoManager = new LinearLayoutManager(this);
        mImgAdapter = new ImgAdapter(this);
        mImgAdapter.setAdd(true);
        mImgAdapter.setDelete(true);
        mVideoAdapter = new VideoAdapter(this);
        mImgRecycler.setLayoutManager(mImgManager);
        mImgRecycler.setAdapter(mImgAdapter);
        mImgRecycler.addItemDecoration(new UploadItemDecoration(this));
        mVideoRecycler.setLayoutManager(mVideoManager);
        mVideoRecycler.setAdapter(mVideoAdapter);
    }

    private void initTitle() {
        mTitle.setText(R.string.publish_homework);
        mRightText.setText(R.string.submit);
        mRightText.setTextColor(ContextCompat.getColor(this, R.color._28ca7e));
    }

    private void initLock() {
        powerManager = (PowerManager) this.getSystemService(this.POWER_SERVICE);
        wakeLock = this.powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "My Lock");
    }

    @Subscribe
    public void onEvent(ImgAddEvent event) {
        mClipHelper.selectPictures(this, imgUrls, 9);
    }

    @Subscribe
    public void onEvent(RecordStatusEvent event) {
        int state = event.status;
        switch (state) {
            case Const.START:
                mHandler.sendEmptyMessageDelayed(MSG_RECORD,1000);
                break;
            case Const.SUCCESS:
            case Const.CANCEL:
            case Const.MAX:
                mHandler.removeCallbacksAndMessages(null);
                mPopupWindow.dismiss();
                voiceTime = time;
                time = 0;
                mVoiceLayout.setVisibility(View.VISIBLE);
                break;

        }

    }


    @Subscribe
    public void onEvent(WorkVideoDeleteEvent event) {
        int position = event.position;
        if (videoIds != null && videoIds.size() > position) {
            videoIds.remove(position);
        }
    }

    @Subscribe
    public void onEvent(WorkVideoPlayEvent event) {
        VideoInfoEntity videoInfo = event.videoInfoEntity;
        mDeletePosition = event.position;
        WatchEssayVideoActivity.start(CreateWorkActivity.this, videoInfo.getFileName(), 10);
    }

    @Subscribe
    public void onEvent(RecordInfoEvent event) {
        if (event != null) {
            mAudioInfo.setFile(event.mFile);
            mAudioInfo.setRecordType(event.mRecordType);
            mAudioInfo.setTime(event.time);
            mVoiceTimeTotal.setText(TimeUtils.formatTime2(event.time / 1000));
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AudioPlayEvent event) {
        mProgress.setProgress((int) ((event.position / (voiceTime * 1000.0)) * 100));
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

    @Subscribe
    public void onEvent(WorkImgDeleteEvent event) {
        int position = event.position;
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


    @Subscribe
    public void onEvent(WorkReceiverEvent event) {
        mWorkGroupL0 = event.mWorkGroupL0;
        StringBuilder stringBuilder = new StringBuilder();
        if (mWorkGroupL0 != null && mWorkGroupL0.size() > 0) {
            for (HomeworkSelectGroupL0Bean homeworkSelectGroupL0Bean : mWorkGroupL0) {
                if (homeworkSelectGroupL0Bean.isCheck()) {
                    stringBuilder.append(homeworkSelectGroupL0Bean.getGradeName());
                    receivers.add(homeworkSelectGroupL0Bean.getTeamId());
                } else {
                    stringBuilder.append(homeworkSelectGroupL0Bean.getGradeName());
                }
                List<HomeworkSelectGroupL1Bean> homeworkSelectGroupL1Beens = homeworkSelectGroupL0Bean.getSubItems();
                if (homeworkSelectGroupL1Beens != null && homeworkSelectGroupL1Beens.size() > 0) {
                    for (HomeworkSelectGroupL1Bean homeworkSelectGroupL1Bean : homeworkSelectGroupL1Beens) {
                        if (homeworkSelectGroupL1Bean.isCheck()) {
                            stringBuilder.append(homeworkSelectGroupL1Bean.getGroupDivideName() + ",");
                            receivers.add(homeworkSelectGroupL0Bean.getTeamId());
                        }
                    }

                }
            }
        }
        if (!TextUtils.isEmpty(stringBuilder)) {
            if (stringBuilder.toString().endsWith(",")) {
                mReceiver.setText(stringBuilder.subSequence(0, stringBuilder.length() - 1));
            } else {
                mReceiver.setText(stringBuilder);
            }

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        wakeLock.acquire();
    }

    @Override
    protected void onPause() {
        super.onPause();
        wakeLock.release();
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

        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundAlpha(0.5f);//设置屏幕透明度
        mPopupWindow.setFocusable(true);// 点击空白处时，隐藏掉pop窗口
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


    /**
     * 初始化拍照选择照片的PopWindow
     */
    private void showPhotoWindow() {

        List<String> buttonName = new ArrayList<>();
        List<Integer> listColor = new ArrayList<>();
        listColor.add(Color.rgb(51, 51, 51));
        listColor.add(Color.rgb(51, 51, 51));
        buttonName.add("拍摄视频");
        buttonName.add("从相册中获取");
        ReminderHelper.getIntentce().showTopMenuDialog(this, "", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.btn_01:
                        PictureTailorHelper.getInstance(false).takePhotoAndAudio(CreateWorkActivity.this, REQ_CODE_VIDEO, 2);
                        break;
                    case R.id.btn_02:
                        mClipHelper.selectPictures(CreateWorkActivity.this, imgUrls, 9);
                        break;
                }
            }
        }, buttonName, listColor, false);

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
        closeDialog();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == -10) {
            //从删除页面返回
            mVideos.remove(mDeletePosition);
            mVideoAdapter.notifyDataSetChanged();
            return;
        }
        switch (requestCode) {
            case REQ_CODE_VIDEO:
                if (resultCode == -1) {//摄像返回
                    if (data != null) {
                        String fileName = data.getStringExtra("file");
                        long duration = (int) data.getLongExtra("duration", 0);
                        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(fileName, MediaStore.Images.Thumbnails.MINI_KIND);
                        String cover = CommonTool.convertIconToString(bitmap);
                        VideoInfoEntity videoInfoEntity = new VideoInfoEntity();
                        videoInfoEntity.setFileName(fileName);
                        videoInfoEntity.setDuration(duration);
                        videoInfoEntity.setCover(cover);
                        mVideos.add(videoInfoEntity);

                        currentFileName = fileName;
                        mPresenter.getVodUploadAuth(new File(fileName).length());
                    }
                }
                break;
            case PictureTailorHelper.PHOTO_REQUEST_GALLERYS://图片选择
                if (data != null) {
                    imgUrls = (List<String>) data.getSerializableExtra("return");
                    mImgAdapter.setList(imgUrls);
                    mPresenter.compressImg(CreateWorkActivity.this, imgUrls);


                }

                break;
            case REQ_SUBJECT:
                if (resultCode == TransferKeys.ConstantNum.NUM_2000) {
                    if (data != null) {
                        CommonSubjectBean commonSubjectBean = data.getParcelableExtra(TransferKeys.GROUP_ID);
                        if (commonSubjectBean != null) {
                            subjectId = commonSubjectBean.getSubjectId();
                            if (!TextUtils.isEmpty(commonSubjectBean.getSubjectName())) {
                                mSubject.setText(commonSubjectBean.getSubjectName());
                            }
                        }
                    }
                }

                break;
            case REQ_RECEIVER:

                break;
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
        emotionMainFragment.bindToEditText(mWorkContent);
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
    public void showVideoList(String videoId) {
        videoIds.add(videoId);
        //上传视频耗时操作  时间过长会导致UI刷新阻塞 使用以下方法进行刷新
        runOnUiThread(() -> mVideoAdapter.setList(mVideos));
    }

    @Override
    public void uploadFile(UploadAuthBean uploadAuthBean) {

        mPresenter.uploadVideo(this, currentFileName, uploadAuthBean);
    }

    @Override
    public void showDialog() {
        showLoadingDialog();
    }

    @Override
    public void closeDialog() {
        dismissLoadingDialog();
    }

    @Override
    public void createWorkSucceed(CreateWorkBean createWorkBean) {
        closeDialog();
        showContent(getString(R.string.publish_homework_success));
        Intent intent = new Intent(this, WorkDetailTeacherActivity.class);
        intent.putExtra("homeworkId", createWorkBean.getClassHomeWorkList().get(0).getHomeWorkId());
        startActivity(intent);
        finish();
    }

    @Override
    public void showImgList(List<File> files) {
        imgFiles = files;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }

    }
}
