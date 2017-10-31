package com.histudent.jwsoft.histudent.body.mine.viewholder;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;

import com.histudent.jwsoft.histudent.R;

/**
 * Created by lichaojie on 2017/9/28.
 * desc:
 * 用于班级圈过滤动态类型
 */

public class DynamicTypeViewHolder {
    public LinearLayoutCompat mLLDynamicLayout;
    public AppCompatTextView mTvModifyTitle;

    public DynamicTypeViewHolder(View view) {
        mLLDynamicLayout = view.findViewById(R.id.ll_dynamic_layout);
        mTvModifyTitle = view.findViewById(R.id.tv_modify_title);
    }
}
