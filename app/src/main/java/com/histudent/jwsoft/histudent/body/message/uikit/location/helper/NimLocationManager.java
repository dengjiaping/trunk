package com.histudent.jwsoft.histudent.body.message.uikit.location.helper;


import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.LocationSource;
import com.histudent.jwsoft.histudent.HiStudentLog;
import com.histudent.jwsoft.histudent.body.message.uikit.infra.TaskExecutor;
import com.histudent.jwsoft.histudent.body.message.uikit.location.model.NimLocation;
import com.netease.nim.uikit.common.util.log.LogUtil;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class NimLocationManager {
    private static final String TAG = "NimLocationManager";
    private Context mContext;

    /**
     * msg handler
     */
    private static final int MSG_LOCATION_WITH_ADDRESS_OK = 1;
    private static final int MSG_LOCATION_POINT_OK = 2;
    private static final int MSG_LOCATION_ERROR = 3;

    private NimLocationListener mListener;

    Criteria criteria; // onResume 重新激活 if mProvider == null

    /**
     * AMap location
     */

    private AMapLocationClient aMapLocationClient;
    private LocationSource.OnLocationChangedListener locationChangedListener;
    private String mProvider;
    private Geocoder mGeocoder;
    private AMap aMap;
    private boolean isNeedMyLocation;

    private MsgHandler mMsgHandler = new MsgHandler();
    private TaskExecutor executor = new TaskExecutor(TAG, TaskExecutor.defaultConfig, true);

    public NimLocationManager(Context context, NimLocationListener oneShotListener, AMap aMap, boolean isNeedMyLocation) {
        mContext = context;
        mGeocoder = new Geocoder(mContext, Locale.getDefault());
        mListener = oneShotListener;
        this.aMap = aMap;
        this.isNeedMyLocation = isNeedMyLocation;

        requestAmapLocation();


    }

    public static boolean isLocationEnable(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Criteria cri = new Criteria();
        cri.setAccuracy(Criteria.ACCURACY_COARSE);
        cri.setAltitudeRequired(false);
        cri.setBearingRequired(false);
        cri.setCostAllowed(false);
        String bestProvider = locationManager.getBestProvider(cri, true);
        return !TextUtils.isEmpty(bestProvider);

    }


    public interface NimLocationListener {
        public void onLocationChanged(NimLocation location);
    }

    public Location getLastKnownLocation() {
        try {
            if (criteria == null) {
                criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                criteria.setAltitudeRequired(false);
                criteria.setBearingRequired(false);
                criteria.setCostAllowed(false);
            }
            if (mProvider == null) {
                mProvider = aMapLocationClient.getLastKnownLocation().getProvider();
            }
            return aMapLocationClient.getLastKnownLocation();
        } catch (Exception e) {
            LogUtil.i(TAG, "get lastknown location failed: " + e.toString());
        }
        return null;
    }

    private void onLocation(NimLocation location, int what) {
        Message msg = mMsgHandler.obtainMessage();
        msg.what = what;
        msg.obj = location;
        mMsgHandler.sendMessage(msg);
    }

    private class MsgHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOCATION_WITH_ADDRESS_OK:
                    if (mListener != null && msg.obj != null) {
                        if (msg.obj != null) {
                            NimLocation loc = (NimLocation) msg.obj;
                            loc.setStatus(NimLocation.Status.HAS_LOCATION_ADDRESS);

                            // 记录地址信息
                            loc.setFromLocation(true);

                            mListener.onLocationChanged(loc);
                        } else {
                            NimLocation loc = new NimLocation();
                            mListener.onLocationChanged(loc);
                        }
                    }
                    break;
                case MSG_LOCATION_POINT_OK:
                    if (mListener != null) {
                        if (msg.obj != null) {
                            NimLocation loc = (NimLocation) msg.obj;
                            loc.setStatus(NimLocation.Status.HAS_LOCATION);
                            mListener.onLocationChanged(loc);
                        } else {
                            NimLocation loc = new NimLocation();
                            mListener.onLocationChanged(loc);
                        }
                    }
                    break;
                case MSG_LOCATION_ERROR:
                    if (mListener != null) {
                        NimLocation loc = new NimLocation();
                        mListener.onLocationChanged(loc);
                    }
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }

    }

    private void getAMapLocationAddress(final AMapLocation loc) {

        if (TextUtils.isEmpty(loc.getAddress())) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    getLocationAddress(new NimLocation(loc, NimLocation.AMap_Location));
                }
            });
        } else {
            NimLocation location = new NimLocation(loc, NimLocation.AMap_Location);
            location.setAddrStr(loc.getAddress());
            location.setProvinceName(loc.getProvince());
            location.setCityName(loc.getCity());
            location.setCityCode(loc.getCityCode());
            location.setDistrictName(loc.getDistrict());
            location.setStreetName(loc.getStreet());
            location.setStreetCode(loc.getAdCode());

            onLocation(location, MSG_LOCATION_WITH_ADDRESS_OK);
        }
    }

    private boolean getLocationAddress(NimLocation location) {
        List<Address> list;
        boolean ret = false;
        try {
            list = mGeocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 2);
            if (list != null && list.size() > 0) {
                Address address = list.get(0);
                if (address != null) {
                    location.setCountryName(address.getCountryName());
                    location.setCountryCode(address.getCountryCode());
                    location.setProvinceName(address.getAdminArea());
                    location.setCityName(address.getLocality());
                    location.setDistrictName(address.getSubLocality());
                    location.setStreetName(address.getThoroughfare());
                    location.setFeatureName(address.getFeatureName());
                }
                ret = true;
            }
        } catch (IOException e) {
            LogUtil.e(TAG, e + "");
        }

        int what = ret ? MSG_LOCATION_WITH_ADDRESS_OK : MSG_LOCATION_POINT_OK;
        onLocation(location, what);

        return ret;
    }


    private void stopAMapLocation() {
        if (aMapLocationClient != null) {
            aMapLocationClient.stopLocation();
            aMapLocationClient.onDestroy();
        }
        aMapLocationClient = null;
    }

    public void activate() {
        requestAmapLocation();
    }

    public void deactivate() {
        stopAMapLocation();
    }

    private void requestAmapLocation() {

        if (isNeedMyLocation) {


            HiStudentLog.e("-------location-------->requestAmapLocation");


            aMap.getUiSettings().setMyLocationButtonEnabled(false);
            aMap.setLocationSource(new LocationSource() {
                @Override
                public void activate(OnLocationChangedListener onLocationChangedListener) {

                    locationChangedListener = onLocationChangedListener;
                    aMapLocationClient = new AMapLocationClient(mContext);
                    AMapLocationClientOption option = new AMapLocationClientOption();
                    option.setInterval(2000);
                    option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

                    aMapLocationClient.setLocationListener(new AMapLocationListener() {
                        @Override
                        public void onLocationChanged(AMapLocation aMapLocation) {

                            if (aMapLocation != null) {
                                if (aMapLocation.getErrorCode() == 0) {

                                    locationChangedListener.onLocationChanged(aMapLocation);
                                    aMap.clear();
                                    getAMapLocationAddress(aMapLocation);
                                    Log.e("Address", aMapLocation.getAddress());
                                    Log.e("City", aMapLocation.getCity());
                                    Log.e("District", aMapLocation.getDistrict());
                                    Log.e("LocationDetail", aMapLocation.getLocationDetail());
                                    Log.e("CityCode", aMapLocation.getCityCode());
                                    Log.e("AdCode", aMapLocation.getAdCode());
                                    Log.e("Latitude", aMapLocation.getLatitude() + "");
                                    Log.e("Longitude", aMapLocation.getLongitude() + "");
                                }


                            } else {
                                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                                Log.e("AmapError", "location Error, ErrCode:"
                                        + aMapLocation.getErrorCode() + ", errInfo:"
                                        + aMapLocation.getErrorInfo());
                            }
                        }
//                    }
                    });

                    aMapLocationClient.setLocationOption(option);
                    aMapLocationClient.startLocation();

                }

                @Override
                public void deactivate() {

                    stopAMapLocation();

                }
            });

            aMap.setMyLocationEnabled(true);
        }

        HiStudentLog.e("-------location-------->requestAmapLocation02");
    }

}