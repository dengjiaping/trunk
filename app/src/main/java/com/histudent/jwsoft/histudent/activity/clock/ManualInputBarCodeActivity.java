package com.histudent.jwsoft.histudent.activity.clock;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.HiStudentLog;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.constant.ParamKeys;
import com.histudent.jwsoft.histudent.constant.TransferKeys;
import com.histudent.jwsoft.histudent.tool.CommonAdvanceUtils;
import com.histudent.jwsoft.histudent.tool.ToastTool;
import com.histudent.jwsoft.histudent.zxing.CaptureConfirmActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lichaojie on 2017/10/12.
 * desc:
 * 手动输入条形码
 */

public class ManualInputBarCodeActivity extends BaseActivity {


    @BindView(R.id.tv_code_1)
    AppCompatTextView mTvCode1;
    @BindView(R.id.tv_code_2)
    AppCompatTextView mTvCode2;
    @BindView(R.id.tv_code_3)
    AppCompatTextView mTvCode3;
    @BindView(R.id.tv_code_4)
    AppCompatTextView mTvCode4;
    @BindView(R.id.tv_code_5)
    AppCompatTextView mTvCode5;
    @BindView(R.id.tv_code_6)
    AppCompatTextView mTvCode6;
    @BindView(R.id.tv_code_7)
    AppCompatTextView mTvCode7;
    @BindView(R.id.tv_code_8)
    AppCompatTextView mTvCode8;
    @BindView(R.id.tv_code_9)
    AppCompatTextView mTvCode9;
    @BindView(R.id.tv_code_10)
    AppCompatTextView mTvCode10;
    @BindView(R.id.tv_code_11)
    AppCompatTextView mTvCode11;
    @BindView(R.id.tv_code_12)
    AppCompatTextView mTvCode12;
    @BindView(R.id.tv_code_13)
    AppCompatTextView mTvCode13;

    @BindView(R.id.ll_manual_input_bar_code)
    LinearLayoutCompat mLLManualInputBarCodeLayout;

    @BindView(R.id.ll_manual_input_bar_code_confirm)
    LinearLayoutCompat mLLConfirm;

    @BindView(R.id.et_input_code)
    AppCompatEditText mEtInputCode;
    @BindView(R.id.title_middle_text)
    TextView mTvMiddleText;

    private int isLocalStartScan = -1;

    private static final String TAG = "ManualInputBarCodeActiv";


    private final List<AppCompatTextView> mListEditTextContainer = new ArrayList<>();

    @OnClick(R.id.title_left)
    void finishPage() {
        this.finish();
    }


    @Override
    public int setViewLayout() {
        return R.layout.activity_manual_input_bar_code;
    }

    @Override
    public void initView() {
        findViewById(R.id.view_bottom_line).setVisibility(View.GONE);
        mTvMiddleText.setText(R.string.manual_input_bar_code_title);
        mTvMiddleText.setTextSize(18);
        TextPaint paint = mTvMiddleText.getPaint();
        paint.setFakeBoldText(true);
        mListEditTextContainer.clear();
        mListEditTextContainer.add(mTvCode1);
        mListEditTextContainer.add(mTvCode2);
        mListEditTextContainer.add(mTvCode3);
        mListEditTextContainer.add(mTvCode4);
        mListEditTextContainer.add(mTvCode5);
        mListEditTextContainer.add(mTvCode6);
        mListEditTextContainer.add(mTvCode7);
        mListEditTextContainer.add(mTvCode8);
        mListEditTextContainer.add(mTvCode9);
        mListEditTextContainer.add(mTvCode10);
        mListEditTextContainer.add(mTvCode11);
        mListEditTextContainer.add(mTvCode12);
        mListEditTextContainer.add(mTvCode13);
    }

    @Override
    public void doAction() {
        super.doAction();
        final Intent intent = getIntent();
        isLocalStartScan = intent.getIntExtra(TransferKeys.LOCAL_START_SCAN, -1);
        final InputBarCodeTextWatcher inputBarCodeTextWatcher = new InputBarCodeTextWatcher();
        mEtInputCode.addTextChangedListener(inputBarCodeTextWatcher);
        loadListener();
    }

    private void loadListener() {
        mLLConfirm.setOnClickListener((View view) -> {
            final String contentISBN = mEtInputCode.getText().toString().trim();
            if (TextUtils.isEmpty(contentISBN)) {
                ToastTool.showCommonToast(ManualInputBarCodeActivity.this, getString(R.string.input_bar_code_is_empty));
                return;
            }

            if (contentISBN.length() != 13) {
                ToastTool.showCommonToast(ManualInputBarCodeActivity.this, getString(R.string.isbn_not_incorrect));
                return;
            }

            final HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put(ParamKeys.ISBN, contentISBN);
            HiStudentHttpUtils.postDataByOKHttp(this, hashMap, HistudentUrl.GET_SCAN_BOOK_INFORMATION, new HttpRequestCallBack() {
                @Override
                public void onSuccess(String result) {
                    final Bundle bundle = new Bundle();
                    bundle.putString(ParamKeys.ISBN, contentISBN);
                    bundle.putInt(TransferKeys.LOCAL_START_SCAN, isLocalStartScan);
                    bundle.putString(TransferKeys.RESULT, result);
                    final Intent intent = new Intent(ManualInputBarCodeActivity.this, CaptureConfirmActivity.class);
                    intent.putExtras(bundle);
                    HiStudentLog.i(TAG, "handleDecode: result:--->" + contentISBN);
                    CommonAdvanceUtils.startActivity(ManualInputBarCodeActivity.this, intent);
                    ManualInputBarCodeActivity.this.finish();
                }

                @Override
                public void onFailure(String errorMsg) {
                    showHintDialog();
                }
            });

        });
    }

    private void showHintDialog() {
        ReminderHelper.getIntentce().showDialog(this, "", getString(R.string.not_query_book_name), getString(R.string.cancel), () -> {
        }, getString(R.string.go_add), () -> {
            final Intent intent = new Intent(ManualInputBarCodeActivity.this, ManualInputBookNameActivity.class);
            final String contentISBN = mEtInputCode.getText().toString().trim();
            intent.putExtra(TransferKeys.BOOK_SCAN_ISBN, contentISBN);
            CommonAdvanceUtils.startActivity(ManualInputBarCodeActivity.this, intent);
        });
    }


    private final class InputBarCodeTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            for (AppCompatTextView appCompatTextView : mListEditTextContainer) {
                appCompatTextView.setText("");
            }
            String str = s.toString().trim();
            if (!TextUtils.isEmpty(str)) {
                for (int i = 0; i < str.length(); i++) {
                    mListEditTextContainer.get(i).setText(str.substring(i, i + 1));
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

}
