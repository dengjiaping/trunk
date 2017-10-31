package com.histudent.jwsoft.histudent.body.find.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.sdk.android.vod.upload.common.utils.StringUtil;
import com.histudent.jwsoft.histudent.CodeNum;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.adapter.GroupVisitPersonAdapter;
import com.histudent.jwsoft.histudent.body.find.bean.GroupBean;
import com.histudent.jwsoft.histudent.body.message.uikit.session.SessionHelper;
import com.histudent.jwsoft.histudent.body.mine.adapter.ActionAdapter;
import com.histudent.jwsoft.histudent.body.mine.model.UserTimeModel;
import com.histudent.jwsoft.histudent.body.mine.parser.DataParser;
import com.histudent.jwsoft.histudent.body.myclass.activity.ClassOrGroupHuoDongActivity;
import com.histudent.jwsoft.histudent.body.myclass.activity.ClassSetActivity;
import com.histudent.jwsoft.histudent.body.myclass.helper.ClassHelper;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.bean.MyShareBean;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.enums.ReportType;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.ItemDataExchangeUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.UtilsStyle;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.commen.view.popupwindow.SharePopupWindow;
import com.histudent.jwsoft.histudent.commen.view.popupwindow.TopMenuPopupWindow;
import com.histudent.jwsoft.histudent.commen.view.swipemenulistview.AutoScrollImageView;
import com.histudent.jwsoft.histudent.comment2.activity.ReportActivity;
import com.histudent.jwsoft.histudent.comment2.utils.ActionTypeEnum;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;
import com.histudent.jwsoft.histudent.info.persioninfo.activity.PersonCenterActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.histudent.jwsoft.histudent.commen.activity.CommentActivity.RESULTCODE;


/**
 * 社群主页
 */

public class GroupCenterActivity extends BaseActivity implements View.OnClickListener {

    private ListView mListView;
    private ImageView group_cover;
    private IconView title_righr, title_left, group_bottom_imag, mSendMessage;
    private View title_bar, title_layout, title_line;
    private HiStudentHeadImageView headImageView;
    private TextView group_name, group_members, group_topics, group_visit, group_bottom_text;
    private int height;
    private String groupId;
    private int timeCursor;
    private ActionAdapter actionAdapter;
    private List<ActionListBean> list_all;
    private GridView group_visit_list;
    private List<GroupBean.GroupMembersListBean> persions;
    private TopMenuPopupWindow menuWindow;
    private boolean isAdmin, isMember;
    private int JOINCODE = 11;
    private int ACTION = 12;
    private GroupBean groupBean;
    private IconView makeTopic;
    private LinearLayout mSign;
    private LinearLayout group_join;
    private AutoScrollImageView scrollImageView;
    private LinearLayout private_view;
    private boolean isShowShade;
    private LinearLayout normal_view, group_visit_layout, empty_layout;
    private View view;
    private SmartRefreshLayout mRefreshLayout;

    @Override
    public int setViewLayout() {
        return R.layout.group_center;
    }

    public static void start(Activity context, String groupId) {
        if (TextUtils.isEmpty(groupId)) return;
        Intent intent = new Intent(context, GroupCenterActivity.class);
        intent.putExtra("groupId", groupId);
        context.startActivityForResult(intent, CodeNum.GROUP_SENTER);
    }

    @Override
    public void initView() {

        groupId = getIntent().getStringExtra("groupId");
        list_all = new ArrayList<>();
        persions = new ArrayList<>();

        mListView = (ListView) findViewById(R.id.ptl_listView);
        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.srl_refresh_layout);
//        mListView.setMode(PullToRefreshBase.Mode.DISABLED);
        title_layout = findViewById(R.id.title_layout);
        title_bar = findViewById(R.id.title_bar);
        title_left = (IconView) findViewById(R.id.title_left_image);
        title_righr = (IconView) findViewById(R.id.title_right_image);
        group_bottom_imag = (IconView) findViewById(R.id.group_bottom_imag);
        makeTopic = (IconView) findViewById(R.id.publish_invitation);
        mSendMessage = (IconView) findViewById(R.id.send_message);
        title_line = findViewById(R.id.title_line);
        group_join = (LinearLayout) findViewById(R.id.group_join);
        group_bottom_text = (TextView) findViewById(R.id.group_bottom_text);

        view = LayoutInflater.from(this).inflate(R.layout.group_commen_top, null);
        group_cover = view.findViewById(R.id.group_cover);
        scrollImageView = view.findViewById(R.id.scroll_image);
        headImageView = view.findViewById(R.id.group_logo);
        group_members = view.findViewById(R.id.group_members);
        group_topics = view.findViewById(R.id.group_topics);
        group_visit = view.findViewById(R.id.group_visit);
        group_name = view.findViewById(R.id.group_name);
        group_visit_list = view.findViewById(R.id.group_visit_list);
        mSign = view.findViewById(R.id.group_center_sign);
        private_view = view.findViewById(R.id.private_view);
        normal_view = view.findViewById(R.id.normal_view);
        group_visit_layout = view.findViewById(R.id.group_visit_layout);
        empty_layout = view.findViewById(R.id.empty_layout);
        view.findViewById(R.id.group_introduction).setOnClickListener(this);
        view.findViewById(R.id.group_live).setOnClickListener(this);
        view.findViewById(R.id.group_album).setOnClickListener(this);
        view.findViewById(R.id.group_action).setOnClickListener(this);
        findViewById(R.id.send_message).setOnClickListener(this);
        findViewById(R.id.title_right_image).setOnClickListener(this);
        findViewById(R.id.publish_invitation).setOnClickListener(this);
        findViewById(R.id.group_join).setOnClickListener(this);
        actionAdapter = new ActionAdapter(this, list_all, ActionAdapter.GROUP);

        initListeners();
    }

    @Override
    public void doAction() {
        super.doAction();

        title_righr.setText(R.string.icon_more3);
        mListView.addHeaderView(view);
        mListView.setAdapter(actionAdapter);

        mRefreshLayout.setOnRefreshListener((RefreshLayout refreshLayout) -> {
            timeCursor = 0;
            list_all.clear();
            persions.clear();
            getGroupInfo(groupId, LoadingType.NONE);
        });
        mRefreshLayout.setOnLoadmoreListener((RefreshLayout refreshLayout) -> getGroupActivitys(groupId));
        getGroupInfo(groupId, LoadingType.FLOWER);

    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;

            case R.id.title_right_image:
                showClassMenue(this);
                break;
            case R.id.send_message:
                SessionHelper.startTeamSession(this, groupBean.getImTeamId(), true);
                break;

            case R.id.group_action:
                ClassOrGroupHuoDongActivity.start(this, 2, groupId, ACTION, groupBean.isIsManager() || groupBean.isIsGroupMaker());
                break;

            case R.id.group_album:

                ClassHelper.gotoAlbumsCenter(this, groupId, ActionTypeEnum.GROUP, 111, false);
                break;

            case R.id.group_center_sign:

                break;
            case R.id.group_live:
                break;

            case R.id.group_introduction:
                GroupIntroduceActivity.start(this, groupBean);
                break;

            case R.id.publish_invitation:
                CreateTopicActivity.start(this, groupBean.getGroupId(), 13);
                break;

            case R.id.group_join:
                GroupJoinActivity.start(GroupCenterActivity.this, groupId, 2);
                break;

            case R.id.join:
                GroupJoinActivity.start(GroupCenterActivity.this, groupId, 2);
                break;

        }
    }

    private void showUI(GroupBean bean) {
        if (bean == null) return;
        CommonGlideImageLoader.getInstance().displayNetImage(this, bean.getGroupLogo(),
                headImageView, ContextCompat.getDrawable(this, R.mipmap.per_def));
        group_name.setText(bean.getGroupName());

        if (bean.getToDayVisitNum() > 0) {
            group_visit_layout.setVisibility(View.VISIBLE);
            group_visit.setText(bean.getToDayVisitNum() + "人");
        } else {
            group_visit_layout.setVisibility(View.GONE);
        }

        if (bean.getGroupMemberNum() == 0) {
            group_members.setText(String.valueOf(0));
        } else {
            group_members.setText(String.valueOf(bean.getGroupMemberNum()));
        }

        if (StringUtil.isEmpty(bean.getTlakNum())) {
            group_topics.setText(String.valueOf(0));
        } else {
            group_topics.setText(bean.getTlakNum());
        }

        //当社群为私密社群，同时用户不是成员时，只需要初始化社群头部信息
        if (!isShowShade) {

            isAdmin = bean.isIsManager();
            isMember = bean.isIsMember();
            if (isMember) {
                mSendMessage.setVisibility(View.VISIBLE);
                group_join.setVisibility(View.GONE);
            } else {
                mSendMessage.setVisibility(View.GONE);
                group_join.setVisibility(View.VISIBLE);
            }

            group_bottom_imag.setText(isMember ? R.string.icon_topic5 : R.string.icon_addmember);

            List<GroupBean.GroupMembersListBean> beens = bean.getGroupMembersList();
            if (beens != null && beens.size() > 0) {
                int size = beens.size();

                if (size < 4) {
                    persions.addAll(beens);
                } else {
                    persions.addAll(beens.subList(0, 4));
                    persions.add(new GroupBean.GroupMembersListBean());
                }

                group_visit_list.setAdapter(new GroupVisitPersonAdapter(persions, GroupCenterActivity.this));
            }

            if (isMember) {
                makeTopic.setVisibility(View.VISIBLE);
                if (bean.isIsTodayVisit()) {
                    ReminderHelper.getIntentce().ToastShow_with_image(this, "今日签到成功", R.string.icon_check);
                    persions.clear();
//                    getGroupInfo(groupId, LoadingType.NONE);

                }
            } else {
                makeTopic.setVisibility(View.GONE);
            }
            getGroupActivitys(groupId);
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

        if (groupBean.isIsMember()) {
            if (groupBean.isIsPublic()) {
                list_name.add("设置");
                list_name.add("分享");
                list_name.add("举报");
                list_color.add(Color.rgb(51, 51, 51));
                list_color.add(Color.rgb(51, 51, 51));
                list_color.add(Color.rgb(51, 51, 51));

                menuWindow = new TopMenuPopupWindow(activity, new View.OnClickListener() {

                    public void onClick(View v) {
                        menuWindow.dismiss();
                        switch (v.getId()) {
                            case R.id.btn_01:
                                ClassSetActivity.start(GroupCenterActivity.this, groupBean, CodeNum.GROUP_SET);
                                break;
                            case R.id.btn_02:
                                getShareData(GroupCenterActivity.this, groupId);
                                break;
                            case R.id.btn_03:
                                ReportActivity.start(GroupCenterActivity.this, groupId, ReportType.OTHER);
                                break;
                            default:
                                break;
                        }
                    }
                }, list_name, list_color, false);
            } else {
                list_name.add("设置");
                list_name.add("举报");
                list_color.add(Color.rgb(51, 51, 51));
                list_color.add(Color.rgb(51, 51, 51));
                list_color.add(Color.rgb(51, 51, 51));

                menuWindow = new TopMenuPopupWindow(activity, new View.OnClickListener() {

                    public void onClick(View v) {
                        menuWindow.dismiss();
                        switch (v.getId()) {
                            case R.id.btn_01:
                                ClassSetActivity.start(GroupCenterActivity.this, groupBean, CodeNum.GROUP_SET);
                                break;
                            case R.id.btn_02:
                                ReportActivity.start(GroupCenterActivity.this, groupId, ReportType.OTHER);
                                break;
                            default:
                                break;
                        }
                    }
                }, list_name, list_color, false);
            }

        } else {

            if (groupBean.isIsPublic()) {
                list_name.add("分享");
                list_name.add("举报");
                list_color.add(Color.rgb(51, 51, 51));
                list_color.add(Color.rgb(51, 51, 51));
                menuWindow = new TopMenuPopupWindow(activity, new View.OnClickListener() {

                    public void onClick(View v) {
                        menuWindow.dismiss();
                        switch (v.getId()) {
                            case R.id.btn_01:
                                getShareData(GroupCenterActivity.this, groupId);
                                break;
                            case R.id.btn_02:
                                ReportActivity.start(GroupCenterActivity.this, groupId, ReportType.OTHER);
                                break;
                            default:
                                break;
                        }
                    }
                }, list_name, list_color, false);
            } else {
                list_name.add("举报");
                list_color.add(Color.rgb(51, 51, 51));
                list_color.add(Color.rgb(51, 51, 51));
                menuWindow = new TopMenuPopupWindow(activity, new View.OnClickListener() {

                    public void onClick(View v) {
                        menuWindow.dismiss();
                        switch (v.getId()) {
                            case R.id.btn_01:
                                ReportActivity.start(GroupCenterActivity.this, groupId, ReportType.OTHER);
                                break;
                            default:
                                break;
                        }
                    }
                }, list_name, list_color, false);
            }
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
        Map<String, Object> map = new TreeMap<>();
        map.put("objectId", activityId);
        map.put("shareObjectType", 13);

        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.share_url_, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                MyShareBean shareBean = JSON.parseObject(result, MyShareBean.class);
                if (StringUtil.isEmpty(shareBean.getShareUrl())) return;
                SharePopupWindow popupWindow = new SharePopupWindow(activity, shareBean);
                popupWindow.showAtLocation(activity.findViewById(R.id.layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }

            @Override
            public void onFailure(String msg) {

            }
        });

    }

    /**
     * 获取社群信息
     *
     * @param groupId
     */
    public void getGroupInfo(final String groupId, LoadingType loadingType) {
        if (TextUtils.isEmpty(groupId)) return;
        Map<String, Object> map = new TreeMap<>();
        map.put("groupId", groupId);
        map.put("pageSize", 5);

        HiStudentHttpUtils.postDataByOKHttp(true, this, map, HistudentUrl.singleGroup, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                mRefreshLayout.finishLoadmore();
                mRefreshLayout.finishRefresh();
                try {
                    org.json.JSONObject object = new org.json.JSONObject(result);

                    groupBean = JSON.parseObject(object.getString("data"), GroupBean.class);
                    if (groupBean != null) {

                        HiCache.getInstance().setGroupDetailInfo(groupBean);

                        //私密社群显示遮罩
                        if (!groupBean.isIsMember() && !groupBean.isIsPublic()) {
                            normal_view.setVisibility(View.GONE);
                            private_view.setVisibility(View.VISIBLE);
                            title_righr.setVisibility(View.GONE);
                            mSendMessage.setVisibility(View.GONE);
                            isShowShade = true;
                        }
                        showUI(groupBean);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String msg) {
                mRefreshLayout.finishLoadmore();
                mRefreshLayout.finishRefresh();
            }
        }, loadingType);

    }

    /**
     * 获取社群动态
     *
     * @param groupId
     */
    public void getGroupActivitys(String groupId) {
        if (TextUtils.isEmpty(groupId)) return;
        Map<String, Object> map = new TreeMap<>();
        map.put("groupId", groupId);
        map.put("timeCursor", timeCursor);
        map.put("pageSize", 8);

        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.activities_group, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                mRefreshLayout.finishLoadmore();
                mRefreshLayout.finishRefresh();
                UserTimeModel model = DataParser.getUserTimeModel(result);
                if (model != null) {
                    List<ActionListBean> beens = ItemDataExchangeUtils.dataExchange(model.getItems());

                    if (beens.size() > 0) {
                        empty_layout.setVisibility(View.GONE);
                        timeCursor = model.getCursor();
                        list_all.addAll(beens);
                    } else {
                        if (timeCursor != 0) {
                            Toast.makeText(GroupCenterActivity.this, R.string.no_more_data, Toast.LENGTH_SHORT).show();
                        } else {
                            empty_layout.setVisibility(View.VISIBLE);
                            if (empty_layout.getChildCount() == 0)
                                empty_layout.addView(EmptyViewUtils.EmptyViewTextAndImage(R.drawable.no_state,
                                        GroupCenterActivity.this, R.string.empty_no_state));
                        }

                    }

                    actionAdapter.setJurisdiction(groupBean.isIsManager());
                    actionAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String msg) {
                mRefreshLayout.finishLoadmore();
                mRefreshLayout.finishRefresh();
            }
        }, LoadingType.NONE);

    }

    /**
     * 获取顶部图片高度后，设置滚动监听
     */
    private void initListeners() {

        group_visit_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (persions.size() > 0) {
                    GroupBean.GroupMembersListBean bean = persions.get(i);
                    if (TextUtils.isEmpty(bean.getUserId())) {
                        CommunitySignActivity.start(GroupCenterActivity.this, groupId);
                    } else {
                        PersonCenterActivity.start(GroupCenterActivity.this, bean.getUserId());
                    }
                } else {
                    CommunitySignActivity.start(GroupCenterActivity.this, groupId);
                }
            }
        });

        UtilsStyle.setOnScrollListener(mListView, new UtilsStyle.ScrollY() {
            @Override
            public void scrollY(int y) {

                int height = 200;
                int imageHeight = SystemUtil.dp2px(200);

                if (y <= imageHeight) {   //设置标题的背景颜色
                    title_righr.setTextColor(Color.argb(255, 255, 255, 255));
                    mSendMessage.setTextColor(Color.argb(255, 255, 255, 255));
                    title_left.setTextColor(Color.argb(255, 255, 255, 255));
                    title_layout.setBackgroundColor(Color.argb(0, 0, 0, 0));
                    title_line.setVisibility(View.GONE);
                } else if (y > imageHeight && y <= (imageHeight + height)) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                    float scale = (float) (y - imageHeight) / height;
                    float alpha = (255 * scale);
                    title_righr.setTextColor(Color.argb((int) alpha, 0, 0, 0));
                    mSendMessage.setTextColor(Color.argb((int) alpha, 0, 0, 0));
                    title_left.setTextColor(Color.argb((int) alpha, 0, 0, 0));
                    title_layout.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                    title_line.setVisibility(View.GONE);
                } else {    //滑动到banner下面设置普通颜色
                    title_righr.setTextColor(Color.argb(255, 0, 0, 0));
                    mSendMessage.setTextColor(Color.argb(255, 0, 0, 0));
                    title_left.setTextColor(Color.argb(255, 0, 0, 0));
                    title_layout.setBackgroundColor(Color.argb(255, 255, 255, 255));
                    title_line.setVisibility(View.VISIBLE);
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CodeNum.GROUP_SET && resultCode == CodeNum.GROUP_EXIT_RESULT) {//退出社群
            setResult(CodeNum.GROUP_EXIT_RESULT);
            finish();
        } else if (requestCode == CodeNum.GROUP_SET && resultCode == CodeNum.GROUP_DISSOLVE_RESULT) {//解散社群
            setResult(CodeNum.GROUP_DISSOLVE_RESULT);
            finish();
        } else if (requestCode == CodeNum.GROUP_SET && resultCode == CodeNum.GROUP_INFO_RESULT) {//社群信息改变
            setResult(CodeNum.GROUP_INFO_RESULT);
            timeCursor = 0;
            list_all.clear();
            persions.clear();
            getGroupInfo(groupId, LoadingType.FLOWER);
        } else if (requestCode == 13 && resultCode == RESULTCODE) {//发布随记返回
            timeCursor = 0;
            list_all.clear();
            persions.clear();
            getGroupInfo(groupId, LoadingType.FLOWER);
        } else if (resultCode == CodeNum.GROUP_MEMBER_RESULT) {
            setResult(CodeNum.GROUP_MEMBER_RESULT);
        } else if (requestCode == 333) {
            finish();
        }
    }

}
