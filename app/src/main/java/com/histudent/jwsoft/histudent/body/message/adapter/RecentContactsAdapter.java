package com.histudent.jwsoft.histudent.body.message.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.view.DragView;
import com.netease.nimlib.sdk.msg.model.RecentContact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/8/17.
 */
public class RecentContactsAdapter extends BaseAdapter {
    List<DragView> views = new ArrayList<DragView>();
    private List<RecentContact> datas;
    private Activity activity;

    public RecentContactsAdapter(Activity activity, List<RecentContact> datas) {
        this.activity = activity;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        VHoldr holder = null;
        if (convertView == null) {
            holder = new VHoldr();
            convertView = View.inflate(activity, R.layout.class_center_log_item, null);
            holder.recent_contacts_headImage = (ImageView) convertView.findViewById(R.id.recent_contacts_headImage);
            holder.recent_contacts_content = (TextView) convertView.findViewById(R.id.recent_contacts_content);
            holder.recent_contacts_title = (TextView) convertView.findViewById(R.id.recent_contacts_title);
            holder.recent_contacts_time = (TextView) convertView.findViewById(R.id.recent_contacts_time);
            convertView.setTag(holder);
            DragView view = (DragView) convertView.findViewById(R.id.drag_view);
            views.add(view);
            view.setOnDragStateListener(new DragView.DragStateListener() {
                @Override
                public void onOpened(DragView dragView) {

                }

                @Override
                public void onClosed(DragView dragView) {

                }

                @Override
                public void onForegroundViewClick(DragView dragView, View v) {
                    int pos = (int) dragView.getTag();
                    Toast.makeText(activity, "click item" + pos, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onBackgroundViewClick(DragView dragView, View v) {
                    int pos = (int) dragView.getTag();

                    close();
                    datas.remove(pos);
                    notifyDataSetChanged();
                    Toast.makeText(activity, ((Button) v).getText().toString() + pos, Toast.LENGTH_SHORT).show();
                }
            });


        } else {
            holder = (VHoldr) convertView.getTag();
        }

        RecentContact recentContact = datas.get(position);

        holder.recent_contacts_title.setText(recentContact.getFromNick());
        holder.recent_contacts_content.setText(recentContact.getContent());
        holder.recent_contacts_time.setText(recentContact.getTime() + "");


        return convertView;
    }

    class VHoldr {
        ImageView recent_contacts_headImage;
        TextView recent_contacts_content, recent_contacts_title, recent_contacts_time;
    }

    public void close() {
        for (int i = 0; i < views.size(); i++) {
            if (views.get(i).isOpen())
                views.get(i).closeAnim();
        }
    }

}

