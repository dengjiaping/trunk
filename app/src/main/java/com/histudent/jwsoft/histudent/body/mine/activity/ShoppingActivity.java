package com.histudent.jwsoft.histudent.body.mine.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;

/**
 * Created by liuguiyu-pc on 2017/6/13.
 * 积分商城
 */

public class ShoppingActivity extends BaseActivity {

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, ShoppingActivity.class));
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_shopping;
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

    @Override
    public void initView() {

        ((TextView) findViewById(R.id.title_middle_text)).setText("积分商城");

    }
}
