package com.histudent.jwsoft.histudent.commen.photo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.histudent.jwsoft.histudent.HiStudentLog;
import com.histudent.jwsoft.histudent.commen.photo.utils.ImageUtils;
import com.histudent.jwsoft.histudent.commen.utils.FileUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.File;
import java.util.List;


/**
 * Created by liuguiyu-pc on 2016/7/18.
 */
public class PhotoHelper {

    public static String saveDir = FileUtil.getPathFactory(FileUtil.CacheType.IMAGE, true);

    public static Bitmap decodeBitmap(String path, int num) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        if (bitmap == null) {
            System.out.println("bitmap为空");
        }
        float realWidth = options.outWidth;
        float realHeight = options.outHeight;
        // 计算缩放比&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
        int scale = (int) ((realHeight > realWidth ? realHeight : realWidth) / num);
        if (scale <= 0) {
            scale = 1;
        }
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        // 注意这次要把options.inJustDecodeBounds 设为 false,这次图片是要读取出来的。&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
        bitmap = BitmapFactory.decodeFile(path, options);


//        int w = bitmap.getWidth();
//        int h = bitmap.getHeight();
        return bitmap;
    }

    public static void showDeletImage(boolean flag, List<ImageView> list) {

        for (ImageView imageView : list) {

            if (flag) {
                imageView.setVisibility(View.VISIBLE);
            } else {
                imageView.setVisibility(View.GONE);
            }

        }

    }

    public static void saveImageInFile(Context context, String info, List<String> list, Handler handler) {
        int level = 0;
        if (info != null) {
            switch (info) {
                case "原图":
                    level = 80;
                    break;
                case "正常":
                    level = 60;
                    break;
                default:
                    break;
            }
        }
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {

                if (!TextUtils.isEmpty(list.get(i)) && list.get(i).contains(".")) {
                    ImageUtils.saveBitmap(context, list.get(i), level, handler, list.size());
                }
            }
        }
    }

    public static void delete(File file) {
        if (file != null) {
            if (file.isFile()) {
                file.delete();
                return;
            }
            if (file.isDirectory()) {
                File[] childFiles = file.listFiles();
                if (childFiles == null || childFiles.length == 0) {
                    file.delete();
                    return;
                }

                for (int i = 0; i < childFiles.length; i++) {
                    delete(childFiles[i]);
                }
                file.delete();
            }
        }
    }


    public static boolean showTheSdcard() {

        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }

    }

    public static void upLoadFile() {
        RequestParams params = new RequestParams();
        params.addHeader("name", "value");
        params.addQueryStringParameter("name", "value");

        // 只包含字符串参数时默认使用BodyParamsEntity，
        // 类似于UrlEncodedFormEntity（"application/x-www-form-urlencoded"）。
        params.addBodyParameter("name", "value");

        // 加入文件参数后默认使用MultipartEntity（"multipart/form-data"），
        // 如需"multipart/related"，xUtils中提供的MultipartEntity支持设置subType为"related"。
        // 使用params.setBodyEntity(httpEntity)可设置更多类型的HttpEntity（如：
        // MultipartEntity,BodyParamsEntity,FileUploadEntity,InputStreamUploadEntity,StringEntity）。
        // 例如发送json参数：params.setBodyEntity(new StringEntity(jsonStr,charset));
        params.addBodyParameter("file", new File(saveDir));

        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST,
                "uploadUrl....",
                params,
                new RequestCallBack<String>() {

                    @Override
                    public void onStart() {

                        HiStudentLog.e("---开始上传--->");

                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        if (isUploading) {
                            HiStudentLog.e("---上传中--->" + "upload: " + current + "/" + total);
                        } else {
                            HiStudentLog.e("---上传中--->" + "reply: " + current + "/" + total);
                        }
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        HiStudentLog.e("---上传结果--->" + "reply: " + responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        HiStudentLog.e("---上传结果--->" + error.getExceptionCode() + ":" + msg);
                    }
                });

    }

}
