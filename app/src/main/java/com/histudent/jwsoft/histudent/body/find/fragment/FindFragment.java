package com.histudent.jwsoft.histudent.body.find.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.gyf.barlibrary.ImmersionBar;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.activity.CommunityCategoryActivity;
import com.histudent.jwsoft.histudent.body.find.activity.EditApplicationActivity;
import com.histudent.jwsoft.histudent.body.find.activity.GroupFindActivity;
import com.histudent.jwsoft.histudent.body.find.activity.SchoolHomeActivity;
import com.histudent.jwsoft.histudent.body.find.adapter.ApplicationAdapter;
import com.histudent.jwsoft.histudent.body.find.adapter.GroupSecondCategoryAdapter;
import com.histudent.jwsoft.histudent.body.find.bean.FindHomeModel;
import com.histudent.jwsoft.histudent.body.find.bean.HuodongBean;
import com.histudent.jwsoft.histudent.body.find.helper.GroupHelper;
import com.histudent.jwsoft.histudent.body.myclass.activity.ClassCircleActivity;
import com.histudent.jwsoft.histudent.commen.activity.HTWebActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.fragment.BaseFragment;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.ComViewUitls;
import com.histudent.jwsoft.histudent.commen.view.MyGridView;
import com.histudent.jwsoft.histudent.comment2.utils.SelectedClassCallBack;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuguiyu-pc on 2016/5/26.
 * “发现”界面的fragment
 */
public class FindFragment extends BaseFragment implements View.OnClickListener {


    private PullToRefreshScrollView mListView;
    private LinearLayout schoolLayout;
    private TextView schoolName;
    private View view;
    private FindHomeModel findHomeModel;
    private MyGridView gridView_category, gridView_app;
    private SelectedClassCallBack callBack;
    private GroupSecondCategoryAdapter favorGroupAdapter;
    private ApplicationAdapter appAdapter;
    private List<Object> favorGroupList, appList, allAppList;
    private SmartRefreshLayout mRefreshLayout;


    @BindView(R.id.view_status_bar)
    View mViewStatus;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //加载布局
        view = inflater.inflate(R.layout.fragment_main_find, container, false);
        return view;
    }

    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this, view);
        schoolLayout = view.findViewById(R.id.school_layout);
        schoolName = view.findViewById(R.id.school_name);
        ((TextView) view.findViewById(R.id.title_middle_text)).setText("发现");
        view.findViewById(R.id.title_left).setVisibility(View.GONE);
        gridView_category = view.findViewById(R.id.grid_view_favor_group);
        gridView_app = view.findViewById(R.id.grid_view_app);
        mListView = view.findViewById(R.id.scroll_view);
        view.findViewById(R.id.favor).setOnClickListener(this);
        view.findViewById(R.id.school).setOnClickListener(this);

        mRefreshLayout = view.findViewById(R.id.srl_refresh_layout);
        mListView.setMode(PullToRefreshBase.Mode.DISABLED);

        favorGroupList = new ArrayList<>();
        appList = new ArrayList<>();
        allAppList = new ArrayList<>();
        favorGroupAdapter = new GroupSecondCategoryAdapter(favorGroupList, getActivity());
        appAdapter = new ApplicationAdapter(getActivity(), appList, gridView_app, 1);
        gridView_app.setAdapter(appAdapter);
        gridView_category.setAdapter(favorGroupAdapter);
        callBack = (FindHomeModel.HotClassListBean bean) -> ClassCircleActivity.start(getActivity(), bean.getClassesId());

        final int statusBarHeight = ImmersionBar.getStatusBarHeight(getActivity());
        final ViewGroup.LayoutParams layoutParams = mViewStatus.getLayoutParams();
        layoutParams.height = statusBarHeight;
        mViewStatus.setLayoutParams(layoutParams);

        String dataStr = HiCache.getInstance().getHttpDataInDB(HistudentUrl.findInfor, "");
        if (TextUtils.isEmpty(dataStr)) {
            getFindHomeInfor();
        } else {
            updateUI(dataStr);
        }

    }


    private void updateUI(String result) {
        findHomeModel = JSON.parseObject(result, FindHomeModel.class);
        if (findHomeModel != null) {
            allAppList.clear();
            appList.clear();
            favorGroupList.clear();
            updateUi();
        }
    }

    //获取发现主页的数据
    private void getFindHomeInfor() {

        GroupHelper.getFindInfor(getActivity(), 10, 8, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                HiCache.getInstance().saveHttpDataIntoDB(HistudentUrl.findInfor, "", result);
                mRefreshLayout.finishRefresh();
                updateUI(result);
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

    private void updateUi() {

        if (findHomeModel.getHotClassList() != null) {
            if (findHomeModel.getHotClassList().size() > 0) {
                schoolLayout.setVisibility(View.VISIBLE);
                ComViewUitls.addHotClass(getActivity(), view.findViewById(R.id.ll_hot_class), findHomeModel.getHotClassList(), callBack);
            } else {
                schoolLayout.setVisibility(View.GONE);
            }
        }

        schoolName.setText(findHomeModel.getSchoolName());

        allAppList.addAll(changeData());

        if (allAppList.size() > 7) {
            appList.addAll(allAppList.subList(0, 7));
        } else {
            appList.addAll(allAppList);
        }

        //添加更多
        HuodongBean b = new HuodongBean();
        b.setEmpty(true);
        b.setAppName("更多应用");
        appList.add(b);


        appAdapter.notifyDataSetChanged();
        favorGroupList.clear();
        favorGroupList.addAll(findHomeModel.getGroupCategorys());
        favorGroupAdapter.notifyDataSetChanged();

        gridView_app.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            HuodongBean appListBean = (HuodongBean) appList.get(position);
            if (appListBean.isEmpty()) {

                EditApplicationActivity.start(getActivity(), (ArrayList) allAppList);
            } else {
                HTWebActivity.start(getActivity(), appListBean.getAppUrl());
            }
        });
        //兴趣社群分类监听
        gridView_category.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            FindHomeModel.GroupCategorysBean bean = ((FindHomeModel.GroupCategorysBean) favorGroupList.get(position));
            CommunityCategoryActivity.start(getActivity(), bean.getId());
        });
        gridView_app.setFocusable(false);
        mRefreshLayout.setOnRefreshListener((RefreshLayout refreshlayout) -> getFindHomeInfor());
        mRefreshLayout.setEnableLoadmore(false);
    }

    private List<Object> changeData() {

        List<Object> temList = new ArrayList<>();
        if (findHomeModel.getMyAppList() != null) {

            for (FindHomeModel.MyAppListBean model : findHomeModel.getMyAppList()) {
                HuodongBean bean = new HuodongBean();
                bean.setAppName(model.getAppName());
                bean.setAppKey(model.getAppKey());
                bean.setAppUrl(model.getAppUrl());
                bean.setLogo(model.getLogo());
                temList.add(bean);
            }
        }
        return temList;
    }


    @Override
    public void initData() {
        super.initData();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //兴趣社群跳转
            case R.id.favor:
                GroupFindActivity.start(getActivity());
                break;
            //学校跳转
            case R.id.school:
                SchoolHomeActivity.start(getActivity(), findHomeModel.getSchoolId());
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case 500:
                if (resultCode == 200)
                    getFindHomeInfor();
                break;
        }
    }
}
