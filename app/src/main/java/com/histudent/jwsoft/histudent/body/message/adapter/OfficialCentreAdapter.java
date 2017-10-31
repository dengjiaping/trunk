package com.histudent.jwsoft.histudent.body.message.adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.model.SystemInfoBean;
import com.histudent.jwsoft.histudent.body.message.uikit.session.fragment.ChatType;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.CircleImageView;
import com.histudent.jwsoft.histudent.manage.PhotoManager;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/6/1.
 */
public class OfficialCentreAdapter extends BaseAdapter {

    private Activity activity;
    private List<SystemInfoBean.ItemsBean> datas;
    private int mFlag;

    public OfficialCentreAdapter(Activity activity, List<SystemInfoBean.ItemsBean> datas, int flag) {
        this.activity = activity;
        this.datas = datas;
        this.mFlag = flag;
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
        ChatHolder holder = null;
        if (convertView == null) {
            holder = new ChatHolder();
            //下拉项布局
            convertView = LayoutInflater.from(activity).inflate(R.layout.msg_active_list_item, null);
            holder.time =  convertView.findViewById(R.id.tv_time);
            holder.title =  convertView.findViewById(R.id.title);
            holder.content =  convertView.findViewById(R.id.content);
            holder.logo =  convertView.findViewById(R.id.logo);
            holder.typeName =  convertView.findViewById(R.id.tv_name_type);
            holder.iconType =  convertView.findViewById(R.id.iv_icon_type);
            convertView.setTag(holder);
        } else {
            holder = (ChatHolder) convertView.getTag();
        }
        if (mFlag == ChatType.SYSTEMINFO) {
            //系统消息
            holder.typeName.setText("系统消息");
            holder.iconType.setImageResource(R.mipmap.system_logo);
        } else if (mFlag == ChatType.ACTION) {
            //官方活动
            holder.typeName.setText("官方活动");
            holder.iconType.setImageResource(R.mipmap.offical_action);
        }

        SystemInfoBean.ItemsBean itemsBean = datas.get(position);
        String time = itemsBean.getCreateTime().replaceAll("-", "/");
        time = time.substring(0, time.length() - 3);
        holder.time.setText(time);
        holder.title.setText(itemsBean.getTitle());
        holder.content.setText(itemsBean.getContent());
        CommonGlideImageLoader.getInstance().displayNetImage(activity, itemsBean.getLogo(),
                holder.logo, PhotoManager.getInstance().getDefaultPlaceholderResource());
        return convertView;
    }

    class ChatHolder {
        TextView time, title, content, typeName;
        ImageView logo;
        CircleImageView iconType;
    }
}
