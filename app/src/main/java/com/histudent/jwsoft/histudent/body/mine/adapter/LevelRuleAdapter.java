package com.histudent.jwsoft.histudent.body.mine.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.mine.model.RankListModel;

import java.util.List;

/**
 * Created by liuguiyu on 2016/6/16.
 */
public class LevelRuleAdapter extends BaseAdapter {
    private List<RankListModel> models;
    private Context context;

    public LevelRuleAdapter(Context context, List<RankListModel> models) {
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
            convertView = View.inflate(context, R.layout.levelrule_item, null);

            viewHolder.text_left = (TextView) convertView.findViewById(R.id.level_rule_item_left);
            viewHolder.text_right = (TextView) convertView.findViewById(R.id.level_rule_item_right);
            viewHolder.text_middle = (TextView) convertView.findViewById(R.id.level_rule_item_middle);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        RankListModel model = models.get(position);
        viewHolder.text_left.setText(model.getRank() + "");
        viewHolder.text_right.setText(model.getPointLower() + "");
        viewHolder.text_middle.setText("LV." + model.getRank());

        return convertView;
    }

    class ViewHolder {
        TextView text_left, text_right, text_middle;
    }
}
