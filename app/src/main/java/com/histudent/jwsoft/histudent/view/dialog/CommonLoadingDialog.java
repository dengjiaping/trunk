package com.histudent.jwsoft.histudent.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import com.histudent.jwsoft.histudent.R;

/**
 * Created by lichaojie on 2017/8/16.
 * desc:
 * 全局Dialog样式
 * 之后相关自定义Dialog加载而已均可放在此
 */

public class CommonLoadingDialog {

    private static Dialog mLoadingDialog;
    private static Animation mAnimation;
    private static View mLoadingView;

    public static final void showDialogForLoading(Activity activity) {
        final View view = LayoutInflater.from(activity).inflate(R.layout.common_loading_dialog, null);
        mLoadingDialog = new Dialog(activity, R.style.NobackDialog);
        mLoadingView = view.findViewById(R.id.iv_loading);
        mAnimation = AnimationUtils.loadAnimation(activity, R.anim.loading_anim);
        mAnimation.setInterpolator(new LinearInterpolator());
        mLoadingDialog.setContentView(view);
        mLoadingDialog.setCancelable(false);
        mLoadingView.setAnimation(mAnimation);
        mLoadingDialog.show();
    }

    public static final void closeDialogForLoading() {
        mLoadingView.clearAnimation();
        mLoadingDialog.cancel();
    }
}
