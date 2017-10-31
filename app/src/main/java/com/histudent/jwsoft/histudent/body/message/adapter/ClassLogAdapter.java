package com.histudent.jwsoft.histudent.body.message.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.view.CircleImageView;
import com.histudent.jwsoft.histudent.body.mine.adapter.GridViewImageAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by liuguiyu on 2016/6/16.
 */
public class ClassLogAdapter extends BaseAdapter {
    private List<Map<String, Object>> maps;
    private Activity context;

    public ClassLogAdapter(Activity context, List<Map<String, Object>> maps) {
        this.maps = maps;
        this.context = context;
    }

    @Override
    public int getCount() {
        return maps.size();
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
        GridViewImageAdapter adapter;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.class_center_log_item, null);

            viewHolder.class_center_log_name = (TextView) convertView.findViewById(R.id.class_center_log_name);
            viewHolder.class_center_log_time = (TextView) convertView.findViewById(R.id.class_center_log_time);
            viewHolder.class_center_log_sees = (TextView) convertView.findViewById(R.id.class_center_log_sees);
            viewHolder.class_center_log_title = (TextView) convertView.findViewById(R.id.class_center_log_title);
            viewHolder.class_center_log_content = (TextView) convertView.findViewById(R.id.class_center_log_content);
            viewHolder.item_comment = (Button) convertView.findViewById(R.id.item_comment);
            viewHolder.item_share = (Button) convertView.findViewById(R.id.item_share);
            viewHolder.item_like = (CheckBox) convertView.findViewById(R.id.item_like);
            viewHolder.grid_view_like = (GridView) convertView.findViewById(R.id.grid_view_like);
            viewHolder.class_center_log_picture = (ImageView) convertView.findViewById(R.id.class_center_log_picture);
            viewHolder.class_center_log_image = (CircleImageView) convertView.findViewById(R.id.class_center_log_image);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Map<String, Object> map = maps.get(position);

        viewHolder.class_center_log_name.setText(map.get("name").toString());
        viewHolder.class_center_log_time.setText(map.get("time").toString());
        viewHolder.class_center_log_sees.setText(map.get("sees").toString());
        viewHolder.class_center_log_title.setText(map.get("title").toString());
        viewHolder.class_center_log_content.setText(map.get("content").toString());
//        viewHolder.item_comment.setText(map.get("comment").toString());
//        viewHolder.item_share.setText(map.get("share").toString());
//        viewHolder.item_like.setText(map.get("like").toString());

        if ("-1".equals(map.get("picture_url").toString())) {
            viewHolder.class_center_log_picture.setVisibility(View.GONE);
        } else {
            viewHolder.class_center_log_picture.setImageResource(R.mipmap.def);
        }
        List<String> list_image = (List<String>) map.get("grid_view_like");

//        adapter = new GridViewImageAdapter(context, SystemUtil.doActionInData(list_image));

//        viewHolder.grid_view_like.setAdapter(adapter);

        return convertView;
    }

    class ViewHolder {
        TextView class_center_log_name, class_center_log_time, class_center_log_sees, class_center_log_title,
                class_center_log_content;
        CheckBox item_like;
        Button item_comment, item_share;
        GridView grid_view_like;
        ImageView class_center_log_picture;
        CircleImageView class_center_log_image;
    }
}
