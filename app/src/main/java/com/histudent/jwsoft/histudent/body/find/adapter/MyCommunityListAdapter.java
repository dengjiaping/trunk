package com.histudent.jwsoft.histudent.body.find.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.bean.SecondCommunityBean;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;

import java.util.List;

/**
 * Created by android03_pc on 2017/5/16.
 */

public class MyCommunityListAdapter extends BaseAdapter {
    private Context mContext;
    private List<SecondCommunityBean> list;

    public MyCommunityListAdapter(Context mContext, List<SecondCommunityBean> listDatas) {
        this.mContext = mContext;
        list = listDatas;
    }

    public void setListDatas(List<SecondCommunityBean> list) {
        this.list = list;
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_page_item_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //社群名字
        holder.mText_name.setText(list.get(position).getGroupName());
        //成员数量
        holder.mText_count.setText(String.valueOf(list.get(position).getGroupMemberNum()));
        //社群logo
        MyImageLoader.getIntent().displayNetImage(mContext, list.get(position).getGroupLogo(), holder.mImage_head);
        //是否加入
        holder.mText_join.setVisibility(list.get(position).isIsMember() ? View.VISIBLE : View.GONE);
        return convertView;
    }

    static class ViewHolder {
        private TextView mText_name, mText_count;
        private TextView mText_join;
        private HiStudentHeadImageView mImage_head;

        public ViewHolder(View convertView) {
            mImage_head = convertView.findViewById(R.id.community_image_head);
            mText_join = convertView.findViewById(R.id.community_iv_join);
            mText_name = convertView.findViewById(R.id.community_text_name);
            mText_count = convertView.findViewById(R.id.community_text_count);
        }
    }
}
