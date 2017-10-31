package com.histudent.jwsoft.histudent.body.find.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.adapter.AddClassmatesToGroupAdapter;
import com.histudent.jwsoft.histudent.body.find.helper.GroupHelper;
import com.histudent.jwsoft.histudent.body.hiworld.bean.SimpleUserModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.SelecteClassmatesActiviy;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.DialogButtonListener;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.comment2.utils.SeletClassMateEnum;

import java.util.ArrayList;

/**
 * 邀请成员
 */
public class AddClassmatesIntoGroupActivity extends BaseActivity {

    private ListView listView;
    private AddClassmatesToGroupAdapter adapter;
    private ArrayList<SimpleUserModel> clasMatesList;
    private String groupId;
    private View footView;
    private TextView title_middle;

    @Override
    public int setViewLayout() {
        return R.layout.activity_add_classmates_into_group;
    }


    public static void start(Activity activity, String groupId, int requestCode) {

        Intent intent = new Intent(activity, AddClassmatesIntoGroupActivity.class);
        intent.putExtra("groupId", groupId);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public void initView() {

        groupId = getIntent().getStringExtra("groupId");
        title_middle = ((TextView) findViewById(R.id.title_middle_text));
        ((TextView) findViewById(R.id.title_right_text)).setText("确定");
        title_middle.setText("邀请新成员(0)");
        ((TextView) findViewById(R.id.title_left_text)).setText("取消");
        findViewById(R.id.title_left_image).setVisibility(View.GONE);
        listView = ((ListView) findViewById(R.id.listview));
        footView = View.inflate(this, R.layout.add_classmate_buttom_layout, null);
        clasMatesList = new ArrayList<>();
        adapter = new AddClassmatesToGroupAdapter(this, clasMatesList);
        listView.addFooterView(footView);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {

            case R.id.add_classmate:

                SelecteClassmatesActiviy.start(this, clasMatesList, 11, SeletClassMateEnum.GROUP, groupId);
                break;

            case R.id.title_left:

                if (clasMatesList.size() > 0) {
                    ReminderHelper.getIntentce().showDialog(this, "提示", "是确定放弃邀请？", "确定", new DialogButtonListener() {
                        @Override
                        public void setOnDialogButtonListener() {

                            AddClassmatesIntoGroupActivity.this.finish();
                        }
                    }, "取消", new DialogButtonListener() {
                        @Override
                        public void setOnDialogButtonListener() {

                        }
                    });
                } else {
                    finish();
                }

                break;

            case R.id.title_right:

                if (clasMatesList.size() > 0) {
                    InvitationClassMates();
                } else {
                    Toast.makeText(this, "没有邀请对象！", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case 11:

                if (resultCode == 200) {

                    clasMatesList.clear();
                    clasMatesList.addAll(((ArrayList) data.getSerializableExtra("userModel")));
                    title_middle.setText("邀请新成员(" + clasMatesList.size() + ")");
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    private void InvitationClassMates() {
        GroupHelper.GroupInvitationClassMates(this, groupId, clasMatesList, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                ReminderHelper.getIntentce().ToastShow_with_image(AddClassmatesIntoGroupActivity.this,
                        "已成功邀请" + clasMatesList.size() + "个成员", R.string.icon_check);


                clasMatesList.clear();
                adapter.notifyDataSetChanged();
                AddClassmatesIntoGroupActivity.this.finish();
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }
}
