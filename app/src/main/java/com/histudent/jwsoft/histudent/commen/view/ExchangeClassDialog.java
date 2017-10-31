package com.histudent.jwsoft.histudent.commen.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by liuguiyu-pc on 2016/8/31.
 */
public class ExchangeClassDialog extends Dialog {
    public ExchangeClassDialog(Context context) {
        super(context);
    }

    public ExchangeClassDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ExchangeClassDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView();

    }
}
