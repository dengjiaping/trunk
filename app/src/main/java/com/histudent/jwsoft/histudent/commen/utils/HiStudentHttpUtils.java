package com.histudent.jwsoft.histudent.commen.utils;

import android.content.Context;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.HTApplication;
import com.histudent.jwsoft.histudent.HiStudentLog;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.account.login.activity.HTLauncherActivity;
import com.histudent.jwsoft.histudent.account.login.model.CheckUpdataBean;
import com.histudent.jwsoft.histudent.body.HTMainActivity;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.bean.ExitBean;
import com.histudent.jwsoft.histudent.commen.bean.HttpResponseModel;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.netease.nim.uikit.common.util.string.StringUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static java.lang.String.valueOf;

/**
 * Created by liuguiyu-pc on 2016/7/26.
 * 网络请求工具类
 */
public class HiStudentHttpUtils {

    public static Call call;

    /**
     * http请求数据
     *
     * @param activity 上下文对象
     * @param map      参数集合
     * @param url      请求地址
     * @param callBack 回调方法
     */
    public static void getDataByOKHttp(final boolean isAll, final BaseActivity activity, final Map<String, Object> map, int type, final String url,
                                       final HttpRequestCallBack callBack, List<String> urls, LoadingType loadingType) {

        if (activity != null && !(activity instanceof HTLauncherActivity) && loadingType != LoadingType.NONE)


            switch (loadingType) {
                case FLOWER:
                    activity.showLoadingImage(activity, LoadingType.FLOWER);
//                    ReminderHelper.getIntentce().showshowFlowerLoading(activity);
                    break;
                case PENCIL:
                    activity.showLoadingImage(activity, LoadingType.PENCIL);
//                    ReminderHelper.getIntentce().showshowPencilLoading(activity);
                    break;
                default:
                    activity.showLoadingImage(activity, LoadingType.FLOWER);
            }
//            activity.showLoadingImage(activity);

        int netState = IntenetUtil.getNetworkState();
        if (netState > 0) {

            call = RequestManager.getInstance().requestAsyn(url, type, map, new ReqCallBack<String>() {
                @Override
                public void onReqSuccess(String result) {
                    if (activity != null) {
                        if (loadingType != LoadingType.NONE){
                            activity.closeLoadingImage();
                        }
                        if (!activity.isFinishing()) {
                            if (isAll) {
                                callBack.onSuccess(result);
                            } else {
                                successDo(activity, result, callBack);
                            }
                        }
                    }
                    call = null;
                    HiStudentLog.e("==返回结果==" + result);
                }

                @Override
                public void onReqFailed(String errorMsg) {
                    if (activity != null) {
                        if (loadingType != LoadingType.NONE){
                            activity.closeLoadingImage();
                        }

                        if (!activity.isFinishing()) {

                            ReminderHelper.getIntentce().closeLoadingImage(activity);
//                        activity.closeLoadingImage();

                            call = null;

                            callBack.onFailure(errorMsg);

                            HiStudentLog.e("==请求异常==" + errorMsg);

                            ReminderHelper.getIntentce().ToastShow(activity, errorMsg);
                        }
                    }

                }
            }, urls, netState);

        } else {
            if (activity != null)
                ReminderHelper.getIntentce().closeLoadingImage(activity);
//                activity.closeLoadingImage();
            callBack.onFailure(activity.getString(R.string.network_unused));
            ReminderHelper.getIntentce().ToastShow(activity, activity.getString(R.string.network_unused));
        }

    }


    /**
     * http请求数据
     *
     * @param map      参数集合
     * @param url      请求地址
     * @param callBack 回调方法
     */
    public static void getDataByOKHttp(final Map<String, Object> map, int type, final String url,
                                       final HttpRequestCallBack callBack, List<String> urls) {
        int netState = IntenetUtil.getNetworkState();

        call = RequestManager.getInstance().requestAsyn(url, type, map, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                callBack.onSuccess(result);
            }

            @Override
            public void onReqFailed(String errorMsg) {
                callBack.onFailure(errorMsg);
            }
        }, urls, netState);

    }


    public static void getDataByOKHttp(final BaseActivity activity, final Map<String, Object> map,
                                       int type, final String url, final HttpRequestCallBack callBack, List<String> urls, LoadingType loadingType) {
        getDataByOKHttp(false, activity, map, type, url, callBack, urls, loadingType);
    }

    public static void postDataByOKHttp(final BaseActivity activity, final Map<String, Object> map,
                                        int type, final String url, final HttpRequestCallBack callBack, List<String> urls, LoadingType loadingType) {
        getDataByOKHttp(false, activity, map, type, url, callBack, urls, loadingType);
    }

    public static void postDataByOKHttp(BaseActivity activity, Map<String, Object> map, String url, HttpRequestCallBack callBack) {
        getDataByOKHttp(false, activity, map, RequestManager.TYPE_POST_FORM, url, callBack, null, LoadingType.FLOWER);
    }

    public static void postDataByOKHttp(Map<String, Object> map, String url, HttpRequestCallBack callBack) {
        getDataByOKHttp(false, null, map, RequestManager.TYPE_POST_FORM, url, callBack, null, LoadingType.FLOWER);
    }

    public static void postDataByOKHttp(boolean isAll, BaseActivity activity, Map<String, Object> map, String url, HttpRequestCallBack callBack) {
        getDataByOKHttp(isAll, activity, map, RequestManager.TYPE_POST_FORM, url, callBack, null, LoadingType.FLOWER);
    }

    public static void postDataByOKHttp(boolean isAll, BaseActivity activity, Map<String, Object> map, String url, HttpRequestCallBack callBack, LoadingType loadingType) {
        getDataByOKHttp(isAll, activity, map, RequestManager.TYPE_POST_FORM, url, callBack, null, loadingType);
    }

    public static void postDataByOKHttp(BaseActivity activity, Map<String, Object> map, String url, HttpRequestCallBack callBack, LoadingType loadingType) {
        getDataByOKHttp(false, activity, map, RequestManager.TYPE_POST_FORM, url, callBack, null, loadingType);
    }

    public static void getDataByOKHttp(BaseActivity activity, Map<String, Object> map, String url, final HttpRequestCallBack callBack) {
        getDataByOKHttp(false, activity, map, RequestManager.TYPE_GET, url, callBack, null, LoadingType.FLOWER);
    }

    public static void getDataByOKHttp(BaseActivity activity, Map<String, Object> map, String url, final HttpRequestCallBack callBack, LoadingType loadingType) {
        getDataByOKHttp(false, activity, map, RequestManager.TYPE_GET, url, callBack, null, loadingType);
    }

    public static void postImageByOKHttp(BaseActivity activity, List<String> filePaths, Map<String, Object> map, String url, HttpRequestCallBack callBack) {
        getDataByOKHttp(false, activity, map, RequestManager.TYPE_POST_FORM_IMG, url, callBack, filePaths, LoadingType.FLOWER);
    }

    public static void postFileByOKHttp(BaseActivity activity, List<String> filePaths, Map<String, Object> map, String url, HttpRequestCallBack callBack) {
        getDataByOKHttp(false, activity, map, RequestManager.TYPE_POST_FORM_VOICE, url, callBack, filePaths, LoadingType.FLOWER);
    }

    public static void getFileByOKHttp(BaseActivity activity, Map<String, Object> map, String url, HttpRequestCallBack callBack) {
        getDataByOKHttp(false, activity, map, RequestManager.TYPE_GET_FORM_VOICE, url, callBack, null, LoadingType.FLOWER);
    }

    public static void postVoidAndImageByOKHttp(BaseActivity activity, Map<String, Object> map, String url, HttpRequestCallBack callBack) {
        getDataByOKHttp(false, activity, map, RequestManager.TYPE_POST_FORM_VOICE_IMG, url, callBack, null, LoadingType.FLOWER);
    }

    public static void downFileByOKHttp(BaseActivity activity, Map<String, Object> map, String url, HttpRequestCallBack callBack) {
        getDataByOKHttp(false, activity, map, RequestManager.TYPE_POST_FORM_DOWN, url, callBack, null, LoadingType.FLOWER);
    }

    /**
     * 检查数据的正确性
     *
     * @param data
     * @return “1”：正确  “0”：错误  “-1”：解析异常
     */
    public static HttpResponseModel dataIsCorrect(String data) {

        HttpResponseModel model = new HttpResponseModel();

        try {


            JSONObject object = new JSONObject(data);

            model.setData(object.optString("data"));
            model.setRet(object.optInt("ret"));
            model.setMsg(object.optString("msg"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return model;
    }

    private static void successDo(BaseActivity activity, String result, HttpRequestCallBack callBack) {
        HttpResponseModel model = HiStudentHttpUtils.dataIsCorrect(result);

        if (model.getRet() == 1) {//请求成功

            if (StringUtil.isEmpty(model.getData())) {
                callBack.onSuccess("{ret:1}");
            } else {
                callBack.onSuccess(model.getData());
            }


        } else if (model.getRet() == -8) {
            if (activity instanceof HTLauncherActivity) {
                callBack.onFailure(model.getMsg());
            } else {
                String offLineMsg = activity.getString(R.string.force_exit);
                HTMainActivity.start(activity);
                EventBus.getDefault().post(new ExitBean(offLineMsg));
            }
        } else if (model.getRet() == -100) {//有新版本

            //请求版本信息
            Map<String, Object> map_sign = new HashMap<>();
            map_sign.put("devicetype", "1");
            map_sign.put("version", SystemUtil.getVersion());

            HiStudentHttpUtils.postDataByOKHttp(activity, map_sign, HistudentUrl.update_apk_url, new HttpRequestCallBack() {
                @Override
                public void onSuccess(String result) {
                    CheckUpdataBean checkUpdataBean = JSON.parseObject(result, CheckUpdataBean.class);

                    if (checkUpdataBean == null) return;

                    if (!checkUpdataBean.getVersion().equals(SystemUtil.getVersion())) {//版本号相同

                        ReminderHelper.showUpdateView_login(activity, checkUpdataBean, null);

                    }

                }

                @Override
                public void onFailure(String errorMsg) {
                }

            });

        } else {
            callBack.onFailure(model.getMsg());
            Toast.makeText(HTApplication.getInstance(), model.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    protected void post_file(Context context, final String url, final Map<String, Object> map, File file) {
        OkHttpClient client = new OkHttpClient();
        // form 表单形式上传
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (file != null) {
            // MediaType.parse() 里面是上传的文件类型。
            RequestBody body = RequestBody.create(MediaType.parse("audio/aac"), file);
            String filename = file.getName();
            // 参数分别为， 请求key ，文件名称 ， RequestBody
            requestBody.addFormDataPart("headImage", file.getName(), body);
        }
        if (map != null) {
            // map 里面是请求中所需要的 key 和 value
            for (Map.Entry entry : map.entrySet()) {
                requestBody.addFormDataPart(valueOf(entry.getKey()), valueOf(entry.getValue()));
            }
        }
        Request request = new Request.Builder().url("请求地址").post(requestBody.build()).tag(context).build();
        // readTimeout("请求超时时间" , 时间单位);
        client.newBuilder().readTimeout(5000, TimeUnit.MILLISECONDS).build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String str = response.body().string();

                } else {
                }
            }
        });

    }

}
