package com.histudent.jwsoft.histudent.body.mine.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.activity.ParserActivity;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.info.persioninfo.activity.PersonCenterActivity;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/6/1.
 */
public class GridViewImageAdapter extends BaseAdapter {

    private Activity activity;
    private List<ActionListBean.PraiseUsersBean> datas;
    private ActionListBean bean;
    private boolean flag;

    public GridViewImageAdapter(Activity activity, List<ActionListBean.PraiseUsersBean> datas) {
        this.activity = activity;
        this.datas = datas;
    }

    public void setBean(ActionListBean bean) {
        this.bean = bean;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatHolder holder;
        if (convertView == null) {
            holder = new ChatHolder();

            convertView = LayoutInflater.from(activity).inflate(R.layout.gridview_image_item, null);

            holder.image = (HiStudentHeadImageView) convertView.findViewById(R.id.grid_image);

            convertView.setTag(holder);
        } else {
            holder = (ChatHolder) convertView.getTag();
        }
        ActionListBean.PraiseUsersBean model = datas.get(position);

        if (!flag) {
            if (StringUtil.isEmpty(model.getUserId())) {
                holder.image.setImageResource(R.mipmap.favor_more);
            } else {
                MyImageLoader.getIntent().displayNetImage(activity, model.getAvatar(), holder.image);
            }
        }

        setListener(holder.image, model, position);

        return convertView;
    }

    class ChatHolder {
        HiStudentHeadImageView image;
    }

    private void setListener(View view, final ActionListBean.PraiseUsersBean model, final int position) {

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (StringUtil.isEmpty(model.getUserId())) {

                    if (bean == null) return;
                    if (bean.getActivityItemKey().equals("Picture")) {
                        ParserActivity.start(activity, bean.getActId(), 5);
                    } else {
                        ParserActivity.start(activity, bean.getActId(), 1);
                    }

                } else {
                    PersonCenterActivity.start(activity, model.getUserId());
                }
            }
        });
    }

}
