package com.histudent.jwsoft.histudent.view.widget.popupwindow;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.keyword.utils.ScreenUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.model.constant.Const;
import com.histudent.jwsoft.histudent.model.entity.WorkTypeEvent;

import org.greenrobot.eventbus.EventBus;

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

public class PopupWindowPublishHomework extends PopupWindow implements View.OnClickListener {
    private View mRootView;
    private Activity mContext;
    private LinearLayout mLLHomeworkContent;

    private PopupWindowPublishHomework(Activity context) {
        this.mContext = context;
    }

    public static final PopupWindowPublishHomework create(Activity context) {
        return new PopupWindowPublishHomework(context);
    }

    public void showMoreWindow(View anchor) {
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.popupwindow_publish_homewok_dialog, null);
        final int width = mContext.getWindowManager().getDefaultDisplay().getWidth();
        final int height = mContext.getWindowManager().getDefaultDisplay().getHeight();
        setContentView(mRootView);
        this.setWidth(width);
        this.setHeight(height - ScreenUtils.getStatusHeight(mContext) - SystemUtil.dp2px(45));
        mLLHomeworkContent = mRootView.findViewById(R.id.ll_homework_content);
        mRootView.findViewById(R.id.ll_homework_text).setOnClickListener(this);
        mRootView.findViewById(R.id.ll_homework_video).setOnClickListener(this);
        mRootView.findViewById(R.id.ll_homework_sound).setOnClickListener(this);
        mRootView.findViewById(R.id.ll_homework_picture).setOnClickListener(this);
        mRootView.findViewById(R.id.close).setOnClickListener(this);
        showAnimation(mLLHomeworkContent);
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
            case R.id.ll_homework_text:
                EventBus.getDefault().post(new WorkTypeEvent(Const.WORK_TEXT));
                break;

            case R.id.ll_homework_video:
                EventBus.getDefault().post(new WorkTypeEvent(Const.WORK_VEDIO));
                break;

            case R.id.ll_homework_sound:
                EventBus.getDefault().post(new WorkTypeEvent(Const.WORK_VOICE));
                break;

            case R.id.ll_homework_picture:
                EventBus.getDefault().post(new WorkTypeEvent(Const.WORK_PHOTO));
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
            closeAnimation(mLLHomeworkContent);
            dismiss();
        }
    }

}
