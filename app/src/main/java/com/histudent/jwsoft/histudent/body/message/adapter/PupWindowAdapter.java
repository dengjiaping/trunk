package com.histudent.jwsoft.histudent.body.message.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.model.CategoriesModel;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/8/4.
 * 关注好友页面弹窗的适配器
 */
public class PupWindowAdapter extends BaseAdapter {

    private Context context;
    private List<CategoriesModel> models;
    private Handler handler;

    public PupWindowAdapter(Context context, List<CategoriesModel> models, Handler handler) {

        this.context = context;
        this.models = models;
        this.handler = handler;

    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int position) {
        return models.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {

            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.myfriends_pupwindow_item, null);
            holder.text_name = (TextView) convertView.findViewById(R.id.text_name);
            holder.text_num = (TextView) convertView.findViewById(R.id.text_num);
            holder.popup_01 = (RelativeLayout) convertView.findViewById(R.id.popup_01);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final CategoriesModel model = models.get(position);

        holder.text_name.setText(model.getName());
        holder.text_num.setText("（" + model.getCateUserNum() + "）");

        holder.popup_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Message message = handler.obtainMessage();
                message.what = 400;
                message.obj = model;
                handler.sendMessage(message);
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView text_name, text_num;
        RelativeLayout popup_01;
    }

}
