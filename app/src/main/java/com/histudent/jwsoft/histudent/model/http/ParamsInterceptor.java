package com.histudent.jwsoft.histudent.model.http;

import android.os.Build;
import android.util.ArrayMap;

import com.histudent.jwsoft.histudent.HiStudentApplication;
import com.histudent.jwsoft.histudent.account.login.model.CurrentUserInfoModel;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.utils.IntenetUtil;
import com.histudent.jwsoft.histudent.commen.utils.SHA1Utils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by huyg on 2017/8/18.
 */

public class ParamsInterceptor implements Interceptor {

    private Map<String, String> bodyParams = null;

    @Override
    public Response intercept(Chain chain) throws IOException {
        bodyParams = new TreeMap<>();
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        RequestBody requestBody = request.body();
        Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);
        Charset charset = Charset.forName("UTF-8");
        MediaType contentType = requestBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(charset);
        }
        if (request.method().equals("POST") && request.body().contentType().subtype().equals("x-www-form-urlencoded")) {
            String paramsStr = buffer.readString(charset);
            initParams(paramsStr);
            String headerStr = initHeaderParam();
            builder.addHeader("Authorization", headerStr);
            initRequestBuilder(builder,request);
            request = builder.build();
        } else if (request.method().equals("POST") && request.body().contentType().subtype().equals("form-data")) {
            String headerStr = initHeaderParam();
            builder.addHeader("Authorization", headerStr);
            request = builder.build();
        }
        Response response = chain.proceed(request);
        return response;
    }

    private void initRequestBuilder(Request.Builder builder,Request request) {
        // process post body inject
        if (request.method().equals("POST") && request.body().contentType().subtype().equals("x-www-form-urlencoded")) {
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            if (bodyParams.size() > 0) {
                Iterator iterator = bodyParams.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    formBodyBuilder.add((String) entry.getKey(), (String) entry.getValue());
                }
            }
            RequestBody formBody = formBodyBuilder.build();
            String postBodyString = bodyToString(request.body());
            postBodyString += ((postBodyString.length() > 0) ? "&" : "") + bodyToString(formBody);
            builder.post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), postBodyString));
        } else if (request.method().equals("POST") && request.body().contentType().subtype().equals("form-data")) {

        } else {    // can't inject into body, then inject into url
            injectParamsIntoUrl(builder,request,bodyParams);
        }

    }

    // func to inject params into url
    private void injectParamsIntoUrl(Request.Builder builder,Request request,Map<String, String> paramsMap) {
        HttpUrl.Builder httpUrlBuilder = request.url().newBuilder();
        if (paramsMap.size() > 0) {
            Iterator iterator = paramsMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                httpUrlBuilder.addQueryParameter((String) entry.getKey(), (String) entry.getValue());
            }
        }
        builder.url(httpUrlBuilder.build());
    }

    private String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    private String initHeaderParam() {
        CurrentUserInfoModel model = HiCache.getInstance().getLoginUserInfo();
        if (model != null) {
            return String.format("Bearer %s", model.getToken());
        }
        return "";
    }

    private Map<String, String> initParams(String paramStr) {
        Map<String, String> params = new TreeMap<>();
        initFixedParams(params);
        String[] items = paramStr.split("&");
        for (int i = 0; i < items.length; i++) {
            String[] param = items[i].split("=");
            if (param.length <= 1) {
                params.put(param[0], "");
            } else {
                params.put(param[0], param[1]);
            }
        }

        initSign(params);
        return params;
    }


    private void initSign(Map<String, String> params) {
        try {
            bodyParams.put("sign", SHA1Utils.GetSHA1Date2(params));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加一些可静态获取的字段
     *
     * @param params
     * @return
     */
    private Map<String, String> initFixedParams(Map<String, String> params) {
        if (params != null) {
            params.put("version", SystemUtil.getVersion());
            params.put("devicetype", SystemUtil.DEVICETYPE);
            params.put("phonedevice", Build.MODEL);
            params.put("width", String.valueOf(SystemUtil.getScreenWind()));
            params.put("height", SystemUtil.getScreenHeight());
            params.put("uniquetag", HiStudentApplication.getInstance().PushId);
            params.put("phoneversion", android.os.Build.VERSION.RELEASE);
            params.put("timestamp", String.valueOf(System.currentTimeMillis()));
            params.put("NetType", String.valueOf(IntenetUtil.getNetworkState()));
        }
        bodyParams.putAll(params);
        return params;
    }
}
