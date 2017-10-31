package com.histudent.jwsoft.histudent.body.message.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.adapter.OfficialChatAdapter;
import com.histudent.jwsoft.histudent.body.message.uikit.session.activity.TeamMessageActivity;
import com.histudent.jwsoft.histudent.body.message.uikit.session.extension.CustomAttachmentType;
import com.histudent.jwsoft.histudent.body.message.uikit.session.fragment.ChatType;
import com.histudent.jwsoft.histudent.body.message.uikit.session.fragment.RecentContactsModel;
import com.histudent.jwsoft.histudent.body.myclass.activity.HuoDongCenterActivity;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.MyWebActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.helper.WebViewHelper;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by liuguiyu-pc on 2016/7/4.
 * 系统消息展示
 */
public class OfficialChatActivity extends BaseActivity {
    private PullToRefreshListView listView;
    private TextView title;
    private IconView right_image;
    private OfficialChatAdapter adapter;
    private List<RecentContactsModel> list;
    private View emptyView;
    private int flag = 0;
    private String accountId;

    @BindView(R.id.srl_refresh_layout)
    RefreshLayout mRefresh;

    /**
     * @param activity
     * @param flag
     */
    public static void start(Activity activity, int flag, String accountId) {
        Intent intent = new Intent(activity, OfficialChatActivity.class);
        intent.putExtra("flag", flag);
        intent.putExtra("accountId", accountId);
        activity.startActivity(intent);
    }

    @Override
    public int setViewLayout() {
        return R.layout.chat_activity;
    }

    @Override
    public void initView() {
        flag = getIntent().getIntExtra("flag", 0);
        accountId = getIntent().getStringExtra("accountId");

        list = new ArrayList<>();
        adapter = new OfficialChatAdapter(this, list, flag);

        listView = (PullToRefreshListView) findViewById(R.id.chat_list);
        title = (TextView) findViewById(R.id.title_middle_text);
        right_image = (IconView) findViewById(R.id.title_right_image);
        listView.setMode(PullToRefreshBase.Mode.DISABLED);
        listView.setAdapter(adapter);
        mRefresh.setEnableLoadmore(false);
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getDatas();
            }
        });
        getDatas();
    }

    @Override
    public void doAction() {
        super.doAction();

        switch (flag) {
            case ChatType.SYSTEMINFO:
                title.setText("系统消息");
                emptyView = EmptyViewUtils.EmptyViewTextAndImage(R.drawable.no_join_request, this, R.string.empty_no_message);
                listView.setEmptyView(emptyView);
                break;
            case ChatType.SUBSCIBR:
                emptyView = EmptyViewUtils.EmptyViewTextAndImage(R.drawable.no_join_request, this, R.string.empty_no_order);
                listView.setEmptyView(emptyView);
                title.setText("订阅号");
                break;
            case ChatType.ACTION:
                title.setText("官方活动");
                emptyView = EmptyViewUtils.EmptyViewTextAndImage(R.drawable.no_join_request, this, R.string.empty_no_group);
                listView.setEmptyView(emptyView);
                break;
        }

        right_image.setVisibility(View.VISIBLE);
        right_image.setText(R.string.icon_id);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RecentContactsModel msgModel = list.get(i - 1);
                switch (msgModel.getImmsgtype()) {
                    case CustomAttachmentType.HomeWork://家庭作业
                        MyWebActivity.start(OfficialChatActivity.this, msgModel.getUrl());
                        break;
                    case CustomAttachmentType.HUODONG_G://班级活动
                        if (msgModel.getOpenmode() == 1) {
                            MyWebActivity.start(OfficialChatActivity.this, msgModel.getUrl());
                        } else if (msgModel.getOpenmode() == 2) {
                            HuoDongCenterActivity.start(OfficialChatActivity.this, msgModel.getOpenParam(), 1, 0);
                        }
                        break;
                    case CustomAttachmentType.HUODONG_C://社群活动
                        if (msgModel.getOpenmode() == 1) {
                            MyWebActivity.start(OfficialChatActivity.this, msgModel.getUrl());
                        } else if (msgModel.getOpenmode() == 2) {
                            HuoDongCenterActivity.start(OfficialChatActivity.this, msgModel.getOpenParam(), 2, 20);
                        }
                        break;
                    default:
                        MyWebActivity.start(OfficialChatActivity.this, WebViewHelper.checkUrl(msgModel.getUrl()));
                        break;
                }
            }
        });

    }

    private void getDatas() {

        List<RecentContactsModel> recentContactsModels =HiCache.getInstance().getSystemNotification(flag);
        mRefresh.finishRefresh();
        if (recentContactsModels != null) {
            list.clear();
            list.addAll(recentContactsModels);
            adapter.notifyDataSetChanged();
            scrollMyListViewToBottom();
        }
    }

    private void scrollMyListViewToBottom() {
        listView.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                listView.getRefreshableView().setSelection(adapter.getCount() - 1);
            }
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

                OfficialCentreActivity.start(this, flag);

                break;
        }
    }

}
