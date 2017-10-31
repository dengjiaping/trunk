package com.histudent.jwsoft.histudent.body.message.uikit.session.search;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.session.module.Container;
import com.netease.nim.uikit.session.module.ModuleProxy;
import com.netease.nim.uikit.session.module.list.MessageListPanel;
import com.netease.nim.uikit.uinfo.UserInfoHelper;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

public class DisplayMessageActivity extends UI implements ModuleProxy, View.OnClickListener {

    private static String EXTRA_ANCHOR = "anchor";

    public static void start(Context context, IMMessage anchor) {
        Intent intent = new Intent();
        intent.setClass(context, DisplayMessageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //search extra
        intent.putExtra(EXTRA_ANCHOR, anchor);

        context.startActivity(intent);
    }

    // context
    private SessionTypeEnum sessionType;
    private String account; // 对方帐号
    private IMMessage anchor;

    private MessageListPanel messageListPanel;

    /**
     * 设置状态栏透明
     */
    private void setransparencyWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setransparencyWindow();
        super.onCreate(savedInstanceState);

        View rootView = LayoutInflater.from(this).inflate(R.layout.message_history_activity, null);
        setContentView(rootView);

        onParseIntent();

        Container container = new Container(this, account, sessionType, this);
        messageListPanel = new MessageListPanel(container, rootView, anchor, true, false);
        messageListPanel.scrollToItem(0);

        LinearLayout layout_left = (LinearLayout) findViewById(com.netease.nim.uikit.R.id.title_left);
        TextView title = (TextView) findViewById(com.netease.nim.uikit.R.id.title_middle_text);
        layout_left.setOnClickListener(this);

        title.setText("聊天记录");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        messageListPanel.onDestroy();
    }

    protected void onParseIntent() {
        anchor = (IMMessage) getIntent().getSerializableExtra(EXTRA_ANCHOR);
        account = anchor.getSessionId();
        sessionType = anchor.getSessionType();

        setTitle(UserInfoHelper.getUserTitleName(account, sessionType));
    }

    @Override
    public boolean sendMessage(IMMessage msg) {
        return false;
    }

    @Override
    public void onInputPanelExpand() {

    }

    @Override
    public void shouldCollapseInputPanel() {

    }

    @Override
    public boolean isLongClickEnabled() {
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
        }
    }
}
