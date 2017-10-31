package com.histudent.jwsoft.histudent.body.find.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.adapter.HandlerRequestAdapter;
import com.histudent.jwsoft.histudent.commen.bean.GroupNewsModel;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/*
*
* 处理社群消息界面
* */
public class PersonGroupNewsActivity extends BaseActivity {

    private PullToRefreshListView mRefreshListView;
    private List<Object> mRequestList;
    private HandlerRequestAdapter mRequestAdapter;
    private GroupNewsModel mGroupNewsModel;
    @BindView(R.id.title_left)
    View mTitleLeft;

    public static void start(Activity context, GroupNewsModel model) {
        Intent intent = new Intent(context, PersonGroupNewsActivity.class);
        intent.putExtra("news", model);
        context.startActivityForResult(intent, 200);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_handle_request;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);

        ((TextView) findViewById(R.id.title_middle_text)).setText("加入请求");
        mGroupNewsModel = ((GroupNewsModel) getIntent().getSerializableExtra("news"));
        mRequestList = new ArrayList<>();
        if (mGroupNewsModel != null) {
            if (mGroupNewsModel.getInvitationList() != null)
                mRequestList.addAll(mGroupNewsModel.getInvitationList());
            if (mGroupNewsModel.getApplyList() != null)
                mRequestList.addAll(mGroupNewsModel.getApplyList());
        }
        mRefreshListView = ((PullToRefreshListView) findViewById(R.id.pull_to_refresh));
        mRequestAdapter = new HandlerRequestAdapter(mRequestList, this);
        mRefreshListView.setAdapter(mRequestAdapter);
        mRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);
        if (mRequestList.size() == 0)
            mRefreshListView.setEmptyView(EmptyViewUtils.EmptyViewTextAndImage(R.drawable.no_join_request, this, R.string.empty_no_join_request));
    }

    @Override
    public void doAction() {
        super.doAction();
        mTitleLeft.setOnClickListener((View view) -> {
            if (mRequestAdapter.isOperate()) {//如果操作过，返回时需要刷新界面，否则不用
                setResult(200);
            } else {
                setResult(-1);
            }
            this.finish();
        });
    }
}
