package com.histudent.jwsoft.histudent.comment2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.model.SortModel;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;

import java.util.List;

public class SelectClassmateAapter extends BaseAdapter {
    private List<SortModel> list = null;
    private Context mContext;

    public SelectClassmateAapter(Context mContext, List<SortModel> list) {
        this.mContext = mContext;
        this.list = list;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<SortModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        final SortModel mContent = list.get(position);
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.selet_classmate_item, null);

            viewHolder = new ViewHolder();
            viewHolder.button_cancle = (Button) view.findViewById(R.id.button);
            viewHolder.imageView = (HiStudentHeadImageView) view.findViewById(R.id.image_left);
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.name);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.imageView.loadBuddyAvatar(mContent.getAvatar());
        viewHolder.tvTitle.setText(mContent.getName());

        return view;

    }


    final static class ViewHolder {
        TextView tvTitle;
        Button button_cancle;
        HiStudentHeadImageView imageView;
    }

}