package com.histudent.jwsoft.histudent.account.password.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.account.AccountActivity;
import com.histudent.jwsoft.histudent.account.password.fragment.FindPassWordFragmentTheFirst;
import com.histudent.jwsoft.histudent.account.password.fragment.FindPassWordFragmentTheSecond;
import com.histudent.jwsoft.histudent.account.password.model.PhoneModel;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by liuguiyu-pc on 2016/5/26.
 * 找回密码界面
 */
public class FindPassWordActivity extends AccountActivity {

    private TextView title_left_text, title_right_text;
    private FragmentManager fragmentManager;
    private Fragment fragment_first, fragment_second;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, FindPassWordActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int setViewLayout() {
        return R.layout.findpassword_activity;
    }

    @Override
    public void initView() {
        //实例化控件
        title_left_text = (TextView) findViewById(R.id.title_left_text);
        title_right_text = (TextView) findViewById(R.id.title_right_text);

        //设置初始标题
        title_left_text.setVisibility(View.GONE);
        title_right_text.setVisibility(View.GONE);

        fragment_first = new FindPassWordFragmentTheFirst();
        fragment_second = new FindPassWordFragmentTheSecond();

        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().add(R.id.findpasswordactivity_fragment, fragment_first).addToBackStack(null).commit();

        EventBus.getDefault().register(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSubscribeEvent(PhoneModel model) {

        switch (model.what) {
            case 0:
                Bundle bundle_01 = new Bundle();
                bundle_01.putInt("codeNum", model.codeNum);
                bundle_01.putString("phoneNum", model.phoneNum);
                fragment_second.setArguments(bundle_01);
                fragmentManager.beginTransaction().add(R.id.findpasswordactivity_fragment, fragment_second).addToBackStack(null).commit();
                break;

            case 2:
                finish();
                break;
        }

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {

            case R.id.title_left:

                if (fragmentManager.getBackStackEntryCount() > 1) {
                    fragmentManager.popBackStack();
                } else {
                    finish();
                }
                break;

        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (fragmentManager.getBackStackEntryCount() == 1) {
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SystemUtil.closeDown();
        EventBus.getDefault().unregister(this);
    }

}
