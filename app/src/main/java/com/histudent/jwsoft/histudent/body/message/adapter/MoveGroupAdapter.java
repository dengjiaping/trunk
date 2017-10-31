package com.histudent.jwsoft.histudent.body.message.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.model.CategoriesModel;

import java.util.List;

import static com.histudent.jwsoft.histudent.R.id.moveGroup_list_item_selected;

/**
 * Created by liuguiyu-pc on 2016/8/29.
 */
public class MoveGroupAdapter extends BaseAdapter {
    private List<CategoriesModel> models_all;
    private List<String> ids_our;
    private Activity activity;

    public MoveGroupAdapter(Activity activity, List<CategoriesModel> models_all, List<String> ids_our) {
        this.models_all = models_all;
        this.ids_our = ids_our;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return models_all.size();
    }

    @Override
    public Object getItem(int i) {
        return models_all.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder;

        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(activity).inflate(R.layout.movegroup_list_item, null);
            holder.moveGroup_list_item_text = (TextView) view.findViewById(R.id.moveGroup_list_item_text);
            holder.moveGroup_list_item_selected = (CheckBox) view.findViewById(moveGroup_list_item_selected);
            holder.moveGroup_list_item = (RelativeLayout) view.findViewById(R.id.moveGroup_list_item);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final CategoriesModel model = models_all.get(i);
        holder.moveGroup_list_item_selected.setChecked(ids_our.contains(model.getCategoryId()));
        holder.moveGroup_list_item_text.setText(model.getName() + "(" + model.getCateUserNum() + ")");

        holder.moveGroup_list_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean flag = holder.moveGroup_list_item_selected.isChecked();
                if (flag) {
                    holder.moveGroup_list_item_selected.setChecked(false);
                    ids_our.remove(model.getCategoryId());
                } else {
                    holder.moveGroup_list_item_selected.setChecked(true);
                    ids_our.add(model.getCategoryId());
                }
            }
        });

        return view;
    }

    class ViewHolder {
        TextView moveGroup_list_item_text;
        CheckBox moveGroup_list_item_selected;
        RelativeLayout moveGroup_list_item;
    }

}
