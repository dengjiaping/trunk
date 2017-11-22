package com.histudent.jwsoft.histudent.body.myclass.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.myclass.bean.NoticesBean;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.comment2.utils.EmotionUtils;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2017/4/20.
 * 通知列表列表适配器
 */

public class NoticesAdapter extends BaseAdapter {

    private Activity activity;
    private List<NoticesBean.ItemsBean> fashionBeans;

    public NoticesAdapter(Activity activity, List<NoticesBean.ItemsBean> fashionBeans) {
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
            convertView = LayoutInflater.from(activity).inflate(R.layout.adapter_notice_item, viewGroup, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        NoticesBean.ItemsBean beanX = fashionBeans.get(i);
        if (beanX != null) {
            if (beanX.isView()) {
                holder.mRedPoint.setVisibility(View.INVISIBLE);
            } else {
                holder.mRedPoint.setVisibility(View.VISIBLE);
            }
            holder.mReadNum.setVisibility(View.VISIBLE);
            holder.mReadNumImg.setVisibility(View.VISIBLE);
            holder.mReadNum.setText(beanX.getReadNum() + "/" + beanX.getNoticeUserNum());
            StringBuilder builder = new StringBuilder();
            if (beanX.isHasImage()){
                builder.append("[图片]");
            }
            if (beanX.isHasVoice()){
                builder.append("[语音]");
            }
            builder.append(beanX.getContent());

            holder.mContent.setText(EmotionUtils.convertNormalStringToSpannableString(builder.toString()));
            holder.mTime.setText(beanX.getNoticeTime());

        }

        return convertView;
    }

    public static class ViewHolder {
        private TextView mContent;
        private TextView mTime;
        private TextView mReadNum;
        private View mRedPoint;
        private IconView mReadNumImg;

        public ViewHolder(View view) {
            mContent = view.findViewById(R.id.notice_content);
            mTime = view.findViewById(R.id.notice_time);
            mReadNum = view.findViewById(R.id.notice_read_num);
            mRedPoint = view.findViewById(R.id.red_point);
            mReadNumImg = view.findViewById(R.id.notice_read_img);
        }

    }

}
