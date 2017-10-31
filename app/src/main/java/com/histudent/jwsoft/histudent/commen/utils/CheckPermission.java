package com.histudent.jwsoft.histudent.commen.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.histudent.jwsoft.histudent.HiStudentApplication;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LaiYingtang on 2016/5/18.
 * <p/>
 * 检查权限的工具类
 */
public class CheckPermission {

    private final Activity activity;

    //构造器
    public CheckPermission(Activity activity) {
        this.activity = activity;
    }

    //检查权限时，判断系统的权限集合
    public boolean permissionSet(String[] permissions) {
        for (String permission : permissions) {
            if (isLackPermission(permission)) {//是否添加完全部权限集合
                return true;
            }
        }
        return false;

    }

    //检查系统权限是，判断当前是否缺少权限(PERMISSION_DENIED:权限是否足够)
    private boolean isLackPermission(String permission) {
        return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_DENIED;
    }

    public boolean isHaveAllPermission(int size, int[] grantResults) {

        for (int i = 0; i < size; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;

    }

    /**
     * 权限申请
     */
    public void requestBasicPermission(String[] permissions, int PERMISSION_REQUEST_CODE) {

        List<String> permissions_r = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (isLackPermission(permissions[i])) {
                permissions_r.add(permissions[i]);
            }
        }
        String[] strings = new String[permissions_r.size()];
        ActivityCompat.requestPermissions(activity, permissions_r.toArray(strings), PERMISSION_REQUEST_CODE);
//        ActivityCompat.requestPermissions(activity, permissions, PERMISSION_REQUEST_CODE);
    }

    /**
     * 显示对话框提示用户缺少权限
     */
    public void showMissingPermissionDialog() {
        showMissingPermissionDialog(false);
    }

    public void showMissingPermissionDialog(final boolean flag) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.help);//提示帮助
        builder.setMessage(R.string.string_help_text);

        //如果是拒绝授权，则退出应用
        //退出
        builder.setNegativeButton(R.string.quit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (flag)
                    activity.finish();
            }
        });
        //打开设置，让用户选择打开权限
        builder.setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + HiStudentApplication.getInstance().getPackageName()));
                activity.startActivity(intent);
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

}