package com.histudent.jwsoft.histudent.body.myclass.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.model.ClassModel;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by huyg on 2017/7/3.
 */

public class BadgeAdapter extends RecyclerView.Adapter<BadgeAdapter.ViewHolder>{

    private Context mContext;
    private List<ClassModel.ClassBadgesBean> mBadges = new ArrayList<>();

    public BadgeAdapter(Context context){
        this.mContext = context;
    }

    public List<ClassModel.ClassBadgesBean> getBadges() {
        return mBadges;
    }

    public void setBadges(List<ClassModel.ClassBadgesBean> mBadges) {
        this.mBadges = mBadges;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_type_list_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ClassModel.ClassBadgesBean badgeBean = mBadges.get(position);
        if (badgeBean!=null){
            if(!TextUtils.isEmpty(badgeBean.getBadgeName())){
                holder.mBadge.setText(badgeBean.getBadgeName());
            }
            if (!TextUtils.isEmpty(badgeBean.getBadgeUrl())){
                CommonGlideImageLoader.getInstance().displayNetImage(mContext,badgeBean.getBadgeUrl(),holder.mBadgeImg);
            }

        }
    }

    @Override
    public int getItemCount() {
        return mBadges == null? 0 :mBadges.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.badge_img)
        CircleImageView mBadgeImg;
        @BindView(R.id.badge)
        TextView mBadge;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }
}
