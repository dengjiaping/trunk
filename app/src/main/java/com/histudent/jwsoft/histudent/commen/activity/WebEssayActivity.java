package com.histudent.jwsoft.histudent.commen.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.sdk.android.vod.upload.VODUploadCallback;
import com.alibaba.sdk.android.vod.upload.VODUploadClient;
import com.alibaba.sdk.android.vod.upload.VODUploadClientImpl;
import com.alibaba.sdk.android.vod.upload.common.utils.StringUtil;
import com.alibaba.sdk.android.vod.upload.model.UploadFileInfo;
import com.alibaba.sdk.android.vod.upload.model.VodInfo;
import com.gzsll.jsbridge.WVJBWebView;
import com.histudent.jwsoft.histudent.HiStudentLog;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.hiworld.activity.SelecteClassActivity;
import com.histudent.jwsoft.histudent.body.hiworld.activity.WatchEssayVideoActivity;
import com.histudent.jwsoft.histudent.body.hiworld.adapter.SyncClassAdapter;
import com.histudent.jwsoft.histudent.body.hiworld.adapter.UploadImageRecyclerViewAdapter;
import com.histudent.jwsoft.histudent.body.hiworld.bean.MovieTokenBean;
import com.histudent.jwsoft.histudent.body.hiworld.bean.RelationPersonModel;
import com.histudent.jwsoft.histudent.body.hiworld.bean.SimpleUserModel;
import com.histudent.jwsoft.histudent.body.hiworld.bean.TopicModel;
import com.histudent.jwsoft.histudent.body.mine.model.UserClassListModel;
import com.histudent.jwsoft.histudent.call.IGuideCommonClickListener;
import com.histudent.jwsoft.histudent.call.IPermissionCallBackListener;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.bean.MyShareBean;
import com.histudent.jwsoft.histudent.commen.bean.WebEssayBean;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.enums.LoactionType;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.enums.ShowImageType;
import com.histudent.jwsoft.histudent.commen.enums.YdType;
import com.histudent.jwsoft.histudent.commen.helper.PictureTailorHelper;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.keyword.emotionkeyboardview.EmotionKeyboard;
import com.histudent.jwsoft.histudent.commen.keyword.fragment.EmotionMainFragment;
import com.histudent.jwsoft.histudent.commen.listener.DialogButtonListener;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.photo.PhotoHelper;
import com.histudent.jwsoft.histudent.commen.photo.utils.ImageUtils;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.BlogEnum;
import com.histudent.jwsoft.histudent.commen.utils.ComViewUitls;
import com.histudent.jwsoft.histudent.commen.utils.DataUtils;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.ListenerUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.view.FixedEditText;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.commen.view.MyGridView;
import com.histudent.jwsoft.histudent.comment2.activity.MapActivity;
import com.histudent.jwsoft.histudent.comment2.bean.AddressInforBean;
import com.histudent.jwsoft.histudent.comment2.utils.ActionTypeEnum;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 发表随记页面(供h5调用)
 */
public class WebEssayActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_add;
    private TextView title_right, tv_authority, movie_time, essay_keyWord_close;
    private RecyclerView recyclerView;
    private ArrayList<String> url_list;//存放选中图片的路径
    private UploadImageRecyclerViewAdapter addImageAdapter;
    private int resultCode;
    private AddressInforBean addressInforBean;
    private Double latitude, longitude;
    private String location;
    private FixedEditText et_content;
    private int width;
    private ArrayList<UserClassListModel> classListModels;//同步班级集合
    private List<String> list_tmp;
    private RelativeLayout relative_quality, user_sycn_layout;
    private UploadImageRecyclerViewAdapter.OnRecyclerViewOnClickListener listener;
    private TextView tv_address;
    private String city;
    private List<ActionListBean.ImagesBean> listImageBean;
    private CheckBox qq_checkBox, friend_checkBox;
    private LinearLayout relative_address;
    private RelativeLayout essay_topic;
    private PictureTailorHelper clippHelper;
    private ArrayList<RelationPersonModel> relationPersonModelList;//用于存放图片关联人的信息
    private MyGridView gridView;
    private SyncClassAdapter syncClassAdapter;//同步班级适配
    private String file_movie;
    private VODUploadClient uploader;
    private RelativeLayout publish_movie_layout;
    private ImageView publish_movie_cover;
    private IconView publish_movie_play;
    private TopicModel topicModel;
    private ArrayList<SimpleUserModel> atList;//at对象集合
    private int cursorPosition;
    private EmotionMainFragment emotionMainFragment;
    private int duration;//录制时长
    private String coverString;//视频封面String
    private List<SimpleUserModel> allAtList;
    private BlogEnum blogEnum = BlogEnum.PERSON;
    private String url;
    private String id;
    private ScrollView scrollView;
    private Handler handler;
    private IconView title_left;
    private boolean isAutoLocation = false;
    private static WVJBWebView.WVJBResponseCallback callback;
    private WebEssayBean bean;
    private IconView rb_authority_image, sycn_image, rb_address_image;

    public static void start(Activity activity, WVJBWebView.WVJBResponseCallback callback, WebEssayBean bean) {
        if (callback == null || bean == null) return;
        Intent intent = new Intent(activity, WebEssayActivity.class);
        intent.putExtra("blogEnum", BlogEnum.PERSON);
        intent.putExtra("bean", bean);
        WebEssayActivity.callback = callback;
        activity.startActivity(intent);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_essay_web;
    }

    @Override
    public void initView() {

        handler = new MyHandler();
        clippHelper = PictureTailorHelper.getInstance();
        resultCode = getIntent().getIntExtra("resultCode", 0);
        blogEnum = ((BlogEnum) getIntent().getSerializableExtra("blogEnum"));
        topicModel = ((TopicModel) getIntent().getSerializableExtra("topicModel"));
        id = getIntent().getStringExtra("id");
        bean = (WebEssayBean) getIntent().getSerializableExtra("bean");

        relationPersonModelList = new ArrayList<>();
        classListModels = new ArrayList<>();
        list_tmp = new ArrayList<>();
        atList = new ArrayList<>();
        allAtList = new ArrayList<>();
        listImageBean = new ArrayList();
        title_left = ((IconView) findViewById(R.id.title_left_image));
        title_right = (TextView) findViewById(R.id.title_right_text);
        movie_time = (TextView) findViewById(R.id.movie_time);
        essay_keyWord_close = (TextView) findViewById(R.id.essay_keyWord_close);

        sycn_image = (IconView) findViewById(R.id.sycn_image);
        rb_authority_image = (IconView) findViewById(R.id.rb_authority_image);
        rb_address_image = (IconView) findViewById(R.id.rb_address_image);

        publish_movie_layout = (RelativeLayout) findViewById(R.id.publish_movie_layout);
        publish_movie_cover = (ImageView) findViewById(R.id.publish_movie_cover);
        publish_movie_play = (IconView) findViewById(R.id.publish_movie_play);
        scrollView = (ScrollView) findViewById(R.id.scroll_view);

        qq_checkBox = (CheckBox) findViewById(R.id.iv_share_QQzone);
        friend_checkBox = (CheckBox) findViewById(R.id.iv_share_friend_quan);
        gridView = ((MyGridView) findViewById(R.id.gridview));

        essay_topic = (RelativeLayout) findViewById(R.id.essay_topic);
        user_sycn_layout = (RelativeLayout) findViewById(R.id.user_sycn_layout);
        ((TextView) findViewById(R.id.title_middle_text)).setText("发布" + bean.getTitle());

        title_right.setText("发布");
        title_right.setTextColor(getResources().getColor(R.color.text_black_h1));
        findViewById(R.id.title_right).setEnabled(true);
        title_right.setVisibility(View.VISIBLE);
        title_left.setText(R.string.icon_close);
        title_left.setTextSize(15);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        syncClassAdapter = new SyncClassAdapter(classListModels, this);
        gridView.setAdapter(syncClassAdapter);

        //选择点击图片的时间监听
        listener = new UploadImageRecyclerViewAdapter.OnRecyclerViewOnClickListener() {
            @Override
            public void setOnItemClickListener(View v, final int postion) {

                if (postion == url_list.size() - 1 && url_list.get(postion).equals("add")) {

                    checkTakePhotoPermission(new IPermissionCallBackListener() {
                        @Override
                        public void doAction() {
                            initPopWindow();
                        }
                    });

                } else {

                    //跳转到图片查看器
                    if (url_list != null && url_list.size() > 1) {
                        ActionListBean.ImagesBean imagesBean;
                        listImageBean.clear();
                        for (String picId : url_list) {
                            if (!"add".equals(picId)) {
                                imagesBean = new ActionListBean.ImagesBean();
                                File f = new File(picId);
                                imagesBean.setBigSizeUrl(picId);
                                imagesBean.setName(f.getName());
                                imagesBean.setThumbnailUrl(picId);
                                listImageBean.add(imagesBean);
                            }
                        }

                        ImageBrowserActivity.start(WebEssayActivity.this, postion, 100, (ArrayList<ActionListBean.ImagesBean>) listImageBean,
                                ShowImageType.RELATION, relationPersonModelList);
                    }
                }
            }
        };

        //控件的初始化
        recyclerView = ((RecyclerView) findViewById(R.id.grid_view));
        relative_address = ((LinearLayout) findViewById(R.id.relative_address));
        relative_quality = ((RelativeLayout) findViewById(R.id.relative_photo_quality));
        tv_authority = ((TextView) findViewById(R.id.tv_authority));
        iv_add = new ImageView(this);
        iv_add.setBackgroundResource(R.mipmap.add_gray);
        et_content = ((FixedEditText) findViewById(R.id.et_content));
        et_content.setFixStr("#" + bean.getDefaultTopic() + "#");
        tv_address = ((TextView) findViewById(R.id.rb_address));
        findViewById(R.id.title_right).setEnabled(false);

        relative_quality.setVisibility(bean.isHideUIPrivacy() ? View.GONE : View.VISIBLE);
        user_sycn_layout.setVisibility(bean.isHideUISyncClass() ? View.GONE : View.VISIBLE);

        url_list = new ArrayList<>();
        url_list.add("add");
        addImageAdapter = new UploadImageRecyclerViewAdapter(url_list, listener);
        recyclerView.setAdapter(addImageAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));

        relative_address.setOnClickListener(this);
        essay_topic.setOnClickListener(this);

        qq_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    friend_checkBox.setChecked(false);
                }

            }
        });

        friend_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    qq_checkBox.setChecked(false);
                }

            }
        });

        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Message message = handler.obtainMessage();
                message.what = 5;
                message.obj = (et_content.getText().toString().length() > bean.getDefaultTopic().length() + 2);
                handler.sendMessage(message);

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (bean.getDefaultTopic().length() + 2 > s.length()) {
                    s.replace(0, s.length(), "#" + bean.getDefaultTopic() + "#");
                }
                essay_keyWord_close.setText(TextUtils.isEmpty(s.toString()) ? "0/140" : s.toString().length() + "/140");
            }
        });



        ListenerUtils.setOnDeleteUserListener(et_content, allAtList);
        initEmotionMainFragment();
        getAddressInfor();

        setInput();

        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);

        handler.sendEmptyMessage(3);

        switch (blogEnum) {

            //个人随记
            case PERSON:
                //ComViewUitls.setSyncViewVisiablity(this, true);
                getSyncClassDate();
                break;

            //班级活动随记
            case CLASSHUODONG:
                break;

            //话题
            case TOPIC:

                if (topicModel != null)
                    DataUtils.setTopicText(et_content, topicModel, 0);
                ComViewUitls.setSyncViewVisiablity(this, true);
                getSyncClassDate();

                break;

        }

    }

    @Override
    public void doAction() {
        super.doAction();
        if (bean != null && !StringUtil.isEmpty(bean.getDefaultTopic())) {
            TopicModel topicModel = new TopicModel();
            topicModel.setTagId("1");
            topicModel.setTagName(bean.getDefaultTopic());
            DataUtils.setTopicText(et_content, topicModel, 0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {

            //地址
            case R.id.relative_address:
                MapActivity.startForResult(this, addressInforBean, LoactionType.DYNAMIC, isAutoLocation, true);
                break;

            //权限
            case R.id.relative_photo_quality:
                CommenPrivacySetting.start(WebEssayActivity.this,
                        tv_authority.getText().toString().trim(), blogEnum == BlogEnum.PERSON ? ActionTypeEnum.OWNER : ActionTypeEnum.CLASS, 102);
                break;

            //发布
            case R.id.title_right:

                if (StringUtil.isEmpty(et_content.getText().toString()) && TextUtils.isEmpty(file_movie) && (url_list == null || url_list.size() == 1)) {
                    Toast.makeText(this, "随记内容不能为空", Toast.LENGTH_LONG).show();
                } else {
                    showLoadingImage(WebEssayActivity.this, LoadingType.FLOWER);
                    deleteNullData(url_list);

                    //随记中有图片需要压缩后上传
                    if (url_list.size() > 0) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                compressImage(url_list);
                            }
                        }).start();
                        //没图片直接上传
                    } else {
                        publishEssay();
                    }
                }

                break;

            //取消
            case R.id.title_left:
                showBackNotice();
                break;

            //同步班级
            case R.id.sycn:
                SelecteClassActivity.start(WebEssayActivity.this, classListModels, "", 400);
                break;

            //点击编辑框时重新获取编辑框的光标的位置
//            case R.id.et_content:
//                cursorPosition = et_content.getSelectionStart();
//                break;

        }
    }

    /**
     * 对图片进行压缩
     */
    private void compressImage(List<String> path) {
        PhotoHelper.saveImageInFile(WebEssayActivity.this, "正常", path, handler);
    }

    /**
     * 视频监听相关
     */
    private void setMovieListener() {
        if (file_movie == null) return;
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(file_movie, MediaStore.Images.Thumbnails.MINI_KIND);
        coverString = convertIconToString(bitmap);
        if (bitmap == null) return;
        publish_movie_cover.setImageBitmap(bitmap);
        publish_movie_layout.setVisibility(View.VISIBLE);
        movie_time.setText(getTimeFromMillisecond((long) duration));
        publish_movie_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WatchEssayVideoActivity.start(WebEssayActivity.this, file_movie, 10);
            }
        });

    }

    /**
     * 从时间(毫秒)中提取出时间(时:分)
     * 时间格式:  时:分
     *
     * @param millisecond
     * @return
     */
    private String getTimeFromMillisecond(Long millisecond) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        Date date = new Date(millisecond);
        String timeStr = simpleDateFormat.format(date);
        return timeStr;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == -1) {//拍照返回
            url_list.remove(url_list.size() - 1);
            url_list.add(clippHelper.photo_path.getAbsolutePath());
            dealAction();

            Message message = handler.obtainMessage();
            message.what = 5;
            message.obj = true;
            handler.sendMessage(message);

        } else if (requestCode == 1001) {//相册返回

            if (resultCode == 200) {
                url_list.clear();
                Message message = handler.obtainMessage();
                message.what = 5;
                message.obj = true;
                handler.sendMessage(message);

                if (data != null) {
                    url_list.addAll(0, data.getStringArrayListExtra("return"));//返回的图片地址

                    if (requestCode == 100 || requestCode == 1001) {//返回关联图片关系集合
                        if (data.getSerializableExtra("relations") != null) {
                            relationPersonModelList.clear();
                            relationPersonModelList.addAll((ArrayList<RelationPersonModel>) data.getSerializableExtra("relations"));
                            addImageAdapter.initRelationData(relationPersonModelList);
                        }
                    }
                }
            }
            dealAction();

        } else if (requestCode == 10 && resultCode == -10) {//视频预览返回

            publish_movie_layout.setVisibility(View.GONE);
            file_movie = null;

        } else if (requestCode == 200 && resultCode == 300) {//地图数据返回

            if (data == null || data.getSerializableExtra("address") == null) return;

            addressInforBean = (AddressInforBean) data.getSerializableExtra("address");
            if (addressInforBean.isShowAddress()) {
                tv_address.setText(addressInforBean.getCity() + "·" + addressInforBean.getName());
                tv_address.setTextColor(getResources().getColor(R.color.text_black_h3));
                rb_address_image.setTextColor(getResources().getColor(R.color.text_black_h3));
                latitude = addressInforBean.getLatitude();
                longitude = addressInforBean.getLongitude();
                city = addressInforBean.getCity();
                isAutoLocation = false;
            } else {
                tv_address.setText("地址");
                tv_address.setTextColor(getResources().getColor(R.color.text_gray_h2));
                rb_address_image.setTextColor(getResources().getColor(R.color.text_gray_h2));
                isAutoLocation = true;
            }

        } else if (requestCode == 100) {//图片预览返回

            if (resultCode == 200) {
                url_list.clear();
                Message message = handler.obtainMessage();
                message.what = 5;
                message.obj = true;
                handler.sendMessage(message);
                relationPersonModelList.clear();
                relationPersonModelList = (ArrayList<RelationPersonModel>) data.getSerializableExtra("relations");
                addImageAdapter.initRelationData(relationPersonModelList);

                if (data != null)
                    url_list.addAll(0, data.getStringArrayListExtra("return"));
            }

            dealAction();

        } else if (requestCode == 400 && resultCode == 200) {//同步班级数据返回

            if (data == null || data.getSerializableExtra("classList") == null) {
                sycn_image.setTextColor(getResources().getColor(R.color.icon_color));
                return;
            }

            List<UserClassListModel> userClassListModels = (List<UserClassListModel>) data.getSerializableExtra("classList");
            if (userClassListModels != null && userClassListModels.size() > 0) {
                sycn_image.setTextColor(getResources().getColor(R.color.green_color));
            } else {
                sycn_image.setTextColor(getResources().getColor(R.color.icon_color));
            }
            classListModels.clear();
            classListModels.addAll((userClassListModels));
            syncClassAdapter.notifyDataSetChanged();

        } else if (requestCode == 333 && resultCode == 200) {//获取话题返回

            if (data == null) return;
            topicModel = ((TopicModel) data.getSerializableExtra("topicModel"));
            DataUtils.setTopicText(et_content, topicModel, cursorPosition);
            DataUtils.showSoftInputFromWindow(this, et_content);

        } else if (requestCode == 222 && resultCode == 200) {//@

            if (data == null) return;
            atList.clear();
            atList.addAll(((ArrayList) data.getSerializableExtra("userModel")));
            allAtList.addAll(atList);
            setText();

        } else if (requestCode == 102 && resultCode == 200) {//权限返回

            if (data == null || data.getStringExtra("authority") == null) return;

            String authority = data.getStringExtra("authority");
            rb_authority_image.setTextColor(getResources().getColor("公开".equals(authority) ? R.color.icon_color : R.color.green_color));
            tv_authority.setText(authority);
            //控制同步布局的显示l
            if (blogEnum == BlogEnum.PERSON)
                ComViewUitls.setSyncViewVisiablity(this, authority);

        }
    }

    /**
     * 初始化拍照选择照片的PopWindow
     */
    private void initPopWindow() {

//        ReminderHelper.getIntentce().showEssayMenuDialog(this, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                switch (v.getId()) {
//                    case R.id.btn_01:
//
//                        PictureTailorHelper.getIntence().takePhoto(WebEssayActivity.this);
//                        break;
//                    case R.id.btn_02:
//
//                        url_list.remove(url_list.size() - 1);
//                        clippHelper.selectPictures(WebEssayActivity.this, url_list, 9, relationPersonModelList);
//                        break;
//                }
//            }
//        });

        List<String> list = new ArrayList<>();
        list.add("拍照");
        list.add("从相册中获取");
        List<Integer> colorList = new ArrayList<>();
        colorList.add(Color.rgb(51, 51, 51));
        colorList.add(Color.rgb(51, 51, 51));


        ReminderHelper.getIntentce().showTopMenuDialog(this, "", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.btn_01:
                        clippHelper.takePhoto(WebEssayActivity.this);
                        break;

                    case R.id.btn_02:
                        url_list.remove(url_list.size() - 1);
                        clippHelper.selectPictures(WebEssayActivity.this, url_list, 9, relationPersonModelList);
                        break;
                }

            }
        }, list, colorList, false);

    }

    /**
     * 发布视频随记
     */
    private void publishEssay() {
        if (!TextUtils.isEmpty(file_movie)) {//有视频，先上传视频
            showTokenWorke();
        } else {//无视频，直接上传
            postEssayData(new Video());
        }
    }

    /**
     * 发布随记
     */
    private void postEssayData(Video video) {

        Map<String, Object> map = new HashMap();

        if (addressInforBean == null) {
            addressInforBean = new AddressInforBean();

        }
        latitude = addressInforBean.getLatitude();
        longitude = addressInforBean.getLongitude();
        location = addressInforBean.getCity() + addressInforBean.getAreaStr();
        int privacyStatus = DataUtils.changeQualityString2Number(tv_authority.getText().toString());
        if (privacyStatus != -1) {
            switch (privacyStatus) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
            }
            map.put("privacyStatus", privacyStatus + "");

        }

        map.put("microblogBody", et_content.getText().toString());
        map.put("longitude", longitude + "");
        map.put("latitude", latitude + "");
        map.put("location", addressInforBean.getName());
        map.put("microblogLink", "");
        map.put("videoId", video.videoId);//视频id
        map.put("videoTimeLength", video.videoTimeLength);//视频长度
        map.put("videoCover", video.videoCover);//视频封面


        deleteNullData(url_list);
        if (url_list.size() != 0) {
            for (int i = 0; i < url_list.size(); i++) {
                map.put("file" + i, url_list.get(i));
            }
        }

        switch (blogEnum) {

            case PERSON:
            case TOPIC:
                map.put("ownerType", "" + 1);//用户类型
                map.put("associateType", 0 + "");
                map.put("associateId", "");
                map.put("ownerId", HiCache.getInstance().getLoginUserInfo().getUserId());
                map.put("appExtType", bean.getAppType());
                if (privacyStatus != 2) {
                    DataUtils.deleteUser(et_content.getText().toString(), allAtList);
                    map.put("atUser", DataUtils.getAtUserString(allAtList));
                    map.put("associatedUsers", DataUtils.getRelationString(relationPersonModelList));
                    map.put("classIds", DataUtils.getSyncClassIds(classListModels).toString());

                } else {
                    map.put("atUser", "");
                    map.put("associatedUsers", "");
                    map.put("classIds", "");
                }
                url = HistudentUrl.postMicroBlog_url;
                break;
            case CLASSHUODONG:
                url = HistudentUrl.ClassHuoDongCreateBlog;
                map.put("associatedUsers", DataUtils.getRelationString(relationPersonModelList));
                map.put("huodongId", id);
                break;

        }

        HiWorldCache.postHttpData(this, handler, 6, url, map, HiWorldCache.Quarry, false,true);

    }

    /**
     * 删除多于的数据
     */
    private void deleteNullData(List<String> list) {

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals("add")) {
                list.remove(list.get(i));
            }
        }
    }

    private void dealAction() {
        if (url_list != null && url_list.size() < 9) {
            if (!url_list.contains("add"))
                url_list.add("add");
        }
        addImageAdapter.notifyDataSetChanged();
    }

    /**
     * 获取默认同步的班级信息
     */
    public void getSyncClassDate() {
        DataUtils.GetUserSyncClassList(this, classListModels, syncClassAdapter, true);
    }

    /**
     * 设置at界面
     */
    private void setText() {
        DataUtils.setAtText(et_content, cursorPosition, DataUtils.getAtUserNameStr(atList));
    }

    /**
     * 创建视频信息实体类
     *
     * @return
     */
    private UploadFileInfo getUploadFileInfo() {
        UploadFileInfo vodInfo = new UploadFileInfo();
        vodInfo.setFilePath(file_movie);
        return vodInfo;
    }

    /**
     * 创建视频信息实体类
     *
     * @return
     */
    private VodInfo getVodInfo() {
        VodInfo vodInfo = new VodInfo();
        vodInfo.setCateId((int) System.currentTimeMillis());
        return vodInfo;
    }

    /**
     * 初始化视频上传函数
     */
    private void initUpload(final MovieTokenBean tokenBean) {
        if (tokenBean == null)
            return;
        uploader = new VODUploadClientImpl(getApplicationContext());
        VODUploadCallback callback = new VODUploadCallback() {

            @Override
            public void onUploadSucceed(UploadFileInfo info) {
                Video video = new Video();
                video.videoId = tokenBean.getData().getVideoId();
                video.videoCover = coverString;
                if (duration > 0)
                    video.videoTimeLength = duration / 1000;
                postEssayData(video);
            }

            @Override
            public void onUploadFailed(UploadFileInfo info, String code, String message) {
                HiStudentLog.e("onfailed ------------------ ");
            }

            @Override
            public void onUploadProgress(UploadFileInfo info, long uploadedSize, long totalSize) {
            }

            @Override
            public void onUploadTokenExpired() {
                HiStudentLog.e("onExpired ------------- ");
            }

            @Override
            public void onUploadRetry(String code, String message) {
                HiStudentLog.e("onUploadRetry ------------- ");
            }

            @Override
            public void onUploadRetryResume() {
                HiStudentLog.e("onUploadRetryResume ------------- ");
            }

            @Override
            public void onUploadStarted(UploadFileInfo uploadFileInfo) {
                HiStudentLog.e("onUploadStarted ------------- ");
            }
        };
        uploader.init(callback);
        uploader.addFile(file_movie, getVodInfo());
        uploader.setUploadAuthAndAddress(getUploadFileInfo(), tokenBean.getData().getUploadAuth(), tokenBean.getData().getUploadAddress());
        uploader.start();
    }

    /**
     * 获取上传视频的toenkey
     */
    private void showTokenWorke() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("fileSize", new File(file_movie).length());
        HiStudentHttpUtils.postDataByOKHttp(true, this, map, HistudentUrl.getVodUploadAuth_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                MovieTokenBean tokenBean = JSONObject.parseObject(result, MovieTokenBean.class);
                if (tokenBean.getRet() == 1)
                    initUpload(tokenBean);
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
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
        emotionMainFragment.bindToEditText(et_content);
        emotionMainFragment.bindToContentView(scrollView);
        emotionMainFragment.setExchangeButton(findViewById(R.id.tv_emtion));
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in thefragment_container view with this fragment,
        // and add the transaction to the backstack
        transaction.replace(R.id.fl_emotionview_main, emotionMainFragment);
//        transaction.addToBackStack(null);
        //提交修改
        transaction.commit();
    }

    public void setInput() {
        //final View rootView = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        final View decorView = getWindow().getDecorView();
        //该Activity的最外层Layout
        final View activityRootView = findViewById(R.id.activityRoot);
        //给该layout设置监听，监听其布局发生变化事件
        activityRootView.getViewTreeObserver().
                addOnGlobalLayoutListener(
                        new ViewTreeObserver
                                .OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                Rect rect = new Rect();
                                decorView.getWindowVisibleDisplayFrame(rect);
                                int heightDifferent = SystemUtil.getScreenHeight_() - rect.bottom;//计算软键盘占有的高度  = 屏幕高度 - 视图可见高度
                                EventBus.getDefault().post(new EmotionKeyboard.KeyWord(heightDifferent));
                            }
                        }
                );
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showKeyWordItem(EmotionKeyboard.KeyWord word) {
        int height = word.height;

        if (EmotionKeyboard.onClikeExchangeButton) {
            EmotionKeyboard.onClikeExchangeButton = false;
            return;
        }

        if (EmotionKeyboard.isShow) return;

        if (height > 100) {
//            essay_topic.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) essay_topic.getLayoutParams();
            lp.setMargins(0, 0, 0, height);
            essay_topic.setLayoutParams(lp);
        } else {
//            essay_topic.setVisibility(View.GONE);
        }
    }

    public void getAddressInfor() {
        addressInforBean = HiWorldCache.getUserLocationInfor();
        if (addressInforBean != null) {
            tv_address.setText(addressInforBean.getName());
        }
    }

    public class Video {
        String videoId;
        String videoCover;
        int videoTimeLength;

        public Video() {

        }

        public Video(String videoId, String videoCover, int videoTimeLength) {
            this.videoId = videoId;
            this.videoCover = videoCover;
            this.videoTimeLength = videoTimeLength;
        }
    }

    /**
     * 图片转成string
     *
     * @param bitmap
     * @return
     */
    public String convertIconToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] appicon = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(appicon, Base64.DEFAULT);
    }

    /**
     * 判断是否拦截返回键操作
     */
    @Override
    public void onBackPressed() {
        boolean flag = emotionMainFragment.isInterceptBackPress();
        if (!flag) {
            super.onBackPressed();
        }
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

                case 6:
                    closeLoadingImage();
                    String content = (String) msg.obj;

                    if (msg.arg1 == HiWorldCache.SUCCESS) {
                        if (content != null) {
                            JSONObject object = JSON.parseObject(content);
                            if (object != null) {
                                Intent intent = new Intent();
                                int n = 0;
                                if (qq_checkBox.isChecked()) {
                                    n = 2;
                                } else if (friend_checkBox.isChecked()) {
                                    n = 1;
                                }

                                ImageUtils.deleteCompressFile();//删除压缩文件
                                if (blogEnum == BlogEnum.PERSON) {
                                    intent.putExtra("flag_share", n);
                                    intent.putExtra("share_content", content);
                                    setResult(resultCode, intent);
                                } else {
                                    setResult(200);
                                }

                                url_list.clear();
                                list_tmp.clear();

                                MyShareBean shareBean = JSON.parseObject(content, MyShareBean.class);
                                ComShareAcitivity.start(WebEssayActivity.this, shareBean, callback);
                                finish();

                            } else {
                                Toast.makeText(WebEssayActivity.this, "发布失败，请稍后再试！", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }
                    }

                    break;

                //上传图片的压缩
                case 1:
                    if (msg.arg1 == 0) {
                        HiStudentLog.e("---->压缩保存失败...");
                    } else {

                        if (url_list != null && url_list.size() > 0) {

                            HiStudentLog.e("---->压缩保存完成...开始长传文件夹");
                            for (int i = 0; i < url_list.size(); i++) {
                                if (!url_list.get(i).equals("add")) {

                                    list_tmp.add(url_list.get(i));
                                }
                            }

                            url_list.clear();
                        }

                        if (list_tmp != null) {

                            for (int i = 0; i < list_tmp.size(); i++) {

                                url_list.add(ImageUtils.getCompressFilePath(list_tmp.get(i)));
                            }
                        }

                        list_tmp.clear();

                        postEssayData(new Video());
                    }
                    break;

                case 3:
                    showUserShadeWindow(WebEssayActivity.this, title_left, YdType.ESSAY_QX, new IGuideCommonClickListener() {
                        @Override
                        public void onClick() {
                            sendEmptyMessage(4);
                        }
                    });
                    break;

                case 4:
                    showUserShadeWindow(WebEssayActivity.this, title_left, YdType.ESSAY_SHARE, null);
                    break;

                case 5:
                    boolean flag = (boolean) msg.obj;
                    title_right.setTextColor(getResources().getColor(flag ? R.color.green_color : R.color.text_black_h1));
                    findViewById(R.id.title_right).setEnabled(flag);
                    break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            showBackNotice();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showBackNotice() {

        if ((url_list != null && url_list.size() > 0) || !StringUtil.isEmpty(et_content.getText().toString())) {
            ReminderHelper.getIntentce().showDialog(WebEssayActivity.this, "提示", bean.getTitle() + "还没发布成功，确定不发布吗？", "确定", new DialogButtonListener() {
                @Override
                public void setOnDialogButtonListener() {
                    finish();
                }
            }, "取消", new DialogButtonListener() {
                @Override
                public void setOnDialogButtonListener() {

                }
            });
        } else {
            finish();
        }
    }

}
