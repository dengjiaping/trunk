package com.histudent.jwsoft.histudent.model.http;

import android.os.Build;

import com.histudent.jwsoft.histudent.HTApplication;
import com.histudent.jwsoft.histudent.commen.utils.IntenetUtil;
import com.histudent.jwsoft.histudent.commen.utils.SHA1Utils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by huyg on 2017/10/30.
 */

public class RequestUtils {


    /**
     * 直接添加文本类型的Part到的MultipartBody的Part集合中
     *
     * @param parts Part集合
     * @param key   参数名（name属性）
     * @param value 文本内容
     */
    public static void addTextPart(List<MultipartBody.Part> parts,
                                   String key, String value) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        MultipartBody.Part part = MultipartBody.Part.createFormData(key, null, requestBody);
        parts.add(part);
    }


    /**
     * 添加一些可静态获取的字段
     *
     * @param params
     * @return
     */
    public static List<MultipartBody.Part> initFixedParams(List<MultipartBody.Part> parts,Map<String, String> params) {
        if (params != null) {
            params.put("version", SystemUtil.getVersion());
            addTextPart(parts,"version", SystemUtil.getVersion());
            params.put("devicetype", SystemUtil.DEVICETYPE);
            addTextPart(parts,"devicetype", SystemUtil.DEVICETYPE);
            params.put("phonedevice", Build.MODEL);
            addTextPart(parts,"phonedevice", Build.MODEL);
            params.put("width", String.valueOf(SystemUtil.getScreenWind()));
            addTextPart(parts,"width", String.valueOf(SystemUtil.getScreenWind()));
            params.put("height", SystemUtil.getScreenHeight());
            addTextPart(parts,"height", SystemUtil.getScreenHeight());
            params.put("uniquetag", HTApplication.getInstance().PushId);
            addTextPart(parts,"uniquetag", HTApplication.getInstance().PushId);
            params.put("phoneversion", android.os.Build.VERSION.RELEASE);
            addTextPart(parts,"phoneversion", android.os.Build.VERSION.RELEASE);
            params.put("timestamp", String.valueOf(System.currentTimeMillis()));
            addTextPart(parts,"timestamp", String.valueOf(System.currentTimeMillis()));
            params.put("NetType", String.valueOf(IntenetUtil.getNetworkState()));
            addTextPart(parts,"NetType", String.valueOf(IntenetUtil.getNetworkState()));
            initSign(parts,params);
        }

        return parts;
    }


    /**
     * 获取sign
     * @param params
     */
    public static void initSign(List<MultipartBody.Part> parts,Map<String, String> params) {
        try {
            params.put("sign", SHA1Utils.GetSHA1Date2(params));
            addTextPart(parts,"sign", SHA1Utils.GetSHA1Date2(params));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


}
