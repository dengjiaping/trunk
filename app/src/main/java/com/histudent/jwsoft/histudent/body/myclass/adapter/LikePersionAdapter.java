package com.histudent.jwsoft.histudent.body.myclass.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.myclass.bean.LikeBean;
import com.histudent.jwsoft.histudent.body.myclass.bean.NoticeDetailBean;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.comment2.utils.TimeUtils;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2017/4/20.
 * 点赞人列表适配器
 */

public class LikePersionAdapter extends BaseAdapter {

    private Activity activity;
    private List<Object> fashionBeans;

    public LikePersionAdapter(Activity activity, List<Object> fashionBeans) {
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
            convertView = LayoutInflater.from(activity).inflate(R.layout.adapter_likepersion_item, viewGroup, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Object object = fashionBeans.get(i);
        if (object instanceof LikeBean.ItemsBean) {
            LikeBean.ItemsBean beanX = (LikeBean.ItemsBean) fashionBeans.get(i);
            MyImageLoader.getIntent().displayNetImage(activity, beanX.getAvatar(), holder.like_headImage);
            holder.like_name.setText(beanX.getName());
            holder.like_time.setText(TimeUtils.exchangeTime(beanX.getPraiseTime()));
        } else if (object instanceof NoticeDetailBean.ReadUserListBean) {
            NoticeDetailBean.ReadUserListBean beanX = (NoticeDetailBean.ReadUserListBean) fashionBeans.get(i);
            MyImageLoader.getIntent().displayNetImage(activity, beanX.getAvatar(), holder.like_headImage);
            holder.like_name.setText(beanX.getRealName());
            holder.like_time.setText(TimeUtils.exchangeTime(beanX.getReadTime()));
        } else if (object instanceof NoticeDetailBean.UnReadUserListBean) {
            NoticeDetailBean.UnReadUserListBean beanX = (NoticeDetailBean.UnReadUserListBean) fashionBeans.get(i);
            MyImageLoader.getIntent().displayNetImage(activity, beanX.getAvatar(), holder.like_headImage);
            holder.like_name.setText(beanX.getRealName());
            holder.like_time.setText("");
        }

        return convertView;
    }

    public static class ViewHolder {

        HiStudentHeadImageView like_headImage;
        TextView like_name, like_time;

        public ViewHolder(View view) {
            this.like_headImage = (HiStudentHeadImageView) view.findViewById(R.id.like_headImage);
            this.like_name = (TextView) view.findViewById(R.id.like_name);
            this.like_time = (TextView) view.findViewById(R.id.like_time);
        }
    }

}
