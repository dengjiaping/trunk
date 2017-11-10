package com.histudent.jwsoft.histudent.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by lichaojie on 2017/10/26.
 * des:自定义样式对话框
 * 中间为输入内容
 */

public class CommonInputDialog extends Dialog {

    private TextView mTitle;
    private EditText mEtInputContent;
    private Button mNegative, mPositive;
    private String mTitleContent, mNegativeContent, mPositiveContent;
    private OnPositiveClickListener mPositiveClickListener;
    private OnNegativeClickListener mNegativeClickListener;

    public CommonInputDialog(Context context) {
        super(context, R.style.common_dialog);
        final Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        final WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.height = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.gravity = Gravity.CENTER;
        window.setAttributes(attributes);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadView();
        loadData();
        loadListener();
    }

    private void loadView() {
        setContentView(R.layout.common_dialog_input_layout);
        setCanceledOnTouchOutside(false);
        findViewById(R.id.rl_root_layout).getBackground().mutate().setAlpha(150);
        mTitle = findViewById(R.id.tv_title);
        mEtInputContent = findViewById(R.id.et_input_content);
        mNegative = findViewById(R.id.bt_negative);
        mPositive = findViewById(R.id.bt_positive);
    }

    public void showDefaultSoftKeyboard() {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) mEtInputContent.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(mEtInputContent, 0);
            }

        }, 100);
    }

    private void loadData() {
        if (mTitleContent == null || mNegativeContent == null || mPositiveContent == null)
            return;
        mTitle.setText(mTitleContent);
        mNegative.setText(mNegativeContent);
        mPositive.setText(mPositiveContent);
    }

    private void loadListener() {
        mNegative.setOnClickListener((View v) -> {
            if (mNegativeClickListener != null) {
                mNegativeClickListener.onNegativeClick();
                setEtInputContent("");
                this.dismiss();
            }
        });

        mPositive.setOnClickListener((View v) -> {
            if (mPositiveClickListener != null)
                mPositiveClickListener.onPositiveClick();
        });

    }

    public void setTitle(String title) {
        mTitleContent = title;
    }

    public String getInputContent() {
        return mEtInputContent.getText().toString().trim();
    }

    public void setEtInputContent(String content) {
        mEtInputContent.setText(content);
    }

    public void setOnNegativeClickListener(String content, OnNegativeClickListener onNegativeClickListener) {
        mNegativeContent = content;
        this.mNegativeClickListener = onNegativeClickListener;
    }

    public void setOnNegativeClickListener(OnNegativeClickListener onNegativeClickListener) {
        this.mNegativeClickListener = onNegativeClickListener;
    }

    public void setOnPositiveClickListener(String content, OnPositiveClickListener onPositiveClickListener) {
        mPositiveContent = content;
        this.mPositiveClickListener = onPositiveClickListener;
    }

    public void setOnPositiveClickListener(OnPositiveClickListener onPositiveClickListener) {
        this.mPositiveClickListener = onPositiveClickListener;
    }


    public interface OnNegativeClickListener {
        void onNegativeClick();
    }

    public interface OnPositiveClickListener {
        void onPositiveClick();
    }
}
