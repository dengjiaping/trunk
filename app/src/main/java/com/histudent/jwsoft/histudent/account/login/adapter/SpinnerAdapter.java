package com.histudent.jwsoft.histudent.account.login.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.model.SaveAccountModel;

import java.util.List;


/**
 * Created by liuguiyu-pc on 2016/5/31.
 */
public class SpinnerAdapter extends BaseAdapter {

    private List<SaveAccountModel> list;
    private Activity activity = null;

    /**
     * 自定义构造方法
     *
     * @param activity
     * @param list
     */
    public SpinnerAdapter(Activity activity, List<SaveAccountModel> list) {
        this.activity = activity;
        this.list = list;
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            //下拉项布局
            convertView = LayoutInflater.from(activity).inflate(R.layout.spinner_item, null);
            holder.textView = (TextView) convertView.findViewById(R.id.item_text);
            holder.imageView = (ImageView) convertView.findViewById(R.id.item_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final SaveAccountModel model = list.get(position);
        holder.textView.setText(model.getAccount());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HiCache.getInstance().deletAccountDataInDB(model);
                list.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView textView;
        ImageView imageView;
    }
}





