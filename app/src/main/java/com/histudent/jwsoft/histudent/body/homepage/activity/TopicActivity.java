package com.histudent.jwsoft.histudent.body.homepage.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.hiworld.activity.EssayActivity;
import com.histudent.jwsoft.histudent.body.hiworld.bean.TopicModel;
import com.histudent.jwsoft.histudent.body.homepage.bean.TopicActionBean;
import com.histudent.jwsoft.histudent.body.homepage.bean.TopicInfoBean;
import com.histudent.jwsoft.histudent.body.mine.activity.ActionBaseActivity;
import com.histudent.jwsoft.histudent.body.mine.adapter.ActionAdapter;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.bean.MyShareBean;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.ItemDataExchangeUtils;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.commen.view.popupwindow.SharePopupWindow;
import com.histudent.jwsoft.histudent.view.widget.refresh.DefinedWeChatHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuguiyu-pc on 2017/4/21.
 * 话题界面
 */

public class TopicActivity extends ActionBaseActivity {

    private PullToRefreshListView topic_list;
    private View headView;
    private ImageView topic_cover;
    private TextView topic_introduce, title, topic_tag, topic_num;
    private int timeCursor;
    private String tagName;
    private ActionAdapter adapter_topic;
    private List<ActionListBean> list_topics;
    private IconView title_right;
    private TopicInfoBean bean;
    private TextView new_action;
    private MyHandler handler;

    @BindView(R.id.srl_refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    public static void start(Context context, String tagName) {
        Intent intent = new Intent(context, TopicActivity.class);
        intent.putExtra("tagName", tagName);
        context.startActivity(intent);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_topic;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        handler = new MyHandler();
        tagName = getIntent().getStringExtra("tagName");
        list_topics = new ArrayList<>();
        topic_list = (PullToRefreshListView) findViewById(R.id.topic_list);
        headView = LayoutInflater.from(this).inflate(R.layout.headview_topic, null);
        topic_cover = headView.findViewById(R.id.topic_cover);
        title_right = (IconView) findViewById(R.id.title_right_image);
        topic_introduce = headView.findViewById(R.id.topic_introduce);
        topic_tag = headView.findViewById(R.id.topic_tag);
        topic_num = headView.findViewById(R.id.topic_num);
        title = (TextView) findViewById(R.id.title_middle_text);
        new_action = (TextView) findViewById(R.id.new_action);
        adapter_topic = new ActionAdapter(this, list_topics, ActionAdapter.TOPIC);

        mRefreshLayout.setEnableAutoLoadmore(true);
        mRefreshLayout.setRefreshHeader(new DefinedWeChatHeader(this));
        mRefreshLayout.autoRefresh();
        mRefreshLayout.setEnableHeaderTranslationContent(false);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

    }

    @Override
    public void doAction() {
        super.doAction();

        title.setText("话题");
        title_right.setText(R.string.icon_forward);

        topic_list.setMode(PullToRefreshBase.Mode.DISABLED);
        topic_list.getRefreshableView().addHeaderView(headView);
        topic_list.setAdapter(adapter_topic);
        String topicResult = HiCache.getInstance().getHttpDataInDB(HistudentUrl.topicActivities_url,tagName);
        if (!TextUtils.isEmpty(topicResult)) {
            displayTopic(topicResult);
        }
        String topicInfoResult = HiCache.getInstance().getHttpDataInDB(HistudentUrl.topicTagInfo_url,tagName);
        if (!TextUtils.isEmpty(topicInfoResult)) {
            displayTopicInfo(topicInfoResult);
        }


        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getTopicCommentData();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                reFreshData();

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
            handler.removeMessages(0);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void deletAction(ActionAdapter.DeletAction action) {
//        list_topics.clear();
//        timeCursor = 0;
//        getTopicInfo(tagName);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;

            case R.id.title_right:
                showShareWind();
                break;

            case R.id.join:
                EssayActivity.start(this, 222, new TopicModel(tagName), HiCache.getInstance().getLoginUserInfo().getUserId());
                break;

            case R.id.new_action:
                reFreshData();
                break;
        }
    }

    /**
     * 获取话题评论数据
     */
    private void getTopicCommentData() {
        Map<String, Object> map = new TreeMap<>();
        map.put("tagName", tagName);
        map.put("timeCursor", timeCursor);
        map.put("pageSize", 8);
        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.topicActivities_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                mRefreshLayout.finishLoadmore();
                mRefreshLayout.finishRefresh();
                displayTopic(result);
                HiCache.getInstance().saveHttpDataIntoDB(HistudentUrl.topicActivities_url,tagName, result);
            }

            @Override
            public void onFailure(String errorMsg) {
                mRefreshLayout.finishLoadmore();
                mRefreshLayout.finishRefresh();
            }
        }, LoadingType.NONE);
    }

    private void displayTopic(String result){
        if (timeCursor == 0) {
            list_topics.clear();
        }
        TopicActionBean bean = JSON.parseObject(result, TopicActionBean.class);
        List<TopicActionBean.ItemsBean> items = bean.getItems();
        if (items != null) {
            if (items.size() > 0) {
                timeCursor = bean.getCursor();
                List<ActionListBean> lists = ItemDataExchangeUtils.dataExchange_Topic(items);
                list_topics.addAll(lists);
                adapter_topic.notifyDataSetChanged();
            } else {
                if (timeCursor != 0)
                    Toast.makeText(TopicActivity.this, R.string.no_more_data, Toast.LENGTH_SHORT).show();
            }
        }

        getUnreadCount(tagName);
    }

    /**
     * 话题详情介绍
     */
    private void getTopicInfo(final String tagName) {
        if (TextUtils.isEmpty(tagName)) return;
        Map<String, Object> map = new TreeMap<>();
        map.put("tagName", tagName);
        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.topicTagInfo_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                displayTopicInfo(result);
                HiCache.getInstance().saveHttpDataIntoDB(HistudentUrl.topicTagInfo_url,tagName, result);

            }

            @Override
            public void onFailure(String errorMsg) {

            }
        }, LoadingType.NONE);
    }

    private void displayTopicInfo(String result){
        bean = JSON.parseObject(result, TopicInfoBean.class);

        if (TextUtils.isEmpty(bean.getFeaturedImage())) {
            topic_cover.setBackgroundResource(R.drawable.topic_def_bg);
        } else {
            CommonGlideImageLoader.getInstance().displayNetImage(TopicActivity.this, bean.getFeaturedImage(), topic_cover);
        }

        topic_introduce.setText(bean.getTagDescription());
        topic_tag.setText("# " + tagName + " #");
        topic_num.setText(bean.getOwnerCount() + "人参与");
    }

    /**
     * 获取未读话题动态
     */
    private void getUnreadCount(final String tagName) {
        if (TextUtils.isEmpty(tagName)) return;
        Map<String, Object> map = new TreeMap<>();
        map.put("tagName", tagName);
        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.unreadCount_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                if (!"0".equals(result)) {
                    new_action.setVisibility(View.VISIBLE);
                }
                handler.sendEmptyMessageDelayed(0, 20 * 1000);

            }

            @Override
            public void onFailure(String errorMsg) {

            }
        }, LoadingType.NONE);
    }

    /**
     * 显示分享界面
     */
    private void showShareWind() {
        if (bean == null) return;
        MyShareBean shareBean = new MyShareBean(bean.getTagId(), 10, bean.getTagName(), bean.getTagDescription(), bean.getHtmlUrl(), bean.getSmallPic());
        SharePopupWindow popupWindow = new SharePopupWindow(this, shareBean);
        popupWindow.showAtLocation(findViewById(R.id.layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 200) {
            timeCursor = 0;
            getTopicInfo(tagName);
        }
    }

    @Override
    protected void reFreshActionList(int position, ActionListBean bean) {
        list_topics.set(position, bean);
        adapter_topic.notifyDataSetChanged();
    }

    @Override
    protected void reMoveAction(int position) {
        list_topics.clear();
        timeCursor = 0;
        getTopicInfo(tagName);
    }

    /**
     * 刷新
     */
    private void reFreshData() {
        list_topics.clear();
        timeCursor = 0;
        new_action.setVisibility(View.GONE);
        getTopicCommentData();
        getTopicInfo(tagName);
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    getUnreadCount(tagName);
                    break;
            }
        }
    }

}
