package com.histudent.jwsoft.histudent.body.mine.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.activity.ImageBrowserActivity;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.enums.ShowImageType;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.manage.PhotoManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/6/1.
 * 在GridView中加载图片的dadpter
 */
public class GridViewPictureAdapter extends BaseAdapter {

    private Activity activity;
    private List<ActionListBean.ImagesBean> datas;
    private ActionListBean bean_;
    private int num;
    private ArrayList<ActionListBean.ImagesBean> url_list;
    private boolean isMyAlbum;

    public GridViewPictureAdapter(Activity activity, ActionListBean bean_, int num) {
        this.activity = activity;
        this.datas = bean_.getImages();
        this.bean_ = bean_;
        this.num = num;

        url_list = new ArrayList<>();

        if (datas != null) {
            for (int i = 0; i < datas.size(); i++) {
                ActionListBean.ImagesBean bean = new ActionListBean.ImagesBean();
                bean.setImgId(datas.get(i).getImgId());
                bean.setName(datas.get(i).getName());
                bean.setThumbnailUrl(datas.get(i).getThumbnailUrl());
                bean.setBigSizeUrl(datas.get(i).getBigSizeUrl());
                url_list.add(bean);
            }
        }
        if (bean_.getOwnerId().equals(HiCache.getInstance().getLoginUserInfo().getUserId())) {
            isMyAlbum = true;
        }
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
            //下拉项布局
            convertView = LayoutInflater.from(activity).inflate(R.layout.gridview_picture_item, null);

            holder.picture = (ImageView) convertView.findViewById(R.id.grid_picture);

            convertView.setTag(holder);
        } else {
            holder = (ChatHolder) convertView.getTag();
        }

        ActionListBean.ImagesBean model = datas.get(position);
        setViewHeight(holder.picture, num);
        holder.picture.setImageResource(R.drawable.default_placeholder_style_1);

        CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(activity, model.getThumbnailUrl(),
                holder.picture, PhotoManager.getInstance().getDefaultPlaceholderResource());

        setListener(holder.picture, position, url_list);


        return convertView;
    }

    class ChatHolder {
        ImageView picture;
    }

    private void setViewHeight(View view, int h) {
        ViewGroup.LayoutParams lp;
        lp = view.getLayoutParams();
        lp.width = h;
        lp.height = (int) Math.ceil(h * 3 / 4);
        view.setLayoutParams(lp);
    }

    private void setListener(View view, final int posion, final ArrayList<ActionListBean.ImagesBean> uri_list) {

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageBrower(posion, uri_list);

            }
        });

    }

    /**
     * 打开图片查看器
     *
     * @param position
     * @param urls2
     */
    protected void imageBrower(int position, ArrayList<ActionListBean.ImagesBean> urls2) {
//        int tag;
//        if (isMyAlbum) {
//            tag = 2;
//        } else {
//            tag = 3;
//        }
        if (bean_.getActivityItemKey().equals("CreateMicroBlog")) {
            ImageBrowserActivity.start(activity, position, 100, urls2, ShowImageType.SAVE, 0, "");
        } else if (bean_.getActivityItemKey().equals("UploadPhoto")) {
            ImageBrowserActivity.start(activity, position, 100, urls2, ShowImageType.COMMENT, 0, "");
        }

    }


}
