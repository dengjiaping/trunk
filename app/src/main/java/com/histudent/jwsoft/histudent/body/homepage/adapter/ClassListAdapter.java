package com.histudent.jwsoft.histudent.body.homepage.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.homepage.bean.HomePageAllBean;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2017/4/20.
 * 班级列表适配器
 */

public class ClassListAdapter extends BaseAdapter {

    private Activity activity;
    private List<HomePageAllBean.DataBean.RecommendClassesBean.ItemsBeanX> classBeans;

    public ClassListAdapter(Activity activity, List<HomePageAllBean.DataBean.RecommendClassesBean.ItemsBeanX> classBeans) {
        this.activity = activity;
        this.classBeans = classBeans;
    }

    @Override
    public int getCount() {
        return classBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return classBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.fragment_homepage_class_item, viewGroup, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HomePageAllBean.DataBean.RecommendClassesBean.ItemsBeanX beanX = classBeans.get(i);
        MyImageLoader.getIntent().displayNetImage(activity, beanX.getLogo(), holder.headImageView);
        holder.class_school.setText(beanX.getSchoolName());
        holder.class_grade.setText(beanX.getGradeName() + beanX.getName());

        return convertView;
    }

    public static class ViewHolder {

        HiStudentHeadImageView headImageView;
        TextView class_school, class_grade;

        public ViewHolder(View view) {
            this.headImageView = (HiStudentHeadImageView) view.findViewById(R.id.class_logo);
            this.class_school = (TextView) view.findViewById(R.id.class_school);
            this.class_grade = (TextView) view.findViewById(R.id.class_grade);
        }
    }

}
