package com.histudent.jwsoft.histudent.view.adapter.work;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.model.bean.homework.WorkCompleteBean;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.comment2.utils.TimeUtils;
import com.histudent.jwsoft.histudent.model.entity.CorrectWorkEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by huyg on 2017/10/25.
 */

public class WorkCompleteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TYPE_EMPTY = 0;
    private final int TYPE_ITEM = 1;
    private boolean isOnline = false;



    private List<WorkCompleteBean.ItemsBean> mList = new ArrayList<>();
    private Context mContext;


    public WorkCompleteAdapter(Context context) {
        this.mContext = context;
    }

    public void setOnline(boolean online) {
        isOnline = online;
        notifyDataSetChanged();
    }

    public void setList(List<WorkCompleteBean.ItemsBean> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    public void addList(List<WorkCompleteBean.ItemsBean> list) {
        this.mList.addAll(list);
        notifyDataSetChanged();
    }

    public List<WorkCompleteBean.ItemsBean> getList() {
        return mList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_ITEM:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_work_complete, parent, false);
                viewHolder = new WorkCompleteAdapter.ViewHolder(view);
                break;
            case TYPE_EMPTY:
                View emptyView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_empty, parent, false);
                viewHolder = new WorkCompleteAdapter.EmptyViewHolder(emptyView);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof WorkCompleteAdapter.ViewHolder) {
            WorkCompleteAdapter.ViewHolder holder = ((ViewHolder) viewHolder);
            WorkCompleteBean.ItemsBean itemsBean = mList.get(position);
            if (isOnline){
                holder.mGoDetail.setVisibility(View.VISIBLE);
            }else{
                holder.mGoDetail.setVisibility(View.GONE);
            }
            if (itemsBean != null) {
                CommonGlideImageLoader.getInstance().displayNetImageWithCircle(mContext, itemsBean.getUserAvatar(), holder.mHead);
                holder.mName.setText(itemsBean.getUserName());
                holder.mTime.setText(TimeUtils.exchangeTime(itemsBean.getCompleteTime()));
                if (itemsBean.isIsComment()) {
                    holder.mTip.setVisibility(View.VISIBLE);
                } else {
                    holder.mTip.setVisibility(View.GONE);
                }

            }
        }else if (viewHolder instanceof WorkCompleteAdapter.EmptyViewHolder){
            WorkCompleteAdapter.EmptyViewHolder holder = ((EmptyViewHolder) viewHolder);
            holder.mTip.setText("还没有人完成");
            holder.mEmptyImg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_no_complete));
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (mList != null && mList.size() > 0) {

            return TYPE_ITEM;
        }

        return TYPE_EMPTY;
    }

    @Override
    public int getItemCount() {
        if (mList != null && mList.size() > 0) {
            return mList.size();
        }
        return 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_complete_head)
        ImageView mHead;
        @BindView(R.id.item_complete_name)
        TextView mName;
        @BindView(R.id.item_comment_tip)
        TextView mTip;
        @BindView(R.id.item_complete_time)
        TextView mTime;
        @BindView(R.id.go_detail)
        IconView mGoDetail;

        @OnClick(R.id.item_complete)
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.item_complete:
                    WorkCompleteBean.ItemsBean itemsBean = mList.get(getLayoutPosition());
                    EventBus.getDefault().post(new CorrectWorkEvent(itemsBean));
                    break;
            }
        }

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class EmptyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_empty_content)
        TextView mTip;
        @BindView(R.id.iv_empty_resource)
        ImageView mEmptyImg;
        public EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
