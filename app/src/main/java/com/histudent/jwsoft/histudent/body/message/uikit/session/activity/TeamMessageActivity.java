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

import com.histudent.jwsoft.histudent.HiStudentLog;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.activity.ChatTeamInfoActivity;
import com.histudent.jwsoft.histudent.body.message.fragment.MyTeamMessageFragment;
import com.histudent.jwsoft.histudent.body.message.uikit.model.MsgModel;
import com.histudent.jwsoft.histudent.body.message.uikit.session.extension.CustomAttachmentType;
import com.histudent.jwsoft.histudent.body.myclass.activity.CreateHuoDongFirstStep;
import com.histudent.jwsoft.histudent.body.myclass.activity.HuoDongCenterActivity;
import com.histudent.jwsoft.histudent.body.myclass.activity.NoticeDetailActivity;
import com.histudent.jwsoft.histudent.body.myclass.activity.NoticePublishActivity;
import com.histudent.jwsoft.histudent.commen.activity.HTWebActivity;
import com.histudent.jwsoft.histudent.commen.activity.ImageBrowserActivity;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.enums.ShowImageType;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.receiver.TheReceiverAction;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.netease.nim.uikit.cache.FriendDataCache;
import com.netease.nim.uikit.cache.SimpleCallback;
import com.netease.nim.uikit.cache.TeamDataCache;
import com.netease.nim.uikit.model.ToolBarOptions;
import com.netease.nim.uikit.session.SessionCustomization;
import com.netease.nim.uikit.session.activity.BaseMessageActivity;
import com.netease.nim.uikit.session.constant.Extras;
import com.netease.nim.uikit.session.fragment.MessageFragment;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.attachment.ImageAttachment;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.team.TeamService;
import com.netease.nimlib.sdk.team.constant.TeamTypeEnum;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.team.model.TeamMember;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 群聊界面
 * <p/>
 * Created by huangjun on 2015/3/5.
 */
public class TeamMessageActivity extends BaseMessageActivity implements View.OnClickListener {

    // model
    private Team team;
    private View invalidTeamTipView;
    private TextView invalidTeamTipText;
    private MyTeamMessageFragment fragment;
    private Class<? extends Activity> backToClass;
    private BroadcastReceiver receiver;
    private List<IMMessage> messageList;
    private String tid;

    public static void start(Context context, String tid, SessionCustomization customization,
                             Class<? extends Activity> backToClass, IMMessage anchor) {
        Intent intent = new Intent();
        intent.putExtra(Extras.EXTRA_ACCOUNT, tid);
        intent.putExtra(Extras.EXTRA_CUSTOMIZATION, customization);
        intent.putExtra(Extras.EXTRA_BACK_TO_CLASS, backToClass);
        if (anchor != null) {
            intent.putExtra(Extras.EXTRA_ANCHOR, anchor);
        }
        intent.setClass(context, TeamMessageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        ((Activity)context).startActivityForResult(intent,998);
    }

    protected void findViews() {
        invalidTeamTipView = findView(R.id.invalid_team_tip);
        invalidTeamTipText = findView(R.id.invalid_team_text);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messageList = new ArrayList<>();
        tid = getIntent().getStringExtra(Extras.EXTRA_ACCOUNT);
        team = NIMClient.getService(TeamService.class).queryTeamBlock(tid);
        backToClass = (Class<? extends Activity>) getIntent().getSerializableExtra(Extras.EXTRA_BACK_TO_CLASS);
        findViews();
        initTitle();
        checkBigImage(this);
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    private void initTitle() {

        LinearLayout layout_left = (LinearLayout) findViewById(R.id.title_left);
        LinearLayout layout_right = (LinearLayout) findViewById(R.id.title_right);
        TextView title = (TextView) findViewById(R.id.title_middle_text);
        IconView image_right = (IconView) findViewById(R.id.title_right_image);

        layout_left.setOnClickListener(this);

        layout_right.setOnClickListener(this);

        title.setText(team == null ? sessionId : team.getName() + "(" + team.getMemberCount() + ")");
        image_right.setText(R.string.icon_member);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void doAction(Integer flag) {
        switch (flag) {
            case 1://通知
                NoticePublishActivity.start(this, 100);
                break;

            case 2://发作业
                HTWebActivity.start(this, HistudentUrl.homework);
                break;

            case 3://发起活动
                CreateHuoDongFirstStep.startNormal(this, HiCache.getInstance().getClassDetailInfo().getClassId());
                break;
        }
    }

    @Subscribe
    public void onEvent(MsgModel msgModel) {
        if (msgModel != null) {
            switch (msgModel.getType()) {
                case CustomAttachmentType.HomeWork://家庭作业
                    HTWebActivity.start(TeamMessageActivity.this, msgModel.getData().getUrl());
                    break;
                case CustomAttachmentType.HUODONG_G://班级活动
                    if (msgModel.getData().getOpenmode() == 1) {
                        HTWebActivity.start(TeamMessageActivity.this, msgModel.getData().getUrl());
                    } else if (msgModel.getData().getOpenmode() == 2) {
                        HuoDongCenterActivity.start(TeamMessageActivity.this, msgModel.getData().getOpenparam(), 1, 0);
                    }
                    break;
                case CustomAttachmentType.HUODONG_C://社群活动
                    if (msgModel.getData().getOpenmode() == 1) {
                        HTWebActivity.start(TeamMessageActivity.this, msgModel.getData().getUrl());
                    } else if (msgModel.getData().getOpenmode() == 2) {
                        HuoDongCenterActivity.start(TeamMessageActivity.this, msgModel.getData().getOpenparam(), 2, 20);
                    }
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        registerTeamUpdateObserver(false);

        if (receiver != null) {
            unregisterReceiver(receiver);
        }

        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerTeamUpdateObserver(true);
        requestTeamInfo();
    }

    /**
     * 请求群基本信息
     */
    private void requestTeamInfo() {
        // 请求群基本信息
        TeamDataCache dataCache = TeamDataCache.getInstance();
        if (dataCache == null) return;
        Team t = TeamDataCache.getInstance().getTeamById(sessionId);
        if (t != null) {
            updateTeamInfo(t);
        } else {
            TeamDataCache.getInstance().fetchTeamById(sessionId, new SimpleCallback<Team>() {
                @Override
                public void onResult(boolean success, Team result) {
                    if (success && result != null) {
                        updateTeamInfo(result);
                    } else {
                        onRequestTeamInfoFailed();
                    }
                }
            });
        }
    }

    private void onRequestTeamInfoFailed() {
        Toast.makeText(TeamMessageActivity.this, "获取群组信息失败!", Toast.LENGTH_SHORT).show();
        finish();
    }

    /**
     * 更新群信息
     *
     * @param d
     */
    private void updateTeamInfo(final Team d) {
        if (d == null) {
            return;
        }

        team = d;
        fragment.setTeam(team);

        setTitle(team == null ? sessionId : team.getName() + "(" + team.getMemberCount() + "人)");

        invalidTeamTipText.setText(team.getType() == TeamTypeEnum.Normal ? R.string.normal_team_invalid_tip : R.string.team_invalid_tip);
        invalidTeamTipView.setVisibility(team.isMyTeam() ? View.GONE : View.VISIBLE);
    }

    /**
     * 注册群信息更新监听
     *
     * @param register
     */
    private void registerTeamUpdateObserver(boolean register) {
        if (register) {
            TeamDataCache.getInstance().registerTeamDataChangedObserver(teamDataChangedObserver);
            TeamDataCache.getInstance().registerTeamMemberDataChangedObserver(teamMemberDataChangedObserver);
        } else {
            TeamDataCache.getInstance().unregisterTeamDataChangedObserver(teamDataChangedObserver);
            TeamDataCache.getInstance().unregisterTeamMemberDataChangedObserver(teamMemberDataChangedObserver);
        }
        FriendDataCache.getInstance().registerFriendDataChangedObserver(friendDataChangedObserver, register);
    }

    /**
     * 群资料变动通知和移除群的通知（包括自己退群和群被解散）
     */
    TeamDataCache.TeamDataChangedObserver teamDataChangedObserver = new TeamDataCache.TeamDataChangedObserver() {
        @Override
        public void onUpdateTeams(List<Team> teams) {
            if (team == null) {
                return;
            }
            for (Team t : teams) {
                if (t.getId().equals(team.getId())) {
                    updateTeamInfo(t);
                    break;
                }
            }
        }

        @Override
        public void onRemoveTeam(Team team) {
            if (team == null) {
                return;
            }
            if (team.getId().equals(TeamMessageActivity.this.team.getId())) {
                updateTeamInfo(team);
            }
        }
    };

    /**
     * 群成员资料变动通知和移除群成员通知
     */
    TeamDataCache.TeamMemberDataChangedObserver teamMemberDataChangedObserver = new TeamDataCache.TeamMemberDataChangedObserver() {

        @Override
        public void onUpdateTeamMember(List<TeamMember> members) {
            fragment.refreshMessageList();
        }

        @Override
        public void onRemoveTeamMember(TeamMember member) {
        }
    };

    FriendDataCache.FriendDataChangedObserver friendDataChangedObserver = new FriendDataCache.FriendDataChangedObserver() {
        @Override
        public void onAddedOrUpdatedFriends(List<String> accounts) {
            fragment.refreshMessageList();
        }

        @Override
        public void onDeletedFriends(List<String> accounts) {
            fragment.refreshMessageList();
        }

        @Override
        public void onAddUserToBlackList(List<String> account) {
            fragment.refreshMessageList();
        }

        @Override
        public void onRemoveUserFromBlackList(List<String> account) {
            fragment.refreshMessageList();
        }
    };

    @Override
    protected MessageFragment fragment() {
        // 添加fragment
        Bundle arguments = getIntent().getExtras();
        arguments.putSerializable(Extras.EXTRA_TYPE, SessionTypeEnum.Team);
        fragment = new MyTeamMessageFragment();
        fragment.setArguments(arguments);
        fragment.setContainerId(R.id.message_fragment_container);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.nim_team_message_activity_;
    }

    @Override
    protected void initToolBar() {
        ToolBarOptions options = new ToolBarOptions();
        options.titleString = "群聊";
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (backToClass != null) {
            Intent intent = new Intent();
            intent.setClass(this, backToClass);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.title_right:
                if (team.isMyTeam()) {
                    Intent intent = new Intent(TeamMessageActivity.this, ChatTeamInfoActivity.class);
                    intent.putExtra("teamId", getIntent().getStringExtra(Extras.EXTRA_ACCOUNT));
                    startActivity(intent);
                }
                break;
        }
    }

    public void checkBigImage(final Activity activity) {

        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(TheReceiverAction.CHECK_BIGIMG);
        myIntentFilter.addAction(TheReceiverAction.GOTIO_HOMEWORK);
        myIntentFilter.addAction(TheReceiverAction.GOTIO_NOTICE);

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
                    case TheReceiverAction.GOTIO_HOMEWORK:
                        HTWebActivity.start(TeamMessageActivity.this, intent.getStringExtra("url"));
                        break;
                    case TheReceiverAction.GOTIO_NOTICE:
                        NoticeDetailActivity.start(TeamMessageActivity.this, intent.getStringExtra("msg"));
                        break;
                }
            }
        };

        registerReceiver(receiver, myIntentFilter);
    }


    public void recordMessage(IMMessage message) {
        if (!TextUtils.isEmpty(message.getContent())){
            Map<String, Object> map = new TreeMap<>();
            map.put("groupId", tid);
            map.put("msg", message.getContent());
            HiStudentHttpUtils.postDataByOKHttp(map, HistudentUrl.recordMessage, new HttpRequestCallBack() {
                @Override
                public void onSuccess(String result) {
                }

                @Override
                public void onFailure(String errorMsg) {

                }
            });
        }

    }

}
