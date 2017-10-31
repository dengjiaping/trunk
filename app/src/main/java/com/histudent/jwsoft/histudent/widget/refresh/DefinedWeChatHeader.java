package com.histudent.jwsoft.histudent.widget.refresh;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.histudent.jwsoft.histudent.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.util.DensityUtil;

/**
 * Created by lichaojie on 2017/7/26.
 * desc:
 * 自定义微信下拉刷新头
 */

public class DefinedWeChatHeader extends LinearLayout implements RefreshHeader{


    private SpinnerStyle mSpinnerStyle = SpinnerStyle.Translate;

    private ImageView mImageView;
    private Animation mAnimation;

    public DefinedWeChatHeader(Context context) {
        super(context);
        this.initView(context, null);
    }

    public DefinedWeChatHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DefinedWeChatHeader(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        DensityUtil density = new DensityUtil();
        setMinimumHeight(density.dip2px(40));
        View layout = LayoutInflater.from(context).inflate(R.layout.refresh_layout_chat_header, null);
        mImageView = layout.findViewById(R.id.iv_chat);
        addView(layout);
    }

    @Override
    public void onPullingDown(float percent, int offset, int headerHeight, int extendHeight) {

    }

    @Override
    public void onReleasing(float percent, int offset, int headerHeight, int extendHeight) {

    }


    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {

    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public void onStartAnimator(RefreshLayout layout, int height, int extendHeight) {
        mAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.loading_anim);
        mAnimation.setInterpolator(new LinearInterpolator());
        mImageView.startAnimation(mAnimation);
    }

    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
        mImageView.clearAnimation();
        return 0;
    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {

    }


    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        return mSpinnerStyle;
    }
}
