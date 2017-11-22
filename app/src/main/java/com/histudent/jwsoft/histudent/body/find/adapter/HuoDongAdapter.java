package com.histudent.jwsoft.histudent.body.find.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.bean.PersonalHuoDongBean;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.comment2.utils.TimeUtils;
import com.histudent.jwsoft.histudent.model.manage.PhotoManager;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.List;

/**
 * 同城活动页面中ListView的适配器
 * Created by ZhangYT on 2016/6/15.
 */

public class HuoDongAdapter extends BaseAdapter {
    private List<Object> list;
    private Context context;
    private int type;

    public HuoDongAdapter(List<Object> list, Context context, int type) {
        this.list = list;
        this.type = type;
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

        ViewHolder viewHolder;

        Object item = list.get(position);

        convertView = View.inflate(context, R.layout.same_activity_list_item, null);
        viewHolder = new ViewHolder(convertView);

        if (item instanceof PersonalHuoDongBean) {
            PersonalHuoDongBean bean = ((PersonalHuoDongBean) item);

            viewHolder.tv_title.setText(bean.getName());
            viewHolder.rb_time.setText(TimeUtils.getFormateStartTimeAndEndTime(bean.getStartTime(), bean.getEndTime()));
            viewHolder.rb_address.setText(bean.getPlace());


            switch (type) {

                //班级活动
                case 1:
                    viewHolder.rb_num.setText(bean.getSignUpNum() + "");
                    break;

                //社群活动
                case 2:

                    if (bean.getUserCost() == 0) {
                        viewHolder.rb_num.setText("[免费]");
                        viewHolder.icon_tag.setVisibility(View.INVISIBLE);
                    } else {
                        viewHolder.icon_tag.setVisibility(View.VISIBLE);
                        viewHolder.icon_tag.setText(context.getString(R.string.icon_rmb));
                        viewHolder.rb_num.setText(bean.getUserCost() + "/人");
                    }
                    break;
            }

            if (!StringUtil.isEmpty(bean.getCoverUrl())) {
                CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(context, bean.getCoverUrl(),
                        viewHolder.iv_activity, PhotoManager.getInstance().getDefaultPlaceholderResource());
            }


            //根据返回的标记改变活动标记颜色


            switch (bean.getStatus()) {

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

            if (bean.getIsSinuped()) {
                viewHolder.tag.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tag.setVisibility(View.GONE);
            }
        }


        return convertView;
    }

    class ViewHolder {
        private ImageView iv_activity;
        private TextView tv_title, tv_tag;
        private TextView rb_time, rb_address, rb_num;
        private LinearLayout linearLayout;
        private IconView tag, icon_tag;

        public ViewHolder(View convertView) {
            iv_activity = convertView.findViewById(R.id.same_city_list_item_iv);
            tv_title =  convertView.findViewById(R.id.same_city_list_item_tv_name);
            tv_tag = convertView.findViewById(R.id.same_city_list_item_btn_tag);
//            rb_type = ((RadioButton) convertView.findViewById(R.id.same_city_list_item_btn_type));
            rb_address =  convertView.findViewById(R.id.same_city_list_item_rb_address);
            rb_time =  convertView.findViewById(R.id.same_city_list_item_rb_time);
            rb_num =  convertView.findViewById(R.id.same_city_list_item_rb_num);
            linearLayout = convertView.findViewById(R.id.linearLayout);
            tag =  convertView.findViewById(R.id.tag);
            icon_tag = convertView.findViewById(R.id.incon_tag);
        }
    }

}
