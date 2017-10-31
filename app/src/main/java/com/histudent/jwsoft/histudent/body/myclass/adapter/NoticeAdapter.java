package com.histudent.jwsoft.histudent.body.myclass.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.myclass.bean.LikeBean;
import com.histudent.jwsoft.histudent.body.myclass.bean.NoticeDetailBean;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.comment2.utils.TimeUtils;
import com.histudent.jwsoft.histudent.entity.UnReadUserEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by huyg on 2017/9/28.
 *  通知item adapter
 */

public class NoticeAdapter extends BaseAdapter {

    private Activity activity;
    private List<Object> fashionBeans;
    private boolean isSend;
    private boolean isAdmin;

    public void setSend(boolean send) {
        isSend = send;
    }
    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public NoticeAdapter(Activity activity, List<Object> fashionBeans) {
        this.activity = activity;
        this.fashionBeans = fashionBeans;
    }

    @Override
    public int getCount() {
        return fashionBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return fashionBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_notice, viewGroup, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Object object = fashionBeans.get(i);
        if (object instanceof NoticeDetailBean.ReadUserListBean) {
            NoticeDetailBean.ReadUserListBean beanX = (NoticeDetailBean.ReadUserListBean) fashionBeans.get(i);
            MyImageLoader.getIntent().displayNetImage(activity, beanX.getAvatar(), holder.mHeadImg);
            holder.mName.setText(beanX.getRealName());
            holder.mTip.setVisibility(View.GONE);
            holder.mTime.setVisibility(View.VISIBLE);
            holder.mTime.setText(TimeUtils.exchangeTime(beanX.getReadTime()));

        } else if (object instanceof NoticeDetailBean.UnReadUserListBean) {
            NoticeDetailBean.UnReadUserListBean beanX = (NoticeDetailBean.UnReadUserListBean) fashionBeans.get(i);
            MyImageLoader.getIntent().displayNetImage(activity, beanX.getAvatar(), holder.mHeadImg);
            holder.mName.setText(beanX.getRealName());

            holder.mTip.setVisibility(View.VISIBLE);
            holder.mTime.setVisibility(View.GONE);
            if (!isSend){
                holder.mTip.setVisibility(View.GONE);
            }else{
                if (isAdmin){
                    holder.mTip.setVisibility(View.VISIBLE);
                }else{
                    holder.mTip.setVisibility(View.GONE);
                }

            }
            holder.mTip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().post(new UnReadUserEvent(beanX));
                }
            });
        }

        return convertView;
    }

    public static class ViewHolder {

        HiStudentHeadImageView mHeadImg;
        TextView mName, mTip;
        TextView mTime;

        public ViewHolder(View view) {
            this.mHeadImg = (HiStudentHeadImageView) view.findViewById(R.id.item_notice_head);
            this.mName = (TextView) view.findViewById(R.id.item_notice_name);
            this.mTip = (TextView) view.findViewById(R.id.item_notice_tip);
            this.mTime = view.findViewById(R.id.item_notice_time);
        }
    }

}
