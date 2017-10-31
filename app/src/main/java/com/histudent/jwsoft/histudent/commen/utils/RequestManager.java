package com.histudent.jwsoft.histudent.commen.utils;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.histudent.jwsoft.histudent.HiStudentApplication;
import com.histudent.jwsoft.histudent.HiStudentLog;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.account.login.model.CurrentUserInfoModel;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.model.http.ParamsInterceptor;
import com.umeng.message.PushAgent;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//http://www.cnblogs.com/whoislcj/p/5529827.html

/**
 * Created by liuguiyu-pc on 2016/10/25.
 */
public class RequestManager {

    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");//mdiatype 这个需要和服务端保持一致
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");//mdiatype 这个需要和服务端保持一致
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static final String TAG = RequestManager.class.getSimpleName();
    private static volatile RequestManager mInstance;//单利引用
    public static final int TYPE_GET = 0;//get请求
    public static final int TYPE_POST_JSON = 1;//post请求参数为json
    public static final int TYPE_POST_FORM = 2;//post请求参数为表单
    public static final int TYPE_POST_FORM_IMG = 3;//post照片
    public static final int TYPE_POST_FORM_VOICE = 4;
    public static final int TYPE_GET_FORM_VOICE = 7;
    public static final int TYPE_POST_FORM_DOWN = 5;
    public static final int TYPE_POST_FORM_VOICE_IMG = 6;
    private OkHttpClient mOkHttpClient;//okHttpClient 实例
    private Handler okHttpHandler;//全局处理子线程和M主线程通信
    private Context mContext;
    private static String FirstUrl = "";
    private static boolean isGetResult = false;
    public final static String COVER = "HUODONGCOVER";
    public final static String LOG = "HUODONGLOG";
    public final static String IMAGE = "HUODONGIMAGE";

    private RequestManager() {
    }

    /**
     * 初始化RequestManager
     */
    private RequestManager(Context context) {
        this.mContext = context;
        //初始化OkHttpClient
        mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(60, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(60, TimeUnit.SECONDS)//设置写入超时时间
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
        //初始化Handler
        okHttpHandler = new Handler(Looper.myLooper());
    }

    private static final class RequestManagerHolder{
        static final RequestManager instance = new RequestManager(HiStudentApplication.getInstance().getApplicationContext());
    }

    public static RequestManager getInstance(){
        return RequestManagerHolder.instance;
    }

//    /**
//     * 获取单例引用
//     *
//     * @return
//     */
//    public static RequestManager getInstance(Context context) {
//        RequestManager inst = mInstance;
//        RequestManager.context = HiStudentApplication.getInstance().getApplicationContext();
//        if (inst == null) {
//            synchronized (RequestManager.class) {
//                inst = mInstance;
//                if (inst == null) {
//                    inst = new RequestManager(HiStudentApplication.getInstance().getApplicationContext());
//                    mInstance = inst;
//                }
//            }
//        }
//        return inst;
//    }

    /**
     * okHttp异步请求统一入口
     *
     * @param actionUrl   接口地址
     * @param requestType 请求类型
     * @param paramsMap   请求参数
     * @param callBack    请求返回数据回调
     * @param <T>         数据泛型
     **/
    public <T> Call requestAsyn(String actionUrl, int requestType, Map<String, Object> paramsMap, ReqCallBack<T> callBack, List<String> mImgUrls, int netState) {

        //连续多次请求相同接口数据时，没返回之前，不进行后面的请求操作
        if (actionUrl.equals(FirstUrl) && isGetResult == false && HiStudentHttpUtils.call != null) {
//            ((BaseActivity) context).closeLoadingImage();
            return HiStudentHttpUtils.call;
        } else {
            isGetResult = false;
            FirstUrl = actionUrl;
        }

        TreeMap<String, Object> map_sign = new TreeMap<>();
        int count = 0;
        TreeMap<String, Object> coverMap = new TreeMap<>();
        map_sign.put("version", SystemUtil.getVersion());
        map_sign.put("devicetype", SystemUtil.DEVICETYPE);
        map_sign.put("phonedevice", Build.MODEL);
        map_sign.put("width", SystemUtil.getScreenWind());
        map_sign.put("height", SystemUtil.getScreenHeight());
        map_sign.put("uniquetag", PushAgent.getInstance(mContext).getRegistrationId());
        map_sign.put("phoneversion", android.os.Build.VERSION.RELEASE);
        map_sign.put("timestamp", System.currentTimeMillis());
        map_sign.put("NetType", netState);

        if (HiCache.getInstance().getLoginUserInfo() != null) {
            HiStudentLog.e("-----------------------------------------------------------------------------");
            HiStudentLog.e("--token-->" + HiCache.getInstance().getLoginUserInfo().getToken());
        }

        for (String key : map_sign.keySet()) {
            String value = map_sign.get(key).toString();
            if (!TextUtils.isEmpty(value))
                HiStudentLog.e("--" + key + "-->" + value);
        }
        HiStudentLog.e("----------------");
        if (paramsMap != null) {

            for (String key : paramsMap.keySet()) {
                Object value = paramsMap.get(key);
                if (value == null || "null".equals(value)) {
                    map_sign.put(key, "");
                    HiStudentLog.e("--" + key + "-->");
                } else if (COVER.equals(key) || LOG.equals(key) || key.contains(IMAGE)) {
                    coverMap.put(key, value);
                } else {
                    map_sign.put(key, value);
                    HiStudentLog.e("--" + key + "-->" + value);
                }
            }
        }
        map_sign.put("sign", SHA1Utils.GetSHA1Date(map_sign));

        HiStudentLog.e("--url-->" + actionUrl);

        Call call = null;
        switch (requestType) {
            case TYPE_GET:
                call = requestGetByAsyn(actionUrl, map_sign, callBack);
                break;
            case TYPE_POST_JSON:
                call = requestPostByAsyn(actionUrl, map_sign, callBack);
                break;

            case TYPE_POST_FORM:
                call = requestPostByAsynWithForm(actionUrl, map_sign, callBack);
                break;
            case TYPE_POST_FORM_IMG:
                call = requestPostByAsynWithForm(actionUrl, map_sign, callBack, mImgUrls, coverMap);
                break;
            case TYPE_POST_FORM_VOICE:
                call = requestPostByAsynWithForm_(actionUrl, map_sign, callBack, mImgUrls);
                break;
            case TYPE_POST_FORM_DOWN:
                call = requestPostByAsynWithForm_downloadVoice(actionUrl, map_sign, callBack);
                break;
            case TYPE_POST_FORM_VOICE_IMG:
                call = requestPostByAsynWithForm_01(actionUrl, map_sign, callBack);
                break;
            case TYPE_GET_FORM_VOICE:
                call = requestGetByAsyn_(actionUrl, map_sign, callBack);
                break;

        }
        return call;
    }

    /**
     * okHttp get异步请求
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @param callBack  请求返回数据回调
     * @param <T>       数据泛型
     * @return
     */
    private <T> Call requestGetByAsyn(final String actionUrl, TreeMap<String, Object> paramsMap, final ReqCallBack<T> callBack) {


        StringBuilder tempParams = new StringBuilder();
        try {
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key) + "", "utf-8")));
                pos++;
            }
            String requestUrl = String.format("%s?%s", actionUrl, tempParams.toString());
            final Request request = addHeaders().url(requestUrl).build();
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    failedCallBack(mContext.getResources().getString(R.string.http_request_erro), callBack);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String string = response.body().string();
                    if (response.isSuccessful()) {
                        successCallBack((T) string, callBack);
                    } else {
                        failedCallBack(mContext.getResources().getString(R.string.http_service_erro), callBack);
                    }
                }
            });
            return call;
        } catch (Exception e) {
            failedCallBack(mContext.getResources().getString(R.string.http_request_erro), callBack);
        }
        return null;
    }


    /**
     * okHttp get异步请求
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @param callBack  请求返回数据回调
     * @param <T>       数据泛型
     * @return
     */
    private <T> Call requestGetByAsyn_(final String actionUrl, TreeMap<String, Object> paramsMap, final ReqCallBack<T> callBack) {


        StringBuilder tempParams = new StringBuilder();
        try {
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key) + "", "utf-8")));
                pos++;
            }
            String requestUrl = String.format("%s?%s", actionUrl, tempParams.toString());
            final Request request = addHeaders().url(requestUrl).build();
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    failedCallBack(mContext.getResources().getString(R.string.http_request_erro), callBack);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String id = System.currentTimeMillis() + "";
                    File file = new File(FileUtil.getPathFactory(FileUtil.CacheType.VOICE, false));

                    InputStream is = null;
                    byte[] buf = new byte[2048];
                    int len = 0;
                    FileOutputStream fos = null;
                    try {
                        long total = response.body().contentLength();
                        long current = 0;
                        is = response.body().byteStream();
                        fos = new FileOutputStream(file);
                        while ((len = is.read(buf)) != -1) {
                            current += len;
                            fos.write(buf, 0, len);
                        }
                        fos.flush();

                        JSONObject object = new JSONObject();
                        object.put("ret", 1);
                        object.put("data", file.getAbsolutePath() + "=" + id);

                        successCallBack((T) object.toString(), callBack);
                    } catch (Exception e) {
                        failedCallBack(mContext.getResources().getString(R.string.http_download_erro), callBack);
                    } finally {
                        try {
                            if (is != null) {
                                is.close();
                            }
                            if (fos != null) {
                                fos.close();
                            }
                        } catch (IOException e) {
                            Log.e(TAG, e.toString());
                        }
                    }
                }
            });
            return call;
        } catch (Exception e) {
            failedCallBack(mContext.getResources().getString(R.string.http_request_erro), callBack);
        }
        return null;
    }

    /**
     * okHttp post异步请求
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @param callBack  请求返回数据回调
     * @param <T>       数据泛型
     * @return
     */
    private <T> Call requestPostByAsyn(String actionUrl, TreeMap<String, Object> paramsMap, final ReqCallBack<T> callBack) {
        try {
            StringBuilder tempParams = new StringBuilder();
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key).toString(), "utf-8")));
                pos++;
            }
            String params = tempParams.toString();
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, params);
            String requestUrl = String.format("%s", actionUrl);
            final Request request = addHeaders().url(requestUrl).post(body).build();
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    failedCallBack(mContext.getResources().getString(R.string.http_request_erro), callBack);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String string = response.body().string();
                    if (response.isSuccessful()) {
                        successCallBack((T) string, callBack);
                    } else {
                        failedCallBack(mContext.getResources().getString(R.string.http_service_erro), callBack);
                    }
                }
            });
            return call;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * okHttp post异步请求表单提交
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @param callBack  请求返回数据回调
     * @param <T>       数据泛型
     * @return
     */
    private <T> Call requestPostByAsynWithForm(String actionUrl, TreeMap<String, Object> paramsMap, final ReqCallBack<T> callBack) {
        try {
            FormBody.Builder builder = new FormBody.Builder();
            for (String key : paramsMap.keySet()) {
                builder.add(key, paramsMap.get(key).toString());
            }
            RequestBody formBody = builder.build();
            String requestUrl = String.format("%s", actionUrl);
            final Request request = addHeaders().url(requestUrl).post(formBody).build();
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    failedCallBack(mContext.getResources().getString(R.string.http_request_erro), callBack);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String string = response.body().string();
                    if (response.isSuccessful()) {
                        successCallBack((T) string, callBack);
                    } else {
                        failedCallBack(mContext.getResources().getString(R.string.http_service_erro), callBack);
                    }
                }
            });
            return call;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * okHttp post异步请求表单提交
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @param callBack  请求返回数据回调
     * @param <T>       数据泛型
     * @return
     */
    private <T> Call requestPostByAsynWithForm_downloadVoice(String actionUrl, TreeMap<String, Object> paramsMap, final ReqCallBack<T> callBack) {
        try {
            FormBody.Builder builder = new FormBody.Builder();
            for (String key : paramsMap.keySet()) {
                builder.add(key, paramsMap.get(key).toString());
            }
            RequestBody formBody = builder.build();
            String requestUrl = String.format("%s", actionUrl);
            final Request request = addHeaders().url(requestUrl).post(formBody).build();
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    failedCallBack(mContext.getResources().getString(R.string.http_request_erro), callBack);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String id = System.currentTimeMillis() + "";
                    File file = new File(FileUtil.getPathFactory(FileUtil.CacheType.VOICE, false));

                    InputStream is = null;
                    byte[] buf = new byte[2048];
                    int len = 0;
                    FileOutputStream fos = null;
                    try {
                        long total = response.body().contentLength();
                        long current = 0;
                        is = response.body().byteStream();
                        fos = new FileOutputStream(file);
                        while ((len = is.read(buf)) != -1) {
                            current += len;
                            fos.write(buf, 0, len);
                        }
                        fos.flush();

                        JSONObject object = new JSONObject();
                        object.put("ret", 1);
                        object.put("data", file.getAbsolutePath() + "=" + id);

                        successCallBack((T) object.toString(), callBack);
                    } catch (Exception e) {
                        failedCallBack(mContext.getResources().getString(R.string.http_download_erro), callBack);
                    } finally {
                        try {
                            if (is != null) {
                                is.close();
                            }
                            if (fos != null) {
                                fos.close();
                            }
                        } catch (IOException e) {
                            Log.e(TAG, e.toString());
                        }
                    }
                }
            });
            return call;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * okHttp post异步请求表单提交
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @param callBack  请求返回数据回调
     * @param <T>       数据泛型
     * @return
     */
    private <T> Call requestPostByAsynWithForm(String actionUrl, TreeMap<String, Object> paramsMap, final ReqCallBack<T> callBack,
                                               List<String> urls, TreeMap<String, Object> coverMap) {
        try {

            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            for (int i = 0; i < urls.size(); i++) {
                File f = new File(urls.get(i));
                if (f != null) {

                    if (coverMap != null && coverMap.size() > 0) {

                        if (f.getAbsolutePath().equals(coverMap.get(COVER))) {
                            builder.addFormDataPart("cover", f.getName(), RequestBody.create(MEDIA_TYPE_MARKDOWN, f));
                            Log.e("=====>COVER", f.getAbsolutePath());
                            coverMap.remove(COVER);
                        } else if (f.getAbsolutePath().equals(coverMap.get(LOG))) {
                            builder.addFormDataPart("logo", f.getName(), RequestBody.create(MEDIA_TYPE_MARKDOWN, f));
                            Log.e("=====>LOG", f.getAbsolutePath());
                            coverMap.remove(LOG);
                        } else {
                            for (String key : coverMap.keySet()) {
                                if (coverMap.get(key).equals(f.getAbsolutePath())) {
                                    builder.addFormDataPart("introImg", f.getName(), RequestBody.create(MEDIA_TYPE_MARKDOWN, f));
                                    coverMap.remove(key);
                                    Log.e("=====>IMAGE", f.getAbsolutePath());
                                    break;
                                }
                            }
                        }
                        continue;
                    }
                    builder.addFormDataPart("image", f.getName(), RequestBody.create(MEDIA_TYPE_MARKDOWN, f));

                    Log.e("=====>", f.getAbsolutePath());
                }
            }
            for (String key : paramsMap.keySet()) {
                builder.addFormDataPart(key, paramsMap.get(key).toString());
            }
            MultipartBody requestBody = builder.build();
            String requestUrl = String.format("%s", actionUrl);
            final Request request = addHeaders().url(requestUrl).post(requestBody).build();
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    failedCallBack(mContext.getResources().getString(R.string.http_request_erro), callBack);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String string = response.body().string();
                    if (response.isSuccessful()) {
                        successCallBack((T) string, callBack);
                    } else {
                        failedCallBack(mContext.getResources().getString(R.string.http_service_erro), callBack);
                    }
                }
            });
            return call;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * okHttp post异步请求表单提交
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @param callBack  请求返回数据回调
     * @param <T>       数据泛型
     * @return
     */
    private <T> Call requestPostByAsynWithForm_(String actionUrl, TreeMap<String, Object> paramsMap, final ReqCallBack<T> callBack, List<String> urls) {
        try {

            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            for (int i = 0; i < urls.size(); i++) {
                File f = new File(urls.get(i));
                if (f != null) {
                    builder.addFormDataPart("audio", f.getName(), RequestBody.create(MEDIA_TYPE_MARKDOWN, f));
                }
            }
            for (String key : paramsMap.keySet()) {
                builder.addFormDataPart(key, paramsMap.get(key).toString());
            }
            MultipartBody requestBody = builder.build();
            String requestUrl = String.format("%s", actionUrl);
            final Request request = addHeaders().url(requestUrl).post(requestBody).build();
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    failedCallBack(mContext.getResources().getString(R.string.http_request_erro), callBack);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String string = response.body().string();
                    if (response.isSuccessful()) {
                        successCallBack((T) string, callBack);
                    } else {
                        failedCallBack(mContext.getResources().getString(R.string.http_service_erro), callBack);
                    }
                }
            });
            return call;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * okHttp post异步请求表单提交
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @param callBack  请求返回数据回调
     * @param <T>       数据泛型
     * @return
     */
    private <T> Call requestPostByAsynWithForm_01(String actionUrl, TreeMap<String, Object> paramsMap, final ReqCallBack<T> callBack) {
        try {

            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

            for (String key : paramsMap.keySet()) {

                if ("voiceFile".equals(key)) {

                    String value = paramsMap.get(key).toString();
                    if (!TextUtils.isEmpty(value)) {
                        File f = new File(value);
                        builder.addFormDataPart("voicefile", f.getName(), RequestBody.create(MEDIA_TYPE_MARKDOWN, f));
                    }

                } else if ("logo".equals(key)) {

                    String value = paramsMap.get(key).toString();
                    if (!TextUtils.isEmpty(value)) {
                        File f = new File(value);
                        builder.addFormDataPart("logo", f.getName(), RequestBody.create(MEDIA_TYPE_MARKDOWN, f));
                    }

                } else if ("imageFile".equals(key)) {

                    List<String> imges = (List<String>) paramsMap.get(key);
                    if (imges != null && imges.size() > 0) {
                        for (int i = 0; i < imges.size(); i++) {
                            File f = new File(imges.get(i));
                            if (f != null) {
                                builder.addFormDataPart("file", f.getName(), RequestBody.create(MEDIA_TYPE_MARKDOWN, f));
                            }
                        }
                    }

                } else {
                    builder.addFormDataPart(key, paramsMap.get(key).toString());
                }
            }
            MultipartBody requestBody = builder.build();
            String requestUrl = String.format("%s", actionUrl);
            final Request request = addHeaders().url(requestUrl).post(requestBody).build();
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    failedCallBack(mContext.getResources().getString(R.string.http_request_erro), callBack);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String string = response.body().string();
                    if (response.isSuccessful()) {
                        successCallBack((T) string, callBack);
                    } else {
                        failedCallBack(mContext.getResources().getString(R.string.http_service_erro), callBack);
                    }
                }
            });
            return call;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 统一为请求添加头信息
     *
     * @return
     */
    private Request.Builder addHeaders() {
        Request.Builder builder = new Request.Builder();
        CurrentUserInfoModel model = HiCache.getInstance().getLoginUserInfo();
        if (model != null) {
            String token = model.getToken();
            if (!TextUtils.isEmpty(token)) {
                builder.header("Authorization", "Bearer " + token);
            }
        }
        return builder;
    }

    /**
     * 统一同意处理成功信息
     *
     * @param result
     * @param callBack
     * @param <T>
     */
    private <T> void successCallBack(final T result, final ReqCallBack<T> callBack) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    RequestManager.FirstUrl = "";
                    RequestManager.isGetResult = true;
                    callBack.onReqSuccess(result);
                }
            }
        });
    }

    /**
     * 统一处理失败信息
     *
     * @param errorMsg
     * @param callBack
     * @param <T>
     */
    private <T> void failedCallBack(final String errorMsg, final ReqCallBack<T> callBack) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    RequestManager.FirstUrl = "";
                    RequestManager.isGetResult = true;
                    callBack.onReqFailed(errorMsg);
                }
            }
        });
    }

}
