package com.histudent.jwsoft.histudent.body.myclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.activity.ActivityMembers_Activity;
import com.histudent.jwsoft.histudent.body.find.bean.GroupBean;
import com.histudent.jwsoft.histudent.body.find.bean.GroupHuoDongDetailModel;
import com.histudent.jwsoft.histudent.body.find.bean.HuoDongDetailsModel;
import com.histudent.jwsoft.histudent.body.find.helper.GroupHelper;
import com.histudent.jwsoft.histudent.body.mine.adapter.ActionAdapter;
import com.histudent.jwsoft.histudent.body.mine.model.UserTimeModel;
import com.histudent.jwsoft.histudent.body.mine.parser.DataParser;
import com.histudent.jwsoft.histudent.body.myclass.helper.ClassHelper;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.bean.MyShareBean;
import com.histudent.jwsoft.histudent.commen.enums.LoactionType;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.ItemDataExchangeUtils;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.commen.view.MyListView2;
import com.histudent.jwsoft.histudent.commen.view.popupwindow.SharePopupWindow;
import com.histudent.jwsoft.histudent.comment2.activity.MapActivity;
import com.histudent.jwsoft.histudent.comment2.bean.AddressInforBean;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;
import com.histudent.jwsoft.histudent.comment2.utils.HuoDongCenterUtils;
import com.histudent.jwsoft.histudent.comment2.utils.SuccessEnum;
import com.histudent.jwsoft.histudent.constant.TransferKeys;
import com.histudent.jwsoft.histudent.info.persioninfo.activity.PersonCenterActivity;
import com.histudent.jwsoft.histudent.widget.scrollview.DefineScrollView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 活动详情（班级活动，社群活动）
 */

public class HuoDongCenterActivity extends BaseActivity implements View.OnClickListener {

    private String huodongId;
    private ActionAdapter adapter;
    private ArrayList<ActionListBean> mList;
    private HuoDongDetailsModel classHuoDongDetailsModel;
    private boolean isChanged;//用于标记是否进行了有效的操作，返回是否进行刷新
    private int timeCursor;
    private MyListView2 mListView;
    private DefineScrollView mRootScrollView;
    private View emptyView;
    private int type;
    private RelativeLayout parentLayout;
    private GroupHuoDongDetailModel groupDetailsModel;
    private HuoDongCenterUtils.itemsSuccessCallBack successCallBack;

    @BindView(R.id.share)
    IconView mShare;
    @BindView(R.id.srl_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.rl_title_layout)
    RelativeLayout mTitleLayout;
    @BindView(R.id.title_left)
    TextView mTitleLeft;
    @BindView(R.id.title_right)
    TextView mTitleRight;
    @BindView(R.id.tv_detail_title)
    TextView mTitleDetail;
    @BindView(R.id.view_title_line)
    View mTitleBottomLine;
    @BindView(R.id.view_title_bg)
    View mTitleBg;

    /**
     * 0:报名中
     * 1:进行中
     * 2:已结束
     * 3:已报满
     */
    private int mActiveType = -1;
    private LinearLayout mBottomLayout;
    private int mImageHeight;

    /**
     * @param activity
     * @param huodongId   活动id
     * @param type        活动类型 1： 班级活动 2：社群活动
     * @param requestCode
     */
    public static void start(Activity activity, String huodongId, int type, int requestCode) {

        Intent intent = new Intent(activity, HuoDongCenterActivity.class);
        intent.putExtra(TransferKeys.ID, huodongId);
        intent.putExtra(TransferKeys.TYPE, type);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_huo_dong_center;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        huodongId = getIntent().getStringExtra(TransferKeys.ID);
        type = getIntent().getIntExtra(TransferKeys.TYPE, 0);
        mActiveType = getIntent().getIntExtra(TransferKeys.STATUS, -1);
        boolean isCreatePerson = getIntent().getBooleanExtra(TransferKeys.CREATE, false);
        mListView = ((MyListView2) findViewById(R.id.listview));
        mRootScrollView = (DefineScrollView) findViewById(R.id.scrollView);
        emptyView = EmptyViewUtils.EmptyViewTextAndImage(R.mipmap.default_dynamic, this, R.string.have_no_persionActions);
        parentLayout = ((RelativeLayout) findViewById(R.id.layout));
        findViewById(R.id.layout_users).setOnClickListener(this);
        findViewById(R.id.title_left).setOnClickListener(this);
        mBottomLayout = (LinearLayout) findViewById(R.id.act_layout);
        IconView iconView = (IconView) findViewById(R.id.title_right);
        mListView.setEmptyView(emptyView);
        mRefreshLayout.setEnableAutoLoadmore(true);

//        if (mActiveType >=2) {
//
//            //活动进行中
//            iconView.setVisibility(View.INVISIBLE);
//            mBottomLayout.setVisibility(View.GONE);
//        } else {
//            iconView.setVisibility(View.VISIBLE);
//            mBottomLayout.setVisibility(View.VISIBLE);
//        }
//
//        //如果是活动创建人  直接隐藏底部
//        if (isCreatePerson) {
//            mBottomLayout.setVisibility(View.GONE);
//        } else {
//            mBottomLayout.setVisibility(View.VISIBLE);
//        }

        getHuoDongInfor();
        //活动的相关操作回调事件
        successCallBack = (SuccessEnum type) -> {
            isChanged = true;
            switch (type) {

                case HUODONG_CANCEL:
                    setResult();
                    break;

                case HUODONG_SIGN_QUIT:
                    getHuoDongInfor();
                    break;
            }
        };

        if (type == 1) {
            mList = new ArrayList<>();
            adapter = new ActionAdapter(this, mList, ActionAdapter.TOPIC);
            mListView.setAdapter(adapter);

            getHuoDongTimeLine(false,true);
        }
    }

    @Override
    public void doAction() {
        super.doAction();


        loadListener();
    }


    private void loadListener() {
        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getHuoDongTimeLine(true,false);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getHuoDongTimeLine(false,false);
            }
        });

        mRootScrollView.setScrollViewListener((DefineScrollView scrollView, int x, int y, int oldX, int oldY) -> {
            int height = 200;
            if (y < height / 2) {
                //设置标题的背景颜色
                mTitleLeft.setTextColor(Color.rgb(255, 255, 255));
                mTitleRight.setTextColor(Color.rgb(255, 255, 255));
                mTitleBg.setBackgroundColor(Color.rgb(0, 0, 0));
                mTitleBg.setAlpha(0);
                mTitleLeft.setAlpha(1);
                mTitleRight.setAlpha(1);
                mTitleBottomLine.setAlpha(0);
                mTitleDetail.setVisibility(View.INVISIBLE);
                mTitleBottomLine.setVisibility(View.GONE);
            } else if (y > height / 2 && y <= height) {
                //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                float alpha = (1 - (float) (height - y) / height);
                mTitleLeft.setTextColor(Color.rgb(0, 0, 0));
                mTitleRight.setTextColor(Color.rgb(0, 0, 0));
                mTitleBg.setBackgroundColor(Color.rgb(255, 255, 255));
                mTitleBg.setAlpha(alpha);
                mTitleBottomLine.setAlpha(alpha);
                mTitleDetail.setVisibility(View.INVISIBLE);
                mTitleBottomLine.setVisibility(View.GONE);
            } else if (y > height) {
                //滑动到banner下面设置普通颜色
                mTitleLeft.setTextColor(Color.rgb(0, 0, 0));
                mTitleRight.setTextColor(Color.rgb(0, 0, 0));
                mTitleBg.setBackgroundColor(Color.rgb(255, 255, 255));
                mTitleBg.setAlpha(1);
                mTitleBottomLine.setAlpha(1);
                mTitleDetail.setVisibility(View.VISIBLE);
                mTitleBottomLine.setVisibility(View.VISIBLE);
            }
        });
    }

    public int getStatusType() {
        return mActiveType;
    }


    //初始化菜单
    private void initItemsMenu() {


        switch (type) {
            case 1:

                HuoDongCenterUtils.initClassBottomMenu(this, classHuoDongDetailsModel, huodongId, parentLayout, successCallBack);
                break;

            case 2:

                HuoDongCenterUtils.initGroupBottomMenu(this, groupDetailsModel, huodongId, parentLayout, successCallBack);

                break;
        }
    }

    //获取活动的详情
    public void getHuoDongInfor() {

        switch (type) {

            case 1:
                ClassHelper.getClassHuoDongInfor(this, huodongId, new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        mRefreshLayout.finishLoadmore();
                        mRefreshLayout.finishRefresh();
                        classHuoDongDetailsModel = JSON.parseObject(result, HuoDongDetailsModel.class);

                        if (classHuoDongDetailsModel != null) {
                            updateUi();
                        }
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        mRefreshLayout.finishLoadmore();
                        mRefreshLayout.finishRefresh();
                    }
                });

                break;

            case 2:
                GroupHelper.getGroupHuoDongDetailInfor(this, huodongId, new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        mRefreshLayout.finishLoadmore();
                        mRefreshLayout.finishRefresh();

                        groupDetailsModel = JSON.parseObject(result, GroupHuoDongDetailModel.class);

                        if (groupDetailsModel != null) {
                            getGroupInfo(groupDetailsModel.getOwnerId(), LoadingType.NONE);
                            updateUi();
                        }
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        mRefreshLayout.finishLoadmore();
                        mRefreshLayout.finishRefresh();
                    }
                });
                break;
        }
    }


    //根据活动类型初始化UI
    private void updateUi() {

        switch (type) {

            case 1:
                HuoDongCenterUtils.updateClassCenterUi(this, classHuoDongDetailsModel, huodongId, parentLayout, this, successCallBack);
                break;

            case 2:
                HuoDongCenterUtils.updateGroupCenterUi(this, groupDetailsModel, huodongId, parentLayout, this, successCallBack);
                break;
        }

    }


    /**
     * 获取活动中参加报名的人数
     *
     * @return
     */
    public int getJoinPersonCount() {
        int count = 0;
        if (type == 1) {
            count = classHuoDongDetailsModel.getSignUpNum();
        } else if (type == 2) {
            count = groupDetailsModel.getSignUpNum();
        }
        return count;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {

            //开始时间
            case R.id.layout_originator:
                PersonCenterActivity.start(this, classHuoDongDetailsModel.getCreateUser());
                break;

            //开始时间
            case R.id.address:
                AddressInforBean addressInforBean = new AddressInforBean();

                if (type == 1) {
                    addressInforBean.setLatitude(classHuoDongDetailsModel.getLatitude());
                    addressInforBean.setLongitude(classHuoDongDetailsModel.getLongitude());
                    addressInforBean.setDetailStr(classHuoDongDetailsModel.getPlace());

                } else {
                    addressInforBean.setLatitude(groupDetailsModel.getLatitude());
                    addressInforBean.setLongitude(groupDetailsModel.getLongitude());
                    addressInforBean.setDetailStr(groupDetailsModel.getPlace());
                }

                MapActivity.start(this, addressInforBean, LoactionType.HUODONG);
                break;

            //分享
            case R.id.share:
                if (type == 1) {
                    getShareData(this, classHuoDongDetailsModel.getId());
                } else {
                    getShareData(this, groupDetailsModel.getId());
                }

                break;

            //菜单
            case R.id.title_right:
                initItemsMenu();
                break;

            //返回
            case R.id.title_left:

                setResult();
                break;

            //活动报名
            case R.id.act_layout:
                SignOrExit();
                break;

            case R.id.layout_users:
                gotoMemberCenter();

                break;


        }
    }

    //进入活动成员列表
    private void gotoMemberCenter() {

        switch (type) {

            //进入班级活动成员界面
            case 1:
                ActivityMembers_Activity.start(HuoDongCenterActivity.this, huodongId, 1);
                break;

            //进入社群活动成员界面
            case 2:
                ActivityMembers_Activity.start(HuoDongCenterActivity.this, huodongId, 2);
                break;
        }
    }


    //获取班级活动轨迹
    private void getHuoDongTimeLine(boolean isMore,boolean isNeedLoadingDialog) {

        if (!isMore) {
            timeCursor = 0;
            if (mList != null)
                mList.clear();
        }
        ClassHelper.getClassHuoDongTimeLine(this, huodongId, timeCursor, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                mRefreshLayout.finishLoadmore();
                mRefreshLayout.finishRefresh();

                UserTimeModel model = DataParser.getUserTimeModel(result);
                List<ActionListBean> list = ItemDataExchangeUtils.dataExchange(model.getItems());
                if (list != null) {
                    if (list.size() > 0) {
                        timeCursor = model.getCursor();
                    } else if (timeCursor != 0) {
                        Toast.makeText(HuoDongCenterActivity.this, R.string.no_more_information, Toast.LENGTH_LONG).show();
                    }
                    if (mList != null)
                        mList.addAll(list);
                }
                if (adapter != null)
                    adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String errorMsg) {
                mRefreshLayout.finishLoadmore();
                mRefreshLayout.finishRefresh();
            }
        },isNeedLoadingDialog ? LoadingType.FLOWER:LoadingType.NONE);
    }

    //加入或者退出班级
    private void SignOrExit() {

        switch (type) {

            case 1:
                ClassHelper.ClassHuoDongSignOrExit(this, huodongId, !classHuoDongDetailsModel.isIsSinuped(), new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        mRefreshLayout.finishLoadmore();
                        mRefreshLayout.finishRefresh();
                        isChanged = true;
                        getHuoDongInfor();
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        mRefreshLayout.finishLoadmore();
                        mRefreshLayout.finishRefresh();
                    }
                });
                break;

            case 2:
                GroupHelper.GroupHuoDongSignOrExit(this, huodongId, !groupDetailsModel.isIsSinuped(), new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        mRefreshLayout.finishLoadmore();
                        mRefreshLayout.finishRefresh();
                        isChanged = true;
                        getHuoDongInfor();
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        mRefreshLayout.finishLoadmore();
                        mRefreshLayout.finishRefresh();
                    }
                });


                break;


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 200 || resultCode == 0) {

            if (requestCode != 222) {
                getHuoDongInfor();
            } else {
                if (type == 1)
                    getHuoDongTimeLine(false,true);
            }

            isChanged = true;
        }
    }

    private void setResult() {

        if (isChanged) {
            setResult(200);
        } else {
            setResult(-2);
        }
        this.finish();
    }

    /**
     * 获取活动分享数据并予以分享
     *
     * @param activityId
     */
    private void getShareData(final BaseActivity activity, String activityId) {
        if (TextUtils.isEmpty(activityId)) return;
        Map<String, Object> map = new TreeMap<>();
        map.put("objectId", activityId);
        map.put("shareObjectType", 8);

        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.share_url_, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                SharePopupWindow popupWindow = new SharePopupWindow(activity, JSONObject.parseObject(result, MyShareBean.class));
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
                try {
                    org.json.JSONObject object = new org.json.JSONObject(result);

                    GroupBean groupBean = JSON.parseObject(object.getString("data"), GroupBean.class);
                    if (groupBean != null) {
                        if (groupBean.isIsPublic()) {
                            mShare.setVisibility(View.VISIBLE);
                        } else {
                            mShare.setVisibility(View.GONE);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String msg) {
            }
        }, loadingType);

    }

}
