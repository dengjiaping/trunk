package com.histudent.jwsoft.histudent.body.myclass.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.myclass.bean.GrouthTaskModel;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by huyg on 2017/7/3.
 */

public class GrowTaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    private boolean isFooter;

    private List<GrouthTaskModel.TaskListBean> mTasks = new ArrayList<>();


    public GrowTaskAdapter(Context context) {
        this.mContext = context;
    }


    public List<GrouthTaskModel.TaskListBean> getmTasks() {
        return mTasks;
    }

    public void setTasks(List<GrouthTaskModel.TaskListBean> mTasks) {
        this.mTasks = mTasks;
        notifyDataSetChanged();
    }

    public void addTasks(List<GrouthTaskModel.TaskListBean> mTasks) {
        this.mTasks.addAll(mTasks);
        notifyDataSetChanged();
    }

    public int getSize() {
        return mTasks == null ? 0 : mTasks.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View view = null;
        switch (viewType) {
            case TYPE_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grow_task_item, parent, false);
                holder = new GrowTaskAdapter.ViewHolder(view);
                break;
            case TYPE_FOOTER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.load_more_bottom_no_data, parent, false);
                holder = new GrowTaskAdapter.FooterViewHolder(view);
                break;
        }
        return holder;
    }


    @Override
    public int getItemViewType(int position) {
        int viewType;
        if (position < mTasks.size()) {
            viewType = TYPE_ITEM;
        } else {
            viewType = TYPE_FOOTER;
        }
        return viewType;
    }

    public boolean getIsFooter() {
        return isFooter;
    }

    public void setFooter(boolean isFooter) {
        this.isFooter = isFooter;
    }

    @Override
    public int getItemCount() {
        return mTasks.size() + (getIsFooter() ? 1 : 0);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GrowTaskAdapter.ViewHolder){
            GrowTaskAdapter.ViewHolder viewHolder=(GrowTaskAdapter.ViewHolder)holder;
            GrouthTaskModel.TaskListBean task = mTasks.get(position);
            if (task != null) {
                if (!TextUtils.isEmpty(task.getTitle())) {
                    viewHolder.mName.setText(task.getTitle());
                }
                if (!TextUtils.isEmpty(task.getContent())) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(task.getContent());
                    if (task.getRequiredTimes() != 0) {
                        builder.append("(");
                        builder.append(task.getCompletedTimes());
                        builder.append("//");
                        builder.append(task.getRequiredTimes());
                        builder.append(")");
                    }
                    viewHolder.mInstr.setText(builder.toString());
                }

                if (!TextUtils.isEmpty(task.getImg())) {
                    CommonGlideImageLoader.getInstance().displayNetImage(mContext, task.getImg(), viewHolder.mLog, mContext.getResources().getDrawable(R.mipmap.picture_gray));
                }

                if (task.getIsComplete()) {
                    viewHolder.isFinish.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.isFinish.setVisibility(View.GONE);
                }
                if (task.getPoints()<10){
                    viewHolder.mPoint.setText("  +"+task.getPoints());
                }else{
                    viewHolder.mPoint.setText("+"+task.getPoints());
                }

            }
        }

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.log)
        HiStudentHeadImageView mLog;
        @BindView(R.id.tv_name)
        TextView mName;
        @BindView(R.id.tv_instr)
        TextView mInstr;
        @BindView(R.id.point)
        TextView mPoint;
        @BindView(R.id.is_finish)
        ImageView isFinish;

//        @OnClick(R.id.btn)
//        public void onClick(View view) {
//            switch (view.getId()) {
//                case R.id.p:
//                    GrouthTaskModel.TaskListBean task = mTasks.get(getLayoutPosition());
//                    EventBus.getDefault().post(new TaskEvent(task));
//
//                    break;
//            }
//
//        }

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }

    }
}
