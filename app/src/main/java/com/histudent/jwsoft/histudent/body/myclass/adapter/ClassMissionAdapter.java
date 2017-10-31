package com.histudent.jwsoft.histudent.body.myclass.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.model.ClassTemberModel;
import com.histudent.jwsoft.histudent.commen.view.IconView;

import java.util.List;


/**
 * 成长任务适配器
 */
public class ClassMissionAdapter extends BaseAdapter {

    private Context context;
    private List<ClassTemberModel> list;

    public ClassMissionAdapter(Activity context, List<ClassTemberModel> list) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.adapter_mission_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = ((ViewHolder) convertView.getTag());
        }

        return convertView;
    }

    class ViewHolder {
        private IconView icon_mission;
        private TextView mission_name, mission_content, mission_statue;

        public ViewHolder(View convertView) {
            icon_mission = (IconView) convertView.findViewById(R.id.icon_mission);
            mission_name = ((TextView) convertView.findViewById(R.id.mission_name));
            mission_content = ((TextView) convertView.findViewById(R.id.mission_content));
            mission_statue = ((TextView) convertView.findViewById(R.id.mission_statue));
        }

    }

    private void setListener(ViewHolder viewHolder, final ClassTemberModel teamMembe) {


    }

}
