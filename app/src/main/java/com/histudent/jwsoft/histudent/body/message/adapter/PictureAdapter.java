package com.histudent.jwsoft.histudent.body.message.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.mine.model.AlbumInfoModel;
import com.histudent.jwsoft.histudent.body.mine.model.PhotosModel;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.model.manage.PhotoManager;

import java.util.List;

/**
 * Created by liuguiyu on 2016/6/16.
 * 相册适配器
 */
public class PictureAdapter extends BaseAdapter {
    private List<Object> objects;
    private Context context;

    public PictureAdapter(Context context, List<Object> objects) {
        this.objects = objects;
        this.context = context;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
//        if (convertView == null) {
        viewHolder = new ViewHolder();
        View convertView = View.inflate(context, R.layout.class_center_picture_item, null);

        viewHolder.name = convertView.findViewById(R.id.name);
        viewHolder.num = convertView.findViewById(R.id.num);
        viewHolder.image = convertView.findViewById(R.id.image);
        viewHolder.layout_l = convertView.findViewById(R.id.layout_l);
        viewHolder.layout_r = convertView.findViewById(R.id.layout_r);

        convertView.setTag(viewHolder);
//        } else {
//            viewHolder = ((ViewHolder) convertView.getTag());
//        }

        Object object = objects.get(position);

        if (object instanceof AlbumInfoModel) {

            AlbumInfoModel model = (AlbumInfoModel) object;

            viewHolder.like_num.setVisibility(View.GONE);
            viewHolder.layout_l.setVisibility(View.VISIBLE);

            if (TextUtils.isEmpty(model.getAlbumId())) {
                viewHolder.layout_l.setVisibility(View.GONE);
                viewHolder.image.setBackgroundResource(R.color.bg_color);
                ViewGroup.LayoutParams lp;
                lp = viewHolder.layout_r.getLayoutParams();
                lp.width = getViewHeight();
                lp.height = lp.width + SystemUtil.dip2px(context, 50);
                viewHolder.layout_r.setLayoutParams(lp);
                viewHolder.image.setImageResource(R.mipmap.add_picture);
            } else {
                viewHolder.name.setText(model.getAlbumName());
                viewHolder.num.setText(model.getPhotoCount() + "");
                setViewHeight(viewHolder.layout_r);
                CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(context, model.getConverPhotoUrl(),
                        viewHolder.image, ContextCompat.getDrawable(context,R.drawable.default_placeholder_style_1));
            }

        } else {

            PhotosModel model = (PhotosModel) object;

            viewHolder.like_num.setVisibility(View.VISIBLE);
            viewHolder.layout_l.setVisibility(View.GONE);

            viewHolder.like_num.setText(model.getPraiseNum() + "");
            setViewHeight(viewHolder.layout_r);
            CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(context, model.getFilePath(),
                    viewHolder.image, PhotoManager.getInstance().getDefaultPlaceholderResource());
        }
        return convertView;
    }

    class ViewHolder {
        private TextView name, num;
        private Button like_num;
        private ImageView image;
        private RelativeLayout layout_l, layout_r;
    }

    private int getViewHeight() {
        WindowManager wm = (WindowManager) context.getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return (width - 30) / 2;
    }

    private void setViewHeight(View view) {
        ViewGroup.LayoutParams lp;
        lp = view.getLayoutParams();
        lp.width = getViewHeight();
        lp.height = lp.width;
        view.setLayoutParams(lp);
    }
}
