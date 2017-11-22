package com.histudent.jwsoft.histudent.view.activity.clock;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2017/9/18.
 */

public class TextInputActivity extends BaseActivity {
    @BindView(R.id.input)
    EditText mInput;

    @BindView(R.id.title_right_text)
    TextView mSubmit;
    @BindView(R.id.title_middle_text)
    TextView mTitle;

    @OnClick({R.id.title_left, R.id.title_right_text})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.title_right_text:
                if (!TextUtils.isEmpty(mInput.getText().toString().trim())) {
                    Intent intent = new Intent();
                    intent.putExtra("input", mInput.getText().toString().trim());
                    setResult(code, intent);
                    finish();
                } else {
                    Toast.makeText(TextInputActivity.this, "请输入内容", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }


    private int code;
    private static final int CODE_TITLE = 1000;
    private static final int CODE_DAY = 1001;
    private String name;


    @Override
    public int setViewLayout() {
        return R.layout.activity_text_input;
    }

    @Override
    public void initView() {
        mSubmit.setText("完成");
        code = getIntent().getIntExtra("code", 0);
        name = getIntent().getStringExtra("name");
        switch (code) {
            case CODE_DAY:
                mTitle.setText("达标天数");
                mInput.setHint("请输入达标天数");
                break;
            case CODE_TITLE:
                mTitle.setText("任务标题");
                if (TextUtils.isEmpty(name)) {
                    mInput.setHint("请输入任务标题（12字以内）");
                } else {
                    mInput.setText(name);
                }
                mInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(12)});
                break;
        }

        mInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                switch (code) {
                    case CODE_DAY:
                        break;
                    case CODE_TITLE:
                        if (!TextUtils.isEmpty(charSequence)) {
                            if (charSequence.toString().length() >= 12) {
                                Toast.makeText(TextInputActivity.this, "标题不能超过12个字", Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;
                }
                changeSubmitState(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        if (!TextUtils.isEmpty(name)) {
            mInput.setSelection(name.length());
        }
        changeSubmitState(mInput.getText());
    }

    private void changeSubmitState(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence.toString().trim())) {
            mSubmit.setTextColor(ContextCompat.getColor(this, R.color.green_color));
            mSubmit.setClickable(true);
        } else {
            mSubmit.setTextColor(ContextCompat.getColor(this, R.color.text_black_h1));
            mSubmit.setClickable(false);
        }
    }

    @Override
    public void doAction() {
        super.doAction();
        initOthers();
    }

    private void initOthers() {
    }
}
