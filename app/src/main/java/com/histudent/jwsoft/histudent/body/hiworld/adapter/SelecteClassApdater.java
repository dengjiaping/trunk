package com.histudent.jwsoft.histudent.body.hiworld.adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.mine.model.UserClassListModel;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.commen.view.swipemenulistview.CheckBoxView;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/11/14.
 */
public class SelecteClassApdater extends BaseAdapter {

    private Activity activity;
    private List<UserClassListModel> classListModels_select;
    private int type;

    public SelecteClassApdater(Activity activity, List<UserClassListModel> classListModels_select, int type) {
        this.activity = activity;
        this.classListModels_select = classListModels_select;
        this.type = type;
    }

    @Override
    public int getCount() {
        return classListModels_select.size();
    }

    @Override
    public Object getItem(int i) {
        return classListModels_select.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(activity).inflate(R.layout.selectclass_item_view, null);
            viewHolder.item_layout = (RelativeLayout) convertView.findViewById(R.id.selecteclass_item_layout);
            viewHolder.item_image = (CheckBoxView) convertView.findViewById(R.id.selecteclass_item_image);
            viewHolder.item_name = (TextView) convertView.findViewById(R.id.selecteclass_item_name);
            viewHolder.item_count = (TextView) convertView.findViewById(R.id.selecteclass_item_member_count);
            viewHolder.tv_def = (TextView) convertView.findViewById(R.id.tv_default);
            viewHolder.iv_person = (HiStudentHeadImageView) convertView.findViewById(R.id.iv_person);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final UserClassListModel classModel = classListModels_select.get(i);

        boolean flag = classModel.isTag();
        viewHolder.item_image.setChecked(flag);


        viewHolder.item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.item_image.setChecked(!viewHolder.item_image.isChecked());
                classModel.setTag(!classModel.isTag());
            }
        });
        viewHolder.item_name.setText(classModel.getCName());

//
        if (!StringUtil.isEmpty(classModel.getClassLogo()))
            CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(activity, classModel.getClassLogo(),
                    viewHolder.iv_person, ContextCompat.getDrawable(activity, R.mipmap.default_class1));
        if (classModel.getIsDefaultClass()) {
            viewHolder.tv_def.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tv_def.setVisibility(View.GONE);
        }

        switch (type) {

            case 1:
                viewHolder.item_count.setText(classModel.getClassMemberNum() + "人");
                break;

            case 2:
                viewHolder.item_count.setText(classModel.getClassMemberNum() + "人");
                break;
        }
        return convertView;
    }

    class ViewHolder {
        private RelativeLayout item_layout;
        private CheckBoxView item_image;
        private TextView item_name, item_count, tv_def;
        private HiStudentHeadImageView iv_person;
    }

}
