package com.histudent.jwsoft.histudent.body.mine.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.mine.model.GrowInfoModel;

import java.util.List;

/**
 * Created by liuguiyu on 2016/6/16.
 */
public class GrowInfoAdapter extends BaseAdapter {
    private List<GrowInfoModel> models;
    private Context context;

    public GrowInfoAdapter(Context context, List<GrowInfoModel> models) {
        this.models = models;
        this.context = context;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int position) {
        return models.get(position);
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
            convertView = View.inflate(context, R.layout.growinfo_item, null);

            viewHolder.text_event = (TextView) convertView.findViewById(R.id.growInfo_item_event);
            viewHolder.text_time = (TextView) convertView.findViewById(R.id.growInfo_item_time);
            viewHolder.text_info = (TextView) convertView.findViewById(R.id.growInfo_item_info);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        StringBuffer buffer = new StringBuffer();
        GrowInfoModel model = models.get(position);
        viewHolder.text_event.setText(model.getPointItemName());
        String[] time = model.getCreateTime().replace("/", "-").split(":");
        viewHolder.text_time.setText(time[0] + ":" + time[1]);
        if (model.getExperiencePoints() > 0) {
            buffer.append("经验值+" + model.getExperiencePoints());
        }
        if (model.getRequtationPoints() > 0) {
            buffer.append("   积分值+" + model.getRequtationPoints());
        }
        viewHolder.text_info.setText(buffer.toString());

        return convertView;
    }

    class ViewHolder {
        TextView text_event, text_time, text_info;
    }
}
