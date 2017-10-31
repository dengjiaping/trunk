package com.histudent.jwsoft.histudent.account.regist.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.account.AccountActivity;
import com.histudent.jwsoft.histudent.account.login.activity.DealActivity;
import com.histudent.jwsoft.histudent.account.login.activity.LoginPresenter;
import com.histudent.jwsoft.histudent.account.regist.bean.RegistBean;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.comment2.utils.ActivityCollector;


/**
 * Created by liuguiyu-pc on 2016/5/26.
 * 注册页面(v2版)
 */
public class RegistActivity extends AccountActivity {

    private TextView link_clik, link_clik_withLine;

    public static void start(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, RegistActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public int setViewLayout() {
        ActivityCollector.addActivity(this);
        return R.layout.activity_regist_v2;
    }

    /**
     * 初始化资源
     */
    @Override
    public void initView() {
        link_clik = (TextView) findViewById(R.id.link_clik);
        link_clik_withLine = (TextView) findViewById(R.id.link_clik_withLine);
    }

    @Override
    public void doAction() {
        super.doAction();

        link_clik.setText("注册即表示同意");
        link_clik_withLine.setText("用户服务协议");

    }

    /**
     * 页面按钮监听
     */
    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {

            case R.id.title_left://回退
                finish();
                break;

            case R.id.regist_phone_v2://手机注册
                PhoneRegistActivity.start(this, new RegistBean());
                break;

            case R.id.regist_weixin_v2://微信注册
                LoginPresenter.getIntentce().loginByWweXin(this);
                break;

            case R.id.regist_qq_v2://QQ注册
                LoginPresenter.getIntentce().loginByQQ(this);
                break;

            case R.id.link_clik_withLine://服务协议
                DealActivity.start(this, HistudentUrl.agreement_url, getResources().getString(R.string.agreement_hi));
                break;
        }
    }


}
