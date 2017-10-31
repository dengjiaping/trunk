package com.histudent.jwsoft.histudent.commen.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.model.ParserModel;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.comment2.utils.TimeUtils;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/8/30.
 */
public class ParserAdapter extends BaseAdapter {

    private List<ParserModel.ItemsBean> lists;
    private Activity activity;

    public ParserAdapter(Activity activity, List<ParserModel.ItemsBean> lists) {

        this.activity = activity;
        this.lists = lists;

    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(activity).inflate(R.layout.parser_list_item, null);
            holder.headImageView = (HiStudentHeadImageView) view.findViewById(R.id.parser_list_item_head);
            holder.parser_layout = (RelativeLayout) view.findViewById(R.id.parser_layout);
            holder.name = (TextView) view.findViewById(R.id.parser_list_item_name);
            holder.time = (TextView) view.findViewById(R.id.parser_list_item_time);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        ParserModel.ItemsBean bean = lists.get(i);
        holder.headImageView.loadBuddyAvatar(bean.getAvatar());
        holder.name.setText(bean.getName());
        holder.time.setText(TimeUtils.exchangeTime(bean.getPraiseTime()));

        setLisner(holder, bean);

        return view;
    }

    class ViewHolder {

        HiStudentHeadImageView headImageView;
        TextView name, time;
        RelativeLayout parser_layout;
    }

    private void setLisner(ViewHolder holder, final ParserModel.ItemsBean bean) {
//        final boolean flag = bean.isIsFollowed();
//
//        holder.add_see.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                PersionHelper.getInstance().attention(activity, flag ? 0 : 1, bean.getUserId(), new HttpRequestCallBack() {
//                    @Override
//                    public void onSuccess(String result) {
//                        bean.setIsFollowed(!flag);
//                        notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onFailure(String msg) {
//
//                    }
//                });
//            }
//        });
//
//        holder.parser_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                InfoHelper.gotoPersonHome((BaseActivity) activity, bean.getUserId(), false);
//            }
//        });
    }

}
