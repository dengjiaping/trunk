package com.histudent.jwsoft.histudent.body.hiworld.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.mine.model.UserClassListModel;

import java.util.List;

/**
 * 同步班级适配
 * Created by ZhangYT on 2017/4/12.
 */

public class SyncClassAdapter extends BaseAdapter {
    private List<UserClassListModel> list;
    private Activity context;

    public SyncClassAdapter(List<UserClassListModel> list, Activity context) {
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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.sync_class_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = ((ViewHolder) convertView.getTag());
        }
        viewHolder.tv_name.setText(list.get(position).getCName());

        //设置数据

        return convertView;
    }

    private class ViewHolder {
        private TextView tv_name;

        private ViewHolder(View convertView) {
            tv_name = ((TextView) convertView.findViewById(R.id.tv_name));
        }

    }
}
