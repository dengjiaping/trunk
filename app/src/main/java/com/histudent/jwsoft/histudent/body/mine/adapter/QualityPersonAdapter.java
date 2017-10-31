package com.histudent.jwsoft.histudent.body.mine.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.mine.model.BlackListBean;
import com.histudent.jwsoft.histudent.body.mine.model.DisabledFriendListBean;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.CircleImageView;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.List;

/**
 * 选择屏蔽或者加入黑名单适配
 * Created by ZhangYT on 2016/8/26.
 */
public class QualityPersonAdapter extends BaseAdapter {

    private Context context;
    private List<Object> list;

    public QualityPersonAdapter(List<Object> list, Context context) {
        this.list = list;
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        ViewHolder viewHolder;

        if (convertView == null) {

            convertView = View.inflate(context, R.layout.black_list_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = ((ViewHolder) convertView.getTag());
        }


        Object obj = list.get(position);

        if (obj instanceof BlackListBean) {
            BlackListBean bean = ((BlackListBean) obj);

            viewHolder.tv_name.setText(bean.getRealName());

            Log.e("realName", bean.getRealName());

            if (!StringUtil.isEmpty(bean.getAvatar())) {
                CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(context, bean.getAvatar(),
                        viewHolder.iv, ContextCompat.getDrawable(context, R.mipmap.avatar_def));
            }

        } else if (obj instanceof DisabledFriendListBean) {

            DisabledFriendListBean friendListBean = (DisabledFriendListBean) obj;

            viewHolder.tv_name.setText(friendListBean.getName());

            if (!StringUtil.isEmpty(friendListBean.getAvatar())) {
                CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(context, friendListBean.getAvatar(),
                        viewHolder.iv, ContextCompat.getDrawable(context, R.mipmap.avatar_def));
            }
        }

        return convertView;
    }


    class ViewHolder {
        private CircleImageView iv;
        private TextView tv_name, tv_delete;


        public ViewHolder(View convertView) {

            iv = ((CircleImageView) convertView.findViewById(R.id.iv));
            tv_name = ((TextView) convertView.findViewById(R.id.tv_realname));
            tv_delete = ((TextView) convertView.findViewById(R.id.tv_delete));

        }

    }
}
