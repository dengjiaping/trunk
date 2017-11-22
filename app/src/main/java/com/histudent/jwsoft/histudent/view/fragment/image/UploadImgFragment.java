package com.histudent.jwsoft.histudent.view.fragment.image;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.view.activity.image.ShowImageActivity;
import com.histudent.jwsoft.histudent.view.adapter.UploadImgAdapter;
import com.histudent.jwsoft.histudent.view.adapter.decoration.UploadItemDecoration;
import com.histudent.jwsoft.histudent.base.BaseFragment;
import com.histudent.jwsoft.histudent.body.mine.model.PhotosModel;
import com.histudent.jwsoft.histudent.commen.bean.AlbumAuthorityModel;
import com.histudent.jwsoft.histudent.commen.keyword.utils.DisplayUtils;
import com.histudent.jwsoft.histudent.comment2.utils.ActionTypeEnum;
import com.histudent.jwsoft.histudent.model.entity.ImageAttrEntity;
import com.histudent.jwsoft.histudent.model.entity.UploadImgClickEvent;
import com.histudent.jwsoft.histudent.presenter.image.UploadPresenter;
import com.histudent.jwsoft.histudent.presenter.image.contract.UploadContract;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by huyg on 2017/9/28.
 * 最新上传
 */

public class UploadImgFragment extends BaseFragment<UploadPresenter> implements UploadContract.View {
    @BindView(R.id.srl_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.upload_recycler)
    RecyclerView mRecycler;


    private int PAGE_INDEX = 0;
    private int PAGE_SIZE = 15;
    private static final int PULL_DOWN = 0;
    private static final int PULL_UP = 1;
    private int loading = 0;
    private int userType;
    private ActionTypeEnum typeEnum;
    private AlbumAuthorityModel model;
    private UploadImgAdapter mAdapter;
    private LinearLayoutManager mManager;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_upload_img;
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


    @Override
    protected void init() {
        initArguments();
        initKey();
        initRefresh();
        initAdapter();
        initData();
    }

    private void initRefresh() {
        mRefreshLayout.setEnableAutoLoadmore(true);
        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                loading = PULL_UP;
                getUploadImg((int) Math.ceil(((float) mAdapter.getItemCount()) / PAGE_SIZE), PAGE_SIZE);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                loading = PULL_DOWN;
                PAGE_INDEX = 0;
                getUploadImg(PAGE_INDEX, PAGE_SIZE);
            }
        });
    }

    private void initAdapter() {
        mAdapter = new UploadImgAdapter(getActivity());
        mManager = new GridLayoutManager(getActivity(), 3);
        mRecycler.setLayoutManager(mManager);
        mRecycler.addItemDecoration(new UploadItemDecoration(getActivity()));
        mRecycler.setAdapter(mAdapter);
    }

    /**
     * @param typeEnum 相册类型
     * @param model    相册权限类型相关实体类
     * @return
     */
    public static UploadImgFragment getUploadFragment(ActionTypeEnum typeEnum, AlbumAuthorityModel model) {

        UploadImgFragment fragment = new UploadImgFragment();
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
        getUploadImg(PAGE_INDEX, PAGE_SIZE);
    }

    private void getUploadImg(int pageIndex, int pageSize) {
        mPresenter.getRecentlyList(model.getId(), userType, pageIndex, pageSize);
    }


    @Override
    public void showContent(String message) {

    }

    @Subscribe
    public void onEvent(UploadImgClickEvent event) {
        List<PhotosModel> photosModels=event.models;
        int position = event.position;
        View view = event.view;
        List<ImageAttrEntity> imageAttrs = new ArrayList<>();
        for (int i = 0; i < photosModels.size(); i++) {
            ImageAttrEntity imageAttrEntity = new ImageAttrEntity();
            imageAttrEntity.setBigSizeUrl(photosModels.get(i).getFilePath());
            imageAttrEntity.setThumbnailUrl(photosModels.get(i).getFilePath());
            imageAttrEntity.setId(photosModels.get(i).getPhotoId());
            imageAttrs.add(imageAttrEntity);
        }
        showImageDetail(view,position,imageAttrs);
    }





    private void showImageDetail(View view, int position, List<ImageAttrEntity> imageAttrs) {
        Intent intent = new Intent(getActivity(), ShowImageActivity.class);
        Bundle bundle = new Bundle();
        int[] location = new int[2];
        view.getLocationInWindow(location);
        int x = location[0];
        int y = location[1];
        bundle.putInt("x", x);
        bundle.putInt("y", y);
        bundle.putInt("width", view.getWidth());
        bundle.putInt("height", view.getHeight());
        bundle.putBoolean("isOperate", true);
        bundle.putSerializable("photos", (Serializable) imageAttrs);
        bundle.putInt("position", position);
        bundle.putInt("column_num", 3);
        bundle.putInt("horizontal_space", DisplayUtils.dp2px(getActivity(), 4));
        bundle.putInt("vertical_space", DisplayUtils.dp2px(getActivity(), 4));

        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void showRecentlyList(List<PhotosModel> photosModels) {
        switch (loading) {
            case PULL_DOWN:
                mRefreshLayout.finishRefresh();
                mAdapter.setList(photosModels);
                break;
            case PULL_UP:
                if (photosModels == null || photosModels.size() < PAGE_SIZE) {
                    mRefreshLayout.setEnableLoadmore(false);
                } else {
                    mRefreshLayout.setEnableLoadmore(true);
                }
                mRefreshLayout.finishLoadmore();
                mAdapter.addList(photosModels);
                break;
        }
    }

    @Override
    public void requestError() {
        mRefreshLayout.finishLoadmore();
        mRefreshLayout.finishRefresh();
    }
}
