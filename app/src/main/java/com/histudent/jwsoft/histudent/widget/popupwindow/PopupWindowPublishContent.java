package com.histudent.jwsoft.histudent.widget.popupwindow;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.HTMainActivity;
import com.histudent.jwsoft.histudent.body.hiworld.activity.EssayActivity;
import com.histudent.jwsoft.histudent.body.hiworld.activity.LogActivity;
import com.histudent.jwsoft.histudent.body.hiworld.activity.UploadPhotoActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.keyword.utils.ScreenUtils;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.comment2.utils.ActionTypeEnum;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lichaojie on 2017/9/26.
 * desc:
 * app 发布页面
 * 动态弹出框
 */

public class PopupWindowPublishContent extends PopupWindow implements View.OnClickListener {
    private View mRootView;
    private RelativeLayout mRLContentView;
    private Activity mContext;
    private IconView mClose;
    private int mRequestCode;
    private int mResultCode;
    private final RotateAnimation mRotateAnimation = new RotateAnimation(
            0f, 45f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

    private PopupWindowPublishContent(Activity context, int requestCode, int resultCode) {
        this.mContext = context;
        this.mRequestCode = requestCode;
        this.mResultCode = resultCode;
    }

    public static final PopupWindowPublishContent create(Activity context, int requestCode, int resultCode) {
        return new PopupWindowPublishContent(context, requestCode, resultCode);
    }

    public void showMoreWindow(View anchor) {
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.popupwindow_publish_dialog, null);
        final int width = mContext.getWindowManager().getDefaultDisplay().getWidth();
        final int height = mContext.getWindowManager().getDefaultDisplay().getHeight();
        setContentView(mRootView);
        this.setWidth(width);
        this.setHeight(height - ScreenUtils.getStatusHeight(mContext));

        mRLContentView = mRootView.findViewById(R.id.rl_content_view);
        mClose = mRootView.findViewById(R.id.close);
        mClose.setOnClickListener(this);
        showAnimation(mRLContentView);
        setOutsideTouchable(true);
        setFocusable(true);
        showAtLocation(anchor, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 显示进入动画效果
     *
     * @param layout
     */
    private void showAnimation(ViewGroup layout) {
        //设置close动画
        mRotateAnimation.setFillAfter(true);
        mRotateAnimation.setDuration(200);
        mClose.setAnimation(mRotateAnimation);
        mRotateAnimation.start();

        //遍历所有子view
        for (int i = 0; i < layout.getChildCount(); i++) {
            final View child = layout.getChildAt(i);
            if (child.getId() == R.id.close) {
                continue;
            }
            child.setOnClickListener(this);
            child.setVisibility(View.INVISIBLE);
            //子view 延迟动画
            Observable.timer(i * 80, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((Long aLong) -> {
                        child.setVisibility(View.VISIBLE);
                        ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 600, 0);
                        fadeAnim.setDuration(300);
                        KickBackAnimator kickAnimator = new KickBackAnimator();
                        kickAnimator.setDuration(150);
                        fadeAnim.setEvaluator(kickAnimator);
                        fadeAnim.start();
                    });
        }

    }



    /**
     * 关闭动画效果
     *
     * @param layout
     */
    private void closeAnimation(ViewGroup layout) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            final View child = layout.getChildAt(i);
            if (child.getId() == R.id.close) {
                continue;
            }
            Observable.timer((layout.getChildCount() - i - 1) * 30, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((Long aLong) -> {
                        child.setVisibility(View.VISIBLE);
                        ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 0, 600);
                        fadeAnim.setDuration(200);
                        KickBackAnimator kickAnimator = new KickBackAnimator();
                        kickAnimator.setDuration(100);
                        fadeAnim.setEvaluator(kickAnimator);
                        fadeAnim.start();
                        fadeAnim.addListener(new Animator.AnimatorListener() {

                            @Override
                            public void onAnimationStart(Animator animation) {
                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                child.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {
                            }
                        });
                    });

            if (child.getId() == R.id.ll_essay) {
                Observable.timer((layout.getChildCount() - i) * 30 + 80, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(((Long aLong) -> dismiss()));
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_essay:
                EssayActivity.start(mContext, mRequestCode, mResultCode);
                break;
            case R.id.ll_upload_photo:
                UploadPhotoActivity.start(mContext, ActionTypeEnum.CLASSANDOWNER,
                        HiCache.getInstance().getLoginUserInfo().getUserId(), true, mRequestCode);
                break;
            case R.id.ll_write_log:
                final Intent intent = new Intent(mContext, LogActivity.class);
                mContext.startActivityForResult(intent, mRequestCode);
                break;
            case R.id.close:
                break;
            default:
                break;
        }
        close();
    }

    public void close() {
        if (isShowing()) {
            closeAnimation(mRLContentView);
        }
        mRotateAnimation.cancel();
        ((HTMainActivity) mContext).showPublishIcon();
    }

}
