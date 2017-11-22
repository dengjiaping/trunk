package com.histudent.jwsoft.histudent.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.fragment.BaseFragment;

/**
 * Created by lichaojie on 2017/7/10.
 */

public abstract class CommunityBaseFragment extends BaseFragment {

    /**
     * 当前View是否被创建
     */
    private boolean mIsViewCreate;
    /**
     * 当前fragment是否显示
     */
    private boolean mIsFragmentVisible;

    protected View mView;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            mIsFragmentVisible = true;
            lazyLoadData();
        } else {
            mIsFragmentVisible = false;
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_page, container, false);
        loadView();
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIsViewCreate = true;
        lazyLoadData();
    }


    private void lazyLoadData() {
        if (mIsViewCreate && mIsFragmentVisible) {
            loadData();
            mIsFragmentVisible = false;
            mIsViewCreate = false;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsViewCreate = false;
        mIsFragmentVisible = false;
    }

    protected abstract void loadView();

    protected abstract void loadData();
}
