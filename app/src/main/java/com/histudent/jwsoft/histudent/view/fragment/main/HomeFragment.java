package com.histudent.jwsoft.histudent.view.fragment.main;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.base.BaseFragment;
import com.histudent.jwsoft.histudent.commen.activity.CommentActivity;
import com.histudent.jwsoft.histudent.commen.activity.HTWebActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.comment2.bean.AddressInforBean;
import com.histudent.jwsoft.histudent.model.bean.main.HomeEntity;
import com.histudent.jwsoft.histudent.model.bean.main.HomeItemType;
import com.histudent.jwsoft.histudent.model.bean.main.HomePageEntity;
import com.histudent.jwsoft.histudent.presenter.main.HomeFragmentPresenter;
import com.histudent.jwsoft.histudent.presenter.main.contract.HomeFragmentContract;
import com.histudent.jwsoft.histudent.tool.ToastTool;
import com.histudent.jwsoft.histudent.view.adapter.main.HomeAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

/**
 * Created by lichaojie on 2017/11/13.
 * desc:
 */

public class HomeFragment extends BaseFragment<HomeFragmentPresenter> implements HomeFragmentContract.View {

    @BindView(R.id.rv_recycler_layout)
    RecyclerView mRecyclerView;
    @BindView(R.id.srl_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.icon_search)
    IconView mIconViewSearch;
    private AddressInforBean mAddressInforBean;
    private final List<HomeEntity> LIST = new ArrayList<>();
    private HomeAdapter mAdapter;
    private boolean isRefresh;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init() {
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        mIconViewSearch.setOnClickListener((View view) -> HTWebActivity.startNoClose(getActivity(), HistudentUrl.search));
        mRefreshLayout.setOnRefreshListener((RefreshLayout refreshlayout) -> {
            isRefresh = true;
            mPresenter.requestHomeListData(mAddressInforBean);
        });
    }

    private void initView() {
        mRefreshLayout.setEnableAutoLoadmore(true);
        mRefreshLayout.setEnableLoadmore(false);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = HomeAdapter.create(LIST);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener());
        mRecyclerView.setAdapter(mAdapter);

    }

    private static final String TAG = "HomeFragment";

    private final class OnItemClickListener extends SimpleClickListener {

        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            final HomeEntity homeEntity = LIST.get(position);
            final int type = homeEntity.getType();
            switch (type) {
                case HomeItemType.CREATE_BLOG:
                    final HomePageEntity.RecommendDynamicEntity.SubRecommendDynamicEntity actionLogEntity = homeEntity.getActionLogEntity();
                    HTWebActivity.start(getActivity(), actionLogEntity.getHtmlUrl());
                    break;
                case HomeItemType.CREATE_MICROBLOG:
                    final HomePageEntity.RecommendDynamicEntity.SubRecommendDynamicEntity essayEntity = homeEntity.getEssayEntity();
                    CommentActivity.start(getActivity(), essayEntity.getActId(), position);
                    break;
                case HomeItemType.UPLOAD_PHOTO:
                    final HomePageEntity.RecommendDynamicEntity.SubRecommendDynamicEntity uploadPhotoEntity = homeEntity.getUploadPhotoEntity();
                    CommentActivity.start(getActivity(), uploadPhotoEntity.getActId(), position);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

        }

        @Override
        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

        }

        @Override
        public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

        }
    }

    private void initData() {
        mAddressInforBean = HiWorldCache.getUserLocationInfor();
        if (mAddressInforBean == null)
            mAddressInforBean = new AddressInforBean();
        //取出缓存数据
        final String httpDataInDB = HiCache.getInstance().getHttpDataInDB(HistudentUrl.homePageInfo_url, "");
        if (!TextUtils.isEmpty(httpDataInDB)) {
            final Type type = new TypeToken<List<HomeEntity>>() {
            }.getType();
            final List<HomeEntity> homeEntityList = new Gson().fromJson(httpDataInDB, type);
            LIST.clear();
            LIST.addAll(homeEntityList);
            mAdapter.notifyDataSetChanged();
            return;
        }
        showLoadingDialog();
        mPresenter.requestHomeListData(mAddressInforBean);
    }

    @Override
    public void showContent(String message) {
        dismissLoadingDialog();
        if (!TextUtils.isEmpty(message)) {
            ToastTool.showCommonToast(message);
        }
    }

    @Override
    public void responseSuccess(List<HomeEntity> homeEntities) {
        dismissLoadingDialog();
        if (isRefresh) {
            mAdapter.reset();
        }
        mRefreshLayout.finishRefresh();
        LIST.clear();
        LIST.addAll(homeEntities);
        mAdapter.notifyDataSetChanged();
        isRefresh = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        final Disposable disposable = mAdapter.getDisposable();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
