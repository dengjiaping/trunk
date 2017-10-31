package com.histudent.jwsoft.histudent.body.myclass.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.myclass.bean.GrouthTaskModel;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2017/4/20.
 * 成长任务适配器
 */

public class GrouthTaskAdapter extends BaseAdapter {

    private Activity activity;
    private List<GrouthTaskModel.TaskListBean> fashionBeans;

    public GrouthTaskAdapter(Activity activity, List<GrouthTaskModel.TaskListBean> fashionBeans) {
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
            convertView = LayoutInflater.from(activity).inflate(R.layout.grouth_task_item, viewGroup, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GrouthTaskModel.TaskListBean beanX = fashionBeans.get(i);
        MyImageLoader.getIntent().displayNetImage(activity, beanX.getImg(), holder.log);
        holder.tv_name.setText(beanX.getTitle());
        holder.tv_instr.setText(beanX.getContent());

        return convertView;
    }

    public static class ViewHolder {

        HiStudentHeadImageView log;
        TextView tv_name, tv_instr, btn;

        public ViewHolder(View view) {
            this.log = (HiStudentHeadImageView) view.findViewById(R.id.log);
            this.tv_name = (TextView) view.findViewById(R.id.tv_name);
            this.tv_instr = (TextView) view.findViewById(R.id.tv_instr);
            this.btn = (TextView) view.findViewById(R.id.btn);
        }
    }

}
