package com.histudent.jwsoft.histudent.comment2.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.comment2.bean.GradeBean;
import com.histudent.jwsoft.histudent.comment2.bean.SchoolBean;
import com.histudent.jwsoft.histudent.comment2.bean.SystemProid;

import java.util.List;

/**
 * Created by ZhangYT on 2016/12/6.
 */

public class CommentSelectAdapter extends BaseAdapter {

    private List<Object> list;
    private Activity activity;

    public CommentSelectAdapter(List<Object> list, Activity activity) {
        this.list = list;
        this.activity = activity;
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
        String name = null;
        Object o = list.get(position);
        if (convertView == null) {
            convertView = View.inflate(activity, R.layout.sort_group_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = ((ViewHolder) convertView.getTag());
        }

        if (o instanceof SchoolBean.ItemsBean) {
            name = ((SchoolBean.ItemsBean) o).getName();
        } else if (o instanceof SystemProid) {
            name = ((SystemProid) o).getEduName();
        } else if (o instanceof GradeBean) {
            name = ((GradeBean) o).getName();
        }

        viewHolder.tv_name.setText(name);
        viewHolder.tv_name.setTextColor(activity.getResources().getColor(R.color.text_black_h2));
        return convertView;
    }

    class ViewHolder {
        private TextView tv_name;

        public ViewHolder(View convertView) {
            tv_name = ((TextView) convertView.findViewById(R.id.tv));
            convertView.findViewById(R.id.iv).setVisibility(View.GONE);
        }
    }
}
