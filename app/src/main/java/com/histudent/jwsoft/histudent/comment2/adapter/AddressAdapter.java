package com.histudent.jwsoft.histudent.comment2.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.model.AreaCodeModel;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;

import java.util.List;

/**
 * 选择地址界面，社群，活动
 * Created by ZhangYT on 2016/12/5.
 */

public class AddressAdapter extends BaseAdapter {
    private List<Object> list_city_sort;
    private Activity activity;

    public AddressAdapter(Activity activity, List<Object> list) {
        this.list_city_sort = list;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return list_city_sort.size();
    }

    @Override
    public Object getItem(int position) {
        return list_city_sort.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public boolean isEnabled(int position) {

        if (list_city_sort.get(position) instanceof String) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Object object = list_city_sort.get(position);
        if (!(object instanceof AreaCodeModel)) {


            if (object.toString().equals("1")){
                convertView = View.inflate(activity, R.layout.default_item, null);

                TextView tv = ((TextView) convertView.findViewById(R.id.tv));
                tv.setPadding(0, SystemUtil.dp2px(100),0,0);
                tv.setText(R.string.default_no_searched_city);
            }else {
                convertView = View.inflate(activity, R.layout.address_divider, null);
                TextView tv = ((TextView) convertView.findViewById(R.id.tv_));
                tv.setText(((char) object) + "");
            }


        } else {
            convertView = View.inflate(activity, R.layout.sort_group_item, null);
            TextView tv = ((TextView) convertView.findViewById(R.id.tv));
            convertView.findViewById(R.id.iv).setVisibility(View.GONE);
            tv.setText(((AreaCodeModel) object).getName());

        }

        return convertView;
    }
}
