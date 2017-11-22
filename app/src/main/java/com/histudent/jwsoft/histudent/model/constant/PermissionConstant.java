package com.histudent.jwsoft.histudent.model.constant;

import android.Manifest;

/**
 * Created by lichaojie on 2017/9/13.
 * desc:
 */

public class PermissionConstant {

    //应用需要获取的基本权限
    public static final int PERMISSION_REQUEST_CODE = 100;
    public static final String[] PERMISSION = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };

    //获取照片需要的权限
    public static final int PERMISSION_TAKE_PHOTO_CODE = 101;
    public static final String[] PERMISSION_TAKE_PHOTO = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };


    //录制音频需要的权限
    public static final int PERMISSION_RECORD_CODE = 102;
    public static final String[] PERMISSION_RECORD = new String[]{
            Manifest.permission.RECORD_AUDIO,
    };

    //播放音频需要的权限
    public static final int PERMISSION_PLAY_CODE = 103;
    public static final String[] PERMISSION_PLAY = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    //拨打电话需要的权限
    public static final int PERMISSION_CALL_CODE = 104;
    public static final String[] PERMISSION_CALL = new String[]{
            Manifest.permission.CALL_PHONE,
    };

    //获取录像需要的权限
    public static final int PERMISSION_TAKE_PHOTOS_CODE = 105;
    public static final String[] PERMISSION_TAKE_PHOTOS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };


    //位置所需要的权限
    public static final int PERMISSION_LOCATION_CODE = 106;
    public static final String[] PERMISSION_LOCATION = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };
}
