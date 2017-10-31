package com.histudent.jwsoft.histudent.info.persioninfo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.hiworld.activity.LogActivity;
import com.histudent.jwsoft.histudent.body.mine.activity.ActionBaseActivity;
import com.histudent.jwsoft.histudent.body.mine.adapter.ActionAdapter;
import com.histudent.jwsoft.histudent.body.mine.model.CurrentUserDetailInfoModel;
import com.histudent.jwsoft.histudent.body.mine.model.UserBlogItemModel;
import com.histudent.jwsoft.histudent.body.mine.parser.DataParser;
import com.histudent.jwsoft.histudent.commen.activity.CommentActivity;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.utils.ItemDataExchangeUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;
import com.histudent.jwsoft.histudent.info.persioninfo.PersionHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuguiyu-pc on 2017/4/21.
 * 日志页面
 */

public class PersonLogActivity extends ActionBaseActivity {

    private PullToRefreshListView mPullToRefreshListView;
    private ActionAdapter adapter_log;
    private List<ActionListBean> list_logs;
    private TextView title_right;
    private int timeCursor;
    private TextView title;
    private MyHandler handler;
    private boolean isMySelfe;
    private SmartRefreshLayout mRefreshLayout;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, PersonLogActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int setViewLayout() {
        return R.layout.fragment_persion_log;
    }

    @Override
    public void initView() {
        handler = new MyHandler();
        list_logs = new ArrayList<>();
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.persion_log_list);
        title = (TextView) findViewById(R.id.title_middle_text);
        title_right = (TextView) findViewById(R.id.title_right_text);
        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.srl_refresh_layout);
        mRefreshLayout.setEnableAutoLoadmore(true);
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);
        adapter_log = new ActionAdapter(this, list_logs, ActionAdapter.LOG);
        mPullToRefreshListView.setAdapter(adapter_log);

        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);

    }

    @Override
    public void doAction() {
        super.doAction();

        CurrentUserDetailInfoModel model = HiCache.getInstance().getUserDetailInfo();
        isMySelfe = SystemUtil.isOneselfIn(model.getUserId());

        if (isMySelfe) {
            title.setText("我的日志");
            title_right.setText("写日志");
        } else {
            int statue = model.getProfile().getSex();
            title.setText(statue == 0 ? "TA的日志" : statue == 2 ? "她的日志" : "他的日志");
        }

        mRefreshLayout.setOnRefreshListener((RefreshLayout refreshLayout) -> EventBus.getDefault().post(true));

        mRefreshLayout.setOnLoadmoreListener((RefreshLayout refreshLayout) ->
                PersionHelper.getInstance().getPersionBlogs(PersonLogActivity.this, timeCursor, handler, 3, false,false)
        );

        PersionHelper.getInstance().getPersionBlogs(PersonLogActivity.this, 0, handler, 3, true,true);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reFresh(Boolean flag) {
        if (flag) {
            list_logs.clear();
            timeCursor = 0;
            PersionHelper.getInstance().getPersionBlogs(PersonLogActivity.this, timeCursor, handler, 3, false,false);
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;

            case R.id.title_right:
                if (isMySelfe)
                    LogActivity.start(this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CommentActivity.REQUESTCODE && resultCode == CommentActivity.RESULTCODE)
            adapter_log.notifyDataSetChanged();
    }

    @Override
    protected void reFreshActionList(int position, ActionListBean bean) {
        list_logs.set(position, bean);
        adapter_log.notifyDataSetChanged();
    }

    @Override
    protected void reMoveAction(int position) {
        list_logs.remove(position);
        adapter_log.notifyDataSetChanged();
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 3:
                    mRefreshLayout.finishLoadmore();
                    mRefreshLayout.finishRefresh();
                    UserBlogItemModel model = DataParser.getBlogParser(msg.obj.toString());
                    List<ActionListBean> lists = ItemDataExchangeUtils.dataExchange_Blog(model.getItems());
                    if (lists.size() > 0) {
                        timeCursor = model.getCursor();
                        list_logs.addAll(lists);
                        adapter_log.notifyDataSetChanged();
                    } else {
                        if (timeCursor != 0) {
                            Toast.makeText(PersonLogActivity.this, R.string.no_more_data, Toast.LENGTH_SHORT).show();
                        } else {
                            mPullToRefreshListView.setEmptyView(EmptyViewUtils.EmptyViewTextAndImage(R.drawable.no_state, PersonLogActivity.this, R.string.have_no_persionLogs));
                        }
                    }
                    break;

                case -3:
                    list_logs.remove(msg.arg1);
                    adapter_log.notifyDataSetChanged();

                    break;

            }

        }
    }

}
