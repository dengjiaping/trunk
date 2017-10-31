package com.histudent.jwsoft.histudent.comment2.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.comment2.bean.AddressInforBean;

import java.util.List;

/**
 * Created by ZhangYT on 2016/7/7.
 */
public class AddressAdpater extends BaseAdapter{

    private List<AddressInforBean>list;
    private Context context;

    public AddressAdpater(List<AddressInforBean>list, Context context){
        this.list=list;
        this.context=context;
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
        if(convertView==null){
            convertView=View.inflate(context,R.layout.address_item,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= ((ViewHolder) convertView.getTag());
        }

        viewHolder.tv.setText(list.get(position).getName());
        viewHolder.tv_detail.setText(list.get(position).getDetailStr());
        return convertView;
    }

    class ViewHolder{

        private TextView tv,tv_detail;

        public ViewHolder(View convertView){
            tv_detail= ((TextView) convertView.findViewById(R.id.tv_detail));
            tv= ((TextView) convertView.findViewById(R.id.tv_));
        }
    }
}
