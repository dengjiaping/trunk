package com.histudent.jwsoft.histudent.body.find.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.bean.FindHomeModel;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.CircleImageView;
import com.histudent.jwsoft.histudent.comment2.bean.TypeBean;

import java.util.List;

/**
 * Created by ZhangYT on 2016/6/16.
 */
public class GroupSecondCategoryAdapter extends BaseAdapter {
    private List<Object> list;
    private Context context;
    private String tagId;

    public GroupSecondCategoryAdapter(List<Object> list, Context context, String tagId) {
        this.context = context;
        this.list = list;
        this.tagId = tagId;
    }


    public GroupSecondCategoryAdapter(List<Object> list, Context context) {
        this.context = context;
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
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;

        Object object = list.get(position);
        if (convertView == null) {

            convertView = View.inflate(context, R.layout.activity_type_list_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = ((ViewHolder) convertView.getTag());
        }


        if (object instanceof TypeBean) {
            TypeBean dataBean = ((TypeBean) object);
            CommonGlideImageLoader.getInstance().displayNetImage(context, dataBean.getIconUrl(), viewHolder.iv);
            if (dataBean.getId().equals(tagId)) {
                viewHolder.iv.setSelected(true);
                viewHolder.tv.setTextColor(context.getResources().getColor(R.color.text_blue));
            }

            viewHolder.tv.setText(dataBean.getName());
        } else if (object instanceof FindHomeModel.GroupCategorysBean) {
            FindHomeModel.GroupCategorysBean dataBean = ((FindHomeModel.GroupCategorysBean) object);

            viewHolder.iv.setBackgroundColor(Color.WHITE);
            viewHolder.tv.setTextSize(13);
            CommonGlideImageLoader.getInstance().displayNetImage(context, dataBean.getIconUrl(), viewHolder.iv);
            viewHolder.tv.setText(dataBean.getName());
        }

        return convertView;
    }

    class ViewHolder {
        private CircleImageView iv;
        private TextView tv;

        public ViewHolder(View convertView) {
            iv = convertView.findViewById(R.id.badge_img);
            tv = convertView.findViewById(R.id.badge);
        }
    }

}
