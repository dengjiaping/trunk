package com.histudent.jwsoft.histudent.body.find.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.bean.SortGroupListBean;
import com.histudent.jwsoft.histudent.body.mine.model.UserClassListModel;
import com.histudent.jwsoft.histudent.body.myclass.bean.ClassJoinBean;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.List;

/**
 * 兴趣群分类查找
 * Created by ZhangYT on 2016/8/15.
 */
public class SortGroupAdapter extends BaseAdapter {

    private List<Object> list;
    private Context context;

    public SortGroupAdapter(List<Object> list, Context context) {

        this.list = list;
        this.context = context;
    }

    private String id;

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

        Object o = list.get(position);
        if (convertView == null) {
            if (o instanceof String) {
                convertView = View.inflate(context, R.layout.right_pop_item, null);
            } else if (o instanceof UserClassListModel) {
                convertView = View.inflate(context, R.layout.sort_group_list_item, null);
            } else if (o instanceof ClassJoinBean) {
                convertView = View.inflate(context, R.layout.sort_class_list_item, null);
            }

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = ((ViewHolder) convertView.getTag());
        }

        if (o instanceof ClassJoinBean) {

            ClassJoinBean model = (ClassJoinBean) o;
            viewHolder.log = convertView.findViewById(R.id.log);
            viewHolder.tag = convertView.findViewById(R.id.tag);

            viewHolder.log.setVisibility(View.VISIBLE);
            MyImageLoader.getIntent().displayNetImage(context, model.getClassLogo(), viewHolder.log);
            viewHolder.tv_name.setText(model.getFullName());

            viewHolder.tag.setVisibility(View.INVISIBLE);

        } else if (o instanceof String) {

            String str = ((String) o);
            viewHolder.tv_name.setText(str);

        } else if (o instanceof UserClassListModel) {

            viewHolder.log = convertView.findViewById(R.id.log);
            viewHolder.tag = convertView.findViewById(R.id.tag);
            viewHolder.log.setVisibility(View.VISIBLE);

            UserClassListModel model = ((UserClassListModel) o);
            viewHolder.tv_name.setText(model.getFullName());
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) viewHolder.mPoint.getLayoutParams();
            viewHolder.mPoint.setVisibility(View.VISIBLE);
            if (model.getImNum() == 0) {
                viewHolder.mPoint.setVisibility(View.GONE);
            } else if (model.getImNum() < 10) {
                params.width = SystemUtil.dp2px(16);
                params.height = SystemUtil.dp2px(16);
                viewHolder.mPoint.setLayoutParams(params);
                viewHolder.mPoint.setText(String.valueOf(model.getImNum()));
                viewHolder.mPoint.setBackground(context.getResources().getDrawable(R.drawable.bg_circle_num_tip));
            } else if (model.getImNum() >= 10 && model.getImNum() <= 99) {
                params.width = SystemUtil.dp2px(24);
                params.height = SystemUtil.dp2px(16);
                viewHolder.mPoint.setLayoutParams(params);
                viewHolder.mPoint.setText(String.valueOf(model.getImNum()));
                viewHolder.mPoint.setBackground(context.getResources().getDrawable(R.drawable.bg_circle_num_tip_plus));
            } else if (model.getImNum() >= 99) {
                params.width = SystemUtil.dp2px(24);
                params.height = SystemUtil.dp2px(16);
                viewHolder.mPoint.setLayoutParams(params);
                viewHolder.mPoint.setText("99+");
                viewHolder.mPoint.setBackground(context.getResources().getDrawable(R.drawable.bg_circle_num_tip_plus));
            } else {
                viewHolder.mPoint.setVisibility(View.GONE);
            }
            MyImageLoader.getIntent().displayNetImage(context, model.getClassLogo(), viewHolder.log);
            if (!StringUtil.isEmpty(id) && id.equals(model.getClassId())) {
//                viewHolder.tag.setVisibility(View.VISIBLE);
                viewHolder.tv_name.setTextColor(Color.parseColor("#28CA7E"));
            } else {
//                viewHolder.tag.setVisibility(View.INVISIBLE);
                viewHolder.tv_name.setTextColor(Color.parseColor("#666666"));
            }

        }

        return convertView;
    }

    public void initSeletedItem(String Id) {
        id = Id;
    }

    class ViewHolder {
        private TextView tv_name;
        private HiStudentHeadImageView log;
        private IconView tag;
        private TextView mPoint;

        public ViewHolder(View convertView) {
            tv_name = convertView.findViewById(R.id.tv_name);
            mPoint = convertView.findViewById(R.id.red_point_num);
        }
    }
}
