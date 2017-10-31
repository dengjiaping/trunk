package com.histudent.jwsoft.histudent.body.myclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.CodeNum;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.model.ClassModel;
import com.histudent.jwsoft.histudent.body.myclass.helper.ClassHelper;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;

/**
 * Created by liuguiyu-pc on 2017/4/24.
 * 转让班级
 */

public class ClassAssignmentActivity extends BaseActivity {

    private EditText assignment_edit;
    private TextView assignment_button;
    private TextView title;

    public static void start(Activity activity, int code) {
        activity.startActivityForResult(new Intent(activity, ClassAssignmentActivity.class), code);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_class_assignment;
    }

    @Override
    public void initView() {
        title = (TextView) findViewById(R.id.title_middle_text);
        assignment_edit = (EditText) findViewById(R.id.assignment_edit);
        assignment_button = (TextView) findViewById(R.id.assignment_button);
    }

    @Override
    public void doAction() {
        super.doAction();

        title.setText("转让班级");

        assignment_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (ReminderHelper.getIntentce().listenerEditIsEmpty(assignment_edit)) {
                    assignment_button.setBackgroundResource(R.drawable.green_button_bg);
                    assignment_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            assignmentClass();
                        }
                    });
                } else {
                    assignment_button.setBackgroundResource(R.drawable.green_button_bg_);
                    assignment_button.setOnClickListener(null);
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
        }

    }

    private void assignmentClass() {
        final ClassModel classModel = HiCache.getInstance().getClassDetailInfo();
        ClassHelper.exchangeClassOwner(this, classModel, assignment_edit.getText().toString(), new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                ReminderHelper.getIntentce().ToastShow_with_image(ClassAssignmentActivity.this, "班级转让成功", R.string.icon_check);
                setResult(CodeNum.CLASS_TRANSFER_RESULT);
                finish();

            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

}
