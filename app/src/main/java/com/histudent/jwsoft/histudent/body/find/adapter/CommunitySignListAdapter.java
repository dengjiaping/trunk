package com.histudent.jwsoft.histudent.body.find.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.bean.CommunitySignBean;
import com.histudent.jwsoft.histudent.commen.utils.DateUtil;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;

import java.util.Date;
import java.util.List;

/**
 * Created by android03_pc on 2017/5/18.
 */

public class CommunitySignListAdapter extends BaseAdapter {
    private Context mContext;
    private List<CommunitySignBean.GroupMembersListBean> list;
    private LayoutInflater mInflater;

    public CommunitySignListAdapter(Context mContext, List<CommunitySignBean.GroupMembersListBean> list) {
        this.mContext = mContext;
        this.list = list;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.sign_item_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mName.setText(list.get(position).getUserRealName());
        holder.mTime.setText(list.get(position).getLastVisitTimeCustom());
        MyImageLoader.getIntent().displayNetImage(mContext,list.get(position).getUserAvatar(),holder.headImageView);
        return convertView;
    }

    static class ViewHolder {
        private HiStudentHeadImageView headImageView;
        private TextView mName,mTime;

        public ViewHolder(View convertView) {
            headImageView = (HiStudentHeadImageView) convertView.findViewById(R.id.community_sign_head);
            mName = (TextView) convertView.findViewById(R.id.community_sign_name);
            mTime = (TextView) convertView.findViewById(R.id.community_sign_time);
        }
    }
}
