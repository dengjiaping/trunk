package com.histudent.jwsoft.histudent.body.find.adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.bean.GroupBean;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.manage.PhotoManager;

import java.util.List;

/**
 * 社群简介图片列表
 * Created by ZhangYT on 2016/8/10.
 */
public class GroupImageListAdapter extends BaseAdapter {

    private List<GroupBean.GroupDescriptionImgsListBean> list;
    private Activity context;

    public GroupImageListAdapter(Activity context, List<GroupBean.GroupDescriptionImgsListBean> list) {

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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.adapter_group_image_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = ((ViewHolder) convertView.getTag());
        }

        String path = list.get(position).getSavePath();
//        MyImageLoader.getIntent().displayNetImageWithAnimation(context, path, viewHolder.iamge, R.mipmap.default_image);
        CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(context, path,
                viewHolder.iamge, PhotoManager.getInstance().getDefaultPlaceholderResource());
        return convertView;
    }


    class ViewHolder {
        private ImageView iamge;

        public ViewHolder(View convertView) {
            iamge = (ImageView) convertView.findViewById(R.id.iamge);
        }
    }

}
