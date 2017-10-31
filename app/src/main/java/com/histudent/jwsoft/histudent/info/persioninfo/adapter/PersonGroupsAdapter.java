package com.histudent.jwsoft.histudent.info.persioninfo.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.mine.model.CurrentUserDetailInfoModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;

import java.util.List;

/**
 * 个人主页社群列表适配器
 */
public class PersonGroupsAdapter extends BaseAdapter {
    private List<CurrentUserDetailInfoModel.GroupInfoBean.ItemsBeanXX> list;
    private BaseActivity context;

    public PersonGroupsAdapter(BaseActivity context, List<CurrentUserDetailInfoModel.GroupInfoBean.ItemsBeanXX> list) {
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
            convertView = View.inflate(context, R.layout.adapter_groups_item, null);
            viewHolder = new ViewHolder();
            viewHolder.headImageView = convertView.findViewById(R.id.group_log);
            viewHolder.group_name = convertView.findViewById(R.id.group_name);
            viewHolder.group_persion_num = convertView.findViewById(R.id.group_persion_num);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final CurrentUserDetailInfoModel.GroupInfoBean.ItemsBeanXX bean = list.get(position);

        if (!TextUtils.isEmpty(bean.getLogo()))
            MyImageLoader.getIntent().displayNetImage(context, bean.getLogo(), viewHolder.headImageView);
        if (!TextUtils.isEmpty(bean.getName()))
            viewHolder.group_name.setText(bean.getName());
        if (!TextUtils.isEmpty(bean.getName()))
            viewHolder.group_persion_num.setText(bean.getMemberNum() + "");

        return convertView;
    }

    class ViewHolder {
        private HiStudentHeadImageView headImageView;
        private TextView group_name, group_persion_num;
    }
}
