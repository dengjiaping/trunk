package com.histudent.jwsoft.histudent.body.message.uikit.session.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.HiStudentLog;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.model.CustomNotificationBean;
import com.histudent.jwsoft.histudent.body.message.uikit.session.extension.CustomAttachmentType;
import com.histudent.jwsoft.histudent.body.message.uikit.session.extension.GuessAttachment;
import com.histudent.jwsoft.histudent.body.message.uikit.session.extension.HomeAttachment;
import com.histudent.jwsoft.histudent.body.message.uikit.session.extension.NoticeAttachment;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.fragment.BaseFragment;
import com.histudent.jwsoft.histudent.commen.listener.ParserCallBack;
import com.histudent.jwsoft.histudent.comment2.utils.TimeUtils;
import com.netease.nim.uikit.cache.FriendDataCache;
import com.netease.nim.uikit.cache.TeamDataCache;
import com.netease.nim.uikit.common.ui.listview.ListViewUtil;
import com.netease.nim.uikit.recent.viewholder.RecentViewHolder;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/8/26.
 */
public class MyRecentContactsFragment extends BaseFragment implements ParserCallBack {

    private View view;
    private PullToRefreshListView listView;
    private MyRecentContactAdapter myAdapter;
    private List<RecentContactsModel> models;
    private List<RecentContact> items;
    private UserInfoObservable.UserInfoObserver userInfoObserver;
    private Handler handler;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        registerObservers(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = LayoutInflater.from(getActivity()).inflate(R.layout.recentcontact_list_item, null);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getRecentContacts();
    }

    @Override
    public void initView() {

        super.initView();
        handler = new Handler();
        listView = (PullToRefreshListView) view.findViewById(R.id.lvMessages);
        models = new ArrayList<>();
        myAdapter = new MyRecentContactAdapter(getActivity(), models, this, handler);
        listView.setAdapter(myAdapter);

    }

    @Override
    public void initData() {
        super.initData();

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                getRecentContacts();
            }
        });

    }

    /**
     * 删除记录
     */
    private void deleteRecentContact(int dex) {
        // 删除会话，删除后，消息历史被一起删除
        RecentContactsModel model = models.get(dex);
        NIMClient.getService(MsgService.class).deleteRecentContact(model.getRecent());
        NIMClient.getService(MsgService.class).clearChattingHistory(model.getRecent().getContactId(), model.getRecent().getSessionType());
        models.remove(model);

        if (model.getRecent().getUnreadCount() > 0) {
            refreshMessages(true);
        } else {
            myAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 更新未读数量
     *
     * @param unreadChanged
     */
    private void refreshMessages(boolean unreadChanged) {
        sortRecentContacts(models);
        myAdapter.notifyDataSetChanged();

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
        List<RecentContactsModel> model_o = HiCache.getInstance().getSystemNotification(ChatType.ACTION);
        if (model_o != null && model_o.size() > 0) {
            models.add(model_o.get(model_o.size() - 1));
        } else {
            RecentContactsModel model = new RecentContactsModel();
            model.setChatType(ChatType.ACTION);
            models.add(model);
        }

        /**
         * 从数据库中获取系统消息数据
         */
        List<RecentContactsModel> model_s = HiCache.getInstance().getSystemNotification(ChatType.SYSTEMINFO);
        if (model_s != null && model_s.size() > 0) {
            models.add(model_s.get(model_s.size() - 1));
        } else {
            RecentContactsModel model = new RecentContactsModel();
            model.setChatType(ChatType.SYSTEMINFO);
            models.add(model);
        }

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
     * 获取最近会话
     */
    private void getRecentContacts() {
        NIMClient.getService(MsgService.class).queryRecentContacts().setCallback(new RequestCallbackWrapper<List<RecentContact>>() {

            @Override
            public void onResult(int code, List<RecentContact> recents, Throwable exception) {

                if (code != ResponseCode.RES_SUCCESS || recents == null) {
                    return;
                }

                listView.onRefreshComplete();

                items = recents;

                refreshData();

            }
        });
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
                        switch (attachment.getMsgModel().getType()){
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


    private List<RecentContactsModel> exchangeData(RecentContact recent) {
        if (recent == null)
            return null;
        List<RecentContact> recents = new ArrayList<>();
        recents.add(recent);
        return exchangeData(recents);
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

    private List<String> list_account = new ArrayList<>();

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
            refreshData();
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
            myAdapter.notifyDataSetChanged();
        }

        @Override
        public void onRemoveTeam(Team team) {

        }
    };

    TeamDataCache.TeamMemberDataChangedObserver teamMemberDataChangedObserver = new TeamDataCache.TeamMemberDataChangedObserver() {
        @Override
        public void onUpdateTeamMember(List<TeamMember> members) {
            myAdapter.notifyDataSetChanged();
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

        try {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Object tag = ListViewUtil.getViewHolderByIndex(listView.getRefreshableView(), index);
                    if (tag instanceof RecentViewHolder) {
                        RecentViewHolder viewHolder = (RecentViewHolder) tag;
                        viewHolder.refreshCurrentItem();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parser(String result) {
        deleteRecentContact(Integer.parseInt(result));
    }

    private HaveNesMsg haveNesMsg;

    public void setOnlisener(HaveNesMsg haveNesMsg) {
        this.haveNesMsg = haveNesMsg;
    }

    public interface HaveNesMsg {
        void showRedPoint(int num);
    }

}
