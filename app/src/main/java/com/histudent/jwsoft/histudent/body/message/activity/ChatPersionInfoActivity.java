package com.histudent.jwsoft.histudent.body.message.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.uikit.session.activity.MessageHistoryActivity;
import com.histudent.jwsoft.histudent.body.message.uikit.session.search.SearchMessageActivity;
import com.histudent.jwsoft.histudent.body.mine.model.CurrentUserDetailInfoModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.enums.ReportType;
import com.histudent.jwsoft.histudent.commen.utils.IMUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.comment2.activity.ReportActivity;
import com.histudent.jwsoft.histudent.info.persioninfo.PersionHelper;
import com.histudent.jwsoft.histudent.info.persioninfo.activity.PersonCenterActivity;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.friend.FriendService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

/**
 * Created by liuguiyu-pc on 2016/6/12.
 * P2P祥情界面
 */
public class ChatPersionInfoActivity extends BaseActivity {
    private TextView title_middle_text, chatPersion_name, chatPersion_address;
    private CheckBox checkBox_00, checkBox_01;
    private String chatAccount;
    private NimUserInfo userInfo;
    private HeadImageView chatPersion_headImage;

    public static void start(Activity activity, String sessionId) {
        Intent intent = new Intent(activity, ChatPersionInfoActivity.class);
        intent.putExtra("account", sessionId);
        activity.startActivity(intent);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                case -100:
                    userInfo = (NimUserInfo) msg.obj;
                    UpdataUI();
                    break;

                case 1:
                    CurrentUserDetailInfoModel model = (CurrentUserDetailInfoModel) msg.obj;
                    chatPersion_address.setText(model.getProfile().getNowAreaCode());
                    break;

            }

        }
    };

    @Override
    public int setViewLayout() {
        return R.layout.chatpersioninfo_activity;
    }

    @Override
    public void initView() {
        title_middle_text = (TextView) findViewById(R.id.title_middle_text);
        title_middle_text.setText("聊天详情");

        chatPersion_headImage = (HeadImageView) findViewById(R.id.chatPersion_image);
        chatPersion_name = (TextView) findViewById(R.id.chatPersion_name);
        chatPersion_address = (TextView) findViewById(R.id.chatPersion_address);

        checkBox_00 = (CheckBox) findViewById(R.id.chatPersion_checkBox_00);
        checkBox_01 = (CheckBox) findViewById(R.id.chatPersion_checkBox_01);

        chatAccount = getIntent().getStringExtra("account");

        IMUtils.getNimUserInfo(handler, chatAccount);

        //消息免打扰
        checkBox_00.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!TextUtils.isEmpty(chatAccount))
                    NIMClient.getService(FriendService.class).setMessageNotify(chatAccount, !isChecked);
            }
        });

        //置顶聊天
        checkBox_01.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (userInfo != null) {
                    if (isChecked) {
                        HiCache.getInstance().setRecentContactsTopFlag(userInfo.getAccount());
                    } else {
                        HiCache.getInstance().removeRecentContactsTopFlag(userInfo.getAccount());
                    }
                }

            }
        });

        PersionHelper.getInstance().getEssayUserInfo(this, chatAccount, handler, 1);

    }

    private void UpdataUI() {
        checkBox_00.setChecked(!NIMClient.getService(FriendService.class).isNeedMessageNotify(chatAccount));
        checkBox_01.setChecked(HiCache.getInstance().getRecentContactsTopFlag(userInfo.getAccount()) > 0 ? true : false);
        chatPersion_headImage.loadBuddyAvatar(userInfo.getAccount());
        chatPersion_name.setText(userInfo.getName());
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {

            case R.id.title_left:

                finish();

                break;

            case R.id.chatPersion_goRight_00:

                PersonCenterActivity.start(this, SystemUtil.IMAccontToUserId(chatAccount));

                break;

            case R.id.chatPersion_goRight_01:

                MessageHistoryActivity.start(this, chatAccount, SessionTypeEnum.P2P);

                break;

            case R.id.chatPersion_goRight_02:

                SearchMessageActivity.start(this, chatAccount, SessionTypeEnum.P2P);

                break;

            case R.id.chatPersion_goRight_03:

                ReportActivity.start(this, chatAccount, ReportType.USER);

                break;

        }
    }

}
