package com.histudent.jwsoft.histudent.body.hiworld.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.hiworld.bean.SimpleUserModel;
import com.histudent.jwsoft.histudent.commen.utils.getSeletedUser;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.CircleImageView;
import com.histudent.jwsoft.histudent.commen.view.swipemenulistview.CheckBoxView;
import com.histudent.jwsoft.histudent.comment2.utils.SeletClassMateEnum;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.List;


/**
 * 已经本选择的好友
 * Created by ZhangYT on 2016/8/29.
 */
public class SelectedClamatesAdapter extends BaseAdapter {
    private List<Object> list;
    private Context context;
    private getSeletedUser callback;
    private SeletClassMateEnum mateEnum;

    public SelectedClamatesAdapter(List<Object> list, Context context,
                                   getSeletedUser callback, SeletClassMateEnum mateEnum) {
        this.list = list;
        this.context = context;
        this.callback = callback;
        this.mateEnum = mateEnum;
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
    public boolean isEnabled(int position) {
        if (list.get(position) instanceof String) {
            return false;
        } else {
            return true;
        }
    }


    //用户刷新已经选择的学生的数据显示
    public void RefreshDateByUser(List<Object> ids) {

        if (ids != null) {
            refreshDate();
            if (ids.size() > 0) {
                for (Object o : ids) {
                    if (o instanceof SimpleUserModel) {
                        SimpleUserModel model = ((SimpleUserModel) o);
                        isSameStu(model.getUserId());
                    }
                }
            }

            notifyDataSetChanged();
        }
    }


    //将所有学生初始化为未被选中的状态
    private void refreshDate() {
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Object item = list.get(i);
                if (item instanceof SimpleUserModel) {
                    SimpleUserModel userModel = ((SimpleUserModel) item);
                    userModel.setTag(false);
                }
            }
        }
    }

    //返回每个字母所对应的位置
    public int getSelection(String c) {
        int m = -1;
        //首字母定位
        for (int i = 3; i < list.size(); i++) {
            if (list.get(i) instanceof String) {
                String c1 = ((String) list.get(i));
                if (c1.equals(c)) {
                    m = i;
                    break;
                }
            }
        }
        return m;
    }

    //标记被选中的学生
    private boolean isSameStu(String id) {
        if (list.size() > 0 && !StringUtil.isEmpty(id)) {
            for (int i = 0; i < list.size(); i++) {
                Object item = list.get(i);
                if (item instanceof SimpleUserModel) {
                    SimpleUserModel model = ((SimpleUserModel) item);
                    if (model.getUserId().equals(id)) {
                        model.setTag(true);
                        break;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        final ViewHolder viewHolder;
        final SimpleUserModel[] model = new SimpleUserModel[1];
        int type = -1;

        final Object object = list.get(position);

        if (object instanceof String) {
            convertView = View.inflate(context, R.layout.text_view_item, null);
            TextView tv = ((TextView) convertView.findViewById(R.id.tv_));
            tv.setText(object.toString());

        } else {
            switch (mateEnum) {
                case CLASSMATE:
                    convertView = View.inflate(context, R.layout.user_item, null);
                    break;
                case AT:
                case REALTION:
                case GROUP:
                    convertView = View.inflate(context, R.layout.selected_student1, null);
                    break;

            }


            viewHolder = new ViewHolder(convertView);

            if (object instanceof SimpleUserModel) {

                final SimpleUserModel bean = ((SimpleUserModel) object);
                model[0] = bean;
                type = bean.getType();
            }

            viewHolder.tv_name.setText(model[0].getName());
            Log.e("getUserMobile", model[0].getUserMobile() + "--" + model[0].getName());

            if (!TextUtils.isEmpty(model[0].getAvatar())) {
                CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(context, model[0].getAvatar(),
                        viewHolder.iv_avatar);

            }

            switch (mateEnum) {

                //同学录显示电话号
                case CLASSMATE:
                    if (position == 0 && model[0].isMasker()) {
                        viewHolder.masker_tag.setVisibility(View.VISIBLE);
                    } else {
                        viewHolder.masker_tag.setVisibility(View.GONE);
                    }
                    viewHolder.tv_tell.setText(model[0].getUserMobile());
                    if (type == 3) {
                        //老师
                        viewHolder.mTeacherIdentity.setVisibility(View.VISIBLE);
                    } else {
                        viewHolder.mTeacherIdentity.setVisibility(View.INVISIBLE);
                    }
                    viewHolder.tv_name.setTextColor(context.getResources().getColor(R.color.text_black_h3));
                    convertView.findViewById(R.id.layout).setOnClickListener((View v) -> callback.getSelectedUser(model[0]));
                    break;

                //关联人
                case REALTION:
                    //@好友

                case GROUP:
                    viewHolder.cb_tag.setChecked(model[0].isTag());
                    viewHolder.cb_tag.setOnCheckChangeListener((View v, boolean isChecked) -> callback.getSelectedUser(model[0]));
                    viewHolder.layout_check.setOnClickListener((View v) -> viewHolder.cb_tag.setChecked(!viewHolder.cb_tag.isChecked()));
                    break;
                case AT:
                    viewHolder.cb_tag.setVisibility(View.GONE);
                    viewHolder.layout.setOnClickListener((View v) -> callback.getSelectedUser(model[0]));
                    ImageView teacherIdentity = viewHolder.mTeacherIdentity;
                    if (teacherIdentity != null) {
                        // TODO: 2017/8/14 身份识别校验
                        if (type == 3) {
                            //老师
                            viewHolder.mTeacherIdentity.setVisibility(View.VISIBLE);
                        } else {
                            viewHolder.mTeacherIdentity.setVisibility(View.INVISIBLE);
                        }
                    }
                    break;

            }
        }

        return convertView;
    }

    class ViewHolder {
        private RelativeLayout layout;
        private CircleImageView iv_avatar;
        private TextView tv_name, tv_tell, masker_tag;
        private CheckBoxView cb_tag;
        private RelativeLayout layout_check;
        private ImageView mTeacherIdentity;


        public ViewHolder(View convertView) {
            if (mateEnum != SeletClassMateEnum.CLASSMATE) {
                cb_tag = ((CheckBoxView) convertView.findViewById(R.id.iv_delete));
                layout_check = ((RelativeLayout) convertView.findViewById(R.id.layout_check));
                layout = ((RelativeLayout) convertView.findViewById(R.id.layout));
            } else {
                tv_tell = ((TextView) convertView.findViewById(R.id.tv_tell));
                masker_tag = ((TextView) convertView.findViewById(R.id.masker_tag));
            }
            mTeacherIdentity = convertView.findViewById(R.id.iv_teacher_identity);

            iv_avatar = ((CircleImageView) convertView.findViewById(R.id.iv));
            tv_name = ((TextView) convertView.findViewById(R.id.tv_name));
        }
    }
}
