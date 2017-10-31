package com.histudent.jwsoft.histudent.body.mine.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;

/**
 * Created by liuguiyu on 2016/6/16.
 */
public class MyDialogAdapter extends BaseAdapter {
    private String[] array;
    private Context context;

    public MyDialogAdapter(Context context, String[] array) {
        this.array = array;
        this.context = context;
    }

    @Override
    public int getCount() {
        return array.length;
    }

    @Override
    public Object getItem(int position) {
        return array[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.mydialog_item, null);

            viewHolder.mydialog_item_name = (TextView) convertView.findViewById(R.id.mydialog_item_name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mydialog_item_name.setText(array[position]);


        return convertView;
    }

    class ViewHolder {
        TextView mydialog_item_name;
    }
}
