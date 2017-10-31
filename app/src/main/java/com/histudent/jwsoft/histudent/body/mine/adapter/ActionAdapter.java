package com.histudent.jwsoft.histudent.body.mine.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSONObject;
import com.histudent.jwsoft.histudent.HiStudentLog;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.account.login.activity.DealActivity;
import com.histudent.jwsoft.histudent.activity.image.ShowImageActivity;
import com.histudent.jwsoft.histudent.body.find.activity.GroupCenterActivity;
import com.histudent.jwsoft.histudent.body.hiworld.activity.WatchActionVideoActivity;
import com.histudent.jwsoft.histudent.body.homepage.activity.TopicActivity;
import com.histudent.jwsoft.histudent.body.homepage.helper.HomePageHelper;
import com.histudent.jwsoft.histudent.body.message.model.ClassModel;
import com.histudent.jwsoft.histudent.body.mine.viewholder.ActionAlbumViewHolder;
import com.histudent.jwsoft.histudent.body.mine.viewholder.ActionEssayViewHolder;
import com.histudent.jwsoft.histudent.body.mine.viewholder.ActionGroupViewHolder;
import com.histudent.jwsoft.histudent.body.mine.viewholder.ActionHuoDongViewHolder;
import com.histudent.jwsoft.histudent.body.mine.viewholder.ActionLevelViewHolder;
import com.histudent.jwsoft.histudent.body.mine.viewholder.ActionLogViewHolder;
import com.histudent.jwsoft.histudent.body.mine.viewholder.ActionPictureViewHolder;
import com.histudent.jwsoft.histudent.body.mine.viewholder.ActionViewHolder;
import com.histudent.jwsoft.histudent.body.mine.viewholder.DynamicTypeViewHolder;
import com.histudent.jwsoft.histudent.body.myclass.activity.ClassCircleActivity;
import com.histudent.jwsoft.histudent.body.myclass.activity.HuoDongCenterActivity;
import com.histudent.jwsoft.histudent.body.myclass.activity.LikePersionListActivity;
import com.histudent.jwsoft.histudent.body.myclass.helper.ClassHelper;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.CommentActivity;
import com.histudent.jwsoft.histudent.commen.activity.ImageBrowserActivity;
import com.histudent.jwsoft.histudent.commen.activity.MyWebActivity;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.bean.AtUserModel;
import com.histudent.jwsoft.histudent.commen.bean.MyShareBean;
import com.histudent.jwsoft.histudent.commen.bean.TopicModel;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.enums.LoactionType;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.enums.ReportType;
import com.histudent.jwsoft.histudent.commen.enums.ShowImageType;
import com.histudent.jwsoft.histudent.commen.keyword.utils.DisplayUtils;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.RichTextView;
import com.histudent.jwsoft.histudent.commen.view.popupwindow.SharePopupWindow;
import com.histudent.jwsoft.histudent.commen.view.popupwindow.TopMenuPopupWindow;
import com.histudent.jwsoft.histudent.comment2.activity.MapActivity;
import com.histudent.jwsoft.histudent.comment2.activity.ReportActivity;
import com.histudent.jwsoft.histudent.comment2.bean.AddressInforBean;
import com.histudent.jwsoft.histudent.comment2.utils.EmotionUtils;
import com.histudent.jwsoft.histudent.entity.CommentUpdateEvent;
import com.histudent.jwsoft.histudent.entity.ImageAttrEntity;
import com.histudent.jwsoft.histudent.info.persioninfo.activity.PersonCenterActivity;
import com.histudent.jwsoft.histudent.manage.PhotoManager;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.histudent.jwsoft.histudent.constant.ClassifyKeys.ADD_GROUP_MEMBER;
import static com.histudent.jwsoft.histudent.constant.ClassifyKeys.CREATE_BLOG;
import static com.histudent.jwsoft.histudent.constant.ClassifyKeys.CREATE_CLASS_ACTIVITY;
import static com.histudent.jwsoft.histudent.constant.ClassifyKeys.CREATE_GROUP;
import static com.histudent.jwsoft.histudent.constant.ClassifyKeys.CREATE_GROUP_ACTIVITY;
import static com.histudent.jwsoft.histudent.constant.ClassifyKeys.CREATE_LEVEL_UP;
import static com.histudent.jwsoft.histudent.constant.ClassifyKeys.CREATE_MICRO_BLOG;
import static com.histudent.jwsoft.histudent.constant.ClassifyKeys.JOIN_CLASS_ACTIVITY;
import static com.histudent.jwsoft.histudent.constant.ClassifyKeys.JOIN_GROUP_ACTIVITY;
import static com.histudent.jwsoft.histudent.constant.ClassifyKeys.PICTURE;
import static com.histudent.jwsoft.histudent.constant.ClassifyKeys.UPLOAD_PHOTO;

/**
 * Created by liuguiyu-pc on 2017/4/20.
 * 所有动态适配器
 */

public class ActionAdapter extends BaseAdapter {
    private BaseActivity activity;
    private List<ActionListBean> mActionListData;
    private boolean flag_onclick;
    private boolean isShield;
    private boolean isAdmin;
    public static final int PHOTO = 1;
    public static final int LOG = 2;
    public static final int ESSAY = 3;
    public static final int CLASS_CIRCLE = 4;
    public static final int PERSION = 5;
    public static final int COMMENT = 6;
    public static final int TOPIC = 7; //话题
    public static final int GROUP = 8;
    public static final int INVITATION = 9;
    public static final int SHCOOL = 10;
    private int showType;
    private TopMenuPopupWindow menuWindow;
    private boolean isComment;
    private boolean isClickTopDynamic = false;


    private boolean isDetail;
    /**
     * 是否显示动态标题
     * 用于班级圈过滤动态类型
     */
    private boolean mIsShowDynamic = false;
    /**
     * 当前过滤类型
     */
    private int mCurrentType = 0;

    public ActionAdapter() {
    }

    public static final ActionAdapter create(BaseActivity activity, List<ActionListBean> data, int type, boolean isShowDynamic) {
        return new ActionAdapter(activity, data, isShowDynamic, type);
    }

    private ActionAdapter(BaseActivity activity, List<ActionListBean> data, boolean isShowDynamic, int type) {
        if (isShowDynamic) {
            data.add(0, new ActionListBean());
        }
        this.activity = activity;
        this.mActionListData = data;
        this.showType = type;
        this.mIsShowDynamic = isShowDynamic;
    }

    public ActionAdapter(BaseActivity activity, List<ActionListBean> data, int type) {
        this.activity = activity;
        this.mActionListData = data;
        this.showType = type;
    }

    public ActionAdapter(BaseActivity activity, List<ActionListBean> data, int type, boolean isComment) {
        this.activity = activity;
        this.mActionListData = data;
        this.showType = type;
        this.isComment = isComment;
    }

    public void setOnClickTopDynamicStatus(boolean status) {
        this.isClickTopDynamic = status;
    }

    public boolean getTopDynamicStatus() {
        return this.isClickTopDynamic;
    }

    public void setListData(List<ActionListBean> beanList, int currentType, boolean isShowDynamic) {
        this.mIsShowDynamic = isShowDynamic;
        final ArrayList<ActionListBean> temp = new ArrayList<>();
        temp.addAll(beanList);
        if (mIsShowDynamic) {
            temp.add(0, new ActionListBean());
        }
        this.mActionListData = temp;
        this.mCurrentType = currentType;
    }


    public List<ActionListBean> getListData() {
        return mActionListData;
    }

    public void setDetail(boolean detail) {
        isDetail = detail;
    }

    public void setJurisdiction(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public int getCount() {
        return mActionListData.size();
    }

    @Override
    public Object getItem(int i) {
        return mActionListData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setItem(int position, ActionListBean bean) {
        mActionListData.set(position, bean);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        mActionListData.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        DynamicTypeViewHolder actionDynamicHolder = null;
        ActionAlbumViewHolder albumViewHolder = null;
        ActionEssayViewHolder essayViewHolder = null;
        ActionLogViewHolder logViewHolder = null;
        ActionHuoDongViewHolder huoDongViewHolder = null;
        ActionGroupViewHolder groupViewHolder = null;
        ActionLevelViewHolder levelViewHolder = null;
        ActionPictureViewHolder pictureViewHolder = null;
        ActionListBean bean = mActionListData.get(i);

        //对班级圈动态类型单独判断
        if (i == 0 && mIsShowDynamic) {
            return getDynamicTitleView(actionDynamicHolder, viewGroup, i);
        }

        //根据返回过来不同的itemKey去加载子条目上的不同类型view
        switch (bean.getActivityItemKey()) {
            case UPLOAD_PHOTO://上传图片
                return getAlbumView(albumViewHolder, viewGroup, bean, i);
            case CREATE_MICRO_BLOG://随记
                return getEssayView(essayViewHolder, viewGroup, bean, i);
            case PICTURE://图片
                return getPictureView(pictureViewHolder, viewGroup, bean, i);
            case CREATE_BLOG://日志
                return getLogView(logViewHolder, viewGroup, bean, i);
            //以下均为不同的活动相关类型
            case JOIN_CLASS_ACTIVITY:
            case CREATE_CLASS_ACTIVITY:
            case JOIN_GROUP_ACTIVITY:
            case CREATE_GROUP_ACTIVITY:
                return getHuoDongView(huoDongViewHolder, viewGroup, bean, i);
            case ADD_GROUP_MEMBER:
            case CREATE_GROUP:
                return getGroupView(groupViewHolder, viewGroup, bean, i);
            case CREATE_LEVEL_UP:
                return getLevelView(levelViewHolder, viewGroup, bean, i);
            default:
                HiStudentLog.e("=======》找不到相配器的动态类型");
                break;
        }
        return null;
    }


    /**
     * 班级圈动态类型过滤
     * 全部动态、随记、日志、照片、活动
     *
     * @param actionDynamicHolder
     * @return
     */
    private View getDynamicTitleView(DynamicTypeViewHolder actionDynamicHolder, ViewGroup viewGroup, int position) {
        View view = null;
        if (actionDynamicHolder == null) {
            view = LayoutInflater.from(activity).inflate(R.layout.item_dynamic_hover_layout, viewGroup, false);
            actionDynamicHolder = new DynamicTypeViewHolder(view);
            view.setTag(actionDynamicHolder);
        } else {
            actionDynamicHolder = (DynamicTypeViewHolder) view.getTag();
        }
        actionDynamicHolder.mLLDynamicLayout.setOnClickListener((View v) -> {
            //显示popupWindow(具体显示是当ListView滑动后 弹出的  根据当前用户所点击的view距顶部高度)
            if (activity instanceof ClassCircleActivity) {
                ((ClassCircleActivity) activity).scrollAssignLocation();
                setOnClickTopDynamicStatus(true);
            }
        });
        final AppCompatTextView tvModifyTitle = actionDynamicHolder.mTvModifyTitle;
        if (activity instanceof ClassCircleActivity) {
            final String content = ((ClassCircleActivity) activity).getTextBySelectType(mCurrentType);
            ((ClassCircleActivity) activity).refreshDynamicTitle(mCurrentType);
            tvModifyTitle.setText(content);
        }
        return view;
    }


    public void setCurrentType(int type) {
        this.mCurrentType = type;
    }

    /**
     * 绑定照片数据
     *
     * @param pictureViewHolder
     * @param viewGroup
     * @return
     */
    private View getPictureView(ActionPictureViewHolder pictureViewHolder, ViewGroup viewGroup, final ActionListBean bean, int position) {
        View view = null;
        if (pictureViewHolder == null) {
            view = LayoutInflater.from(activity).inflate(R.layout.activity_picture_item, viewGroup, false);
            pictureViewHolder = new ActionPictureViewHolder(view);
            view.setTag(pictureViewHolder);
        } else {
            pictureViewHolder = (ActionPictureViewHolder) view.getTag();
        }
        pictureViewHolder.bindCommenData(activity, pictureViewHolder, bean, showType, isDetail);


        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) pictureViewHolder.picture_img.getLayoutParams();
        layoutParams.weight = SystemUtil.getScreenWind();
        layoutParams.height = SystemUtil.getScreenWind() * 3 / 4;
        pictureViewHolder.picture_img.setLayoutParams(layoutParams);

        CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(activity, bean.getImages().get(0).getBigSizeUrl(), pictureViewHolder.picture_img);
        doSomething(pictureViewHolder, bean, view, position);

        return view;
    }

    /**
     * 绑定相册数据
     *
     * @param albumViewHolder
     * @param viewGroup
     * @return
     */
    private View getAlbumView(ActionAlbumViewHolder albumViewHolder, ViewGroup viewGroup, final ActionListBean bean, int position) {
        View view = null;
        if (albumViewHolder == null) {
            view = LayoutInflater.from(activity).inflate(R.layout.action_album_item, viewGroup, false);
            albumViewHolder = new ActionAlbumViewHolder(view);
            view.setTag(albumViewHolder);
        } else {
            albumViewHolder = (ActionAlbumViewHolder) view.getTag();
        }
        albumViewHolder.bindCommenData(activity, albumViewHolder, bean, showType, isDetail);

        List<HomePageHelper.ImageBeans> imageBeanses = new ArrayList<>();
        List<ActionListBean.ImagesBean> imagesBeanList = bean.getImages();
        List<ImageAttrEntity> imageUrls = new ArrayList<>();
        if (imagesBeanList == null || imagesBeanList.size() == 0) {
            albumViewHolder.picture_list.setVisibility(View.GONE);
        } else {
            for (int i = 0; i < imagesBeanList.size(); i++) {
                HomePageHelper.ImageBeans imageBeans = new HomePageHelper.ImageBeans();
                imageBeans.setThumbnailUrl(bean.getImages().get(i).getThumbnailUrl());
                imageBeanses.add(imageBeans);
                ImageAttrEntity imageUrl = new ImageAttrEntity();
                imageUrl.setId(bean.getImages().get(i).getImgId());
                imageUrl.setThumbnailUrl(bean.getImages().get(i).getThumbnailUrl());
                imageUrl.setBigSizeUrl(bean.getImages().get(i).getBigSizeUrl());
                imageUrls.add(imageUrl);
            }
            albumViewHolder.picture_list.setVisibility(View.VISIBLE);

            albumViewHolder.picture_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    ImageBrowserActivity.start(activity, position, 111, bean, ShowImageType.COMMENT);

                    showImageDetail(view, position, imageUrls);
                }
            });
            HomePageHelper.getIntence().showPictures(activity, albumViewHolder.picture_list, imageUrls);
        }

        String actionInfo = bean.getSummary();
        if (TextUtils.isEmpty(actionInfo)) {
            albumViewHolder.album_summer.setVisibility(View.GONE);
        } else {
            albumViewHolder.album_summer.setVisibility(View.VISIBLE);
            albumViewHolder.album_summer.setText(EmotionUtils.convertNormalStringToSpannableString(bean.getSummary()));
        }

        albumViewHolder.title.setText("上传" + imagesBeanList.size() + "张照片到相册");
        albumViewHolder.action_album_name.setText("《" + bean.getTitle() + "》");
        albumViewHolder.action_album_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClassHelper.gotoAlbum(activity, bean.getSourceId());
            }
        });
        doSomething(albumViewHolder, bean, view, position);
        return view;
    }


    private void showImageDetail(View view, int position, List<ImageAttrEntity> imageAttrs) {
        Intent intent = new Intent(activity, ShowImageActivity.class);
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
        bundle.putInt("column_num", getColumn(imageAttrs));
        bundle.putInt("horizontal_space", DisplayUtils.dp2px(activity, 4));
        bundle.putInt("vertical_space", DisplayUtils.dp2px(activity, 4));

        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    private int getColumn(List<ImageAttrEntity> imageAttrs) {
        int columns;
        int size = imageAttrs.size();
        switch (size) {
            case 1:
                columns = 1;
                break;
            case 2:
                columns = 2;
                break;
            default:
                columns = 3;
                break;
        }
        return columns;
    }

    /**
     * 绑定随记数据
     *
     * @param essayViewHolder
     * @param viewGroup
     * @return
     */
    private View getEssayView(ActionEssayViewHolder essayViewHolder, ViewGroup viewGroup, final ActionListBean bean, int position) {
        View view = null;
        if (essayViewHolder == null) {
            view = LayoutInflater.from(activity).inflate(R.layout.action_essay_item, viewGroup, false);
            essayViewHolder = new ActionEssayViewHolder(view);
            view.setTag(essayViewHolder);
        } else {
            essayViewHolder = (ActionEssayViewHolder) view.getTag();
        }
        essayViewHolder.bindCommenData(activity, essayViewHolder, bean, showType, isDetail);

        ArrayList<ActionListBean.ImagesBean> imagesBeanList = new ArrayList<>();
        List<ImageAttrEntity> imageUrls = new ArrayList<>();
        imagesBeanList.addAll(bean.getImages());

        if (bean.getMultiMediaType() == 0) {//照片
            essayViewHolder.action_essay_movie.setVisibility(View.GONE);
            if (imagesBeanList != null && imagesBeanList.size() > 0) {
                essayViewHolder.action_essay_gridView.setVisibility(View.VISIBLE);
                for (int i = 0; i < imagesBeanList.size(); i++) {
                    ImageAttrEntity imageUrl = new ImageAttrEntity();
                    imageUrl.setId(bean.getImages().get(i).getImgId());
                    imageUrl.setThumbnailUrl(bean.getImages().get(i).getThumbnailUrl());
                    imageUrl.setBigSizeUrl(bean.getImages().get(i).getBigSizeUrl());
                    imageUrls.add(imageUrl);
                }

                essayViewHolder.action_essay_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //ImageBrowserActivity.start(activity, position, 111, imagesBeanList, ShowImageType.SAVE, 0, "");
                        showImageDetail(view, position, imageUrls);
                    }
                });
                HomePageHelper.getIntence().showPictures(activity, essayViewHolder.action_essay_gridView, imageUrls);
            }
        } else if (bean.getMultiMediaType() == 1) {//视频
            essayViewHolder.action_essay_gridView.setVisibility(View.GONE);
            essayViewHolder.action_essay_movie.setVisibility(View.VISIBLE);

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) essayViewHolder.essay_movie_cover.getLayoutParams();
            params.height = SystemUtil.getScreenWind() * 3 / 4;
            essayViewHolder.essay_movie_cover.setLayoutParams(params);

            CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(activity, bean.getCover(), essayViewHolder.essay_movie_cover);
            essayViewHolder.video_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!TextUtils.isEmpty(bean.getVideoId())) {
//                        MyWebActivity.startNoTitle(activity, HistudentUrl.playvideo + bean.getVideoId());
                        WatchActionVideoActivity.start(activity, bean.getVideoId());
                    } else {
                        MyWebActivity.start(activity, bean.getHtmlUrl());
                    }

                }
            });
        }

        if (TextUtils.isEmpty(bean.getSummary())) {
            essayViewHolder.content.setVisibility(View.GONE);
        } else {
            essayViewHolder.content.setVisibility(View.VISIBLE);
            if (bean.getTopicList() != null && bean.getTopicList().size() > 0) {
                for (TopicModel topicModel : bean.getTopicList()) {
                    essayViewHolder.content.addTopic(topicModel);
                }
            }
            if (bean.getAtUserList() != null && bean.getAtUserList().size() > 0) {
                for (AtUserModel atUserModel : bean.getAtUserList()) {
                    essayViewHolder.content.addUser(atUserModel);
                }
            }
            essayViewHolder.content.setText(bean.getSummary());
            essayViewHolder.content.setListener(new RichTextView.RichTextClick() {
                @Override
                public void clickTopic(RichTextView.Topic topic) {
                    TopicActivity.start(activity, topic.hash);
                }

                @Override
                public void clickUser(RichTextView.User user) {
                    PersonCenterActivity.start(activity, user.id);
                }
            });
//            essayViewHolder.content.setText(EmotionUtils.convertNormalStringToSpannableString(bean.getSummary()));
        }

        if (showType == INVITATION) {
            String ownerName = bean.getOwnerName();
            if (TextUtils.isEmpty(ownerName)) {
                essayViewHolder.item_location_layout.setVisibility(View.GONE);
            } else {
                essayViewHolder.item_location_layout.setVisibility(View.VISIBLE);
                essayViewHolder.item_commen_location_image.setVisibility(View.GONE);
                essayViewHolder.item_group_name.setVisibility(View.VISIBLE);
                essayViewHolder.item_location.setText(bean.getOwnerName());
            }
        } else {
            String location = bean.getLocation();
            if (TextUtils.isEmpty(location)) {
                essayViewHolder.item_location_layout.setVisibility(View.GONE);
            } else {
                essayViewHolder.item_location_layout.setVisibility(View.VISIBLE);
                essayViewHolder.item_group_name.setVisibility(View.GONE);
                essayViewHolder.item_location.setText(location);
                essayViewHolder.item_location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AddressInforBean inforBean = new AddressInforBean();
                        inforBean.setDetailStr(bean.getLocation());
                        inforBean.setLatitude(bean.getLatitude());
                        inforBean.setLongitude(bean.getLongitude());
                        MapActivity.start(activity, inforBean, LoactionType.DYNAMIC);
                    }
                });
            }
        }

        doSomething(essayViewHolder, bean, view, position);
        return view;
    }

    /**
     * 绑定日志数据
     *
     * @param logViewHolder
     * @param viewGroup
     * @return
     */
    private View getLogView(ActionLogViewHolder logViewHolder, ViewGroup viewGroup, ActionListBean bean, int position) {
        View view = null;
        if (logViewHolder == null) {
            view = LayoutInflater.from(activity).inflate(R.layout.action_log_item, viewGroup, false);
            logViewHolder = new ActionLogViewHolder(view);
            view.setTag(logViewHolder);
        } else {
            logViewHolder = (ActionLogViewHolder) view.getTag();
        }
        logViewHolder.bindCommenData(activity, logViewHolder, bean, showType, isDetail);

        if (!TextUtils.isEmpty(bean.getCover())) {
            logViewHolder.action_log_cover_layout.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) logViewHolder.action_log_cover_layout.getLayoutParams();
            layoutParams.height = SystemUtil.getScreenWind() * 3 / 4;
            layoutParams.width = SystemUtil.getScreenWind();
            logViewHolder.action_log_cover_layout.setLayoutParams(layoutParams);

            CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(activity, bean.getCover(),
                    logViewHolder.action_log_cover, PhotoManager.getInstance().getDefaultPlaceholderResource());

            if (bean.getImageCount() > 1) {
                logViewHolder.action_log_images.setVisibility(View.VISIBLE);
                logViewHolder.action_log_images.setText(bean.getImageCount() + "张");
            } else {
                logViewHolder.action_log_images.setVisibility(View.GONE);
            }

        } else {
            logViewHolder.action_log_cover_layout.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(bean.getTitle().trim())) {
            logViewHolder.action_log_title.setVisibility(View.GONE);
        } else {
            logViewHolder.action_log_title.setVisibility(View.VISIBLE);
            logViewHolder.action_log_title.setText(EmotionUtils.convertNormalStringToSpannableString(bean.getTitle()));
        }

        if (TextUtils.isEmpty(bean.getSummary().trim())) {
            logViewHolder.action_log_content.setVisibility(View.GONE);
        } else {
            logViewHolder.action_log_content.setVisibility(View.VISIBLE);
            logViewHolder.action_log_content.setText(EmotionUtils.convertNormalStringToSpannableString(bean.getSummary()));
        }

        doSomething(logViewHolder, bean, view, position);
        return view;
    }

    /**
     * 绑定活动数据
     *
     * @param huoDongViewHolder
     * @param viewGroup
     * @return
     */
    private View getHuoDongView(ActionHuoDongViewHolder huoDongViewHolder, ViewGroup viewGroup, ActionListBean bean, int position) {
        View view = null;
        if (huoDongViewHolder == null) {
            view = LayoutInflater.from(activity).inflate(R.layout.action_huodong_item, viewGroup, false);
            huoDongViewHolder = new ActionHuoDongViewHolder(view);
            view.setTag(huoDongViewHolder);
        } else {
            huoDongViewHolder = (ActionHuoDongViewHolder) view.getTag();
        }
        huoDongViewHolder.bindCommenData(activity, huoDongViewHolder, bean, showType, isDetail);

        if (TextUtils.isEmpty(bean.getActionInfo())) {
            huoDongViewHolder.huodong_summer.setVisibility(View.GONE);
        } else {
            huoDongViewHolder.huodong_summer.setText(bean.getActionInfo());
        }

        CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(activity, bean.getCover(),
                huoDongViewHolder.huodong_cover, PhotoManager.getInstance().getDefaultPlaceholderResource());
        huoDongViewHolder.huodong_title.setText(EmotionUtils.convertNormalStringToSpannableString(bean.getTitle()));
        huoDongViewHolder.huodong_content.setText(EmotionUtils.convertNormalStringToSpannableString(bean.getSummary()));

        doSomething(huoDongViewHolder, bean, view, position);
        return view;
    }

    /**
     * 绑定社群数据
     *
     * @param groupViewHolder
     * @param viewGroup
     * @return
     */
    private View getGroupView(ActionGroupViewHolder groupViewHolder, ViewGroup viewGroup, ActionListBean bean, int position) {
        View view = null;
        if (groupViewHolder == null) {
            view = LayoutInflater.from(activity).inflate(R.layout.action_group_item, viewGroup, false);
            groupViewHolder = new ActionGroupViewHolder(view);
            view.setTag(groupViewHolder);
        } else {
            groupViewHolder = (ActionGroupViewHolder) view.getTag();
        }
        groupViewHolder.bindCommenData(activity, groupViewHolder, bean, showType, isDetail);
        if (TextUtils.isEmpty(bean.getActionInfo())) {
            groupViewHolder.huodong_summer.setVisibility(View.GONE);
        } else {
            groupViewHolder.huodong_summer.setText(bean.getActionInfo());
        }

        CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(activity, bean.getCover(), groupViewHolder.huodong_cover);
        groupViewHolder.huodong_title.setText(EmotionUtils.convertNormalStringToSpannableString(bean.getTitle()));
        groupViewHolder.huodong_content.setText(EmotionUtils.convertNormalStringToSpannableString(bean.getSummary()));

        doSomething(groupViewHolder, bean, view, position);
        return view;
    }

    /**
     * 绑定成长等级数据
     *
     * @param levelViewHolder
     * @param viewGroup
     * @return
     */
    private View getLevelView(ActionLevelViewHolder levelViewHolder, ViewGroup viewGroup, ActionListBean bean, int position) {
        View view = null;
        if (levelViewHolder == null) {
            view = LayoutInflater.from(activity).inflate(R.layout.action_level_item, viewGroup, false);
            levelViewHolder = new ActionLevelViewHolder(view);
            view.setTag(levelViewHolder);
        } else {
            levelViewHolder = (ActionLevelViewHolder) view.getTag();
        }
        levelViewHolder.bindCommenData(activity, levelViewHolder, bean, showType, isDetail);
        levelViewHolder.level_num.setText("LV." + bean.getSummary());
        levelViewHolder.level_num_.setText("我升级到" + bean.getSummary() + "级啦！快来围观！");
        doSomething(levelViewHolder, bean, view, position);
        return view;
    }


    //==========================================================================================================================

    /**
     * 共有的操作
     *
     * @param holder
     * @param bean
     * @param view
     */
    private void doSomething(final ActionViewHolder holder, final ActionListBean bean, final View view, final int position) {
        showUserInfo(holder, bean);
        doPraise(holder, bean);
        doComment(holder, bean, position);
        itemListener(bean, view, holder, position);
        doShare(holder, bean);
        doMore(holder, bean);
        showLikes(holder, bean);
    }

    /**
     * 跳转个人主页
     */
    private void showUserInfo(final ActionViewHolder holder, final ActionListBean bean) {
        if (showType == LOG || showType == ESSAY) return;
        holder.action_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonCenterActivity.start(activity, bean.getUserId());
            }
        });
    }

    /**
     * 点赞操作
     */
    private void doPraise(final ActionViewHolder holder, final ActionListBean bean) {

        holder.action_like
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(final View v) {

                                if (flag_onclick)
                                    return;

                                flag_onclick = true;
                                final boolean flag_ = bean.getIsPraised();

                                if ("Picture".equals(bean.getActivityItemKey())) {
                                    Map<String, Object> map = new TreeMap<>();
                                    map.put("doOrUndo", !flag_);
                                    map.put("objectType", 5);
                                    map.put("objectId", bean.getActId());
                                    doThing(holder, bean, flag_);
                                    HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.praise_url, new HttpRequestCallBack() {
                                        @Override
                                        public void onSuccess(String result) {

                                        }

                                        @Override
                                        public void onFailure(String msg) {
                                            doThing(holder, bean, !flag_);
                                        }
                                    }, LoadingType.NONE);
                                } else {
                                    Map<String, Object> map = new TreeMap<>();
                                    map.put("doOrUndo", !flag_);
                                    map.put("objectType", 1);
                                    map.put("objectId", bean.getActId());
                                    doThing(holder, bean, flag_);
                                    HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.praise_url, new HttpRequestCallBack() {
                                        @Override
                                        public void onSuccess(String result) {

                                            //EventBus.getDefault().post(new CommentUpdateEvent());
                                        }

                                        @Override
                                        public void onFailure(String msg) {
                                            doThing(holder, bean, !flag_);
                                            EventBus.getDefault().post(new CommentUpdateEvent());
                                        }
                                    }, LoadingType.NONE);
                                }
                            }
                        }

                );

    }

    private void doThing(ActionViewHolder holder, ActionListBean bean, boolean flag_) {
        int num = bean.getPraiseCount();
        bean.setIsPraised(!flag_);
        bean.setPraiseCount(flag_ ? num - 1 : num + 1);

        //切换点赞图标
        holder.action_like.setTextColor(activity.getResources().getColor(!flag_ ? R.color.green_color : R.color.text_black_h3));
        holder.action_like.setText(!flag_ ? R.string.icon_zan : R.string.icon_zannone);
        flag_onclick = false;

        //切换点赞人数
        if (showType == ActionAdapter.COMMENT || showType == ActionAdapter.INVITATION || (bean.getPraiseCount() == 0 && bean.getCommentCount() == 0)) {
            holder.like_layout.setVisibility(View.GONE);
        } else {

            if (bean.getPraiseCount() > 0 && !isDetail) {
                holder.like_layout.setVisibility(View.VISIBLE);
                holder.show_like_num.setText(bean.getPraiseCount() + "人点赞");
            } else {
                holder.like_layout.setVisibility(View.GONE);
            }
        }

//        if (bean.getCommentCount() == 0 && isDetail) {
//            holder.comment_layout.setVisibility(View.GONE);
//        }else{
//            holder.comment_layout.setVisibility(View.VISIBLE);
//        }


//        if (showType == COMMENT)
        EventBus.getDefault().post(new CommentActivity.CommentActions(!flag_ ? 2 : 3));
    }

    /**
     * 友盟分享
     */
    private void doShare(ActionViewHolder holder, final ActionListBean bean) {
        holder.action_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!"Picture".equals(bean.getActivityItemKey())) {
                    getShareData(activity, bean.getActId());
                }
            }
        });
    }

    /**
     * 获取动态动分享数据
     *
     * @param activityId
     */
    private void getShareData(BaseActivity activity, final String activityId) {
        if (TextUtils.isEmpty(activityId)) return;
        Map<String, Object> map = new TreeMap<>();
        map.put("activityId", activityId);

        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.share_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                MyShareBean shareBean = JSONObject.parseObject(result, MyShareBean.class);
                SharePopupWindow popupWindow = new SharePopupWindow(activity, shareBean);
                popupWindow.showAtLocation(activity.findViewById(R.id.layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }

            @Override
            public void onFailure(String msg) {

            }
        });

    }

    /**
     * 弹出下拉弹窗
     */
    private void doMore(ActionViewHolder holder, final ActionListBean bean) {

        holder.action_menue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showClassMenue(activity, bean);
            }
        });
    }

    /**
     * 查看点赞人
     */
    private void showLikes(ActionViewHolder holder, final ActionListBean bean) {
        holder.show_like_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LikePersionListActivity.start(activity, bean.getActId(), false);
            }
        });
    }

    /**
     * 显示菜单
     *
     * @param activity
     */
    private void showClassMenue(final Activity activity, final ActionListBean bean) {

        List<String> list_name = new ArrayList<>();
        List<Integer> list_color = new ArrayList<>();

        final boolean isMine = SystemUtil.isOneselfIn(bean.getUserId());

        ClassModel classModel = HiCache.getInstance().getClassDetailInfo();

        switch (showType) {
            case CLASS_CIRCLE:
                if (classModel != null && classModel.isIsAdmin()) {
                    list_name.add(bean.getIsRecommand() ? "取消推荐" : "推荐");
                    list_name.add(isShield ? "取消屏蔽" : "屏蔽");
                    list_name.add(isMine ? "删除" : "举报");
                    list_color.add(Color.rgb(51, 51, 51));
                    list_color.add(Color.rgb(51, 51, 51));
                    list_color.add(Color.rgb(51, 51, 51));

                    menuWindow = new TopMenuPopupWindow(activity, new View.OnClickListener() {

                        public void onClick(View v) {
                            menuWindow.dismiss();
                            switch (v.getId()) {
                                case R.id.btn_01:
                                    setActivityRecommend(bean);
                                    break;

                                case R.id.btn_02:
                                    disableActivity(bean);
                                    break;

                                case R.id.btn_03:
                                    if (isMine) {
                                        deletTimeline(bean);
                                    } else {
                                        ReportActivity.start(activity, bean.getActId(), ReportType.DYNAMIC);
                                    }
                                    break;

                                default:
                                    break;
                            }
                        }
                    }, list_name, list_color, false);
                } else {
                    if (isMine) {
                        list_name.add("删除");
                        list_color.add(Color.rgb(51, 51, 51));

                        menuWindow = new TopMenuPopupWindow(activity, new View.OnClickListener() {

                            public void onClick(View v) {
                                menuWindow.dismiss();
                                switch (v.getId()) {
                                    case R.id.btn_01:
                                        deletTimeline(bean);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }, list_name, list_color, false);
                    } else {
                        list_name.add("举报");
                        list_color.add(Color.rgb(51, 51, 51));

                        menuWindow = new TopMenuPopupWindow(activity, new View.OnClickListener() {

                            public void onClick(View v) {
                                menuWindow.dismiss();
                                switch (v.getId()) {
                                    case R.id.btn_01:
                                        ReportActivity.start(activity, bean.getActId(), ReportType.DYNAMIC);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }, list_name, list_color, false);
                    }
                }
                break;

            case LOG:
                if (isMine) {
                    list_name.add("删除");
                    list_color.add(Color.rgb(51, 51, 51));

                    menuWindow = new TopMenuPopupWindow(activity, new View.OnClickListener() {

                        public void onClick(View v) {
                            menuWindow.dismiss();
                            switch (v.getId()) {
                                case R.id.btn_01:
                                    deletBlog(bean);
                                    break;

                                default:
                                    break;
                            }
                        }
                    }, list_name, list_color, false);
                } else {
                    list_name.add("举报");
                    list_color.add(Color.rgb(51, 51, 51));

                    menuWindow = new TopMenuPopupWindow(activity, new View.OnClickListener() {

                        public void onClick(View v) {
                            menuWindow.dismiss();
                            switch (v.getId()) {
                                case R.id.btn_01:
                                    ReportActivity.start(activity, bean.getActId(), ReportType.LOG);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }, list_name, list_color, false);
                }

                break;

            case ESSAY:
                if (isMine) {
                    list_name.add("删除");
                    list_color.add(Color.rgb(51, 51, 51));

                    menuWindow = new TopMenuPopupWindow(activity, new View.OnClickListener() {

                        public void onClick(View v) {
                            menuWindow.dismiss();
                            switch (v.getId()) {
                                case R.id.btn_01:
                                    deletEssay(bean);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }, list_name, list_color, false);
                } else {
                    list_name.add("举报");
                    list_color.add(Color.rgb(51, 51, 51));

                    menuWindow = new TopMenuPopupWindow(activity, new View.OnClickListener() {

                        public void onClick(View v) {
                            menuWindow.dismiss();
                            switch (v.getId()) {
                                case R.id.btn_01:
                                    ReportActivity.start(activity, bean.getActId(), ReportType.ESSAY);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }, list_name, list_color, false);
                }

                break;

            case PERSION:
            case TOPIC:
            case SHCOOL:
            case GROUP:
            case COMMENT:
                list_name.add(isMine ? "删除" : "举报");
                list_color.add(Color.rgb(51, 51, 51));

                menuWindow = new TopMenuPopupWindow(activity, new View.OnClickListener() {

                    public void onClick(View v) {
                        menuWindow.dismiss();
                        switch (v.getId()) {
                            case R.id.btn_01:
                                if (isMine) {
                                    deletTimeline(bean);
                                } else {
                                    ReportActivity.start(activity, bean.getActId(), ReportType.DYNAMIC);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }, list_name, list_color, false);
                break;

        }

        if (menuWindow != null)
            menuWindow.showAtLocation(activity.findViewById(R.id.layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }

    /**
     * 设置/取消推荐动态
     */
    private void setActivityRecommend(final ActionListBean bean) {
        final boolean flag = bean.getIsRecommand();
        Map<String, Object> map = new TreeMap<>();
        map.put("isRecommend", !flag);
        map.put("actId", bean.getActId());

        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.setActivityRecommend_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                bean.setIsRecommand(!flag);
                if (activity instanceof CommentActivity) {
                    EventBus.getDefault().post(new Refresh());
                }

                notifyDataSetChanged();
            }

            @Override
            public void onFailure(String msg) {
            }
        });
    }

    /**
     * 屏蔽动态
     */
    private void disableActivity(final ActionListBean bean) {
        Map<String, Object> map = new TreeMap<>();
        map.put("objectId", HiCache.getInstance().getClassDetailInfo().getClassId());
        map.put("actId", bean.getActId());
        map.put("objectType", 1);
        map.put("isDisable", !isShield);

        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.disableActivity_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                EventBus.getDefault().post(new Refresh());
                if (activity instanceof CommentActivity) {
                    activity.finish();
                }

            }

            @Override
            public void onFailure(String msg) {
            }
        });
    }

    /**
     * 删除随记
     */
    private void deletEssay(final ActionListBean bean) {
        Map<String, Object> map = new TreeMap<>();
        map.put("microblogid", bean.getSourceId());

        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.deleteMicroBlog, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                mActionListData.remove(bean);
                notifyDataSetChanged();
                EventBus.getDefault().post(new DeletAction());
            }

            @Override
            public void onFailure(String msg) {
            }
        });
    }

    /**
     * 删除日志
     */
    private void deletBlog(final ActionListBean bean) {
        Map<String, Object> map = new TreeMap<>();
        map.put("blogId", bean.getSourceId());

        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.deleteBlog_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                mActionListData.remove(bean);
                notifyDataSetChanged();
                EventBus.getDefault().post(new DeletAction());
            }

            @Override
            public void onFailure(String msg) {
            }
        });
    }

    /**
     * 删除动态
     */
    private void deletTimeline(final ActionListBean bean) {
        Map<String, Object> map = new TreeMap<>();
        map.put("actId", bean.getActId());
        map.put("ownerId", bean.getOwnerId());
        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.deleteTimeline_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                if (showType == COMMENT) {
                    EventBus.getDefault().post(new CommentActivity.CommentActions(1));
                } else {
                    mActionListData.remove(bean);
                    notifyDataSetChanged();
                    if(activity instanceof ClassCircleActivity){
                        ((ClassCircleActivity)activity).updateUIStatus();
                    }
                }
            }

            @Override
            public void onFailure(String msg) {
            }
        });
    }

    /**
     * adapter中item监听
     *
     * @param bean
     * @param view
     */
    private void itemListener(final ActionListBean bean, View view, final ActionViewHolder holder, final int position) {

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (showType == COMMENT) {
//                    EventBus.getDefault().post(true);
//                } else {

                if (isComment) return;

                if (holder instanceof ActionLogViewHolder) {
                    DealActivity.start(activity, bean.getHtmlUrl(), "日志详情");
//                    Intent intent = new Intent(activity, BlogDetailActivity.class);
//                    intent.putExtra("actionId",bean.getActId());
//                    activity.startActivity(intent);
                } else if (holder instanceof ActionHuoDongViewHolder) {

                    switch (bean.getActivityItemKey()) {
                        case "JoinClassHuoDong":
                        case "CreateClassHuoDong":
                            HuoDongCenterActivity.start(activity, bean.getSourceId(), 1, 0);
                            break;
                        case "JoinGroupHuoDong":
                        case "CreateGroupHuoDong":
                            HuoDongCenterActivity.start(activity, bean.getSourceId(), 2, 0);
                            break;
                    }

                } else if (holder instanceof ActionGroupViewHolder) {
                    switch (bean.getActivityItemKey()) {
                        case "AddGroupMember":
                            PersonCenterActivity.start(activity, bean.getUserId());
                            break;

                        case "CreateGroup":
                            GroupCenterActivity.start(activity, bean.getSourceId());
                            break;
                    }
                } else {
                    CommentActivity.start(activity, bean.getActId(), bean.getOwnerId(), showType, position, isShield);
                }
            }
        });
    }

    /**
     * 评论
     *
     * @param holder
     * @param bean
     */
    private void doComment(final ActionViewHolder holder, final ActionListBean bean, final int position) {
        holder.action_discuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isComment) {
                    EventBus.getDefault().post(new CommentActivity.CommentActions(4));
                } else {
                    if (holder instanceof ActionLogViewHolder) {
                        DealActivity.start(activity, bean.getHtmlUrl(), "日志详情");
                    } else {
                        CommentActivity.start(activity, bean.getActId(), bean.getOwnerId(), showType, position, isShield);
                    }
                }
            }
        });

        holder.show_comment_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isComment) {
                    return;
                } else {
                    if (holder instanceof ActionLogViewHolder) {
                        DealActivity.start(activity, bean.getHtmlUrl(), "日志详情");
                    } else {
                        CommentActivity.start(activity, bean.getActId(), bean.getOwnerId(), showType, position, isShield);
                    }
                }
            }
        });
    }

    /**
     * 设置屏蔽与非屏蔽标志位，用于班级圈
     *
     * @param isShield
     */
    public void setisShield(boolean isShield) {
        this.isShield = isShield;
    }

    /**
     * 用于刷新数据
     */
    public static class Refresh {

    }

    /**
     * 删除动态
     */
    public static class DeletAction {

    }

}
