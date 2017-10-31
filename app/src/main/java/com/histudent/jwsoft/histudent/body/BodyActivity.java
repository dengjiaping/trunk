package com.histudent.jwsoft.histudent.body;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.HiStudentApplication;
import com.histudent.jwsoft.histudent.HiStudentLog;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.account.login.activity.LoginActivity;
import com.histudent.jwsoft.histudent.account.login.model.CurrentUserInfoModel;
import com.histudent.jwsoft.histudent.body.find.fragment.FindFragment;
import com.histudent.jwsoft.histudent.body.homepage.fragment.HomeFragment;
import com.histudent.jwsoft.histudent.body.message.activity.MyFriendsActivity;
import com.histudent.jwsoft.histudent.body.message.uikit.session.fragment.ChatType;
import com.histudent.jwsoft.histudent.body.message.uikit.session.fragment.RecentContactsModel;
import com.histudent.jwsoft.histudent.body.mine.activity.PersonalDataActivity;
import com.histudent.jwsoft.histudent.body.mine.fragment.MineFragment;
import com.histudent.jwsoft.histudent.body.mine.model.UserClassListModel;
import com.histudent.jwsoft.histudent.body.mine.parser.DataParser;
import com.histudent.jwsoft.histudent.body.myclass.fragment.ClassFragment;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.MyWebActivity;
import com.histudent.jwsoft.histudent.commen.bean.ExitBean;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.keyword.utils.SharedPreferencedUtils;
import com.histudent.jwsoft.histudent.commen.listener.DialogButtonListener;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.receiver.RequestCodeModel;
import com.histudent.jwsoft.histudent.commen.receiver.TheReceiverAction;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.DataUtils;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.UtilsStyle;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.constant.ParamKeys;
import com.histudent.jwsoft.histudent.entity.IMMessageEvent;
import com.histudent.jwsoft.histudent.entity.MessageCountEvent;
import com.histudent.jwsoft.histudent.entity.MessageUpdateEvent;
import com.histudent.jwsoft.histudent.entity.ReadClockMessageEvent;
import com.histudent.jwsoft.histudent.info.persioninfo.activity.PersonCenterActivity;
import com.histudent.jwsoft.histudent.manage.UserManager;
import com.histudent.jwsoft.histudent.tool.ToastTool;
import com.histudent.jwsoft.histudent.widget.popupwindow.PopupWindowPublishContent;
import com.netease.nim.uikit.LoginSyncDataStatusObserver;
import com.netease.nim.uikit.common.ui.dialog.CustomAlertDialog;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;


/**
 * Created by liuguiyu-pc on 2016/5/26.
 * 登录后主体界面
 */
public class BodyActivity extends BaseActivity {

    private FrameLayout frame, frame_;
    private LinearLayout bottom;
    private RelativeLayout bodyFragment;
    private IconView footMessageImage, footHiworldImage, footFindImage, footMyImage;
    private TextView footMessageText, footHiwroldText, footFindText, footMyText;

    private final int TYPE_MESSAGE = 0;
    private final int TYPE_HIWORLD = 1;
    private final int TYPE_FIND = 2;
    private final int TYPE_MY = 3;
    private int current_type = 0;
    private int old_type = -1;

    private List<IconView> iamgeView_foot;
    private List<TextView> textView_foot;

    private FragmentManager fragmentManager;
    private Fragment fragment_homePage, fragment_class, fragment_find, fragment_mine, fragment_flag;

    private BroadcastReceiver receiver;
    private BodyHelper helper;

    private boolean returnKey;//再按一次退出标志位
    private boolean isExit;//强制下线标志位
    private int unReadNum;
    private int imNumber;
    private int mReadClockMessageCount;
    private int homeNumber;//家校通，班级
    private int systemNumber;
    private int netNumber;//点赞等网络请求
    private int totle;
    private List<UserClassListModel> userClasss;

    @BindView(R.id.message_num)
    TextView mMessageNum;
    @BindView(R.id.im_number)
    TextView mImNum;
    @BindView(R.id.ll_publish)
    LinearLayout mLLPublish;
    @BindView(R.id.publish_action)
    IconView mPublishIcon;
    private PopupWindowPublishContent mPopupWindowPublishContent;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, BodyActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        setransparencyWindow();
        //setInput();

        UtilsStyle.setTranslateStatusBar(this);

        //推送设置别名
        CurrentUserInfoModel model = HiCache.getInstance().getLoginUserInfo();
        if (model != null && !TextUtils.isEmpty(model.getUserId())) {
            PushAgent.getInstance(this).addAlias(model.getUserId(), "sns_userid", new UTrack.ICallBack() {
                @Override
                public void onMessage(boolean isSuccess, String message) {
                    HiStudentLog.e("=== 推送别名设置成功：" + message);
                }
            });
        }

        // 等待同步数据完成
        boolean syncCompleted = LoginSyncDataStatusObserver.getInstance().observeSyncDataCompletedEvent(new Observer<Void>() {
            @Override
            public void onEvent(Void v) {
                DialogMaker.dismissProgressDialog();
            }
        });

        if (!syncCompleted) {
            DialogMaker.showProgressDialog(BodyActivity.this, "正在准备数据...").setCanceledOnTouchOutside(false);
        }

        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);

        //检查版本更新
        UserManager.getInstance().checkVersionUpdate(this);
    }

    @Override
    public void initView() {

//        frame = (FrameLayout) findViewById(R.id.frame);
//        frame_ = (FrameLayout) findViewById(R.id.frame_);
        footMessageImage = (IconView) findViewById(R.id.foot_message_image);
        footMessageText = (TextView) findViewById(R.id.foot_message_text);
        footHiworldImage = (IconView) findViewById(R.id.foot_hiworld_image);
        footHiwroldText = (TextView) findViewById(R.id.foot_hiwrold_text);
        footFindImage = (IconView) findViewById(R.id.foot_find_image);
        footFindText = (TextView) findViewById(R.id.foot_find_text);
        footMyImage = (IconView) findViewById(R.id.foot_my_image);
        bottom = (LinearLayout) findViewById(R.id.bottom);
        footMyText = (TextView) findViewById(R.id.foot_my_text);
        bodyFragment = (RelativeLayout) findViewById(R.id.body_fragment);

        iamgeView_foot = new ArrayList<>();
        textView_foot = new ArrayList<>();

        fragment_homePage = new HomeFragment();
        fragment_class = new ClassFragment();
        fragment_find = new FindFragment();
        fragment_mine = new MineFragment();

        textView_foot.add(footMessageText);
        textView_foot.add(footHiwroldText);
        textView_foot.add(footFindText);
        textView_foot.add(footMyText);

        iamgeView_foot.add(footMessageImage);
        iamgeView_foot.add(footHiworldImage);
        iamgeView_foot.add(footFindImage);
        iamgeView_foot.add(footMyImage);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.body_fragment, fragment_homePage).commit();
        fragment_flag = fragment_homePage;

        helper = new BodyHelper(this, iamgeView_foot);

        helper.showTitleAndFoot(current_type, old_type, iamgeView_foot, textView_foot);
//        helper.showBottomPopupWindow(this,frame, frame_, RequestCodeModel.getInstence().PUBLIC, 200);

    }

    @Override
    public void doAction() {
        super.doAction();


        initClassList();
        loadListener();
    }


    private void loadListener() {
        mLLPublish.setOnClickListener((View view) -> {
            mPublishIcon.setVisibility(View.INVISIBLE);
            mPopupWindowPublishContent = PopupWindowPublishContent.create(BodyActivity.this, RequestCodeModel.getInstence().PUBLIC, 200);
            mPopupWindowPublishContent.showMoreWindow(view);
            mPopupWindowPublishContent.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    if (mPopupWindowPublishContent != null) {
                        mPopupWindowPublishContent.close();
                    }
                }
            });
        });

    }

    public void showPublishIcon() {
        mPublishIcon.setVisibility(View.VISIBLE);
    }

    private void initClassList() {
        DataUtils.GetUserSyncClassList(this, false, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                Log.e("onSuccess", result);

                userClasss = DataParser.getUserClassList(result);
                registerIM(true);
                updataNewMsgs();
            }

            @Override
            public void onFailure(String errorMsg) {
            }
        }, LoadingType.NONE);
    }


    /**
     * 刷新消息数
     */
    private void updataNewMsgs() {
        NIMClient.getService(MsgService.class).queryRecentContacts().setCallback(requestCallbackWrapper);
    }

    /**
     * 获取最近未读会话
     */
    RequestCallbackWrapper requestCallbackWrapper = new RequestCallbackWrapper<List<RecentContact>>() {
        @Override
        public void onResult(int code, List<RecentContact> recents, Throwable exception) {

            if (code != ResponseCode.RES_SUCCESS || recents == null) {
                NIMClient.getService(MsgService.class).queryRecentContacts().setCallback(requestCallbackWrapper);
                return;
            }
            unReadNum = 0;
            imNumber = 0;
            String userId = HiCache.getInstance().getLoginUserInfo().getUserId().replaceAll("-", "");
            for (RecentContact recentContact : recents) {
                if (recentContact.getFromAccount().equals(userId)) {
                    continue;
                }
                unReadNum += recentContact.getUnreadCount();
                if (userClasss != null) {
                    for (UserClassListModel userClassListModel : userClasss) {
                        if (userClassListModel.getChatGroupKey().equals(recentContact.getContactId())) {
                            imNumber += recentContact.getUnreadCount();
                        }
                    }
                }

            }
            //EventBus.getDefault().postSticky(new RecentContactEvent(recents));
            if (mImNum != null) {
                mImNum.setVisibility(imNumber + mReadClockMessageCount > 0 ? View.VISIBLE : View.GONE);
                mImNum.setText(String.valueOf(imNumber + mReadClockMessageCount));
            }
            if (mMessageNum != null) {
                mMessageNum.setVisibility((unReadNum + systemNumber + netNumber) > 0 ? View.VISIBLE : View.GONE);
                mMessageNum.setText(String.valueOf(unReadNum + systemNumber + netNumber));
            }
            EventBus.getDefault().postSticky(new MessageCountEvent(unReadNum + systemNumber + netNumber));
        }
    };


    @Subscribe(sticky = true)
    public void onEvent(ReadClockMessageEvent readClockMessageEvent) {
        //当有阅读打卡消息时  调用
        mReadClockMessageCount = readClockMessageEvent.getCount();
        if (mImNum != null) {
            mImNum.setVisibility(imNumber + mReadClockMessageCount > 0 ? View.VISIBLE : View.GONE);
            mImNum.setText(String.valueOf(imNumber + mReadClockMessageCount));
        }
    }

    @Subscribe
    public void onEvent(ExitBean bean) {
        if (isExit) return;
        isExit = true;
        ReminderHelper.getIntentce().showDialog(this, "下线通知", bean.getMsg(), "立即退出", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {
                HiCache.getInstance().clearUserToken();
                SharedPreferencedUtils.setString(HiStudentApplication.getInstance(), ParamKeys.TOKEN, null);
                helper.exitAction();
            }
        }, "重新登陆", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {
                //当用户被迫下线后  清空token
                HiCache.getInstance().clearUserToken();
                SharedPreferencedUtils.setString(HiStudentApplication.getInstance(), ParamKeys.TOKEN, null);
                helper.exitAction();
                LoginActivity.start(BodyActivity.this);
            }
        });
    }

    @Override
    public int setViewLayout() {
        return R.layout.body_activity;
    }

    @Override
    public void doWorkByResevier() {
        super.doWorkByResevier();

        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(TheReceiverAction.HIDE_FLOOTACTIONBAR);
        myIntentFilter.addAction(TheReceiverAction.SHOW_FLOOTACTIONBAR);
        myIntentFilter.addAction(TheReceiverAction.DELET_ABOUTMINE_COMMENT_01);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, final Intent intent) {

                switch (intent.getAction()) {

                    case TheReceiverAction.HIDE_FLOOTACTIONBAR:
                        frame.setVisibility(View.GONE);
                        bottom.setVisibility(View.GONE);
                        break;

                    case TheReceiverAction.SHOW_FLOOTACTIONBAR:
                        helper.hideKeyword(bodyFragment);
                        frame.setVisibility(View.VISIBLE);
                        bottom.setVisibility(View.VISIBLE);
                        break;

                    case TheReceiverAction.DELET_ABOUTMINE_COMMENT_01:

                        final String commentId = intent.getStringExtra("commentId");
                        final String userId = intent.getStringExtra("userId");
                        CustomAlertDialog dialog = new CustomAlertDialog(BodyActivity.this);

                        if (SystemUtil.isOneselfIn(userId)) {
                            dialog.addItem("删除", new CustomAlertDialog.onSeparateItemClickListener() {
                                @Override
                                public void onClick() {
                                    helper.deletCommentData(commentId, intent.getIntExtra("position01", -1), intent.getIntExtra("position02", -1));
                                }
                            });
                        }
                        dialog.addItem("复制", new CustomAlertDialog.onSeparateItemClickListener() {
                            @Override
                            public void onClick() {
                                helper.copy(intent.getStringExtra("content"));
                            }
                        });
                        dialog.show();

                        break;
                }
            }
        };

        registerReceiver(receiver, myIntentFilter);
    }

    @Override
    public void onClick(View view) {

        int flag = current_type;

        switch (view.getId()) {
            case R.id.foot_message:
                if (current_type == TYPE_MESSAGE)
                    break;

                current_type = TYPE_MESSAGE;

                helper.switchFragment(fragmentManager, fragment_flag, fragment_homePage);
                fragment_flag = fragment_homePage;

                helper.showTitleAndFoot(current_type, flag, iamgeView_foot, textView_foot);

                break;

            case R.id.foot_hiword:
                if (current_type == TYPE_HIWORLD) {
                    break;
                }
                current_type = TYPE_HIWORLD;
                helper.switchFragment(fragmentManager, fragment_flag, fragment_class);
                fragment_flag = fragment_class;

                helper.showTitleAndFoot(current_type, flag, iamgeView_foot, textView_foot);

                break;
            case R.id.foot_find:
                if (current_type == TYPE_FIND)
                    break;

                current_type = TYPE_FIND;
                helper.switchFragment(fragmentManager, fragment_flag, fragment_find);
                fragment_flag = fragment_find;

                helper.showTitleAndFoot(current_type, flag, iamgeView_foot, textView_foot);

                break;

            case R.id.foot_my:

                if (current_type == TYPE_MY)
                    break;
                current_type = TYPE_MY;
                helper.switchFragment(fragmentManager, fragment_flag, fragment_mine);
                fragment_flag = fragment_mine;
                helper.showTitleAndFoot(current_type, flag, iamgeView_foot, textView_foot);
                break;

            case R.id.title_left:

                if (current_type == TYPE_MESSAGE) {

                    Intent intent_myFriend = new Intent(BodyActivity.this, MyFriendsActivity.class);
                    startActivity(intent_myFriend);

                } else if (current_type == TYPE_MY) {
                    PersonCenterActivity.start(this, HiCache.getInstance().getLoginUserInfo().getUserId());
                }

                break;

            case R.id.title_right:

                if (current_type == TYPE_MESSAGE) {
                    MyWebActivity.startNoClose(this, HistudentUrl.search);

                } else if (current_type == TYPE_HIWORLD) {

                } else if (current_type == TYPE_MY) {

                    PersonalDataActivity.start(this);
                }

                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 100) {
            helper.exitAction();
            LoginActivity.start(BodyActivity.this);
        } else if (requestCode == 101 && resultCode == 200) {
            helper.exit();
        } else if (requestCode == RequestCodeModel.getInstence().PUBLIC && resultCode == 200) {
            if (current_type == TYPE_HIWORLD) {
                fragment_flag.onActivityResult(requestCode, resultCode, data);
            } else {
                helper.synShare(this, data);
            }
        } else {
            fragment_flag.onActivityResult(requestCode, resultCode, data);
            EventBus.getDefault().postSticky(new MessageUpdateEvent());


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUnreadMessageCount();
        updataNewMsgs();
    }

    /**
     * 判断是否拦截返回键操作
     */
    @Override
    public void onBackPressed() {

        if (bottom.getVisibility() != View.VISIBLE) {
            helper.hideKeyword(bodyFragment);
            frame.setVisibility(View.VISIBLE);
            bottom.setVisibility(View.VISIBLE);
            returnKey = false;
        } else {
            if (!returnKey) {
                ReminderHelper.getIntentce().ToastShow(this, "再按一次退出");
                returnKey = true;
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);

        if (mPopupWindowPublishContent != null) {
            mPopupWindowPublishContent.dismiss();
        }

        registerIM(false);
    }

    private void registerIM(boolean register) {
        MsgServiceObserve service = NIMClient.getService(MsgServiceObserve.class);
        service.observeReceiveMessage(incomingMessageObserver, register);
    }

    /**
     * 监听实时消息
     */
    Observer<List<IMMessage>> incomingMessageObserver = new Observer<List<IMMessage>>() {
        @Override
        public void onEvent(List<IMMessage> messages) {
//            String userId = HiCache.getIntence().getLoginUserInfo().getUserId().replaceAll("-", "");
//            for (IMMessage message : messages) {
//                if (!message.getConfig().enableUnreadCount) {
//                    continue;
//                }
//                if (message.getFromAccount().equals(userId)) {
//                    continue;
//                }
//                EventBus.getDefault().postSticky(new IMMessageEvent(message));
//                unReadNum++;
//                for (UserClassListModel userClassListModel : userClasss) {
//                    if (message.getSessionId().equals(userClassListModel.getChatGroupKey())) {
//                        imNumber++;
//                    }
//                }
//            }
//
//            EventBus.getDefault().postSticky(new MessageCountEvent((unReadNum)));

//            mMessageNum.setVisibility((unReadNum) > 0 ? View.VISIBLE : View.GONE);
//            mMessageNum.setText(String.valueOf((unReadNum)));
//            mImNum.setVisibility(imNumber > 0 ? View.VISIBLE : View.GONE);
//            mImNum.setText(String.valueOf(imNumber));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (messages != null && messages.size() == 1) {
                EventBus.getDefault().postSticky(new IMMessageEvent(messages.get(0)));
            }

            NIMClient.getService(MsgService.class).queryRecentContacts().setCallback(requestCallbackWrapper);
        }
    };


    /**
     * 获取未读消息数量
     */
    public void getUnreadMessageCount() {

        Map<String, Object> map = new TreeMap<>();
        map.put("userId", HiCache.getInstance().getLoginUserInfo().getUserId());
        HiStudentHttpUtils.postDataByOKHttp(true, this, map, HistudentUrl.unreadMessageCount, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                netNumber = 0;
                try {
                    JSONObject object = new JSONObject(result);
                    netNumber += object.optInt("data");
                    getUnreadCount();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        }, LoadingType.NONE);

    }

    /**
     * 获取友盟推送数据的未读数
     */
    private void getUnreadCount() {
        /**
         * 从数据库中获取官方活动数据
         */
        List<RecentContactsModel> model_o = HiCache.getInstance().getSystemNotification(ChatType.ACTION);
        if (model_o != null && model_o.size() > 0) {
            RecentContactsModel model = model_o.get(model_o.size() - 1);
            if (model.getIsRead() == 1) {
                // unReadNum += 1;
            }

        }

        /**
         * 从数据库中获取系统消息数据
         */
        systemNumber = 0;
        List<RecentContactsModel> models = HiCache.getInstance().getSystemNotification(ChatType.SYSTEMINFO);

        if (models != null && models.size() > 0) {
            for (RecentContactsModel recentContactsModel : models) {
                if (recentContactsModel.getIsRead() == 1)
                    systemNumber += 1;
            }

        }
        EventBus.getDefault().postSticky(new MessageCountEvent(unReadNum + systemNumber + netNumber));
        mMessageNum.setVisibility((unReadNum + systemNumber + netNumber) > 0 ? View.VISIBLE : View.GONE);
        mMessageNum.setText(String.valueOf(unReadNum + systemNumber + netNumber));
    }


}
