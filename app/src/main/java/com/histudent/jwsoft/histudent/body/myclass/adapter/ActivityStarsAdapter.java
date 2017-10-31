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
 * 班级圈活跃之星适配器
 */

public class ActivityStarsAdapter extends BaseAdapter {

    private Activity activity;
    private List<ActivityStarsBean> fashionBeans;

    public ActivityStarsAdapter(Activity activity, List<ActivityStarsBean> fashionBeans) {
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
            convertView = LayoutInflater.from(activity).inflate(R.layout.adapter_activitystars_item, viewGroup, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ActivityStarsBean beanX = fashionBeans.get(i);
        MyImageLoader.getIntent().displayNetImage(activity, beanX.getAvatar(), holder.headImageView);
        holder.name.setText(beanX.getRealName());
        holder.num.setText(beanX.getClassActivityCount() > 10000 ? "10000+" : beanX.getClassActivityCount() + "");
        holder.time.setText(beanX.getLoginTimeLength());
        return convertView;
    }

    public static class ViewHolder {

        HiStudentHeadImageView headImageView;
        TextView name, num, time;

        public ViewHolder(View view) {
            this.headImageView = (HiStudentHeadImageView) view.findViewById(R.id.hendImage);
            this.name = (TextView) view.findViewById(R.id.name);
            this.num = (TextView) view.findViewById(R.id.num);
            this.time = (TextView) view.findViewById(R.id.time);
        }

    }

}
