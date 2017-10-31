package com.histudent.jwsoft.histudent.body.message.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.model.NoticeBean;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.comment2.utils.EmotionUtils;
import com.histudent.jwsoft.histudent.comment2.utils.TimeUtils;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/8/26.
 * 消息中通知适配器
 */
public class MyNotificationAdapter extends BaseAdapter {

    private Activity activity;
    private List<NoticeBean.ItemsBean> recentContactsModels;
    private int type;

    public MyNotificationAdapter(Activity activity, List<NoticeBean.ItemsBean> recentContactsModels) {
        this.activity = activity;
        this.recentContactsModels = recentContactsModels;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getCount() {
        return recentContactsModels.size();
    }

    @Override
    public Object getItem(int i) {
        return recentContactsModels.get(i);
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
            view = LayoutInflater.from(activity).inflate(R.layout.offical_action_item, null);
            holder.logo = (HiStudentHeadImageView) view.findViewById(R.id.image);
            holder.title = (TextView) view.findViewById(R.id.title);
            holder.content = (TextView) view.findViewById(R.id.content);
//            holder.layout = (LinearLayout) view.findViewById(R.id.layout_recentcontact);
            holder.red_point = (TextView) view.findViewById(R.id.red_point);
            holder.red_point_num = (TextView) view.findViewById(R.id.red_point_num);
            holder.time = (TextView) view.findViewById(R.id.time);
//            holder.button_null = (Button) view.findViewById(R.id.button_null);
            holder.button_delet = (Button) view.findViewById(R.id.button_delet);
            holder.scrollView = (HorizontalScrollView) view.findViewById(R.id.scrollView);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        NoticeBean.ItemsBean model = recentContactsModels.get(i);
        holder.scrollView.setVisibility(View.GONE);
        MyImageLoader.getIntent().displayNetImage(activity, model.getPAvatar(), holder.logo);
        holder.title.setText(model.getPUserName());
        switch (model.getInfoStatus()) {
            case 0:
                holder.content.setText(EmotionUtils.convertNormalStringToSpannableString(model.getConent()));
                break;
            case 1:
                holder.content.setText("删除动态");
                break;
            case 2:
                holder.content.setText(type == 5 ? "删除评论" : "取消点赞");
                break;
        }
        holder.time.setText(TimeUtils.exchangeTime(model.getAddTime()));

        return view;
    }

    class ViewHolder {
        HiStudentHeadImageView logo;
        TextView title, content, red_point, red_point_num, time;
        LinearLayout layout;
        Button button_null, button_delet;
        HorizontalScrollView scrollView;
    }

}
