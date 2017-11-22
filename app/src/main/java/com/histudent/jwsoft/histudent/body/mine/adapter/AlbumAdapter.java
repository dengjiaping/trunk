package com.histudent.jwsoft.histudent.body.mine.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.mine.activity.AlbumDetailActivity;
import com.histudent.jwsoft.histudent.body.mine.model.AlbumAdapterModel;
import com.histudent.jwsoft.histudent.body.mine.model.PictureInAlbumModel;
import com.histudent.jwsoft.histudent.commen.activity.ImageBrowserActivity;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.enums.ShowImageType;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.comment2.utils.ActionTypeEnum;
import com.histudent.jwsoft.histudent.model.manage.PhotoManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/8/19.
 */
public class AlbumAdapter extends BaseAdapter {

    private Activity activity;
    private List<AlbumAdapterModel> mAlbumAdapterModelsList;
    private boolean isShowSeleted;
    private boolean isChecked;
    private List<String> list_seletcedIds = new ArrayList<>();
    private View headView;
    private ActionTypeEnum typeEnum;
    private String id;
    private boolean isManager;


    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    private boolean getIsChecked() {

        return isChecked;
    }

    @Override
    public void notifyDataSetChanged() {
        CleanDatas();
        super.notifyDataSetChanged();
    }

    public AlbumAdapter(Activity activity, List<AlbumAdapterModel> models, View headView, ActionTypeEnum typeEnum, String id, boolean isManager) {
        this.activity = activity;
        this.mAlbumAdapterModelsList = models;
        this.headView = headView;
        this.typeEnum = typeEnum;
        this.id = id;
        this.isManager = isManager;
    }

    public void setIsShowSelected(boolean isShowSelected) {
        this.isShowSeleted = isShowSelected;
    }

    @Override
    public int getCount() {
        return mAlbumAdapterModelsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mAlbumAdapterModelsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Glide.get(activity).clearMemory();
        if (mAlbumAdapterModelsList.size() == 1 && mAlbumAdapterModelsList.get(0) == null) {
            return SystemUtil.setEmptyView(activity, headView, R.drawable.no_photo_video, R.string.have_no_classPictures);
        } else {
            AlbumViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(activity).inflate(R.layout.activity_album_item, parent, false);
                viewHolder = new AlbumViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (AlbumViewHolder) convertView.getTag();
            }
            AlbumAdapterModel model = mAlbumAdapterModelsList.get(position);
            int n = model.getBeans().size() % 3;
            int m = model.getBeans().size() / 3;
            setViewHeight(viewHolder.gridView, SystemUtil.getScreenWind(), n == 0 ? m == 0 ? SystemUtil.getScreenWind() : SystemUtil.getScreenWind() / 3 * m : SystemUtil.getScreenWind() / 3 * (m + 1));

            //标记每个图片的所在的父亲的位置
            for (int i = 0; i < model.getBeans().size(); i++) {
                model.getBeans().get(i).setParentPosition(position);
            }

            GridAdapter mSubPhotoGridAdapter = new GridAdapter(activity, model.getBeans());
            viewHolder.gridView.setAdapter(mSubPhotoGridAdapter);

            String[] time_ = model.getTime().split("-");
            viewHolder.time.setText(time_[0] + "年" + time_[1] + "月" + time_[2] + "日");
            if (isShowSeleted) {
                boolean isAllChecekd = true;
                viewHolder.checkBox.setVisibility(View.VISIBLE);
                for (int i = 0; i < model.getBeans().size(); i++) {
                    if (!model.getBeans().get(i).isSecleted()) {
                        isAllChecekd = false;
                        break;
                    }
                }

                viewHolder.checkBox.setChecked(isAllChecekd);
                viewHolder.checkBox.setOnCheckedChangeListener((CompoundButton compoundButton, boolean b) -> {
                    mSubPhotoGridAdapter.setIsChecked(b);
                    mSubPhotoGridAdapter.notifyDataSetChanged();
                    for (PictureInAlbumModel.ItemsBean.PhotoListBean bean : model.getBeans()) {
                        saveImageUrl(bean.getPhotoId(), b);
                    }
                });
            } else {
                viewHolder.checkBox.setVisibility(View.GONE);
                viewHolder.gridView.setOnItemClickListener((AdapterView<?> adapterView, View view, int i, long l) -> {
                    ArrayList<ActionListBean.ImagesBean> beans = getImageShowData(mAlbumAdapterModelsList.get(position).getBeans());
                    if (beans != null && AlbumDetailActivity.model.getOwnerList() != null) {
                        switch (typeEnum) {
                            case CLASS:
                                ImageBrowserActivity.start(activity, i, 100, beans, AlbumDetailActivity.model.isIsEditAuth() ? ShowImageType.EDIT :
                                        AlbumDetailActivity.model.isIsUploadAuth() ? ShowImageType.COMMENT : ShowImageType.SAVE, 1, id);
                                break;
                            case OWNER:
                                ImageBrowserActivity.start(activity, i, 100, beans, AlbumDetailActivity.model.isIsEditAuth() ? ShowImageType.EDIT : ShowImageType.COMMENT, 0, id);
                                break;
                            case GROUP:
                                ImageBrowserActivity.start(activity, i, 100, beans, AlbumDetailActivity.model.isIsEditAuth() ? ShowImageType.EDIT :
                                        AlbumDetailActivity.model.isIsUploadAuth() ? ShowImageType.COMMENT : ShowImageType.SAVE, 2, id);
                                break;
                        }
                    }
                });
            }
        }

        return convertView;
    }

    public boolean getIsUpDate() {

        return isShowSeleted;
    }


    class GridAdapter extends BaseAdapter {
        private List<PictureInAlbumModel.ItemsBean.PhotoListBean> gift;
        private Context mContext;
        private boolean isChecked;
        private final int size = SystemUtil.getScreenWind() / 3;
        private final int with = size, height = size;

        public void setIsChecked(boolean checked) {
            this.isChecked = checked;

            for (int i = 0; i < gift.size(); i++) {
                gift.get(i).setSecleted(checked);
            }
        }

        public GridAdapter(Context context, List<PictureInAlbumModel.ItemsBean.PhotoListBean> gift) {
            this.mContext = context;
            this.gift = gift;
        }

        @Override
        public int getCount() {
            return gift.size();
        }

        @Override
        public Object getItem(int position) {
            return gift.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public void notifyDataSetChanged() {
            CleanDatas();
            super.notifyDataSetChanged();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.emotion_imag, parent, false);
            CheckBox checkBox = view.findViewById(R.id.cb_single_iv);
            ImageView iv_image = view.findViewById(R.id.iv_img);
            setViewHeight(iv_image, with, height);

            CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(activity, gift.get(position).getFilePath(), iv_image,
                    PhotoManager.getInstance().getDefaultPlaceholderResource(), with, height);

            if (isShowSeleted) {
                checkBox.setVisibility(View.VISIBLE);
                if (isChecked) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(gift.get(position).isSecleted());
                }
                checkBox.setOnCheckedChangeListener((CompoundButton compoundButton, boolean b) -> {
                    gift.get(position).setSecleted(b);

                    List<String> s = new ArrayList<>();
                    s.add(gift.get(position).getPhotoId());
                    saveImageUrl(gift.get(position).getPhotoId());
                });

                iv_image.setOnClickListener((View v) -> checkBox.setChecked(!checkBox.isChecked()));
            } else {
                checkBox.setVisibility(View.GONE);
            }
            return view;
        }
    }

    private void saveImageUrl(String photoId, boolean flag) {
        if (flag) {
            if (!AlbumDetailActivity.picture_ids.contains(photoId)) {
                AlbumDetailActivity.picture_ids.add(photoId);
            }
        } else {
            if (AlbumDetailActivity.picture_ids.contains(photoId)) {
                AlbumDetailActivity.picture_ids.remove(photoId);
            }
        }

    }

    private void saveImageUrl(String photoId) {
        if (AlbumDetailActivity.picture_ids.contains(photoId)) {
            AlbumDetailActivity.picture_ids.remove(photoId);
        } else {
            AlbumDetailActivity.picture_ids.add(photoId);
        }
    }

    public void CleanDatas() {

        list_seletcedIds.clear();
    }

    private void setViewHeight(View view, int w, int h) {
        ViewGroup.LayoutParams lp;
        lp = view.getLayoutParams();
        lp.width = w;
        lp.height = h;
        view.setLayoutParams(lp);
    }

    /**
     * 获取照片查看器的数据源
     */
    private ArrayList<ActionListBean.ImagesBean> getImageShowData(List<PictureInAlbumModel.ItemsBean.PhotoListBean> models) {


        ArrayList<ActionListBean.ImagesBean> beans = new ArrayList<>();

        for (int n = 0; n < models.size(); n++) {
            PictureInAlbumModel.ItemsBean.PhotoListBean itemsBean = models.get(n);
            ActionListBean.ImagesBean bean = new ActionListBean.ImagesBean();
            bean.setImgId(itemsBean.getPhotoId());
            bean.setName(itemsBean.getPhotoName());
            bean.setThumbnailUrl(itemsBean.getFilePath());
            bean.setBigSizeUrl(itemsBean.getFilePath());
            beans.add(bean);
        }
        return beans;
    }


    static class AlbumViewHolder {

        CheckBox checkBox;
        TextView time;
        GridView gridView;

        public AlbumViewHolder(View convertView) {
            this.checkBox = convertView.findViewById(R.id.checkbox);
            this.time = convertView.findViewById(R.id.activity_album_item_time);
            this.gridView = convertView.findViewById(R.id.activity_album_item_grid);
        }
    }
}
