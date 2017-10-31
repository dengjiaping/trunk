package com.histudent.jwsoft.histudent.commen.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gzsll.jsbridge.WVJBWebView;
import com.histudent.jwsoft.histudent.HiStudentLog;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.hiworld.adapter.UploadImageRecyclerViewAdapter;
import com.histudent.jwsoft.histudent.call.IGuideCommonClickListener;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.enums.LoactionType;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.enums.ShowImageType;
import com.histudent.jwsoft.histudent.commen.enums.YdType;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.DialogButtonListener;
import com.histudent.jwsoft.histudent.commen.listener.MyDialogListener;
import com.histudent.jwsoft.histudent.commen.photo.utils.ImageUtils;
import com.histudent.jwsoft.histudent.commen.photo_selecter.imageloader.SelectPhotoActivity;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.FileUtil;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.view.Dialog_getPicture;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.comment2.activity.MapActivity;
import com.histudent.jwsoft.histudent.comment2.bean.AddressInforBean;
import com.histudent.jwsoft.histudent.comment2.utils.ActionTypeEnum;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* 发表轨迹页面
* */
public class TrackActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_add;
    private IconView title_right_image;
    private TextView title_left, title_right, title, tv_authority;
    private RecyclerView recyclerView;
    private ArrayList<String> url_list;//存放选中图片的路径
    private File file_takePhoto;//拍照后照片存储的路径
    private UploadImageRecyclerViewAdapter addImageAdapter;
    private AddressInforBean addressInforBean;
    private Double latitude, longitude;
    private String location;
    private List<String> userids;
    private EditText et_content;
    private int width;
    private List<String> list_tmp = new ArrayList<>();
    private RelativeLayout relative_quality, relative_address;
    private UploadImageRecyclerViewAdapter.OnRecyclerViewOnClickListener listener;
    private TextView tv_address;
    private String city;
    private List<ActionListBean.ImagesBean> listImageBean = new ArrayList();
    private CheckBox qq_checkBox, friend_checkBox;
    private LinearLayout essay_topic;
    static WVJBWebView.WVJBResponseCallback callback;
    private String info;
    private boolean isAutoLocation = false;

    public static void start(BaseActivity activity, WVJBWebView.WVJBResponseCallback callback) {
        TrackActivity.callback = callback;
        activity.startActivity(new Intent(activity, TrackActivity.class));

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    closeLoadingImage();
                    String content = (String) msg.obj;

                    if (msg.arg1 == HiWorldCache.SUCCESS) {
                        JSONObject object = JSON.parseObject(content);
                        if (object != null) {
                            Intent intent = new Intent();
                            int n = 0;
                            if (qq_checkBox.isChecked()) {
                                n = 2;
                            } else if (friend_checkBox.isChecked()) {
                                n = 1;
                            }
                            intent.putExtra("flag_share", n);
                            intent.putExtra("share_content", content);
                            setResult(200, intent);
                            Toast.makeText(TrackActivity.this, "轨迹发布成功！", Toast.LENGTH_LONG).show();
                            finish();
                            callback.callback("1");

                        } else {
                            setResult(0);
                            Toast.makeText(TrackActivity.this, "轨迹发布失败，请稍后再试！", Toast.LENGTH_LONG).show();
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
                        postEssayData();
                    }
                    break;

                case 3:
                    showUserShadeWindow(TrackActivity.this, title_left, YdType.ESSAY_QX, new IGuideCommonClickListener() {
                        @Override
                        public void onClick() {
                            sendEmptyMessage(4);
                        }
                    });
                    break;
                case 4:
                    showUserShadeWindow(TrackActivity.this, title_left, YdType.ESSAY_SHARE, null);
                    break;
                case 5:
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    break;
            }
        }
    };

    @Override
    public void initView() {
        userids = new ArrayList<>();
        title_left = (TextView) findViewById(R.id.title_left_text);
        title_right = (TextView) findViewById(R.id.title_right_text);
        title = (TextView) findViewById(R.id.title_middle_text);
        title_right_image = (IconView) findViewById(R.id.title_right_image);

        qq_checkBox = (CheckBox) findViewById(R.id.iv_share_QQzone);
        friend_checkBox = (CheckBox) findViewById(R.id.iv_share_friend_quan);
        essay_topic = (LinearLayout) findViewById(R.id.essay_topic);

        title_left.setText("返回");
        title.setText("记录读书轨迹");
        title_right.setText("提交");
        title_right_image.setVisibility(View.VISIBLE);
        title_right_image.setText(R.string.icon_enter);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        addressInforBean = HiWorldCache.getUserLocationInfor();

        //选择点击图片的时间监听
        listener = new UploadImageRecyclerViewAdapter.OnRecyclerViewOnClickListener() {
            @Override
            public void setOnItemClickListener(View v, final int postion) {

                if (url_list != null && url_list.size() > 0) {
                    String path = url_list.get(postion);
                    if ("add".equals(path)) {
                        initPopWindow();
                    } else {
                        ActionListBean.ImagesBean imagesBean;
                        listImageBean.clear();
                        for (String picId : url_list) {
                            imagesBean = new ActionListBean.ImagesBean();
                            imagesBean.setBigSizeUrl(picId);
                            imagesBean.setThumbnailUrl(picId);
                            listImageBean.add(imagesBean);
                        }

                        listImageBean.remove(listImageBean.size() - 1);

                        ImageBrowserActivity.start(TrackActivity.this, postion, 100,
                                (ArrayList<ActionListBean.ImagesBean>) listImageBean, ShowImageType.DElET, 0, "");
                    }
                }

            }
        };

        //控件的初始化
        recyclerView = ((RecyclerView) findViewById(R.id.grid_view));
        relative_address = ((RelativeLayout) findViewById(R.id.relative_address));
        relative_quality = ((RelativeLayout) findViewById(R.id.relative_photo_quality));
        tv_authority = ((TextView) findViewById(R.id.tv_authority));
        iv_add = new ImageView(this);
        iv_add.setBackgroundResource(R.mipmap.add_gray);
        et_content = ((EditText) findViewById(R.id.et_content));
        et_content.requestFocus();
        info = "#21天阅读习惯养成# ";
        et_content.setText(info);//设置EditText控件的内容
        et_content.setSelection(info.length());//将光标移至文字末尾
        tv_address = ((TextView) findViewById(R.id.tv_address));

        url_list = new ArrayList<>();
        url_list.add("add");
        addImageAdapter = new UploadImageRecyclerViewAdapter(url_list, listener);
        recyclerView.setAdapter(addImageAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));

        relative_address.setOnClickListener(this);
        relative_quality.setOnClickListener(this);
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

        handler.sendEmptyMessageDelayed(5, 500);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {

            /**
             * 地址
             */
            case R.id.relative_address:
                MapActivity.startForResult(this, addressInforBean, LoactionType.DYNAMIC, isAutoLocation, true);
                break;

            /**
             * 权限
             */
            case R.id.relative_photo_quality:
                CommenPrivacySetting.start(TrackActivity.this, tv_authority.getText().toString().trim(), ActionTypeEnum.OWNER, 102);
                break;

            /**
             * 发布轨迹
             */
            case R.id.title_right:
            case R.id.public_track:
                if (StringUtil.isEmpty(et_content.getText().toString().replace(info, "").trim())) {
                    Toast.makeText(this, "轨迹内容不能为空", Toast.LENGTH_LONG).show();
                } else {
                    showLoadingImage(TrackActivity.this, LoadingType.FLOWER);
                    deleteNullData(url_list);

                    //随记中有图片需要压缩后上传
                    if (url_list.size() != 0) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                compressImage(url_list);
                            }
                        }).start();

                        //没图片直接上传
                    } else {
                        postEssayData();
                    }
                }

                break;

            /**
             * 取消发布
             */
            case R.id.title_left:
                ReminderHelper.getIntentce().showDialog(TrackActivity.this, "提示", "是否确定取消？", "确定", new DialogButtonListener() {
                    @Override
                    public void setOnDialogButtonListener() {
                        if (callback == null)
                            callback.callback("2");
                        setResult(100);
                        finish();
                    }
                }, "取消", new DialogButtonListener() {
                    @Override
                    public void setOnDialogButtonListener() {

                    }
                });
                break;

            /**
             * 插入话题
             */
            case R.id.essay_topic:
                String content = et_content.getText().toString();
                content = content + "#请输入话题内容#";
                et_content.setText(content);
                et_content.setSelection(content.length() - 8, content.length() - 1);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        switch (resultCode) {
//            //权限数据返回
//            case 102:
//                if (data.getStringExtra("authority") != null) {
//
//                    String authority = data.getStringExtra("authority");
//                    tv_authority.setText(authority);
//
//                    if (authority.equals("部分人可见") || authority.equals("部分人不可见")) {
//                        userids = data.getStringArrayListExtra("list");
//                    }
//                }
//                break;
//
//            //地址数据返回
//            case 300:
//                if (data != null) {
//                    if (data.getSerializableExtra("address") != null) {
//                        addressInforBean = (AddressInforBean) data.getSerializableExtra("address");
//                        tv_address.setText(addressInforBean.getName());
//                        isNeedMyLocation = false;
//                        latitude = addressInforBean.getLatitude();
//                        longitude = addressInforBean.getLongitude();
//                        city = addressInforBean.getCity();
//                    }
//                }
//                break;
//
//            case -1:
//                // 如果是调用相机拍照时
//                url_list.add(file_takePhoto.getPath());
//                url_list.add("add");
//                addImageAdapter.notifyDataSetChanged();
//                break;
//
//            case 0:
//                // 如果是调用相机拍照时
//                if (requestCode == 2 || requestCode == 1001) {
//                    url_list.add("add");
//                    addImageAdapter.notifyDataSetChanged();
//                }
//                break;
//
//            case 200:
//
//                url_list.clear();
//                url_list.addAll(0, data.getStringArrayListExtra("return"));
//                url_list.add("add");
//                addImageAdapter.notifyDataSetChanged();
//                break;
//
//            case 800:
//                url_list.clear();
//                if (data != null) {
//                    List<String> list_tmp = data.getStringArrayListExtra("list");
//                    if (list_tmp != null)
//                        url_list.addAll(list_tmp);
//                }
//                url_list.add("add");
//                addImageAdapter.notifyDataSetChanged();
//                break;
//        }

        /**
         * 权限返回
         */
        if (requestCode == 102 && resultCode == 200) {
            if (data.getStringExtra("authority") != null) {
                String authority = data.getStringExtra("authority");
                tv_authority.setText(authority);

                if (authority.equals("部分人可见") || authority.equals("部分人不可见")) {
                    userids = data.getStringArrayListExtra("list");
                }
            }
        }
        /**
         * 地址返回
         */
        else if (requestCode == 200 && resultCode == 300) {
            if (data != null) {
                if (data.getSerializableExtra("address") != null) {
                    if (addressInforBean.isShowAddress()) {
                        addressInforBean = (AddressInforBean) data.getSerializableExtra("address");
                        tv_address.setText(addressInforBean.getName());
                        latitude = addressInforBean.getLatitude();
                        longitude = addressInforBean.getLongitude();
                        city = addressInforBean.getCity();
                        isAutoLocation = false;
                    } else {
                        isAutoLocation = true;
                        tv_address.setText("地址");
                    }
                }
            }
        }


        /**
         * 拍照返回
         */
        else if (requestCode == 2) {
            if (resultCode == -1) {
                // 拍照成功

                if (file_takePhoto.exists()) {
                    url_list.add(file_takePhoto.getPath());
                    url_list.add("add");
                    addImageAdapter.notifyDataSetChanged();
                }

            } else if (resultCode == 0) {
                // 取消拍照
                url_list.add("add");
                addImageAdapter.notifyDataSetChanged();
            }
        }
        /**
         * 拍选择照片返回
         */
        else if (requestCode == 1001) {
            if (resultCode == 200) {
                // 操作成功
                url_list.clear();
                url_list.addAll(0, data.getStringArrayListExtra("return"));
                url_list.add("add");
                addImageAdapter.notifyDataSetChanged();
            } else if (resultCode == 0) {
                // 取消操作
                url_list.add("add");
                addImageAdapter.notifyDataSetChanged();
            }
        }

    }

    /**
     * 对图片进行压缩
     */
    private void compressImage(List<String> path) {

        for (int i = 0; i < path.size(); i++) {
            ImageUtils.saveBitmap(this, path.get(i), 60, handler, path.size());
        }

    }

    /**
     * 初始化拍照选择照片的PopWindow
     */
    private void initPopWindow() {

        Dialog_getPicture dialog_head = new Dialog_getPicture(this, "获取照片", getResources().getStringArray(R.array.getPicture), new MyDialogListener() {
            @Override
            public void callBack(final int index) {

                switch (index) {
                    case 0:
                        url_list.remove(url_list.size() - 1);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        file_takePhoto = new File(FileUtil.getPathFactory(FileUtil.CacheType.IMAGE, false));
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file_takePhoto));
                        startActivityForResult(intent, 2);
                        break;

                    case 1:
                        url_list.remove(url_list.size() - 1);
                        SelectPhotoActivity.start(TrackActivity.this, url_list, 9, null);
                        break;
                }
            }
        });
        dialog_head.show();
    }

    private void postEssayData() {

        Map<String, Object> map = new HashMap();

        if (addressInforBean == null) {
            addressInforBean = new AddressInforBean();

        }
        latitude = addressInforBean.getLatitude();
        longitude = addressInforBean.getLongitude();
        location = addressInforBean.getCity() + addressInforBean.getAreaStr();

        int privacyStatus = getPrivacyStatus(tv_authority.getText().toString());
//        if (privacyStatus != -1) {
//            switch (privacyStatus) {
//                case 0:
//                    break;
//                case 1:
//                    break;
//                case 2:
//                    break;
//                case 3:
//                    map.put("userids", dealWithUserIds(userids));
//                    break;
//                case 4:
//                    map.put("userids", dealWithUserIds(userids));
//
//                    break;
//            }
//            map.put("privacyStatus", privacyStatus + "");
//
//        }

        map.put("microblogBody", et_content.getText().toString());
        map.put("longitude", longitude + "");
        map.put("latitude", latitude + "");
        map.put("location", location);
//        map.put("ownerId", HiCache.getInstance().getLoginUserInfo().getUserId());
//        map.put("microblogLink", "");


        deleteNullData(url_list);
        if (url_list.size() != 0) {
            for (int i = 0; i < url_list.size(); i++) {
                map.put("file" + i, url_list.get(i));
            }

        }

        HiWorldCache.postHttpData(this, handler, 0, HistudentUrl.createTrack, map, HiWorldCache.Quarry, false,true);

    }

    /**
     * 权限的判断
     */
    private int getPrivacyStatus(String authority) {

        int privacyStatus = -1;

        if ("公开".equals(authority)) {
            privacyStatus = 0;
        } else if ("同学可见".equals(authority)) {
            privacyStatus = 1;
        } else if ("仅自己可见".equals(authority)) {
            privacyStatus = 2;
        } else if ("部分人可见".equals(authority)) {
            privacyStatus = 3;
        } else if ("部分人不可见".equals(authority)) {
            privacyStatus = 4;
        } else {

        }
        return privacyStatus;
    }

    /**
     * 删除多于的数据
     *
     * @param list
     */
    private void deleteNullData(List<String> list) {

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals("add")) {
                list.remove(list.get(i));
            }
        }
    }

    private String dealWithUserIds(List<String> userids) {

        StringBuilder builder;
        if (userids.size() > 0) {
            builder = new StringBuilder();
            for (int i = 0; i < userids.size() - 1; i++) {

                builder.append(userids.get(i).replaceAll("\"", "")).append(",");
            }

            builder.append(userids.get(userids.size() - 1).replaceAll("\"", ""));


//            Log.e("useids==>", builder.toString());
            return builder.toString();
        }


        return "";

    }

    /**
     * 键盘弹出来的时候向上偏移高度
     */
    public void setInput() {

        final View rootView = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        final View decorView = getWindow().getDecorView();

        //该Activity的最外层Layout
        final View activityRootView = findViewById(R.id.activityRoot);

        //给该layout设置监听，监听其布局发生变化事件
        activityRootView
                .getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver
                                                   .OnGlobalLayoutListener() {
                                               @Override
                                               public void onGlobalLayout() {
                                                   Rect rect = new Rect();
                                                   decorView.getWindowVisibleDisplayFrame(rect);
                                                   int heightDifferent = SystemUtil.getScreenHeight_() - rect.bottom;//计算软键盘占有的高度  = 屏幕高度 - 视图可见高度
                                                   if (heightDifferent > 200) {
                                                       essay_topic.setVisibility(View.VISIBLE);
                                                   } else {
                                                       essay_topic.setVisibility(View.GONE);
                                                   }

                                                   FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) rootView.getLayoutParams();
                                                   lp.setMargins(0, 0, 0, heightDifferent);//设置ScrollView的marginBottom的值为软键盘占有的高度即可
                                                   rootView.requestLayout();
                                               }
                                           }
                );
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_track;
    }

}
