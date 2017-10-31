package com.histudent.jwsoft.histudent.body.find.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.activity.GroupJoinActivity;
import com.histudent.jwsoft.histudent.body.find.bean.DiscoverGroupListBean;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;

import java.util.List;

/**
 * 兴趣社群下发现社群的适配
 * Created by ZhangYT on 2016/6/14.
 */
public class DiscoverGroupAdapter extends BaseAdapter {
    private List<DiscoverGroupListBean.ItemsBean> list;
    private Context context;

    public DiscoverGroupAdapter(List<DiscoverGroupListBean.ItemsBean> list, Context context) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.favor_group_list_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = ((ViewHolder) convertView.getTag());
        }

        final DiscoverGroupListBean.ItemsBean dataBean = list.get(position);
        viewHolder.tv_title.setText(dataBean.getGroupName());
        viewHolder.tv_num.setText("参加人员： " + dataBean.getGroupMemberNum() + "");
        viewHolder.tv_address.setText(dataBean.getAearStr());
        viewHolder.tv_tag.setText(dataBean.getTagName());


//        MyImageLoader.getIntent().displayNetImageWithAnimation(context,dataBean.getGroupLogo(), viewHolder.iv, R.mipmap.default_group);
        CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(context,dataBean.getGroupLogo(),
                viewHolder.iv, ContextCompat.getDrawable(context,R.mipmap.default_group));

        if (dataBean.isIsMember()) {
            viewHolder.btn_join.setChecked(true);
            viewHolder.btn_join.setEnabled(false);
            viewHolder.btn_join.setTextColor(Color.WHITE);
            viewHolder.btn_join.setText("已加入");

        } else {
            viewHolder.btn_join.setChecked(false);
            viewHolder.btn_join.setTextColor(Color.rgb(32,126,189));
            viewHolder.btn_join.setText("加入");


        }
        viewHolder.btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size()>0){
                    viewHolder.btn_join.setChecked(false);
                    GroupJoinActivity.start((Activity) context, list.get(position).getGroupId(),111);
                }
            }
        });

        return convertView;
    }

    class ViewHolder {
        private ImageView iv, iv_join;
        private TextView tv_title, tv_num, tv_address;
        private Button btn_group;
        private RadioButton btn_join, tv_tag;

        public ViewHolder(View convertView) {
            iv = ((ImageView) convertView.findViewById(R.id.favor_group_list_iv));
            tv_title = ((TextView) convertView.findViewById(R.id.favor_group_list_tv_title));
            tv_num = ((TextView) convertView.findViewById(R.id.favor_group_list_tv_num));
            tv_address = ((TextView) convertView.findViewById(R.id.favor_group_list_tv_address));
            tv_tag = ((RadioButton) convertView.findViewById(R.id.favor_group_list_tv_tag));
            btn_join = ((RadioButton) convertView.findViewById(R.id.favor_group_list_btn_join));

        }
    }
}
