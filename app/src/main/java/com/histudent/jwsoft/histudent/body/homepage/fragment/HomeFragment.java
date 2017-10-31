package com.histudent.jwsoft.histudent.body.homepage.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.activity.blog.BlogDetailActivity;
import com.histudent.jwsoft.histudent.body.homepage.activity.TopicActivity;
import com.histudent.jwsoft.histudent.body.homepage.adapter.HomePageAdapter;
import com.histudent.jwsoft.histudent.body.homepage.bean.HomePageAllBean;
import com.histudent.jwsoft.histudent.body.myclass.activity.ClassCircleActivity;
import com.histudent.jwsoft.histudent.body.myclass.helper.ClassHelper;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.CommentActivity;
import com.histudent.jwsoft.histudent.commen.activity.MyWebActivity;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.bean.AdvertisementModel;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.fragment.BaseFragment;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.ItemDataExchangeUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.UtilsStyle;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.commen.view.MyHorizontalScrollView;
import com.histudent.jwsoft.histudent.comment2.bean.AddressInforBean;
import com.histudent.jwsoft.histudent.comment2.utils.UpDateDialogUtils;
import com.histudent.jwsoft.histudent.info.persioninfo.activity.PersonCenterActivity;
import com.histudent.jwsoft.histudent.widget.AutoImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.histudent.jwsoft.histudent.R.id.class_list_layout;


/**
 * Created by liuguiyu-pc on 2017/4/20.
 * 首页fragment
 */

public class HomeFragment extends BaseFragment {

    private View view, headView;
    private ListView mListView;

    private ViewPager viewPager_head;
    private List<ImageView> dots;
    private LinearLayout head_fashion_list, hot_topic_list, head_class_list_00, head_class_list_01;

    private List<ActionListBean> beens;

    private HomePageAdapter homePageAdapter_rule;
    private int timeCursor;

    private LinearLayout fashion_list_layout, hot_topic_layout, llt_page_indicator;
    private RelativeLayout view_image;
    private MyHorizontalScrollView mScrollView;

    private MyHandler handler;
    private final int START = 0;
    private final int SLIDE = 1;
    private int slidePageSize;
    private int currentPage;
    private List<View> viewList;
    private AddressInforBean bean;
    private Message message;

    private View title_bar, title_layout, title_line;
    private IconView title_left_img;
    private TextView title;
    private int imageHeight;
    private RadioGroup mGroup;

    private SmartRefreshLayout mRefreshLayout;

    private IconView mTitleRightIcon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return view = inflater.inflate(R.layout.fragment_main_home, container, false);
    }

    @Override
    public void initView() {
        super.initView();
        handler = new MyHandler();
        headView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_homepage_head, null);
        mListView = view.findViewById(R.id.recyclerView_homePage);
        mScrollView = headView.findViewById(class_list_layout);
        mGroup = headView.findViewById(R.id.class_indicator);
        fashion_list_layout = headView.findViewById(R.id.fashion_list_layout);
        llt_page_indicator = headView.findViewById(R.id.llt_page_indicator);
        hot_topic_layout = headView.findViewById(R.id.hot_topic_layout);
        viewPager_head = headView.findViewById(R.id.head_viewpager);
        head_fashion_list = headView.findViewById(R.id.fashion_list);
        hot_topic_list = headView.findViewById(R.id.hot_topic);
        head_class_list_00 = headView.findViewById(R.id.head_class_list_00);
        head_class_list_01 = headView.findViewById(R.id.head_class_list_01);
        view_image = headView.findViewById(R.id.view_image);
        title_layout = view.findViewById(R.id.title_layout);
        mTitleRightIcon = view.findViewById(R.id.title_right_image);
        title_bar = view.findViewById(R.id.title_bar);
        title_line = view.findViewById(R.id.title_line);
        title_left_img = view.findViewById(R.id.title_left_image);
        title = view.findViewById(R.id.title_middle_text);

        mRefreshLayout = view.findViewById(R.id.srl_refresh_layout);
        mRefreshLayout.setEnableAutoLoadmore(true);
        mRefreshLayout.setEnableLoadmore(false);

        headView.findViewById(R.id.head_reseach).setOnClickListener((View view) ->
                MyWebActivity.startNoClose(getActivity(), HistudentUrl.search));
        beens = new ArrayList<>();

        homePageAdapter_rule = new HomePageAdapter(getActivity(), beens);
        title_layout.setVisibility(View.GONE);
        loadListener();
    }

    private void loadListener() {
        mTitleRightIcon.setOnClickListener((View view) -> MyWebActivity.startNoClose(getActivity(), HistudentUrl.search));
    }

    private static final String TAG = "HomeFragment";

    @Override
    public void initData() {
        super.initData();
        title_left_img.setVisibility(View.GONE);
        title.setText("首页");
        bean = HiWorldCache.getUserLocationInfor();
        mListView.addHeaderView(headView);
        View footView = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_divide_layout, null);
        mListView.addFooterView(footView);
        mListView.setAdapter(homePageAdapter_rule);
        mRefreshLayout.setOnRefreshListener((RefreshLayout refreshlayout) -> {
            beens.clear();
            timeCursor = 0;
            getAllData(LoadingType.NONE);
        });
        mListView.setOnItemClickListener((AdapterView<?> adapterView, View view, int i, long l) -> {
            ActionListBean bean = beens.get(i - 1);
            if ("CreateBlog".equals(bean.getActivityItemKey())) {
                MyWebActivity.start(getActivity(), bean.getHtmlUrl());
//                Intent intent = new Intent(getActivity(), BlogDetailActivity.class);
//                intent.putExtra("actionId",bean.getActId());
//                startActivity(intent);

            } else {
                CommentActivity.start(getActivity(), beens.get(i - 1).getActId(), i - 1);
            }
        });

        UtilsStyle.setOnScrollListener(mListView, (int y) -> {
                    int height = 200;
//                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) title_bar.getLayoutParams();
//                    layoutParams.height = SystemUtil.getStatusBarHeight();
//                    title_bar.setLayoutParams(layoutParams);
                    if (y <= imageHeight) {   //设置标题的背景颜色
                        mTitleRightIcon.setVisibility(View.GONE);
                        title_bar.setBackgroundColor(Color.argb(0, 0, 0, 0));
                        title.setTextColor(Color.argb(0, 0, 0, 0));
                        title_layout.setBackgroundColor(Color.argb(0, 0, 0, 0));
                        title_line.setVisibility(View.GONE);
                    } else if (y > imageHeight && y <= (imageHeight + height)) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                        float scale = (float) (y - imageHeight) / height;
                        float alpha = (255 * scale);
                        title.setTextColor(Color.argb((int) alpha, 0, 0, 0));
                        title_bar.setBackgroundColor(Color.argb((int) alpha, 0, 0, 0));
                        title_layout.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                        title_line.setVisibility(View.GONE);
                    } else {    //滑动到banner下面设置普通颜色
                        title_bar.setBackgroundColor(Color.argb(255, 0, 0, 0));
                        title.setTextColor(Color.argb(255, 0, 0, 0));
                        title_layout.setBackgroundColor(Color.argb(255, 255, 255, 255));
                        title_line.setVisibility(View.VISIBLE);
                        title_layout.setVisibility(View.VISIBLE);
                        mTitleRightIcon.setVisibility(View.VISIBLE);
                        mTitleRightIcon.setText(getString(R.string.icon_search));
                        mTitleRightIcon.setTextColor(ContextCompat.getColor(getContext(), R.color._C1CBCB));
                        mTitleRightIcon.setTextSize(20);
                    }

                }
        );
        String dataStr = HiCache.getInstance().getHttpDataInDB(HistudentUrl.homePageInfo_url, "");
        if (TextUtils.isEmpty(dataStr)) {
            getAllData(LoadingType.FLOWER);
        } else {
            updateUI(dataStr);
        }
        showAdvertisementView(getActivity());

    }

    //显示广告页面
    private void showAdvertisementView(final Activity activity) {
        ClassHelper.getAdvertisement(activity, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                final AdvertisementModel model = JSON.parseObject(result, AdvertisementModel.class);
                if (model != null && model.isStatus())
                    UpDateDialogUtils.showAdvertisementView(activity, model);
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

    /**
     * 获取所有数据
     */
    public void getAllData(LoadingType loadingType) {
        if (bean == null)
            bean = new AddressInforBean();
        Map<String, Object> map = new TreeMap<>();
        map.put("city", bean.getCity());
        map.put("quName", bean.getAreaStr());
        map.put("slideShowCategory", 4);
        map.put("rcPageSize", 10);
        map.put("ruPageSize", 15);
        map.put("rtPageSize", 20);
        map.put("raPageSize", 15);
        HiStudentHttpUtils.postDataByOKHttp(true, (BaseActivity) getActivity(), map, HistudentUrl.homePageInfo_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                mRefreshLayout.finishRefresh();

                updateUI(result);
                HiCache.getInstance().saveHttpDataIntoDB(HistudentUrl.homePageInfo_url, "", result);

            }

            @Override
            public void onFailure(String errorMsg) {
                mRefreshLayout.finishRefresh();
            }
        }, loadingType);

    }

    private void updateUI(String result) {
        HomePageAllBean bean = JSON.parseObject(result, HomePageAllBean.class);

        if (bean.getRet() == 1) {

            getCarouselData(bean.getData().getSlideShows());
            getClassData(bean.getData().getRecommendClasses().getItems());
            getFashionData(bean.getData().getRecommendUsers().getItems());
            getHotData(bean.getData().getRecommendTags().getItems());

            List<HomePageAllBean.DataBean.RecommendActivitiesBean.ItemsBeanXXX> items = bean.getData().getRecommendActivities().getItems();

            if (items != null && items.size() > 0) {
                timeCursor = bean.getData().getRecommendActivities().getCursor();
                beens.addAll(ItemDataExchangeUtils.dataExchange_Home(items));
                homePageAdapter_rule.notifyDataSetChanged();
            }
        }
    }

    /**
     * 获取动态数据数据
     */
//    public void getActionData() {
//
//        Map<String, Object> map = new TreeMap<>();
//        map.put("timeCursor", timeCursor);
//        map.put("pageSize", 8);
//        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) getActivity(), map, HistudentUrl.getRecommendActivities_url, new HttpRequestCallBack() {
//            @Override
//            public void onSuccess(String result) {
//                mRefreshLayout.finishLoadmore();
//                HomePageAllBean.DataBean.RecommendActivitiesBean recommendActivitiesBean = JSON.parseObject(result, HomePageAllBean.DataBean.RecommendActivitiesBean.class);
//                List<HomePageAllBean.DataBean.RecommendActivitiesBean.ItemsBeanXXX> items = recommendActivitiesBean.getItems();
//                if (items != null && items.size() > 0) {
//                    timeCursor = recommendActivitiesBean.getCursor();
//                    beens.addAll(ItemDataExchangeUtils.dataExchange_Home(items));
//                    homePageAdapter_rule.notifyDataSetChanged();
//                }
//
//            }
//
//            @Override
//            public void onFailure(String errorMsg) {
//                mRefreshLayout.finishLoadmore();
//            }
//
//        }, LoadingType.NONE);
//
//    }

    /**
     * 获取轮播页面数据
     */
    public void getCarouselData(List<HomePageAllBean.DataBean.SlideShowsBean> slideList) {

        if (slideList == null || slideList.size() == 0) return;

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view_image.getLayoutParams();
        layoutParams.width = SystemUtil.getScreenWind();
        imageHeight = SystemUtil.getScreenWind() * 54 / 100;
        layoutParams.height = imageHeight;
        viewPager_head.setLayoutParams(layoutParams);

        viewList = new ArrayList<>();
        ImageView imageView;
        LinearLayout.LayoutParams params;
        for (int i = 0; i < slideList.size(); i++) {
            imageView = new ImageView(getActivity());
            params = new LinearLayout.LayoutParams(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(getContext(), slideList.get(i).getBanner(),
//                    imageView, PhotoManager.getInstance().getDefaultPlaceholderResource());
            MyImageLoader.getIntent().displayNetImage(getContext(), slideList.get(i).getBanner(), imageView);
            final String link = slideList.get(i).getLink();
            imageView.setOnClickListener((View v) -> MyWebActivity.start(getActivity(), link));
            viewList.add(imageView);
        }

        PagerAdapter pagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return viewList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView(viewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewList.get(position));
                return viewList.get(position);
            }
        };

        viewPager_head.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeImageBackGroud(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager_head.setAdapter(pagerAdapter);
        initDots();
        changeImageBackGroud(0);

        if (message == null) {
            message = handler.obtainMessage();
            message.arg1 = viewList.size();
            message.what = START;
            handler.sendMessage(message);
        }
    }

    /**
     * 接口班级列表获取数据，然后更新数据源
     */
    public void getClassData(final List<HomePageAllBean.DataBean.RecommendClassesBean.ItemsBeanX> classBeans) {
        if (classBeans == null || classBeans.size() == 0) {
            mScrollView.setVisibility(View.GONE);
        } else {
            mScrollView.setVisibility(View.VISIBLE);
            int count = (int) Math.ceil(classBeans.size() / 4.0);
            initIndicator(count);
            LinearLayout.LayoutParams params00 = (LinearLayout.LayoutParams) head_class_list_00.getLayoutParams();
            params00.width = SystemUtil.getScreenWind() * count;
            head_class_list_00.setLayoutParams(params00);
            LinearLayout.LayoutParams params11 = (LinearLayout.LayoutParams) head_class_list_01.getLayoutParams();
            params11.width = SystemUtil.getScreenWind() * count;
            head_class_list_01.setLayoutParams(params11);
            head_class_list_00.removeAllViews();
            head_class_list_01.removeAllViews();

            for (int i = 0; i < classBeans.size(); i++) {
                final HomePageAllBean.DataBean.RecommendClassesBean.ItemsBeanX itemsBeanX = classBeans.get(i);
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_homepage_class_item, null);

                LinearLayout layout = view.findViewById(R.id.classList_layout);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layout.getLayoutParams();
                params.width = (SystemUtil.getScreenWind() - SystemUtil.dp2px(48)) / 2;
                params.height = SystemUtil.dip2px(getActivity(), 70);
                params.setMargins(SystemUtil.dp2px(12), 0, SystemUtil.dp2px(12), 0);
                layout.setLayoutParams(params);

                ImageView headImageView = view.findViewById(R.id.class_logo);
                TextView class_school = view.findViewById(R.id.class_school);
                TextView class_grade = view.findViewById(R.id.class_grade);

                if (!TextUtils.isEmpty(itemsBeanX.getLogo()))
                    CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(getActivity(), itemsBeanX.getLogo(), headImageView);

                class_school.setText(itemsBeanX.getSchoolName());
                class_grade.setText(itemsBeanX.getGradeName() + itemsBeanX.getName());

                view.setTag(itemsBeanX);
                view.setOnClickListener((View v) -> ClassCircleActivity.start(getActivity(), itemsBeanX.getClassesId()));

                if (i < (classBeans.size() + 1) / 2) {
                    head_class_list_00.addView(view);
                } else {
                    head_class_list_01.addView(view);
                }
            }
            if (((RadioButton) mGroup.getChildAt(0)) != null) {
                ((RadioButton) mGroup.getChildAt(0)).setChecked(true);
            }
        }

        mScrollView.setOnSmoothListener((int currentPage) -> {
            if (((RadioButton) mGroup.getChildAt(currentPage)) != null) {
                ((RadioButton) mGroup.getChildAt(currentPage)).setChecked(true);
            }
        });

    }

    private void initIndicator(int count) {
        mGroup.removeAllViews();
        for (int i = 0; i < count; i++) {
            RadioButton radioButton = new RadioButton(getActivity());
            radioButton.setButtonDrawable(android.R.color.transparent);
            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(SystemUtil.dp2px(10), SystemUtil.dp2px(3));
            layoutParams.setMargins(SystemUtil.dp2px(3), SystemUtil.dp2px(3), SystemUtil.dp2px(3), SystemUtil.dp2px(3));
            radioButton.setLayoutParams(layoutParams);
            radioButton.setBackground(getResources().getDrawable(R.drawable.selector_class_indicator));
            mGroup.addView(radioButton);
        }
    }

    /**
     * 接口红人榜列表获取数据，然后更新数据源
     */
    public void getFashionData(final List<HomePageAllBean.DataBean.RecommendUsersBean.ItemsBean> fashionBeans) {

        if (fashionBeans == null || fashionBeans.size() == 0) {
            fashion_list_layout.setVisibility(View.GONE);
        } else {
            fashion_list_layout.setVisibility(View.VISIBLE);
            head_fashion_list.removeAllViews();
            View layout;
            AutoImageView headImageView;
            TextView className;
            View isTeacherIdentity;
            for (HomePageAllBean.DataBean.RecommendUsersBean.ItemsBean model : fashionBeans) {
                layout = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_homepage_fashion_item, null);
                int padding = (int) ((SystemUtil.getScreenWind() - 5.5 * SystemUtil.dp2px(60)) / 10);
                layout.setPadding(padding, SystemUtil.dp2px(8), padding, SystemUtil.dp2px(8));
                headImageView = layout.findViewById(R.id.fashion_head);
                className = layout.findViewById(R.id.fashion_name);
                isTeacherIdentity = layout.findViewById(R.id.iv_teacher_identity);
                if (!TextUtils.isEmpty(model.getAvatar()))
                    CommonGlideImageLoader.getInstance().displayNetImageWithCircle(getActivity(), model.getAvatar(), headImageView);

                if (model.getUserType() == 3) {
                    isTeacherIdentity.setVisibility(View.VISIBLE);
                } else {
                    isTeacherIdentity.setVisibility(View.INVISIBLE);
                }
                className.setTextColor(getResources().getColor(R.color.text_black_h3));

                className.setText(model.getName());

                layout.setTag(model);
                layout.setOnClickListener((View view) -> PersonCenterActivity.start(getActivity(), model.getUserId()));
                headImageView.setOnClickListener((View view) -> PersonCenterActivity.start(getActivity(), model.getUserId()));
                head_fashion_list.addView(layout);
            }
        }
    }

    /**
     * 接口热门话题列表获取数据，然后更新数据源
     */
    public void getHotData(final List<HomePageAllBean.DataBean.RecommendTagsBean.ItemsBeanXX> hotBeans) {

        if (hotBeans == null || hotBeans.size() == 0) {
            hot_topic_list.setVisibility(View.GONE);
        } else {
            hot_topic_list.setVisibility(View.VISIBLE);
            hot_topic_list.removeAllViews();
            for (HomePageAllBean.DataBean.RecommendTagsBean.ItemsBeanXX model : hotBeans) {
                View layout = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_homepage_hot_item, null);
                AutoImageView iv = layout.findViewById(R.id.hot_image);
                TextView className = layout.findViewById(R.id.topic_tag);

                CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(getActivity(), model.getFeaturedImage(), iv, ContextCompat.getDrawable(getActivity(), R.drawable.bg_topic_default));

                className.setText(model.getTagName());

                layout.setTag(model);
                iv.setOnClickListener((View v) -> TopicActivity.start(getActivity(), model.getTagName()));
                hot_topic_list.addView(layout);
            }

        }
    }

    /**
     * 初始化导航点以及标题
     */
    private void initDots() {
        llt_page_indicator.removeAllViews();
        if (viewList.size() == 1)
            llt_page_indicator.setVisibility(View.INVISIBLE);
        dots = new ArrayList<>();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 15);
        for (int i = 0; i < viewList.size(); i++) {
            ImageView dot = new ImageView(getActivity());
            dot.setBackgroundResource(R.drawable.photo_dot_selector);
            params.leftMargin = 10;
            dot.setLayoutParams(params);
            dot.setSelected(false);
            dots.add(dot);
            llt_page_indicator.addView(dot);
        }
    }

    /**
     * 根据选择改变导航点的颜色
     */
    private void changeImageBackGroud(int position) {

        for (int i = 0; i < viewList.size(); i++) {
            if (position == i) {
                dots.get(i).setSelected(true);
            } else {
                dots.get(i).setSelected(false);
            }
        }
    }

    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case START:
                    slidePageSize = msg.arg1;
                    currentPage = 0;
                    sendEmptyMessageDelayed(SLIDE, 5000);
                    break;
                case SLIDE:
                    currentPage++;
                    if (currentPage > slidePageSize - 1)
                        currentPage = 0;
                    viewPager_head.setCurrentItem(currentPage);
                    sendEmptyMessageDelayed(SLIDE, 5000);
                    break;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
