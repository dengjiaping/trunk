package com.histudent.jwsoft.histudent.body.message.activity;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.uikit.session.SessionHelper;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.utils.IMUtils;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.friend.FriendService;
import com.netease.nimlib.sdk.friend.constant.VerifyType;
import com.netease.nimlib.sdk.friend.model.AddFriendData;

/**
 * Created by liuguiyu-pc on 2016/6/3.
 */
public class AddFriendActivity extends BaseActivity {

    private TextView text_name, text_account;

    @Override
    public int setViewLayout() {
        return R.layout.addfriend_activity;
    }

    @Override
    public void initView() {
        text_name = (TextView) findViewById(R.id.addfriend_name);
        text_account = (TextView) findViewById(R.id.addfriend_account);

        if (IMUtils.userInfo != null) {
            text_name.setText(IMUtils.userInfo.getName());
            text_account.setText(IMUtils.userInfo.getAccount());
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.addfriend_chat:

                SessionHelper.startP2PSession(AddFriendActivity.this, IMUtils.userInfo.getAccount());

                break;
            case R.id.addfriend_add:

                final VerifyType verifyType = VerifyType.VERIFY_REQUEST; // 发起好友验证请求
                String msg = "好友请求附言";
                NIMClient.getService(FriendService.class).addFriend(new AddFriendData(IMUtils.userInfo.getAccount(), verifyType, msg))
                        .setCallback(new RequestCallback<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                if (VerifyType.DIRECT_ADD == verifyType) {
                                    Toast.makeText(getApplicationContext(), "添加好友成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "添加好友请求发送成功", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailed(int i) {

                                Toast.makeText(getApplicationContext(), "添加好友请求发送失败", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onException(Throwable throwable) {

                                Toast.makeText(getApplicationContext(), "Throwable：" + throwable, Toast.LENGTH_SHORT).show();

                            }
                        });

                break;
        }
    }

}
