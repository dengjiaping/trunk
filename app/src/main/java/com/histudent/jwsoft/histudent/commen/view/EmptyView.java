package com.histudent.jwsoft.histudent.commen.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;

/**
 * Created by android03_pc on 2017/5/31.
 */

public class EmptyView extends LinearLayout{
    private Context context;
    private ImageView mImageView;
    private TextView mTextView;
    private TextView mButton;
    public EmptyView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }
    private void init(){
        View view = LayoutInflater.from(context).inflate(R.layout.new_empty_view,null);
        mImageView = (ImageView) view.findViewById(R.id.empty_view_iv);
        mImageView.setVisibility(GONE);
        mTextView = (TextView) view.findViewById(R.id.empty_view_tv);
        mTextView.setVisibility(GONE);
        mButton = (TextView) view.findViewById(R.id.empty_view_btn);
        mButton.setVisibility(GONE);
        invalidate();
    }
}
