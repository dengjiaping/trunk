package com.histudent.jwsoft.histudent.widget.popupwindow;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.myclass.activity.ClassCircleActivity;
import com.histudent.jwsoft.histudent.commen.keyword.utils.ScreenUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.sunfusheng.glideimageview.util.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichaojie on 2017/9/28.
 * desc:
 * 用于班级圈动态分类标题
 */

public class PopupWindowDynamicCategory extends PopupWindow implements View.OnClickListener {


    private final Activity mContext;
    private AppCompatCheckBox mCbDynamicAll;
    private AppCompatCheckBox mCbDynamicEssay;
    private AppCompatCheckBox mCbDynamicLog;
    private AppCompatCheckBox mCbDynamicPhoto;
    private AppCompatCheckBox mCbDynamicActivity;
    private AppCompatTextView mTvModifyTitle;
    /**
     * 0:所有动态
     * 1:随记
     * 2:日志
     * 3:照片
     * 4:活动
     */
    private int mCurrentSelectType = -1;

    private List<AppCompatCheckBox> mListCheckBox = new ArrayList<>();
    private View mLLBottomLayout;
    private View mLLTopLayout;
    private int mTopDistance;
    private int mStatusHeight;

    public PopupWindowDynamicCategory(Activity context, int type, int height) {
        this.mContext = context;
        this.mCurrentSelectType = type;
        this.mTopDistance = height;
    }

    public static final PopupWindowDynamicCategory create(Activity context, int type, int height) {
        return new PopupWindowDynamicCategory(context, type, height);
    }


    public void showPopupWindow(View view) {
        final View rootView = LayoutInflater.from(mContext).inflate(R.layout.popupwidow_dynamic_layout, null);
        final int width = DisplayUtil.getWindowWidth(mContext);
        final int height = DisplayUtil.getWindowHeight(mContext);
        mStatusHeight = ScreenUtils.getStatusHeight(mContext);
        final int popupWindowHeight = height - SystemUtil.dp2px(45) - mStatusHeight;
        setContentView(rootView);
        this.setWidth(width);
        this.setHeight(popupWindowHeight);
        initView(rootView);
        initData();
        loadListener();
        this.setOutsideTouchable(true);
        this.setFocusable(true);
        this.showAtLocation(view, Gravity.TOP, 0, SystemUtil.dp2px(45) + mStatusHeight);
    }


    private void initView(View rootView) {
        mTvModifyTitle = rootView.findViewById(R.id.tv_modify_title);
        mCbDynamicAll = rootView.findViewById(R.id.cb_dynamic_all);
        mCbDynamicEssay = rootView.findViewById(R.id.cb_dynamic_essay);
        mCbDynamicLog = rootView.findViewById(R.id.cb_dynamic_log);
        mCbDynamicPhoto = rootView.findViewById(R.id.cb_dynamic_photo);
        mCbDynamicActivity = rootView.findViewById(R.id.cb_dynamic_activity);
        mLLBottomLayout = rootView.findViewById(R.id.ll_bottom_layout);
        mLLTopLayout = rootView.findViewById(R.id.ll_top_layout);
    }


    private void initData() {
        if (mTopDistance != 0) {
            final ViewGroup.LayoutParams layoutParams = mLLTopLayout.getLayoutParams();
            layoutParams.height = mTopDistance - mStatusHeight - SystemUtil.dp2px(45);
            mLLTopLayout.setLayoutParams(layoutParams);
        }

        if (mContext instanceof ClassCircleActivity) {
            final String content = ((ClassCircleActivity) mContext).getTextBySelectType(mCurrentSelectType);
            mTvModifyTitle.setText(content);
        }
    }

    private void loadListener() {
        mTvModifyTitle.setOnClickListener(this);
        mLLBottomLayout.setOnClickListener(this);
        mLLTopLayout.setOnClickListener(this);
        mListCheckBox.clear();
        mListCheckBox.add(mCbDynamicAll);
        mListCheckBox.add(mCbDynamicEssay);
        mListCheckBox.add(mCbDynamicLog);
        mListCheckBox.add(mCbDynamicPhoto);
        mListCheckBox.add(mCbDynamicActivity);
        for (int i = 0; i < mListCheckBox.size(); i++) {
            final AppCompatCheckBox subCompatCheckBox = mListCheckBox.get(i);
            subCompatCheckBox.setTag(i);
            subCompatCheckBox.setOnClickListener(this);
            if (i == mCurrentSelectType) {
                subCompatCheckBox.setChecked(true);
                subCompatCheckBox.setTextColor(ContextCompat.getColor(mContext, R.color._28ca7e));
            } else {
                subCompatCheckBox.setChecked(false);
                subCompatCheckBox.setTextColor(ContextCompat.getColor(mContext, R.color._666666));
            }
        }
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.tv_modify_title || id == R.id.ll_bottom_layout || id == R.id.ll_top_layout) {
            this.dismiss();
            return;
        }
        final int tag = (int) v.getTag();
        mCurrentSelectType = tag;
        if (mContext instanceof ClassCircleActivity)
            ((ClassCircleActivity) mContext).changeUserSelectDynamicType(mCurrentSelectType);
        for (int i = 0; i < mListCheckBox.size(); i++) {
            if (mCurrentSelectType != i) {
                mListCheckBox.get(i).setChecked(false);
                mListCheckBox.get(i).setTextColor(ContextCompat.getColor(mContext, R.color._666666));
            } else {
                mListCheckBox.get(i).setTextColor(ContextCompat.getColor(mContext, R.color._28ca7e));
            }
        }
        this.dismiss();
    }
}
