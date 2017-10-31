package com.histudent.jwsoft.histudent.body.myclass.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.myclass.bean.ActivityStarsBean;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2017/4/20.
 * 徽章,活跃之星列表适配器
 */

public class BadgeListAdapter extends BaseAdapter {

    private Activity activity;
    private List<ActivityStarsBean> fashionBeans;

    public BadgeListAdapter(Activity activity, List<ActivityStarsBean> fashionBeans) {
        this.activity = activity;
        this.fashionBeans = fashionBeans;
    }

    @Override
    public int getCount() {
        return fashionBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return fashionBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.adapter_badge_item, viewGroup, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ActivityStarsBean beanX = fashionBeans.get(i);
        MyImageLoader.getIntent().displayNetImage(activity, beanX.getAvatar(), holder.fashion_head);
        holder.fashion_name.setText(beanX.getRealName());

        return convertView;
    }

    public static class ViewHolder {

        HiStudentHeadImageView fashion_head;
        TextView fashion_name;

        public ViewHolder(View view) {
            this.fashion_head = (HiStudentHeadImageView) view.findViewById(R.id.fashion_head);
            this.fashion_name = (TextView) view.findViewById(R.id.fashion_name);
        }
    }

}
