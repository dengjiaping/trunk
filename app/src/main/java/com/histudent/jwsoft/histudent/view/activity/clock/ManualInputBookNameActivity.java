package com.histudent.jwsoft.histudent.view.activity.clock;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.HTApplication;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.model.constant.ParamKeys;
import com.histudent.jwsoft.histudent.model.constant.TransferKeys;
import com.histudent.jwsoft.histudent.model.manage.ParamsManager;
import com.histudent.jwsoft.histudent.tool.CommonAdvanceUtils;
import com.histudent.jwsoft.histudent.tool.ToastTool;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lichaojie on 2017/10/17.
 * desc:
 * 手动输入书名
 */

public class ManualInputBookNameActivity extends BaseActivity {

    @BindView(R.id.et_input_book_name)
    AppCompatEditText mEtInputBookName;
    @BindView(R.id.title_middle_text)
    TextView mTvTopMiddleText;
    @BindView(R.id.title_right_text)
    TextView mTvTopRightText;
    private String mBookScanISBN;


    @OnClick(R.id.title_left)
    public void back() {
        this.finish();
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_manual_book_name;
    }

    @Override
    public void initView() {
        mBookScanISBN = getIntent().getStringExtra(TransferKeys.BOOK_SCAN_ISBN);
        mTvTopMiddleText.setText(R.string.manual_add);
        mTvTopRightText.setText(R.string.complete);
        mTvTopRightText.setTextColor(ContextCompat.getColor(this, R.color._333333));
        defaultInvokeSystemInputSoft();
    }

    private void defaultInvokeSystemInputSoft() {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) mEtInputBookName.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(mEtInputBookName, 0);
            }

        }, 300);
    }

    @Override
    public void doAction() {
        super.doAction();
        loadListener();
    }

    private void loadListener() {
        mEtInputBookName.addTextChangedListener(new InputTextWatcher());
        mTvTopRightText.setOnClickListener((View view) -> {
            final String inputBookName = mEtInputBookName.getText().toString().trim();
            if (TextUtils.isEmpty(inputBookName)) {
                ToastTool.showCommonToast(HTApplication.getInstance(), getString(R.string.book_name_is_empty));
                return;
            }

            //添加用户所输入书籍至书库里
            final Map<String, Object> map = ParamsManager.getInstance()
                    .setParams(ParamKeys.ISBN, mBookScanISBN == null ? "" : mBookScanISBN)
                    .setParams(ParamKeys.BOOK_NAME, inputBookName)
                    .getParamsMap();
            HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.ADD_BOOK_INFORMATION, new HttpRequestCallBack() {
                @Override
                public void onSuccess(String result) {
                    //跳转至阅读打卡页面
                    final Intent intent = new Intent();
                    intent.setClass(ManualInputBookNameActivity.this, ReadClockInActivity.class);
                    intent.putExtra(TransferKeys.BOOK_NAME, inputBookName);
                    intent.putExtra(TransferKeys.RESULT, TransferKeys.ConstantNum.NUM_1000);
                    intent.putExtra(TransferKeys.BOOK_SCAN_ISBN, mBookScanISBN);
                    CommonAdvanceUtils.startActivity(ManualInputBookNameActivity.this, intent);
                    ManualInputBookNameActivity.this.finish();
                }

                @Override
                public void onFailure(String errorMsg) {

                }
            });

        });
    }

    private final class InputTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (TextUtils.isEmpty(s)) {
                mTvTopRightText.setTextColor(ContextCompat.getColor(ManualInputBookNameActivity.this, R.color.text_black_h1));
            } else {
                mTvTopRightText.setTextColor(ContextCompat.getColor(ManualInputBookNameActivity.this, R.color.green_color));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

}
