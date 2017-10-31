package com.histudent.jwsoft.histudent.body.find.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.mine.activity.ActionBaseActivity;
import com.histudent.jwsoft.histudent.body.mine.adapter.ActionAdapter;
import com.histudent.jwsoft.histudent.body.mine.model.UserTimeModel;
import com.histudent.jwsoft.histudent.body.mine.parser.DataParser;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.ItemDataExchangeUtils;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2017/5/18.
 * 热门帖子
 */

public class HotInvitationActivity extends ActionBaseActivity {

    private TextView title;
    private PullToRefreshListView hot_invitation_list;
    private int pageIndex;
    private List<ActionListBean> beens;
    private ActionAdapter homePageAdapter_rule;
    private SmartRefreshLayout mRefreshLayout;

    public static void start(Activity context) {
        Intent intent = new Intent(context, HotInvitationActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_hotinvitation;
    }

    @Override
    public void initView() {
        beens = new ArrayList<>();
        homePageAdapter_rule = new ActionAdapter(this, beens, ActionAdapter.INVITATION);
        title = (TextView) findViewById(R.id.title_middle_text);
        hot_invitation_list = (PullToRefreshListView) findViewById(R.id.hot_invitation_list);
        hot_invitation_list.setMode(PullToRefreshBase.Mode.DISABLED);
        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.srl_refresh_layout);
        mRefreshLayout.setEnableAutoLoadmore(true);
    }

    @Override
    public void doAction() {
        super.doAction();
        title.setText("热门帖子");
        hot_invitation_list.setAdapter(homePageAdapter_rule);
        getInvitationList(true);
        mRefreshLayout.setOnRefreshListener((RefreshLayout refreshLayout) -> {
            pageIndex = 0;
            beens.clear();
            getInvitationList(false);
        });

        mRefreshLayout.setOnLoadmoreListener((RefreshLayout refreshLayout) -> getInvitationList(false));
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
        }
    }

    /**
     * 获取热门帖子列表
     */
    private void getInvitationList(boolean isNeedLoadingDialog) {
        Map<String, Object> map = new TreeMap<>();
        map.put("pageIndex", pageIndex);
        map.put("pageSize", 8);

        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.noteHotList, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                mRefreshLayout.finishLoadmore();
                mRefreshLayout.finishRefresh();
                UserTimeModel model = DataParser.getUserTimeModel(result);
                if (model.getItems() == null || model.getItems().size() == 0) {
                    hot_invitation_list.setEmptyView(EmptyViewUtils.EmptyViewTextAndImage(R.drawable.no_hot_tizi,
                            HotInvitationActivity.this, R.string.empty_no_hot_note));
                } else {
                    if (model.getItems() != null && model.getItems().size() > 0) {
                        pageIndex++;
                        beens.addAll(ItemDataExchangeUtils.dataExchange(model.getItems()));
                        homePageAdapter_rule.notifyDataSetChanged();
                    } else {
                        if (pageIndex > 0)
                            Toast.makeText(HotInvitationActivity.this, R.string.no_more_data, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(String msg) {
                mRefreshLayout.finishLoadmore();
                mRefreshLayout.finishRefresh();
            }
        }, isNeedLoadingDialog ? LoadingType.FLOWER : LoadingType.NONE);

    }

    @Override
    protected void reFreshActionList(int position, ActionListBean bean) {
        beens.set(position, bean);
        homePageAdapter_rule.notifyDataSetChanged();
    }

    @Override
    protected void reMoveAction(int position) {
        beens.remove(position);
        homePageAdapter_rule.notifyDataSetChanged();
    }

}
