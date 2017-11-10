package com.histudent.jwsoft.histudent.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.activity.image.ShowImageActivity;
import com.histudent.jwsoft.histudent.commen.keyword.utils.DisplayUtils;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.entity.ImageAttrEntity;
import com.histudent.jwsoft.histudent.entity.ImgAddEvent;
import com.histudent.jwsoft.histudent.entity.ShowImgEvent;
import com.histudent.jwsoft.histudent.entity.WorkImgDeleteEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by huyg on 2017/10/25.
 */

public class ImgAdapter extends RecyclerView.Adapter<ImgAdapter.ViewHolder> {
    private List<String> mList = new ArrayList<>();
    private boolean isDelete = false;//是否可以删除
    private boolean isAdd = false;//是否可以添加
    private Context mContext;
    private List<ImageAttrEntity> imageAttrEntities = new ArrayList<>();

    public ImgAdapter(Context context) {
        this.mContext = context;
    }

    public void setList(List<String> list) {
        this.mList = list;
        imageAttrEntities.clear();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                ImageAttrEntity imageAttrEntity = new ImageAttrEntity();
                imageAttrEntity.setThumbnailUrl(list.get(i));
                imageAttrEntity.setBigSizeUrl(list.get(i));
                imageAttrEntities.add(imageAttrEntity);
            }

        }
        notifyDataSetChanged();
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public void setAdd(boolean add) {
        if (add != isAdd) {
            isAdd = add;
            notifyDataSetChanged();
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_work_img, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImgAdapter.ViewHolder holder, int position) {
        if (position == mList.size()) {
            holder.mDelete.setVisibility(View.GONE);
            holder.mImage.setImageResource(R.mipmap.addpic);
        } else {
            if (isDelete) {
                holder.mDelete.setVisibility(View.VISIBLE);
            } else {
                holder.mDelete.setVisibility(View.GONE);
            }
            String imgUrl = mList.get(position);
            if (imgUrl.startsWith("http")) {
                MyImageLoader.getIntent().displayNetImage(mContext, imgUrl, holder.mImage, R.drawable.default_placeholder_style_1);
            } else {
                MyImageLoader.getIntent().displayLocalImage(mContext, new File(imgUrl), holder.mImage, R.drawable.default_placeholder_style_1);
            }
            holder.mImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position != mList.size()) {
                        showImageDetail(view, position, imageAttrEntities);
                    }

                }
            });
        }
    }


    private void showImageDetail(View view, int position, List<ImageAttrEntity> imageAttrs) {
        Intent intent = new Intent(mContext, ShowImageActivity.class);
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
        bundle.putInt("horizontal_space", DisplayUtils.dp2px(mContext, 4));
        bundle.putInt("vertical_space", DisplayUtils.dp2px(mContext, 4));
        bundle.putBoolean("isCb", false);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if (isAdd) {
            return mList == null ? 1 : mList.size() + 1;
        } else {
            return mList == null ? 0 : mList.size();
        }

    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_work_img)
        ImageView mImage;
        @BindView(R.id.item_img_delete)
        IconView mDelete;

        @OnClick({R.id.item_img_delete, R.id.item_work_img})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.item_img_delete:
                    int position = getLayoutPosition();
                    EventBus.getDefault().post(new WorkImgDeleteEvent(position));
                    mList.remove(position);
                    imageAttrEntities.remove(position);
                    if (getItemCount() != 0) {
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, getItemCount() - position);
                    } else {
                        notifyDataSetChanged();
                    }

                    break;
                case R.id.item_work_img:
                    if (getLayoutPosition() == mList.size()) {
                        EventBus.getDefault().post(new ImgAddEvent());
                    } else {
                        EventBus.getDefault().post(new ShowImgEvent(getLayoutPosition()));
                    }
                    break;
            }
        }

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
