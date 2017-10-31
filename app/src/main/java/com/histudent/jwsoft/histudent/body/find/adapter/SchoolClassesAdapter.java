package com.histudent.jwsoft.histudent.body.find.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.bean.SchoolClassesModel;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.List;

/**
 * Created by ZhangYT on 2017/5/18.
 */

public class SchoolClassesAdapter extends BaseAdapter {

    private Activity activity;
    private List<Object> list;

    public SchoolClassesAdapter(Activity activity, List<Object> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public boolean isEnabled(int position) {
        if (list.get(position) instanceof String) {
            return false;
        } else {
            return super.isEnabled(position);
        }
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
        Object item = list.get(position);

        if (item instanceof String) {

            convertView = LayoutInflater.from(activity).inflate(R.layout.my_group_item_tag, null);
            TextView tv_tag = ((TextView) convertView.findViewById(R.id.tv_tag));
            tv_tag.setTextColor(activity.getResources().getColor(R.color.text_gray_h1));
            tv_tag.setText((String) item);

        } else if (item instanceof SchoolClassesModel.ItemsBean) {
            convertView = View.inflate(activity, R.layout.school_classes_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
            SchoolClassesModel.ItemsBean classModel = ((SchoolClassesModel.ItemsBean) item);
            viewHolder.classMasker.setText(classModel.getTeacherName());
            viewHolder.className.setText(classModel.getGradeName()+classModel.getName());
            viewHolder.classMemberCount.setText(classModel.getMemberNum() + "");

            if (!StringUtil.isEmpty(classModel.getLogo())) {
                MyImageLoader.getIntent().displayNetImage(activity, classModel.getLogo(), viewHolder.classLog);
            }
        }

        return convertView;
    }

    class ViewHolder {

        //社群或者班级使用
        TextView className, classMemberCount, classMasker;
        HiStudentHeadImageView classLog;

        public ViewHolder(View convertView) {

            classLog = ((HiStudentHeadImageView) convertView.findViewById(R.id.class_log));
            classMasker = ((TextView) convertView.findViewById(R.id.class_masker));
            classMemberCount = ((TextView) convertView.findViewById(R.id.class_number));
            className = ((TextView) convertView.findViewById(R.id.class_name));
        }
    }

}
