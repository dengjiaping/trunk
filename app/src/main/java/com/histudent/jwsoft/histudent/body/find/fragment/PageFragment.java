package com.histudent.jwsoft.histudent.body.find.fragment;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.activity.CommunityCategoryActivity;
import com.histudent.jwsoft.histudent.body.find.activity.GroupCenterActivity;
import com.histudent.jwsoft.histudent.body.find.adapter.MyCommunityListAdapter;
import com.histudent.jwsoft.histudent.body.find.bean.SecondCommunityBean;
import com.histudent.jwsoft.histudent.body.find.helper.GroupHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;
import com.histudent.jwsoft.histudent.comment2.utils.PullToRefreshUtils;
import com.histudent.jwsoft.histudent.model.constant.TransferKeys;
import com.histudent.jwsoft.histudent.view.fragment.CommunityBaseFragment;
import com.histudent.jwsoft.histudent.tool.ToastTool;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android03_pc on 2017/5/16.
 */

public class PageFragment extends CommunityBaseFragment {

    private String mCommunityId;
    private boolean mLoadMore;
    private MyCommunityListAdapter mCommunityListAdapter;
    private PullToRefreshListView mListView;
    private List<SecondCommunityBean> mSecondCommunityBeenList = new ArrayList<>();
    private int mPageSize = 10;//每次固定返回数据为10条数据
    private int mCurrentPageIndex = 0;//当前第几页0
    private boolean mHasMoreData;
    private Context mContext;
    private SmartRefreshLayout mRefreshLayout;


    @Override
    protected void loadView() {
        mContext = getActivity();
        mCommunityId = getArguments().getString(TransferKeys.COMMUNITY_ID);
        mListView = mView.findViewById(R.id.fragment_page_listView);
        mRefreshLayout = mView.findViewById(R.id.srl_refresh_layout);
        mListView.setMode(PullToRefreshBase.Mode.DISABLED);
        mRefreshLayout.setEnableAutoLoadmore(true);
    }

    @Override
    protected void loadData() {
        mSecondCommunityBeenList.clear();
        mCommunityListAdapter = null;
        mLoadMore = false;
        mCurrentPageIndex = 0;
        getNetData();
        initRefreshListView();
        initListener();
    }

    private void getNetData() {
        GroupHelper.getSecondGroupInfo(getActivity(), mPageSize, mCurrentPageIndex, mCommunityId, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                refreshListView(result);
            }

            @Override
            public void onFailure(String errorMsg) {
            }
        });
    }

    private void initRefreshListView() {
        PullToRefreshUtils.initPullToRefreshListView2(mListView);
        mRefreshLayout.setOnRefreshListener((RefreshLayout refreshLayout)->{
            mCurrentPageIndex = 0;
            mSecondCommunityBeenList.clear();
            loadDataBySearchStatus();
        });

        mRefreshLayout.setOnLoadmoreListener((RefreshLayout refreshLayout)->{
            mLoadMore = true;
            if(!mHasMoreData)
                mCurrentPageIndex++;
            loadDataBySearchStatus();
        });

    }


    private void initListener() {
        mListView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) ->
                GroupCenterActivity.start(getActivity(), mSecondCommunityBeenList.get(position - 1).getGroupId()));

        ((CommunityCategoryActivity) mContext).setOnSearchChangedListener
                ((String inputContent, MyCommunityListAdapter adapter, String tagId, PullToRefreshListView pullToRefreshListView) ->{
                    mCurrentPageIndex = 0;
                    getCommunitySearchData(inputContent, adapter, tagId, pullToRefreshListView);
                        }

                );
    }

    /**
     * 根据用户是否处理搜索状态去加载数据
     */
    private void loadDataBySearchStatus() {
        String searchContent = "";
        if (getActivity() instanceof CommunityCategoryActivity)
            searchContent = ((CommunityCategoryActivity) getActivity()).getSearchContent();
        if (TextUtils.isEmpty(searchContent)) {
            getNetData();
        } else {
            getCommunitySearchData(searchContent, null, mCommunityId, null);
        }
    }


    private void getCommunitySearchData(String searchContent, MyCommunityListAdapter adapter, String tagId, PullToRefreshListView listView) {

        GroupHelper.getSecondGroupInfo(getActivity(), searchContent, mPageSize, mCurrentPageIndex, tagId, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                if (adapter != null) {
                    //说明是点击搜索获取 刷新的数据
                    mSecondCommunityBeenList.clear();
                }
                refreshListView(result);
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

    /**
     * 处理返回过来的数据并刷新列表
     *
     * @param result
     */
    private void refreshListView(String result) {
        mListView.onRefreshComplete();
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadmore();
        List<SecondCommunityBean> secondCommunityBeanList = JSON.parseArray(result, SecondCommunityBean.class);
        mSecondCommunityBeenList.addAll(secondCommunityBeanList);
        if (mSecondCommunityBeenList.size() == 0)
            mListView.setEmptyView(EmptyViewUtils.EmptyViewTextAndImage(R.drawable.net_error_fail, getActivity()
                    , R.string.empty_load_data_fail));

        if (secondCommunityBeanList.size() == 0) {
            if (mLoadMore) {
                ToastTool.showCommonToast(getActivity(), "暂无更多数据！");
                mHasMoreData = true;
                return;
            }
        }else{
            mHasMoreData = false;
        }

        if (mCommunityListAdapter == null) {
            mCommunityListAdapter = new MyCommunityListAdapter(getActivity(), mSecondCommunityBeenList);
            mListView.setAdapter(mCommunityListAdapter);
        } else {
            mCommunityListAdapter.notifyDataSetChanged();
        }

    }

    public MyCommunityListAdapter getAdapter() {
        return mCommunityListAdapter;
    }

    public String getTagId() {
        return mCommunityId;
    }

    public PullToRefreshListView getListView() {
        return mListView;
    }


    public PullToRefreshListView getPullToRefreshListView() {
        return mListView;
    }
}
