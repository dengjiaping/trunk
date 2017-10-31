package com.histudent.jwsoft.histudent.body.homepage.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.homepage.activity.TopicActivity;
import com.histudent.jwsoft.histudent.body.homepage.helper.HomePageHelper;
import com.histudent.jwsoft.histudent.body.homepage.holder.AlbumViewHolder;
import com.histudent.jwsoft.histudent.body.homepage.holder.EssayViewHolder;
import com.histudent.jwsoft.histudent.body.homepage.holder.LogViewHolder;
import com.histudent.jwsoft.histudent.commen.activity.MyWebActivity;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.bean.AtUserModel;
import com.histudent.jwsoft.histudent.commen.bean.TopicModel;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.RichTextView;
import com.histudent.jwsoft.histudent.comment2.utils.TimeUtils;
import com.histudent.jwsoft.histudent.entity.ImageAttrEntity;
import com.histudent.jwsoft.histudent.info.persioninfo.activity.PersonCenterActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuguiyu-pc on 2017/4/20.
 */

public class HomePageAdapter extends BaseAdapter {
    private Context mContext;
    private List<ActionListBean> beens;

    public HomePageAdapter(Context context, List<ActionListBean> beens) {
        this.mContext = context;
        this.beens = beens;
    }

    @Override
    public int getCount() {
        return beens.size();
    }

    @Override
    public Object getItem(int i) {
        return beens.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AlbumViewHolder albumViewHolder = null;
        EssayViewHolder essayViewHolder = null;
        LogViewHolder logViewHolder = null;
        ActionListBean bean = beens.get(i);

        switch (bean.getActivityItemKey()) {
            case "UploadPhoto":
                return getAlbumView(albumViewHolder, viewGroup, bean);
            case "CreateMicroBlog":
                return getEssayView(essayViewHolder, viewGroup, bean);
            case "CreateBlog":
                return getLogView(logViewHolder, viewGroup, bean);
        }

        return null;
    }

    /**
     * 绑定相册数据
     *
     * @param albumViewHolder
     * @param viewGroup
     * @return
     */
    private View getAlbumView(AlbumViewHolder albumViewHolder, ViewGroup viewGroup, ActionListBean bean) {
        View view = null;
        if (albumViewHolder == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.fragment_homepage_action_album_item, viewGroup, false);
            albumViewHolder = new AlbumViewHolder(view);
            view.setTag(albumViewHolder);
        } else {
            albumViewHolder = (AlbumViewHolder) view.getTag();
        }

        String title = bean.getTitle();
        if (TextUtils.isEmpty(title)) {
            albumViewHolder.title.setVisibility(View.GONE);
        } else {
            albumViewHolder.title.setVisibility(View.VISIBLE);
            albumViewHolder.title.setText(title);
        }

        albumViewHolder.name.setText(bean.getUserName());
        List<ImageAttrEntity> imageUrls = new ArrayList<>();
        List<HomePageHelper.ImageBeans> imageBeanses = new ArrayList<>();
        for (int i = 0; i < bean.getImages().size(); i++) {
            ImageAttrEntity imageUrl = new ImageAttrEntity();
            imageUrl.setThumbnailUrl(bean.getImages().get(i).getThumbnailUrl());
            imageUrl.setBigSizeUrl(bean.getImages().get(i).getBigSizeUrl());
            imageUrls.add(imageUrl);
        }
        HomePageHelper.getIntence().showPictures(mContext, albumViewHolder.picture_list, imageUrls);

        albumViewHolder.className.setText(bean.getLocation());
        albumViewHolder.time.setText(TimeUtils.exchangeTime(bean.getLastUpdateTime()));

        return view;
    }

    /**
     * 绑定随记数据
     *
     * @param essayViewHolder
     * @param viewGroup
     * @return
     */
    private View getEssayView(EssayViewHolder essayViewHolder, ViewGroup viewGroup, final ActionListBean bean) {
        View view = null;
        if (essayViewHolder == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.fragment_homepage_action_essay_item, viewGroup, false);
            essayViewHolder = new EssayViewHolder(view);
            view.setTag(essayViewHolder);
        } else {
            essayViewHolder = (EssayViewHolder) view.getTag();
        }

        List<ActionListBean.ImagesBean> imagesBean = bean.getImages();

        if (imagesBean != null) {

            if (bean.getMultiMediaType() == 0) {//照片
                if (imagesBean.size() > 0) {
                    essayViewHolder.action_essay_layout.setVisibility(View.VISIBLE);
                    essayViewHolder.video_play.setVisibility(View.GONE);
                    essayViewHolder.action_essay_images.setVisibility(View.VISIBLE);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) essayViewHolder.action_essay_layout.getLayoutParams();
                    layoutParams.height = (SystemUtil.getScreenWind() - SystemUtil.dip2px(mContext, 24)) / 2;
                    essayViewHolder.action_essay_layout.setLayoutParams(layoutParams);
                    CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(mContext, imagesBean.get(0).getBigSizeUrl(), essayViewHolder.action_essay_cover);
                    if (imagesBean.size() > 1) {
                        essayViewHolder.action_essay_images.setVisibility(View.VISIBLE);
                        essayViewHolder.action_essay_images.setText(imagesBean.size() + "张");
                    } else {
                        essayViewHolder.action_essay_images.setVisibility(View.GONE);
                    }
                } else {
                    essayViewHolder.action_essay_layout.setVisibility(View.GONE);
                }
            } else if (bean.getMultiMediaType() == 1) {//视频
                essayViewHolder.action_essay_layout.setVisibility(View.VISIBLE);
                essayViewHolder.video_play.setVisibility(View.VISIBLE);
                essayViewHolder.action_essay_images.setVisibility(View.GONE);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) essayViewHolder.action_essay_layout.getLayoutParams();
                layoutParams.height = (SystemUtil.getScreenWind() - SystemUtil.dip2px(mContext, 24)) * 3 / 4;
                essayViewHolder.action_essay_layout.setLayoutParams(layoutParams);
                CommonGlideImageLoader.getInstance()
                        .displayNetImage(mContext, bean.getCover(), essayViewHolder.action_essay_cover);
                essayViewHolder.video_play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!TextUtils.isEmpty(bean.getVideoId())) {
                            MyWebActivity.startNoTitle(mContext, HistudentUrl.playvideo + bean.getVideoId());
                        } else {
                            MyWebActivity.start(mContext, bean.getHtmlUrl());
                        }
                    }
                });
            }

        } else {
            essayViewHolder.action_essay_layout.setVisibility(View.GONE);
        }

        String content = bean.getSummary();
        if (TextUtils.isEmpty(content)) {
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
            essayViewHolder.content.setText(content);
            essayViewHolder.content.setListener(new RichTextView.RichTextClick() {
                @Override
                public void clickTopic(RichTextView.Topic topic) {
                    TopicActivity.start(mContext, topic.hash);
                }

                @Override
                public void clickUser(RichTextView.User user) {
                    PersonCenterActivity.start(mContext, user.id);
                }
            });
        }

        essayViewHolder.name.setText(bean.getUserName());
        essayViewHolder.className.setText(bean.getLocation());
        essayViewHolder.time.setText(TimeUtils.exchangeTime(bean.getLastUpdateTime()));
        return view;
    }

    /**
     * 绑定日志数据
     *
     * @param logViewHolder
     * @param viewGroup
     * @return
     */
    private View getLogView(LogViewHolder logViewHolder, ViewGroup viewGroup, ActionListBean bean) {
        View view = null;
        if (logViewHolder == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.fragment_homepage_action_log_item, viewGroup, false);
            logViewHolder = new LogViewHolder(view);
            view.setTag(logViewHolder);
        } else {
            logViewHolder = (LogViewHolder) view.getTag();
        }
        logViewHolder.title.setText(bean.getTitle());
        logViewHolder.content.setText(bean.getSummary());

        String coverPath = bean.getCover();
        if (TextUtils.isEmpty(coverPath)) {
            logViewHolder.action_log_layout.setVisibility(View.GONE);
        } else {
            logViewHolder.action_log_layout.setVisibility(View.VISIBLE);
            CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(mContext, coverPath, logViewHolder.cover);


        }
        if (bean.getImageCount() > 1) {
            logViewHolder.action_log_images.setVisibility(View.VISIBLE);
            logViewHolder.action_log_images.setText(bean.getImageCount() + "张");
        } else {
            logViewHolder.action_log_images.setVisibility(View.GONE);
        }
        logViewHolder.name.setText(bean.getUserName());
        logViewHolder.className.setText(bean.getLocation());
        logViewHolder.time.setText(TimeUtils.exchangeTime(bean.getLastUpdateTime()));
        return view;
    }

}
