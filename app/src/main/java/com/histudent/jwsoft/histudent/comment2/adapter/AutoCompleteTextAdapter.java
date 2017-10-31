package com.histudent.jwsoft.histudent.comment2.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;

import java.util.List;


/**
 * Created by ZhangYT on 2016/10/19.
 */

public class AutoCompleteTextAdapter extends BaseAdapter {

    private List<PoiItem> list;
    private Context context;

    public AutoCompleteTextAdapter(Context context, List<PoiItem> list) {
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

        convertView = View.inflate(context, R.layout.address_item, null);

        TextView tv_address = ((TextView) convertView.findViewById(R.id.tv_));
        TextView tv_address_detail = ((TextView) convertView.findViewById(R.id.tv_detail));


        if (list.get(position).getLatLonPoint() == null) {

            tv_address.setText("不显示位置");
            tv_address.setMinHeight(SystemUtil.dp2px(40));
            tv_address_detail.setVisibility(View.GONE);
        } else {
            tv_address.setText(list.get(position).getTitle());
            tv_address_detail.setText(list.get(position).getSnippet());
        }


        return convertView;
    }
}
