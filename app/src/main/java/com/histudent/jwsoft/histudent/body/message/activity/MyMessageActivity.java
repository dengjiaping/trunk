package com.histudent.jwsoft.histudent.body.message.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.fragment.MyNotificationFragment;
import com.histudent.jwsoft.histudent.body.message.uikit.session.fragment.MyRecentContactsFragment;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2017/4/16.
 * 我的消息界面
 */

public class MyMessageActivity extends BaseActivity {

    private TextView msg_name, notification_name, notice_red;
    private View msg_line, notification_line;
    private FragmentManager fragmentManager;
    private MyRecentContactsFragment fragment_message;
    private MyNotificationFragment fragment_notification;
    private final int MSG = 0;
    private final int NOTIFICATION = 1;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, MyMessageActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_mymessage;
    }

    @Override
    public void initView() {
        msg_name = (TextView) findViewById(R.id.msg_name);
        notice_red = (TextView) findViewById(R.id.notice_red);
        notification_name = (TextView) findViewById(R.id.notification_name);
        msg_line = findViewById(R.id.msg_line);
        notification_line = findViewById(R.id.notification_line);

        fragmentManager = getSupportFragmentManager();
        fragment_message = new MyRecentContactsFragment();
        fragment_notification = new MyNotificationFragment();

        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);

    }

    @Override
    public void doAction() {
        super.doAction();
        fragmentManager.beginTransaction().add(R.id.fragment, fragment_message).commit();
        EventBus.getDefault().post(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.msg_layout://消息
                uapteTitle(MSG);
                switchFragment(fragmentManager, R.id.fragment, fragment_notification, fragment_message);
                break;

            case R.id.notification_layout://通知
                uapteTitle(NOTIFICATION);
                switchFragment(fragmentManager, R.id.fragment, fragment_message, fragment_notification);
                break;

            case R.id.goback://返回
                finish();
                break;

        }

    }

    private void uapteTitle(int type) {
        switch (type) {
            case MSG:
                msg_name.setTextColor(getResources().getColor(R.color.green_color));
                msg_line.setVisibility(View.VISIBLE);
                notification_name.setTextColor(getResources().getColor(R.color.text_gray_h2));
                notification_line.setVisibility(View.INVISIBLE);
                break;
            case NOTIFICATION:
                msg_name.setTextColor(getResources().getColor(R.color.text_gray_h2));
                msg_line.setVisibility(View.INVISIBLE);
                notification_name.setTextColor(getResources().getColor(R.color.green_color));
                notification_line.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * 获取未读消息数量
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getUnreadMessageCount(Boolean b) {
        if (!b) return;
        Map<String, Object> map = new TreeMap<>();
        map.put("userId", HiCache.getInstance().getLoginUserInfo().getUserId());
        HiStudentHttpUtils.postDataByOKHttp(true, this, map, HistudentUrl.unreadMessageCount, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject object = new JSONObject(result);
                    notice_red.setVisibility(object.optInt("data") > 0 ? View.VISIBLE : View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String string = result;
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });

    }

}
