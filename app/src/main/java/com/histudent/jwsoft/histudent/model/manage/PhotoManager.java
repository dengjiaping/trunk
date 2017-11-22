package com.histudent.jwsoft.histudent.model.manage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.sdk.android.vod.upload.VODUploadCallback;
import com.alibaba.sdk.android.vod.upload.VODUploadClient;
import com.alibaba.sdk.android.vod.upload.VODUploadClientImpl;
import com.alibaba.sdk.android.vod.upload.model.UploadFileInfo;
import com.alibaba.sdk.android.vod.upload.model.VodInfo;
import com.histudent.jwsoft.histudent.HTApplication;
import com.histudent.jwsoft.histudent.HiStudentLog;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.view.activity.clock.ReadClockInActivity;
import com.histudent.jwsoft.histudent.body.hiworld.activity.WatchEssayVideoActivity;
import com.histudent.jwsoft.histudent.body.hiworld.adapter.UploadImageRecyclerViewAdapter;
import com.histudent.jwsoft.histudent.body.hiworld.bean.MovieTokenBean;
import com.histudent.jwsoft.histudent.body.hiworld.bean.RelationPersonModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.photo.utils.ImageUtils;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.comment2.bean.AddressInforBean;
import com.histudent.jwsoft.histudent.model.entity.StoreAddPhotoVideoEntity;
import com.histudent.jwsoft.histudent.model.entity.StoreUploadVideoEntity;
import com.histudent.jwsoft.histudent.tool.CommonTool;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lichaojie on 2017/8/21.
 * desc:
 * 添加图片、拍摄视频、选择图片、获取默认加载图片资源管理类
 */

public class PhotoManager {


    private PhotoManager() {
    }

    private static final class PhotoManagerHolder {
        static final PhotoManager INSTANCE = new PhotoManager();
    }

    public static PhotoManager getInstance() {
        return PhotoManagerHolder.INSTANCE;
    }

    /**
     * 处理从相册返回过来的数据
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void solveRequestCode1001(final int requestCode, final int resultCode,
                                     final Intent data,
                                     final ArrayList<String> photoListUrl,
                                     final ArrayList<RelationPersonModel> relationPersonModels,
                                     final UploadImageRecyclerViewAdapter uploadImageRecyclerViewAdapter) {
        if (resultCode == 200) {
            photoListUrl.clear();
            if (data != null) {
                //返回的图片地址
                final List<String> carrySelectPhotoUrlList = data.getStringArrayListExtra("return");
                photoListUrl.addAll(0, carrySelectPhotoUrlList);
                if (requestCode == 100 || requestCode == 1001) {
                    //返回关联图片关系集合
                    if (data.getSerializableExtra("relations") != null) {
                        relationPersonModels.clear();
                        relationPersonModels.addAll((ArrayList<RelationPersonModel>) data.getSerializableExtra("relations"));
                        uploadImageRecyclerViewAdapter.initRelationData(relationPersonModels);
                    }
                }
            }
        }
    }


    /**
     * 处理拍照或者摄像返回的数据
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public StoreAddPhotoVideoEntity solveRequestCode201(Context context, int requestCode, int resultCode, Intent data,
                                                        boolean isVideoMovie, String fileMovieUrl,
                                                        double fileMovieDuration, final ArrayList<String> photoListUrl,
                                                        String fileMovieTransfer, final AppCompatImageView ivMovieVideo,
                                                        final AppCompatTextView tvMovieTime, final IconView mIvPublishMoviePlay) {
        StoreAddPhotoVideoEntity storeAddPhotoVideoEntity = new StoreAddPhotoVideoEntity();
        if (resultCode == -1) {
            //摄像返回
            isVideoMovie = true;
            fileMovieUrl = data.getStringExtra("file");
            fileMovieDuration = (int) data.getLongExtra("duration", 0);
            photoListUrl.clear();
            if (fileMovieUrl == null) return null;
            Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(fileMovieUrl, MediaStore.Images.Thumbnails.MINI_KIND);
            fileMovieTransfer = CommonTool.convertIconToString(bitmap);
            if (bitmap == null) return null;
            ivMovieVideo.setImageBitmap(bitmap);
            tvMovieTime.setText(CommonTool.getTimeFromMillisecond((long) fileMovieDuration));
            mIvPublishMoviePlay.setOnClickListener((View view) -> WatchEssayVideoActivity.start((BaseActivity) context, data.getStringExtra("file"), 10));

            storeAddPhotoVideoEntity.setFileMovieTransfer(fileMovieTransfer);
            storeAddPhotoVideoEntity.setFileMovieDuration((int) fileMovieDuration);
            storeAddPhotoVideoEntity.setFileMovieUrl(fileMovieUrl);
        } else if (resultCode == -2) {
            //拍照返回
            isVideoMovie = false;
            photoListUrl.remove(photoListUrl.size() - 1);
            photoListUrl.add(data.getStringExtra("file"));
        }
        storeAddPhotoVideoEntity.setVideoMovie(isVideoMovie);

        return storeAddPhotoVideoEntity;
    }


    /**
     * 处理图片预览返回数据
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void solveRequestCode100(int requestCode, int resultCode, Intent data,
                                    final ArrayList<String> photoListUrl,
                                    ArrayList<RelationPersonModel> relationPersonModels,
                                    final UploadImageRecyclerViewAdapter uploadImageRecyclerViewAdapter) {
        if (resultCode == 200) {
            photoListUrl.clear();
            relationPersonModels.clear();
            relationPersonModels.addAll((ArrayList<RelationPersonModel>) data.getSerializableExtra("relations"));
//            relationPersonModels = (ArrayList<RelationPersonModel>) data.getSerializableExtra("relations");
            uploadImageRecyclerViewAdapter.initRelationData(relationPersonModels);
            if (data != null)
                photoListUrl.addAll(0, data.getStringArrayListExtra("return"));
        }
    }


    /**
     * 处理地理位置返回数据
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public StoreAddPhotoVideoEntity solveRequestCode200_300(Context context, int requestCode, int resultCode,
                                                            Intent data, AddressInforBean addressInformationBean,
                                                            final AppCompatTextView currentLocation, final IconView ivLocationIcon,
                                                            boolean isAutoLocation) {
        StoreAddPhotoVideoEntity entity = new StoreAddPhotoVideoEntity();
        if (data == null || data.getSerializableExtra("address") == null) return null;
        addressInformationBean = (AddressInforBean) data.getSerializableExtra("address");
        if (addressInformationBean.isShowAddress()) {
            if (currentLocation.getText().toString().equals(addressInformationBean.getCity() + "·" + addressInformationBean.getName())) {
                ivLocationIcon.setTextColor(ContextCompat.getColor(context, R.color.page_bg_color_v2));
                currentLocation.setTextColor(ContextCompat.getColor(context, R.color.text_black_h1));
            } else {
                ivLocationIcon.setTextColor(ContextCompat.getColor(context, R.color.page_bg_color_v2));
                currentLocation.setTextColor(ContextCompat.getColor(context, R.color.text_black_h1));
            }
            currentLocation.setText(addressInformationBean.getCity() + "·" + addressInformationBean.getName());
            isAutoLocation = false;
        } else {
            currentLocation.setText("地址");
            currentLocation.setTextColor(ContextCompat.getColor(context, R.color.text_black_h1));
            ivLocationIcon.setTextColor(ContextCompat.getColor(context, R.color.page_bg_color_v2));
            isAutoLocation = true;
        }
        entity.setAddressInformationBean(addressInformationBean);
        entity.setAutoLocation(isAutoLocation);
        return entity;
    }


    /**
     * 处理上传图片压缩后 返回结果
     *
     * @param msg
     * @param photoListUrl
     * @param photoCompressUrlTemp
     */
    public boolean solvePhotoCompressCase(Message msg, ArrayList<String> photoListUrl, ArrayList<String> photoCompressUrlTemp) {
        if (msg.arg1 == 0) {
            HiStudentLog.e("---->压缩保存失败...");
            return false;
        } else {
            if (photoListUrl != null && photoListUrl.size() > 0) {
                HiStudentLog.e("---->压缩保存完成...开始长传文件夹");
                for (int i = 0; i < photoListUrl.size(); i++) {
                    if (!photoListUrl.get(i).equals("add"))
                        photoCompressUrlTemp.add(photoListUrl.get(i));
                }
                photoListUrl.clear();
            }

            if (photoCompressUrlTemp != null) {
                final int size = photoCompressUrlTemp.size();
                for (int i = 0; i < size; i++) {
                    photoListUrl.add(ImageUtils.getCompressFilePath(photoCompressUrlTemp.get(i)));
                }
            }
            photoCompressUrlTemp.clear();
        }
        return true;
    }


    private boolean isPublishingVideo = false;

    /**
     * 上传视频文件
     */
    public void uploadVideoFile(final Context context, final String fileMovieUrl, final String fileMovieTransfer,
                                final int fileMovieDuration) {
        if (isPublishingVideo)
            return;
        isPublishingVideo = true;
        HashMap<String, Object> map = new HashMap<>();
        map.put("fileSize", new File(fileMovieUrl).length());
        HiStudentHttpUtils.postDataByOKHttp(true, (BaseActivity) context, map, HistudentUrl.getVodUploadAuth_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                MovieTokenBean tokenBean = JSONObject.parseObject(result, MovieTokenBean.class);
                if (tokenBean.getRet() == 1)
                    initUpload(tokenBean, context, fileMovieTransfer, fileMovieDuration, fileMovieUrl);
            }

            @Override
            public void onFailure(String errorMsg) {
                isPublishingVideo = false;
            }
        });

    }


    /**
     * 初始化视频上传函数
     */
    private void initUpload(final MovieTokenBean tokenBean, final Context context,
                            final String fileMovieTransfer, final int fileMovieDuration,
                            final String fileMovieUrl) {
        if (tokenBean == null)
            return;
        final StoreUploadVideoEntity video = new StoreUploadVideoEntity();
        VODUploadClient uploadClient = new VODUploadClientImpl(context);
        VODUploadCallback callback = new VODUploadCallback() {

            @Override
            public void onUploadSucceed(UploadFileInfo info) {
                video.videoId = tokenBean.getData().getVideoId();
                video.videoCover = fileMovieTransfer;
                if (fileMovieDuration > 0)
                    video.videoTimeLength = fileMovieDuration / 1000;
                if (context instanceof ReadClockInActivity) {
                    ((ReadClockInActivity) context).publishReadClockFeel(video);
                }
            }

            @Override
            public void onUploadFailed(UploadFileInfo info, String code, String message) {
                HiStudentLog.e("onfailed ------------------ ");
            }

            @Override
            public void onUploadProgress(UploadFileInfo info, long uploadedSize, long totalSize) {
            }

            @Override
            public void onUploadTokenExpired() {
                HiStudentLog.e("onExpired ------------- ");
            }

            @Override
            public void onUploadRetry(String code, String message) {
                HiStudentLog.e("onUploadRetry ------------- ");
            }

            @Override
            public void onUploadRetryResume() {
                HiStudentLog.e("onUploadRetryResume ------------- ");
            }

            @Override
            public void onUploadStarted(UploadFileInfo uploadFileInfo) {
                HiStudentLog.e("onUploadStarted ------------- ");
            }
        };
        uploadClient.init(callback);
        uploadClient.addFile(fileMovieUrl, getVodInfo());
        uploadClient.setUploadAuthAndAddress(getUploadFileInfo(fileMovieUrl), tokenBean.getData().getUploadAuth(), tokenBean.getData().getUploadAddress());
        uploadClient.start();
    }

    /**
     * 创建视频信息实体类
     *
     * @return
     */
    private VodInfo getVodInfo() {
        VodInfo vodInfo = new VodInfo();
        vodInfo.setCateId((int) System.currentTimeMillis());
        return vodInfo;
    }

    /**
     * 创建视频信息实体类
     *
     * @param fileMovieUrl
     * @return
     */
    private UploadFileInfo getUploadFileInfo(String fileMovieUrl) {
        UploadFileInfo vodInfo = new UploadFileInfo();
        vodInfo.setFilePath(fileMovieUrl);
        return vodInfo;
    }


    /**
     * 获取占位图加载资源
     * @return
     */
    public final Drawable getDefaultPlaceholderResource() {
        final Drawable drawable = ContextCompat.getDrawable(HTApplication.getInstance(), R.drawable.default_placeholder_style_1);
        return drawable;
    }

}
