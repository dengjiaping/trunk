package com.histudent.jwsoft.histudent.body.find.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.bean.ParentCategoryModel;

import java.util.List;

/**
 * Created by ZhangYT on 2017/5/16.
 */

public class CategroyAdapter extends BaseAdapter {
    private List<Object> list;
    private Context context;

    public CategroyAdapter(List<Object> list, Context context) {
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

        Object o = list.get(position);
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.first_category_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = ((ViewHolder) convertView.getTag());
        }


        if (o instanceof ParentCategoryModel) {

            ParentCategoryModel model = ((ParentCategoryModel) o);
            viewHolder.tv_name.setText(model.getName());
        }

        return convertView;
    }

    private class ViewHolder {

        private TextView tv_name;

        public ViewHolder(View convertView) {
            tv_name = ((TextView) convertView.findViewById(R.id.tv_name));
        }

    }
}
