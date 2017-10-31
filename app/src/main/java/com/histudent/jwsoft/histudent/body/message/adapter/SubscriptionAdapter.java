

package com.histudent.jwsoft.histudent.body.message.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;

import java.util.List;
import java.util.Map;

/**
 * Created by liuguiyu-pc on 2016/6/1.
 */
public class SubscriptionAdapter extends BaseAdapter {

    private Activity activity;
    private List<Map<String, String>> users;


    public SubscriptionAdapter(Activity activity, List<Map<String, String>> datas) {
        this.activity = activity;
        this.users = datas;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
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
            convertView = LayoutInflater.from(activity).inflate(R.layout.subscription_list_item, null);

            holder.class_name = (TextView) convertView.findViewById(R.id.class_name);
            holder.class_introduce = (TextView) convertView.findViewById(R.id.class_introduce);
            holder.class_image = (ImageView) convertView.findViewById(R.id.class_image);

            convertView.setTag(holder);
        } else {
            holder = (ChatHolder) convertView.getTag();
        }
        Map<String, String> map = users.get(position);

        holder.class_name.setText(map.get("class_name"));
        holder.class_introduce.setText(map.get("class_introduce"));

        return convertView;
    }

    class ChatHolder {
        TextView class_name, class_introduce;
        ImageView class_image;
    }
}
