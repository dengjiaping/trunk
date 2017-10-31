package com.histudent.jwsoft.histudent.body.myclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.model.ClassModel;
import com.histudent.jwsoft.histudent.body.myclass.adapter.NoticesAdapter;
import com.histudent.jwsoft.histudent.body.myclass.bean.NoticesBean;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2017/5/3.
 * 通知列表
 */

public class NoticeListActivity extends BaseActivity {

    private TextView title, title_right;
    private PullToRefreshListView listView;
    private int pageIndex;
    private List<NoticesBean.ItemsBean> itemsBeens;
    private NoticesAdapter adapter;
    private String classId;
    private boolean isAdmin;
    private SmartRefreshLayout mRefreshLayout;
    private IconView mRightImg;

    public static void start(Activity activity, String classId) {
        Intent intent = new Intent(activity, NoticeListActivity.class);
        intent.putExtra("classId", classId);
        activity.startActivity(intent);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_notices;
    }

    @Override
    public void initView() {
        classId = getIntent().getStringExtra("classId");
        itemsBeens = new ArrayList<>();
        title = (TextView) findViewById(R.id.title_middle_text);
        title_right = (TextView) findViewById(R.id.title_right_text);
        mRightImg = (IconView) findViewById(R.id.title_right_image);
        listView = (PullToRefreshListView) findViewById(R.id.like_list);
        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.srl_refresh_layout);
        adapter = new NoticesAdapter(this, itemsBeens);
    }

    @Override
    public void doAction() {
        super.doAction();
        title.setText("通知");
        listView.setMode(PullToRefreshBase.Mode.DISABLED);
        listView.setAdapter(adapter);
        getLikesData(true);

        ClassModel classModel = HiCache.getInstance().getClassDetailInfo();
        if (classModel != null) {
            if (classModel.isIsAdmin()) {
                mRightImg.setText(R.string.icon_submit);
                isAdmin = true;
            }
        }

        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getLikesData(false);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageIndex = 0;
                itemsBeens.clear();
                getLikesData(false);
            }
        });
        listView.setOnItemClickListener((AdapterView<?> adapterView, View view, int i, long l) -> {
            NoticeDetailActivity.start(NoticeListActivity.this, itemsBeens.get(i - 1).getNotifyId(), itemsBeens.get(i - 1).isSend(),isAdmin);
        });
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;

            case R.id.title_right:
                if (isAdmin)
                    NoticePublishActivity.start(this, 100);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            pageIndex = 0;
            itemsBeens.clear();
            getLikesData(true);
        }
    }

    /**
     * 通知列表
     */
    private void getLikesData(boolean isNeedLoading) {
        Map<String, Object> map = new TreeMap<>();
        map.put("classId", classId);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", 8);

        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.notices_list_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadmore();
                NoticesBean bean = JSON.parseObject(result, NoticesBean.class);
                if (bean != null && bean.getItems().size() > 0) {
                    pageIndex++;
                    itemsBeens.addAll(bean.getItems());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                listView.onRefreshComplete();
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadmore();
            }
        }, isNeedLoading ? LoadingType.FLOWER : LoadingType.NONE);

    }

}
