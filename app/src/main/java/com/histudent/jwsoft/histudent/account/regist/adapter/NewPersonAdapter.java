package com.histudent.jwsoft.histudent.account.regist.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.account.regist.bean.NewPersonModel;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/8/30.
 */
public class NewPersonAdapter extends BaseAdapter {

    private List<NewPersonModel.ItemsBean> lists;
    private Activity activity;
    private List<String> list_ids;

    public NewPersonAdapter(Activity activity, List<NewPersonModel.ItemsBean> lists, List<String> list_ids) {

        this.activity = activity;
        this.lists = lists;
        this.list_ids = list_ids;

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

        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(activity).inflate(R.layout.newperson_item, null);
            holder.headImageView = (HiStudentHeadImageView) view.findViewById(R.id.hendImage);
            holder.imageView = (ImageView) view.findViewById(R.id.check);
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.className = (TextView) view.findViewById(R.id.className);
            holder.gz_count = (TextView) view.findViewById(R.id.gz_text);
            holder.fs_count = (TextView) view.findViewById(R.id.fs_text);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final NewPersonModel.ItemsBean bean = lists.get(i);
        holder.headImageView.loadBuddyAvatar(bean.getAvatar());
        holder.name.setText(bean.getRealName());
        holder.className.setText(bean.getSchoolName());
        holder.gz_count.setText("关注  " + bean.getAttNumber());
        holder.fs_count.setText("粉丝  " + bean.getFansNumber());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = bean.getUserId();
                if (list_ids.contains(userId)) {
                    holder.imageView.setImageResource(R.mipmap.select_friend_false);
                    list_ids.remove(userId);
                } else {
                    holder.imageView.setImageResource(R.mipmap.select_friend_true);
                    list_ids.add(userId);
                }

            }
        });

        return view;
    }

    class ViewHolder {
        HiStudentHeadImageView headImageView;
        TextView name, className, gz_count, fs_count;
        ImageView imageView;
    }

}
