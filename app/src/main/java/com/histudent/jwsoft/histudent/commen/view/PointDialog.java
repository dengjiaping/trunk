package com.histudent.jwsoft.histudent.commen.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.histudent.jwsoft.histudent.R;

/**
 * Created by android03_pc on 2017/6/1.
 */

public class PointDialog extends Dialog {

    private MyGifView imageView;
    private Context context;

    public PointDialog(Context context) {
        super(context, R.style.NobackDialog);
        this.context = context;
    }
    public PointDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected PointDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.alertdialog_point);
        imageView = (MyGifView) findViewById(R.id.anim);
    }

    @Override
    public void show() {
        if (imageView != null)
            imageView.setMovieResource(R.raw.pencil);
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (imageView != null)
            imageView.clearAnimation();
    }
}
