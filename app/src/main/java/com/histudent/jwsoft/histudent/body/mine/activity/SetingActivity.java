package com.histudent.jwsoft.histudent.body.mine.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.SuggestionsActivity;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/12/1.
 */

public class SetingActivity extends BaseActivity {

    private TextView title;

    public static void start(Activity activity, int requestCode) {
        activity.startActivityForResult(new Intent(activity, SetingActivity.class), requestCode);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_seting;
    }

    @Override
    public void initView() {

        title = (TextView) findViewById(R.id.title_middle_text);
        title.setText("设置");
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {

            case R.id.title_left:
                finish();
                break;

            case R.id.user_accountSafe://账号安全
                AccountSafeActivity.start(SetingActivity.this);
                break;

            case R.id.user_bind://账号绑定
                MyAccountBind.start(SetingActivity.this);
                break;

            case R.id.user_setting://通用设置
                CommonSetActivity.start(SetingActivity.this);
                break;

            case R.id.user_suggestion://意见反馈
                SuggestionsActivity.start(this);
                break;

//            case R.id.user_about://关于嗨同学
//                AboutHiActivity.start(SetingActivity.this);
//                break;

            case R.id.theMyFragment_exit://退出
                exitLogin();
                break;
//
//            case R.id.user_suggestion:
//                SuggestionsActivity.start(SetingActivity.this);
//                break;

        }

    }

    /**
     * 退出登录
     */
    private void exitLogin() {
        List<String> list_name = new ArrayList<>();
        List<Integer> list_color = new ArrayList<>();
        list_name.add("确认");
        list_color.add(Color.rgb(255, 0, 0));

        ReminderHelper.getIntentce().showTopMenuDialog(this, "确认退出吗？", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(200);
                finish();

            }
        }, list_name, list_color,true);

    }

}
