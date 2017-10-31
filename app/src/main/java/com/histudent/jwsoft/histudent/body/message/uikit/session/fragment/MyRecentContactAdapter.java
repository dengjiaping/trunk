package com.histudent.jwsoft.histudent.body.message.uikit.session.fragment;

import android.app.Activity;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.HiStudentLog;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.activity.OfficialChatActivity;
import com.histudent.jwsoft.histudent.body.message.uikit.session.SessionHelper;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.listener.ParserCallBack;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.netease.nim.uikit.cache.NimUserInfoCache;
import com.netease.nim.uikit.common.util.sys.TimeUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.team.TeamService;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/8/26.
 */
public class MyRecentContactAdapter extends BaseAdapter {

    private Activity activity;
    private List<RecentContactsModel> recentContactsModels;
    private float x_down, x_up;
    private boolean flag = false;
    private ParserCallBack callBack;
    private Handler handler;

    public MyRecentContactAdapter(Activity activity, List<RecentContactsModel> recentContactsModels, ParserCallBack callBack, Handler handler) {
        this.activity = activity;
        this.recentContactsModels = recentContactsModels;
        this.callBack = callBack;
        this.handler = handler;
    }

    @Override
    public int getCount() {
        return recentContactsModels.size();
    }

    @Override
    public Object getItem(int i) {
        return recentContactsModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(activity).inflate(R.layout.offical_action_item, null);
            holder.logo = (HiStudentHeadImageView) view.findViewById(R.id.image);
            holder.title = (TextView) view.findViewById(R.id.title);
            holder.content = (TextView) view.findViewById(R.id.content);
//            holder.layout = (LinearLayout) view.findViewById(R.id.layout_recentcontact);
            holder.red_point = (TextView) view.findViewById(R.id.red_point);
            holder.red_point_num = (TextView) view.findViewById(R.id.red_point_num);
            holder.time = (TextView) view.findViewById(R.id.time);
//            holder.button_null = (Button) view.findViewById(R.id.button_null);
            holder.button_delet = (Button) view.findViewById(R.id.button_delet);
            holder.scrollView = (HorizontalScrollView) view.findViewById(R.id.scrollView);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        RecentContactsModel model = recentContactsModels.get(i);

        showLogo(holder, model);
        setWidth(holder);
        setLisner(holder, model, i);

        return view;
    }

    class ViewHolder {
        HiStudentHeadImageView logo;
        TextView title, content, red_point, red_point_num, time;
        LinearLayout layout;
        Button button_null, button_delet;
        HorizontalScrollView scrollView;
    }

    /**
     * 重新设置可见部分的宽度
     */
    private void setWidth(ViewHolder holder) {

        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) holder.button_null.getLayoutParams();
        linearParams.width = SystemUtil.getScreenWind();
        holder.button_null.setLayoutParams(linearParams);

    }

    /**
     * 设置控件滚动监听
     */
    private void setScrollListener(final ViewHolder holderMember) {

        holderMember.scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_DOWN:

                        x_down = motionEvent.getX();

                        break;

                    case MotionEvent.ACTION_UP:

                        x_up = motionEvent.getX();

                        HiStudentLog.e("---->移动   " + x_down + "  " + x_up);

                        if (x_down > x_up) {//左移

                            if ((x_down - x_up) > 15) {

                                HiStudentLog.e("---->左移   " + (x_down - x_up));

                                scorllView(holderMember.scrollView, false);
                                flag = true;
                            } else {
                                scorllView(holderMember.scrollView, true);
                            }
                        } else {//右移
                            if ((x_up - x_down) > 15) {
                                HiStudentLog.e("---->右移   " + (x_up - x_down));
                                scorllView(holderMember.scrollView, true);
                            } else {
                                scorllView(holderMember.scrollView, false);
                            }

                        }

                        break;
                }
                return false;
            }
        });

    }

    private void setLisner(final ViewHolder holderMember, final RecentContactsModel model, final int psition) {
        holderMember.button_null.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (model.getChatType()) {
                    case ChatType.ACTION://官方活动
                    case ChatType.SYSTEMINFO://系统消息
                    case ChatType.SUBSCIBR://订阅号
                        HiCache.getInstance().exchangeSystemNotificationUnreadNum(model.getChatType());
                        scorllView(holderMember.scrollView, true);
                        OfficialChatActivity.start(activity, model.getChatType(),model.getAccountId());
                        break;

                    case ChatType.P2P://P2P
                        scorllView(holderMember.scrollView, true);
                        SessionHelper.startP2PSession(activity, model.getAccountId());
                        break;

                    case ChatType.TEAM://Team
                        scorllView(holderMember.scrollView, true);
                        SessionHelper.startTeamSession(activity, model.getAccountId(),false);
                        break;

                }
            }
        });

        holderMember.button_delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scorllView(holderMember.scrollView, true);
                callBack.parser(psition + "");
            }
        });
    }


    private void scorllView(final HorizontalScrollView view, final boolean isLeft) {

        if (handler != null)
            handler.post(new Runnable() {
                @Override
                public void run() {
                    view.fullScroll(isLeft ? ScrollView.FOCUS_LEFT : ScrollView.FOCUS_RIGHT);
                }
            });
    }

    private void showLogo(final ViewHolder holder, final RecentContactsModel contactsModel) {

        switch (contactsModel.getChatType()) {


            case ChatType.SUBSCIBR://订阅号
                holder.button_delet.setVisibility(View.GONE);
                holder.logo.setImageResource(R.mipmap.subscibr);
                holder.title.setText("订阅号");
                holder.red_point.setVisibility(contactsModel.getIsRead() == 1 ? View.VISIBLE : View.GONE);
                break;

            case ChatType.ACTION://官方活动
                holder.button_delet.setVisibility(View.GONE);
                holder.logo.setImageResource(R.mipmap.offical_action);
                holder.title.setText("官方活动");
                holder.red_point.setVisibility(contactsModel.getIsRead() == 1 ? View.VISIBLE : View.GONE);
                break;

            case ChatType.SYSTEMINFO://系统消息
                holder.button_delet.setVisibility(View.GONE);
                holder.logo.setImageResource(R.mipmap.system_logo);
                holder.title.setText("系统消息");
                holder.red_point.setVisibility(contactsModel.getIsRead() == 1 ? View.VISIBLE : View.GONE);
                break;

            case ChatType.NOTICE://消息通知
                holder.button_delet.setVisibility(View.GONE);
                MyImageLoader.getIntent().displayNetImage(activity,contactsModel.getLogo(),holder.logo);
                holder.title.setText(contactsModel.getTitle());
                holder.red_point.setVisibility(contactsModel.getIsRead() == 1 ? View.VISIBLE : View.GONE);
                break;

            case ChatType.P2P://P2P
                holder.button_delet.setVisibility(View.VISIBLE);
                NimUserInfo userInfo = NIMClient.getService(UserService.class).getUserInfo(contactsModel.getAccountId());
                if (userInfo != null) {
                    holder.logo.loadBuddyAvatar(userInfo.getAvatar());
                    holder.title.setText(userInfo.getName());
                } else {
                    NimUserInfoCache.getInstance().getUserInfoFromRemote(contactsModel.getAccountId(), new RequestCallbackWrapper<NimUserInfo>() {
                        @Override
                        public void onResult(int i, NimUserInfo nimUserInfo, Throwable throwable) {
                            if (i != ResponseCode.RES_SUCCESS || nimUserInfo == null) {
                                return;
                            } else {
                                holder.logo.loadBuddyAvatar(nimUserInfo.getAvatar());
                                holder.title.setText(nimUserInfo.getName());
                            }
                        }
                    });
                }
                if (contactsModel.getUnreadCount() == 0) {
                    holder.red_point_num.setVisibility(View.GONE);
                } else {
                    holder.red_point_num.setVisibility(View.VISIBLE);
                    holder.red_point_num.setText(contactsModel.getUnreadCount() + "");
                }
                holder.red_point.setVisibility(View.GONE);
                break;

            case ChatType.TEAM://Team
                holder.button_delet.setVisibility(View.GONE);
                Team team = NIMClient.getService(TeamService.class).queryTeamBlock(contactsModel.getAccountId());
                if (team != null) {
                    holder.logo.loadBuddyAvatar(team.getIcon());
                    holder.title.setText(team.getName());
                }
                if (contactsModel.getUnreadCount() == 0) {
                    holder.red_point_num.setVisibility(View.GONE);
                } else {
                    holder.red_point_num.setVisibility(View.VISIBLE);
                    holder.red_point_num.setText(contactsModel.getUnreadCount() + "");
                }
                holder.red_point.setVisibility(View.GONE);
                break;

        }
        holder.content.setText(contactsModel.getContent());
        holder.time.setText(contactsModel.getTime() == 0 ? "" : TimeUtil.getTimeShowString(contactsModel.getTime(), true));
    }

}
