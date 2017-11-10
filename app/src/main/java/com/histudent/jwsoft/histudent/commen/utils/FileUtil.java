package com.histudent.jwsoft.histudent.commen.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.text.TextUtils;

import com.histudent.jwsoft.histudent.HTApplication;
import com.histudent.jwsoft.histudent.HiStudentLog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 文件夹相关工具类
 */
public class FileUtil {

    private static DateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");

    public enum CacheType {
        ALL,//所有缓存
        IMAGE,//图片
        VOICE,//音频
        LOG,//日志
        WEBVIEW,//网页缓存
        BATEBASE,//网页缓存
        XUTILS_IMG,//xutils图片缓存
        APP,//更新时app临时存放点
        PEG//活动网络获取临时保存的本地的图片
    }

    /**
     * 将Bitmap 图片保存到本地路径，并返回路径
     *
     * @param c
     * @param fileName 文件名称
     * @param bitmap   图片
     * @return
     */

    public static String saveFile(Context c, String fileName, Bitmap bitmap) {
        return saveFile(c, "", fileName, bitmap);
    }

    public static String saveFile(Context c, String filePath, String fileName, Bitmap bitmap) {
        byte[] bytes = bitmapToBytes(bitmap);
        return saveFile(c, filePath, fileName, bytes);
    }

    public static byte[] bitmapToBytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    public static String saveFile(Context c, String filePath, String fileName, byte[] bytes) {
        String fileFullName = "";
        FileOutputStream fos = null;
        String dateFolder = new SimpleDateFormat("yyyyMMdd", Locale.CHINA)
                .format(new Date());
        try {
            String suffix = "";
            if (filePath == null || filePath.trim().length() == 0) {
                filePath = Environment.getExternalStorageDirectory() + "/JiaXT/" + dateFolder + "/";
            }
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            File fullFile = new File(filePath, fileName + suffix);
            fileFullName = fullFile.getPath();
            fos = new FileOutputStream(new File(filePath, fileName + suffix));
            fos.write(bytes);
        } catch (Exception e) {
            fileFullName = "";
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    fileFullName = "";
                }
            }
        }
        return fileFullName;
    }

    public static String getBasePath() {
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/data/" + HTApplication.getInstance().getPackageName() + "/HiStudentCahe";
        }
        return null;
    }

    /**
     * 获取文件(文件夹)缓存地址的工厂
     *
     * @param type     类型
     * @param isFolder 是否是文件夹
     * @return
     */

    public static String getPathFactory(CacheType type, boolean isFolder) {

        String basePath = getBasePath();

        deleteEmptyFiles(new File(basePath).getAbsolutePath());

        if (basePath == null) return null;

        File file = null;
        String fileName = null;
        String time = formatter.format(new Date());

        switch (type) {
            case ALL:
                file = new File(basePath);
                break;
            case IMAGE:
                file = new File(basePath, "image");
                fileName = time + ".jpg";
                break;
            case VOICE:
                file = new File(basePath, "voice");
                fileName = time + ".acc";
                break;
            case LOG:
                file = new File(basePath, "log");
                fileName = time + ".log";
                break;
            case WEBVIEW:
                file = new File(basePath, "webview");
                break;
            case BATEBASE:
                file = new File(basePath, "database");
                break;
            case XUTILS_IMG:
                file = new File(basePath, "xutils_image");
                break;
            case APP:
                file = new File(basePath, "app");
                break;
            case PEG:
                file = new File(basePath, "images");//存放临时保存的网络图片，需要手动删除
                fileName = time + ".jpg";
                break;
        }

        if (!file.exists() && !file.mkdirs())
            return null;

        if (isFolder)
            return file.toString();

        File dir = new File(file, fileName);
            if (!dir.exists()) {
                try {
                    dir.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        return dir.toString();
    }




    /**
     * 获取文件(文件夹)缓存地址的工厂
     *
     * @param type     类型
     * @param isFolder 是否是文件夹
     * @return
     */

    public static String getPathFactory(boolean isFolder,CacheType type) {

        String basePath = getBasePath();


        if (basePath == null) return null;

        File file = null;
        String fileName = null;
        String time = formatter.format(new Date());

        switch (type) {
            case ALL:
                file = new File(basePath);
                break;
            case IMAGE:
                file = new File(basePath, "image");
                fileName = time + ".jpg";
                break;
            case VOICE:
                file = new File(basePath, "voice");
                fileName = time + ".acc";
                break;
            case LOG:
                file = new File(basePath, "log");
                fileName = time + ".log";
                break;
            case WEBVIEW:
                file = new File(basePath, "webview");
                break;
            case BATEBASE:
                file = new File(basePath, "database");
                break;
            case XUTILS_IMG:
                file = new File(basePath, "xutils_image");
                break;
            case APP:
                file = new File(basePath, "app");
                break;
            case PEG:
                file = new File(basePath, "images");//存放临时保存的网络图片，需要手动删除
                fileName = time + ".jpg";
                break;
        }

        if (!file.exists() && !file.mkdirs())
            return null;

        if (isFolder)
            return file.toString();

        File dir = new File(file, fileName);
        if (!dir.exists()) {
            try {
                dir.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dir.toString();
    }

    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     *
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    public static String getAutoFileOrFilesSize() {
        String path = FileUtil.getPathFactory(FileUtil.CacheType.XUTILS_IMG, true);
        if (TextUtils.isEmpty(path)) return "";
        File file = new File(path);
        if (!file.exists()) return "";
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            HiStudentLog.e("获取文件大小失败!" + e.toString());
        }
        return FormetFileSize(blockSize);
    }

    /**
     * 获取指定文件大小
     *
     * @return
     * @throws Exception
     */
    public static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
            fis.close();
        } else {
            file.createNewFile();
            HiStudentLog.e("文件不存在!");
        }
        return size;
    }

    /**
     * 获取指定文件夹
     *
     * @param f
     * @return
     * @throws Exception
     */
    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        if (f.exists()) {
            File flist[] = f.listFiles();
            for (int i = 0; i < flist.length; i++) {
                if (flist[i].isDirectory()) {
                    size = size + getFileSizes(flist[i]);
                } else {
                    size = size + getFileSize(flist[i]);
                }
            }
        } else {
            HiStudentLog.e("文件不存在!");
        }

        return size;
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    private static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 2      * 删除指定目录下文件及目录
     * 3      * @param deleteThisPath
     * 4      * @param filepath
     * 5      * @return
     * 6
     */
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 处理目录
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        file.delete();
                    } else {// 目录
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    //删除空白文件
    public static void deleteEmptyFiles(String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 处理目录
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteEmptyFiles(files[i].getAbsolutePath());
                    }
                }
                if (!file.isDirectory() && file.length() == 0) {// 如果是文件，删除

                    file.delete();

                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
