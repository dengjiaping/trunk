package com.histudent.jwsoft.histudent.commen.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.histudent.jwsoft.histudent.HTApplication;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.account.login.model.CurrentUserInfoModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.IntenetUtil;
import com.histudent.jwsoft.histudent.commen.utils.LocationUtils;
import com.histudent.jwsoft.histudent.commen.utils.RequestManager;
import com.histudent.jwsoft.histudent.comment2.bean.AddressInforBean;
import com.histudent.jwsoft.histudent.comment2.bean.LocationDB;
import com.histudent.jwsoft.histudent.comment2.utils.DBDataSet;
import com.histudent.jwsoft.histudent.comment2.utils.MyDBData;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 网络数据请求，缓存
 * Created by ZhangYT on 2016/8/3.
 */
public class HiWorldCache {


    private static HiWorldCache hiWorldCache;
    private static String account;
    private static CurrentUserInfoModel currentUserInfoModel;
    public static int Quarry = 1111, Body = 2222, GET = 333, SUCCESS = -10, FAIL = -11;

    private HiWorldCache() {
        currentUserInfoModel = HiCache.getInstance().getLoginUserInfo();
        account = currentUserInfoModel.getUserId();
    }


    //判断是否是当前账号，否者切换到当前账号
    private static void changeToCurrentModel() {
        if (hiWorldCache == null) {
            hiWorldCache = new HiWorldCache();

        } else {
            if (currentUserInfoModel.getToken().equals(HiCache.getInstance().getLoginUserInfo().getToken())) {
            } else {
                currentUserInfoModel = HiCache.getInstance().getLoginUserInfo();
                account = currentUserInfoModel.getUserId();
            }
        }
    }


    /**
     * 1. Handler handler, 存放消息内容的handler
     * 2.   int handlerWhat,消息的what
     * 3.   String url, 请求的url
     * 4.   Map<String, String> map,请求参数
     * 5.  int postType,请求类型
     * 6.  String categoryId,用于区分用户保存不同资源的标识
     * 7.  boolean isSave  请求成功后的数据是否需要保存到数据库
     */


    public static void postHttpData(final BaseActivity baseActivity, final Handler handler, final int handlerWhat,
                                    final String url, final Map<String, Object> map,
                                    final int postType, final boolean isSave,final boolean isLoadingDialog) {

        final String categoryId = url.replace(HistudentUrl.getBaseUrl(), "");//缓存标记
        //切换到当前账号用户
        changeToCurrentModel();

        int type = RequestManager.TYPE_POST_FORM;
        List<String> list = new ArrayList<>();
        boolean tag = false;

        Map<String, Object> paramsMap = new HashMap<>();

        HttpRequestCallBack callBack = new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                Log.e("---onSuccess---", result);

                //分页请求加载更多时，没有更多内容的统一提示
                if (map != null && map.containsKey("pageIndex") && Integer.parseInt(((String) map.get("pageIndex"))) != 0) {

                    if (!StringUtil.isEmpty(result)) {
                        JSONObject object = JSON.parseObject(result);

                        if (object != null &&
                                object.getIntValue("itemCount") == 0) {

                            Toast.makeText(baseActivity, R.string.no_more_data, Toast.LENGTH_LONG).show();
                        }
                    }
                }

                if (isSave) {

                    Log.e("onSuccess", "网络数据获取成功，准备保存到本地数据库");

                    //分页请求的数据
                    if (map != null && map.containsKey("pageIndex")) {

                        int currentPageIndex = Integer.parseInt((String) map.get("pageIndex"));

                        Log.e("Index", currentPageIndex + "");
                        if (currentPageIndex >= 0) {

                            savePageDataToDB(account, result, categoryId, currentPageIndex);

                        } else {

                            Log.e("HiWorldCache", "缓存数据下标有误");
                        }


                        //请求一般数据
                    } else {
                        saveDataToDB(account, result, categoryId);  //不是分页请求的

                    }


                } else {
                    Log.e("onSuccess", "网络数据获取成功，数据不需要保存");
                    Log.e("responseInfo.result", result);

                }

                if (handler != null) {

                    Message msg = handler.obtainMessage();
                    msg.what = handlerWhat;
                    msg.obj = result;
                    msg.arg1 = SUCCESS;
                    handler.sendMessage(msg);
                    Log.e("handler", result);
                    baseActivity.closeLoadingImage();
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                Message msg = handler.obtainMessage();
                msg.what = handlerWhat;
                msg.arg1 = FAIL;
                msg.obj = "{ret:-1}";
                Bundle bundle = new Bundle();
                bundle.putString("errorMsg", errorMsg);
                msg.setData(bundle);
                handler.sendMessage(msg);
                Log.e("handler", errorMsg);
                baseActivity.closeLoadingImage();
            }
        };


        if (IntenetUtil.getNetworkState() > 0) {
            if (postType == Body) {
                type = RequestManager.TYPE_POST_JSON;
            } else if (postType == Quarry) {
                type = RequestManager.TYPE_POST_FORM;


            } else if (postType == GET) {
                type = RequestManager.TYPE_GET;
            }
            if (map != null && map.size() > 0) {
                for (String key : map.keySet()) {
                    if (key.contains("file")) {
                        type = RequestManager.TYPE_POST_FORM_IMG;

                        list.add(map.get(key) + "");

                        Log.e("FilePath==>", map.get(key).toString());
                        tag = true;
                    } else {
                        paramsMap.put(key, map.get(key));
                    }
                }
            }
            HiStudentHttpUtils.getDataByOKHttp(baseActivity, paramsMap, type, url, callBack, tag == true ? list : null, isLoadingDialog ? LoadingType.FLOWER:LoadingType.NONE);


        } else {

            Log.e("<----没有网路-->", "开始准备从数据库中获取数据");

            String content = null;

            //获取数据库中分页数据
            if (map != null && map.containsKey("pageIndex")) {

                int currentPageIndex = Integer.parseInt((String) map.get("pageIndex"));
                content = getPageDBData(account, categoryId, currentPageIndex);

                //获取数据库中的一般数据
            } else {

                content = getDBData(account, categoryId);

            }


            if (!StringUtil.isEmpty(content)) {
                Message msg = handler.obtainMessage();
                msg.what = handlerWhat;
                msg.arg1 = SUCCESS;
                msg.obj = content;
                handler.sendMessage(msg);
                baseActivity.closeLoadingImage();
                Log.e("<----没有网路-->", "从数据库中获取一般数据成功");
            } else {

                Message msg = handler.obtainMessage();
                msg.what = handlerWhat;
                msg.arg1 = FAIL;
                msg.obj = "{ret:-1}";
                handler.sendMessage(msg);
                baseActivity.closeLoadingImage();
                Log.e("<----没有网路-->", "从数据库中获取数据失败");
            }
        }

    }

    //保存分页请求数据到数据库

    /**
     * @param account          用户账号
     * @param data             要保存的数据
     * @param categoryId       标记
     * @param currentPageIndex 获取该页的数据
     */

    public static void savePageDataToDB(String account, String data, String categoryId, int currentPageIndex) {
        DbUtils dbUtils = DbUtils.create(HTApplication.getInstance());
        String data1 = null;
        MyDBData dbData = null;

        if (!StringUtil.isEmpty(account)) {


            try {
                dbData = dbUtils.findById(MyDBData.class, account + categoryId + currentPageIndex);

                if (dbData == null) {
                    dbData = new MyDBData();
                    dbData.setId(account + categoryId + currentPageIndex);

                    //保存当前数据库缓存数据的页数下标
                    dbData.setCurrentPageIndex(currentPageIndex);
                    dbData.setData(data);

                    Log.e("saveDataToDB", "新建数据库");

                } else {

                    dbData.setData(data);
                    dbData.setCurrentPageIndex(currentPageIndex);
                    Log.e("savePageDataToDB", "缓存数量已增加");


                }

                dbUtils.saveOrUpdate(dbData);
                Log.e("savePageDataToDB", data);
                Log.e("savePageDataToDB", "数据保存到数据库成功！");

                Log.e("savePageDataToDBLen", data.length() + "");

            } catch (DbException e) {

                Log.e("savePageDataToDB", "数据库发生异常");
                e.printStackTrace();

            } finally {
                dbUtils.close();
            }
        } else {
            Log.e("savePageDataToDB", "用户账号获取失败！");
        }
    }


    //从数据库中取分页请求数据

    public static String getPageDBData(String account, String categoryId, int currentPageIndex) {

        DbUtils dbUtils = DbUtils.create(HTApplication.getInstance());

        if (!StringUtil.isEmpty(account)) {

            try {

                //根据个人id和相册id的链接来获取保存的相应的 相册信息
                MyDBData dbData = dbUtils.findById(MyDBData.class, account + categoryId + currentPageIndex);

                if (dbData == null) {
                    return null;
                }

                String data = dbData.getData();

                if (!StringUtil.isEmpty(data)) {

                    Log.e("getPageDataToDB", "从数据库中获取信息成功");
                    Log.e("getPageDataToDBLen", data.length() + "");

                    return data;

                } else {
                    Log.e("getPageDataToDB", "数据库中没有数据");
                }


            } catch (DbException e) {

                Log.e("getPageDataToDB", "数据库发生异常");
                e.printStackTrace();
            } finally {
                dbUtils.close();
            }


        } else {
            Log.e("getPageDataToDB", "获取用户账号失败！");
        }
        return null;
    }


    //保存一般数据到数据库

    public static void saveDataToDB(String account, String data, String categoryId) {
        DbUtils dbUtils = DbUtils.create(HTApplication.getInstance());
        String data1 = null;
        MyDBData dbData = null;
        String appCategoryId = HistudentUrl.update_apk_url.replace(HistudentUrl.getBaseUrl(), "");


        if (appCategoryId.equals(categoryId) || !StringUtil.isEmpty(account)) {//保存版本信息时不用用户账号


            try {

                if (categoryId.equals(appCategoryId)) {
                    account = "";
                }
                dbData = dbUtils.findById(MyDBData.class, account + categoryId);

                Log.e("saveDataToDB", account + categoryId);

                if (dbData == null) {
                    dbData = new MyDBData();
                    dbData.setId(account + categoryId);

                    //保存当前数据库缓存数据的页数下标
                    dbData.setData(data);
                    Log.e("saveDataToDB", data);

                    Log.e("saveDataToDB", "新建数据库");

                } else {

                    dbData.setData(data);

                    Log.e("saveDataToDB", data.length() + "");
                }

                dbUtils.saveOrUpdate(dbData);
                Log.e("saveDataToDB", data);
                Log.e("saveDataToDB", "数据保存到数据库成功！");

            } catch (DbException e) {

                Log.e("saveDataToDB", "数据库发生异常");
                e.printStackTrace();

            } finally {
                dbUtils.close();
            }
        } else {
            Log.e("saveDataToDB", "用户账号获取失败！");
        }
    }


    //获取数据库中保存的一般数据

    public static String getDBData(String account, String categoryId) {
        String appCategoryId = HistudentUrl.update_apk_url.replace(HistudentUrl.getBaseUrl(), "");
        DbUtils dbUtils = DbUtils.create(HTApplication.getInstance());

        if (appCategoryId.equals(categoryId) || !StringUtil.isEmpty(account)) {//获取版本信息时不用用户账号

            try {

                if (categoryId.equals(appCategoryId)) {
                    account = "";
                }

                //根据个人id和相册id的链接来获取保存的相应的 相册信息
                MyDBData dbData = dbUtils.findById(MyDBData.class, account + categoryId);
                Log.e("getDBData", account + categoryId);
                if (dbData == null) {
                    return null;
                }


                String data = dbData.getData();
                String content = data;

                if (!StringUtil.isEmpty(data)) {

                    Log.e("getDBData", "从数据库中获取信息成功");
                    Log.e("getDBData", data.length() + "");

                    return data;

                } else {
                    Log.e("getDBData", "数据库中没有数据");
                }


            } catch (DbException e) {

                Log.e("getDBData", "数据库发生异常");
                e.printStackTrace();
            } finally {
                dbUtils.close();
            }


        } else {
            Log.e("getDBData", "获取用户账号失败！");
        }
        return null;
    }


    //保存用户浏览的最后一页数据的页码进入数据库
    public static void saveLastPageIndexOfUserScanfToDB(String account, String categoryId, int lastPageIndex) {

        DbUtils dbUtils = DbUtils.create(HTApplication.getInstance());


        if (!StringUtil.isEmpty(account)) {

            DBDataSet dbData = null;


            try {
                dbData = dbUtils.findById(DBDataSet.class, account + categoryId);

                if (dbData == null) {
                    dbData = new DBDataSet();
                    dbData.setId(account + categoryId);

                    //保存当前数据库缓存数据的页数下标
                    dbData.setLastPageIndex(lastPageIndex);
                    dbUtils.saveOrUpdate(dbData);
                    Log.e("LastPageIndex", lastPageIndex + "");

                    Log.e("LastPageIndex", "新建浏览记录页码数据库");

                } else {

                    if (dbData.getLastPageIndex() < lastPageIndex) {

                        dbData.setLastPageIndex(lastPageIndex);
                        Log.e("LastPageIndex", "修改浏览最多页码成功！");
                        dbUtils.saveOrUpdate(dbData);
                    }
                }

            } catch (DbException e) {

                Log.e("LastPageIndex", "保存浏览最大页码异常！");
                e.printStackTrace();
            } finally {
                dbUtils.close();
            }

        } else {
            Log.e("LastPageIndex", "获取用户账号信息失败");
        }
    }


    //图片下载
    public static Bitmap downLoadImage(String url) {


        if (!StringUtil.isEmpty(url)) {

            if (IntenetUtil.getNetworkState() > 0) {

                try {
                    URL url1 = new URL(url);

                    HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    if (connection.getResponseCode() == 200) {

                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inPreferredConfig = Bitmap.Config.RGB_565;
                        InputStream is = connection.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
                        is.close();
                        Log.e("downLoadImage", "下载图片数据成功");
                        return bitmap;
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("downLoadImage", "下载图片数据失败，路径为空！");
            }
        }

        return null;
    }

    //获取用户登录时保存的地址，当查找用户位置失败时，默认返回初始化的用户位置信息类
    public static AddressInforBean getUserLocationInfor() {

        AddressInforBean addressInforBean = new AddressInforBean();
        DbUtils dbUtils = DbUtils.create(HTApplication.getInstance());
        int count = 0;

        try {
            LocationDB db = dbUtils.findById(LocationDB.class, LocationUtils.USER_LOCATION);

            while (count < 3) {
                count++;
                if (db != null) {

                    String location = db.getLocation();
                    String city = db.getCity();
                    String area = db.getAreaStr();
                    addressInforBean.setName(StringUtil.isEmpty(location) ? "" : location.equals("null") ? "" : location);
                    addressInforBean.setLatitude(db.getLat());
                    addressInforBean.setLongitude(db.getLog());
                    addressInforBean.setCity(StringUtil.isEmpty(city) ? "" : city.equals("null") ? "" : city);
                    addressInforBean.setAreaStr(StringUtil.isEmpty(area) ? "" : area.equals("null") ? "" : area);
                    addressInforBean.setDetailStr(StringUtil.isEmpty(location) ? "" : location.equals("null") ? "" : location);
                    count = 3;
                    Log.e("AddressInforBean", "数据库中查找用户位置成功！" + addressInforBean.toString());
                    return addressInforBean;
                } else {
                    //LocationUtils.getLocationUtils().getLocationInfor(HTApplication.getInstance(), null);
                }
            }


        } catch (DbException e) {
            e.printStackTrace();
        } finally {
            dbUtils.close();
        }

        Log.e("AddressInforBean", "查找用户位置信息失败");
        return addressInforBean;
    }


}


