package com.histudent.jwsoft.histudent.comment2.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.comment2.bean.ReportReasonModel;

import java.util.List;

/**
 * Created by ZhangYT on 2017/6/2.
 */

public class ReportResonSelectAdapter extends BaseAdapter {

    private List<ReportReasonModel> list;
    private Context context;

    public ReportResonSelectAdapter(List<ReportReasonModel> list, Context context) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.report_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = ((ViewHolder) convertView.getTag());
        }
        viewHolder.tv_instr.setText(list.get(position).getReason());
        return convertView;
    }

    public class ViewHolder {

        private TextView tv_instr;

        public ViewHolder(View convertView) {

            tv_instr = ((TextView) convertView.findViewById(R.id.tv_instr));
        }
    }

}
