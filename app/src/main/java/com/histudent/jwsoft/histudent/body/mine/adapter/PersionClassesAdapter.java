package com.histudent.jwsoft.histudent.body.mine.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.mine.model.CurrentUserDetailInfoModel;
import com.histudent.jwsoft.histudent.body.myclass.activity.ClassCircleActivity;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;

import java.util.List;

/**
 * 个人资料班级
 */
public class PersionClassesAdapter extends BaseAdapter {
    private List<CurrentUserDetailInfoModel.ClassInfoBean.ItemsBeanX> list;
    private BaseActivity context;

    public PersionClassesAdapter(BaseActivity context, List<CurrentUserDetailInfoModel.ClassInfoBean.ItemsBeanX> list) {
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
    public View getView(final int position, View convertView, final ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.adapter_classes_item, null);
            viewHolder = new ViewHolder();
            viewHolder.headImageView = (HiStudentHeadImageView) convertView.findViewById(R.id.class_log);
            viewHolder.class_name = (TextView) convertView.findViewById(R.id.class_name);
            viewHolder.class_item = (RelativeLayout) convertView.findViewById(R.id.class_item);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final CurrentUserDetailInfoModel.ClassInfoBean.ItemsBeanX bean = list.get(position);

        viewHolder.headImageView.loadBuddyAvatar(bean.getLogo());
        viewHolder.class_name.setText(bean.getName());
        viewHolder.class_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClassCircleActivity.start(context,bean.getClassesId());
            }
        });

        return convertView;
    }

    class ViewHolder {
        private HiStudentHeadImageView headImageView;
        private TextView class_name;
        private RelativeLayout class_item;
    }
}
