package com.histudent.jwsoft.histudent.body.find.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.adapter.PersonHuoDongAdapter;
import com.histudent.jwsoft.histudent.body.find.bean.PersonalHuoDongBean;
import com.histudent.jwsoft.histudent.body.myclass.activity.HuoDongCenterActivity;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;
import com.histudent.jwsoft.histudent.comment2.utils.PullToRefreshUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的活动页面（班级活动，社群活动）
 */

public class PersonalHuoDongActivity extends BaseActivity implements View.OnClickListener {

    private PersonHuoDongAdapter adapter;
    private List<PersonalHuoDongBean> list;
    private int currentPageIndex;
    private PullToRefreshListView listView;
    private String id;
    private int type;

    @BindView(R.id.srl_refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mRefreshLayout.finishLoadmore();
            mRefreshLayout.finishRefresh();
            switch (msg.what) {
                case 0:
                    String content = (String) msg.obj;
                    if (msg.arg1 == HiWorldCache.SUCCESS) {
                        List<PersonalHuoDongBean> list1 = JSON.parseArray(
                                JSON.parseObject(content).getJSONArray("items").toString(), PersonalHuoDongBean.class);

                        if (list1 != null) {

                            list.addAll(list1);

                        }

                    }

                    if (list.size() == 0) {
                        listView.setEmptyView(EmptyViewUtils.EmptyViewTextAndImage(R.drawable.no_join_or_take_group,
                                PersonalHuoDongActivity.this, R.string.empty_no_report));
                    }
                    if (listView.isRefreshing()) {
                        listView.onRefreshComplete();
                    }
                    adapter.notifyDataSetChanged();

                    Log.e("MyActivity", content);
                    break;
            }
        }
    };

    /**
     * @param activity
     * @param id       班级id　或者社群id
     * @param type     　活动类型　1，班级活动 2，社群活动
     */

    public static void start(Activity activity, String id, int type) {
        Intent intent = new Intent(activity, PersonalHuoDongActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("type", type);
        activity.startActivityForResult(intent, 200);
    }


    @Override
    public int setViewLayout() {
        return R.layout.activity_my_activity_;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("id");
        type = getIntent().getIntExtra("type", type);
        listView = ((PullToRefreshListView) findViewById(R.id.my_activity_list_view));
        listView.getRefreshableView().setDividerHeight(0);
        ((TextView) findViewById(R.id.title_middle_text)).setText("我的活动");
        list = new ArrayList<>();

        adapter = new PersonHuoDongAdapter(list, this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            PersonalHuoDongBean bean = list.get(position - 1);
            String groupId = bean.getId();
            HuoDongCenterActivity.start(PersonalHuoDongActivity.this, groupId, type, 111);
        });

        mRefreshLayout.setEnableAutoLoadmore(true);

        initPullToRefreshList();
        getActivity(false, true);

    }

    private void initPullToRefreshList() {

        PullToRefreshUtils.initPullToRefreshListView(listView);
        listView.setMode(PullToRefreshBase.Mode.DISABLED);
        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                currentPageIndex++;
                getActivity(true, false);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getActivity(false, false);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_left:
                this.finish();
                break;
        }
    }

    //获取我的班级活动数据
    private void getMyClassHuoDongList(boolean isMore, boolean isNeedLoading) {
        if (!isMore) {
            currentPageIndex = 0;
            list.clear();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("pageIndex", String.valueOf(currentPageIndex));
        map.put("classId", id);
        map.put("pagesize", String.valueOf(8));

        HiWorldCache.postHttpData(this, handler, 0, HistudentUrl.myClassHuoDongList, map, HiWorldCache.Quarry, true, isNeedLoading);
    }


    /**
     * 获取我的社群活动数据
     *
     * @param isNeedLoading 是否需要加载框
     * @param isMore        是否是加载更多
     */
    private void getMyGroupHuoDongList(boolean isMore, boolean isNeedLoading) {

        if (!isMore) {
            currentPageIndex = 0;
            list.clear();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("pageIndex", currentPageIndex + "");
        map.put("groupId", id);
        map.put("pagesize", 8 + "");

        HiWorldCache.postHttpData(this, handler, 0, HistudentUrl.getMyGroupHuoDongList, map, HiWorldCache.Quarry, true, isNeedLoading);
    }

    private void getActivity(boolean isMore, boolean isNeedLoading) {

        switch (type) {

            //班级活动
            case 1:
                getMyClassHuoDongList(isMore, isNeedLoading);
                break;

            //社群活动
            case 2:
                getMyGroupHuoDongList(isMore, isNeedLoading);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 || resultCode == 200 || resultCode == 0) {
            getActivity(false, true);
        }
    }
}

