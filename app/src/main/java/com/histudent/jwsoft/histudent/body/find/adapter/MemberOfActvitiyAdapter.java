package com.histudent.jwsoft.histudent.body.find.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.bean.ActivityMembersBean;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.CircleImageView;
import com.histudent.jwsoft.histudent.comment2.utils.TimeUtils;
import com.histudent.jwsoft.histudent.info.persioninfo.activity.PersonCenterActivity;

import java.util.List;

/**
 * 活动群成员的适配器
 * Created by ZhangYT on 2016/6/16.
 */
public class MemberOfActvitiyAdapter extends BaseAdapter {
    private List<ActivityMembersBean> list;
    private Context context;

    public MemberOfActvitiyAdapter(List<ActivityMembersBean> list, Context context) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.member_of_activity_list_item, null);
            viewHolder = new ViewHolder(convertView);


            final ActivityMembersBean dataBean = list.get(position);
            viewHolder.tv_name.setText(dataBean.getSignupUserRealName());
            CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(context, dataBean.getSignupUserAvatar(),
                    viewHolder.iv, ContextCompat.getDrawable(context, R.mipmap.avatar_def));
            viewHolder.tv_name.setTextColor(context.getResources().getColor(R.color.text_black_h3));

            if (dataBean.getSignUpUserType() == 3) {
                //老师
                viewHolder.teacherIndentity.setVisibility(View.VISIBLE);
            } else {
                viewHolder.teacherIndentity.setVisibility(View.GONE);
            }

            if (dataBean.isIsCreateUser()) {
                viewHolder.rb_describe.setText("发起者");
                viewHolder.rb_describe.setTextColor(context.getResources().getColor(R.color.yellow_color));
            } else {
                viewHolder.rb_describe.setText(TimeUtils.getFormateTimeWithoutCurrentYearAndSecond(dataBean.getCreateTime()) + "  报名");
            }

            viewHolder.iv.setOnClickListener((View v) -> PersonCenterActivity.start((BaseActivity) context, dataBean.getSignupUserId()));
        }
        return convertView;
    }


    class ViewHolder {
        private CircleImageView iv;
        private TextView tv_name;
        private RadioButton rb_describe;
        private ImageView iv_phone, teacherIndentity;

        public ViewHolder(View convertView) {
            iv = ((CircleImageView) convertView.findViewById(R.id.member_of_activty_list_item_iv));
            tv_name = ((TextView) convertView.findViewById(R.id.member_of_activty_list_item_tv_name));
            rb_describe = ((RadioButton) convertView.findViewById(R.id.member_of_activty_list_item_rb_describe));
            iv_phone = ((ImageView) convertView.findViewById(R.id.member_of_activty_list_item_iv_phone));
            teacherIndentity = convertView.findViewById(R.id.iv_teacher_identity);
        }

    }


    //打电话的操作
    private void takeCall(String tell) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + tell));
        context.startActivity(intent);

    }

}
