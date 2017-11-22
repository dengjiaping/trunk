package com.histudent.jwsoft.histudent.commen.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.hiworld.activity.MyCaptureVideoActivity;
import com.histudent.jwsoft.histudent.body.hiworld.bean.RelationPersonModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.photo_selecter.imageloader.SelectPhotoActivity;
import com.histudent.jwsoft.histudent.commen.utils.FileUtil;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.model.constant.Const;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.lidroid.xutils.bitmap.core.BitmapDecoder.calculateInSampleSize;

/**
 * Created by liuguiyu-pc on 2016/8/4.
 * 图片选择与剪裁类
 */
public class PictureTailorHelper {

    public static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
    public static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择(单张)
    public static final int PHOTO_REQUEST_GALLERYS = 1001;//从相册中选择(多张)
    public static final int PHOTO_REQUEST_CUT = 3;// 结果
    public static final int PHOTO = 110;// 结果
    public File photo_path;//剪裁后存放图片的地址
    private File clip_path; //拍照后图片存放的地址
    private String path;
    private Context mContext;
    private Uri mTransferUri;
    private boolean mIsTakePhoto;

    private PictureTailorHelper() {
    }

    private static final class PictureTailorHelperHolder {
        private static final PictureTailorHelper INSTANCE = new PictureTailorHelper();
    }

    public static final PictureTailorHelper getInstance() {
        deleteFile();
        return PictureTailorHelperHolder.INSTANCE;
    }

    /**
     * @param isDeleteFile 是否删除临时文件
     * @return
     */
    public static final PictureTailorHelper getInstance(boolean isDeleteFile) {
        if (isDeleteFile)
            deleteFile();
        return PictureTailorHelperHolder.INSTANCE;
    }

    /**
     * 显示获取图片选择对话框
     */
    public void showGetPhotoPictureListDialog(final Activity activity) {

        List<String> list = new ArrayList<>();
        list.add("拍照");
        list.add("从相册中获取");
        List<Integer> colorList = new ArrayList<>();
        colorList.add(Color.rgb(51, 51, 51));
        colorList.add(Color.rgb(51, 51, 51));


        ReminderHelper.getIntentce().showTopMenuDialog(activity, "", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.btn_01:
                        takePhoto(activity);
                        break;

                    case R.id.btn_02:
                        selectPicture(activity);
                        break;
                }

            }
        }, list, colorList, false);

    }

    /**
     * 调用系统的拍照功能
     *
     * @param activity
     */
    public void takePhoto(Activity activity) {
        mIsTakePhoto = true;
        String path = FileUtil.getPathFactory(FileUtil.CacheType.IMAGE, false);
        if (TextUtils.isEmpty(path)) return;
        photo_path = new File(path);
        Intent intentTakePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            intentTakePhoto.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo_path));
        } else {
            //7.0以上版本拍照
            Uri uri = FileProvider.getUriForFile(activity, Const.AUTHORITIES_SIT, photo_path);
            //将拍取的照片保存到指定URI
            intentTakePhoto.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intentTakePhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        //开启相机
        activity.startActivityForResult(intentTakePhoto, PHOTO_REQUEST_TAKEPHOTO);

//        //扫描文件
//        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        Uri uri = Uri.fromFile(photo_path);
//        intent.setData(uri);
//        activity.sendBroadcast(intent);
    }

    /**
     * 调用自定义的拍照功能（可摄像）
     */
    public void takePhotoAndAudio(Activity activity, int requestCode, int flag) {
        String path = FileUtil.getPathFactory(FileUtil.CacheType.IMAGE, true);
        MyCaptureVideoActivity.start(activity, path, requestCode, flag);
    }

    /**
     * 从相册中选择单张照片
     */
    public void selectPicture(Activity activity) {
        Intent intent_01 = new Intent(Intent.ACTION_PICK, null);
        intent_01.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(intent_01, PHOTO_REQUEST_GALLERY);
    }

    /**
     * 从相册中选择多张照片
     */
    public void selectPictures(Activity activity, ArrayList<String> list, int limit, ArrayList<RelationPersonModel> relations) {
        SelectPhotoActivity.start(activity, list, limit, relations);
    }

    /**
     * 从相册中选择多张照片
     */
    public void selectPictures(Activity activity, List<String> list, int limit) {
        SelectPhotoActivity.start(activity, list, limit);
    }

    private static final String TAG = "PictureTailorHelper";

    /**
     * 剪裁
     *
     * @param activity
     * @param uri
     * @param size
     */
    public void startPhotoZoom(BaseActivity activity, Uri uri, int size) {
        this.mContext = activity;
        path = FileUtil.getPathFactory(false, FileUtil.CacheType.IMAGE);
        clip_path = new File(path);
        if (uri == null) return;
        Intent intent = new Intent("com.android.camera.action.CROP");//调用Android系统自带的一个图片剪裁页面
        Uri imageUri = getImageUri(activity, intent, uri, null);

        //输入的uri
        intent.setDataAndType(imageUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);//去除图像黑边
        intent.putExtra("outputY", size);
        intent.putExtra("scaleUpIfNeeded", true);//黑边
        intent.putExtra("outputX", size);
//        intent.putExtra("return-data", true);
        Log.d("requestCode", "requestCode" + Uri.fromFile(clip_path));

        //输出的uri
        mTransferUri = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory() + "/tmp.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mTransferUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("return-data", false);

        activity.startActivityForResult(intent, PHOTO_REQUEST_CUT);
        mIsTakePhoto = false;
    }

    /**
     * 自由裁剪
     * 指定宽高
     *
     * @param activity
     * @param uri
     * @param width    宽度
     * @param height   高度
     */
    public void startPhotoZoom(BaseActivity activity, Uri uri, int width, int height, File file) {
        mIsTakePhoto = true;
        this.mContext = activity;
        path = FileUtil.getPathFactory(false, FileUtil.CacheType.IMAGE);
        clip_path = new File(path);
        if (uri == null) return;
        Intent intent = new Intent("com.android.camera.action.CROP");
        Uri imageUri = getImageUri(activity, intent, uri, file);
        intent.setDataAndType(imageUri, "image/*");
        intent.putExtra("aspectX", width);
        intent.putExtra("aspectY", height);
        intent.putExtra("scale", true);//去除图像黑边
        intent.putExtra("scaleUpIfNeeded", true);//黑边
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("crop", "true");

        Log.d("requestCode", "requestCode" + Uri.fromFile(clip_path));
        mTransferUri = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory() + "/tmp.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mTransferUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("return-data", false);
        activity.startActivityForResult(intent, PHOTO_REQUEST_CUT);
        mIsTakePhoto = false;
    }

    /**
     * 保存剪裁后的图片
     *
     * @param imageView
     * @param
     * @return
     */
    public String setPicToView(ImageView imageView, Activity activity) {

        if (clip_path == null) return null;

        if (imageView != null)
            MyImageLoader.getIntent().displayLocalImage(activity, clip_path, imageView);

        return clip_path.toString();
    }

    /**
     * 保存剪裁后的图片
     *
     * @param imageView
     * @param intent
     * @return
     */
    public String setPicToView(ImageView imageView, Intent intent) {
        Log.e(TAG, "setPicToView: ---->come in");
        if (intent == null) return null;
        Bitmap bitmap = getTailorBackBitmap(mContext, mTransferUri, 150, 150);
        clip_path = new File(FileUtil.getPathFactory(FileUtil.CacheType.IMAGE, false));
        if (imageView != null)
            imageView.setImageBitmap(bitmap);
        // 图像保存到文件中
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(clip_path);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (null != fileOutputStream) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return clip_path.toString();
        }
    }

    public String setPicToView(Intent data) {
        return setPicToView(null, data);
    }

    /**
     * 将进行剪裁后的图片删除
     */
    private static void deleteFile() {
        String path = FileUtil.getPathFactory(FileUtil.CacheType.IMAGE, true);
        if (TextUtils.isEmpty(path)) return;
        File file = new File(path);
        if (file == null || !file.exists()) return;
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) return;
            for (File f : childFile) {
                f.delete();
            }
        }
    }


    /**
     * 获取裁剪后返回过来的 根据之前定义好的URI
     *
     * @param context
     * @param uri
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private Bitmap getTailorBackBitmap(Context context, Uri uri, int reqWidth, int reqHeight) {
        InputStream input = null;
        Bitmap bitmap = null;
        try {
            input = context.getContentResolver().openInputStream(uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(input, null, options);
            if ((options.outWidth == -1) || (options.outHeight == -1))
                return null;
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
            options.inJustDecodeBounds = false;
            input = context.getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(input, null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    /**
     * 根据当前版本信息获取不同的uri
     *
     * @param activity
     * @param intent
     * @param uri
     * @return
     */
    private final Uri getImageUri(BaseActivity activity, Intent intent, Uri uri, File file) {
        Uri imageUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && mIsTakePhoto) {
            //7.0以上版本统一使用provider获取uri
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            //通过FileProvider创建一个content类型的Uri
            if (file != null) {
                imageUri = FileProvider.getUriForFile(activity, Const.AUTHORITIES_SIT, file);
            } else {
                imageUri = FileProvider.getUriForFile(activity, Const.AUTHORITIES_SIT, photo_path);
            }
        } else {
            imageUri = uri;
        }
        return imageUri;
    }

}
