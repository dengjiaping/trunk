package com.histudent.jwsoft.histudent.commen.photo_selecter.imageloader;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.photo_selecter.utils.CommonAdapter;
import com.histudent.jwsoft.histudent.commen.photo_selecter.utils.ViewHolder;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.ArrayList;
import java.util.List;


public class MyAdapter extends CommonAdapter<String> {

    /**
     * 用户选择的图片，存储为图片的完整路径
     */
    //public static ArrayList<String> mSelectedImage;
    public ArrayList<String> mSelectedImage;
    private getImageCountCallBack callBack;


    /**
     * 文件夹路径
     */
    private int limitCount;


    public MyAdapter(Context context, List<String> mDatas, int itemLayoutId, ArrayList<String> urls, int limitCount, getImageCountCallBack callBack) {
        super(context, mDatas, itemLayoutId);
        mSelectedImage = urls;
        this.limitCount = limitCount;
        this.callBack = callBack;

    }

    @Override
    public void convert(final ViewHolder helper, final String item) {
        //设置no_pic
        helper.setImageResource(R.id.id_item_image, R.mipmap.pictures_no);
        //设置no_selected
        helper.setCheckResource(R.id.id_item_select,
                mContext.getResources().getColor(R.color.white));

        final ImageView mImageView = helper.getView(R.id.id_item_image);
        final IconView mSelect = helper.getView(R.id.id_item_select);

        if (!StringUtil.isEmpty(item) && item.equals("camera")) {
            mSelect.setVisibility(View.GONE);
            helper.setCameraImageResource(R.id.id_item_image, R.mipmap.camera_);
            //设置no_selected
            helper.setCheckResource(R.id.id_item_select,
                    mContext.getResources().getColor(R.color.transparent));
        } else {
            mSelect.setVisibility(View.VISIBLE);
            helper.setImageByUrl(R.id.id_item_image, item);
        }

        mImageView.setColorFilter(null);


        //设置ImageView的点击事件
        mImageView.setOnClickListener(new OnClickListener() {
            //选择，则将图片变暗，反之则反之
            @Override
            public void onClick(View v) {

                if (!StringUtil.isEmpty(item) && item.equals("camera")) {

                    callBack.getImageCount(SelectPhotoActivity.CAMERA);//点击拍照

                } else {

                    if (mSelectedImage.contains(item)) {
                        mSelectedImage.remove(item);
                        mSelect.setTextColor(mContext.getResources().getColor(R.color.white));
                        mImageView.setColorFilter(null);

                    } else {// 未选择该图片

                        if (limitCount == 0) {
                            mSelectedImage.add(item);
                            mSelect.setTextColor(mContext.getResources().getColor(R.color.green_color));
                            mImageView.setColorFilter(Color.parseColor("#77000000"));
                        } else {
                            if (limitCount != 1) {
                                if (mSelectedImage.size() < limitCount) {
                                    mSelectedImage.add(item);
                                    mSelect.setTextColor(mContext.getResources().getColor(R.color.green_color));
                                    mImageView.setColorFilter(Color.parseColor("#77000000"));
                                }
                            } else {

                                mSelectedImage.clear();
                                mSelectedImage.add(item);
                                mSelect.setTextColor(mContext.getResources().getColor(R.color.green_color));
                                mImageView.setColorFilter(Color.parseColor("#77000000"));
                            }
                        }
                    }

                    callBack.getImageCount(mSelectedImage.size());

                }
                // 已经选择过该图片
            }
        });

        /**
         * 已经选择过的图片，显示出选择过的效果
         */
        if (mSelectedImage.contains(item)) {
            mSelect.setTextColor(mContext.getResources().getColor(R.color.green_color));
            mImageView.setColorFilter(Color.parseColor("#77000000"));
        } else {
            mSelect.setTextColor(mContext.getResources().getColor(R.color.white));
            mImageView.setColorFilter(null);
        }

    }

    public interface getImageCountCallBack {
        void getImageCount(int count);
    }
}
