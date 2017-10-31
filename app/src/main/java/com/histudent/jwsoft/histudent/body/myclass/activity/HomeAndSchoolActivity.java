package com.histudent.jwsoft.histudent.body.myclass.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.HiStudentLog;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.activity.ClassNoticeActivity;
import com.histudent.jwsoft.histudent.body.message.adapter.TeamAdapter;
import com.histudent.jwsoft.histudent.body.message.model.NoticeModel;
import com.histudent.jwsoft.histudent.body.message.uikit.session.activity.MessageHistoryActivity;
import com.histudent.jwsoft.histudent.body.message.uikit.session.search.SearchMessageActivity;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.QRCodeActivity;
import com.histudent.jwsoft.histudent.commen.enums.ReportType;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.model.SaveData;
import com.histudent.jwsoft.histudent.commen.model.SortModel;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.view.MyGridView;
import com.histudent.jwsoft.histudent.comment2.activity.ReportActivity;
import com.histudent.jwsoft.histudent.info.InfoHelper;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 家校通
 */
public class HomeAndSchoolActivity extends BaseActivity {
    private TextView chatTeam_text_00, chatTeam_text_01, title_middle_text;
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
    private RelativeLayout relative_qrcode;
    private String classId;
    private MyHandler handler;

    @Override
    public int setViewLayout() {
        return R.layout.chatteaminfo_activity;
    }

    @Override
    public void initView() {

        handler = new MyHandler();

        SystemUtil.addActivityToList(this);
        datas = new ArrayList<>();

        adapter = new TeamAdapter(this, datas);

        title_middle_text = (TextView) findViewById(R.id.title_middle_text);
        chatTeam_text_00 = (TextView) findViewById(R.id.chatTeam_text_00);
        chatTeam_text_01 = (TextView) findViewById(R.id.chatTeam_text_01);
        gridView = (MyGridView) findViewById(R.id.chatTeam_gridView);
        relative_qrcode = ((RelativeLayout) findViewById(R.id.relative_qrcode));
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridView.setAdapter(adapter);

        checkBox_00 = (CheckBox) findViewById(R.id.chatTeam_checkBox_00);

        //消息免打扰
        checkBox_00.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (team != null)
                    NIMClient.getService(TeamService.class).muteTeam(team.getId(), isChecked);

            }
        });

        //GridView监听
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int size = datas.size();
                if (position == (size - 2)) {
//                    showReduceImage(false);
//                    //点击了添加
//                    Intent intent = new Intent(ChatTeamInfoActivity.this, SelectContactsActivity.myclass);
//                    datas.remove(datas.size() - 2);
//                    datas.remove(datas.size() - 1);
//                    IMUtils.teamMembers.clear();
//                    IMUtils.teamMembers.addAll(datas);
//                    intent.putExtra("from", 1);
//                    startActivityForResult(intent, 200);

                } else if (position == (size - 1)) {
//                    //点击了减少
//                    if (!flag) {
//                        showReduceImage(true);
//                        flag = true;
//                    } else {
//                        showReduceImage(false);
//                        flag = false;
//                    }

                } else {
                    //点击了头像
                    String account = datas.get(position).getAccount();
                    if (account == null)
                        return;
                    if (TeamAdapter.image_list.get(position).getVisibility() == View.VISIBLE) {
                        TeamAdapter.image_list.remove(position);
                        removeMember(account);
                        HomeAndSchoolActivity.positin = position;
                    } else {
                        String uuid = account.replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5");
//                        PersonalHomepageActivity.start(ChatTeamInfoActivity.this, uuid);
                        InfoHelper.gotoPersonHome(HomeAndSchoolActivity.this, uuid, false);
                    }
                }
            }
        });

        registNotifiction();
        relative_qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                QRCodeActivity.start(HomeAndSchoolActivity.this, classId, 2, 1);
            }
        });

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

            case R.id.chatTeam_goRight_00://班级中心

                InfoHelper.gotoClassHome(this, classId, false);

                break;

            case R.id.chatTeam_goRight_01://最新公告

                ClassNoticeActivity.start(this, classId);

                break;

            case R.id.chatTeam_goRight_02://聊天文件

                MessageHistoryActivity.start(this, team.getId(), SessionTypeEnum.Team);

                break;

            case R.id.chatTeam_goRight_03://查找聊天记录

                SearchMessageActivity.start(this, team.getId(), SessionTypeEnum.Team);

                break;

            case R.id.classInfo_jubao://举报

                ReportActivity.start(this, classId, ReportType.OTHER);

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
     * 获取班级最新动态
     *
     * @param team
     */
    private void getNewClassNotice(final Team team) {

        try {
            JSONObject object = new JSONObject(team.getExtServer());
            classId = object.getString("ClassId");

            Map<String, Object> map = new TreeMap<>();
            map.put("classId", classId);

            HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.getNewestNotice_url, new HttpRequestCallBack() {
                @Override
                public void onSuccess(String result) {

                    NoticeModel models = JSON.parseObject(result, NoticeModel.class);

                    chatTeam_text_01.setText(models.getTitle());

                }

                @Override
                public void onFailure(String msg) {

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

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

                        HiStudentLog.e("---->获取群组成员资料成功：" + teamMembers.size());

                        Message message = handler.obtainMessage();
                        message.what = GETEACHUSERINFO_EXCEPTION;
                        message.obj = teamMembers;
                        handler.sendMessage(message);

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

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GETUSERINFO_SUCCED:

                    if (msg.arg1 == 0) {

                        team = (Team) msg.obj;
                        checkBox_00.setChecked(team.mute());
                        String name = team.getName();
                        title_middle_text.setText(name + "(" + team.getMemberCount() + ")");
                        chatTeam_text_00.setText(name.substring(0, name.length() - 3));
                        checkBox_00.setChecked(team.mute());

                        getTeamPersionInfo();

                        getNewClassNotice(team);

                    }
                    break;

                case GETEACHUSERINFO_EXCEPTION:

                    List<TeamMember> teamMembers = (List<TeamMember>) msg.obj;

                    if (teamMembers != null) {
                        datas.addAll(teamMembers);

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
                    datas.remove(HomeAndSchoolActivity.positin);
                    title_middle_text.setText("群聊信息(" + (datas.size() - 2) + ")");
                    adapter.notifyDataSetChanged();

                    break;

            }
        }
    }

}
