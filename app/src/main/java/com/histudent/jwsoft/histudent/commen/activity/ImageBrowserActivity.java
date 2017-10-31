package com.histudent.jwsoft.histudent.commen.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.adapter.PreviewPhotoAdapter;
import com.histudent.jwsoft.histudent.body.find.bean.GroupBean;
import com.histudent.jwsoft.histudent.body.hiworld.bean.RelationPersonModel;
import com.histudent.jwsoft.histudent.body.hiworld.bean.SimpleUserModel;
import com.histudent.jwsoft.histudent.body.hiworld.helper.ImageActivityHelper;
import com.histudent.jwsoft.histudent.body.hiworld.widget.RoundProgressBar;
import com.histudent.jwsoft.histudent.body.message.model.ClassModel;
import com.histudent.jwsoft.histudent.body.mine.model.CurrentUserDetailInfoModel;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.bean.PhotoCommentBean;
import com.histudent.jwsoft.histudent.commen.bean.PhotoHeadBean;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.enums.ShowImageType;
import com.histudent.jwsoft.histudent.commen.enums.YdType;
import com.histudent.jwsoft.histudent.commen.helper.PictureTailorHelper;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.CheckPermission;
import com.histudent.jwsoft.histudent.commen.utils.ComViewUitls;
import com.histudent.jwsoft.histudent.commen.utils.DataUtils;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.commen.view.swipemenulistview.ScalableImageView;
import com.histudent.jwsoft.histudent.comment2.utils.GetDeleteUserCallBack;
import com.histudent.jwsoft.histudent.comment2.utils.SeletClassMateEnum;
import com.histudent.jwsoft.histudent.info.persioninfo.PersionHelper;
import com.histudent.jwsoft.histudent.widget.scrollview.PhotoViewViewPager;
import com.sunfusheng.glideimageview.progress.CircleProgressView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;

/**
 * desc:
 * 大图片浏览page
 */
public class ImageBrowserActivity extends BaseActivity {


    private PhotoViewViewPager mViewPager;
    /**
     * 当前点击图片的位置
     */
    private int mCurrentPosition;
    private ShowImageType type;
    private PreviewPhotoAdapter mImageViewPagerAdapter;
    private RelativeLayout linearLayout_comment;
    private IconView rb_comment_image, cb_favor_image;
    private TextView rb_comment_text, cb_favor_text;
    private List<ActionListBean.ImagesBean> beanList;
    private ActionListBean.ImagesBean bean;
    private PhotoCommentBean mPhotoCommentBean;
    private TextView mTitle;
    private IconView imageMenue;
    private ScalableImageView mScalableImageView;//修改头像时用到
    private int currentImageIndex;
    private Intent intent;
    private CheckPermission checkPermission;
    private int type_from;//0:个人 ; 1:班级 ; 2:社群
    private String id;
    private ImageActivityHelper helper;
    private PictureTailorHelper clippHelper;
    private List<Object> stuList;//关联学生的信息集合
    private ArrayList<Object> relationPersonModelList;//图片关联和学生的关系信息集合
    private LinearLayout linear_user;
    private GetDeleteUserCallBack mDeleteUserCallBack;
    private TextView tv_sure;
    private ActionListBean actionListBean;
    private TextView tv_done;

    /****************引用支持加载进度的GlideImageView************************/
    private CircleProgressView mCircleProgressView;
    /**
     * 存放图片预览图数据
     */
    private List<String> mGlideImageUrlList;

    //获取照片需要的权限
    private final int PERMISSION_TAKTPHOTO_CODE = 100;
    static final String[] PERMISSION_TAKTPHOTO = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };


    @BindView(R.id.rb_comment_relation)
    LinearLayout relative_relation;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

                //保存图片到本地结果
                case 0:
                    if ((msg.obj.toString()).equals("success")) {

                        ReminderHelper.getIntentce().ToastShow_with_image(ImageBrowserActivity.this,
                                "保存图片成功", R.string.icon_check);
                    } else {
                        ReminderHelper.getIntentce().ToastShow_with_image(ImageBrowserActivity.this,
                                "保存图片失败，请稍后再试", R.string.icon_remind);
                    }
                    break;
                case 1:
                    showUserShadeWindow(ImageBrowserActivity.this, mTitle, YdType.ALBUM_YD, null);
                    break;
                case 2:
                    mGlideImageUrlList.remove(currentImageIndex);
                    beanList.remove(currentImageIndex);
                    mImageViewPagerAdapter.notifyDataSetChanged();

                    initTitleAndBottom();
                    if (mGlideImageUrlList.size() == 0) {
                        getReturnData();
                    } else {
                        bean = beanList.get(currentImageIndex);
                        if (helper != null)
                            helper.setImageBean(bean);
                        mTitle.setText(currentImageIndex + 1 + "/" + mGlideImageUrlList.size());
                        getPhotoInfo();
                    }
                    break;
                case 3:

                    mGlideImageUrlList.remove(currentImageIndex);
                    beanList.remove(currentImageIndex);
                    if (mGlideImageUrlList.size() == 0) {
                        getReturnData();
                    } else {
                        mImageViewPagerAdapter.notifyDataSetChanged();
                        initTitleAndBottom();
                        getReturnData();
                        bean = beanList.get(currentImageIndex);
                        if (helper != null)
                            helper.setImageBean(bean);
                    }
                    break;
                case 4:
                    DataUtils.deleteRelationByPicName(getCurrentPicName(), relationPersonModelList);
                    mGlideImageUrlList.remove(currentImageIndex);
                    beanList.remove(currentImageIndex);
                    mImageViewPagerAdapter.notifyDataSetChanged();
                    initTitleAndBottom();
                    if (mGlideImageUrlList.size() == 0)
                        getReturnData();

                    break;
            }
        }
    };



    @Override
    public int setViewLayout() {
        return R.layout.activity_browse_image;
    }

    /**
     * @param activity
     * @param position
     * @param request
     * @param urls2
     * @param type
     * @param type_from 0:个人 1：班级  2:社群
     * @param id
     */

    public static void start(Activity activity, int position, int request, ArrayList<ActionListBean.ImagesBean> urls2, ShowImageType type, int type_from, String id) {
        Intent intent = new Intent(activity, ImageBrowserActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("position", position);
        intent.putExtra("type_from", type_from);
        intent.putExtra("type", type);
        intent.putExtra("urls2", urls2);
        activity.startActivityForResult(intent, request);
    }

    /**
     * @param activity
     * @param position
     * @param request
     * @param type
     */
    public static void start(Activity activity, int position, int request, ActionListBean bean, ShowImageType type) {
        Intent intent = new Intent(activity, ImageBrowserActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("type", type);
        intent.putExtra("bean", bean);
        activity.startActivityForResult(intent, request);
    }


    public static void start(Activity activity, int position, int request, ArrayList<ActionListBean.ImagesBean> urls2,
                             ShowImageType type, ArrayList<RelationPersonModel> relations) {
        Intent intent = new Intent(activity, ImageBrowserActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("type", type);
        intent.putExtra("urls2", urls2);
        intent.putExtra("relations", relations);//存放图片关联人信息集合
        activity.startActivityForResult(intent, request);
    }

    @Override
    public void initView() {
        mTitle = (TextView) findViewById(R.id.title_middle_text);
        mViewPager = (PhotoViewViewPager) findViewById(R.id.view_pager_pic);
        linearLayout_comment = (RelativeLayout) findViewById(R.id.linearLayout_comment);
        rb_comment_image = (IconView) findViewById(R.id.rb_comment_image);
        cb_favor_image = (IconView) findViewById(R.id.cb_favor_image);
        rb_comment_text = (TextView) findViewById(R.id.rb_comment_text);
        cb_favor_text = (TextView) findViewById(R.id.cb_favor_text);
        imageMenue = (IconView) findViewById(R.id.title_right_image);
        tv_done = ((TextView) findViewById(R.id.tv_sure));
        mCircleProgressView = (CircleProgressView) findViewById(R.id.iv_progress_loading);
    }


    @Override
    public void doAction() {
        super.doAction();
        clippHelper = PictureTailorHelper.getInstance(false);
        checkPermission = new CheckPermission(this);
        intent = getIntent();
        id = intent.getStringExtra("id");
        type_from = intent.getIntExtra("type_from", 0);
        mCurrentPosition = intent.getIntExtra("position", 0);
        type = (ShowImageType) intent.getSerializableExtra("type");
        actionListBean = ((ActionListBean) getIntent().getSerializableExtra("bean"));

        if (actionListBean == null) {
            beanList = (ArrayList<ActionListBean.ImagesBean>) intent.getSerializableExtra("urls2");
        } else {
            beanList = actionListBean.getImages();
        }
        currentImageIndex = mCurrentPosition;
        bean = beanList.get(mCurrentPosition);
        helper = new ImageActivityHelper(this, handler, bean, clippHelper, type_from, id);


        switch (type) {
            case EXCHANGE:
                imageMenue.setVisibility(View.VISIBLE);
                imageMenue.setText(R.string.icon_more);
                mTitle.setText(R.string.set_image_icon);
                break;

            case COMMENT:
                getPhotoInfo();
                imageMenue.setTextColor(getResources().getColor(R.color.green_color));
                imageMenue.setText(R.string.icon_download);
                linearLayout_comment.setVisibility(View.VISIBLE);
                imageMenue.setTextColor(getResources().getColor(R.color.green_color));
                break;

            case RELATION://可以添加图片关联人功能
                findViewById(R.id.tv_instr1).setVisibility(View.VISIBLE);
                initRelationData();
            case DElET:
                imageMenue.setVisibility(View.VISIBLE);
                imageMenue.setText(R.string.icon_del2);
                break;

            case MOVE:
            case SHOW:
                imageMenue.setText(R.string.icon_more);
                break;

            case EDIT:
                linearLayout_comment.setVisibility(View.VISIBLE);
                getPhotoInfo();
                imageMenue.setVisibility(View.VISIBLE);
                handler.sendEmptyMessageDelayed(1, 1000);
                imageMenue.setText(R.string.icon_more);
                break;

            case SAVE:
                imageMenue.setTextColor(getResources().getColor(R.color.green_color));
                imageMenue.setText(R.string.icon_download);
                break;

            case SCANF:
                imageMenue.setVisibility(View.VISIBLE);
                imageMenue.setText(R.string.icon_del);
                break;
        }

//        mListImageData = new ArrayList<>();
        mGlideImageUrlList = new ArrayList<>();
        ScalableImageView scalableImageView;
        for (int i = 0; i < beanList.size(); i++) {
            mScalableImageView = new ScalableImageView(this);
            mGlideImageUrlList.add(beanList.get(i).getBigSizeUrl());

        }
        initTitleAndBottom();
        mImageViewPagerAdapter = new PreviewPhotoAdapter(this, mGlideImageUrlList);
        mViewPager.setAdapter(mImageViewPagerAdapter);
        mViewPager.setCurrentItem(mCurrentPosition);
        mViewPager.setOffscreenPageLimit(1);
        loadListener();
    }

    private void loadListener() {

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentImageIndex = position;
                bean = beanList.get(position);
                getRelationUsers();
                if (helper != null) {
                    helper.setImageBean(bean);
                }
                if (type != ShowImageType.EXCHANGE)
                    mTitle.setText(currentImageIndex + 1 + "/" + mGlideImageUrlList.size());

                if (type == ShowImageType.COMMENT || type == ShowImageType.EDIT)
                    getPhotoInfo();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    //初始化关联人数据
    private void initRelationData() {
        linear_user = ((LinearLayout) findViewById(R.id.linear_person));
        tv_sure = ((TextView) findViewById(R.id.tv_sure));
        relative_relation.setVisibility(View.VISIBLE);
        stuList = new ArrayList<>();
        relationPersonModelList = new ArrayList<>();
        if (intent.getSerializableExtra("relations") != null)
            relationPersonModelList.addAll((ArrayList<RelationPersonModel>) intent.getSerializableExtra("relations"));
        mDeleteUserCallBack = (SimpleUserModel user) -> {
            DataUtils.deleteRelationUser(getCurrentPicName(), relationPersonModelList, user);
            stuList.remove(user);
            tv_done.setText("完成(" + stuList.size() + ")");
            Log.e("getDeleteImageUrl", user.getName());
        };
        getRelationUsers();
    }


    /**
     * 使用Glide加载大图时
     * 控件图片加载进度
     *
     * @param percent
     * @param isDone
     */
    public void setCircleProgressView(int percent, boolean isDone) {
        mCircleProgressView.setProgress(percent);
        mCircleProgressView.setVisibility(isDone ? View.GONE : View.VISIBLE);
    }

    //获取图片关联的用户集合
    private void getRelationUsers() {
        if (type == ShowImageType.RELATION) {
            if (stuList != null) {
                stuList.clear();
                stuList.addAll(DataUtils.getRelationUsers(getCurrentPicName(), relationPersonModelList));
                tv_done.setText("完成(" + stuList.size() + ")");

                if (stuList != null)
                    linear_user.removeAllViews();
                for (Object m : stuList) {
                    SimpleUserModel model = ((SimpleUserModel) m);
                    ComViewUitls.addView(this, linear_user, model, mDeleteUserCallBack);
                }
            }
        }
    }

    //获取图片关联的用户集合
    private void getRelationUsers(List<Object> list) {
        if (type == ShowImageType.RELATION) {
            if (list != null) {
                linear_user.removeAllViews();
                for (Object m : list) {
                    SimpleUserModel model = ((SimpleUserModel) m);
                    ComViewUitls.addView(this, linear_user, model, mDeleteUserCallBack);
                }
            }
        }
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.title_left:

                if (type == ShowImageType.SCANF) {
                    getReturnData();
                } else {
                    finish();
                }

                break;
            case R.id.title_right:
                switch (type) {
                    case DElET:
                    case RELATION:
                    case SCANF:
                        helper.doDeleteImageAction();
                        break;
                    case EXCHANGE:
                        if (checkPermission.permissionSet(PERMISSION_TAKTPHOTO)) {
                            checkPermission.requestBasicPermission(PERMISSION_TAKTPHOTO, PERMISSION_TAKTPHOTO_CODE);
                        } else {
                            helper.showBottomMenue(type);
                        }
                        break;

                    case MOVE:
                    case SHOW:
                    case EDIT:
                        helper.showBottomMenue(type);
                        break;
                    case SAVE:
                    case COMMENT:
                        helper.saveImageAction();
                        break;
                }
                break;

            case R.id.cb_favor_layout://点赞
                likePhoto();
                break;
            case R.id.rb_comment_layout://评论
                //造个数据源使用万能适配器
                gotoCommentActivity();

                break;

            //添加关联人的确按钮
            case R.id.tv_sure:

                getReturnData();
                break;

            //添加关联

            case R.id.iv_add:
                SelecteClassmatesActiviy.start(this, DataUtils.getRelationUsers(getCurrentPicName(),
                        relationPersonModelList), 1000, SeletClassMateEnum.REALTION);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {

        switch (requestCode) {

            case PictureTailorHelper.PHOTO_REQUEST_TAKEPHOTO:
                //相册拍照成功返回
                if (resultCode == Activity.RESULT_OK)
                    clippHelper.startPhotoZoom(this, Uri.fromFile(clippHelper.photo_path), 150);
                break;

            case PictureTailorHelper.PHOTO_REQUEST_GALLERY:
                if (data != null)
                    clippHelper.startPhotoZoom(this, data.getData(), 150);
                break;

            case PictureTailorHelper.PHOTO_REQUEST_CUT:
                if (resultCode == 0) return;
                final String path = clippHelper.setPicToView(mScalableImageView, data);
                List<String> urlList = new ArrayList<>();
                for (int i = 0; i < mGlideImageUrlList.size(); i++) {
                    if (i == 0) {
                        urlList.add(path);
                    } else {
                        urlList.add(mGlideImageUrlList.get(i));
                    }
                }
                if (mImageViewPagerAdapter != null) {
                    mImageViewPagerAdapter.setPreviewPhotoUrlList(urlList);
                    mViewPager.getAdapter().notifyDataSetChanged();
                }

                checkTakePhotoPermission(() ->
                        PersionHelper.getInstance().setPicToView(ImageBrowserActivity.this, path, new HttpRequestCallBack() {
                            @Override
                            public void onSuccess(String result) {
                                try {
                                    String path = new JSONObject(result).optString("imgUrl");
                                    HiCache.getInstance().saveLoginUserAvatar(path);
                                    HiCache.getInstance().updataAccountDataInDB(path);
                                    setResult(200);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(String errorMsg) {

                            }
                        })
                );
                break;
            case 666:
                getPhotoInfo();
                break;


            //获取关联信息返回
            case 1000:

                if (resultCode == 200) {
                    if (data != null && data.getSerializableExtra("userModel") != null) {

                        //接收关联用户信息集合，刷新界面
                        stuList.clear();
                        stuList.addAll(((ArrayList) data.getSerializableExtra("userModel")));
                        getRelationUsers(stuList);
                        tv_done.setText("完成(" + stuList.size() + ")");
//                        changTextColor();

                        //设置图片与关联用户之间对应关系
                        DataUtils.setRelationUsers(getCurrentPicName(), relationPersonModelList, ((ArrayList<SimpleUserModel>) data.getSerializableExtra("userModel")));
                        Log.e("relationPersonModelList", DataUtils.getRelationString1(relationPersonModelList));

                    }
                }
                break;
        }

        if (requestCode == CommentActivity.REQUESTCODE && resultCode == CommentActivity.RESULTCODE) {
            getPhotoInfo();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_TAKTPHOTO_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    helper.showBottomMenue(type);
                } else {
                    checkPermission.showMissingPermissionDialog();
                }
                break;

        }
    }


    private void initTitleAndBottom() {
        mTitle.setText(currentImageIndex + 1 + "/" + beanList.size());
        getRelationUsers();

        if (type == ShowImageType.SCANF) {
            tv_done.setText("完成(" + beanList.size() + ")");
        }
    }

    /**
     * 照片点赞
     */
    private void likePhoto() {
        if (mPhotoCommentBean == null) return;
        final boolean flag_ = mPhotoCommentBean.isIsPraise();
        Map<String, Object> map = new TreeMap<>();
        map.put("doOrUndo", !flag_);
        map.put("relatedMainId", bean.getImgId());
        map.put("objectType", String.valueOf(5));
        map.put("objectId", bean.getImgId());

        HiStudentHttpUtils.postDataByOKHttp(ImageBrowserActivity.this, map, HistudentUrl.praise_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                getPhotoInfo();
            }

            @Override
            public void onFailure(String msg) {
            }
        },LoadingType.NONE);
    }

    /**
     * 照片信息
     */
    private void getPhotoInfo() {
        if (bean == null || TextUtils.isEmpty(bean.getImgId())) return;
        Map<String, Object> map = new TreeMap<>();
        map.put("objectType", 5);
        map.put("objectId", bean.getImgId());
        HiStudentHttpUtils.postDataByOKHttp(ImageBrowserActivity.this, map, HistudentUrl.getPhotoInfo, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                mPhotoCommentBean = JSON.parseObject(result, PhotoCommentBean.class);
                cb_favor_text.setText(String.valueOf(mPhotoCommentBean.getPraiseCount()));
                rb_comment_text.setText(String.valueOf(mPhotoCommentBean.getCommentCount()));
                setPraiseBg(cb_favor_text, cb_favor_image, mPhotoCommentBean.isIsPraise());
                setCommentBg(rb_comment_text, rb_comment_image, mPhotoCommentBean.getCommentCount());
            }

            @Override
            public void onFailure(String msg) {

            }
        }, LoadingType.NONE);
    }

    /**
     * 设置点赞背景
     *
     * @param view
     * @param flag
     */

    private void setPraiseBg(TextView textView, IconView view, boolean flag) {
        view.setText(flag ? R.string.icon_zan : R.string.icon_zannone);
        view.setTextColor(flag ? getResources().getColor(R.color.green_color) : getResources().getColor(R.color.text_black_h2));
        textView.setTextColor(flag ? getResources().getColor(R.color.green_color) : getResources().getColor(R.color.text_black_h2));
    }

    /**
     * 设置评论背景
     *
     * @param view
     * @param num
     */
    private void setCommentBg(TextView textView, IconView view, int num) {
        view.setText(num > 0 ? R.string.icon_comment : R.string.icon_topic4);
        view.setTextColor(num > 0 ? getResources().getColor(R.color.green_color) : getResources().getColor(R.color.text_black_h2));
        textView.setTextColor(num > 0 ? getResources().getColor(R.color.green_color) : getResources().getColor(R.color.text_black_h2));
    }

    /**
     * 设置返回参数
     */
    private void getReturnData() {
        ArrayList<String> path = new ArrayList<>();
        for (int i = 0; i < beanList.size(); i++) {
            path.add(beanList.get(i).getBigSizeUrl());
        }
        Intent intent = new Intent();

        if (type == ShowImageType.RELATION) {//如何可以添加关联，则返回关联信息集合
            intent.putExtra("relations", relationPersonModelList);
        }
        intent.putStringArrayListExtra("return", path);
        setResult(200, intent);
        this.finish();

    }

    private String getCurrentPicName() {

        if (type == ShowImageType.RELATION && beanList != null && beanList.size() > 0) {
            return beanList.get(currentImageIndex).getName();
        }
        return "";
    }

    private void gotoCommentActivity() {

        PhotoHeadBean bean = new PhotoHeadBean();
        bean.setPath(beanList.get(currentImageIndex).getBigSizeUrl());

        if (actionListBean != null) {
            bean.setUserAvatar(actionListBean.getUserAvatar());
            bean.setUserId(actionListBean.getUserId());
            bean.setUserName(actionListBean.getUserName());
            bean.setTime(actionListBean.getLastUpdateTime());
        } else {

            if (type_from == 0) {
                CurrentUserDetailInfoModel userModel = HiCache.getInstance().getUserDetailInfo();
                if (userModel != null) {
                    bean.setUserAvatar(userModel.getAvatar());
                    bean.setUserId(userModel.getUserId());
                    bean.setUserName(userModel.getRealName());
                }
            } else if (type_from == 1) {
                ClassModel classModel = HiCache.getInstance().getClassDetailInfo();
                if (classModel != null) {
                    bean.setUserAvatar(classModel.getClassLogo());
                    bean.setUserId(classModel.getClassId());
                    bean.setUserName(classModel.getSchoolName() + classModel.getGradeName() + classModel.getCName());
                }
            } else if (type_from == 2) {
                GroupBean groupBean = HiCache.getInstance().getGroupDetailInfo();
                if (groupBean != null) {
                    bean.setUserAvatar(groupBean.getGroupLogo());
                    bean.setUserId(groupBean.getGroupId());
                    bean.setUserName(groupBean.getGroupName());
                }
            }
        }

        CommentActivity.start(ImageBrowserActivity.this, beanList.get(currentImageIndex).getImgId(), bean);

    }


    private static class ProgressHandler extends Handler {

        private final WeakReference<Activity> mActivity;
        private final RoundProgressBar roundProgressBar;

        public ProgressHandler(Activity activity, RoundProgressBar roundProgressBar) {
            super(Looper.getMainLooper());
            mActivity = new WeakReference<>(activity);
            this.roundProgressBar = roundProgressBar;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final Activity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case 1:
                        int percent = msg.arg1 * 100 / msg.arg2;
                        roundProgressBar.setProgress(percent);
                        break;
                    default:
                        break;
                }
            }
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (type == ShowImageType.SCANF || type == ShowImageType.RELATION) {
                getReturnData();
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Glide.get(this).clearMemory();
    }
}
