package com.histudent.jwsoft.histudent.comment2.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.model.AreaCodeModel;
import java.util.List;

/**
 * Created by ZhangYT on 2016/12/5.
 */

public class HotCityAdapter extends BaseAdapter {

    private List<Object> list;
    private Activity activity;

    public HotCityAdapter(List<Object> list, Activity activity) {
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
        String name;
        Object o = list.get(position);
        if (convertView == null) {

            convertView = View.inflate(activity, R.layout.hot_city_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = ((ViewHolder) convertView.getTag());
        }


        if (o instanceof String) {
            name = ((String) o);
        } else {
            AreaCodeModel model = ((AreaCodeModel) o);
            name = model.getName();
            if (model.getDepth().equals("3"))
            viewHolder.tv_city.setEnabled(!model.isSelected());
        }

        viewHolder.tv_city.setText(name);
        return convertView;
    }

    class ViewHolder {
        private TextView tv_city;

        ViewHolder(View convertView) {

            tv_city = ((TextView) convertView.findViewById(R.id.tv_hot_city));
        }
    }

}
