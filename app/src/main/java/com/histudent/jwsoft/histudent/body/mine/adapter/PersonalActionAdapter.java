package com.histudent.jwsoft.histudent.body.mine.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.mine.model.ActionModel;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/6/1.
 * 个人资料中社群和活动的adapter
 */
public class PersonalActionAdapter extends BaseAdapter {

    private Activity activity;
    private List<ActionModel> datas;
    private int num = 0;

    public PersonalActionAdapter(Activity activity, List<ActionModel> datas) {
        this.activity = activity;
        this.datas = datas;

    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatHolder holder = null;
        if (convertView == null) {
            holder = new ChatHolder();
            //下拉项布局
            convertView = LayoutInflater.from(activity).inflate(R.layout.personalaction_item, null);

            holder.action_image = (ImageView) convertView.findViewById(R.id.action_image);
            holder.action_more = (ImageView) convertView.findViewById(R.id.action_more);
            holder.action_name = (TextView) convertView.findViewById(R.id.action_name);

            convertView.setTag(holder);
        } else {
            holder = (ChatHolder) convertView.getTag();
        }
        ActionModel actionModel = datas.get(position);

        holder.action_name.setText(actionModel.getActionName());

        return convertView;
    }

    class ChatHolder {
        ImageView action_image, action_more;
        TextView action_name;
    }

}
