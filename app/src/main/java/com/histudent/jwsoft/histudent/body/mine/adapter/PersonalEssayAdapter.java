//package com.histudent.jwsoft.histudent.body.mine.adapter;
//
//import android.app.Activity;
//import android.content.Context;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.GridView;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.histudent.jwsoft.histudent.R;
//import com.histudent.jwsoft.histudent.body.mine.model.EssayModel;
//
//import java.util.List;
//
///**
// * Created by liuguiyu on 2016/6/16.
// */
//public class PersonalEssayAdapter extends BaseAdapter {
//    private List<EssayModel> maps;
//    private Activity context;
//
//    public PersonalEssayAdapter(Activity context, List<EssayModel> maps) {
//        this.maps = maps;
//        this.context = context;
//    }
//
//    @Override
//    public int getCount() {
//        return maps.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return maps.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder viewHolder;
//        GridViewImageAdapter adapter_image, adapter_picture;
//        if (convertView == null) {
//            viewHolder = new ViewHolder();
//            convertView = View.inflate(context, R.layout.personal_essay_item, null);
//
//            viewHolder.time = (TextView) convertView.findViewById(R.id.time);
//            viewHolder.delet = (ImageView) convertView.findViewById(R.id.delet);
//            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
//            viewHolder.location = (TextView) convertView.findViewById(R.id.location);
//            viewHolder.item_comment = (Button) convertView.findViewById(R.id.item_comment);
//            viewHolder.item_share = (Button) convertView.findViewById(R.id.item_share);
//            viewHolder.item_like = (CheckBox) convertView.findViewById(R.id.item_like);
//            viewHolder.grid_view_like = (GridView) convertView.findViewById(R.id.grid_view_like);
//            viewHolder.gridview = (GridView) convertView.findViewById(R.id.gridview);
//
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//
//        EssayModel model = maps.get(position);
//
//        viewHolder.time.setText(changeTimeFromat(model.getCreateTime()));
////        viewHolder.title.setText(model.get);
////        viewHolder.location.setText(model.getSourceUrl());
//        viewHolder.item_comment.setText(model.getReplyCount() + "");
//        viewHolder.item_share.setText(model.getForwardCount() + "");
//        viewHolder.item_like.setText("未知");
//
////        List<String> list_picture = (List<String>) map.get("gridview");
////        List<String> list_image = (List<String>) map.get("grid_view_like");
////
////        adapter_image = new GridViewImageAdapter(context, SystemUtil.doActionInData(list_image));
////        adapter_picture = new GridViewImageAdapter(context, list_picture, getViewHeight());
////
////        viewHolder.grid_view_like.setAdapter(adapter_image);
////        viewHolder.gridview.setAdapter(adapter_picture);
//        return convertView;
//    }
//
//    private int getViewHeight() {
//        WindowManager wm = (WindowManager) context.getApplicationContext()
//                .getSystemService(Context.WINDOW_SERVICE);
//        int width = wm.getDefaultDisplay().getWidth();
//        return (width - 20) / 6 * 5 / 3 - 8;
//    }
//
//    class ViewHolder {
//        TextView time, title, location;
//        ImageView delet;
//        CheckBox item_like;
//        Button item_comment, item_share;
//        GridView gridview, grid_view_like;
//    }
//
//    private String changeTimeFromat(String time) {
//
//        String[] time_01 = time.split(" ");
//        String[] time_02 = time_01[0].split("-");
//        String[] time_03 = time_01[1].split(":");
//
//        return time_02[1] + "月" + time_02[2] + "日  " + time_03[0] + ":" + time_03[1];
//    }
//
//}
