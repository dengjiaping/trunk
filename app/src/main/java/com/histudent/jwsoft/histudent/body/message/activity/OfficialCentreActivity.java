package com.histudent.jwsoft.histudent.body.message.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.adapter.OfficialCentreAdapter;
import com.histudent.jwsoft.histudent.body.message.model.SystemInfoBean;
import com.histudent.jwsoft.histudent.body.message.uikit.session.extension.CustomAttachmentType;
import com.histudent.jwsoft.histudent.body.message.uikit.session.fragment.ChatType;
import com.histudent.jwsoft.histudent.body.message.uikit.session.fragment.RecentContactsModel;
import com.histudent.jwsoft.histudent.body.myclass.activity.HuoDongCenterActivity;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.HTWebActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.enums.ReportType;
import com.histudent.jwsoft.histudent.commen.helper.WebViewHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.commen.view.popupwindow.TopMenuPopupWindow;
import com.histudent.jwsoft.histudent.comment2.activity.ReportActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by liuguiyu-pc on 2016/6/23.
 * 系统中心
 */
public class OfficialCentreActivity extends BaseActivity {


    private TopMenuPopupWindow mTopMenuPopupWindow;
    private SystemInfoBean mSystemInfoBean;
    private int mPageIndex = 0;
    private TextView mPersonCount, mOfficialPageName;
    private int mFlag = 0;
    private HiStudentHeadImageView mHiStudentHeadImageView;
    private OfficialCentreAdapter mOfficialCentreAdapter = null;
    private final List<SystemInfoBean.ItemsBean> mItemsBeanList = new ArrayList<>();
    @BindView(R.id.officialentre_list)
    PullToRefreshListView mPullToRefreshListView;
    @BindView(R.id.srl_refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    /**
     * 0：官方活动 1：系统消息
     *
     * @param activity
     * @param flag
     */
    public static void start(Activity activity, int flag) {
        Intent intent = new Intent(activity, OfficialCentreActivity.class);
        intent.putExtra("flag", flag);
        activity.startActivity(intent);
    }

    @Override
    public int setViewLayout() {
        return R.layout.officialentre_activity_body;
    }

    @Override
    public void initView() {
        mFlag = getIntent().getIntExtra("flag", -1);
        mOfficialCentreAdapter = new OfficialCentreAdapter(this, mItemsBeanList, mFlag);
        View headView = LayoutInflater.from(this).inflate(R.layout.msg_active_head, null);
        mOfficialPageName = headView.findViewById(R.id.officialentre_activity_name);
        mPersonCount = headView.findViewById(R.id.pe_num);
        mHiStudentHeadImageView = headView.findViewById(R.id.officialentre_activity_image);

        mRefreshLayout.setEnableAutoLoadmore(true);
        mRefreshLayout.setEnableLoadmore(true);
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);
        mPullToRefreshListView.getRefreshableView().addHeaderView(headView, null, false);
        mPullToRefreshListView.setAdapter(mOfficialCentreAdapter);
        getData();
        loadListener();
        updateUI();

    }

    private void loadListener() {
        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPageIndex++;
                getData();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mItemsBeanList.clear();
                mPageIndex = 0;
                getData();
            }
        });

        mPullToRefreshListView.setOnItemClickListener((AdapterView<?> adapterView, View view, int i, long l) -> {
            SystemInfoBean.ItemsBean msgModel = mItemsBeanList.get(i - 2);
            switch (msgModel.getNeteaseImMsgType()) {
                case CustomAttachmentType.HomeWork://家庭作业
                    HTWebActivity.start(OfficialCentreActivity.this, msgModel.getUrl());
                    break;
                case CustomAttachmentType.HUODONG_G://班级活动
                    if (msgModel.getOpenMode() == 1) {
                        HTWebActivity.start(OfficialCentreActivity.this, msgModel.getUrl());
                    } else if (msgModel.getOpenMode() == 2) {
                        HuoDongCenterActivity.start(OfficialCentreActivity.this, msgModel.getOpenParam(), 1, 0);
                    }
                    break;
                case CustomAttachmentType.HUODONG_C://社群活动
                    if (msgModel.getOpenMode() == 1) {
                        HTWebActivity.start(OfficialCentreActivity.this, msgModel.getUrl());
                    } else if (msgModel.getOpenMode() == 2) {
                        HuoDongCenterActivity.start(OfficialCentreActivity.this, msgModel.getOpenParam(), 2, 20);
                    }
                    break;

                default:
                    HTWebActivity.start(OfficialCentreActivity.this, WebViewHelper.checkUrl(msgModel.getUrl()));
                    break;
            }
        });
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            mTopMenuPopupWindow.dismiss();
            switch (v.getId()) {
                case R.id.btn_01://举报

//                    Toast.makeText(getApplicationContext(), "--->举报", Toast.LENGTH_SHORT).show();
                    ReportActivity.start(OfficialCentreActivity.this, mSystemInfoBean.getInfo().getOfficialId(), ReportType.OTHER);

                    break;
                case R.id.btn_02://清空内容

//                    Toast.makeText(getApplicationContext(), "--->清空内容", Toast.LENGTH_SHORT).show();

                    break;
                case R.id.btn_03://不再关注

//                    Toast.makeText(getApplicationContext(), "--->不再关注", Toast.LENGTH_SHORT).show();

                    break;
                default:
                    break;
            }
        }

    };

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.title_left_image:
                finish();
                break;
        }
    }

    private void getData() {
        Map<String, Object> map = new HashMap<>();
        String officialId = "";
        switch (mFlag) {
            case ChatType.SYSTEMINFO:
                officialId = "00000000-0000-0000-0000-000000000000";
                break;
            case ChatType.SUBSCIBR:
                break;
            case ChatType.ACTION:
                officialId = "058728de-6aa8-4b50-b4fe-8dd33496d652";
                break;
        }
        map.put("officialId", officialId);
        map.put("pageIndex", String.valueOf(mPageIndex));
        map.put("pageSize", String.valueOf(10));
        HiStudentHttpUtils.postDataByOKHttp(OfficialCentreActivity.this, map, HistudentUrl.systemInfo_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadmore();
                mSystemInfoBean = JSON.parseObject(result, SystemInfoBean.class);
                updateUI();
                if (mSystemInfoBean != null && mSystemInfoBean.getItems().size() > 0) {
                    mItemsBeanList.addAll(mSystemInfoBean.getItems());
                    showOfficialCount();
                    mOfficialCentreAdapter.notifyDataSetChanged();
                } else {
                    if (mPageIndex > 1) {
                        mPageIndex--;
                    }
                    Toast.makeText(OfficialCentreActivity.this, R.string.no_more_data, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String msg) {
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadmore();
                Toast.makeText(OfficialCentreActivity.this, R.string.get_data_faild, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showOfficialCount() {
        List<RecentContactsModel> recentContactsModels = HiCache.getInstance().getSystemNotification(3);
        int notifyCount = recentContactsModels == null ? 0 : recentContactsModels.size();
        mPersonCount.setText(String.valueOf(mSystemInfoBean.getItemCount() + notifyCount));
    }

    private void updateUI() {
        switch (mFlag) {
            case ChatType.SYSTEMINFO:
                mOfficialPageName.setText("系统通知");
//                text.setText("认证信息:系统账号");
                mHiStudentHeadImageView.setImageResource(R.mipmap.system_logo);
                break;
            case ChatType.SUBSCIBR:
                mOfficialPageName.setText("订阅号");
//                text.setText("认证信息:订阅号");
                mHiStudentHeadImageView.setImageResource(R.mipmap.subscibr);
                break;
            case ChatType.ACTION:
                mOfficialPageName.setText("Hi同学官方活动");
//                text.setText("认证信息:官方账号");
                mHiStudentHeadImageView.setImageResource(R.mipmap.offical_action);
                break;
            default:
                break;
        }
    }

}
