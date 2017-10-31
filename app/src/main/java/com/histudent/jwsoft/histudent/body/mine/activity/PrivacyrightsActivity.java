package com.histudent.jwsoft.histudent.body.mine.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;


/*
* 隐私权限页面
*
* */
public class PrivacyrightsActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout relative_setting, relative_sheid, relative_blackList;
    private Intent intent;

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, PrivacyrightsActivity.class));
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_privacyrights;
    }

    @Override
    public void initView() {
        relative_setting = ((RelativeLayout) findViewById(R.id.relative_setting));
        relative_sheid = ((RelativeLayout) findViewById(R.id.relative_sheid));
        relative_blackList = ((RelativeLayout) findViewById(R.id.relative_blackList));
//        iv_back = ((ImageView) findViewById(R.id.title_left_image));
        ((TextView) findViewById(R.id.title_middle_text)).setText("隐私权限");


        relative_setting.setOnClickListener(this);
        relative_blackList.setOnClickListener(this);
        relative_sheid.setOnClickListener(this);
//        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {

            //返回
            case R.id.title_left:
                this.finish();
                break;


            //隐私设置
            case R.id.relative_setting:

                intent = new Intent(this, PrivacySettingActivity.class);
                startActivity(intent);

                break;


            //屏蔽动态的人
            case R.id.relative_sheid:

                QualitySelectActivity.start(PrivacyrightsActivity.this, "sheild");
                break;


            //黑名单
            case R.id.relative_blackList:
                QualitySelectActivity.start(PrivacyrightsActivity.this, "blackList");
                break;
        }
    }

}
