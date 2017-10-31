package com.histudent.jwsoft.histudent.comment2.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.utils.FileUtil;
import com.histudent.jwsoft.histudent.commen.utils.IntenetUtil;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by ZhangYT on 2016/8/8.
 */
public class FileUtils {

    /*
     * 文件保存的路径
     */
    public static final String FILE_PATH = FileUtil.getPathFactory(FileUtil.CacheType.PEG, true);

    /**
     * 从本地SD卡获取网络图片，key是url的MD5值
     *
     * @param url
     * @return
     */
    public static Bitmap getImageBitmapFromLocal(String url) {
        try {
            //  String fileName = MD5Encoder.encode(url);

//            String fileName = getImageName(url);
            File file = new File(url);
//
//            if (fileName.contains(FILE_PATH)) {
//                file = new File(fileName);
//            } else {
//                file = new File(FILE_PATH, fileName);
//            }
            if (file.exists()) {

                BitmapFactory.Options options = new BitmapFactory.Options();

                options.inJustDecodeBounds = false;

                options.inSampleSize = 5;   // width，hight设为原来的十分一

                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(
                        file));

                Log.e("getBitmapFromLocal", "获取本地图片数据成功" + file.getAbsoluteFile());
                return bitmap;

            } else {

                file = new File(url);

                if (file.exists()) {


                    BitmapFactory.Options options = new BitmapFactory.Options();

                    options.inJustDecodeBounds = false;

                    options.inSampleSize = 5;   // width，hight设为原来的十分一

                    Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(
                            file));
                    if (bitmap != null)
                        saveImageBitmapIntoLocal(url, bitmap, false);

                    Log.e("getBitmapFromLocal", "获取本地图片数据成功" + file.getAbsoluteFile());
                    return bitmap;

                } else {

                    Log.e("getBitmapFromLocal", "该图片名文件不存在！");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.e("getBitmapFromLocal", "获取本地图片失败，数据为空");
        return null;

    }

    /**
     * 向本地SD卡写网络图片
     *
     * @param url
     */
    public static String saveImageBitmapIntoLocal(String url, Bitmap bitmap, boolean tag) {
        try {
            // 文件的名字
            //     String fileName = MD5Encoder.encode(url);
            // 创建文件流，指向该路径，文件名叫做fileName
            String fileName = getImageName(url);
            File file = new File(FILE_PATH, fileName);
            // file其实是图片，它的父级File是文件夹，判断一下文件夹是否存在，如果不存在，创建文件夹
//            File fileParent = file.getParentFile();
//            if (!fileParent.exists()) {
//                // 文件夹不存在
//                fileParent.mkdirs();// 创建文件夹
//            }
            file.createNewFile();


            if (tag) {
                bitmap = addBorder(bitmap);
            }

            if (fileName.endsWith("jpg")) {

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                        new FileOutputStream(file));
            } else {

                bitmap.compress(Bitmap.CompressFormat.PNG, 100,
                        new FileOutputStream(file));
            }

            // 将图片保存到本地

            Log.e("saveBitmapIntoLocal", "保存图片到本地成功" + file.getName());

            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    //获取文件名，不包含路径
    public static String getImageName(String url) {

        if (url.contains("http://") || url.contains("/")) {

            String fileName = url.substring(url.lastIndexOf("/") + 1);

            fileName = fileName + ".jpg";

            Log.e("getImageName", fileName);

            if (!StringUtil.isEmpty(fileName)) {
                Log.e("getImageName", "图片名称获取成功！");
                return fileName;
            } else {
                Log.e("getImageName", "图片名称获取失败！");
            }
        }
        return url;
    }


    //给图片添加边框
    public static Bitmap addBorder(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth() + 20, bitmap.getHeight() + 20, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(10, 10, bitmap.getWidth() + 10, bitmap.getHeight() + 10);
        final Rect rect1 = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawColor(Color.WHITE);
        paint.setColor(color);
        paint.setStrokeWidth(10);
        canvas.drawRect(rect, paint);

        canvas.drawBitmap(bitmap, rect1, rect, paint);
        bitmap.recycle();
        return output;
    }

    public static String getFilePath(String url) {

        String fileName = getImageName(url);
        File file = new File(FILE_PATH, fileName);

        Log.e("length", file.length() + "" + file.getAbsoluteFile());

        return file.getAbsolutePath().toString();
    }

    public static void deleteFile(String filePath) {

        if (!StringUtil.isEmpty(filePath)) {
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public static void deleteFiles(List<String> filePaths) {

        if (filePaths != null && filePaths.size() > 0) {

            for (String path : filePaths) {
                deleteFile(path);
            }
        }

    }


    public static void DeleteTepFile() {

        File file = TakePicUtils.getTepFile();
        if (file.exists()) {
            file.delete();
        }
    }


    //将图片保存的相册
    public static void saveImageToAlbum(final Activity activity, final Handler handler, final int what, final String imageUrl) {

        ((BaseActivity) activity).showLoadingImage(activity, LoadingType.NONE);
        final Message message = handler.obtainMessage();
        message.obj = "failed";
        message.what = what;

        if (!StringUtil.isEmpty(imageUrl)) {
            if (IntenetUtil.getNetworkState() > 0) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap;
                        if (!imageUrl.startsWith("http")) {
                            bitmap = FileUtils.getImageBitmapFromLocal(imageUrl);

                            Log.e("=====", imageUrl);
                        } else {
                            bitmap = HiWorldCache.downLoadImage(imageUrl);
                        }

                        if (bitmap != null) {
                            String path = FileUtils.saveImageBitmapIntoLocal(imageUrl, bitmap, false);
//                            bitmap = FileUtils.getImageBitmapFromLocal(imageUrl);
//
//                            if (bitmap != null) {
                            message.obj = "success";
                            Bundle arg = new Bundle();
                            arg.putString("path", path);
                            Log.e("=====>>>", FileUtils.getFilePath(imageUrl));
                            message.setData(arg);
                            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            Uri uri = Uri.fromFile(new File(FileUtils.getFilePath(imageUrl)));
                            intent.setData(uri);
                            activity.sendBroadcast(intent);
//                            }
                        }
                        handler.sendMessage(message);
                    }

                }).start();

            } else {
                handler.sendMessage(message);
                Toast.makeText(activity, "当前网络不可用！", Toast.LENGTH_LONG).show();
            }
        } else {

            Bitmap bitmap = takeScreenShot(activity);
            if (bitmap != null) {
                String imageUrl1 = TimeUtils.getCurrentTime() + ".jpg";
                Log.d("imageUrl1", imageUrl1);
                String path = FileUtils.saveImageBitmapIntoLocal(imageUrl1, bitmap, false);
                Log.e("length", bitmap.getByteCount() + "");
                if (bitmap != null) {
                    message.obj = "success";
                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri uri = Uri.fromFile(new File(FileUtils.getFilePath(imageUrl1)));
                    intent.setData(uri);
                    activity.sendBroadcast(intent);
                }
            }
            handler.sendMessage(message);
        }


        ((BaseActivity) activity).closeLoadingImage();
    }

    //将图片保存的相册
    public static void saveImageToAlbum(final Activity activity, final Handler handler, final int what, final String imageUrl, final boolean tag) {

        ((BaseActivity) activity).showLoadingImage(activity, LoadingType.FLOWER);
        final Message message = handler.obtainMessage();
        message.obj = "failed";
        message.what = what;

        if (!StringUtil.isEmpty(imageUrl)) {
            if (IntenetUtil.getNetworkState() > 0) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = FileUtils.getImageBitmapFromLocal(imageUrl);
                        if (bitmap == null) {
                            if (imageUrl.startsWith("http")) {
                                bitmap = HiWorldCache.downLoadImage(imageUrl);
                                Log.e("=====", imageUrl);

                                if (bitmap != null) {
                                    FileUtils.saveImageBitmapIntoLocal(imageUrl, bitmap, tag);
                                }
                            }
                        }

                        if (bitmap != null) {
                            message.obj = "success";
                            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            Uri uri = Uri.fromFile(new File(FileUtils.getFilePath(imageUrl)));
                            intent.setData(uri);
                            activity.sendBroadcast(intent);
                        }
                        handler.sendMessage(message);
                    }

                }).start();

            } else {
                handler.sendMessage(message);
                Toast.makeText(activity, "当前网络不可用！", Toast.LENGTH_LONG).show();
            }
        } else {

            Bitmap bitmap = takeScreenShot(activity);
            if (bitmap != null) {
                String imageUrl1 = TimeUtils.getCurrentTime() + ".jpg";
                Log.d("imageUrl1", imageUrl1);
                FileUtils.saveImageBitmapIntoLocal(imageUrl1, bitmap, tag);
                Log.e("length", bitmap.getByteCount() + "");
                if (bitmap != null) {
                    message.obj = "success";
                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri uri = Uri.fromFile(new File(FileUtils.getFilePath(imageUrl1)));
                    intent.setData(uri);
                    activity.sendBroadcast(intent);
                }
            }
            handler.sendMessage(message);
        }


        ((BaseActivity) activity).closeLoadingImage();
    }

    private static Bitmap takeScreenShot(Activity activity) {
        // View是你需要截图的View
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();

        // 获取状态栏高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);


        // 获取屏幕长和高

        int width = activity.getWindowManager().getDefaultDisplay().getWidth() - SystemUtil.dp2px(80);
        int height = activity.getWindowManager().getDefaultDisplay()
                .getHeight();
        int statusBarHeight = frame.top + SystemUtil.dp2px(70) + height / 7;
        // 去掉标题栏
        Bitmap b = Bitmap.createBitmap(b1, SystemUtil.dp2px(40), statusBarHeight, width, height - statusBarHeight - height / 7);
        view.destroyDrawingCache();
        return b;

    }


    //判断图片文件是否已经在本地存在
    public static boolean imageFileIsExist(String fileUrl) {

        String fileName = getImageName(fileUrl);
        File file = new File(FILE_PATH, fileName);
        if (file.exists()) {
            return true;
        } else {
            if (!(fileUrl.endsWith(".jpg") || fileUrl.endsWith(".png") || fileUrl.endsWith(".jpeg"))) {
                return false;
            } else {
                File file_ = new File(fileUrl);
                return file_.exists();
            }
        }
    }

    /**
     * 保存图片到本地，返回图片的全路径
     *
     * @param activity
     * @param imageUrl
     * @param callBack
     */
    public static void saveImage2Album(final Activity activity, final List<String> imageUrl, final getSaveImagePath callBack) {

        final Map<String, String> map = new HashMap<>();

        if (imageUrl != null && imageUrl.size() > 0) {
            if (IntenetUtil.getNetworkState() > 0) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {


                        Bitmap bitmap;
                        for (String url : imageUrl) {

                            if (!StringUtil.isEmpty(url)) {
                                if (!url.startsWith("http")) {
                                    bitmap = FileUtils.getImageBitmapFromLocal(url);
                                } else {
                                    bitmap = HiWorldCache.downLoadImage(url);
                                }

                                if (bitmap != null) {
                                    String path = FileUtils.saveImageBitmapIntoLocal(url, bitmap, false);
                                    map.put(url, new File(path).getAbsolutePath());
                                    Log.e("=====", new File(path).getAbsolutePath() + new File(path).length());
                                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                                    Uri uri = Uri.fromFile(new File(path));
                                    intent.setData(uri);
                                    activity.sendBroadcast(intent);
                                }
                            }

                        }
                        callBack.getImagePath(map);

                    }

                }).start();
            }
        }
    }

    public interface getSaveImagePath {
        public void getImagePath(Map<String, String> map);
    }
}
