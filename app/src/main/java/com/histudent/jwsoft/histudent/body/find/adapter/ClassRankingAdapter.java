package com.histudent.jwsoft.histudent.body.find.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.bean.ClassRankBean;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;

import java.util.List;

/**
 * Created by liuguiyu on 2016/6/16.
 */
public class ClassRankingAdapter extends BaseAdapter {
    private List<ClassRankBean.ItemsBean> beans;
    private Context context;

    public ClassRankingAdapter(Context context, List<ClassRankBean.ItemsBean> beans) {
        this.beans = beans;
        this.context = context;
    }

    @Override
    public int getCount() {
        return beans.size();
    }

    @Override
    public Object getItem(int position) {
        return beans.get(position);
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
            convertView = View.inflate(context, R.layout.activity_action_ranking_class_item, null);

            viewHolder.index_image = convertView.findViewById(R.id.index);
            viewHolder.name_text = convertView.findViewById(R.id.name);
            viewHolder.index_num = convertView.findViewById(R.id.index_num);
            viewHolder.logNum = convertView.findViewById(R.id.logNum);
            viewHolder.pictureNum = convertView.findViewById(R.id.pictureNum);
            viewHolder.headImageView = convertView.findViewById(R.id.headImage);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ClassRankBean.ItemsBean bean = beans.get(position);

        int index = bean.getRankNum();
        viewHolder.index_num.setVisibility(View.GONE);
        viewHolder.index_image.setVisibility(View.GONE);

        switch (index) {
            case 1:
                viewHolder.index_image.setVisibility(View.VISIBLE);
                viewHolder.index_image.setImageResource(R.mipmap.award_01);
                break;
            case 2:
                viewHolder.index_image.setVisibility(View.VISIBLE);
                viewHolder.index_image.setImageResource(R.mipmap.award_02);
                break;
            case 3:
                viewHolder.index_image.setVisibility(View.VISIBLE);
                viewHolder.index_image.setImageResource(R.mipmap.award_03);
                break;
            default:
                viewHolder.index_num.setVisibility(View.VISIBLE);
                viewHolder.index_num.setText(index + "");
                break;
        }
        viewHolder.name_text.setText(bean.getName());
        viewHolder.logNum.setText(bean.getMemberNum() + "");
        viewHolder.pictureNum.setText(bean.getActivitiesCount() + "");
        MyImageLoader.getIntent().displayNetImage(context, bean.getLogo(), viewHolder.headImageView);

        return convertView;
    }

    class ViewHolder {
        private TextView name_text, logNum, pictureNum, index_num;
        private ImageView index_image;
        private HiStudentHeadImageView headImageView;
    }

}
