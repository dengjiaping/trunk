package com.histudent.jwsoft.histudent.body.hiworld.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.histudent.jwsoft.histudent.HiStudentApplication;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.hiworld.bean.RelationPersonModel;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.IconView;

import java.io.File;
import java.util.List;


/**
 * 上传图片到相册的适配器
 * Created by ZhangYT on 2016/8/2.
 */
public class UploadImageRecyclerViewAdapter extends RecyclerView.Adapter {

    private List<String> list;
    private OnRecyclerViewOnClickListener listener;
    private List<RelationPersonModel> relationPersonModels;


    public UploadImageRecyclerViewAdapter(List<String> list, OnRecyclerViewOnClickListener listener) {
        this.list = list;
        this.listener = listener;
    }


    //用于显示图片关联用户
    public void initRelationData(List<RelationPersonModel> list) {
        this.relationPersonModels = list;
    }


    private boolean isHaveRelationPerson(String fileName) {

        if (relationPersonModels != null && relationPersonModels.size() > 0) {

            for (RelationPersonModel m : relationPersonModels) {
                if (m.getPicName().equals(fileName) && m.getUsers() != null && m.getUsers().size() > 0) {
                    return true;
                }
            }
        }

        return false;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new BodyItemAdapter(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_image_item, null));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        if (!list.get(position).equals("add")) {
            final ImageView imageView = ((BodyItemAdapter) holder).getImageView();

            File file = new File(list.get(position));
            MyImageLoader.getIntent().displayLocalImage(HiStudentApplication.getInstance(), file, imageView,R.drawable.default_placeholder_style_1);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (isHaveRelationPerson(file.getName())) {
                ((BodyItemAdapter) holder).getRelationIcon().setVisibility(View.VISIBLE);
            } else {
                ((BodyItemAdapter) holder).getRelationIcon().setVisibility(View.GONE);
            }
            imageView.setOnClickListener((View v) -> listener.setOnItemClickListener(v, position));

        } else {
            ImageView imageView = ((BodyItemAdapter) holder).getImageView();
            imageView.setImageResource(R.mipmap.addpic);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setOnClickListener((View v) -> listener.setOnItemClickListener(v, position));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class BodyItemAdapter extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private IconView icon_relation;

        public BodyItemAdapter(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.iv);
            icon_relation = itemView.findViewById(R.id.icon_relation);


        }

        private ImageView getImageView() {
            return imageView;
        }

        private IconView getRelationIcon() {
            return icon_relation;
        }
    }


    public interface OnRecyclerViewOnClickListener {
        void setOnItemClickListener(View v, int position);
    }

}
