package com.histudent.jwsoft.histudent.body.mine.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.mine.model.UserClassListModel;

import java.util.List;

/**
 * Created by liuguiyu on 2016/6/16.
 */
public class PopupClassesAdapter extends BaseAdapter {
    private List<UserClassListModel> classes;
    private Context context;

    public PopupClassesAdapter(Context context, List<UserClassListModel> classes) {
        this.classes = classes;
        this.context = context;
    }

    @Override
    public int getCount() {
        return classes.size();
    }

    @Override
    public Object getItem(int position) {
        return classes.get(position);
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
            convertView = View.inflate(context, R.layout.popupwindow_classes_item, null);

            viewHolder.className = (TextView) convertView.findViewById(R.id.popup_classNmae);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        UserClassListModel bean = classes.get(position);

        String schoolName = bean.getSchoolName();

        viewHolder.className.setText((TextUtils.isEmpty(schoolName) ? "" : schoolName) + bean.getGradeName() + bean.getCName());

        return convertView;
    }

    class ViewHolder {
        private TextView className;
    }
}
