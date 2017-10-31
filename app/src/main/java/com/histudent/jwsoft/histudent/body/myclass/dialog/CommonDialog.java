package com.histudent.jwsoft.histudent.body.myclass.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;


/**
 * Created by lichaojie on 2017/6/26.
 * des:自定义样式对话框
 */

public class CommonDialog extends Dialog {

    private TextView mTitle, mDesMsg;
    private Button mNegative, mPositive;
    private String mTitleContent, mDesMsgContent, mNegativeContent, mPositiveContent;
    private OnPositiveClickListener mPositiveClickListener;
    private OnNegativeClickListener mNegativeClickListener;

    public CommonDialog(Context context) {
        super(context, R.style.common_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_dialog_layout);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        loadView();
        loadData();
        loadListener();
    }

    private void loadView() {
        mTitle = (TextView) findViewById(R.id.tv_title);
        mDesMsg = (TextView) findViewById(R.id.tv_message);
        mNegative = (Button) findViewById(R.id.bt_negative);
        mPositive = (Button) findViewById(R.id.bt_positive);
    }

    private void loadData() {
        if (mTitleContent == null || mDesMsgContent == null || mNegativeContent == null || mPositiveContent == null)
            return;
        mTitle.setText(mTitleContent);
        mDesMsg.setText(mDesMsgContent);
        mNegative.setText(mNegativeContent);
        mPositive.setText(mPositiveContent);
    }

    private void loadListener() {
        mNegative.setOnClickListener((View v) -> {
            if (mNegativeClickListener != null)
                mNegativeClickListener.onNegativeClick();

        });

        mPositive.setOnClickListener((View v) -> {
            if (mPositiveClickListener != null)
                mPositiveClickListener.onPositiveClick();

        });

    }

    public void setTitle(String title) {
        mTitleContent = title;
    }

    public void setDesMsg(String desMsg) {
        mDesMsgContent = desMsg;
    }

    public void setOnNegativeClickListener(String content, OnNegativeClickListener onNegativeClickListener) {
        mNegativeContent = content;
        this.mNegativeClickListener = onNegativeClickListener;
    }

    public void setOnPositiveClickListener(String content, OnPositiveClickListener onPositiveClickListener) {
        mPositiveContent = content;
        this.mPositiveClickListener = onPositiveClickListener;
    }


    public interface OnNegativeClickListener {
        void onNegativeClick();
    }

    public interface OnPositiveClickListener {
        void onPositiveClick();
    }
}
