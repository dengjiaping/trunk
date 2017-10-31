package com.histudent.jwsoft.histudent.comment2.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.comment2.utils.TellUtils;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//邀请好友加入班级
public class AddFriendsToClass extends BaseActivity {
    private TextView tv_instr;
    private List<RadioButton> radioButtons;
    private EditText et_name, et_phone;
    private RadioGroup radioGroup;
    private String classId;
    private boolean isSuccess;
    private String tell, phone, name;
    private int userType = 1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 0:

                    String content = ((String) msg.obj);
                    if (msg.arg1 == HiWorldCache.SUCCESS) {

                        Toast.makeText(AddFriendsToClass.this, "邀请成功！", Toast.LENGTH_LONG).show();
                        isSuccess = true;
                        setResult();
                    }

                    Log.e("AddFriendsToClass", content);
                    break;
            }
        }
    };

    public static void start(Activity activity, String classId) {
        Intent i = new Intent(activity, AddFriendsToClass.class);
        i.putExtra("classId", classId);
        activity.startActivityForResult(i, 500);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_add_friends_to_class;
    }

    @Override
    public void initView() {
        classId = getIntent().getStringExtra("classId");
        ((TextView) findViewById(R.id.title_right_text)).setText(R.string.completed);
        ((TextView) findViewById(R.id.title_middle_text)).setText("添加成员");
        ((TextView) findViewById(R.id.title_left_text)).setText(R.string.cancel);
        et_name = ((EditText) findViewById(R.id.et_name));
        et_phone = ((EditText) findViewById(R.id.et_phone));
        tv_instr = ((TextView) findViewById(R.id.tv));
        tv_instr.setText(R.string.ClassAddFriendInstr);
        initRadioButtons();
    }

    private void initRadioButtons() {
        radioButtons = new ArrayList<>();

        radioGroup = ((RadioGroup) findViewById(R.id.radio_group));

        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            RadioButton radioButton = ((RadioButton) radioGroup.getChildAt(i));
            radioButton.setChecked(false);
            radioButtons.add(radioButton);
        }


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                for (int i = 0; i < radioButtons.size(); i++) {

                    RadioButton radioButton1 = radioButtons.get(i);
                    if (radioButton1.getId() == checkedId) {
                        radioButton1.setChecked(true);
                        if (i == 0) {
                            userType = 1;
                        } else {
                            userType = 3;
                        }
                    } else {
                        radioButton1.setChecked(false);
                    }
                }
            }
        });
        radioButtons.get(0).setChecked(true);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.title_left:
                setResult();
                break;

            case R.id.title_right:

                if (!NecessaryContentIsEmpty()) {
                    addTeacherOrStudentToClass();
                }
                break;
        }
    }

    private void addTeacherOrStudentToClass() {
        Map<String, Object> map = new HashMap<>();
        map.put("classId", classId);
        map.put("mobile", tell);
        map.put("userType", userType + "");
        map.put("realName", name);

        HiWorldCache.postHttpData(this, mHandler, 0, HistudentUrl.classSetMemberByPhone, map, HiWorldCache.Quarry, false,true);

    }

    private boolean NecessaryContentIsEmpty() {
        tell = et_phone.getText().toString().trim();
        name = et_name.getText().toString().trim();

        if (!StringUtil.isEmpty(tell)) {
            if (!TellUtils.isPhoneNumber(tell)) {

                Toast.makeText(this, "手机号填写有误！", Toast.LENGTH_LONG).show();

            } else {
                if (StringUtil.isEmpty(name)) {
                    Toast.makeText(this, "请填写姓名！", Toast.LENGTH_LONG).show();
                } else {
                    return false;
                }
            }
        } else {
            Toast.makeText(this, "请填写手机号！", Toast.LENGTH_LONG).show();
        }

        return true;
    }

    private void setResult() {
        if (isSuccess) {
            setResult(200);
        } else {
            setResult(-1);
        }

        this.finish();
    }

}
