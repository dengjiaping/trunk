package com.histudent.jwsoft.histudent.body.find.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.helper.GroupHelper;
import com.histudent.jwsoft.histudent.body.hiworld.activity.TopicSearchActiviy;
import com.histudent.jwsoft.histudent.body.hiworld.adapter.UploadImageRecyclerViewAdapter;
import com.histudent.jwsoft.histudent.body.hiworld.bean.TopicModel;
import com.histudent.jwsoft.histudent.model.listener.IPermissionCallBackListener;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.CommentActivity;
import com.histudent.jwsoft.histudent.commen.activity.ImageBrowserActivity;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.bean.MyShareBean;
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
import com.histudent.jwsoft.histudent.commen.photo.utils.ImageUtils;
import com.histudent.jwsoft.histudent.commen.utils.DataUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.commen.view.swipemenulistview.AutoScrollTextView;
import com.histudent.jwsoft.histudent.comment2.activity.MapActivity;
import com.histudent.jwsoft.histudent.comment2.bean.AddressInforBean;
import com.histudent.jwsoft.histudent.comment2.bean.CreateTopicModel;
import com.histudent.jwsoft.histudent.comment2.utils.ViewUtils;
import com.netease.nim.uikit.common.util.string.StringUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 发布社群帖子界面
 */
public class CreateTopicActivity extends BaseActivity implements View.OnClickListener {
    private TextView title_text_right;
    private EditText et_content;
    private LinearLayout log_content_body;
    private ArrayList<String> url_list;//存放选中图片的路径
    private List<String> file_url_compress;//压缩后的图片保存集合.
    private RelativeLayout log_bottom;
    private String associationId;//帖子id
    private PictureTailorHelper clippHelper;
    private LinearLayout addpicture_layout;
    private Handler handler;
    private int cursorPosition;
    private AddressInforBean addressModel;
    private EmotionMainFragment emotionMainFragment;
    private View layout;
    private CreateTopicModel model;
    private TopicModel topicModel;
    private AutoScrollTextView scrollTextView;
    private List<TopicModel> hotTopicList;//热门话题集合
    private UploadImageRecyclerViewAdapter addImageAdapter;
    private UploadImageRecyclerViewAdapter.OnRecyclerViewOnClickListener listener;
    private RecyclerView recyclerView;
    private List<ActionListBean.ImagesBean> listImageBean;
    private TextView tv_address;
    private IconView title_left;
    private ScrollView scrollView;

    /**
     * 社群发帖入口
     *
     * @param activity
     * @param associationId 社群的id
     * @param requestCode
     */

    public static void start(Activity activity, String associationId, int requestCode) {
        Intent intent = new Intent(activity, CreateTopicActivity.class);
        intent.putExtra("associationId", associationId);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public int setViewLayout() {
        return R.layout.create_topic;
    }

    @Override
    public void initView() {

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        model.setImages(file_url_compress);
                        model.setAssocitionId(associationId);
                        model.setLocation(addressModel.getDetailStr());
                        model.setLat(addressModel.getLatitude());
                        model.setLon(addressModel.getLongitude());
                        model.setContent(getContent());
                        createTopic();
                        break;
                    default:
                        showUserShadeWindow(CreateTopicActivity.this, log_content_body, YdType.LOG_SHARE, null);
                        break;
                }

            }
        };

        setInput();


        model = new CreateTopicModel();
        clippHelper = PictureTailorHelper.getInstance();
        associationId = getIntent().getStringExtra("associationId");
        url_list = new ArrayList<>();
        hotTopicList = new ArrayList<>();
        file_url_compress = new ArrayList<>();
        scrollTextView = ((AutoScrollTextView) findViewById(R.id.hot_topic));
        layout = findViewById(R.id.grid_view);
        title_text_right = (TextView) findViewById(R.id.title_right_text);
        log_content_body = (LinearLayout) findViewById(R.id.log_content_body);
        addpicture_layout = (LinearLayout) findViewById(R.id.log_picture_select);
        log_bottom = (RelativeLayout) findViewById(R.id.log_bottom_);
        et_content = ((EditText) findViewById(R.id.log_content_edit));
        recyclerView = ((RecyclerView) findViewById(R.id.grid_view));
        ((TextView) findViewById(R.id.title_middle_text)).setText("新帖子");
        findViewById(R.id.iv_pic).setOnClickListener(this);
        findViewById(R.id.tv_topic).setOnClickListener(this);
        title_left = ((IconView) findViewById(R.id.title_left_image));
        tv_address = ((TextView) findViewById(R.id.tv_address));
        scrollView = ((ScrollView) findViewById(R.id.scrollView));
        title_left.setText(R.string.icon_close5);
        title_left.setVisibility(View.VISIBLE);
        title_left.setTextSize(15);
        initListener();
        addImageAdapter = new UploadImageRecyclerViewAdapter(url_list, listener);
        recyclerView.setAdapter(addImageAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));

        listImageBean = new ArrayList<>();
        title_text_right.setText("发布");
        addpicture_layout.setVisibility(View.VISIBLE);

        getAddressInfor();
        initEmotionMainFragment();
        getHotTopicList();


        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);

    }

    private void initListener() {

        //选择点击图片的时间监听
        listener = new UploadImageRecyclerViewAdapter.OnRecyclerViewOnClickListener() {
            @Override
            public void setOnItemClickListener(View v, final int postion) {

                if (url_list != null && url_list.size() > 0) {
                    ActionListBean.ImagesBean imagesBean;
                    listImageBean.clear();
                    for (String picId : url_list) {
                        imagesBean = new ActionListBean.ImagesBean();
                        File f = new File(picId);
                        imagesBean.setBigSizeUrl(picId);
                        imagesBean.setName(f.getName());
                        imagesBean.setThumbnailUrl(picId);
                        listImageBean.add(imagesBean);
                    }

                    ImageBrowserActivity.start(CreateTopicActivity.this, postion, 100, (ArrayList<ActionListBean.ImagesBean>) listImageBean,
                            ShowImageType.SCANF, null);
                }
            }
        };


        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                changTextColor();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    //获取地址信息
    private void getAddressInfor() {
        addressModel = HiWorldCache.getUserLocationInfor();
        if (addressModel != null) {
            tv_address.setText(addressModel.getCity() + "·" + addressModel.getName());
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {

            //返回
            case R.id.title_left:
                if (StringUtil.isEmpty(getContent())) {
                    finish();
                } else {

                    ReminderHelper.getIntentce().showDialog(this, "提示", "帖子还没发布，确定不发布吗？", "放弃", new DialogButtonListener() {
                        @Override
                        public void setOnDialogButtonListener() {
                            CreateTopicActivity.this.finish();
                        }
                    }, "继续", new DialogButtonListener() {
                        @Override
                        public void setOnDialogButtonListener() {

                        }
                    });
                }

                break;

            //发帖子
            case R.id.title_right:

                compressImage(url_list);

                break;

            //添加图片
            case R.id.iv_pic:

                checkTakePhotoPermission(new IPermissionCallBackListener() {
                    @Override
                    public void doAction() {
                        initPopWindow();
                    }
                });

                break;

            //插入话题
            case R.id.tv_topic:

                cursorPosition = et_content.getSelectionStart();

                TopicSearchActiviy.start(this, associationId, 111);

                break;

            //草稿箱
            case R.id.hot_topic:

                if (hotTopicList != null && hotTopicList.size() > 0) {
                    cursorPosition = et_content.getSelectionStart();
                    topicModel = hotTopicList.get(scrollTextView.getCurrentIndex() - 1);
                    setText();
                }

                break;

            case R.id.relative_address:

                MapActivity.startForResult(this, addressModel, LoactionType.DYNAMIC, true, true);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PictureTailorHelper.PHOTO_REQUEST_TAKEPHOTO && resultCode == -1) {

            url_list.add(clippHelper.photo_path.getAbsolutePath());
            addImageAdapter.notifyDataSetChanged();
            changTextColor();

        } else if (requestCode == PictureTailorHelper.PHOTO_REQUEST_GALLERYS && resultCode == 200) {

            List<String> urls = data.getStringArrayListExtra("return");

            if (urls != null) {
                url_list.clear();
                url_list.addAll(urls);
                addImageAdapter.notifyDataSetChanged();
            }
            changTextColor();
            //话题返回
        } else if (resultCode == 200 && requestCode == 111) {
            if (data != null && data.getSerializableExtra("topicModel") != null) {
                topicModel = ((TopicModel) data.getSerializableExtra("topicModel"));
                setText();
                DataUtils.showSoftInputFromWindow(this, et_content);
            }

            //图片查看器返回
        } else if (requestCode == 100 && resultCode == 200) {

            url_list.clear();
            if (data != null) {
                url_list.addAll(0, data.getStringArrayListExtra("return"));//返回的图片地址

            }
            addImageAdapter.notifyDataSetChanged();
            changTextColor();
        } else if (requestCode == 200 && resultCode == 300) {
            if (data != null) {
                if (data.getSerializableExtra("address") != null) {
                    addressModel = (AddressInforBean) data.getSerializableExtra("address");
                    if (addressModel.isShowAddress()) {
                        tv_address.setText(addressModel.getCity() + "·" + addressModel.getName());
                    } else {
                        tv_address.setText("地址");
                    }
                }
            }
        }
    }

    private void setText() {
        if (topicModel != null)
            DataUtils.setTopicText(et_content, topicModel, cursorPosition);
    }

    /**
     * 发表帖子
     */
    private void createTopic() {
        GroupHelper.createGroupTopic(this, model, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                MyShareBean shareBean = JSON.parseObject(result, MyShareBean.class);
                if (shareBean != null) {
                    CommentActivity.start(CreateTopicActivity.this, shareBean.getShareObjectId(), 0);
                    ImageUtils.deleteCompressFile();
                    setResult(200);
                    finish();
                }
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });

    }

    /**
     * 照片选择对话菜单
     */
    private void initPopWindow() {

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
                        if (url_list.size() < 9)
                            clippHelper.takePhoto(CreateTopicActivity.this);
                        break;

                    case R.id.btn_02:
                        clippHelper.selectPictures(CreateTopicActivity.this, url_list, 9, null);
                        break;
                }

            }
        }, list, colorList, false);
    }

    /**
     * 压缩图片
     *
     * @param path
     */
    private void compressImage(final List<String> path) {

        showLoadingImage(this, LoadingType.NONE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (path.size() > 0) {
                    List<String> files = ImageUtils.comPressBitmaps(CreateTopicActivity.this, path, 80);
                    file_url_compress.addAll(files);
                }
                Message message = Message.obtain();
                message.what = 0;
                message.obj = file_url_compress;
                handler.sendMessage(message);
            }
        }).start();
    }

    //获取内容
    private String getContent() {

        return et_content.getText().toString();
    }

    private void getHotTopicList() {
        GroupHelper.getGroupHotTopicList(this, associationId, "", 0, 10, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                List<TopicModel> list = JSON.parseArray(JSON.parseObject(result).get("items").toString(), TopicModel.class);
                if (list != null) {
                    hotTopicList.addAll(list);
                    doScrollAction();
                }
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

    private void doScrollAction() {
        String topic[] = new String[hotTopicList.size()];
        for (int i = 0; i < hotTopicList.size(); i++) {
            topic[i] = "#" + hotTopicList.get(i).getTagName() + "#";
        }
        scrollTextView.setDuring(2000);
        scrollTextView.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
        scrollTextView.setTextList(topic);
        scrollTextView.startAutoScroll();
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
        emotionMainFragment.bindToContentView(scrollView);
        emotionMainFragment.bindToEditText(et_content);
        emotionMainFragment.setExchangeButton(findViewById(R.id.tv_emtion));
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fl_emotionview_main, emotionMainFragment);
        //ransaction.addToBackStack(null);
        //提交修改
        transaction.commit();
    }

    public void setInput() {
//        final View rootView = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
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
        if (height > 100) {
//            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) log_bottom.getLayoutParams();
//            lp.setMargins(0, 0, 0, EmotionKeyboard.isShow ? height : 0);
//            log_bottom.setLayoutParams(lp);
        }
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

    private void changTextColor() {
        ViewUtils.changeTitleRightClickable(this, !StringUtil.isEmpty(et_content.getText().toString()) || url_list.size() > 0);
    }
}
