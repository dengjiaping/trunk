package com.histudent.jwsoft.histudent.info.persioninfo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.histudent.jwsoft.histudent.CodeNum;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.activity.GroupCenterActivity;
import com.histudent.jwsoft.histudent.body.mine.activity.ActionBaseActivity;
import com.histudent.jwsoft.histudent.body.mine.adapter.ActionAdapter;
import com.histudent.jwsoft.histudent.body.mine.model.CurrentUserDetailInfoModel;
import com.histudent.jwsoft.histudent.body.mine.model.UserTimeModel;
import com.histudent.jwsoft.histudent.body.mine.parser.DataParser;
import com.histudent.jwsoft.histudent.body.myclass.helper.ClassHelper;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.CommentActivity;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.bean.MyShareBean;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.enums.ReportType;
import com.histudent.jwsoft.histudent.commen.keyword.utils.DisplayUtils;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.ItemDataExchangeUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.UtilsStyle;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.commen.view.MyListView2;
import com.histudent.jwsoft.histudent.commen.view.popupwindow.SharePopupWindow;
import com.histudent.jwsoft.histudent.commen.view.popupwindow.TopMenuPopupWindow;
import com.histudent.jwsoft.histudent.comment2.activity.ReportActivity;
import com.histudent.jwsoft.histudent.comment2.utils.ActionTypeEnum;
import com.histudent.jwsoft.histudent.info.persioninfo.PersionHelper;
import com.histudent.jwsoft.histudent.info.persioninfo.adapter.PersonGroupsAdapter;
import com.histudent.jwsoft.histudent.manage.ParamsManager;
import com.histudent.jwsoft.histudent.widget.refresh.DefinedWeChatHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by liuguiyu-pc on 2016/12/21.
 * 个人主页
 */

public class PersonCenterActivity extends ActionBaseActivity implements HttpRequestCallBack {

    private ListView pullToRefreshListView;
    private MyListView2 persion_group_list;
    private IconView title_right;
    private ActionAdapter adapter_time;
    private boolean flag_showGroup;
    private List<ActionListBean> list_times;
    private PersonGroupsAdapter adapter_groups;
    private List<CurrentUserDetailInfoModel.GroupInfoBean.ItemsBeanXX> list_groups;
    private View view, title_line;
    private HiStudentHeadImageView headImageView;
    private TextView persion_center_name, persion_center_class, action_presion;
    private IconView persion_group_gomore, sendMsg, persion_center_sex, title_left_image;
    private IconView persion_essay, persion_log, persion_album, persion_info;
    private int timeCursor;
    private CurrentUserDetailInfoModel model;
    private String userId;
    private TopMenuPopupWindow menuWindow;
    private LinearLayout group_list_layout;
    private RelativeLayout title_layout;
    private TextView mTitle;
    private SmartRefreshLayout mRefreshLayout;
    private View mFootView;

    public static void start(Activity activity, String userId) {
        if (TextUtils.isEmpty(userId)) return;
        Intent intent = new Intent(activity, PersonCenterActivity.class);
        intent.putExtra("userId", userId);
        activity.startActivity(intent);
    }

    public static void start(Context context, String userId) {
        if (TextUtils.isEmpty(userId)) return;
        Intent intent = new Intent(context, PersonCenterActivity.class);
        intent.putExtra("userId", userId);
        context.startActivity(intent);
    }

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, PersonCenterActivity.class);
        intent.putExtra("userId", HiCache.getInstance().getLoginUserInfo().getUserId());
        activity.startActivity(intent);
    }

    @Override
    public int setViewLayout() {
        /**
         * 全屏不重绘
         */
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        return R.layout.activity_persion;
    }

    @Override
    public void initView() {
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.layout);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) frameLayout.getLayoutParams();
        params.topMargin = DisplayUtils.getStatusHeight(this);
        userId = getIntent().getStringExtra("userId");
        frameLayout.setLayoutParams(params);

        view = LayoutInflater.from(this).inflate(R.layout.persion_commen_top, null);
        list_times = new ArrayList<>();
        list_groups = new ArrayList<>();
        adapter_time = new ActionAdapter(this, list_times, ActionAdapter.PERSION);
        adapter_groups = new PersonGroupsAdapter(this, list_groups);

        title_right = (IconView) findViewById(R.id.title_right_image);
        sendMsg = (IconView) findViewById(R.id.sendMsg);
        title_left_image = (IconView) findViewById(R.id.title_left_image);
        pullToRefreshListView = (ListView) findViewById(R.id.person_center_list);
        headImageView = view.findViewById(R.id.persion_center_headImage);
        persion_group_list = view.findViewById(R.id.persion_group_list);
        persion_center_name = view.findViewById(R.id.persion_center_name);
        persion_center_class = view.findViewById(R.id.persion_center_class);
        action_presion = view.findViewById(R.id.action_presion);
        persion_center_sex = view.findViewById(R.id.persion_center_sex);
        persion_essay = view.findViewById(R.id.persion_essay);
        persion_log = view.findViewById(R.id.persion_log);
        persion_album = view.findViewById(R.id.persion_album);
        persion_info = view.findViewById(R.id.persion_info);
        persion_group_gomore = view.findViewById(R.id.persion_group_gomore);
        group_list_layout = view.findViewById(R.id.group_list_layout);
        title_layout = (RelativeLayout) findViewById(R.id.title_layout);
        mTitle = (TextView) findViewById(R.id.title_middle_text);
        title_line = findViewById(R.id.title_line);
        persion_group_list.setAdapter(adapter_groups);

        mFootView = LayoutInflater.from(this).inflate(R.layout.load_more_bottom_no_data, null);

        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.srl_refresh_layout);
        mRefreshLayout.setEnableAutoLoadmore(true);
        mRefreshLayout.autoRefresh();


        DefinedWeChatHeader chatHeader = new DefinedWeChatHeader(this);
        mRefreshLayout.setRefreshHeader(chatHeader);
        mRefreshLayout.setEnableHeaderTranslationContent(false);//下拉时内容不偏移

        pullToRefreshListView.setAdapter(adapter_time);
        pullToRefreshListView.addHeaderView(view);

    }

    @Override
    public void doAction() {
        super.doAction();
        String dataStr = HiCache.getInstance().getHttpDataInDB(HistudentUrl.getTimeThingsInfo_url, userId);
        if (!TextUtils.isEmpty(dataStr)) {
            displayAction(dataStr);
        }
        title_right.setText(R.string.icon_more3);
        mRefreshLayout.setOnRefreshListener((RefreshLayout refreshLayout) -> {
            mRefreshLayout.setEnableLoadmore(true);
            if (pullToRefreshListView.getFooterViewsCount() > 0)
                pullToRefreshListView.removeFooterView(mFootView);
            list_times.clear();
            timeCursor = 0;
            PersionHelper.getInstance().getPersionTimeline(PersonCenterActivity.this, timeCursor, LoadingType.NONE, PersonCenterActivity.this);
        });

        mRefreshLayout.setOnLoadmoreListener((RefreshLayout refreshLayout) ->
                PersionHelper.getInstance().getPersionTimeline(PersonCenterActivity.this, timeCursor, LoadingType.NONE, PersonCenterActivity.this)
        );
        persion_group_list.setOnItemClickListener((AdapterView<?> adapterView, View view, int i, long l) -> {
            CurrentUserDetailInfoModel.GroupInfoBean.ItemsBeanXX itemsBeanXX = list_groups.get(i);
            GroupCenterActivity.start(PersonCenterActivity.this, itemsBeanXX.getGroupId());
        });

        UtilsStyle.setOnScrollListener(pullToRefreshListView, new UtilsStyle.ScrollY() {
            @Override
            public void scrollY(int y) {

                int height = 200;
                int imageHeight = SystemUtil.dp2px(200);

                if (y <= imageHeight) {   //设置标题的背景颜色
                    title_right.setTextColor(Color.argb(255, 255, 255, 255));
                    sendMsg.setTextColor(Color.argb(255, 255, 255, 255));
                    title_left_image.setTextColor(Color.argb(255, 255, 255, 255));
                    title_layout.setBackgroundColor(Color.argb(0, 0, 0, 0));
                    title_line.setVisibility(View.GONE);
                    mTitle.setVisibility(View.GONE);
                } else if (y > imageHeight && y <= (imageHeight + height)) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                    float scale = (float) (y - imageHeight) / height;
                    float alpha = (255 * scale);
                    title_right.setTextColor(Color.argb((int) alpha, 0, 0, 0));
                    sendMsg.setTextColor(Color.argb((int) alpha, 0, 0, 0));
                    title_left_image.setTextColor(Color.argb((int) alpha, 0, 0, 0));
                    title_layout.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                    title_line.setVisibility(View.GONE);
                    mTitle.setVisibility(View.GONE);

                } else {    //滑动到banner下面设置普通颜色
                    title_right.setTextColor(Color.argb(255, 0, 0, 0));
                    sendMsg.setTextColor(Color.argb(255, 0, 0, 0));
                    title_left_image.setTextColor(Color.argb(255, 0, 0, 0));
                    title_layout.setBackgroundColor(Color.argb(255, 255, 255, 255));
                    title_line.setVisibility(View.VISIBLE);
                    mTitle.setVisibility(View.VISIBLE);
                    if (SystemUtil.isOneselfIn(model.getUserId())) {
                        mTitle.setText("个人主页");
                    } else {
                        mTitle.setText(model.getRealName() + "的主页");
                    }
                }

            }
        });
        showUI(false);
    }

    private void showUI(boolean flag) {
        model = HiCache.getInstance().getUserDetailInfo();

        if (model != null && userId.equals(model.getUserId())) {
            sendMsg.setVisibility(SystemUtil.isOneselfIn(model.getUserId()) ? View.GONE : View.VISIBLE);

            setViewHeight(false);

            if (model != null) {
                CommonGlideImageLoader.getInstance().displayNetImage(this, model.getAvatar(),
                        headImageView, ContextCompat.getDrawable(this, R.mipmap.avatar_def));
                persion_center_name.setText(model.getRealName());
                String schoolName = TextUtils.isEmpty(model.getSchoolName()) ? "" : model.getSchoolName();
                String gradeName = TextUtils.isEmpty(model.getGradeName()) ? "" : model.getGradeName();
                String currClassName = TextUtils.isEmpty(model.getCurrClassName()) ? "" : model.getCurrClassName();
                persion_center_class.setText(schoolName + gradeName + currClassName);
                if (model.getProfile().getSex() == 2) {
                    persion_center_sex.setText(R.string.icon_female);
                    persion_center_sex.setTextColor(ContextCompat.getColor(this, R.color.red_color));
                } else if (model.getProfile().getSex() == 1) {
                    persion_center_sex.setText(R.string.icon_male);
                    persion_center_sex.setTextColor(ContextCompat.getColor(this, R.color.top));
                } else {
                    persion_center_sex.setVisibility(View.GONE);
                }

                if (SystemUtil.isOneselfIn(model.getUserId())) {
                    action_presion.setText("我的动态");
                } else {
                    int statue = model.getProfile().getSex();
                    action_presion.setText(statue == 0 ? "TA的动态" : statue == 2 ? "她的动态" : "他的动态");
                }

            }
        } else {
            if (flag) return;
            PersionHelper.getInstance().getUserInfo(this, userId, new HttpRequestCallBack() {
                @Override
                public void onSuccess(String result) {
                    showUI(true);
                }

                @Override
                public void onFailure(String errorMsg) {

                }
            });
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.title_left_image://返回
                finish();
                break;

            case R.id.title_right_image://菜单
                showClassMenue(this);
                break;

            case R.id.persion_essay://随记

                PersonEssayActivity.start(this);

                break;

            case R.id.persion_log://日志

                PersonLogActivity.start(this);

                break;

            case R.id.persion_album://相册

                ClassHelper.gotoAlbumsCenter(this, userId, ActionTypeEnum.OWNER, 222, false);

                break;

            case R.id.persion_info://个人档
                PersonDataInfoActivity.start(this);
                break;

            case R.id.sendMsg://发消息
                PersionHelper.getInstance().sendMsg(PersonCenterActivity.this);
                break;

            case R.id.persion_group_gomore://显示更多社群列表
                setViewHeight(flag_showGroup);
                break;
        }
    }

    @Override
    public void onSuccess(String result) {
        displayAction(result);
        HiCache.getInstance().saveHttpDataIntoDB(HistudentUrl.getTimeThingsInfo_url, userId, result);
    }

    private void displayAction(String result) {
        UserTimeModel model = DataParser.getUserTimeModel(result);
        mRefreshLayout.finishLoadmore();
        mRefreshLayout.finishRefresh();
        if (model.getItems().size() > 0) {
            timeCursor = model.getCursor();
            list_times.addAll(ItemDataExchangeUtils.dataExchange(model.getItems()));
            adapter_time.notifyDataSetChanged();
        } else {
            if (timeCursor != 0) {
                pullToRefreshListView.addFooterView(mFootView);
                mRefreshLayout.setEnableLoadmore(false);
            }
        }
    }

    @Override
    public void onFailure(String errorMsg) {
        mRefreshLayout.finishLoadmore();
        mRefreshLayout.finishRefresh();
    }

    /**
     * 动态设置社群List的高度
     */
    private void setViewHeight(boolean showAllItem) {

        flag_showGroup = !showAllItem;

        persion_group_gomore.setText(showAllItem ? R.string.icon_doublearrow : R.string.icon_doublearrow2);

        if (model == null || model.getGroupInfo() == null) {
            group_list_layout.setVisibility(View.GONE);
            return;
        }
        List<CurrentUserDetailInfoModel.GroupInfoBean.ItemsBeanXX> beans = model.getGroupInfo().getItems();

        if (beans == null) return;

        int size = beans.size();
        if (size > 0 && size < 3) {
            persion_group_gomore.setVisibility(View.GONE);
            list_groups.clear();
            list_groups.addAll(beans);
            adapter_groups.notifyDataSetChanged();
        } else {
            persion_group_gomore.setVisibility(View.VISIBLE);
            list_groups.clear();
            list_groups.addAll(showAllItem ? beans : beans.subList(0, 2));
            adapter_groups.notifyDataSetChanged();
        }

    }

    /**
     * 显示菜单
     *
     * @param activity
     */
    private void showClassMenue(final BaseActivity activity) {

        List<String> list_name = new ArrayList<>();
        List<Integer> list_color = new ArrayList<>();

        if (SystemUtil.isOneselfIn(model.getUserId())) {
            list_name.add("分享");
            list_color.add(Color.rgb(51, 51, 51));
            menuWindow = new TopMenuPopupWindow(activity, (View v) -> {
                menuWindow.dismiss();
                switch (v.getId()) {
                    case R.id.btn_01:
                        getShareData(PersonCenterActivity.this, userId);
                        break;

                    default:
                        break;
                }
            }, list_name, list_color, false);
        } else {
            list_name.add("分享");
            list_name.add("举报");
            list_color.add(Color.rgb(51, 51, 51));
            list_color.add(Color.rgb(51, 51, 51));
            menuWindow = new TopMenuPopupWindow(activity, (View v) -> {
                menuWindow.dismiss();
                switch (v.getId()) {
                    case R.id.btn_01:
                        getShareData(PersonCenterActivity.this, userId);
                        break;

                    case R.id.btn_02:
                        ReportActivity.start(PersonCenterActivity.this, userId, ReportType.USER);
                        break;

                    default:
                        break;
                }
            }, list_name, list_color, false);
        }

        menuWindow.showAtLocation(activity.findViewById(R.id.layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }

    /**
     * 获取个人主页分享数据并予以分享
     *
     * @param activityId
     */
    private void getShareData(final BaseActivity activity, final String activityId) {
        if (TextUtils.isEmpty(activityId)) return;
        final Map<String, Object> map = ParamsManager.getInstance()
                .setParams("objectId", activityId)
                .setParams("shareObjectType", 6)
                .getParamsMap();
        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.share_url_, new HttpRequestCallBack() {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CommentActivity.REQUESTCODE && resultCode == CommentActivity.RESULTCODE) {
            adapter_time.notifyDataSetChanged();
        } else if (requestCode == CodeNum.GROUP_SENTER && resultCode == CodeNum.GROUP_DISSOLVE_RESULT) {//社群已解散
            refreshPersonInformation(userId);
        } else if (requestCode == CodeNum.GROUP_SENTER && resultCode == CodeNum.GROUP_EXIT_RESULT) {//退出社群
            refreshPersonInformation(userId);
        }
    }

    @Override
    protected void reFreshActionList(int position, ActionListBean bean) {
        list_times.set(position, bean);
        adapter_time.notifyDataSetChanged();
    }

    @Override
    protected void reMoveAction(int position) {
        list_times.remove(position);
        adapter_time.notifyDataSetChanged();
    }

    /**
     * 刷新个人信息
     */
    private void refreshPersonInformation(String userId) {
        PersionHelper.getInstance().getUserInfo(this, userId, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                showUI(true);
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

}
