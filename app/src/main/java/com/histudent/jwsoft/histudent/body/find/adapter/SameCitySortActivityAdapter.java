package com.histudent.jwsoft.histudent.body.find.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.body.find.bean.SameCitySortBean;

import java.util.List;

/**
 * 分类查找的popwindow的适配器
 * Created by ZhangYT on 2016/8/18.
 */
public class SameCitySortActivityAdapter extends BaseAdapter {

    private List<SameCitySortBean> list;
    private Context context;
    private int left, right;


    public SameCitySortActivityAdapter(List<SameCitySortBean> list, Context context, int left, int right) {

        this.list = list;
        this.context = context;
        this.left = left;
        this.right = right;
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

//        if (convertView == null) {

            convertView = View.inflate(context, R.layout.same_city_sort_activity_list_item, null);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = ((ViewHolder) convertView.getTag());
//        }


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SystemUtil.dp2px(50));
        params.leftMargin = left;
        params.rightMargin = right;
        viewHolder.tv.setLayoutParams(params);


        if (position == 0) {
            viewHolder.tv.setTextColor(Color.rgb(32, 126, 189));
        }
        viewHolder.tv.setText(list.get(position).getName());


        return convertView;
    }

    class ViewHolder {

        private TextView tv;

        public ViewHolder(View convertView) {
            tv = ((TextView) convertView.findViewById(R.id.tv));
        }
    }
}
