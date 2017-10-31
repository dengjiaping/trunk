package com.histudent.jwsoft.histudent.body.find.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.CodeNum;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.bean.GroupBean;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2017/4/24.
 * 转让社群
 */

public class GroupAssignmentActivity extends BaseActivity {

    private TextView assignment_edit;
    private TextView assignment_button;
    private TextView title;
    private final int OK = 200;
    private GroupBean groupBean;
    private String mobile;

    public static void start(Activity activity, GroupBean groupBean, int code) {
        Intent intent = new Intent(activity, GroupAssignmentActivity.class);
        intent.putExtra("groupBean", groupBean);
        activity.startActivityForResult(intent, code);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_group_assignment;
    }

    @Override
    public void initView() {
        groupBean = (GroupBean) getIntent().getSerializableExtra("groupBean");
        title = (TextView) findViewById(R.id.title_middle_text);
        assignment_edit = (TextView) findViewById(R.id.assignment_edit);
        assignment_button = (TextView) findViewById(R.id.assignment_button);
    }

    @Override
    public void doAction() {
        super.doAction();
        title.setText("转让社群");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == 200) {
            mobile = data.getStringExtra("mobile");
            assignment_edit.setText(data.getStringExtra("name"));
            assignment_button.setBackgroundResource(R.drawable.green_button_bg);
            assignment_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    assignmentGroup(groupBean.getGroupId(), mobile);
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.assignment_edit:
                GroupMemberActivity.start(this, groupBean, 10, true);
                break;
        }
    }

    private void assignmentGroup(String groupId, String groupUserPhone) {

        Map<String, Object> map = new TreeMap<>();
        map.put("groupId", groupId);
        map.put("groupUserPhone", groupUserPhone);
        HiStudentHttpUtils.postDataByOKHttp(GroupAssignmentActivity.this, map, HistudentUrl.groupTransfer_group, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                ReminderHelper.getIntentce().ToastShow_with_image(GroupAssignmentActivity.this, "社群转让成功", R.string.icon_check);
                setResult(CodeNum.GROUP_TRANSFER_RESULT);
                finish();
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });


    }

}
