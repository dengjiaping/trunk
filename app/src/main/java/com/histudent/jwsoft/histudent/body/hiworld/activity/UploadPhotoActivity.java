package com.histudent.jwsoft.histudent.body.hiworld.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.histudent.jwsoft.histudent.HiStudentLog;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.hiworld.adapter.SyncClassAdapter;
import com.histudent.jwsoft.histudent.body.hiworld.adapter.UploadImageRecyclerViewAdapter;
import com.histudent.jwsoft.histudent.body.hiworld.bean.RelationPersonModel;
import com.histudent.jwsoft.histudent.body.hiworld.bean.SimpleUserModel;
import com.histudent.jwsoft.histudent.body.hiworld.bean.TopicModel;
import com.histudent.jwsoft.histudent.body.mine.model.UserClassListModel;
import com.histudent.jwsoft.histudent.model.listener.IPermissionCallBackListener;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.CommentActivity;
import com.histudent.jwsoft.histudent.commen.activity.ImageBrowserActivity;
import com.histudent.jwsoft.histudent.commen.activity.SelecteClassmatesActiviy;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.enums.ShowImageType;
import com.histudent.jwsoft.histudent.commen.helper.PictureTailorHelper;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.keyword.emotionkeyboardview.EmotionKeyboard;
import com.histudent.jwsoft.histudent.commen.keyword.fragment.EmotionMainFragment;
import com.histudent.jwsoft.histudent.commen.listener.DialogButtonListener;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.listener.ParserCallBack;
import com.histudent.jwsoft.histudent.commen.photo.PhotoHelper;
import com.histudent.jwsoft.histudent.commen.photo.utils.ImageUtils;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.ComViewUitls;
import com.histudent.jwsoft.histudent.commen.utils.DataUtils;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.ListenerUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.commen.view.MyGridView;
import com.histudent.jwsoft.histudent.comment2.bean.AddressInforBean;
import com.histudent.jwsoft.histudent.comment2.utils.ActionTypeEnum;
import com.histudent.jwsoft.histudent.comment2.utils.SeletClassMateEnum;
import com.histudent.jwsoft.histudent.comment2.utils.ViewUtils;
import com.netease.nim.uikit.common.util.string.StringUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

//Hi圈中点击相册后跳转后的上传照片的页面
public class UploadPhotoActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_right_text)
    public TextView title_right;
    @BindView(R.id.tv_quality)
    public TextView tv_photo_quality;
    @BindView(R.id.tv_file_name)
    public TextView tv_file_name;
    private Intent intent;
    private String fileName;
    @BindView(R.id.grid_view)
    public RecyclerView mRecyclerView;
    private UploadImageRecyclerViewAdapter addImageAdapter;
    private String ownId, albumId;
    private ArrayList<String> url_list;
    private List<String> list_tmp = new ArrayList<>();
    @BindView(R.id.relative_file_name)
    public RelativeLayout relative_file_name;
    @BindView(R.id.relative_photo_quality)
    public RelativeLayout relative_photo_quality;
    private String id, Key;
    private boolean isCanCreateFile;
    @BindView(R.id.essay_topic)
    public RelativeLayout essay_topic;
    private List<ActionListBean.ImagesBean> listImageBean = new ArrayList<>();
    private ActionTypeEnum typeEnum;
    private PictureTailorHelper clippHelper;
    private ArrayList<SimpleUserModel> atList;
    private TopicModel topicModel;//话题内容
    private SyncClassAdapter syncClassAdapter;//同步班级适配
    private ArrayList<UserClassListModel> classListModels;//同步班级集合
    private ArrayList<RelationPersonModel> relationPersonModelList;//用于存放图片关联人的信息
    @BindView(R.id.gridview)
    public MyGridView gridView;
    @BindView(R.id.et_body)
    public EditText et_cotent;
    private int SelectionStart; //记录光标的位置
    private EmotionMainFragment emotionMainFragment;
    private int privacyStatus = 0;
    private AddressInforBean addressModel;
    private List<SimpleUserModel> allAtList;
    @BindView(R.id.scrollView)
    public ScrollView scrollView;
    private UserClassListModel classModel;
    @BindView(R.id.tv_tag)
    public TextView fileTag;
    @BindView(R.id.title_left_image)
    public IconView title_left;
    @BindView(R.id.keyWord_close)
    public RelativeLayout keyWord_close;
    @BindView(R.id.icon_01)
    public IconView mQualityImg;
    @BindView(R.id.icon_0)
    public IconView mUploadImg;
    @BindView(R.id.sycn_image)
    public IconView mClass;
    private String defaultAlbumId;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                case 1:

                    if (msg.arg1 == 0) {
                        HiStudentLog.e("---->压缩保存失败...");
                    } else {
                        getCompressImagePath();
                        uploadImage();
                    }
                    break;

                //上传照片的相册的结果处理
                case 4:

                    String content = (String) msg.obj;
                    if (msg.arg1 == HiWorldCache.SUCCESS) {
                        JSONObject object = JSON.parseObject(content);

                        //上传图片成功后，删除压缩图片
                        if (object != null) {
                            ImageUtils.deleteCompressFile();
                            setResult(200);
                        }


                        try {
                            org.json.JSONObject object1 = new org.json.JSONObject(content);
                            CommentActivity.start(UploadPhotoActivity.this, object1.optString("shareObjectId"), 0);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        ClassHelper.gotoAlbum(UploadPhotoActivity.this, albumId, typeEnum == ActionTypeEnum.CLASSANDOWNER ?
//                                HiCache.getInstance().getLoginUserInfo().getUserId().equals(ownId) ? ActionTypeEnum.OWNER : ActionTypeEnum.CLASS : typeEnum);
                        UploadPhotoActivity.this.finish();
                    }
                    break;

            }
        }
    };


    /**
     * 照片视图的item监听
     */
    private UploadImageRecyclerViewAdapter.OnRecyclerViewOnClickListener mOnRecyclerViewOnClickListener = (View v, int postion) -> {
        if (url_list == null || clippHelper == null)
            return;
        if (postion == url_list.size() - 1 && url_list.get(url_list.size() - 1).equals("add")) {
            //跳转图片选择界面
            checkTakePhotoPermission(new IPermissionCallBackListener() {
                @Override
                public void doAction() {
                    url_list.remove("add");
                    clippHelper.selectPictures(UploadPhotoActivity.this, url_list, 9, new ArrayList<>());
                }
            });
        } else {
            gotoImageBrowser(postion);
        }
    };
    private RecyclerView.LayoutManager mLayoutManager;


    public static void start(Activity activity, ActionTypeEnum typeEnum, String id, boolean isCanCreateFile, int requestCode) {
        Intent intent = new Intent(activity, UploadPhotoActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("typeEnum", typeEnum);
        intent.putExtra("isCanCreateFile", isCanCreateFile);
        activity.startActivityForResult(intent, requestCode);
    }






    @Override
    public int setViewLayout() {
        return R.layout.activity_upload_photo;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        clippHelper = PictureTailorHelper.getInstance();
        //接受选择上传到相册
        id = getIntent().getStringExtra("id");
        typeEnum = ((ActionTypeEnum) getIntent().getSerializableExtra("typeEnum"));
        initKey();
        findViewById(R.id.title_left_image).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.title_middle_text)).setText("上传照片");
        title_left.setVisibility(View.VISIBLE);
        title_left.setText(R.string.icon_close);
        title_left.setTextSize(15);
        title_right.setText("上传");
        title_right.setTextColor(getResources().getColor(R.color.text_black_h1));
        findViewById(R.id.title_right).setEnabled(false);
    }


    @Override
    public void doAction() {
        super.doAction();
        isCanCreateFile = getIntent().getBooleanExtra("isCanCreateFile", false);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        ListenerUtils.setTextChangeListener(et_cotent, ((TextView) findViewById(R.id.limit_num)));
        url_list = new ArrayList<>();
        atList = new ArrayList<>();
        allAtList = new ArrayList<>();
        classListModels = new ArrayList<>();
        relationPersonModelList = new ArrayList<>();
        addImageAdapter = new UploadImageRecyclerViewAdapter(url_list, mOnRecyclerViewOnClickListener);
        mRecyclerView.setAdapter(addImageAdapter);
        mLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        syncClassAdapter = new SyncClassAdapter(classListModels, this);
        gridView.setAdapter(syncClassAdapter);
        setInput();
        if (typeEnum == ActionTypeEnum.CLASSANDOWNER || typeEnum == ActionTypeEnum.OWNER) {
            getSyncClassDate();
            findViewById(R.id.user_sycn_class_layout).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.user_sycn_class_layout).setVisibility(View.GONE);
        }
        getAddressInfor();
        getUserLastCreateAlbum();
        initEmotionMainFragment();
        ListenerUtils.setOnDeleteUserListener(et_cotent, allAtList);
        keyWord_close.setOnClickListener((View v) -> emotionMainFragment.isInterceptBackPress());
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        clippHelper.selectPictures(this, url_list, 9, relationPersonModelList);
        loadListener();
    }

    private void loadListener() {
        relative_file_name.setOnClickListener(this);
        relative_photo_quality.setOnClickListener(this);
        findViewById(R.id.tv_at).setOnClickListener(this);
    }

    //跳转到图片查看器
    private void gotoImageBrowser(int position) {
        if (url_list != null && url_list.size() > 1) {
            if (url_list.contains("add")) {
                url_list.remove("add");
            }
            ActionListBean.ImagesBean imagesBean;
            listImageBean.clear();
            for (String picId : url_list) {
                File f = new File(picId);
                imagesBean = new ActionListBean.ImagesBean();
                imagesBean.setBigSizeUrl(picId);
                imagesBean.setName(f.getName());
                imagesBean.setThumbnailUrl(picId);
                listImageBean.add(imagesBean);
            }

            ImageBrowserActivity.start(UploadPhotoActivity.this, position, 100, (ArrayList<ActionListBean.ImagesBean>) listImageBean,
                    ShowImageType.RELATION, relationPersonModelList);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    //获取默认同步的班级信息
    public void getSyncClassDate() {
        DataUtils.GetUserSyncClassList(this, classListModels, syncClassAdapter, true);
        if (classListModels != null && classListModels.size() > 0) {
            mClass.setTextColor(getResources().getColor(R.color.page_bg_color_v2));
        } else {
            mClass.setTextColor(getResources().getColor(R.color.icon_color));
        }
    }

    private void initKey() {
        Key = typeEnum == ActionTypeEnum.CLASS ? "classId" : typeEnum == ActionTypeEnum.GROUP ? "groupId" : "ownerId";
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            //取消
            case R.id.title_left:

                showBackNotice();
                break;

            //完成
            case R.id.title_right:
                Log.e("----需要上传的@好友的关系信息----", DataUtils.getAtUserString(atList));//相册关联信息
                if (StringUtil.isEmpty(fileName)) {
                    Toast.makeText(this, "请选择需要上传的相册", Toast.LENGTH_LONG).show();
                } else {

                    if (url_list.size() > 1) {

                        showLoadingImage(UploadPhotoActivity.this, LoadingType.FLOWER);

                        deleteNullData(url_list);
                        //压缩上传
                        if (!tv_photo_quality.getText().toString().equals("高清")) {

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    PhotoHelper.saveImageInFile(UploadPhotoActivity.this, tv_photo_quality.getText().toString(), url_list, handler);

                                }
                            }).start();

                            //高清不压缩直接上传
                        } else {
                            uploadImage();
                        }

                    } else {
                        Toast.makeText(this, "请选择上传的照片", Toast.LENGTH_LONG).show();
                    }
                }


                break;

            //选择上传图片的相册
            case R.id.relative_file_name:

                SelectUploadAlbumActivity.start(this, typeEnum, id, isCanCreateFile);

                break;

            //选择上传图片的质量
            case R.id.relative_photo_quality:

                PhotoQualitySelect.start(this, tv_photo_quality.getText().toString(), 1000);

                break;


            //@
            case R.id.tv_at:

                SelectionStart = et_cotent.getSelectionStart();
                SelecteClassmatesActiviy.start(this, new ArrayList<SimpleUserModel>(), 111, SeletClassMateEnum.AT);

                break;

            //同步班级
            case R.id.sycn:

                SelecteClassActivity.start(UploadPhotoActivity.this, classListModels, ownId, 555);
        }
    }

    //接受返回的选择上传的照片质量
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // TODO: 2017/6/22  

        switch (resultCode) {

            //选择本地图片返回值,或者图片查看器返回值
            case 200:

                switch (requestCode) {

                    //@返回
                    case 111:

                        if (data != null) {
                            atList.clear();
                            atList.addAll(((ArrayList) data.getSerializableExtra("userModel")));
                            allAtList.addAll(atList);
                            setText();
                            Log.e("atList===>", atList.toString());
                        }

                        break;


                    //话题
                    case 333:
                        if (data != null) {
                            topicModel = ((TopicModel) data.getSerializableExtra("topicModel"));//获取话题
                            Log.e("topicModel", topicModel.toString());
                        }

                        break;

                    //同步班级数据返回
                    case 555:
                        if (data != null && data.getSerializableExtra("classList") != null) {
                            classListModels.clear();
                            List<UserClassListModel> userClassListModels = (List<UserClassListModel>) data.getSerializableExtra("classList");
                            classListModels.addAll(userClassListModels);
                            syncClassAdapter.notifyDataSetChanged();
                            Log.e("Size", classListModels.size() + "");
                            if (userClassListModels != null && userClassListModels.size() > 0) {
                                mClass.setTextColor(getResources().getColor(R.color.page_bg_color_v2));
                            } else {
                                mClass.setTextColor(getResources().getColor(R.color.icon_color));
                            }
                        }
                        break;

                    //图片查看器返回
                    case 100:
                        url_list.clear();
                        relationPersonModelList.clear();
                        if (data != null) {
                            //关联关系返回
                            relationPersonModelList.addAll((ArrayList<RelationPersonModel>) data.getSerializableExtra("relations"));
                            Log.e("--------", DataUtils.getRelationString(relationPersonModelList));//相册关联信息
                            addImageAdapter.initRelationData(relationPersonModelList);
                            url_list.addAll(data.getStringArrayListExtra("return"));
                            for (String s : url_list) {
                                Log.e("Tag", s);
                            }
                        }

                        if (url_list.size() < 9)
                            url_list.add("add");
                        addImageAdapter.notifyDataSetChanged();
                        changTextColor();

                        break;

                    case 1001:
                        url_list.clear();
                        if (data != null) {
                            url_list.addAll(data.getStringArrayListExtra("return"));
                            if (data.getSerializableExtra("relations") != null) {
                                relationPersonModelList.clear();
                                relationPersonModelList.addAll((ArrayList<RelationPersonModel>) data.getSerializableExtra("relations"));
                            }
                        }

                        addImageAdapter.initRelationData(relationPersonModelList);
                        for (String s : url_list) {
                            Log.e("Tag", s);
                        }
                        if (url_list.size() < 9)
                            url_list.add("add");
                        changTextColor();
                        addImageAdapter.notifyDataSetChanged();

                        break;
                }

                break;

            //上传图片选择放回值
            case 1000:
                if (data == null)
                    return;
                if (data.getStringExtra("quality") != null) {
                    String quality = data.getStringExtra("quality");
                    tv_photo_quality.setText(quality);
                    if (TextUtils.isEmpty(quality)) {
                        mQualityImg.setTextColor(getResources().getColor(R.color.icon_color));
                    } else {
                        mQualityImg.setTextColor(getResources().getColor(R.color.page_bg_color_v2));
                    }
                }
                break;


            //相册选择返回值
            case 400:
                if (data == null)
                    return;
                if (TextUtils.isEmpty(data.getStringExtra("albumId"))) {
                    mUploadImg.setTextColor(getResources().getColor(R.color.icon_color));
                } else {
                    mUploadImg.setTextColor(getResources().getColor(R.color.page_bg_color_v2));
                }
                fileName = data.getStringExtra("fileName");
                albumId = data.getStringExtra("albumId");
                ownId = data.getStringExtra("ownId");
                int privacyStatus = data.getIntExtra("privacyStatus", 0);
                if (fileName != null) {
                    tv_file_name.setText(fileName);
                }

                if (typeEnum == ActionTypeEnum.CLASSANDOWNER || typeEnum == ActionTypeEnum.OWNER) {

                    //相册标识的显示
                    if (!StringUtil.isEmpty(ownId)) {
                        fileTag.setVisibility(View.VISIBLE);

                        if (ownId.equals(HiCache.getInstance().getLoginUserInfo().getUserId())) {
                            fileTag.setText("个人");
                        } else {
                            fileTag.setText("班级");
                        }
                    } else {
                        fileTag.setVisibility(View.GONE);
                    }

                    ComViewUitls.setSyncViewVisiablityByNum(this, privacyStatus);
                    removeSyncClass(ownId);
                }
                changTextColor();

                break;

            //图片查看器或者本地选图片后退键返回
            case 0:
                if (requestCode == 1001 || requestCode == 100) {
                    if (url_list.size() < 9)
                        url_list.add("add");
                    addImageAdapter.notifyDataSetChanged();
                }
                break;

            case -1:
                url_list.remove(url_list.size() - 1);
                url_list.add(clippHelper.photo_path.getAbsolutePath());

                if (url_list.size() < 9)
                    url_list.add("add");
                addImageAdapter.notifyDataSetChanged();
                changTextColor();
                break;

            case -2:
                url_list.remove("add");
                if (url_list.size() < 9)
                    url_list.add("add");
                addImageAdapter.notifyDataSetChanged();
                changTextColor();

                break;


        }
    }

    private void removeSyncClass(String owerId) {

        if (!StringUtil.isEmpty(owerId)) {
            if (classModel != null && !isContain(classModel.getClassId())) {
                classListModels.add(classModel);
            }

            boolean tag = isContain(owerId);
            if (tag && classModel != null) {
                classListModels.remove(classModel);
            }
            syncClassAdapter.notifyDataSetChanged();
        }
    }

    //设置at界面
    private void setText() {

        if (!StringUtil.isEmpty(DataUtils.getAtUserNameStr(atList))) {
            if (et_cotent.getText().length() + DataUtils.getAtUserNameStr(atList).length() > 140) {
                Toast.makeText(this, "文字长度超出范围，请先删除部分内容后再操作！", Toast.LENGTH_LONG).show();
                atList.clear();
            } else {
                DataUtils.setAtText(et_cotent, SelectionStart, DataUtils.getAtUserNameStr(atList));
            }
        }
    }

    //将选择的图片上传的制定相册
    private void uploadImage() {

        //缓存我的相册的相册数据
        Map<String, Object> map1 = new HashMap<>();

        deleteNullData(url_list);

        if (addressModel == null) {
            addressModel = new AddressInforBean();
        }

        map1.put("latitude", addressModel.getLatitude() + "");
        map1.put("albumId", albumId);
        map1.put("location", addressModel.getCity() + addressModel.getAreaStr());
        map1.put("longitude", addressModel.getLongitude() + "");
        map1.put("description", StringUtil.isEmpty(et_cotent.getText().toString()) ? "" : et_cotent.getText().toString());//相册描述

        for (int i = 0; i < url_list.size(); i++) {
            map1.put("file" + i, url_list.get(i));
            Log.e("filePath", url_list.get(i));
        }

        //私有权限不需上传的
        if (privacyStatus != 2) {

            Log.e("----需要上传的同步班级信息----", DataUtils.getSyncClassIds(classListModels).toString());
            Log.e("----需要上传的图片关联关系信息----", DataUtils.getRelationString(relationPersonModelList));//相册关联信息
            Log.e("----需要上传的@好友的关系信息----", DataUtils.getAtUserString(atList));//at用户需要上传的信息


            DataUtils.deleteUser(et_cotent.getText().toString(), allAtList);//删除用户手动删除的用户
            map1.put("atUser", DataUtils.getAtUserString(allAtList));//atUser
            map1.put("associatedUsers", DataUtils.getRelationString(relationPersonModelList));
            map1.put("classIds", DataUtils.getSyncClassIds(classListModels));
        }

        HiWorldCache.postHttpData(this, handler, 4, HistudentUrl.hiworld_upload_image_url, map1, HiWorldCache.Quarry, false,true);

    }

    //删除多于的数据
    private void deleteNullData(List<String> list) {

        if (list == null)
            return;

        for (int i = 0; i < list.size(); i++) {
            if (("add").equals(list.get(i))) {
                list.remove(list.get(i));
            }
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
        emotionMainFragment.bindToContentView(scrollView);
        emotionMainFragment.bindToEditText(et_cotent);
        emotionMainFragment.setExchangeButton(findViewById(R.id.tv_emtion));
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_emotionview_main, emotionMainFragment);

        //提交修改
        transaction.commit();
        emotionMainFragment.setCallBack(new ParserCallBack() {
            @Override
            public void parser(String result) {

                emotionMainFragment.setEmotionIsVisiable(false);
                findViewById(R.id.essay_topic).setVisibility(View.VISIBLE);
                if (et_cotent.getText().toString().length() + result.length() >= 140) {

                    Toast.makeText(UploadPhotoActivity.this, "文字长度超出范围，请先删除部分内容后再操作！", Toast.LENGTH_LONG).show();
                } else {
                    DataUtils.setAtText(et_cotent, SelectionStart, result);
                }

            }
        });
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

        if (EmotionKeyboard.onClikeExchangeButton) {
            EmotionKeyboard.onClikeExchangeButton = false;
            return;
        }

        if (EmotionKeyboard.isShow) return;

        if (height > 100) {
            essay_topic.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) essay_topic.getLayoutParams();
            lp.setMargins(0, 0, 0, height);
            essay_topic.setLayoutParams(lp);
        } else {
            essay_topic.setVisibility(View.GONE);
        }
    }

    public void getAddressInfor() {

        addressModel = HiWorldCache.getUserLocationInfor();
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


    private boolean isContain(String classId) {

        for (UserClassListModel model : classListModels) {

            if (classId.equals(model.getClassId())) {
                classModel = model;
                return true;
            }
        }
        return false;
    }


    //改变发布的点击状态
    private void changTextColor() {

        ViewUtils.changeTitleRightClickable(this, !StringUtil.isEmpty(ownId) && url_list.size() > 1 ? true : false);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            showBackNotice();
        }
        return super.onKeyDown(keyCode, event);
    }

    //返回提示
    private void showBackNotice() {

        if (!StringUtil.isEmpty(ownId) || url_list.size() > 1 || et_cotent.getText().length() > 0) {

            ReminderHelper.getIntentce().showDialog(UploadPhotoActivity.this, "提示", "照片还没有上传，确认不发布吗？", "确定", new DialogButtonListener() {
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


    //获取用户最新创建的相册
    private void getUserLastCreateAlbum() {

        if (!(typeEnum == ActionTypeEnum.CLASSANDOWNER || typeEnum == ActionTypeEnum.OWNER)) return;

        Map<String, Object> map = new HashMap<>();
        map.put("ownerId", HiCache.getInstance().getLoginUserInfo().getUserId());

        HiStudentHttpUtils.getDataByOKHttp(UploadPhotoActivity.this, map, HistudentUrl.getUserLastCreateAlbum, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                JSONObject object = JSON.parseObject(result);
                fileTag.setVisibility(View.VISIBLE);
                fileName = object.getString("albumName");
                albumId = object.getString("albumId");
                defaultAlbumId = object.getString("albumId");
                ownId = HiCache.getInstance().getLoginUserInfo().getUserId();
                privacyStatus = object.getIntValue("privacyStatus");
                changTextColor();
                fileTag.setText("个人");
                tv_file_name.setText(fileName);
                ComViewUitls.setSyncViewVisiablityByNum(UploadPhotoActivity.this, privacyStatus);
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        }, LoadingType.PENCIL);
    }


    //获取图片压缩的路径
    public void getCompressImagePath() {

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
    }
}