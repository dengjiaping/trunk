package com.histudent.jwsoft.histudent.body.find.activity;
/**
 * 社群签到页面
 */

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.adapter.CommunitySignListAdapter;
import com.histudent.jwsoft.histudent.body.find.bean.CommunitySignBean;
import com.histudent.jwsoft.histudent.body.find.helper.GroupHelper;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;
import com.histudent.jwsoft.histudent.comment2.utils.PullToRefreshUtils;
import com.histudent.jwsoft.histudent.info.persioninfo.activity.PersonCenterActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CommunitySignActivity extends BaseActivity {

    private static final String TAG = CommunitySignActivity.class.getSimpleName();
    @BindView(R.id.title_left)
    LinearLayout titleLeft;
    @BindView(R.id.title_middle_text)
    TextView titleMiddleText;
    @BindView(R.id.community_sign_text)
    TextView communitySignText;
    @BindView(R.id.community_sign_listView)
    PullToRefreshListView communitySignListView;

    private List<CommunitySignBean.GroupMembersListBean> list = new ArrayList<>();
    private int pageIndex = 0;
    private static final int pageSize = 6;
    private String groupId;

    public static void start(Activity activity, String groupId) {
        Intent intent = new Intent(activity, CommunitySignActivity.class);
        intent.putExtra("groupId", groupId);
        activity.startActivity(intent);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_comunity_sign;
    }

    @Override
    public void initView() {
        titleMiddleText.setText("今日签到记录");
        initData();
        //上拉加载刷新下拉刷新
        initRefresh();
    }

    private void initData() {
        groupId = getIntent().getStringExtra("groupId");
        Log.e(TAG, "签到记录数据-------->>>>" + groupId);
        getNetData(groupId);
    }

    private void getNetData(String groupId) {
        GroupHelper.GroupSignInfo(this, groupId, pageIndex, pageSize, signCallback);
        //初始化监听
        initListeners();
    }

    private void initListeners() {
        //返回
        titleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommunitySignActivity.this.finish();
            }
        });
        communitySignListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PersonCenterActivity.start(CommunitySignActivity.this, list.get(position - 1).getUserId());
            }
        });

    }

    /**
     * 签到异步
     */
    private final HttpRequestCallBack signCallback = new HttpRequestCallBack() {
        @Override
        public void onSuccess(String result) {
            Log.e(TAG, "签到记录数据-------->>>>" + result);
            communitySignListView.onRefreshComplete();//结束上下拉刷新加载
            CommunitySignBean data = JSON.parseObject(result, CommunitySignBean.class);//解析数据
//            communitySignText.setText(data.getToDayVisitNum()+"/"+data.getGroupMemberNum()+" 人今天签到");
            communitySignText.setText(data.getToDayVisitNum() + " 人今天签到");
            list.addAll(data.getGroupMembersList());//绑定数据源
            if (list.size() == 0) {
                communitySignListView.setEmptyView(EmptyViewUtils.EmptyViewTextAndImage(R.drawable.no_make_money,
                        CommunitySignActivity.this, R.string.empty_no_sign));
            }
            communitySignListView.setAdapter(new CommunitySignListAdapter(CommunitySignActivity.this, list));
        }

        @Override
        public void onFailure(String errorMsg) {
            Log.e(TAG, "签到记录数据Failure-------->>>>" + errorMsg);
        }
    };

    private void initRefresh() {
        PullToRefreshUtils.initPullToRefreshListView2(communitySignListView);
        communitySignListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageIndex = 0;
                list.clear();
                getNetData(groupId);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageIndex++;
                getNetData(groupId);
            }
        });
    }

}
