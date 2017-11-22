package com.histudent.jwsoft.histudent.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.mine.model.PhotosModel;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.model.entity.UploadImgClickEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by huyg on 2017/9/28.
 */

public class UploadImgAdapter extends RecyclerView.Adapter<UploadImgAdapter.ViewHolder> {

    private Context mContext;
    private List<PhotosModel> mPhotos = new ArrayList<>();
    private int itemWidth;
    private int itemHeight;

    public UploadImgAdapter(Context context) {
        this.mContext = context;
        initItemWidth();
    }

    public void setList(List<PhotosModel> photosModels) {
        this.mPhotos = photosModels;
        notifyDataSetChanged();
    }

    public void addList(List<PhotosModel> photosModels) {
        this.mPhotos.addAll(photosModels);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_upload_img, parent, false);
        return new ViewHolder(view);
    }


    private void initItemWidth() {
        int screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        itemWidth = (screenWidth - SystemUtil.dip2px(mContext, 4) * 2) / 3;
        itemHeight = itemWidth;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        PhotosModel photosModel = mPhotos.get(position);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.mImageView.getLayoutParams();
        params.width = itemWidth;
        params.height = itemHeight;
        holder.mImageView.setLayoutParams(params);

        if (photosModel != null) {
            CommonGlideImageLoader.getInstance().displayNetImage(mContext, photosModel.getFilePath(), holder.mImageView);
        }
    }

    @Override
    public int getItemCount() {
        return mPhotos == null ? 0 : mPhotos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_upload_img)
        ImageView mImageView;

        @OnClick(R.id.item_upload_img)
        public void onClick(View view) {
            EventBus.getDefault().post(new UploadImgClickEvent(view, mPhotos, getLayoutPosition()));
        }


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
