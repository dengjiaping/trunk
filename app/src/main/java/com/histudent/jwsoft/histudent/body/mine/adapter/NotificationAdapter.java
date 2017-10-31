package com.histudent.jwsoft.histudent.body.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.model.NoticeBean;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.comment2.utils.EmotionUtils;
import com.histudent.jwsoft.histudent.comment2.utils.TimeUtils;
import com.histudent.jwsoft.histudent.entity.NotificationClickEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by huyg on 2017/7/21.
 */

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<NoticeBean.ItemsBean> mMessages = new ArrayList<>();
    private int type;
    private Context mContext;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_EMPTY = -1;


    public NotificationAdapter(Context context) {
        this.mContext = context;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setMessages(List<NoticeBean.ItemsBean> messages) {
        this.mMessages = messages;
        notifyDataSetChanged();
    }

    public void addMessages(List<NoticeBean.ItemsBean> messages) {
        this.mMessages.addAll(messages);
        notifyDataSetChanged();
    }

    public int getSize() {
        return mMessages == null ? 0 : mMessages.size();
    }


    @Override

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_ITEM:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_detail, parent, false);
                viewHolder = new NotificationAdapter.ViewHolder(view);
                break;
            case TYPE_EMPTY:
                View emptyView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_empty, parent, false);
                viewHolder = new NotificationAdapter.EmptyViewHolder(emptyView);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NotificationAdapter.ViewHolder) {
            NotificationAdapter.ViewHolder itemHolder = (NotificationAdapter.ViewHolder) holder;
            NoticeBean.ItemsBean message = mMessages.get(position);
            if (message != null) {
                CommonGlideImageLoader.getInstance().displayNetImage(mContext, message.getPAvatar(), itemHolder.mAvatar, mContext.getResources().getDrawable(R.mipmap.per_def));
                itemHolder.mName.setText(message.getPUserName());
                itemHolder.mTime.setText(TimeUtils.exchangeTime(message.getAddTime()));
                switch (message.getInfoStatus()) {
                    case 0:
                        itemHolder.mContent.setText(EmotionUtils.convertNormalStringToSpannableString(message.getConent()));
                        break;
                    case 1:
                        itemHolder.mContent.setText("删除动态");
                        break;
                    case 2:
                        itemHolder.mContent.setText(type == 5 ? "删除评论" : "取消点赞");
                        break;
                }
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (mMessages != null && mMessages.size() > 0) {
            return TYPE_ITEM;
        }
        return TYPE_EMPTY;
    }

    @Override
    public int getItemCount() {
        if (mMessages != null && mMessages.size() > 0) {
            return mMessages.size();
        }
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_message)
        LinearLayout mItem;
        @BindView(R.id.item_message_avatar)
        ImageView mAvatar;
        @BindView(R.id.item_message_name)
        TextView mName;
        @BindView(R.id.item_message_time)
        TextView mTime;
        @BindView(R.id.item_message_content)
        TextView mContent;

        @OnClick(R.id.item_message)
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.item_message:
                    NoticeBean.ItemsBean itemsBean = mMessages.get(getLayoutPosition());
                    EventBus.getDefault().post(new NotificationClickEvent(getLayoutPosition(), itemsBean));
                    break;
            }
        }

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
