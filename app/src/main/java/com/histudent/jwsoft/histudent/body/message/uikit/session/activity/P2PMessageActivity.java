package com.histudent.jwsoft.histudent.body.message.uikit.session.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.histudent.jwsoft.histudent.HiStudentLog;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.activity.ChatPersionInfoActivity;
import com.histudent.jwsoft.histudent.commen.activity.ImageBrowserActivity;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.enums.ShowImageType;
import com.histudent.jwsoft.histudent.commen.receiver.TheReceiverAction;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.netease.nim.uikit.cache.FriendDataCache;
import com.netease.nim.uikit.session.SessionCustomization;
import com.netease.nim.uikit.session.activity.BaseMessageActivity;
import com.netease.nim.uikit.session.constant.Extras;
import com.netease.nim.uikit.session.fragment.MessageFragment;
import com.netease.nim.uikit.uinfo.UserInfoHelper;
import com.netease.nim.uikit.uinfo.UserInfoObservable;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.attachment.ImageAttachment;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomNotification;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.uinfo.UserService;

import java.util.ArrayList;
import java.util.List;


/**
 * 点对点聊天界面
 * <p>
 * Created by huangjun on 2015/2/1.
 */
public class P2PMessageActivity extends BaseMessageActivity implements View.OnClickListener {

    private boolean isResume = false;
    private BroadcastReceiver receiver;
    private List<IMMessage> messageList;

    public static void start(Context context, String contactId, SessionCustomization customization, IMMessage anchor) {
        Intent intent = new Intent();
        intent.putExtra(Extras.EXTRA_ACCOUNT, contactId);
        intent.putExtra(Extras.EXTRA_CUSTOMIZATION, customization);
        if (anchor != null) {
            intent.putExtra(Extras.EXTRA_ANCHOR, anchor);
        }
        intent.setClass(context, P2PMessageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        ((Activity)context).startActivityForResult(intent,999);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        messageList = new ArrayList<>();

//        initWindow();

//        setInput();

        // 单聊特例话数据，包括个人信息，
        requestBuddyInfo();
        registerObservers(true);
        initTitle();

        checkBigImage(this);

    }

    private void initTitle() {

        LinearLayout layout_left = (LinearLayout) findViewById(R.id.title_left);
        LinearLayout layout_right = (LinearLayout) findViewById(R.id.title_right);
        TextView title = (TextView) findViewById(R.id.title_middle_text);
        IconView image_right = (IconView) findViewById(R.id.title_right_image);

        layout_left.setOnClickListener(this);

        layout_right.setOnClickListener(this);

        title.setText(NIMClient.getService(UserService.class).getUserInfo(sessionId).getName());
        image_right.setText(R.string.icon_minenone);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registerObservers(false);
        if (receiver != null) {
            unregisterReceiver(receiver);
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        isResume = true;
        // TODO: 2017/8/16 刷新页面数据
        requestBuddyInfo();
    }

    @Override
    protected void onStop() {
        super.onStop();
        isResume = false;
    }

    private void requestBuddyInfo() {
        setTitle(UserInfoHelper.getUserTitleName(sessionId, SessionTypeEnum.P2P));
    }

    private void registerObservers(boolean register) {
        if (register) {
            registerUserInfoObserver();
        } else {
            unregisterUserInfoObserver();
        }
        NIMClient.getService(MsgServiceObserve.class).observeCustomNotification(commandObserver, register);
        FriendDataCache.getInstance().registerFriendDataChangedObserver(friendDataChangedObserver, register);
    }

    FriendDataCache.FriendDataChangedObserver friendDataChangedObserver = new FriendDataCache.FriendDataChangedObserver() {
        @Override
        public void onAddedOrUpdatedFriends(List<String> accounts) {
            setTitle(UserInfoHelper.getUserTitleName(sessionId, SessionTypeEnum.P2P));
        }

        @Override
        public void onDeletedFriends(List<String> accounts) {
            setTitle(UserInfoHelper.getUserTitleName(sessionId, SessionTypeEnum.P2P));
        }

        @Override
        public void onAddUserToBlackList(List<String> account) {
            setTitle(UserInfoHelper.getUserTitleName(sessionId, SessionTypeEnum.P2P));
        }

        @Override
        public void onRemoveUserFromBlackList(List<String> account) {
            setTitle(UserInfoHelper.getUserTitleName(sessionId, SessionTypeEnum.P2P));
        }
    };

    private UserInfoObservable.UserInfoObserver uinfoObserver;

    private void registerUserInfoObserver() {
        if (uinfoObserver == null) {
            uinfoObserver = new UserInfoObservable.UserInfoObserver() {
                @Override
                public void onUserInfoChanged(List<String> accounts) {
                    if (accounts.contains(sessionId)) {
                        requestBuddyInfo();
                    }
                }
            };
        }

        UserInfoHelper.registerObserver(uinfoObserver);
    }

    private void unregisterUserInfoObserver() {
        if (uinfoObserver != null) {
            UserInfoHelper.unregisterObserver(uinfoObserver);
        }
    }

    /**
     * 命令消息接收观察者
     */
    Observer<CustomNotification> commandObserver = new Observer<CustomNotification>() {
        @Override
        public void onEvent(CustomNotification message) {
            if (!sessionId.equals(message.getSessionId()) || message.getSessionType() != SessionTypeEnum.P2P) {
                return;
            }
            showCommandMessage(message);
        }
    };

    protected void showCommandMessage(CustomNotification message) {
        if (!isResume) {
            return;
        }

        String content = message.getContent();
        try {
            JSONObject json = JSON.parseObject(content);
            int id = json.getIntValue("id");
            if (id == 1) {
                // 正在输入
                Toast.makeText(P2PMessageActivity.this, "对方正在输入...", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(P2PMessageActivity.this, "command: " + content, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {

        }
    }

    @Override
    protected MessageFragment fragment() {
        Bundle arguments = getIntent().getExtras();
        arguments.putSerializable(Extras.EXTRA_TYPE, SessionTypeEnum.P2P);
        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(arguments);
        fragment.setContainerId(R.id.message_fragment_container);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.nim_message_activity_;
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.title_right:
                ChatPersionInfoActivity.start(P2PMessageActivity.this, sessionId);
                break;
        }
    }

    public void checkBigImage(final Activity activity) {

        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(TheReceiverAction.CHECK_BIGIMG);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, final Intent intent) {

                switch (intent.getAction()) {
                    case TheReceiverAction.CHECK_BIGIMG:

                        IMMessage message = (IMMessage) intent.getSerializableExtra("message");

                        final String id = message.getUuid();

                        IMMessage anchor = MessageBuilder.createEmptyMessage(message.getSessionId(), message.getSessionType(), 0);

                        NIMClient.getService(MsgService.class).queryMessageListByType(MsgTypeEnum.image, anchor, Integer.MAX_VALUE).setCallback(new RequestCallback<List<IMMessage>>() {
                            @Override
                            public void onSuccess(List<IMMessage> param) {
                                messageList.clear();
                                messageList.addAll(param);

                                if (messageList.size() == 0)
                                    return;

                                ArrayList<ActionListBean.ImagesBean> beenList = new ArrayList<>();
                                int position = 0;

                                for (int i = messageList.size() - 1, j = 0; i > -1; i--, j++) {
                                    ActionListBean.ImagesBean bean = new ActionListBean.ImagesBean();
                                    String thumbPath = ((ImageAttachment) messageList.get(i).getAttachment()).getThumbPath();
                                    String path = ((ImageAttachment) messageList.get(i).getAttachment()).getPath();

                                    if (id.equals(messageList.get(i).getUuid())) {
                                        position = j;
                                    }

                                    if (!TextUtils.isEmpty(thumbPath)) {
                                        bean.setThumbnailUrl(thumbPath);
                                        bean.setBigSizeUrl(thumbPath);
                                    } else if (!TextUtils.isEmpty(path)) {
                                        bean.setThumbnailUrl(path);
                                        bean.setBigSizeUrl(path);
                                    }
                                    beenList.add(bean);
                                }

                                ImageBrowserActivity.start(activity, position, 100, beenList, ShowImageType.SAVE, 0, "");

                            }

                            @Override
                            public void onFailed(int code) {
                                HiStudentLog.e("query msg by type failed, code:" + code);
                            }

                            @Override
                            public void onException(Throwable exception) {

                            }
                        });

                        break;
                }
            }
        };

        registerReceiver(receiver, myIntentFilter);
    }

}
