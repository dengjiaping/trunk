package com.histudent.jwsoft.histudent.body.message.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.model.NoticeModel;

import java.util.List;

/**
 * Created by liuguiyu on 2016/6/16.
 */
public class ClassNoticeAdapter extends BaseAdapter {
    private List<NoticeModel> maps;
    private Context context;

    public ClassNoticeAdapter(Context context, List<NoticeModel> maps) {
        this.maps = maps;
        this.context = context;
    }

    @Override
    public int getCount() {
        return maps.size();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        return maps.get(position);
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
            convertView = View.inflate(context, R.layout.class_center_notice_item, null);

            viewHolder.class_center_log_item_days = (TextView) convertView.findViewById(R.id.class_center_log_item_days);
            viewHolder.class_center_log_item_months = (TextView) convertView.findViewById(R.id.class_center_log_item_months);
            viewHolder.class_center_log_item_text = (TextView) convertView.findViewById(R.id.class_center_log_item_text);
            viewHolder.class_center_log_item_introduction = (TextView) convertView.findViewById(R.id.class_center_log_item_introduction);
            viewHolder.class_center_log_item_time = (TextView) convertView.findViewById(R.id.class_center_log_item_time);
            viewHolder.class_center_log_item_content = (TextView) convertView.findViewById(R.id.class_center_log_item_content);
            viewHolder.class_center_log_item_image = (LinearLayout) convertView.findViewById(R.id.class_center_log_item_image);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = ((ViewHolder) convertView.getTag());
        }

        NoticeModel model = maps.get(position);

        if (model != null) {
            viewHolder.class_center_log_item_days.setText(model.getCreateTime().split(" ")[0].split("-")[2]);
            viewHolder.class_center_log_item_months.setText(exchangeNum(model.getCreateTime().split(" ")[0].split("-")[1]));
            viewHolder.class_center_log_item_text.setText(model.getTitle());
            viewHolder.class_center_log_item_content.setText(model.getContent());
        }

        return convertView;
    }

    class ViewHolder {
        private TextView class_center_log_item_days, class_center_log_item_months, class_center_log_item_text,
                class_center_log_item_introduction, class_center_log_item_time, class_center_log_item_content;
        private LinearLayout class_center_log_item_image;
    }

    private String exchangeNum(String months) {
        String info = "";
        if ("1".equals(months) || "01".equals(months)) {
            info = "一月";
        } else if ("2".equals(months) || "02".equals(months)) {
            info = "二月";
        } else if ("3".equals(months) || "03".equals(months)) {
            info = "三月";
        } else if ("4".equals(months) || "04".equals(months)) {
            info = "四月";
        } else if ("5".equals(months) || "05".equals(months)) {
            info = "五月";
        } else if ("6".equals(months) || "06".equals(months)) {
            info = "六月";
        } else if ("7".equals(months) || "07".equals(months)) {
            info = "七月";
        } else if ("8".equals(months) || "08".equals(months)) {
            info = "八月";
        } else if ("9".equals(months) || "09".equals(months)) {
            info = "九月";
        } else if ("10".equals(months)) {
            info = "十月";
        } else if ("11".equals(months)) {
            info = "十一月";
        } else if ("12".equals(months)) {
            info = "十二月";
        }
        return info;
    }
}
