package com.histudent.jwsoft.histudent.body.myclass.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.myclass.bean.PointRecordModel;
import com.histudent.jwsoft.histudent.body.myclass.bean.RecordListBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.histudent.jwsoft.histudent.body.myclass.bean.PointRecordModel.TYPE_CONTENT;

/**
 * Created by huyg on 2017/7/3.
 */

public class PointRecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private static final int ACTION_ALL = 0;
    private static final int ACTION_EARN = 1;
    private static final int ACTION_USE = 2;
    private static final int TYPE_EMPTY = -1;
    private int action = 0;


    private boolean isEmpty;

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    private List<PointRecordModel> mPointRecordModels = new ArrayList<>();


    public PointRecordAdapter(Context context) {
        this.mContext = context;
    }


    public List<PointRecordModel> getRecord() {
        return mPointRecordModels;
    }

    public void setRecords(List<PointRecordModel> pointRecordModels) {
        this.mPointRecordModels = pointRecordModels;
        notifyDataSetChanged();
    }

    public void addRecords(List<PointRecordModel> pointRecordModels) {
        this.mPointRecordModels.addAll(pointRecordModels);
        notifyDataSetChanged();
    }

    public void clearRecords() {
        this.mPointRecordModels.clear();
        notifyDataSetChanged();
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getSize() {
        int size = 0;
        for (PointRecordModel pointRecordModel : mPointRecordModels)
            if (pointRecordModel.getType() == TYPE_CONTENT) {
                size++;
            }
        return size;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case PointRecordModel.TYPE_TITLE:
                View titleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_point_record_title, parent, false);
                viewHolder = new TitleViewHolder(titleView);
                break;
            case PointRecordModel.TYPE_CONTENT:
                View contentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_point_record, parent, false);
                viewHolder = new ContentViewHolder(contentView);
                break;
            case TYPE_EMPTY:
                View emptyView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_empty, parent, false);
                viewHolder = new EmptyViewHolder(emptyView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof TitleViewHolder) {
            PointRecordModel pointRecordModel = mPointRecordModels.get(position);
            TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
            titleViewHolder.mMonth.setText("" + pointRecordModel.getMonth());
            switch (action) {
                case ACTION_EARN:
                    titleViewHolder.mPointTip.setVisibility(View.VISIBLE);
                    titleViewHolder.mEarnPoint.setVisibility(View.VISIBLE);
                    titleViewHolder.mEarnPoint.setText("" + pointRecordModel.getPoints());
                    titleViewHolder.mConsumePoint.setVisibility(View.GONE);
                    titleViewHolder.mConsumePointTip.setVisibility(View.GONE);
                    break;
                case ACTION_USE:
                    titleViewHolder.mConsumePointTip.setVisibility(View.VISIBLE);
                    titleViewHolder.mConsumePoint.setVisibility(View.VISIBLE);
                    titleViewHolder.mConsumePoint.setText("" + pointRecordModel.getConsumePoints());
                    titleViewHolder.mEarnPoint.setVisibility(View.GONE);
                    titleViewHolder.mPointTip.setVisibility(View.GONE);
                    break;
                default:
                    titleViewHolder.mPointTip.setVisibility(View.VISIBLE);
                    titleViewHolder.mConsumePointTip.setVisibility(View.VISIBLE);
                    titleViewHolder.mEarnPoint.setVisibility(View.VISIBLE);
                    titleViewHolder.mEarnPoint.setText("" + pointRecordModel.getPoints());
                    titleViewHolder.mConsumePoint.setVisibility(View.VISIBLE);
                    titleViewHolder.mConsumePoint.setText("" + pointRecordModel.getConsumePoints());
                    break;
            }
        } else if (holder instanceof ContentViewHolder) {

            PointRecordModel pointRecordModel = mPointRecordModels.get(position);
            ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
            RecordListBean recordListBean = pointRecordModel.getRecordListBean();
            if (recordListBean != null) {
                if (!TextUtils.isEmpty(recordListBean.getPointItemName())) {
                    contentViewHolder.mContent.setText(recordListBean.getPointItemName());
                }
                if (!TextUtils.isEmpty(recordListBean.getCreateTime())) {
                    contentViewHolder.mTime.setText(recordListBean.getCreateTime());
                }
                contentViewHolder.mPoint.setText("+" + recordListBean.getPoints());
            }
        }
    }

    @Override
    public int getItemCount() {
        return mPointRecordModels.size() + (isEmpty ? 1 : 0);
    }


    @Override
    public int getItemViewType(int position) {

        if (mPointRecordModels != null && mPointRecordModels.size() > 0) {
            PointRecordModel pointRecordModel = mPointRecordModels.get(position);
            return pointRecordModel.getType();
        } else {
            return TYPE_EMPTY;
        }
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_record_content)
        TextView mContent;
        @BindView(R.id.item_record_time)
        TextView mTime;
        @BindView(R.id.item_record_point)
        TextView mPoint;


        public ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public class TitleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title_point)
        TextView mEarnPoint;//赚取
        @BindView(R.id.title_consume_point)
        TextView mConsumePoint;//消耗
        @BindView(R.id.title_point_tip)
        TextView mPointTip;
        @BindView(R.id.title_consume_point_tip)
        TextView mConsumePointTip;//消耗
        @BindView(R.id.title_month)
        TextView mMonth;


        public TitleViewHolder(View itemView) {
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
