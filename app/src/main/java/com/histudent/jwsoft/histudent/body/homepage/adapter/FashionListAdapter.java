package com.histudent.jwsoft.histudent.body.homepage.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.bean.SchoolHomeBean;
import com.histudent.jwsoft.histudent.body.homepage.bean.HomePageAllBean;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.info.persioninfo.activity.PersonCenterActivity;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2017/4/20.
 * 红人榜列表适配器
 */

public class FashionListAdapter extends BaseAdapter {

    private Activity activity;
    private List<Object> fashionBeans;

    public FashionListAdapter(Activity activity, List<Object> fashionBeans) {
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
            convertView = LayoutInflater.from(activity).inflate(R.layout.fragment_homepage_fashion_item, viewGroup, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Object object = fashionBeans.get(i);

        if (object instanceof HomePageAllBean.DataBean.RecommendUsersBean.ItemsBean) {
           final HomePageAllBean.DataBean.RecommendUsersBean.ItemsBean beanX = (HomePageAllBean.DataBean.RecommendUsersBean.ItemsBean) object;
            MyImageLoader.getIntent().displayNetImage(activity, beanX.getAvatar(), holder.fashion_head);
            holder.fashion_name.setText(beanX.getName());
            if(beanX.getUserType() == 1){
                //学生
                holder.isTeacherIdentity.setVisibility(View.INVISIBLE);
            }else{
                //老师
                holder.isTeacherIdentity.setVisibility(View.VISIBLE);
            }
            holder.fashion_name.setTextColor(activity.getResources().getColor(R.color.text_black_h3));
//            holder.fashion_name.setTextColor(activity.getResources().getColor(beanX.getUserType() == 1 ? R.color.text_black_h3 : R.color.yellow_color));
            holder.fashion_head.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PersonCenterActivity.start(activity, beanX.getUserId());
                }
            });
        } else if (object instanceof SchoolHomeBean.TopClassesBean) {
            SchoolHomeBean.TopClassesBean beanX = (SchoolHomeBean.TopClassesBean) object;
            MyImageLoader.getIntent().displayNetImage(activity, beanX.getLogo(), holder.fashion_head);
            holder.fashion_name.setText(beanX.getName());
            holder.fashion_name.setTextColor(activity.getResources().getColor(R.color.text_black_h1));
        }

        return convertView;
    }

    public static class ViewHolder {

        ImageView fashion_head;
        TextView fashion_name;
        View isTeacherIdentity;

        public ViewHolder(View view) {
            this.fashion_head = (ImageView) view.findViewById(R.id.fashion_head);
            this.fashion_name = (TextView) view.findViewById(R.id.fashion_name);
            this.isTeacherIdentity = view.findViewById(R.id.iv_teacher_identity);
        }
    }

}
