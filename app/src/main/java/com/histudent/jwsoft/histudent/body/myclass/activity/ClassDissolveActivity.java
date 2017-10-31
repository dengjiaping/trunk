package com.histudent.jwsoft.histudent.body.myclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.CodeNum;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.myclass.helper.ClassHelper;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.manage.ParamsManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by liuguiyu-pc on 2017/4/24.
 * 解散班级(社群)
 */

public class ClassDissolveActivity extends BaseActivity {

    private TextView mTvTitle;

    private AppCompatTextView mBtDissolve, mTip00, mTip01, mTip02;
    private boolean isClassAction;
    private String mGroupId;

    private final List<AppCompatTextView> mListDissolve = new ArrayList<>();

    @BindView(R.id.tv_dissolve_text_1)
    AppCompatTextView mTvDissolve1;
    @BindView(R.id.tv_dissolve_text_2)
    AppCompatTextView mTvDissolve2;
    @BindView(R.id.tv_dissolve_text_3)
    AppCompatTextView mTvDissolve3;
    @BindView(R.id.tv_dissolve_text_4)
    AppCompatTextView mTvDissolve4;
    @BindView(R.id.tv_dissolve_text_5)
    AppCompatTextView mTvDissolve5;
    @BindView(R.id.tv_dissolve_text_6)
    AppCompatTextView mTvDissolve6;

    @BindView(R.id.et_input_text)
    AppCompatEditText mEtInputText;


    public static void start(Activity activity, int requestCode, boolean isClassAction) {
        Intent intent = new Intent(activity, ClassDissolveActivity.class);
        intent.putExtra("isClassAction", isClassAction);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void start(Activity activity, int requestCode, boolean isClassAction, String groupId) {
        Intent intent = new Intent(activity, ClassDissolveActivity.class);
        intent.putExtra("isClassAction", isClassAction);
        intent.putExtra("groupId", groupId);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_class_dissolve;
    }

    @Override
    public void initView() {
        isClassAction = getIntent().getBooleanExtra("isClassAction", true);
        mGroupId = getIntent().getStringExtra("groupId");
        mTvTitle = (TextView) findViewById(R.id.title_middle_text);
        mTip00 = (AppCompatTextView) findViewById(R.id.tip_00);
        mTip01 = (AppCompatTextView) findViewById(R.id.tip_01);
        mTip02 = (AppCompatTextView) findViewById(R.id.tip_02);
        mBtDissolve = (AppCompatTextView) findViewById(R.id.dissolve_button);
    }

    @Override
    public void doAction() {
        super.doAction();
        mListDissolve.clear();
        mListDissolve.add(mTvDissolve1);
        mListDissolve.add(mTvDissolve2);
        mListDissolve.add(mTvDissolve3);
        mListDissolve.add(mTvDissolve4);
        mListDissolve.add(mTvDissolve5);
        mListDissolve.add(mTvDissolve6);
        mBtDissolve.setOnClickListener((View view) -> deleteClass(isClassAction));

        mEtInputText.addTextChangedListener(new InputTextWatcher());

        if (isClassAction) {
            mTvTitle.setText("解散班级");
            mTip00.setText(R.string.tip_dissolve_class_00);
            mTip01.setText(R.string.tip_dissolve_class_01);
            mTip02.setText(R.string.tip_dissolve_class_02);
        } else {
            mTvTitle.setText("解散社群");
            mTip00.setText(R.string.tip_dissolve_group_00);
            mTip01.setText(R.string.tip_dissolve_group_01);
            mTip02.setText(R.string.tip_dissolve_group_02);
        }

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.title_left://返回
                finish();
                break;
        }
    }

    private void deleteClass(boolean isClassAction) {
        final String info = mEtInputText.getText().toString().trim();

        if (isClassAction) {
            if (!"我要解散班级".equals(info)) {
                ReminderHelper.getIntentce().ToastShow_with_image(this, "输入的确认解散文字错误", R.string.icon_remind);
            } else {
                ClassHelper.deleteClassOwner(this, HiCache.getInstance().getClassDetailInfo(), new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        ReminderHelper.getIntentce().ToastShow_with_image(ClassDissolveActivity.this, "班级已解散", R.string.icon_check);
                        setResult(CodeNum.CLASS_DISSOLVE_RESULT);
                        finish();
                    }

                    @Override
                    public void onFailure(String errorMsg) {

                    }
                });
            }
        } else {
            if (!"我要解散社群".equals(info)) {
                ReminderHelper.getIntentce().ToastShow_with_image(this, "输入的确认解散文字错误", R.string.icon_remind);
            } else {
                final Map<String, Object> map = ParamsManager.getInstance().setParams("groupId", mGroupId).getParamsMap();
                HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.groupDelete_group, new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        ReminderHelper.getIntentce().ToastShow_with_image(ClassDissolveActivity.this, "社群已解散", R.string.icon_check);
                        setResult(CodeNum.GROUP_DISSOLVE_RESULT);
                        finish();
                    }

                    @Override
                    public void onFailure(String errorMsg) {

                    }
                });
            }
        }

    }

    private final class InputTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            for (AppCompatTextView appCompatTextView : mListDissolve) {
                appCompatTextView.setText("");
            }
            String str = s.toString().trim();
            if (!TextUtils.isEmpty(str)) {
                for (int i = 0; i < str.length(); i++) {
                    mListDissolve.get(i).setText(str.substring(i, i + 1));
                }
            }

            refreshButtonStatue();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }


    private void refreshButtonStatue() {
        final String context = mEtInputText.getText().toString().trim();
        if (context.length() == 6) {
            mBtDissolve.setBackgroundResource(R.drawable.green_button_bg_circle);
            mBtDissolve.setClickable(true);
        } else {
            mBtDissolve.setBackgroundResource(R.drawable.green_button_bg_circle_);
            mBtDissolve.setClickable(false);
        }
    }

}
