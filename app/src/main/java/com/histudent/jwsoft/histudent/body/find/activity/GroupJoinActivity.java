package com.histudent.jwsoft.histudent.body.find.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2017/5/17.
 * 申请加入社群
 */

public class GroupJoinActivity extends BaseActivity {

    private TextView join_button, title;
    private EditText join_text;
    private String groupId;

    public static void start(Activity activity, String groupId,int requestCode) {
        Intent intent = new Intent(activity, GroupJoinActivity.class);
        intent.putExtra("groupId", groupId);
        activity.startActivityForResult(intent,requestCode);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_group_join;
    }

    @Override
    public void initView() {
        groupId = getIntent().getStringExtra("groupId");
        join_button = (TextView) findViewById(R.id.join_button);
        title = (TextView) findViewById(R.id.title_middle_text);
        join_text = (EditText) findViewById(R.id.join_text);
    }

    @Override
    public void doAction() {
        super.doAction();

        title.setText("申请加入");

        join_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                if (TextUtils.isEmpty(editable)) {
                    join_button.setBackgroundResource(R.drawable.green_button_bg_);
                    join_button.setOnClickListener(null);
                } else {
                    join_button.setBackgroundResource(R.drawable.green_button_bg);
                    join_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            joinGroup(groupId, editable.toString());
                        }
                    });
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

    /**
     * 申请加入社群
     *
     * @param groupId
     * @param applyReason
     */
    private void joinGroup(String groupId, String applyReason) {
        if (TextUtils.isEmpty(groupId)) return;
        Map<String, Object> map = new TreeMap<>();
        map.put("groupId", groupId);
        map.put("applyReason", applyReason);

        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.applyJoin_group, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                setResult(200);
                finish();
                ReminderHelper.getIntentce().ToastShow_with_image(GroupJoinActivity.this, "已提交加入申请，等待管理员审核！", R.string.icon_check);

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

}
