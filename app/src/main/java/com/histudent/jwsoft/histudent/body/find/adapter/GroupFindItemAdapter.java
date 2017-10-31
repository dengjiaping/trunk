package com.histudent.jwsoft.histudent.body.find.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.bean.GroupFindBean;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;

import java.util.List;

/**
 * 发现社群适配器
 * Created by ZhangYT on 2016/8/11.
 */
public class GroupFindItemAdapter extends BaseAdapter {

    private List<GroupFindBean.GrorpModelsBean> list;
    private Activity context;

    public GroupFindItemAdapter(List<GroupFindBean.GrorpModelsBean> list, Activity context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.adapter_groupfind_item_, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = ((ViewHolder) convertView.getTag());
        }

        final GroupFindBean.GrorpModelsBean bean = list.get(position);
        MyImageLoader.getIntent().displayNetImage(context, bean.getGroupLogo(), viewHolder.group_logo);
        viewHolder.group_name.setText(bean.getGroupName());
        viewHolder.group_members.setText(bean.getGroupMemberNum() + "");
        viewHolder.group_tag.setVisibility(bean.isIsMember() ? View.VISIBLE : View.GONE);
        return convertView;
    }

    class ViewHolder {
        private TextView group_name, group_members, group_tag;
        private HiStudentHeadImageView group_logo;

        public ViewHolder(View convertView) {
            group_name = (TextView) convertView.findViewById(R.id.group_name);
            group_members = (TextView) convertView.findViewById(R.id.group_members);
            group_tag = (TextView) convertView.findViewById(R.id.group_tag);
            group_logo = (HiStudentHeadImageView) convertView.findViewById(R.id.group_logo);
        }
    }

}
