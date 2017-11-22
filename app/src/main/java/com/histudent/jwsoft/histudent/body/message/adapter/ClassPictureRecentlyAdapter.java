package com.histudent.jwsoft.histudent.body.message.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.view.activity.image.ShowImageActivity;
import com.histudent.jwsoft.histudent.body.mine.model.PhotosModel;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.keyword.utils.DisplayUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.comment2.utils.ActionTypeEnum;
import com.histudent.jwsoft.histudent.model.entity.ImageAttrEntity;
import com.histudent.jwsoft.histudent.model.manage.PhotoManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuguiyu on 2016/6/16.
 * 照片适配
 */
public class ClassPictureRecentlyAdapter extends BaseAdapter {
    private List<PhotosModel> models;
    private Context context;
    private ActionTypeEnum typeEnum;

    public ClassPictureRecentlyAdapter(Context context, List<PhotosModel> models, ActionTypeEnum typeEnum) {
        this.models = models;
        this.context = context;
        this.typeEnum = typeEnum;

    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int position) {
        return models.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {

            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.picture_item, null);
            viewHolder.image = convertView.findViewById(R.id.image);
            viewHolder.layout_r = convertView.findViewById(R.id.layout_r);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = ((ViewHolder) convertView.getTag());
        }

        final PhotosModel model = models.get(position);

        setViewHeight(viewHolder.layout_r);
        CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(context, model.getFilePath(), viewHolder.image,
                PhotoManager.getInstance().getDefaultPlaceholderResource());

        viewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActionListBean.ImagesBean imagesBean;
                List<ImageAttrEntity> imageAttrs = new ArrayList<>();
                for (int i = 0; i < models.size(); i++) {
                    ImageAttrEntity imageAttrEntity = new ImageAttrEntity();
                    imageAttrEntity.setBigSizeUrl(models.get(i).getFilePath());
                    imageAttrEntity.setThumbnailUrl(models.get(i).getFilePath());
                    imageAttrs.add(imageAttrEntity);
                }

                showImageDetail(view, position, imageAttrs);
//                ImageBrowserActivity.start((BaseActivity) context, position, 10, urls,
//                        ShowImageType.COMMENT, typeEnum == ActionTypeEnum.OWNER ? 0 : typeEnum == ActionTypeEnum.CLASS ? 1 : 2, "");
            }
        });

        return convertView;
    }


    private void showImageDetail(View view, int position, List<ImageAttrEntity> imageAttrs) {
        Intent intent = new Intent(context, ShowImageActivity.class);
        Bundle bundle = new Bundle();
        int[] location = new int[2];
        view.getLocationInWindow(location);
        int x = location[0];
        int y = location[1];
        bundle.putInt("x", x);
        bundle.putInt("y", y);
        bundle.putInt("width", view.getWidth());
        bundle.putInt("height", view.getHeight());
        bundle.putBoolean("isOperate", true);
        bundle.putSerializable("photos", (Serializable) imageAttrs);
        bundle.putInt("position", position);
        bundle.putInt("column_num", 3);
        bundle.putInt("horizontal_space", DisplayUtils.dp2px(context, 4));
        bundle.putInt("vertical_space", DisplayUtils.dp2px(context, 4));

        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    class ViewHolder {
        private ImageView image;
        private RelativeLayout layout_r;
    }

    private void setViewHeight(View view) {
        ViewGroup.LayoutParams lp;
        lp = view.getLayoutParams();
        int num = (SystemUtil.getScreenWind() - SystemUtil.dp2px(4)) / 3;
        lp.width = num;
        lp.height = num;
        view.setLayoutParams(lp);
    }
}
