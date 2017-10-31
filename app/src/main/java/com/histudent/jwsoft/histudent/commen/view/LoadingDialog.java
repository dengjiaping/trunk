package com.histudent.jwsoft.histudent.commen.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.histudent.jwsoft.histudent.R;

/**
 * 等待层（数据加载）
 * Created by liuguiyu-pc on 2017/3/9.
 */

public class LoadingDialog extends Dialog {

    private ImageView imageView;
    private Context context;
    private Animation operatingAnim;

    public LoadingDialog(Context context) {
        super(context, R.style.NobackDialog);
        this.context = context;
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.alertdialog_press);

        imageView = (ImageView) findViewById(R.id.anim);
        operatingAnim = AnimationUtils.loadAnimation(context, R.anim.loading_anim);
        operatingAnim.setInterpolator(new LinearInterpolator());

    }

    @Override
    public void show() {
        super.show();
        if (operatingAnim != null && imageView != null)
            imageView.startAnimation(operatingAnim);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (imageView != null)
            imageView.clearAnimation();
    }
}
