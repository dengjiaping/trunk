package com.histudent.jwsoft.histudent.body.mine.activity;

import android.content.Intent;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.HiStudentLog;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.activity.OfficialChatActivity;
import com.histudent.jwsoft.histudent.body.message.model.CustomNotificationBean;
import com.histudent.jwsoft.histudent.body.message.uikit.session.SessionHelper;
import com.histudent.jwsoft.histudent.body.message.uikit.session.extension.CustomAttachmentType;
import com.histudent.jwsoft.histudent.body.message.uikit.session.extension.GuessAttachment;
import com.histudent.jwsoft.histudent.body.message.uikit.session.extension.HomeAttachment;
import com.histudent.jwsoft.histudent.body.message.uikit.session.extension.NoticeAttachment;
import com.histudent.jwsoft.histudent.body.message.uikit.session.fragment.ChatType;
import com.histudent.jwsoft.histudent.body.message.uikit.session.fragment.MyRecentContactsFragment;
import com.histudent.jwsoft.histudent.body.message.uikit.session.fragment.RecentContactsModel;
import com.histudent.jwsoft.histudent.body.mine.adapter.MessageAdapter;
import com.histudent.jwsoft.histudent.body.mine.model.UnReadModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.comment2.utils.TimeUtils;
import com.histudent.jwsoft.histudent.constant.Const;
import com.histudent.jwsoft.histudent.entity.MessageClickEvent;
import com.histudent.jwsoft.histudent.entity.MessageEvent;
import com.histudent.jwsoft.histudent.entity.RecentContactDelete;
import com.netease.nim.uikit.cache.FriendDataCache;
import com.netease.nim.uikit.cache.TeamDataCache;
import com.netease.nim.uikit.uinfo.UserInfoHelper;
import com.netease.nim.uikit.uinfo.UserInfoObservable;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomNotification;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.team.model.TeamMember;
import com.netease.nimlib.sdk.uinfo.UserService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by huyg on 2017/7/21.
 */

public class MessageManageActivity extends BaseActivity {

    @BindView(R.id.message_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.title_left)
    LinearLayout mLeft;
    @BindView(R.id.title_middle_text)
    TextView mTitle;



    @OnClick(R.id.title_left)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
        }
    }


    private Intent mIntent;
    private List<RecentContactsModel> models;
    private UserInfoObservable.UserInfoObserver userInfoObserver;
    private List<RecentContact> items;
    private List<String> list_account = new ArrayList<>();
    private MessageAdapter mAdapter;
    private LinearLayoutManager mManager;

    @Override
    public int setViewLayout() {
        return R.layout.activity_message_manage;
    }

    @Override
    public void initView() {
        mTitle.setText("消息中心");
        initAdapter();
    }

    private void initAdapter() {
        mAdapter = new MessageAdapter(this);
        mManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mManager);
        mRecycler.setNestedScrollingEnabled(false);
        mRecycler.setAdapter(mAdapter);
    }



    @Override
    public void doAction() {
        super.doAction();
        initIntent();
    }

    private void getUnReadCount() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", HiCache.getInstance().getLoginUserInfo().getUserId());
        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.unreadEachCount, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                if (!TextUtils.isEmpty(result)) {
                    UnReadModel unReadModel = JSON.parseObject(result, UnReadModel.class);
                    mAdapter.setUnReadModel(unReadModel);
                }
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getRecentContacts();
        getUnReadCount();
        initSystemPoint();
    }

    private void initSystemPoint() {
        List<RecentContactsModel> recentContactsModels = HiCache.getInstance().getSystemNotification(3);
        mAdapter.setRecentContactsModels(recentContactsModels);

    }

    @Override
    protected void onStart() {
        super.onStart();
        registerObservers(true);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    private void initIntent() {
        mIntent = new Intent();
        mIntent.setClass(this, NotificationActivity.class);
    }

    @Subscribe
    public void onEvent(MessageEvent event) {
        RecentContactsModel recentContactsModel=event.recentContactsModel;
        switch (recentContactsModel.getChatType()) {
            case ChatType.ACTION://官方活动
            case ChatType.SYSTEMINFO://系统消息
            case ChatType.SUBSCIBR://订阅号
                HiCache.getInstance().exchangeSystemNotificationUnreadNum(recentContactsModel.getChatType());
                OfficialChatActivity.start(MessageManageActivity.this, recentContactsModel.getChatType(), recentContactsModel.getAccountId());
                break;

            case ChatType.P2P://P2P
                SessionHelper.startP2PSession(MessageManageActivity.this, recentContactsModel.getAccountId());
                break;

            case ChatType.TEAM://Team

                SessionHelper.startTeamSession(MessageManageActivity.this, recentContactsModel.getAccountId(), false);
                break;

        }
    }

    @Subscribe
    public void onEvent(MessageClickEvent event){
        int type = event.type;
        switch (type){
            case Const.NOTICE_TYPE_MIME:
                mIntent.putExtra("type",Const.NOTICE_TYPE_MIME);
                startActivity(mIntent);
                break;
            case Const.NOTICE_TYPE_COMMENT:
                mIntent.putExtra("type",Const.NOTICE_TYPE_COMMENT);
                startActivity(mIntent);
                break;
            case Const.NOTICE_TYPE_PRAISE:
                mIntent.putExtra("type",Const.NOTICE_TYPE_PRAISE);
                startActivity(mIntent);
                break;
            case Const.NOTICE_TYPE_SYSTEM:
                HiCache.getInstance().exchangeSystemNotificationUnreadNum(3);
                OfficialChatActivity.start(MessageManageActivity.this, ChatType.SYSTEMINFO, "");
                break;
        }
    }

    @Subscribe
    public void onEvent(RecentContactDelete event) {
        deleteRecentContact(event.position, event.model);
    }


    /**
     * 获取最近会话
     */
    private void getRecentContacts() {
        models = new ArrayList<>();
        NIMClient.getService(MsgService.class).queryRecentContacts().setCallback(new RequestCallbackWrapper<List<RecentContact>>() {

            @Override
            public void onResult(int code, List<RecentContact> recents, Throwable exception) {

                if (code != ResponseCode.RES_SUCCESS || recents == null) {
                    return;
                }
                items = recents;
                refreshData();

            }
        });
    }

    /**
     * 删除记录
     */
    private void deleteRecentContact(int position, RecentContactsModel model) {
        // 删除会话，删除后，消息历史被一起删除
        NIMClient.getService(MsgService.class).deleteRecentContact(model.getRecent());
        NIMClient.getService(MsgService.class).clearChattingHistory(model.getRecent().getContactId(), model.getRecent().getSessionType());
        models.remove(model);

        if (model.getRecent().getUnreadCount() > 0) {
            refreshMessages(true);
        } else {
            mAdapter.notifyItemRemoved(position);
        }
    }

    /**
     * ********************** 收消息，处理状态变化 ************************
     */
    private void registerObservers(boolean register) {
        MsgServiceObserve service = NIMClient.getService(MsgServiceObserve.class);
        service.observeRecentContact(messageObserver, register);
        service.observeMsgStatus(statusObserver, register);
        service.observeRecentContactDeleted(deleteObserver, register);
        service.observeCustomNotification(customObserver, register);

        registerTeamUpdateObserver(register);
        registerTeamMemberUpdateObserver(register);
        FriendDataCache.getInstance().registerFriendDataChangedObserver(friendDataChangedObserver, register);

        if (register) {
            registerUserInfoObserver();
        } else {
            unregisterUserInfoObserver();
        }
    }


    FriendDataCache.FriendDataChangedObserver friendDataChangedObserver = new FriendDataCache.FriendDataChangedObserver() {
        @Override
        public void onAddedOrUpdatedFriends(List<String> accounts) {
            refreshMessages(false);
        }

        @Override
        public void onDeletedFriends(List<String> accounts) {
            refreshMessages(false);
        }

        @Override
        public void onAddUserToBlackList(List<String> account) {
            refreshMessages(false);
        }

        @Override
        public void onRemoveUserFromBlackList(List<String> account) {
            refreshMessages(false);
        }
    };


    /**
     * 注册群信息&群成员更新监听
     */
    private void registerTeamUpdateObserver(boolean register) {
        if (register) {
            TeamDataCache.getInstance().registerTeamDataChangedObserver(teamDataChangedObserver);
        } else {
            TeamDataCache.getInstance().unregisterTeamDataChangedObserver(teamDataChangedObserver);
        }
    }

    private void registerTeamMemberUpdateObserver(boolean register) {
        if (register) {
            TeamDataCache.getInstance().registerTeamMemberDataChangedObserver(teamMemberDataChangedObserver);
        } else {
            TeamDataCache.getInstance().unregisterTeamMemberDataChangedObserver(teamMemberDataChangedObserver);
        }
    }


    Observer<List<RecentContact>> messageObserver = new Observer<List<RecentContact>>() {
        @Override
        public void onEvent(List<RecentContact> messages) {
            int index;
            for (RecentContact msg : messages) {
                index = -1;
                for (int i = 0; i < models.size(); i++) {
//                    if (msg.getContactId().equals(models.get(i).getAccountId()) && msg.getSessionType() == (models.get(i).getRecent().getSessionType())) {
                    if (msg.getContactId().equals(models.get(i).getAccountId())) {
                        index = i;
                        break;
                    }
                }

                if (index >= 0) {
                    models.remove(index);
                }

                models.addAll(exchangeData(msg));
                list_account.add(msg.getContactId());
            }

            refreshMessages(true);

            NIMClient.getService(UserService.class).fetchUserInfo(list_account);

        }
    };


    private List<RecentContactsModel> exchangeData(RecentContact recent) {
        if (recent == null)
            return null;
        List<RecentContact> recents = new ArrayList<>();
        recents.add(recent);
        return exchangeData(recents);
    }


    /**
     * '
     * 转换数据
     *
     * @param recents
     * @return
     */
    private List<RecentContactsModel> exchangeData(List<RecentContact> recents) {
        List<RecentContactsModel> exchangeData = new ArrayList<>();
        if (recents != null) {
            for (int i = 0; i < recents.size(); i++) {
                RecentContact recentContact = recents.get(i);
//                if (recentContact.getSessionType() == SessionTypeEnum.P2P) {
                RecentContactsModel model = new RecentContactsModel();
                model.setTime(recentContact.getTime());
                model.setAccountId(recentContact.getContactId());
                model.setChatType(recentContact.getSessionType() == SessionTypeEnum.Team ? ChatType.TEAM : ChatType.P2P);
                if (recentContact.getMsgType() == MsgTypeEnum.custom) {
                    MsgAttachment msgAttachment = recentContact.getAttachment();
                    if (msgAttachment instanceof HomeAttachment) {
                        HomeAttachment attachment = (HomeAttachment) msgAttachment;
                        switch (attachment.getMsgModel().getType()) {
                            case CustomAttachmentType.HomeWork://家庭作业
                                model.setContent("[作业消息]");
                                break;
                            case CustomAttachmentType.HUODONG_G://班级活动
                                model.setContent("[活动消息]");
                                break;
                            case CustomAttachmentType.HUODONG_C://社群活动
                                model.setContent("[活动消息]");
                                break;
                        }
                    } else if (msgAttachment instanceof NoticeAttachment) {
                        model.setContent("[通知消息]");
                    } else if (msgAttachment instanceof GuessAttachment) {
                        GuessAttachment attachment = (GuessAttachment) msgAttachment;
                        GuessAttachment.Guess s = attachment.getValue();
                        model.setContent("[" + s.getDesc() + "]");
                    }
                } else {
                    model.setContent(recentContact.getContent());
                }
                model.setRecent(recentContact);
                model.setUnreadCount(recentContact.getUnreadCount());
                exchangeData.add(model);
            }
//            }
        }
        return exchangeData;
    }

    Observer<CustomNotification> customObserver = new Observer<CustomNotification>() {
        @Override
        public void onEvent(CustomNotification message) {
            // 在这里处理自定义通知。

            HiStudentLog.e("-------云信消息----->" + message.getContent());
            CustomNotificationBean bean = JSON.parseObject(message.getContent(), CustomNotificationBean.class);

            if (!HiCache.getInstance().isHaveTheSystemNotification(bean.getBatchNumber())) {
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
            }
            //refreshData();
        }
    };

    Observer<IMMessage> statusObserver = new Observer<IMMessage>() {
        @Override
        public void onEvent(IMMessage message) {
            int index = getItemIndex(message.getUuid());
            if (index >= 0 && index < items.size()) {
                RecentContact item = items.get(index);
                item.setMsgStatus(message.getStatus());
                refreshViewHolderByIndex(index);
            }
        }
    };


    private int getItemIndex(String uuid) {
        if (items == null) return -1;
        for (int i = 0; i < items.size(); i++) {
            RecentContact item = items.get(i);
            if (TextUtils.equals(item.getRecentMessageId(), uuid)) {
                return i;
            }
        }

        return -1;
    }

    protected void refreshViewHolderByIndex(final int index) {

//        try {
//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Object tag = ListViewUtil.getViewHolderByIndex(listView.getRefreshableView(), index);
//                    if (tag instanceof RecentViewHolder) {
//                        RecentViewHolder viewHolder = (RecentViewHolder) tag;
//                        viewHolder.refreshCurrentItem();
//                    }
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    Observer<RecentContact> deleteObserver = new Observer<RecentContact>() {
        @Override
        public void onEvent(RecentContact recentContact) {
            if (items == null) return;
            if (recentContact != null) {
                for (RecentContact item : items) {
                    if (TextUtils.equals(item.getContactId(), recentContact.getContactId())
                            && item.getSessionType() == recentContact.getSessionType()) {
                        items.remove(item);
                        refreshMessages(true);
                        break;
                    }
                }
            } else {
                items.clear();
                refreshMessages(true);
            }
        }
    };

    TeamDataCache.TeamDataChangedObserver teamDataChangedObserver = new TeamDataCache.TeamDataChangedObserver() {

        @Override
        public void onUpdateTeams(List<Team> teams) {
        }

        @Override
        public void onRemoveTeam(Team team) {

        }
    };

    TeamDataCache.TeamMemberDataChangedObserver teamMemberDataChangedObserver = new TeamDataCache.TeamMemberDataChangedObserver() {
        @Override
        public void onUpdateTeamMember(List<TeamMember> members) {
        }

        @Override
        public void onRemoveTeamMember(TeamMember member) {

        }
    };

    private void registerUserInfoObserver() {
        if (userInfoObserver == null) {
            userInfoObserver = new UserInfoObservable.UserInfoObserver() {
                @Override
                public void onUserInfoChanged(List<String> accounts) {
                    refreshMessages(false);
                }
            };
        }

        UserInfoHelper.registerObserver(userInfoObserver);
    }

    private void unregisterUserInfoObserver() {
        if (userInfoObserver != null) {
            UserInfoHelper.unregisterObserver(userInfoObserver);
        }
    }


    /**
     * 当监听器监听到新的数据时，刷新整个数据源
     */
    private void refreshData() {

        models.clear();

        /**
         * 加入P2P会话
         */
        List<RecentContactsModel> contactsModels = exchangeData(items);
        if (contactsModels != null && contactsModels.size() > 0)
            models.addAll(exchangeData(items));

        /**
         * 从数据库中获取官方活动数据
         */
//        List<RecentContactsModel> model_o = HiCache.getInstance().getSystemNotification(ChatType.ACTION);
//        if (model_o != null && model_o.size() > 0) {
//            models.add(model_o.get(model_o.size() - 1));
//        } else {
//
//        }
        RecentContactsModel model = new RecentContactsModel();
        model.setChatType(ChatType.ACTION);
        model.setTitle("官方活动");
        models.add(model);
        /**
         * 从数据库中获取系统消息数据
         */
//        List<RecentContactsModel> model_s = HiCache.getInstance().getSystemNotification(ChatType.SYSTEMINFO);
//        if (model_s != null && model_s.size() > 0) {
//            models.add(model_s.get(model_s.size() - 1));
//        } else {
//            RecentContactsModel model = new RecentContactsModel();
//            model.setChatType(ChatType.SYSTEMINFO);
//            models.add(model);
//        }

//        /**
//         * 从数据库中获取订阅号数据
//         */
//        List<RecentContactsModel> model_d = HiCache.getInstance().getSystemNotification(ChatType.SUBSCIBR);
//        if (model_d != null && model_d.size() > 0) {
//            models.add(model_d.get(model_d.size() - 1));
//        } else {
//            RecentContactsModel model = new RecentContactsModel();
//            model.setChatType(ChatType.SUBSCIBR);
//            models.add(model);
//        }

        refreshMessages(true);
    }


    /**
     * 更新未读数量
     *
     * @param unreadChanged
     */
    private void refreshMessages(boolean unreadChanged) {
        sortRecentContacts(models);
        mAdapter.setMessages(models);
        if (unreadChanged) {

            // 方式一：累加每个最近联系人的未读（快）
            int unreadNum = 0;
            for (RecentContactsModel m : models) {
                unreadNum += m.getUnreadCount();
            }

            if (haveNesMsg != null)
                haveNesMsg.showRedPoint(unreadNum);

//            // 方式二：直接从SDK读取（相对慢）
//            int unreadNum = NIMClient.getService(MsgService.myclass).getTotalUnreadCount();
//
//            if (callback != null) {
//                callback.onUnreadCountChange(unreadNum);
//            }
        }
    }


    /**
     * **************************** 排序 ***********************************
     */
    private void sortRecentContacts(List<RecentContactsModel> list) {
        if (list.size() == 0) {
            return;
        }
        Collections.sort(list, comp);
    }


    private static Comparator<RecentContactsModel> comp = new Comparator<RecentContactsModel>() {

        @Override
        public int compare(RecentContactsModel o1, RecentContactsModel o2) {
            // 先比较置顶tag
            long i1 = HiCache.getInstance().getRecentContactsTopFlag(o1.getAccountId());
            long i2 = HiCache.getInstance().getRecentContactsTopFlag(o2.getAccountId());
            long i = i1 - i2;
            if (i > 0) {
                return -1;
            } else if (i < 0) {
                return 1;
            } else {
                long time = o1.getTime() - o2.getTime();
                return time == 0 ? 0 : (time > 0 ? -1 : 1);
            }
        }
    };


    private MyRecentContactsFragment.HaveNesMsg haveNesMsg;

    public void setOnlisener(MyRecentContactsFragment.HaveNesMsg haveNesMsg) {
        this.haveNesMsg = haveNesMsg;
    }

    public interface HaveNesMsg {
        void showRedPoint(int num);
    }
}
