package com.histudent.jwsoft.histudent.body.homepage.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.homepage.bean.HomePageAllBean;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2017/4/20.
 * 热门话题列表适配器
 */

public class HotListAdapter extends BaseAdapter {

    private Activity activity;
    private List<HomePageAllBean.DataBean.RecommendTagsBean.ItemsBeanXX> hotBeans;

    public HotListAdapter(Activity activity, List<HomePageAllBean.DataBean.RecommendTagsBean.ItemsBeanXX> hotBeans) {
        this.activity = activity;
        this.hotBeans = hotBeans;
    }

    @Override
    public int getCount() {
        return hotBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return hotBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.fragment_homepage_hot_item, viewGroup, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HomePageAllBean.DataBean.RecommendTagsBean.ItemsBeanXX beanX = hotBeans.get(i);
        MyImageLoader.getIntent().displayNetImage(activity, beanX.getFeaturedImage(), holder.hot_image);
        holder.topic_tag.setText("#" + beanX.getTagName() + "#");

        return convertView;
    }

    public static class ViewHolder {

        ImageView hot_image;
        TextView topic_tag;

        public ViewHolder(View view) {
            this.hot_image = (ImageView) view.findViewById(R.id.hot_image);
            this.topic_tag = (TextView) view.findViewById(R.id.topic_tag);
        }

    }

}
