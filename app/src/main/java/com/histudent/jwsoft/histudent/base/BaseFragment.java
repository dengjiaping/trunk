package com.histudent.jwsoft.histudent.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.histudent.jwsoft.histudent.HTApplication;
import com.histudent.jwsoft.histudent.commen.view.LoadingDialog;
import com.histudent.jwsoft.histudent.model.di.componet.DaggerFragmentComponent;
import com.histudent.jwsoft.histudent.model.di.componet.FragmentComponent;
import com.histudent.jwsoft.histudent.model.di.module.FragmentModule;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by codeest on 2016/8/2.
 * MVP Fragment基类
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView {

    @Inject
    protected T mPresenter;
    protected View mView;
    protected Activity mActivity;
    protected Context mContext;
    private Unbinder mUnBinder;
    protected boolean isInited = false;

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), null);
        initInject();
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.attachView(this);
        mUnBinder = ButterKnife.bind(this, view);
        init();
    }


    protected FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .appComponent(HTApplication.getInstance().getAppComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
        dismissLoadingDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachView();
    }


    protected abstract void initInject();

    protected abstract int getLayoutId();

    protected abstract void init();

    private LoadingDialog mLoadingDialog;

    public void showLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(getContext());
            mLoadingDialog.show();
        } else {
            if (!mLoadingDialog.isShowing()) {
                mLoadingDialog.show();
            }
        }
    }

    public void dismissLoadingDialog() {
        if (mLoadingDialog != null) {
            if (mLoadingDialog.isShowing())
                mLoadingDialog.dismiss();
        }
    }
}