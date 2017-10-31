package com.histudent.jwsoft.histudent.body.mine.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.HiStudentLog;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.hiworld.activity.CreateAlbumActivity;
import com.histudent.jwsoft.histudent.body.hiworld.activity.UploadPhotoActivity;
import com.histudent.jwsoft.histudent.body.hiworld.bean.SimpleUserModel;
import com.histudent.jwsoft.histudent.body.mine.adapter.AlbumAdapter;
import com.histudent.jwsoft.histudent.body.mine.model.AlbumAdapterModel;
import com.histudent.jwsoft.histudent.body.mine.model.AlbumInfoModel;
import com.histudent.jwsoft.histudent.body.mine.model.PictureInAlbumModel;
import com.histudent.jwsoft.histudent.body.myclass.activity.LikePersionListActivity;
import com.histudent.jwsoft.histudent.body.myclass.helper.ClassHelper;
import com.histudent.jwsoft.histudent.call.IPermissionCallBackListener;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.ParserActivity;
import com.histudent.jwsoft.histudent.commen.bean.AlbumAuthorityModel;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.enums.YdType;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.DialogButtonListener;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.photo.utils.ImageUtils;
import com.histudent.jwsoft.histudent.commen.photo_selecter.imageloader.SelectPhotoActivity;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.ComViewUitls;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.popupwindow.TopMenuPopupWindow;
import com.histudent.jwsoft.histudent.comment2.bean.AddressInforBean;
import com.histudent.jwsoft.histudent.comment2.utils.ActionTypeEnum;
import com.histudent.jwsoft.histudent.comment2.utils.FileUtils;
import com.histudent.jwsoft.histudent.comment2.utils.GetDeleteUserCallBack;
import com.histudent.jwsoft.histudent.comment2.utils.MovePicPopupWindow;
import com.histudent.jwsoft.histudent.info.persioninfo.activity.PersonCenterActivity;
import com.netease.nim.uikit.common.util.string.StringUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 相册中照片的详情界面
 */
public class AlbumDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView album_show_cover;
    public static AlbumInfoModel model;
    private PullToRefreshListView listView;
    private View top_view;
    private int index = 0;//页码数
    private PictureInAlbumModel albumModel;
    private AlbumAdapter mAlbumAdapter;
    private List<AlbumAdapterModel> models;//数据源
    private TopMenuPopupWindow menuWindow;
    private View.OnClickListener itemsOnClick;
    private RelativeLayout btn_upload;
    private List<String> mSelectedImage = new ArrayList<>();
    private List<String> list;
    private ImageView iv_back;
    private FrameLayout layout;
    private Bitmap photo;
    private String fileName;
    public static List<String> picture_ids;
    private LinearLayout linear_share;
    private ImageView iv_share, iv_delete;
    private TextView album_Num, album_praiseNum, album_instr, album_name;
    private TopMenuPopupWindow takePicPopupWindow;
    private View.OnClickListener itemsSetPicOnClick;
    private int result;
    private MovePicPopupWindow movePicPopupWindow;
    private boolean isChanged;
    private ActionTypeEnum typeEnum;
    private String id;
    private boolean flage;
    private MovePicPopupWindow.isSuccessCallBack isSuccessBack;
    private AlbumAuthorityModel authorityModel;
    private LinearLayout praseImage_layout;
    private SmartRefreshLayout mRefreshLayout;
    private ActionTypeEnum albumEnum;
    /**
     * 点赞区域最多可以放置的头像数量
     */
    private static final int AREA_SIZE = 5;

    private Handler hanlder = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                //删除相册
                case 0:
                    String content = (String) msg.obj;
                    JSONObject jsonObject = null;
                    if (msg.arg1 == HiWorldCache.SUCCESS) {
                        if (content.startsWith("{") && content.endsWith("}")) {
                            jsonObject = JSON.parseObject(content);
                            if (jsonObject != null && jsonObject.getIntValue("status") == 1) {

                                isChanged = true;
                                ReminderHelper.getIntentce().ToastShow_with_image(AlbumDetailActivity.this,
                                        "已删除", R.string.icon_check);
                                setResult();
                            }
                        }
                    }

                    break;


                //上传图片的压缩
                case 1:
                    List<String> list_tmp = new ArrayList<>();
                    if (msg.arg1 == 0) {
                        HiStudentLog.e("---->压缩保存失败...");
                    } else {

                        if (list != null && list.size() > 0) {
                            HiStudentLog.e("---->压缩保存完成...开始长传文件夹");
                            for (int i = 0; i < list.size(); i++) {
                                list_tmp.add(list.get(i));
                            }
                            list.clear();
                        }

                        if (list_tmp != null && list_tmp.size() > 0) {
                            for (int i = 0; i < list_tmp.size(); i++) {
                                list.add(ImageUtils.getCompressFilePath(list_tmp.get(i)));
                            }
                        }

                        list_tmp.clear();
                        uploadImage();
                    }
                    break;

                //批量操作
                case 2:

                    content = (String) msg.obj;
                    isChanged = true;

                    layout.setVisibility(View.VISIBLE);
                    btn_upload.setVisibility(View.VISIBLE);
                    mAlbumAdapter.setIsShowSelected(false);
                    mAlbumAdapter.notifyDataSetChanged();
                    HiStudentLog.e("MultiplyAction", content);
                    break;

                case 4:
                    content = (String) msg.obj;
                    if (msg.arg1 == HiWorldCache.SUCCESS) {
                        if (content.startsWith("{") && content.endsWith("}")) {
                            jsonObject = JSON.parseObject(content);
                            if (jsonObject != null) {

                                ReminderHelper.getIntentce().ToastShow_with_image(AlbumDetailActivity.this,
                                        "上传图片成功", R.string.icon_check);
                                ImageUtils.deleteCompressFile();//删除压缩文件
                                list.clear();
                                fileName = "";
                                photo = null;
                                isChanged = true;
                                refreshPictures();
                                getSingleAlbumInfor();

                            }
                        }
                    }

                    Log.e("UploadImage", content);
                    break;

                case 5:
                    content = ((String) msg.obj);
                    jsonObject = JSON.parseObject(content);
                    if (msg.arg1 == HiWorldCache.SUCCESS) {
                        if (jsonObject != null) {

                            isChanged = true;
                            mAlbumAdapter.setIsShowSelected(false);
                            linear_share.setVisibility(View.GONE);
                            layout.setVisibility(View.VISIBLE);
                            btn_upload.setVisibility(View.VISIBLE);
                            refreshPictures();
                            picture_ids.clear();
                            ReminderHelper.getIntentce().ToastShow_with_image(AlbumDetailActivity.this,
                                    "已删除", R.string.icon_check);
                            getSingleAlbumInfor();
                        }
                    }
                    Log.e("DeleteImages", content);
                    break;

                case 6:
                    showUserShadeWindow(AlbumDetailActivity.this, layout, YdType.EDIT_ALBUM, null);
                    break;

            }
        }
    };

    private void setResult() {

        if (isChanged) {
            setResult(200);
        } else {
            setResult(-1);
        }
        finish();
    }

    /**
     * @param activity
     * @param model
     * @param request
     * @param typeEnum
     */

    public static void start(Activity activity, AlbumInfoModel model, int request, ActionTypeEnum typeEnum) {

        Intent intent = new Intent(activity, AlbumDetailActivity.class);
        intent.putExtra("typeEnum", typeEnum);
        intent.putExtra("authorityModel", new AlbumAuthorityModel());
        AlbumDetailActivity.model = model;
        activity.startActivityForResult(intent, request);

    }

    /**
     * @param activity
     * @param model          相册详情的实体类
     * @param typeEnum       相册类型
     * @param authorityModel 相册权限类型的实体类
     */

    public static void start(Activity activity, AlbumInfoModel model, ActionTypeEnum typeEnum, AlbumAuthorityModel authorityModel) {

        AlbumDetailActivity.model = model;
        Intent intent = new Intent(activity, AlbumDetailActivity.class);
        intent.putExtra("authorityModel", authorityModel);
        intent.putExtra("typeEnum", typeEnum);
        activity.startActivityForResult(intent, 500);

    }

    @Override
    protected void onResume() {
        super.onResume();
        picture_ids.clear();
        if (flage) {
            getSingleAlbumInfor();
            flage = false;
            isChanged = true;
        }
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_album;
    }

    @Override
    public void initView() {
        authorityModel = ((AlbumAuthorityModel) getIntent().getSerializableExtra("authorityModel"));
        typeEnum = ((ActionTypeEnum) getIntent().getSerializableExtra("typeEnum"));
        picture_ids = new ArrayList<>();

        intId();

        models = new ArrayList<>();
        list = new ArrayList<>();
        layout = (FrameLayout) findViewById(R.id.title_right);
        findViewById(R.id.activity_topview_shadow).setBackground(getResources().getDrawable(R.drawable.transparent));
        listView = (PullToRefreshListView) findViewById(R.id.activity_album_list);

        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.srl_refresh_layout);
        top_view = LayoutInflater.from(this).inflate(R.layout.albun_head, null);
        album_show_cover =  top_view.findViewById(R.id.album_bg);
        album_Num =  top_view.findViewById(R.id.pic_num);//照片数量
        album_praiseNum = top_view.findViewById(R.id.favor_num);//点赞数量
        iv_back = ((ImageView) findViewById(R.id.title_left_image));
        iv_delete = ((ImageView) findViewById(R.id.iv_delete));
        iv_share = ((ImageView) findViewById(R.id.iv_share));
        linear_share = ((LinearLayout) findViewById(R.id.linear_share));

        btn_upload =  top_view.findViewById(R.id.album_show_upload);
        album_instr =  top_view.findViewById(R.id.album_instr);//相册介绍
        album_name =  top_view.findViewById(R.id.album_name);//相册名称
        praseImage_layout =  top_view.findViewById(R.id.layout);
        top_view.findViewById(R.id.linear_favor).setOnClickListener(this);

        if (!model.isIsEditAuth()) {//是否有编辑相册权限
            layout.setVisibility(View.GONE);
        }

        if (!model.isIsUploadAuth()) {//是否有上传图片的权限
            btn_upload.setVisibility(View.GONE);
        }

        itemsOnClick = new View.OnClickListener() {

            public void onClick(View v) {
                menuWindow.dismiss();
                switch (v.getId()) {

                    //批量操作
                    case R.id.btn_01:

                        mAlbumAdapter.setIsShowSelected(true);
                        mAlbumAdapter.notifyDataSetChanged();
                        linear_share.setVisibility(View.VISIBLE);
                        layout.setVisibility(View.GONE);
                        btn_upload.setVisibility(View.GONE);

                        break;


                    //编辑相册
                    case R.id.btn_02:
                        flage = true;
                        CreateAlbumActivity.startEditeAlbum(AlbumDetailActivity.this, model, typeEnum, id);

                        break;

                    //删除相册
                    case R.id.btn_03:

                        ReminderHelper.getIntentce().showDialog(AlbumDetailActivity.this, "提示", "是否确定删除该相册？", "确定", new DialogButtonListener() {
                            @Override
                            public void setOnDialogButtonListener() {
                                deleteImageFile();
                            }
                        }, "取消", new DialogButtonListener() {
                            @Override
                            public void setOnDialogButtonListener() {

                            }
                        });
                        break;

                    //取消
                    default:
                        break;
                }
            }

        };


        //上传图片选择
        itemsSetPicOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                takePicPopupWindow.dismiss();
                switch (view.getId()) {

                    //本地照片上传
                    case R.id.btn_01:
                        SelectPhotoActivity.start(AlbumDetailActivity.this, (ArrayList<String>) mSelectedImage, 9, null);
                        break;

                    //拍照
                    case R.id.btn_02:

                        checkTakePhotoPermission(new IPermissionCallBackListener() {
                            @Override
                            public void doAction() {
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent, 2);
                            }
                        });

                        break;

                    //取消
                    default:
                        break;

                }
            }

        };

        iv_delete.setOnClickListener(this);
        iv_share.setOnClickListener(this);
        btn_upload.setOnClickListener(this);

        hanlder.sendEmptyMessageDelayed(6, 100);
    }

    private void intId() {
        if (model != null) {
            id = typeEnum == ActionTypeEnum.CLASS ? model.getOwnerId() : typeEnum == ActionTypeEnum.GROUP ? model.getOwnerId() : HiCache.getInstance().getLoginUserInfo().getUserId();
        }
    }

    @Override
    public void doAction() {
        super.doAction();

        upateHeadViewUI();

        listView.setMode(PullToRefreshBase.Mode.DISABLED);

        listView.getRefreshableView().addHeaderView(top_view);

        mAlbumAdapter = new AlbumAdapter(this, models, top_view, typeEnum, id, authorityModel.isMasker() || authorityModel.isAdmin());

        listView.setAdapter(mAlbumAdapter);

        if (model != null)
            CommonGlideImageLoader.getInstance().displayNetImage(this, model.getConverPhotoUrl(),
                    album_show_cover, ContextCompat.getDrawable(this, R.drawable.no_photo_video));
        mRefreshLayout.setOnRefreshListener((RefreshLayout refreshLayout) -> {
            index = 0;
            models.clear();
            getPictures(false);
            listView.getRefreshableView().setSelection(0);
        });
        mRefreshLayout.setOnLoadmoreListener((RefreshLayout refreshLayout) -> getPictures(false));
        getPictures(true);
    }

    //绘制点赞的人
    private void addParserUsers() {

        GetDeleteUserCallBack callBack = new GetDeleteUserCallBack() {
            @Override
            public void getDeleteUserModel(SimpleUserModel user) {

                if (!StringUtil.isEmpty(user.getUserId())) {
                    PersonCenterActivity.start(AlbumDetailActivity.this, user.getUserId());
                    //进入个人空间
                } else {
                    ParserActivity.start(AlbumDetailActivity.this, model.getAlbumId(), 4);
                }
            }
        };

        praseImage_layout.removeAllViews();
        int size = model.getPraiseUser().size() > AREA_SIZE ? AREA_SIZE : model.getPraiseUser().size();

        for (int i = 0; i < size; i++) {
            AlbumInfoModel.PraiseUserBean bean = model.getPraiseUser().get(i);
            SimpleUserModel userModel = new SimpleUserModel();
            userModel.setName(bean.getRealname());
            userModel.setUserId(bean.getUserId());
            userModel.setAvatar(bean.getAvatar());
            userModel.setType(bean.getUserType());
            ComViewUitls.addUserView(this, praseImage_layout, userModel, callBack);
        }
        SimpleUserModel userModel = new SimpleUserModel();
        ComViewUitls.addUserView(this, praseImage_layout, userModel, callBack);

    }

    /**
     * 获取相册中的照片
     */
    private void getPictures(boolean isNeedLoading) {

        Map<String, Object> map = new TreeMap<>();
        map.put("albumId", model.getAlbumId());
        map.put("pageIndex", index + "");
        map.put("ownerId", model.getOwnerId());
        map.put("pagesize", 16 + "");

        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.getPhotoInAlbum_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadmore();
                albumModel = JSON.parseObject(result, PictureInAlbumModel.class);
                if (albumModel != null) {
                    if (albumModel.getItems().size() > 0) {
                        if (models.contains(null))
                            models.clear();
                        models.addAll(exchangeData(albumModel));
                        mAlbumAdapter.notifyDataSetChanged();
                        index++;
                    } else {
                        if (models.size() == 0) {
                            models.add(null);
                            mAlbumAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(AlbumDetailActivity.this, R.string.no_more_data, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(String msg) {
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadmore();

            }
        }, isNeedLoading ? LoadingType.FLOWER : LoadingType.NONE);

    }

    private List<AlbumAdapterModel> exchangeData(PictureInAlbumModel albumModel) {

        List<PictureInAlbumModel.ItemsBean> itemsBeans = albumModel.getItems();
        List<AlbumAdapterModel> models = new ArrayList<>();
        List<String> times = new ArrayList<>();

        for (int i = 0; i < itemsBeans.size(); i++) {


            if (!StringUtil.isEmpty(itemsBeans.get(i).getPhotoDate())) {
                String time = itemsBeans.get(i).getPhotoDate().split(" ")[0];
                if (!times.contains(time)) {
                    times.add(time);
                }
            }
        }

        for (int i = 0; i < times.size(); i++) {
            AlbumAdapterModel model = new AlbumAdapterModel();
            List<PictureInAlbumModel.ItemsBean.PhotoListBean> beens = new ArrayList<>();
            String time = times.get(i);
            for (int n = 0; n < itemsBeans.size(); n++) {
                PictureInAlbumModel.ItemsBean bean = itemsBeans.get(n);
                if (bean.getPhotoDate().split(" ")[0].equals(time)) {
                    beens.addAll(bean.getPhotoList());
                }
            }
            model.setTime(time);
            model.setBeans(beens);
            models.add(model);
        }

        return models;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.title_right:
                List<String> list_name = new ArrayList<>();
                List<Integer> list_color = new ArrayList<>();

                list_name.add("批量操作");
                list_color.add(Color.rgb(51, 51, 51));
                if (!(model.getIsDefault() || model.getIsSystem())) {
                    list_name.add("编辑相册");
                    list_name.add("删除相册");
                    list_color.add(Color.rgb(51, 51, 51));
                    list_color.add(Color.rgb(255, 88, 88));
                }

                menuWindow = new TopMenuPopupWindow(AlbumDetailActivity.this, itemsOnClick, list_name, list_color, false);
                menuWindow.showAtLocation(AlbumDetailActivity.this.findViewById(R.id.album_bg), Gravity.CENTER, 0, 0);
                break;

            //选择上传的图片
            case R.id.album_show_upload:

                //编辑社群封面时弹出选择图片来源的选择
//                list_name = new ArrayList<>();
//                list_name.add("本地图片");
//                list_name.add("拍照");
//
//                list_color = new ArrayList<>();
//                list_color.add(Color.rgb(51, 51, 51));
//                list_color.add(Color.rgb(51, 51, 51));
//                takePicPopupWindow = new TopMenuPopupWindow(AlbumDetailActivity.this, itemsSetPicOnClick, list_name, list_color, false);
//                takePicPopupWindow.showAtLocation(findViewById(R.id.title_left), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);




                UploadPhotoActivity.start(this, typeEnum,
                        model.getOwnerId(), true, 200);
                break;

            case R.id.title_left:
                if (mAlbumAdapter.getIsUpDate()) {
                    layout.setVisibility(View.VISIBLE);
                    btn_upload.setVisibility(View.VISIBLE);
                    mAlbumAdapter.setIsShowSelected(false);
                    mAlbumAdapter.notifyDataSetChanged();
                    linear_share.setVisibility(View.GONE);
                } else {
                    setResult();
                }

                break;

            //批量删除
            case R.id.iv_delete:

                Log.e("ids", picture_ids.toString());

                if (picture_ids.size() != 0) {

                    ReminderHelper.getIntentce().showDialog(AlbumDetailActivity.this, "提示", "是否确认删除？", "确定", new DialogButtonListener() {
                        @Override
                        public void setOnDialogButtonListener() {
                            deletePics();
                        }
                    }, "取消", new DialogButtonListener() {
                        @Override
                        public void setOnDialogButtonListener() {

                        }
                    });
                } else {
                    ReminderHelper.getIntentce().ToastShow_with_image(this,
                            "请选择操作对象", R.string.icon_remind);
                }

                break;

            //批量操作
            case R.id.iv_share:

                if (picture_ids != null && picture_ids.size() > 0) {

                    //转移图片操作的回调事件
                    isSuccessBack = new MovePicPopupWindow.isSuccessCallBack() {
                        @Override
                        public void isSuccess(boolean isSuccess) {
                            if (isSuccess) {
                                isChanged = true;
                                mAlbumAdapter.setIsShowSelected(false);
                                linear_share.setVisibility(View.GONE);
                                layout.setVisibility(View.VISIBLE);
                                btn_upload.setVisibility(View.VISIBLE);
                                refreshPictures();
                                getSingleAlbumInfor();
                                picture_ids.clear();
                            }
                        }
                    };

                    Map<String, Object> map = new HashMap<>();
                    int ownerType = typeEnum == ActionTypeEnum.CLASS ? 2 : typeEnum == ActionTypeEnum.GROUP ? 3 : 1;
                    map.put("ownerId", model.getOwnerId());
                    map.put("ownerType", ownerType);
                    movePicPopupWindow = new MovePicPopupWindow(AlbumDetailActivity.this, picture_ids, map, isSuccessBack, typeEnum == ActionTypeEnum.OWNER ? 0 : 1);
                    movePicPopupWindow.showAtLocation(findViewById(R.id.title_left), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                } else {

                    ReminderHelper.getIntentce().ToastShow_with_image(this,
                            "请选择操作对象", R.string.icon_remind);
                }

                break;

            case R.id.linear_favor:
                if (model != null && model.getPraiseUser() != null && model.getPraiseUser().size() > 0) {
                    LikePersionListActivity.start(this, model.getAlbumId(), true);
                }
                break;
        }
    }


    //批量删除照片
    private void deletePics() {
        if (picture_ids.size() != 0) {
            List<String> list_tep = new ArrayList<>();
            for (int i = 0; i < picture_ids.size(); i++) {
                list_tep.add("\"" + picture_ids.get(i) + "\"");
            }
            int ownerType = typeEnum == ActionTypeEnum.CLASS ? 2 : typeEnum == ActionTypeEnum.GROUP ? 3 : 1;
            Map<String, Object> map = new HashMap<>();
            map.put("photoIds", list_tep.toString());
            map.put("ownerType", ownerType + "");
            HiWorldCache.postHttpData(this, hanlder, 5, HistudentUrl.album_delete_images, map, HiWorldCache.Body, false, true);
        } else {

            ReminderHelper.getIntentce().ToastShow_with_image(this,
                    "请选择删除的图片", R.string.icon_remind);
        }
    }

    //删除相册
    private void deleteImageFile() {

        if (model.getIsDefault() || model.getIsSystem()) {

            ReminderHelper.getIntentce().ToastShow_with_image(this,
                    model.getIsDefault() == true ? "默认相册不能删除" : "系统相册不能删除", R.string.icon_remind);

        } else {


            Map<String, Object> map = new HashMap<>();
            map.put("albumId", model.getAlbumId());

            HiWorldCache.postHttpData(this, hanlder, 0, HistudentUrl.mine_delete_imageFile_url, map, HiWorldCache.Quarry, false, true);
        }

    }


    //上传图片
    private void uploadImage() {

        showLoadingImage(this, LoadingType.FLOWER);
        Map<String, Object> map1 = new HashMap<String, Object>();
        AddressInforBean addressInforBean = HiWorldCache.getUserLocationInfor();

        if (addressInforBean == null) {
            addressInforBean = new AddressInforBean();
        }

        map1.put("latitude", addressInforBean.getLatitude() + "");
        map1.put("longitude", addressInforBean.getLongitude() + "");
        map1.put("location", addressInforBean.getCity() + addressInforBean.getAreaStr());
        map1.put("albumId", model.getAlbumId());
        map1.put("ownerId", model.getOwnerId());
        map1.put("description", "");
        map1.put("atUser", "");
        map1.put("classIds", "");
        map1.put("associatedUsers", "");

        for (int i = 0; i < list.size(); i++) {

            map1.put("file" + i, list.get(i));
            Log.e("filePath", list.get(i));
        }

        HiWorldCache.postHttpData(this, hanlder, 4, HistudentUrl.hiworld_upload_image_url, map1, HiWorldCache.Quarry, false, true);
    }

    //接受选择的图片
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1001 && resultCode == 200) {
            isChanged = true;
            list.clear();
            list.addAll(0, data.getStringArrayListExtra("return"));

            compressImage(list);
        } else if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            // 如果是调用相机拍照时
            if (data != null) {
                isChanged = true;
                Bundle bundle = data.getExtras();
                Bitmap photo = bundle.getParcelable("data");
                fileName = System.currentTimeMillis() + ".jpg";
                FileUtils.saveImageBitmapIntoLocal(fileName, photo, false);
                fileName = FileUtils.getFilePath(fileName);
                Log.e("fileName", FileUtils.getFilePath(fileName));
                list.add(fileName);
                compressImage(list);
            }
        } else if (requestCode == 100 && resultCode == 200) {

            getSingleAlbumInfor();
            refreshPictures();
            isChanged = true;
        } else if (requestCode == 100 && resultCode == 300) {
            if (data == null) return;
            //设置封面返回
            MyImageLoader.getIntent().displayNetImage(AlbumDetailActivity.this, data.getStringExtra("cover_url"), album_show_cover);
            isChanged = true;
        }
    }


    //对图片进行压缩
    private void compressImage(final List<String> path) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < path.size(); i++) {

                    ImageUtils.saveBitmap(AlbumDetailActivity.this, path.get(i), 60, hanlder, path.size());
                }
            }
        }).start();

    }


    //获取相册详情
    private void getSingleAlbumInfor() {

        ClassHelper.getSingleAlbumDetail(this, model.getAlbumId(), typeEnum, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                model = JSON.parseObject(result, AlbumInfoModel.class);

                if (model != null) {

                    upateHeadViewUI();
                }
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });

    }

    //初始化相册头部
    private void upateHeadViewUI() {

        if (model != null) {
            album_Num.setText(model.getPhotoCount() + " 张");
            if (model.getPraiseUser() != null)
                album_praiseNum.setText(model.getPraiseUser().size() + " 人点赞");
            album_instr.setText(model.getAlbumDescription());
            album_name.setText(model.getAlbumName());

            //点赞用户显示的控制
            if (model.getPraiseUser() != null && model.getPraiseUser().size() > 0) {
                addParserUsers();
            } else {
                top_view.findViewById(R.id.linear_favor).setVisibility(View.GONE);
            }

        }

    }

    /**
     * 刷新图片信息
     */
    private void refreshPictures() {
        index = 0;
        models.clear();
        getPictures(true);
        listView.getRefreshableView().setSelection(0);
    }
}
