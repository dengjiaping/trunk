package com.histudent.jwsoft.histudent.body.mine.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.mine.model.SpecialBean;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;

import java.util.List;

/**
 * Created by liuguiyu on 2016/6/16.
 * 等级特权适配器
 */
public class GridViewAdapter extends BaseAdapter {
    private List<SpecialBean> beens;
    private Context context;

    public GridViewAdapter(Context context, List<SpecialBean> beens) {
        this.beens = beens;
        this.context = context;
    }

    @Override
    public int getCount() {
        return beens.size();
    }

    @Override
    public Object getItem(int position) {
        return beens.get(position);
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
            convertView = View.inflate(context, R.layout.adapter_grade_item, null);
            viewHolder.layout = (LinearLayout) convertView.findViewById(R.id.layout);
            viewHolder.imag = (ImageView) convertView.findViewById(R.id.imag);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        SpecialBean bean = beens.get(position);

        int roundRadius = 10; // 8dp 圆角半径
        int fillColor = Color.parseColor(bean.getBgColor());//内部填充颜色
        GradientDrawable gd = new GradientDrawable();//创建drawable
        gd.setColor(fillColor);
        gd.setCornerRadius(roundRadius);

        MyImageLoader.getIntent().displayNetImage(context, bean.getCover(), viewHolder.imag);
        viewHolder.layout.setBackground(gd);
        viewHolder.name.setText(bean.getName());

        return convertView;
    }

    class ViewHolder {
        LinearLayout layout;
        ImageView imag;
        TextView name;
    }
}
