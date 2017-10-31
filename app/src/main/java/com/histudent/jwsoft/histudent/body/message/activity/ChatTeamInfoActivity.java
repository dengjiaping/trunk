package com.histudent.jwsoft.histudent.body.message.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.histudent.jwsoft.histudent.HiStudentLog;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.bean.MemberTypeBean;
import com.histudent.jwsoft.histudent.body.find.activity.GroupCenterActivity;
import com.histudent.jwsoft.histudent.body.message.adapter.TeamAdapter;
import com.histudent.jwsoft.histudent.body.message.uikit.session.activity.MessageHistoryActivity;
import com.histudent.jwsoft.histudent.body.message.uikit.session.search.SearchMessageActivity;
import com.histudent.jwsoft.histudent.body.myclass.activity.ClassCircleActivity;
import com.histudent.jwsoft.histudent.body.myclass.activity.NoticeDetailActivity;
import com.histudent.jwsoft.histudent.body.myclass.bean.NoticesBean;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.DialogButtonListener;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.model.SaveData;
import com.histudent.jwsoft.histudent.commen.model.SortModel;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.view.MyGridView;
import com.histudent.jwsoft.histudent.constant.ParamKeys;
import com.histudent.jwsoft.histudent.entity.ExtServer;
import com.histudent.jwsoft.histudent.info.persioninfo.activity.PersonCenterActivity;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.netease.nimlib.sdk.team.TeamService;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.team.model.TeamMember;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2016/6/12.
 * 班级祥情界面
 */
public class ChatTeamInfoActivity extends BaseActivity {

    private TextView chatTeam_text_00, chatTeam_text_01, title_middle_text, chatTeam_QRcode;
    private MyGridView gridView;
    private CheckBox checkBox_00;
    private final int GETUSERINFO_SUCCED = 0;
    private final int GETUSERINFO_FAILD = 1;
    private final int GETUSERINFO_EXCEPTION = 2;
    private final int GETEACHUSERINFO_EXCEPTION = 3;
    private List<TeamMember> datas;
    private TeamAdapter adapter;
    private Team team;
    private String TAG = "ChatTeamInfoActivity";
    private static int positin;//删除的item的位置。
    private boolean flag = false;
    private String classId;
    private TextView chatTeam_goRight, chatTeam_name;
    private RelativeLayout chatTeam_goRight_01;
    private NoticesBean bean;
    private boolean isGroup;
    private List<String> mTeacherIdInMemberList;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case GETUSERINFO_SUCCED:

                    if (msg.arg1 == 0) {

                        team = (Team) msg.obj;
                        checkBox_00.setChecked(team.mute());
//                        checkBox_01.setChecked(HiCache.getIntence().getRecentContactsTopFlag(team.getId()) > 0 ? true : false);
                        String name = team.getName();
                        if (name.endsWith("家校群") || name.endsWith("班")) {
                            chatTeam_goRight.setText("班级名称");
                            chatTeam_name.setText("班主任");
                            isGroup = false;
                        } else {
                            chatTeam_goRight.setText("社群名称");
                            chatTeam_name.setText("群主");
                            isGroup = true;
                        }
                        title_middle_text.setText(name + "(" + team.getMemberCount() + ")");
                        if (name.length() >= 3) {
                            chatTeam_text_00.setText(isGroup ? name : name.substring(0, name.length() - 3));
                        } else {
                            chatTeam_text_00.setText(name);
                        }
                        NimUserInfo user = NIMClient.getService(UserService.class).getUserInfo(team.getCreator());
                        chatTeam_QRcode.setText(user.getName());
                        checkBox_00.setChecked(team.mute());

                        getTeamPersionInfo();

                        getNewClassNotice(team);

                    }
                    break;

                case GETEACHUSERINFO_EXCEPTION:

                    List<TeamMember> teamMembers = (List<TeamMember>) msg.obj;
                    if (teamMembers != null) {
                        datas.addAll(teamMembers);
                        adapter.setTeacherUserId(mTeacherIdInMemberList);
                        adapter.notifyDataSetChanged();
                    }

                    break;

                case GETUSERINFO_FAILD:

                    Log.i(TAG, "---->获取群组资料失败：" + msg.arg1);

                    break;

                case GETUSERINFO_EXCEPTION:

                    Log.i(TAG, "---->获取群组资料异常：" + msg.obj.toString());

                    break;

                case 4:

                    HiStudentLog.e("---->删除会话:" + team.getId());
                    deleteRecentContact(team.getId());

                    HiStudentLog.e("---->销毁activity!");
                    finish();

                    break;

                case 5:
                    datas.remove(ChatTeamInfoActivity.positin);
                    title_middle_text.setText("群聊信息(" + (datas.size() - 2) + ")");
                    adapter.notifyDataSetChanged();

                    break;

            }

        }
    };

    @Override
    public int setViewLayout() {
        return R.layout.chatteaminfo_activity;
    }

    @Override
    public void initView() {
        SystemUtil.addActivityToList(this);
        datas = new ArrayList<>();

        adapter = new TeamAdapter(this, datas);

        chatTeam_goRight_01 = (RelativeLayout) findViewById(R.id.chatTeam_goRight_01);
        chatTeam_goRight = (TextView) findViewById(R.id.chatTeam_goRight);
        chatTeam_name = (TextView) findViewById(R.id.chatTeam_name);
        title_middle_text = (TextView) findViewById(R.id.title_middle_text);
        chatTeam_QRcode = (TextView) findViewById(R.id.chatTeam_QRcode);
        chatTeam_text_00 = (TextView) findViewById(R.id.chatTeam_text_00);
        chatTeam_text_01 = (TextView) findViewById(R.id.chatTeam_text_01);
        gridView = (MyGridView) findViewById(R.id.chatTeam_gridView);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridView.setAdapter(adapter);


        chatTeam_goRight_01.setVisibility(isGroup ? View.GONE : View.VISIBLE);

        checkBox_00 = (CheckBox) findViewById(R.id.chatTeam_checkBox_00);

        //消息免打扰
        checkBox_00.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (team != null)
                    NIMClient.getService(TeamService.class).muteTeam(team.getId(), isChecked);

            }
        });

        //置顶聊天
//        checkBox_01.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                if (team != null) {
//                    if (isChecked) {
//                        HiCache.getIntence().setRecentContactsTopFlag(team.getId());
//                    } else {
//                        HiCache.getIntence().removeRecentContactsTopFlag(team.getId());
//                    }
//                }
//            }
//        });

        //GridView监听
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击了头像
                String account = datas.get(position).getAccount();
                if (!TextUtils.isEmpty(account)) {
                    String uuid = account.replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5");
                    PersonCenterActivity.start(ChatTeamInfoActivity.this, uuid);
                }
            }
        });

        registNotifiction();

        //跳转二维码
//        relative_qrcode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                QRCodeActivity.start(ChatTeamInfoActivity.this, classId, 2, 1);
//            }
//        });

    }

    @Override
    public void doAction() {
        super.doAction();
        getTeamInfo();
    }

    private void showReduceImage(boolean flag) {

        if (flag) {
            for (ImageView imageView : TeamAdapter.image_list) {
                if (imageView.getVisibility() != View.VISIBLE) {
                    imageView.setVisibility(View.VISIBLE);
                }
            }
        } else {
            for (ImageView imageView : TeamAdapter.image_list) {
                if (imageView.getVisibility() == View.VISIBLE) {
                    imageView.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {

            case R.id.title_left:

                finish();

                break;

            case R.id.chatTeam_goRight_00://班级名称（群名）
                ExtServer extServer = JSON.parseObject(team.getExtServer(), ExtServer.class);
                if (extServer != null) {
                    if (isGroup) {
                        GroupCenterActivity.start(this, extServer.getClassId());
                    } else {
                        ClassCircleActivity.start(this, extServer.getClassId());
                    }
                }


                break;

            case R.id.relative_qrcode://班主任（群主）
                String uuid = team.getCreator().replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5");
                PersonCenterActivity.start(this, uuid);
                break;

            case R.id.chatTeam_goRight_01://最新公告

                if (bean != null && bean.getItems() != null && bean.getItems().size() > 0) {
                    NoticeDetailActivity.start(this, bean.getItems().get(0).getNotifyId(),bean.getItems().get(0).isSend(),false);
                } else {
                    ReminderHelper.getIntentce().ToastShow(this, "未接收到通知");
                }
                break;

            case R.id.chatTeam_goRight_02://聊天文件

                MessageHistoryActivity.start(this, team.getId(), SessionTypeEnum.Team);

                break;

            case R.id.chatTeam_goRight_03://查找聊天记录

                SearchMessageActivity.start(this, team.getId(), SessionTypeEnum.Team);

                break;

            case R.id.classInfo_jubao://清除聊天记录

                ReminderHelper.getIntentce().showDialog(this, null, "是否清除聊天记录？", "取消", new DialogButtonListener() {
                    @Override
                    public void setOnDialogButtonListener() {

                    }
                }, "确定", new DialogButtonListener() {
                    @Override
                    public void setOnDialogButtonListener() {
                        NIMClient.getService(MsgService.class).clearChattingHistory(team.getId(), SessionTypeEnum.Team);
                    }
                });

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (SaveData.list_data != null) {
            if (requestCode == 200) {
                List<String> list = new ArrayList<>();
                for (SortModel sortModel : SaveData.list_data) {
                    if (sortModel.isFlag()) {
                        list.add(sortModel.getUserId());
                    }
                }
                addTeam(team.getId(), list);
            } else if (requestCode == 300) {

            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegistNotifiction();
    }

    //===================================================================================================

    /**
     * 获取班级最新通知
     */
    private void getNewClassNotice(Team team) {

        ExtServer extServer = JSON.parseObject(team.getExtServer(), ExtServer.class);
        Map<String, Object> map = new TreeMap<>();
        map.put("classId", extServer.getClassId());
        map.put("pageIndex", 0);
        map.put("pageSize", 1);

        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.notices_list_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                bean = JSON.parseObject(result, NoticesBean.class);
                if (bean != null && bean.getItems().size() > 0) {
                    chatTeam_text_01.setText(bean.getItems().get(0).getContent());

                }
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });

    }

    /**
     * 拉人入群
     */
    private void addTeam(String teamId, List<String> accounts) {
        NIMClient.getService(TeamService.class).addMembers(teamId, accounts)
                .setCallback(new RequestCallback<Void>() {
                    @Override
                    public void onSuccess(Void param) {
                        // 返回onSuccess，表示拉人不需要对方同意，且对方已经入群成功了
                        datas.clear();
                        getTeamInfo();
                    }

                    @Override
                    public void onFailed(int code) {
                        // 返回onFailed，并且返回码为810，表示发出邀请成功了，但是还需要对方同意
                    }

                    @Override
                    public void onException(Throwable exception) {

                    }
                });
    }

    /**
     * 获取群组成员资料
     */
    private void getTeamPersionInfo() {
        // 该操作有可能只是从本地数据库读取缓存数据，也有可能会从服务器同步新的数据，因此耗时可能会比较长。
        Log.i(TAG, "---->获取群组资料：" + team.getId());
        NIMClient.getService(TeamService.class).queryMemberList(team.getId())
                .setCallback(new RequestCallback<List<TeamMember>>() {
                    @Override
                    public void onSuccess(List<TeamMember> teamMembers) {
                        ExtServer extServer = JSON.parseObject(team.getExtServer(), ExtServer.class);
                        HiStudentLog.e("---->获取群组成员资料成功：" + teamMembers.size());

                        HashMap<String, Object> hashMap = new HashMap<>();
                        if (isGroup) {
                            //社群
                            hashMap.put(ParamKeys.OBJECT_ID, extServer.getMainId());
                            hashMap.put(ParamKeys.OBJECT_TYPE, 1);
                        } else {
                            //班级
                            hashMap.put(ParamKeys.OBJECT_ID, extServer.getMainId());
                            hashMap.put(ParamKeys.OBJECT_TYPE, 2);
                        }

                        HiStudentHttpUtils.postDataByOKHttp(ChatTeamInfoActivity.this, hashMap, HistudentUrl.getMemberList, new HttpRequestCallBack() {
                            @Override
                            public void onSuccess(String result) {
                                List<MemberTypeBean> memberTypeList = JSONObject.parseArray(result, MemberTypeBean.class);
                                if (mTeacherIdInMemberList == null) {
                                    mTeacherIdInMemberList = new ArrayList<>();
                                } else {
                                    mTeacherIdInMemberList.clear();
                                }
                                String userTeacherId;
                                for (MemberTypeBean memberTypeBean : memberTypeList) {
                                    if (memberTypeBean.getUserType() == 3) {
                                        userTeacherId = memberTypeBean.getUserId().replace("-", "");
                                        mTeacherIdInMemberList.add(userTeacherId);
                                    }
                                }

                                Message message = handler.obtainMessage();
                                message.what = GETEACHUSERINFO_EXCEPTION;
                                message.obj = teamMembers;

                                ArrayList<String> strings = new ArrayList<>();
                                for (TeamMember teamMember : teamMembers) {
                                    strings.add(teamMember.getAccount());
                                }
                                handler.sendMessage(message);
                            }

                            @Override
                            public void onFailure(String errorMsg) {

                            }
                        }, LoadingType.NONE);

                    }

                    @Override
                    public void onFailed(int i) {

                        HiStudentLog.e("---->获取群组成员资料失败：" + i);

                    }

                    @Override
                    public void onException(Throwable throwable) {

                        HiStudentLog.e("---->获取群组成员资料异常：" + throwable.toString());

                    }
                });
    }

    /**
     * 获取群组资料
     */
    private void getTeamInfo() {
        NIMClient.getService(TeamService.class).searchTeam(getIntent().getStringExtra("teamId"))
                .setCallback(new RequestCallback<Team>() {
                    @Override
                    public void onSuccess(Team team) {

                        Message message = handler.obtainMessage();
                        message.what = GETUSERINFO_SUCCED;
                        message.obj = team;
                        handler.sendMessage(message);

                    }

                    @Override
                    public void onFailed(int i) {

                        Message message = handler.obtainMessage();
                        message.what = GETUSERINFO_FAILD;
                        message.arg1 = i;
                        handler.sendMessage(message);

                    }

                    @Override
                    public void onException(Throwable throwable) {

                        Message message = handler.obtainMessage();
                        message.what = GETUSERINFO_EXCEPTION;
                        message.obj = throwable;
                        handler.sendMessage(message);

                    }
                });
    }

    /**
     * 退出群组
     */
    private void quitTeam() {
        NIMClient.getService(TeamService.class).quitTeam(team.getId())
                .setCallback(new RequestCallback<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        HiStudentLog.e("---->退群成功!");
                        Message message = handler.obtainMessage();
                        message.what = 4;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onFailed(int i) {
                        HiStudentLog.e("---->退群失败：" + i);

                    }

                    @Override
                    public void onException(Throwable throwable) {

                        HiStudentLog.e("---->退群异常：" + throwable);
                    }
                });
    }

    /**
     * 踢出群组
     */
    private void removeMember(String account) {
        NIMClient.getService(TeamService.class).removeMember(team.getId(), account)
                .setCallback(new RequestCallback<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        handler.sendEmptyMessage(5);

                    }

                    @Override
                    public void onFailed(int i) {

                    }

                    @Override
                    public void onException(Throwable throwable) {

                    }
                });
    }

    /**
     * 删除最近会话
     */
    private void deleteRecentContact(String recentContact) {
        // 删除与某个聊天对象的全部消息记录
        NIMClient.getService(MsgService.class).clearChattingHistory(recentContact, SessionTypeEnum.Team);
    }

    private Observer<RecentContact> observer = new Observer<RecentContact>() {
        @Override
        public void onEvent(RecentContact recentContact) {
            HiStudentLog.e("---->退群结果：" + recentContact);
        }
    };

    /**
     * 注册退群通知
     */
    private void registNotifiction() {
        NIMClient.getService(MsgServiceObserve.class).observeRecentContactDeleted(observer, true);
    }

    /**
     * 注销退群通知
     */
    private void unRegistNotifiction() {
        NIMClient.getService(MsgServiceObserve.class).observeRecentContactDeleted(observer, false);
    }

}
