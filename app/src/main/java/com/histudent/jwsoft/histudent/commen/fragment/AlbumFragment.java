package com.histudent.jwsoft.histudent.commen.fragment;

import android.os.Bundle;
import android.widget.GridView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.base.BaseFragment;
import com.histudent.jwsoft.histudent.body.hiworld.activity.CreateAlbumActivity;
import com.histudent.jwsoft.histudent.body.message.adapter.ClassPictureListAdapter;
import com.histudent.jwsoft.histudent.body.mine.model.AlbumInfoModel;
import com.histudent.jwsoft.histudent.body.myclass.helper.ClassHelper;
import com.histudent.jwsoft.histudent.commen.bean.AlbumAuthorityModel;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.comment2.utils.ActionTypeEnum;
import com.histudent.jwsoft.histudent.entity.AlbumClickEvent;
import com.histudent.jwsoft.histudent.presenter.image.AlbumPresenter;
import com.histudent.jwsoft.histudent.presenter.image.contract.AlbumContract;
import com.netease.nim.uikit.common.util.string.StringUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;

/**
 * 相册中心的fragment
 * Created by ZhangYT on 2016/12/21.
 */

public class AlbumFragment extends BaseFragment<AlbumPresenter> implements AlbumContract.View {

    private ClassPictureListAdapter mAlbumAdapter;
    private int userType;
    private ActionTypeEnum typeEnum;
    private AlbumAuthorityModel model;
    private int PAGE_INDEX = 0;
    private int PAGE_SIZE = 8;
    private static final int PULL_DOWN = 0;
    private static final int PULL_UP = 1;
    private int loading = 0;


    @BindView(R.id.srl_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.album_gridview)
    GridView mAlbumGridView;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.group_album;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe
    public void onEvent(AlbumClickEvent event) {
        if (event != null) {
            if (StringUtil.isEmpty(event.model.getAlbumId())) {
                CreateAlbumActivity.start(getActivity(), typeEnum, model.getId());
            } else {
                ClassHelper.gotoAlbum(getActivity(), event.model.getAlbumId());
            }
        }
    }

    @Override
    protected void init() {
        initArguments();
        initKey();
        initAdapter();
        initRefresh();
        initData();
    }

    private void initRefresh() {
        mRefreshLayout.setEnableAutoLoadmore(true);
        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                loading = PULL_UP;
                getAlbums((int) Math.ceil(((float) mAlbumAdapter.getCount()) / PAGE_SIZE), PAGE_SIZE);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                loading = PULL_DOWN;
                PAGE_INDEX = 0;
                getAlbums(PAGE_INDEX, PAGE_SIZE);
            }
        });
    }

    private void initAdapter() {
        mAlbumAdapter = new ClassPictureListAdapter(getActivity());
        mAlbumGridView.setAdapter(mAlbumAdapter);
        mAlbumGridView.setNumColumns(2);
        mAlbumGridView.setHorizontalSpacing(SystemUtil.dip2px(mContext, 8));
    }

    /**
     * @param typeEnum 相册类型
     * @param model    相册权限类型相关实体类
     * @return
     */
    public static AlbumFragment getAlbumFragment(ActionTypeEnum typeEnum, AlbumAuthorityModel model) {

        AlbumFragment fragment = new AlbumFragment();
        Bundle args = new Bundle();
        args.putSerializable("typeEnum", typeEnum);
        args.putSerializable("model", model);
        fragment.setArguments(args);
        return fragment;
    }


    private void initArguments() {
        typeEnum = ((ActionTypeEnum) getArguments().getSerializable("typeEnum"));
        model = ((AlbumAuthorityModel) getArguments().getSerializable("model"));
    }

    private void initKey() {
        userType = typeEnum == ActionTypeEnum.CLASS ? 2 : typeEnum == ActionTypeEnum.GROUP ? 3 : 1;
    }

    public void initData() {
        getAlbums(PAGE_INDEX, PAGE_SIZE);
    }

    public void getAlbums(int pageIndex, int pageSize) {
        mPresenter.getAlbumList(model.getId(), userType, "", pageIndex, pageSize);
    }


    @Override
    public void showContent(String message) {

    }

    @Override
    public void showAlbumList(List<AlbumInfoModel> albumInfoModels) {

        switch (loading) {
            case PULL_DOWN:
                mRefreshLayout.finishRefresh();
                albumInfoModels.add(0,new AlbumInfoModel());
                mAlbumAdapter.setList(albumInfoModels);
                break;
            case PULL_UP:
                if (albumInfoModels == null || albumInfoModels.size() < PAGE_SIZE) {
                    mRefreshLayout.setEnableLoadmore(false);
                } else {
                    mRefreshLayout.setEnableLoadmore(true);
                }
                mRefreshLayout.finishLoadmore();
                mAlbumAdapter.addList(albumInfoModels);
                break;
        }
    }

    @Override
    public void requestError() {
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadmore();
    }
}
