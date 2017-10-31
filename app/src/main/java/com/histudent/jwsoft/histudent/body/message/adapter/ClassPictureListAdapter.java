package com.histudent.jwsoft.histudent.body.message.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.mine.model.AlbumInfoModel;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.entity.AlbumClickEvent;
import com.histudent.jwsoft.histudent.manage.PhotoManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static com.histudent.jwsoft.histudent.R.id.num;

/**
 * Created by liuguiyu on 2016/6/16.
 * 相册列表适配器
 */
public class ClassPictureListAdapter extends BaseAdapter {
    private List<AlbumInfoModel> models=new ArrayList<>();
    private Context context;

    public ClassPictureListAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<AlbumInfoModel> models) {
        this.models = models;
        notifyDataSetChanged();
    }

    public void addList(List<AlbumInfoModel> models) {
        this.models.addAll(models);
        notifyDataSetChanged();
    }



    @Override
    public int getCount() {
        return models == null ? 0 : models.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.class_center_picture_item, null);

            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.num = convertView.findViewById(num);
            viewHolder.image = convertView.findViewById(R.id.image);
            viewHolder.layout_l = convertView.findViewById(R.id.layout_l);
            viewHolder.layout_r = convertView.findViewById(R.id.layout_r);
            viewHolder.albumLine1 = convertView.findViewById(R.id.view_album_line1);
            viewHolder.albumLine2 = convertView.findViewById(R.id.view_album_line2);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = ((ViewHolder) convertView.getTag());
        }


        AlbumInfoModel model = models.get(position);
        setViewHeight(viewHolder.layout_r, viewHolder.layout_l);
        viewHolder.layout_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new AlbumClickEvent(model));
            }
        });
        if (TextUtils.isEmpty(model.getAlbumId())) {
            viewHolder.image.setBackgroundResource(R.color.bg_color);
            viewHolder.image.setImageResource(R.mipmap.new_create_album);
            viewHolder.albumLine1.setVisibility(View.INVISIBLE);
            viewHolder.albumLine2.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.name.setText(model.getAlbumName());
            viewHolder.num.setText(model.getPhotoCount() + "");
            viewHolder.albumLine1.setVisibility(View.VISIBLE);
            viewHolder.albumLine2.setVisibility(View.VISIBLE);
            CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(context, model.getConverPhotoUrl(), viewHolder.image,
                    PhotoManager.getInstance().getDefaultPlaceholderResource());
        }
        return convertView;
    }

    class ViewHolder {
        private TextView name, num;
        private ImageView image;
        private LinearLayout layout_l, layout_r;
        private View albumLine1, albumLine2;
    }

    private void setViewHeight(View view, View view2) {
        int num = (SystemUtil.getScreenWind() - SystemUtil.dp2px(10)) / 2;
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.width = num;
        lp.height = num;
        view.setLayoutParams(lp);

        ViewGroup.LayoutParams lp2 = view2.getLayoutParams();
        lp2.width = num;
        lp2.height = SystemUtil.dp2px(45);
        view2.setLayoutParams(lp2);
    }

}
