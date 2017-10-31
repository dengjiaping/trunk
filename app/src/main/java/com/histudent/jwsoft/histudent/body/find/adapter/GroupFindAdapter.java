package com.histudent.jwsoft.histudent.body.find.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.activity.CommunityCategoryActivity;
import com.histudent.jwsoft.histudent.body.find.activity.GroupCenterActivity;
import com.histudent.jwsoft.histudent.body.find.bean.GroupFindBean;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.commen.view.MyListView2;

import java.util.List;

/**
 * 发现社群适配器
 * Created by ZhangYT on 2016/8/11.
 */
public class GroupFindAdapter extends BaseAdapter {

    private List<GroupFindBean> list;
    private Activity context;

    public GroupFindAdapter(List<GroupFindBean> list, Activity context) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.adapter_groupfind_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = ((ViewHolder) convertView.getTag());
        }

        final GroupFindBean bean = list.get(position);
        viewHolder.tag.setText(bean.getName());

        GroupFindItemAdapter itemAdapter = new GroupFindItemAdapter(bean.getGrorpModels(), context);
        viewHolder.listView2.setAdapter(itemAdapter);

        viewHolder.listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GroupCenterActivity.start(context, bean.getGrorpModels().get(i).getGroupId());
            }
        });

        viewHolder.go_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommunityCategoryActivity.start(context, bean.getTagId());
            }
        });

        return convertView;
    }

    class ViewHolder {
        private TextView tag;
        private MyListView2 listView2;
        private IconView go_more;

        public ViewHolder(View convertView) {
            tag = (TextView) convertView.findViewById(R.id.tag);
            listView2 = (MyListView2) convertView.findViewById(R.id.list);
            go_more = (IconView) convertView.findViewById(R.id.go_more);
        }
    }

}
