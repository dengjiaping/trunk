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
import com.histudent.jwsoft.histudent.body.hiworld.activity.EssayActivity;
import com.histudent.jwsoft.histudent.body.mine.activity.ActionBaseActivity;
import com.histudent.jwsoft.histudent.body.mine.adapter.ActionAdapter;
import com.histudent.jwsoft.histudent.body.mine.model.CurrentUserDetailInfoModel;
import com.histudent.jwsoft.histudent.body.mine.model.EssayModel;
import com.histudent.jwsoft.histudent.body.mine.parser.DataParser;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.utils.ItemDataExchangeUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;
import com.histudent.jwsoft.histudent.info.persioninfo.PersionHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuguiyu-pc on 2017/4/21.
 * 随记页面
 */

public class PersonEssayActivity extends ActionBaseActivity {

    private PullToRefreshListView mPullToRefreshListView;
    private ActionAdapter adapter_essay;
    private TextView title, title_right;
    private List<ActionListBean> list_essays;
    private int timeCursor;
    private MyHandler handler;
    private int REQUESTCODE = 100;
    private int RESULTCODE = 101;
    private boolean isMySelfe;
    private SmartRefreshLayout mRefreshLayout;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, PersonEssayActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_persion_essay;
    }

    @Override
    public void initView() {
        handler = new MyHandler();
        list_essays = new ArrayList<>();
        title = (TextView) findViewById(R.id.title_middle_text);
        title_right = (TextView) findViewById(R.id.title_right_text);
        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.srl_refresh_layout);
        mRefreshLayout.setEnableAutoLoadmore(true);
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.persion_essay_list);
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);
        adapter_essay = new ActionAdapter(this, list_essays, ActionAdapter.ESSAY);
        mPullToRefreshListView.setAdapter(adapter_essay);
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
                    EssayActivity.start(PersonEssayActivity.this, REQUESTCODE, RESULTCODE);
                break;
        }
    }

    @Override
    public void doAction() {
        super.doAction();

        CurrentUserDetailInfoModel model = HiCache.getInstance().getUserDetailInfo();

        isMySelfe = SystemUtil.isOneselfIn(model.getUserId());

        if (isMySelfe) {
            title.setText("我的随记");
            title_right.setText("发随记");
        } else {
            int statue = model.getProfile().getSex();
            title.setText(statue == 0 ? "TA的随记" : statue == 2 ? "她的随记" : "他的随记");
        }

        mRefreshLayout.setOnRefreshListener((RefreshLayout refreshLayout) -> {
            list_essays.clear();
            timeCursor = 0;
            PersionHelper.getInstance().getPersionEssays(PersonEssayActivity.this, timeCursor, handler, 2, LoadingType.NONE);
        });

        mRefreshLayout.setOnLoadmoreListener((RefreshLayout refreshLayout) ->
                PersionHelper.getInstance().getPersionEssays(PersonEssayActivity.this, timeCursor, handler, 2, LoadingType.NONE)
        );
        PersionHelper.getInstance().getPersionEssays(this, 0, handler, 2, LoadingType.FLOWER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE && resultCode == RESULTCODE) {//发布随记返回
            list_essays.clear();
            timeCursor = 0;
            PersionHelper.getInstance().getPersionEssays(PersonEssayActivity.this, timeCursor, handler, 2, LoadingType.NONE);
        }
    }

    @Override
    protected void reFreshActionList(int position, ActionListBean bean) {
        list_essays.set(position, bean);
        adapter_essay.notifyDataSetChanged();
    }

    @Override
    protected void reMoveAction(int position) {
        list_essays.remove(position);
        adapter_essay.notifyDataSetChanged();
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                case 2:
                    mRefreshLayout.finishLoadmore();
                    mRefreshLayout.finishRefresh();
                    EssayModel model = DataParser.getUserEssayModel(msg.obj.toString());
                    List<ActionListBean> lists = ItemDataExchangeUtils.dataExchange_Essay(model.getItems());
                    if (lists.size() > 0) {
                        timeCursor = model.getCursor();
                        list_essays.addAll(lists);
                        adapter_essay.notifyDataSetChanged();
                    } else {
                        if (timeCursor != 0) {
                            Toast.makeText(PersonEssayActivity.this, R.string.no_more_data, Toast.LENGTH_SHORT).show();
                        } else {
                            mPullToRefreshListView.setEmptyView(EmptyViewUtils.EmptyViewTextAndImage(R.drawable.no_state, PersonEssayActivity.this, R.string.have_no_persionEssay));
                        }
                    }
                    break;

                case -2:

                    list_essays.remove(msg.arg1);
                    adapter_essay.notifyDataSetChanged();

                    break;

            }

        }
    }

}
