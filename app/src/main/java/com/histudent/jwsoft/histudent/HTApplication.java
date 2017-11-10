package com.histudent.jwsoft.histudent;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.multidex.MultiDexApplication;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.facebook.stetho.Stetho;
import com.histudent.jwsoft.histudent.body.message.model.CustomNotificationBean;
import com.histudent.jwsoft.histudent.body.message.uikit.DemoCache;
import com.histudent.jwsoft.histudent.body.message.uikit.avchat.AVChatProfile;
import com.histudent.jwsoft.histudent.body.message.uikit.avchat.activity.AVChatActivity;
import com.histudent.jwsoft.histudent.body.message.uikit.rts.activity.RTSActivity;
import com.histudent.jwsoft.histudent.body.message.uikit.session.NimDemoLocationProvider;
import com.histudent.jwsoft.histudent.body.message.uikit.session.SessionHelper;
import com.histudent.jwsoft.histudent.body.message.uikit.session.extension.CustomAttachmentType;
import com.histudent.jwsoft.histudent.body.message.uikit.session.extension.GuessAttachment;
import com.histudent.jwsoft.histudent.body.message.uikit.session.extension.HomeAttachment;
import com.histudent.jwsoft.histudent.body.message.uikit.session.extension.NoticeAttachment;
import com.histudent.jwsoft.histudent.body.message.uikit.session.fragment.RecentContactsModel;
import com.histudent.jwsoft.histudent.body.mine.activity.MessageManageActivity;
import com.histudent.jwsoft.histudent.commen.activity.MyWebActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.preference.Preferences;
import com.histudent.jwsoft.histudent.commen.preference.UserPreferences;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.comment2.utils.TimeUtils;
import com.histudent.jwsoft.histudent.di.componet.AppComponent;
import com.histudent.jwsoft.histudent.di.componet.DaggerAppComponent;
import com.histudent.jwsoft.histudent.di.module.AppModule;
import com.histudent.jwsoft.histudent.widget.refresh.ClassicsFooter;
import com.histudent.jwsoft.histudent.widget.refresh.ClassicsHeader;
import com.netease.nim.uikit.ImageLoaderKit;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.cache.FriendDataCache;
import com.netease.nim.uikit.cache.NimUserInfoCache;
import com.netease.nim.uikit.cache.TeamDataCache;
import com.netease.nim.uikit.contact.ContactProvider;
import com.netease.nim.uikit.session.viewholder.MsgViewHolderThumbBase;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.NimStrings;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.avchat.model.AVChatAttachment;
import com.netease.nimlib.sdk.avchat.model.AVChatData;
import com.netease.nimlib.sdk.msg.MessageNotifierCustomization;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.rts.RTSManager;
import com.netease.nimlib.sdk.rts.model.RTSData;
import com.netease.nimlib.sdk.team.constant.TeamFieldEnum;
import com.netease.nimlib.sdk.team.model.IMMessageFilter;
import com.netease.nimlib.sdk.team.model.UpdateTeamAttachment;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.umeng.socialize.PlatformConfig;
import com.zhy.autolayout.config.AutoLayoutConifg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by liuguiyu-pc on 2016/5/30.
 */
public class HTApplication extends MultiDexApplication {

    //false: 测试，true:生产
    public static boolean isOnLine = true;

    private static HTApplication instance;

    public static synchronized HTApplication getInstance() {
        return instance;
    }

    /**
     * Module与Activity连接器
     */
    private AppComponent mAppComponent;

    //字体图标
    private Typeface iconTypeFace;
    public String PushId;

    static {
        //初始化上拉刷新及上拉加载
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater((Context context, RefreshLayout layout) -> new ClassicsHeader(context));
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater((Context context, RefreshLayout layout) -> new ClassicsFooter(context));
    }

    public Typeface getIconTypeFace() {
        return iconTypeFace;
    }

    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
//        LeakCanary.install(this);
        instance = this;
        initDaggerComponent();
        iconTypeFace = Typeface.createFromAsset(getAssets(), "fonts/icomoon.ttf");

        AutoLayoutConifg.getInstance().useDeviceSize();

//        BlockCanary.install(this, new AppBlockCanaryContext()).start();
        //友盟错误统计开关
        MobclickAgent.setCatchUncaughtExceptions(isOnLine);

//        if (!isOnLine) {
//            HiStudentCrashHandler handler = HiStudentCrashHandler.getInstance();
//            handler.init(getApplicationContext());
//        }


        //====================================友盟分享=====================================================
        //微信 appid appsecret(分享)
        PlatformConfig.setWeixin(getResources().getString(R.string.weiXin_appId), getResources().getString(R.string.weiXin_appSecret));

        //QQ空间 appid appsecret（分享）
        PlatformConfig.setQQZone(getResources().getString(R.string.QQZone_appId), getResources().getString(R.string.QQZone_appKey));

        HiCache.getInstance().setContext(this);

        //====================================友盟推送=====================================================
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.setDisplayNotificationNumber(3);

        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                HiStudentLog.e("友盟注册成功：" + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                HiStudentLog.e("友盟注册失败：" + s + "==" + s1);
            }
        });

        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER); //声音
        mPushAgent.setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SERVER);//呼吸灯
        mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SERVER);//振动

        /**
         * 定义收到通知后，所呈现的样式
         */
        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            @Override
            public Notification getNotification(Context context, UMessage msg) {
                switch (msg.builder_id) {
                    case 1:
                        NotificationCompat.Builder builder_01 = new NotificationCompat.Builder(context);
                        builder_01.setContentTitle(msg.title);
                        builder_01.setContentText(msg.text);
                        builder_01.setSmallIcon(R.mipmap.hi_log);
                        builder_01.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.hi_log));
                        builder_01.setShowWhen(true);
                        builder_01.setAutoCancel(true);
                        builder_01.setOngoing(false);
                        return builder_01.getNotification();
                    default:
                        return super.getNotification(context, msg);
                }
            }
        };
        mPushAgent.setMessageHandler(messageHandler);

        /**
         * 定义收到通知后，进行的后续操作
         */
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {

                HiStudentLog.e("-------推送消息----->" + msg.custom);
                CustomNotificationBean bean = JSON.parseObject(msg.custom, CustomNotificationBean.class);

                if (!HiCache.getInstance().isHaveTheSystemNotification(bean.getBatchNumber())) {
                    //数据库中没有该条数据，将数据入库
                    RecentContactsModel model = new RecentContactsModel();
                    model.setBatchNumber(bean.getBatchNumber());
                    model.setChatType(bean.getMsgtype());
                    model.setPushId(bean.getPushId());
                    model.setOfficiaId(bean.getOfficiaId());
                    model.setTitle(bean.getTitle());
                    model.setTime(TimeUtils.getTimeLong(bean.getCreatetime()));
                    model.setContent(bean.getContent());
                    model.setUrl(bean.getUrl());
                    model.setLogo(bean.getLogo());
                    model.setUnreadCount(0);
                    model.setIsRead(1);
                    model.setImg(bean.getImg());
                    model.setUserId(HiCache.getInstance().getLoginUserInfo().getUserId());
                    model.setOpenmode(bean.getOpenmode());
                    model.setOpenParam(bean.getOpenParam());
                    model.setImmsgtype(bean.getImmsgtype());
                    HiCache.getInstance().saveSystemNotification(model);
                } else {
                    //数据库中有该条数据，修改红点标记
                    HiCache.getInstance().exchangeSystemNotificationUnreadNum(bean.getMsgtype());
                }

                //跳转指定页面
//                Intent intent = new Intent();
//                intent.setClass(context.getApplicationContext(), SystemInformationActivity.class);
//                intent.putExtra("flag", bean.getMsgtype());
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.getApplicationContext().startActivity(intent);


                if (TextUtils.isEmpty(bean.getUrl())) {
                    Intent intent = new Intent();
                    intent.setClass(context, MessageManageActivity.class);
                    context.startActivity(intent);
                    return;
                }
                MyWebActivity.start(context, bean.getUrl());

            }
        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);


        //================================云信相关监听=====================================================

        DemoCache.setContext(this);

        NIMClient.init(this, getLoginInfo(), getOptions());

        //ExtraOptions.provide();

        // crash handler
        //AppCrashHandler.getInstance(this);

        if (inMainProcess()) {
            //init pinyin
            //PinYin.init(this);
            //PinYin.validate();

            // 初始化UIKit模块
            initUIKit();

            // 注册通知消息过滤器
            registerIMMessageFilter();

            // 初始化消息提醒
            NIMClient.toggleNotification(UserPreferences.getNotificationToggle());

            // 注册网络通话来电
            enableAVChat();

            // 注册白板会话
            enableRTS();

            // 注册语言变化监听
            registerLocaleReceiver(true);
        }
        PushId = PushAgent.getInstance(this).getRegistrationId();
    }


    //=========================================================================================
    private LoginInfo getLoginInfo() {
        String account = Preferences.getUserAccount();
        String token = Preferences.getUserToken();

        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
            DemoCache.setAccount(account.toLowerCase());
            return new LoginInfo(account, token);
        } else {
            return null;
        }
    }

    private SDKOptions getOptions() {
        SDKOptions options = new SDKOptions();

        // 如果将新消息通知提醒托管给SDK完成，需要添加以下配置。
        StatusBarNotificationConfig config = UserPreferences.getStatusConfig();
        if (config == null) {
            config = new StatusBarNotificationConfig();
        }
        // 点击通知需要跳转到的界面
        config.notificationEntrance = MessageManageActivity.class;
        config.notificationSmallIconId = R.mipmap.hi_log;

        // 通知铃声的uri字符串
        config.notificationSound = "android.resource://com.netease.nim.demo/raw/msg";

        // 呼吸灯配置
        config.ledARGB = Color.GREEN;
        config.ledOnMs = 1000;
        config.ledOffMs = 1500;

        options.statusBarNotificationConfig = config;
        DemoCache.setNotificationConfig(config);
        UserPreferences.setStatusConfig(config);

        // 配置保存图片，文件，log等数据的目录
        String sdkPath = Environment.getExternalStorageDirectory() + "/" + getPackageName() + "/nim";
        options.sdkStorageRootPath = sdkPath;

        // 配置数据库加密秘钥
        options.databaseEncryptKey = "NETEASE";

        // 配置是否需要预下载附件缩略图
        options.preloadAttach = true;

        // 配置附件缩略图的尺寸大小，
        options.thumbnailSize = MsgViewHolderThumbBase.getImageMaxEdge();

        // 用户信息提供者
        options.userInfoProvider = infoProvider;

        // 定制通知栏提醒文案（可选，如果不定制将采用SDK默认文案）
        options.messageNotifierCustomization = messageNotifierCustomization;

        return options;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public boolean inMainProcess() {
        String packageName = getPackageName();
        String processName = SystemUtil.getProcessName(this);
        return packageName.equals(processName);
    }

    /**
     * 通知消息过滤器（如果过滤则该消息不存储不上报）
     */
    private void registerIMMessageFilter() {
        NIMClient.getService(MsgService.class).registerIMMessageFilter(new IMMessageFilter() {
            @Override
            public boolean shouldIgnore(IMMessage message) {
                if (UserPreferences.getMsgIgnore() && message.getAttachment() != null) {
                    if (message.getAttachment() instanceof UpdateTeamAttachment) {
                        UpdateTeamAttachment attachment = (UpdateTeamAttachment) message.getAttachment();
                        for (Map.Entry<TeamFieldEnum, Object> field : attachment.getUpdatedFields().entrySet()) {
                            if (field.getKey() == TeamFieldEnum.ICON) {
                                return true;
                            }
                        }
                    } else if (message.getAttachment() instanceof AVChatAttachment) {
                        return true;
                    }
                }
                return false;
            }
        });
    }

    /**
     * 音视频通话配置与监听
     */
    private void enableAVChat() {
        registerAVChatIncomingCallObserver(true);
    }

    private void registerAVChatIncomingCallObserver(boolean register) {
        AVChatManager.getInstance().observeIncomingCall(new Observer<AVChatData>() {
            @Override
            public void onEvent(AVChatData data) {
                String extra = data.getExtra();
                Log.e("Extra", "Extra Message->" + extra);
                // 有网络来电打开AVChatActivity
                AVChatProfile.getInstance().setAVChatting(true);
                AVChatActivity.launch(DemoCache.getContext(), data, AVChatActivity.FROM_BROADCASTRECEIVER);
            }
        }, register);
    }

    /**
     * 白板实时时会话配置与监听
     */
    private void enableRTS() {
        registerRTSIncomingObserver(true);
    }


    private void registerRTSIncomingObserver(boolean register) {
        RTSManager.getInstance().observeIncomingSession(new Observer<RTSData>() {
            @Override
            public void onEvent(RTSData rtsData) {
                RTSActivity.incomingSession(DemoCache.getContext(), rtsData, RTSActivity.FROM_BROADCAST_RECEIVER);
            }
        }, register);
    }

    private void registerLocaleReceiver(boolean register) {
        if (register) {
            updateLocale();
            IntentFilter filter = new IntentFilter(Intent.ACTION_LOCALE_CHANGED);
            registerReceiver(localeReceiver, filter);
        } else {
            unregisterReceiver(localeReceiver);
        }
    }

    private BroadcastReceiver localeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_LOCALE_CHANGED)) {
                updateLocale();
            }
        }
    };

    private void updateLocale() {
        NimStrings strings = new NimStrings();
        strings.status_bar_multi_messages_incoming = getString(R.string.nim_status_bar_multi_messages_incoming);
        strings.status_bar_image_message = getString(R.string.nim_status_bar_image_message);
        strings.status_bar_audio_message = getString(R.string.nim_status_bar_audio_message);
        strings.status_bar_custom_message = getString(R.string.nim_status_bar_custom_message);
        strings.status_bar_file_message = getString(R.string.nim_status_bar_file_message);
        strings.status_bar_location_message = getString(R.string.nim_status_bar_location_message);
        strings.status_bar_notification_message = getString(R.string.nim_status_bar_notification_message);
        strings.status_bar_ticker_text = getString(R.string.nim_status_bar_ticker_text);
        strings.status_bar_unsupported_message = getString(R.string.nim_status_bar_unsupported_message);
        strings.status_bar_video_message = getString(R.string.nim_status_bar_video_message);
        strings.status_bar_hidden_message_content = getString(R.string.nim_status_bar_hidden_msg_content);
        NIMClient.updateStrings(strings);
    }

    private void initUIKit() {
        // 初始化，需要传入用户信息提供者
        NimUIKit.init(this, infoProvider, contactProvider);

        // 设置地理位置提供者。如果需要发送地理位置消息，该参数必须提供。如果不需要，可以忽略。
        NimUIKit.setLocationProvider(new NimDemoLocationProvider());

        // 会话窗口的定制初始化。
        SessionHelper.init();

        // 通讯录列表定制初始化
//        ContactHelper.init();
    }

    private UserInfoProvider infoProvider = new UserInfoProvider() {
        @Override
        public UserInfo getUserInfo(String account) {
            UserInfo user = NimUserInfoCache.getInstance().getUserInfo(account);
            if (user == null) {
                NimUserInfoCache.getInstance().getUserInfoFromRemote(account, null);
            }

            return user;
        }

        @Override
        public int getDefaultIconResId() {
            return R.mipmap.avatar_def;
        }

        @Override
        public Bitmap getTeamIcon(String teamId) {
            Drawable drawable = getResources().getDrawable(R.mipmap.hi_log);
            if (drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable) drawable).getBitmap();
            }

            return null;
        }

        @Override
        public Bitmap getAvatarForMessageNotifier(String account) {
            /**
             * 注意：这里最好从缓存里拿，如果读取本地头像可能导致UI进程阻塞，导致通知栏提醒延时弹出。
             */
            UserInfo user = getUserInfo(account);
            return (user != null) ? ImageLoaderKit.getNotificationBitmapFromCache(user) : null;
        }

        @Override
        public String getDisplayNameForMessageNotifier(String account, String sessionId, SessionTypeEnum sessionType) {
            String nick = null;
            if (sessionType == SessionTypeEnum.P2P) {
                nick = NimUserInfoCache.getInstance().getAlias(account);
            } else if (sessionType == SessionTypeEnum.Team) {
                nick = TeamDataCache.getInstance().getTeamNick(sessionId, account);
                if (TextUtils.isEmpty(nick)) {
                    nick = NimUserInfoCache.getInstance().getAlias(account);
                }
            }
            // 返回null，交给sdk处理。如果对方有设置nick，sdk会显示nick
            if (TextUtils.isEmpty(nick)) {
                return null;
            }

            return nick;
        }
    };

    private ContactProvider contactProvider = new ContactProvider() {
        @Override
        public List<UserInfoProvider.UserInfo> getUserInfoOfMyFriends() {
            List<NimUserInfo> nimUsers = NimUserInfoCache.getInstance().getAllUsersOfMyFriend();
            List<UserInfoProvider.UserInfo> users = new ArrayList<>(nimUsers.size());
            if (!nimUsers.isEmpty()) {
                users.addAll(nimUsers);
            }

            return users;
        }

        @Override
        public int getMyFriendsCount() {
            return FriendDataCache.getInstance().getMyFriendCounts();
        }

        @Override
        public String getUserDisplayName(String account) {
            return NimUserInfoCache.getInstance().getUserDisplayName(account);
        }
    };

    private MessageNotifierCustomization messageNotifierCustomization = new MessageNotifierCustomization() {
        @Override
        public String makeNotifyContent(String nick, IMMessage message) {
            String msg = "";
            MsgAttachment msgAttachment = message.getAttachment();

            if (msgAttachment instanceof HomeAttachment) {
                HomeAttachment attachment = (HomeAttachment) msgAttachment;
                switch (attachment.getMsgModel().getType()) {
                    case CustomAttachmentType.HomeWork://家庭作业
                        msg = "[作业消息]";
                        break;
                    case CustomAttachmentType.HUODONG_G://班级活动
                        msg = "[活动消息]";
                        break;
                    case CustomAttachmentType.HUODONG_C://社群活动
                        msg = "[活动消息]";
                        break;
                }
            } else if (msgAttachment instanceof NoticeAttachment) {
                msg = "[通知消息]";
            } else if (msgAttachment instanceof GuessAttachment) {
                GuessAttachment attachment = (GuessAttachment) msgAttachment;
                GuessAttachment.Guess s = attachment.getValue();
                msg = "[" + s.getDesc() + "]";
            }

            return msg; // 采用SDK默认文案
        }

        @Override
        public String makeTicker(String nick, IMMessage message) {
            return nick; // 采用SDK默认文案
        }
    };


    /**
     * 初始化Dagger所使用的连接器
     */
    private void initDaggerComponent() {
        mAppComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule())
                .build();
    }

    /**
     * 获取连接器
     *
     * @return
     */
    public AppComponent getAppComponent() {
        return mAppComponent;
    }

}
