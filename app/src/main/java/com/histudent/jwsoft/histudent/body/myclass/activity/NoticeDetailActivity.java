package com.histudent.jwsoft.histudent.body.myclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.homepage.helper.HomePageHelper;
import com.histudent.jwsoft.histudent.body.myclass.adapter.LikePersionAdapter;
import com.histudent.jwsoft.histudent.body.myclass.adapter.NoticeAdapter;
import com.histudent.jwsoft.histudent.body.myclass.bean.NoticeDetailBean;
import com.histudent.jwsoft.histudent.call.IPermissionCallBackListener;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.ImageBrowserActivity;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.enums.ShowImageType;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.helper.WebViewHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.commen.view.MyGifView;
import com.histudent.jwsoft.histudent.commen.view.MyGridView;
import com.histudent.jwsoft.histudent.commen.view.MyListView2;
import com.histudent.jwsoft.histudent.comment2.utils.EmotionUtils;
import com.histudent.jwsoft.histudent.entity.AudioPlayEvent;
import com.histudent.jwsoft.histudent.entity.AudioPlayStatusEvent;
import com.histudent.jwsoft.histudent.entity.ImageAttrEntity;
import com.histudent.jwsoft.histudent.entity.UnReadUserEvent;
import com.histudent.jwsoft.histudent.info.persioninfo.activity.PersonCenterActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by liuguiyu-pc on 2017/5/3.
 * 通知详情
 */

public class NoticeDetailActivity extends BaseActivity {

    private TextView title, notice_name, notice_time, notice_already_read, notice_no_read, notice_content, notice_title;
    private View notice_already_read_line, notice_no_read_line;
    private MyListView2 listView;
    private List<Object> itemsBeens;
    private String noticeId;
    private NoticeDetailBean bean;
    private NoticeAdapter adapter;
    private MyGridView notice_picture;
    private WebViewHelper helper;
    private String voiceId;
    private ImageView img_empty;
    private int showTime;
    private int type = 1;
    private TextView mEmptyView;
    private boolean isPlay;
    private String noticeUserId;
    private boolean isSend;
    private boolean isAdmin;
    @BindView(R.id.scrollview)
    ScrollView mScrollView;

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


    @OnClick({R.id.notices_voice_control, R.id.title_left, R.id.notice_already_read, R.id.notice_no_read})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.notices_voice_control:
                if (helper.isPlaying()) {
                    mVoiceControl.setText(getResources().getString(R.string.icon_bofang));
                    helper.pauseVoice();
                } else {
                    mVoiceControl.setText(getResources().getString(R.string.icon_zanting));
                    helper.playVoice_o(NoticeDetailActivity.this, voiceId);
                }
                break;
            case R.id.title_left:
                helper.stopVoice();
                finish();
                break;

            case R.id.notice_already_read:
                notice_already_read_line.setBackgroundResource(R.color.green_color);
                notice_no_read_line.setBackgroundResource(R.color.text_white);
                notice_already_read.setTextColor(getResources().getColor(R.color.text_black_h2));
                notice_no_read.setTextColor(getResources().getColor(R.color.text_gray_h1));
                itemsBeens.clear();
                type = 1;
                if (bean.getReadUserList() != null) {
                    itemsBeens.addAll(bean.getReadUserList());
                    adapter.notifyDataSetChanged();
                }
                setEmptyView();
                break;

            case R.id.notice_no_read:
                notice_already_read_line.setBackgroundResource(R.color.text_white);
                notice_no_read_line.setBackgroundResource(R.color.green_color);
                notice_already_read.setTextColor(getResources().getColor(R.color.text_gray_h1));
                notice_no_read.setTextColor(getResources().getColor(R.color.text_black_h2));
                itemsBeens.clear();
                type = 2;
                if (bean.getUnReadUserList() != null) {
                    itemsBeens.addAll(bean.getUnReadUserList());
                    adapter.notifyDataSetChanged();
                }
                setEmptyView();
                break;
        }
    }

    public static void start(Activity activity, String noticeId, boolean isSend, boolean isAdmin) {
        Intent intent = new Intent(activity, NoticeDetailActivity.class);
        intent.putExtra("noticeId", noticeId);
        intent.putExtra("isSend", isSend);
        intent.putExtra("isAdmin", isAdmin);
        activity.startActivityForResult(intent, 100);
    }


    public static void start(Activity activity, String noticeId) {
        Intent intent = new Intent(activity, NoticeDetailActivity.class);
        intent.putExtra("noticeId", noticeId);
        activity.startActivity(intent);
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

    @Subscribe
    public void onEvent(UnReadUserEvent event) {
        noticeUserId = event.unReadUserListBean.getUserId();
        initPopWindow();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AudioPlayEvent event) {
        mVoiceProgress.setProgress((int) ((event.position / (showTime * 1000.0)) * 100));
        mVoiceTime.setText(formatTime2(event.position / 1000));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AudioPlayStatusEvent event) {
        mVoiceProgress.setProgress(0);
        mVoiceTime.setText("00:00");
        mVoiceControl.setText(getResources().getString(R.string.icon_bofang));
    }

    private String formatTime2(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        String ms = formatter.format(time * 1000);
        Log.d(getClass().getSimpleName(), ms);
        return ms;
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
                        singleNotice(3);
                        break;
                    case R.id.btn_03:
                        singleNotice(4);
                        break;
                }
            }
        }, buttonName, listColor, false);

    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_notices_detail;
    }

    @Override
    public void initView() {
        mScrollView.smoothScrollTo(0, 0);
        noticeId = getIntent().getStringExtra("noticeId");
        isSend = getIntent().getBooleanExtra("isSend", true);
        isAdmin = getIntent().getBooleanExtra("isAdmin", false);
        itemsBeens = new ArrayList<>();
        title = (TextView) findViewById(R.id.title_middle_text);
        notice_name = (TextView) findViewById(R.id.notice_name);
        notice_time = (TextView) findViewById(R.id.notice_time);
        notice_title = (TextView) findViewById(R.id.notice_title);
        notice_already_read = (TextView) findViewById(R.id.notice_already_read);
        notice_no_read = (TextView) findViewById(R.id.notice_no_read);
        notice_content = (TextView) findViewById(R.id.notice_content);
        notice_picture = (MyGridView) findViewById(R.id.notice_picture);
        notice_already_read_line = findViewById(R.id.notice_already_read_line);
        notice_no_read_line = findViewById(R.id.notice_no_read_line);
        listView = (MyListView2) findViewById(R.id.notice_list);
        mEmptyView = (TextView) findViewById(R.id.tv_empty);
        img_empty = (ImageView) findViewById(R.id.img_empty);
        adapter = new NoticeAdapter(this, itemsBeens);
        adapter.setSend(isSend);
        adapter.setAdmin(isAdmin);
        helper = WebViewHelper.getIntence();

        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void doAction() {
        super.doAction();

        helper.init(this, null);
        title.setText("通知详情");
        listView.setAdapter(adapter);
        getNoticeDetail();
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                if (type == 1) {
//                    PersonCenterActivity.start(NoticeDetailActivity.this, ((NoticeDetailBean.ReadUserListBean) itemsBeens.get(i)).getUserId());
//                } else if (type == 2) {
//                    PersonCenterActivity.start(NoticeDetailActivity.this, ((NoticeDetailBean.UnReadUserListBean) itemsBeens.get(i)).getUserId());
//                }
//            }
//        });

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getVoiceId(String id) {
        voiceId = id;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            helper.stopVoice();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 通知详情
     */
    private void getNoticeDetail() {

        Map<String, Object> map = new TreeMap<>();
        map.put("noticeId", noticeId);
        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.noticeDetail_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                bean = JSON.parseObject(result, NoticeDetailBean.class);
                if (bean != null) {
                    notice_already_read.setText("已查阅(" + bean.getReadNum() + ")");
                    notice_no_read.setText("未查阅(" + bean.getUnReadNum() + ")");
//                    notice_title.setText(bean.getTitle());
                    notice_name.setText(bean.getCreateUserName());
                    notice_time.setText(bean.getCreateTime());
                    notice_content.setText(EmotionUtils.convertNormalStringToSpannableString(bean.getContent()));
                    notice_already_read_line.setBackgroundResource(R.color.text_white);
                    notice_no_read_line.setBackgroundResource(R.color.green_color);
                    notice_already_read.setTextColor(getResources().getColor(R.color.text_gray_h1));
                    notice_no_read.setTextColor(getResources().getColor(R.color.text_black_h2));

                    itemsBeens.clear();

                    if (bean.getReadUserList() != null) {
                        itemsBeens.addAll(bean.getUnReadUserList());
                        adapter.notifyDataSetChanged();
                    }
                    setEmptyView();

                    if (bean.isHasImage()) {
                        notice_picture.setVisibility(View.VISIBLE);
                        List<HomePageHelper.ImageBeans> imageBeanses = new ArrayList<>();
                        List<ImageAttrEntity> imageUrls = new ArrayList<>();
                        final ArrayList<ActionListBean.ImagesBean> urls2 = new ArrayList<>();
                        List<NoticeDetailBean.ImageListBean> imagesBeanList = bean.getImageList();
                        for (int i = 0; i < imagesBeanList.size(); i++) {
                            HomePageHelper.ImageBeans imageBeans = new HomePageHelper.ImageBeans();
                            imageBeans.setThumbnailUrl(imagesBeanList.get(i).getImageUrl());
                            imageBeanses.add(imageBeans);

                            ActionListBean.ImagesBean image = new ActionListBean.ImagesBean();
                            image.setThumbnailUrl(imagesBeanList.get(i).getImageUrl());
                            image.setBigSizeUrl(imagesBeanList.get(i).getImageUrl());
                            urls2.add(image);

                            ImageAttrEntity imageUrl = new ImageAttrEntity();
                            imageUrl.setThumbnailUrl(imagesBeanList.get(i).getImageUrl());
                            imageUrl.setBigSizeUrl(imagesBeanList.get(i).getImageUrl());
                            imageUrls.add(imageUrl);

                        }
                        HomePageHelper.getIntence().showPictures(NoticeDetailActivity.this, notice_picture, imageUrls);

                        notice_picture.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                ImageBrowserActivity.start(NoticeDetailActivity.this, i, 10, urls2, ShowImageType.SAVE, null);
                            }
                        });
                    }

                    if (bean.isHasVoice()) {
                        showTime = bean.getVoice().getVoiceDuration();
                        mVoice.setVisibility(View.VISIBLE);
                        mVoiceTimeTotal.setText(formatTime2(bean.getVoice().getVoiceDuration()));
                        helper.downloadVoice_o(NoticeDetailActivity.this, bean.getVoice().getVoiceUrl());
                    }

                }

            }

            @Override
            public void onFailure(String errorMsg) {
            }
        });

    }

    private void singleNotice(int noticeType) {
        Map<String, Object> map = new TreeMap<>();
        map.put("noticeId", noticeId);
        map.put("userId", noticeUserId);
        map.put("noticeType", noticeType);
        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.singNotice_url, new HttpRequestCallBack() {

            @Override
            public void onSuccess(String result) {
                Toast.makeText(NoticeDetailActivity.this, "通知成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String errorMsg) {
                Toast.makeText(NoticeDetailActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setEmptyView() {
        if (itemsBeens.size() == 0) {
            mEmptyView.setVisibility(View.VISIBLE);
            img_empty.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            img_empty.setImageResource(R.drawable.no_body_scan);
            mEmptyView.setText("暂无人查阅");
        } else {
            mEmptyView.setVisibility(View.GONE);
            img_empty.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }
    }

}
