package com.histudent.jwsoft.histudent.body.myclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.activity.CreateGroupHuoDongFristStep;
import com.histudent.jwsoft.histudent.body.find.activity.PersonalHuoDongActivity;
import com.histudent.jwsoft.histudent.body.find.adapter.HuoDongAdapter;
import com.histudent.jwsoft.histudent.body.find.bean.PersonalHuoDongBean;
import com.histudent.jwsoft.histudent.body.find.helper.GroupHelper;
import com.histudent.jwsoft.histudent.body.myclass.helper.ClassHelper;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;
import com.histudent.jwsoft.histudent.constant.TransferKeys;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 班级或者社群活动界面
 */
public class ClassOrGroupHuoDongActivity extends BaseActivity {
    private PullToRefreshListView pullToRefreshListView;
    private int type;
    private HuoDongAdapter mHuoDongAdapter;
    private String id;
    private List<Object> list;
    private boolean isAdmin;
    private int pageIndex;
    private SmartRefreshLayout mRefreshLayout;
    private static final int REQ_INFO = 10001;

    /**
     * @param activity
     * @param type       活动类型  1：班级活动  2：社群活动
     * @param id         班级id 或者社群id 与类型对应
     * @param requstCode
     * @param isAdmin    是否是管理员 管理员可以创建活动，一般成员没有创建活动权限
     */
    public static void start(Activity activity, int type, String id, int requstCode, boolean isAdmin) {
        Intent intent = new Intent(activity, ClassOrGroupHuoDongActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("id", id);
        intent.putExtra("isAdmin", isAdmin);
        activity.startActivityForResult(intent, requstCode);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_class_active;
    }

    @Override
    public void initView() {

        list = new ArrayList<>();
        id = getIntent().getStringExtra("id");
        type = getIntent().getIntExtra("type", 0);
        isAdmin = getIntent().getBooleanExtra("isAdmin", false);
        pullToRefreshListView = ((PullToRefreshListView) findViewById(R.id.list_view));
        if (!isAdmin) {
            findViewById(R.id.create).setVisibility(View.GONE);
        } else {
            findViewById(R.id.create).setVisibility(View.VISIBLE);
        }
        if (type == 1) {
            ((TextView) findViewById(R.id.title_middle_text)).setText("班级活动");
            ((IconView) findViewById(R.id.title_right_image)).setText(R.string.icon_minenone);
        } else {
            ((TextView) findViewById(R.id.title_middle_text)).setText("社群活动");
            ((IconView) findViewById(R.id.title_right_image)).setText(R.string.icon_minenone);
        }


        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.srl_refresh_layout);
        initRefreshListView();
        mHuoDongAdapter = new HuoDongAdapter(list, this, type);
        pullToRefreshListView.setAdapter(mHuoDongAdapter);
        getHuoDongList(false, true);


        pullToRefreshListView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            PersonalHuoDongBean bean = ((PersonalHuoDongBean) list.get(position - 1));
            Intent intent = new Intent(ClassOrGroupHuoDongActivity.this, HuoDongCenterActivity.class);
            intent.putExtra(TransferKeys.STATUS, bean.getStatus());
            intent.putExtra(TransferKeys.CREATE, bean.getIsCreate());
            intent.putExtra(TransferKeys.TYPE, type);
            intent.putExtra(TransferKeys.ID, bean.getId());
            startActivityForResult(intent,REQ_INFO);
        });
    }

    private void initRefreshListView() {
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);
        mRefreshLayout.setOnRefreshListener((RefreshLayout refreshLayout) -> getHuoDongList(false, false));
        mRefreshLayout.setOnLoadmoreListener((RefreshLayout refreshLayout) -> getHuoDongList(true, false));
    }

    public void getHuoDongList(boolean isMore, boolean isNeedLoading) {

        if (isMore) {
            pageIndex++;
        } else {
            list.clear();
            pageIndex = 0;
        }

        //班级活动列表
        if (type == 1) {
            ClassHelper.getClassHuoDongList(this, pageIndex, id, new HttpRequestCallBack() {
                @Override
                public void onSuccess(String result) {
//                    pullToRefreshListView.onRefreshComplete();
                    mRefreshLayout.finishLoadmore();
                    mRefreshLayout.finishRefresh();
                    List<PersonalHuoDongBean> list1 = JSON.parseArray(JSON.parseObject(result).getJSONArray("items").toString(), PersonalHuoDongBean.class);
                    if (list1.size() == 0) {
                        pullToRefreshListView.setEmptyView(EmptyViewUtils.EmptyViewTextAndImage(R.drawable.no_join_or_take_group,
                                ClassOrGroupHuoDongActivity.this, R.string.empty_no_huodong));
                    } else {
                        list.addAll(list1);
                        mHuoDongAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(String errorMsg) {
                    mRefreshLayout.finishLoadmore();
                    mRefreshLayout.finishRefresh();
                }
            }, isNeedLoading);

            //社群活动列表
        } else {

            GroupHelper.getGroupHuoDongList(this, id, pageIndex, new HttpRequestCallBack() {
                @Override
                public void onSuccess(String result) {
                    mRefreshLayout.finishLoadmore();
                    mRefreshLayout.finishRefresh();
                    List<PersonalHuoDongBean> list1 = JSON.parseArray(JSON.parseObject(result).getJSONArray("items").toString(), PersonalHuoDongBean.class);
                    if (list1.size() == 0) {
                        pullToRefreshListView.setEmptyView(EmptyViewUtils.EmptyViewTextAndImage(R.drawable.no_join_or_take_group,
                                ClassOrGroupHuoDongActivity.this, R.string.empty_no_huodong));
                    } else {
                        list.addAll(list1);
                        mHuoDongAdapter.notifyDataSetChanged();
                    }
                    pullToRefreshListView.onRefreshComplete();
                }

                @Override
                public void onFailure(String errorMsg) {
                    mRefreshLayout.finishLoadmore();
                    mRefreshLayout.finishRefresh();
                }
            }, isNeedLoading);
        }


    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            //发起活动
            case R.id.create:

                if (type == 1) {
                    CreateHuoDongFirstStep.start(this, id);
                } else {
                    CreateGroupHuoDongFristStep.start(this, id, 222);
                }

                break;

            case R.id.title_right:

                PersonalHuoDongActivity.start(this, id, type);
                break;

            case R.id.title_left:

                setResult(-2);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case 200:
                getHuoDongList(false, true);
                break;
        }
    }
}
