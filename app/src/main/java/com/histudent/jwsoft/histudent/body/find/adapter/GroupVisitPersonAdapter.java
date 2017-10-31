package com.histudent.jwsoft.histudent.body.find.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.bean.GroupBean;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;

import java.util.List;


/**
 * Created by liuguiyu-pc on 2017/5/17.
 */

public class GroupVisitPersonAdapter extends BaseAdapter {

    private List<GroupBean.GroupMembersListBean> beans;
    private Context context;

    public GroupVisitPersonAdapter(List<GroupBean.GroupMembersListBean> beans, Context context) {
        this.beans = beans;
        this.context = context;
    }

    @Override
    public int getCount() {
        return beans.size();
    }

    @Override
    public Object getItem(int i) {
        return beans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        GroupBean.GroupMembersListBean bean = beans.get(i);
        View convertView = View.inflate(context, R.layout.adapter_person_item, null);
        HiStudentHeadImageView headImageView = convertView.findViewById(R.id.person_image);
        View teacherIdentityView = convertView.findViewById(R.id.iv_teacher_identity);

        if (bean.getUserType() == 3) {
            //老师
            teacherIdentityView.setVisibility(View.VISIBLE);
        } else {
            teacherIdentityView.setVisibility(View.INVISIBLE);
        }

        if (TextUtils.isEmpty(bean.getUserAvatar())) {
            headImageView.setImageResource(R.mipmap.favor_more);
        } else {
            MyImageLoader.getIntent().displayNetImage(context, bean.getUserAvatar(), headImageView);
        }

        return convertView;
    }
}
