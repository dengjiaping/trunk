package com.histudent.jwsoft.histudent.commen.utils;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.histudent.jwsoft.histudent.HTApplication;
import com.histudent.jwsoft.histudent.HiStudentLog;
import com.histudent.jwsoft.histudent.call.IPermissionCallBackListener;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.comment2.bean.LocationDB;
import com.histudent.jwsoft.histudent.comment2.utils.BackGroundUtils;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;


/**
 * 定位工具类
 * Created by ZhangYT on 2016/7/27.
 */
public class LocationUtils {

    private double myLatitude = -1, myLongitude = -1;
    private String poiName, city, cityCode, dis;
    public static boolean isSucess;
    private AMapLocationClient aMapLocationClient;
    private LocationListener locationListener;
    AMapLocationClientOption option;
    public static boolean isAllowed = false;
    public static String USER_LOCATION = "com.histudent.jwsoft.histudent.location";

    private static LocationUtils locationUtils;
    private LocationCallBack callBack;

    private LocationUtils() {
    }

    public static LocationUtils getLocationUtils() {
        if (locationUtils == null) {
            locationUtils = new LocationUtils();
        }
        return locationUtils;
    }


    public void getLocationInfor(Context context, LocationCallBack callBack) {

        this.callBack = callBack;
        isSucess = false;
        ((BaseActivity) context).checkGetLocationPermission(new IPermissionCallBackListener() {
            @Override
            public void doAction() {
                requestAmapLocation(context);
            }
        });

    }

    private void requestAmapLocation(Context context) {
        Log.e("LocationUtils===>", "准备定位中。。。。");


        if (BackGroundUtils.isBackground(context.getApplicationContext())) {
            HiStudentLog.e("LocationUtils===>" + "应用处于后台，位置不更新！");
        } else {

            HiStudentLog.e("LocationUtils===>" + "应用处于前台，位置更新！");
            aMapLocationClient = new AMapLocationClient(context.getApplicationContext());
            option = new AMapLocationClientOption();
            option.setInterval(2000);
            option.setOnceLocation(true);
            option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

            locationListener = new LocationListener();
            aMapLocationClient.setLocationListener(locationListener);
            aMapLocationClient.setLocationOption(option);
            aMapLocationClient.startLocation();
        }
    }


    private void setLocationInfor() {

        final DbUtils dbUtils = DbUtils.create(HTApplication.getInstance());
        final LocationDB locationDB[] = new LocationDB[1];
        try {
            locationDB[0] = dbUtils.findById(LocationDB.class, USER_LOCATION);
        } catch (DbException e) {
            e.printStackTrace();
        }

        if (locationDB[0] == null) {
            locationDB[0] = new LocationDB();
        }
        locationDB[0].setId(USER_LOCATION);
        locationDB[0].setLat(myLatitude);
        locationDB[0].setLog(myLongitude);
        locationDB[0].setLocation(poiName);
        locationDB[0].setCity(city);
        locationDB[0].setAreaStr(dis);

        try {
            dbUtils.saveOrUpdate(locationDB[0]);

            Log.e("LocationUtils===>", "保存用户地理信息成功！");
            Log.e("LocationUtils===>", locationDB[0].getLog() + "");
            Log.e("LocationUtils===>", locationDB[0].getLat() + "");
            Log.e("LocationUtils===>", locationDB[0].getLocation());
            Log.e("LocationUtils===>", locationDB[0].getCity());
            Log.e("LocationUtils===>", locationDB[0].getAreaStr());

            if (callBack != null) {
                callBack.getLocationInfor(myLatitude, myLongitude, poiName, city, dis, true);
            }
            isSucess = true;

        } catch (DbException e) {
            e.printStackTrace();
        } finally {

            if (aMapLocationClient != null) {
                aMapLocationClient.stopLocation();
                aMapLocationClient.onDestroy();

            }

            aMapLocationClient = null;
            locationListener = null;
            option = null;
            dbUtils.close();
            Log.e("地图", "结束定位");
        }

    }


    class LocationListener implements AMapLocationListener {

        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {

            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    myLatitude = aMapLocation.getLatitude();
                    myLongitude = aMapLocation.getLongitude();
                    Log.e("Address", aMapLocation.getAddress());
                    Log.e("City", aMapLocation.getCity());
                    Log.e("District", aMapLocation.getDistrict());
                    Log.e("LocationDetail", aMapLocation.getLocationDetail());
                    Log.e("CityCode", aMapLocation.getCityCode());
                    Log.e("AdCode", aMapLocation.getAdCode());
                    Log.e("Latitude", aMapLocation.getLatitude() + "");
                    Log.e("Longitude", aMapLocation.getLongitude() + "");
                    Log.e("getPoiName", aMapLocation.getPoiName() + "");


                    poiName = aMapLocation.getPoiName();
                    city = aMapLocation.getCity();
                    cityCode = aMapLocation.getCityCode();
                    dis = aMapLocation.getDistrict();
                    Log.e("地图", "定位成功，准备处理结果！");

                    //定位成功后结束定位
                    if (!StringUtil.isEmpty(city) && !StringUtil.isEmpty(dis)) {
                        setLocationInfor();

                    } else {
                        if (callBack != null) {
                            callBack.getLocationInfor(myLatitude, myLongitude, poiName, city, dis, false);
                        }
                    }

                } else {
                    Log.e("地图", "定位失败，" + aMapLocation.getErrorCode());
                }
            }
        }
    }

    public String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
