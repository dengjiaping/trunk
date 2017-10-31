package com.histudent.jwsoft.histudent.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.histudent.jwsoft.histudent.call.IPermissionCallBackListener;
import com.histudent.jwsoft.histudent.commen.utils.CheckPermission;
import com.histudent.jwsoft.histudent.constant.PermissionConstant;

/**
 * Created by lichaojie on 2017/9/14.
 * desc:
 * 权限统一处理page
 */

public class BasePermissionActivity extends AppCompatActivity {

    /**
     * 应用需要获取的基本权限
     * 获取照片需要的权限
     * 获取录像需要的权限
     * 录制音频需要的权限
     * 播放音频需要的权限
     * 拨打电话需要的权限
     */
    private IPermissionCallBackListener mLocationCallBack,
            mPhotoCallBack, mRecordVideoCallBack, mRecordSoundCallBack,
            mPlaySoundCallBack, mRingUpCallBack, mAllPermissionCallBack;
    private CheckPermission mCheckPermission;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCheckPermission = new CheckPermission(this);
    }

    /**
     * 检查所有权限
     */
    public void checkAllPermission(IPermissionCallBackListener callBack) {

        mAllPermissionCallBack = callBack;
        //缺少权限时，进入权限设置页面
        if (mCheckPermission.permissionSet(PermissionConstant.PERMISSION)) {
            mCheckPermission.requestBasicPermission(PermissionConstant.PERMISSION, PermissionConstant.PERMISSION_REQUEST_CODE);
        } else {
            mAllPermissionCallBack.doAction();
        }
    }


    /**
     * 检测位置权限
     */
    public void checkGetLocationPermission(IPermissionCallBackListener callBack) {

        mLocationCallBack = callBack;
        //缺少权限时，进入权限设置页面
        if (mCheckPermission.permissionSet(PermissionConstant.PERMISSION)) {
            mCheckPermission.requestBasicPermission(PermissionConstant.PERMISSION_LOCATION, PermissionConstant.PERMISSION_LOCATION_CODE);
        } else {
            mLocationCallBack.doAction();
        }
    }

    /**
     * 检测拍照权限
     */
    public void checkTakePhotoPermission(IPermissionCallBackListener callBack) {

        mPhotoCallBack = callBack;
        //缺少权限时，进入权限设置页面
        if (mCheckPermission.permissionSet(PermissionConstant.PERMISSION_TAKE_PHOTO)) {
            mCheckPermission.requestBasicPermission(PermissionConstant.PERMISSION_TAKE_PHOTO, PermissionConstant.PERMISSION_TAKE_PHOTO_CODE);
        } else {
            mPhotoCallBack.doAction();
        }
    }

    /**
     * 检测摄像权限
     */
    public void checkTakePhotosPermission(IPermissionCallBackListener callBack) {

        mRecordVideoCallBack = callBack;
        //缺少权限时，进入权限设置页面
        if (mCheckPermission.permissionSet(PermissionConstant.PERMISSION_TAKE_PHOTOS)) {
            mCheckPermission.requestBasicPermission(PermissionConstant.PERMISSION_TAKE_PHOTOS, PermissionConstant.PERMISSION_TAKE_PHOTOS_CODE);
        } else {
            mRecordVideoCallBack.doAction();
        }
    }

    /**
     * 检测录音权限
     */
    public void checkRecordPermission(IPermissionCallBackListener callBack) {

        mRecordSoundCallBack = callBack;
        //缺少权限时，进入权限设置页面
        if (mCheckPermission.permissionSet(PermissionConstant.PERMISSION_RECORD)) {
            mCheckPermission.requestBasicPermission(PermissionConstant.PERMISSION_RECORD, PermissionConstant.PERMISSION_RECORD_CODE);
        } else {
            mRecordSoundCallBack.doAction();
        }
    }

    /**
     * 检测播放音频权限
     */
    public void checkPlayPermission(IPermissionCallBackListener callBack) {

        mPlaySoundCallBack = callBack;
        //缺少权限时，进入权限设置页面
        if (mCheckPermission.permissionSet(PermissionConstant.PERMISSION_PLAY)) {
            mCheckPermission.requestBasicPermission(PermissionConstant.PERMISSION_PLAY, PermissionConstant.PERMISSION_PLAY_CODE);
        } else {
            mPlaySoundCallBack.doAction();
        }
    }

    /**
     * 检测拨号权限
     */
    public void checkCallPermission(IPermissionCallBackListener callBack) {

        mRingUpCallBack = callBack;
        //缺少权限时，进入权限设置页面
        if (mCheckPermission.permissionSet(PermissionConstant.PERMISSION_CALL)) {
            mCheckPermission.requestBasicPermission(PermissionConstant.PERMISSION_CALL, PermissionConstant.PERMISSION_CALL_CODE);
        } else {
            mRingUpCallBack.doAction();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionConstant.PERMISSION_REQUEST_CODE:
                if (mCheckPermission.isHaveAllPermission(permissions.length, grantResults)) {
                    mAllPermissionCallBack.doAction();
                } else {
                    mCheckPermission.showMissingPermissionDialog(true);
                }
                break;

            case PermissionConstant.PERMISSION_TAKE_PHOTO_CODE:
                if (mCheckPermission.isHaveAllPermission(permissions.length, grantResults)) {
                    mPhotoCallBack.doAction();
                } else {
                    mCheckPermission.showMissingPermissionDialog();
                }
                break;

            case PermissionConstant.PERMISSION_TAKE_PHOTOS_CODE:
                if (mCheckPermission.isHaveAllPermission(permissions.length, grantResults)) {
                    mRecordVideoCallBack.doAction();
                } else {
                    mCheckPermission.showMissingPermissionDialog();
                }
                break;

            case PermissionConstant.PERMISSION_RECORD_CODE:
                if (mCheckPermission.isHaveAllPermission(permissions.length, grantResults)) {
                    mRecordSoundCallBack.doAction();
                } else {
                    mCheckPermission.showMissingPermissionDialog();
                }
                break;

            case PermissionConstant.PERMISSION_PLAY_CODE:
                if (mCheckPermission.isHaveAllPermission(permissions.length, grantResults)) {
                    mPlaySoundCallBack.doAction();
                } else {
                    mCheckPermission.showMissingPermissionDialog();
                }
                break;
            case PermissionConstant.PERMISSION_CALL_CODE:
                if (mCheckPermission.isHaveAllPermission(permissions.length, grantResults)) {
                    mRingUpCallBack.doAction();
                } else {
                    mCheckPermission.showMissingPermissionDialog();
                }
                break;
            case PermissionConstant.PERMISSION_LOCATION_CODE:
                if (mCheckPermission.isHaveAllPermission(permissions.length, grantResults)) {
                    mLocationCallBack.doAction();
                } else {
                    mCheckPermission.showMissingPermissionDialog();
                }
                break;
            default:
                break;
        }
    }
}
