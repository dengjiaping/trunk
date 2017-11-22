package com.histudent.jwsoft.histudent.body.myclass.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.model.ClassModel;
import com.histudent.jwsoft.histudent.body.myclass.bean.GrouthTaskModel;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.commen.view.StarBar;
import com.histudent.jwsoft.histudent.model.entity.BadgeClickEvent;
import com.histudent.jwsoft.histudent.model.entity.TaskClickEvent;
import com.histudent.jwsoft.histudent.model.entity.TaskEvent;
import com.histudent.jwsoft.histudent.model.manage.PhotoManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by huyg on 2017/7/3.
 */

public class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private static final int TYPE_HEAD = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    private boolean isFooter;


    private List<GrouthTaskModel.TaskListBean> mTasks = new ArrayList<>();
    private ClassModel mClassModel = new ClassModel();

    public ClassModel getClassModel() {
        return mClassModel;
    }

    public void setClassModel(ClassModel classModel) {
        this.mClassModel = classModel;
        notifyItemChanged(0);
    }


    public TaskAdapter(Context context) {
        this.mContext = context;
    }


    public List<GrouthTaskModel.TaskListBean> getmTasks() {
        return mTasks;
    }

    public void setTasks(List<GrouthTaskModel.TaskListBean> mTasks) {
        if (mTasks != null) {
            this.mTasks = mTasks;
            notifyDataSetChanged();
        }

    }

    public void addTasks(List<GrouthTaskModel.TaskListBean> mTasks) {

        this.mTasks.addAll(mTasks);
        notifyDataSetChanged();
    }

    public boolean getIsFooter() {
        return isFooter;
    }

    public void setFooter(boolean isFooter) {
        this.isFooter = isFooter;
    }

    public int getSize() {
        return mTasks.size() + 1 + (getIsFooter() ? 1 : 0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View view = null;
        switch (viewType) {
            case TYPE_HEAD:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task_head, parent, false);
                holder = new HeadViewHolder(view);
                break;
            case TYPE_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grouth_task_item, parent, false);
                holder = new ItemViewHolder(view);
                break;
            case TYPE_FOOTER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.load_more_bottom_no_data, parent, false);
                holder = new FooterViewHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            GrouthTaskModel.TaskListBean task = mTasks.get(position - 1);
            if (task != null) {
                if (!TextUtils.isEmpty(task.getTitle())) {
                    itemViewHolder.mName.setText(task.getTitle());
                }
                if (!TextUtils.isEmpty(task.getContent())) {
                    itemViewHolder.mInstr.setText(task.getContent());
                }
                if (!TextUtils.isEmpty(task.getImg())) {
                    CommonGlideImageLoader.getInstance().displayNetImage(mContext, task.getImg(),
                            itemViewHolder.mLog, ContextCompat.getDrawable(mContext,R.mipmap.picture_gray));
                }
                if (task.getIsComplete()) {
                    itemViewHolder.mBtn.setText("已完成");
                    itemViewHolder.mBtn.setBackground(mContext.getResources().getDrawable(R.drawable.gray_button_bg));
                } else {
                    itemViewHolder.mBtn.setText("去完成");
                    itemViewHolder.mBtn.setBackground(mContext.getResources().getDrawable(R.drawable.green_button_bg));
                }
            }
        } else if (holder instanceof HeadViewHolder) {
            HeadViewHolder headViewHolder = (HeadViewHolder) holder;
            if (mClassModel != null) {
                headViewHolder.mGroupNum.setText(String.valueOf(mClassModel.getClassGrothValue()));
                setMask(headViewHolder, mClassModel.getPlatformRanking());
                headViewHolder.mSchoolGrade.setText(String.valueOf(mClassModel.getSchoolRanking()));
                headViewHolder.mTerraceGrade.setText(String.valueOf(mClassModel.getPlatformRanking()));

                if (mClassModel.getClassBadges() != null && mClassModel.getClassBadges().size() > 0) {
                    headViewHolder.mBadgeList.removeAllViews();
                    View layout;
                    ImageView logImg;
                    TextView logTv;
                    View isTeacherIdentity;
                    for (ClassModel.ClassBadgesBean classBadgesBean : mClassModel.getClassBadges()) {
                        layout = LayoutInflater.from(mContext).inflate(R.layout.badge_item, null);
                        int padding = (int) ((SystemUtil.getScreenWind() - 5.5 * SystemUtil.dp2px(60)) / 10);
                        layout.setPadding(padding, SystemUtil.dp2px(8), padding, SystemUtil.dp2px(8));
                        logImg = layout.findViewById(R.id.iv_log);
                        logTv = layout.findViewById(R.id.tv_log);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(SystemUtil.dp2px(60), SystemUtil.dp2px(60));
                        params.gravity = Gravity.CENTER_HORIZONTAL;
                        logImg.setLayoutParams(params);

                        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(SystemUtil.dp2px(70), SystemUtil.dp2px(20));

                        params1.setMargins(5, 5, 5, 5);
                        logTv.setLayoutParams(params1);
                        if (!TextUtils.isEmpty(classBadgesBean.getBadgeUrl()))
                            CommonGlideImageLoader.getInstance().displayNetImage(mContext, classBadgesBean.getBadgeUrl(),
                                    logImg, PhotoManager.getInstance().getDefaultPlaceholderResource());
                        logTv.setText(classBadgesBean.getBadgeName());
                        headViewHolder.mBadgeList.addView(layout);
                        headViewHolder.mEmblemLayout.setVisibility(View.VISIBLE);
                    }
                } else {
                    headViewHolder.mEmblemLayout.setVisibility(View.GONE);
                }
            }

        }

    }

    private void setMask(HeadViewHolder headViewHolder, int rank) {
        if (rank > 0 && rank <= 1000) {
            headViewHolder.mStarBar.setStarMark(5);
        } else if (1001 < rank && rank <= 3000) {
            headViewHolder.mStarBar.setStarMark(4);
        } else if (3001 < rank && rank <= 5000) {
            headViewHolder.mStarBar.setStarMark(3);
        } else if (5001 < rank && rank <= 10000) {
            headViewHolder.mStarBar.setStarMark(2);
        } else if (rank == 0) {
            headViewHolder.mStarBar.setStarMark(0);
        } else {
            headViewHolder.mStarBar.setStarMark(1);
        }

    }

    @Override
    public int getItemViewType(int position) {
        int viewType;
        if (position == 0) {
            viewType = TYPE_HEAD;
        } else if (position <= mTasks.size()) {
            viewType = TYPE_ITEM;
        } else {
            viewType = TYPE_FOOTER;
        }
        return viewType;
    }

    @Override
    public int getItemCount() {
        return mTasks.size() + 1 + (getIsFooter() ? 1 : 0);
    }

    public class HeadViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.class_group_num)
        TextView mGroupNum;
        @BindView(R.id.school_grade)
        TextView mSchoolGrade;
        @BindView(R.id.terrace_grade)
        TextView mTerraceGrade;
        @BindView(R.id.rl_badge)
        RelativeLayout mBadge;
        @BindView(R.id.emblem_layout)
        LinearLayout mEmblemLayout;
        @BindView(R.id.badge_horizontal)
        HorizontalScrollView mScrollView;
        @BindView(R.id.starBar)
        StarBar mStarBar;
        @BindView(R.id.rl_task)
        RelativeLayout mTask;
        @BindView(R.id.badge_list)
        LinearLayout mBadgeList;

        @OnClick({R.id.rl_task, R.id.rl_badge})
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.rl_task://成长任务

                    EventBus.getDefault().post(new TaskClickEvent(mClassModel.getClassGrothValue()));
                    break;
                case R.id.rl_badge://徽章
                    EventBus.getDefault().post(new BadgeClickEvent(mClassModel.getClassBadges()));


                    break;
            }
        }

        public HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }

    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.log)
        HiStudentHeadImageView mLog;
        @BindView(R.id.tv_name)
        TextView mName;
        @BindView(R.id.tv_instr)
        TextView mInstr;
        @BindView(R.id.btn)
        TextView mBtn;

        @OnClick(R.id.btn)
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn:
                    GrouthTaskModel.TaskListBean task = mTasks.get(getLayoutPosition() - 1);
                    if (!task.getIsComplete()) {
                        EventBus.getDefault().post(new TaskEvent(task));
                    }
                    break;
            }

        }

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
