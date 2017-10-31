package com.histudent.jwsoft.histudent.comment2.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;

import java.io.File;
import java.io.FileNotFoundException;


/**
 * 获取照片
 *
 */
public class TakePicUtils {


    //开启拍照的意图
    public static void StartCameraIntent(Activity activity, int resultCode) {

        File file = getTepFile2();
        if (file.exists()) {
            file.delete();
        }

        FileUtils.DeleteTepFile();
        Intent intent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        //下面这句指定调用相机拍照后的照片存储的路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                .fromFile(TakePicUtils.getTepFile()));
        activity.startActivityForResult(intent, resultCode);

        intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(TakePicUtils.getTepFile());
        intent.setData(uri);
        activity.sendBroadcast(intent);
    }


    //开启从本地获取图片的意图

    /**
     * @param activity
     * @param resultCode 结果码
     */
    public static void StartTakePicFromLocalIntent(Activity activity, int resultCode) {
        File file = getTepFile2();
        if (file.exists()) {
            file.delete();
        }
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        activity.startActivityForResult(intent, resultCode);
    }


    /**
     * @param activity
     * @param uri
     * @param resultCode
     * @param tag        是否是正方形
     */
    public static void startPhotoZoom(final Activity activity, final Uri uri, final int resultCode, final boolean tag) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例

        if (tag) {
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            // outputX outputY 是裁剪图片宽高
            intent.putExtra("outputX", SystemUtil.getScreenWind() * 2 / 3);
            intent.putExtra("outputY", SystemUtil.getScreenWind() * 2 / 3);
        } else {
            intent.putExtra("aspectX", 2);
            intent.putExtra("aspectY", 1);
            // outputX outputY 是裁剪图片宽高
            intent.putExtra("outputX", SystemUtil.getScreenWind());
            intent.putExtra("outputY", SystemUtil.getScreenWind() / 2);
        }

//        intent.putExtra("return-data", true);

        Uri tem = Uri.fromFile(getTepFile2());
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, tem);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);

        activity.startActivityForResult(intent, resultCode);


    }


    //将图片设置到控件上并返回图片的路径

    /**
     * @param picdata
     * @param iv
     * @return 图片保存的路径
     */
    public static String setPicToViewAndGetPicPath(Intent picdata, ImageView iv, Context context) {


        File file = getTepFile();
        if (file.exists()) {
            file.delete();
        }
        Uri uritempFile = Uri.fromFile(TakePicUtils.getTepFile2());
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uritempFile));

            if (iv != null && bitmap != null)
                iv.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return TakePicUtils.getTepFile2().getAbsolutePath();
    }


    public static File getTepFile() {
        File temp = new File(FileUtils.FILE_PATH
                + "/tmp.jpg");

        isFatherPathExist(temp);

        return temp;

    }

    public static File getTepFile2() {
        File temp = new File(FileUtils.FILE_PATH
                + "/tmp2.jpg");
        isFatherPathExist(temp);
        return temp;

    }

    private static void isFatherPathExist(File file) {

        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
    }

    public static void DeleteTempFile(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }

        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {

                Log.e("DeleteFilePath-->", file.getAbsolutePath());
                file.delete();
                return;
            }

            for (int i = 0; i < childFiles.length; i++) {
                DeleteTempFile(childFiles[i]);
            }
            Log.e("DeleteFilePath-->", file.getAbsolutePath());
            file.delete();
        }
    }
}
