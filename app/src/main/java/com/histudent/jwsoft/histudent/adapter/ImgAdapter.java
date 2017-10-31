package com.histudent.jwsoft.histudent.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.histudent.jwsoft.histudent.HiStudentApplication;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.entity.WorkImgDeleteEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
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
    public ImgAdapter(Context context){
        this.mContext = context;
    }
    public void setList(List<String> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public void setAdd(boolean add) {
        isAdd = add;
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
            MyImageLoader.getIntent().displayLocalImage(mContext, new File(imgUrl), holder.mImage, R.drawable.default_placeholder_style_1);
        }
    }

    @Override
    public int getItemCount() {
        if (mList != null && mList.size() > 0) {
            if (isAdd) {
                return mList.size() + 1;
            } else {
                return mList.size();
            }
        }

        return 0;
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
                    if (getItemCount() != 0) {
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, getItemCount() - position);
                    } else {
                        notifyDataSetChanged();
                    }

                    break;
                case R.id.item_work_img:

                    break;
            }
        }

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
