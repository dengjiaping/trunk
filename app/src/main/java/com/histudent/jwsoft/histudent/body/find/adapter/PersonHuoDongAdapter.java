package com.histudent.jwsoft.histudent.body.find.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.bean.PersonalHuoDongBean;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.comment2.utils.TimeUtils;
import com.histudent.jwsoft.histudent.manage.PhotoManager;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.List;


/**
 * Created by ZhangYT on 2016/6/16.
 */
public class PersonHuoDongAdapter extends BaseAdapter {
    private List<PersonalHuoDongBean> list;
    private Context context;
    private int type;

    public PersonHuoDongAdapter(List<PersonalHuoDongBean> list, Context context) {
        this.list = list;
        this.context = context;
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
        ViewHolder viewHolder = null;

        convertView = View.inflate(context, R.layout.my_acitvity_list_item, null);
        viewHolder = new ViewHolder(convertView);

        PersonalHuoDongBean dataBean = list.get(position);

        viewHolder.tv_title.setText(dataBean.getName());

        viewHolder.rb_time.setText(TimeUtils.getFormateStartTimeAndEndTime(dataBean.getStartTime(), dataBean.getEndTime()));
        viewHolder.rb_num.setText(dataBean.getSignUpNum() + "");

        if (!StringUtil.isEmpty(dataBean.getCoverUrl())) {
            CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(context, dataBean.getCoverUrl(),
                    viewHolder.iv_activity, PhotoManager.getInstance().getDefaultPlaceholderResource());
        }
        switch (dataBean.getStatus()) {

            //报名中
            case 0:
                viewHolder.tv_tag.setBackgroundResource(R.drawable.same_city_now);
                viewHolder.tv_tag.setText("正在报名");
                break;

            //进行中
            case 1:
                viewHolder.tv_tag.setBackgroundResource(R.drawable.same_city_last);
                viewHolder.tv_tag.setText("正在进行");
                break;

            //已结束
            case 2:
                viewHolder.tv_tag.setBackgroundResource(R.drawable.same_city_gone);
                viewHolder.tv_tag.setText("已结束");
                break;

            //已报满
            case 3:
                viewHolder.tv_tag.setBackgroundResource(R.drawable.same_city_full);
                viewHolder.tv_tag.setText("已报满");
                break;


        }

        if (dataBean.getIsCreate()) {

            viewHolder.greate_tag.setText("我发起的");
            viewHolder.greate_tag.setTextColor(context.getResources().getColor(R.color.green_color));
            viewHolder.greate_tag.setBackground(context.getResources().getDrawable(R.drawable.green_bg));
        } else {
            viewHolder.greate_tag.setText("我参与的");
            viewHolder.greate_tag.setTextColor(context.getResources().getColor(R.color.same_city_now_bg));
            viewHolder.greate_tag.setBackground(context.getResources().getDrawable(R.drawable.yellow_bg));
        }

        return convertView;
    }

    class ViewHolder {
        private ImageView iv_activity;
        private TextView tv_title, tv_tag;
        private TextView rb_time, greate_tag, rb_num;
        private LinearLayout linearLayout;

        public ViewHolder(View convertView) {
            iv_activity = ((ImageView) convertView.findViewById(R.id.same_city_list_item_iv));
            tv_title = ((TextView) convertView.findViewById(R.id.same_city_list_item_tv_name));
            tv_tag = ((TextView) convertView.findViewById(R.id.same_city_list_item_btn_tag));
            greate_tag = ((TextView) convertView.findViewById(R.id.greate_tag));
            rb_time = ((TextView) convertView.findViewById(R.id.same_city_list_item_rb_time));
            rb_num = ((TextView) convertView.findViewById(R.id.same_city_list_item_rb_num));
            linearLayout = ((LinearLayout) convertView.findViewById(R.id.linearLayout));
        }
    }

}
