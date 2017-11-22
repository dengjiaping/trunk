package com.histudent.jwsoft.histudent.view.adapter.main;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.HTMainActivity;
import com.histudent.jwsoft.histudent.body.homepage.activity.TopicActivity;
import com.histudent.jwsoft.histudent.body.homepage.helper.HomePageHelper;
import com.histudent.jwsoft.histudent.body.myclass.activity.ClassCircleActivity;
import com.histudent.jwsoft.histudent.commen.activity.HTWebActivity;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.commen.view.MyHorizontalScrollView;
import com.histudent.jwsoft.histudent.commen.view.RichTextView;
import com.histudent.jwsoft.histudent.comment2.utils.TimeUtils;
import com.histudent.jwsoft.histudent.info.persioninfo.activity.PersonCenterActivity;
import com.histudent.jwsoft.histudent.model.bean.main.HomeEntity;
import com.histudent.jwsoft.histudent.model.bean.main.HomeItemType;
import com.histudent.jwsoft.histudent.model.bean.main.HomePageEntity;
import com.histudent.jwsoft.histudent.model.entity.ImageAttrEntity;
import com.histudent.jwsoft.histudent.view.widget.AutoImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * Created by lichaojie on 2017/11/13.
 * desc:
 */

public class HomeAdapter extends BaseMultiItemQuickAdapter<HomeEntity, BaseViewHolder> {


    private ViewPager mBannerViewPager;
    private boolean mIsInitBanner = false;//确保只初始化banner一次 防止重复加载banner
    private Disposable mDisposable;

    public static final HomeAdapter create(List<HomeEntity> data) {
        return new HomeAdapter(data);
    }

    private HomeAdapter(List<HomeEntity> data) {
        super(data);
        addItemType(HomeItemType.BANNER, R.layout.item_fragment_home_banner);
        addItemType(HomeItemType.SUB_CLASS, R.layout.item_fragment_home_class);
        addItemType(HomeItemType.ACTIVE_USER, R.layout.item_fragment_home_active_user);
        addItemType(HomeItemType.HOT_TOPIC, R.layout.item_fragment_home_hot_topic);
        addItemType(HomeItemType.UPLOAD_PHOTO, R.layout.item_fragment_homepage_action_album);
        addItemType(HomeItemType.CREATE_MICROBLOG, R.layout.item_fragment_homepage_action_essay);
        addItemType(HomeItemType.CREATE_BLOG, R.layout.item_fragment_homepage_action_log);
    }

    public void reset() {
        mIsInitBanner = false;
        mCurrentPage = 0;
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }


    @Override
    protected void convert(BaseViewHolder helper, HomeEntity item) {
        final int type = helper.getItemViewType();
        switch (type) {
            case HomeItemType.BANNER:
                if (!mIsInitBanner) {
                    convertBanners(helper, item);
                    mIsInitBanner = true;
                }
                break;
            case HomeItemType.SUB_CLASS:
                convertClass(helper, item);
                break;
            case HomeItemType.ACTIVE_USER:
                convertActiveUser(helper, item);
                break;
            case HomeItemType.HOT_TOPIC:
                convertHotTopic(helper, item);
                break;
            case HomeItemType.UPLOAD_PHOTO:
                convertUploadPhoto(helper, item);
                break;
            case HomeItemType.CREATE_MICROBLOG:
                convertEssay(helper, item);
                break;
            case HomeItemType.CREATE_BLOG:
                convertLog(helper, item);
                break;
            default:
                break;
        }
    }


    /**
     * 推荐动态-日志
     *
     * @param helper
     * @param item
     */
    private void convertLog(BaseViewHolder helper, HomeEntity item) {

        final HomePageEntity.RecommendDynamicEntity.SubRecommendDynamicEntity actionLogEntity = item.getActionLogEntity();
        final TextView action_log_images = helper.getView(R.id.action_log_images);
        final RelativeLayout action_log_layout = helper.getView(R.id.action_log_layout);
        final TextView title = helper.getView(R.id.action_log_title);
        final TextView content = helper.getView(R.id.action_log_content);
        final ImageView cover = helper.getView(R.id.action_log_cover);
        final TextView name = helper.getView(R.id.action_tuijian_name);
        final TextView className = helper.getView(R.id.action_tuijian_class);
        final TextView time = helper.getView(R.id.action_tuijian_time);

        title.setText(actionLogEntity.getTitle());
        content.setText(actionLogEntity.getSummary());

        String coverPath = actionLogEntity.getCover();
        if (TextUtils.isEmpty(coverPath)) {
            action_log_layout.setVisibility(View.GONE);
        } else {
            action_log_layout.setVisibility(View.VISIBLE);
            CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(mContext, coverPath, cover);
        }
        if (actionLogEntity.getImageCount() > 1) {
            action_log_images.setVisibility(View.VISIBLE);
            action_log_images.setText(actionLogEntity.getImageCount() + "张");
        } else {
            action_log_images.setVisibility(View.GONE);
        }
        name.setText(actionLogEntity.getUserName());
        className.setText(actionLogEntity.getLocation());
        time.setText(TimeUtils.exchangeTime(actionLogEntity.getLastUpdateTime()));
    }


    /**
     * 推荐动态-随记
     *
     * @param helper
     * @param item
     */
    private void convertEssay(BaseViewHolder helper, HomeEntity item) {
        final HomePageEntity.RecommendDynamicEntity.SubRecommendDynamicEntity essayEntity = item.getEssayEntity();
        final List<HomePageEntity.RecommendDynamicEntity.SubRecommendDynamicEntity.ImageEntity> imagesBean = essayEntity.getImages();

        final RelativeLayout action_essay_layout = helper.getView(R.id.action_essay_layout);
        final IconView video_play = helper.getView(R.id.video_play);
        final TextView action_essay_images = helper.getView(R.id.action_essay_images);
        final ImageView action_essay_cover = helper.getView(R.id.action_essay_cover);
        final RichTextView richTextView = helper.getView(R.id.action_essay_content);

        final TextView name = helper.getView(R.id.action_tuijian_name);
        final TextView className = helper.getView(R.id.action_tuijian_class);
        final TextView time = helper.getView(R.id.action_tuijian_time);


        if (imagesBean != null) {
            if (essayEntity.getMultiMediaType() == 0) {//照片
                if (imagesBean.size() > 0) {
                    action_essay_layout.setVisibility(View.VISIBLE);
                    video_play.setVisibility(View.GONE);
                    action_essay_images.setVisibility(View.VISIBLE);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) action_essay_layout.getLayoutParams();
                    layoutParams.height = (SystemUtil.getScreenWind() - SystemUtil.dip2px(mContext, 24)) / 2;
                    action_essay_layout.setLayoutParams(layoutParams);
                    CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(mContext, imagesBean.get(0).getBigSizeUrl(), action_essay_cover);
                    if (imagesBean.size() > 1) {
                        action_essay_images.setVisibility(View.VISIBLE);
                        action_essay_images.setText(imagesBean.size() + "张");
                    } else {
                        action_essay_images.setVisibility(View.GONE);
                    }
                } else {
                    action_essay_layout.setVisibility(View.GONE);
                }
            } else if (essayEntity.getMultiMediaType() == 1) {//视频
                action_essay_layout.setVisibility(View.VISIBLE);
                video_play.setVisibility(View.VISIBLE);
                action_essay_images.setVisibility(View.GONE);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) action_essay_layout.getLayoutParams();
                layoutParams.height = (SystemUtil.getScreenWind() - SystemUtil.dip2px(mContext, 24)) * 3 / 4;
                action_essay_layout.setLayoutParams(layoutParams);
                CommonGlideImageLoader.getInstance()
                        .displayNetImage(mContext, essayEntity.getCover(), action_essay_cover);
                video_play.setOnClickListener((View view) -> {
                    if (!TextUtils.isEmpty(essayEntity.getVideoId())) {
                        HTWebActivity.startNoTitle(mContext, HistudentUrl.playvideo + essayEntity.getVideoId());
                    } else {
                        HTWebActivity.start(mContext, essayEntity.getHtmlUrl());
                    }
                });
            }

        } else {
            action_essay_layout.setVisibility(View.GONE);
        }

        String content = essayEntity.getSummary();
        if (TextUtils.isEmpty(content)) {
            richTextView.setVisibility(View.GONE);
        } else {
            richTextView.setVisibility(View.VISIBLE);
            if (essayEntity.getTopicList() != null && essayEntity.getTopicList().size() > 0) {
                final List<HomePageEntity.RecommendDynamicEntity.SubRecommendDynamicEntity.TopicModel> topicList = essayEntity.getTopicList();
                for (HomePageEntity.RecommendDynamicEntity.SubRecommendDynamicEntity.TopicModel topicModel : topicList) {
                    richTextView.addTopic(topicModel);
                }
            }
            if (essayEntity.getAtUserList() != null && essayEntity.getAtUserList().size() > 0) {
                final List<HomePageEntity.RecommendDynamicEntity.SubRecommendDynamicEntity.AtUserModel> atUserList = essayEntity.getAtUserList();
                for (HomePageEntity.RecommendDynamicEntity.SubRecommendDynamicEntity.AtUserModel atUserModel : atUserList) {
                    richTextView.addUser(atUserModel);
                }
            }
            richTextView.setText(content);
            richTextView.setListener(new RichTextView.RichTextClick() {
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

        name.setText(essayEntity.getUserName());
        className.setText(essayEntity.getLocation());
        time.setText(TimeUtils.exchangeTime(essayEntity.getLastUpdateTime()));
    }

    /**
     * 推荐动态-上传图片列表
     *
     * @param helper
     * @param item
     */
    private void convertUploadPhoto(BaseViewHolder helper, HomeEntity item) {
        final HomePageEntity.RecommendDynamicEntity.SubRecommendDynamicEntity photoEntity = item.getUploadPhotoEntity();
        final String title = photoEntity.getTitle();

        final TextView titleView = helper.getView(R.id.action_album_title);
        final TextView userNameView = helper.getView(R.id.action_tuijian_name);
        final TextView classView = helper.getView(R.id.action_tuijian_class);
        final TextView timeView = helper.getView(R.id.action_tuijian_time);
        final GridView picture_list = helper.getView(R.id.action_album_list);

        if (TextUtils.isEmpty(title)) {
            titleView.setVisibility(View.GONE);
        } else {
            titleView.setVisibility(View.VISIBLE);
            titleView.setText(title);
        }
        userNameView.setText(photoEntity.getUserName());
        final List<ImageAttrEntity> imageUrls = new ArrayList<>();
        final int size = photoEntity.getImages().size();
        if (size > 0) {
            picture_list.setVisibility(View.VISIBLE);
            for (int i = 0; i < size; i++) {
                ImageAttrEntity imageUrl = new ImageAttrEntity();
                imageUrl.setThumbnailUrl(photoEntity.getImages().get(i).getThumbnailUrl());
                imageUrl.setBigSizeUrl(photoEntity.getImages().get(i).getBigSizeUrl());
                imageUrls.add(imageUrl);
            }
            HomePageHelper.getIntence().showPictures(mContext, picture_list, imageUrls);
        } else {
            picture_list.setVisibility(View.GONE);
        }

        classView.setText(photoEntity.getLocation());
        timeView.setText(TimeUtils.exchangeTime(photoEntity.getLastUpdateTime()));
    }


    /**
     * 热门话题
     *
     * @param helper
     * @param item
     */
    private void convertHotTopic(BaseViewHolder helper, HomeEntity item) {
        final List<HomePageEntity.HotTopicEntity.SubHotTopicEntity> subHotTopicEntities = item.getSubHotTopicEntities();
        final LinearLayout topicRootLayout = helper.getView(R.id.ll_root_hot_topic);
        final LinearLayout topicLayoutList = helper.getView(R.id.ll_hot_topic_layout);

        if (subHotTopicEntities == null || subHotTopicEntities.size() == 0) {
            topicRootLayout.setVisibility(View.GONE);
        } else {
            topicRootLayout.setVisibility(View.VISIBLE);
            topicLayoutList.removeAllViews();
            for (HomePageEntity.HotTopicEntity.SubHotTopicEntity model : subHotTopicEntities) {
                View layout = LayoutInflater.from(mContext).inflate(R.layout.fragment_homepage_hot_item, null);
                AutoImageView iv = layout.findViewById(R.id.hot_image);
                TextView className = layout.findViewById(R.id.topic_tag);

                CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(mContext, model.getFeaturedImage(),
                        iv, ContextCompat.getDrawable(mContext, R.drawable.bg_topic_default));

                className.setText(model.getTagName());
                layout.setTag(model);
                iv.setOnClickListener((View v) -> TopicActivity.start(mContext, model.getTagName()));
                topicLayoutList.addView(layout);
            }

        }
    }


    /**
     * 活跃用户
     *
     * @param helper
     * @param item
     */
    private void convertActiveUser(BaseViewHolder helper, HomeEntity item) {
        final List<HomePageEntity.ActiveUserEntity.SubActiveUserEntity> subActiveUserEntities = item.getSubActiveUserEntities();
        final LinearLayout rootLayout = helper.getView(R.id.ll_root_active_user_layout);
        final LinearLayout activeUserLayout = helper.getView(R.id.ll_active_user_list);

        if (subActiveUserEntities == null || subActiveUserEntities.size() == 0) {
            rootLayout.setVisibility(View.GONE);
        } else {
            rootLayout.setVisibility(View.VISIBLE);
            activeUserLayout.removeAllViews();
            View layout;
            AutoImageView headImageView;
            TextView className;
            View isTeacherIdentity;
            for (HomePageEntity.ActiveUserEntity.SubActiveUserEntity model : subActiveUserEntities) {
                layout = LayoutInflater.from(mContext).inflate(R.layout.fragment_homepage_fashion_item, null);
                final int padding = (int) ((SystemUtil.getScreenWind() - 5.5 * SystemUtil.dp2px(60)) / 10);
                layout.setPadding(padding, SystemUtil.dp2px(8), padding, SystemUtil.dp2px(8));
                headImageView = layout.findViewById(R.id.fashion_head);
                className = layout.findViewById(R.id.fashion_name);
                isTeacherIdentity = layout.findViewById(R.id.iv_teacher_identity);
                if (!TextUtils.isEmpty(model.getAvatar()))
                    CommonGlideImageLoader.getInstance().displayNetImageWithCircle(mContext, model.getAvatar(), headImageView);

                if (model.getUserType() == 3) {
                    isTeacherIdentity.setVisibility(View.VISIBLE);
                } else {
                    isTeacherIdentity.setVisibility(View.INVISIBLE);
                }
                className.setTextColor(mContext.getResources().getColor(R.color.text_black_h3));

                className.setText(model.getName());

                layout.setTag(model);
                layout.setOnClickListener((View view) -> PersonCenterActivity.start(mContext, model.getUserId()));
                headImageView.setOnClickListener((View view) -> PersonCenterActivity.start(mContext, model.getUserId()));
                activeUserLayout.addView(layout);
            }
        }

    }


    /**
     * 班级列表
     *
     * @param helper
     * @param item
     */
    private void convertClass(BaseViewHolder helper, HomeEntity item) {
        final MyHorizontalScrollView horizontalScrollView = helper.getView(R.id.hs_class_list_layout);
        final LinearLayout linearLayout00 = helper.getView(R.id.ll_class_list_00);
        final LinearLayout linearLayout01 = helper.getView(R.id.ll_class_list_01);
        final RadioGroup radioGroup = helper.getView(R.id.rg_class_indicator);

        final List<HomePageEntity.RecommendClassEntity.SubClassEntity> subClassEntities = item.getSubClassEntities();

        if (subClassEntities == null || subClassEntities.size() == 0) {
            horizontalScrollView.setVisibility(View.GONE);
        } else {
            horizontalScrollView.setVisibility(View.VISIBLE);
            int count = (int) Math.ceil(subClassEntities.size() / 4.0);

            //初始化底部标识
            radioGroup.removeAllViews();
            for (int i = 0; i < count; i++) {
                final RadioButton radioButton = new RadioButton(mContext);
                radioButton.setButtonDrawable(android.R.color.transparent);
                final RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(SystemUtil.dp2px(10), SystemUtil.dp2px(3));
                layoutParams.setMargins(SystemUtil.dp2px(3), SystemUtil.dp2px(3), SystemUtil.dp2px(3), SystemUtil.dp2px(3));
                radioButton.setLayoutParams(layoutParams);
                radioButton.setBackground(mContext.getResources().getDrawable(R.drawable.selector_class_indicator));
                radioGroup.addView(radioButton);
            }

            final LinearLayout.LayoutParams params00 = (LinearLayout.LayoutParams) linearLayout00.getLayoutParams();
            params00.width = SystemUtil.getScreenWind() * count;
            linearLayout00.setLayoutParams(params00);
            final LinearLayout.LayoutParams params11 = (LinearLayout.LayoutParams) linearLayout01.getLayoutParams();
            params11.width = SystemUtil.getScreenWind() * count;
            linearLayout01.setLayoutParams(params11);
            linearLayout00.removeAllViews();
            linearLayout01.removeAllViews();

            for (int i = 0; i < subClassEntities.size(); i++) {
                final HomePageEntity.RecommendClassEntity.SubClassEntity subClassEntity = subClassEntities.get(i);
                View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_homepage_class_item, null);

                LinearLayout layout = view.findViewById(R.id.classList_layout);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layout.getLayoutParams();
                params.width = (SystemUtil.getScreenWind() - SystemUtil.dp2px(48)) / 2;
                params.height = SystemUtil.dip2px(mContext, 70);
                params.setMargins(SystemUtil.dp2px(12), 0, SystemUtil.dp2px(12), 0);
                layout.setLayoutParams(params);

                ImageView headImageView = view.findViewById(R.id.class_logo);
                TextView class_school = view.findViewById(R.id.class_school);
                TextView class_grade = view.findViewById(R.id.class_grade);

                if (!TextUtils.isEmpty(subClassEntity.getLogo()))
                    CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(mContext, subClassEntity.getLogo(), headImageView);

                class_school.setText(subClassEntity.getSchoolName());
                class_grade.setText(subClassEntity.getGradeName() + subClassEntity.getName());

                view.setTag(subClassEntity);
                view.setOnClickListener((View v) -> ClassCircleActivity.start((Activity) mContext, subClassEntity.getClassesId()));

                if (i < (subClassEntities.size() + 1) / 2) {
                    linearLayout00.addView(view);
                } else {
                    linearLayout01.addView(view);
                }
            }
            if ((radioGroup.getChildAt(0)) != null) {
                ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);
            }
        }

        horizontalScrollView.setOnSmoothListener((int currentPage) -> {
            if ((radioGroup.getChildAt(currentPage)) != null) {
                ((RadioButton) radioGroup.getChildAt(currentPage)).setChecked(true);
            }
        });


    }

    /**
     * 轮播图处理
     *
     * @param helper
     * @param item
     */
    private void convertBanners(BaseViewHolder helper, HomeEntity item) {
        final List<ImageView> imageList = new ArrayList<>();
        final List<String> urlList = new ArrayList<>();
        mBannerViewPager = helper.getView(R.id.vp_viewpager);
        final LinearLayout homeSearchLayout = helper.getView(R.id.ll_home_search_layout);
        final LinearLayout indicatorDotLayout = helper.getView(R.id.ll_page_indicator_dot);


        homeSearchLayout.setOnClickListener((View view) -> HTWebActivity.startNoClose((HTMainActivity) mContext, HistudentUrl.search));

        final List<HomePageEntity.BannerEntity> slideShows = item.getBannerEntities();
        if (slideShows == null && slideShows.size() == 0)
            return;

        //初始化banner数据
        ImageView imageView;
        for (HomePageEntity.BannerEntity slideShow : slideShows) {
            imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            final String banner = slideShow.getBanner();
            final String link = slideShow.getLink();
            urlList.add(banner);
            imageList.add(imageView);
            imageView.setOnClickListener((View v) -> HTWebActivity.start(mContext, link));
            MyImageLoader.getIntent().displayNetImage(mContext, banner, imageView);
        }

        PagerAdapter pagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return imageList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(imageList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(imageList.get(position));
                return imageList.get(position);
            }
        };

        //初始化底部小圆点
        indicatorDotLayout.removeAllViews();
        if (imageList.size() == 1)
            indicatorDotLayout.setVisibility(View.INVISIBLE);
        final List<ImageView> dots = new ArrayList<>();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 15);
        for (int i = 0; i < imageList.size(); i++) {
            ImageView dot = new ImageView(mContext);
            dot.setBackgroundResource(R.drawable.photo_dot_selector);
            params.leftMargin = 10;
            dot.setLayoutParams(params);
            dot.setSelected(false);
            dots.add(dot);
            indicatorDotLayout.addView(dot);
        }

        mBannerViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mCurrentPage = position;
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < imageList.size(); i++) {
                    if (position == i) {
                        dots.get(i).setSelected(true);
                    } else {
                        dots.get(i).setSelected(false);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mBannerViewPager.setAdapter(pagerAdapter);
        dots.get(0).setSelected(true);

        mDisposable = Observable.interval(5, 5, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        mCurrentPage++;
                        if (mCurrentPage > urlList.size() - 1) {
                            mCurrentPage = 0;
                        }
                        mBannerViewPager.setCurrentItem(mCurrentPage);
                    }
                });
    }

    public Disposable getDisposable() {
        return mDisposable;
    }

    private int mCurrentPage;
}
