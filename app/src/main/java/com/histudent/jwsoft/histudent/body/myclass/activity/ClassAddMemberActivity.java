package com.histudent.jwsoft.histudent.body.myclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.myclass.helper.ClassHelper;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.netease.nim.uikit.common.util.string.StringUtil;

/**
 * Created by liuguiyu-pc on 2017/4/24.
 * 添加成员
 */

public class ClassAddMemberActivity extends BaseActivity {
    private TextView title, title_left_text, title_right_text;
    private IconView title_left_image;
    private EditText name, phone;
    private LinearLayout student_radioButton, teacher_radioButton;
    private int type = 1;
    private boolean isClickAble;

    public static void start(Activity activity, int requestCode) {
        activity.startActivityForResult(new Intent(activity, ClassAddMemberActivity.class), requestCode);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_class_addmember;
    }

    @Override
    public void initView() {
        title = (TextView) findViewById(R.id.title_middle_text);
        title_left_text = (TextView) findViewById(R.id.title_left_text);
        title_right_text = (TextView) findViewById(R.id.title_right_text);
        title_left_image = (IconView) findViewById(R.id.title_left_image);
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        student_radioButton = (LinearLayout) findViewById(R.id.student_radioButton);
        teacher_radioButton = (LinearLayout) findViewById(R.id.teacher_radioButton);
    }

    @Override
    public void doAction() {
        super.doAction();
        title_left_image.setVisibility(View.GONE);
        title_left_text.setText("取消");
        title.setText("添加成员");
        title_right_text.setText("确定");
        title_right_text.setTextColor(getResources().getColor(R.color.click_false));

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (!StringUtil.isEmpty(name.getText().toString()) && !StringUtil.isEmpty(phone.getText().toString())) {
                    title_right_text.setTextColor(getResources().getColor(R.color.click_true));
                    isClickAble = true;
                } else {
                    title_right_text.setTextColor(getResources().getColor(R.color.click_false));
                    isClickAble = false;
                }

            }
        });

        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!StringUtil.isEmpty(name.getText().toString()) && !StringUtil.isEmpty(phone.getText().toString())) {
                    title_right_text.setTextColor(getResources().getColor(R.color.click_true));
                    isClickAble = true;
                } else {
                    title_right_text.setTextColor(getResources().getColor(R.color.click_false));
                    isClickAble = false;
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.title_left://取消
                finish();
                break;
            case R.id.title_right://确定
                if (isClickAble)
                    addMember(name.getText().toString(), phone.getText().toString(), type);
                break;
            case R.id.student_radioButton://学生
                type = 1;
                student_radioButton.setBackgroundResource(R.drawable.green_button_bg_circle);
                teacher_radioButton.setBackgroundResource(R.drawable.gray_button_bg_circle);
                name.setHint("孩子的真实姓名");
                phone.setHint("家长的手机号码");
                break;
            case R.id.teacher_radioButton://老师
                type = 3;
                student_radioButton.setBackgroundResource(R.drawable.gray_button_bg_circle);
                teacher_radioButton.setBackgroundResource(R.drawable.green_button_bg_circle);
                name.setHint("老师的真实姓名");
                phone.setHint("老师的手机号码");
                break;
        }
    }

    /**
     * 添加班级成员
     *
     * @param name
     * @param phoneNum
     * @param userType
     */
    private void addMember(String name, String phoneNum, int userType) {

        final String classId = HiCache.getInstance().getClassDetailInfo().getClassId();
        ClassHelper.addMembers(this, classId, phoneNum, userType, name, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                ClassHelper.getClassInfo(ClassAddMemberActivity.this, classId, new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        ReminderHelper.getIntentce().ToastShow_with_image(ClassAddMemberActivity.this, "添加成功", R.string.icon_check);
                        setResult(200);
                        finish();
                    }

                    @Override
                    public void onFailure(String errorMsg) {

                    }
                }, LoadingType.FLOWER);

            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });

    }

}
