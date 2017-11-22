package com.histudent.jwsoft.histudent.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.model.constant.TransferKeys;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lichaojie on 2017/8/3.
 * desc:
 * 日志编辑 字体颜色选择样式
 */

public class RichEditorSelectColorActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.cb_black)
    CheckBox mBlack;
    @BindView(R.id.cb_red)
    CheckBox mRed;
    @BindView(R.id.cb_orange)
    CheckBox mOrange;
    @BindView(R.id.cb_yellow)
    CheckBox mYellow;
    @BindView(R.id.cb_green)
    CheckBox mGreen;
    @BindView(R.id.cb_blue)
    CheckBox mBlue;
    @BindView(R.id.cb_pink)
    CheckBox mPink;
    @BindView(R.id.cb_purple)
    CheckBox mPurple;
    @BindView(R.id.cb_blue2)
    CheckBox mBlue2;
    @BindView(R.id.cb_lilac2)
    CheckBox mLilac2;
    @BindView(R.id.cb_lilac)
    CheckBox mLilac;
    @BindView(R.id.cb_black2)
    CheckBox mBlack2;
    @BindView(R.id.cb_gray1)
    CheckBox mGray1;
    @BindView(R.id.cb_gray2)
    CheckBox mGray2;
    @BindView(R.id.cb_gray3)
    CheckBox mGray3;
    @BindView(R.id.cb_gray4)
    CheckBox mGray4;

    @BindView(R.id.tv_close)
    TextView mCancel;
    @BindView(R.id.tv_confirm)
    TextView mConfirm;
    private int mCurrentSelectPosition;
    private List<CheckBox> mListCheckBox;
    private List<Integer> mListColors;

    @Override
    public int setViewLayout() {
        return R.layout.activity_editor_select_color;
    }

    @Override
    public void initView() {

    }

    @Override
    public void doAction() {
        super.doAction();
        mListCheckBox = new ArrayList<>();
        mListColors = new ArrayList<>();
        mListCheckBox.add(mBlack);
        mListColors.add(Color.rgb(0, 0, 0));
        mListCheckBox.add(mRed);
        mListColors.add(Color.rgb(251, 5, 3));
        mListCheckBox.add(mOrange);
        mListColors.add(Color.rgb(254, 128, 3));
        mListCheckBox.add(mYellow);
        mListColors.add(Color.rgb(250, 176, 0));
        mListCheckBox.add(mGreen);
        mListColors.add(Color.rgb(40, 181, 80));
        mListCheckBox.add(mBlue);
        mListColors.add(Color.rgb(10, 176, 253));
        mListCheckBox.add(mPink);
        mListColors.add(Color.rgb(255, 1, 253));
        mListCheckBox.add(mPurple);
        mListColors.add(Color.rgb(140, 8, 253));
        mListCheckBox.add(mBlue2);
        mListColors.add(Color.rgb(9, 235, 254));
        mListCheckBox.add(mLilac2);
        mListColors.add(Color.rgb(197, 1, 254));
        mListCheckBox.add(mLilac);
        mListColors.add(Color.rgb(98, 7, 253));
        mListCheckBox.add(mBlack2);
        mListColors.add(Color.rgb(51, 51, 51));
        mListCheckBox.add(mGray1);
        mListColors.add(Color.rgb(101, 101, 102));
        mListCheckBox.add(mGray2);
        mListColors.add(Color.rgb(153, 153, 153));
        mListCheckBox.add(mGray3);
        mListColors.add(Color.rgb(203, 203, 204));
        mListCheckBox.add(mGray4);
        mListColors.add(Color.rgb(228, 228, 228));
        for (int i = 0; i < 16; i++) {
            mListCheckBox.get(i).setTag(i);
            mListCheckBox.get(i).setOnClickListener(this);
        }
        mCancel.setOnClickListener((View view) -> RichEditorSelectColorActivity.this.finish());
        mConfirm.setOnClickListener((View view) -> {
            Intent intent = new Intent();
            intent.putExtra(TransferKeys.RICH_EDITOR_TEXT_COLOR, mListColors.get(mCurrentSelectPosition));
            RichEditorSelectColorActivity.this.setResult(TransferKeys.ConstantNum.NUM_1000, intent);
            RichEditorSelectColorActivity.this.finish();
        });
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        mCurrentSelectPosition = (int) view.getTag();
        for (int i = 0; i < 16; i++) {
            if (i != mCurrentSelectPosition)
                mListCheckBox.get(i).setChecked(false);
        }
    }
}
