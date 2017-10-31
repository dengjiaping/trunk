package com.histudent.jwsoft.histudent.body.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.uikit.session.fragment.ChatType;
import com.histudent.jwsoft.histudent.body.message.uikit.session.fragment.RecentContactsModel;
import com.histudent.jwsoft.histudent.body.mine.model.UnReadModel;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.constant.Const;
import com.histudent.jwsoft.histudent.entity.MessageClickEvent;
import com.histudent.jwsoft.histudent.entity.MessageEvent;
import com.histudent.jwsoft.histudent.entity.RecentContactDelete;
import com.netease.nim.uikit.cache.NimUserInfoCache;
import com.netease.nim.uikit.common.util.sys.TimeUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.team.TeamService;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by huyg on 2017/7/21.
 */

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int type;
    private Context mContext;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private int viewType;
    private List<RecentContactsModel> mRecentContacts = new ArrayList<>();
    private UnReadModel unReadModel = new UnReadModel();
    private List<RecentContactsModel> recentContactsModels;

    public void setRecentContactsModels(List<RecentContactsModel> recentContactsModels) {
        this.recentContactsModels = recentContactsModels;
        notifyItemChanged(0);
    }


    public void setUnReadModel(UnReadModel unReadModel) {
        this.unReadModel = unReadModel;
        notifyItemChanged(0);
    }

    public MessageAdapter(Context context) {
        this.mContext = context;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setMessages(List<RecentContactsModel> recentContacts) {
        this.mRecentContacts = recentContacts;
        notifyDataSetChanged();
    }

    public void addMessages(List<RecentContactsModel> recentContacts) {
        this.mRecentContacts.addAll(recentContacts);
        notifyDataSetChanged();
    }


    @Override

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_HEADER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_header, parent, false);
                viewHolder = new HeaderViewHolder(view);
                break;
            case TYPE_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offical_action_item, parent, false);
                viewHolder = new ViewHolder(view);
                break;
        }

        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            viewType = TYPE_HEADER;
        } else {
            viewType = TYPE_ITEM;
        }
        return viewType;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MessageAdapter.HeaderViewHolder) {
            MessageAdapter.HeaderViewHolder headerViewHolder = (MessageAdapter.HeaderViewHolder) holder;
            if (unReadModel.getAtUserCount() == 0) {
                headerViewHolder.mMinePoint.setVisibility(View.GONE);
            } else {
                headerViewHolder.mMinePoint.setVisibility(View.VISIBLE);
            }
            if (unReadModel.getCommentCount() == 0) {
                headerViewHolder.mCommentPoint.setVisibility(View.GONE);
            } else {
                headerViewHolder.mCommentPoint.setVisibility(View.VISIBLE);
            }
            if (unReadModel.getPraiseCount() == 0) {
                headerViewHolder.mPraisePoint.setVisibility(View.GONE);
            } else {
                headerViewHolder.mPraisePoint.setVisibility(View.VISIBLE);
            }
            if (recentContactsModels != null && recentContactsModels.size() > 0) {
                for (RecentContactsModel recentContactsModel : recentContactsModels) {
                    if (recentContactsModel.getIsRead() == 1) {
                        headerViewHolder.mSystemPoint.setVisibility(View.VISIBLE);
                        return;
                    } else {
                        headerViewHolder.mSystemPoint.setVisibility(View.GONE);
                    }
                }

            } else {
                headerViewHolder.mSystemPoint.setVisibility(View.GONE);
            }
        } else if (holder instanceof MessageAdapter.ViewHolder) {
            MessageAdapter.ViewHolder viewHolder = (MessageAdapter.ViewHolder) holder;
            RecentContactsModel recentContact = mRecentContacts.get(position - 1);
            if (recentContact != null) {
                showHolder(recentContact, viewHolder);
            }
        }

    }


    private void showHolder(RecentContactsModel contactsModel, MessageAdapter.ViewHolder holder) {

        switch (contactsModel.getChatType()) {
            case ChatType.SUBSCIBR://订阅号
                holder.mButtonDelet.setVisibility(View.GONE);
                holder.mLogo.setImageResource(R.mipmap.subscibr);
                holder.mTitle.setText("订阅号");
                //holder.mPoint.setVisibility(contactsModel.getIsRead() == 1 ? View.VISIBLE : View.GONE);
                break;

            case ChatType.ACTION://官方活动
                holder.mButtonDelet.setVisibility(View.GONE);
                holder.mLogo.setImageResource(R.mipmap.offical_action);
                holder.mTitle.setText("官方活动");
                //holder.mPoint.setVisibility(contactsModel.getIsRead() == 1 ? View.VISIBLE : View.GONE);
                break;

            case ChatType.SYSTEMINFO://系统消息
                holder.mButtonDelet.setVisibility(View.GONE);
                holder.mLogo.setImageResource(R.mipmap.system_logo);
                holder.mTitle.setText("系统消息");
                //holder.mPoint.setVisibility(contactsModel.getIsRead() == 1 ? View.VISIBLE : View.GONE);
                break;

            case ChatType.NOTICE://消息通知
                holder.mButtonDelet.setVisibility(View.GONE);
                MyImageLoader.getIntent().displayNetImage(mContext, contactsModel.getLogo(), holder.mLogo);
                holder.mTitle.setText(contactsModel.getTitle());
                //holder.mPoint.setVisibility(contactsModel.getIsRead() == 1 ? View.VISIBLE : View.GONE);
                break;

            case ChatType.P2P://P2P
                holder.mButtonDelet.setVisibility(View.VISIBLE);
                NimUserInfo userInfo = NIMClient.getService(UserService.class).getUserInfo(contactsModel.getAccountId());
                if (userInfo != null) {
                    holder.mLogo.loadBuddyAvatar(userInfo.getAvatar());
                    holder.mTitle.setText(userInfo.getName());
                } else {
                    NimUserInfoCache.getInstance().getUserInfoFromRemote(contactsModel.getAccountId(), new RequestCallbackWrapper<NimUserInfo>() {
                        @Override
                        public void onResult(int i, NimUserInfo nimUserInfo, Throwable throwable) {
                            if (i != ResponseCode.RES_SUCCESS || nimUserInfo == null) {
                                return;
                            } else {
                                holder.mLogo.loadBuddyAvatar(nimUserInfo.getAvatar());
                                holder.mTitle.setText(nimUserInfo.getName());
                            }
                        }
                    });
                }
                if (contactsModel.getUnreadCount() == 0) {
                    holder.mRedPointNum.setVisibility(View.GONE);
                } else {
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.mRedPointNum.getLayoutParams();
                    holder.mRedPointNum.setVisibility(View.VISIBLE);
                    if (contactsModel.getUnreadCount() < 10) {
                        params.width = SystemUtil.dp2px(16);
                        params.height = SystemUtil.dp2px(16);
                        holder.mRedPointNum.setLayoutParams(params);
                        holder.mRedPointNum.setText(String.valueOf(contactsModel.getUnreadCount()));
                        holder.mRedPointNum.setBackground(mContext.getResources().getDrawable(R.drawable.bg_circle_num_tip));
                    } else if (contactsModel.getUnreadCount() >= 0 && contactsModel.getUnreadCount() <= 99) {
                        params.width = SystemUtil.dp2px(24);
                        params.height = SystemUtil.dp2px(16);
                        holder.mRedPointNum.setLayoutParams(params);
                        holder.mRedPointNum.setText(String.valueOf(contactsModel.getUnreadCount()));
                        holder.mRedPointNum.setBackground(mContext.getResources().getDrawable(R.drawable.bg_circle_num_tip_plus));
                    } else if (contactsModel.getUnreadCount() >= 99) {
                        params.width = SystemUtil.dp2px(24);
                        params.height = SystemUtil.dp2px(16);
                        holder.mRedPointNum.setLayoutParams(params);
                        holder.mRedPointNum.setText("99+");
                        holder.mRedPointNum.setBackground(mContext.getResources().getDrawable(R.drawable.bg_circle_num_tip_plus));
                    }

                }
                //holder.mPoint.setVisibility(View.GONE);
                break;

            case ChatType.TEAM://Team
                holder.mButtonDelet.setVisibility(View.GONE);
                Team team = NIMClient.getService(TeamService.class).queryTeamBlock(contactsModel.getAccountId());
                if (team != null) {
                    holder.mLogo.loadBuddyAvatar(team.getIcon());
                    holder.mTitle.setText(team.getName());
                }
                if (contactsModel.getUnreadCount() == 0) {
                    holder.mRedPointNum.setVisibility(View.GONE);
                } else {
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.mRedPointNum.getLayoutParams();
                    holder.mRedPointNum.setVisibility(View.VISIBLE);
                    if (contactsModel.getUnreadCount() < 10) {
                        params.width = SystemUtil.dp2px(16);
                        params.height = SystemUtil.dp2px(16);
                        holder.mRedPointNum.setLayoutParams(params);
                        holder.mRedPointNum.setText(String.valueOf(contactsModel.getUnreadCount()));
                        holder.mRedPointNum.setBackground(mContext.getResources().getDrawable(R.drawable.bg_circle_num_tip));
                    } else if (contactsModel.getUnreadCount() >= 0 && contactsModel.getUnreadCount() <= 99) {
                        params.width = SystemUtil.dp2px(24);
                        params.height = SystemUtil.dp2px(16);
                        holder.mRedPointNum.setLayoutParams(params);
                        holder.mRedPointNum.setText(String.valueOf(contactsModel.getUnreadCount()));
                        holder.mRedPointNum.setBackground(mContext.getResources().getDrawable(R.drawable.bg_circle_num_tip_plus));
                    } else if (contactsModel.getUnreadCount() >= 99) {
                        params.width = SystemUtil.dp2px(24);
                        params.height = SystemUtil.dp2px(16);
                        holder.mRedPointNum.setLayoutParams(params);
                        holder.mRedPointNum.setText("99+");
                        holder.mRedPointNum.setBackground(mContext.getResources().getDrawable(R.drawable.bg_circle_num_tip_plus));
                    }
                }


                //holder.mPoint.setVisibility(View.GONE);
                break;

        }
        holder.mContent.setText(contactsModel.getContent());
        holder.mTime.setText(contactsModel.getTime() == 0 ? "" : TimeUtil.getTimeShowString(contactsModel.getTime(), true));
    }

    @Override
    public int getItemCount() {
        return mRecentContacts == null ? 1 : mRecentContacts.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        HiStudentHeadImageView mLogo;
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.content)
        TextView mContent;
        @BindView(R.id.middle)
        LinearLayout mLayout;
        //        @BindView(R.id.red_point)
//        TextView mPoint;
        @BindView(R.id.red_point_num)
        TextView mRedPointNum;
        @BindView(R.id.time)
        TextView mTime;
        @BindView(R.id.delete)
        Button mButtonDelet;

        @OnClick({R.id.middle, R.id.delete})
        public void onClick(View view) {
            RecentContactsModel recentContactsModel = mRecentContacts.get(getLayoutPosition() - 1);
            switch (view.getId()) {
                case R.id.middle:
                    EventBus.getDefault().post(new MessageEvent(recentContactsModel));
                    break;
                case R.id.delete:
                    EventBus.getDefault().post(new RecentContactDelete(recentContactsModel, getLayoutPosition()));
                    break;
            }
        }

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.message_mine)
        LinearLayout mMine;
        @BindView(R.id.message_comment)
        LinearLayout mComment;
        @BindView(R.id.message_praise)
        LinearLayout mPraise;
        @BindView(R.id.message_system)
        LinearLayout mSystem;
        @BindView(R.id.mine_red_point)
        View mMinePoint;
        @BindView(R.id.praise_red_point)
        View mPraisePoint;
        @BindView(R.id.comment_red_point)
        View mCommentPoint;
        @BindView(R.id.system_red_point)
        View mSystemPoint;
        @BindView(R.id.mine_goto)
        IconView mMineGo;
        @BindView(R.id.praise_goto)
        IconView mPraiseGo;
        @BindView(R.id.comment_goto)
        IconView mCommentGo;

        @OnClick({R.id.message_mine, R.id.message_comment, R.id.message_praise, R.id.message_system})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.message_mine:
                    EventBus.getDefault().post(new MessageClickEvent(Const.NOTICE_TYPE_MIME));
                    break;
                case R.id.message_comment:
                    EventBus.getDefault().post(new MessageClickEvent(Const.NOTICE_TYPE_COMMENT));
                    break;
                case R.id.message_praise:
                    EventBus.getDefault().post(new MessageClickEvent(Const.NOTICE_TYPE_PRAISE));
                    break;
                case R.id.message_system:
                    EventBus.getDefault().post(new MessageClickEvent(Const.NOTICE_TYPE_SYSTEM));
                    break;
            }
        }

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
