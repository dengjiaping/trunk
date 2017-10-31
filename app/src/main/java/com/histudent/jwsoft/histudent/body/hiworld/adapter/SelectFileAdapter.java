package com.histudent.jwsoft.histudent.body.hiworld.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.hiworld.bean.FileListBean;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.List;

/**
 * Created by ZhangYT on 2016/6/28.
 */

//选择上传图片到文件夹的适配器
public class SelectFileAdapter extends BaseAdapter {

    private List<FileListBean.ItemsBean> list;
    private Context context;
    private int type;

    public SelectFileAdapter(List<FileListBean.ItemsBean> list, Context context, int type) {
        this.context = context;
        this.list = list;
        this.type = type;
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
            convertView = View.inflate(context, R.layout.select_file_name_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        FileListBean.ItemsBean photoFileBean = list.get(position);
        viewHolder.tv_file_name.setText(photoFileBean.getAlbumName());
        viewHolder.tv_num.setText(photoFileBean.getPhotoCount() + "张");


        //相册权限的判断

        /*
        * PrivacyStatus
         隐私状态：0-对所有人公开，1-对同学公开,2-仅自己可见,3-部分可见,4-部分不可见

        * */

        String authority = "";
        switch (photoFileBean.getPrivacyStatus()) {
            case 0:
                authority = "公开";
                break;
            case 1:
                if (type == 0) {//个人相册

                    authority = "同学可见";
                } else {//个人班级相册

                    authority = "成员可见";
                }

                break;
            case 2:
                authority = "仅自己可见";
                break;
            case 3:
                authority = "部分可见";
                break;
            case 4:
                authority = "部分不可见";
                break;
        }

        if (authority != "") {
            viewHolder.tv_authority.setText(authority);
        }


        //设置相册封面
        if (StringUtil.isEmpty(photoFileBean.getConverPhotoUrl())) {
            viewHolder.iv.setImageResource(R.mipmap.def_no_picture);

            //没有手动设置相册封面时加载默认图片
        } else {

            //加载图片
            CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(context, photoFileBean.getConverPhotoUrl(),
                    viewHolder.iv, ContextCompat.getDrawable(context, R.mipmap.def_no_picture));

        }

        return convertView;
    }

    class ViewHolder {
        private TextView tv_file_name, tv_authority, tv_num;
        private ImageView iv, iv_next;

        public ViewHolder(View converView) {
            tv_authority = converView.findViewById(R.id.tv_authority);
            tv_file_name = converView.findViewById(R.id.tv_photo_name);
            tv_num = converView.findViewById(R.id.tv_num);
            iv = converView.findViewById(R.id.iv);
            iv_next = converView.findViewById(R.id.iv_next);
        }
    }

}
