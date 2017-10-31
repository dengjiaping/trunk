//package com.histudent.jwsoft.histudent.body.mine.adapter;
//
//import android.app.Activity;
//import android.text.Html;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.GridView;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.histudent.jwsoft.histudent.R;
//import com.histudent.jwsoft.histudent.body.mine.model.UserBlogItemModel;
//
//import java.util.List;
//
///**
// * Created by liuguiyu on 2016/6/16.
// */
//public class PersonalLogAdapter extends BaseAdapter {
//    private List<UserBlogItemModel> models;
//    private Activity context;
//
//    public PersonalLogAdapter(Activity context, List<UserBlogItemModel> maps) {
//        this.models = maps;
//        this.context = context;
//    }
//
//    @Override
//    public int getCount() {
//        return models.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return models.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        final ViewHolder viewHolder;
//        GridViewImageAdapter adapter;
//        if (convertView == null) {
//            viewHolder = new ViewHolder();
//            convertView = View.inflate(context, R.layout.personal_log_item, null);
//
//            viewHolder.class_center_log_time = (TextView) convertView.findViewById(R.id.class_center_log_time);
//            viewHolder.class_center_log_sees = (TextView) convertView.findViewById(R.id.class_center_log_sees);
//            viewHolder.class_center_log_title = (TextView) convertView.findViewById(R.id.class_center_log_title);
//            viewHolder.class_center_log_content = (TextView) convertView.findViewById(R.id.class_center_log_content);
//            viewHolder.item_comment = (Button) convertView.findViewById(R.id.item_comment);
//            viewHolder.item_share = (Button) convertView.findViewById(R.id.item_share);
//            viewHolder.item_like = (CheckBox) convertView.findViewById(R.id.item_like);
//            viewHolder.grid_view_like = (GridView) convertView.findViewById(R.id.grid_view_like);
//            viewHolder.class_center_log_picture = (ImageView) convertView.findViewById(R.id.class_center_log_picture);
//
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//
//        final UserBlogItemModel model = models.get(position);
//
//        viewHolder.class_center_log_time.setText(model.getCreateTime() + "-" + model.getOwnerCategoryName());
//        viewHolder.class_center_log_sees.setText(model.getVisitNum() + "");
//        viewHolder.class_center_log_title.setText(model.getTitle());
//        viewHolder.class_center_log_content.setText(Html.fromHtml(model.getContent()));
//        viewHolder.item_comment.setText(model.getCommentNum() + "");
//        viewHolder.item_share.setText(model.getPraiseNum() + "");
//        viewHolder.item_like.setText(model.getTranspondNum() + "");
//
//        if (model.getTranspondNum() == 0) {
//            viewHolder.grid_view_like.setVisibility(View.GONE);
//        }
//
//        viewHolder.item_like.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//
//                if (isChecked) {
//                    viewHolder.item_like.setText(Integer.parseInt(viewHolder.item_like.getText().toString()) + 1 + "");
//                } else {
//                    viewHolder.item_like.setText(Integer.parseInt(viewHolder.item_like.getText().toString()) - 1 + "");
//                }
//
//            }
//        });
//
////        if ("-1".equals(map.get("picture_url").toString())) {
////            viewHolder.class_center_log_picture.setVisibility(View.GONE);
////        } else {
////            viewHolder.class_center_log_picture.setImageResource(R.mipmap.def);
////        }
////        List<String> list_image = (List<String>) map.get("grid_view_like");
////
////        adapter = new GridViewImageAdapter(context, SystemUtil.doActionInData(list_image));
////
////        viewHolder.grid_view_like.setAdapter(adapter);
//
//        return convertView;
//    }
//
//    class ViewHolder {
//        TextView class_center_log_time, class_center_log_sees, class_center_log_title,
//                class_center_log_content;
//        CheckBox item_like;
//        Button item_comment, item_share;
//        GridView grid_view_like;
//        ImageView class_center_log_picture;
//    }
//}
