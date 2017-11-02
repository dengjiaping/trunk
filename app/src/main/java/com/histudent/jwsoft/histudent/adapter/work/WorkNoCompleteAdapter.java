package com.histudent.jwsoft.histudent.adapter.work;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.adapter.BaseAdapter;
import com.histudent.jwsoft.histudent.adapter.BaseViewHolder;
import com.histudent.jwsoft.histudent.bean.RecordBean;
import com.histudent.jwsoft.histudent.bean.homework.WorkCompleteBean;
import com.histudent.jwsoft.histudent.commen.adapter.RecyclerAdapter;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.comment2.utils.TimeUtils;
import com.histudent.jwsoft.histudent.entity.WorkNoticeEvent;
import com.histudent.jwsoft.histudent.presenter.homework.WorkNoCompletePresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by huyg on 2017/10/25.
 */

public class WorkNoCompleteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final int TYPE_EMPTY = 0;
    private final int TYPE_ITEM = 1;


    private List<WorkCompleteBean.ItemsBean> mList = new ArrayList<>();
    private Context mContext;

    public WorkNoCompleteAdapter(Context context) {
        this.mContext = context;
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
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_work_no_complete, parent, false);
                viewHolder = new WorkNoCompleteAdapter.ViewHolder(view);
                break;
            case TYPE_EMPTY:
                View emptyView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_empty, parent, false);
                viewHolder = new WorkNoCompleteAdapter.EmptyViewHolder(emptyView);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof WorkNoCompleteAdapter.ViewHolder) {
            WorkNoCompleteAdapter.ViewHolder holder = ((WorkNoCompleteAdapter.ViewHolder) viewHolder);
            WorkCompleteBean.ItemsBean itemsBean = mList.get(position);
            if (itemsBean != null) {
                CommonGlideImageLoader.getInstance().displayNetImageWithCircle(mContext, itemsBean.getUserAvatar(), holder.mHead);
                holder.mName.setText(itemsBean.getUserName());
            }
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
        @BindView(R.id.item_no_complete_head)
        ImageView mHead;
        @BindView(R.id.item_no_complete_name)
        TextView mName;
        @BindView(R.id.item_no_complete_tip)
        IconView mTip;

        @OnClick(R.id.item_no_complete_tip)
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.item_no_complete_tip:
                    WorkCompleteBean.ItemsBean itemsBean=mList.get(getLayoutPosition());
                    EventBus.getDefault().post(new WorkNoticeEvent(itemsBean.getUserId()));
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
