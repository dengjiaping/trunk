package com.histudent.jwsoft.histudent.commen.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.account.login.model.CheckUpdataBean;
import com.histudent.jwsoft.histudent.commen.listener.DialogButtonListener;
import com.histudent.jwsoft.histudent.comment2.utils.UpDateDialogUtils;
import com.histudent.jwsoft.histudent.constant.Const;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 版本更新
 * Created by ZhangYT on 2016/9/8.
 */
public class MyUpdateManager {

    private Context mContext;
    //提示语
    private String updateMsg;

    //    //返回的安装包url
    private String apkUrl;

    private Dialog downloadDialog;
    /* 下载包安装路径 */
    private File ApkFile;

    /* 进度条与通知ui刷新的handler和msg常量 */
    private ProgressBar mProgress;

    private static final int DOWN_UPDATE = 1;

    private static final int DOWN_OVER = 2;

    private int progress;

    private Thread downLoadThread;
    private TextView tv_progress;

    private boolean interceptFlag = false;
    private getDataSetIsOk callBack;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:

                    mProgress.setProgress(progress);
                    mProgress.setSecondaryProgress(progress + 2);
                    tv_progress.setText("已下载：" + progress + "%");
                    break;
                case DOWN_OVER:


                    ((Activity) mContext).finish();

                    downloadDialog.dismiss();

                    installApk();
                    break;
                default:
                    break;
            }
        }

        ;
    };

    public MyUpdateManager(Context context) {
        this.mContext = context;
    }

    //外部接口让主Activity调用
    public void checkUpdateInfo(CheckUpdataBean checkUpdataBean, getDataSetIsOk callBack) {

        apkUrl = checkUpdataBean.getUpdateurl();
        updateMsg = checkUpdataBean.getUpdatedesc();
        this.callBack = callBack;
        switch (checkUpdataBean.getStatus()) {

            //0-不升级，1-提示升级，2-强制升级 ,
            case 0:
                Log.e("UpdataApK", "不升级");
                if (callBack != null) {
                    callBack.getData(true);
                }
                break;

            case 1:
                showNoticeDialog();
                Log.e("UpdataApK", "提示升级");
                break;

            case 2:
                showNoticeDialogForce();
                Log.e("UpdataApK", "强制升级");
                break;
        }
    }


    //显示下载界面
    public void LoadingApk(int status, String apkUrl) {

        //更新路径为空直接返回
        if (StringUtil.isEmpty(apkUrl)) {
            Toast.makeText(mContext, "更新路径错误！", Toast.LENGTH_LONG).show();
            return;
        }

        this.apkUrl = apkUrl;

        switch (status) {

            case 1:
                showDownloadDialog();
                Log.e("UpdataApK", "提示升级");
                break;

            case 2:
                showDownloadDialogForce();
                Log.e("UpdataApK", "强制升级");
                break;
        }

    }


    //强制更新的提示
    private void showNoticeDialogForce() {


        UpDateDialogUtils.showNoticeDialog("有新版本啦...", updateMsg, "确定", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {
                showDownloadDialogForce();
            }
        }, "", null, mContext, true);

    }


    //强制更新后的下载
    private void showDownloadDialogForce() {

        downloadDialog = UpDateDialogUtils.showLoadingDialog("版本更新", "", null, mContext, true);
        mProgress = ((ProgressBar) downloadDialog.findViewById(R.id.progress));
        tv_progress = ((TextView) downloadDialog.findViewById(R.id.dialog_content));
        downloadApk();
    }


    //提示更新
    private void showNoticeDialog() {

        UpDateDialogUtils.showNoticeDialog("有新版本啦...", updateMsg, "确定", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {
                showDownloadDialog();
            }
        }, "以后再说", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {
                if (callBack != null) {
                    callBack.getData(true);
                }
            }
        }, mContext, false);

    }


    //提示更新下载
    private void showDownloadDialog() {

        downloadDialog = UpDateDialogUtils.showLoadingDialog("版本更新", "取消", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {
                if (callBack != null) {
                    callBack.getData(true);
                }
                interceptFlag = true;
            }
        }, mContext, false);
        mProgress = ((ProgressBar) downloadDialog.findViewById(R.id.progress));
        tv_progress = ((TextView) downloadDialog.findViewById(R.id.dialog_content));


        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        int permission = ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions((Activity) mContext, PERMISSIONS_STORAGE, 1);
        } else {
            downloadApk();
        }
    }


    //apk下载
    private void downloadApk() {
        Runnable mdownApkRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(apkUrl);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    int length = conn.getContentLength();

                    InputStream is = conn.getInputStream();

                    Log.e("length", length + "");

                    File file = new File(FileUtil.getPathFactory(FileUtil.CacheType.APP, true));
                    if (!file.exists()) {
                        file.mkdirs();
                    }

                    ApkFile = new File(file, "UpdateDemoRelease.apk");

                    if (!ApkFile.exists()) {
                        ApkFile.createNewFile();
                    }

                    FileOutputStream fos = new FileOutputStream(ApkFile);

                    int count = 0;
                    byte buf[] = new byte[1024];

                    do {
                        int numread = is.read(buf);
                        count += numread;

                        Log.e("numread", numread + "");
                        Log.e("count", count + "");
                        progress = (int) (((float) count / length) * 100);

                        //更新进度

                        Log.e("progress", progress + "");
                        mHandler.sendEmptyMessage(DOWN_UPDATE);
                        if (numread <= 0) {
                            //下载完成通知安装
                            mHandler.sendEmptyMessage(DOWN_OVER);
                            break;
                        }
                        fos.write(buf, 0, numread);
                    } while (!interceptFlag);//点击取消就停止下载.

                    fos.close();
                    is.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
    }


    //apk安装
    private void installApk() {
        if (!ApkFile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            uri = Uri.parse("file://" + ApkFile.getAbsolutePath());
        } else {
            uri = FileProvider.getUriForFile(mContext, Const.AUTHORITIES_SIT,new File(ApkFile.getAbsolutePath()));
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        i.setDataAndType(uri, "application/vnd.android.package-archive");
        mContext.startActivity(i);
    }
}
