package com.histudent.jwsoft.histudent.body.myclass.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.myclass.adapter.PointRecordAdapter;
import com.histudent.jwsoft.histudent.body.myclass.bean.PointRecordBean;
import com.histudent.jwsoft.histudent.body.myclass.bean.PointRecordModel;
import com.histudent.jwsoft.histudent.body.myclass.bean.RecordListBean;
import com.histudent.jwsoft.histudent.body.myclass.helper.ClassHelper;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by huyg on 2017/7/4.
 * <p>
 * 成长值记录
 */

public class PointRecordActivity extends BaseActivity {

    @BindView(R.id.title_middle_text)
    TextView mTitle;
    @BindView(R.id.title_right_text)
    TextView mRightTitle;
    @BindView(R.id.group_point)
    RadioGroup mPoints;
    @BindView(R.id.rb_all)
    RadioButton mAll;
    @BindView(R.id.rb_use)
    RadioButton mUse;
    @BindView(R.id.rb_earn)
    RadioButton mEarn;
    @BindView(R.id.point_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.srl_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.empty)
    LinearLayout mEmptyView;


    @BindColor(R.color.green_color)
    int green;
    @BindColor(R.color.white)
    int white;

    @OnClick({R.id.title_left, R.id.title_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left://返回
                finish();
                break;

            case R.id.title_right://兑换奖品

                break;
        }
    }

    @OnCheckedChanged({R.id.rb_earn, R.id.rb_all, R.id.rb_use})
    public void onCheckedChanged(android.widget.CompoundButton view, boolean isChecked) {
        if (isChecked) {
            loading = PULL_DOWN;
            mTitles.clear();
            switch (view.getId()) {
                case R.id.rb_all:
                    action = ACTION_ALL;
                    changeAllColor();
                    getPointRecordList(0, LoadingType.FLOWER);

                    break;
                case R.id.rb_use:
                    changeUseColor();
                    action = ACTION_USE;
                    getPointRecordList(0, LoadingType.FLOWER);

                    break;
                case R.id.rb_earn:
                    changeEarnColor();
                    action = ACTION_EARN;
                    getPointRecordList(0, LoadingType.FLOWER);

                    break;
            }
        }
    }

    private PointRecordAdapter mAdapter;
    private LinearLayoutManager mManager;
    private static int ACTION_ALL = 0;
    private static int ACTION_EARN = 1;
    private static int ACTION_USE = 2;
    private int action = 0;
    private static final int PAGE_SIZE = 12;
    private static final int PULL_DOWN = 0;
    private static final int PULL_UP = 1;
    private int loading = 0;
    private Map<String, String> mTitles = new HashMap<>();


    @Override
    public int setViewLayout() {
        return R.layout.activity_pointrecord;
    }

    @Override
    public void initView() {
        initTitle();
        initAdapter();
        initRefresh();
        mAll.setChecked(true);

    }

    private void initAdapter() {
        mAdapter = new PointRecordAdapter(this);
        mManager = new LinearLayoutManager(this);
        mRecycler.setNestedScrollingEnabled(false);
        mRecycler.setLayoutManager(mManager);
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void doAction() {
        super.doAction();
        getPointRecordList(0, LoadingType.FLOWER);
    }

    private void initTitle() {
        mTitle.setText("成长值记录");
    }


    private void initRefresh() {
        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                loading = PULL_UP;
                getPointRecordList((int) Math.ceil(((float) mAdapter.getSize()) / PAGE_SIZE), LoadingType.NONE);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                loading = PULL_DOWN;
                mTitles.clear();
                getPointRecordList(0, LoadingType.NONE);
            }
        });
    }

    private void changeAllColor() {
        mAll.setTextColor(white);
        mUse.setTextColor(green);
        mEarn.setTextColor(green);
    }

    private void changeUseColor() {
        mAll.setTextColor(green);
        mUse.setTextColor(white);
        mEarn.setTextColor(green);
    }

    private void changeEarnColor() {
        mAll.setTextColor(green);
        mEarn.setTextColor(white);
        mUse.setTextColor(green);
    }


    public void getPointRecordList(int index, LoadingType loadingType) {
        ClassHelper.getPointRecordList(this, action, index, PAGE_SIZE, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadmore();
                if (!TextUtils.isEmpty(result)) {
                    PointRecordBean pointRecordBean = JSONObject.parseObject(result, PointRecordBean.class);

                    if (pointRecordBean != null) {
                        List<PointRecordBean.ItemsBean> itemsBeens = pointRecordBean.getItems();
                        formatList(itemsBeens);

                    }
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadmore();
                Toast.makeText(PointRecordActivity.this, "获取失败", Toast.LENGTH_SHORT).show();
            }
        }, loadingType);
    }


    private void formatList(List<PointRecordBean.ItemsBean> itemsBeens) {
        List<PointRecordModel> pointRecordModels = new ArrayList<>();
        for (PointRecordBean.ItemsBean itemsBean : itemsBeens) {
            PointRecordModel pointRecordModel = new PointRecordModel();
            pointRecordModel.setMonth(itemsBean.getMonth());
            pointRecordModel.setConsumePoints(itemsBean.getConsumePoints());
            pointRecordModel.setPoints(itemsBean.getPoints());
            pointRecordModel.setType(PointRecordModel.TYPE_TITLE);

            if (!mTitles.containsKey(itemsBean.getMonth())) {
                pointRecordModels.add(pointRecordModel);
                mTitles.put(itemsBean.getMonth(), itemsBean.getMonth());
            }


            List<RecordListBean> recordListBeans = itemsBean.getRecordList();
            if (recordListBeans != null) {
                for (RecordListBean recordListBean : recordListBeans) {
                    PointRecordModel pointRecordModel1 = new PointRecordModel();
                    pointRecordModel1.setRecordListBean(recordListBean);
                    pointRecordModels.add(pointRecordModel1);
                }
            }
        }
        switch (loading) {
            case PULL_DOWN:
                mAdapter.setAction(action);
                if (pointRecordModels.size() == 0) {
                    mAdapter.setEmpty(true);
                } else {
                    mAdapter.setEmpty(false);
                }
                mAdapter.setRecords(pointRecordModels);
                mRecycler.smoothScrollBy(0, 0);
                break;
            case PULL_UP:
                mAdapter.setAction(action);
                mAdapter.addRecords(pointRecordModels);
                break;
        }


    }
}
