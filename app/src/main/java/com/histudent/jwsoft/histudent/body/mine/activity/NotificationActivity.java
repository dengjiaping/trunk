package com.histudent.jwsoft.histudent.body.mine.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.model.NoticeBean;
import com.histudent.jwsoft.histudent.body.mine.adapter.NotificationAdapter;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.CommentActivity;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.constant.Const;
import com.histudent.jwsoft.histudent.entity.NotificationClickEvent;
import com.histudent.jwsoft.histudent.widget.MostRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2017/7/21.
 */

public class NotificationActivity extends BaseActivity {
    @BindView(R.id.message_list_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.title_middle_text)
    TextView mTitle;
    @BindView(R.id.title_left)
    LinearLayout mBack;
    @BindView(R.id.srl_refresh_layout)
    SmartRefreshLayout mRefresh;

    @OnClick(R.id.title_left)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
        }
    }

    private NotificationAdapter mAdapter;
    private LinearLayoutManager mManager;
    private int type;
    private static final int PAGE_SIZE = 20;
    private static final int PULL_DOWN = 0;
    private static final int PULL_UP = 1;
    private int loading = 0;
    private int cursor=0;

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_message_list;
    }

    @Override
    public void initView() {
        type = getIntent().getIntExtra("type", 5);
        initTitle();
        initRefresh();
        initRecycler();
    }

    private void initRefresh() {

        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                loading = PULL_DOWN;
                cursor=0;
                getNoticeList();
            }
        });

        mRefresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                loading = PULL_UP;
                getNoticeList();
            }
        });
    }

    @Subscribe
    public void onEvent(NotificationClickEvent event){

        NoticeBean.ItemsBean itemsBean =  event.mNotice;
        if (itemsBean != null) {
            if (itemsBean.getInfoStatus() == 1) {
                ReminderHelper.getIntentce().ToastShow(NotificationActivity.this, "该动态已被删除");
            } else if (itemsBean.getInfoStatus() == 2) {

            } else {
                CommentActivity.start(NotificationActivity.this, itemsBean.getActId(),event.position);
            }
        }
    }

    private void initTitle() {
        switch (type) {
            case Const.NOTICE_TYPE_MIME:
                mTitle.setText("@我");
                break;
            case Const.NOTICE_TYPE_COMMENT:
                mTitle.setText("评论");
                break;
            case Const.NOTICE_TYPE_PRAISE:
                mTitle.setText("点赞");
                break;
        }
    }

    private void initRecycler() {
        mAdapter = new NotificationAdapter(this);
        mManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mManager);
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void doAction() {
        super.doAction();
        getNoticeList();
    }


    /**
     * 获取通知列表
     */
    private void getNoticeList() {
        Map<String, Object> map = new HashMap();
        map.put("pmType", type);
        map.put("timeCursor", cursor);
        map.put("pageSize", PAGE_SIZE);

        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.noticeList, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                NoticeBean bean = JSON.parseObject(result, NoticeBean.class);
                if (bean != null) {
                    switch (loading) {
                        case PULL_DOWN:
                            mRefresh.finishRefresh(500);
                            cursor =bean.getCursor();
                            if (cursor==0){
                                mRefresh.setEnableLoadmore(false);
                            }else{
                                mRefresh.setEnableLoadmore(true);
                            }
                            List<NoticeBean.ItemsBean> itemsBeans = bean.getItems();
                            mAdapter.setMessages(itemsBeans);

                            break;
                        case PULL_UP:

                            cursor = bean.getCursor();
                            mRefresh.finishLoadmore();
                            if (cursor==0){
                                mRefresh.setEnableLoadmore(false);
                            }else{
                                mRefresh.setEnableLoadmore(true);
                            }
                            mAdapter.addMessages(bean.getItems());
                            break;
                    }
                }

            }

            @Override
            public void onFailure(String errorMsg) {
                switch (loading) {
                    case PULL_DOWN:
                        mRefresh.finishRefresh(500);
                        break;
                    case PULL_UP:
                        mRefresh.finishLoadmore();
                        break;
                }
            }
        }, LoadingType.NONE);

    }



}
