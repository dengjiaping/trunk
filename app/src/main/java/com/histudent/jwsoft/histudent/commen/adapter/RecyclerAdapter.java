package com.histudent.jwsoft.histudent.commen.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.account.login.activity.DealActivity;
import com.histudent.jwsoft.histudent.account.login.model.CurrentUserInfoModel;
import com.histudent.jwsoft.histudent.body.mine.activity.AlbumDetailActivity;
import com.histudent.jwsoft.histudent.body.mine.adapter.GridViewPictureAdapter;
import com.histudent.jwsoft.histudent.body.mine.model.AlbumInfoModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.enums.ItemType;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.enums.ReportType;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.receiver.RequestCodeModel;
import com.histudent.jwsoft.histudent.commen.receiver.TheReceiverAction;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.ActionMorePopWindow;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.commen.view.MyListView2;
import com.histudent.jwsoft.histudent.comment2.activity.ReportActivity;
import com.histudent.jwsoft.histudent.comment2.utils.ActionTypeEnum;
import com.histudent.jwsoft.histudent.comment2.utils.EmotionUtils;
import com.histudent.jwsoft.histudent.comment2.utils.TimeUtils;
import com.histudent.jwsoft.histudent.info.InfoHelper;
import com.histudent.jwsoft.histudent.model.manage.PhotoManager;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

//import com.histudent.jwsoft.histudent.commen.view.SharePopWindow;

/**
 * Created by liuguiyu-pc on 2017/2/7.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ChatHolder> implements View.OnClickListener {

    private List<ActionListBean> datas;
    private Activity activity;
    private boolean flag;
    private ItemType type;
    private String userId;
    private int shareObjectType = -1;//分享类型
    private int requestCode = 0;//跳转评论界面请求码
    private boolean flag_onclick;
    private ReportType reportType;

    public RecyclerAdapter(Activity activity, List<ActionListBean> datas, ItemType type) {
        this.datas = datas;
        this.activity = activity;
        this.type = type;
    }

    public RecyclerAdapter(Activity activity, List<ActionListBean> datas, ItemType type, boolean flag) {
        this.datas = datas;
        this.activity = activity;
        this.flag = flag;
        this.type = type;
    }

    @Override
    public RecyclerAdapter.ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity).inflate(R.layout.persiontims_fragment_list_item_text, null);
        return new ChatHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ChatHolder holder, int position) {

        holder.setIsRecyclable(false);
        final ActionListBean bean = datas.get(position);

        setGridSize(bean, holder);

        String location = bean.getLocation();
        if (TextUtils.isEmpty(location)) {
            holder.item_commen_location_layout.setVisibility(View.GONE);
        } else {
            holder.item_commen_location.setText(location);
        }

        showDifferentView(bean, holder);
        showDifferentType(bean, holder);

        //设置点赞，评论，分享栏初始状态
        holder.item_commen_likeNum_text.setText(bean.getPraiseCount() == 0 ? "" : " " + bean.getPraiseCount());
        holder.item_commen_shareNum_text.setText(bean.getForwardCount() == 0 ? "" : " " + bean.getForwardCount());
        holder.item_commen_commentNum_text.setText(bean.getCommentCount() == 0 ? "" : " " + bean.getCommentCount());
        setCommentBg(holder, bean.getCommentCount() > 0);
        setPraiseBg(holder, bean.getIsPraised());
        showLikeImage(holder, bean);

        //删除日志
        deletLog(holder, bean, position);

        //点击头像跳转个人主页
        gotoHomePage(holder, bean);

        //设置点赞图标点击监听
        doPraise(holder, bean, position);

        //设置评论图标点击监听
        doComment(holder, bean, position);

        //设置分享图标点击监听
        doShare(holder, bean);

        //设置向下图标点击监听
        doMore(holder, bean);

        //设置“我的模块”中评论的item点击和长按监听
        doCommentItem(holder, bean, position);

        //删除随记
        deletEssay(holder, bean, position);

        //设置item点击监听
        onClickItem(holder, bean);

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ChatHolder extends RecyclerView.ViewHolder {

        View item_top_null;
        TextView item_foot_line;

        //commen
        TextView item_commen_content, item_commen_location, item_commen_title_left, item_commen_title_right, item_commen_line;
        LinearLayout item_commen_commentNum, item_commen_shareNum, item_commen_likeNum;
        TextView item_commen_commentNum_text, item_commen_shareNum_text, item_commen_likeNum_text;
        ImageView item_commen_commentNum_image, item_commen_shareNum_image, item_commen_likeNum_image;
        GridView item_commen_likes;
        GridView item_commen_picture;
        ImageView item_commen_location_image;
        LinearLayout item_commen_location_layout, item_commen_foot_down_layout;
        RelativeLayout layout_commen_content_body, item_commen_content_layout;

        //persionTimes
        TextView item_persionTimes_days, item_persionTimes_months;
        ImageView item_persionTimes_cover;
        LinearLayout item_persionTimes_time;

        //essay
        TextView item_essay_time;
        ImageView item_essay_delet, item_persionTimes_play;
        LinearLayout item_essay_layout;

        //blog
        TextView item_blog_time, item_blog_title, item_blog_name;
        ImageView item_blog_delete;
        HiStudentHeadImageView item_blog_image;
        RelativeLayout item_blog_layout;

        //goodFriend
        RelativeLayout item_goodFriend_layout;
        HiStudentHeadImageView item_goodFriend_image;
        TextView item_goodFriend_name, item_goodFriend_time;
        ImageView item_goodFriend_more;

        //mine
        MyListView2 item_mine_list;

        //photo
        ImageView item_photo_image;

        public ChatHolder(View view) {
            super(view);
            item_top_null = view.findViewById(R.id.item_top_null);
            item_foot_line = view.findViewById(R.id.item_foot_line);

            item_commen_content = view.findViewById(R.id.item_commen_content);
            item_commen_location = view.findViewById(R.id.item_commen_location);
            item_commen_line = view.findViewById(R.id.item_commen_line);
            item_commen_likeNum = view.findViewById(R.id.item_commen_likeNum);
            item_commen_commentNum = view.findViewById(R.id.item_commen_commentNum);
            item_commen_shareNum = view.findViewById(R.id.item_commen_shareNum);

            item_persionTimes_play = view.findViewById(R.id.item_persionTimes_play);

            item_commen_likeNum_text = view.findViewById(R.id.item_commen_likeNum_text);
            item_commen_commentNum_text = view.findViewById(R.id.item_commen_commentNum_text);
            item_commen_shareNum_text = view.findViewById(R.id.item_commen_shareNum_text);

            item_commen_likeNum_image = view.findViewById(R.id.item_commen_likeNum_image);
            item_commen_commentNum_image = view.findViewById(R.id.item_commen_commentNum_image);

            item_commen_likes = view.findViewById(R.id.item_commen_likes);
            item_commen_picture = view.findViewById(R.id.item_commen_picture);
            item_commen_title_left = view.findViewById(R.id.item_commen_title_left);
            item_commen_title_right = view.findViewById(R.id.item_commen_title_right);
            item_commen_location_image = view.findViewById(R.id.item_commen_location_image);
            item_commen_location_layout = view.findViewById(R.id.item_commen_location_layout);
            item_commen_foot_down_layout = view.findViewById(R.id.item_commen_foot_down_layout);
            layout_commen_content_body = view.findViewById(R.id.layout_commen_content_body);

            item_commen_content_layout = view.findViewById(R.id.item_commen_content_layout);

            item_persionTimes_time = view.findViewById(R.id.item_persionTimes_time);
            item_persionTimes_days = view.findViewById(R.id.item_persionTimes_days);
            item_persionTimes_months = view.findViewById(R.id.item_persionTimes_months);
            item_persionTimes_cover = view.findViewById(R.id.item_persionTimes_cover);

            item_essay_layout = view.findViewById(R.id.item_essay_layout);
            item_essay_time = view.findViewById(R.id.item_essay_time);
            item_essay_delet = view.findViewById(R.id.item_essay_delet);

            item_blog_layout = view.findViewById(R.id.item_blog_layout);
            item_blog_time = view.findViewById(R.id.item_blog_time);
            item_blog_delete = view.findViewById(R.id.item_blog_delet);
            item_blog_title = view.findViewById(R.id.item_blog_title);
            item_blog_image = view.findViewById(R.id.item_blog_image);
            item_blog_name = view.findViewById(R.id.item_blog_name);

            item_goodFriend_layout = view.findViewById(R.id.item_goodFriend_layout);
            item_goodFriend_image = view.findViewById(R.id.item_goodFriend_image);
            item_goodFriend_name = view.findViewById(R.id.item_goodFriend_name);
            item_goodFriend_time = view.findViewById(R.id.item_goodFriend_time);
            item_goodFriend_more = view.findViewById(R.id.item_goodFriend_more);

            item_mine_list = view.findViewById(R.id.item_mine_list);

            item_photo_image = view.findViewById(R.id.item_photo_image);
        }

    }

    /**
     * 根据数据来源选择显示控件显隐
     *
     * @param bean
     * @param holder
     */
    private void showDifferentView(final ActionListBean bean, RecyclerAdapter.ChatHolder holder) {

        switch (type) {

            case PERSON_HOME:

                requestCode = RequestCodeModel.getInstence().TIME;
                shareObjectType = -1;
                holder.item_persionTimes_time.setVisibility(View.VISIBLE);
                String[] info_ = bean.getLastUpdateTime().split(" ")[0].split("-");
                holder.item_persionTimes_days.setText(info_[2]);
                holder.item_persionTimes_months.setText(info_[1] + "月");

                break;

            case PERSON_ESSAY:

                requestCode = RequestCodeModel.getInstence().ESSAY;
                shareObjectType = 2;
                holder.item_essay_layout.setVisibility(View.VISIBLE);
                holder.item_essay_time.setText(activity.getResources().getStringArray(R.array.privacyStatu)[bean.getPrivacyStatus()] + "-" + TimeUtils.exchangeTime(bean.getCreateTime()));

                break;

            case PERSON_LOG:

                requestCode = RequestCodeModel.getInstence().LOG_P;
                shareObjectType = 0;
                holder.item_blog_layout.setVisibility(View.VISIBLE);
                holder.item_blog_image.setVisibility(View.GONE);
                holder.item_blog_name.setVisibility(View.GONE);
                holder.item_blog_time.setText(TimeUtils.exchangeTime(bean.getCreateTime()) + "-" + bean.getOwnerCategoryName());
                holder.item_commen_title_left.setText(bean.getTitle());

                break;

            case CLASS_LOG:

                requestCode = RequestCodeModel.getInstence().LOG_C;
                shareObjectType = 1;
                holder.item_blog_layout.setVisibility(View.VISIBLE);
                holder.item_blog_image.loadBuddyAvatar(bean.getUserAvatar());
                holder.item_blog_name.setText(bean.getUserName());
                holder.item_blog_time.setText(TimeUtils.exchangeTime(bean.getCreateTime()) + "-" + bean.getOwnerCategoryName());
                holder.item_commen_title_left.setText(bean.getTitle());

                break;

            case HI_CLASS_ACTION:

                requestCode = RequestCodeModel.getInstence().CLASS_ACTION_H;
                shareObjectType = -1;
                holder.item_commen_location_layout.setVisibility(View.VISIBLE);
                holder.item_commen_location_image.setImageResource(R.mipmap.classroom);
                holder.item_goodFriend_name.setText(bean.getUserName());
                holder.item_goodFriend_layout.setVisibility(View.VISIBLE);
                holder.item_goodFriend_image.loadBuddyAvatar(bean.getUserAvatar());
                holder.item_goodFriend_time.setText(bean.getActionInfo() + " " + TimeUtils.exchangeTime(bean.getLastUpdateTime()));
                holder.item_goodFriend_more.setVisibility(SystemUtil.isOneselfIn(bean.getUserId()) ? View.GONE : View.VISIBLE);
                holder.item_commen_location_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        InfoHelper.gotoClassHome((BaseActivity) activity, bean.getOwnerId(), false);
                    }
                });

                break;

            case CLASS_HOME:

                requestCode = RequestCodeModel.getInstence().CLASS_ACTION_C;
                shareObjectType = -1;
                holder.item_commen_location_layout.setVisibility(View.VISIBLE);
                holder.item_commen_location_image.setImageResource(R.mipmap.classroom);
                holder.item_goodFriend_name.setText(bean.getUserName());
                holder.item_goodFriend_layout.setVisibility(View.VISIBLE);
                holder.item_goodFriend_image.loadBuddyAvatar(bean.getUserAvatar());
                holder.item_goodFriend_time.setText(bean.getActionInfo() + " " + TimeUtils.exchangeTime(bean.getLastUpdateTime()));
                holder.item_goodFriend_more.setVisibility(SystemUtil.isOneselfIn(bean.getUserId()) ? View.GONE : View.VISIBLE);
                holder.item_commen_location_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        InfoHelper.gotoClassHome((BaseActivity) activity, bean.getOwnerId(), false);
                    }
                });

                break;

            case HI_FRIEND_ACTION:

                requestCode = RequestCodeModel.getInstence().GOODFRIENDA_ACTION;
                shareObjectType = -1;
                holder.item_goodFriend_name.setText(bean.getOwnerName());
                holder.item_goodFriend_layout.setVisibility(View.VISIBLE);
                holder.item_goodFriend_image.loadBuddyAvatar(bean.getUserAvatar());
                holder.item_goodFriend_time.setText(bean.getActionInfo() + " " + TimeUtils.exchangeTime(bean.getLastUpdateTime()));
                holder.item_goodFriend_more.setVisibility(SystemUtil.isOneselfIn(bean.getOwnerId()) ? View.GONE : View.VISIBLE);

                break;

            case HI_ABOUTMINE:

                shareObjectType = -1;
                holder.item_commen_foot_down_layout.setVisibility(View.VISIBLE);
                holder.item_commen_likes.setVisibility(View.GONE);
                holder.item_mine_list.setVisibility(View.VISIBLE);
                holder.item_goodFriend_layout.setVisibility(View.VISIBLE);
                holder.item_goodFriend_image.loadBuddyAvatar(bean.getUserAvatar());
                holder.item_goodFriend_name.setText(bean.getUserName());
                holder.item_goodFriend_time.setText(bean.getActionInfo() + " " + changeTimeFromat(bean.getLastUpdateTime()));
                holder.item_goodFriend_more.setVisibility(SystemUtil.isOneselfIn(bean.getOwnerId()) ? View.GONE : View.VISIBLE);
                holder.item_goodFriend_more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initPopWindow(v, bean);
                    }
                });
//                MyCommentAdpater myCommentAdpater = new MyCommentAdpater(bean.getItemsBeen(), activity);
//                holder.item_mine_list.setAdapter(myCommentAdpater);

                break;

            case PHOTO:
                holder.item_photo_image.setVisibility(View.VISIBLE);
                setViewSize(holder.item_photo_image, SystemUtil.getScreenWind(), SystemUtil.getScreenWind() * 3 / 4);
                CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(activity, bean.getUserAvatar(),
                        holder.item_photo_image, PhotoManager.getInstance().getDefaultPlaceholderResource());
                break;
        }

    }

    /**
     * 点击头像跳转个人主页
     */
    private void gotoHomePage(RecyclerAdapter.ChatHolder holder, final ActionListBean bean) {
        holder.item_goodFriend_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InfoHelper.gotoPersonHome((BaseActivity) activity, bean.getUserId(), false);
            }
        });
    }

    /**
     * 根据动态类型选择显示控件显隐
     *
     * @param bean
     * @param holder
     */
    private void showDifferentType(final ActionListBean bean, RecyclerAdapter.ChatHolder holder) {
        userId = bean.getSourceId();
        switch (bean.getActivityItemKey()) {

            case "UploadPhoto":

                reportType = ReportType.ALBUM;

                holder.item_commen_picture.setVisibility(View.VISIBLE);
                holder.item_commen_title_left.setVisibility(View.VISIBLE);
                holder.item_commen_title_right.setVisibility(View.VISIBLE);
                showPictures(holder.item_commen_picture, bean);
                holder.item_commen_title_left.setText("上传" + bean.getImages().size() + "张照片到:");
                holder.item_commen_title_right.setText(bean.getSummary());
                holder.item_commen_title_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gotoAlbum(bean);
                    }
                });

                break;

            case "CreateBlog":

                reportType = ReportType.ESSAY;
                if (!TextUtils.isEmpty(bean.getCover())) {
                    holder.item_persionTimes_cover.setVisibility(View.VISIBLE);
                    CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(activity, bean.getCover(),
                            holder.item_persionTimes_cover, PhotoManager.getInstance().getDefaultPlaceholderResource());
                }
                holder.item_commen_content.setMaxLines(4);
                holder.item_blog_title.setVisibility(View.VISIBLE);
                holder.item_blog_title.setText(bean.getTitle());
                holder.item_commen_content.setVisibility(View.VISIBLE);
                holder.item_commen_content.setText(EmotionUtils.convertNormalStringToSpannableString(bean.getSummary()));

                break;

            case "CreateMicroBlog":

                reportType = ReportType.ESSAY;

                holder.item_commen_content.setVisibility(View.VISIBLE);

                if (!TextUtils.isEmpty(bean.getCover())) {
                    holder.item_persionTimes_cover.setVisibility(View.VISIBLE);
                    holder.item_persionTimes_play.setVisibility(View.VISIBLE);
                    CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(activity, bean.getCover(),
                            holder.item_persionTimes_cover, PhotoManager.getInstance().getDefaultPlaceholderResource());
                }

                holder.item_commen_content.setMaxEms(140);
                holder.item_commen_content.setText(EmotionUtils.convertNormalStringToSpannableString(bean.getSummary()));
                holder.item_essay_delet.setVisibility(SystemUtil.isOneselfIn(bean.getOwnerId()) ? View.VISIBLE : View.GONE);

                if (bean.getImages() != null && bean.getImages().size() > 0) {
                    holder.item_commen_picture.setVisibility(View.VISIBLE);
                    showPictures(holder.item_commen_picture, bean);
                }
                break;

        }
    }

    private ActionListBean bean;
    private ActionMorePopWindow popWindow;

    /**
     * item中向下箭头的弹窗
     *
     * @param v
     * @param bean_
     */
    private void initPopWindow(View v, ActionListBean bean_) {

//        popWindow = new ActionMorePopWindow((BaseActivity) activity, bean_, shareObjectType, this);
//        popWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
//        bean = bean_;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //举报
            case R.id.relative_report:
                //调到举报页面
                ReportActivity.start(activity, userId, reportType);
                popWindow.dismiss();
                break;
        }
    }

    /**
     * 跳转相册
     */
    private void gotoAlbum(final ActionListBean bean) {
        Map<String, Object> map = new TreeMap<>();
        map.put("albumId", bean.getSourceId());
        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.getAlbumInfo, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                AlbumInfoModel model = JSON.parseObject(result, AlbumInfoModel.class);
                model.setOwnerId(bean.getOwnerId());
                AlbumDetailActivity.start(activity, model, 0, ActionTypeEnum.OWNER);
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    /**
     * 计算高度
     *
     * @return
     */
    private void setGridSize(ActionListBean bean, RecyclerAdapter.ChatHolder holder) {

        if (bean.getImages() != null && bean.getImages().size() > 0) {
            holder.item_commen_picture.setVisibility(View.VISIBLE);

            int W;
            if (type == ItemType.PERSON_HOME) {
                W = SystemUtil.getScreenWind() / 5 * 4;
            } else {
                W = SystemUtil.getScreenWind();
            }
            int h = 0;
            int s = 3;
            switch (bean.getImages().size()) {
                case 1:
                    h = (int) Math.ceil(W * s / 4);
                    holder.item_commen_picture.setNumColumns(1);
                    break;
                case 2:
                    h = (int) Math.ceil(W / 2 * s / 4);
                    holder.item_commen_picture.setNumColumns(2);
                    break;
                case 3:
                    h = (int) Math.ceil(W / 3 * s / 4);
                    holder.item_commen_picture.setNumColumns(3);
                    break;
                case 4:
                    h = (int) Math.ceil(W * s / 4);
                    holder.item_commen_picture.setNumColumns(2);
                    break;

                case 5:
                case 6:
                    h = (int) Math.ceil(W / 3 * 2 * s / 4);
                    holder.item_commen_picture.setNumColumns(3);
                    break;

                case 7:
                case 8:
                case 9:
                    h = (int) Math.ceil(W * 3 / 4);
                    holder.item_commen_picture.setNumColumns(3);
                    break;
            }
            setViewSize(holder.item_commen_picture, W, h);
        }
    }

    /**
     * 动态设置图片尺寸
     *
     * @param view
     * @param w
     * @param h
     */
    private void setViewSize(View view, int w, int h) {
        RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) view.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
        linearParams.width = w;
        linearParams.height = h;
        view.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
    }

    /**
     * 转化时间格式
     *
     * @param time
     * @return
     */
    private String changeTimeFromat(String time) {

        if (TextUtils.isEmpty(time))
            return "";
        String[] time_01 = time.split(" ");
        String[] time_02 = time_01[0].split("-");
        String[] time_03 = time_01[1].split(":");
        return time_02[1] + "月" + time_02[2] + "日 " + time_03[0] + ":" + time_03[1];
    }

    /**
     * 显示点赞的好友的头像
     */
    private void showLikeImage(RecyclerAdapter.ChatHolder holder, ActionListBean bean) {
        if (bean.getPraiseUsers() == null || bean.getPraiseUsers().size() == 0 || type.equals(ItemType.HI_ABOUTMINE)) {
            return;
        } else {
            holder.item_commen_foot_down_layout.setVisibility(View.VISIBLE);
//            GridViewImageAdapter adapter_image = new GridViewImageAdapter(activity, bean);
//            holder.item_commen_likes.setAdapter(adapter_image);
        }
    }

    /**
     * 显示单张图片宽度
     */
    private void showPictures(GridView view, ActionListBean bean) {
        int size;
        if (type == ItemType.PERSON_HOME) {
            size = SystemUtil.getScreenWind() / 5 * 4;
        } else {
            size = SystemUtil.getScreenWind();
        }

        int showSize = 0;
        switch (bean.getImages().size()) {
            case 1:
                showSize = size;
                break;
            case 2:
            case 4:
                showSize = size / 2;
                break;
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                showSize = size / 3;
                break;
        }

        GridViewPictureAdapter adapter_picture = new GridViewPictureAdapter(activity, bean, showSize);
        view.setAdapter(adapter_picture);
    }

    /**
     * 设置点赞背景图标
     *
     * @param holder
     * @param flag
     */
    private void setPraiseBg(RecyclerAdapter.ChatHolder holder, boolean flag) {
        if (flag) {
            holder.item_commen_likeNum_image.setImageResource(R.mipmap.like_true);
        } else {
            holder.item_commen_likeNum_image.setImageResource(R.mipmap.like_false);
        }
    }

    /**
     * 设置评论背景图标
     *
     * @param holder
     * @param flag
     */
    private void setCommentBg(RecyclerAdapter.ChatHolder holder, boolean flag) {
        if (flag) {
            holder.item_commen_commentNum_image.setImageResource(R.mipmap.discuss_true);
        } else {
            holder.item_commen_commentNum_image.setImageResource(R.mipmap.discuss_false);
        }
    }

    /**
     * 点赞操作, , bean.getSourceId()
     */
    private void doPraise(final RecyclerAdapter.ChatHolder holder, final ActionListBean bean, final int position) {

        holder.item_commen_likeNum
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(final View v) {

                                if (flag_onclick)
                                    return;

                                flag_onclick = true;
                                final boolean flag_ = bean.getIsPraised();

                                if (type == ItemType.PHOTO) {
                                    Map<String, Object> map = new TreeMap<>();
                                    map.put("doOrUndo", !flag_);
                                    map.put("relatedMainId", bean.getSourceId());
                                    map.put("objectType", "5");
                                    map.put("objectId", bean.getSourceId());

                                    HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.praise_url, new HttpRequestCallBack() {
                                        @Override
                                        public void onSuccess(String result) {
                                            int num = bean.getPraiseCount();
                                            bean.setIsPraised(!flag_);
                                            bean.setPraiseCount(flag_ ? num - 1 : num + 1);
                                            datas.remove(position);
                                            datas.add(position, bean);
                                            notifyDataSetChanged();

                                            if (flag) {
                                                setPraiseBg(holder, !flag_);
                                                int num_show = flag_ ? (num - 1) : (num + 1);
                                                holder.item_commen_likeNum_text.setText(num_show > 0 ? " " + num_show : "");

                                            }
                                            flag_onclick = false;
                                        }

                                        @Override
                                        public void onFailure(String msg) {
                                        }
                                    },LoadingType.NONE);
                                } else {
                                    Map<String, Object> map = new TreeMap<>();
                                    map.put("DoOrUndo", !flag_);
                                    map.put("relatedMainId", bean.getSourceId());
                                    int objectType = 0;
                                    switch (bean.getActivityItemKey()) {
                                        case "UploadPhoto":
                                            objectType = 1;
                                            break;
                                        case "CreateBlog":
                                            objectType = 2;
                                            break;
                                        case "CreateMicroBlog":
                                            objectType = 3;
                                            break;
                                    }
                                    map.put("objectType", objectType);
                                    map.put("objectId", bean.getActivityItemKey().equals("UploadPhoto") ? bean.getActId() : bean.getSourceId());

                                    HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.praise_url, new HttpRequestCallBack() {
                                        @Override
                                        public void onSuccess(String result) {

                                            int objectType = 100;
                                            switch (bean.getActivityItemKey()) {
                                                case "UploadPhoto":
                                                    objectType = 1;
                                                    break;
                                                case "CreateBlog":
                                                    objectType = 2;
                                                    break;

                                                case "CreateMicroBlog":
                                                    objectType = 3;
                                                    break;
                                            }

                                            Map<String, Object> map = new TreeMap<>();
                                            map.put("objectId", bean.getActivityItemKey().equals("UploadPhoto") ? bean.getActId() : bean.getSourceId());
                                            map.put("objectType", objectType + "");

                                            HiStudentHttpUtils.getDataByOKHttp((BaseActivity) activity, map, HistudentUrl.getPraiseDatil_url, new HttpRequestCallBack() {
                                                @Override
                                                public void onSuccess(String result) {

//                                                    ParserModel model = JSON.parseObject(result, ParserModel.class);
//                                                    List<ParserModel.FollowUsersBean> followUsersBeens = model.getFollowUsers();
//                                                    List<ActionListBean.PraiseUsersBean> praiseUsersBeens = new ArrayList<>();
//                                                    ActionListBean.PraiseUsersBean praiseUsersBean;
//                                                    for (int i = 0; i < followUsersBeens.size(); i++) {
//                                                        praiseUsersBean = new ActionListBean.PraiseUsersBean();
//                                                        praiseUsersBean.setName(followUsersBeens.get(i).getName());
//                                                        praiseUsersBean.setUserId(followUsersBeens.get(i).getUserId());
//                                                        praiseUsersBean.setAvatar(followUsersBeens.get(i).getAvatar());
//                                                        praiseUsersBeens.add(praiseUsersBean);
//                                                    }
//                                                    bean.setIsPraised(!flag_);
//                                                    bean.setPraiseUsers(praiseUsersBeens);
//                                                    bean.setPraiseCount(praiseUsersBeens.size());
//                                                    notifyDataSetChanged();
//
//                                                    if (flag) {
//                                                        int num = praiseUsersBeens.size();
//                                                        setPraiseBg(holder, !flag_);
//                                                        holder.item_commen_likeNum_text.setText(num > 0 ? num + "" : "");
//
//                                                    }
//                                                    flag_onclick = false;
                                                }

                                                @Override
                                                public void onFailure(String msg) {
                                                }
                                            });
                                        }

                                        @Override
                                        public void onFailure(String msg) {

                                        }
                                    }, LoadingType.NONE);
                                }
                            }
                        }

                );

    }

    private List<ActionListBean.PraiseUsersBean> onPraise(ActionListBean bean) {
        List<ActionListBean.PraiseUsersBean> usersBeans = new ArrayList<>();
        if (bean.getPraiseUsers() != null) {
            usersBeans.addAll(bean.getPraiseUsers());
        }
        ActionListBean.PraiseUsersBean usersBean = new ActionListBean.PraiseUsersBean();
        CurrentUserInfoModel model = HiCache.getInstance().getLoginUserInfo();
        usersBean.setUserId(model.getUserId());
        usersBean.setAvatar(model.getAvatar());
        usersBean.setName(model.getRealName());
        usersBeans.add(usersBean);
        return usersBeans;
    }

    private List<ActionListBean.PraiseUsersBean> offPraise(ActionListBean bean) {
        List<ActionListBean.PraiseUsersBean> usersBeans = bean.getPraiseUsers();
        CurrentUserInfoModel model = HiCache.getInstance().getLoginUserInfo();
        for (int i = 0; i < usersBeans.size(); i++) {
            if (usersBeans.get(i).getUserId().equals(model.getUserId())) {
                usersBeans.remove(i);
            }
        }
        return usersBeans;
    }

    /**
     * 点击评论图标操作
     */
    private void doComment(final RecyclerAdapter.ChatHolder holder, final ActionListBean bean, final int position) {

        if (flag) {
            //从评论过来复用的
            holder.item_top_null.setVisibility(View.GONE);
            holder.item_commen_foot_down_layout.setVisibility(View.GONE);
            holder.item_foot_line.setVisibility(View.VISIBLE);
        } else {
            holder.item_commen_commentNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (type != ItemType.HI_ABOUTMINE) {
//                        CommentActivity.startByCode(activity, type, 1, bean, requestCode);
                    } else {
                        Intent mIntent = new Intent(TheReceiverAction.HIDE_FLOOTACTIONBAR);
                        mIntent.putExtra("position01", position);
                        mIntent.putExtra("position02", -1);
                        activity.sendBroadcast(mIntent);
                    }
                }
            });
        }
    }

    /**
     * 点击和长按评论item
     *
     * @param holder
     * @param position
     */
    private boolean flag_comment = false;

    private void doCommentItem(final RecyclerAdapter.ChatHolder holder, final ActionListBean bean, final int position) {

        holder.item_mine_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position_, long id) {
                if (!flag_comment) {
                    Intent mIntent = new Intent(TheReceiverAction.HIDE_FLOOTACTIONBAR);
                    mIntent.putExtra("position01", position);
                    mIntent.putExtra("position02", position_);
                    activity.sendBroadcast(mIntent);
                } else {
                    flag_comment = false;
                }
            }
        });

        holder.item_mine_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position_, long l) {
                flag_comment = true;
                Intent mIntent = new Intent(TheReceiverAction.DELET_ABOUTMINE_COMMENT_01);
                int position_num = bean.getItemsBeen().size() - 1 - position_;
                mIntent.putExtra("commentId", bean.getItemsBeen().get(position_num).getCommentId());
                mIntent.putExtra("userId", bean.getItemsBeen().get(position_num).getUser().getUserId());
                mIntent.putExtra("content", bean.getItemsBeen().get(position_num).getContent());
                mIntent.putExtra("position01", position);
                mIntent.putExtra("position02", position_);
                activity.sendBroadcast(mIntent);
                return false;
            }
        });
    }

    /**
     * 删除随记
     */
    private void deletEssay(RecyclerAdapter.ChatHolder holder, final ActionListBean bean, final int position) {

        holder.item_essay_delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent mIntent = new Intent(TheReceiverAction.DELET_ESSAY);
//                mIntent.putExtra("microblogid", bean.getSourceId());
//                mIntent.putExtra("position", position);
//                activity.sendBroadcast(mIntent);
            }
        });

    }

    /**
     * 删除日志
     *
     * @param holder
     * @param bean
     */
    private void deletLog(RecyclerAdapter.ChatHolder holder, final ActionListBean bean, final int position) {

        if (bean.getOwnerList() != null && !bean.getOwnerList().contains(HiCache.getInstance().getLoginUserInfo().getUserId()))
            holder.item_blog_delete.setVisibility(View.GONE);

        holder.item_blog_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent mIntent = new Intent(TheReceiverAction.DELET_BLOG);
//                mIntent.putExtra("microblogid", bean.getSourceId());
//                mIntent.putExtra("position", position);
//                activity.sendBroadcast(mIntent);
            }
        });
    }

    /**
     * item内容点击
     *
     * @param holder
     * @param bean
     */
    private void onClickItem(RecyclerAdapter.ChatHolder holder, final ActionListBean bean) {
        if (bean.getActivityItemKey().equals("CreateMicroBlog")) {
            holder.item_persionTimes_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DealActivity.start(activity, bean.getHtmlUrl(), "日志详情");
                }
            });
        } else if (bean.getActivityItemKey().equals("CreateBlog")) {
            holder.item_goodFriend_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DealActivity.start(activity, bean.getHtmlUrl() + "?accesstoken=" + HiCache.getInstance().getLoginUserInfo().getToken() + "&isApp=1", bean, type);
                }
            });

            holder.item_commen_content_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DealActivity.start(activity, bean.getHtmlUrl() + "?accesstoken=" + HiCache.getInstance().getLoginUserInfo().getToken() + "&isApp=1", bean, type);

                }
            });
        }
    }

    /**
     * 友盟分享
     */
    private void doShare(RecyclerAdapter.ChatHolder holder, final ActionListBean bean) {
        holder.item_commen_shareNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new SharePopWindow((BaseActivity) activity, bean.getActId(), shareObjectType).showAtLocation(v, Gravity.CENTER, 0, 0);
            }
        });
    }

    /**
     * 弹出下拉弹窗
     */
    private void doMore(RecyclerAdapter.ChatHolder holder, final ActionListBean bean) {
        holder.item_goodFriend_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopWindow(v, bean);
            }
        });
    }

    /**
     * textView加载Html
     *
     * @param textView
     * @param context
     * @param html
     */
    private void showHtml(final TextView textView, final Context context, final String html) {

        // 因为从网上下载图片是耗时操作 所以要开启新线程
        Thread t = new Thread(new Runnable() {
            Message msg = Message.obtain();

            @Override
            public void run() {
                // TODO Auto-generated method stub
                // bar.setVisibility(View.VISIBLE);
                /**
                 * 要实现图片的显示需要使用Html.fromHtml的一个重构方法：public static Spanned
                 * fromHtml (String source, Html.ImageGetterimageGetter,
                 * Html.TagHandler
                 * tagHandler)其中Html.ImageGetter是一个接口，我们要实现此接口，在它的getDrawable
                 * (String source)方法中返回图片的Drawable对象才可以。
                 */
                Html.ImageGetter imageGetter = new Html.ImageGetter() {
                    @Override
                    public Drawable getDrawable(String source) {
                        // TODO Auto-generated method stub
                        URL url;
                        Drawable drawable = null;
                        try {
                            url = new URL(source);
                            drawable = Drawable.createFromStream(
                                    url.openStream(), null);
                            drawable.setBounds(0, 0,
                                    drawable.getIntrinsicWidth(),
                                    drawable.getIntrinsicHeight());
                        } catch (MalformedURLException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        return drawable;
                    }
                };
                final CharSequence test = Html.fromHtml(html, imageGetter, null);

                ((Activity) context).runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // 在这里执行你要想的操作 比如直接在这里更新ui或者调用回调在 在回调中更新ui
                        textView.setText(test);
                    }
                });
            }
        });
        t.start();

    }

}
