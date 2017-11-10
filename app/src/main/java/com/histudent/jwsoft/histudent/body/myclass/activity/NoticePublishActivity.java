package com.histudent.jwsoft.histudent.body.myclass.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.vod.upload.common.utils.StringUtil;
import com.bigkoo.pickerview.TimePickerView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.hiworld.adapter.SyncClassAdapter;
import com.histudent.jwsoft.histudent.body.hiworld.adapter.UploadImageRecyclerViewAdapter;
import com.histudent.jwsoft.histudent.body.mine.model.UserClassListModel;
import com.histudent.jwsoft.histudent.call.IPermissionCallBackListener;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.ImageBrowserActivity;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.enums.ShowImageType;
import com.histudent.jwsoft.histudent.commen.helper.PictureTailorHelper;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.helper.WebViewHelper;
import com.histudent.jwsoft.histudent.commen.keyword.fragment.EmotionMainFragment;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.photo.utils.ImageUtils;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.DataUtils;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.entity.AudioPlayEvent;
import com.histudent.jwsoft.histudent.entity.AudioPlayStatusEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liuguiyu-pc on 2017/5/3.
 * 发布通知
 */

public class NoticePublishActivity extends BaseActivity {

    private TextView title_left, title, title_right, delet_voice, record_time, mRecordTimeUp, mTip;
    private EditText notice_edit;
    private LinearLayout mRecordLayout;
    private ArrayList<UserClassListModel> classListModels;//同步班级集合
    private SyncClassAdapter syncClassAdapter;//同步班级适配
    private ArrayList<String> pictures_list;//存放选中图片的路径
    private List<ActionListBean.ImagesBean> listImageBean;
    private UploadImageRecyclerViewAdapter addImageAdapter;
    private RecyclerView recyclerView;
    private UploadImageRecyclerViewAdapter.OnRecyclerViewOnClickListener listener;
    private NoticePublishActivity.VoiceInfo voiceInfo = null;
    private WebViewHelper helper;
    private MyHandler handler;
    private int showTime;
    private boolean isRecord;
    private int time;
    private String classId;
    private PictureTailorHelper mPictureTailorHelper;
    private PopupWindow mPopupWindow;
    private boolean onClickAble;
    private PowerManager powerManager = null;
    private PowerManager.WakeLock wakeLock = null;

    private Calendar endDate = Calendar.getInstance();
    private TimePickerView pvTime;
    private String noticeTimeStr;
    private EmotionMainFragment emotionMainFragment;
    private boolean isStart;
    @BindView(R.id.notices_send)
    CheckBox mCheckBox;
    @BindView(R.id.ll_notice_time)
    LinearLayout mNoticeTimeLayout;
    @BindView(R.id.notice_time)
    TextView mNoticeTime;
    @BindView(R.id.ll_layout)
    LinearLayout mLayout;
    @BindView(R.id.tv_emotion)
    IconView mEmotion;
    @BindView(R.id.notices_voice)
    FrameLayout mVoice;
    @BindView(R.id.notices_voice_control)
    IconView mVoiceControl;
    @BindView(R.id.notices_voice_time)
    TextView mVoiceTime;
    @BindView(R.id.notices_voice_time_total)
    TextView mVoiceTimeTotal;
    @BindView(R.id.notices_voice_progress)
    SeekBar mVoiceProgress;


    @OnClick({R.id.ll_notice_time, R.id.title_left, R.id.notice_takePhoto, R.id.notice_startRecord, R.id.title_right, R.id.notices_voice_control})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_notice_time:
                SystemUtil.hideSoftKeyboard(NoticePublishActivity.this);
                pvTime.show(view);
                break;
            case R.id.title_left:
                finish();
                break;


            case R.id.notice_takePhoto://拍照
                initPopWindow();
                break;

            case R.id.notice_startRecord://录音
                SystemUtil.hideSoftKeyboard(NoticePublishActivity.this);
                record();
                break;

            case R.id.title_right://发布通知
                if (onClickAble)
                    publishNotice();
                break;

            case R.id.notices_voice_control:
                if (helper.isPlaying()) {
                    mVoiceControl.setText(getResources().getString(R.string.icon_bofang));
                    helper.pauseVoice();
                } else {
                    mVoiceControl.setText(getResources().getString(R.string.icon_zanting));
                    helper.playVoice_o(NoticePublishActivity.this, voiceInfo.getId());
                }
                break;
        }
    }

    public static void start(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, NoticePublishActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_notices_publish;
    }

    @Override
    public void initView() {
        classId = HiCache.getInstance().getClassDetailInfo().getClassId();
        mPictureTailorHelper = PictureTailorHelper.getInstance();
        handler = new MyHandler();
        classListModels = new ArrayList<>();
        pictures_list = new ArrayList<>();
        listImageBean = new ArrayList();
        title_left = (TextView) findViewById(R.id.title_left_text);
        title = (TextView) findViewById(R.id.title_middle_text);
        title_right = (TextView) findViewById(R.id.title_right_text);
        recyclerView = (RecyclerView) findViewById(R.id.notice_recycler);
        notice_edit = (EditText) findViewById(R.id.notice_edit);
        delet_voice = (IconView) findViewById(R.id.delete_voice);
        syncClassAdapter = new SyncClassAdapter(classListModels, this);
        helper = WebViewHelper.getIntence();
        mVoiceProgress.setEnabled(false);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        mCheckBox.setChecked(false);
        mNoticeTimeLayout.setVisibility(View.GONE);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isCanCommint();
                if (b) {
                    mNoticeTimeLayout.setVisibility(View.VISIBLE);
                } else {
                    mNoticeTimeLayout.setVisibility(View.GONE);
                }
            }
        });
        initTimePicker();
        initEmotionMainFragment();
        initLock();
        showSysKeyboard();
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

    private void initLock() {
        powerManager = (PowerManager) this.getSystemService(this.POWER_SERVICE);
        wakeLock = this.powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "My Lock");
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
        emotionMainFragment.bindToEditText(notice_edit);
        emotionMainFragment.bindToContentView(mLayout);
        emotionMainFragment.setExchangeButton(mEmotion);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in thefragment_container view with this fragment,
        // and add the transaction to the backstack
        transaction.replace(R.id.fl_emotionview_main, emotionMainFragment);
//        transaction.addToBackStack(null);
        //提交修改
        transaction.commit();
    }

    private void initTimePicker() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        endDate.set(2099, 11, 28);
        //时间选择器
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                if (date.before(new Date())) {
                    Toast.makeText(NoticePublishActivity.this, "发送时间要大于当前时间", Toast.LENGTH_SHORT).show();
                    return;
                }
                noticeTimeStr = getTime(date);
                mNoticeTime.setText(noticeTimeStr);
                isCanCommint();
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, true, true, false})
                .setLabel("年", "月", "日", "时", "分", "")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(21)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();
    }


    //返回选择的时间，用于时间的回显
    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    @Override
    public void doAction() {
        super.doAction();
        title_left.setText("取消");
        title.setText("发布通知");
        title_right.setText("提交");
        title_right.setTextColor(getResources().getColor(R.color.click_false));
        onClickAble = false;
        getSyncClassDate();

        helper.init(this, null);

        //选择点击图片的时间监听
        listener = new UploadImageRecyclerViewAdapter.OnRecyclerViewOnClickListener() {
            @Override
            public void setOnItemClickListener(View v, final int postion) {

                if (postion == pictures_list.size() - 1 && pictures_list.get(postion).equals("add")) {

                    checkTakePhotoPermission(new IPermissionCallBackListener() {
                        @Override
                        public void doAction() {
                            initPopWindow();
                        }
                    });

                } else {

                    if (pictures_list != null && pictures_list.size() > 1) {
                        ActionListBean.ImagesBean imagesBean;
                        listImageBean.clear();
                        pictures_list.remove("add");
                        for (String picId : pictures_list) {
                            if (!"add".equals(picId)) {
                                imagesBean = new ActionListBean.ImagesBean();
                                File f = new File(picId);
                                imagesBean.setBigSizeUrl(picId);
                                imagesBean.setName(f.getName());
                                imagesBean.setThumbnailUrl(picId);
                                listImageBean.add(imagesBean);
                            }
                        }
                        ImageBrowserActivity.start(NoticePublishActivity.this, postion, 100, (ArrayList<ActionListBean.ImagesBean>) listImageBean,
                                ShowImageType.SCANF, null);
                    }
                }
            }
        };
        addImageAdapter = new UploadImageRecyclerViewAdapter(pictures_list, listener);
        recyclerView.setAdapter(addImageAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));


        delet_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voiceInfo = null;
                showTime = 0;
                mVoice.setVisibility(View.GONE);
                helper.stopVoice();
                isCanCommint();
            }
        });

        notice_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isCanCommint();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 400 && resultCode == 200) {
            if (data != null && data.getSerializableExtra("classList") != null) {
                classListModels.clear();
                classListModels.addAll(((ArrayList<UserClassListModel>) data.getSerializableExtra("classList")));
                syncClassAdapter.notifyDataSetChanged();
            }

        } else if (requestCode == PictureTailorHelper.PHOTO_REQUEST_TAKEPHOTO) {//拍照返回
            if (resultCode == -1) {
                pictures_list.add(mPictureTailorHelper.photo_path.getAbsolutePath());
                pictures_list.add("add");
                addImageAdapter.notifyDataSetChanged();
            }
        } else if (requestCode == 1001) {//相册选择返回
            if (resultCode == 200 && data != null) {
                pictures_list.clear();
                pictures_list.addAll(data.getStringArrayListExtra("return"));
            }

            if (pictures_list != null && pictures_list.size() < 9) {
                pictures_list.add("add");
            }
            addImageAdapter.notifyDataSetChanged();
        } else if (requestCode == 100) {

            if (data != null && resultCode == 200) {
                pictures_list.clear();
                pictures_list.addAll(data.getStringArrayListExtra("return"));
            }

            if (pictures_list != null && pictures_list.size() < 9) {
                pictures_list.add("add");
            }
            addImageAdapter.notifyDataSetChanged();
        }

        isCanCommint();

    }

    @Subscribe
    public void onEvent(NoticePublishActivity.VoiceInfo info) {
        voiceInfo = info;
        showTime = info.getTime();
        mVoice.setVisibility(View.VISIBLE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AudioPlayEvent event) {
        mVoiceProgress.setProgress((int) ((event.position / (time * 1000.0)) * 100));
        mVoiceTime.setText(formatTime2(event.position / 1000));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AudioPlayStatusEvent event) {
        mVoiceProgress.setProgress(100);
        mVoiceProgress.setProgress(0);
        mVoiceTime.setText("00:00");
        mVoiceControl.setText(getResources().getString(R.string.icon_bofang));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.pauseVoice();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 获取默认同步的班级信息
     */
    public void getSyncClassDate() {
        DataUtils.GetUserSyncClassList(this, classListModels, syncClassAdapter);
    }

    /**
     * 发布通知
     */
    private void publishNotice() {
        helper.pauseVoice();
        showLoadingImage(this, LoadingType.FLOWER);
        String content = notice_edit.getText().toString();
        String classIds = getSyncClassIds(classListModels).toString();
        String voiceFile = voiceInfo == null ? "" : voiceInfo.getPath();

        int size = pictures_list.size();
        if (pictures_list != null && pictures_list.size() > 1)
            pictures_list.remove(size - 1);
        Map<String, Object> map = new TreeMap<>();
        map.put("content", content);
        map.put("classIds", classIds);
        if (!TextUtils.isEmpty(voiceFile)) {
            map.put("voiceFile", voiceFile);
        }

        List<String> imageFiles = ImageUtils.comPressBitmaps(NoticePublishActivity.this, pictures_list, 80);
        if (imageFiles != null && imageFiles.size() > 0) {
            map.put("imageFile", imageFiles);
        }

        map.put("ext", voiceInfo == null ? "" : voiceInfo.getTime());
        map.put("noticeTime", mNoticeTime.getText().toString());

        HiStudentHttpUtils.postVoidAndImageByOKHttp(this, map, HistudentUrl.createNotice_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                closeLoadingImage();
                ReminderHelper.getIntentce().ToastShow_with_image(NoticePublishActivity.this, "发布成功", R.string.icon_enter);
                setResult(200);
                finish();

            }

            @Override
            public void onFailure(String errorMsg) {
                closeLoadingImage();
            }
        });

    }

    private void singleNotice() {
//        Map<String, Object> map = new TreeMap<>();
//        map.put("noticeId", content);
//        map.put("userId", classIds);
//
    }

    private void isCanCommint() {
        if (mCheckBox.isChecked() && TextUtils.isEmpty(mNoticeTime.getText().toString())) {
            title_right.setTextColor(getResources().getColor(R.color.click_false));
            onClickAble = false;
            return;
        }
        if (StringUtil.isEmpty(notice_edit.getText().toString()) && voiceInfo == null && pictures_list.size() == 0) {
            title_right.setTextColor(getResources().getColor(R.color.click_false));
            onClickAble = false;
        } else {
            title_right.setTextColor(getResources().getColor(R.color.click_true));
            onClickAble = true;
        }
    }

    //获取同步班级的id的集合
    public String getSyncClassIds(ArrayList<UserClassListModel> classList) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");

//        if (classList != null && classList.size() > 0) {
//            for (UserClassListModel model : classList) {
//                builder.append("\"").append(model.getClassId()).append("\"").append(",");
//            }
//        }

        builder.append("\"").append(classId).append("\"");
        builder.append("]");
        return builder.toString();
    }

    /**
     * 初始化拍照选择照片的PopWindow
     */
    private void initPopWindow() {


        List<String> buttonName = new ArrayList<>();
        List<Integer> listColor = new ArrayList<>();
        listColor.add(Color.rgb(51, 51, 51));
        listColor.add(Color.rgb(51, 51, 51));
        buttonName.add("拍照");
        buttonName.add("从相册中获取");
        ReminderHelper.getIntentce().showTopMenuDialog(this, "", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.btn_01:
                        pictures_list.remove("add");
                        mPictureTailorHelper.takePhoto(NoticePublishActivity.this);
                        break;
                    case R.id.btn_02:
                        pictures_list.remove("add");
                        mPictureTailorHelper.selectPictures(NoticePublishActivity.this, pictures_list, 9, null);
                        break;
                }
            }
        }, buttonName, listColor, false);

    }

    public static class VoiceInfo {
        private String id;
        private int time;
        private String path;

        public VoiceInfo(String id, int time, String path) {
            this.id = id;
            this.time = time;
            this.path = path;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }

    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 2:
                    if (!isRecord) {
                        helper.startRecord_o();
                        isRecord = true;
                        time = 0;
                        sendEmptyMessageDelayed(3, 1000);
                    }
                    break;

                case 3:
                    if (isRecord) {
                        if (record_time != null) {
                            time++;
                            if (time == 5 * 60) {
                                isRecord = false;
                                handler.sendEmptyMessage(4);
                                isStart = false;
                                mPopupWindow.dismiss();
                            }
                            record_time.setText(formatTime2(time) + "/");
                        }
                        sendEmptyMessageDelayed(3, 1000);
                    }
                    break;

                case 4:
                    isRecord = false;
                    mPopupWindow.dismiss();
                    helper.startRecord_o();
                    mVoiceTimeTotal.setText(formatTime2(time));
                    break;

            }
        }
    }

    private String formatTime(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        String ms = formatter.format(5 * 60 * 1000 - time * 1000);
        Log.d(getClass().getSimpleName(), ms);
        return ms;
    }


    private String formatTime2(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        String ms = formatter.format(time * 1000);
        Log.d(getClass().getSimpleName(), ms);
        return ms;
    }

    /**
     * 录音
     */
    private void record() {
        View view = LayoutInflater.from(this).inflate(R.layout.popup_record_layout, null);
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.layout);
        record_time = (TextView) view.findViewById(R.id.record);
        mTip = (TextView) view.findViewById(R.id.tip);
        mRecordLayout = (LinearLayout) view.findViewById(R.id.record_layout);
        ImageView record = (ImageView) view.findViewById(R.id.record_img);

        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundAlpha(0.5f);//设置屏幕透明度
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isStart) {
                    handler.sendEmptyMessage(2);
                    record_time.setText("00:00/");
                    mTip.setVisibility(View.GONE);
                    mRecordLayout.setVisibility(View.VISIBLE);
                    record.setImageDrawable(getResources().getDrawable(R.mipmap.icon_record_stop));
                } else {
                    mTip.setVisibility(View.VISIBLE);
                    mRecordLayout.setVisibility(View.GONE);
                    handler.sendEmptyMessage(4);
                    record.setImageDrawable(getResources().getDrawable(R.mipmap.icon_record_start));
                }
                isStart = !isStart;
            }
        });
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
                if (helper.isRecording()){
                    handler.sendEmptyMessage(4);
                    isStart =false;
                }

            }
        });
        mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);
        mPopupWindow.showAtLocation(mLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
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

    /**
     * 调用android系统键盘
     */
    private void showSysKeyboard() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) notice_edit.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(notice_edit, 0);
            }

        }, 500);
    }

}
